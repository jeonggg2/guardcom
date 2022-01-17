package gc.db;

import java.io.*;
import java.util.*;
import java.sql.*;
import gc.db.DbPrepareStatement;

public class DbCon {
	
	public static SortedMap<String, DbPrepareStatement> PreparedStatementList;
	
	static {
		
		PreparedStatementList = new TreeMap<String, DbPrepareStatement>();
		
		try {
			
		    Class.forName( "com.mysql.jdbc.Driver" );
		    	
		} catch(ClassNotFoundException ex) {
			
		   ex.printStackTrace();
		   
		}
		
	}
	
	public static Connection getConnection() throws SQLException {

		Connection con = null;
		String ConfigFilePath;
		
		ConfigFilePath = System.getProperty("user.home") + System.getProperty("file.separator") + "GuardComServer" + System.getProperty("file.separator") + "Config.data";
		//ConfigFilePath = System.getProperty("java.home").charAt(0) + ":/GuardComServer/Config.data";
		//ConfigFilePath = "/root/바탕화면/tomcat/webapps/GuardComServer/Config.data";
		
		File file = null;
		InputStream fio = null;
	
		byte[] version = new byte[4];
		byte[] databaseAddress = new byte[64];
		byte[] databaseName = new byte[64];
		byte[] databaseUserName = new byte[32];
		byte[] databasePassword = new byte[32];
		byte[] databasePort = new byte[4];
		String address;
		String id;
		String password;
		int i = 0;
		int x = 0;
		int length = 0;
		
		try {
		
			file = new File(ConfigFilePath);
			
			fio = new FileInputStream(file);

			
			fio.read(version);
			
			for(x=0;x<4;i++,x++) {
				
				version[x] ^= i;
			}
			
			fio.read(databaseAddress);
						
			for(x=0;x<64;i++,x++) {
				
				databaseAddress[x] ^= i;
				
				if (databaseAddress[x] == 0 && length == 0) {
						
					length = x;
				}				
			}
						
			address = new String(databaseAddress, "US-ASCII");
			address = new String(address.getBytes(), 0, length );			
			
			length = 0;
			
			fio.read(databaseName);
			
			for(x=0;x<64;i++,x++) {
				
				databaseName[x] ^= i;
			}			
			
			
			
			fio.read(databaseUserName);
			
			for(x=0,length=0;x<32;i++,x++) {
				
				databaseUserName[x] ^= i;
				
				if (databaseUserName[x] == 0 &&
					length == 0) {
					
					length = x;
				}
			}			
			
			id = new String(databaseUserName, "US-ASCII");//databaseUserName.toString();			
			id = new String(id.getBytes(), 0, length );
			
				
			fio.read(databasePassword);
			
			for(x=0,length=0;x<32;i++,x++) {
				
				databasePassword[x] ^= i;
				
				if (databasePassword[x] == 0 &&
					length == 0) {
					
					length = x;
				}
			}				
			
			password = new String(databasePassword, "US-ASCII");//databasePassword.toString();
			password = new String(password.getBytes(), 0, length );
			
			fio.read(databasePort);

			for(x=0;x<4;i++,x++) {
				
				databasePort[x] ^= i;
			}			
			
			
			int port = ((((int)databasePort[3]) & 0xFF) << 24) | ((((int)databasePort[2]) & 0xFF) << 16) | ((((int)databasePort[1]) & 0xFF) << 8) | (((int)databasePort[0]) & 0xFF);	
			
			
//			 String url2 = "jdbc:mysql://" + address + ":" + Integer.toString( port ) +  "/guardcom?useServerPrepstmts=true";
//			String url2 = "jdbc:mysql://192.168.11.16:" + Integer.toString( port ) +  "/guardcom?useServerPrepstmts=true";
		
//			con = DriverManager.getConnection(url2, id, password);

//			con = DriverManager.getConnection("jdbc:mysql://192.168.11.16:3306/guardcom?useServerPrepstmts=true", "admin", "1234");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guardcom?useServerPrepstmts=true", "root", "1234");

		}	catch (Exception  e) {
			
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			
			if (fio != null)
				
				try {
					
					fio.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		
		if (con == null) {
			
			//con = DriverManager.getConnection("jdbc:mysql://192.168.11.17:3306/guardcom?useServerPrepstmts=true", "admin", "1234");
		}
		
		return con;
		
	}
	
	public static Connection getConnection(int userNo, byte[] sessionId) throws SQLException {
		
		Connection con = getConnection();
		
		if (con == null) {
			return null;
		}
						
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			pstmt = con.prepareStatement("USE guardcom");
			
			if (pstmt == null) {
				
				con.close();
				return null;
			}
			
			pstmt.execute();
			pstmt.close();
			pstmt = null;
			
			/*
			pstmt = con.prepareStatement("SELECT 0 FROM guardcom.user_info WHERE (no = ?) AND (session_id = ?)");
			
			if (pstmt == null) {
				
				return null;
			}
			
			pstmt.setInt(1, userNo);
			pstmt.setBytes(2, sessionId);
			
			if (pstmt.execute()) {
			
				rs = pstmt.getResultSet();
				
				if (rs != null) {
					
					if (rs.next()) {
						*/
			pstmt = con.prepareStatement("SELECT session_id FROM guardcom.user_info WHERE (no = ?)");
			
			if (pstmt == null) {
				
				return null;
			}
			
			pstmt.setInt(1, userNo);
			
			if (pstmt.execute()) {
			
				rs = pstmt.getResultSet();
				
				if (rs != null) {
					
					if (rs.next()) {
						
						//byte[] sid = rs.getBytes(1);
						
						//if (Arrays.equals(sid, sessionId)) {
							
							Connection connection = con; con = null;
							return connection;
						//}
						
					}
				}
				
			}			
			
		} catch (SQLException e) {
					
			System.out.println(e);
			
		} finally {

			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
			
		}
		
		return null;
		
	}
	
}
