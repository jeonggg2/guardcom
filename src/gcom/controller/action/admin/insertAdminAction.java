package gcom.controller.action.admin;

import java.util.HashMap;

import gcom.Model.ServerAuditModel;
import gcom.service.Personal.IPersonalService;
import gcom.service.Personal.PersonalServiceImpl;
import gcom.service.Policy.IPolicyService;
import gcom.service.Policy.PolicyServiceImpl;
import gcom.service.Request.IRequestService;
import gcom.service.Request.RequestServiceImpl;
import gcom.service.UserManage.IUserManageService;
import gcom.service.UserManage.UserManageServiceImpl;
import gcom.service.management.IManagementService;
import gcom.service.management.ManagementServiceImpl;

public class insertAdminAction {

	public HashMap<String, Object> insertNoticeWriteSave(HashMap<String, Object> map) {
		IPersonalService as = new PersonalServiceImpl();
		return as.insertNoticeWriteSave(map);
	}
	
	public HashMap<String, Object> insertUserInfoFromRequest(HashMap<String, Object> map){
		IRequestService as = new RequestServiceImpl();
		return as.insertUserInfoFromRequest(map);
	}

	public HashMap<String, Object> applyPolicyDataSave(HashMap<String, Object> map) {
		IPersonalService as = new PersonalServiceImpl();
		return as.applyPolicyDataSave(map);
	}

	public HashMap<String, Object> insertUserInfo(HashMap<String, Object> map) {
		IUserManageService as = new UserManageServiceImpl();
		return as.insertUserInfo(map);
	}

	public HashMap<String, Object> insertAdminUserInfo(HashMap<String, Object> map){
		IManagementService as = new ManagementServiceImpl();
		return as.insertAdminUserInfo(map);
	}
	
	public HashMap<String, Object> insertServeriAudit(ServerAuditModel audit){
		IPolicyService as = new PolicyServiceImpl();
		return as.insertServeriAudit(audit);
	}
	public HashMap<String, Object> insertContactCommentSave(HashMap<String, Object> map) {
		IPersonalService as = new PersonalServiceImpl();
		return as.insertContactCommentSave(map);
	}

	public HashMap<String, Object> insertPolicyMsgSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyMsgSave(map);
	}

	public HashMap<String, Object> insertPolicyProcessSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyProcessSave(map);
	}

	public HashMap<String, Object> insertPolicyPatternSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyPatternSave(map);
	}

	public HashMap<String, Object> insertPolicyNetworkSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.addNetworkPortInfo(map);
	}

	public HashMap<String, Object> insertPolicySerialSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicySerialSave(map);
	}

	public HashMap<String, Object> insertPolicyWebsiteSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyWebsiteSave(map);
	}

	public HashMap<String, Object> insertPolicyUsbSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyUsbSave(map);
	}

	public HashMap<String, Object> insertPolicyDeviceSave(HashMap<String, Object> map) {
		IPolicyService as = new PolicyServiceImpl();
		return as.insertPolicyDeviceSave(map);
	}


}
