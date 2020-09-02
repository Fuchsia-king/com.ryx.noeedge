package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class UserQuery extends LoreBaseQuery {
	
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private String code; 
	
	private String qq; 
	
	private String wangwang;
	
	private String email ;
	
	private String mobile; 
	
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date rstart;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date rend; 

	

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
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

	public Date getRstart() {
		return rstart;
	}

	public void setRstart(Date rstart) {
		this.rstart = rstart;
	}

	public Date getRend() {
		return rend;
	}

	public void setRend(Date rend) {
		this.rend = rend;
	}

	
	
	
	
	
	
	
	

}
