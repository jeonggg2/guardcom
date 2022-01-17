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
import gcom.Model.DiskConnectLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.DiskInfoModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.PartitionConnectLogModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;
import gcom.common.util.ConfigInfo;;

//�뵒�뒪�겕 �뙆�씪�쟾�넚濡쒓렇
public class DiskDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public DiskDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public int getDiskExportListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
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
		if(!start_date.equals("")) 	whereSql += "AND de.export_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND de.export_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
				"SELECT "
				+ "COUNT(*) AS cnt "
				+ "FROM disk_export_log AS de "
				+ "INNER JOIN user_info AS ur ON ur.no = de.user_no "
				+ "INNER JOIN agent_log AS agent ON agent.no = de.agent_log_no "
				+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no "
				+ "LEFT JOIN partition_log AS ptn ON de.partition_log_no = ptn.no ";

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
	
	
	public List<DiskExportModel> getDiskExportList(HashMap<String, Object> map){
		List<DiskExportModel> data = new ArrayList<DiskExportModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
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
		if(!start_date.equals("")) 	whereSql += "AND de.export_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND de.export_client_time < ? + interval 1 day ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY de.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "de.no AS export_no, "
+ "ur.number AS user_no, "
+ "ifnull(de.export_server_time, '') AS export_server_time, "
+ "ifnull(de.export_client_time, '') AS export_client_time, "
+ "de.grade, "
+ "de.file_list, "
+ "de.notice, "
+ "de.export_status, "
+ "ifnull(de.file_id, '') AS file_id, "
+ "ur.id AS user_id, "
+ "ur.dept_no, "
+ "ur.name, "
+ "ur.duty,"
+ "ur.rank,"
+ "agent.ip_addr,"
+ "agent.mac_addr,"
+ "agent.pc_name,"
+ "dept.name AS dept_name, "
+ "ifnull(ptn.guid, '') AS partition_guid, "
+ "ifnull(ptn.label, '') AS partition_label "
+ "FROM disk_export_log AS de "
+ "INNER JOIN user_info AS ur ON ur.no = de.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = de.agent_log_no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no "
+ "LEFT JOIN partition_log AS ptn ON de.partition_log_no = ptn.no ";

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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DiskExportModel model = new DiskExportModel();
				model.setExportNo(rs.getInt("export_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("name"));
				model.setExportServerTime(rs.getString("export_server_time"));
				model.setExportClientTime(rs.getString("export_client_time"));
				model.setGrade(rs.getInt("grade"));
				model.setFileList(rs.getString("file_list"));
				model.setNotice(rs.getString("notice"));
				model.setExportStatus(rs.getString("export_status"));
				model.setFileId(rs.getString("file_id"));
				model.setUserId(rs.getString("user_id"));
				model.setDeptId(rs.getInt("dept_no"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));
				model.setPartitionGuid(rs.getString("partition_guid"));
				model.setPartitionLabel(rs.getString("partition_label"));
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
	
	
	public int getUsbConnectListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE usb.visible = true ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String device_name = map.get("device_name").toString();
		String pc_name = map.get("pc_name").toString();
		String device_property = map.get("device_property").toString();

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
		if(!start_date.equals("")) 	whereSql += "AND usb.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND usb.connect_client_time < ? + interval 1 day ";
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!device_name.equals("")) whereSql += "AND usb.device_name LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!device_property.equals("")) 	whereSql += "AND usb.device_property LIKE ? ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";

		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM usb_connect_log AS usb "
+ "INNER JOIN user_info AS ur ON ur.no = usb.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = usb.agent_log_no "
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!device_name.equals("")) pstmt.setString(i++, "%" + device_name + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!device_property.equals("")) pstmt.setString(i++, "%" + device_property + "%");
			
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
	
	
	public List<UsbConnectModel> getUsbConnectList(HashMap<String, Object> map){
		List<UsbConnectModel> data = new ArrayList<UsbConnectModel>();
		
		String whereSql = "WHERE usb.visible = true ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String device_name = map.get("device_name").toString();
		String pc_name = map.get("pc_name").toString();
		String device_property = map.get("device_property").toString();
		
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
		if(!start_date.equals("")) 	whereSql += "AND usb.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND usb.connect_client_time < ? + interval 1 day ";
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!device_name.equals("")) whereSql += "AND usb.device_name LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!device_property.equals("")) 	whereSql += "AND usb.device_property LIKE ? ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		whereSql += "ORDER BY usb.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "usb.no AS usb_no, "
+ "usb.device_name, "
+ "usb.device_property, "
+ "ifnull(usb.connect_server_time, '') AS connect_server_time, "
+ "ifnull(usb.connect_client_time, '') AS connect_client_time, "
+ "usb.notice, "
+ "ur.id AS user_id, "
+ "ur.dept_no, ur.duty, "
+ "ur.rank, "
+ "ur.number AS user_no, "
+ "ur.name AS user_name, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM usb_connect_log AS usb "
+ "INNER JOIN user_info AS ur ON ur.no = usb.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = usb.agent_log_no "
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!device_name.equals("")) pstmt.setString(i++, "%" + device_name + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!device_property.equals("")) pstmt.setString(i++, "%" + device_property + "%");

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UsbConnectModel model = new UsbConnectModel();
				model.setUsbNo(rs.getInt("usb_no"));
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
				model.setConnectServerTime(rs.getString("connect_server_time"));
				model.setConnectClientTime(rs.getString("connect_client_time"));
				model.setDeviceProperty(rs.getString("device_property"));
				model.setDeviceName(rs.getString("device_name"));			
				model.setNotice(rs.getString("notice"));
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
	
	
	public int getUnAuthUsbListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String name = map.get("name").toString();
		String serial = map.get("serial").toString();
		String desc = map.get("desc").toString();
		String allow = map.get("allow").toString();

		if(!name.equals("")) 	whereSql += "AND usb.name LIKE ? ";
		if(!serial.equals("")) 	whereSql += "AND usb.serial_number LIKE ? ";
		if(!desc.equals("")) 	whereSql += "AND usb.description LIKE ? ";
		if(!allow.equals("")) 	whereSql += "AND usb.allow = ? ";

		
		String sql= 
"SELECT "
+ "COUNT(*) cnt "
+ "FROM usb_dev_info AS usb ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!name.equals("")) pstmt.setString(i++, "%" + name + "%");
			if(!serial.equals("")) pstmt.setString(i++, "%" + serial + "%");
			if(!desc.equals("")) 	pstmt.setString(i++, "%" + desc + "%");
			if(!allow.equals("")){
				int iAllow = Integer.parseInt(allow.toString());
				pstmt.setInt(i, iAllow);
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
	
	
	public List<UsbDevInfoModel> getUnAuthUsbList(HashMap<String, Object> map){
		List<UsbDevInfoModel> data = new ArrayList<UsbDevInfoModel>();
		
		String whereSql = "WHERE 1=1 ";
		String name = map.get("name").toString();
		String serial = map.get("serial").toString();
		String desc = map.get("desc").toString();
		String allow = map.get("allow").toString();

		if(!name.equals("")) 	whereSql += "AND usb.name LIKE ? ";
		if(!serial.equals("")) 	whereSql += "AND usb.serial_number LIKE ? ";
		if(!desc.equals("")) 	whereSql += "AND usb.description LIKE ? ";
		if(!allow.equals("")) 	whereSql += "AND usb.allow = ? ";
		
		whereSql += "ORDER BY usb.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "usb.no, "
+ "usb.name, "
+ "usb.vid, "
+ "usb.pid, "
+ "class, "
+ "subclass, "
+ "protocol, "
+ "usb.allow, "
+ "usb.serial_number, "
+ "usb.description "
+ "FROM usb_dev_info AS usb ";
sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(!name.equals("")) pstmt.setString(i++, "%" + name + "%");
			if(!serial.equals("")) pstmt.setString(i++, "%" + serial + "%");
			if(!desc.equals("")) 	pstmt.setString(i++, "%" + desc + "%");
			if(!allow.equals("")){
				int iAllow = Integer.parseInt(allow.toString());
				pstmt.setInt(i++, iAllow);
			}

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UsbDevInfoModel model = new UsbDevInfoModel();
				model.setUsbId(rs.getInt("no"));
				model.setName(rs.getString("name"));
				model.setVid(rs.getString("vid"));
				model.setPid(rs.getString("pid"));
				model.setMainclass(rs.getString("class"));
				model.setSubclass(rs.getString("subclass"));
				model.setProtocol(rs.getString("protocol"));
				model.setAllow(rs.getInt("allow") == 1 ? true : false);
				model.setSerialNumber(rs.getString("serial_number"));
				model.setDescription(rs.getString("description"));
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
	
	public HashMap<String, Object> updateAllowDiskInfo(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String insertSql= 
				"UPDATE disk_info "
				+ "SET "
				+ "allow=? ";
				
		insertSql +=   "WHERE no=?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(insertSql);

			int i = 1;
			pstmt.setString(i++, map.get("allow").toString());
			pstmt.setString(i++, map.get("no").toString());
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
	
	public List<DiskInfoModel> getDiskInfoList(HashMap<String, Object> map){
		List<DiskInfoModel> data = new ArrayList<DiskInfoModel>();
		
		String whereSql = "WHERE 1=1 ";
		
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String label = map.get("label").toString();
		String type = map.get("type").toString();
		String hwinfo = map.get("hwinfo").toString();

		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		
		String status = map.get("status").toString();
		
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
		
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!guid.equals("")) 	whereSql += "AND disk.guid LIKE ? ";
		if(!label.equals("")) 	whereSql += "AND disk.label LIKE ? ";
		if(!type.equals("")) 	whereSql += "AND disk.type LIKE ? ";
		if(!hwinfo.equals("")) 	whereSql += "AND disk.hw_info LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND disk.status = ? ";

		if(!start_date.equals("")) 	whereSql += "AND disk.created_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND disk.created_server_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY disk.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "disk.no AS disk_no, "
+ "ur.number AS user_no, "
+ "ur.name AS name, "
+ "disk.status, "
+ "ifnull(disk.created_server_time, '') AS create_server_time, "
+ "ifnull(disk.created_client_time, '') AS create_client_time, "
+ "ifnull(disk.label, '') AS label, "
+ "ifnull(disk.guid, '') AS guid, "
+ "ifnull(disk.hw_info, '') AS hw_info, "
+ "disk.type, "
+ "ur.id AS user_id, "
+ "ur.name, "
+ "ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name,"
+ "dept.name AS dept_name "
+ "FROM disk_info AS disk "
+ "INNER JOIN user_info AS ur ON ur.no = disk.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = disk.agent_log_no "
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
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
			if(!type.equals("")) 	pstmt.setString(i++, "%" + type + "%");
			if(!hwinfo.equals("")) 	pstmt.setString(i++, "%" + hwinfo + "%");
			
			if(!status.equals("")) 	pstmt.setString(i++, status);

			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DiskInfoModel model = new DiskInfoModel();
				model.setDiskNo(rs.getInt("disk_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("name"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setLabel(rs.getString("label"));
				model.setGuid(rs.getString("guid"));
				model.setHwInfo(rs.getString("hw_info"));
				model.setType(Integer.parseInt(rs.getString("type")));
				
				model.setCreateServerTime(rs.getString("create_server_time"));
				model.setCreateClientTime(rs.getString("create_client_time"));
				model.setStatus(rs.getInt("status"));
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
	
	public int getDiskInfoListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String label = map.get("label").toString();
		String type = map.get("type").toString();
		String hwinfo = map.get("hwinfo").toString();

		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		
		String status = map.get("status").toString();
		
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
		
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!guid.equals("")) 	whereSql += "AND disk.guid LIKE ? ";
		if(!label.equals("")) 	whereSql += "AND disk.label LIKE ? ";
		if(!type.equals("")) 	whereSql += "AND disk.type LIKE ? ";
		if(!hwinfo.equals("")) 	whereSql += "AND disk.hw_info LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND disk.status = ? ";

		if(!start_date.equals("")) 	whereSql += "AND disk.created_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND disk.created_server_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY disk.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM disk_info AS disk "
+ "INNER JOIN user_info AS ur ON ur.no = disk.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = disk.agent_log_no "
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
			if(!type.equals("")) 	pstmt.setString(i++, "%" + type + "%");
			if(!hwinfo.equals("")) 	pstmt.setString(i++, "%" + hwinfo + "%");
			
			if(!status.equals("")) 	pstmt.setString(i++, status);
						
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			
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
	
public String updateDiskAllow(HashMap<String, Object> map) {
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		int disk_no = Integer.parseInt(map.get("disk_no").toString());
		int status = Integer.parseInt(map.get("status").toString());

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
				
			String sql;
			sql = "UPDATE disk_info SET status = ? WHERE no = ? ";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setInt(2, disk_no);
			pstmt.executeUpdate();
			
		
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

	public List<DiskConnectLogModel> getDiskConnectLogList(HashMap<String, Object> map){
		List<DiskConnectLogModel> data = new ArrayList<DiskConnectLogModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String label = map.get("label").toString();
		String type = map.get("type").toString();
		String hwinfo = map.get("hwinfo").toString();

		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		
		String status = map.get("status").toString();
		
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
		
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!guid.equals("")) 	whereSql += "AND log.guid LIKE ? ";
		if(!label.equals("")) 	whereSql += "AND log.label LIKE ? ";
		if(!type.equals("")) 	whereSql += "AND log.type LIKE ? ";
		if(!hwinfo.equals("")) 	whereSql += "AND log.hw_info LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND disk.status LIKE ? ";

		if(!start_date.equals("")) 	whereSql += "AND disk.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND disk.connect_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY disk.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "disk.no AS connect_no, "
+ "ur.number AS user_no, "
+ "ur.name AS name, "
+ "disk.status, "
+ "ifnull(disk.connect_server_time, '') AS connect_server_time, "
+ "ifnull(disk.connect_client_time, '') AS connect_client_time, "
+ "ifnull(log.created_server_time, '') AS create_server_time, "
+ "ifnull(log.created_client_time, '') AS create_client_time, "
+ "log.label, "
+ "log.guid, "
+ "log.hw_info, "
+ "log.type, "
+ "ur.id AS user_id, "
+ "ur.name, "
+ "ur.duty, "
+ "ur.rank, "
+ "agent.ip_addr, "
+ "agent.mac_addr, "
+ "agent.pc_name, "
+ "dept.name AS dept_name "
+ "FROM disk_connect_log AS disk "
+ "INNER JOIN disk_log AS log ON log.no = disk.disk_log_no "
+ "INNER JOIN user_info AS ur ON ur.no = disk.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = disk.agent_log_no "
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
			if(!type.equals("")) 	pstmt.setString(i++, "%" + type + "%");
			if(!hwinfo.equals("")) 	pstmt.setString(i++, "%" + hwinfo + "%");
			
			if(!status.equals("")) 	pstmt.setString(i++, "%" + status + "%");
						
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DiskConnectLogModel model = new DiskConnectLogModel();
				model.setConnectNo(rs.getInt("connect_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("name"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setLabel(rs.getString("label"));
				model.setGuid(rs.getString("guid"));
				model.setHwInfo(rs.getString("hw_info"));
				model.setType(Integer.parseInt(rs.getString("type")));
				
				model.setCreateServerTime(rs.getString("create_server_time"));
				model.setCreateClientTime(rs.getString("create_client_time"));
				model.setConnectServerTime(rs.getString("connect_server_time"));
				model.setConnectClientTime(rs.getString("connect_client_time"));
				model.setStatus(rs.getInt("status"));
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
	


	public int getDiskConnectLogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String label = map.get("label").toString();
		String type = map.get("type").toString();
		String hwinfo = map.get("hwinfo").toString();

		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		
		String status = map.get("status").toString();
		
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
		
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!guid.equals("")) 	whereSql += "AND log.guid LIKE ? ";
		if(!label.equals("")) 	whereSql += "AND log.label LIKE ? ";
		if(!type.equals("")) 	whereSql += "AND log.type LIKE ? ";
		if(!hwinfo.equals("")) 	whereSql += "AND log.hw_info LIKE ? ";
		if(!status.equals("")) 	whereSql += "AND disk.status LIKE ? ";

		if(!start_date.equals("")) 	whereSql += "AND disk.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND disk.connect_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		whereSql += "ORDER BY disk.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM disk_connect_log AS disk "
+ "INNER JOIN disk_log AS log ON log.no = disk.disk_log_no "
+ "INNER JOIN user_info AS ur ON ur.no = disk.user_no "
+ "INNER JOIN agent_log AS agent ON agent.no = disk.agent_log_no "
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
			if(!type.equals("")) 	pstmt.setString(i++, "%" + type + "%");
			if(!hwinfo.equals("")) 	pstmt.setString(i++, "%" + hwinfo + "%");
			
			if(!status.equals("")) 	pstmt.setString(i++, "%" + status + "%");
						
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			
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
	
	
	
	public List<PartitionConnectLogModel> getPartitionConnectLogList(HashMap<String, Object> map){
		List<PartitionConnectLogModel> data = new ArrayList<PartitionConnectLogModel>();
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String disk_guid = map.get("disk_guid").toString();
		String label = map.get("label").toString();
		
		
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

		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";

		if(!guid.equals("")) 		whereSql += "AND pl.guid LIKE ? ";
		if(!disk_guid.equals("")) 		whereSql += "AND pl.disk_guid LIKE ? ";
		if(!label.equals("")) 		whereSql += "AND pl.label LIKE ? ";

		if(!start_date.equals("")) 	whereSql += "AND connect.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND connect.connect_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		whereSql += "ORDER BY connect.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "connect.no AS connect_no, "
+ "ur.number AS user_no, "
+ "ifnull(connect.connect_server_time, '') AS connect_server_time, "
+ "ifnull(connect.connect_client_time, '') AS connect_client_time, "
+ "pl.guid, "
+ "pl.label, "
+ "pl.disk_guid, "
+ "ifnull(pl.created_server_time, '') AS created_server_time , "
+ "ifnull(pl.created_client_time, '') AS created_client_time , "
+ "ifnull(pl.update_client_time, '') AS update_client_time , "
+ "ifnull(pl.update_server_time, '') AS update_server_time , "
+ "ur.id AS user_id, "
+ "ur.name, "
+ "ur.duty,"
+ "ur.rank,"
+ "agent.ip_addr,"
+ "agent.mac_addr,"
+ "agent.pc_name,"
+ "dept.name AS dept_name  "
+ "FROM partition_connect_log AS connect "
+ "INNER JOIN partition_log AS pl ON pl.no = connect.partition_log_no "
+ "INNER JOIN agent_log AS agent ON connect.agent_log_no = agent.no "
+ "INNER JOIN user_info AS ur ON ur.no = connect.user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = agent.dept_no ";
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
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!disk_guid.equals("")) 	pstmt.setString(i++, "%" + disk_guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PartitionConnectLogModel model = new PartitionConnectLogModel();
				model.setConnectNo(rs.getInt("connect_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setUserName(rs.getString("name"));
				model.setUserId(rs.getString("user_id"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setConnectServerTime(rs.getString("connect_server_time"));
				model.setConnectClientTime(rs.getString("connect_client_time"));
				model.setCreateServerTime(rs.getString("created_server_time"));
				model.setCreateClientTime(rs.getString("created_client_time"));
				model.setUpdateServerTime(rs.getString("update_server_time"));
				model.setUpdateClientTime(rs.getString("update_client_time"));

				model.setGuid(rs.getString("guid"));
				model.setDiskGuid(rs.getString("disk_guid"));
				model.setLabel(rs.getString("label"));
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
	
	public int getPartitionConnectLogListCount(HashMap<String, Object> map){

		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String user_number = map.get("user_number").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("duty").toString();
		String rank = map.get("rank").toString();
		String pc_name = map.get("pc_name").toString();

		String guid = map.get("guid").toString();
		String disk_guid = map.get("disk_guid").toString();
		String label = map.get("label").toString();
		
		
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

		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";

		if(!guid.equals("")) 		whereSql += "AND pl.guid LIKE ? ";
		if(!disk_guid.equals("")) 		whereSql += "AND pl.disk_guid LIKE ? ";
		if(!label.equals("")) 		whereSql += "AND pl.label LIKE ? ";

		if(!start_date.equals("")) 	whereSql += "AND connect.connect_client_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND connect.connect_client_time < ? + interval 1 day ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
				
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM partition_connect_log AS connect "
+ "INNER JOIN partition_log AS pl ON pl.no = connect.partition_log_no "
+ "INNER JOIN agent_log AS agent ON connect.agent_log_no = agent.no "
+ "INNER JOIN user_info AS ur ON ur.no = connect.user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = agent.dept_no ";
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
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");
			if(!guid.equals("")) 	pstmt.setString(i++, "%" + guid + "%");
			if(!disk_guid.equals("")) 	pstmt.setString(i++, "%" + disk_guid + "%");
			if(!label.equals("")) 	pstmt.setString(i++, "%" + label + "%");
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
	
	public List<FileEventLogModel> getRmvDiskFileLogList(HashMap<String, Object> map){
		List<FileEventLogModel> data = new ArrayList<FileEventLogModel>();
		
		String whereSql = "WHERE fevent.access_type = 2 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("user_duty").toString();
		String rank = map.get("user_rank").toString();
		String pc_name = map.get("pc_name").toString();

		String file_name = map.get("file_name").toString();
		
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

		if(!start_date.equals("")) 	whereSql += "AND fevent.send_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND fevent.send_server_time < ? + interval 1 day ";
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!file_name.equals("")) 		whereSql += "AND fevent.file_list LIKE ? ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "ORDER BY fevent.no DESC LIMIT ?, ? ";	
		
		String sql= 
"SELECT "
+ "fevent.no AS file_no, "
+ "ur.id AS user_id, "
+ "ur.name AS user_name, "
+ "ur.number AS user_number, "
+ "ifnull(ur.duty, '') AS duty, "
+ "ifnull(ur.rank, '') AS rank, "
+ "ifnull(agent.ip_addr, '') AS ip_addr, "
+ "ifnull(agent.mac_addr, '') AS mac_addr, "
+ "ifnull(agent.pc_name, '') AS pc_name, "
+ "dept.name AS dept_name, "
+ "ifnull(fevent.file_id, '') AS file_id, "
+ "ifnull(fevent.file_list, '') AS file_list, "
+ "ifnull(fevent.send_server_time, '') AS send_server_time, "
+ "ifnull(fevent.send_client_time, '') AS send_client_time, "
+ "ifnull(fevent.description, '') AS description "
+ "FROM file_event_log AS fevent "
+ "INNER JOIN agent_log AS agent ON fevent.agent_log_no = agent.no "
+ "INNER JOIN user_info AS ur ON ur.no = fevent.user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = agent.dept_no ";
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");

			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				FileEventLogModel model = new FileEventLogModel();
				model.setFileNo(rs.getInt("file_no"));
				model.setUserName(rs.getString("user_name"));
				model.setUserId(rs.getString("user_id"));
				model.setUserNo(rs.getString("user_number"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ip_addr"));
				model.setMacAddr(rs.getString("mac_addr"));
				model.setPcName(rs.getString("pc_name"));
				model.setDeptName(rs.getString("dept_name"));

				model.setFileId(rs.getString("file_id"));
				model.setFileList(rs.getString("file_list"));
				model.setDescription(rs.getString("description"));
				model.setServerTime(rs.getString("send_server_time"));
				model.setClientTime(rs.getString("send_client_time"));

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
	
	public int getRmvDiskFileLogListCount(HashMap<String, Object> map){
		int result = 0;
		
		String whereSql = "WHERE fevent.access_type = 2 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String duty = map.get("user_duty").toString();
		String rank = map.get("user_rank").toString();
		String pc_name = map.get("pc_name").toString();

		String file_name = map.get("file_name").toString();
		
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
			return 0;
		}
		if(oDept != null)			whereSql += "AND ur.dept_no in ("+idList+") ";
		if(!user_id.equals("")) 	whereSql += "AND ur.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ur.name LIKE ? ";

		if(!start_date.equals("")) 	whereSql += "AND fevent.send_server_time >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND fevent.send_server_time < ? + interval 1 day ";
		
		if(!duty.equals("")) 		whereSql += "AND ur.duty LIKE ? ";
		if(!rank.equals("")) 		whereSql += "AND ur.rank LIKE ? ";
		if(!pc_name.equals("")) 		whereSql += "AND agent.pc_name LIKE ? ";
		if(!file_name.equals("")) 		whereSql += "AND fevent.file_list LIKE ? ";

		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		whereSql += "";	
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM file_event_log AS fevent "
+ "INNER JOIN agent_log AS agent ON fevent.agent_log_no = agent.no "
+ "INNER JOIN user_info AS ur ON ur.no = fevent.user_no "
+ "INNER JOIN dept_info AS dept ON dept.no = agent.dept_no ";
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
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			if(!duty.equals("")) 		pstmt.setString(i++, "%" + duty + "%");
			if(!rank.equals("")) 		pstmt.setString(i++, "%" + rank + "%");
			if(!pc_name.equals("")) 	pstmt.setString(i++, "%" + pc_name + "%");

			if(!file_name.equals("")) 	pstmt.setString(i++, "%" + file_name + "%");
			
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
	
}
