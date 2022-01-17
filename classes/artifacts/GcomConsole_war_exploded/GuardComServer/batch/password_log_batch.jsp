<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="gc.db.DbCon"%>
<%
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		con = DbCon.getConnection();
		if (con == null) {
			throw new SQLException("failed to DbCon.getConnection");
		}
		
		pstmt = con.prepareStatement("DELETE FROM pw_user_latest_log WHERE CHANGE_DATE < DATE_ADD(NOW(), INTERVAL -3 MONTH)");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		pstmt.executeUpdate();
		
		Thread.sleep(10000);
		
		pstmt = con.prepareStatement("DELETE FROM pw_admin_latest_log WHERE CHANGE_DATE < DATE_ADD(NOW(), INTERVAL -3 MONTH)");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		pstmt.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
		
	} finally {
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (con != null) con.close();
	}
%>