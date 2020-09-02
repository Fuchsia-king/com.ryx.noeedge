package com.king.nowedge.dto.ryx2;

/**
 * RyxMember entity. @author MyEclipse Persistence Tools
 */

public class MemberDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private Integer login;
	private Long lastLoginIp;
	private Integer lastLoginTime;
	private Short status;

	// Constructors

	/** default constructor */
	public MemberDTO() {
	}

	/** minimal constructor */
	public MemberDTO(Integer login, Long lastLoginIp, Integer lastLoginTime,
			Short status) {
		this.login = login;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
	}

	/** full constructor */
	public MemberDTO(String username, String password, Integer login,
			Long lastLoginIp, Integer lastLoginTime, Short status) {
		this.username = username;
		this.password = password;
		this.login = login;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLogin() {
		return this.login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public Long getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}