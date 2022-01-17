
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="gc.admin.AdminVo"%>
<%@page import="gc.db.DbCon"%>
<%@page import="java.net.DatagramSocket"%>
<%@page import="java.net.DatagramPacket"%>
<%@page import="java.net.InetAddress"%>
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

	String value = null, desc = null, name = null;
	Connection con = null;
	PreparedStatement pstmt = null;

	boolean hasSuccess = false;
	
	try {
		
		value = request.getParameter("value");
		name = request.getParameter("name");
		
		
		con = DbCon.getConnection();	
		if (con == null) {			
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		switch (Integer.parseInt(request.getParameter("mode"))) {	
		case 0:
			desc = "port_descriptor";
			pstmt = con.prepareStatement("UPDATE guardcom.system_info SET value=? WHERE name='port_descriptor'");			
			break;
		case 1:
			desc = "usb_descriptor";
			pstmt = con.prepareStatement("UPDATE guardcom.system_info SET value=? WHERE name='usb_descriptor'");
			break;
		case 2:
			desc = "sensitive_info_schedule";
			pstmt = con.prepareStatement("UPDATE guardcom.system_info SET value=? WHERE name='sensitive_info_schedule'");
			break;
		case 3:
			desc = "integrity_schedule";
			pstmt = con.prepareStatement("UPDATE guardcom.system_info SET value=? WHERE name='integrity_schedule'");
			break;
		default:
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		if (pstmt == null) {			
			out.print("error:오류가 발생했습니다.");
			return;
		}
		
		pstmt.setString(1, value);
		pstmt.execute();	
	
				
		hasSuccess = true;
		
		out.clear();
		out.print("success:성공했습니다.:");
		
		if (name.compareTo("sensitive_info_schedule") == 0) {
			
			//조건에 따라 둘중하나 보내기
			pstmt.close(); pstmt = null;		
			pstmt = con.prepareStatement("SELECT ip_addr FROM guardcom.agent_info");		
			if (pstmt.execute()) {
				DatagramSocket socket = null;
				String data2 = "gcdlp-start:patternSchedule:gcdlp-end";
				ResultSet rs1 = pstmt.getResultSet();			
				if (rs1 != null) {				
					while (rs1.next()) {					
						socket = new DatagramSocket();
						socket.send(new DatagramPacket(data2.getBytes(), data2.getBytes().length, InetAddress.getByName(rs1.getString(1)), 8805));					
					}	
					rs1.close();
				}			
			}
			
		} else if (name.compareTo("integrity_schedule") == 0) {
			
			pstmt.close(); pstmt = null;		
			pstmt = con.prepareStatement("SELECT ip_addr FROM guardcom.agent_info");		
			if (pstmt.execute()) {
				DatagramSocket socket = null;
				String data2 = "gcdlp-start:integritySchedule:gcdlp-end";
				ResultSet rs1 = pstmt.getResultSet();			
				if (rs1 != null) {				
					while (rs1.next()) {					
						socket = new DatagramSocket();
						socket.send(new DatagramPacket(data2.getBytes(), data2.getBytes().length, InetAddress.getByName(rs1.getString(1)), 8805));					
					}	
					rs1.close();
				}			
			}
		}		
		
		return;
		
	} catch (Exception e) {
		out.print("error:오류가 발생했습니다.");
		
	} finally {
		
		if (pstmt != null) pstmt.close();
		
		if (con == null) {
			con = DbCon.getConnection();
		}
	
		if (con != null) {
			PreparedStatement pstmta;
			pstmta = con.prepareStatement("INSERT INTO guardcom.server_audit (ip, id, action_id, parameter, description, status) VALUES (?,?,'0', ?, '시스템 정보 수정 작업'," + (hasSuccess ? "'성공')" : "'실패')") );
			if (pstmta != null) {
				pstmta.setString(1, request.getRemoteAddr());
				pstmta.setString(2, adminVo.getLogin_id());
				pstmta.setString(3, "이름 : " + desc + ", 값 : " + value );
				pstmta.execute();
				pstmta.close();
			}	
		 con.close();
		}
	}
%>
	