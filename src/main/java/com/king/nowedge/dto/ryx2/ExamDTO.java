package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ExamDTO extends BaseDTO implements java.io.Serializable {

	private String title;
	private String descr;
	private Integer category;
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
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	
	
	
}