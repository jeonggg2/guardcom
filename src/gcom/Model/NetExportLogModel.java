package gcom.Model;

import lombok.Data;

@Data
public class NetExportLogModel {
	private int exportNo;

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
	private String protocolType;
	private String destAddr = "";
	private String fileName = "";
	private String notice = "";
	private String fileKey = "";
	private String exportServerTime = "";
	private String exportClientTime = "";
	private String completed = "";

}
