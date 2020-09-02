package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class DeptDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{org.code.not.empty}")
	private String code; 
	
	@NotEmpty(message="{org.name.not.empty}")
	private String name; 
	
	private String parent; 
	
	private String manager ;
	
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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
