package gcom.service.network;

import java.util.HashMap;
import java.util.List;

import gcom.Model.DiskConnectLogModel;
import gcom.Model.FileExportLogModel;
import gcom.Model.FileOwnerShipLogModel;
import gcom.Model.NetExportLogModel;
import gcom.Model.NetPortLogModel;
import gcom.Model.PartitionConnectLogModel;

public interface INetworkService {
	public List<NetPortLogModel> getNetPortLogList(HashMap<String, Object> map);
	public int getNetPortLogListCount(HashMap<String, Object> map);
	public List<NetExportLogModel> getNetExportLogList(HashMap<String, Object> map);
	public int getNetExportLogListCount(HashMap<String, Object> map);
	
}
