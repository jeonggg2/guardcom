package gcom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;
import gcom.Model.LoginLogModel;


public class LoginLogDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public LoginLogDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getLoginlogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();

		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();


		
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
			
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";		
		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND login.login_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND login.login_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND userinfo.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt " 
+ "FROM login_log AS login "
+ "INNER JOIN user_info AS userinfo ON login.user_no = userinfo.no "
+ "INNER JOIN agent_log AS agent ON agent.no = login.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = userinfo.dept_no ";
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
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

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
	
	
	public List<LoginLogModel> getLoginlogList(HashMap<String, Object> map){
		List<LoginLogModel> data = new ArrayList<LoginLogModel>();
		
		String whereSql = "WHERE userinfo.valid=1 ";

		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();

		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();


		
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
			
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";		
		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND login.login_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND login.login_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND userinfo.permit = 'P' ";
		
		whereSql += "ORDER BY login_client_time desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "login.no, "
+ "userinfo.no AS uid,"
+ "userinfo.dept_no,"
+ "userinfo.duty,"
+ "userinfo.rank,"
+ "userinfo.name, "
+ "userinfo.number, "
+ "userinfo.phone,"
+ "userinfo.id,"
+ "userinfo.valid,"
+ "ifnull(login.login_server_time, '') AS login_server_time,"
+ "ifnull(login.login_client_time, '') AS login_client_time,"
+ "dept.short_name AS dept_name, "
+ "ifnull(agent.pc_name, '') AS pc_name,"
+ "ifnull(agent.ip_addr,'') AS ip_addr, "
+ "ifnull(agent.mac_addr,'') AS mac_addr "
+ "FROM login_log AS login "
+ "INNER JOIN user_info AS userinfo ON login.user_no = userinfo.no "
+ "INNER JOIN agent_log AS agent ON agent.no = login.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = userinfo.dept_no ";
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
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			
			
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				LoginLogModel model = new LoginLogModel();
				model.setLogin_no(rs.getInt("no"));
				model.setUid(rs.getInt("uid"));
				model.setDeptNo(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setNumber(rs.getString("number"));
				
				model.setName(rs.getString("name"));
				model.setPhone(rs.getString("phone"));
				model.setId(rs.getString("id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setLogin_servertime(rs.getString("login_server_time"));
				model.setLogin_clienttime(rs.getString("login_client_time"));
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
}
