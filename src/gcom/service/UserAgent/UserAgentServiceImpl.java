package gcom.service.UserAgent;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.LoginLogDAO;
import gcom.DAO.UserAgentDAO;
import gcom.Model.LoginLogModel;
import gcom.Model.UserAgentModel;
import gcom.Model.UserInfoModel;
import gcom.Model.UserPolicyModel;

public class UserAgentServiceImpl implements IUserAgentService {

	UserAgentDAO uaDao = new UserAgentDAO();
	LoginLogDAO loginDao = new LoginLogDAO();
	
	//사용자에이전트 정보
	public List<UserAgentModel> getUserAgentList(HashMap<String, Object> map){		
		return uaDao.getUserAgentList(map);
	}
	public int getUserAgentListCount(HashMap<String, Object> map){		
		return uaDao.getUserAgentListCount(map);		
	}
	//사용자에이전트 정보
	public List<UserAgentModel> getAgentList(HashMap<String, Object> map){		
		return uaDao.getAgentList(map);
	}
	public int getAgentListCount(HashMap<String, Object> map){		
		return uaDao.getAgentListCount(map);		
	}
	
	//로그인로그
	public List<LoginLogModel> getLoginlogList(HashMap<String, Object> map){		
		return loginDao.getLoginlogList(map);		
	}
	public int getLoginlogListCount(HashMap<String, Object> map){
		
		return loginDao.getLoginlogListCount(map);		
	}

	public List<UserPolicyModel> getUserPolicyList(HashMap<String, Object> map){
		return uaDao.getUserPolicyList(map);
	}
	public int getUserPolicyCount(HashMap<String, Object> map){
		return uaDao.getUserPolicyListCount(map);
	}

	
	public List<UserInfoModel> getUserInfoList(HashMap<String, Object> map){
		return uaDao.getUserInfoList(map);
	}
	
	public int getUserInfoListCount(HashMap<String, Object> map){
		return uaDao.getUserInfoListCount(map);		
	}

	public UserInfoModel getUserInfo(HashMap<String, Object> map){
		return uaDao.getUserInfo(map);
	}
	
}
