package gcom.user.model;

import lombok.Data;

@Data
public class UserInfoModel {
	
	private int userNo = 0;
	private String name = "";
	private String phone = "";
	private String deptName = "";
	private String duty = "";
	private String rank = "";
	private int attfile_id = 0; 
	private String notice = "";
	private String id = "";
	private String number = "";
	
	private String userPhotoPath = "";
	private String userPhotofileSaveName = "";
	private String userPhotoFileViewName = "";
}
