package gcom.service.Device;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.CDDataDAO;
import gcom.DAO.DeviceDataDAO;
import gcom.DAO.DiskDataDAO;
import gcom.Model.CDExportLogModel;
import gcom.Model.DiskExportModel;
import gcom.Model.FileEventLogModel;
import gcom.Model.PrintFileModel;
import gcom.Model.UsbConnectModel;
import gcom.Model.UsbDevInfoModel;

public class DeviceInfoServiceImpl implements IDeviceInfoService {

	DeviceDataDAO uaDao = new DeviceDataDAO();
	DiskDataDAO diskDao = new DiskDataDAO();
	CDDataDAO cdDao = new CDDataDAO();
	
	//비인가 USB목록
	public List<UsbDevInfoModel> getUnAuthUsbList(HashMap<String, Object> map){		
		return diskDao.getUnAuthUsbList(map);
	}	
	public int getUnAuthUsbListCount(HashMap<String, Object> map){		
		return diskDao.getUnAuthUsbListCount(map);		
	}
	
	//디스크반출 로그
	public List<DiskExportModel> getDiskTranList(HashMap<String, Object> map){		
		return diskDao.getDiskExportList(map);
	}	
	public int getDiskTranListCount(HashMap<String, Object> map){		
		return diskDao.getDiskExportListCount(map);		
	}

	//USB차단로그
	public List<UsbConnectModel> getUsbBlockList(HashMap<String, Object> map){		
		return diskDao.getUsbConnectList(map);
	}	
	public int getUsbBlockListCount(HashMap<String, Object> map){		
		return diskDao.getUsbConnectListCount(map);		
	}

	
	//프린트로그
	public List<PrintFileModel> getPrintLogList(HashMap<String, Object> map){		
		return uaDao.getPrintLogList(map);
	}	
	
	public int getPrintLogCount(HashMap<String, Object> map){		
		return uaDao.getPrintLogListCount(map);		
	}

	//이동식 디스크 파일전송로그
	public List<FileEventLogModel> getRmvDiskFileLogList(HashMap<String, Object> map){		
		return diskDao.getRmvDiskFileLogList(map);
	}	
	
	public int getRmvDiskFileLogListCount(HashMap<String, Object> map){
		return diskDao.getRmvDiskFileLogListCount(map);		
	}

	public List<CDExportLogModel> getCDExportList(HashMap<String, Object> map){
		return cdDao.getCDExportList(map);
	}
	public int getCDExportListCount(HashMap<String, Object> map){
		return cdDao.getCDExportListCount(map);
		
	}

	
}
