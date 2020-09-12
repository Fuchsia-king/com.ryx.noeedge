package com.king.nowedge.query.base;


public class LoreBaseQuery extends BaseQuery4MySQL {
	
	
	
	private Long id ; 
	
	private String uid; 
	
	private Integer ideleted;
	
	private Long createrId;
	
	private Long creater ;

	public Integer getIdeleted() {
		return ideleted;
	}

	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	}
	
	
	private String keyword ;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}
	
	
	
	
	
	

}
