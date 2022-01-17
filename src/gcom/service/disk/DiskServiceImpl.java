package gcom.service.disk;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.DeviceDataDAO;
import gcom.DAO.DiskDataDAO;
import gcom.Model.DiskConnectLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.DiskInfoModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.PartitionConnectLogModel;
import gcom.Model.PrintFileModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;

public class DiskServiceImpl implements IDiskService {

	DiskDataDAO diskDao = new DiskDataDAO();
	
	public List<DiskConnectLogModel> getDiskConnectLogList(HashMap<String, Object> map){
		return diskDao.getDiskConnectLogList(map);
	}
	public int getDiskConnectLogListCount(HashMap<String, Object> map){
		return diskDao.getDiskConnectLogListCount(map);
	}

	public List<PartitionConnectLogModel> getPartitionConnectLogList(HashMap<String, Object> map){
		return diskDao.getPartitionConnectLogList(map);
		
	}
	public int getPartitionConnectLogListCount(HashMap<String, Object> map){
		return diskDao.getPartitionConnectLogListCount(map);
		
	}
	
	public List<DiskInfoModel> getDiskInfoList(HashMap<String, Object> map){
		return diskDao.getDiskInfoList(map);
		
	}

	public int getDiskInfoListCount(HashMap<String, Object> map){
		return diskDao.getDiskInfoListCount(map);
	}

	public HashMap<String, Object> updateDiskAllow(HashMap<String, Object> map){
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("returnCode", diskDao.updateDiskAllow(map));
		return result;
		
	}

	
}
