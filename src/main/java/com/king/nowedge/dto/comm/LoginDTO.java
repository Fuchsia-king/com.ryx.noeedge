package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{common.username.not.empty}") 
	private String username;
	
	@NotEmpty(message="{common.passd.not.empty}")
	private String passd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassd() {
		return passd;
	}

	public void setPassd(String passd) {
		this.passd = passd;
	} 
	
	
	
	


}
