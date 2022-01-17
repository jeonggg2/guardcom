package gcom.Model;

import lombok.Data;

@Data
public class PartitionModel {
	private int connectNo;
	private String userNo;
	private String userName = "";
	private String userId = "";
	private int deptId ;
	private String duty = "";
	private String rank = "";
	private String ipAddr = "";
	private String macAddr = "";
	private String pcName = "";
	private String deptName = "";

	private String guid = "";
	private String label = "";
	private String diskGuid = "";
	private String createServerTime = "";
	private String createClientTime = "";

	private String updateServerTime = "";
	private String updateClientTime = "";
}
