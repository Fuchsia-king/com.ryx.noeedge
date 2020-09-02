package com.king.nowedge.dto.ryx.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.nowedge.dto.base.RyxBaseDTO;

public class RyxMoneyItemDTO extends RyxBaseDTO{
	
	
	@JsonIgnore
    private Long custId;
	
	
	@JsonProperty("客户名称")
	private String custName ;
	
	
	@JsonIgnore
    private String status;
    
    @JsonIgnore
    private Long actId;
    
    
    @JsonProperty("金额")
    private Double money;
    
    @JsonIgnore
    private Long planId ;
    @JsonProperty("合同计划")
    private String planName ;
    
    
    
    
    @JsonIgnore
    private Long payee ;
    
    
    
    @JsonProperty("收款人")
    private String payeeName ;
    
    
    @JsonIgnore
    private Long payType ;

    @JsonProperty("支付类型")
    private String payTypeName ;
    
    
    @JsonIgnore
    private Long contract ;
    
    @JsonProperty("合同名称")
    private String contractName ;
    
    @JsonProperty("备注")
    private String descr ;
    
    
    
    
    
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Long getPayType() {
		return payType;
	}
	public void setPayType(Long payType) {
		this.payType = payType;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
    
    

}
