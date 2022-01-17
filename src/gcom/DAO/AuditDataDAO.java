package gcom.DAO;

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

import gc.db.DbCon;
import gcom.Model.AuditClientModel;
import gcom.Model.AuditServerModel;
import gcom.Model.ServerAuditModel;
import gcom.Model.statistic.AuditClientSimpleModel;
import gcom.common.util.ConfigInfo;


public class AuditDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;

	public AuditDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getAuditClientLogListCount(HashMap<String, Object> map){
		int result = 0;

		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String pc_name = map.get("pc_name").toString();
		String module_name = map.get("module_name").toString();
		String description = map.get("description").toString();
		
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
			return result;
		}
		if(oDept != null)			whereSql += "AND ur.dept_no in ("+idList+") ";
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!user_duty.equals("")) 	whereSql += "AND ur.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND ur.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!module_name.equals("")) 	whereSql += "AND audit.module_name LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND audit.description LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND audit.audit_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND (audit.audit_client_time < ? + interval 1 day) ";
		
	
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM client_audit AS audit "
						+ "INNER JOIN user_info AS ur ON ur.no = audit.user_no "
						+ "INNER JOIN agent_log AS agent ON agent.no = audit.agent_log_no "
						+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
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

			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!module_name.equals("")) 	pstmt.setString(i++, "%" + module_name + "%");
			if(!description.equals("")) 	pstmt.setString(i++, "%" + description + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

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
	
	public List<AuditClientModel> getAuditClientLogList(HashMap<String, Object> map){
		List<AuditClientModel> data = new ArrayList<AuditClientModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String pc_name = map.get("pc_name").toString();
		String module_name = map.get("module_name").toString();
		String description = map.get("description").toString();
		
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
		if(oDept != null)			whereSql += "AND ur.dept_no in ("+idList+") ";
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!user_duty.equals("")) 	whereSql += "AND ur.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND ur.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!module_name.equals("")) 	whereSql += "AND audit.module_name LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND audit.description LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND audit.audit_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND (audit.audit_client_time < ? + interval 1 day) ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY audit.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "audit.no AS audit_no, "
+ "ifnull(audit.module_name, '') AS module_name, "
+ "ifnull(audit.description, '') AS description, "
+ "ifnull(audit.audit_server_time, '') AS audit_server_time, "
+ "ifnull(audit.audit_client_time, '') AS audit_client_time, "
+ "ifnull(audit.status, '') AS status, "
+ "ur.number AS user_no,  "
+ "ur.id AS user_id, "
+ "ur.name AS user_name, "
+ "ur.dept_no, "
+ "ur.duty,  "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM client_audit AS audit "
+ "INNER JOIN user_info AS ur ON ur.no = audit.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = audit.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";

		
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

			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!module_name.equals("")) 	pstmt.setString(i++, "%" + module_name + "%");
			if(!description.equals("")) 	pstmt.setString(i++, "%" + description + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
	
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				AuditClientModel model = new AuditClientModel();
				model.setAuditNo(rs.getInt("audit_no"));
				model.setModuleName(rs.getString("module_name"));
				model.setDescription(rs.getString("description"));
				model.setServerTime(rs.getString("audit_server_time"));
				model.setClientTime(rs.getString("audit_client_time"));
				model.setStatus(rs.getString("status"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("user_name"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptId(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));
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
	
	public List<AuditClientSimpleModel> getAuditClientSimpleLogList(Map<String, Object> map){
		List<AuditClientSimpleModel> data = new ArrayList<AuditClientSimpleModel>();
		
		String whereSql = "";
		
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
		if(oDept != null)			whereSql += "WHERE ur.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += " ORDER BY audit.no DESC LIMIT 9 ";	
		
		String sql= 
"SELECT "
+ "audit.no AS audit_no, "
+ "audit.description, "
+ "audit.level, "
+ "ur.name AS user_name, "
+ "dept.short_name "
+ "FROM client_audit AS audit "
+ "INNER JOIN user_info AS ur ON ur.no = audit.user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";


sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(oDept != null){
				for(int t = 0; t<oDept.size() ; t++){
					pstmt.setInt(i++, oDept.get(t));
				}
			}	
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				AuditClientSimpleModel model = new AuditClientSimpleModel();
				model.setAuditNo(rs.getInt("audit_no"));
				model.setAction(rs.getString("description"));
				model.setDeptName(rs.getString("short_name"));
				model.setUserName(rs.getString("user_name"));
				model.setLevel(rs.getInt("level"));
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
	
	public int getAuditServerLogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_ip = map.get("user_ip").toString();
		String description = map.get("description").toString();
		String parameter = map.get("parameter").toString();
		String status = map.get("status").toString();
		
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();


		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!user_ip.equals("")) 	whereSql += "AND audit.ip LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND audit.description LIKE ? ";
		if(!parameter.equals("")) 	whereSql += "AND audit.parameter LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND audit.status LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND audit.audit_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND (audit.audit_time < ? + interval 1 day) ";

	
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM server_audit AS audit ";

sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");

			if(!user_ip.equals("")) pstmt.setString(i++, "%" + user_ip + "%");
			if(!description.equals("")) pstmt.setString(i++, "%" + description + "%");
			if(!parameter.equals("")) pstmt.setString(i++, "%" + parameter + "%");
			if(!status.equals("")) pstmt.setString(i++, "%" + status + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

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
	
	public List<AuditServerModel> getAuditServerLogList(HashMap<String, Object> map){
		List<AuditServerModel> data = new ArrayList<AuditServerModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_ip = map.get("user_ip").toString();
		String description = map.get("description").toString();
		String parameter = map.get("parameter").toString();
		String status = map.get("status").toString();
		
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();


		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!user_ip.equals("")) 	whereSql += "AND audit.ip LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND audit.description LIKE ? ";
		if(!parameter.equals("")) 	whereSql += "AND audit.parameter LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND audit.status LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND audit.audit_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND (audit.audit_time < ? + interval 1 day) ";

		
		whereSql += "ORDER BY audit.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "audit.no AS audit_no, "
+ "IFNULL(audit.id, '') AS admin_id, "
+ "IFNULL(audit.ip, '') AS ip, "
+ "IFNULL(audit.parameter, '') AS parameter, "
+ "IFNULL(audit.description, '') AS description, "
+ "IFNULL(audit.audit_time, '') AS audit_time, "
+ "IFNULL(audit.status, '') AS status "
+ "FROM server_audit AS audit ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");

			if(!user_ip.equals("")) pstmt.setString(i++, "%" + user_ip + "%");
			if(!description.equals("")) pstmt.setString(i++, "%" + description + "%");
			if(!parameter.equals("")) pstmt.setString(i++, "%" + parameter + "%");
			if(!status.equals("")) pstmt.setString(i++, "%" + status + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);


			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				AuditServerModel model = new AuditServerModel();
				model.setAuditNo(rs.getInt("audit_no"));
				model.setIpAddr(rs.getString("ip"));
				model.setAdminId(rs.getString("admin_id"));
				model.setParameter(rs.getString("parameter"));
				model.setDescription(rs.getString("description"));
				model.setAuditTime(rs.getString("audit_time"));
				model.setStatus(rs.getString("status"));

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
	
	
	public String getAuditServerWorkData(int key){
		String result = "";
		
		String whereSql = "WHERE no = ? ";
	
		String sql= 
				"SELECT "
						+ "parameter AS data "
						+ "FROM server_audit AS audit ";

sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;

			pstmt.setInt(i++, key);
		
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getString("data");				
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

	public HashMap<String, Object> insertServeriAudit(ServerAuditModel audit) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String insertSql= "INSERT INTO "
				+ "server_audit "
				+ "(ip, id, action_id, parameter, description, status) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(insertSql);

			int i = 1;
			pstmt.setString(i++, audit.getWorkIp());
			pstmt.setString(i++, audit.getAdminId());
			pstmt.setInt(i++, audit.getActionId());
			pstmt.setString(i++, audit.getParameter());
			pstmt.setString(i++, audit.getDescription());
			pstmt.setString(i++, audit.getStatus());

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
	
	public HashMap<String, Object> getAuditMap(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("audit", "N");

		String sql= 
				"SELECT "
				+ "audit.uri_id, "
				+ "audit.uri, "
				+ "audit.action_id, "
				+ "audit.description "
				+ "FROM audit_map_info AS audit "
				+ "WHERE uri = ? ";

					
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			pstmt.setString(1, map.get("uri").toString());

			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result.put("audit", "Y");
				result.put("uri_id", rs.getInt("uri_id"));
				result.put("uri", rs.getString("uri"));
				result.put("action_id", rs.getInt("action_id"));
				result.put("description", rs.getString("description"));
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
	
}
