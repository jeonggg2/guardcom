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

import gc.crypto.CryptoMgr;
import gc.crypto.CryptoStatus;
import gc.db.DbCon;
import gcom.common.util.ConfigInfo;
import gcom.common.util.EncProc;
import gcom.common.util.encrypto.hashEncrypto;


public class LoginDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public LoginDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}

	public HashMap<String, Object> selectUserLoginCheck(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String userId = map.get("userId").toString();
    	String userPw = map.get("userPw").toString();
    	
    	byte[] password = null;
		try {
			password = userPw.getBytes("UTF-16LE");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String returnCode = ConfigInfo.RETURN_CODE_ERROR;
    	
 
    	CryptoMgr cryptoMgr = new CryptoMgr();
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	byte[] salt;
    	byte[] psHash;
    	CryptoStatus status;
    	
    	try {
    		
    		status = cryptoMgr.init();
    		if (status != CryptoStatus.CS_SUCCESS) {
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		 
			//con = ds.getConnection();
			con = DbCon.getConnection();
    		if (con == null) {
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		con.setAutoCommit(false);
    		
    		pstmt = con.prepareStatement("SELECT salt, pshash, no, dept_no, name, id FROM guardcom.user_info WHERE (id = ?) AND PERMIT = 'P'"); 
    		if (pstmt == null) {
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		
    		pstmt.setString(1, userId);
    		
    		if (! pstmt.execute()) {
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		
    		rs = pstmt.getResultSet();
    		if (rs == null) {
    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		
    		if (! rs.next()) {
    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		
    		salt = rs.getBytes(1);
    		psHash = rs.getBytes(2);
    		
    		status = cryptoMgr.authenticatePassword(password, salt, psHash);
    		if (status == CryptoStatus.CS_UNAUTHORIZED) {
    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
    			result.put("returnCode", returnCode);
    			return result;
    		} else if (status != CryptoStatus.CS_SUCCESS) {
    			returnCode = ConfigInfo.RETURN_CODE_ERROR;
    			result.put("returnCode", returnCode);
    			return result;
    		}
    		
			returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
			result.put("userNo", rs.getString("no"));
			result.put("deptNo", rs.getString("dept_no"));
			result.put("userName", rs.getString("name"));
			result.put("userId", rs.getString("id"));
			result.put("returnCode", returnCode);
			return result;
			
    	} catch (Exception e) {
    		
    	} finally {
    		
    		cryptoMgr.terminate();
    		
    		try {
	    		if (rs != null) rs.close();
	    		if (pstmt != null) pstmt.close();
	    		if (con != null) con.close();
    		} catch (SQLException e) {
    			
    		}
    		
    	}
    	
		return result;
    	
	}

	public HashMap<String, Object> selectConsoleLoginCheck(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String userId = map.get("userId").toString();
		String userIp = map.get("userIp").toString();
    	String userPw = "";
    	
		try {
			userPw = EncProc.encrypt(map.get("userPw").toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
    	String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
    	
    	String sql= 
				"SELECT no, "
				+ "dept_no, "
				+ "id, "
				+ "pw,"
				+ "admin_mode,"
				+ "ip_addr0,"
				+ "ip_addr1  "
				+ "FROM admin_info "
				+ "WHERE id = ? ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result.put("userNo", rs.getString("no"));
				result.put("deptNo", rs.getString("dept_no"));
				result.put("userId", rs.getString("id"));
				result.put("adminMode", rs.getInt("admin_mode"));
				
				if (!hashEncrypto.HashEncrypt(userPw).equals(rs.getString("pw"))) {
					returnCode = ConfigInfo.NOT_CORRECT_PASSWORD;
					result.put("returnCode", returnCode);
				} else if(!userIp.equals(rs.getString("ip_addr0")) &&  !userIp.equals(rs.getString("ip_addr1")) && !rs.getString("ip_addr1").equals("0.0.0.0")  && !rs.getString("ip_addr0").equals("0.0.0.0")){
					//IP가 일치하지 않음
					returnCode = ConfigInfo.NOT_CORRECT_IP;
					result.put("returnCode", returnCode);					
				}
				else {
					result.put("returnCode", returnCode);
				}
				
			} else {
				returnCode = ConfigInfo.NOT_EXIST_USER;
				result.put("returnCode", returnCode);
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
    	
		return result;
	}
	
//	public HashMap<String, Object> selectConsoleLoginCheck(HashMap<String, Object> map) {
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		String userId = map.get("userId").toString();
//		String userIp = map.get("userIp").toString();
//    	String userPw = map.get("userPw").toString();	
//    	HttpSession session = (HttpSession) map.get("session");
//    	
//    	String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
//    	
//    	Connection con = null;
//    	PreparedStatement stmt = null;
//    	ResultSet rs = null;
//    	
//    	try {
//    		
//    		con = ds.getConnection();
//    		
//    		if (con == null) {			
//    			returnCode = ConfigInfo.RETURN_CODE_ERROR;
//    			result.put("returnCode", returnCode);
//    			return result;
//    		}		
//    		
//    		stmt = con.prepareStatement("SELECT guardcom.admin_info.no, dept_no, depth, user_no, id, pw, ip_addr0, ip_addr1, admin_mode, salt, hash FROM guardcom.admin_info, guardcom.dept_info WHERE (((id = ?) AND ((pw is NULL) OR (pw = PASSWORD(?))) and (dept_no = guardcom.dept_info.no)))");
//    		stmt.setNString(1, userId);
//    		stmt.setNString(2, userPw);
//    		
//    		if (stmt.execute()) {
//    			
//    			rs = stmt.getResultSet();
//    			
//    			if (rs.next()) {
//    				
//    				AdminVo obj = new AdminVo();
//    				
//    				if (rs.getInt(1) > 2)
//    					obj.setNo(rs.getInt(1));				
//    				else
//    					obj.setNo(0);
//    				
//    				obj.setDept_no(rs.getInt(2));				
//    				obj.setDept_depth(rs.getInt(3));
//    				obj.setUser_no(rs.getInt(4));
//    				obj.setLogin_id(rs.getString(5));				
//    				obj.setIp1(rs.getString(7));				
//    				obj.setIp2(rs.getString(8));
//    				obj.setLogin_pw(rs.getString(6));
//    				obj.setAdmin_Mode(rs.getInt(9));
//    				
//    				if (obj.getLogin_pw() == null) {
//    					
//    					byte[] salt = rs.getBytes(10);
//    					byte[] hash = rs.getBytes(11);
//    					
//    					if ((salt == null) || (hash == null)) {
//    		    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
//    		    			result.put("returnCode", returnCode);
//    		    			return result;
//    					}
//    					
//    					CryptoMgr cryptoMgr = new CryptoMgr();
//    					CryptoStatus status;
//    					status = cryptoMgr.init();
//    					if (status != CryptoStatus.CS_SUCCESS) {
//    		    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
//    		    			result.put("returnCode", returnCode);
//    		    			return result;
//    					}
//    					
//    					status = cryptoMgr.authenticatePassword(userPw.getBytes(), salt, hash);
//    					
//    					cryptoMgr.terminate();
//    					
//    					
//    					if (status == CryptoStatus.CS_INVALID_HANDLE) {
//    						
////    						wait(30000);
//    						
//    						status = cryptoMgr.init();
//    						
//    						if (status != CryptoStatus.CS_SUCCESS) {
//        		    			returnCode = ConfigInfo.RETURN_CODE_ERROR;
//        		    			result.put("returnCode", returnCode);
//        		    			return result;
//    						}
//    						
//    						status = cryptoMgr.authenticatePassword(userPw.getBytes(), salt, hash);
//    						cryptoMgr.terminate();
//    					}
//    									
//    					
//    					if (status != CryptoStatus.CS_SUCCESS) {
//    		    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
//    		    			result.put("returnCode", returnCode);
//    		    			return result;
//    					}				
//    					
//    				}
//    				
//    				String ip0 = rs.getString(7);
//    				String ip1 = rs.getString(8);
//    				String remoteAddr = userIp;
//    				
//    				Connection conA = null;
//    				PreparedStatement stmtA = null;
//    				ResultSet rsA = null;		
//    				String sessionStringA, nowsessionStringA;				
//    				
//    				conA = ds.getConnection();
//    				
//    				if (conA == null) {
//		    			returnCode = ConfigInfo.RETURN_CODE_ERROR;
//		    			result.put("returnCode", returnCode);
//		    			return result;
//    				}				
//    								
//    				if (remoteAddr.equalsIgnoreCase("0:0:0:0:0:0:0:1") ||
//    					remoteAddr.equalsIgnoreCase("127.0.0.1")) {
//    										
//    					String sessionString = session.getId();
//    					
//    					stmt = con.prepareStatement("UPDATE admin_info SET session=? WHERE id = ?");
//    					stmt.setNString(1, sessionString);
//    					stmt.setNString(2, userId);
//    					stmt.execute();
//    					stmt.close();
//    					
//    					obj.SetSession(sessionString);
//    					
//		    			returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
//		    			result.put("returnCode", returnCode);
//		    			return result;
//    				}
//    				
//    				if (ip0 != null) {
//    					
//    					if (!remoteAddr.equalsIgnoreCase(ip0)) {
//    						
//    						if (ip1 == null) {
//    			    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
//    			    			result.put("returnCode", returnCode);
//    			    			return result;
//    						}
//    						
//    						if (! remoteAddr.equalsIgnoreCase(ip1)) {
//    			    			returnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
//    			    			result.put("returnCode", returnCode);
//    			    			return result;
//    						}
//    						
//    					}
//    					
//    				}
//    				
//    				String sessionString = session.getId();
//    				
//    				stmt = con.prepareStatement("UPDATE admin_info SET session=? WHERE id = ?");
//    				stmt.setNString(1, sessionString);
//    				stmt.setNString(2, userId);
//    				stmt.execute();
//    				stmt.close();
//    				
//    				obj.SetSession(sessionString);			
//    				
//	    			returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
//	    			result.put("returnCode", returnCode);
//					result.put("userNo", rs.getString("no"));
//					result.put("deptNo", rs.getString("dept_no"));
//					result.put("userId", rs.getString("id"));
//					result.put("adminMode", rs.getInt("admin_mode"));
//	    			return result;
//    			}
//    			
//    		}
//    		
//			returnCode = ConfigInfo.RETURN_CODE_ERROR;
//			result.put("returnCode", returnCode);
//			return result;
//			
//    	} catch (Exception e) {
//    		
//    		
//    	} finally {
//
//    		try {
//	    		if (rs != null) rs.close();
//	    		if (pstmt != null) pstmt.close();
//	    		if (con != null) con.close();
//    		} catch (SQLException e) {
//    			
//    		}
//    		
//    	}
//		return result;
//    	
//	}
	
}
