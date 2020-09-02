package com.king.nowedge.dto.ryx2;

import java.util.Date;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxTeacher entity. @author MyEclipse Persistence Tools
 */

public class PartnerOrderDTO  extends BaseDTO implements java.io.Serializable {

	
	// Fields
	
	private Long userId;
	private Long partnerId;
	private Long orderId;
	private String ouid;
	
	/**
	 * partner user id
	 */
	private Double amount;
	private Double rate;
	private Double commission;
	private Long lcreate ;
	private Integer status ; // 支付状态
	private Date tpay ; // 支付时间
	
	
	
	
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getOuid() {
		return ouid;
	}
	public void setOuid(String ouid) {
		this.ouid = ouid;
	}
	public Long getLcreate() {
		return lcreate;
	}
	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getTpay() {
		return tpay;
	}
	public void setTpay(Date tpay) {
		this.tpay = tpay;
	}
	
		
}