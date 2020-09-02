package com.king.nowedge.dto.ryx.crm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.nowedge.dto.base.RyxBaseDTO;

public class RyxContractDTO extends RyxBaseDTO{
	
	
	@JsonProperty("合同标题")
	private String title ;
	
	/**
	 * 客户Id
	 */
	@JsonIgnore
	private Long custId;
	
	@JsonProperty("客户名称")
	private String custName ;
	
	
	/**
	 * 跟进人
	 */
	@JsonIgnore
	private Long follower ;
	
	
	@JsonProperty("跟进人")
	private String followerName ;
	
	
	@JsonIgnore
    private Long actId;
    
    /**
     * 备注
     */
	@JsonProperty("备注")
    private String descr;
	
	@JsonIgnore
    private Long attach;
	
	@JsonIgnore
    private Long content;
    
	
	@JsonIgnore
    private Long bizType ;
	
	@JsonProperty("业务类型1")
	private String bizTypeName ;
    
	
	@JsonIgnore
    private Long project ;
	
	@JsonProperty("业务类型2")
	private String projectName ;
    
	
	@JsonIgnore
    private Integer type ;
    
	
	@JsonIgnore
    private Integer payType ;
	
	
    
	
	@JsonIgnore
    private Integer dept ;
	
	
	@JsonProperty("部门")
	private String deptName ;
    
	
	@JsonIgnore
    private Long manager ;
	
	
	@JsonProperty("负责人")
	private String managerName ;
    
	
	@JsonIgnore
    private Long status ;
	
	
	@JsonProperty("状态")
	private String statusName ;
    
	
	@JsonProperty("合同金额")
    private Double money ;
    
	@JsonIgnore
    private Double paidMoney ;
    
	
	@JsonIgnore
    private Double unpaidMoney ;
    
    /**
     * 签订时间
     */
    @JsonProperty("签订时间")
    private String stime;
    
    
    
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getAttach() {
		return attach;
	}
	public void setAttach(Long attach) {
		this.attach = attach;
	}
	public Long getContent() {
		return content;
	}
	public void setContent(Long content) {
		this.content = content;
	}
	public Long getBizType() {
		return bizType;
	}
	public void setBizType(Long bizType) {
		this.bizType = bizType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public Long getProject() {
		return project;
	}
	public void setProject(Long project) {
		this.project = project;
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
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getFollower() {
		return follower;
	}
	public void setFollower(Long follower) {
		this.follower = follower;
	}
	public String getFollowerName() {
		return followerName;
	}
	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}
    
	
    
    
    

}
