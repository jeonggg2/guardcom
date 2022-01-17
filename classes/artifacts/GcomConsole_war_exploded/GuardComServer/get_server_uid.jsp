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
	
	String number;
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	

	ServletOutputStream sos;

	out.clear();
	out=pageContext.pushBody(); 
	sos = response.getOutputStream();
	if (sos == null) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return;
	}
	
	try {
		
		con = DbCon.getConnection();
		
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		stmt = con.prepareStatement("SELECT id, client_count, maximum_client_count FROM guardcom.server_info order by update_time DESC LIMIT 1");

		
		if (stmt.execute()) {
			
			rs = stmt.getResultSet();
			
			if (rs.next()) {
				
				int ServerUid = rs.getInt(1);
				int ClientCount = rs.getInt(2);
				int MaximumClientCount = rs.getInt(3);				
	
				sos.write(1);				
				sos.write((byte)(ServerUid & 0x000000ff));
				sos.write((byte)((ServerUid & 0x0000ff00) >> 8));
				sos.write((byte)((ServerUid & 0x00ff0000) >> 16));
				sos.write((byte)((ServerUid & 0xff000000) >> 24));
					
				sos.write((byte)(ClientCount & 0x000000ff));
				sos.write((byte)((ClientCount & 0x0000ff00) >> 8));
				sos.write((byte)((ClientCount & 0x00ff0000) >> 16));
				sos.write((byte)((ClientCount & 0xff000000) >> 24));				
				
				sos.write((byte)(MaximumClientCount & 0x000000ff));
				sos.write((byte)((MaximumClientCount & 0x0000ff00) >> 8));
				sos.write((byte)((MaximumClientCount & 0x00ff0000) >> 16));
				sos.write((byte)((MaximumClientCount & 0xff000000) >> 24));
				sos.flush();
				sos.close();
				
				response.setStatus(HttpServletResponse.SC_OK);
				return;
			}
			
		}
		
		sos.write(-1);
		sos.flush();
		sos.close();
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return;
		
	} catch (Exception e) {
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {

		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		
	}
	
%>