package gcom.service.Department;

import java.util.HashMap;
import java.util.List;

import gcom.DAO.DeptDAO;
import gcom.Model.DeptModel;
import gcom.Model.DeptTreeModel;

public class DeptServiceImpl implements IDeptService {
	
	public List<DeptModel> getDeptList(int adminNumber){
		
		DeptDAO dao = new DeptDAO();
		
		List<DeptModel> result = dao.getDeptList(adminNumber);
		
		return result;
		
	}
	
	public List<Integer> getDeptIntList(int deptNo){
		
		DeptDAO dao = new DeptDAO();
		
		return dao.getDeptIntList(deptNo);
		
	}


	public List<DeptTreeModel> getDeptListForJSTree(int adminNumber){
		
		DeptDAO dao = new DeptDAO();
		
		List<DeptTreeModel> result = dao.getDeptListForJSTree(adminNumber);
		
		return result;
		
	}	

	public List<DeptTreeModel> getSelectDeptListForJSTree(int adminNumber){
		
		DeptDAO dao = new DeptDAO();
		
		List<DeptTreeModel> result = dao.getSelectDeptListForJSTree(adminNumber);
		
		return result;
	}	
	
	public DeptModel getDeptInfo(int deptNo){
		DeptDAO dao = new DeptDAO();
		DeptModel data = dao.getDeptInfo(deptNo);
		data.setDeptBelongMemberCount(dao.getDeptMemberCount(deptNo));		
		data.setDeptMemberCount(dao.getDeptsMemberCount(dao.getChildDeptIds(deptNo)));
		return data;		
	}
	
	public HashMap<String, Object> insertDeptInfo(DeptModel model){
		DeptDAO dao = new DeptDAO();
		DeptModel parent = dao.getDeptInfo(model.getParent());
	
		model.setDepth(parent.getDepth());
		return dao.insertDeptInfo(model);
		
	}
	public HashMap<String, Object> updateDeptNameInfo(DeptModel model){
		DeptDAO dao = new DeptDAO();
		return dao.updateDeptNameInfo(model);
	}

	public HashMap<String, Object> removeDeptInfo(int deptNo){
		DeptDAO dao = new DeptDAO();
		return dao.removeDeptInfo(deptNo);		
	}

}
