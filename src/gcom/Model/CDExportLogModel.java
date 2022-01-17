package gcom.Model;

import lombok.Data;

@Data
public class CDExportLogModel {
	private int exportNo;
	private String userNo;
	private String userName = "";
	private String userNumber = "";
	private String userId = "";
	private int deptId ;
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	
	private String fileId;
	private String fileList;
	private String firstFileName;
	private String notice;
	private String guid;
	private int status;
	private String label;
	private String grade;
	
	
	private boolean valid;
	
	private String exportServerTime;
	private String exportClientTime;

	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
