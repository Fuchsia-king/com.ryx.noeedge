package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxOrder entity.
 * 
 * @author MyEclipse 
 * Persistence Tools
 */
public class RyxAdminOrderDTO extends BaseDTO implements java.io.Serializable {

	@NotEmpty(message="{adminOrder.courses.not.empty}")
	private Long[] courseIds;
	
	@NotNull(message="{adminOrder.realPrice.not.empty}")
	private Double realPrice;
	
	@NotNull(message="{adminOrder.partnerId.not.empty}")
	private Long partnerId;
	
	
	private Integer avaiDays;
	
	@NotEmpty(message="{adminOrder.orderUid.not.empty}")
	private String username;
	
	
	private Long orderUid;
	
	@NotEmpty(message="{adminOrder.remark.not.empty}")
	private String remark;
	
	
	public Long[] getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(Long[] courseIds) {
		this.courseIds = courseIds;
	}
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}	
	public Long getOrderUid() {
		return orderUid;
	}
	public void setOrderUid(Long orderUid) {
		this.orderUid = orderUid;
	}
	public Integer getAvaiDays() {
		return avaiDays;
	}
	public void setAvaiDays(Integer avaiDays) {
		this.avaiDays = avaiDays;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	

}