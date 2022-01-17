package gcom.itasset.model;

import java.util.Date;

import gcom.common.db.Field;
import gcom.common.db.Table;
import lombok.Data;

@Table(value="sw_list", primaryFieldName="no")
@Data
public class ITAssetSwModel {
	
	@Field(value="no", isPrimaryKey=true)
	private int no;
	
	@Field("name")
	private String name;
	
	@Field("type")
	private int type;
	
	@Field("has_own")
	private int hasOwn;
	
	@Field("desc")
	private String desc;
	
	@Field("intro_date")
	private Date introDate;
	
	@Field("commit_date")
	private Date commitDate;
	
	@Field("has_managed")
	private int hasManaged;
}
