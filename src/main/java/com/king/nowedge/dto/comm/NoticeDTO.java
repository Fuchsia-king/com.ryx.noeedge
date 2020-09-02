package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class NoticeDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{common.title.not.empty}") 
	private String title;
	
	@NotEmpty(message="{common.descr.not.empty}")
	private String descr;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	

}
