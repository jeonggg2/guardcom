package gcom.service.System;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.SystemInfoDAO;
import gcom.Model.SystemInfoModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;

public class SystemServiceImpl implements ISystemService {

	SystemInfoDAO siDao = new SystemInfoDAO();

	//사용자에이전트 정보
	public List<SystemInfoModel> getSystemInfoList(HashMap<String, Object> map){
		return siDao.getSystemInfoList(map);
	}

	public int getSystemInfoListCount(HashMap<String, Object> map){
		return siDao.getSystemInfoListCount(map);
	}

	public List<SystemInfoModel> getPwPatternList(HashMap<String, Object> map){
		return siDao.getPwPatternList(map);
	}

	public HashMap<String, Object> updateSystemInfo(HashMap<String, Object> map){

		HashMap<String, Object> result = siDao.updateSystemInfo(map);
		String name = map.get("name").toString();

		if (result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
			if (name.compareToIgnoreCase("sensitive_info_schedule") == 0) {
				NotificationObject.NotifyAllUser(NotificationType.PatternSchedule);
			} else if (name.compareToIgnoreCase("integrity_schedule") == 0) {
				NotificationObject.NotifyAllUser(NotificationType.IntegritySchedule);
			} else {
				NotificationObject.NotifyAllUser(NotificationType.System);
			}
		}
		return result;
	}

	public HashMap<String, Object> updatePwPatternInfo(HashMap<String, Object> map){

		return siDao.updatePwPatternInfo(map);
	}

	public int serverLogoutTimeInfo() {
		return siDao.serverLogoutTimeInfo();
	}
}
