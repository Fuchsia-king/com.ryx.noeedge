package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class LoreTagDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	private String tag; 
	
	private Long visit = 1L;
	
	 
	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getVisit() {
		return visit;
	}

	public void setVisit(Long visit) {
		this.visit = visit;
	}

	
	
	
	

}
