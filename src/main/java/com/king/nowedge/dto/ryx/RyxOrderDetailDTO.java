package com.king.nowedge.dto.ryx;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * RyxOrderDetail entity. @author MyEclipse Persistence Tools
 */

public class RyxOrderDetailDTO implements java.io.Serializable {

	// Fields

	private Integer detailId;
	private String detailName;
	private Long orderId;
	private Integer objType;
	private Long objId;
	private Long payTime;
	private String ouid;
	private Double oamount ; // 讲师佣金结果结果
	private Double oamount1 ; //合作伙伴佣金计算结果
	private Integer buycount;
	
	public Integer getBuycount() {
		return buycount;
	}

	public void setBuycount(Integer buycount) {
		this.buycount = buycount;
	}

	public String getOuid() {
		return ouid;
	}

	public void setOuid(String ouid) {
		this.ouid = ouid;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	@NumberFormat(style=Style.NUMBER, pattern="#,###.00") 
	private Double price;
	private Integer num;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Long getObjer() {
		return objer;
	}

	public void setObjer(Long objer) {
		this.objer = objer;
	}

	public Double getOrate() {
		return orate;
	}

	public void setOrate(Double orate) {
		this.orate = orate;
	}

	private String detailType;
	private Integer avaiDay;
	private Integer isFeedback;
	private RyxCourseDTO course;
	private String returnOrderId;
	
	private Long objer;
	
	/**
	 * 讲师佣金比例
	 */
	private Double orate;
	
	/**
	 * 合作伙伴佣金比例
	 */
	private Double orate1;
	
	private Long tnow;
	
	private Long limitTime; 
	
	private String title;
	
	
	/**
	 * 用户名
	 */
	private Long userId;
	
	/**
	 *  支付状态
	 */
	private Integer status ;
	
	
	/**
	 * 真实支付价格
	 */
	private Double realPrice;
	
	/**
	 * 
	 */
	private Long partnerId;
	
	
	/**
	 * 使用的代金券价格
	 */
	private Double coupon ;
	
	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	private Double originalPrice;
	

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
	

	// Constructors

	public RyxCourseDTO getCourse() {
		return course;
	}

	public void setCourse(RyxCourseDTO course) {
		this.course = course;
	}

	/** default constructor */
	public RyxOrderDetailDTO() {
	}

	
	// Property accessors

	public Integer getDetailId() {
		return this.detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getDetailName() {
		return this.detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getObjType() {
		return this.objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Long getObjId() {
		return this.objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public Integer getAvaiDay() {
		return avaiDay;
	}

	public void setAvaiDay(Integer avaiDay) {
		this.avaiDay = avaiDay;
	}

	public Integer getIsFeedback() {
		return isFeedback;
	}

	public void setIsFeedback(Integer isFeedback) {
		this.isFeedback = isFeedback;
	}

	public Long getTnow() {
		return tnow;
	}

	public void setTnow(Long tnow) {
		this.tnow = tnow;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public String getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(String returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getOamount() {
		return oamount;
	}

	public void setOamount(Double oamount) {
		this.oamount = oamount;
	}

	public Double getOamount1() {
		return oamount1;
	}

	public void setOamount1(Double oamount1) {
		this.oamount1 = oamount1;
	}

	public Double getOrate1() {
		return orate1;
	}

	public void setOrate1(Double orate1) {
		this.orate1 = orate1;
	}

	
	
	
	

}