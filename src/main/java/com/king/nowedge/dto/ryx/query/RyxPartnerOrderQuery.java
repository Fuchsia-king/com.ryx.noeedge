package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxPartnerOrderQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long partnerId;
	private Long orderId;
	private Integer status ;
	
	
	
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
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	

}