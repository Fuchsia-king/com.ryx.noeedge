package com.king.nowedge.dto.base;

import org.hibernate.validator.constraints.NotEmpty;

public class KeyvalueDTO extends BaseDTO{
	
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{common.key.not.empty}")
	private String key1 ; 
	
	
	private String value ;
		
	private Integer type ; 
	
	private String pid; 
	
	private String pid1; 
	
	private String pid2; 
	
	private String value1;
	
	private String value2;
	
	private String value3;
	
	private String value4;

	

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getPid1() {
		return pid1;
	}

	public void setPid1(String pid1) {
		this.pid1 = pid1;
	}

	public String getPid2() {
		return pid2;
	}

	public void setPid2(String pid2) {
		this.pid2 = pid2;
	}
	
	
	
	
	
	
	

}
