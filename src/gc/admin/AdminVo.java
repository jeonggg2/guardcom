package gc.admin;
import java.util.Enumeration;
import javax.servlet.ServletContext;

public class AdminVo {

	private int no;
	private int dept_no;
	private int dept_depth;
	private int user_no;
	private String login_id;
	private String login_pw;
	private int admin_Mode;
	private String ip1;
	private String Session;
	private String id;

	static public String getAdminVoId(String id, ServletContext application) {
		Enumeration<String> attr = (Enumeration<String>)application.getAttributeNames();
		while (attr.hasMoreElements()){
			String attrName = (String)attr.nextElement();
			if (attrName.startsWith("AdminVo")) {
				AdminVo adminVo = (AdminVo)application.getAttribute(attrName);
				if (id.compareTo(adminVo.getLogin_id()) == 0) {
					return "AdminVo"+adminVo.toString();
				}
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}
	public String getIp1() {
		return ip1;
	}
	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}
	public String getIp2() {
		return ip2;
	}
	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}
	private String ip2;

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getDept_no() {
		return dept_no;
	}
	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_pw() {
		return login_pw;
	}
	public void setLogin_pw(String login_pw) {
		this.login_pw = login_pw;
	}
	public int getDept_depth() {
		return dept_depth;
	}
	public void setDept_depth(int dept_depth) {
		this.dept_depth = dept_depth;
	}
	public boolean IsViewMode() {
		return (admin_Mode != 0);
	}
	public int getAdmin_Mode() {
		return admin_Mode;
	}
	public void setAdmin_Mode(int admin_Mode) {
		this.admin_Mode = admin_Mode;
	}
	public void SetSession(String Session) {
		this.Session = Session;
	}
	public String GetSession() {
		return Session;
	}
	public void SetId(String Id) {
		this.id = Id;
	}
}
