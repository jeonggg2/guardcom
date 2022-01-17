package gcom.drm.service;

import java.util.HashMap;
import java.util.List;

import gcom.drm.dao.DrmCommonDao;

public class DrmAutoDecryptionProcessService {
	public boolean register(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.appendValue("encryption_process_list", value);
	}
	
	public boolean unregister(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.removeValue("encryption_process_list", value);
	}
	
	public HashMap<String, Object> getList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		DrmCommonDao dao = new DrmCommonDao();		
		List<String> data = dao.getSystemInfo("encryption_process_list");
		result.put("data", data);
		int total = data.size();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		return result;
	}
}
