<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="gc.crypto.*"%>
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
	
	//int length = request.getContentLength();
	//if (length <= 0) length = 1024;
	int length;
	byte[] buff = new byte[4];
	DataInputStream dis = new DataInputStream(request.getInputStream());
	
	int agentNo;
	int idLength;
	int passwordLength;
	
	buff[0] = -1;
	buff[1] = 0;
	buff[2] = 0;
	buff[3] = 0;
	
	dis.read(buff);
	agentNo =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	idLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	passwordLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	if (idLength == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	if (passwordLength == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	// UserId
	byte[] userid = new byte[idLength];
	dis.read(userid, 0, idLength);
	String userId = new String(userid, "UTF-16LE");
	
	// Password
	byte[] password = new byte[passwordLength];
	dis.read(password, 0, passwordLength);
	
	// login_client_time
	buff = new byte[20]; //length - (12 + idLength + passwordLength)];
	dis.read(buff);
	Timestamp loginClientTime = Timestamp.valueOf(new String(buff));	
	
	/*----------------------------------------------------------------------------------------

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
	int agentNo = Integer.parseInt(request.getParameter("agentNo"));
	String userId = request.getParameter("userId");
	byte[] password = request.getParameter("password").getBytes("UTF-16LE");
	Timestamp loginClientTime = Timestamp.valueOf(request.getParameter("loginClientTime"));

	----------------------------------------------------------------------------------------*/
	
	int userNo;
	int agentLogNo;
	CryptoMgr cryptoMgr = new CryptoMgr();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	byte[] salt;
	byte[] psHash;
	byte[] eKek;
	byte[] public_key;
	byte[] private_Key;
	ByteArrayOutputStream cekList = null;
	byte[] sessionId = new byte[16];
	CryptoStatus status;
	
	try {
		
		status = cryptoMgr.init();
		if (status != CryptoStatus.CS_SUCCESS) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;	
		}
		
		con = DbCon.getConnection();
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		con.setAutoCommit(false);
		
		pstmt = con.prepareStatement("SELECT salt, pshash, ekek, public_key, private_key, no FROM guardcom.user_info WHERE (id = ?) AND PERMIT = 'P'");
		if (pstmt == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		pstmt.setString(1, userId);
		
		if (! pstmt.execute()) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		rs = pstmt.getResultSet();
		if (rs == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if (! rs.next()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		userNo = rs.getInt(6);
		salt = rs.getBytes(1);
		psHash = rs.getBytes(2);
		eKek = rs.getBytes(3);
		public_key = rs.getBytes(4);
		private_Key = rs.getBytes(5);
		
		rs.close(); rs = null;
		pstmt.close(); pstmt = null;
		
		// 사용자 인증 수행.
		status = cryptoMgr.authenticatePassword(password, salt, psHash);
		if (status == CryptoStatus.CS_UNAUTHORIZED) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} else if (status != CryptoStatus.CS_SUCCESS) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		//
		// 사용자 CEK 조회.
		//
		
		PreparedStatement pstmt2 = con.prepareStatement("SELECT cek FROM guardcom.cek_info WHERE (user_no = ?) OR (user_no = 0)");
		if (pstmt2 == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		pstmt2.setInt(1, userNo);
		
		ResultSet rs2 = pstmt2.executeQuery();
		if (rs2 == null) {
			pstmt2.close();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		cekList = new ByteArrayOutputStream();
		while (rs2.next()) {
			cekList.write(rs2.getBytes(1));
		}
		cekList.close();
		
		//
		// 세션 식별자 등록.
		//
		
		con.setAutoCommit(false);
		
		pstmt2 = con.prepareStatement("UPDATE guardcom.user_info SET session_id=? WHERE id=? AND permit = 'P'");
		if (pstmt2 == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
				
		//sessionId[0] = 0x20;
		//sessionId[1] = 0x20;
		//sessionId[2] = 0x20;
		//sessionId[3] = 0x20;
		//sessionId[4] = 0x20;
		//sessionId[5] = 0x20;
		//sessionId[6] = 0x20;
		//sessionId[7] = 0x20;
		//sessionId[8] = 0x20;
		//sessionId[9] = 0x20;
		//sessionId[10] = 0x20;
		//sessionId[11] = 0x20;
		//sessionId[12] = 0x20;
		//sessionId[13] = 0x20;
		//sessionId[14] = 0x20;
		//sessionId[15] = 0x20;
		
		status = cryptoMgr.generateRandom(sessionId);
		if (status != CryptoStatus.CS_SUCCESS) {
			pstmt2.close();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		pstmt2.setBytes(1, sessionId);
		pstmt2.setString(2, userId);
		pstmt2.executeUpdate();				
		pstmt2.close();
		
		if (agentNo > 0) {
			
			//
			// 에이전트 로그 번호 획득.
			//
			pstmt2 = con.prepareStatement("SELECT no FROM guardcom.agent_log WHERE agent_no = ? ORDER BY no DESC LIMIT 1");
			if (pstmt2 == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			pstmt2.setInt(1, agentNo);
			rs2 = pstmt2.executeQuery();
			if (rs2 == null) {
				pstmt2.close();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			if (rs2.next()) {
				agentLogNo = rs2.getInt(1);
				rs2.close();
				pstmt2.close();
			} else {
				rs2.close();
				pstmt2.close();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			//
			// 로그인 로그 저장
			//
			
			pstmt2 = con.prepareStatement("INSERT INTO guardcom.login_log (agent_log_no, user_no, login_client_time, login_server_time) VALUES (?, ?, ?, NOW())");
			if (pstmt2 == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			pstmt2.setInt(1, agentLogNo);
			pstmt2.setInt(2, userNo);
			pstmt2.setTimestamp(3, loginClientTime);
			pstmt2.executeUpdate();
			pstmt2.close();
			
		}
		
		con.commit();
		
		//
		// 사용자 정보 반환
		//
		
		int bufferLength = 0;
		ByteArrayOutputStream bab = new ByteArrayOutputStream(response.getBufferSize());
		
		// Session
		bab.write(sessionId);	
		bufferLength += sessionId.length;
		
		byte[] offset = new byte[4];
		
		// Salt Offset는  항상 0이므로 기록할 필요없음.
		
		// PsHash Offset
		length = salt.length;
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);	
		bufferLength += offset.length;
		
		// Ekek Offset
		length += psHash.length;
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);	
		bufferLength += offset.length;
		
		// Cek List Offset
		length += eKek.length;
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);	
		bufferLength += offset.length;
		
		// PublicKey Offset
		length += cekList.size();
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);					
		bufferLength += offset.length;
		
		// PrivateKey Offset
		length += public_key.length;
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);				
		bufferLength += offset.length;
		
		// data length
		length += private_Key.length;
		offset[0] = (byte)(length & 0x000000ff);
		offset[1] = (byte)((length & 0x0000ff00) >> 8);
		offset[2] = (byte)((length & 0x00ff0000) >> 16);
		offset[3] = (byte)((length & 0xff000000) >> 24);
		bab.write(offset);
		bufferLength += offset.length;
		
		// data
		bab.write(salt);
		bufferLength += salt.length;
		
		bab.write(psHash);
		bufferLength += psHash.length;
		
		bab.write(eKek);
		bufferLength += eKek.length;
		
		bab.write(cekList.toByteArray());
		bufferLength += cekList.size();
		
		bab.write(public_key);
		bufferLength += public_key.length;
		
		bab.write(private_Key);
		bufferLength += private_Key.length;
		
		//
		// 정보 출력
		//
		
		response.setContentLength(bufferLength);
		
		out.clear();
		out=pageContext.pushBody(); 
		ServletOutputStream sos = response.getOutputStream();
		if (sos == null) {	
			bab.close();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		sos.write(bab.toByteArray());
		sos.flush();				
		sos.close();
		bab.close();
		
		response.setStatus(HttpServletResponse.SC_OK);
		return;
		
	} catch (Exception e) {
		
		System.out.println(e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {
		
		cryptoMgr.terminate();
		if (cekList != null) cekList.close();
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		
		if (con != null) {
			if (response.getStatus() != HttpServletResponse.SC_OK) con.rollback();
			con.close();
		}
		
	}
%>