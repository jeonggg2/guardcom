<%@page import="java.util.Arrays"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.io.DataInputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="gc.admin.AdminVo"%>
<%@page import="gc.db.DbCon"%>
<%@page import="gc.crypto.CryptoStatus"%>
<%@page import="gc.crypto.CryptoMgr"%>
<%@page import="gc.crypto.PkInfo"%>
<%@page import="gc.crypto.EkekInfo"%>
<%@page import="gc.crypto.PsHashInfo"%>
<%@page import="gc.crypto.SaltInfo"%>
<%@page import="gc.crypto.CekInfo"%>
<%@page import="gcom.common.util.PwCheck"%>
<%

	if(! request.getMethod().equalsIgnoreCase("POST")) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}

	response.setContentType("application/octet-stream");
	response.setHeader("Content-Transfer-Encoding", "binary;");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	if(request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache;");
		response.setHeader("Cache-Control", "no-store;");
	}
	
	byte[] buff = new byte [4];
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());
	dis.read(buff);
	int userNo =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	int passwordLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);

	dis.read(buff);
	int newPasswordLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	if (passwordLength == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}

	if (newPasswordLength == 0) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	// Password
	byte[] password = new byte[passwordLength];
	dis.read(password, 0, passwordLength);
	
	// NewPassword
	byte[] newPassword = new byte[newPasswordLength];
	dis.read(newPassword, 0, newPasswordLength);
/*
	byte[] buff;
	
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Accept-Ranges", "bytes");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	if(request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache;");
		response.setHeader("Cache-Control", "no-store;");
	}
	
	int userNo = Integer.parseInt(request.getParameter("userNo"));
	byte[] password = request.getParameter("password").getBytes("UTF-16LE");
	byte[] newPassword = request.getParameter("newPassword").getBytes("UTF-16LE");
*/	
	
	if (Arrays.equals(password, newPassword)) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	String newPasswordStr = new String(newPassword, "UTF-16LE");
	
	/*
	Pattern pAlpha = null;
	Pattern pNumber = null;
	Pattern pChar = null;
	
	pAlpha = Pattern.compile("[a-zA-Z]");
	pNumber = Pattern.compile("[0-9]");
	pChar = Pattern.compile("\\p{Punct}");
	
	if (newPasswordStr.length() < 10) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	if (! pAlpha.matcher(newPasswordStr).find()) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	if (! pNumber.matcher(newPasswordStr).find()) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	if (! pChar.matcher(newPasswordStr).find()) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	*/
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	CryptoStatus status;
	CryptoMgr cryptoMgr = new CryptoMgr();
	
	try {
		status = cryptoMgr.init();
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize CryptoMgr");
		}
		
		con = DbCon.getConnection();
		con.setAutoCommit(false);
		if (con == null) {
			throw new SQLException("failed to DbCon.getConnection");
		}
		
		pstmt = con.prepareStatement("SELECT salt, pshash, ekek, id, number FROM guardcom.user_info WHERE no =? AND permit = 'P'");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		pstmt.setInt(1, userNo);
		rs = pstmt.executeQuery();
		if (rs == null) {
			throw new SQLException("failed to executeQuery");
		}
		
		if (! rs.next()) {
			throw new SQLException("failed to next");
		}
		
		PwCheck pwCheck = new PwCheck();
		boolean pwCheckResult = pwCheck.Check(newPasswordStr, rs.getString(4)); 
		
		if (!pwCheckResult) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String number = rs.getString(5);

		status = cryptoMgr.authenticatePassword(password, rs.getBytes(1), rs.getBytes(2));
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to authenticatePassword");
		}
// 		rs.close();
// 		pstmt.close();
		
		String userNumber = rs.getString("number");
		pstmt = con.prepareStatement("SELECT SALT, PSHASH FROM pw_user_latest_log WHERE USER_NO = ?");
		
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		pstmt.setString(1, userNumber);
		rs = pstmt.executeQuery();
		
		if (rs == null) {
			throw new SQLException("failed to executeQuery");
		}
		
		while (rs.next()) {
			byte[] salt;
	    	byte[] psHash;
	    	
	    	salt = rs.getBytes(1);
	    	psHash = rs.getBytes(2);
	    	
	    	status = cryptoMgr.authenticatePassword(newPassword, salt, psHash);
	    	
	    	if (status == CryptoStatus.CS_SUCCESS) {
	    		throw new Exception("failed to authenticatePassword");
	    	}
		}
		
		
		SaltInfo saltInfo = new SaltInfo();
		status = saltInfo.init(cryptoMgr, userNo);
		if (status != CryptoStatus.CS_SUCCESS) {
			cryptoMgr.terminate();
			throw new Exception("failed to initialize SaltInfo");
		}

		PsHashInfo psHashInfo = new PsHashInfo();
		status = psHashInfo.init(cryptoMgr, userNo, saltInfo.getSaltData(), newPassword);
		if (status != CryptoStatus.CS_SUCCESS) {
			cryptoMgr.terminate();
			throw new Exception("failed to initialize PsHashInfo");
		}
		
		EkekInfo ekekInfo = new EkekInfo();
		status = ekekInfo.init(cryptoMgr, userNo, psHashInfo.getPsHashData(), newPassword);
		if (status != CryptoStatus.CS_SUCCESS) {
    		cryptoMgr.terminate();	   
    		throw new Exception("failed to initialize EkekInfo");
		}
		
		PkInfo pkInfo = new PkInfo();
		status = pkInfo.init(cryptoMgr, userNo, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), newPassword);
		if (status != CryptoStatus.CS_SUCCESS) {
    		cryptoMgr.terminate();	   
    		throw new Exception("failed to initialize PkInfo");
		}
		
		
		String sql = "UPDATE user_info SET  "
				+ "salt = ?, "
				+ "pshash = ?, "
				+ "ekek = ?, "
				+ "public_key = ?, "
				+ "private_key = ? "
				+ "WHERE no = ?";
		
		pstmt=con.prepareStatement(sql);
		pstmt.setBytes(1, saltInfo.getBytes()); 		
		pstmt.setBytes(2, psHashInfo.getBytes());
		pstmt.setBytes(3, ekekInfo.getBytes());
		pstmt.setBytes(4, pkInfo.getPublicKeyData());
		pstmt.setBytes(5, pkInfo.getPrivateKeyData());
		pstmt.setInt(6, userNo);
		pstmt.executeUpdate();
		
		
		
		sql = "UPDATE cek_info SET cek = ? WHERE USER_NO = ?";
		CekInfo cekInfo = new CekInfo(); 		
		status = cekInfo.init(cryptoMgr, userNo, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), newPassword);
		if (status != CryptoStatus.CS_SUCCESS) {		
    		cryptoMgr.terminate();	   
    		throw new Exception("failed to initialize CekInfo");
		}
		pstmt=con.prepareStatement(sql);
		pstmt.setBytes(1, cekInfo.getBytes());
		pstmt.setInt(2, userNo);
		pstmt.executeUpdate(); 
		
		
		
		// 최근 3개월 내 동일 비밀번호 관리 테이블에 저장.
		pstmt=con.prepareStatement("INSERT INTO pw_user_latest_log (USER_NO, SALT, PSHASH, CHANGE_DATE) SELECT NUMBER, SALT, PSHASH, NOW() FROM user_info WHERE NUMBER = ?  AND PERMIT = 'P' ");
		pstmt.setString(1, number);
		pstmt.executeUpdate();
		
		cryptoMgr.terminate();	  
		
		
		/*
		saltInfo = new SaltInfo(rs.getBytes(1));
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize SaltInfo");
		}
		
		psHashInfo = new PsHashInfo(rs.getBytes(2));
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize PsHashInfo");
		}		
		
		Blob ekek = rs.getBlob(3);
		buff = ekek.getBytes(9, 4);
		int ekekLength = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
		byte[] kekData = new byte[ekekLength + CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
		
		byte[] hashData = new byte[CryptoMgr.CM_DEFAULT_HASH_LENGTH];
		status = cryptoMgr.hashPassword(password, psHashInfo.getPsHashData(), hashData);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to hashPassword");
		}
		
		byte[] paddingBuff = new byte[CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
		for (int i = 0; i < paddingBuff.length; i++) {
			paddingBuff[i] = 16;
		}
		buff = new byte[CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH * 2];
		status = cryptoMgr.encryptData(hashData, CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH, paddingBuff, buff);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to encryptData");
		}
		
		byte[] ekekData = new byte[ekekLength + CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
		System.arraycopy(ekek.getBytes(13, ekekLength), 0, ekekData, 0, ekekLength);
		System.arraycopy(buff, 0, ekekData, ekekLength, CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH);
		status = cryptoMgr.decryptKek(ekekData, psHashInfo.getPsHashData(), password, kekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to decryptKek");
		}
		
		byte[] realKekData = new byte[kekData.length - CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
		System.arraycopy(kekData, 0, realKekData, 0, realKekData.length);
		for (int i = 0; i < kekData.length; i++) {
			kekData[i] = 0;
		}
		
		// 새로운 패스워드를 기반으로 Kek를 암호화하도록 합니다.
		SaltInfo newSaltInfo = new SaltInfo();
		status = newSaltInfo.init(cryptoMgr, userNo);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize SaltInfo");
		}
		
		PsHashInfo newPsHashInfo = new PsHashInfo();
		status = newPsHashInfo.init(cryptoMgr, userNo, newSaltInfo.getSaltData(), newPassword);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize PsHashInfo");
		}
		
		EkekInfo newEkekInfo = new EkekInfo();
		status = newEkekInfo.init(cryptoMgr, userNo, newPsHashInfo.getPsHashData(), newPassword, realKekData);
		for (int i = 0; i < realKekData.length; i++) {
			realKekData[i] = 0;
		}
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize EkekInfo");
		}
				
		rs.close();
		pstmt.close();
		pstmt = con.prepareStatement("UPDATE guardcom.user_info SET salt=?, pshash=?, ekek=? WHERE no=? AND permit = 'P'");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		pstmt.setBytes(1, newSaltInfo.getBytes());
		pstmt.setBytes(2, newPsHashInfo.getBytes());
		pstmt.setBytes(3, newEkekInfo.getBytes());
		pstmt.setInt(4, userNo);
		
		pstmt.executeUpdate();
		*/
		con.commit();
		response.setStatus(HttpServletResponse.SC_OK);
		
	} catch (Exception e) {
		
		System.out.println(e);
		if(con!=null) try{con.rollback();}catch(SQLException sqle){sqle.printStackTrace();}
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {
		
		//cryptoMgr.terminate();
		if (rs != null) rs.close();
		if (pstmt != null) pstmt.close();
		if (con != null) con.close();
		
	}
%>