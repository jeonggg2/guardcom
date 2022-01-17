package gcom.policy.model;
import lombok.Data;

import gcom.common.db.Table;
import gcom.common.db.Field;

@Data
@Table(value="policy_info", primaryFieldName="no")
public class PolicyModel {
	
	// Enabled 정책 데이터
	@Field(value="no", isPrimaryKey=true)
	private int policyNo = 0;
	
	private Boolean isUninstall = false;
	private Boolean isFileEncryption = false;
	private Boolean isCdEncryption = false;
	private Boolean isPrint = false;
	private Boolean isCdEnabled = false;
	private Boolean isCdExport = false;
	private Boolean isWlan = false;
	private Boolean isNetShare = false;
	private Boolean isWebExport = false;

	private Boolean isSensitiveDirEnabled = false;
	private Boolean isSensitiveFileAccess = false;
	private Boolean isUsbControlEnabled = false;
	
	private Boolean isStorageExport = false;
	private Boolean isStorageAdmin = false;

	// LongText형 List 정책 정보 Enabled 가공
	private Boolean isUsbBlock = false;
	private Boolean isComPortBlock = false;
	private Boolean isNetPortBlock = false;
	private Boolean isProcessList = false;
	private Boolean isFilePattern = false;
	private Boolean isWebAddr = false;
	private Boolean isMsgBlock = false;
	private Boolean isWatermark = false;
	
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
	private String quarantinePathAccessCode = "";
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
			isUsbBlock = false;
			usbBlockCode = value.length() > 1 ? value.substring(2, value.length()) : "" ;
		}else{
			isUsbBlock = true;
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
