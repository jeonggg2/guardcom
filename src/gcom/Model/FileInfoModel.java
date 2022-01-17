package gcom.Model;

import lombok.Data;

@Data
public class FileInfoModel {
	private int attFileId;
	private String attFilePath  = "";
	private String attViewFileName  = "";
	private String attSaveFileName  = "";
}
