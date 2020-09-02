package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * RyxOrder entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxOrderDTO implements java.io.Serializable {
	
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

	// Fields

	private Long id;
	private String uid;
	private String orderName;
	private Integer orderTime;
	
	
	private List<Long> users ;
	
	
	/**
	 * 折扣一之后的价格
	 */
	private Double orderAmount;
	

	private String orderStatus;
	private String orderBody;
	private Long orderUid;
	private String orderUsername;
	private String returnOrderId;
	private Integer sellerUid;
	private String sellerUsername;
	private String toSellerMessage;
	private Integer status;
	private Long courseId;
	private String avaiTime;
	private Integer ifFeedback;
	private List<RyxOrderDetailDTO> detailList;
	private String orderIdStr;
	private Double useBalance;
	
	/**
	 * 是否管理员订单
	 */
	private Boolean iadminOrder  =  false;
	
	private Long[] courseIds;
	private Integer iid;
	
	private Integer payType;	
	private Date tcreate; // 订单创建时间
	private Date tpay; // 支付时间
	private Long tnow ;
	
	private Double discount1 = 1.00;  // 满减折扣
	
	private Double discount2 = 1.00;  //购物券折扣
	
	private Double originalPrice ; // 最原始价格
	
	private String orderTimeString;
	
	private Integer orderType;; 
	
	private Double commission;
	
	private Integer avaiDays;
	
	private Integer objType;
	
	private Long creater;
	
	private String remark ;
	
	private Integer source ;
	
	
	/**
	 * 代金券 Id
	 */
	private Long couponId;
	
	
	
	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderTimeString() {
		return orderTimeString;
	}

	public void setOrderTimeString(String orderTimeString) {
		this.orderTimeString = orderTimeString;
	}

	/**
	 * 真实支付价格
	 */
	private Double realPrice;
	
	
	/**
	 * 使用的代金券价格
	 */
	private Double coupon ;
	
	/**
	 * 使用余额支付
	 */
	private Double balance ;
	
	
	/**
	 * 合作伙伴Id （来源Id）
	 * @return
	 */
	private Long partnerId;
	

	// Constructors

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public String getOrderIdStr() {
		return orderIdStr;
	}

	public void setOrderIdStr(String orderIdStr) {
		this.orderIdStr = orderIdStr;
	}

	/** default constructor */
	public RyxOrderDTO() {
	}

	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOrderBody() {
		return this.orderBody;
	}

	public void setOrderBody(String orderBody) {
		this.orderBody = orderBody;
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

	public String getToSellerMessage() {
		return this.toSellerMessage;
	}

	public void setToSellerMessage(String toSellerMessage) {
		this.toSellerMessage = toSellerMessage;
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

	public String getAvaiTime() {
		return this.avaiTime;
	}

	public void setAvaiTime(String avaiTime) {
		this.avaiTime = avaiTime;
	}

	public Integer getIfFeedback() {
		return this.ifFeedback;
	}

	public void setIfFeedback(Integer ifFeedback) {
		this.ifFeedback = ifFeedback;
	}

	public List<RyxOrderDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<RyxOrderDetailDTO> detailList) {
		this.detailList = detailList;
	}

	public Double getUseBalance() {
		return useBalance;
	}

	public void setUseBalance(Double useBalance) {
		this.useBalance = useBalance;
	}

	public Long[] getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(Long[] courseIds) {
		this.courseIds = courseIds;
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getTcreate() {
		return tcreate;
	}

	public void setTcreate(Date tcreate) {
		this.tcreate = tcreate;
	}

	public Date getTpay() {
		return tpay;
	}

	public void setTpay(Date tpay) {
		this.tpay = tpay;
	}

	public Long getTnow() {
		return tnow;
	}

	public void setTnow(Long tnow) {
		this.tnow = tnow;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Double getDiscount1() {
		return discount1;
	}

	public void setDiscount1(Double discount1) {
		this.discount1 = discount1;
	}

	public Double getDiscount2() {
		return discount2;
	}

	public void setDiscount2(Double discount2) {
		this.discount2 = discount2;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Integer getAvaiDays() {
		return avaiDays;
	}

	public void setAvaiDays(Integer avaiDays) {
		this.avaiDays = avaiDays;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Boolean getIadminOrder() {
		return iadminOrder;
	}

	public void setIadminOrder(Boolean iadminOrder) {
		this.iadminOrder = iadminOrder;
	}

	public List<Long> getUsers() {
		return users;
	}

	public void setUsers(List<Long> users) {
		this.users = users;
	}	
	
	
	
	
	

}