package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxSpreadLinkDTO extends BaseDTO implements java.io.Serializable {

	private String pc;
	
	private String mobile;
	
	private Long partnerId;

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	
	
	
}