package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxAnswerQuery extends LoreBaseQuery implements java.io.Serializable {

	private Long userId;
	private Long  qid;
	private String descr;
	private Integer status;
	
	
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}