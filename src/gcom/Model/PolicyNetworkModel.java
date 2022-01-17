package gcom.Model;

import lombok.Data;

@Data
public class PolicyNetworkModel {
	private int netNo;
	private String netName ="";
	private String port = "";
	private String descriptor = "";
	private int allow;
}
