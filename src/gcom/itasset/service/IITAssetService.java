package gcom.itasset.service;

import java.util.HashMap;
import java.util.List;

public interface IITAssetService<E> {
	public HashMap<String, Object> addItem(HashMap<String, Object> map);
	public HashMap<String, Object> deleteItem(HashMap<String, Object> map);
	public HashMap<String, Object> modifyItem(HashMap<String, Object> map);
	public E getItem(int no);
	public List<E> getList(HashMap<String, Object> map);
	public int getListCount(HashMap<String, Object> map);
}
