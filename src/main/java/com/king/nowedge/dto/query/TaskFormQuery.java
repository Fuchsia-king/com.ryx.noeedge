package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class TaskFormQuery extends LoreBaseQuery {
	
	
	private String title; 
	private Long type ;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	} 
	

	
	

}
