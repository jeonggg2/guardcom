package gcom.user.model;

import lombok.Data;

@Data
public class UserNoticeModel {
	
	private int bbsId = 0;
	private String bbsTitle = "";
	private String bbsRegStaf = "";
	private String bbsRegDate = "";
	private String bbsSpecialYN = "";
	private String bbsAttfileYN = "";
	private int attfileId = 0;
	private int bbsClickCnt = 0;
	private String bbsBody="";
	
}
