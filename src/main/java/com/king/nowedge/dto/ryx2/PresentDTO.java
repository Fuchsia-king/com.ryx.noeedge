package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxAction entity. @author MyEclipse Persistence Tools
 */

public class PresentDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	private Integer type;
	private Long value;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
	


	
}