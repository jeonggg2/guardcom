package gcom.controller.action;

import java.util.HashMap;

import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

public class insertAction {

	public HashMap<String, Object> insertRequestPolicyInfoSave(HashMap<String, Object> map) {
		UserService us = new UserServiceImpl();
		return us.insertRequestPolicyInfoSave(map);
	}

}
