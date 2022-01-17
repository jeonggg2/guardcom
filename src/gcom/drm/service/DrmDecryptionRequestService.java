package gcom.drm.service;

import java.util.HashMap;

import gcom.common.util.ConfigInfo;
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;
import gcom.drm.dao.DrmDecryptionRequestDao;

public class DrmDecryptionRequestService {
	
	DrmDecryptionRequestDao dao = new DrmDecryptionRequestDao();
	
	public HashMap<String, Object> permitRequest(HashMap<String, Object> map) {
		HashMap<String, Object> result = dao.updatePermitRequestPolicy(map);
		if (result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
			String ip = map.get("ip").toString();
			NotificationObject.Notify(NotificationType.ExportFile, ip);
		}
		return result;
	}

	public HashMap<String, Object> rejectRequest(HashMap<String, Object> map) {
		HashMap<String, Object> result = dao.updateRejectRequestPolicy(map);
		if (result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
			String ip = map.get("ip").toString();
			NotificationObject.Notify(NotificationType.ExportFile, ip);
		}
		return result;
	}
	
	public HashMap<String, Object> getList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("data", dao.getRequestedPolicyList(map));
		int total = dao.getRequestedPolicyListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		return result;		
	}
}
