package gcom.Model;

import lombok.Data;

@Data
public class CDConnectLogModel {
	private int exportNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private int deptId ;
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";

	private String status = "";

	private String serverTime = "";
	private String clientTime = "";
}
