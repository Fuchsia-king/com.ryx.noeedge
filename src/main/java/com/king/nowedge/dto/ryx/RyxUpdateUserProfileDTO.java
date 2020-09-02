package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxUpdateUserProfileDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer iid;
	
	@NotEmpty(message="{common.username.not.empty}")
	private String username;
	
	private String password;
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email;
	
	
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	
	@NotEmpty(message="{common.province.not.empty}")
	private String province; 
	
	
	@NotNull(message="{common.industry.not.null}")
	private Integer industry;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getIid() {
		return iid;
	}


	public void setIid(Integer iid) {
		this.iid = iid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public Integer getIndustry() {
		return industry;
	}


	public void setIndustry(Integer industry) {
		this.industry = industry;
	} 
	

	// Constructors
	
	

	
}