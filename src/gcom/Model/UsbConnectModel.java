package gcom.Model;

import lombok.Data;

@Data
public class UsbConnectModel {
	private int usbNo;
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
	private String connectServerTime  = "";
	private String connectClientTime  = "";
	private String deviceProperty = "";
	private String deviceName = "";
	private String notice;
}
