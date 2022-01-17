package gcom.Model;

import lombok.Data;

@Data
public class UsbDevInfoModel {
	private int usbId;
	private String name = "";
	private String vid  = "";
	private String pid  = "";
	private String serialNumber  = "";
	private String mainclass  = "";
	private String subclass  = "";
	private String protocol  = "";
	private String compareType  = "";
	private Boolean allow  = true;
	private String description  = "";
	
	private Boolean compareVid = false;
	private Boolean comparePid = false;
	private Boolean compareSerial = false;
	private Boolean compareMainclass = false;
	private Boolean compareSubclass = false;
	private Boolean compareProtocol = false;

	public void setCompareType(String value){
		compareType = value;
		if(value.substring(0, 1).equals("1")){
			compareVid = true;
		}
		if(value.substring(1, 2).equals("1")){
			comparePid = true;
		}
		if(value.substring(2, 3).equals("1")){
			compareSerial = true;
		}
		if(value.substring(3, 4).equals("1")){
			compareMainclass = true;
		}
		if(value.substring(4, 5).equals("1")){
			compareSubclass = true;
		}
		if(value.substring(5, 6).equals("1")){
			compareProtocol = true;
		}

	}
}
