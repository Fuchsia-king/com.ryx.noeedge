package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.NotEmpty;


public class SubAccountDTO {


	
	//@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	//@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile;
	
	@NotEmpty(message="{common.username.not.empty}")
	private String username ;
	
	@NotEmpty(message="{common.isAgreeProtocal.not.empty}")
	private String isAgreeProtocal;
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}
	
	
	
	
	
}