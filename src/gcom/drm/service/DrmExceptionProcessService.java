package gcom.drm.service;

import java.util.HashMap;
import java.util.List;

import gcom.drm.dao.DrmCommonDao;

public class DrmExceptionProcessService {
	
	public boolean register(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.appendValue("non_encryption_process_list", value);
	}
	
	public boolean unregister(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.removeValue("non_encryption_process_list", value);
	}
	
	public HashMap<String, Object> getList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		DrmCommonDao dao = new DrmCommonDao();		
		List<String> data = dao.getSystemInfo("non_encryption_process_list");
		result.put("data", data);
		int total = data.size();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		return result;
	}

}
