package gcom.controller.action;

import java.util.HashMap;

import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

public class updateAction {

	public HashMap<String, Object> updateUserInfoData(HashMap<String, Object> map) {
		UserService us = new UserServiceImpl();
		return us.updateUserInfoData(map);
	}

	

}
