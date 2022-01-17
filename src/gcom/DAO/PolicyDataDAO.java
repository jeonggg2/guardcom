package gcom.DAO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;
import gcom.Model.PolicyMessengerModel;
import gcom.Model.PolicyNetworkModel;
import gcom.Model.PolicyPatternModel;
import gcom.Model.PolicyProcessModel;
import gcom.Model.PolicyRequestInfo;
import gcom.Model.PolicySerialModel;
import gcom.Model.PolicyWebSiteBlocklModel;
import gcom.Model.UsbDevInfoModel;
import gcom.Model.UserPolicyLogModel;
import gcom.Model.UserPolicyModel;
import gcom.common.util.CommonUtil;
import gcom.common.util.ConfigInfo;
import gcom.common.util.StringUtil;
import gcom.service.common.CommonServiceImpl;
import gcom.service.common.ICommonService;



public class PolicyDataDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public PolicyDataDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	

	public int getUserPolicyLogListCount(HashMap<String, Object> map){
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
						+ "COUNT(*) AS cnt "
						+ "FROM policy_log AS policy "
						 + "INNER JOIN agent_log AS agent ON agent.no = policy.agent_log_no "
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
	
	public List<UserPolicyLogModel> getUserPolicyLogList(HashMap<String, Object> map){
		List<UserPolicyLogModel> data = new ArrayList<UserPolicyLogModel>();
		
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
+ "policy.no AS policy_no, "
+ "ifnull(policy.apply_time, '') AS apply_time, "
+ "ifnull(policy.request_server_time, '' ) AS request_server_time, "
+ "ifnull(policy.request_client_time, '' ) AS request_client_time, "
+ "policy.uninstall_enabled AS uninstall_enabled, "
+ "policy.file_encryption_enabled AS file_encryption_enabled, "
+ "policy.cd_encryption_enabled AS cd_encryption_enabled, "
+ "policy.printer_enabled AS printer_enabled, "
+ "policy.cd_enabled AS cd_enabled, "
+ "policy.cd_export_enabled AS cd_export_enabled, "
+ "policy.wlan_enabled AS wlan_enabled, "
+ "policy.net_share_enabled AS net_share_enabled, "
+ "policy.web_export_enabled AS web_export_enabled, "
+ "policy.removal_storage_export_enabled AS removal_storage_export_enabled, "
+ "policy.removal_storage_admin_mode AS removal_storage_admin_mode, "
+ "policy.usb_dev_list AS usb_dev_list, "
+ "policy.com_port_list AS com_port_list, "
+ "policy.net_port_list AS net_port_list, "
+ "policy.process_list AS process_list, "
+ "policy.file_pattern_list AS file_pattern_list, "
+ "policy.web_addr_list AS web_addr_list, "
+ "policy.watermark_descriptor AS watermark_descriptor, "
+ "policy.print_log_descriptor AS print_log_descriptor, "
+ "policy.quarantine_path_access_code AS quarantine_path_access_code, "
+ "policy.pattern_file_control AS pattern_file_control, "

+ "policy.sensitive_dir_enabled AS sensitive_dir_enabled, "
+ "policy.policy_sensitive_file_access AS policy_sensitive_file_access, "
+ "policy.policy_usb_control_enabled AS policy_usb_control_enabled, "

+ "IFNULL(policy.usb_dev_list, 'N') as isUsbBlock, "
+ "IFNULL(policy.com_port_list, 'N') as isComPortBlock, "
+ "IFNULL(policy.net_port_list, 'N') as isNetPortBlock, "
+ "IFNULL(policy.process_list, '') as isProcessList, "
+ "IFNULL(policy.file_pattern_list, '') as isFilePattern, "
+ "IFNULL(policy.web_addr_list, 'N') as isWebAddr, "
+ "IFNULL(policy.msg_block_list, 'N') as isMsgBlock, "
+ "IFNULL(policy.watermark_descriptor, 'N') as isWatermark, "
+ "IFNULL(policy.print_log_descriptor, 0) as printLogDesc, "
+ "IFNULL(policy.pattern_file_control, 0) as patternFileControl, "

+ "ur.number AS user_no, "
+ "ur.id AS user_id, "
+ "ifnull(ur.name , '' ) AS user_name, "
+ "ur.dept_no, ur.duty, "
+ "ifnull(ur.rank, '' ) AS rank, "
+ "ifnull(agent.ip_addr, '' ) AS ip_addr, "
+ "ifnull(agent.mac_addr, '' ) AS mac_addr, "
+ "ifnull(agent.pc_name, '' ) AS pc_name,  " 
+ "dept.name AS dept_name "
+ "FROM policy_log AS policy "
+ "INNER JOIN agent_log AS agent ON agent.no = policy.agent_log_no "
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
				UserPolicyLogModel model = new UserPolicyLogModel();
				model.setPolicyNo(rs.getInt("policy_no"));
				model.setUserNo(rs.getString("user_no"));
				model.setRequestServerTime(rs.getString("request_server_time"));
				model.setRequestClientTime(rs.getString("request_client_time"));
				model.setApplyTime(rs.getString("apply_time"));				
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

				model.setIsSensitiveDirEnabled(rs.getInt("sensitive_dir_enabled"));
				model.setIsSensitiveFileAccess(rs.getInt("policy_sensitive_file_access"));
				model.setIsUsbControlEnabled(rs.getInt("policy_usb_control_enabled"));
				
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
	
	public List<PolicyMessengerModel> getPolicyMessengerList(HashMap<String, Object> map) {
		List<PolicyMessengerModel> data = new ArrayList<PolicyMessengerModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT " 
					+ "no as msg_no, "
					+ "name as msg_name, "
					+ "process_name, "
					+ "txt_log, "
					+ "txt_block, "
					+ "file_log, "
					+ "file_block "
			    + "FROM msg_info WHERE 1 = 1 ";
			    
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//�޽�����
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//���ϸ�
				sql += "AND process_name LIKE ? ";
			} 
		}	    
			    
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicyMessengerModel model = new PolicyMessengerModel();
				model.setMsgNo(rs.getInt("msg_no"));
				model.setMsgName(rs.getString("msg_name"));
				model.setProcessName(rs.getString("process_name"));
				model.setTxtLog(rs.getInt("txt_log"));
				model.setTxtBlock(rs.getInt("txt_block"));
				model.setFileLog(rs.getInt("file_log"));
				model.setFileBlock(rs.getInt("file_block"));

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

	public int getPolicyMessengerListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM msg_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//�޽�����
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//���ϸ�
				sql += "AND process_name LIKE ? ";
			} 
		}	
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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
	
	public List<PolicyProcessModel> getPolicyProcessList(HashMap<String, Object> map) {
		List<PolicyProcessModel> data = new ArrayList<PolicyProcessModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as pro_no, "
					+ "process_name, "
					+ "IFNULL(process_path,'') as process_path, "
					+ "IFNULL(hash, '') as hash, "
					+ "notice, "
					+ "valid "
				+ "FROM process_info WHERE 1 = 1 ";
				
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//���μ����̸�
				sql += "AND process_name LIKE ? ";
			} else if (search_type == 2) {	//���μ������
				sql += "AND process_path LIKE ? ";
			}
		}
		
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicyProcessModel model = new PolicyProcessModel();
				model.setProNo(rs.getInt("pro_no"));
				model.setProcessName(rs.getString("process_name"));
				model.setProcessPath(rs.getString("process_path"));
				model.setHash(rs.getString("hash"));
				model.setNotice(rs.getString("notice"));
				model.setValid(rs.getInt("valid"));

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
	
	public int getPolicyProcessListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM process_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//���μ����̸�
				sql += "AND process_name LIKE ? ";
			} else if (search_type == 2) {	//���μ������
				sql += "AND process_path LIKE ? ";
			}
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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
	
	public List<HashMap<String, Object>> getPolicyPatternSimpleList() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		String sql =
				"SELECT "
					+ "no as pattern_no, "
					+ "description as pattern_name "
				+ "FROM pattern_info "
			    + "WHERE valid = 1 ORDER BY no desc  ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, Object> model = new HashMap<String, Object>();
				model.put("pattern_no", rs.getInt("pattern_no"));
				model.put("pattern_name", rs.getString("pattern_name"));

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
	
	public List<PolicyPatternModel> getPolicyPatternList(HashMap<String, Object> map) {
		List<PolicyPatternModel> data = new ArrayList<PolicyPatternModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as pat_no, "
					+ "description as pat_name, "
					+ "IFNULL(data, '') as data, "
					+ "notice, "
					+ "valid "
				+ "FROM pattern_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//�����̸�
				sql += "AND description LIKE ? ";
			} else if (search_type == 2) {	//���ϵ�����
				sql += "AND data LIKE ? ";
			} 
		}
				
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicyPatternModel model = new PolicyPatternModel();
				model.setPatNo(rs.getInt("pat_no"));
				model.setPatName(rs.getString("pat_name"));
				model.setData(rs.getString("data"));
				model.setNotice(rs.getString("notice"));
				model.setValid(rs.getInt("valid"));

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
	
	public int getPolicyPatternListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM pattern_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//�����̸�
				sql += "AND description LIKE ? ";
			} else if (search_type == 2) {	//���ϵ�����
				sql += "AND data LIKE ? ";
			} 
		}
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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
	
	public List<PolicyNetworkModel> getPolicyNetworkList(HashMap<String, Object> map) {
		List<PolicyNetworkModel> data = new ArrayList<PolicyNetworkModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as net_no, "
					+ "name as net_name, "
					+ "port, "
					+ "descriptor, "
					+ "allow "
				+ "FROM net_port_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��Ʈ�̸�
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//��Ʈ
				sql += "AND port LIKE ? ";
			}
		}
		
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicyNetworkModel model = new PolicyNetworkModel();
				model.setNetNo(rs.getInt("net_no"));
				model.setNetName(rs.getString("net_name"));
				model.setPort(rs.getString("port"));
				model.setDescriptor(rs.getString("descriptor"));
				model.setAllow(rs.getInt("allow"));

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
	
	public int getPolicyNetworkListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM net_port_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��Ʈ�̸�
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//��Ʈ
				sql += "AND port LIKE ? ";
			}
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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
	
	public List<PolicySerialModel> getPolicySerialList(HashMap<String, Object> map) {
		List<PolicySerialModel> data = new ArrayList<PolicySerialModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as serial_no, "
					+ "name as serial_name, "
					+ "allow, "
					+ "description "
				+ "FROM com_port_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��Ʈ�̸�
				sql += "AND name LIKE ? ";
			}
		}
				
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicySerialModel model = new PolicySerialModel();
				model.setSerialNo(rs.getInt("serial_no"));
				model.setSerialName(rs.getString("serial_name"));
				model.setDescription(rs.getString("description"));
				model.setAllow(rs.getInt("allow"));

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
	
	public int getPolicySerialListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM com_port_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��Ʈ�̸�
				sql += "AND name LIKE ? ";
			}
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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


	public List<UserPolicyModel> getPolicyAssignMemberList(HashMap<String, Object> map) {
		List<UserPolicyModel> data = new ArrayList<UserPolicyModel>();
		
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
		
		whereSql += "ORDER BY ui.no DESC";	
		
		if (map.get("startRow") != null) {
			whereSql += " LIMIT ?, ? ";	
		}
		
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
			if (map.get("startRow") != null) {
				pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
				pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserPolicyModel model = new UserPolicyModel();
				model.setAgentNo(rs.getInt("agentNo"));
				model.setUserNo(rs.getString("userNo"));
				model.setUno(rs.getInt("uno"));
				model.setPolicyNo(rs.getInt("policyNo"));
				model.setDeptId(rs.getInt("deptId"));
				model.setUserId(rs.getString("userId"));
				model.setUserName(rs.getString("userName"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setIpAddr(rs.getString("ipAddr"));
				model.setMacAddr(rs.getString("macAddr"));
				model.setPcName(rs.getString("pcName"));
				model.setDeptName(rs.getString("deptName"));
				model.setUserNumber(rs.getString("number"));
				
				model.setIsUninstall(rs.getInt("isUninstall"));
				model.setIsFileEncryption(rs.getInt("isFileEncryption"));
				model.setIsCdEncryption(rs.getInt("isCdEncryption"));
				model.setIsPrint(rs.getInt("isPrint"));
				model.setIsCdEnabled(rs.getInt("isCdEnabled"));
				model.setIsCdExport(rs.getInt("isCdExport"));
				model.setIsWlan(rs.getInt("isWlan"));
				model.setIsNetShare(rs.getInt("isNetShare"));
				model.setIsWebExport(rs.getInt("isWebExport"));
				
				model.setIsSensitiveDirEnabled(rs.getInt("sensitive_dir_enabled"));
				model.setIsSensitiveFileAccess(rs.getInt("policy_sensitive_file_access"));
				model.setIsUsbControlEnabled(rs.getInt("policy_usb_control_enabled"));
				
				model.setIsStorageExport(rs.getInt("isStorageExport"));
				model.setIsStorageAdmin(rs.getInt("isStorageAdmin"));
				
				model.setIsUsbBlock(rs.getString("isUsbBlock"));
				model.setIsComPortBlock(rs.getString("isComPortBlock"));
				model.setIsNetPortBlock(rs.getString("isNetPortBlock"));
				model.setIsProcessList(rs.getString("isProcessList"));
				model.setIsFilePattern(rs.getString("isFilePattern"));
				model.setIsWebAddr(rs.getString("isWebAddr"));
				model.setIsMsgBlock(rs.getString("isMsgBlock"));
				model.setIsWatermark(rs.getString("isWatermark"));
				model.setPrintLogDesc(rs.getInt("printLogDesc"));
				model.setQuarantinePathAccessCode(rs.getString("quarantinePathAccessCode"));
				model.setPatternFileControl(rs.getInt("patternFileControl"));
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Class<?> clazz = model.getClass();
				String value;
				Method method;
				for (int ci = 1; ci <= columnCount; ci++) {					
					if (rsmd.getColumnLabel(ci).indexOf("_temporarily") != -1) {						
						try {
							method = model.getClass().getMethod("set".concat(StringUtil.capitalize(rsmd.getColumnLabel(ci))), String.class);
							try {
								value = rs.getString(rsmd.getColumnLabel(ci));
								if (value == null) {
									value = "";
								}
								method.invoke(model, value);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
					
				}
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


	public int getPolicyAssignMemberListCount(HashMap<String, Object> map) {
		int result = 0;
		
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
			return result;
		}

		if(!user_id.equals("")) 	whereSql += "AND ui.id LIKE ? ";
		if(!user_name.equals("")) 	whereSql += "AND ui.name LIKE ? ";
		
		if(!user_phone.equals("")) whereSql += "AND ui.phone LIKE ? ";
		if(!user_duty.equals("")) whereSql += "AND ui.duty LIKE ? ";
		if(!user_rank.equals("")) whereSql += "AND ui.rank LIKE ? ";
		if(!user_number.equals("")) whereSql += "AND ui.number LIKE ? ";
		if(!user_pc.equals("")) whereSql += "AND ai.pc_name LIKE ? ";
		if(!user_ip.equals("")) whereSql += "AND ai.ip_addr LIKE ? ";
	
		if(oDept != null)			whereSql += "AND ai.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
				
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM agent_info AS ai "
						+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
						+ "LEFT JOIN policy_info AS pi ON ai.policy_no = pi.no ";

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
+ "FROM policy_request_info AS request "
+ "INNER JOIN user_info AS ur ON ur.no = request.user_no "
+ "INNER JOIN agent_info AS agent ON agent.no = request.agent_no "
+ "INNER JOIN policy_info As policy ON agent.policy_no = policy.no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";

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
	
	public List<PolicyRequestInfo> getRequestedPolicyList(HashMap<String, Object> map){
		List<PolicyRequestInfo> data = new ArrayList<PolicyRequestInfo>();
		
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
+ "request.new_policy_uninstall_enabled, "
+ "request.new_policy_file_encryption_enabled, "
+ "request.new_policy_cd_encryption_enabled, "
+ "request.new_policy_printer_enabled, "
+ "request.new_policy_cd_enabled, "
+ "request.new_policy_cd_export_enabled, "
+ "request.new_policy_wlan_enabled, "
+ "request.new_policy_net_share_enabled, "
+ "request.new_policy_web_export_enabled, "

+ "request.new_sensitive_dir_enabled, "
+ "request.new_policy_sensitive_file_access, "
+ "request.new_policy_usb_control_enabled, "

+ "request.new_policy_removal_storage_export_enabled, "
+ "request.new_policy_removal_storage_admin_mode, "
+ "request.new_policy_usb_dev_list, "
+ "request.new_policy_com_port_list, "
+ "request.new_policy_net_port_list, "
+ "request.new_policy_process_list, "
+ "request.new_policy_file_pattern_list, "
+ "request.new_policy_web_addr_list, "
+ "request.new_policy_msg_block_list, "
+ "request.new_policy_watermark_descriptor, "
+ "request.new_policy_print_log_descriptor, "
+ "request.new_policy_quarantine_path_access_code, "
+ "request.new_policy_pattern_file_control, "
+ "request.notice, "
+ "request.request_server_time, "
+ "IFNULL(request.request_client_time, '') AS request_client_time, "
+ "request.permit, "
+ "IFNULL(request.permit_date, '') as permit_date, "
+ "IFNULL(request.permit_admin_id, '') as permit_admin_id, "
+ "policy.no as policy_no, "
+ "policy.uninstall_enabled AS uninstall_enabled, "
+ "policy.file_encryption_enabled AS file_encryption_enabled, "
+ "policy.cd_encryption_enabled, policy.printer_enabled, "
+ "policy.cd_enabled, "
+ "policy.cd_export_enabled, "
+ "policy.wlan_enabled, "
+ "policy.net_share_enabled, "
+ "policy.web_export_enabled, "

+ "policy.sensitive_dir_enabled, "
+ "policy.policy_sensitive_file_access, "
+ "policy.policy_usb_control_enabled, "

+ "policy.removal_storage_export_enabled, "
+ "policy.removal_storage_admin_mode, "
+ "policy.usb_dev_list, "
+ "policy.com_port_list, "
+ "policy.net_port_list, "
+ "policy.process_list, "
+ "policy.file_pattern_list, "
+ "policy.web_addr_list, "
+ "policy.msg_block_list, "
+ "policy.watermark_descriptor, "
+ "policy.print_log_descriptor, "
+ "policy.quarantine_path_access_code, "
+ "policy.pattern_file_control, "
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
+ "FROM policy_request_info AS request "
+ "INNER JOIN user_info AS ur ON ur.no = request.user_no "
+ "INNER JOIN agent_info AS agent ON agent.no = request.agent_no "
+ "INNER JOIN policy_info As policy ON agent.policy_no = policy.no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no ";

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
				PolicyRequestInfo model = new PolicyRequestInfo();
				UserPolicyModel p_model = new UserPolicyModel();

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
				model.setUserNumber(rs.getString("user_no"));
				model.setReqNotice(rs.getString("notice"));
				model.setPermitState(rs.getString("permit"));
				model.setPermitDate(rs.getString("permit_date"));
				model.setPermitStaf(rs.getString("permit_admin_id"));
				model.setReqClientTime(rs.getString("request_client_time"));
				
				model.setIsUninstall(rs.getInt("new_policy_uninstall_enabled"));
				model.setIsFileEncryption(rs.getInt("new_policy_file_encryption_enabled"));
				model.setIsCdEncryption(rs.getInt("new_policy_cd_encryption_enabled"));
				model.setIsPrint(rs.getInt("new_policy_printer_enabled"));
				model.setIsCdEnabled(rs.getInt("new_policy_cd_enabled"));
				model.setIsCdExport(rs.getInt("new_policy_cd_export_enabled"));
				model.setIsWlan(rs.getInt("new_policy_wlan_enabled"));
				model.setIsNetShare(rs.getInt("new_policy_net_share_enabled"));
				model.setIsWebExport(rs.getInt("new_policy_web_export_enabled"));
				
				model.setIsSensitiveDirEnabled(rs.getInt("new_sensitive_dir_enabled"));
				model.setIsSensitiveFileAccess(rs.getInt("new_policy_sensitive_file_access"));
				model.setIsUsbControlEnabled(rs.getInt("new_policy_usb_control_enabled"));
				
				model.setIsStorageExport(rs.getInt("new_policy_removal_storage_export_enabled"));
				model.setIsStorageAdmin(rs.getInt("new_policy_removal_storage_admin_mode"));
				
				model.setIsUsbBlock(rs.getString("new_policy_usb_dev_list"));
				model.setIsComPortBlock(rs.getString("new_policy_com_port_list"));
				model.setIsNetPortBlock(rs.getString("new_policy_net_port_list"));
				model.setIsProcessList(rs.getString("new_policy_process_list"));
				model.setIsFilePattern(rs.getString("new_policy_file_pattern_list"));
				model.setIsWebAddr(rs.getString("new_policy_web_addr_list"));
				model.setIsMsgBlock(rs.getString("new_policy_msg_block_list"));
				model.setIsWatermark(rs.getString("new_policy_watermark_descriptor"));
				model.setPrintLogDesc(rs.getInt("new_policy_print_log_descriptor"));
				model.setPatternFileControl(rs.getInt("new_policy_pattern_file_control"));

				p_model.setAgentNo(rs.getInt("agent_no"));
				p_model.setPolicyNo(rs.getInt("policy_no"));
				p_model.setIsUninstall(rs.getInt("uninstall_enabled"));
				p_model.setIsFileEncryption(rs.getInt("file_encryption_enabled"));
				p_model.setIsCdEncryption(rs.getInt("cd_encryption_enabled"));
				p_model.setIsPrint(rs.getInt("printer_enabled"));
				p_model.setIsCdEnabled(rs.getInt("cd_enabled"));
				p_model.setIsCdExport(rs.getInt("cd_export_enabled"));
				p_model.setIsWlan(rs.getInt("wlan_enabled"));
				p_model.setIsNetShare(rs.getInt("net_share_enabled"));
				p_model.setIsWebExport(rs.getInt("web_export_enabled"));
				
				p_model.setIsSensitiveDirEnabled(rs.getInt("sensitive_dir_enabled"));
				p_model.setIsSensitiveFileAccess(rs.getInt("policy_sensitive_file_access"));
				p_model.setIsUsbControlEnabled(rs.getInt("policy_usb_control_enabled"));
				
				p_model.setIsStorageExport(rs.getInt("removal_storage_export_enabled"));
				p_model.setIsStorageAdmin(rs.getInt("removal_storage_admin_mode"));
				p_model.setIsUsbBlock(rs.getString("usb_dev_list"));
				p_model.setIsComPortBlock(rs.getString("com_port_list"));
				p_model.setIsNetPortBlock(rs.getString("net_port_list"));
				p_model.setIsProcessList(rs.getString("process_list"));
				p_model.setIsFilePattern(rs.getString("file_pattern_list"));
				p_model.setIsWebAddr(rs.getString("web_addr_list"));
				p_model.setIsMsgBlock(rs.getString("msg_block_list"));
				p_model.setIsWatermark(rs.getString("watermark_descriptor"));
				p_model.setPrintLogDesc(rs.getInt("print_log_descriptor"));
				p_model.setPatternFileControl(rs.getInt("pattern_file_control"));

				model.setOldPolicy(p_model);

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


	public List<UsbDevInfoModel> getPolicyUsbBlockList(HashMap<String, Object> map) {
		List<UsbDevInfoModel> data = new ArrayList<UsbDevInfoModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as usb_id, "
					+ "name as usb_name, "
					+ "vid, "
					+ "pid, "
					+ "serial_number, "
					+ "allow, "
					+ "class, "
					+ "subclass, "
					+ "protocol, "
					+ "allow, "
					+ "description "

					+ "FROM usb_dev_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��ġ��
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//���� �ĺ���
				sql += "AND vid LIKE ? ";
			} else if (search_type == 3) {	// ��ǰ �ĺ���
				sql += "AND pid LIKE ? ";
			} else if (search_type == 4) {	//�Ϸ� ��ȣ
				sql += "AND serial_number LIKE ? ";
			}
		}
		
		
		sql	+= "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UsbDevInfoModel model = new UsbDevInfoModel();
				model.setUsbId(rs.getInt("usb_id"));
				model.setName(rs.getString("usb_name"));
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


	public int getPolicyUsbBlockListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM usb_dev_info WHERE 1 = 1 ";
		
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//��ġ��
				sql += "AND name LIKE ? ";
			} else if (search_type == 2) {	//���� �ĺ���
				sql += "AND vid LIKE ? ";
			} else if (search_type == 3) {	// ��ǰ �ĺ���
				sql += "AND pid LIKE ? ";
			} else if (search_type == 4) {	//�Ϸ� ��ȣ
				sql += "AND serial_number LIKE ? ";
			}
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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


	public List<PolicyWebSiteBlocklModel> getPolicyWebSiteBlockList(HashMap<String, Object> map) {
		List<PolicyWebSiteBlocklModel> data = new ArrayList<PolicyWebSiteBlocklModel>();
		
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql =
				"SELECT "
					+ "no as site_id, "
					+ "address, "
					+ "allow, "
					+ "description "
				+ "FROM web_addr_info WHERE 1 = 1 ";
				
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//����Ʈ�ּ�
				sql += "AND address LIKE ? ";
			} else if (search_type == 2) {	//����
				sql += "AND description LIKE ? ";
			} 
		}
			    
		sql += "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				PolicyWebSiteBlocklModel model = new PolicyWebSiteBlocklModel();
				model.setSiteId(rs.getInt("site_id"));
				model.setAddress(rs.getString("address"));
				model.setAllow(rs.getInt("allow"));
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


	public int getPolicyWebSiteBlockListCount(HashMap<String, Object> map) {
		int result = 0;
		int search_type = Integer.parseInt(map.get("search_type").toString());
		String search_text = map.get("search_text").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM web_addr_info WHERE 1 = 1 ";
			
		if(!"".equals(search_text) && search_text.length() > 0){
			if(search_type == 1) {	//����Ʈ�ּ�
				sql += "AND address LIKE ? ";
			} else if (search_type == 2) {	//����
				sql += "AND description LIKE ? ";
			} 
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text) && search_text.length() > 0){
				pstmt.setString(1, "%" + search_text + "%");
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


	public PolicyMessengerModel getMsgInfo(int code) {
		PolicyMessengerModel model = new PolicyMessengerModel();
		int msgNo = code;
		
		String sql =
				"SELECT " 
					+ "no as msg_no, "
					+ "name as msg_name, "
					+ "process_name, "
					+ "txt_log, "
					+ "txt_block, "
					+ "file_log, "
					+ "file_block "
			    + "FROM msg_info "
			    + "WHERE no = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  msgNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setMsgNo(rs.getInt("msg_no"));
				model.setMsgName(rs.getString("msg_name"));
				model.setProcessName(rs.getString("process_name"));
				model.setTxtLog(rs.getInt("txt_log"));
				model.setTxtBlock(rs.getInt("txt_block"));
				model.setFileLog(rs.getInt("file_log"));
				model.setFileBlock(rs.getInt("file_block"));
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


	public HashMap<String, Object> insertPolicyMsgSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String msgName = map.get("msg_name").toString();
		String msgProName = map.get("msg_pro_name").toString();
		int txt_log = Integer.parseInt(map.get("txt_log").toString());
		int txt_block = Integer.parseInt(map.get("txt_block").toString());
		int file_log = Integer.parseInt(map.get("file_log").toString());
		int file_block = Integer.parseInt(map.get("file_block").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO msg_info (name, process_name, txt_log, txt_block, file_log, file_block) VALUES (?, ?, ?, ?, ?, ?)";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, msgName);
			pstmt.setString(2, msgProName);
			pstmt.setInt(3, txt_log);
			pstmt.setInt(4, txt_block);
			pstmt.setInt(5, file_log);
			pstmt.setInt(6, file_block);
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


	public HashMap<String, Object> updatePolicyMsgUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int msgNo = Integer.parseInt(map.get("msg_no").toString());
		String msgName = map.get("msg_name").toString();
		String msgProName = map.get("msg_pro_name").toString();
		int txt_log = Integer.parseInt(map.get("txt_log").toString());
		int txt_block = Integer.parseInt(map.get("txt_block").toString());
		int file_log = Integer.parseInt(map.get("file_log").toString());
		int file_block = Integer.parseInt(map.get("file_block").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE msg_info  SET name = ?, process_name = ?, txt_log = ?, txt_block = ?, file_log = ?, file_block = ? WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, msgName);
			pstmt.setString(2, msgProName);
			pstmt.setInt(3, txt_log);
			pstmt.setInt(4, txt_block);
			pstmt.setInt(5, file_log);
			pstmt.setInt(6, file_block);
			pstmt.setInt(7, msgNo);
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


	public PolicyProcessModel getProcessInfo(int code) {
		PolicyProcessModel model = new PolicyProcessModel();
		int proNo = code;
		
		String sql =
				"SELECT "
					+ "no as pro_no, "
					+ "process_name, "
					+ "IFNULL(process_path,'') as process_path, "
					+ "IFNULL(hash, '') as hash, "
					+ "notice, "
					+ "valid "
				+ "FROM process_info "
				+ "WHERE no = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  proNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setProNo(rs.getInt("pro_no"));
				model.setProcessName(rs.getString("process_name"));
				model.setProcessPath(rs.getString("process_path"));
				model.setHash(rs.getString("hash"));
				model.setNotice(rs.getString("notice"));
				model.setValid(rs.getInt("valid"));

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


	public HashMap<String, Object> insertPolicyProcessSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String proName = map.get("pro_name").toString();
		String proPath = map.get("pro_path").toString();
		String hash = map.get("hash").toString();
		String notice = map.get("notice").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO process_info (process_name, process_path, hash, notice, valid) VALUES (?, ?, ?, ?, ?)";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, proName);
			pstmt.setString(2, proPath);
			pstmt.setString(3, hash);
			pstmt.setString(4, notice);
			pstmt.setInt(5, valid);
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


	public HashMap<String, Object> updatePolicyProcessUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int proNo = Integer.parseInt(map.get("pro_no").toString());
		String proName = map.get("pro_name").toString();
		String proPath = map.get("pro_path").toString();
		String hash = map.get("hash").toString();
		String notice = map.get("notice").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE process_info SET process_name = ?, process_path = ?, hash = ?, notice = ?, valid = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, proName);
			pstmt.setString(2, proPath);
			pstmt.setString(3, hash);
			pstmt.setString(4, notice);
			pstmt.setInt(5, valid);
			pstmt.setInt(6, proNo);
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


	public PolicyPatternModel getPatternInfo(int code) {
		PolicyPatternModel model = new PolicyPatternModel();
		int patId = code;
		
		String sql =
				"SELECT "
					+ "no as pat_no, "
					+ "description as pat_name, "
					+ "IFNULL(data, '') as data, "
					+ "notice, "
					+ "valid "
				+ "FROM pattern_info "
				+ "WHERE no = ?";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, patId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setPatNo(rs.getInt("pat_no"));
				model.setPatName(rs.getString("pat_name"));
				model.setData(rs.getString("data"));
				model.setNotice(rs.getString("notice"));
				model.setValid(rs.getInt("valid"));
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


	public HashMap<String, Object> insertPolicyPatternSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String patName = map.get("pat_name").toString();
		String patData = map.get("pat_data").toString();
		String notice = map.get("notice").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO pattern_info (description, data, notice, valid) VALUES (?, ?, ?, ?)";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, patName);
			pstmt.setString(2, patData);
			pstmt.setString(3, notice);
			pstmt.setInt(4, valid);
			pstmt.executeUpdate();
			ResultSet rsKeys = pstmt.getGeneratedKeys();
			if (rsKeys.next()) {
				int no = rsKeys.getInt(1);
				rsKeys.close();
				pstmt.close();
				sql = "UPDATE pattern_info SET id = no WHERE no = ? ";
				pstmt=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, no);
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


	public HashMap<String, Object> updatePolicyPatternUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int patNo = Integer.parseInt(map.get("pat_no").toString());
		String patName = map.get("pat_name").toString();
		String patData = map.get("pat_data").toString();
		String notice = map.get("notice").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE pattern_info SET description = ?, data = ?, notice = ?, valid = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, patName);
			pstmt.setString(2, patData);
			pstmt.setString(3, notice);
			pstmt.setInt(4, valid);
			pstmt.setInt(5, patNo);
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


	public PolicyNetworkModel getNetworkInfo(int code) {
		PolicyNetworkModel model = new PolicyNetworkModel();
		int netId = code;
		
		String sql =
				"SELECT "
					+ "no as net_no, "
					+ "name as net_name, "
					+ "port, "
					+ "descriptor, "
					+ "allow "
				+ "FROM net_port_info "
				+ "WHERE no = ?";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  netId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				model.setNetNo(rs.getInt("net_no"));
				model.setNetName(rs.getString("net_name"));
				model.setPort(rs.getString("port"));
				model.setDescriptor(rs.getString("descriptor"));
				model.setAllow(rs.getInt("allow"));

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


	public HashMap<String, Object> insertPolicyNetworkSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String netName = map.get("net_name").toString();
		String netPort = map.get("net_port").toString();
		String descript = map.get("descript").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO net_port_info (name, port, descriptor, allow) VALUES (?, ?, ?, ?) ";
		
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, netName);
			pstmt.setString(2, netPort);
			pstmt.setString(3, descript);
			pstmt.setInt(4, valid);
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


	public HashMap<String, Object> updatePolicyNetworkUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int netNo = Integer.parseInt(map.get("net_no").toString());
		String netName = map.get("net_name").toString();
		String netPort = map.get("net_port").toString();
		String descript = map.get("descript").toString();
		int valid = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE net_port_info SET name = ?, port = ?, descriptor = ?, allow = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, netName);
			pstmt.setString(2, netPort);
			pstmt.setString(3, descript);
			pstmt.setInt(4, valid);
			pstmt.setInt(5, netNo);
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


	public PolicySerialModel getSerialInfo(int code) {
		PolicySerialModel model = new PolicySerialModel();
		int serialId = code;
		
		String sql =
				"SELECT "
					+ "no as serial_no, "
					+ "name as serial_name, "
					+ "allow, "
					+ "description "
				+ "FROM com_port_info "
				+ "WHERE no = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  serialId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setSerialNo(rs.getInt("serial_no"));
				model.setSerialName(rs.getString("serial_name"));
				model.setDescription(rs.getString("description"));
				model.setAllow(rs.getInt("allow"));

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


	public HashMap<String, Object> insertPolicySerialSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String serialName = map.get("serial_name").toString();
		String descript = map.get("descript").toString();
		int allow = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO com_port_info (name, allow, description) VALUES (?, ?, ?) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, serialName);
			pstmt.setInt(2, allow);
			pstmt.setString(3, descript);
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


	public HashMap<String, Object> updatePolicySerialUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int serialNo = Integer.parseInt(map.get("serial_no").toString());
		String serialName = map.get("serial_name").toString();
		String descript = map.get("descript").toString();
		int allow = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE com_port_info SET name = ?, allow = ?, description = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, serialName);
			pstmt.setInt(2, allow);
			pstmt.setString(3, descript);
			pstmt.setInt(4, serialNo);
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


	public PolicyWebSiteBlocklModel getWebsiteInfo(int code) {
		PolicyWebSiteBlocklModel model = new PolicyWebSiteBlocklModel();
		int siteId = code;
		
		String sql =
				"SELECT "
					+ "no as site_id, "
					+ "address, "
					+ "allow, "
					+ "description "
				+ "FROM web_addr_info "
				+ "WHERE no = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  siteId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setSiteId(rs.getInt("site_id"));
				model.setAddress(rs.getString("address"));
				model.setAllow(rs.getInt("allow"));
				model.setDescription(rs.getString("description"));
				
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


	public HashMap<String, Object> insertPolicyWebsiteSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String siteAddress = map.get("site_address").toString();
		String descript = map.get("descript").toString();
		int allow = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO web_addr_info (address, allow, description) VALUES (?, ?, ?) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, siteAddress);
			pstmt.setInt(2, allow);
			pstmt.setString(3, descript);
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


	public HashMap<String, Object> updatePolicyWebsiteUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int siteNo = Integer.parseInt(map.get("site_no").toString());
		String siteAddress = map.get("site_address").toString();
		String descript = map.get("descript").toString();
		int allow = Integer.parseInt(map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE web_addr_info SET address = ?, allow = ?, description = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, siteAddress);
			pstmt.setInt(2, allow);
			pstmt.setString(3, descript);
			pstmt.setInt(4, siteNo);
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


	public UsbDevInfoModel getUsbInfo(int code) {
		UsbDevInfoModel model = new UsbDevInfoModel();
		int usbId = code;
		
		String sql =
				"SELECT "
					+ "no as usb_id, "
					+ "name as usb_name, "
					+ "vid, "
					+ "pid, "
					+ "serial_number, "
					+ "allow, "
					+ "description, "
					+ "class, "
					+ "subclass, "
					+ "protocol, "
					+ "compare_type "
					+ "FROM usb_dev_info "
				+ "WHERE no = ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  usbId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				model.setUsbId(rs.getInt("usb_id"));
				model.setName(rs.getString("usb_name"));
				model.setVid(rs.getString("vid"));
				model.setPid(rs.getString("pid"));
				model.setAllow(rs.getInt("allow") == 1 ? true : false);
				model.setMainclass(rs.getString("class"));
				model.setProtocol(rs.getString("protocol"));
				model.setSubclass(rs.getString("subclass"));
				model.setCompareType(rs.getString("compare_type"));
				
				model.setSerialNumber(rs.getString("serial_number"));
				model.setDescription(rs.getString("description"));
				
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


	public HashMap<String, Object> insertPolicyUsbSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String usbName = map.get("usb_name").toString();
		String vid = map.get("vid").toString();
		String pid = map.get("pid").toString();
		String serial = map.get("serial").toString();

		String mainclass = map.get("mainclass").toString();
		String subclass = map.get("subclass").toString();
		String protocol = map.get("protocol").toString();
		String compare = map.get("compare").toString();

		String descript = map.get("descript").toString();
		int allow = Integer.parseInt( map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO usb_dev_info (name, vid, pid, serial_number, allow, description, class,subclass,protocol,compare_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, usbName);
			pstmt.setString(2, vid);
			pstmt.setString(3, pid);
			pstmt.setString(4, serial);
			pstmt.setInt(5, allow);
			pstmt.setString(6, descript);
			pstmt.setString(7, mainclass);
			pstmt.setString(8, subclass);
			pstmt.setString(9, protocol);
			pstmt.setString(10, compare);
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


	public HashMap<String, Object> updatePolicyUsbUpdate(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int usbNo = Integer.parseInt(map.get("usb_no").toString());
		String usbName = map.get("usb_name").toString();
		String vid = map.get("vid").toString();
		String pid = map.get("pid").toString();
		String serial = map.get("serial").toString();
		String descript = map.get("descript").toString();
		String mainclass = map.get("mainclass").toString();
		String subclass = map.get("subclass").toString();
		String protocol = map.get("protocol").toString();
		String compare = map.get("compare").toString();
		int allow = Integer.parseInt( map.get("use_type").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "UPDATE usb_dev_info SET name = ?, vid = ?, pid = ?, serial_number = ?, allow = ?, description = ?, class=?, subclass=?, protocol=?, compare_type=? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, usbName);
			pstmt.setString(2, vid);
			pstmt.setString(3, pid);
			pstmt.setString(4, serial);
			pstmt.setInt(5, allow);
			pstmt.setString(6, descript);

			pstmt.setString(7, mainclass);
			pstmt.setString(8, subclass);
			pstmt.setString(9, protocol);
			pstmt.setString(10, compare);
			
			pstmt.setInt(11, usbNo);
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
	
	
	public HashMap<String, Object> insertBlockDeviceSaveWithGetAgentData(HashMap<String, Object> map) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		int deviceId = Integer.parseInt(map.get("deviceId").toString());
		int allow = 1; // ���� �ʿ�� �Ķ���� ����
		
		// ��ġ �α� ���� �˻�
		String sql= "SELECT " 
					+ "no, "
					+ "user_no, "
					+ "agent_log_no, "
					+ "device_name, "
					+ "vid, "
					+ "pid, "
					+ "serial_number "
					+ "FROM usb_connect_log " 
					+ "WHERE no = ? ";

			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, deviceId);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int sizeOfColumn = metaData.getColumnCount();
			int agent_no = 0;
			String column = "";
			
			if (rs.next()) {
				HashMap<String, Object> device_info = new HashMap<String, Object>();
				
				// Column�� ������ŭ ȸ��
				for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++) {
					// Column�� ������ŭ ȸ��
					column = metaData.getColumnName(indexOfcolumn + 1);
					// phone number �� ��� ��ȣȭ
					device_info.put(column, rs.getString(column));
				}
				
				data.put("user_no", device_info.get("user_no"));
				
				sql = "SELECT ag_log.agent_no FROM guardcom.usb_connect_log AS conn_log, guardcom.agent_log as ag_log WHERE conn_log.agent_log_no = ag_log.no AND conn_log.no = ?;";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, deviceId);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					agent_no = rs.getInt(1);				
				}
							
				data.put("agent_log_no", device_info.get("agent_log_no"));
				data.put("agent_no", agent_no);
				
				String serialNumber = device_info.get("serial_number").toString();
				int compareType = 0;
				
				if (serialNumber.equalsIgnoreCase("Unknown")) {
					
					compareType = 111000;
					sql = "SELECT no FROM usb_dev_info WHERE vid = ? AND pid = ? AND compare_type = ? AND serial_number = ''";					
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, device_info.get("vid").toString());
					pstmt.setString(2, device_info.get("pid").toString());
					pstmt.setInt(3, compareType);
					rs = pstmt.executeQuery();
					device_info.put("serial_number", ""); 
				} else {
					
					compareType = 111000;					
					sql = "SELECT no FROM usb_dev_info WHERE vid = ? AND pid = ? AND serial_number = ? AND compare_type = ?";					
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, device_info.get("vid").toString());
					pstmt.setString(2, device_info.get("pid").toString());
					pstmt.setString(3, device_info.get("serial_number").toString());
					pstmt.setInt(4, compareType);
					rs = pstmt.executeQuery();
				}
							
				int usb_no = 0;
				
				if (rs != null && rs.next()) {
					usb_no = rs.getInt("no");
					data.put("usb_no", usb_no);
				} else {
					
					con.setAutoCommit(false);
					
					sql= "INSERT INTO usb_dev_info (name, vid, pid, class, subclass, protocol, serial_number, allow, description, compare_type) "
							+ "VALUES (?, ?, ?, '', '', '',?, ?, ?, ?) ";
					
					pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, device_info.get("device_name").toString());
					pstmt.setString(2, device_info.get("vid").toString());
					pstmt.setString(3, device_info.get("pid").toString());


					pstmt.setString(4, device_info.get("serial_number").toString());
					pstmt.setInt(5, allow);
					pstmt.setString(6, "���� Device ���");
					pstmt.setInt(7,compareType);
					pstmt.executeUpdate();
					if(rs!=null) rs.close(); rs = null;
					rs = pstmt.getGeneratedKeys();
					
					if (rs.next()) {
						usb_no = rs.getInt(1);
					}
					
					data.put("usb_no", usb_no);
					pstmt.close(); pstmt=null;
					con.commit();
					con.setAutoCommit(true);
				}
				
				sql = "UPDATE `guardcom`.`usb_connect_log` SET `visible`=0 WHERE `no`= ?;";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, deviceId);
				pstmt.executeUpdate();
				pstmt.close(); pstmt=null;
				
				data.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
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

	public List<Integer> lookupPolicyNoListByUserNo(int UserNo) {

		List<Integer> result = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql= "SELECT "
					+ "ai.policy_no "
					+ "FROM agent_info as ai "
					+ "INNER JOIN user_info as ui ON ai.own_user_no = ui.no "
					+ "LEFT JOIN policy_info as pi ON ai.policy_no = pi.no "
					+ "WHERE ui.no = ? AND ui.valid = 1 AND ui.permit = 'P' ";
		
		try {
			
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, UserNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	
	public HashMap<String, Object> setUserPolicyDeviceData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String usb_no = map.get("usb_no").toString();
		int user_no = Integer.parseInt(map.get("user_no").toString());
		int agent_no = Integer.parseInt(map.get("agent_no").toString());
		int sCount = 0;
		int fCount = 0;
		String sql;
		
		
		if (agent_no == 0) {
			
			sql= "SELECT "
					+ "ai.no as agent_no, "
						+ "ai.policy_no, "
						+ "pi.usb_dev_list "
						+ "FROM agent_info as ai "
					+ "INNER JOIN user_info as ui ON ai.own_user_no = ui.no "
					+ "LEFT JOIN policy_info as pi ON ai.policy_no = pi.no "
					+ "WHERE ui.no = ? AND ui.valid = 1  AND ui.permit = 'P' ";			
			
		} else {
			
			sql= "SELECT "
					+ "ai.no as agent_no, "
						+ "ai.policy_no, "
						+ "pi.usb_dev_list "
						+ "FROM agent_info as ai "
					+ "LEFT JOIN policy_info as pi ON ai.policy_no = pi.no "
					+ "WHERE ai.no = ?";			
		}
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if (agent_no == 0) {
				pstmt.setInt(1, user_no);
			} else {
				pstmt.setInt(1, agent_no);
			}
			rs = pstmt.executeQuery();
			
			if (rs != null) {
				int policy_no = 0;
				
				while (rs.next()) {
					policy_no = rs.getInt("policy_no");
				
					String usbPolicy = rs.getString("usb_dev_list");
					String savePolicy = CommonUtil.getStrPolicyChangeOperation(usb_no, usbPolicy);
					
					if (!"".equals(savePolicy)) {
						
						con.setAutoCommit(false);
						
						sql = "UPDATE policy_info SET usb_dev_list = ? WHERE no = ? ";
						
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, savePolicy);
						pstmt.setInt(2, policy_no);
						pstmt.executeUpdate();
						
						con.commit();
						
						// ��å ���� �α�
						ICommonService commonService = new CommonServiceImpl();
						commonService.setPolicyUpdateToInsertLog(policy_no);
					}
					
					sCount ++;
				}
								
				result.put("success_cnt", sCount);
				result.put("fail_cnt", fCount);
				result.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
			} else {
				// Agent ���� ����.
				result.put("returnCode", ConfigInfo.EXIST_NOT_AGENT);
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


	public HashMap<String, Object> updatePermitRequestPolicy(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int policyNo = Integer.parseInt(map.get("policy_no").toString());
		int requestNo = Integer.parseInt(map.get("request_no").toString());
		String adminId = map.get("admin_id").toString();
		
		String sql= "UPDATE policy_info AS pi, (SELECT * FROM policy_request_info WHERE no = ?) AS pri "
				+ "SET "
					+ "pi.uninstall_enabled = pri.new_policy_uninstall_enabled"
					+ ",pi.file_encryption_enabled = pri.new_policy_file_encryption_enabled"
					+ ",pi.cd_encryption_enabled = pri.new_policy_cd_encryption_enabled"
					+ ",pi.printer_enabled = pri.new_policy_printer_enabled"
					+ ",pi.cd_enabled = pri.new_policy_cd_enabled"
					+ ",pi.cd_export_enabled = pri.new_policy_cd_export_enabled"
					+ ",pi.wlan_enabled = pri.new_policy_wlan_enabled" 
					+ ",pi.net_share_enabled = pri.new_policy_net_share_enabled"
					+ ",pi.web_export_enabled = pri.new_policy_web_export_enabled"
					
					+ ",pi.sensitive_dir_enabled = pri.new_sensitive_dir_enabled"
					+ ",pi.policy_sensitive_file_access = pri.new_policy_sensitive_file_access"
					+ ",pi.policy_usb_control_enabled = pri.new_policy_usb_control_enabled"
					
				    + ",pi.removal_storage_export_enabled = pri.new_policy_removal_storage_export_enabled"
				    + ",pi.removal_storage_admin_mode = pri.new_policy_removal_storage_admin_mode"
					+ ",pi.usb_dev_list = pri.new_policy_usb_dev_list"
					+ ",pi.com_port_list = pri.new_policy_com_port_list"
					+ ",pi.net_port_list = pri.new_policy_net_port_list"
					+ ",pi.process_list = pri.new_policy_process_list"
					+ ",pi.file_pattern_list = pri.new_policy_file_pattern_list"
					+ ",pi.web_addr_list = pri.new_policy_web_addr_list"
					+ ",pi.msg_block_list = pri.new_policy_msg_block_list"
					+ ",pi.watermark_descriptor = pri.new_policy_watermark_descriptor"
					+ ",pi.print_log_descriptor = pri.new_policy_print_log_descriptor"
				    + ",pi.quarantine_path_access_code = pri.new_policy_quarantine_path_access_code"
					+ ",pi.pattern_file_control = pri.new_policy_pattern_file_control "
				+ "WHERE pi.no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, requestNo);
			pstmt.setInt(2, policyNo);
			pstmt.executeUpdate();
			
			sql = "UPDATE policy_request_info "
				+ "SET "
					+ "permit = 'P' "
					+ ",permit_date = NOW()"
					+ ",permit_admin_id = ? "
				+ "WHERE no = ? "; 
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, adminId);
			pstmt.setInt(2, requestNo);
			pstmt.executeUpdate();
			
			con.commit();
			
			// ��å ���� �α�
			ICommonService commonService = new CommonServiceImpl();
			commonService.setPolicyUpdateToInsertLog(policyNo);
			
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
		
		String sql = "UPDATE policy_request_info "
				+ "SET "
				+ "permit = 'R' "
				+ ",permit_date = NOW()"
				+ ",permit_admin_id = ? "
			+ "WHERE no = ? "; 
		
		try{
			//con = ds.getConnection();
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


	public HashMap<String, Object> daletePolicyMsgData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int msg_no = Integer.parseInt(map.get("msg_no").toString());
		
		String sql = "DELETE FROM msg_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, msg_no);
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


	public HashMap<String, Object> daletePolicyProcessData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int pro_no = Integer.parseInt(map.get("pro_no").toString());
		
		String sql = "DELETE FROM process_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, pro_no);
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


	public HashMap<String, Object> daletePolicyPatternData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int pat_no = Integer.parseInt(map.get("pat_no").toString());
		
		String sql = "DELETE FROM pattern_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, pat_no);
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


	public HashMap<String, Object> daletePolicyNetworkData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int net_no = Integer.parseInt(map.get("net_no").toString());
		
		String sql = "DELETE FROM net_port_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, net_no);
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


	public HashMap<String, Object> daletePolicySerialData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int serial_no = Integer.parseInt(map.get("serial_no").toString());
		
		String sql = "DELETE FROM com_port_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, serial_no);
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


	public HashMap<String, Object> daletePolicyWebsiteData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int site_no = Integer.parseInt(map.get("site_no").toString());
		
		String sql = "DELETE FROM web_addr_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, site_no);
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


	public HashMap<String, Object> daletePolicyUsbData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int usb_no = Integer.parseInt(map.get("usb_no").toString());
		
		String sql = "DELETE FROM usb_dev_info WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, usb_no);
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


	public int selectNetPortIsValied(HashMap<String, Object> map) {
		int result = 0;
		int port = Integer.parseInt(map.get("net_port").toString());
		String sql= "SELECT COUNT(*) AS cnt FROM net_port_info WHERE port = ? ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, port);
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
