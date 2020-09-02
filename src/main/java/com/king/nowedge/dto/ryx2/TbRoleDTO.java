package com.king.nowedge.dto.ryx2;

/**
 * TbRole entity. @author MyEclipse Persistence Tools
 */

public class TbRoleDTO implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String roleName;
	private String rights;

	// Constructors

	/** default constructor */
	public TbRoleDTO() {
	}

	/** full constructor */
	public TbRoleDTO(String roleName, String rights) {
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