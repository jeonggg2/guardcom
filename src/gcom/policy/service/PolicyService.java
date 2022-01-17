package gcom.policy.service;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.PersonalDataDAO;
import gcom.DAO.UserAgentDAO;
import gcom.Model.UserAgentModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;
import gcom.policy.dao.PolicyDAO;
import gcom.policy.model.PolicyModel;

public class PolicyService implements IPolicyService {

	@Override
	public PolicyModel getPolicyDataByNo(int no) {
		PolicyDAO dao = new PolicyDAO();
		return dao.getMemberPolicyInfoByNo(no);
	}
	
	public HashMap<String, Object> savePolicyData(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String returnCode = ConfigInfo.RETURN_CODE_SUCCESS;
		
		PersonalDataDAO dao = new PersonalDataDAO();
		
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> apply_list = (List<HashMap<String, Object>>) map.get("apply_list");
		List<UserAgentModel> agentList;
		
		for(HashMap<String, Object> data : apply_list) {
			map.put("policy_no", data.get("policy_no"));
			returnCode = dao.updatePolicyDataSave(map);
			
			if (returnCode.equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
				UserAgentDAO userAgentDao = new UserAgentDAO();
				agentList = userAgentDao.lookupAgentListByPolicyNo(map);
				for (UserAgentModel agent : agentList) {
					NotificationObject.Notify(NotificationType.Policy, agent.getIpAddr());
				}
			}
		}
		
		result.put("returnCode", returnCode);
		return result;
	}
}
