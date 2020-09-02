package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class DeptQuery extends LoreBaseQuery {
	
	
	
	private String code; 
	
	private String name; 
	
	private String manager;
	
	private String parent;

	private Integer sort ;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	} 
	
}
