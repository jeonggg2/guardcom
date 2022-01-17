<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.DataOutputStream"%>
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
	
	
	int contentLength = request.getContentLength();
	if (contentLength <=0) contentLength = 2048;
	
	DataInputStream dis = new  DataInputStream(request.getInputStream());
	byte[] buff = new byte[4];

	// Statement Index
	dis.read(buff);
	int stmtindex =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);	
	
	// Parameter Index
	dis.read(buff);
	int index =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	// Parameter Type
	dis.read(buff);
	int type = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	// Parameter Length
	dis.read(buff);
	int length = ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);
	
	dis.read(buff);
	int isNull =  ((((int)buff[3]) & 0xFF) << 24) | ((((int)buff[2]) & 0xFF) << 16) | ((((int)buff[1]) & 0xFF) << 8) | (((int)buff[0]) & 0xFF);

	int listlength = DbCon.PreparedStatementList.size();
	
	if (listlength == 0) {
		
		listlength = 0;
	}

	DbPrepareStatement pstmt = DbCon.PreparedStatementList.get("Statement" + stmtindex);
	if (pstmt == null) {
		
		if (isNull == 0) {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream(length+1);
	
			int availableLength = dis.available(), totalLength = 0, readCount =0;
			
			byte[] subdata = new byte[availableLength];
			
		
	        while((readCount = dis.read(subdata)) != -1){
	                  	
	        	bos.write(subdata, 0, readCount); 
	                      
	            totalLength += readCount;
	        }				
			
	        bos.close();
		}
        
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return;
	}	
	
	
	// Parameter Data
	if (isNull == 0) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(length+1);

		int availableLength = dis.available(), totalLength = 0, readCount =0;
		
		byte[] subdata = new byte[availableLength];
		
	
        while((readCount = dis.read(subdata)) != -1){
                  	
        	bos.write(subdata, 0, readCount); 
                      
            totalLength += readCount;
        }		
		
        pstmt.bindParameter(index, type, length, bos.toByteArray());
        
        bos.close();
		
	} else {
		
		response.setBufferSize(length);
		pstmt.bindParameter(index, type, length, request.getInputStream());
		
	}
	
%>