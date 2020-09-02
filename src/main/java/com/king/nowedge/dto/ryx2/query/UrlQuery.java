package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class UrlQuery extends LoreBaseQuery implements java.io.Serializable {

	
	private Long userId;
	private Long partnerId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	
	
	
	

}