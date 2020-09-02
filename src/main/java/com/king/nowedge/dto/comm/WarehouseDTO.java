package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;



/**
 * 仓库
 * @author wangdap
 *
 */
public class WarehouseDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code ; 
	
	private String name; 
	
	private String parent ;
	
	private String manager ;
	
	private String addr ; 
	
	private String rcity ;
	
	private String test; 
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRcity() {
		return rcity;
	}

	public void setRcity(String rcity) {
		this.rcity = rcity;
	} 
	
	
	
	
	
	


}
