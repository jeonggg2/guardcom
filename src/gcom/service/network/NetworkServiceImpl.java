package gcom.service.network;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.DeviceDataDAO;
import gcom.DAO.DiskDataDAO;
import gcom.DAO.FileDataDAO;
import gcom.DAO.NetworkManageDAO;
import gcom.Model.DiskConnectLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.FileExportLogModel;
import gcom.Model.FileOwnerShipLogModel;
import gcom.Model.NetExportLogModel;
import gcom.Model.NetPortLogModel;
import gcom.Model.PartitionConnectLogModel;
import gcom.Model.PrintFileModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;

public class NetworkServiceImpl implements INetworkService {

	NetworkManageDAO netDao = new NetworkManageDAO();
	
	public List<NetPortLogModel> getNetPortLogList(HashMap<String, Object> map){
		return netDao.getNetPortLogList(map);
	}
	public int getNetPortLogListCount(HashMap<String, Object> map){
		return netDao.getNetPortLogListCount(map);
	}

	public List<NetExportLogModel> getNetExportLogList(HashMap<String, Object> map){
		return netDao.getNetExportLogList(map);
	}

	public int getNetExportLogListCount(HashMap<String, Object> map){
		return netDao.getNetExportLogListCount(map);
	}
}
