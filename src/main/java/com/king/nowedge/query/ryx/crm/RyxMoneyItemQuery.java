package com.king.nowedge.query.ryx.crm;

import com.king.nowedge.query.base.LoreBaseQuery;

public class RyxMoneyItemQuery extends LoreBaseQuery {

	private Long custId;
    private String status;
    private Long actId;
    private Long payee ;
    private Long planId ;
    private Long payType ;
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
	public Long getPayee() {
		return payee;
	}
	public void setPayee(Long payee) {
		this.payee = payee;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getPayType() {
		return payType;
	}
	public void setPayType(Long payType) {
		this.payType = payType;
	}
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
	}
    
	
    

}
