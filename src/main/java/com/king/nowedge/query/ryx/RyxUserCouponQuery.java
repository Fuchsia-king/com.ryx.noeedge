package com.king.nowedge.query.ryx;import java.util.ArrayList;
import java.util.List;

import com.king.nowedge.query.base.LoreBaseQuery;





/**
 * 
 * @author wangdap
 *
 */
public class RyxUserCouponQuery extends LoreBaseQuery implements java.io.Serializable {
	

	private Long userId;	
	private Long createrId;
	private Integer type;
	private Long tstart;
	private Long tend;
	private Integer interval;
	private Integer inorout ; // 收入还是支出
	private Long slimi ;
	private Long elimi ;	
	private Integer iuse ;
	private Integer scoupon ;  // 起始金额
	private Integer ecoupon ;  // 结束金额
	private List<Integer> types = new ArrayList<Integer>(); ;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public Integer getInorout() {
		return inorout;
	}
	public void setInorout(Integer inorout) {
		this.inorout = inorout;
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
	public Integer getIuse() {
		return iuse;
	}
	public void setIuse(Integer iuse) {
		this.iuse = iuse;
	}
	public Integer getScoupon() {
		return scoupon;
	}
	public void setScoupon(Integer scoupon) {
		this.scoupon = scoupon;
	}
	public Integer getEcoupon() {
		return ecoupon;
	}
	public void setEcoupon(Integer ecoupon) {
		this.ecoupon = ecoupon;
	}
	public List<Integer> getTypes() {
		return types;
	}
	public void setTypes(List<Integer> types) {
		this.types = types;
	}
	
	
	
	
	

}