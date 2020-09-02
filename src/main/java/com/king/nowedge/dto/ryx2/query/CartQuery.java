package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxCart entity. @author MyEclipse Persistence Tools
 */

public class CartQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	
	private Long buyerId;
	private Long sellerId;
	private String sellerUsername;
	private Long objId;
	private String objName;
	private Double objPrice;
	private Short objNum;
	private String objImage;
	
	private Long courseId;
	private Long userId;

	// Constructors

	

	/** default constructor */
	public CartQuery() {
	}

	
	// Property accessors

	

	public Long getBuyerId() {
		return this.buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerUsername() {
		return this.sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public Long getObjId() {
		return this.objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public Double getObjPrice() {
		return this.objPrice;
	}

	public void setObjPrice(Double objPrice) {
		this.objPrice = objPrice;
	}

	public Short getObjNum() {
		return this.objNum;
	}

	public void setObjNum(Short objNum) {
		this.objNum = objNum;
	}

	public String getObjImage() {
		return this.objImage;
	}

	public void setObjImage(String objImage) {
		this.objImage = objImage;
	}


	public Long getCourseId() {
		return courseId;
	}


	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	

}