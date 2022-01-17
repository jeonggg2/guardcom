package gcom.Model;

import lombok.Data;

@Data
public class DiskInfoModel {
	private int diskNo;
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

	private int status = -1;

	private String label = "";
	private String guid = "";
	private int type = -1;
	private String hwInfo = "";

	private String createServerTime = "";
	private String createClientTime = "";
}
