package gcom.Model;

import lombok.Data;

@Data
public class FileExportLogModel {
	private int exportNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	
	private String filePath = "";
	private String fileId;
	private String fileList;
	private String firstFileName;
	private String password = "";
	private String notice = "";
	private Boolean isOriginalFile = false;
	
	private String exportServerTime = "";
	private String exportClientTime = "";
	
	public void setFilePath(String path){
		if(path == ""){
			isOriginalFile = true;
		}
		filePath = path;
	}
	
	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
