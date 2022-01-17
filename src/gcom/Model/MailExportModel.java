package gcom.Model;

import lombok.Data;

@Data
public class MailExportModel {
	private int mailNo;
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

	private String subject ="";
	private String fileList ="";
	private String fileId = "";
	private String srcAddr="";
	private String dstAddr="";
	
	private String sendServerTime = "";
	private String sendClientTime = "";
	
}
