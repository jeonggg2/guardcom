<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.sql.*"%>
<%@page import="gc.db.*"%>
<%
	
	if(! request.getMethod().equalsIgnoreCase("POST")) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Accept-Ranges", "bytes");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	if(request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache;");
		response.setHeader("Cache-Control", "no-store;");
	}
	
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());
	byte[] buff = new byte[4];

	// Statement Index
	dis.read(buff);
	int stmtindex =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);	
	
	int listlength = DbCon.PreparedStatementList.size();
	
	if (listlength == 0) {
		
		listlength = 0;
	}	
	
	DbPrepareStatement pstmt = DbCon.PreparedStatementList.get("Statement" + stmtindex);
	
	if (pstmt == null) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return;
	}
	
	out.clear();
	
	try {
		if (pstmt.execute(response)) response.setStatus(HttpServletResponse.SC_OK);
	} catch (Exception e) {
		e.printStackTrace();
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	DbCon.PreparedStatementList.remove("Statement" + stmtindex);
%>