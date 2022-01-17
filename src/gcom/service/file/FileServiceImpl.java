package gcom.service.file;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.DeviceDataDAO;
import gcom.DAO.DiskDataDAO;
import gcom.DAO.FileDataDAO;
import gcom.Model.DiskConnectLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.FileExportLogModel;
import gcom.Model.FileOwnerShipLogModel;
import gcom.Model.PartitionConnectLogModel;
import gcom.Model.PrintFileModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;

public class FileServiceImpl implements IFileService {

	FileDataDAO fileDao = new FileDataDAO();
	
	public List<FileOwnerShipLogModel> getFileOwnershipList(HashMap<String, Object> map){
		return fileDao.getFileOwnershipList(map);
	}
	public int getFileOwnershipListCount(HashMap<String, Object> map){
		return fileDao.getFileOwnershipListCount(map);
	}

	public List<FileExportLogModel> getFileExportList(HashMap<String, Object> map){
		return fileDao.getFileExportList(map);
	}

	public int getFileExportListCount(HashMap<String, Object> map){
		return fileDao.getFileExportListCount(map);
	}
}
