package com.king.nowedge.dto.ryx.query.crm;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

public class RyxContractQuery extends LoreBaseQuery {

	private Long custId;
    private Long actId;
    
    
    private Integer status ;
    private Integer dept ;
	private Integer type;
	private Long manager;
	
	private Long project ;
	
	private Long bizType ;
	
	
	private Long follower ;
    
    
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public Long getProject() {
		return project;
	}
	public void setProject(Long project) {
		this.project = project;
	}
	public Long getBizType() {
		return bizType;
	}
	public void setBizType(Long bizType) {
		this.bizType = bizType;
	}
	public Long getFollower() {
		return follower;
	}
	public void setFollower(Long follower) {
		this.follower = follower;
	}
    
	
	
    

}
