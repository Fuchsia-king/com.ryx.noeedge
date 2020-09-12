package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxAdminQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String username; 
	
	private String mobile;
	
	private String email;
	
	private Long userId;


	private Integer dept ;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}

	
	
	
	
}