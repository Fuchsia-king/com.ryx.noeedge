package com.king.nowedge.dto.ryx;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.ryx2.validate.SubOnlineDTO;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class RyxCourseDTO extends BaseDTO implements java.io.Serializable {


	
	@NotEmpty(message="{common.title.not.empty}")
	private String title;
	
	@NumberFormat(style=Style.CURRENCY, pattern="#,###.00") 
	
	@NotNull(message="{common.price.not.empty}")
	private Double price ;
	
	
	@NotNull(message="{common.oprice.not.empty}")
	private Double oprice ;
	
	private Integer coverId;
	
	private String content;
	

	private String image;
	
	@NotNull(message="{common.display.not.empty}")
	private Integer display;
	private Integer position;
	private Integer difficulty;
	private Integer up;
	private Integer down;
	private Integer hits;
	private Integer studyCount;
	private Double rating;
	private Integer ratingCount;
	private Long updateTime;
	private Long createTime;
	
	private Integer itop ;
	
	private Integer inew ;
	
	
	
	//@NotNull(message="{common.status.not.empty}")
	//@Min(message="{common.status.not.empty}",value=0)
	private Integer status;
	private String source;
	
	private String[] courseSeries  ;
	
	/**
	 * 课件时长
	 */
	//@NotNull(message="{commom.duration.not.empty}")
	private String duration;
	private String district1;
	private String district2;
	private String district3;
	private String imageUrl;
	private String vid;
	private Integer tryTime;
	
	@NotNull(message="{course.avaiDay.not.empty}")
	private Integer avaiDay;
	
	private String relatedCourse;
	private List<RyxCourseDTO> list;
	private Integer renqi;
	private Integer flag;
	private String contactMan;
	private String address;
	private String phone;
	
	@NotNull(message="{course.category.not.empty}")
	@Min(message="{common.status.not.empty}",value=1)
	private Integer category;
	
	/**
	 * 二级类目
	 */
	private Integer subcate;
	
	
	private Integer mssort;
	
	//@NotNull(message="{course.teacher.not.empty}")
	private Long tid;
	
	private String url;
	
	private Long tstart;
	
	private Long tend;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")   
	private Date tstartDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")   
	private Date tendDate;
	
	
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date tend1;	
	
	private String putAddress;
	
    private String getHttp;	

    private String getHls;	

    private String getRtmp;
	
	private Long limitTime;
	
	private Integer leftDays;
	
	private Integer applyCount;
	
	private Integer scount;
	
	
	/**
	 * 下载所需积分
	 */
	private Double score ;
	
	private Integer limitStatus;
	
	private String isAgreeProtocal ;
	
	
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
	private String twoma;
	
	/**
	 * 课程简短介绍
	 */
	private String descr;
	
	
	/**
	 * 举办位置
	 */
	private String location;
	
	
	public Long getTend1() {
		return tend;
	}

	public void setTend1(Date tend1) {
		this.tend1 = tend1;
	}

	private String reason;
	
	private Integer sort;
	
	
	/**
	 * 课程所属讲师Id 
	 */
	private Long cuid;
	
	
	/**
	 * 第三级类别
	 */
	private Integer tcate ;

	
	private List<SubOnlineDTO> listSubOnline ;

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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public List<RyxCourseDTO> getList() {
		return list;
	}

	public void setList(List<RyxCourseDTO> list) {
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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
		if(null == this.studyCount){
			return 0;
		}
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

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVid() {
		return this.vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}


	public Integer getTryTime() {
		return tryTime;
	}

	public void setTryTime(Integer tryTime) {
		this.tryTime = tryTime;
	}

	public Integer getAvaiDay() {
		return avaiDay;
	}

	public void setAvaiDay(Integer avaiDay) {
		this.avaiDay = avaiDay;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRenqi() {
		if(null == this.renqi){
			return 0;
		}
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getTstart() {
		if(null != tstartDate){
			return tstartDate.getTime()/1000;
		}
		return tstart;
	}

	public void setTstart(Long tstart) {
		this.tstart = tstart;
	}

	public Long getTend() {
		if(null != tendDate){
			return tendDate.getTime()/1000;
		}
		return tend;
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

	public String getPutAddress() {
		return putAddress;
	}

	public void setPutAddress(String putAddress) {
		this.putAddress = putAddress;
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
	
	

	public Integer getScount() {
		return 0 ;
	}

	public void setScount(Integer scount) {
		this.scount = scount;
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

	public String getTwoma() {
		return twoma;
	}

	public void setTwoma(String twoma) {
		this.twoma = twoma;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTstartDate() {
		return tstartDate;
	}

	public void setTstartDate(Date tstartDate) {
		this.tstartDate = tstartDate;
	}

	public Date getTendDate() {
		return tendDate;
	}

	public void setTendDate(Date tendDate) {
		this.tendDate = tendDate;
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

	public String[] getCourseSeries() {
		return courseSeries;
	}

	public void setCourseSeries(String[] courseSeries) {
		this.courseSeries = courseSeries;
	}

	public List<SubOnlineDTO> getListSubOnline() {
		return listSubOnline;
	}

	public void setListSubOnline(List<SubOnlineDTO> listSubOnline) {
		this.listSubOnline = listSubOnline;
	}

	public Integer getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(Integer limitStatus) {
		this.limitStatus = limitStatus;
	}

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}

	public Integer getItop() {
		return itop;
	}

	public void setItop(Integer itop) {
		this.itop = itop;
	}

	public Integer getInew() {
		return inew;
	}

	public void setInew(Integer inew) {
		this.inew = inew;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
}