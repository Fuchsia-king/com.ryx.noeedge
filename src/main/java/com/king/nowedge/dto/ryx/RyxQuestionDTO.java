package com.king.nowedge.dto.ryx;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;

/**
 * RyxCart entity. @author MyEclipse Persistence Tools
 */

public class RyxQuestionDTO extends BaseDTO implements java.io.Serializable {

	private String title;
	private Long userId;
	private Integer category;
	private Integer otype;
	private Integer objer;
	private Integer status = EnumAuditStatus.UNAUDITED.getCode();
	
	public Integer getObjer() {
		return objer;
	}
	public void setObjer(Integer objer) {
		this.objer = objer;
	}
	/**
	 * course id
	 */
	private Long cid; 
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}