package gcom.drm.service;

import java.util.HashMap;
import java.util.List;

import gcom.drm.dao.DrmCommonDao;

public class DrmAutoDecryptionSiteService {
	

	public boolean register(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.appendValue("auto_decryption_site_list", value);
	}
	
	public boolean unregister(String value) {
		DrmCommonDao dao = new DrmCommonDao();
		return dao.removeValue("auto_decryption_site_list", value);
	}
	
	public HashMap<String, Object> getList() {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		DrmCommonDao dao = new DrmCommonDao();		
		List<String> data = dao.getSystemInfo("auto_decryption_site_list");
		result.put("data", data);
		int total = data.size();
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		return result;
	}

}
