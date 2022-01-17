package gcom.Model;

import lombok.Data;

@Data
public class PolicyWebSiteBlocklModel {
	private int siteId;
	private String address ="";
	private String description = "";
	private int allow;
}
