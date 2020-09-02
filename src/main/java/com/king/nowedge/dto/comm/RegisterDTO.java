package com.king.nowedge.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{common.username.not.empty}") 
	private String username;
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}") 
	private String email;
	
	@NotEmpty(message="{common.passd.not.empty}")
	private String passd;
	
	@NotEmpty(message="{common.confirmPassd.not.empty}")
	private String confirmPassd;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassd() {
		return confirmPassd;
	}

	public void setConfirmPassd(String confirmPassd) {
		this.confirmPassd = confirmPassd;
	} 
	

}
