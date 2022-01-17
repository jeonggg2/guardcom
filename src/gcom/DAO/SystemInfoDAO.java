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
import gcom.Model.SystemInfoModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;

public class SystemInfoDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;

	public SystemInfoDAO(){
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}

	public int getSystemInfoListCount(HashMap<String, Object> map){
		int result = 0;

		String whereSql = "WHERE visible = 1 ";

		String sql=
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM system_info AS sys ";

		sql += whereSql;

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


	public List<SystemInfoModel> getSystemInfoList(HashMap<String, Object> map){
		List<SystemInfoModel> data = new ArrayList<SystemInfoModel>();

		String whereSql = "WHERE visible = 1 ";

		whereSql += "ORDER BY sys.description DESC LIMIT ?, ? ";

		String sql=
				"SELECT "
						+ "sys.no AS sys_no, "
						+ "sys.name AS sys_name, "
						+ "sys.value AS value, "
						+ "sys.description AS description "
						+ "FROM system_info AS sys ";

		sql += whereSql;

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;

			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));

			rs = pstmt.executeQuery();

			while(rs.next()){
				SystemInfoModel model = new SystemInfoModel();
				model.setSysNo(rs.getInt("sys_no"));
				model.setName(rs.getString("sys_name"));
				model.setDescription(rs.getString("description"));
				model.setValue(rs.getString("value"));
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

	// 사용자 패스워드 패턴 설정 값 불러오기
	public List<SystemInfoModel> getPwPatternList(HashMap<String, Object> map){
		List<SystemInfoModel> data = new ArrayList<SystemInfoModel>();

		String sql=
				"SELECT "
						+ "upper_char_enabled, "
						+ "number_enabled, "
						+ "special_char_enabled, "
						+ "equal_char_enabled, "
						+ "consecutive_char_enabled, "
						+ "include_id_char_enabled, "
						+ "min_cnt,"
						+ "max_cnt "
						+ "FROM pw_pattern_info ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				SystemInfoModel model = new SystemInfoModel();
				model.setUpperCharEnabled(rs.getInt("upper_char_enabled"));
				model.setNumberEnabled(rs.getInt("number_enabled"));
				model.setSpecialCharEnabled(rs.getInt("special_char_enabled"));
				model.setEqualCharEnabled(rs.getInt("equal_char_enabled"));
				model.setConsecutiveCharEnabled(rs.getInt("consecutive_char_enabled"));
				model.setIncludeIdCharEnabled(rs.getInt("include_id_char_enabled"));
				model.setMinCnt(rs.getInt("min_cnt"));
				model.setMaxCnt(rs.getInt("max_cnt"));
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


	public HashMap<String, Object> updateSystemInfo(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		int system_no = Integer.parseInt(map.get("system_no").toString());
		String value = map.get("value").toString();

		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;

		String insertSql= "UPDATE system_info SET  "
				+ "value = ? ";

		insertSql += "WHERE no = ? ";

		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		try{
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			pstmt.setString(i++, value);
			pstmt.setInt(i++, system_no);
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

	public HashMap<String, Object> updatePwPatternInfo(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		int upper_char_enabled = Integer.parseInt(map.get("upper_char_enabled").toString());
		int number_enabled = Integer.parseInt(map.get("number_enabled").toString());
		int special_char_enabled = Integer.parseInt(map.get("special_char_enabled").toString());
		int equal_char_enabled = Integer.parseInt(map.get("equal_char_enabled").toString());
		int consecutive_char_enabled = Integer.parseInt(map.get("consecutive_char_enabled").toString());
		int include_id_char_enabled = Integer.parseInt(map.get("include_id_char_enabled").toString());
		int min_cnt = Integer.parseInt(map.get("min_cnt").toString());
		int max_cnt = Integer.parseInt(map.get("max_cnt").toString());

		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;

		String insertSql= "UPDATE pw_pattern_info SET  "
				+ "upper_char_enabled = ?, "
				+ "number_enabled = ?, "
				+ "special_char_enabled = ?, "
				+ "equal_char_enabled = ?, "
				+ "consecutive_char_enabled = ?, "
				+ "include_id_char_enabled = ?, "
				+ "min_cnt = ?, "
				+ "max_cnt = ? ";

		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		try{
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(insertSql, java.sql.Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			pstmt.setInt(i++, upper_char_enabled);
			pstmt.setInt(i++, number_enabled);
			pstmt.setInt(i++, special_char_enabled);
			pstmt.setInt(i++, equal_char_enabled);
			pstmt.setInt(i++, consecutive_char_enabled);
			pstmt.setInt(i++, include_id_char_enabled);
			pstmt.setInt(i++, min_cnt);
			pstmt.setInt(i++, max_cnt);
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

	public int serverLogoutTimeInfo() {
		int result = 600000;

		String selectSql= "SELECT value FROM system_info WHERE name = 'console_time_out' ";

		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();

			pstmt=con.prepareStatement(selectSql);
			rs = pstmt.executeQuery();

			if(rs.next()){
				result = rs.getInt("value");
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
