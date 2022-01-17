package gcom.service.Department;

import java.util.HashMap;
import java.util.List;

import gcom.Model.DeptModel;
import gcom.Model.DeptTreeModel;

public interface IDeptService {
	List<DeptModel> getDeptList(int adminNumber);
	public List<Integer> getDeptIntList(int deptNo);
	List<DeptTreeModel> getDeptListForJSTree(int adminNumber);
	List<DeptTreeModel> getSelectDeptListForJSTree(int adminNumber);
	DeptModel getDeptInfo(int deptNo);
	HashMap<String, Object> insertDeptInfo(DeptModel model);
	HashMap<String, Object> updateDeptNameInfo(DeptModel model);
	HashMap<String, Object> removeDeptInfo(int deptNo);
}
