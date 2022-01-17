package gcom.drm.model;
import lombok.Data;

@Data
public class DrmDecryptionRequestModel {
	private int requestNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";
	private String phone = "";
	
	private String filePath = "";
	private String fileHash = "";
	private String fileId = "";
	private String fileList = "";
	
	private String reqNotice = "";
	private String permitState = "";
	private String permitDate = "";
	private String permitStaf = "";
	private String userNumber = "";
	private String reqClientTime = "";
}
