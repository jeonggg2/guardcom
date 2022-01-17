package gcom.Model;

import lombok.Data;

@Data
public class DeptModel {
	private int deptNo;
	private int parent;
	private int leap;
	private int depth;
	private int adminNo;
	private String name;
	private String shortName;
	private int sortIndex;
	private int childCount;	
	private int recnetNo;
	private int minChildNo;
	private int maxChildNo;
	private int policyNo;
	private int deptMemberCount = 0;
	private int deptBelongMemberCount = 0;
	
}
