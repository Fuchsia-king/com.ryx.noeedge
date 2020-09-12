package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * RyxMessage entity. @author MyEclipse Persistence Tools
 */

public class RyxMessageQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long oid ;
	private Integer otype ;
	private String descr ;
	private Long lcreate;
	private Integer iread ;
	private Integer interval ;
	
	private Long tstart  ; // 起始起始时间
	private Long tend  ; // 结束起始时间
	
	
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getLcreate() {
		return lcreate;
	}
	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}
	public Integer getIread() {
		return iread;
	}
	public void setIread(Integer iread) {
		this.iread = iread;
	}	

	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Long getTstart() {
		return tstart;
	}
	public void setTstart(Long tstart) {
		this.tstart = tstart;
	}
	public Long getTend() {
		return tend;
	}
	public void setTend(Long tend) {
		this.tend = tend;
	}
	
	
}