package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;



/**
 * 
 * @author wangdap
 *
 */
public class AuthRoleQuery extends LoreBaseQuery {
	
	
	private String sysmenuId; 
	
	private String roleId;

	

	public String getSysmenuId() {
		return sysmenuId;
	}

	public void setSysmenuId(String sysmenuId) {
		this.sysmenuId = sysmenuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	
	
	

}
