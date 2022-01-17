package gcom.Model;

import lombok.Data;

@Data
public class SystemInfoModel {
	private int sysNo;
	private String description = "";
	private String value = "";
	private String name ="";

	private int upperCharEnabled;
	private int numberEnabled;
	private int specialCharEnabled;
	private int equalCharEnabled;
	private int consecutiveCharEnabled;
	private int includeIdCharEnabled;
	private int minCnt;
	private int maxCnt;
}
