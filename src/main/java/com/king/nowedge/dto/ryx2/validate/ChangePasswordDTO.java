package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ChangePasswordDTO implements java.io.Serializable {

	// Fields

	
	
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;
	
	@NotEmpty(message="{common.password.old.not.empty}")
	private String password;
	
	
	@NotEmpty(message="{common.password.new.not.empty}")
	private String newPassword;
	
	
	@NotEmpty(message="{common.confirmNewPassword.not.empty}")
	private String confirmNewPassword;
	
	

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
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	} 
	
	
	
	

}