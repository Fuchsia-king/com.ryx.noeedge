package com.king.nowedge.query.ryx2;

/**
 * TbRole entity. @author MyEclipse Persistence Tools
 */

public class TbRoleQuery implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String roleName;
	private String rights;

	// Constructors

	/** default constructor */
	public TbRoleQuery() {
	}

	/** full constructor */
	public TbRoleQuery(String roleName, String rights) {
		this.roleName = roleName;
		this.rights = rights;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRights() {
		return this.rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

}