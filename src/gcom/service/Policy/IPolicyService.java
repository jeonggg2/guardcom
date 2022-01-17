package gcom.service.Policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gcom.Model.AuditClientModel;
import gcom.Model.AuditServerModel;
import gcom.Model.PolicyRequestInfo;
import gcom.Model.PolicyMessengerModel;
import gcom.Model.PolicyNetworkModel;
import gcom.Model.PolicyPatternModel;
import gcom.Model.PolicyProcessModel;
import gcom.Model.PolicySerialModel;
import gcom.Model.PolicyWebSiteBlocklModel;
import gcom.Model.ServerAuditModel;
import gcom.Model.UsbDevInfoModel;
import gcom.Model.UserPolicyLogModel;

import gcom.Model.UserPolicyModel;
import gcom.Model.statistic.AuditClientSimpleModel;

public interface IPolicyService {
	public List<UserPolicyLogModel> getUserPolicyLogList(HashMap<String, Object> map);	
	public int getUserPolicyLogCount(HashMap<String, Object> map);
	
	public List<AuditClientModel> getAuditClientLogList(HashMap<String, Object> map);	
	public int getAuditClientLogListCount(HashMap<String, Object> map);
	public List<AuditClientSimpleModel> getAuditClientSimpleLogList(Map<String, Object> map);
	
	public List<AuditServerModel> getAuditServerLogList(HashMap<String, Object> map);	
	public int getAuditServerLogListCount(HashMap<String, Object> map);
	
	public HashMap<String, Object> insertServeriAudit(ServerAuditModel audit);
	public HashMap<String, Object> getAuditMap(HashMap<String, Object> map);

	
	public List<PolicyRequestInfo> getRequestedPolicyList(HashMap<String, Object> map);	
	public int getRequestedPolicyListCount(HashMap<String, Object> map);
	
	
	public String getAuditServerWorkData(int key);
	public List<PolicyMessengerModel> getPolicyMessengerList(HashMap<String, Object> map);
	public int getPolicyMessengerListCount(HashMap<String, Object> map);
	public List<PolicyProcessModel> getPolicyProcessList(HashMap<String, Object> map);
	public int getPolicyProcessListCount(HashMap<String, Object> map);
	public List<HashMap<String, Object>> getPolicyPatternSimpleList();
	public List<PolicyPatternModel> getPolicyPatternList(HashMap<String, Object> map);
	public int getPolicyPatternListCount(HashMap<String, Object> map);
	public List<PolicyNetworkModel> getPolicyNetworkList(HashMap<String, Object> map);
	public int getPolicyNetworkListCount(HashMap<String, Object> map);
	public List<PolicySerialModel> getPolicySerialList(HashMap<String, Object> map);
	public int getPolicySerialListCount(HashMap<String, Object> map);
	public List<UserPolicyModel> getPolicyAssignMemberList(HashMap<String, Object> map);
	public int getPolicyAssignMemberListCount(HashMap<String, Object> map);
	public List<UsbDevInfoModel> getPolicyUsbBlockList(HashMap<String, Object> map);
	public int getPolicyUsbBlockListCount(HashMap<String, Object> map);
	public List<PolicyWebSiteBlocklModel> getPolicyWebSiteBlockList(HashMap<String, Object> map);
	public int getPolicyWebSiteBlockListCount(HashMap<String, Object> map);
	public PolicyMessengerModel getMsgInfo(int code);
	public HashMap<String, Object> insertPolicyMsgSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicyMsgUpdate(HashMap<String, Object> map);
	public PolicyProcessModel getProcessInfo(int code);
	public HashMap<String, Object> insertPolicyProcessSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicyProcessUpdate(HashMap<String, Object> map);
	public PolicyPatternModel getPatternInfo(int code);
	public HashMap<String, Object> insertPolicyPatternSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicyPatternUpdate(HashMap<String, Object> map);
	public PolicyNetworkModel getNetworkInfo(int code);
	public HashMap<String, Object> addNetworkPortInfo(HashMap<String, Object> map);
	public HashMap<String, Object> updateNetworkPortInfo(HashMap<String, Object> map);
	public PolicySerialModel getSerialInfo(int code);
	public HashMap<String, Object> insertPolicySerialSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicySerialUpdate(HashMap<String, Object> map);
	public PolicyWebSiteBlocklModel getWebsiteInfo(int code);
	public HashMap<String, Object> insertPolicyWebsiteSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicyWebsiteUpdate(HashMap<String, Object> map);
	public UsbDevInfoModel getUsbInfo(int code);
	public HashMap<String, Object> insertPolicyUsbSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePolicyUsbUpdate(HashMap<String, Object> map);
	public HashMap<String, Object> insertPolicyDeviceSave(HashMap<String, Object> map);
	public HashMap<String, Object> updatePermitRequestPolicy(HashMap<String, Object> map);
	public HashMap<String, Object> updateRejectRequestPolicy(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicyMsgData(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicyProcessData(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicyPatternData(HashMap<String, Object> map);
	public HashMap<String, Object> deleteNetworkPortInfo(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicySerialData(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicyWebsiteData(HashMap<String, Object> map);
	public HashMap<String, Object> daletePolicyUsbData(HashMap<String, Object> map);
}
