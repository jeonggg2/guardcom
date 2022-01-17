package gcom.Model;

import lombok.Data;

@Data
public class NetPortLogModel {
	private int logNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private String userNumber = "";
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	
	private String processName = "";
	private String port;
	private String description = "";
	private String control = "";
	private String serverTime = "";
	private String clientTime = "";
  
}
