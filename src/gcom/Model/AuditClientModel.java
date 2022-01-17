package gcom.Model;

import lombok.Data;

@Data
public class AuditClientModel {
	private int auditNo;
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
	private String moduleName  = "";
	private String description = "";
	private String serverTime = "";
	private String clientTime = "";
	private String status = "";
}
