package gcom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;
import gcom.Model.DeptModel;
import gcom.Model.FileInfoModel;
import gcom.Model.MailExportContentModel;
import gcom.Model.MailExportModel;
import gcom.Model.MsnFileModel;
import gcom.Model.MsnTalkModel;
import gcom.Model.PrivacyLogModel;
import gcom.common.util.CommonUtil;
import gcom.common.util.ConfigInfo;
import gcom.service.common.CommonServiceImpl;
import gcom.service.common.ICommonService;
import gcom.user.model.UserContactModel;


public class PersonalDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public PersonalDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getMailExportListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String src_addr = map.get("src_addr").toString();
		String dst_addr = map.get("dst_addr").toString();
		String subject = map.get("subject").toString();
		String body = map.get("body").toString();
		String pc_name = map.get("pc_name").toString();
		String user_number = map.get("user_number").toString();
		
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
		if(!src_addr.equals("")) 	whereSql += "AND mail.src_addr LIKE ? ";
		if(!dst_addr.equals("")) 	whereSql += "AND mail.dst_addr LIKE ? ";
		if(!subject.equals("")) 	whereSql += "AND mail.subject LIKE ? ";
		if(!body.equals("")) 	whereSql += "AND mail.body LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND mail.send_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND mail.send_server_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
				
		String sql= 
"SELECT " 
+ "COUNT(*) AS cnt "  
+ "FROM mail_log AS mail "
+ "INNER JOIN user_info AS ur ON ur.no = mail.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = mail.agent_log_no "
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
			if(!src_addr.equals("")) 	pstmt.setString(i++, "%" + src_addr + "%");
			if(!dst_addr.equals("")) 	pstmt.setString(i++, "%" + dst_addr + "%");
			if(!subject.equals("")) 	pstmt.setString(i++, "%" + subject + "%");
			if(!body.equals("")) 	pstmt.setString(i++, "%" + body + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			
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
	
	
	public List<MailExportModel> getMailExportList(HashMap<String, Object> map){
		List<MailExportModel> data = new ArrayList<MailExportModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String src_addr = map.get("src_addr").toString();
		String dst_addr = map.get("dst_addr").toString();
		String subject = map.get("subject").toString();
		String body = map.get("body").toString();
		String pc_name = map.get("pc_name").toString();
		String user_number = map.get("user_number").toString();
		
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
		if(!src_addr.equals("")) 	whereSql += "AND mail.src_addr LIKE ? ";
		if(!dst_addr.equals("")) 	whereSql += "AND mail.dst_addr LIKE ? ";
		if(!subject.equals("")) 	whereSql += "AND mail.subject LIKE ? ";
		if(!body.equals("")) 	whereSql += "AND mail.body LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND mail.send_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND mail.send_server_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
				
		whereSql += "ORDER BY mail.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "mail.no AS mail_no, "
+ "ifnull(mail.send_server_time, '' ) AS send_server_time, "
+ "ifnull(mail.send_client_time, '' ) AS send_client_time, "
+ "mail.src_addr, "
+ "mail.dst_addr, "
+ "mail.subject, "
+ "mail.body, "
+ "mail.cc, "
+ "mail.bcc, "
+ "ifnull(mail.file_list, '') AS file_list, "
+ "ifnull(mail.file_id, '') AS file_id, "
+ "dept.name AS dept_name, "
+ "ur.number AS user_no,  "
+ "ur.id AS user_id,  "
+ "ur.name AS user_name,  "
+ "ur.dept_no, ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name "
+ "FROM mail_log AS mail "
+ "INNER JOIN user_info AS ur ON ur.no = mail.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = mail.agent_log_no "
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
			if(!src_addr.equals("")) 	pstmt.setString(i++, "%" + src_addr + "%");
			if(!dst_addr.equals("")) 	pstmt.setString(i++, "%" + dst_addr + "%");
			if(!subject.equals("")) 	pstmt.setString(i++, "%" + subject + "%");
			if(!body.equals("")) 	pstmt.setString(i++, "%" + body + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);


			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				MailExportModel model = new MailExportModel();
				model.setMailNo(rs.getInt("mail_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setUserName(rs.getString("user_name"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setSubject(rs.getString("subject"));
				model.setFileList(rs.getString("file_list"));
				model.setFileId(rs.getString("file_id"));
				model.setSrcAddr(rs.getString("src_addr"));
				model.setDstAddr(rs.getString("dst_addr"));
				model.setSendServerTime(rs.getString("send_client_time"));
				model.setSendClientTime(rs.getString("send_client_time"));

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
	
	
	public MailExportContentModel getMailExportContent(HashMap<String, Object> map){
		
		String sql= 
"SELECT "
+ "mail.send_server_time, "
+ "mail.send_client_time, "
+ "mail.src_addr, "
+ "mail.dst_addr, "
+ "mail.subject, "
+ "mail.body, "
+ "mail.cc, "
+ "mail.bcc, "
+ "mail.file_list "
+ "FROM mail_log AS mail WHERE no = ? ";

		MailExportContentModel model = new MailExportContentModel();

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			pstmt.setInt(1, Integer.parseInt(map.get("mail_no").toString()));
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				model.setSubject(rs.getString("subject"));
				model.setFileList(rs.getString("file_list"));
				model.setSrcAddr(rs.getString("src_addr"));
				model.setDstAddr(rs.getString("dst_addr"));
				model.setBody(rs.getString("body"));
				model.setCc(rs.getString("cc"));
				model.setBcc(rs.getString("bcc"));
				model.setSendServerTime(rs.getString("send_server_time"));
				model.setSendClientTime(rs.getString("send_client_time"));
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
		
		return model;
	}
	
	
	public int getMsnTalkListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";

		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_ip = map.get("user_ip").toString();
		String pc_name = map.get("pc_name").toString();
		String msg_txt = map.get("msg_txt").toString();
		String msg_type = map.get("msg_type").toString();
		
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
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!msg_txt.equals("")) 	whereSql += "AND msg.msg_txt LIKE ? ";
		if(!msg_type.equals("")) 	whereSql += "AND msg.msg_type LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND msg.send_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND msg.send_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
				
		String sql= 
	"SELECT "
	+ "COUNT(*) AS cnt "
	+ "FROM msg_txt_log AS msg "
	+ "INNER JOIN user_info AS ur ON ur.no = msg.user_no "
	 + "INNER JOIN agent_log AS agent ON agent.no = msg.agent_log_no "
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
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!msg_txt.equals("")) 	pstmt.setString(i++, "%" + msg_txt + "%");
			if(!msg_type.equals("")) 	pstmt.setString(i++, "%" + msg_type + "%");
			
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
	
	
	public List<MsnTalkModel> getMsnTalkList(HashMap<String, Object> map){
		List<MsnTalkModel> data = new ArrayList<MsnTalkModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_ip = map.get("user_ip").toString();
		String pc_name = map.get("pc_name").toString();
		String msg_txt = map.get("msg_txt").toString();
		String msg_type = map.get("msg_type").toString();
		
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
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(!msg_txt.equals("")) 	whereSql += "AND msg.msg_txt LIKE ? ";
		if(!msg_type.equals("")) 	whereSql += "AND msg.msg_type LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND msg.send_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND msg.send_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY msg.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "msg.no AS msg_no,"
+ "msg.msg_type,"
+ "msg.msg_txt,"
+ "ifnull(msg.send_server_time, '') AS send_server_time, "
+ "ifnull(msg.send_client_time, '') AS send_client_time, "
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
+ "FROM msg_txt_log AS msg "
+ "INNER JOIN user_info AS ur ON ur.no = msg.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = msg.agent_log_no "
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
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!msg_txt.equals("")) 	pstmt.setString(i++, "%" + msg_txt + "%");
			if(!msg_type.equals("")) 	pstmt.setString(i++, "%" + msg_type + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);


			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				MsnTalkModel model = new MsnTalkModel();
				model.setMsgNo(rs.getInt("msg_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setUserName(rs.getString("user_name"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));

				model.setMsgText(rs.getString("msg_txt"));
				model.setMsgType(rs.getString("msg_type"));
				model.setSendServerTime(rs.getString("send_client_time"));
				model.setSendClientTime(rs.getString("send_server_time"));

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
	
	public int getMsnFileListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_ip = map.get("user_ip").toString();
		String user_number = map.get("user_number").toString();
		String msg_file = map.get("msg_file").toString();
		String msg_type = map.get("msg_type").toString();
		
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
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!msg_file.equals("")) 	whereSql += "AND msg.file_list LIKE ? ";
		if(!msg_type.equals("")) 	whereSql += "AND msg.msg_type LIKE ? ";
		
		if(!start_date.equals("")) 	whereSql += "AND msg.send_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND msg.send_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM msg_file_log AS msg "
+ "INNER JOIN user_info AS ur ON ur.no = msg.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = msg.agent_log_no "
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
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!msg_file.equals("")) 	pstmt.setString(i++, "%" + msg_file + "%");
			if(!msg_type.equals("")) 	pstmt.setString(i++, "%" + msg_type + "%");
			
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
	
	
	public List<MsnFileModel> getMsnFileList(HashMap<String, Object> map){
		List<MsnFileModel> data = new ArrayList<MsnFileModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_ip = map.get("user_ip").toString();
		String user_number = map.get("user_number").toString();
		String msg_file = map.get("msg_file").toString();
		String msg_type = map.get("msg_type").toString();
		
	
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
		if(!user_ip.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!user_number.equals("")) 	whereSql += "AND ur.number LIKE ? ";
		if(!msg_file.equals("")) 	whereSql += "AND msg.file_list LIKE ? ";
		if(!msg_type.equals("")) 	whereSql += "AND msg.msg_type LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND msg.send_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND msg.send_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY msg.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "msg.no AS msg_no, "
+ "msg.msg_type AS msg_type, "
+ "ifnull(msg.file_list, '') AS file_list, "
+ "ifnull(msg.file_id, '') AS file_id, "
+ "ifnull(msg.send_server_time, '') AS send_server_time, "
+ "ifnull(msg.send_client_time, '') AS send_client_time, "
+ "ur.number AS user_no,  "
+ "ur.id AS user_id,  "
+ "ur.name AS user_name,  "
+ "ur.dept_no,  "
+ "ur.duty,  "
+ "ur.rank,  "
+ "agent.ip_addr,  "
+ "agent.mac_addr,  "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM msg_file_log AS msg "
+ "INNER JOIN user_info AS ur ON ur.no = msg.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = msg.agent_log_no "
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
			if(!user_ip.equals("")) 	pstmt.setString(i++, "%" + user_ip + "%");
			if(!user_number.equals("")) 	pstmt.setString(i++, "%" + user_number + "%");
			if(!msg_file.equals("")) 	pstmt.setString(i++, "%" + msg_file + "%");
			if(!msg_type.equals("")) 	pstmt.setString(i++, "%" + msg_type + "%");

			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);


			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				MsnFileModel model = new MsnFileModel();
				model.setMsgNo(rs.getInt("msg_no"));
				model.setFileId(rs.getString("file_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setUserName(rs.getString("user_name"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setMsgType(rs.getString("msg_type"));
				model.setFileList(rs.getString("file_list"));
				model.setSendServerTime(rs.getString("send_client_time"));
				model.setSendClientTime(rs.getString("send_server_time"));

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
	
	
	
	public int getPrivacyFileListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String ip_addr = map.get("ip_addr").toString();
		String pc_name = map.get("pc_name").toString();
		int pattern = Integer.parseInt(map.get("pattern").toString());
		String matched_data = map.get("matched_data").toString();
		String file_name = map.get("file_name").toString();

		
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
		if(!ip_addr.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(pattern != -1) 	whereSql += "AND ptn.pattern_id = ? ";
		if(!matched_data.equals("")) 	whereSql += "AND ptn.matched_data LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND ptn.file_name LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND ptn.found_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND ptn.found_client_time < ? + interval 1 day ";
			
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
		"SELECT "
		+ "COUNT(*) AS cnt "
		+ "FROM pattern_log AS ptn "
		+ "INNER JOIN user_info AS ur ON ur.no = ptn.user_no "
		 + "INNER JOIN agent_log AS agent ON agent.no = ptn.agent_log_no "
		+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no "
		+ "INNER JOIN pattern_info AS ptn_info ON ptn.pattern_id = ptn_info.id  ";
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
			if(!ip_addr.equals("")) 	pstmt.setString(i++, "%" + ip_addr + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(pattern != -1) 	pstmt.setInt(i++, pattern );
			if(!matched_data.equals("")) 	pstmt.setString(i++, "%" + matched_data + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
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
	
	
	public List<PrivacyLogModel> getPrivacyFileList(HashMap<String, Object> map){
		List<PrivacyLogModel> data = new ArrayList<PrivacyLogModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();

		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String ip_addr = map.get("ip_addr").toString();
		String pc_name = map.get("pc_name").toString();
		int pattern = Integer.parseInt(map.get("pattern").toString());
		String matched_data = map.get("matched_data").toString();
		String file_name = map.get("file_name").toString();

		
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
		if(!ip_addr.equals("")) 	whereSql += "AND agent.ip_addr LIKE ? ";
		if(!pc_name.equals("")) 	whereSql += "AND agent.pc_name LIKE ? ";
		if(pattern != -1) 	whereSql += "AND ptn.pattern_id = ? ";
		if(!matched_data.equals("")) 	whereSql += "AND ptn.matched_data LIKE ? ";
		if(!file_name.equals("")) 	whereSql += "AND ptn.file_name LIKE ? ";

		
		if(!start_date.equals("")) 	whereSql += "AND ptn.found_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND ptn.found_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY ptn.no desc LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "ptn.no AS ptn_no, "
+ "ptn.process_name AS process_name, "
+ "ptn_info.description AS pattern_name, "
+ "ptn.file_name AS file_name, "
+ "ptn.file_size AS file_size,"
+ "ptn.matched_data AS matched_data, "
+ "ifnull(ptn.found_server_time, '') AS found_server_time, "
+ "ifnull(ptn.found_client_time, '') AS found_client_time, "
+ "ur.number AS user_no, "
+ "ur.id AS user_id, "
+ "ur.name AS user_name, "
+ "ur.dept_no,  "
+ "ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM pattern_log AS ptn "
+ "INNER JOIN user_info AS ur ON ur.no = ptn.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = ptn.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no "
+ "INNER JOIN pattern_info AS ptn_info ON ptn.pattern_id = ptn_info.id  ";
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
			if(!ip_addr.equals("")) 	pstmt.setString(i++, "%" + ip_addr + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(pattern != -1) 	pstmt.setInt(i++, pattern );
			if(!matched_data.equals("")) 	pstmt.setString(i++, "%" + matched_data + "%");
			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);


			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PrivacyLogModel model = new PrivacyLogModel();
				model.setPtnNo(rs.getInt("ptn_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("user_name"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPcName(rs.getString("pc_name"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));

				model.setProcessName(rs.getString("process_name"));				
				model.setFileName(rs.getString("file_name"));
				model.setPatternName(rs.getString("pattern_name"));
				model.setFileSize(rs.getString("file_size"));
				model.setMatchedData(rs.getString("matched_data"));
				model.setFoundServerTime(rs.getString("found_server_time"));
				model.setFoundClientTime(rs.getString("found_client_time"));

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
	

	public HashMap<String, Object> insertNoticeWriteSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String bbs_title = map.get("bbs_title").toString();
		String bbs_body = map.get("bbs_body").toString();
		String bbs_body_trim = map.get("bbs_body_trim").toString();
		String special_type = map.get("special_type").toString();
		String attfile_yn = map.get("attfile_yn").toString();
		int reg_staf_no = Integer.parseInt(map.get("reg_staf_no").toString());
		
		String save_file_nm =  map.get("save_file_nm").toString();
		String view_file_nm =  map.get("view_file_nm").toString();
		String att_file_path =  map.get("att_file_path").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		
		
		String sql= "INSERT INTO services_file_upload_info (att_file_path, view_file_nm, save_file_nm) "
					+ "VALUES (?, ?, ?) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			
			if (attfile_yn.equals("Y")) {
				pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, att_file_path);
				pstmt.setString(2, view_file_nm);
				pstmt.setString(3, save_file_nm);
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				
				if (rs.next()) {
					int fileKey = rs.getInt(1);
					
					sql= "INSERT INTO user_notice_bbs (bbs_title, special_type, bbs_body, bbs_body_trim, reg_staf_no, reg_dt, attfile_yn, attfile_id) " 
							+ "VALUES (?, ?, ?, ?, ?, NOW(), ?, ? )";
					
					pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, bbs_title);
					pstmt.setString(2, special_type);
					pstmt.setString(3, bbs_body);
					pstmt.setString(4, bbs_body_trim);
					pstmt.setInt(5, reg_staf_no);
					pstmt.setString(6, attfile_yn);
					pstmt.setInt(7, fileKey);
					pstmt.executeUpdate();
					
					rs = pstmt.getGeneratedKeys();
					
					if (rs.next()) {
						int bbsKey = rs.getInt(1);
						sql = "INSERT INTO user_notice_bbs_hit (bbs_id) VALUES (?)";
						
						pstmt=con.prepareStatement(sql);
						pstmt.setInt(1, bbsKey);
						pstmt.executeUpdate();
					}
					
				}
				
			} else {
				
				sql= "INSERT INTO user_notice_bbs (bbs_title, special_type, bbs_body, bbs_body_trim, reg_staf_no, reg_dt, attfile_yn) " 
						+ "VALUES (?, ?, ?, ?, ?, NOW(), ? )";
				
				pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, bbs_title);
				pstmt.setString(2, special_type);
				pstmt.setString(3, bbs_body);
				pstmt.setString(4, bbs_body_trim);
				pstmt.setInt(5, reg_staf_no);
				pstmt.setString(6, attfile_yn);
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				
				if (rs.next()) {
					int bbsKey = rs.getInt(1);
					sql = "INSERT INTO user_notice_bbs_hit (bbs_id) VALUES (?)";
					
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1, bbsKey);
					pstmt.executeUpdate();
				}
				
			}
									
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

	public FileInfoModel getAttFileInfo(HashMap<String, Object> map) {
		FileInfoModel model = new FileInfoModel();
		int fileId = Integer.parseInt(map.get("file_id").toString());
		
		String sql =
				"SELECT "
					+ "attfile_id, "
					+ "att_file_path, "
					+ "view_file_nm, "
					+ "save_file_nm "
				+ "FROM services_file_upload_info "
			    + "WHERE attfile_id = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  fileId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				model.setAttFileId(rs.getInt("attfile_id"));
				model.setAttFilePath(rs.getString("att_file_path"));
				model.setAttViewFileName(rs.getString("view_file_nm"));
				model.setAttSaveFileName(rs.getString("save_file_nm"));
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
		
		return model;
	}
	
	public boolean isGroupPolicy(int policyNo) {
		
		if (policyNo == 0) {
			return true;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql =
				"SELECT COUNT(no)"
				+ "FROM dept_info "
			    + "WHERE policy_no = ? ";
		
		try{
			
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  policyNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				return rs.getInt(1) > 0;
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
		
		return false;
	}
	
	public String insertPolicyDataSave(HashMap<String, Object> map) {
		
		int agent_id = Integer.parseInt(map.get("agent_no").toString());
		int user_id = Integer.parseInt(map.get("user_no").toString());
		//��å
		int isUninstall = Integer.parseInt(map.get("isUninstall").toString());
		int isFileEncryption = Integer.parseInt(map.get("isFileEncryption").toString());
		int isCdEncryption = Integer.parseInt(map.get("isCdEncryption").toString());
		int isPrint = Integer.parseInt(map.get("isPrint").toString());
		int isCdEnabled = Integer.parseInt(map.get("isCdEnabled").toString());
		int isCdExport = Integer.parseInt(map.get("isCdExport").toString());
		int isWlan = Integer.parseInt(map.get("isWlan").toString());
		int isNetShare = Integer.parseInt(map.get("isNetShare").toString());
		int isWebExport = Integer.parseInt(map.get("isWebExport").toString());
		
		int isSensitiveDirEnabled = Integer.parseInt(map.get("isSensitiveDirEnabled").toString());
		String quarantinePathAccessCode = "";
		
		if (isSensitiveDirEnabled == 1) {
			quarantinePathAccessCode = CommonUtil.createQuarantinePathAccessCode();
		}
		
		int isSensitiveFileAccess = Integer.parseInt(map.get("isSensitiveFileAccess").toString());
		int isStorageExport = Integer.parseInt(map.get("isStorageExport").toString());
		int isStorageAdmin = Integer.parseInt(map.get("isStorageAdmin").toString());
		int isUsbControlEnabled = Integer.parseInt(map.get("isUsbControlEnabled").toString());
		
		int patternFileControl = Integer.parseInt(map.get("patternFileControl").toString());
		String printLogDesc = map.get("printLogDesc").toString();
		String isUsbBlock = map.get("isUsbBlock").toString();
		String isComPortBlock = map.get("isComPortBlock").toString();
		String isNetPortBlock = map.get("isNetPortBlock").toString();
		String isProcessList = map.get("isProcessList").toString();
		String isFilePattern = map.get("isFilePattern").toString();
		String isWebAddr = map.get("isWebAddr").toString();
		String isMsgBlock = map.get("isMsgBlock").toString();
		String isWatermark = map.get("isWatermark").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO policy_info ("
					+ "agent_no, "
					+ "update_server_time, " 
					+ "uninstall_enabled, " 
					+ "file_encryption_enabled, " 
					+ "cd_encryption_enabled, " 
					+ "printer_enabled, " 
					+ "cd_enabled, " 
					+ "cd_export_enabled, " 
					+ "wlan_enabled, " 
					+ "net_share_enabled, " 
					+ "web_export_enabled, "
					
					+ "sensitive_dir_enabled, "
					+ "policy_sensitive_file_access, "
					+ "policy_usb_control_enabled, "
					+ "removal_storage_export_enabled, "
					+ "removal_storage_admin_mode, "
					
					+ "usb_dev_list, " 
					+ "com_port_list, " 
					+ "net_port_list, "
					+ "process_list, " 
					+ "file_pattern_list, " 
					+ "web_addr_list, " 
					+ "msg_block_list, " 
					+ "watermark_descriptor, " 
					+ "print_log_descriptor,"
					+ "quarantine_path_access_code, " 
					+ "pattern_file_control )" 
				+ "VALUES ( ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, agent_id);
			pstmt.setInt(2, isUninstall);
			pstmt.setInt(3, isFileEncryption);
			pstmt.setInt(4, isCdEncryption);
			pstmt.setInt(5, isPrint);
			pstmt.setInt(6, isCdEnabled);
			pstmt.setInt(7, isCdExport);
			pstmt.setInt(8, isWlan);
			pstmt.setInt(9, isNetShare);
			pstmt.setInt(10, isWebExport);
			
			pstmt.setInt(11, isSensitiveDirEnabled);
			pstmt.setInt(12, isSensitiveFileAccess);
			pstmt.setInt(13, isUsbControlEnabled);
			pstmt.setInt(14, isStorageExport);
			pstmt.setInt(15, isStorageAdmin);
			
			pstmt.setString(16, isUsbBlock);
			pstmt.setString(17, isComPortBlock);
			pstmt.setString(18, isNetPortBlock);
			pstmt.setString(19, isProcessList);
			pstmt.setString(20, isFilePattern);
			pstmt.setString(21, isWebAddr);
			pstmt.setString(22, isMsgBlock);
			pstmt.setString(23, isWatermark);
			pstmt.setString(24, printLogDesc);
			pstmt.setString(25, quarantinePathAccessCode);
			pstmt.setInt(26, patternFileControl);
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				
				int policy_id = rs.getInt(1);
				sql = "UPDATE agent_info SET policy_no = ? WHERE no = ? AND own_user_no = ? ";
				map.put("policy_no", policy_id);
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, policy_id);
				pstmt.setInt(2, agent_id);
				pstmt.setInt(3, user_id);
				pstmt.executeUpdate();
				
				con.commit();
				
				// ��å ���� �α�
				ICommonService commonService = new CommonServiceImpl();
				commonService.setPolicyUpdateToInsertLog(policy_id);
			}
		
		}catch(SQLException ex){
			returnCode = ConfigInfo.RETURN_CODE_ERROR;
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
		
		return returnCode;
	}

	public String updatePolicyDataSave(HashMap<String, Object> map) {
		
		int policy_id = Integer.parseInt(map.get("policy_no").toString());
		
		HashMap<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("isUninstall", "uninstall_enabled");
		fieldMap.put("isFileEncryption", "file_encryption_enabled");
		fieldMap.put("isCdEncryption", "cd_encryption_enabled");
		fieldMap.put("isPrint", "printer_enabled");
		fieldMap.put("isCdEnabled", "cd_enabled");
		fieldMap.put("isCdExport", "cd_export_enabled");
		fieldMap.put("isWlan", "wlan_enabled");
		fieldMap.put("isNetShare", "net_share_enabled");
		fieldMap.put("isWebExport", "web_export_enabled");
		fieldMap.put("isSensitiveFileAccess", "policy_sensitive_file_access");
		fieldMap.put("isStorageExport", "removal_storage_export_enabled");
		fieldMap.put("isStorageAdmin", "removal_storage_admin_mode");
		fieldMap.put("isUsbControlEnabled", "policy_usb_control_enabled");
		fieldMap.put("isUsbBlock", "usb_dev_list");
		fieldMap.put("isComPortBlock", "com_port_list");
		fieldMap.put("isNetPortBlock", "net_port_list");
		fieldMap.put("isProcessList", "process_list");
		fieldMap.put("isFilePattern", "file_pattern_list");
		fieldMap.put("isWebAddr", "web_addr_list");
		fieldMap.put("isMsgBlock", "msg_block_list");
		fieldMap.put("isWatermark", "watermark_descriptor");
		fieldMap.put("printLogDesc", "print_log_descriptor");
		fieldMap.put("patternFileControl", "pattern_file_control");		
		fieldMap.put("isSensitiveDirEnabled", "sensitive_dir_enabled");
		
		HashMap<String, String> temporarilypolicyList = new HashMap<String, String>();
		String fieldName;
		
		for (Entry<String, Object> entry : map.entrySet()) {		
			if (map.containsKey(entry.getKey() + "_temporarily")) {
				fieldName = fieldMap.get(entry.getKey());
				if (fieldName != null) {
					temporarilypolicyList.put(fieldName + "_temporarily", map.get(entry.getKey() + "_temporarily").toString());
				}
			}
		}
		
		int isUninstall = Integer.parseInt(map.get("isUninstall").toString());		
		int isFileEncryption = Integer.parseInt(map.get("isFileEncryption").toString());
		int isCdEncryption = Integer.parseInt(map.get("isCdEncryption").toString());
		int isPrint = Integer.parseInt(map.get("isPrint").toString());
		int isCdEnabled = Integer.parseInt(map.get("isCdEnabled").toString());
		int isCdExport = Integer.parseInt(map.get("isCdExport").toString());
		int isWlan = Integer.parseInt(map.get("isWlan").toString());
		int isNetShare = Integer.parseInt(map.get("isNetShare").toString());
		int isWebExport = Integer.parseInt(map.get("isWebExport").toString());
		
		int isSensitiveDirEnabled = Integer.parseInt(map.get("isSensitiveDirEnabled").toString());
		String quarantinePathAccessCode = "";
		int isSensitiveFileAccess = Integer.parseInt(map.get("isSensitiveFileAccess").toString());
		int isStorageExport = Integer.parseInt(map.get("isStorageExport").toString());
		int isStorageAdmin = Integer.parseInt(map.get("isStorageAdmin").toString());
		int isUsbControlEnabled = Integer.parseInt(map.get("isUsbControlEnabled").toString());
		
		int patternFileControl = Integer.parseInt(map.get("patternFileControl").toString());
		String printLogDesc = map.get("printLogDesc").toString();
		String isUsbBlock = map.get("isUsbBlock").toString();
		String isComPortBlock = map.get("isComPortBlock").toString();
		String isNetPortBlock = map.get("isNetPortBlock").toString();
		String isProcessList = map.get("isProcessList").toString();
		int isProcessCheck = Integer.parseInt(map.get("isProcessCheck").toString());
		String isFilePattern = map.get("isFilePattern").toString();
		int isPatternCheck = Integer.parseInt(map.get("isPatternCheck").toString());
		String isWebAddr = map.get("isWebAddr").toString();
		String isMsgBlock = map.get("isMsgBlock").toString();
		String isWatermark = map.get("isWatermark").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE policy_info SET update_server_time= NOW()";
		
		if (isUninstall != -1) { sql += ",uninstall_enabled= ? "; }
		if (isFileEncryption != -1) { sql += ",file_encryption_enabled= ? "; }
		if (isCdEncryption != -1) { sql += ",cd_encryption_enabled= ? "; }
		if (isPrint != -1) { sql += ",printer_enabled= ? "; }
		if (isCdEnabled != -1) { sql += ",cd_enabled= ? "; }
		if (isCdExport != -1) { sql += ",cd_export_enabled= ? "; }
		if (isWlan != -1) { sql += ",wlan_enabled= ? "; }
		if (isNetShare != -1) { sql += ",net_share_enabled= ? "; }
		if (isWebExport != -1) { sql += ",web_export_enabled= ? "; }
		
		if (isSensitiveDirEnabled != -1) {
			if (isSensitiveDirEnabled == 1) {
				quarantinePathAccessCode = CommonUtil.createQuarantinePathAccessCode();
			}
			sql += ",quarantine_path_access_code= ? ";
			sql += ",sensitive_dir_enabled= ? "; 
		}
		if (isSensitiveFileAccess != -1) { sql += ",policy_sensitive_file_access= ? "; }
		if (isStorageExport != -1) { sql += ",removal_storage_export_enabled= ? "; }
		if (isStorageAdmin != -1) { sql += ",removal_storage_admin_mode= ? "; }
		if (isUsbControlEnabled != -1) { sql += ",policy_usb_control_enabled= ? "; }
		
		if (!"".equals(isUsbBlock)) { sql += ",usb_dev_list= ? "; }
		if (!"".equals(isComPortBlock)) { sql += ",com_port_list= ? "; }
		if (!"".equals(isNetPortBlock)) { sql += ",net_port_list= ? "; }
		if (isProcessCheck != -1) { sql += ",process_list= ? "; }
		if (isPatternCheck != -1) { sql += ",file_pattern_list= ? "; }
		if (!"".equals(isWebAddr)) { sql += ",web_addr_list= ? "; }
		if (!"".equals(isMsgBlock)) { sql += ",msg_block_list= ? "; }
		if (!"".equals(isWatermark)) { sql += ",watermark_descriptor= ? "; }
		if (Integer.parseInt(printLogDesc) != -1) { sql += ",print_log_descriptor= ? "; }
		if (patternFileControl != -1) { sql += ",pattern_file_control= ? "; }
		
		for (Entry<String, String> entry : temporarilypolicyList.entrySet()) {			
			sql += "," + entry.getKey() + "=? ";
		}
		
		sql += "WHERE no= ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			
			int i = 1;
			if (isUninstall != -1) { pstmt.setInt(i++ , isUninstall); }
			if (isFileEncryption != -1) { pstmt.setInt(i++ , isFileEncryption); }
			if (isCdEncryption != -1) { pstmt.setInt(i++ , isCdEncryption); }
			if (isPrint != -1) { pstmt.setInt(i++ , isPrint); }
			if (isCdEnabled != -1) { pstmt.setInt(i++ , isCdEnabled); }
			if (isCdExport != -1) { pstmt.setInt(i++ , isCdExport); }
			if (isWlan != -1) { pstmt.setInt(i++ , isWlan); }
			if (isNetShare != -1) { pstmt.setInt(i++ , isNetShare); }
			if (isWebExport != -1) { pstmt.setInt(i++ , isWebExport); }
			
			if (isSensitiveDirEnabled != -1) {
				pstmt.setString(i++, quarantinePathAccessCode);
				pstmt.setInt(i++, isSensitiveDirEnabled); 
			}
			if (isSensitiveFileAccess != -1) { pstmt.setInt(i++, isSensitiveFileAccess); }
			if (isStorageExport != -1) { pstmt.setInt(i++, isStorageExport); }
			if (isStorageAdmin != -1) { pstmt.setInt(i++, isStorageAdmin); }
			if (isUsbControlEnabled != -1) { pstmt.setInt(i++, isUsbControlEnabled); }
			
			if (!"".equals(isUsbBlock)) { pstmt.setString(i++ , isUsbBlock); }
			if (!"".equals(isComPortBlock)) { pstmt.setString(i++ , isComPortBlock); }
			if (!"".equals(isNetPortBlock)) { pstmt.setString(i++ , isNetPortBlock); }
			if (isProcessCheck != -1) { pstmt.setString(i++ , isProcessList); }
			if (isPatternCheck != -1) { pstmt.setString(i++ , isFilePattern); }
			if (!"".equals(isWebAddr)) { pstmt.setString(i++ , isWebAddr); }
			if (!"".equals(isMsgBlock)) { pstmt.setString(i++ , isMsgBlock); }
			if (!"".equals(isWatermark)) { pstmt.setString(i++ , isWatermark); }
			if (Integer.parseInt(printLogDesc) != -1) { pstmt.setString(i++ , printLogDesc); }
			if (patternFileControl != -1) { pstmt.setInt(i++ , patternFileControl); }
			
			for (Entry<String, String> entry : temporarilypolicyList.entrySet()) {			
				pstmt.setString(i++, entry.getValue());
			}
			
			pstmt.setInt(i++ , policy_id);
			pstmt.executeUpdate();
									
			con.commit();
			
			// ��å ���� �α�
			ICommonService commonService = new CommonServiceImpl();
			commonService.setPolicyUpdateToInsertLog(policy_id);
		
		}catch(SQLException ex){
			returnCode = ConfigInfo.RETURN_CODE_ERROR;
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
		
		return returnCode;
	}

	public HashMap<String, Object> updateNoticeModifyUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int bbs_id = Integer.parseInt(map.get("bbs_id").toString());
		int file_id = Integer.parseInt(map.get("file_id").toString());
		String bbs_title = map.get("bbs_title").toString();
		String bbs_body = map.get("bbs_body").toString();
		String bbs_body_trim = map.get("bbs_body_trim").toString();
		String special_type = map.get("special_type").toString();
		String attfile_yn = map.get("attfile_yn").toString();
		int upd_staf_no = Integer.parseInt(map.get("upd_staf_no").toString());
		
		String save_file_nm =  map.get("save_file_nm").toString();
		String view_file_nm =  map.get("view_file_nm").toString();
		String att_file_path =  map.get("att_file_path").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		
		
		String sql= "UPDATE services_file_upload_info "
				+ "SET att_file_path = ? , "
				+ "view_file_nm = ? , "
				+ "save_file_nm = ? "
				+ "WHERE attfile_id = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			
			if (attfile_yn.equals("Y")) {
				
				if (file_id == 0) {
					
					sql= "INSERT INTO services_file_upload_info (att_file_path, view_file_nm, save_file_nm) "
							+ "VALUES (?, ?, ?) ";
					
					pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, att_file_path);
					pstmt.setString(2, view_file_nm);
					pstmt.setString(3, save_file_nm);
					pstmt.executeUpdate();
					
					rs = pstmt.getGeneratedKeys();
					
					if (rs.next()) {
						file_id = rs.getInt(1);
					}
					
				} else {
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, att_file_path);
					pstmt.setString(2, view_file_nm);
					pstmt.setString(3, save_file_nm);
					pstmt.setInt(4, file_id);
					pstmt.executeUpdate();
				}
				
				sql= "UPDATE user_notice_bbs "
						+ "SET bbs_title = ?, "
						+ "special_type = ?, "
						+ "bbs_body = ?, "
						+ "bbs_body_trim = ?, "
						+ "upd_staf_no = ?, "
						+ "upd_dt = NOW(), "
						+ "attfile_yn = ?, "
						+ "attfile_id = ? " 
						+ "WHERE bbs_id = ?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, bbs_title);
				pstmt.setString(2, special_type);
				pstmt.setString(3, bbs_body);
				pstmt.setString(4, bbs_body_trim);
				pstmt.setInt(5, upd_staf_no);
				pstmt.setString(6, attfile_yn);
				pstmt.setInt(7, file_id);
				pstmt.setInt(8, bbs_id);
				pstmt.executeUpdate();
				
			} else {
				
				sql= "UPDATE user_notice_bbs "
						+ "SET bbs_title = ?, "
						+ "special_type = ?, "
						+ "bbs_body = ?, "
						+ "bbs_body_trim = ?, "
						+ "upd_staf_no = ?, "
						+ "upd_dt = NOW(), "
						+ "attfile_yn = ?, "
						+ "attfile_id = ? " 
						+ "WHERE bbs_id = ?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, bbs_title);
				pstmt.setString(2, special_type);
				pstmt.setString(3, bbs_body);
				pstmt.setString(4, bbs_body_trim);
				pstmt.setInt(5, upd_staf_no);
				pstmt.setString(6, attfile_yn);
				pstmt.setInt(7, file_id);
				pstmt.setInt(8, bbs_id);
				pstmt.executeUpdate();
				
			}
									
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

	public List<UserContactModel> getAdminContactList(HashMap<String, Object> map) {
		List<UserContactModel> data = new ArrayList<UserContactModel>();
		
		String whereSql = "WHERE 1=1 ";
		String contact_type = map.get("contact_type").toString();
		String contact_tilte = map.get("contact_tilte").toString();
		String contact_user = map.get("contact_user").toString();
		
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
		
		if(!contact_type.equals("")) 	whereSql += "AND con.contact_type = ? ";
		if(!contact_tilte.equals("")) 	whereSql += "AND con.contact_title LIKE ? ";
		if(!contact_user.equals("")) 	whereSql += "AND ui.name LIKE ? ";

		if(oDept != null)			whereSql += "AND ui.dept_no in ("+idList+") ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
		
		whereSql += "ORDER BY con.contact_id DESC LIMIT ?, ? ";	
		
		String sql= 
				"SELECT con.contact_id, "
				+ "con.contact_type, "
				+ "con.contact_title, "
				+ "con.contact_body, "
				+ "ui.id, "
				+ "ui.name, "
				+ "DATE(con.reg_dt) as reg_dt, "
				+ "con.comment_yn, "
				+ "con_comm.comment_id, "
				+ "IFNULL(admin_info.id, '')as comment_reg_staf_id, "
				+ "IFNULL(con_comm.reply_content, '') as reply_content, "
				+ "IFNULL(con_comm.reg_dt, '') as comment_reg_dt "
				+ "FROM user_contact_info AS con "
				+ "LEFT JOIN user_info AS ui ON con.reg_user_staf_no = ui.no "
				+ "LEFT JOIN user_contact_comment AS con_comm ON con.contact_id = con_comm.contact_id "
				+ "LEFT JOIN admin_info AS admin_info ON con_comm.reg_admin_staf_no = admin_info.no ";

		sql += whereSql;
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
		
			int i = 1;
			if(!contact_type.equals("")) pstmt.setString(i++, contact_type);
			if(!contact_tilte.equals("")) pstmt.setString(i++, "%" + contact_tilte + "%");
			if(!contact_user.equals("")) pstmt.setString(i++, "%" + contact_user + "%");
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}
		
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserContactModel model = new UserContactModel();
				model.setContactId(rs.getInt("contact_id"));
				model.setContactType(rs.getInt("contact_type"));
				model.setTypeName(rs.getInt("contact_type"));
				model.setContactTitle(CommonUtil.getReplaceHtmlChar(rs.getString("contact_title")));
				model.setContactBody(CommonUtil.getReplaceHtmlChar(rs.getString("contact_body")));
				model.setRegUserStafId(rs.getString("id"));
				model.setRegUserName(rs.getString("name"));
				model.setRegDt(rs.getString("reg_dt"));
				model.setCommentYN(rs.getString("comment_yn"));
				model.setCommentId(rs.getInt("comment_id"));
				model.setCommnetRegStafId(rs.getString("comment_reg_staf_id"));
				model.setReplyContent(rs.getString("reply_content"));
				model.setCommentRegDt(rs.getString("comment_reg_dt"));
				
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

	public int getAdminContactListCount(HashMap<String, Object> map) {
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String contact_type = map.get("contact_type").toString();
		String contact_tilte = map.get("contact_tilte").toString();
		String contact_user = map.get("contact_user").toString();
		
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

		if(!contact_type.equals("")) 	whereSql += "AND con.contact_type = ? ";
		if(!contact_tilte.equals("")) 	whereSql += "AND con.contact_title LIKE ? ";
		if(!contact_user.equals("")) 	whereSql += "AND ui.name LIKE ? ";

		if(oDept != null)			whereSql += "AND ui.dept_no in ("+idList+") ";
	
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
				
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM user_contact_info AS con "
						+ "LEFT JOIN user_info AS ui ON con.reg_user_staf_no = ui.no "
						+ "LEFT JOIN user_contact_comment AS con_comm ON con.contact_id = con_comm.contact_id "
						+ "LEFT JOIN admin_info AS admin_info ON con_comm.reg_admin_staf_no = admin_info.no ";

		sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);	

			int i = 1;

			if(!contact_type.equals("")) pstmt.setString(i++, contact_type);
			if(!contact_tilte.equals("")) pstmt.setString(i++, "%" + contact_tilte + "%");
			if(!contact_user.equals("")) pstmt.setString(i++, "%" + contact_user + "%");
			
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

	public HashMap<String, Object> insertContactCommentSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int reg_admin_staf_no = Integer.parseInt(map.get("reg_admin_staf_no").toString());
		int contact_id = Integer.parseInt(map.get("contact_id").toString());
		String reply_content = map.get("reply_content").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO user_contact_comment (contact_id, reg_admin_staf_no, reply_content, reg_dt) VALUES ( ?, ?, ?, NOW() )";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, contact_id);
			pstmt.setInt(2, reg_admin_staf_no);
			pstmt.setString(3, reply_content);
			pstmt.executeUpdate();
			
			sql = "UPDATE user_contact_info set comment_yn = 'Y' WHERE contact_id = ? ";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, contact_id);
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

	public HashMap<String, Object> getCommentInfo(HashMap<String, Object> map) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		int comment_id = Integer.parseInt(map.get("comment_id").toString());
		
		String sql= "SELECT "
				+ "comment_id, "
				+ "contact_id, "
				+ "reg_admin_staf_no, "
				+ "reply_content, "
				+ "reg_dt "
				+ "FROM user_contact_comment WHERE del_yn = 'N' AND comment_id = ?";
					
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, comment_id);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int sizeOfColumn = metaData.getColumnCount();
			
			String column = "";
			
			if(rs.next()){
				// Column�� ������ŭ ȸ��
				for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++) {
					// Column�� ������ŭ ȸ��
					column = metaData.getColumnName(indexOfcolumn + 1);
					// phone number �� ��� ��ȣȭ
					data.put(column, rs.getString(column));
				}

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

	public HashMap<String, Object> updateContactCommentUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int comment_id = Integer.parseInt(map.get("comment_id").toString());
		String reply_content = map.get("reply_content").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE user_contact_comment SET reply_content = ?, upd_dt = NOW() WHERE comment_id = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
					
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, reply_content);
			pstmt.setInt(2, comment_id);
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

	public HashMap<String, Object> updateNoticeDelete(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int del_staf_no = Integer.parseInt(map.get("del_staf_no").toString());
		String del_bbs_id = map.get("del_bbs_id").toString();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE user_notice_bbs SET del_staf_no = ?, del_dt = NOW(), del_yn= 'Y' WHERE bbs_id in ("+ del_bbs_id +")";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
					
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, del_staf_no);
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

	public List<HashMap<String, Object>> getApplyPolicyAllUserList(HashMap<String, Object> map) {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		String whereSql = "WHERE 1=1 AND ui.valid = 1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
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
		
		if(!user_id.equals(""))	whereSql += "AND ui.id LIKE ? ";
		if(!user_name.equals("")) whereSql += "AND ui.name LIKE ? ";
		if(!user_phone.equals("")) whereSql += "AND ui.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND ui.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND ui.rank LIKE ? ";
		if(!user_number.equals("")) whereSql += "AND ui.number LIKE ? ";
		if(!user_pc.equals("")) whereSql += "AND ai.pc_name LIKE ? ";
		if(!user_ip.equals("")) whereSql += "AND ai.ip_addr LIKE ? ";

		if(oDept != null) whereSql += "AND ai.dept_no in ("+idList+") ";

		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
		
		whereSql += "ORDER BY ui.no DESC ";	
		
		String sql= 
			"SELECT "
				+ "ai.no as agentNo, "
			    + "ui.number as userNo, "
			    + "ui.no as uno, "
				
			    + "ai.policy_no as policyNo, "
			    + "ai.dept_no as deptId, "
			    + "ui.id as userId, "
			    + "ui.name as userName, "
			    + "ui.duty as duty, "
			    + "ui.rank as rank, "
				+ "ai.ip_addr as ipAddr, "
			    + "ai.mac_addr as macAddr, "
			    + "ai.pc_name as pcName, "
			    + "di.short_name as deptName, "
			    + "ui.number, "
			    
			    + "pi.uninstall_enabled_temporarily as isUninstall_temporarily, "
			    + "pi.file_encryption_enabled_temporarily as isFileEncryption_temporarily, "
			    + "pi.cd_encryption_enabled_temporarily as isCdEncryption_temporarily, "
			    + "pi.printer_enabled_temporarily as isPrint_temporarily, "
			    + "pi.cd_enabled_temporarily as isCdEnabled_temporarily, "
			    + "pi.cd_export_enabled_temporarily as isCdExport_temporarily, "
			    + "pi.wlan_enabled_temporarily as isWlan_temporarily, "
			    + "pi.net_share_enabled_temporarily as isNetShare_temporarily, "
			    + "pi.web_export_enabled_temporarily as isWebExport_temporarily, "	
			    	
			    + "pi.sensitive_dir_enabled_temporarily as isSensitiveDirEnabled_temporarily, "
			    + "pi.policy_sensitive_file_access_temporarily as isSensitiveFileAccess_temporarily, "
			    + "pi.policy_usb_control_enabled_temporarily as isUsbControlEnabled_temporarily, "
			    
			    + "pi.removal_storage_export_enabled_temporarily as isStorageExport_temporarily, "
			    + "pi.removal_storage_admin_mode_temporarily as isStorageAdmin_temporarily, "
			    
			    + "pi.usb_dev_list_temporarily as isUsbBlock_temporarily, "
			    + "pi.com_port_list_temporarily as isComPortBlock_temporarily, "
			    + "pi.net_port_list_temporarily as isNetPortBlock_temporarily, "
			    + "pi.process_list_temporarily as isProcessList_temporarily, "
			    + "pi.file_pattern_list_temporarily as isFilePattern_temporarily, "
			    + "pi.web_addr_list_temporarily as isWebAddr_temporarily, "
			    + "pi.msg_block_list_temporarily as isMsgBlock_temporarily, "
			    + "pi.watermark_descriptor_temporarily as isWatermark_temporarily, "
			    + "pi.print_log_descriptor_temporarily as printLogDesc_temporarily, "
			    + "pi.quarantine_path_access_code_temporarily as quarantinePathAccessCode_temporarily, " 
			    + "pi.pattern_file_control_temporarily as patternFileControl_temporarily, "
			    
			    
			    
			    + "IFNULL(pi.uninstall_enabled, 0) as isUninstall, "
			    + "IFNULL(pi.file_encryption_enabled, 0) as isFileEncryption, "
			    + "IFNULL(pi.cd_encryption_enabled, 0) as isCdEncryption, "
			    + "IFNULL(pi.printer_enabled, 0) as isPrint, "
			    + "IFNULL(pi.cd_enabled, 0) as isCdEnabled, "
			    + "IFNULL(pi.cd_export_enabled, 0) as isCdExport, "
			    + "IFNULL(pi.wlan_enabled, 0) as isWlan, "
			    + "IFNULL(pi.net_share_enabled, 0) as isNetShare, "
			    + "IFNULL(pi.web_export_enabled, 0) as isWebExport, "
			    
			    + "IFNULL(pi.sensitive_dir_enabled, 0) as sensitive_dir_enabled, "
			    + "IFNULL(pi.policy_sensitive_file_access, 0) as policy_sensitive_file_access, "
			    + "IFNULL(pi.policy_usb_control_enabled, 0) as policy_usb_control_enabled, "
			    
			    + "IFNULL(pi.removal_storage_export_enabled, 0) as isStorageExport, "
			    + "IFNULL(pi.removal_storage_admin_mode, 0) as isStorageAdmin, "
			    
			    + "IFNULL(pi.usb_dev_list, 'N') as isUsbBlock, "
			    + "IFNULL(pi.com_port_list, 'N') as isComPortBlock, "
			    + "IFNULL(pi.net_port_list, 'N') as isNetPortBlock, "
			    + "IFNULL(pi.process_list, '') as isProcessList, "
			    + "IFNULL(pi.file_pattern_list, '') as isFilePattern, "
			    + "IFNULL(pi.web_addr_list, 'N') as isWebAddr, "
			    + "IFNULL(pi.msg_block_list, 'N') as isMsgBlock, "
			    + "IFNULL(pi.watermark_descriptor, 'N') as isWatermark, "
			    + "IFNULL(pi.print_log_descriptor, 0) as printLogDesc, "
			    + "IFNULL(pi.quarantine_path_access_code, '') as quarantinePathAccessCode, " 
			    + "IFNULL(pi.pattern_file_control, 0) as patternFileControl "
			+ "FROM agent_info AS ai "
			+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
			+ "LEFT JOIN policy_info AS pi ON ai.policy_no = pi.no "
			+ "INNER JOIN dept_info as di ON ai.dept_no = di.no ";


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
			if(!user_number.equals(""))	pstmt.setString(i++, "%" + user_number + "%");;
			if(!user_pc.equals("")) pstmt.setString(i++, "%" + user_pc + "%");
			if(!user_ip.equals("")) pstmt.setString(i++, "%" + user_ip + "%");
			
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, Object> result = new HashMap<String, Object>();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount(); //컬럼의 총개수

				for (int ci = 1; ci <= columnCount; ci++) {
					
					if (rsmd.getPrecision(ci) == 1) {
						result.put(rsmd.getColumnLabel(ci), rs.getBoolean(rsmd.getColumnLabel(ci)));
					} else {
						if ("isStorageAdmin".equals(rsmd.getColumnLabel(ci))) {
							result.put("isStorageAdmin", rs.getBoolean("isStorageAdmin"));
						} else {
							result.put(rsmd.getColumnLabel(ci), rs.getObject(rsmd.getColumnLabel(ci)));
							if (result.get(rsmd.getColumnLabel(ci)) == null) {
								//result.put(rsmd.getColumnLabel(ci), "");
							}
						}
					}
				}
				data.add(result);
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
