package gcom.service.UserAgent;

import java.util.HashMap;
import java.util.List;

import gcom.Model.LoginLogModel;
import gcom.Model.UserAgentModel;
import gcom.Model.UserInfoModel;
import gcom.Model.UserPolicyModel;

public interface IUserAgentService {

	public List<UserAgentModel> getUserAgentList(HashMap<String, Object> map);
	public int getUserAgentListCount(HashMap<String, Object> map);	
	public List<UserAgentModel> getAgentList(HashMap<String, Object> map);
	public int getAgentListCount(HashMap<String, Object> map);	
	public List<LoginLogModel> getLoginlogList(HashMap<String, Object> map);	
	public int getLoginlogListCount(HashMap<String, Object> map);

	public List<UserPolicyModel> getUserPolicyList(HashMap<String, Object> map);	
	public int getUserPolicyCount(HashMap<String, Object> map);
	
	public List<UserInfoModel> getUserInfoList(HashMap<String, Object> map);
	public int getUserInfoListCount(HashMap<String, Object> map);

	public UserInfoModel getUserInfo(HashMap<String, Object> map);


}
