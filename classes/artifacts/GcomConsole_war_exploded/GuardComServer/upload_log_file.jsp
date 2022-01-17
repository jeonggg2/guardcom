<%@page import="org.apache.commons.dbcp2.PStmtKey"%>
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
	
	
	
	int length = request.getContentLength();
	if (length <=0) length = 2048;
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());


	
	int fileIdLength = 38;
	byte[] fileIdbuff = new byte[fileIdLength];
	int offset = dis.read(fileIdbuff);
	
	while (offset < fileIdLength) {
		offset += dis.read(fileIdbuff, offset, fileIdLength - offset);
	}

	String fileId = new String(fileIdbuff);
	
	// get log_path;
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	

	con = DbCon.getConnection();
	if (con == null) { return; }
	
	stmt = con.createStatement();
	if (stmt == null) { con.close(); return; }
	
	rs = stmt.executeQuery("SELECT file_path FROM guardcom.log_file_info WHERE file_id = '" + fileId + "'");
	if (rs == null)  { con.close();return; }
	if (! rs.next()) { rs.close(); con.close(); return; }

	String filePath = rs.getString(1);		
	
	stmt.close();
	rs.close();	
	con.close();	
	

	byte[] buff0 = new byte[1];
	dis.read(buff0);	

	
	byte[] buff = new byte[4];
	dis.read(buff);
	int dataLength = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);	
	
	
	try {
			
		FileOutputStream fos = new FileOutputStream( filePath, true );
		
		int availableLength = dis.available(), totalLength = 0, readCount =0;
		
		byte[] data = new byte[availableLength];
		
	
        while((readCount = dis.read(data)) != -1){
                  	
            fos.write(data, 0, readCount); 
            
            totalLength += readCount;
        }		
		
        
        if (dataLength > totalLength) {
        	
        	totalLength = dataLength;
        }
        
        		
		fos.close();
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	} catch (Exception e) {
		
		e.printStackTrace();
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
		
	dis.close();


%>