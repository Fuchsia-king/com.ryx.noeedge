package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ChangeEmailDTO implements java.io.Serializable {
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;
	
	//@NotEmpty(message="{common.password.not.empty}")
	private String password;
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.new.not.empty}")
	private String email;
	
	

	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	

}