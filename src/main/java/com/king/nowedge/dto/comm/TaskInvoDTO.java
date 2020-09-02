package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class TaskInvoDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * user unique id 
	 */
	private Long user ;
	
	
	/**
	 * user code 
	 */
	private String ucode; 
	
	
	/**
	 * task id 
	 */
	private String task ;


	public Long getUser() {
		return user;
	}


	public void setUser(Long user) {
		this.user = user;
	}


	public String getUcode() {
		return ucode;
	}


	public void setUcode(String ucode) {
		this.ucode = ucode;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	} 
	
	
	
	

}
