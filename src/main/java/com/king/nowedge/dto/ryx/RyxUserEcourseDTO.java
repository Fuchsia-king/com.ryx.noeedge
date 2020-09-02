package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAccountType;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxUserEcourseDTO extends BaseDTO implements java.io.Serializable {

	
	
	private Long userId;
	
	private Long ecid ;
	
	private Long ecid1 ;
	
	private Integer category ;
	
	private Integer status ;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEcid() {
		return ecid;
	}

	public void setEcid(Long ecid) {
		this.ecid = ecid;
	}

	public Long getEcid1() {
		return ecid1;
	}

	public void setEcid1(Long ecid1) {
		this.ecid1 = ecid1;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	
}