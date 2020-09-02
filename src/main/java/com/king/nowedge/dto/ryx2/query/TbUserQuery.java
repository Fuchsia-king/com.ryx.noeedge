package com.king.nowedge.dto.ryx2.query;

import java.sql.Timestamp;

/**
 * TbUser entity. @author MyEclipse Persistence Tools
 */

public class TbUserQuery implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String loginname;
	private String password;
	private String username;
	private String rights;
	private Boolean status;
	private Integer roleId;
	private Timestamp lastLogin;

	// Constructors

	/** default constructor */
	public TbUserQuery() {
	}

	/** full constructor */
	public TbUserQuery(String loginname, String password, String username,
			String rights, Boolean status, Integer roleId, Timestamp lastLogin) {
		this.loginname = loginname;
		this.password = password;
		this.username = username;
		this.rights = rights;
		this.status = status;
		this.roleId = roleId;
		this.lastLogin = lastLogin;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRights() {
		return this.rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Timestamp getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

}