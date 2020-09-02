package com.king.nowedge.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;

public class EducationExperienceDTO extends BaseDTO{
	
	
	
	private String member;
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{educationExperience.school.not.empty}")
	private String school ; 
	
	
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date startDate ;
	
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date endDate ;
	
	private Integer educationLevel;
	
	private String descr ;
	
	private String major ; 

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	
}
