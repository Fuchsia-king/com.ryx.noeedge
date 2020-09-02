package com.king.nowedge.dto;

import java.io.Serializable;
import java.util.List;

import com.king.nowedge.dto.base.BaseDTO;


/**
 * 任务处理角色定义
 * @author Administrator
 *
 */
public class TaskRoleDTO extends BaseDTO implements Serializable{
	
	
	/*
	 * task type;
	 */
	private Long ttype ;
	
	/**
	 * role id 
	 */
	private Long role ;
	
	

	public Long getTtype() {
		return ttype;
	}

	public void setTtype(Long ttype) {
		this.ttype = ttype;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	
	
	

}
