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
import gcom.Model.PrintFileModel;;


public class DeviceDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public DeviceDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getPrintLogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_rank = map.get("user_rank").toString();
		String user_duty = map.get("user_duty").toString();
		String user_number = map.get("user_number").toString();
		String pc_name = map.get("pc_name").toString();
		String file_name = map.get("file_name").toString();
		int water_mark = Integer.parseInt(map.get("water_mark").toString());
		
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

		if(!user_rank.equals("")) 	whereSql += "AND ur.rank LIKE ? ";
		if(!user_duty.equals("")) 	whereSql += "AND ur.duty LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND print.file_name LIKE ? ";
		if(water_mark != -1 ) 	whereSql += "AND print.watermark = ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND print.print_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND print.print_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM print_log AS print "
+ "INNER JOIN user_info AS ur ON ur.no = print.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = print.agent_log_no "
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
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			if(water_mark != -1 ) 	pstmt.setInt(i++, water_mark);

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
	
	public List<PrintFileModel> getPrintLogList(HashMap<String, Object> map){
		List<PrintFileModel> data = new ArrayList<PrintFileModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_rank = map.get("user_rank").toString();
		String user_duty = map.get("user_duty").toString();
		String user_number = map.get("user_number").toString();
		String pc_name = map.get("pc_name").toString();
		String file_name = map.get("file_name").toString();
		int water_mark = Integer.parseInt(map.get("water_mark").toString());
		
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

		if(!user_rank.equals("")) 	whereSql += "AND ur.rank LIKE ? ";
		if(!user_duty.equals("")) 	whereSql += "AND ur.duty LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND print.file_name LIKE ? ";
		if(water_mark != -1 ) 	whereSql += "AND print.watermark = ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND print.print_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND print.print_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY print.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "print.no AS print_no, "
+ "IFNULL(print.print_server_time, '') AS print_server_time, "
+ "IFNULL(print.print_client_time, '') AS print_client_time, "
+ "print.file_name AS file_name, print.watermark, "
+ "print.page_count, "
+ "print.print_copies, "
+ "print.file_list, "
+ "ifnull(print.file_id, '') AS file_id, "
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
+ "FROM print_log AS print "
+ "INNER JOIN user_info AS ur ON ur.no = print.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = print.agent_log_no "
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
		
			if(!user_rank.equals("")) 	pstmt.setString(i++, "%" + user_rank + "%");
			if(!user_duty.equals("")) 	pstmt.setString(i++, "%" + user_duty + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			if(water_mark != -1 ) 	pstmt.setInt(i++, water_mark);


			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PrintFileModel model = new PrintFileModel();
				model.setPrintNo(rs.getInt("print_no"));
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
				model.setPrintServerTime(rs.getString("print_server_time"));
				model.setPrintClientTime(rs.getString("print_client_time"));
				model.setFileName(rs.getString("file_name"));
				model.setWatermark(rs.getInt("watermark") == 0 ? false : true);			
				model.setPageCount(rs.getInt("page_count"));
				model.setPrintCopies(rs.getInt("print_copies"));
				model.setFileId(rs.getString("file_id"));
				model.setFileList(rs.getString("file_list"));
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
