package gcom.service.UserManage;

import java.util.HashMap;
import java.util.List;

import gcom.Model.LoginLogModel;
import gcom.Model.UserAgentModel;
import gcom.Model.UserInfoModel;
import gcom.Model.UserPolicyModel;

public interface IUserManageService {

	public HashMap<String, Object> insertUserInfo(HashMap<String, Object> map);
	public HashMap<String, Object> removeUserInfo(HashMap<String, Object> map);
	public HashMap<String, Object> updateUserInfo(HashMap<String, Object> map);
}
