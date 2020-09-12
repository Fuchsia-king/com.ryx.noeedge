package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * RyxAction entity. @author MyEclipse Persistence Tools
 */

public class ExamQuery extends LoreBaseQuery  {

	private String title;
	private Integer category;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	

	

}