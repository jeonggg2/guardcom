package gcom.controller.action;

import java.util.HashMap;

import gcom.service.Policy.IPolicyService;
import gcom.service.Policy.PolicyServiceImpl;

public class getDataAction {
	public String getServerAuditWorkData(int key){
		IPolicyService as = new PolicyServiceImpl();
		return as.getAuditServerWorkData(key);						
	}
	
	public HashMap<String, Object> getAuditMap(HashMap<String, Object> map){
		IPolicyService as = new PolicyServiceImpl();
		return as.getAuditMap(map);	
	}

}
