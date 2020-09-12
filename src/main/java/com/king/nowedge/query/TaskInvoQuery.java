package com.king.nowedge.query;import com.king.nowedge.query.base.LoreBaseQuery;


public class TaskInvoQuery extends LoreBaseQuery {
	
	
	private String title; 
	
	private String uid;
	
	private String type ; 
	
	
	private String recver;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public String getRecver() {
		return recver;
	}

	public void setRecver(String recver) {
		this.recver = recver;
	} 

	
	
	

}
