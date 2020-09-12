package com.king.nowedge.query;import com.king.nowedge.query.base.LoreBaseQuery;


public class ProjectStatusQuery extends LoreBaseQuery {
	
	
	private String uid  ;
	
	private String name; 
	
	private String rstatus ;
	
	private String type ;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	

}
