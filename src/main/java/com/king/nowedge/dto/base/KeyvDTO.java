package com.king.nowedge.dto.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class KeyvDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
	private String uid ; 
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{common.key.not.empty}")
	private String key1 ; 	
	
	private String value ;
		
	private Integer type ; 
	
	private Integer sort = 1;
	
	private Integer display = 1;
	
	private Integer ideleted;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getIdeleted() {
		return ideleted;
	}

	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
	
	
	
	

}
