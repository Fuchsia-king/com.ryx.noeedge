package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class BlackEventDTO extends BlackListDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId; 
	

	@NotEmpty(message="{common.descr.not.empty}")
	private String descr ;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	} 
	
	

	
	

}
