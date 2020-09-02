package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class TaskStatusActionDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{task.status.not.empty}") 	
	private String status; 
	
	private String action; 
	
	@NotEmpty(message="{task.type.not.empty}") 
	private String type;
	
	
	@NotEmpty(message="{task.actions.not.empty}") 
	private String actions; 
	
	
	
	/**
	 * status name 
	 */
	private String sname ;
	
	
	/**
	 * action name  
	 */
	private String aname ;
	
	

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	} 
	
	
	

}
