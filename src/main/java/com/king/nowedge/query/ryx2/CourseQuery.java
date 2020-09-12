package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;

import java.util.List;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class CourseQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields
	
	private String title;
	private String price ;
	private Double sprice ; // start price
	private Double eprice ; // end price 

	private Integer display;
	private Integer position;
	private Integer difficulty;


	private Integer status;
	private String district1;   // 省份
	private String district2;   // 城市
	private String district3;   // 地区

	private Integer flag;

	private Integer category;
	private Integer subcate; //二级分类
	
	private Long ttstart  ; // 起始起始时间
	private Long etstart  ; // 结束起始时间
	
	private Long ttend  ; // 起始结束时间
	private Long etend  ; // 结束结束时间
	
	private Integer interval; //
	private Long userId;
	
	private Long tnow;	
	
	private Long tid; 	
	
	private Integer objType;
	
	private List<Integer> flags ;
	
	private List<Integer> categorys ;
	
	private Integer orderType ;
	
	private Integer tstatus ;
	
	
	public List<Integer> getObjTypes() {
		return objTypes;
	}

	public void setObjTypes(List<Integer> objTypes) {
		this.objTypes = objTypes;
	}

	private List<Integer> objTypes;
	
	private List<Long> orderCourseIds;
	
	private Long ustart;
	
	private Long uend ;
	
	
	
	
	/**
	 * 讲师UserId
	 */
	private Long cuid;
	
	/**
	 * 第三级目录
	 */
	private Integer tcate ;
	
	private Long nextId ; // next id 
	
	private Long preId ;// pre Id
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTnow() {
		return tnow;
	}

	public void setTnow(Long tnow) {
		this.tnow = tnow;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	
	public String getDistrict2() {
		return district2;
	}

	public void setDistrict2(String district2) {
		this.district2 = district2;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}	

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Double getSprice() {
		return sprice;
	}

	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}

	public Double getEprice() {
		return eprice;
	}

	public void setEprice(Double eprice) {
		this.eprice = eprice;
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

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public List<Long> getOrderCourseIds() {
		return orderCourseIds;
	}

	public void setOrderCourseIds(List<Long> orderCourseIds) {
		this.orderCourseIds = orderCourseIds;
	}

	public Integer getSubcate() {
		return subcate;
	}

	public void setSubcate(Integer subcate) {
		this.subcate = subcate;
	}

	public Long getCuid() {
		return cuid;
	}

	public void setCuid(Long cuid) {
		this.cuid = cuid;
	}

	public Integer getTcate() {
		return tcate;
	}

	public void setTcate(Integer tcate) {
		this.tcate = tcate;
	}

	public Long getUstart() {
		return ustart;
	}

	public void setUstart(Long ustart) {
		this.ustart = ustart;
	}

	public Long getUend() {
		return uend;
	}

	public void setUend(Long uend) {
		this.uend = uend;
	}

	public String getDistrict1() {
		return district1;
	}

	public void setDistrict1(String district1) {
		this.district1 = district1;
	}

	public String getDistrict3() {
		return district3;
	}

	public void setDistrict3(String district3) {
		this.district3 = district3;
	}

	public Long getTtend() {
		return ttend;
	}

	public void setTtend(Long ttend) {
		this.ttend = ttend;
	}

	public Long getEtend() {
		return etend;
	}

	public void setEtend(Long etend) {
		this.etend = etend;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<Integer> getFlags() {
		return flags;
	}

	public void setFlags(List<Integer> flags) {
		this.flags = flags;
	}

	public List<Integer> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Integer> categorys) {
		this.categorys = categorys;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getTstatus() {
		return tstatus;
	}

	public void setTstatus(Integer tstatus) {
		this.tstatus = tstatus;
	}

	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	
	
	
	

}