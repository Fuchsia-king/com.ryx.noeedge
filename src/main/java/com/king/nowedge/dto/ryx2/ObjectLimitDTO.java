package com.king.nowedge.dto.ryx2;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumOrderStatus;

/**
 * 
 * @author Administrator
 *
 */
public class ObjectLimitDTO extends BaseDTO implements java.io.Serializable {

	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

	
	/**
	 * 体系课程的排序
	 */
	private Integer sort ;
	
	
	private Long userId;
	
	private Integer otype;
	
	
	/**
	 * 
	 */
	private Long oid;
	
	
	/**
	 *  主课程 Id
	 */
	private Long moid;
	
	
	/**
	 * 主账号Id
	 */
	private Long muserId ;
	
	private Long limi ;
	
	private Integer avaiDay;
	
	private Integer status = EnumOrderStatus.PAY_SUCCESS.getCode() ;
	
	private Integer category;
	
	private Integer orderType;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	

	public Long getLimi() {
		return limi;
	}

	public void setLimi(Long limi) {
		this.limi = limi;
	}

	public Integer getAvaiDay() {
		return avaiDay;
	}

	public void setAvaiDay(Integer avaiDay) {
		this.avaiDay = avaiDay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Long getMoid() {
		return moid;
	}

	public void setMoid(Long moid) {
		this.moid = moid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getMuserId() {
		return muserId;
	}

	public void setMuserId(Long muserId) {
		this.muserId = muserId;
	}
	
	

}