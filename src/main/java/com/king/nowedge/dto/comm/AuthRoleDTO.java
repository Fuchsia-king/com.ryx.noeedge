package com.king.nowedge.dto;

import java.util.List;

import com.king.nowedge.dto.base.BaseDTO;


public class AuthRoleDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String sysmenuId;
	
	private String roleId ;
	
	private List<String> menus;
	
	private List<Long> menuIds;

	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	

	public String getSysmenuId() {
		return sysmenuId;
	}

	public void setSysmenuId(String sysmenuId) {
		this.sysmenuId = sysmenuId;
	}

	public List<String> getMenus() {
		return menus;
	}

	public void setMenus(List<String> menus) {
		this.menus = menus;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	
	
	
}
