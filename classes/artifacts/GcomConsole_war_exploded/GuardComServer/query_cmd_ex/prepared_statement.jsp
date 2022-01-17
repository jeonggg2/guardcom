<%@page import="java.util.Random"%>
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
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());
	byte[] buff = new byte[4];
	
	dis.read(buff);
	int userNo = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	byte[] sessionId = new byte[16];
	dis.read(sessionId, 0, 16);
	
	
	int sqlLength = length - 20;
	byte[] sqlbuff = new byte[sqlLength];
	int offset = dis.read(sqlbuff);
	
	while (offset < sqlLength) {
		offset += dis.read(sqlbuff, offset, sqlLength - offset);
	}

	//String sql = new String(sqlbuff);
	String sql = new String(sqlbuff, "euc-kr");
	sql = sql.replaceAll("`GuardCom`", "`guardcom`");
	
	DbPrepareStatement pstmt = new DbPrepareStatement();
	if (! pstmt.connect(userNo, sessionId, sql)) {
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return;
	}
	
	
	out.clear();
	
	ServletOutputStream sos;
	String StatementNumber, StatementName;
	StatementNumber = String.valueOf(pstmt.hashCode()); ;
	
	StatementName = "Statement" + StatementNumber;
	
	int listlength = DbCon.PreparedStatementList.size();
	
	if (listlength == 0) {
		
		listlength = 0;
	}	
	
	DbPrepareStatement pstmt2 = DbCon.PreparedStatementList.get(StatementName);
	
	if (pstmt2 != null) {

		  String str = "";

		  int d = 0;

		  for (int i = 1; i <= 8; i++){

		    Random r = new Random();

		    d = r.nextInt(9);

		    str = str + Integer.toString(d);

		  }

		StatementName = "Statement" + str;
	}
	
	out.clear();
	out=pageContext.pushBody(); 	
	sos = response.getOutputStream();
	
	sos.write(StatementNumber.getBytes());
	
	sos.flush();
	sos.close();
		
	DbCon.PreparedStatementList.put("Statement" + StatementNumber, pstmt);
	
	response.setStatus(HttpServletResponse.SC_OK);
	
%>