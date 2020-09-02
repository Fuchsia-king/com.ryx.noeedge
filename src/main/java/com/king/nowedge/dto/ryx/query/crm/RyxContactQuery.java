package com.king.nowedge.dto.ryx.query.crm;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

public class RyxContactQuery extends RyxPresaleQuery {

	private Long custId;
	private Integer idefault ;
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Integer getIdefault() {
		return idefault;
	}
	public void setIdefault(Integer idefault) {
		this.idefault = idefault;
	}
	
	
	

}
