package com.king.nowedge.dto.ryx2.query;

import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * 
 * @author wangdap
 *
 */
public class UserEcourseQuery extends LoreBaseQuery implements java.io.Serializable {
	

	private Long userId;	
	private Long ecid ;	
	private Long ecid1 ;	
	private Integer category ;
	private Integer status ;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getEcid() {
		return ecid;
	}
	public void setEcid(Long ecid) {
		this.ecid = ecid;
	}
	public Long getEcid1() {
		return ecid1;
	}
	public void setEcid1(Long ecid1) {
		this.ecid1 = ecid1;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	

}