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
import gcom.Model.CDExportLogModel;
import gcom.Model.NetExportLogModel;
import gcom.Model.NetPortLogModel;
import gcom.Model.UserAgentModel;
import gcom.Model.UserInfoModel;
import gcom.Model.UserPolicyModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.encrypto.hashEncrypto;


public class NetworkManageDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public NetworkManageDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getNetPortLogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();

		String port = map.get("port").toString();
		String process_name = map.get("process_name").toString();
		String description = map.get("description").toString();
		String control = map.get("control").toString();

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
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";

		if(!port.equals("")) 	whereSql += "AND port.port LIKE ? ";
		if(!process_name.equals("")) 	whereSql += "AND port.process_name LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND port.description LIKE ? ";
		if(!control.equals("")) 	whereSql += "AND port.control LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND port.control_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND port.control_server_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		String sql= 
"SELECT 	"
+ "COUNT(*) AS cnt "
+ "FROM net_port_log AS port "
+ "INNER JOIN user_info AS ur ON ur.no = port.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = port.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no  ";

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
			if(!user_number.equals("")) pstmt.setString(i++, "%" + user_number + "%");

			if(!port.equals("")) 	pstmt.setString(i++, "%" + port + "%");
			if(!process_name.equals("")) 	pstmt.setString(i++, "%" + process_name + "%");
			if(!description.equals("")) 	pstmt.setString(i++, "%" + description + "%");
			if(!control.equals("")) 	pstmt.setString(i++, "%" + control + "%");
			
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


	public List<NetPortLogModel> getNetPortLogList(HashMap<String, Object> map){
		List<NetPortLogModel> data = new ArrayList<NetPortLogModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();

		String port = map.get("port").toString();
		String process_name = map.get("process_name").toString();
		String description = map.get("description").toString();
		String control = map.get("control").toString();

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
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";

		if(!port.equals("")) 	whereSql += "AND port.port LIKE ? ";
		if(!process_name.equals("")) 	whereSql += "AND port.process_name LIKE ? ";
		if(!description.equals("")) 	whereSql += "AND port.description LIKE ? ";
		if(!control.equals("")) 	whereSql += "AND port.control LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND port.control_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND port.control_server_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY port.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT 	"
+ "port.no AS log_no,    "
+ "ifnull(port.process_name, '') AS process_name,    "
+ "ifnull(port.port, '') AS port ,   "
+ "ifnull(port.description, '')  AS description, "
+ "ifnull(port.control, '')  AS control, "
+ "ifnull(port.control_server_time, '') AS control_server_time, "
+ "ifnull(port.control_client_time, '') AS control_client_time,	"
+ "ur.id AS user_id, 	"
+ "ur.number AS user_no, 	"
+ "ur.name AS user_name, 	"
+ "ur.dept_no,	"
+ "ur.name,  "
+ "ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, 	"
+ "agent.mac_addr,	"
+ "agent.pc_name, 	"
+ "dept.name AS dept_name "
+ "FROM net_port_log AS port "
+ "INNER JOIN user_info AS ur ON ur.no = port.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = port.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no  ";

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
			if(!user_number.equals("")) pstmt.setString(i++, "%" + user_number + "%");

			if(!port.equals("")) 	pstmt.setString(i++, "%" + port + "%");
			if(!process_name.equals("")) 	pstmt.setString(i++, "%" + process_name + "%");
			if(!description.equals("")) 	pstmt.setString(i++, "%" + description + "%");
			if(!control.equals("")) 	pstmt.setString(i++, "%" + control + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				NetPortLogModel model = new NetPortLogModel();
				model.setLogNo(rs.getInt("log_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("user_name"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setUserNumber(rs.getString("user_no"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setProcessName(rs.getString("process_name"));
				model.setPort(rs.getString("port"));
				model.setDescription(rs.getString("description"));
				model.setControl(rs.getString("control"));
				model.setServerTime(rs.getString("control_server_time"));
				model.setClientTime(rs.getString("control_client_time"));
				
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

	public int getNetExportLogListCount(HashMap<String, Object> map){
		
		int result = 0;
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
	
		String dst_addr = map.get("dst_addr").toString();
		String file_key = map.get("file_key").toString();
		String process_name = map.get("process_name").toString();
		String file_name = map.get("file_name").toString();
		String notice = map.get("notice").toString();
		
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
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";

		if(!dst_addr.equals("")) 	whereSql += "AND exp.dest_addr LIKE ? ";
		if(!file_key.equals("")) 	whereSql += "AND exp.file_key LIKE ? ";
		if(!process_name.equals("")) 	whereSql += "AND exp.process_name LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND exp.file_name LIKE ? ";
		if(!notice.equals("")) 	whereSql += "AND exp.notice LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND exp.export_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND exp.export_server_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		String sql= 
"SELECT	"
+ "COUNT(*) AS cnt "
+ "FROM net_export_log AS exp "
+ "INNER JOIN user_info AS ur ON ur.no = exp.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = exp.agent_log_no "
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
			if(!user_number.equals("")) pstmt.setString(i++, "%" + user_number + "%");

			if(!dst_addr.equals("")) 	pstmt.setString(i++, "%" + dst_addr + "%");
			if(!file_key.equals("")) 	pstmt.setString(i++, "%" + file_key + "%");
			if(!process_name.equals("")) 	pstmt.setString(i++, "%" + process_name + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			if(!notice.equals("")) 		pstmt.setString(i++, "%" + notice + "%");

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
	
	public List<NetExportLogModel> getNetExportLogList(HashMap<String, Object> map){
		List<NetExportLogModel> data = new ArrayList<NetExportLogModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
	
		String dst_addr = map.get("dst_addr").toString();
		String file_key = map.get("file_key").toString();
		String process_name = map.get("process_name").toString();
		String file_name = map.get("file_name").toString();
		String notice = map.get("notice").toString();
		
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
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";

		if(!dst_addr.equals("")) 	whereSql += "AND exp.dest_addr LIKE ? ";
		if(!file_key.equals("")) 	whereSql += "AND exp.file_key LIKE ? ";
		if(!process_name.equals("")) 	whereSql += "AND exp.process_name LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND exp.file_name LIKE ? ";
		if(!notice.equals("")) 	whereSql += "AND exp.notice LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND exp.export_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND exp.export_server_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY exp.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT	"
+ "exp.no AS export_no, "
+ "exp.process_name, "
+ "ifnull(exp.protocol_type,'') AS protocol_type, "
+ "ifnull(exp.dest_addr,'') AS dest_addr, "
+ "ifnull(exp.file_name,'') AS file_name, "
+ "ifnull(exp.file_key,'') AS file_key, "
+ "ifnull(exp.export_server_time,'') AS export_server_time, "
+ "ifnull(exp.export_client_time,'') AS export_client_time, "
+ "ifnull(exp.notice,'') AS notice, "
+ "exp.completed, "
+ "ur.id AS user_id, "
+ "ur.dept_no, "
+ "ur.name AS user_name, "
+ "ur.duty,	"
+ "ur.rank,	"
+ "ur.number AS user_no, "
+ "agent.ip_addr,	"
+ "agent.mac_addr,	"
+ "agent.pc_name, 	"
+ "dept.name AS dept_name "
+ "FROM net_export_log AS exp "
+ "INNER JOIN user_info AS ur ON ur.no = exp.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = exp.agent_log_no "
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
			if(!user_number.equals("")) pstmt.setString(i++, "%" + user_number + "%");

			if(!dst_addr.equals("")) 	pstmt.setString(i++, "%" + dst_addr + "%");
			if(!file_key.equals("")) 	pstmt.setString(i++, "%" + file_key + "%");
			if(!process_name.equals("")) 	pstmt.setString(i++, "%" + process_name + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			if(!notice.equals("")) 		pstmt.setString(i++, "%" + notice + "%");

			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				NetExportLogModel model = new NetExportLogModel();
				model.setExportNo(rs.getInt("export_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setUserName(rs.getString("user_name"));
				model.setUserNumber(rs.getString("user_no"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setProcessName(rs.getString("process_name"));
				model.setProtocolType(rs.getString("protocol_type"));
				model.setDestAddr(rs.getString("dest_addr"));
				model.setFileName(rs.getString("file_name"));
				model.setFileKey(rs.getString("file_key"));

				model.setNotice(rs.getString("notice"));
				model.setExportServerTime(rs.getString("export_server_time"));
				model.setExportClientTime(rs.getString("export_client_time"));
				model.setCompleted(rs.getString("completed"));
				
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
