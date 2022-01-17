package gcom.policy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;
import gcom.policy.model.PolicyModel;

public class PolicyDAO {
	
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public PolicyDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public boolean updatePolicyInfoByNo(PolicyModel model) {
		return false;
	}
	
	public PolicyModel getMemberPolicyInfoByNo(int no){
		
		PolicyModel model = new PolicyModel();
		
		String sql= 
				"SELECT "
				    + "pi.no as policyNo, "
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
				+ "FROM policy_info AS pi "
				+ "WHERE pi.no = ? ";

		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				model.setPolicyNo(rs.getInt("policyNo"));				
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
	
}
