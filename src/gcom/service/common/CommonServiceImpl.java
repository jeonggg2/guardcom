package gcom.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gcom.DAO.CommonDAO;

public class CommonServiceImpl implements ICommonService {
	
	CommonDAO comDao = new CommonDAO();
	
	public void setPolicyUpdateToInsertLog(int key) {
		comDao.setPolicyUpdateToInsertLog(key);
	}
	
	public List<HashMap<String, Object>> getFileList(HashMap<String, Object> map){

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		CommonDAO ca = new CommonDAO();
		String data = ca.getFileList(map);

		int count = 0;
		double offset = 0;
		String[] fileNameList = data.split("\\r\\n|\n");//data.split("\n");
		count = fileNameList.length;
		
		for(int i = 0; i < count; i += 2){
			HashMap<String, Object> fileData = new HashMap<String, Object>();
			fileData.put("index", (i+2)/2);
			fileData.put("fileName", fileNameList[i]);
			
			if (i+1 >= count) {
				
				break;
			}
			
			if (count > 1) {
				
				fileData.put("fileSize", fileNameList[i + 1]);
				fileData.put("fileOffset", offset);		
				offset += Double.parseDouble(fileNameList[i + 1]);	
				
			} else {
				
				fileData.put("fileSize", 0);
				fileData.put("fileOffset", 0);
			}
	
			list.add(fileData);
		}

		
		return list;

	}
	
	public String getFilePath(HashMap<String, Object> map){
		CommonDAO ca = new CommonDAO();
		return ca.getFilePath(map);
	}
}
