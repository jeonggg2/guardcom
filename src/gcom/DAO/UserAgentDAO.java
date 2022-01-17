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
import gcom.Model.UserAgentModel;
import gcom.Model.UserInfoModel;
import gcom.Model.UserPolicyModel;


public class UserAgentDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public UserAgentDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getUserAgentListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
//		int user_connected = Integer.parseInt(map.get("user_connected").toString());
		String user_number = map.get("user_number").toString();
/*		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();
*/		int user_installed = Integer.parseInt(map.get("user_installed").toString());

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
			
		
		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(user_installed == 1) 	whereSql += "AND agent.ip_addr is not null ";	//��ġ ����
		else if(user_installed == 2) 	whereSql += "AND agent.ip_addr is null ";	//�̼�ġ ����

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
/*		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";

		if(user_connected == 2) 	whereSql += "AND agent.connect_server_time < now() - interval 30 minute ";	//���� ����
		else if(user_connected == 1) 	whereSql += "AND agent.connect_server_time >= now() -  interval 30 minute ";	//������ ����
*/
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt " 
+ "FROM user_info AS userinfo "
+ "LEFT JOIN agent_info AS agent ON agent.own_user_no=userinfo.no "
+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");


			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
/*			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");

*/			if(oDept != null){
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
	
	
	public List<UserAgentModel> getUserAgentList(HashMap<String, Object> map){
		List<UserAgentModel> data = new ArrayList<UserAgentModel>();
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
//		int user_connected = Integer.parseInt(map.get("user_connected").toString());
		String user_number = map.get("user_number").toString();
//		String user_pc = map.get("user_pc").toString();
//		String user_ip = map.get("user_ip").toString();
		int user_installed = Integer.parseInt(map.get("user_installed").toString());

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

		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(user_installed == 1) 	whereSql += "AND agent.ip_addr is not null ";	//��ġ ����
		else if(user_installed == 2) 	whereSql += "AND agent.ip_addr is null ";	//�̼�ġ ����

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
/*		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";

		if(user_connected == 2) 	whereSql += "AND agent.connect_server_time < now() - interval 30 minute ";	//���� ����
		else if(user_connected == 1) 	whereSql += "AND agent.connect_server_time >= now() -  interval 30 minute ";	//������ ����
*/
		
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		whereSql += "ORDER BY userinfo.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "userinfo.no AS uid, "
+ "userinfo.dept_no,"
+ "userinfo.duty,"
+ "userinfo.rank,"
+ "userinfo.name, "
+ "userinfo.phone, "
+ "userinfo.id,"
+ "userinfo.valid,"
+ "userinfo.number, "
+ "dept.short_name AS dept_name,"
+ "ifnull(agent.pc_name, '') AS pc_name,"
+ "ifnull(agent.ip_addr,'') AS ip_addr, "
+ "ifnull(agent.mac_addr,'') AS mac_addr, "
+ "ifnull(agent.connect_server_time,'') AS connect_server_time, "
+ "ifnull(agent.install_server_time,'') AS install_server_time, "
+ "ifnull(agent.connect_server_time,'') AS connect_client_time, "
+ "ifnull(agent.install_server_time,'') AS install_client_time, "
+ "ifnull(agent.version, '') AS version, "
+ "if(agent.connect_server_time >= now() - interval 30 minute, 1, 0 ) AS isConnected "
+ "FROM user_info AS userinfo "
+ "LEFT JOIN agent_info AS agent ON agent.own_user_no=userinfo.no "
+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");


			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
/*			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
*/
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserAgentModel model = new UserAgentModel();
				model.setUid(rs.getInt("uid"));
				model.setDeptNo(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setName(rs.getString("name"));
				model.setNumber(rs.getString("number"));				
				model.setPhone(rs.getString("phone"));
				model.setId(rs.getString("id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setValid(rs.getInt("valid"));
				model.setVersion(rs.getString("version"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setConnect_server_time(rs.getString("connect_server_time"));
				model.setConnect_client_time(rs.getString("connect_client_time"));
				model.setInstall_server_time(rs.getString("install_server_time"));
				model.setInstall_client_time(rs.getString("install_client_time"));

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
	
	public int getAgentListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		int user_connected = Integer.parseInt(map.get("user_connected").toString());
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();
		int user_installed = Integer.parseInt(map.get("user_installed").toString());

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
			
		
		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(user_installed == 1) 	whereSql += "AND agent.ip_addr is not null ";	//��ġ ����
		else if(user_installed == 2) 	whereSql += "AND agent.ip_addr is null ";	//�̼�ġ ����

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";

		if(user_connected == 2) 	whereSql += "AND agent.connect_server_time < now() - interval 30 minute ";	//���� ����
		else if(user_connected == 1) 	whereSql += "AND agent.connect_server_time >= now() -  interval 30 minute ";	//������ ����

		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt " 
+ "FROM agent_info AS agent "
+ "LEFT JOIN user_info AS userinfo ON agent.own_user_no=userinfo.no "
+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");


			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");

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
	
	
	public List<UserAgentModel> getAgentList(HashMap<String, Object> map){
		List<UserAgentModel> data = new ArrayList<UserAgentModel>();
		
		String whereSql = "WHERE userinfo.valid=1 ";
		
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		int user_connected = Integer.parseInt(map.get("user_connected").toString());
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();
		int user_installed = Integer.parseInt(map.get("user_installed").toString());

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

		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(user_installed == 1) 	whereSql += "AND agent.ip_addr is not null ";	//��ġ ����
		else if(user_installed == 2) 	whereSql += "AND agent.ip_addr is null ";	//�̼�ġ ����

		if(!user_duty.equals("")) 	whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) 	whereSql += "AND userinfo.rank LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND userinfo.number LIKE ? ";
		if(!user_pc.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";

		if(user_connected == 2) 	whereSql += "AND agent.connect_server_time < now() - interval 30 minute ";	//���� ����
		else if(user_connected == 1) 	whereSql += "AND agent.connect_server_time >= now() -  interval 30 minute ";	//������ ����

		
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
				
		whereSql += "ORDER BY userinfo.no desc LIMIT ?, ? ";	
		
		String sql= 
					"SELECT "
					+ "userinfo.no AS uid, "
					+ "userinfo.dept_no,"
					+ "userinfo.duty,"
					+ "userinfo.rank,"
					+ "userinfo.name, "
					+ "userinfo.phone, "
					+ "userinfo.id,"
					+ "userinfo.valid,"
					+ "userinfo.number, "
					+ "dept.short_name AS dept_name,"
					+ "ifnull(agent.pc_name, '') AS pc_name,"
					+ "ifnull(agent.ip_addr,'') AS ip_addr, "
					+ "ifnull(agent.mac_addr,'') AS mac_addr, "
					+ "ifnull(agent.connect_server_time,'') AS connect_server_time, "
					+ "ifnull(agent.install_server_time,'') AS install_server_time, "
					+ "ifnull(agent.connect_server_time,'') AS connect_client_time, "
					+ "ifnull(agent.install_server_time,'') AS install_client_time, "
					+ "ifnull(agent.version, '') AS version, "
					+ "if(agent.connect_server_time >= now() - interval 30 minute, 1, 0 ) AS isConnected "
					+ "FROM agent_info AS agent "
					+ "LEFT JOIN user_info AS userinfo ON agent.own_user_no=userinfo.no "
					+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
					sql += whereSql;	
			
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");


			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");;
			if(!user_pc.equals("")) 	pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");

			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserAgentModel model = new UserAgentModel();
				model.setUid(rs.getInt("uid"));
				model.setDeptNo(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setName(rs.getString("name"));
				model.setNumber(rs.getString("number"));				
				model.setPhone(rs.getString("phone"));
				model.setId(rs.getString("id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setValid(rs.getInt("valid"));
				model.setVersion(rs.getString("version"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setConnect_server_time(rs.getString("connect_server_time"));
				model.setConnect_client_time(rs.getString("connect_client_time"));
				model.setInstall_server_time(rs.getString("install_server_time"));
				model.setInstall_client_time(rs.getString("install_client_time"));

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
	
	public List<UserAgentModel> lookupAgentListByPolicyNo(HashMap<String, Object> map){
		return lookupAgentListByPolicyNo(Integer.parseInt(map.get("policy_no").toString()));
	}
	
	public List<UserAgentModel> lookupAgentListByPolicyNo(int policyNo){
		
		List<UserAgentModel> data = new ArrayList<UserAgentModel>();
		
		String whereSql = "WHERE userinfo.valid=1 AND agent.policy_no = ? ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
				
		whereSql += "ORDER BY userinfo.no desc";	
		
		String sql= 
					"SELECT "
					+ "userinfo.no AS uid, "
					+ "userinfo.dept_no,"
					+ "userinfo.duty,"
					+ "userinfo.rank,"
					+ "userinfo.name, "
					+ "userinfo.phone, "
					+ "userinfo.id,"
					+ "userinfo.valid,"
					+ "userinfo.number, "
					+ "dept.short_name AS dept_name,"
					+ "ifnull(agent.pc_name, '') AS pc_name,"
					+ "ifnull(agent.ip_addr,'') AS ip_addr, "
					+ "ifnull(agent.mac_addr,'') AS mac_addr, "
					+ "ifnull(agent.connect_server_time,'') AS connect_server_time, "
					+ "ifnull(agent.install_server_time,'') AS install_server_time, "
					+ "ifnull(agent.connect_server_time,'') AS connect_client_time, "
					+ "ifnull(agent.install_server_time,'') AS install_client_time, "
					+ "ifnull(agent.version, '') AS version, "
					+ "if(agent.connect_server_time >= now() - interval 30 minute, 1, 0 ) AS isConnected "
					+ "FROM agent_info AS agent "
					+ "LEFT JOIN user_info AS userinfo ON agent.own_user_no=userinfo.no "
					+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
					sql += whereSql;	
			
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, policyNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserAgentModel model = new UserAgentModel();
				model.setUid(rs.getInt("uid"));
				model.setDeptNo(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setName(rs.getString("name"));
				model.setNumber(rs.getString("number"));				
				model.setPhone(rs.getString("phone"));
				model.setId(rs.getString("id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setValid(rs.getInt("valid"));
				model.setVersion(rs.getString("version"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setConnect_server_time(rs.getString("connect_server_time"));
				model.setConnect_client_time(rs.getString("connect_client_time"));
				model.setInstall_server_time(rs.getString("install_server_time"));
				model.setInstall_client_time(rs.getString("install_client_time"));
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
	
	public List<UserAgentModel> lookupAgentListByUserNo(HashMap<String, Object> map){
		return lookupAgentListByUserNo(Integer.parseInt(map.get("user_no").toString()));
	}
	
	public List<UserAgentModel> lookupAgentListByUserNo(int userNo){
		
		List<UserAgentModel> data = new ArrayList<UserAgentModel>();
		
		String whereSql = "WHERE userinfo.valid=1 AND userinfo.no = ? ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		whereSql += "ORDER BY userinfo.no desc";	
		
		String sql= 
					"SELECT "
					+ "userinfo.no AS uid, "
					+ "userinfo.dept_no,"
					+ "userinfo.duty,"
					+ "userinfo.rank,"
					+ "userinfo.name, "
					+ "userinfo.phone, "
					+ "userinfo.id,"
					+ "userinfo.valid,"
					+ "userinfo.number, "
					+ "dept.short_name AS dept_name,"
					+ "ifnull(agent.pc_name, '') AS pc_name,"
					+ "ifnull(agent.ip_addr,'') AS ip_addr, "
					+ "ifnull(agent.mac_addr,'') AS mac_addr, "
					+ "ifnull(agent.connect_server_time,'') AS connect_server_time, "
					+ "ifnull(agent.install_server_time,'') AS install_server_time, "
					+ "ifnull(agent.connect_server_time,'') AS connect_client_time, "
					+ "ifnull(agent.install_server_time,'') AS install_client_time, "
					+ "ifnull(agent.version, '') AS version, "
					+ "if(agent.connect_server_time >= now() - interval 30 minute, 1, 0 ) AS isConnected "
					+ "FROM agent_info AS agent "
					+ "LEFT JOIN user_info AS userinfo ON agent.own_user_no=userinfo.no "
					+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
					sql += whereSql;	
			
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserAgentModel model = new UserAgentModel();
				model.setUid(rs.getInt("uid"));
				model.setDeptNo(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setName(rs.getString("name"));
				model.setNumber(rs.getString("number"));				
				model.setPhone(rs.getString("phone"));
				model.setId(rs.getString("id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setValid(rs.getInt("valid"));
				model.setVersion(rs.getString("version"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setConnect_server_time(rs.getString("connect_server_time"));
				model.setConnect_client_time(rs.getString("connect_client_time"));
				model.setInstall_server_time(rs.getString("install_server_time"));
				model.setInstall_client_time(rs.getString("install_client_time"));
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
	
	public int getUserPolicyListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
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
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(oDept != null)			whereSql += "AND ur.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) cnt "
+ "FROM policy_info AS policy "
+ "INNER JOIN agent_info AS agent ON agent.no = policy.agent_no "
+ "INNER JOIN user_info AS ur ON ur.no = agent.own_user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
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
	
	
	public List<UserPolicyModel> getUserPolicyList(HashMap<String, Object> map){
		List<UserPolicyModel> data = new ArrayList<UserPolicyModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
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
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(oDept != null)			whereSql += "AND ur.dept_no in ("+idList+") ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY policy.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "policy.no AS policy_no,"
+ "IFNULL(policy.update_server_time, '') AS print_server_time, "
+ "IFNULL(policy.update_client_time, '') AS print_client_time, "
+ "policy.uninstall_enabled AS uninstall_enabled, "
+ "policy.file_encryption_enabled AS file_encryption_enabled, "
+ "policy.cd_encryption_enabled,"
+ "policy.printer_enabled,"
+ "policy.cd_enabled,"
+ "policy.cd_export_enabled,"
+ "policy.wlan_enabled,"
+ "policy.net_share_enabled,"
+ "policy.web_export_enabled,"
+ "policy.removal_storage_export_enabled,"
+ "policy.removal_storage_admin_mode,"
+ "policy.usb_dev_list,"
+ "policy.com_port_list,"
+ "policy.net_port_list,"
+ "policy.process_list,"
+ "policy.file_pattern_list,"
+ "policy.web_addr_list,"
+ "policy.watermark_descriptor,"
+ "policy.print_log_descriptor,"
+ "policy.quarantine_path_access_code,"
+ "policy.pattern_file_control,"
+ "ur.number AS user_no, "
+ "ur.id AS user_id, "
+ "ur.name AS user_name, "
+ "ur.dept_no, "
+ "ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM policy_info AS policy "
+ "INNER JOIN agent_info AS agent ON agent.no = policy.agent_no "
+ "INNER JOIN user_info AS ur ON ur.no = agent.own_user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";


sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserPolicyModel model = new UserPolicyModel();
				model.setPolicyNo(rs.getInt("policy_no"));
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
				model.setIsUninstall(rs.getInt("uninstall_enabled"));
				model.setIsFileEncryption(rs.getInt("file_encryption_enabled"));
				model.setIsCdEncryption(rs.getInt("cd_encryption_enabled"));
				model.setIsPrint(rs.getInt("printer_enabled"));
				model.setIsCdEnabled(rs.getInt("cd_enabled"));
				model.setIsCdExport(rs.getInt("cd_export_enabled"));
				model.setIsWlan(rs.getInt("wlan_enabled"));
				model.setIsNetShare(rs.getInt("net_share_enabled"));
				model.setIsWebExport(rs.getInt("web_export_enabled"));
				model.setIsStorageExport(rs.getInt("removal_storage_export_enabled"));
				model.setIsStorageAdmin(rs.getInt("removal_storage_admin_mode"));
				model.setIsUsbBlock(rs.getString("usb_dev_list"));
				model.setIsComPortBlock(rs.getString("com_port_list"));
				model.setIsNetPortBlock(rs.getString("net_port_list"));
				model.setIsProcessList(rs.getString("process_list"));
				model.setIsFilePattern(rs.getString("file_pattern_list"));
				model.setIsWebAddr(rs.getString("web_addr_list"));
				model.setIsWatermark(rs.getString("watermark_descriptor"));
				model.setPrintLogDesc(rs.getInt("print_log_descriptor"));
				model.setPatternFileControl(rs.getInt("pattern_file_control"));
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
	

	public int getUserInfoListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		
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
			
		
		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND userinfo.rank LIKE ? ";
		
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt " 
+ "FROM user_info AS userinfo "
+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

			if(!user_duty.equals("")) pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) pstmt.setString(i++, "%" + user_rank + "%");
			
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
	
	
	public List<UserInfoModel> getUserInfoList(HashMap<String, Object> map){
		List<UserInfoModel> data = new ArrayList<UserInfoModel>();
		
		String whereSql = "WHERE userinfo.valid=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		
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

		if(!user_id.equals("")) 	whereSql += "AND userinfo.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND userinfo.name LIKE ? ";
		if(!user_phone.equals("")) 	whereSql += "AND userinfo.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND userinfo.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND userinfo.rank LIKE ? ";
		
		if(oDept != null)			whereSql += "AND userinfo.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND userinfo.permit = 'P' ";
		
		whereSql += "ORDER BY userinfo.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "userinfo.no,"
+ "userinfo.number AS user_no, "
+ "userinfo.dept_no,"
+ "userinfo.duty,"
+ "userinfo.rank,"
+ "userinfo.name, "
+ "userinfo.phone, "
+ "userinfo.id,"
+ "userinfo.valid,"
+ "dept.short_name AS dept_name "
+ "FROM user_info AS userinfo "
+ "INNER JOIN dept_info AS dept ON userinfo.dept_no = dept.no ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!user_id.equals("")) pstmt.setString(i++, "%" + user_id + "%");
			if(!user_name.equals("")) pstmt.setString(i++, "%" + user_name + "%");
			if(!user_phone.equals("")) pstmt.setString(i++,  "%" + user_phone + "%");

			if(!user_duty.equals("")) pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_rank.equals("")) pstmt.setString(i++, "%" + user_rank + "%");
			
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserInfoModel model = new UserInfoModel();
				model.setUno(rs.getInt("no"));
				model.setUserNo(rs.getString("user_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setUserName(rs.getString("name"));
				model.setPhone(rs.getString("phone"));
				model.setUserId(rs.getString("id"));
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
	
	public UserInfoModel getUserInfo(HashMap<String, Object> map){
		UserInfoModel data = new UserInfoModel();
		int user_no = Integer.parseInt(map.get("user_no").toString());
		
		String whereSql = "WHERE no = ? ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND permit = 'P' ";
	
		String sql= 
"SELECT "
+ "no, "
+ "dept_no, "
+ "duty, "
+ "rank, "
+ "number, "
+ "name, "
+ "phone, "
+ "secure_level, "
+ "id FROM "
+ "user_info ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			pstmt.setInt(i++, user_no);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				data.setUserNo(rs.getString("no"));				
				data.setDeptId(rs.getInt("dept_no"));				
				data.setDuty(rs.getString("duty"));
				data.setRank(rs.getString("rank"));
				data.setNumber(rs.getString("number"));
				data.setUserName(rs.getString("name"));
				data.setPhone(rs.getString("phone"));
				data.setUserId(rs.getString("id"));
				data.setSecureLevel(rs.getInt("secure_level"));
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
