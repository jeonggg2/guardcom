package gcom.DAO;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.crypto.CekInfo;
import gc.crypto.CryptoMgr;
import gc.crypto.CryptoStatus;
import gc.crypto.EkekInfo;
import gc.crypto.PkInfo;
import gc.crypto.PsHashInfo;
import gc.crypto.SaltInfo;
import gc.db.DbCon;
import gcom.common.util.ConfigInfo;


public class UserManageDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public UserManageDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public HashMap<String, Object> insertUserInfo(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int dept_no = Integer.parseInt(map.get("dept_no").toString()); 
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String number = map.get("number").toString();
		String name = map.get("name").toString();
		String phone = map.get("phone").toString();
		String id = map.get("id").toString();
		String password = map.get("password").toString();
		int secure_level = Integer.parseInt(map.get("secure_level").toString());
		String returnCode = ConfigInfo.RETURN_CODE_ERROR;

		String numberCheck = "SELECT COUNT(*) AS cnt FROM user_info WHERE number = ? ";
		String idCheck = "SELECT COUNT(*) AS cnt FROM user_info WHERE id = ? ";
		
		
		String insertSql= "INSERT INTO user_info "
				+ "(dept_no, duty, rank, number, name, phone, id, valid, salt, pshash, ekek, session_id, public_key, private_key, password, notice, secure_level, permit) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, 1, null, null, null, null, null, null, '', '', ?, 'P') ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(numberCheck);

			int i = 1;
			pstmt.setString(i++, number);

			rs = pstmt.executeQuery();
			int numberCount = 0;
			int idCount = 0;
			if(rs.next()){	
				numberCount = rs.getInt("cnt");
			}
			
			if(numberCount > 0){
				returnCode = ConfigInfo.EXIST_USER_NUMBER;
				return result;
			}
			
			pstmt=con.prepareStatement(idCheck);

			 i = 1;
			pstmt.setString(i++, id);
			rs = pstmt.executeQuery();
			if(rs.next()){	
				idCount = rs.getInt("cnt");
			}
			if(idCount > 0){
				returnCode = ConfigInfo.DUP_USER_ID;
				return result;
			}
			
			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			i = 1;
			pstmt.setInt(i++, dept_no);
			pstmt.setString(i++, duty);
			pstmt.setString(i++, rank);
			pstmt.setString(i++, number);
			pstmt.setString(i++, name);
			pstmt.setString(i++, phone);
			pstmt.setString(i++, id);
			// pstmt.setString(i++, password);
			pstmt.setInt(i++, secure_level);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next() == false) {	
				return result;
			}
						
			int user_no = rs.getInt(1); 
			
			pstmt.close();
			pstmt = null;
				
			insertSql= "UPDATE user_info SET  "
					+ "salt = ?, "
					+ "pshash = ?, "
					+ "ekek = ?, "
					+ "public_key = ?, "
					+ "private_key = ? "
					+ "WHERE no = ?";
			
			pstmt = con.prepareStatement(insertSql);
			
	    	CryptoMgr cryptoMgr = new CryptoMgr();
	    	
			byte[] pw = null;
			try {
				pw = password.getBytes("UTF-16LE");
			} catch (UnsupportedEncodingException e) {
    			cryptoMgr.terminate();
    			return result;
			}
			
    		CryptoStatus status = cryptoMgr.init();
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}
    		
    		SaltInfo saltInfo = new SaltInfo();
    		status = saltInfo.init(cryptoMgr, user_no);
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}

    		PsHashInfo psHashInfo = new PsHashInfo();
    		status = psHashInfo.init(cryptoMgr, user_no, saltInfo.getSaltData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}
    		
    		EkekInfo ekekInfo = new EkekInfo();
    		status = ekekInfo.init(cryptoMgr, user_no, psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		
    		PkInfo pkInfo = new PkInfo();
    		status = pkInfo.init(cryptoMgr, user_no, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		
    		pstmt.setBytes(1, saltInfo.getBytes()); 		
    		pstmt.setBytes(2, psHashInfo.getBytes());
    		pstmt.setBytes(3, ekekInfo.getBytes());
    		pstmt.setBytes(4, pkInfo.getPublicKeyData());
    		pstmt.setBytes(5, pkInfo.getPrivateKeyData());
    		pstmt.setInt(6, user_no);
    		
    		pstmt.executeUpdate();
    		pstmt.close();
    		pstmt = null;
    		
    		//
    		// ���� CEK ���
    		//
    		
    		pstmt = con.prepareStatement("INSERT INTO guardcom.cek_info(no, user_no, cek) VALUES (0, ?, ?)");
  		 		
    		CekInfo cekInfo = new CekInfo(); 		
    		status = cekInfo.init(cryptoMgr, user_no, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {		
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		   				
    		cryptoMgr.terminate();	   
    		
    		pstmt.setInt(1, user_no);
    		pstmt.setBytes(2, cekInfo.getBytes());
    		pstmt.executeUpdate();   		
    		
    		// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
			pstmt=con.prepareStatement("INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ?  AND PERMIT = 'P' ");
			pstmt.setString(1, number);
			pstmt.executeUpdate();
    				
    		con.commit();
    		
			returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
			
		}catch(SQLException ex){		
			ex.printStackTrace();
			if(con!=null) try{con.rollback();}catch(SQLException sqle){sqle.printStackTrace();}
		} catch(Exception e) {
			e.printStackTrace();
			if(con!=null) try{con.rollback();}catch(SQLException sqle){sqle.printStackTrace();}
		}finally {
			try{
				if (con != null) {
					if (returnCode != ConfigInfo.RETURN_CODE_SUCCESS) {
						try {
							con.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					if(rs!=null) rs.close();
					if(pstmt!=null)pstmt.close();
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			result.put("returnCode", returnCode);
		}
		return result;
	}
	
	public HashMap<String, Object> updateUserInfo(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int user_no = Integer.parseInt(map.get("user_no").toString()); 
		int dept_no = Integer.parseInt(map.get("dept_no").toString()); 
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String number = map.get("number").toString();
		String name = map.get("name").toString();
		String phone = map.get("phone").toString();
		String id = map.get("id").toString();
		String password = map.get("password").toString();
		int secure_level = Integer.parseInt(map.get("secure_level").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		String numberCheck = "SELECT COUNT(*) AS cnt FROM user_info WHERE number = ? AND no != ?";
		String idCheck = "SELECT COUNT(*) AS cnt FROM user_info WHERE id = ? AND no != ?";

		String insertSql= "";
		String selectSql = "";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);

			pstmt=con.prepareStatement(numberCheck);

			int i = 1;
			pstmt.setString(i++, number);
			pstmt.setInt(i++, user_no);

			rs = pstmt.executeQuery();
			int numberCount = 0;
			int idCount = 0;
			if(rs.next()){	
				numberCount = rs.getInt("cnt");
			}
			
			if(numberCount > 0){
				result.put("returnCode", ConfigInfo.EXIST_USER_NUMBER);
				return result;
			}

			
			pstmt=con.prepareStatement(idCheck);

			 i = 1;
			pstmt.setString(i++, id);
			pstmt.setInt(i++, user_no);
			rs = pstmt.executeQuery();
			if(rs.next()){	
				idCount = rs.getInt("cnt");
			}
			if(idCount > 0){
				returnCode = ConfigInfo.DUP_USER_ID;
				return result;
			}
			
			byte[] salt = null;
	    	byte[] psHash = null;
	    	byte[] ekek = null;
	    	byte[] publicKey = null;
	    	byte[] privateKey = null;
	    	byte[] pw = null;
	    	
	    	CryptoMgr cryptoMgr = new CryptoMgr();
			CryptoStatus status = cryptoMgr.init();
			PsHashInfo psHashInfo = null;
			EkekInfo ekekInfo = null;
			
			if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
    			return result;
    		}
	    	
			if(password != null && password != "") {
				selectSql = "SELECT SALT, PSHASH FROM pw_user_latest_log WHERE USER_NO = ?";

				pstmt=con.prepareStatement(selectSql);
				pstmt.setString(1, number);
				rs = pstmt.executeQuery();
				
				try {
					pw = password.getBytes("UTF-16LE");
				} catch (UnsupportedEncodingException e) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				while (rs.next()) {
					byte[] saltTemp;
			    	byte[] psHashTemp;
			    	
			    	saltTemp = rs.getBytes(1);
			    	psHashTemp = rs.getBytes(2);
			    	
			    	status = cryptoMgr.authenticatePassword(pw, saltTemp, psHashTemp);
			    	
			    	if (status == CryptoStatus.CS_SUCCESS) {
			    		cryptoMgr.terminate();
			    		returnCode = ConfigInfo.PW_USED;
		    			result.put("returnCode", returnCode);
			    		return result;
			    	}
				}
	    		
	    		
	    		
	    		SaltInfo saltInfo = new SaltInfo();
	    		status = saltInfo.init(cryptoMgr, user_no);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
	    		}

	    		psHashInfo = new PsHashInfo();
	    		status = psHashInfo.init(cryptoMgr, user_no, saltInfo.getSaltData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
	    		}
	    		
	    		ekekInfo = new EkekInfo();
	    		status = ekekInfo.init(cryptoMgr, user_no, psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	        		cryptoMgr.terminate();	   
	        		result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
	    		}
	    		
	    		PkInfo pkInfo = new PkInfo();
	    		status = pkInfo.init(cryptoMgr, user_no, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	        		cryptoMgr.terminate();	   
	        		result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
	    		}
	    		
	    		salt = saltInfo.getBytes();
		    	psHash = psHashInfo.getBytes();
		    	ekek = ekekInfo.getBytes();
		    	publicKey = pkInfo.getPublicKeyData();
    			privateKey = pkInfo.getPrivateKeyData();
			}
			
			insertSql= "UPDATE user_info SET  "
					+ "dept_no = ?, "
					+ "duty = ?, "
					+ "rank = ?, "
					+ "number = ?, "
					+ "name = ?, "
					+ "phone = ?, "
					+ "secure_level = ?, "
					+ "id = ? ";

					if(password != null && password != "")
						insertSql +=  ", salt = ?, pshash = ?, public_key = ?, private_key = ? ";

					insertSql += "WHERE no = ? ";
					
			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			i = 1;
			pstmt.setInt(i++, dept_no);
			pstmt.setString(i++, duty);
			pstmt.setString(i++, rank);
			pstmt.setString(i++, number);
			pstmt.setString(i++, name);
			pstmt.setString(i++, phone);
			pstmt.setInt(i++, secure_level);
			pstmt.setString(i++, id);
			
			if (password != null && password != "") {
				pstmt.setBytes(i++, salt);
				pstmt.setBytes(i++, psHash);
				pstmt.setBytes(i++, publicKey);
				pstmt.setBytes(i++, privateKey);
				pstmt.setInt(i++, user_no);
				pstmt.executeUpdate();
				
				// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
				insertSql= "INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ? AND PERMIT = 'P' ";
				
				pstmt=con.prepareStatement(insertSql);
				pstmt.setString(1, number);
				pstmt.executeUpdate();
				
				insertSql = "UPDATE cek_info SET cek = ? WHERE USER_NO = ?";
	    		CekInfo cekInfo = new CekInfo(); 		
	    		status = cekInfo.init(cryptoMgr, user_no, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {		
	        		cryptoMgr.terminate();	   
	        		return result;
	    		}
	    		pstmt=con.prepareStatement(insertSql);
	    		pstmt.setBytes(1, cekInfo.getBytes());
	    		pstmt.setInt(2, user_no);
	    		pstmt.executeUpdate(); 
				
			} else {
				pstmt.setInt(i++, user_no);
				pstmt.executeUpdate();	
			}
			
			cryptoMgr.terminate();
			con.commit();
			result.put("returnCode", returnCode);
			
		}catch(SQLException ex){
			result.put("returnCode", ConfigInfo.RETURN_CODE_ERROR);
			if(con!=null) try{con.rollback();}catch(SQLException sqle){sqle.printStackTrace();}
			ex.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public HashMap<String, Object> removeUserInfo(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		int user_no = Integer.parseInt(map.get("user_no").toString()); 
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String insertSql= "UPDATE user_info SET  "
				+ "valid = 0 "
				+ "WHERE no = ? AND permit = 'P'";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			pstmt.setInt(i++, user_no);

			pstmt.executeUpdate();
			
			result.put("returnCode", returnCode);
			
			
		}catch(SQLException ex){
			result.put("returnCode", ConfigInfo.RETURN_CODE_ERROR);
			ex.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
