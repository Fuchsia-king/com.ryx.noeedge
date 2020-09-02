package com.king.nowedge.dto.ryx.crm;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.base.RyxBaseDTO;

public class RyxMoneyPlanDTO extends RyxBaseDTO{
	
	
	@JsonIgnore
    private Long custId;
	
	@JsonIgnore
    private Long status;
	

	@JsonProperty("状态")
    private String statusName ;
	
	@JsonIgnore
    private Long actId;
	
	@JsonIgnore
    private Long contract;
	
	@JsonProperty("已支付")
    private Double paidMoney ;
	
	@JsonProperty("未支付")
    private Double unpaidMoney ;
	
	@JsonProperty("总额")
    private Double money ;
    
	@JsonProperty("标题")    
    private String title ;
	
	@JsonProperty("备注")
    private String descr ; 
	
	
	@JsonProperty("合同名称")
	private String contractName ;
	
	@JsonProperty("客户名称")
	private String custName ;
   
    @JsonIgnore
    private List<RyxMoneyPlanDTO> planList;
    
    
	public List<RyxMoneyPlanDTO> getPlanList() {
		return planList;
	}
	public void setPlanList(List<RyxMoneyPlanDTO> planList) {
		this.planList = planList;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
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
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
	}
	public Double getPaidMoney() {
		return paidMoney;
	}
	public void setPaidMoney(Double paidMoney) {
		this.paidMoney = paidMoney;
	}
	public Double getUnpaidMoney() {
		return unpaidMoney;
	}
	public void setUnpaidMoney(Double unpaidMoney) {
		this.unpaidMoney = unpaidMoney;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
    
    

}
