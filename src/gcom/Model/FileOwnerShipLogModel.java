package gcom.Model;

import lombok.Data;

@Data
public class FileOwnerShipLogModel {
	private int ownNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private String deptName = "";
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String ownerType = "";
	private String ownerData = "";
	private String fileId;
	private String firstFileName;
	private String fileList;
	private String sendServerTime = "";
	private String sendClientTime = "";
	
	public void setFileList(String value){
		fileList = value;
		if(value != null)
			firstFileName = fileList.split("\n")[0];
		
	}
}
