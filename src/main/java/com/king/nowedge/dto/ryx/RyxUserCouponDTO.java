package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAccountType;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxUserCouponDTO extends BaseDTO implements java.io.Serializable {

	
	
	/**
	 * 
	 */
	@NotEmpty(message="{common.coupon.not.null}")
	private String remark;
	
	
	/**
	 * 
	 */
	
	@NotNull(message="{common.userId.not.null}")
	private Long userId;
	
	
	/**
	 * 
	 */
	
	@NotNull(message="{common.coupon.not.null}")
	private Double coupon;
	
	
	
	/**
	 * 
	 */
	private Long orderId;
	
	
	/**
	 * 余额
	 */
	private Double balance;
	
	
	private Long lcreate;
	
	private Integer objType; 
	
	private Integer type = EnumAccountType.COUPON.getCode();
	
	
	/**
	 * 优惠券是否使用
	 */
	private Integer iuse ;
	
	
	/**
	 * 优惠券到期日
	 */
	private Long limi ; 
	
	
	private Integer days ;

	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getLcreate() {
		return lcreate;
	}

	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getIuse() {
		return iuse;
	}

	public void setIuse(Integer iuse) {
		this.iuse = iuse;
	}

	public Long getLimi() {
		return limi;
	}

	public void setLimi(Long limi) {
		this.limi = limi;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	
	
	
}