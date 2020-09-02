package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class EcologyDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	Long id;
    Long agree = 0L ;
    Long dagree = 0L ;
    Long visit = 0L ;
    Long comment = 0L ;
    
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	
    

}
