package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * RyxAction entity. @author MyEclipse Persistence Tools
 */

public class EitemQuery extends LoreBaseQuery  {

	private String title;
	private Integer category;
	private Integer difficulty;
	private Integer type;
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
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

	
	

}