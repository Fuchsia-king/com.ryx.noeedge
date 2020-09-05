package com.king.nowedge.dto.ryx2;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RegisterDTO2 implements java.io.Serializable {

	// Fields

	
	@Pattern(regexp = "[a-zA-Z0-9_]*" ,message="{common.username.invalid}")
	//@Length(min=4,message="{common.username.invalid}")
	private String username;
	
	@Email(message="{common.email.invalid}")
	//@NotEmpty(message="{common.email.not.empty}")
	private String email;
	
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	//@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile;
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;
	
	@NotEmpty(message="{common.password.not.empty}")
	private String password;
	
	@NotEmpty(message="{common.confirmPassword.not.empty}")
	private String confirmPassword;
	
	@NotEmpty(message="{register.isAgreeProtocal.not.empty}")
	private String isAgreeProtocal;
	
	
	@NotEmpty(message="{common.imgVerifyCode.not.empty}")
	private String imgVerifyCode;
	
	private Long mid;
	
	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
	
	public Long getMid() {
		return mid;
	}
	public void setMid(Long mid) {
		this.mid = mid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}
	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getImgVerifyCode() {
		return imgVerifyCode;
	}


	public void setImgVerifyCode(String imgVerifyCode) {
		this.imgVerifyCode = imgVerifyCode;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	} 
	
	
	
	

}