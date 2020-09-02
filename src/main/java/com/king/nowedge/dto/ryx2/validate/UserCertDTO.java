package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class UserCertDTO implements java.io.Serializable {

	
	@NotEmpty(message="{user.name.not.empty}")
	private String name;
	

	@NotEmpty(message="{user.dnCode.not.empty}")
	private String dnCode;
	
	
	@NotEmpty(message="{user.dnPic1.not.empty}")
	private String dnPic1;
	
	
	@NotEmpty(message="{user.dnPic2.not.empty}")
	private String dnPic2;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDnCode() {
		return dnCode;
	}


	public void setDnCode(String dnCode) {
		this.dnCode = dnCode;
	}


	public String getDnPic1() {
		return dnPic1;
	}


	public void setDnPic1(String dnPic1) {
		this.dnPic1 = dnPic1;
	}


	public String getDnPic2() {
		return dnPic2;
	}


	public void setDnPic2(String dnPic2) {
		this.dnPic2 = dnPic2;
	}
	
	
	
	
	
	

	
}