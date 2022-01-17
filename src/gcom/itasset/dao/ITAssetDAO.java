package gcom.itasset.dao;

import java.lang.reflect.Field;
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
import gcom.common.db.Table;
import gcom.common.util.ConfigInfo;
import gcom.itasset.model.ITAssetHwModel;
import gcom.itasset.model.ITAssetSwModel;

public class ITAssetDAO {
	DataSource ds;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	
	public ITAssetDAO(){ 
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/mysql");			
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public List<ITAssetHwModel> getDeviceList(HashMap<String, Object> map) {
		List<ITAssetHwModel> data = new ArrayList<ITAssetHwModel>();
		
		String whereSql = "WHERE 1=1 AND ui.valid = 1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();
		String sw_name = map.get("sw_name").toString();
		String hw_name = map.get("hw_name").toString();
		
		String[] oDept = null;
		StringBuilder idList = new StringBuilder();
		
		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (@SuppressWarnings("unused") String id : oDept){
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
		if(!sw_name.equals("")) whereSql += "AND si.sw_list LIKE ? ";
		if(!hw_name.equals("")) whereSql += "AND si.hw_list LIKE ? ";
		if(oDept != null) whereSql += "AND ai.dept_no in ("+idList+") ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
		
		whereSql += "AND si.hw_list is not null ORDER BY ui.no DESC LIMIT ?, ? ";	
		
		String sql= 
			"SELECT "
				+ "ai.no as agentNo, "
			    + "ui.number as userNo, "
			    + "ui.no as uno, "
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
			    + "si.hw_list as hw_list, "
			    + "si.sw_list as sw_list "
			    
			+ "FROM agent_info AS ai "
			+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
			+ "LEFT JOIN device_info AS si ON si.agent_no = ai.no "
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
			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			if(!hw_name.equals("")) pstmt.setString(i++, "%" + hw_name + "%");
			
			if(oDept != null){
				for(int t = 0; t<oDept.length ; t++){
					pstmt.setInt(i++, Integer.parseInt(oDept[t]));
				}
			}
		
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ITAssetHwModel model = new ITAssetHwModel();
				model.setAgentNo(rs.getInt("agentNo"));
				model.setUserNo(rs.getString("userNo"));
				model.setUno(rs.getInt("uno"));
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
				model.setHwList(rs.getString("hw_list"));				
				model.setSwList(rs.getString("sw_list"));
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


	public int getDeviceListCount(HashMap<String, Object> map) {
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String user_id = map.get("user_id").toString();
		String user_name = map.get("user_name").toString();
		
		String user_phone = map.get("user_phone").toString();
		String user_duty = map.get("user_duty").toString();
		String user_rank = map.get("user_rank").toString();
		String user_number = map.get("user_number").toString();
		String user_pc = map.get("user_pc").toString();
		String user_ip = map.get("user_ip").toString();
		String sw_name = map.get("sw_name").toString();
		String hw_name = map.get("hw_name").toString();
		
		String[] oDept = null;
		StringBuilder idList = new StringBuilder();

		if(map.containsKey("dept") && map.get("dept") != null){
			oDept = (String[])map.get("dept");			
			for (@SuppressWarnings("unused") String id : oDept){
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
		if(!sw_name.equals("")) whereSql += "AND si.sw_list LIKE ? ";
		if(!hw_name.equals("")) whereSql += "AND si.hw_list LIKE ? ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
				
		if(oDept != null)			whereSql += "AND ai.dept_no in ("+idList+") ";
		
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM agent_info AS ai "
						+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
						+ "LEFT JOIN device_info AS si ON ai.no = si.agent_no ";

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
			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			if(!hw_name.equals("")) pstmt.setString(i++, "%" + hw_name + "%");
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
	
	public List<String> getSwNameList() {
		
		List<String> data = new ArrayList<String>();
		
		String sql= 
				"SELECT "
				    + "name "
				+ "FROM sw_list ";
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);			
			rs = pstmt.executeQuery();				
			while(rs.next()){
				data.add(rs.getString("name"));
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
	
	public List<ITAssetSwModel> getSwList(HashMap<String, Object> map) {
		
		List<ITAssetSwModel> data = new ArrayList<ITAssetSwModel>();
		
		String whereSql = "WHERE 1=1 ";
		
		String name = map.get("name").toString();
		String type = map.get("type").toString();		
		String has_own = map.get("has_own").toString();		
		String start_date = map.get("start_date").toString();
		String end_date = map.get("end_date").toString();
		String has_commited = map.get("has_commited").toString();
		String has_managed = map.get("has_managed").toString();
		
		if(!name.equals("")) whereSql += "AND name LIKE ? ";
		if(!type.equals("")) whereSql += "AND type LIKE ? ";
		if(!has_own.equals("")) whereSql += "AND has_own = ? ";
		
		if (! has_commited.equals("")) {
			if (has_commited.equals("0")) {
				whereSql += "AND commit_date is null ";	
			} else {
				whereSql += "AND commit_date is not null ";
			}
		}
		
		if(!has_managed.equals("")) whereSql += "AND has_managed = ? ";
		if(!start_date.equals("")) 	whereSql += "AND intro_date >= ? ";
		if(!end_date.equals("")) 	whereSql += "AND intro_date < ? + interval 1 day ";
		
		whereSql += "ORDER BY commit_date, no ASC LIMIT ?, ? ";	
		
		String sql= 
			"SELECT "
				+ "no, "
			    + "name, "
			    + "type, "
			    + "`desc`, "
			    + "intro_date, "
			    + "has_own, "
			    + "has_managed, "
			    + "commit_date "
			+ "FROM sw_list ";
		
		sql += whereSql;
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
		
			int i = 1;
			if(!name.equals("")) pstmt.setString(i++, "%" + name + "%");
			if(!type.equals("")) pstmt.setString(i++, type);
			if(!has_own.equals("")) pstmt.setString(i++, has_own);
			if(!has_managed.equals("")) pstmt.setString(i++, has_managed);
			if(!start_date.equals("")) 	pstmt.setString(i++, start_date);
			if(!end_date.equals("")) 	pstmt.setString(i++, end_date);
			
			pstmt.setInt(i++,  Integer.parseInt(map.get("startRow").toString()));
			pstmt.setInt(i++,  Integer.parseInt(map.get("endRow").toString()));
			
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				
				ITAssetSwModel model = new ITAssetSwModel();
				model.setNo(rs.getInt("no"));
				model.setName(rs.getString("name"));
				model.setType(rs.getInt("type"));
				model.setHasOwn(rs.getInt("has_own"));
				model.setDesc(rs.getString("desc"));
				model.setDesc(rs.getString("desc"));
				model.setIntroDate(rs.getTimestamp("intro_date"));
				model.setCommitDate(rs.getTimestamp("commit_date"));
				model.setHasManaged(rs.getInt("has_managed"));
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

	public int getSwListCount(HashMap<String, Object> map) {
		
		int result = 0;
		String whereSql = "WHERE 1=1 ";
		
		String name = map.get("name").toString();
		String type = map.get("type").toString();		
		String has_own = map.get("has_own").toString();		
		
		if(! name.equals("")) whereSql += "AND name LIKE ? ";
		if(! type.equals("")) whereSql += "AND type = ? ";
		if(! has_own.equals("")) whereSql += "AND has_own = ? ";
		if (! map.get("has_commited").equals("")) {
			if (Boolean.parseBoolean(map.get("has_commited").toString())) {
				whereSql += "AND commit_date is not null ";
			} else {
				whereSql += "AND commit_date is null ";
			}
		}
		
		whereSql += "ORDER BY commit_date, no ASC";	
		
		String sql= 
			"SELECT "
			+ "COUNT(*) AS cnt "	
			+ "FROM sw_list ";
		
		sql += whereSql;
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
		
			int i = 1;
			if(!name.equals("")) pstmt.setString(i++, "%" + name + "%");
			if(!type.equals("")) pstmt.setString(i++, type);
			if(!has_own.equals("")) pstmt.setString(i++, has_own);		
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
	
	public <T> T getInfo(int no, Class<T> type) {
		
		T result = null;
		
		String sql= "SELECT ";
		
		gcom.common.db.Field annField;
		String fieldName = null;
		
		for (Field f : type.getDeclaredFields()) {
			
			if (fieldName != null) {
				sql += ", ";
			}
			
			annField = f.getDeclaredAnnotation(gcom.common.db.Field.class);
			
			if (annField == null) {
				continue;
			}

			fieldName = "`" + annField.value() + "`";			
			sql += fieldName;
		}
		
		Table annTable = type.getDeclaredAnnotation(Table.class);
		sql += " FROM `" + annTable.value() + "` WHERE `" + annTable.primaryFieldName() + "` = ?";
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);			
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				
				try {
					result = type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				Object value;
				
				for (Field f : type.getDeclaredFields()) {
					
					annField = f.getDeclaredAnnotation(gcom.common.db.Field.class);
					
					if (annField == null) {
						continue;
					}
					
					value = rs.getObject(annField.value());
					
					try {
						f.setAccessible(true);
						f.set(result, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
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
		
		return result;	
	}
	
	public HashMap<String, Object> modifySw(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int no = Integer.parseInt(map.get("no").toString());
		
		String sql= "UPDATE sw_list SET ";
		String name = null;
		String desc = null;
		int type = -1;
		int has_own = -1;
		String intro_date = null;
		int has_managed = -1;
		
		if (map.get("name") != null) {
			name = map.get("name").toString(); 
			sql += "name = ?, ";	
		}
		
		if (map.get("desc") != null) {
			desc = map.get("desc").toString(); 
			sql += "`desc` = ?, ";	
		}
		
		if (map.get("type") != null) {
			sql += "type = ?, ";	
			type = Integer.parseInt(map.get("type").toString());
		}
		
		if (map.get("has_own") != null) {
			sql += "has_own = ?, ";	
			has_own = Integer.parseInt(map.get("has_own").toString());
		}
		
		if (map.get("has_managed") != null) {
			sql += "has_managed = ?, ";	
			has_managed = Integer.parseInt(map.get("has_managed").toString());
		}
		
		if (map.get("intro_date") != null) {
			intro_date = map.get("intro_date").toString();
			if (intro_date.equals("")) {
				intro_date = null;
				sql += "`intro_date` = null, ";
			} else {
				sql += "`intro_date` = ?, ";
			}
		}
		
		boolean is_commit = Boolean.parseBoolean(map.get("is_commit").toString());
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		if (is_commit) sql += " commit_date = NOW(), ";
		sql += "no=no WHERE no = ?";
		
		int i = 1;
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			if (name != null) {
				pstmt.setString(i++, name);
			}
			if (desc != null) pstmt.setString(i++, desc);
			if (type != -1) pstmt.setInt(i++, type);
			if (has_own != -1) pstmt.setInt(i++, has_own);
			if (has_managed != -1) pstmt.setInt(i++, has_managed);
			if (intro_date != null) pstmt.setString(i++, intro_date);
			pstmt.setInt(i++, no);
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
		
	public HashMap<String, Object> unregisterSw(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		
		int no = Integer.parseInt(map.get("no").toString());
		
		String sql = "DELETE FROM sw_list WHERE no = ?";
		
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, no);
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

	public HashMap<String, Object> registerSw(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String name = map.get("name").toString();
		String desc = null;
		int type = -1;
		int has_own = -1;
		String intro_date = null;
		

		if (map.containsKey("desc")) desc = map.get("desc").toString();
		if (map.containsKey("type")) type = Integer.parseInt(map.get("type").toString());
		if (map.containsKey("has_own")) has_own = Integer.parseInt(map.get("has_own").toString());
		if (map.containsKey("intro_date")) {
			if (map.get("intro_date").equals("")) {
				map.remove("intro_date");
			} else {
				intro_date = map.get("intro_date").toString();
			}
		}
		
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		String sql= "INSERT INTO sw_list (name, ";
		
		if (map.containsKey("desc")) sql += "`desc`, ";
		if (map.containsKey("type")) sql += "type, ";
		if (map.containsKey("has_own")) sql += "has_own, ";
		if (map.containsKey("intro_date")) sql += "intro_date, ";
		
		sql +="commit_date) VALUES (?,";
		
		if (map.containsKey("desc")) sql += "?, ";
		if (map.containsKey("type")) sql += "?, ";
		if (map.containsKey("has_own")) sql += "?, ";
		if (map.containsKey("intro_date")) sql += "?, ";
		
		if (map.containsKey("is_not_commit")) {
			sql += " null) ";
		} else {
			sql += " NOW()) ";	
		}
		try{
			con = DbCon.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, name);
			
			int i = 2;
			if (map.containsKey("desc")) pstmt.setString(i++, desc);
			if (map.containsKey("type")) pstmt.setInt(i++, type);
			if (map.containsKey("has_own")) pstmt.setInt(i++, has_own);
			if (map.containsKey("intro_date")) pstmt.setString(i++, intro_date);
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

	public List<ITAssetHwModel> getUsageList(HashMap<String, Object> map) {
		List<ITAssetHwModel> data = new ArrayList<ITAssetHwModel>();
		
		String whereSql = "WHERE 1=1 AND ui.valid = 1 ";
		
		String sw_name = map.get("sw_name").toString();
		
		if(!sw_name.equals("")) whereSql += "AND si.sw_list LIKE ? OR si.hw_list LIKE ? ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
		
		whereSql += "ORDER BY ui.no DESC";	
		
		String sql= 
			"SELECT "
				+ "ai.no as agentNo, "
			    + "ui.number as userNo, "
			    + "ui.no as uno, "
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
			    + "si.hw_list as hw_list, "
			    + "si.sw_list as sw_list "
			    
			+ "FROM agent_info AS ai "
			+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
			+ "LEFT JOIN device_info AS si ON si.agent_no = ai.no "
			+ "INNER JOIN dept_info as di ON ai.dept_no = di.no ";

		sql += whereSql;
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
		
			int i = 1;
			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ITAssetHwModel model = new ITAssetHwModel();
				model.setAgentNo(rs.getInt("agentNo"));
				model.setUserNo(rs.getString("userNo"));
				model.setUno(rs.getInt("uno"));
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
				model.setHwList(rs.getString("hw_list"));				
				model.setSwList(rs.getString("sw_list"));
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


	public int getUsageListCount(HashMap<String, Object> map) {
		int result = 0;
		
		String whereSql = "WHERE 1=1 ";
		String sw_name = map.get("sw_name").toString();
		
		if(!sw_name.equals("")) whereSql += "AND si.sw_list LIKE ? OR si.hw_list LIKE ? ";
		
		// user_info 조인 permit 조건 추가	
		whereSql += " AND ui.permit = 'P' ";
	
		String sql= 
				"SELECT "
						+ "COUNT(*) AS cnt "
						+ "FROM agent_info AS ai "
						+ "INNER JOIN user_info AS ui ON ai.own_user_no = ui.no "
						+ "LEFT JOIN device_info AS si ON ai.no = si.agent_no ";

		sql += whereSql;			
			
		try{
			//con = ds.getConnection();
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);

			int i = 1;

			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			if(!sw_name.equals("")) pstmt.setString(i++, "%" + sw_name + "%");
			
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
