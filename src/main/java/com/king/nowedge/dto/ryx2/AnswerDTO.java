package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;

/**
 * RyxCart entity. @author MyEclipse Persistence Tools
 */

public class AnswerDTO extends BaseDTO implements java.io.Serializable {

	private String descr;
	private Long userId;
	private Long  qid;
	private Integer agree;
	private Integer disagree;
	private Integer status = EnumAuditStatus.UNAUDITED.getCode();
	
	
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getQid() {
		return qid;
	}
	public void setQid(Long qid) {
		this.qid = qid;
	}
	public Integer getAgree() {
		return agree;
	}
	public void setAgree(Integer agree) {
		this.agree = agree;
	}
	public Integer getDisagree() {
		return disagree;
	}
	public void setDisagree(Integer disagree) {
		this.disagree = disagree;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}