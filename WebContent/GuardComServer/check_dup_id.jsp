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
	
	String id; // = request.getParameter("id");
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	
	DataInputStream dis = new DataInputStream(request.getInputStream());
	
	byte[] buff = new byte[length];
	dis.read(buff);
	id = new String(buff);
	
	try {
		
		con = DbCon.getConnection();
		
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		stmt = con.prepareStatement("SELECT 0 FROM guardcom.user_info WHERE (id = ?) AND (valid = 1)");
		stmt.setNString(1, id);
		
		if (stmt.execute()) {
			
			rs = stmt.getResultSet();
			
			if (rs.next()) {
				
				out.clear();
				out.write(0);	
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			
		}
		
		out.clear();
		out.write(1);
		response.setStatus(HttpServletResponse.SC_OK);
		
	} catch (Exception e) {
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {

		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		
	}
	
%>