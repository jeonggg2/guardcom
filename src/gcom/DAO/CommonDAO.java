package gcom.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;



public class CommonDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public CommonDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}

	public void setPolicyUpdateToInsertLog(int key) {
		
		int policy_no = key;
		
		String sql= " INSERT INTO policy_log ( "
						+ "agent_log_no, "
						+ "apply_time, "
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
						+ "print_log_descriptor, "
						+ "quarantine_path_access_code, " 
						+ "pattern_file_control, "
						+ "request_server_time, "
						+ "request_client_time "
					+ ") SELECT "
						+ "ai.log_no, "
						+ "NOW(), "
						+ "pi.uninstall_enabled, "
						+ "pi.file_encryption_enabled, "
						+ "pi.cd_encryption_enabled, "
						+ "pi.printer_enabled, "
						+ "pi.cd_enabled, "
						+ "pi.cd_export_enabled, " 
						+ "pi.wlan_enabled, "
						+ "pi.net_share_enabled, "
						+ "pi.web_export_enabled, "
						
						+ "pi.sensitive_dir_enabled, "
						+ "pi.policy_sensitive_file_access, "
						+ "pi.policy_usb_control_enabled, "
						
						+ "pi.removal_storage_export_enabled, "
						+ "pi.removal_storage_admin_mode, "
						+ "pi.usb_dev_list, "
						+ "pi.com_port_list, "
						+ "pi.net_port_list, "
						+ "pi.process_list, "
						+ "pi.file_pattern_list, "
						+ "pi.web_addr_list, "
						+ "pi.msg_block_list, "
						+ "pi.watermark_descriptor, " 
						+ "pi.print_log_descriptor, "
						+ "pi.quarantine_path_access_code, " 
						+ "pi.pattern_file_control, "
						+ "pi.update_server_time, "
						+ "pi.update_client_time "
					+ "FROM policy_info as pi "
					+ "INNER JOIN agent_info AS ai ON pi.no = ai.policy_no "
					+ "WHERE pi.no = ? ";
;
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, policy_no);
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
	
	public String getFileList(HashMap<String, Object> map){
		String result = "";
		String type = map.get("type").toString();
		int no = Integer.parseInt(map.get("no").toString());
		
		String sql= "SELECT "
				+ "file_list FROM "
				+ type
				+ " WHERE no = ? ";
				
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			
			pstmt.setInt(1, no);			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getString("file_list");				
			}
			
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
		
		return result;
		
	}
	
	public String getFilePath(HashMap<String, Object> map){
		String result = "";
		String file_id = map.get("file_id").toString();
		
		String sql= "SELECT "
				+ "file_path FROM "
				+ "log_file_info "
				+ "WHERE file_id = ? ";
				
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			
			pstmt.setString(1, file_id);			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = rs.getString("file_path");				
			}
			
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
		
		return result;
	}
	
}
