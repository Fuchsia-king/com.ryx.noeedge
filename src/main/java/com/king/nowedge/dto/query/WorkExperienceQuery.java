package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;



public class WorkExperienceQuery  extends LoreBaseQuery{
	
	/**
	 *  会员Id
	 */
	private Long userId;
	private Integer type;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
	
	

}
