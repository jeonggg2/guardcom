<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.sql.*"%>
<%@page import="gc.db.*"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.net.UnknownHostException"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	/*
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Accept-Ranges", "bytes");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	int userNo = Integer.parseInt(request.getParameter("userNo"));
	byte[] sessionId = request.getParameter("sessionId").getBytes();
	String sql = request.getParameter("sql");
	*/
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	ServletOutputStream sos;
	
	try {
		
		if (sql.length() <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		con = DbCon.getConnection();
		if (con == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		stmt = con.createStatement();
		if (stmt == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		ResultSetMetaData rsmd;
		int columnCount;
		
		switch (sql.charAt(0) | 0x20) {
		case 'i':
		case 'u':
		case 'd':
			
			ByteArrayOutputStream bab = new ByteArrayOutputStream(128);
			
			int i = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			bab.write((byte)(i & 0x000000ff));
			bab.write((byte)((i & 0x0000ff00) >> 8));
			bab.write((byte)((i & 0x00ff0000) >> 16));
			bab.write((byte)((i & 0xff000000) >> 24));
			int bufferLength = 4;
			
			rs  =  stmt.getGeneratedKeys();
			rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			bab.write((byte)(columnCount & 0x000000ff));
			bab.write((byte)((columnCount & 0x0000ff00) >> 8));
			bab.write((byte)((columnCount & 0x00ff0000) >> 16));
			bab.write((byte)((columnCount & 0xff000000) >> 24));
			bufferLength += 4;
			
			byte[] key;
			
			while (rs.next()) {
				
				for (i = 1; i <= columnCount; i++) {
					
					switch (rsmd.getColumnType(i)) {
					
					case Types.BLOB:						
					case Types.BINARY:
					case Types.ARRAY:
						key = rs.getBytes(i);
						break;

					case Types.INTEGER:
						int ik = rs.getInt(i);
						key = new byte[4];
						key[0] = (byte)(ik & 0x000000ff);
						key[1] = (byte)((ik & 0x0000ff00) >> 8);
						key[2] = (byte)((ik & 0x00ff0000) >> 16);
						break;
						
					case Types.BIGINT:
						long lk = rs.getLong(i);
						key = new byte[8];
						key[0] = (byte)(lk & 0x00000000000000ffL);
						key[1] = (byte)((lk & 0x000000000000ff00L) >> 8);
						key[2] = (byte)((lk & 0x0000000000ff0000L) >> 16);
						key[3] = (byte)((lk & 0x00000000ff000000L) >> 24);
						key[4] = (byte)((lk & 0x000000ff00000000L) >> 32);
						key[5] = (byte)((lk & 0x0000ff0000000000L) >> 40);
						key[6] = (byte)((lk & 0x00ff000000000000L) >> 48);
						key[7] = (byte)((lk & 0xff00000000000000L) >> 56);
						break;
						
					default:
						key = null;
						break;
						
					}
					
					if (key != null) {
						bab.write((byte)(key.length & 0x000000ff));
						bab.write((byte)((key.length & 0x0000ff00) >> 8));
						bab.write((byte)((key.length & 0x00ff0000) >> 16));
						bab.write((byte)((key.length & 0xff000000) >> 24));
						bab.write(key);
						bufferLength += (4 + key.length);
					}
					
				}
				
			}
			
			response.setContentLength(bufferLength);
			
			out.clear();
			out=pageContext.pushBody(); 
			sos = response.getOutputStream();
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
			break;
			
		case 's':
			
			rs = stmt.executeQuery(sql);
			if (rs == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			out.clear();
			sos = response.getOutputStream();
			if (sos == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			
			rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			sos.write((byte)(columnCount & 0x000000ff));
			sos.write((byte)((columnCount & 0x0000ff00) >> 8));
			sos.write((byte)((columnCount & 0x00ff0000) >> 16));
			sos.write((byte)((columnCount & 0xff000000) >> 24));
			
			byte[] data;
			while (rs.next()) {
				
				for (i = 1; i <= columnCount; i++) {
					
					switch (rsmd.getColumnType(i)) {
					
					case Types.TINYINT:
					case Types.BIT:
					case Types.BOOLEAN:
					case Types.CHAR:
						byte b =  rs.getByte(i);
						if (rs.wasNull()) {
							data = null;
						} else {
							data = new byte[1];
							data[0] = b;
						}
						break;
					
					case Types.SMALLINT:
						int is = rs.getShort(i);
						if (rs.wasNull()) {
							data = null;
						} else {
							data = new byte[2];
							data[0] = (byte)(is & 0x00ff);
							data[1] = (byte)((is & 0xff00) >> 8);
						}
						break;
						
					case Types.INTEGER:
						int ik = rs.getInt(i);
						if (rs.wasNull()) {
							data = null;
						} else {
							data = new byte[4];
							data[0] = (byte)(ik & 0x000000ff);
							data[1] = (byte)((ik & 0x0000ff00) >> 8);
							data[2] = (byte)((ik & 0x00ff0000) >> 16);
							data[3] = (byte)((ik & 0xff000000) >> 24);
						}
						break;
					
					case Types.BIGINT:
						long lk = rs.getLong(i);
						if (rs.wasNull()) {
							data = null;
						} else {
							data = new byte[8];
							data[0] = (byte)(lk & 0x00000000000000ffL);
							data[1] = (byte)((lk & 0x000000000000ff00L) >> 8);
							data[2] = (byte)((lk & 0x0000000000ff0000L) >> 16);
							data[3] = (byte)((lk & 0x00000000ff000000L) >> 24);
							data[4] = (byte)((lk & 0x000000ff00000000L) >> 32);
							data[5] = (byte)((lk & 0x0000ff0000000000L) >> 40);
							data[6] = (byte)((lk & 0x00ff000000000000L) >> 48);
							data[7] = (byte)((lk & 0xff00000000000000L) >> 56);
						}
						break;
						
					case Types.LONGNVARCHAR:
					case Types.LONGVARCHAR:
					case Types.VARCHAR:
						String str = rs.getString(i);
						data = (str == null ? null : str.getBytes("EUC-KR"));
						//data = (str == null ? null : str.getBytes());
						break;
						
					case Types.DECIMAL:
					case Types.TIMESTAMP:
					case Types.TIME:
					case Types.DATE:						
					case Types.BLOB:						
					case Types.BINARY:
					case Types.ARRAY:
					default:
						data = rs.getBytes(i);
						break;
					}
					
					if (data == null) {
						sos.write(0);
						sos.write(0);
						sos.write(0);
						sos.write(0);
					} else {
						sos.write((byte)(data.length & 0x000000ff));
						sos.write((byte)((data.length & 0x0000ff00) >> 8));
						sos.write((byte)((data.length & 0x00ff0000) >> 16));
						sos.write((byte)((data.length & 0xff000000) >> 24));
						sos.write(data);
					}
					sos.write(0);
					
				}
				
			}
			
			sos.flush();
			sos.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
			break;
			
		default:
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			break;
		}
		
	} catch (Exception e) {
		
		System.out.println(e);
		e.printStackTrace();
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	} finally {
		
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		
	}
%>