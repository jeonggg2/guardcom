package gcom.Model.statistic;

import lombok.Data;

@Data
public class RequestSimpleModel {
	private int requestType; //1: 정책요청, 2:가입요청
	private int requestNo;
	private String requestDept;
	private String requestWriter;
}
