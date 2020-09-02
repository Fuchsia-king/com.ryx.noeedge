package com.king.nowedge.dto.ryx2.validate;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class CourseDTO extends BaseDTO {
	
	@NotEmpty(message="{common.title.not.empty}")
	private String title;
	
	@NumberFormat(style=Style.CURRENCY, pattern="#,###.00") 
	
	//@NotNull(message="{course.price.not.empty}")
	private Double price;
	
	
	//@NotNull(message="{course.oprice.not.empty}")
	private Double oprice;
	
	private Integer coverId;
	
	
	@NotNull(message="{common.display.not.empty}")
	private Integer display;
	private Integer position;
	
	private Integer up;
	private Integer down;
	private Integer hits;
	private Integer studyCount;
	private Double rating;
	private Integer ratingCount;
	private Long updateTime;
	private Long createTime;
	
	@NotNull(message="{common.status.not.empty}")
	@Min(message="{common.status.not.empty}",value=0)
	private Integer status;
	private String source;
	
	
	private String district1;
	private String district3;
	
	
	
	private String relatedCourse;
	private List<CourseDTO> list;
	private Integer renqi;

	
	@NotNull(message="{course.category.not.empty}")
	@Min(message="{common.status.not.empty}",value=1)
	private Integer category;
	
	
	
	private Integer mssort;
	
	@NotNull(message="{course.teacher.not.empty}")
	private Long tid;
	
	
	
	
	private Long tend;
	
	
	
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date tend1;	
	
	
	
    private String getHttp;	

    private String getHls;	

    private String getRtmp;
	
	private Long limitTime;
	
	private Integer leftDays;
	
	private Integer applyCount;
	
	
	
	
	
	public Long getTend() {
		return tend;
	}

	/**
	 * 产品类型
	 */
	private Integer objType;
	
	//@NotEmpty(message="{course.iframeUrl.not.empty}")
	private String iframeUrl;
	
	private String svid; 
	
	/**
	 * 二维码
	 */
	
	
	
	@NotEmpty(message="{common.isAgreeProtocal.not.empty}")
	private String isAgreeProtocal;
	
	
	
	public Long getTend1() {
		return tend;
	}

	public void setTend1(Date tend1) {
		this.tend1 = tend1;
	}

	private String reason;
	
	private Integer sort;
	
	private Long cuid;
	
	
	
	

	// Constructors

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getMssort() {
		return mssort;
	}

	public void setMssort(Integer mssort) {
		this.mssort = mssort;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
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

	

	public List<CourseDTO> getList() {
		return list;
	}

	public void setList(List<CourseDTO> list) {
		this.list = list;
	}

	public String getRelatedCourse() {
		return relatedCourse;
	}

	public void setRelatedCourse(String relatedCourse) {
		this.relatedCourse = relatedCourse;
	}

	
	



	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCoverId() {
		return this.coverId;
	}

	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
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

	public Integer getUp() {
		return this.up;
	}

	public void setUp(Integer up) {
		this.up = up;
	}

	public Integer getDown() {
		return this.down;
	}

	public void setDown(Integer down) {
		this.down = down;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getStudyCount() {
		return this.studyCount;
	}

	public void setStudyCount(Integer studyCount) {
		this.studyCount = studyCount;
	}

	public Double getRating() {
		return this.rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getRatingCount() {
		return this.ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Long getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}




	


	

	

	

	
	
	


	public Integer getRenqi() {
		return renqi;
	}

	public void setRenqi(Integer renqi) {
		this.renqi = renqi;
	}

	

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	
	

	
	public void setTend(Long tend) {
		this.tend = tend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getGetHttp() {
		return getHttp;
	}

	public void setGetHttp(String getHttp) {
		this.getHttp = getHttp;
	}

	public String getGetHls() {
		return getHls;
	}

	public void setGetHls(String getHls) {
		this.getHls = getHls;
	}

	public String getGetRtmp() {
		return getRtmp;
	}

	public void setGetRtmp(String getRtmp) {
		this.getRtmp = getRtmp;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(Integer leftDays) {
		this.leftDays = leftDays;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}
	
	



	public String getIframeUrl() {
		return iframeUrl;
	}

	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}

	public String getSvid() {
		return svid;
	}

	public void setSvid(String svid) {
		this.svid = svid;
	}

	
	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Double getOprice() {
		return oprice;
	}

	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}

	

	

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}

	public Long getCuid() {
		return cuid;
	}

	public void setCuid(Long cuid) {
		this.cuid = cuid;
	}
	
	
}