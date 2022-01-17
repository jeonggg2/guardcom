package gcom.service.file;

import java.util.HashMap;
import java.util.List;

import gcom.Model.DiskConnectLogModel;
import gcom.Model.FileExportLogModel;
import gcom.Model.FileOwnerShipLogModel;
import gcom.Model.PartitionConnectLogModel;

public interface IFileService {

	public List<FileOwnerShipLogModel> getFileOwnershipList(HashMap<String, Object> map);
	public int getFileOwnershipListCount(HashMap<String, Object> map);
	public List<FileExportLogModel> getFileExportList(HashMap<String, Object> map);
	public int getFileExportListCount(HashMap<String, Object> map);
}
