package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;

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