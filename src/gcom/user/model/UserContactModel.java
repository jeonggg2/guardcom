package gcom.user.model;

import lombok.Data;

@Data
public class UserContactModel {
	
	final static private String[] TYPE_NAME = {"단순문의","서비스문의","버그문의"};
	final static private int DYNAMIC_MAGIC_NUMBER = 1;
	
	private int contactId = 0;
	private int contactType = 0;
	private String contactTypeName ="";
	private String contactTitle = "";
	private String contactBody ="";
	private String regUserStafId = "";
	private String regUserName = "";
	private String regDt = "";
	private int commentId = 0;
	private String commentYN = "";
	private String commnetRegStafId = "";
	private String replyContent = "";
	private String commentRegDt = "";
	
	public void setTypeName(int type) {
		this.contactTypeName = TYPE_NAME[type - DYNAMIC_MAGIC_NUMBER];
	}
	
}
