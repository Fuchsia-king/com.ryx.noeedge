package com.king.nowedge.dto.ryx;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxForgetPasswordDTO1 implements java.io.Serializable {

	// Fields

	
	
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;
	
	@NotEmpty(message="{common.password.not.empty}")
	private String password;
	
	@NotEmpty(message="{common.imgVerifyCode.not.empty}")
	private String imgVerifyCode;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
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
	public String getImgVerifyCode() {
		return imgVerifyCode;
	}
	public void setImgVerifyCode(String imgVerifyCode) {
		this.imgVerifyCode = imgVerifyCode;
	}

}