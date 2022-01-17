package gcom.service.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gcom.DAO.ContactDataDAO;
import gcom.DAO.RequestDataDAO;
import gcom.Model.UserEnrollModel;
import gcom.Model.statistic.ContactSimpleModel;
import gcom.Model.statistic.RequestSimpleModel;

public class RequestServiceImpl implements IRequestService {
	
	ContactDataDAO contDao = new ContactDataDAO();
	RequestDataDAO reqDao = new RequestDataDAO();
	
	public List<ContactSimpleModel> getSimpleContactList(Map<String, Object> map){
		return contDao.getSimpleContactList(map);
	}
	public List<RequestSimpleModel> getSimpleRequestList(Map<String, Object> map){
		return reqDao.getSimpleRequestList(map);
	}
	
	public List<UserEnrollModel> getEnrollRequestList(Map<String, Object> map){
		return reqDao.getEnrollRequestList(map);
	}

	public int getEnrollRequestListCount(Map<String, Object> map){
		return reqDao.getEnrollRequestListCount(map);
	}

	public HashMap<String, Object> insertUserInfoFromRequest(HashMap<String, Object> map){
		return reqDao.insertUserInfoFromRequest(map);
	}
	
	public HashMap<String, Object> updateEnrollRequestReject(HashMap<String, Object> map){
		return reqDao.updateEnrollRequestReject(map);		
	}
	
	public HashMap<String, Object> getEnrollRequestCheckDupl(HashMap<String, Object> map) {
		return reqDao.selectEnrollRequestCheckDupl(map);		
	}
}