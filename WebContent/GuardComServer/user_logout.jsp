<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.InputStreamReader"%>
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
	
	//int length = request.getContentLength();
	DataInputStream dis = new  DataInputStream(request.getInputStream());
	
	// agentNo, userNo
	byte[] buff = new byte[4];
	dis.read(buff);
	int agentNo =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	int userNo =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	if (userNo == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	// sessionId
	byte[] sessionId = new byte[16];
	dis.read(sessionId, 0, 16);
	
	// logout_client_time
	buff = new byte[20]; //length - 24];
	dis.read(buff);
	Timestamp logoutClientTime = Timestamp.valueOf(new String(buff));
	
	/*----------------------------------------------------------------------------------------
	int agentNo = Integer.parseInt(request.getParameter("agentNo"));
	int userNo = Integer.parseInt(request.getParameter("userNo"));
	byte[] sessionId = request.getParameter("sessionId").getBytes();
	Timestamp logoutClientTime = Timestamp.valueOf(request.getParameter("logoutClientTime"));
	----------------------------------------------------------------------------------------*/
	
	int agentLogNo;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		
		con = DbCon.getConnection(userNo, sessionId);
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		con.setAutoCommit(false);
		
		//
		// 에이전트 로그 번호 획득.
		//
		
		pstmt = con.prepareStatement("SELECT no FROM guardcom.agent_log WHERE agent_no = ? ORDER BY no DESC LIMIT 1");
		if (pstmt == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		pstmt.setInt(1, agentNo);
		
		rs = pstmt.executeQuery();
		if (rs == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if (rs.next()) {
			agentLogNo = rs.getInt(1);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		rs.close(); rs = null;
		pstmt.close();
		
		//
		// 로그아웃 로그 저장
		//
		
		pstmt = con.prepareStatement("INSERT INTO guardcom.logout_log (agent_log_no, user_no, logout_client_time, logout_server_time) VALUES (?, ?, ?, NOW())");
		if (pstmt == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		pstmt.setInt(1, agentLogNo);
		pstmt.setInt(2, userNo);
		pstmt.setTimestamp(3, logoutClientTime);
		pstmt.executeUpdate();
		pstmt.close();
		
		//
		// 세션 식별자 초기화
		//
		
		pstmt = con.prepareStatement("UPDATE guardcom.user_info SET session_id= NULL WHERE (session_id = ?) AND (no=?) AND permit = 'P' ");
		if (pstmt == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		pstmt.setBytes(1, sessionId);
		pstmt.setInt(2, userNo);
		pstmt.executeUpdate();
		con.commit();
		
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