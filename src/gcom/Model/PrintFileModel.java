package gcom.Model;

import lombok.Data;

@Data
public class PrintFileModel {
	private int printNo;
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
	private String printServerTime  = "";
	private String printClientTime  = "";
	private String fileName = "";
	private String fileId = "";
	private String fileList = "";
	
	private Boolean watermark = false;
	private int pageCount = 0;
	private int printCopies;
}
