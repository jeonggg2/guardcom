package gcom.itasset.hw.service;

import java.util.HashMap;
import java.util.List;
import gcom.itasset.model.ITAssetHwModel;

public interface IITAssetHwService {
	public List<ITAssetHwModel> getDeviceList(HashMap<String, Object> map);
	public int getDeviceListCount(HashMap<String, Object> map);
}
