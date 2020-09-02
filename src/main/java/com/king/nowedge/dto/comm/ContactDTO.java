package com.king.nowedge.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class ContactDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*
	 * 
	 */
	@NotEmpty(message="{common.name.not.empty}")
	private String name;
	
	@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile  ; 
	
	@NotEmpty(message="{contact.phone.not.empty}")
	private String phone;
	
	@Email(message="{common.email.not.valid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email ;
	
	@NotEmpty(message="{contact.descr.not.empty}")
	private String descr ;	
	
	private Long userId; 	
	
	private Integer idefault ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIdefault() {
		return idefault;
	}

	public void setIdefault(Integer idefault) {
		this.idefault = idefault;
	}

	
}
