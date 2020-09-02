package com.king.nowedge.dto.ryx.query.crm;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

public class RyxMoneyPlanQuery extends LoreBaseQuery {

	private Long custId;
    private String status;
    private Long actId;
    private Long contract ;
    
    
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
	}
    
    

}
