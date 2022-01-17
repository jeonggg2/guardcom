package gcom.itasset.service;

import java.util.HashMap;
import java.util.List;

import gcom.itasset.dao.ITAssetDAO;
import gcom.itasset.model.ITAssetHwModel;
import gcom.itasset.model.ITAssetSwModel;

public class ITAssetSwService implements IITAssetService<ITAssetSwModel> {

	ITAssetDAO dao = new ITAssetDAO();
	
	public void uninstall() {
		return;
	}
	
	public List<String> getSwNameList() {
		return dao.getSwNameList();
	}
	
	public List<ITAssetHwModel> getUsageList(HashMap<String, Object> map) {
		return dao.getUsageList(map);
	}
	
	public int getUsageListCount(HashMap<String, Object> map) {
		return dao.getUsageListCount(map);
	}
	
	@Override
	public HashMap<String, Object> addItem(HashMap<String, Object> map) {
		return dao.registerSw(map);
	}

	@Override
	public HashMap<String, Object> deleteItem(HashMap<String, Object> map) {
		return dao.unregisterSw(map);
	}

	@Override
	public HashMap<String, Object> modifyItem(HashMap<String, Object> map) {
		return dao.modifySw(map);
	}

	@Override
	public ITAssetSwModel getItem(int no) {
		return dao.getInfo(no, ITAssetSwModel.class);
	}
	
	@Override
	public List<ITAssetSwModel> getList(HashMap<String, Object> map) {
		return dao.getSwList(map);
	}

	@Override
	public int getListCount(HashMap<String, Object> map) {
		return dao.getSwListCount(map);
	}

}
