package gcom.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gcom.Model.AuditClientModel;
import gcom.Model.AuditServerModel;
import gcom.Model.CDExportLogModel;
import gcom.Model.DiskConnectLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.DiskInfoModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.FileExportLogModel;
import gcom.Model.FileOwnerShipLogModel;
import gcom.Model.LoginLogModel;
import gcom.Model.MailExportContentModel;
import gcom.Model.MailExportModel;
import gcom.Model.MsnFileModel;
import gcom.Model.MsnTalkModel;
import gcom.Model.NetExportLogModel;
import gcom.Model.NetPortLogModel;
import gcom.Model.PartitionConnectLogModel;
import gcom.Model.PolicyMessengerModel;
import gcom.Model.PolicyNetworkModel;
import gcom.Model.PolicyPatternModel;
import gcom.Model.PolicyProcessModel;
import gcom.Model.PolicySerialModel;
import gcom.Model.PolicyWebSiteBlocklModel;
import gcom.Model.PrintFileModel;
import gcom.Model.PrivacyLogModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;
import gcom.Model.UserAgentModel;
import gcom.Model.UserPolicyLogModel;
import gcom.Model.UserPolicyModel;
import gcom.service.Device.DeviceInfoServiceImpl;
import gcom.service.Device.IDeviceInfoService;
import gcom.service.Personal.IPersonalService;
import gcom.service.Personal.PersonalServiceImpl;
import gcom.service.Policy.IPolicyService;
import gcom.service.Policy.PolicyServiceImpl;
import gcom.service.UserAgent.IUserAgentService;
import gcom.service.UserAgent.UserAgentServiceImpl;
import gcom.service.common.CommonServiceImpl;
import gcom.service.common.ICommonService;
import gcom.service.disk.DiskServiceImpl;
import gcom.service.disk.IDiskService;
import gcom.service.file.FileServiceImpl;
import gcom.service.file.IFileService;
import gcom.service.network.INetworkService;
import gcom.service.network.NetworkServiceImpl;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

public class getAction {

	public HashMap<String, Object> getUserAgentList(HashMap<String, Object> map){
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		IUserAgentService as = new UserAgentServiceImpl();
		List<UserAgentModel> data = as.getUserAgentList(map);
		result.put("data", data);
		int total = as.getUserAgentListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;
	}

	public HashMap<String, Object> getAgentList(HashMap<String, Object> map){
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		IUserAgentService as = new UserAgentServiceImpl();
		List<UserAgentModel> data = as.getAgentList(map);
		result.put("data", data);
		int total = as.getAgentListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;
	}

	
	public HashMap<String, Object> getLoginLogList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IUserAgentService as = new UserAgentServiceImpl();
		List<LoginLogModel> data = as.getLoginlogList(map);
		result.put("data", data);
		int total = as.getLoginlogListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;				
	}
	
	public HashMap<String, Object> getUnAuthUsbList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IDeviceInfoService as = new DeviceInfoServiceImpl();
		List<UsbDevInfoModel> data = as.getUnAuthUsbList(map);
		result.put("data", data);
		int total = as.getUnAuthUsbListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getDiskTranList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IDeviceInfoService as = new DeviceInfoServiceImpl();
		List<DiskExportModel> data = as.getDiskTranList(map);
		result.put("data", data);
		int total = as.getDiskTranListCount(map);
//		int total = 100;

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	public HashMap<String, Object> getUsbBlockList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IDeviceInfoService as = new DeviceInfoServiceImpl();
		List<UsbConnectModel> data = as.getUsbBlockList(map);
		result.put("data", data);
		int total = as.getUsbBlockListCount(map);

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public HashMap<String, Object> getPrintList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IDeviceInfoService as = new DeviceInfoServiceImpl();
		List<PrintFileModel> data = as.getPrintLogList(map);
		result.put("data", data);
		int total = as.getPrintLogCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public HashMap<String, Object> getModifyPolicyList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IDeviceInfoService as = new DeviceInfoServiceImpl();
		List<UsbDevInfoModel> data = as.getUnAuthUsbList(map);
		result.put("data", data);
		int total = as.getUnAuthUsbListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public HashMap<String, Object> getMailExportList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPersonalService as = new PersonalServiceImpl();
		List<MailExportModel> data = as.getMailExportList(map);
		result.put("data", data);
		int total = as.getMailExportListCount(map);

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public MailExportContentModel getMailExportContent(HashMap<String, Object> map){
		IPersonalService as = new PersonalServiceImpl();
		return as.getMailExportContent(map);
	}
	
	public HashMap<String, Object> getMsnTalkList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPersonalService as = new PersonalServiceImpl();
		List<MsnTalkModel> data = as.getMsnTalkList(map);
		result.put("data", data);
		int total = as.getMsnTalkListCount(map);

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public HashMap<String, Object> getMsnFileList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPersonalService as = new PersonalServiceImpl();
		List<MsnFileModel> data = as.getMsnFileList(map);
		result.put("data", data);
		int total = as.getMsnFileListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getPrivacyFileList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPersonalService as = new PersonalServiceImpl();
		List<PrivacyLogModel> data = as.getPrivacyFileList(map);
		result.put("data", data);
		int total = as.getPrivacyFileListCount(map);

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}
	
	public HashMap<String, Object> getUserPolicyList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IUserAgentService as = new UserAgentServiceImpl();
		List<UserPolicyModel> data = as.getUserPolicyList(map);
		result.put("data", data);
		int total = as.getUserPolicyCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getUserPolicyLogList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPolicyService as = new PolicyServiceImpl();
		List<UserPolicyLogModel> data = as.getUserPolicyLogList(map);
		result.put("data", data);
		int total = as.getUserPolicyLogCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getAuditClientLogList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPolicyService as = new PolicyServiceImpl();
		List<AuditClientModel> data = as.getAuditClientLogList(map);
		result.put("data", data);
		int total = as.getAuditClientLogListCount(map);

		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getAuditServerLogList(HashMap<String, Object> map){
		HashMap<String, Object> result = new HashMap<String, Object>();
		IPolicyService as = new PolicyServiceImpl();
		List<AuditServerModel> data = as.getAuditServerLogList(map);
		result.put("data", data);
		int total = as.getAuditServerLogListCount(map);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		
		return result;						
	}

	public HashMap<String, Object> getPolicyUsbBlockList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("usbId").toString())) {
			List<UsbDevInfoModel> empty = new ArrayList<UsbDevInfoModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<UsbDevInfoModel> data = us.getPolicyUsbBlockList(map);
			result.put("data", data);
			int total = us.getPolicyUsbBlockListCount(map);

			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicySerialList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("serialId").toString())) {
			List<PolicySerialModel> empty = new ArrayList<PolicySerialModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicySerialModel> data = us.getPolicySerialList(map);
			result.put("data", data);
			int total = us.getPolicySerialListCount(map);

			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicyNetworkList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("netId").toString())) {
			List<PolicyNetworkModel> empty = new ArrayList<PolicyNetworkModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicyNetworkModel> data = us.getPolicyNetworkList(map);
			result.put("data", data);
			int total = us.getPolicyNetworkListCount(map);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicyProcessList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("prsId").toString())) {
			List<PolicyProcessModel> empty = new ArrayList<PolicyProcessModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicyProcessModel> data = us.getPolicyProcessList(map);
			result.put("data", data);
			int total = us.getPolicyProcessListCount(map);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicyPatternList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("patId").toString())) {
			List<PolicyPatternModel> empty = new ArrayList<PolicyPatternModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicyPatternModel> data = us.getPolicyPatternList(map);
			result.put("data", data);
			int total = us.getPolicyPatternListCount(map);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicyWebSiteBlockList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("siteId").toString())) {
			List<PolicyWebSiteBlocklModel> empty = new ArrayList<PolicyWebSiteBlocklModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicyWebSiteBlocklModel> data = us.getPolicyWebSiteBlockList(map);
			result.put("data", data);
			int total = us.getPolicyWebSiteBlockListCount(map);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	public HashMap<String, Object> getPolicyMessengerList(HashMap<String, Object> map) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if ("".equals(map.get("msgId").toString())) {
			List<PolicyMessengerModel> empty = new ArrayList<PolicyMessengerModel>();
			result.put("data", empty);
			result.put("recordsTotal", 0);
			result.put("recordsFiltered", 0);
		} else {
			UserService us = new UserServiceImpl();
			List<PolicyMessengerModel> data = us.getPolicyMessengerList(map);
			result.put("data", data);
			int total = us.getPolicyMessengerListCount(map);
			result.put("recordsTotal", total);
			result.put("recordsFiltered", total);
		}
		
		return result;
	}

	
	public HashMap<String, Object> getRmvDiskFileLogList(HashMap<String, Object> map){
		IDeviceInfoService ds = new DeviceInfoServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getRmvDiskFileLogListCount(map);
		List<FileEventLogModel> data = ds.getRmvDiskFileLogList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;
	}


	
	public List<HashMap<String, Object>> getFileList(HashMap<String, Object> map) {
		ICommonService ca = new CommonServiceImpl();
		return ca.getFileList(map);
	}

	public String getFilePath(HashMap<String, Object> map){
		ICommonService ca = new CommonServiceImpl();
		return ca.getFilePath(map);	
	}
	
	public HashMap<String, Object> getDiskConnectLogList(HashMap<String, Object> map){
		IDiskService ds = new DiskServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getDiskConnectLogListCount(map);
		List<DiskConnectLogModel> data = ds.getDiskConnectLogList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;

	}

	public HashMap<String, Object> getDiskInfoList(HashMap<String, Object> map){
		IDiskService ds = new DiskServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getDiskInfoListCount(map);
		List<DiskInfoModel> data = ds.getDiskInfoList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;
		
	}

	
	public HashMap<String, Object> getPartitionConnectLogList(HashMap<String, Object> map){
		IDiskService ds = new DiskServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getPartitionConnectLogListCount(map);
		List<PartitionConnectLogModel> data = ds.getPartitionConnectLogList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;

	}
	
	public HashMap<String, Object> getCDExportList(HashMap<String, Object> map){
		IDeviceInfoService ds = new DeviceInfoServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getCDExportListCount(map);
		List<CDExportLogModel> data = ds.getCDExportList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;

	}
		
	public HashMap<String, Object> getFileOwnershipList(HashMap<String, Object> map){
		IFileService ds = new FileServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getFileOwnershipListCount(map);
		List<FileOwnerShipLogModel> data = ds.getFileOwnershipList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;
	
	}

	public HashMap<String, Object> getFileExportList(HashMap<String, Object> map){
		IFileService ds = new FileServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getFileExportListCount(map);
		List<FileExportLogModel> data = ds.getFileExportList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;	
	}
	
	public HashMap<String, Object> getNetPortLogList(HashMap<String, Object> map){
		INetworkService ds = new NetworkServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getNetPortLogListCount(map);
		List<NetPortLogModel> data = ds.getNetPortLogList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;
	}
	
	public HashMap<String, Object> getNetExportLogList(HashMap<String, Object> map){
		INetworkService ds = new NetworkServiceImpl();
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int total = ds.getNetExportLogListCount(map);
		List<NetExportLogModel> data = ds.getNetExportLogList(map);

		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
				
		return result;
	}
	


	
}
