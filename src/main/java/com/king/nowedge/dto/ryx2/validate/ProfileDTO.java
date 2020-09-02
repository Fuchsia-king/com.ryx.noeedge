package com.king.nowedge.dto.ryx2.validate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ProfileDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer iid;
	
	//@NotEmpty(message="{common.name.not.empty}")
	private String name;
	
	//@NotEmpty(message="{common.introduction.not.empty}")
	private String introduction;
	
	//@Pattern(regexp = "[a-zA-Z0-9_]*" ,message="{common.username.invalid}")
	//@Length(min=4,message="{common.username.invalid}")
	private String username;
	
	private String password;
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email;
	
	
	//@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	
	@NotEmpty(message="{common.province.not.empty}")
	private String province; 
	
	
	@NotNull(message="{common.industry.not.null}")
	private Integer industry;
	
	
	@NotNull(message="{common.gender.not.null}")
	private Integer gender;
	
	
	//@NotNull(message="{common.birthday.not.null}")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	private String path ;


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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	public Date getBirthday() {
		if(null == birthday){
			return new Date();
		}
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public Integer getGender() {
		return gender;
	}


	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	} 
	
	
	
	

	
}