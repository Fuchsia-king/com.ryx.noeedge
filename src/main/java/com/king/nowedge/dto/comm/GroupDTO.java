package com.king.nowedge.dto;

import java.util.List;

import com.king.nowedge.dto.base.BaseDTO;



public class GroupDTO extends BaseDTO {
	
	private String name ;	
	private Long manager;	
	private List<Long> users;
	
	
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
