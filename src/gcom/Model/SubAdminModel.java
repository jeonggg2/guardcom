package gcom.Model;

import lombok.Data;

@Data
public class SubAdminModel {
	private int adminNo;
	private String adminId;
	private int dept_no;
	private String deptNm;
	private Boolean isPassword;
	private String ipAddr;
	private String ipAddr1;
	private int adminMode;
	private double loginFailTimeStamp;
	
	
}
