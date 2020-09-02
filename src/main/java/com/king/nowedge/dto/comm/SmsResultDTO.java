package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxAction entity. @author MyEclipse Persistence Tools
 */

public class SmsResultDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	String msg;
	
	Integer code ; 
	
	String detail;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	

	
	
	

}