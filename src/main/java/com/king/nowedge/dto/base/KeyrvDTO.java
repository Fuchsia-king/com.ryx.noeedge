package com.king.nowedge.dto.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class KeyrvDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
	private String uid ; 
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{common.key.not.empty}")
	private String key1 ;
	
	private String rkey ;
	
	private List<String> rkeys ;
		
	private Integer type ; 
	
	private Integer sort = 1;
	
	private Integer display = 1;
	
	private Integer ideleted = 0 ;
	
	private String rkey1 ;
	
	private String value ;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	

	public String getRkey() {
		return rkey;
	}

	public void setRkey(String rkey) {
		this.rkey = rkey;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getRkeys() {
		return rkeys;
	}

	public void setRkeys(List<String> rkeys) {
		this.rkeys = rkeys;
	}

	public String getRkey1() {
		return rkey1;
	}

	public void setRkey1(String rkey1) {
		this.rkey1 = rkey1;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
