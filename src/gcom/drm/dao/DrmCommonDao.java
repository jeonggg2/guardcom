package gcom.drm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gc.db.DbCon;

public class DrmCommonDao {
	
	public boolean appendValue(String name, String value) {
		String list;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement("SELECT value FROM system_info WHERE name = ?");
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = rs.getString(1).replaceAll(value + ";", "").replaceAll(";;", ";") + value + ";";
				pstmt.close();
				pstmt = con.prepareStatement("UPDATE system_info SET value = ? WHERE name = ?");
				pstmt.setString(1, list);
				pstmt.setString(2, name);
				pstmt.executeUpdate();
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
		return true;
	}
	

	public boolean removeValue(String name, String value) {
		String list;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try{
			con = DbCon.getConnection();
			pstmt=con.prepareStatement("SELECT value FROM system_info WHERE name = ?");
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = rs.getString(1).replaceAll(value + ";", "").replaceAll(";;", ";");
				pstmt.close();
				pstmt = con.prepareStatement("UPDATE system_info SET value = ? WHERE name = ?");
				pstmt.setString(1, list);
				pstmt.setString(2, name);
				pstmt.executeUpdate();
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
		return true;
	}
	
	
	public List<String> getSystemInfo(String name){
		
		List<String> data = null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try{
			
			con = DbCon.getConnection();
			pstmt=con.prepareStatement("SELECT value FROM system_info WHERE name = ?");
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String list = rs.getString(1).replaceAll(";;", ";");
				if (list.length() > 0) {
					data = Arrays.asList(list.split(";"));
				} else {
					data = new ArrayList<String>();
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
	
}
