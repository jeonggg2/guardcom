package gcom.service.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gcom.Model.UserEnrollModel;
import gcom.Model.statistic.ContactSimpleModel;
import gcom.Model.statistic.RequestSimpleModel;


public interface IRequestService {
	
	public List<ContactSimpleModel> getSimpleContactList(Map<String, Object> map);
	public List<RequestSimpleModel> getSimpleRequestList(Map<String, Object> map);
	public List<UserEnrollModel> getEnrollRequestList(Map<String, Object> map);
	
	public HashMap<String, Object> insertUserInfoFromRequest(HashMap<String, Object> map);
	public HashMap<String, Object> updateEnrollRequestReject(HashMap<String, Object> map);
	public HashMap<String, Object> getEnrollRequestCheckDupl(HashMap<String, Object> map);
	public int getEnrollRequestListCount(Map<String, Object> map);
}
