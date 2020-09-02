package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class ProjectQuery extends LoreBaseQuery {
	
	
	private String title; 
	
	private String uid;
	
	private String type ; 
	
	private Long creater ; 
	
	private String recver;
	
	private String involer  ;  //参与着

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

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public String getRecver() {
		return recver;
	}

	public void setRecver(String recver) {
		this.recver = recver;
	}

	public String getInvoler() {
		return involer;
	}

	public void setInvoler(String involer) {
		this.involer = involer;
	} 

	
	
	
	

}
