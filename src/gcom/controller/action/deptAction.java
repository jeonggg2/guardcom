package gcom.controller.action;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.DeptDAO;
import gcom.Model.DeptModel;
import gcom.Model.DeptTreeModel;
import gcom.service.Department.DeptServiceImpl;
import gcom.service.Department.IDeptService;

public class deptAction {

	public List<DeptModel> getDeptList(int adminNumber){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.getDeptList(adminNumber);
	}
	
	public List<Integer> getDeptIntList(int deptNo){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.getDeptIntList(deptNo);		
	}

	public List<DeptTreeModel> getDeptListForJSTree(int adminNumber){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.getDeptListForJSTree(adminNumber);
	}

	public List<DeptTreeModel> getSelectDeptListForJSTree(int adminNumber){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.getSelectDeptListForJSTree(adminNumber);
	}
	
	public DeptModel getDeptInfo(int deptNo){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.getDeptInfo(deptNo);
	}

	
	public HashMap<String, Object> insertDeptInfo(DeptModel model){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.insertDeptInfo(model);
		
	}
	public HashMap<String, Object> updateDeptNameInfo(DeptModel model){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.updateDeptNameInfo(model);
		
	}
	public HashMap<String, Object> removeDeptInfo(int deptNo){
		IDeptService dsv = new DeptServiceImpl();

		return dsv.removeDeptInfo(deptNo);
		
	}

}
