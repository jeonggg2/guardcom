package gcom.common.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gc.db.DbCon;


public class NotificationObject {
	
	private static final int port = 8805;
	private static final String header = "gcdlp-start:";
	private static final String footer = ":gcdlp-end";
	
	public enum NotificationType {
		
		User("user"),
		Policy("policy"),
		System("systemInfo"),
		DeviceInfo("usbDesc"),
		NetworkPortInfo("netportInfo"),
		PatternSchedule("patternSchedule"),
		IntegritySchedule("integritySchedule"),
		ExportFile("exportFile");
		
		private String typeName;
		
		private NotificationType(String typeName) {
			this.setTypeName(typeName);
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
	}
	
	public static boolean NotifyAllUser(NotificationType type) {

		boolean result = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT ip_addr FROM guardcom.agent_info";
		
		try{
			
			con = DbCon.getConnection();
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Notify(type, rs.getString(1));
			}
			result = true;
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static boolean Notify(NotificationType type, String IpAddress) {
		try {
			return Notify(type, InetAddress.getByName(IpAddress));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean Notify(NotificationType type, InetAddress IpAddress) {

		boolean result = false;
		
		try {
			
			DatagramSocket socket = null;
			
			try {
				socket = new DatagramSocket();
				String packetData = header + type.getTypeName() + footer;				
				socket.send(new DatagramPacket(packetData.getBytes(), packetData.getBytes().length, IpAddress, port));
				result = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					socket.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
}
