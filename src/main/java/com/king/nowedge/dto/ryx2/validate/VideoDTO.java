package com.king.nowedge.dto.ryx2.validate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


public class VideoDTO extends CourseDTO {


	
	
	

	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") 
	@NotNull(message="{course.tstartDate.not.empty}")
	private Date tstartDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@NotNull(message="{course.tstartDate.not.empty}")
	private Date tendDate;
	

	private Long tstart;
	
	private Long tend ;
	

	private Integer flag;
	
	@NotEmpty(message="{course.imageUrl.not.empty}")
	private String imageUrl;
	

	@NotEmpty(message="{video.url.not.empty}")
	private String url;

	
	/**
	 * 视频直播对应的体系课程的主课程
	 */
	private String vid ;
	
	
	@NotEmpty(message="{course.descr.not.empty}")
	private String descr;
	

	@NotEmpty(message="{course.content.not.empty}")
	private String content;
	
	
	
	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
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

	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
	
}