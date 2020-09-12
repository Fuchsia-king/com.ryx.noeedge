package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;

import java.util.List;


/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ObjectLimitQuery extends LoreBaseQuery implements java.io.Serializable {


	private Long userId ;
	
	private Long muserId ;
	
	private Long oid ;
	
	private Integer otype;
	
	private List<Integer> otypes;
	
	private List<Integer> statuss;
	
	private Integer status;
	
	private Long slimi;
	
	private Long elimi;
	
	private Integer category ;
	
	private Long moid ; 
	
	private Integer orderType;
	
	
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSlimi() {
		return slimi;
	}

	public void setSlimi(Long slimi) {
		this.slimi = slimi;
	}

	public Long getElimi() {
		return elimi;
	}

	public void setElimi(Long elimi) {
		this.elimi = elimi;
	}

	public List<Integer> getOtypes() {
		return otypes;
	}

	public void setOtypes(List<Integer> otypes) {
		this.otypes = otypes;
	}

	public List<Integer> getStatuss() {
		return statuss;
	}

	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Long getMoid() {
		return moid;
	}

	public void setMoid(Long moid) {
		this.moid = moid;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getMuserId() {
		return muserId;
	}

	public void setMuserId(Long muserId) {
		this.muserId = muserId;
	}
	
	
	

	
	
	

}