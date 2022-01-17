package gcom.service.System;

import java.util.HashMap;
import java.util.List;

import gcom.Model.SystemInfoModel;

public interface ISystemService {
	public List<SystemInfoModel> getSystemInfoList(HashMap<String, Object> map);
	public int getSystemInfoListCount(HashMap<String, Object> map);
	public List<SystemInfoModel> getPwPatternList(HashMap<String, Object> map);
	public HashMap<String, Object> updateSystemInfo(HashMap<String, Object> map);
	public HashMap<String, Object> updatePwPatternInfo(HashMap<String, Object> map);
	public int serverLogoutTimeInfo();
}
