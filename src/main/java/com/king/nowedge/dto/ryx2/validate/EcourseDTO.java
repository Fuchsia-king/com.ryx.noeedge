package com.king.nowedge.dto.ryx2.validate;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class EcourseDTO extends CourseDTO{


	/**
	 * 
	 */
	//@NotNull(message="{course.subcate.not.empty}")
	private Integer subcate;
	
	
	/**
	 * 
	 */
	//@NotNull(message="{course.tcate.not.empty}")
	private Integer tcate;

	
	/**
	 * 
	 */
	private String vid;
	
	private Long tstart;	
	
	private Long tend;
	
	
	/**
	 * 课件时长
	 */
	//@Pattern(regexp = "^([0-9]?[0-9]):([0-5][0-9]):([0-5][0-9])$" ,message="{online.duration.invalid}")
	//@NotEmpty(message="{course.duration.not.empty}")
	private String duration;
	
	//@NotNull(message="{course.difficulty.not.empty}")
	private Integer difficulty;
	
	@NotNull(message="{course.flag.not.empty}")
	private Integer flag;
	
	
	@NotNull(message="{course.avaiDay.not.empty}")
	private Integer avaiDay;
	
	
	/**
	 * 课程体系的子课程
	 */
	private String[] courseSeries;
	
	private  String imageUrl;

	
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


	public String getVid() {
		return vid;
	}


	public void setVid(String vid) {
		this.vid = vid;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public Integer getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	
	public Integer getAvaiDay() {
		return avaiDay;
	}

	public void setAvaiDay(Integer avaiDay) {
		this.avaiDay = avaiDay;
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


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public String[] getCourseSeries() {
		return courseSeries;
	}


	public void setCourseSeries(String[] courseSeries) {
		this.courseSeries = courseSeries;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	List<SubOnlineDTO> listSubOnline;
	
	
	
	
	public List<SubOnlineDTO> getListSubOnline() {
		return listSubOnline;
	}


	public void setListSubOnline(List<SubOnlineDTO> listSubOnline) {
		this.listSubOnline = listSubOnline;
	}

	
	private String[] subtitle;
	private String[] subdescr;
	private Double[] suboprice;
	private Double[] subprice;
	private String[] subvid ;

	public String[] getSubtitle() {
		return subtitle;
	}


	public void setSubtitle(String[] subtitle) {
		this.subtitle = subtitle;
	}


	public String[] getSubdescr() {
		return subdescr;
	}


	public void setSubdescr(String[] subdescr) {
		this.subdescr = subdescr;
	}


	public Double[] getSuboprice() {
		return suboprice;
	}


	public void setSuboprice(Double[] suboprice) {
		this.suboprice = suboprice;
	}


	public Double[] getSubprice() {
		return subprice;
	}


	public void setSubprice(Double[] subprice) {
		this.subprice = subprice;
	}


	public String[] getSubvid() {
		return subvid;
	}


	public void setSubvid(String[] subvid) {
		this.subvid = subvid;
	}

	/**
	 * 课程简短介绍
	 */
	//@NotEmpty(message="{course.descr.not.empty}")
	private String descr;
	

	//@NotEmpty(message="{course.content.not.empty}")
	private String content;


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
	

	
	
}