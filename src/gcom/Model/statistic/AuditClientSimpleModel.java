package gcom.Model.statistic;

import lombok.Data;

@Data
public class AuditClientSimpleModel {
	private int auditNo;
	private String action;
	private String deptName;
	private String userName;
	private int level;
} 
