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
import gcom.Model.DeptModel;
import gcom.Model.DeptTreeModel;
import gcom.common.util.ConfigInfo;

public class DeptDAO {
	
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public DeptDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public List<DeptModel> getDeptList(int deptNo){
		List<DeptModel> data = new ArrayList<DeptModel>();
		
		String sql= 
"SELECT no, parent, short_name, policy_no from "
+ "(select * from dept_info where valid = 1 order by parent, no) dept_info_sorted,"
+ "(select @pv := ?) initialisation where (find_in_set(parent, @pv) > 0 or no = @pv) and"
+ "@pv := concat(@pv, ',', no);";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  deptNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DeptModel model = new DeptModel();
				model.setDeptNo(rs.getInt("no"));
				model.setParent(rs.getInt("parent"));
				model.setShortName(rs.getString("short_name"));
				model.setPolicyNo(rs.getInt("policy_no"));
				data.add(model);
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		} finally {
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
	
	public List<Integer> getDeptIntList(int deptNo){
		
		List<Integer> data = new ArrayList<Integer>();
		
		String sql= 
"SELECT no from "
+ "(select * from dept_info where valid = 1 order by parent, no) dept_info_sorted,"
+ "(select @pv := ?) initialisation where (find_in_set(parent, @pv) > 0 or no = @pv) and"
+ "@pv := concat(@pv, ',', no);";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  deptNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				data.add(rs.getInt("no")) ;
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		} finally {
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
	
	public List<DeptTreeModel> getDeptListForJSTree(int adminNumber){
		List<DeptTreeModel> data = new ArrayList<DeptTreeModel>();
		
		String sql= 
"SELECT if(no = ? , 0, parent) AS parent,"
+" no, leaf, admin_no, name, short_name, valid, sort_index,child_count, recent_no, min_child_no, max_child_no, policy_no"
+" from "
+" (select * from dept_info where valid = 1 order by parent, no) dept_info_sorted,"
+" (select @pv := ?) initialisation where (find_in_set(parent, @pv) > 0 or no = @pv) and"
+" @pv := concat(@pv, ',', no);";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  adminNumber);
			pstmt.setInt(2,  adminNumber);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DeptTreeModel model = new DeptTreeModel();
				model.setId(Integer.toString(rs.getInt("no")));
				model.setParent( Integer.toString(rs.getInt("parent")));
				model.setText(rs.getString("short_name"));
				model.setData(Integer.toString(rs.getInt("policy_no")));
				data.add(model);
				
				if(rs.getInt("child_count") > 0 ){
					DeptTreeModel _model = new DeptTreeModel();
					_model.setId( "_" + Integer.toString(rs.getInt("no")));
					_model.setParent( Integer.toString(rs.getInt("no")));
					_model.setText(".." + rs.getString("short_name"));					
					data.add(_model);
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
	
	public List<DeptTreeModel> getSelectDeptListForJSTree(int adminNumber){
		List<DeptTreeModel> data = new ArrayList<DeptTreeModel>();
		
		String sql= 
"SELECT if(no = ? , 0, parent) AS parent, "
+" no, leaf, admin_no, name, short_name, valid, sort_index,child_count, recent_no, min_child_no, max_child_no, policy_no "
+" from "
+" (select * from dept_info where valid = 1 order by parent, no) dept_info_sorted, "
+" (select @pv := ?) initialisation where (find_in_set(parent, @pv) > 0 or no = @pv) and "
+" @pv := concat(@pv, ',', no) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  adminNumber);
			pstmt.setInt(2,  adminNumber);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				DeptTreeModel model = new DeptTreeModel();
				model.setId(Integer.toString(rs.getInt("no")));
				model.setParent( Integer.toString(rs.getInt("parent")));
				model.setData(Integer.toString(rs.getInt("policy_no")));
				model.setText(rs.getString("short_name"));
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
	
	
	public List<Integer> getChildDeptIds(int parentId){
		List<Integer> data = new ArrayList<Integer>();
		
		String sql= 
"SELECT "
+ "no "
+ "from "
+ "(select * from dept_info where valid = 1 order by parent, no) dept_info_sorted, "
+ "(select @pv := ?) initialisation "
+ "where (find_in_set(parent, @pv) > 0 or no = @pv) and @pv := concat(@pv, ',', no) ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  parentId);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				data.add(rs.getInt("no"));			
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
	
	public int getDeptMemberCount(int deptId){
		int data = 0;
		String whereSql = "";

		whereSql += "AND ur.dept_no = ? ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM user_info AS ur "
+ "WHERE ur.valid = 1 ";
		
		sql += whereSql;	
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			pstmt.setInt(1, deptId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				data = rs.getInt("cnt");
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
	
	public int getDeptsMemberCount(List<Integer> deptIds){
		int data = 0;
		String whereSql = "";
		StringBuilder idList = new StringBuilder();

		for (@SuppressWarnings("unused") int id : deptIds){
			if(idList.length() > 0 )	
				idList.append(",");

			idList.append("?");
		}
		
		whereSql += "AND ur.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "COUNT(*) AS cnt "
+ "FROM user_info AS ur "
+ "WHERE ur.valid = 1 ";
		
		sql += whereSql;	
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			int i = 1;

			for(int t = 0; t<deptIds.size() ; t++){
				pstmt.setInt(i++, deptIds.get(t));
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				data = rs.getInt("cnt");
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
	
	
	public DeptModel getDeptInfo(int deptId){
	
		DeptModel data = new DeptModel();
		
		String sql= 
"SELECT "
+ "no, "
+ "parent, "
+ "leaf, "
+ "depth, "
+ "admin_no, "
+ "name, "
+ "short_name, "
+ "valid, "
+ "sort_index, "
+ "child_count, "
+ "recent_no, "
+ "min_child_no, "
+ "max_child_no, "
+ "policy_no "
+ "FROM dept_info "
+ "WHERE valid=1 and no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  deptId);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				data.setDeptNo(rs.getInt("no"));
				data.setParent(rs.getInt("parent"));
				data.setLeap(rs.getInt("leaf"));
				data.setDepth(rs.getInt("depth"));
				data.setAdminNo(rs.getInt("admin_no"));
				data.setName(rs.getString("name"));
				data.setShortName(rs.getString("short_name"));
				data.setSortIndex(rs.getInt("sort_index"));
				data.setChildCount(rs.getInt("child_count"));
				data.setRecnetNo(rs.getInt("recent_no"));
				data.setMinChildNo(rs.getInt("min_child_no"));
				data.setMaxChildNo(rs.getInt("max_child_no"));
				data.setPolicyNo(rs.getInt("policy_no"));
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
	
	public HashMap<String, Object> updateDeptNameInfo(DeptModel model){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= 
"UPDATE dept_info SET name = ?, short_name = ? WHERE no = ? ";
		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			pstmt.setString(1,  model.getName());
			pstmt.setString(2,  model.getShortName());
			pstmt.setInt(3,  model.getDeptNo());
			
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
	public HashMap<String, Object> insertDeptInfo(DeptModel model){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int policyNo = 0;
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		String policyInsertSql = "INSERT INTO `guardcom`.`policy_info` (`agent_no`, `log_no`, `update_server_time`, `update_client_time`, `cd_export_enabled`, `removal_storage_export_enabled`, `removal_storage_admin_mode`, `usb_dev_list`, `com_port_list`, `net_port_list`, `process_list`, `file_pattern_list`, `web_addr_list`, `msg_block_list`, `watermark_descriptor`, `print_log_descriptor`, `quarantine_path_access_code`, `pattern_file_control`, `uninstall_enabled_temporarily`, `file_encryption_enabled_temporarily`, `cd_encryption_enabled_temporarily`, `printer_enabled_temporarily`, `cd_enabled_temporarily`, `cd_export_enabled_temporarily`, `wlan_enabled_temporarily`, `net_share_enabled_temporarily`, `web_export_enabled_temporarily`, `sensitive_dir_enabled_temporarily`, `policy_sensitive_file_access_temporarily`, `removal_storage_export_enabled_temporarily`, `removal_storage_admin_mode_temporarily`, `usb_dev_list_temporarily`, `com_port_list_temporarily`, `net_port_list_temporarily`, `process_list_temporarily`, `file_pattern_list_temporarily`, `web_addr_list_temporarily`, `msg_block_list_temporarily`, `watermark_descriptor_temporarily`, `print_log_descriptor_temporarily`, `pattern_file_control_temporarily`) VALUES ('1', '1', now(), now(), b'1', b'1', '1', 'Y', 'Y', 'Y', '', '', 'Y', 'Y', 'N', '0', '', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');";
		String sql= 
"INSERT INTO dept_info "
+ "(parent, leaf, depth, name, short_name, valid, child_count, recent_no, min_child_no, max_child_no, policy_no) "
+ "VALUES ( "
+ "?, "
+ "1, "
+ "?, "
+ "?, "
+ "?, "
+ "1, "
+ "0, "
+ "0, "
+ "?, "
+ "?, "
+ "?"
+ ") ";
	
		
		try{
			
			con = DbCon.getConnection();
			con.setAutoCommit(false);

			pstmt=con.prepareStatement(policyInsertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				policyNo = rs.getInt(1);		
			}
					
			
			pstmt=con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			pstmt.setInt(i++,  model.getParent());			
			pstmt.setInt(i++,  model.getDepth() + 1);
			pstmt.setString(i++,  model.getName());
			pstmt.setString(i++,  model.getShortName());
			pstmt.setInt(i++,  model.getMaxChildNo());
			pstmt.setInt(i++,  model.getMinChildNo());
			pstmt.setInt(i++, policyNo);
			
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				int Key = rs.getInt(1);
				result.put("dept_no", Key);
			}
			
			
			
			sql= "UPDATE dept_info "
					+ "SET leaf = 0, child_count = child_count + 1 "
					+ " WHERE child.no = ? ";

			
			pstmt.setInt(1, model.getParent());
			
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
	
	/* **삭제전 체크사항
	 * 해당 부서의 하위부서가 있는지 체크
	 * 해당 부서의 등록된 사용자가 있는지 체크
	 * 
	 * ** 필요 파라미터
	 *  - 삭제되는 부서 아이디
	 * 	 */
	public HashMap<String, Object> removeDeptInfo(int deptNo){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		

		
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			con.setAutoCommit(false);

			//사용자 존재 체크
			pstmt=con.prepareStatement("SELECT COUNT(*) AS cnt FROM user_info WHERE dept_no = ? AND permit = 'P' ");

			pstmt.setInt(1, deptNo);
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				if(rs.getInt("cnt") > 0){
					result.put("returnCode", ConfigInfo.EXIST_DEPT_USER);
					return result;
				}
			}

			//에이전트 존재 체크
			pstmt=con.prepareStatement("SELECT COUNT(*) AS cnt FROM agent_info WHERE dept_no = ? ");

			pstmt.setInt(1, deptNo);
			
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				if(rs.getInt("cnt") > 0){
					result.put("returnCode", ConfigInfo.EXIST_DEPT_AGENT);
					return result;
				}
			}

			//하위부서 존재 체크
			pstmt=con.prepareStatement("SELECT COUNT(*) AS cnt FROM dept_info WHERE parent = ? AND valid=1 ");

			pstmt.setInt(1, deptNo);
			
			rs = pstmt.executeQuery();
						
			if (rs.next()) {
				if(rs.getInt("cnt") > 0){
					result.put("returnCode", ConfigInfo.EXIST_CHILD_DEPT);
					return result;
				}
			}

			//모두 존재하지 않으면 부서 삭제
			pstmt=con.prepareStatement("UPDATE guardcom.dept_info SET valid = 0 WHERE no = ? ");

			pstmt.setInt(1, deptNo);
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
	

	
}
