package com.king.nowedge.dto.ryx2.query;import java.util.List;

import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ApplyQuery extends LoreBaseQuery implements java.io.Serializable {

	
	private Long oid;
	private Long userId; 
	private Long objer ;
	private Integer iattent;
	private List<Integer> statuss ;

private List<Integer> otypes ;
	
	
	public Integer getIattent() {
		return iattent;
	}
	public void setIattent(Integer iattent) {
		this.iattent = iattent;
	}
	/**
	 * 课程类目
	 */
	private Integer category ;
	
	
	/**
	 *  申请类型
	 */
	private Integer otype;
	
	/**
	 * 支付状态
	 */
	private Integer status ;
	
	
	/**
	 * 申请时间
	 */
	private Integer ainterval ;
	
	/**
	 * 活动开始时间的其实查询时间
	 */
	Long ttstart;
	
	/**
	 * 活动开始时间的起始查询时间
	 */
	Long etstart;
	
	/**
	 * 申请修改时间的起始查询时间（m -> modified）
	 */
	Long mstart ;
	
	/**
	 * 申请修改时间的起始查询时间（m -> modified）
	 */
	Long mend ;
	
	
	Long etend;
	
	Long ttend ;
	
	/**
	 * 召开时间
	 */
	private Integer ointerval ;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	
	public Integer getAinterval() {
		return ainterval;
	}
	public void setAinterval(Integer ainterval) {
		this.ainterval = ainterval;
	}
	public Integer getOinterval() {
		return ointerval;
	}
	public void setOinterval(Integer ointerval) {
		this.ointerval = ointerval;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getTtstart() {
		return ttstart;
	}
	public void setTtstart(Long ttstart) {
		this.ttstart = ttstart;
	}
	public Long getEtstart() {
		return etstart;
	}
	public void setEtstart(Long etstart) {
		this.etstart = etstart;
	}
	public Long getMstart() {
		return mstart;
	}
	public void setMstart(Long mstart) {
		this.mstart = mstart;
	}
	public Long getMend() {
		return mend;
	}
	public void setMend(Long mend) {
		this.mend = mend;
	}
	public Long getObjer() {
		return objer;
	}
	public void setObjer(Long objer) {
		this.objer = objer;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Long getEtend() {
		return etend;
	}
	public void setEtend(Long etend) {
		this.etend = etend;
	}
	public Long getTtend() {
		return ttend;
	}
	public void setTtend(Long ttend) {
		this.ttend = ttend;
	}
	public List<Integer> getStatuss() {
		return statuss;
	}
	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}
	public List<Integer> getOtypes() {
		return otypes;
	}
	public void setOtypes(List<Integer> otypes) {
		this.otypes = otypes;
	}
	
	
	
	
	

}