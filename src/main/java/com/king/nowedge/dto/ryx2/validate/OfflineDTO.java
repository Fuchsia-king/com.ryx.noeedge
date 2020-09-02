package com.king.nowedge.dto.ryx2.validate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


public class OfflineDTO extends CourseDTO {


	
	@NotEmpty(message="{course.contactMan.map.not.empty}")
	private String contactMan;
	
	@NotEmpty(message="{course.phone.map.not.empty}")
	private String phone;

	@NotEmpty(message="{course.address.map.not.empty}")
	private String putAddress;
	
	private String vid ;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") 
	@NotNull(message="{course.tstartDate.not.empty}")
	private Date tstartDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@NotNull(message="{course.tstartDate.not.empty}")
	private Date tendDate;
	

	private Long tstart;
	
	private Long tend ;
	
	@NotEmpty(message="{course.imageUrl.not.empty}")
	private String imageUrl;
	
	/**
	 * 直播 链接
	 */
	private String url;
	

	/**
	 * 举办位置
	 */
	@NotEmpty(message="{offline.location.not.empty}")
	private String location;

	
	@NotEmpty(message="{course.district2.map.not.empty}")
	private String district2;
	
	
	@NotNull(message="{course.applyNbr.not.empty}")
	private Integer tryTime;

	
	@NotEmpty(message="{course.descr.not.empty}")
	private String descr;
	

	@NotEmpty(message="{course.content.not.empty}")
	private String content;

	
	
	public String getPutAddress() {
		return putAddress;
	}

	public void setPutAddress(String putAddress) {
		this.putAddress = putAddress;
	}

	

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDistrict2() {
		return district2;
	}

	public void setDistrict2(String district2) {
		this.district2 = district2;
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


	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getTryTime() {
		return tryTime;
	}

	public void setTryTime(Integer tryTime) {
		this.tryTime = tryTime;
	}
	

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}