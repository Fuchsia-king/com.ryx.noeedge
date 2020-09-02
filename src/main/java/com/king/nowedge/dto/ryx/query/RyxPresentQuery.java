package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class RyxPresentQuery extends LoreBaseQuery implements java.io.Serializable {
	
	private Integer type;
	private Integer value;	
	private Integer svalue;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getSvalue() {
		return svalue;
	}
	public void setSvalue(Integer svalue) {
		this.svalue = svalue;
	}
	
	
	
	
	
	
	
	

}