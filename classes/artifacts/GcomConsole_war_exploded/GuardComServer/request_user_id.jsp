<%@page import="java.util.Arrays"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="gc.db.DbCon"%>
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
	
	int length;
	byte[] buff = new byte[4];
	DataInputStream dis = new DataInputStream(request.getInputStream());
	
	int userNo;
	int idLength;
	
	dis.read(buff);
	userNo =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	idLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	if (idLength == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
		
	// UserId
	byte[] userid = new byte[idLength];
	dis.read(userid, 0, idLength);
	String userId = new String(userid, "UTF-16LE");
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		
		con = DbCon.getConnection();
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		pstmt = con.prepareStatement("UPDATE guardcom.user_info SET id=? WHERE no=?");
		if (pstmt == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		pstmt.setString(1, userId);
		pstmt.setInt(2, userNo);
		pstmt.executeUpdate();
		
		response.setStatus(HttpServletResponse.SC_OK);
		return;
		
	} catch (Exception e) {
		
		System.out.println(e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {
		
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		
		if (con != null) {
			if (response.getStatus() != HttpServletResponse.SC_OK) con.rollback();
			con.close();
		}
		
	}
%>