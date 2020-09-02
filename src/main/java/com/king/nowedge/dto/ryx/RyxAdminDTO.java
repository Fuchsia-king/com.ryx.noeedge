package com.king.nowedge.dto.ryx;

import com.king.nowedge.dto.base.BaseDTO;

public class RyxAdminDTO extends BaseDTO implements java.io.Serializable {
	
	private String password;
	private String username;
	private String email;
	private String mobile;
	private Long userId;
	private Integer dept ;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}
	
	
	
	
	

}
