package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class EitemDTO extends BaseDTO implements java.io.Serializable {

	private String title;
	private String descr;
	private Integer category;
	private Integer type;
	private Integer difficulty;
	private String analysis;
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	
	
	
}