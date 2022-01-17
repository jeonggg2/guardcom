package gcom.Model;

import lombok.Data;

@Data
public class DiskExportModel {
	private int exportNo;
	private String userNo;
	private String userName = "";
	private String exportServerTime  = "";
	private String exportClientTime  = "";
	private int grade;
	private String fileList = "";
	private String notice = "";
	private String exportStatus = "";
	private String fileId;
	private String userId = "";
	private int deptId ;
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	private String partitionGuid = "";
	private String partitionLabel = "";
	private String firstFileName = "";

	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
