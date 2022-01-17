package gcom.itasset.hw.service;

import java.util.HashMap;
import java.util.List;

import gcom.itasset.dao.ITAssetDAO;
import gcom.itasset.model.ITAssetHwModel;

public class ITAssetHwService implements IITAssetHwService {
	
	ITAssetDAO iaDao = new ITAssetDAO();
	
	@Override
	public List<ITAssetHwModel> getDeviceList(HashMap<String, Object> map) {
		return iaDao.getDeviceList(map);
	}
	@Override
	public int getDeviceListCount(HashMap<String, Object> map) {
		return iaDao.getDeviceListCount(map);
	}
}
