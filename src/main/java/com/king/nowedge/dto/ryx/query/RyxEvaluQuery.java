package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;



/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxEvaluQuery extends LoreBaseQuery implements java.io.Serializable {

	
	private Long objId;
	private Long objer;
	private Long userId; 
	private Long orderId;
	private Integer objType;
	private Integer status; 
	private Integer score;
	private Integer category;
	
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
	}
	public Long getObjer() {
		return objer;
	}
	public void setObjer(Long objer) {
		this.objer = objer;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getObjType() {
		return objType;
	}
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	
	

}