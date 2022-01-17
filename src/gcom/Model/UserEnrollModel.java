package gcom.Model;

import lombok.Data;

@Data
public class UserEnrollModel {
	private int requestId;
	private String userNumber;
	private String userId = "";
	private String password = "";
	private String memberName = "";
	private String memberPhone = "";
	private String memberDuty = "";
	private String memberRank = "";
	private String requestDate = "";
	private String permit = ""; 
	private String permitDate = "";
	private String deptName = "";
	private String permitAdmin = "";


	public void setPermitDate(String value){
		if(value == null){
			permitDate = "";
		}else{
			permitDate = value;		
		}
	}


}

