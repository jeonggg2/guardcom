package gcom.controller.action;

import java.util.HashMap;

import gcom.DAO.LoginDAO;

public class commonAction {
	
	LoginDAO logDao = new LoginDAO();
	
	public HashMap<String, Object> getLoginCheckResult(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String loginType = map.get("loginType").toString();
		
		if ("U".equals(loginType)) {
			result = logDao.selectUserLoginCheck(map);
		} else if ("C".equals(loginType)) {
			result = logDao.selectConsoleLoginCheck(map);
		}
		return result;
	}

	
}
