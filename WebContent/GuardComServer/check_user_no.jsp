<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.sql.*"%>
<%@page import="gc.db.DbCon"%>
<%

	if(! request.getMethod().equalsIgnoreCase("POST")) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}

	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	if(request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache;");
		response.setHeader("Cache-Control", "no-store;");
	}
	
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	/*
	int userNo = Integer.parseInt(request.getParameter("userNo"));
	*/
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	
	DataInputStream dis = new DataInputStream(request.getInputStream());
	
	byte[] buff = new byte[4];
	dis.read(buff);
	int userNo = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	try {
		
		con = DbCon.getConnection();
		
		if (con == null) {
			
			return;
		}
		
		stmt = con.prepareStatement("SELECT 0 FROM guardcom.agent_info WHERE own_user_no = ?");
		stmt.setInt(1, userNo);
		
		if (stmt.execute()) {
			
			rs = stmt.getResultSet();
			
			if (rs.next()) {
				
				out.clear();
				out.write(0);		
				return;
			}
			
		}
		
		out.clear();
		out.write(1);
		
	} catch (Exception e) {
		
		System.out.println("error");
		out.println(e);
		
	} finally {

		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		
	}
	
%>