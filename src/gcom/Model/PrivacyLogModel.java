package gcom.Model;

import lombok.Data;

@Data
public class PrivacyLogModel {
	private int ptnNo;
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
	private String processName = "";
	private String patternName = "";
	private String fileName = "";
	private String fileSize = "";
	private String matchedData = "";
	private String foundServerTime = "";
	private String foundClientTime = "";
}
