package gcom.itasset.dashboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import gc.db.DbCon;
import gcom.itasset.dashboard.model.ITAssetStatisticModel;

public class ITAssetDashboardDao {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public ITAssetDashboardDao(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public ITAssetStatisticModel getStatisticData(Map<String, Object> map){
		ITAssetStatisticModel data = new ITAssetStatisticModel();
		String whereSql = " ";
		
		List<Integer> oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (List<Integer>) map.get("dept");			
			for (int id : oDept){
				if(idList.length() > 0 )	
					idList.append(",");

				idList.append("?");
			}
		}else{
			return data;
		}

		if(oDept != null)			whereSql += "WHERE ur.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ur.permit = 'P' ";
		
		String sql= 
"SELECT "
+ "t.*, b.* "
+ "FROM ("

+ "SELECT COUNT(*) AS total_cnt, "
+ "COUNT(if( timestampdiff(MINUTE, NOW(), agent.connect_server_time) > 30 , ur.no, null )) AS connect_count,"
+ "COUNT(if(agent.no is not null, ur.no, null)) AS agent_count,"
+ "COUNT(if(agent.install_server_time > curdate(), ur.no, null)) AS today_install_count "
+ "FROM user_info AS ur "
+ "LEFT JOIN agent_info AS agent ON agent.own_user_no = ur.no "
+ "INNER JOIN dept_info AS dept ON dept.no = ur.dept_no "
+  whereSql
+ ") AS t, "

+ "(SELECT COUNT(*) AS contact_count, "
+ "COUNT(if(comment_id is null, ci.contact_id, null)) AS no_comment_contact, "
+ "COUNT(if(ci.reg_dt > curdate(), ci.contact_id, null)) AS today_contact "
+ "FROM user_contact_info as ci "
+ "LEFT JOIN user_contact_comment AS cc ON ci.contact_id = cc.contact_id "
+ "INNER JOIN user_info AS ur ON ur.no = cc.reg_admin_staf_no "
+ whereSql
+ ") AS b ";
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;
			if(oDept != null){
				for(int t = 0; t<oDept.size() ; t++){
					pstmt.setInt(i++, oDept.get(t));
				}
				for(int t = 0; t<oDept.size() ; t++){
					pstmt.setInt(i++, oDept.get(t));
				}
			}
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				
				data.setTotalUserCount(rs.getInt("total_cnt"));				
				data.setConnectAgentCount(rs.getInt("connect_count"));			
				data.setInstalledAgentCount(rs.getInt("agent_count"));				
				data.setTodayInstalledCount(rs.getInt("today_install_count"));		
				
				data.setContactCount(rs.getInt("contact_count"));		
				data.setNonCommentContactCount(rs.getInt("no_comment_contact"));		
				data.setTodayContactCount(rs.getInt("today_contact"));	
				data.setConnectRate();
				data.setInstallRate();
				data.setCommentRate();
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
