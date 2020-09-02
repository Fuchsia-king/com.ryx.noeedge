package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class ProjectDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{task.title.not.empty}") 	
	private String title; 
	
	@NotEmpty(message="{task.descr.not.empty}") 
	private String descr; 
	
	private String status;
	
	private String sname;  // status name 
	
	private String recver ;
	
	@NotEmpty(message="{task.type.not.empty}") 
	private String type; 
	
	private String tname ; // type name ;  
	
	

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecver() {
		return recver;
	}

	public void setRecver(String recver) {
		this.recver = recver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	} 
	
	
	

}
