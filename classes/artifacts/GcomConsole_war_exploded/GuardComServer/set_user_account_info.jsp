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
	int idLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	int passwordLength =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
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
	
	int userNo = Integer.parseInt(request.getParameter("userNo"));
	String userId = request.getParameter("userId");
	byte[] password = request.getParameter("password").getBytes("UTF-16LE");
	----------------------------------------------------------------------------------------*/
	SaltInfo saltInfo;
	PsHashInfo psHashInfo;
	EkekInfo ekekInfo;
	PkInfo pkInfo;
	CekInfo cekInfo;
	
	Connection con = null;
	PreparedStatement pstmt = null;

	CryptoStatus status;
	CryptoMgr cryptoMgr = new CryptoMgr();
	
	try {

		status = cryptoMgr.init();
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize CryptoMgr");
		}
		
		saltInfo = new SaltInfo();
		status = saltInfo.init(cryptoMgr, userNo);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize SaltInfo");
		}
		
		psHashInfo = new PsHashInfo();
		status = psHashInfo.init(cryptoMgr, userNo, saltInfo.getSaltData(), password);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize PsHashInfo");
		}
		
		ekekInfo = new EkekInfo();
		status = ekekInfo.init(cryptoMgr, userNo, psHashInfo.getPsHashData(), password);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize EkekInfo");
		}
		
		pkInfo = new PkInfo();
		status = pkInfo.init(cryptoMgr, userNo, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), password);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to initialize PkInfo");
		}
		
		con = DbCon.getConnection();
		if (con == null) {
			throw new SQLException("failed to DbCon.getConnection");
		}
		con.setAutoCommit(false);
		
		pstmt = con.prepareStatement("UPDATE guardcom.user_info SET id=?, salt=?, pshash=?, ekek=?, public_key=?, private_key=? WHERE no=?");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		pstmt.setString(1, userId);
		pstmt.setBytes(2, saltInfo.getBytes());
		pstmt.setBytes(3, psHashInfo.getBytes());
		pstmt.setBytes(4, ekekInfo.getBytes());
		pstmt.setBytes(5, pkInfo.getPublicKeyData());
		pstmt.setBytes(6, pkInfo.getPrivateKeyData());
		pstmt.setInt(7, userNo);
		
		pstmt.executeUpdate();
		pstmt.close();
		pstmt = null;
		
		//
		// 최초 CEK 등록
		//
		
		pstmt = con.prepareStatement("INSERT INTO guardcom.cek_info(no, user_no, cek) VALUES (0, ?, ?)");
		if (pstmt == null) {
			throw new SQLException("failed to preparedStatement");
		}
		
		cekInfo = new CekInfo();
		status = cekInfo.init(cryptoMgr, userNo, 0, ekekInfo.getEkekData(), psHashInfo.getPsHashData(), password);
		if (status != CryptoStatus.CS_SUCCESS) {
			throw new Exception("failed to Initialize CekInfo");
		}
		
		pstmt.setInt(1, userNo);
		pstmt.setBytes(2, cekInfo.getBytes());
		pstmt.executeUpdate();
		con.commit();
		
		out.print("seccess:사용자 정보가 추가되었습니다.");
		
	} catch (Exception e) {
		
		if (con != null) con.rollback();
		System.out.println(e);
		out.print("error:오류가 발생했습니다.");
		
	} finally {
		
		//cryptoMgr.terminate();
		if (pstmt != null) pstmt.close();
		if (con != null) con.close();
		
	}
%>