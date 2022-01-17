package gcom.Model;

import lombok.Data;

@Data
public class UserPolicyLogModel {
	private int policyNo;
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
	private String requestServerTime = "";
	private String requestClientTime = "";
	private String applyTime = "";
	
	private Boolean isUninstall;
	private Boolean isFileEncryption;
	private Boolean isCdEncryption;
	private Boolean isPrint;
	private Boolean isCdEnabled;
	private Boolean isCdExport;
	private Boolean isWlan;
	private Boolean isNetShare;
	private Boolean isWebExport;
	
	private Boolean isSensitiveDirEnabled;
	private Boolean isSensitiveFileAccess;
	private Boolean isUsbControlEnabled;

	private Boolean isStorageExport;
	private Boolean isStorageAdmin;
	
	private Boolean isUsbBlock;
	private Boolean isComPortBlock;	
	private Boolean isNetPortBlock;
	private Boolean isProcessList;
	private Boolean isFilePattern;	
	private Boolean isWebAddr;
	private Boolean isMsgBlock = false;	
	private Boolean isWatermark;
	
	// LongText형 List 정책 정보 적용 코드 데이터 (String)
	private String usbBlockCode = "";
	private String comPortBlockCode = "";
	private String netPortBlockCode = "";
	private String processListCode = "";
	private String filePatternCode = "";
	private String webAddrCode = "";
	private String msgBlockCode = "";
	private String watermarkCode = "";

	private int printLogDesc;	//0:로그 전송 안함	1:이벤트 로그	2: 파일 원본 로그
	//private String quarantinePathAccessCode = "";
	private int patternFileControl;   //검출된 패턴파일 처리 방법 0:격리 1:삭제

	public void setIsUninstall(int value){
		if(value == 0){
			isUninstall = false;
		}else{
			isUninstall = true;
		}
	}
	public void setIsFileEncryption(int value){
		if(value == 0){
			isFileEncryption = false;
		}else{
			isFileEncryption = true;
		}
	}
	public void setIsCdEncryption(int value){
		if(value == 0){
			isCdEncryption = false;
		}else{
			isCdEncryption = true;
		}
	}
	public void setIsPrint(int value){
		if(value == 0){
			isPrint = false;
		}else{
			isPrint = true;
		}
	}
	public void setIsCdEnabled(int value){
		if(value == 0){
			isCdEnabled = false;
		}else{
			isCdEnabled = true;
		}
	}
	public void setIsCdExport(int value){
		if(value == 0){
			isCdExport = false;
		}else{
			isCdExport = true;
		}
	}
	public void setIsWlan(int value){
		if(value == 0){
			isWlan = false;
		}else{
			isWlan = true;
		}
	}
	public void setIsNetShare(int value){
		if(value == 0){
			isNetShare = false;
		}else{
			isNetShare = true;
		}
	}
	public void setIsWebExport(int value){
		if(value == 0){
			isWebExport = false;
		}else{
			isWebExport = true;
		}
	}
	
	public void setIsSensitiveDirEnabled(int value){
		if(value == 0){
			isSensitiveDirEnabled = false;
		}else{
			isSensitiveDirEnabled = true;
		}
	}
	public void setIsSensitiveFileAccess(int value){
		if(value == 0){
			isSensitiveFileAccess = false;
		}else{
			isSensitiveFileAccess = true;
		}
	}
	public void setIsUsbControlEnabled(int value){
		if(value == 0){
			isUsbControlEnabled = false;
		}else{
			isUsbControlEnabled = true;
		}
	}
	public void setIsStorageExport(int value){
		if(value == 0){
			isStorageExport = false;
		}else{
			isStorageExport = true;
		}
	}
	public void setIsStorageAdmin(int value){
		if(value == 0){
			isStorageAdmin = false;
		}else{
			isStorageAdmin = true;
		}
	}
	
	public void setIsUsbBlock(String value){
		if(value.substring(0,1).equals("Y")){
			isUsbBlock = true;
			usbBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;

		}else{
			isUsbBlock = false;
			usbBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;

		};		
	}
	public void setIsComPortBlock(String value){
		if(value.substring(0,1).equals("Y")){
			isComPortBlock = false;
			comPortBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		}else{
			isComPortBlock = true;
			comPortBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		};				
	}
	public void setIsNetPortBlock(String value){
		if(value.substring(0,1).equals("Y")){
			isNetPortBlock = false;
			netPortBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		}else{
			isNetPortBlock = true;
			netPortBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		};			
	}
	public void setIsWebAddr(String value){
		if(value.substring(0,1).equals("Y")){
			isWebAddr = false;
			webAddrCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		}else{
			isWebAddr = true;
			webAddrCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		};		
	}
	
	public void setIsMsgBlock(String value){
		if(value.substring(0,1).equals("Y")){
			isMsgBlock = false;
			msgBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		}else{
			isMsgBlock = true;
			msgBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		};		
	}
	
	public void setIsWatermark(String value){
		if(value.substring(0,1).equals("Y")){
			isWatermark = true;
			watermarkCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		} else {
			isWatermark = false;
		}
	}

	public void setIsProcessList(String value){
		if("".equals(value)){
			isProcessList = false;
		}else{
			isProcessList = true;
			processListCode = value;
		}
	}
	
	public void setIsFilePattern(String value){
		if("".equals(value)){
			isFilePattern = false;
		}else{
			isFilePattern = true;
			filePatternCode = value;
		}
	}
}
