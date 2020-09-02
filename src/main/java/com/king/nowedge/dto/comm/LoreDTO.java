package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class LoreDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Long agree = 0L ;
    Long dagree = 0L ;
    Long visit = 0L ;
    Long comment = 0L ;
    

	private String title; 
	
	private String descr ;
	
	private Long category ;
	
	private String tags ; 
	

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

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getAgree() {
		return agree;
	}

	public void setAgree(Long agree) {
		this.agree = agree;
	}

	public Long getDagree() {
		return dagree;
	}

	public void setDagree(Long dagree) {
		this.dagree = dagree;
	}

	public Long getVisit() {
		return visit;
	}

	public void setVisit(Long visit) {
		this.visit = visit;
	}

	public Long getComment() {
		return comment;
	}

	public void setComment(Long comment) {
		this.comment = comment;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
}
