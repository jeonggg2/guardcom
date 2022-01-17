package gcom.Model;

import lombok.Data;

@Data
public class AuditServerModel {
	private int auditNo;
	private String ipAddr;
	private String adminId = "";
	private String parameter = "";
	private String description ;
	private String auditTime = "";
	private String status = "";
}
