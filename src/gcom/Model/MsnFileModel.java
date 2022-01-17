package gcom.Model;

import lombok.Data;
@Data
public class MsnFileModel {
	private int msgNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private int deptId ;
	private String duty = "";
	private String fileId = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	private String msgType = "";
	private String fileList = "";
	private String sendServerTime = "";
	private String sendClientTime = "";
	private String firstFileName = "";
	
	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
