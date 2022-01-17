package gcom.controller.action.admin;

import java.util.HashMap;

import gcom.service.Policy.IPolicyService;
import gcom.service.Policy.PolicyServiceImpl;

public class deleteAdminAction {

	public HashMap<String, Object> daletePolicyMsgData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicyMsgData(map);
	}

	public HashMap<String, Object> daletePolicyProcessData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicyProcessData(map);
	}

	public HashMap<String, Object> daletePolicyPatternData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicyPatternData(map);
	}

	public HashMap<String, Object> daletePolicyNetworkData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.deleteNetworkPortInfo(map);
	}

	public HashMap<String, Object> daletePolicySerialData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicySerialData(map);
	}

	public HashMap<String, Object> daletePolicyWebsiteData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicyWebsiteData(map);
	}

	public HashMap<String, Object> daletePolicyUsbData(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.daletePolicyUsbData(map);
	}
	
}
