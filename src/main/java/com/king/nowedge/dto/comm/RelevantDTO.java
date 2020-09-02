package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;



public class RelevantDTO extends BaseDTO {
	
	
	
	private String sid ; 
	
	private String tid; 
	
	private Integer type ;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	} 
	
	
}
