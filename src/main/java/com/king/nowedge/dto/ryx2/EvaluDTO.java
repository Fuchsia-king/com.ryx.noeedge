package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EvaluDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * userId
	 */
	private Long userId;
	
	/**
	 * object id
	 */
	private Long objId;
	
	/***
	 * object type
	 */
	private Integer objType;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	
	/***
	 * 1~5 星级
	 */
	private Integer score;
	
	
	/**
	 * 评价描述
	 */
	private String descr;
	
	
	/**
	 * obj 所有者
	 */
	private Long objer;
	
	
	/**
	 * 
	 */
	private Integer category;
	
	private Long lcreate ; 
	
	
	/**
	 * 
	 */
	private Long detailId;
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getObjer() {
		return objer;
	}
	public void setObjer(Long objer) {
		this.objer = objer;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Long getLcreate() {
		return lcreate;
	}
	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	
	
	
	
	
	

}