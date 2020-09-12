package com.king.nowedge.query;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * 
 * @author wangdap
 *
 */
public class UserRoleQuery extends LoreBaseQuery {
	
	
	private String userId; 
	
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	
	
	

}
