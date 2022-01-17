package gcom.Model;

import lombok.Data;

@Data
public class MailExportContentModel {
	private String subject ="";
	private String fileList ="";
	private String srcAddr="";
	private String dstAddr="";	
	private String sendServerTime = "";
	private String sendClientTime = "";
	private String body="";
	private String cc="";
	private String bcc="";

	
}
