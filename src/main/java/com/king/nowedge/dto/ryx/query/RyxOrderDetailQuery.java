package com.king.nowedge.dto.ryx.query;import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.query.base.LoreBaseQuery;



/**
 * RyxOrderDetail entity. @author MyEclipse Persistence Tools
 */

public class RyxOrderDetailQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Integer detailId;
	private Long orderId;
	private String objType;
	private Long objId;
	private String detailType;
	private Integer isFeedback;
	private Long partnerId;
	private Long objer;
	private Long ouid;
	private Long userId;
	private Integer status ;

	private String year ;
	
	private Integer realPrice ;
	

	
	private Integer oamount ;
	
	private Long startTime;
	private Long endTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dstartTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dendTime;
	
	
	
	

	public Integer getOamount() {
		return oamount;
	}

	public void setOamount(Integer oamount) {
		this.oamount = oamount;
	}

	public Integer getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Integer realPrice) {
		this.realPrice = realPrice;
	}

	public Date getDstartTime() {
		return dstartTime;
	}

	public void setDstartTime(Date dstartTime) {
		this.dstartTime = dstartTime;
	}

	public Date getDendTime() {
		return dendTime;
	}

	public void setDendTime(Date dendTime) {
		this.dendTime = dendTime;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTim) {
		this.endTime = endTim;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOuid() {
		return ouid;
	}

	public void setOuid(Long ouid) {
		this.ouid = ouid;
	}

	public Long getObjer() {
		return objer;
	}

	public void setObjer(Long objer) {
		this.objer = objer;
	}


	public Integer getDetailId() {
		return this.detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}


	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getObjType() {
		return this.objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public Long getObjId() {
		return this.objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	
	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}


	public Integer getIsFeedback() {
		return isFeedback;
	}

	public void setIsFeedback(Integer isFeedback) {
		this.isFeedback = isFeedback;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	
	
	

}