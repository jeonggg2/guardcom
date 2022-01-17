package gcom.Model;

import lombok.Data;

@Data
public class UserInfoModel {
	private int uno;
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
	private String phone = "";
	private String number = "";
	private int secureLevel;
}
