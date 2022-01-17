<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="gc.admin.AdminVo"%>
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
	
	HttpSession Session = request.getSession(false);
	AdminVo adminVo = (AdminVo)application.getAttribute(request.getParameter("lid"));
	
	if (adminVo == null) return;	

	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		
		con = DbCon.getConnection();	
		if (con == null) {			
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		stmt = con.createStatement();
		if (stmt == null) {			
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		switch (Integer.parseInt(request.getParameter("mode"))) {	
		case 0:
			rs = stmt.executeQuery("SELECT value FROM guardcom.system_info WHERE name = 'port_descriptor'");			
			break;
		case 1:
			rs = stmt.executeQuery("SELECT value FROM guardcom.system_info WHERE name = 'usb_descriptor'");
			break;
		case 2:
			rs = stmt.executeQuery("SELECT value FROM guardcom.system_info WHERE name = 'sensitive_info_schedule'");
			break;
		case 3:
			rs = stmt.executeQuery("SELECT value FROM guardcom.system_info WHERE name = 'integrity_schedule'");
			break;
		default:
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		if (rs == null) {
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		if (rs.next()) {
			out.clear();
			out.print("success:성공했습니다.:" + rs.getString(1));
			return;
		}
		
	} catch (Exception e) {
		
		out.print("error:오류가 발생했습니다.");
		
	} finally {
		
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
	}
	
%>
	
