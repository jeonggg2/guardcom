package gcom.drm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gc.db.DbCon;
import gcom.common.util.ConfigInfo;
import gcom.drm.model.DrmDecryptionRequestModel;

public class DrmDecryptionRequestDao {
	
	public int getRequestedPolicyListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String policy_permit = map.get("policy_permit").toString();
		
		String[] oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (@SuppressWarnings("unused") String id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return result;
		}
		
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!user_phone.equals("")) whereSql += "AND ur.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND ur.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND ur.rank LIKE ? ";
		if(!user_number.equals("")) whereSql += "AND ur.number LIKE ? ";
		if(!policy_permit.equals("")) whereSql += "AND request.permit = ? ";
		
		if(oDept != null)			whereSql += " AND ur.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM file_export_request_info AS request "
+ "INNER JOIN user_info AS ur ON ur.no = request.user_no "
+ "INNER JOIN agent_info AS agent ON agent.no = request.agent_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";

sql += whereSql;			
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;

			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

			if(!user_duty.equals("")) pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals(""))	pstmt.setString(i++, "%" + user_number + "%");
			if(!policy_permit.equals("")) 	pstmt.setString(i++, policy_permit);

			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}
	
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getInt("cnt");				
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
	
	public List<DrmDecryptionRequestModel> getRequestedPolicyList(HashMap<String, Object> map){
		List<DrmDecryptionRequestModel> data = new ArrayList<DrmDecryptionRequestModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String policy_permit = map.get("policy_permit").toString();
		
		String[] oDept = null;
		StringBuilder idList = new StringBuilder();
		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (@SuppressWarnings("unused") String id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return data;
		}
		
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND ur.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND ur.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND ur.rank LIKE ? ";
		if(!user_number.equals("")) whereSql += "AND ur.number LIKE ? ";
		if(!policy_permit.equals("")) whereSql += "AND request.permit = ? ";
		
		if(oDept != null)			whereSql += " AND ur.dept_no in ("+idList+") ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY request.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "request.no AS request_no, "
+ "request.file_path, "
+ "request.file_hash, "
+ "request.file_id, "
+ "request.file_list, "
+ "request.notice, "
+ "request.request_server_time, "
+ "IFNULL(request.request_client_time, '') AS request_client_time, "
+ "request.permit, "
+ "IFNULL(request.permit_date, '') as permit_date, "
+ "IFNULL(request.permit_admin_id, '') as permit_admin_id, "
+ "ur.number AS user_no, "
+ "ur.id AS user_id, "
+ "ur.name AS user_name, "
+ "ur.dept_no, "
+ "ur.duty, "
+ "ur.rank, "
+ "ur.phone, "
+ "ur.number AS user_no, "
+ "agent.no as agent_no, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM file_export_request_info AS request "
+ "INNER JOIN user_info AS ur ON ur.no = request.user_no "
+ "INNER JOIN agent_info AS agent ON agent.no = request.agent_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";

sql += whereSql;			
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

			if(!user_duty.equals("")) pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals(""))	pstmt.setString(i++, "%" + user_number + "%");
			if(!policy_permit.equals("")) 	pstmt.setString(i++, policy_permit);
			
		 	if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DrmDecryptionRequestModel model = new DrmDecryptionRequestModel();

				model.setRequestNo(rs.getInt("request_no"));
				model.setUserNo(rs.getString("user_no"));
				
				model.setIpAddr(rs.getString("ip_addr"));
				model.setUserName(rs.getString("user_name"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setUserNo(rs.getString("user_no"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPhone(rs.getString("phone"));
				
				model.setFilePath(rs.getString("file_path"));
				model.setFileHash(rs.getString("file_hash"));
				model.setFileId(rs.getString("file_id"));
				model.setFileList(rs.getString("file_list"));
				
				model.setUserNumber(rs.getString("user_no"));
				model.setReqNotice(rs.getString("notice"));
				model.setPermitState(rs.getString("permit"));
				model.setPermitDate(rs.getString("permit_date"));
				model.setPermitStaf(rs.getString("permit_admin_id"));
				model.setReqClientTime(rs.getString("request_client_time"));
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
	
	public HashMap<String, Object> updatePermitRequestPolicy(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int requestNo = Integer.parseInt(map.get("request_no").toString());
		String adminId = map.get("admin_id").toString();
		String sql = "UPDATE file_export_request_info "
				+ "SET "
				+ "permit = 'P' "
				+ ",permit_date = NOW()"
				+ ",permit_admin_id = ? "
			+ "WHERE no = ? ";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, adminId);
			pstmt.setInt(2, requestNo);
			pstmt.executeUpdate();
			con.commit();
			
			result.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
			
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
	
	public HashMap<String, Object> updateRejectRequestPolicy(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int requestNo = Integer.parseInt(map.get("request_no").toString());
		String adminId = map.get("admin_id").toString();
		
		String sql = "UPDATE file_export_request_info "
				+ "SET "
				+ "permit = 'R' "
				+ ",permit_date = NOW()"
				+ ",permit_admin_id = ? "
			+ "WHERE no = ? "; 
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, adminId);
			pstmt.setInt(2, requestNo);
			pstmt.executeUpdate();
			
			con.commit();
			result.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
			
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

}
