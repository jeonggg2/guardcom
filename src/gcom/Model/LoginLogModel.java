package gcom.Model;
import lombok.Data;

@Data
public class LoginLogModel {
	private int login_no;
	private int uid;
	private String userNo;
	private int deptNo;
	private String duty  = "";
	private String rank  = "";
	private String name  = "";
	private String phone  = "";
	private String number  = "";
	
	private String id  = "";
	private String deptName  = "";
	private String pcName  = "";
	private String ipAddr  = "";
	private String macAddr  = "";
	private String login_servertime ="";
	private String login_clienttime ="";
}
