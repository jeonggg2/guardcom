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
	
	DataInputStream dis = new DataInputStream(request.getInputStream());
	
	byte[] buff = new byte[length];
	dis.read(buff);
	number = new String(buff);
	
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
		
		stmt = con.prepareStatement("SELECT no, id, pshash FROM guardcom.user_info WHERE number = ? and valid = 1");
		stmt.setNString(1, number);
		
		if (stmt.execute()) {
			
			rs = stmt.getResultSet();
			
			if (rs.next()) {
				
				int userNo = rs.getInt(1);
				String id = rs.getString(2);
				Blob psHash = rs.getBlob(3);
				
				if (id == null) {
						
					// 사용자가 등록되지 않은 경우.
					sos.write(1);
					sos.write((byte)(userNo & 0x000000ff));
					sos.write((byte)((userNo & 0x0000ff00) >> 8));
					sos.write((byte)((userNo & 0x00ff0000) >> 16));
					sos.write((byte)((userNo & 0xff000000) >> 24));
					
				} else {
					
					if ((psHash == null) ||
						(psHash.length() == 0)) {
						
						// 임시로 등록되있는 경우.
						sos.write(2);
						sos.write((byte)(userNo & 0x000000ff));
						sos.write((byte)((userNo & 0x0000ff00) >> 8));
						sos.write((byte)((userNo & 0x00ff0000) >> 16));
						sos.write((byte)((userNo & 0xff000000) >> 24));
						
					} else {
						
						// 이미 사용자가 등록되있는 경우.
						sos.write(0);
						sos.write((byte)(userNo & 0x000000ff));
						sos.write((byte)((userNo & 0x0000ff00) >> 8));
						sos.write((byte)((userNo & 0x00ff0000) >> 16));
						sos.write((byte)((userNo & 0xff000000) >> 24));
						
					}
					
				}
				
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