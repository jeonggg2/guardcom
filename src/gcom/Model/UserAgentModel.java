package gcom.Model;

import lombok.Data;

@Data
public class UserAgentModel {
	private int uid;
	private int deptNo;
	private String duty  = "";
	private String number = "";
	private String rank  = "";
	private String name  = "";
	private String phone  = "";
	private String id  = "";
	private String deptName  = "";
	private boolean valid;
	private String pcName  = "";
	private String ipAddr  = "";
	private String macAddr  = "";
	private String connect_server_time = "";
	private String connect_client_time = "";
	
	private String version = "";
	private String install_server_time  = "";
	private String install_client_time  = "";

	private Boolean isConnection = false; 
	
	public void setValid(int value){
		if(value == 1){
			valid = true;
		}else{
			valid = false;
		}
	}

	public void setConnect_server_time(String value){
		if(value.equals("")){
			connect_server_time = "기록없음";
		}else{
			connect_server_time = value;
		}
	}
	public void setInstall_server_time(String value){
		if(value.equals("")){
			install_server_time = "기록없음";
		}else{
			install_server_time = value;
		}
	}
	public void setConnect_client_time(String value){
		if(value.equals("")){
			connect_server_time = "기록없음";
		}else{
			connect_server_time = value;
		}
	}
	public void setInstall_client_time(String value){
		if(value.equals("")){
			install_server_time = "기록없음";
		}else{
			install_server_time = value;
		}
	}
}
