package gcom.Model;

import lombok.Data;

@Data
public class DeptTreeModel {
	private String id;
	private String parent;
	private String text;
	private String data;
	//JSTree에서 가장상위 Parent는 #으로 표기되어야 함
	public void setParent(String value){
		if(value.equals("0")){
			parent = "#";
		}else{
			parent = value;
		}		
	}
}
