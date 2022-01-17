package gcom.service.common;

import java.util.HashMap;
import java.util.List;

public interface ICommonService {

	public void setPolicyUpdateToInsertLog(int key) ;
	public List<HashMap<String, Object>> getFileList(HashMap<String, Object> map);
	public String getFilePath(HashMap<String, Object> map);

	
}
