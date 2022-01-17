package gcom.itasset.model;

import lombok.Data;

@Data
public class ITAssetHwModel {
	private int agentNo;
	private int uno;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private int deptId;
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	private String userNumber;
	private String hwList = "";
	private String swList = "";
}
