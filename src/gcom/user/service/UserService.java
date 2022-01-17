package gcom.user.service;

import java.util.HashMap;
import java.util.List;

import gcom.Model.PolicyMessengerModel;
import gcom.Model.PolicyNetworkModel;
import gcom.Model.PolicyPatternModel;
import gcom.Model.PolicyProcessModel;
import gcom.Model.PolicySerialModel;
import gcom.Model.PolicyWebSiteBlocklModel;
import gcom.Model.UsbDevInfoModel;
import gcom.user.model.MemberPolicyModel;
import gcom.user.model.UserContactModel;
import gcom.user.model.UserInfoModel;
import gcom.user.model.UserNoticeModel;

public interface UserService {

	//public List<HashMap<String, Object>> getUserSystemPolicyList(String code);
	
	public List<MemberPolicyModel> getMemberPolicyInfo(HashMap<String, Object> map);

	public UserInfoModel getUserInfo(HashMap<String, Object> map);
	
	public int getUserNoticeListCount(HashMap<String, Object> map);

	public List<UserNoticeModel> getUserNoticeList(HashMap<String, Object> map);

	public UserNoticeModel getUserNoticeDetail(HashMap<String, Object> map);

	public HashMap<String, Object> getUserContactInfo(HashMap<String, Object> map);

	public void updateNoticeViewCount(HashMap<String, Object> map) throws Exception;

	public HashMap<String, Object> insertContactSave(HashMap<String, Object> map) throws Exception;

	public List<UsbDevInfoModel> getPolicyUsbBlockList(HashMap<String, Object> map);

	public int getPolicyUsbBlockListCount(HashMap<String, Object> map);

	public List<PolicySerialModel> getPolicySerialList(HashMap<String, Object> map);

	public int getPolicySerialListCount(HashMap<String, Object> map);

	public List<PolicyNetworkModel> getPolicyNetworkList(HashMap<String, Object> map);

	public int getPolicyNetworkListCount(HashMap<String, Object> map);

	public List<PolicyProcessModel> getPolicyProcessList(HashMap<String, Object> map);

	public int getPolicyProcessListCount(HashMap<String, Object> map);

	public List<PolicyPatternModel> getPolicyPatternList(HashMap<String, Object> map);

	public int getPolicyPatternListCount(HashMap<String, Object> map);

	public List<PolicyWebSiteBlocklModel> getPolicyWebSiteBlockList(HashMap<String, Object> map);

	public int getPolicyWebSiteBlockListCount(HashMap<String, Object> map);

	public List<PolicyMessengerModel> getPolicyMessengerList(HashMap<String, Object> map);

	public int getPolicyMessengerListCount(HashMap<String, Object> map);

	public UserContactModel getUserContactDetail(HashMap<String, Object> map);
	
	public HashMap<String, Object> insertAccountReqeust(HashMap<String, Object> map);

	public MemberPolicyModel getSettingPolicyInfo(HashMap<String, Object> map);

	public HashMap<String, Object> insertRequestPolicyInfoSave(HashMap<String, Object> map);

	public HashMap<String, Object> updateUserInfoData(HashMap<String, Object> map);

	public HashMap<String, Object> getLatestListData();

	public boolean selectUserDupCnt(HashMap<String, Object> param);

}
