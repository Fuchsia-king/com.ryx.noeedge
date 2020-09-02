package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class RoleDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@NotEmpty(message="{role.name.not.empty}")
	private String name ;

	public String getName() {
		return name ;
	}

	public void setName(String name) {
		this.name = name ;
	}
	
}
