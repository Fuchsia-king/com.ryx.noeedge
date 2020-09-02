package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class TaskProcessDTO extends BaseDTO {
	
	
	
	private String action;
	
	private String descr ; 
	
	private String recver;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRecver() {
		return recver;
	}

	public void setRecver(String recver) {
		this.recver = recver;
	} 

	

}
