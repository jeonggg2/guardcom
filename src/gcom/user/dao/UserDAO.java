package gcom.user.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
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

import gc.crypto.CekInfo;
import gc.crypto.CryptoMgr;
import gc.crypto.CryptoStatus;
import gc.crypto.EkekInfo;
import gc.crypto.PkInfo;
import gc.crypto.PsHashInfo;
import gc.crypto.SaltInfo;
import gc.db.DbCon;
import gcom.Model.PolicyMessengerModel;
import gcom.Model.PolicyNetworkModel;
import gcom.Model.PolicyPatternModel;
import gcom.Model.PolicyProcessModel;
import gcom.Model.PolicySerialModel;
import gcom.Model.PolicyWebSiteBlocklModel;
import gcom.Model.UsbDevInfoModel;
import gcom.common.util.CommonUtil;
import gcom.common.util.ConfigInfo;
import gcom.user.model.MemberPolicyModel;
import gcom.user.model.UserContactModel;
import gcom.user.model.UserInfoModel;
import gcom.user.model.UserNoticeModel;

public class UserDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public UserDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public List<MemberPolicyModel> getMemberPolicyInfo(HashMap<String, Object> map){
		List<MemberPolicyModel> list = new ArrayList<MemberPolicyModel>();
		String user_id = map.get("user_id").toString();
		
		String sql= 
				"SELECT "
					+ "ai.no as agentNo, "
				    + "ui.no as userNo, "
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
				    
				    + "IFNULL(pi.uninstall_enabled, 0) as isUninstall, "
				    + "IFNULL(pi.file_encryption_enabled, 0) as isFileEncryption, "
				    + "IFNULL(pi.cd_encryption_enabled, 0) as isCdEncryption, "
				    + "IFNULL(pi.printer_enabled, 0) as isPrint, "
				    + "IFNULL(pi.cd_enabled, 0) as isCdEnabled, "
				    + "IFNULL(pi.cd_export_enabled, 0) as isCdExport, "
				    + "IFNULL(pi.wlan_enabled, 0) as isWlan, "
				    + "IFNULL(pi.net_share_enabled, 0) as isNetShare, "
				    + "IFNULL(pi.web_export_enabled, 0) as isWebExport, "
				    
				  	//異붽�
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
				+ "INNER JOIN policy_info AS pi ON ai.policy_no = pi.no "
				+ "INNER JOIN dept_info as di ON ai.dept_no = di.no "
				+ "WHERE ui.id = ? AND ui.permit = 'P' ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				MemberPolicyModel model = new MemberPolicyModel();
				model.setAgentNo(rs.getInt("agentNo"));
				model.setUserNo(rs.getInt("userNo"));
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
				model.setIsUninstall(rs.getInt("isUninstall"));
				model.setIsFileEncryption(rs.getInt("isFileEncryption"));
				model.setIsCdEncryption(rs.getInt("isCdEncryption"));
				model.setIsPrint(rs.getInt("isPrint"));
				model.setIsCdEnabled(rs.getInt("isCdEnabled"));
				model.setIsCdExport(rs.getInt("isCdExport"));
				model.setIsWlan(rs.getInt("isWlan"));
				model.setIsNetShare(rs.getInt("isNetShare"));
				model.setIsWebExport(rs.getInt("isWebExport"));
				
				//異붽�
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
				
				list.add(model);
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
		
		return list;
	}
	

	public UserInfoModel getUserInfo(HashMap<String, Object> map) {
		UserInfoModel model = new UserInfoModel();
		
		String sql= 
				"SELECT user_info.no, "
				+ "user_info.name, "
				+ "user_info.phone, "
				+ "dept_info.short_name as dept_name, "
				+ "user_info.duty, "
				+ "user_info.rank, "
				+ "user_info.attfile_id, "
				+ "user_info.id, "
				+ "user_info.number, "
				+ "IFNULL(user_info.notice, '') AS notice, "
				+ "IFNULL(fu.att_file_path, '') AS att_file_path, " 
				+ "IFNULL(fu.view_file_nm, '') AS view_file_nm, "
				+ "IFNULL(fu.save_file_nm, '') AS save_file_nm "
				+ "FROM user_info "
				+ "INNER JOIN dept_info ON user_info.dept_no = dept_info.no "
				+ "LEFT JOIN services_file_upload_info AS fu ON fu.attfile_id = user_info.attfile_id "
				+ "WHERE user_info.id = ? AND user_info.permit = 'P' ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, map.get("user_id").toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setUserNo(rs.getInt("no"));
				model.setName(rs.getString("name"));
				model.setPhone(rs.getString("phone"));
				model.setDeptName(rs.getString("dept_name"));
				model.setDuty(rs.getString("duty"));
				model.setRank(rs.getString("rank"));
				model.setAttfile_id(rs.getInt("attfile_id"));
				model.setId(rs.getString("id"));
				model.setNumber(rs.getString("number"));
				model.setNotice(rs.getString("notice"));
				model.setUserPhotoPath(rs.getString("att_file_path"));
				model.setUserPhotofileSaveName(rs.getString("save_file_nm"));
				model.setUserPhotoFileViewName(rs.getString("view_file_nm"));
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
	
	public int getUserNoticeListCount(HashMap<String, Object> map) {
		int cnt = 0;
		String search_text = map.get("search_text").toString();
		String search_type = map.get("search_type").toString();
		
		String sql= 
				"SELECT COUNT(*) AS cnt FROM user_notice_bbs AS bbs "
				+ "LEFT JOIN admin_info AS admin ON bbs.reg_staf_no = admin.no "
				+ "WHERE bbs.del_yn = 'N' ";
		
		if(!"".equals(search_text)){
			if("1".equals(search_type)) {
				sql += "AND bbs.bbs_title like ? ";
			} else if ("2".equals(search_type)) {
				sql += "AND admin.id like ? ";
			}
		}
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text)){
				pstmt.setString(1, "%" + search_text + "%");
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				cnt = rs.getInt("cnt");	
				
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
		return cnt;
	}

	public List<UserNoticeModel> getUserNoticeList(HashMap<String, Object> map) {
		List<UserNoticeModel> list = new ArrayList<UserNoticeModel>();
		String search_text = map.get("search_text").toString();
		String search_type = map.get("search_type").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql= 
				"SELECT bbs.bbs_id, "
			    + "bbs.bbs_title, "
			    + "bbs.special_type, "
			    + "IFNULL(admin.id, '') AS id, "
			    + "DATE(bbs.reg_dt) AS reg_dt, "
			    + "bbs_hit.hit_cnt, "
			    + "bbs.attfile_yn, "
			    + "bbs.attfile_id "
				+ "FROM user_notice_bbs AS bbs "
				+ "LEFT JOIN admin_info AS admin ON bbs.reg_staf_no = admin.no "
				+ "INNER JOIN user_notice_bbs_hit AS bbs_hit ON bbs.bbs_id = bbs_hit.bbs_id "
				+ "WHERE bbs.del_yn = 'N' ";
				
		
		if(!"".equals(search_text)){
			if("1".equals(search_type)) {
				sql += "AND bbs.bbs_title like ? ";
			} else if ("2".equals(search_type)) {
				sql += "AND admin.id like ? ";
			}
		}
		
		sql += "ORDER BY bbs.bbs_id DESC, bbs.reg_dt DESC LIMIT ?, ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			if(!"".equals(search_text)){
				pstmt.setString(1, "%" + search_text + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else {
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, endRow);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserNoticeModel model = new UserNoticeModel();
				model.setBbsId(rs.getInt("bbs_id"));
				model.setBbsTitle(rs.getString("bbs_title"));
				model.setBbsSpecialYN(rs.getString("special_type"));
				model.setBbsRegStaf(rs.getString("id"));
				model.setBbsRegDate(rs.getString("reg_dt"));
				model.setBbsClickCnt(rs.getInt("hit_cnt"));
				model.setBbsAttfileYN(rs.getString("attfile_yn"));
				model.setAttfileId(rs.getInt("attfile_id"));
				
				list.add(model);
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
		
		return list;
	}

	public UserNoticeModel getUserNoticeDetail(HashMap<String, Object> map) {
		UserNoticeModel model = new UserNoticeModel();
		int bbs_id = Integer.parseInt(map.get("bbs_id").toString());
		
		String sql= 
				"SELECT bbs.bbs_id, "
			    + "bbs.bbs_title, "
			    + "bbs.special_type, "
			    + "admin.id, "
			    + "DATE(bbs.reg_dt) AS reg_dt, "
			    + "bbs_hit.hit_cnt, "
			    + "bbs.attfile_yn, "
			    + "bbs.bbs_body, "
			    + "bbs.attfile_id "
				+ "FROM user_notice_bbs AS bbs "
				+ "LEFT JOIN admin_info AS admin ON bbs.reg_staf_no = admin.no "
				+ "INNER JOIN user_notice_bbs_hit AS bbs_hit ON bbs.bbs_id = bbs_hit.bbs_id "
				+ "WHERE bbs.del_yn = 'N' "
				+ "AND bbs.bbs_id = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bbs_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setBbsId(rs.getInt("bbs_id"));
				model.setBbsTitle(rs.getString("bbs_title"));
				model.setBbsSpecialYN(rs.getString("special_type"));
				model.setBbsRegStaf(rs.getString("id"));
				model.setBbsRegDate(rs.getString("reg_dt"));
				model.setBbsClickCnt(rs.getInt("hit_cnt"));
				model.setBbsAttfileYN(rs.getString("attfile_yn"));
				model.setBbsBody(rs.getString("bbs_body"));
				model.setAttfileId(rs.getInt("attfile_id"));
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
	
	public void updateNoticeViewCount(HashMap<String, Object> map) {
		int bbs_id = Integer.parseInt(map.get("bbs_id").toString());
		
		String sql= "UPDATE user_notice_bbs_hit SET hit_cnt = hit_cnt + 1 WHERE bbs_id = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bbs_id);
			pstmt.executeUpdate();
			
			con.commit();
			
		}catch(SQLException ex){
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
	}

	public int getUserContactInfoCount(HashMap<String, Object> map) {
		int cnt = 0;
		
		String id = map.get("user_id").toString();
		
		String sql= 
				"SELECT COUNT(*) as cnt FROM (SELECT con.contact_id, "
				+ "con.contact_type, "
				+ "con.contact_title, "
				+ "user_info.id, "
				+ "DATE(con.reg_dt) as reg_dt, "
				+ "con.comment_yn, "
				+ "IFNULL(admin_info.id, '') as comment_reg_staf_id, "
				+ "IFNULL(con_comm.reply_content, '') as reply_content, "
				+ "IFNULL(con_comm.reg_dt, '') as comment_reg_dt "
				+ "FROM user_contact_info AS con "
				+ "LEFT JOIN user_info AS user_info ON con.reg_user_staf_no = user_info.no "
				+ "LEFT JOIN user_contact_comment AS con_comm ON con.contact_id = con_comm.contact_id "
				+ "LEFT JOIN admin_info AS admin_info ON con_comm.reg_admin_staf_no = admin_info.no "
				+ "WHERE user_info.id = ? AND user_info.permit = 'P'  ) AS T ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				cnt = rs.getInt("cnt");	
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
		
		return cnt;
	}

	public List<UserContactModel> getUserContactlist(HashMap<String, Object> map) {
		List<UserContactModel> list = new ArrayList<UserContactModel>();
		String id = map.get("user_id").toString();
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		String sql= 
				"SELECT con.contact_id, "
				+ "con.contact_type, "
				+ "con.contact_title, "
				+ "user_info.id, "
				+ "DATE(con.reg_dt) as reg_dt, "
				+ "con.comment_yn, "
				+ "IFNULL(admin_info.id, '')as comment_reg_staf_id, "
				+ "IFNULL(con_comm.reply_content, '') as reply_content, "
				+ "IFNULL(con_comm.reg_dt, '') as comment_reg_dt "
				+ "FROM user_contact_info AS con "
				+ "LEFT JOIN user_info AS user_info ON con.reg_user_staf_no = user_info.no "
				+ "LEFT JOIN user_contact_comment AS con_comm ON con.contact_id = con_comm.contact_id "
				+ "LEFT JOIN admin_info AS admin_info ON con_comm.reg_admin_staf_no = admin_info.no "
				+ "WHERE user_info.id = ?  AND user_info.permit = 'P' "
				+ "ORDER BY con.contact_id DESC, con.reg_dt DESC "
				+ "LIMIT ? ,? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UserContactModel model = new UserContactModel();
				model.setContactId(rs.getInt("contact_id"));
				model.setContactType(rs.getInt("contact_type"));
				model.setTypeName(rs.getInt("contact_type"));
				model.setContactTitle(CommonUtil.getReplaceHtmlChar(rs.getString("contact_title")));
				model.setRegUserStafId(rs.getString("id"));
				model.setRegDt(rs.getString("reg_dt"));
				model.setCommentYN(rs.getString("comment_yn"));
				model.setCommnetRegStafId(rs.getString("comment_reg_staf_id"));
				model.setReplyContent(rs.getString("reply_content"));
				model.setCommentRegDt(rs.getString("comment_reg_dt"));
								
				list.add(model);
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
		
		return list;
	}

	public HashMap<String, Object> insertContactSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String titel = map.get("titel").toString();
		String body = map.get("body").toString();
		int userNo = Integer.parseInt(map.get("user_no").toString());
		String eMail = map.get("eMail").toString();
		int conType = Integer.parseInt(map.get("conType").toString());
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO user_contact_info (contact_title, contact_body, reg_user_staf_no, reg_dt, email, contact_type, comment_yn) " 
					+ " VALUES ( ?, ?, ?, NOW(), ?, ?, 'N') ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, titel);
			pstmt.setString(2, body);
			pstmt.setInt(3, userNo);
			pstmt.setString(4, eMail);
			pstmt.setInt(5, conType);
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
	
	public HashMap<String, Object> insertAccountReqeust(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String user_password1 = map.get("user_password1").toString();
		String user_number = map.get("user_number").toString();
		String user_name = map.get("user_name").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_dept = map.get("user_dept").toString();		
		String user_id = map.get("user_id").toString();
		String user_phone = map.get("user_phone").toString();

		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT "
				+ "INTO user_account_request "
				+ "(dept_no, "
				+ "mem_number, "
				+ "account_id, "
				+ "mem_password , "
				+ "mem_name, "
				+ "mem_phone, "
				+ "mem_duty, "
				+ "mem_rank, "
				+ "req_date, "
				+ "permit, "
				+ "permit_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), 'P', NOW()) ";
		
		String userInfoInsertSql = "INSERT INTO user_info "
				+ "(dept_no, duty, rank, number, name, phone, id, valid,  password,  notice, permit) "
				+ "VALUES (?, ?,     ?,     ?,    ?,     ?,   ?,    ?,       ?,        '' ,    'P' ) ";
		
		String userInfoUpdataSql = "UPDATE user_info SET "
				+ "salt = ?, "
				+ "pshash = ?, "
				+ "ekek = ?, "
				+ "public_key = ?, "
				+ "private_key = ? "					
				+ "WHERE no = ?";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user_dept);
			pstmt.setString(2, user_number);
			pstmt.setString(3, user_id);
			pstmt.setString(4, "");
			pstmt.setString(5, user_name);
			pstmt.setString(6, user_phone);
			pstmt.setString(7, user_duty);
			pstmt.setString(8, user_rank);

			con.setAutoCommit(false);
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(userInfoInsertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user_dept);
			pstmt.setString(2, user_duty);
			pstmt.setString(3, user_rank);
			pstmt.setString(4, user_number);
			pstmt.setString(5, user_name);
			pstmt.setString(6, user_phone);
			pstmt.setString(7, user_id);
			pstmt.setInt(8, 1);
			pstmt.setString(9, "");
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next() == false) {
				return result;
			}
			int user_no = rs.getInt(1);
			
			CryptoMgr cryptoMgr = new CryptoMgr();
	    	
			byte[] pw = null;
			try {
				pw = user_password1.getBytes("UTF-16LE");
			} catch (UnsupportedEncodingException e) {
    			cryptoMgr.terminate();
    			return result;
			}
			
    		CryptoStatus status = cryptoMgr.init();
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}
    		
    		SaltInfo saltInfo = new SaltInfo();
    		status = saltInfo.init(cryptoMgr, user_no);
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}

    		PsHashInfo psHashInfo = new PsHashInfo();
    		status = psHashInfo.init(cryptoMgr, user_no, saltInfo.getSaltData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
    			cryptoMgr.terminate();
    			return result;
    		}
    		
    		EkekInfo ekekInfo = new EkekInfo();
    		status = ekekInfo.init(cryptoMgr, user_no, psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		
    		PkInfo pkInfo = new PkInfo();
    		status = pkInfo.init(cryptoMgr, user_no, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		pstmt = con.prepareStatement(userInfoUpdataSql);
    		
    		pstmt.setBytes(1, saltInfo.getBytes()); 		
    		pstmt.setBytes(2, psHashInfo.getBytes());
    		pstmt.setBytes(3, ekekInfo.getBytes());
    		pstmt.setBytes(4, pkInfo.getPublicKeyData());
    		pstmt.setBytes(5, pkInfo.getPrivateKeyData());
    		pstmt.setInt(6, user_no);
    		
    		pstmt.executeUpdate();
    		
    		pstmt = con.prepareStatement("INSERT INTO guardcom.cek_info(no, user_no, cek) VALUES (0, ?, ?)");
    		
    		CekInfo cekInfo = new CekInfo(); 		
    		status = cekInfo.init(cryptoMgr, user_no, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
    		if (status != CryptoStatus.CS_SUCCESS) {		
        		cryptoMgr.terminate();	   
        		return result;
    		}
    		   				
    		cryptoMgr.terminate();	   
    		
    		pstmt.setInt(1, user_no);
    		pstmt.setBytes(2, cekInfo.getBytes());
    		pstmt.executeUpdate();   		  		
    		
    		// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
    		sql= "INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ?  AND PERMIT = 'P' ";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, user_number);
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
	

	public List<UsbDevInfoModel> getPolicyUsbBlockList(HashMap<String, Object> map) {
		List<UsbDevInfoModel> data = new ArrayList<UsbDevInfoModel>();
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String usbId = map.get("usbId").toString();
		
		String sql =
				"SELECT "
					+ "no as usb_id, "
					+ "name as usb_name, "
					+ "vid, "
					+ "pid, "
					+ "serial_number, "
					+ "allow, "
					+ "description "
				+ "FROM usb_dev_info "
				+ "WHERE no in (" + usbId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				UsbDevInfoModel model = new UsbDevInfoModel();
				model.setUsbId(rs.getInt("usb_id"));
				model.setName(rs.getString("usb_name"));
				model.setVid(rs.getString("vid"));
				model.setPid(rs.getString("pid"));
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
		String usbId = map.get("usbId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM usb_dev_info WHERE no in ("+ usbId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String serialId = map.get("serialId").toString();
		
		String sql =
				"SELECT "
					+ "no as serial_no, "
					+ "name as serial_name, "
					+ "allow, "
					+ "description "
				+ "FROM com_port_info "
				+ "WHERE no in (" + serialId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String serialId = map.get("serialId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM com_port_info WHERE no in ("+ serialId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String netId = map.get("netId").toString();
		
		String sql =
				"SELECT "
					+ "no as net_no, "
					+ "name as net_name, "
					+ "port, "
					+ "descriptor, "
					+ "allow "
				+ "FROM net_port_info "
				+ "WHERE no in (" + netId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String netId = map.get("netId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM net_port_info WHERE no in ("+ netId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String prsId = map.get("prsId").toString();
		
		String sql =
				"SELECT "
					+ "no as pro_no, "
					+ "process_name, "
					+ "IFNULL(process_path,'') as process_path, "
					+ "IFNULL(hash, '') as hash, "
					+ "notice, "
					+ "valid "
				+ "FROM process_info "
				+ "WHERE no in (" + prsId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String prsId = map.get("prsId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM process_info WHERE no in ("+ prsId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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

	public List<PolicyPatternModel> getPolicyPatternList(HashMap<String, Object> map) {
		List<PolicyPatternModel> data = new ArrayList<PolicyPatternModel>();
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String patId = map.get("patId").toString();
		
		String sql =
				"SELECT "
					+ "no as pat_no, "
					+ "description as pat_name, "
					+ "IFNULL(data, '') as data, "
					+ "notice, "
					+ "valid "
				+ "FROM pattern_info "
				+ "WHERE no in (" + patId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String patId = map.get("patId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM pattern_info WHERE no in ("+ patId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String siteId = map.get("siteId").toString();
		
		String sql =
				"SELECT "
					+ "no as site_id, "
					+ "address, "
					+ "allow, "
					+ "description "
				+ "FROM web_addr_info "
				+ "WHERE no in (" + siteId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String siteId = map.get("siteId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM web_addr_info WHERE no in ("+ siteId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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

	public List<PolicyMessengerModel> getPolicyMessengerList(HashMap<String, Object> map) {
		List<PolicyMessengerModel> data = new ArrayList<PolicyMessengerModel>();
		int start_date = Integer.parseInt(map.get("startRow").toString());
		int end_date = Integer.parseInt(map.get("endRow").toString());
		String msgId = map.get("msgId").toString();
		
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
			    + "WHERE no in (" + msgId + ") "
			    + "ORDER BY no desc LIMIT ?, ? ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  start_date);
			pstmt.setInt(2,  end_date);
			
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
		String msgId = map.get("msgId").toString();
		
		String sql= "SELECT COUNT(*) as cnt FROM msg_info WHERE no in ("+ msgId + ") ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
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

	public UserContactModel getUserContactDetail(HashMap<String, Object> map) {
		UserContactModel model = new UserContactModel();
		int contact_id = Integer.parseInt(map.get("contact_id").toString());
		
		String sql= 
				"SELECT con.contact_id, "
				+ "con.contact_type, "
				+ "con.contact_title, "
				+ "con.contact_body, "
				+ "ui.id, "
				+ "ui.name, "
				+ "DATE(con.reg_dt) as reg_dt, "
				+ "con.comment_yn, "
				+ "IFNULL(admin_info.id, '')as comment_reg_staf_id, "
				+ "IFNULL(con_comm.reply_content, '') as reply_content, "
				+ "IFNULL(con_comm.reg_dt, '') as comment_reg_dt "
				+ "FROM user_contact_info AS con "
				+ "LEFT JOIN user_info AS ui ON con.reg_user_staf_no = ui.no "
				+ "LEFT JOIN user_contact_comment AS con_comm ON con.contact_id = con_comm.contact_id "
				+ "LEFT JOIN admin_info AS admin_info ON con_comm.reg_admin_staf_no = admin_info.no "
				+ "WHERE con.contact_id = ? AND ui.permit = 'P' ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, contact_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				model.setContactId(rs.getInt("contact_id"));
				model.setContactType(rs.getInt("contact_type"));
				model.setTypeName(rs.getInt("contact_type"));
				model.setContactTitle(CommonUtil.getReplaceHtmlChar(rs.getString("contact_title")));
				model.setContactBody(CommonUtil.getReplaceHtmlChar(rs.getString("contact_body")));
				model.setRegUserStafId(rs.getString("id"));
				model.setRegUserName(rs.getString("name"));
				model.setRegDt(rs.getString("reg_dt"));
				model.setCommentYN(rs.getString("comment_yn"));
				model.setCommnetRegStafId(rs.getString("comment_reg_staf_id"));
				model.setReplyContent(rs.getString("reply_content"));
				model.setCommentRegDt(rs.getString("comment_reg_dt"));
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

	public MemberPolicyModel getSettingPolicyInfo(HashMap<String, Object> map) {
		MemberPolicyModel model = new MemberPolicyModel();
		int policyNo = Integer.parseInt(map.get("policy_no").toString());
		
		String sql= 
				"SELECT "
					+ "ai.no as agentNo, "
				    + "ui.no as userNo, "
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
				    
				    + "IFNULL(pi.uninstall_enabled, 0) as isUninstall, "
				    + "IFNULL(pi.file_encryption_enabled, 0) as isFileEncryption, "
				    + "IFNULL(pi.cd_encryption_enabled, 0) as isCdEncryption, "
				    + "IFNULL(pi.printer_enabled, 0) as isPrint, "
				    + "IFNULL(pi.cd_enabled, 0) as isCdEnabled, "
				    + "IFNULL(pi.cd_export_enabled, 0) as isCdExport, "
				    + "IFNULL(pi.wlan_enabled, 0) as isWlan, "
				    + "IFNULL(pi.net_share_enabled, 0) as isNetShare, "
				    + "IFNULL(pi.web_export_enabled, 0) as isWebExport, "
				    
				  	//異붽�
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
				    + "IFNULL(pi.pattern_file_control, 0) as patternFileControl "
				+ "FROM agent_info AS ai "
				+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
				+ "INNER JOIN policy_info AS pi ON ai.policy_no = pi.no "
				+ "INNER JOIN dept_info as di ON ai.dept_no = di.no "
				+ "WHERE pi.no = ?  AND ui.permit = 'P' ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, policyNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				model.setAgentNo(rs.getInt("agentNo"));
				model.setUserNo(rs.getInt("userNo"));
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
				model.setIsUninstall(rs.getInt("isUninstall"));
				model.setIsFileEncryption(rs.getInt("isFileEncryption"));
				model.setIsCdEncryption(rs.getInt("isCdEncryption"));
				model.setIsPrint(rs.getInt("isPrint"));
				model.setIsCdEnabled(rs.getInt("isCdEnabled"));
				model.setIsCdExport(rs.getInt("isCdExport"));
				model.setIsWlan(rs.getInt("isWlan"));
				model.setIsNetShare(rs.getInt("isNetShare"));
				model.setIsWebExport(rs.getInt("isWebExport"));
				
				//異붽�
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
				model.setPatternFileControl(rs.getInt("patternFileControl"));
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

	public HashMap<String, Object> insertRequestPolicyInfoSave(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int agent_id = Integer.parseInt(map.get("agent_no").toString());
		int user_id = Integer.parseInt(map.get("user_no").toString());
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
		String notice = map.get("notice").toString();
		
		String sql= "INSERT INTO policy_request_info ( "
						+ "agent_no, "
						+ "user_no, "
						+ "new_policy_uninstall_enabled, "
						+ "new_policy_file_encryption_enabled, "
						+ "new_policy_cd_encryption_enabled, "
						+ "new_policy_printer_enabled, "
						+ "new_policy_cd_enabled, "
						+ "new_policy_cd_export_enabled, "
						+ "new_policy_wlan_enabled, "
						+ "new_policy_net_share_enabled, "
						+ "new_policy_web_export_enabled, "
						
						+ "new_sensitive_dir_enabled, "
						+ "new_policy_sensitive_file_access, "
						+ "new_policy_usb_control_enabled, "
						
						+ "new_policy_removal_storage_export_enabled, " 
						+ "new_policy_removal_storage_admin_mode, "
						+ "new_policy_usb_dev_list, "
						+ "new_policy_com_port_list, "
						+ "new_policy_net_port_list, "
						+ "new_policy_process_list, "
						+ "new_policy_file_pattern_list, " 
						+ "new_policy_web_addr_list, "
						+ "new_policy_msg_block_list, "
						+ "new_policy_watermark_descriptor, "
						+ "new_policy_print_log_descriptor, "
						+ "new_policy_quarantine_path_access_code, " 
						+ "new_policy_pattern_file_control, "
						+ "notice, "
						+ "request_server_time, "
						+ "request_client_time "
					+") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW()) ";
		
		try{
			
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, agent_id);
			pstmt.setInt(2, user_id);
			pstmt.setInt(3, isUninstall);
			pstmt.setInt(4, isFileEncryption);
			pstmt.setInt(5, isCdEncryption);
			pstmt.setInt(6, isPrint);
			pstmt.setInt(7, isCdEnabled);
			pstmt.setInt(8, isCdExport);
			pstmt.setInt(9, isWlan);
			pstmt.setInt(10, isNetShare);
			pstmt.setInt(11, isWebExport);
			pstmt.setInt(12, isSensitiveDirEnabled);
			pstmt.setInt(13, isSensitiveFileAccess);
			pstmt.setInt(14, isUsbControlEnabled);
			pstmt.setInt(15, isStorageExport);
			pstmt.setInt(16, isStorageAdmin);
			pstmt.setString(17, isUsbBlock);
			pstmt.setString(18, isComPortBlock);
			pstmt.setString(19, isNetPortBlock);
			pstmt.setString(20, isProcessList);
			pstmt.setString(21, isFilePattern);
			pstmt.setString(22, isWebAddr);
			pstmt.setString(23, isMsgBlock);
			pstmt.setString(24, isWatermark);
			pstmt.setString(25, printLogDesc);
			pstmt.setString(26, quarantinePathAccessCode);
			pstmt.setInt(27, patternFileControl);
			pstmt.setString(28, notice);
			pstmt.executeUpdate();
									
			con.commit();
			result.put("returnCode",  ConfigInfo.RETURN_CODE_SUCCESS);
			
		}catch(SQLException ex){
			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
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

	public HashMap<String, Object> updateUserInfoData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int userNo = Integer.parseInt(map.get("user_no").toString());
		String password = map.get("password").toString();
		String changePasswordYn = map.get("changePasswordYn").toString();
		String phone = map.get("phone").toString();
		String number = map.get("number").toString();
		
		String sql= "";
		
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
				
			if ("Y".equals(changePasswordYn)) {
				
				/*
				pstmt = con.prepareStatement("SELECT password FROM user_info WHERE no = ?");
				pstmt.setInt(1,  userNo);
				rs = pstmt.executeQuery();
				
				if (rs.next() == false) {
					result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
					return result;
				}
				
				String oldpassword = rs.getString(1);
				
				byte[] oldpw = null;
				try {
					oldpw = oldpassword.getBytes("UTF-16LE");
				} catch (UnsupportedEncodingException e) {
					result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}								
						
				
				sql = "UPDATE user_info SET password = ? , phone = ? WHERE no = ?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, password);
				pstmt.setString(2, phone);
				pstmt.setInt(3, userNo);
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
				*/
				
				String selectSql = "SELECT SALT, PSHASH FROM pw_user_latest_log WHERE USER_NO = ?";
				pstmt=con.prepareStatement(selectSql);
				pstmt.setString(1, number);
				rs = pstmt.executeQuery();
				
				byte[] pw = null;
				try {
					pw = password.getBytes("UTF-16LE");
				} catch (UnsupportedEncodingException e) {
					result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				CryptoStatus status;
				CryptoMgr cryptoMgr = new CryptoMgr();
				
				status = cryptoMgr.init();
				if (status != CryptoStatus.CS_SUCCESS) {
					result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
					return result;
				}
				
				while (rs.next()) {
					byte[] saltTemp;
			    	byte[] psHashTemp;
			    	
			    	saltTemp = rs.getBytes(1);
			    	psHashTemp = rs.getBytes(2);
			    	
			    	status = cryptoMgr.authenticatePassword(pw, saltTemp, psHashTemp);
			    	
			    	if (status == CryptoStatus.CS_SUCCESS) {
			    		cryptoMgr.terminate();
		    			result.put("returnCode", ConfigInfo.PW_USED);
			    		return result;
			    	}
				}
				
				
				
				SaltInfo saltInfo = new SaltInfo();
	    		status = saltInfo.init(cryptoMgr, userNo);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			return result;
	    		}

	    		PsHashInfo psHashInfo = new PsHashInfo();
	    		status = psHashInfo.init(cryptoMgr, userNo, saltInfo.getSaltData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			return result;
	    		}
	    		
	    		EkekInfo ekekInfo = new EkekInfo();
	    		status = ekekInfo.init(cryptoMgr, userNo, psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	        		cryptoMgr.terminate();	   
	        		return result;
	    		}
	    		
	    		PkInfo pkInfo = new PkInfo();
	    		status = pkInfo.init(cryptoMgr, userNo, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {
	        		cryptoMgr.terminate();	   
	        		return result;
	    		}
	    		
	    		
	    		sql = "UPDATE user_info SET  "
						+ "salt = ?, "
						+ "pshash = ?, "
						+ "ekek = ?, "
						+ "public_key = ?, "
						+ "private_key = ?, "
						+ "phone = ? "
						+ "WHERE no = ?";
	    		pstmt=con.prepareStatement(sql);
	    		pstmt.setBytes(1, saltInfo.getBytes()); 		
	    		pstmt.setBytes(2, psHashInfo.getBytes());
	    		pstmt.setBytes(3, ekekInfo.getBytes());
	    		pstmt.setBytes(4, pkInfo.getPublicKeyData());
	    		pstmt.setBytes(5, pkInfo.getPrivateKeyData());
	    		pstmt.setString(6, phone);
	    		pstmt.setInt(7, userNo);
	    		pstmt.executeUpdate();
	    		
	    		sql = "UPDATE cek_info SET cek = ? WHERE USER_NO = ?";
	    		CekInfo cekInfo = new CekInfo(); 		
	    		status = cekInfo.init(cryptoMgr, userNo, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), pw);
	    		if (status != CryptoStatus.CS_SUCCESS) {		
	        		cryptoMgr.terminate();	   
	        		return result;
	    		}
	    		pstmt=con.prepareStatement(sql);
	    		pstmt.setBytes(1, cekInfo.getBytes());
	    		pstmt.setInt(2, userNo);
	    		pstmt.executeUpdate(); 
	    			    		// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
				pstmt=con.prepareStatement("INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ?  AND PERMIT = 'P' ");
				pstmt.setString(1, number);
				pstmt.executeUpdate();
				
				cryptoMgr.terminate();	  
	    		
				/*
				pstmt = con.prepareStatement("SELECT salt, pshash, ekek, number FROM guardcom.user_info WHERE no =?");
				if (pstmt == null) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				pstmt.setInt(1, userNo);
				rs = pstmt.executeQuery();
				if (rs == null) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				if (! rs.next()) {
					throw new SQLException("failed to next");
				}

				status = cryptoMgr.authenticatePassword(oldpw, rs.getBytes(1), rs.getBytes(2));
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				@SuppressWarnings("unused")
				SaltInfo saltInfo = new SaltInfo(rs.getBytes(1));
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}

				PsHashInfo psHashInfo = new PsHashInfo(rs.getBytes(2));
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}		
				
				Blob ekek = rs.getBlob(3);
				byte[] buff = new byte [4];
				buff = ekek.getBytes(9, 4);
				int ekekLength = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
				byte[] kekData = new byte[ekekLength + CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
				
				byte[] hashData = new byte[CryptoMgr.CM_DEFAULT_HASH_LENGTH];
				status = cryptoMgr.hashPassword(oldpw, psHashInfo.getPsHashData(), hashData);
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				byte[] paddingBuff = new byte[CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
				for (int i = 0; i < paddingBuff.length; i++) {
					paddingBuff[i] = 16;
				}
				buff = new byte[CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH * 2];
				status = cryptoMgr.encryptData(hashData, CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH, paddingBuff, buff);
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				byte[] ekekData = new byte[ekekLength + CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
				System.arraycopy(ekek.getBytes(13, ekekLength), 0, ekekData, 0, ekekLength);
				System.arraycopy(buff, 0, ekekData, ekekLength, CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH);
				status = cryptoMgr.decryptKek(ekekData, psHashInfo.getPsHashData(), oldpw, kekData);
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				byte[] realKekData = new byte[kekData.length - CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
				System.arraycopy(kekData, 0, realKekData, 0, realKekData.length);
				for (int i = 0; i < kekData.length; i++) {
					kekData[i] = 0;
				}
				
				// 새로운 패스워드를 기반으로 Kek를 암호화하도록 합니다.
				SaltInfo newSaltInfo = new SaltInfo();
				status = newSaltInfo.init(cryptoMgr, userNo);
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				PsHashInfo newPsHashInfo = new PsHashInfo();
				status = newPsHashInfo.init(cryptoMgr, userNo, newSaltInfo.getSaltData(), pw);
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
				
				EkekInfo newEkekInfo = new EkekInfo();
				status = newEkekInfo.init(cryptoMgr, userNo, newPsHashInfo.getPsHashData(), pw, realKekData);
				for (int i = 0; i < realKekData.length; i++) {
					realKekData[i] = 0;
				}
				if (status != CryptoStatus.CS_SUCCESS) {
	    			cryptoMgr.terminate();
	    			result.put("returnCode",  ConfigInfo.RETURN_CODE_ERROR);
	    			return result;
				}
						
				rs.close();
				pstmt.close();
				pstmt = con.prepareStatement("UPDATE guardcom.user_info SET salt=?, pshash=?, ekek=? WHERE no=?");
				if (pstmt == null) {
					throw new SQLException("failed to preparedStatement");
				}
				
				pstmt.setBytes(1, newSaltInfo.getBytes());
				pstmt.setBytes(2, newPsHashInfo.getBytes());
				pstmt.setBytes(3, newEkekInfo.getBytes());
				pstmt.setInt(4, userNo);
				
				pstmt.executeUpdate();				
				*/
				
			} else {
				sql = "UPDATE user_info SET phone = ? WHERE no = ?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, phone);
				pstmt.setInt(2, userNo);
				pstmt.executeUpdate();
			}
			
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

	public List<HashMap<String, Object>> getLatestListData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int startRow = 0;
		int endRow = 3;
		
		String sql= 
				"SELECT bbs.bbs_id, "
			    + "bbs.bbs_title, "
			    + "bbs.special_type, "
			    + "IFNULL(admin.id, '') AS id, "
			    + "DATE(bbs.reg_dt) AS reg_dt, "
			    + "bbs_hit.hit_cnt, "
			    + "bbs.attfile_yn, "
			    + "bbs.attfile_id "
				+ "FROM user_notice_bbs AS bbs "
				+ "LEFT JOIN admin_info AS admin ON bbs.reg_staf_no = admin.no "
				+ "INNER JOIN user_notice_bbs_hit AS bbs_hit ON bbs.bbs_id = bbs_hit.bbs_id "
				+ "WHERE bbs.del_yn = 'N' "
				+ "ORDER BY bbs.bbs_id DESC, bbs.reg_dt DESC LIMIT ?, ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("bbs_id", rs.getInt("bbs_id"));
				map.put("bbs_title", rs.getString("bbs_title"));
				map.put("reg_dt", rs.getString("reg_dt"));
				map.put("attfile_yn", rs.getString("attfile_yn"));
				map.put("attfile_id", rs.getString("attfile_id"));
				
				list.add(map);
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
		
		return list;
	}

	public boolean selectUserDupCnt(HashMap<String, Object> param) {
		boolean numResult = false;
		boolean idResult = false;
		
		String sql = "";
				
		try{
			con = DbCon.getConnection();
			sql = "SELECT COUNT(*) AS CNT FROM user_info WHERE ID = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, (String)param.get("user_id"));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				idResult = rs.getInt("CNT") < 1 ? true : false;
			}
			
			sql = "SELECT COUNT(*) AS CNT FROM user_info WHERE NUMBER = ?"; 
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, (String)param.get("user_number"));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				numResult = rs.getInt("CNT") < 1 ? true : false;
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
		
		return numResult && idResult;
	}

}
