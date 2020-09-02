package com.king.nowedge.dto.ryx2.validate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


public class RecruitDTO  {
	
	private Long id ;

	private Long cuid;
	
	private Long creater;
	
	private Long createTime ;
	
	private Long updateTime;

	private Integer objType ;
	
	private Integer status;
	
	@NotEmpty(message="{recruit.descr.not.empty}")
	private String descr ;
	
	@NotEmpty(message="{recruit.title.not.empty}")
	private String title;	

	@NotEmpty(message="{recruit.contactMan.not.empty}")
	private String contactMan;	
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message="{recruit.tstartDate.not.empty}")
	private Date tendDate;
	
	private Long tend;
	
	@NotNull(message="{recruit.applyNbr.not.empty}")
	private Integer tryTime;

	@NotEmpty(message="{recruit.address.not.empty}")
	private String address;
	
	/**
	 * 职位类别
	 */
	@NotNull(message="{recruit.ptype.not.empty}")
	private String ptype;

	private Double oprice ;
	
	
	private String district1;
	private String district2;
	private String district3;
	
	
	
	
	
	/**
	 * 公司规模
	 */
	@NotNull(message="{recruit.avaiDay.not.empty}")
	private Integer avaiDay;
	
	
	/**
	 * 学历要求
	 */
	@NotNull(message="{recruit.difficulty.not.empty}")
	private Integer difficulty;
	
	
	
	/**
	 * 工作时间年限要求
	 */
	@NotNull(message="{recruit.sort.not.empty}")
	private Integer sort;
	
	
	private Long tid ;
	private Integer display;
	private Integer up;
	private Integer down;
	private Integer hits;
	private Double rating;
	private Integer ratingCount;
	
	private Integer category ; 
	private Integer subcate;  
	private Integer tcate ;



	public String getContactMan() {
		return contactMan;
	}



	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}



	public Date getTendDate() {
		return tendDate;
	}



	public void setTendDate(Date tendDate) {
		this.tendDate = tendDate;
	}



	public Integer getTryTime() {
		return tryTime;
	}



	public void setTryTime(Integer tryTime) {
		this.tryTime = tryTime;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}







	public Integer getAvaiDay() {
		return avaiDay;
	}



	public void setAvaiDay(Integer avaiDay) {
		this.avaiDay = avaiDay;
	}



	public Integer getDifficulty() {
		return difficulty;
	}



	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	public Long getCuid() {
		return cuid;
	}



	public void setCuid(Long cuid) {
		this.cuid = cuid;
	}



	public Long getCreater() {
		return creater;
	}



	public void setCreater(Long creater) {
		this.creater = creater;
	}



	public Long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}



	public Long getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}



	public Integer getObjType() {
		return objType;
	}



	public void setObjType(Integer objType) {
		this.objType = objType;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}





	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
	}



	


	public String getPtype() {
		return ptype;
	}



	public void setPtype(String ptype) {
		this.ptype = ptype;
	}



	public Long getTend() {
		if(null != tendDate){
			return tendDate.getTime() / 1000 ;
		}
		return tend;
	}

	public Double getOprice() {
		return oprice;
	}



	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}



	public Long getTid() {
		return tid;
	}



	public void setTid(Long tid) {
		this.tid = tid;
	}



	public Integer getDisplay() {
		return display;
	}



	public void setDisplay(Integer display) {
		this.display = display;
	}



	public Integer getUp() {
		return up;
	}



	public void setUp(Integer up) {
		this.up = up;
	}



	public Integer getDown() {
		return down;
	}



	public void setDown(Integer down) {
		this.down = down;
	}



	public Integer getHits() {
		return hits;
	}



	public void setHits(Integer hits) {
		this.hits = hits;
	}



	public Double getRating() {
		return rating;
	}



	public void setRating(Double rating) {
		this.rating = rating;
	}



	public Integer getRatingCount() {
		return ratingCount;
	}



	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}



	public void setTend(Long tend) {
		this.tend = tend;
	}



	public Integer getCategory() {
		return category;
	}



	public void setCategory(Integer category) {
		this.category = category;
	}



	public Integer getSubcate() {
		return subcate;
	}



	public void setSubcate(Integer subcate) {
		this.subcate = subcate;
	}



	public Integer getTcate() {
		return tcate;
	}



	public void setTcate(Integer tcate) {
		this.tcate = tcate;
	}



	public String getDistrict1() {
		return district1;
	}



	public void setDistrict1(String district1) {
		this.district1 = district1;
	}



	public String getDistrict2() {
		return district2;
	}



	public void setDistrict2(String district2) {
		this.district2 = district2;
	}



	public String getDistrict3() {
		return district3;
	}



	public void setDistrict3(String district3) {
		this.district3 = district3;
	}
	
	
	
	
	
	
	
	
	
	
	

}