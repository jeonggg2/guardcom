package gcom.Model;

import lombok.Data;

@Data
public class PolicySerialModel {
	private int serialNo;
	private String serialName ="";
	private String description = "";
	private int allow;
}
