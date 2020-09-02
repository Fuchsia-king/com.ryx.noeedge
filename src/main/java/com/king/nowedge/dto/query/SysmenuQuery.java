package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class SysmenuQuery extends LoreBaseQuery {
	
	
	private String name;
	
	private Long pid;
	
	private Long userId;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	} 
	
	
	
	

}
