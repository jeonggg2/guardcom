package gcom.service.Personal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gcom.Model.FileInfoModel;
import gcom.Model.MailExportContentModel;
import gcom.Model.MailExportModel;
import gcom.Model.MsnFileModel;
import gcom.Model.MsnTalkModel;
import gcom.Model.PrivacyLogModel;
import gcom.Model.UserInfoModel;
import gcom.user.model.UserContactModel;

public interface IPersonalService {
	public List<MailExportModel> getMailExportList(HashMap<String, Object> map);
	public int getMailExportListCount(HashMap<String, Object> map);
	public List<MsnTalkModel> getMsnTalkList(HashMap<String, Object> map);
	public int getMsnTalkListCount(HashMap<String, Object> map);
	public List<MsnFileModel> getMsnFileList(HashMap<String, Object> map);
	public int getMsnFileListCount(HashMap<String, Object> map);
	public List<PrivacyLogModel> getPrivacyFileList(HashMap<String, Object> map);
	public int getPrivacyFileListCount(HashMap<String, Object> map);
	public FileInfoModel getAttFileInfo(HashMap<String, Object> map);
	public HashMap<String, Object> insertNoticeWriteSave(HashMap<String, Object> map);
	public HashMap<String, Object> applyPolicyDataSave(HashMap<String, Object> map);
	public MailExportContentModel getMailExportContent(HashMap<String, Object> map);
	public HashMap<String, Object> updateNoticeModifyUpdate(HashMap<String, Object> map);
	public List<UserContactModel> getAdminContactList(HashMap<String, Object> map);
	public int getAdminContactListCount(HashMap<String, Object> map);
	public HashMap<String, Object> insertContactCommentSave(HashMap<String, Object> map);
	public HashMap<String, Object> getCommentInfo(HashMap<String, Object> map);
	public HashMap<String, Object> updateContactCommentUpdate(HashMap<String, Object> map);
	public HashMap<String, Object> getCurrentPolicyCheck(List<Map<String, Object>> apply_list);
	public HashMap<String, Object> updateNoticeDelete(HashMap<String, Object> map);
	public HashMap<String, Object> getApplyPolicyAllUserInfo(HashMap<String, Object> map);
}
