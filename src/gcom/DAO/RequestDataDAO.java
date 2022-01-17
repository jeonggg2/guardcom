package gcom.DAO;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import gcom.Model.UserEnrollModel;
import gcom.Model.statistic.RequestSimpleModel;
import gcom.common.util.CommonUtil;
import gcom.common.util.ConfigInfo;


public class RequestDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public RequestDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public List<RequestSimpleModel> getSimpleRequestList(Map<String, Object> map){
		List<RequestSimpleModel> data = new ArrayList<RequestSimpleModel>();
		
		String whereSql = " ";

		List<Integer> oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (List<Integer>) map.get("dept");			
			for (int id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return data;
		}
		
		if(oDept != null)			whereSql += "WHERE dept.no in ("+idList+") ";
		
		String sql= 
"SELECT * "
+ "FROM (  "
	+ "(SELECT 2 AS req_type, "
	+ "req.req_id, "
	+ "dept.short_name AS dept_name, "
	+ "req.mem_name, "
	+ "req.req_date AS reg_date "
	+ "FROM user_account_request AS req   "
	+ "INNER JOIN dept_info AS dept "
	+ "ON dept.no = req.dept_no  "
	+ whereSql
	+ "ORDER BY req.req_date DESC LIMIT 7)  "
	+ " "
	+ "UNION"
	+ " "
	+ "(SELECT 1 AS req_type, "
	+ "req.no AS req_id, "
	+ "dept.short_name AS dept_name, "
	+ "ur.name AS mem_name, "
	+ "req.request_server_time AS reg_date "
	+ "FROM policy_request_info AS req  "
	+ "INNER JOIN user_info AS ur ON ur.no = req.user_no AND ur.permit = 'Y' "
	+ "INNER JOIN dept_info AS dept ON ur.dept_no = dept.no  "
	+ whereSql
	+ "ORDER BY req.request_server_time DESC LIMIT 7)"
+ ") AS T "
+ "ORDER BY reg_date DESC LIMIT 7 ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(oDept != null){
				for(int t = 0; t<oDept.size() ; t++){
					pstmt.setInt(i++, oDept.get(t));
				}
				for(int t = 0; t<oDept.size() ; t++){
					pstmt.setInt(i++, oDept.get(t));
				}
			}	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				RequestSimpleModel model = new RequestSimpleModel();
				model.setRequestType(rs.getInt("req_type"));
				model.setRequestNo(rs.getInt("req_id"));
				model.setRequestDept(rs.getString("dept_name"));
				model.setRequestWriter(CommonUtil.getReplaceHtmlChar(rs.getString("mem_name")));

				data.add(model);
			}
			
		}catch(SQLException ex){
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
		
		return data;
	}

	public List<UserEnrollModel> getEnrollRequestList(Map<String, Object> map){
		List<UserEnrollModel> data = new ArrayList<UserEnrollModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_permit = map.get("user_permit").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();

		String[] oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (String id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return data;
		}
		
		if(oDept != null)			
			whereSql += "AND dept.no in ("+idList+") ";

		if(!user_id.equals("")) 	whereSql += "AND req.account_id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND req.mem_name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND req.mem_phone LIKE ? ";
		if(!user_duty.equals("")) 	whereSql += "AND req.mem_duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND req.mem_rank LIKE ? ";
		if(!user_permit.equals("")) whereSql += "AND req.permit = ? ";
		if(!start_date.equals("")) 	whereSql += "AND req.req_date >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND req.req_date < ? + interval 1 day ";

		
		whereSql += "ORDER BY req.req_id DESC LIMIT ?, ? ";	

		String sql= 
"SELECT "
+ "req.req_id, "
+ "req.mem_number, "
+ "req.account_id, "
+ "req.mem_password, "
+ "req.mem_name, "
+ "req.mem_phone, "
+ "req.mem_duty, "
+ "req.mem_rank, "
+ "IFNULL(req_date, '') AS req_date, "
+ "req.permit, "
+ "IFNULL(req.permit_admin_id, '') AS permit_admin_id, "
+ "IFNULL(req.permit_date, '') AS permit_date, "
+ "dept.short_name "
+ "FROM user_account_request AS req "
+ "INNER JOIN dept_info AS dept ON dept.no = req.dept_no ";

		sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");

			if(!user_phone.equals("")) 	pstmt.setString(i++, "%" + user_phone + "%");
			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_permit.equals("")) 	pstmt.setString(i++, user_permit);
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserEnrollModel model = new UserEnrollModel();
				model.setRequestId(rs.getInt("req_id"));
				model.setUserNumber(rs.getString("mem_number"));
				model.setUserId(rs.getString("account_id"));
				model.setPassword(rs.getString("mem_password"));
				model.setMemberName(rs.getString("mem_name"));
				model.setMemberPhone(rs.getString("mem_phone"));
				model.setMemberDuty(rs.getString("mem_duty"));
				model.setMemberRank(rs.getString("mem_rank"));
				model.setRequestDate(rs.getString("req_date"));
				model.setPermit(rs.getString("permit"));
				model.setPermitDate(rs.getString("permit_date"));
				model.setDeptName(rs.getString("short_name"));
				model.setPermitAdmin(rs.getString("permit_admin_id"));

				data.add(model);
			}
			
		}catch(SQLException ex){
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
		
		return data;
	}
	
	public int getEnrollRequestListCount(Map<String, Object> map){
		int count = 0;
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_permit = map.get("user_permit").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();

		String[] oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (String id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return count;
		}
		
		if(oDept != null)			
			whereSql += "AND dept.no in ("+idList+") ";

		if(!user_id.equals("")) 	whereSql += "AND req.account_id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND req.mem_name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND req.mem_phone LIKE ? ";
		if(!user_duty.equals("")) 	whereSql += "AND req.mem_duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND req.mem_rank LIKE ? ";
		if(!user_permit.equals("")) whereSql += "AND req.permit = ? ";
		if(!start_date.equals("")) 	whereSql += "AND req.req_date >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND req.req_date < ? + interval 1 day ";

		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM user_account_request AS req "
+ "INNER JOIN dept_info AS dept ON dept.no = req.dept_no ";

		sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");

			if(!user_phone.equals("")) 	pstmt.setString(i++, "%" + user_phone + "%");
			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_permit.equals("")) 	pstmt.setString(i++, user_permit);
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
		
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt("cnt");
			}
			
		}catch(SQLException ex){
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
		
		return count;
	}
	
	
	public HashMap<String, Object> insertUserInfoFromRequest(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String admin_id = map.get("admin_id").toString();		
		int req_id = Integer.parseInt(map.get("req_id").toString());
		
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
//		String insertSql= "INSERT INTO user_info "
//				+ "(dept_no, duty, rank, number, name, phone, id, valid, salt, pshash, ekek, session_id, public_key, private_key, password, notice) "
//				+ "SELECT dept_no, mem_duty, mem_rank, mem_number, mem_name, mem_phone, account_id, 1, null, null, null, null, null, null, mem_password, ''  FROM user_account_request WHERE req_id = ? ";

		String updateSql= "UPDATE user_account_request SET permit = 'P', permit_date = NOW(), permit_admin_id = ? WHERE req_id = ? ";

		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			
			con.setAutoCommit(false);

			/*
			// 20210102 김재혁 주석 시작
			// 기존 소스 전부 보관.
			pstmt = con.prepareStatement("SELECT mem_password  FROM user_account_request WHERE req_id = ?");
			pstmt.setInt(1,  req_id);
			rs = pstmt.executeQuery();
			
			if (rs.next() == false) {
				return result;
			}
			
			String password = rs.getString(1);
			
			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, req_id);
			pstmt.executeUpdate();
						
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next() == false) {
				
				return result;
			}
						
			int user_no = rs.getInt(1); 
					
			pstmt=con.prepareStatement(updateSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, admin_id);
			pstmt.setInt(2, req_id);
			pstmt.executeUpdate();
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
    		   		
    		// 20210102 김재혁 주석 끝
    		*/
			
			// 20210102 김재혁 새로운 만드는 부분 시작.
			// 요청 테이블에서 id 조회. 
			pstmt = con.prepareStatement("SELECT MEM_NUMBER FROM user_account_request WHERE req_id = ?");
			pstmt.setInt(1,  req_id);
			rs = pstmt.executeQuery();
			
			if (rs.next() == false) {
				return result;
			}
			
			String number = rs.getString(1);
			
			// 요청테이블 permit 컬럼 p 허가로 변경
			pstmt=con.prepareStatement(updateSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, admin_id);
			pstmt.setInt(2, req_id);
			pstmt.executeUpdate();

			// 유저테이블 permit 컬럼 p 허가로 변경
			updateSql= "UPDATE user_info SET permit = 'P' WHERE NUMBER = ? ";
			
			pstmt=con.prepareStatement(updateSql);
			pstmt.setString(1, number);
			pstmt.executeUpdate();
			
			
			// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
			updateSql= "INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ?  AND PERMIT = 'P' ";
			
			pstmt=con.prepareStatement(updateSql);
			pstmt.setString(1, number);
			pstmt.executeUpdate();
			
			con.commit();
			// 20210102 김재혁 새로운 만드는 부분 끝.
			
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

	public HashMap<String, Object> updateEnrollRequestReject(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String admin_id = map.get("admin_id").toString();		
		int req_id = Integer.parseInt(map.get("req_id").toString());
		
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;		
		String updateSql= "UPDATE user_account_request SET permit = 'R', permit_date = NOW(), permit_admin_id = ? WHERE req_id = ? ";

		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(updateSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, admin_id);
			pstmt.setInt(2, req_id);
			pstmt.executeUpdate();
			
			// 20210102 김재혁 새로운 만드는 부분.
			// 요청 테이블에서 id 조회. 
			pstmt = con.prepareStatement("SELECT MEM_NUMBER FROM user_account_request WHERE req_id = ?");
			pstmt.setInt(1,  req_id);
			rs = pstmt.executeQuery();
			
			if (rs.next() == false) {
				return result;
			}
			
			String number = rs.getString(1);
			
			updateSql= "UPDATE user_info SET permit = 'R' WHERE NUMBER = ? ";
			pstmt=con.prepareStatement(updateSql);
			pstmt.setString(1, number);
			pstmt.executeUpdate();
			
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
	
	public HashMap<String, Object> selectEnrollRequestCheckDupl(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int req_id = Integer.parseInt(map.get("req_id").toString());

		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;		
		String select1= "SELECT COUNT(*) AS cnt FROM user_info WHERE permit = 'P' and id = (SELECT account_id FROM user_account_request WHERE req_id = ? ) ";
		String select2= "SELECT COUNT(*) AS cnt FROM user_info WHERE permit = 'P' and number = (SELECT mem_number FROM user_account_request WHERE req_id = ? ) ";

		result.put("returnCode", returnCode);

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(select1 , java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, req_id);

			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if( rs.getInt("cnt") > 0 ){
					result.put("returnCode", ConfigInfo.DUP_USER_ID );
					return result;					
				}
			}
		
			pstmt=con.prepareStatement(select2 , java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, req_id);

			rs = pstmt.executeQuery();

			if(rs.next()){
				if( rs.getInt("cnt") > 0 ){
					result.put("returnCode", ConfigInfo.DUP_USER_NUMBER );
					return result;					
				}
			}
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
