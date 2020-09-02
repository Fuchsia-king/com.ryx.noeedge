package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class TaskFlowDTO extends BaseDTO {
	
	
	
	private String tid; 
	
	private String descr ; 
	
	private String action; 
	
	private String status ;

	

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	} 
	
	
	
	

}
