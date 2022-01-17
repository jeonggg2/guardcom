package gcom.Model;

import lombok.Data;

@Data
public class MsnTalkModel {
	private int msgNo;
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
	private String msgType = "";
	private String msgText = "";
	private String sendServerTime = "";
	private String sendClientTime = "";
}
