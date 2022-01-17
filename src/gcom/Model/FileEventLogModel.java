package gcom.Model;

import lombok.Data;

@Data
public class FileEventLogModel {
	private int fileNo;
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
	
	private String fileId = "";
	private String description = "";
	private String fileList = "";

	private String serverTime = "";
	private String clientTime = "";
	private String firstFileName = "";
 

	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
