package com.king.nowedge.dto.ryx;

/**
 * RyxOrderCharge entity. @author MyEclipse Persistence Tools
 */

public class RyxOrderChargeDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderType;
	private String payType;
	private String returnOrderId;
	private Integer objId;
	private String uid;
	private String username;
	private Integer payTime;
	private Double payMoney;
	private String orderStatus;
	private String payInfo;

	// Constructors

	/** default constructor */
	public RyxOrderChargeDTO() {
	}

	/** full constructor */
	public RyxOrderChargeDTO(String orderType, String payType,
			String returnOrderId, Integer objId, String uid, String username,
			Integer payTime, Double payMoney, String orderStatus, String payInfo) {
		this.orderType = orderType;
		this.payType = payType;
		this.returnOrderId = returnOrderId;
		this.objId = objId;
		this.uid = uid;
		this.username = username;
		this.payTime = payTime;
		this.payMoney = payMoney;
		this.orderStatus = orderStatus;
		this.payInfo = payInfo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getReturnOrderId() {
		return this.returnOrderId;
	}

	public void setReturnOrderId(String returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}

	public Double getPayMoney() {
		return this.payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayInfo() {
		return this.payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

}