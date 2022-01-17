package gcom.Model;

import lombok.Data;

@Data
public class ServerAuditModel {
	private String workIp = "";
	private String adminId = "";
	private int actionId;
	private String parameter = "";
	private String description = "";
	private String status = "";
	private int admin_mode;
}
