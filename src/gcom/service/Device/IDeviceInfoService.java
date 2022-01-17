package gcom.service.Device;

import java.util.HashMap;
import java.util.List;

import gcom.Model.CDExportLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.PrintFileModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;

public interface IDeviceInfoService {

	public List<UsbDevInfoModel> getUnAuthUsbList(HashMap<String, Object> map);
	public int getUnAuthUsbListCount(HashMap<String, Object> map);
	public List<DiskExportModel> getDiskTranList(HashMap<String, Object> map);
	public int getDiskTranListCount(HashMap<String, Object> map);
	public List<UsbConnectModel> getUsbBlockList(HashMap<String, Object> map);
	public int getUsbBlockListCount(HashMap<String, Object> map);
	public List<PrintFileModel> getPrintLogList(HashMap<String, Object> map);
	public int getPrintLogCount(HashMap<String, Object> map);
	
	public List<FileEventLogModel> getRmvDiskFileLogList(HashMap<String, Object> map);
	public int getRmvDiskFileLogListCount(HashMap<String, Object> map);

	public List<CDExportLogModel> getCDExportList(HashMap<String, Object> map);
	public int getCDExportListCount(HashMap<String, Object> map);
	

	
	
}
