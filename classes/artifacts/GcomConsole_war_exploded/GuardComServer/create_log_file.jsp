<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.*"%>
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
	
	
	// get log_path;
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	

	con = DbCon.getConnection();
	if (con == null) { return; }
	
	stmt = con.createStatement();
	if (stmt == null) { con.close(); return; }
	
	rs = stmt.executeQuery("SELECT value FROM guardcom.system_info WHERE name = 'log_path'");
	if (rs == null)  { con.close();return; }
	if (! rs.next()) { rs.close(); con.close(); return; }

	String log_path = rs.getString(1);		
	
	stmt.close();
	rs.close();	
	con.close();
	
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());

	int fileIdLength = length-1;
	byte[] fileIdbuff = new byte[fileIdLength];
	int offset = dis.read(fileIdbuff);
	
	while (offset < fileIdLength) {
		offset += dis.read(fileIdbuff, offset, fileIdLength - offset);
	}

	String fileId = new String(fileIdbuff);
	//String root_path = log_path.charAt(0) + "";
	//String filePath = log_path + "\\" + fileId + ".data";
	
	
	//String root_path = System.getProperty("user.home");	
	//String filePath = log_path + System.getProperty("file.separator") + "GcLog" + System.getProperty("file.separator") + fileId + ".data";	

	String root_path = log_path;
	String filePath = log_path + System.getProperty("file.separator") + fileId + ".data";	
	
	PreparedStatement pstmt = null;
	
	try {
		
		con = DbCon.getConnection();
		if (con == null) {
			return;
		}
		
		pstmt = con.prepareStatement("INSERT INTO `guardcom`.`log_file_info` (`file_id`, `file_path`, `root_path`) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, fileId);
		pstmt.setString(2, filePath);
		pstmt.setString(3, root_path);
		pstmt.executeUpdate();
				
		FileOutputStream fos = new FileOutputStream( filePath );
		
		fos.close();
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	} catch (Exception e) {
		
		e.printStackTrace();
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
	


%>