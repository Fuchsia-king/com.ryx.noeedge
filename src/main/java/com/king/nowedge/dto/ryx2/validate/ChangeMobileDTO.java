package com.king.nowedge.dto.ryx2.validate;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ChangeMobileDTO implements java.io.Serializable {

	// Fields

	
	
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;
	
	@NotEmpty(message="{common.password.not.empty}")
	private String password;
	
	
	@NotEmpty(message="{common.mobile.new.not.empty}")
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	

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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	
	
	

}