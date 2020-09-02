package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class TaskQuery extends LoreBaseQuery {
	
	
	private String title; 
	
	private String uid;
	
	private String type ; 
	
	
	private Long recver;
	
	private Long involer  ;  //参与着
	
	private Integer otype ;

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

	

	
	public Long getRecver() {
		return recver;
	}

	public void setRecver(Long recver) {
		this.recver = recver;
	}

	public Long getInvoler() {
		return involer;
	}

	public void setInvoler(Long involer) {
		this.involer = involer;
	}

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
	} 

	
	
	
	

}
