package gcom.service.UserManage;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.PolicyDataDAO;
import gcom.DAO.UserAgentDAO;
import gcom.DAO.UserManageDAO;
import gcom.Model.UserAgentModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;

public class UserManageServiceImpl implements IUserManageService {

	UserManageDAO uaDao = new UserManageDAO();
	
	public HashMap<String, Object> insertUserInfo(HashMap<String, Object> map){		
		return uaDao.insertUserInfo(map);
	}
	public HashMap<String, Object> removeUserInfo(HashMap<String, Object> map){		
		return uaDao.removeUserInfo(map);
	}
	public HashMap<String, Object> updateUserInfo(HashMap<String, Object> map){
		
		HashMap<String, Object> result = uaDao.updateUserInfo(map);
		
		if (result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
			UserAgentDAO userAgentDao = new UserAgentDAO();
			List<UserAgentModel> agentList = userAgentDao.lookupAgentListByUserNo(map);
			for (UserAgentModel agent : agentList) {
				NotificationObject.Notify(NotificationType.User, agent.getIpAddr());
			}
		}
		
		return result;	
	}


}
