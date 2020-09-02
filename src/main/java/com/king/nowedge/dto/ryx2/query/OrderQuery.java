package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * RyxOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String orderName;
	private Integer orderTime;
	private Double orderAmount;
	private String orderStatus;
	private Long orderUid;
	private String orderUsername;
	private String returnOrderId;
	private Integer sellerUid;
	private String sellerUsername;
	private Integer status;
	private Long courseId;
	private Integer ifFeedback;
	private String orderIdStr;

	private Long tnow;
	private String uid;
	private Long partnerId;
	private Integer orderType;
	private Integer payType;
	private Integer objType;
	private Integer flag ;

	private Integer source ;
	
	
	private Long startTime;
	private Long endTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dstartTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dendTime;
	
	
	public String getOrderIdStr() {
		return orderIdStr;
	}

	public void setOrderIdStr(String orderIdStr) {
		this.orderIdStr = orderIdStr;
	}

	/** default constructor */
	public OrderQuery() {
	}
	
	

	

	// Property accessors



	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Integer getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}

	public Double getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	

	public Long getOrderUid() {
		return this.orderUid;
	}

	public void setOrderUid(Long orderUid) {
		this.orderUid = orderUid;
	}

	public String getOrderUsername() {
		return this.orderUsername;
	}

	public void setOrderUsername(String orderUsername) {
		this.orderUsername = orderUsername;
	}

	public String getReturnOrderId() {
		return this.returnOrderId;
	}

	public void setReturnOrderId(String returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public Integer getSellerUid() {
		return this.sellerUid;
	}

	public void setSellerUid(Integer sellerUid) {
		this.sellerUid = sellerUid;
	}

	public String getSellerUsername() {
		return this.sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}


	public Integer getIfFeedback() {
		return this.ifFeedback;
	}

	public void setIfFeedback(Integer ifFeedback) {
		this.ifFeedback = ifFeedback;
	}

	

	public Long getTnow() {
		return tnow;
	}

	public void setTnow(Long tnow) {
		this.tnow = tnow;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
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
	
	

}