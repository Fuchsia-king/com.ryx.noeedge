package com.king.nowedge.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;

public class WorkExperienceDTO extends BaseDTO{
	
	
	
	private Long  userId;
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{workExperience.company.not.empty}")
	private String company ; 
	
	@NotNull(message="{workExperience.startDate.not.empty}")
	@DateTimeFormat(pattern = "yyyy-MM")	
	private Date startDate ;
	
	@NotNull(message="{workExperience.endDate.not.empty}")
	@DateTimeFormat(pattern = "yyyy-MM")	
	private Date endDate ;
	
	private Long lstart;
	
	private long lend;
	
	private Integer educationLevel;
	
	@NotEmpty(message="{workExperience.descr.not.empty}")
	private String descr ;
	
	@NotEmpty(message="{workExperience.department.not.empty}")
	private String department;
	
	private Integer industry;
	
	
	private Integer    companyScale;
	private Integer    specialty;
	
	@NotEmpty(message="{workExperience.position.not.empty}")
	private String    position;
	
	private Integer companyType ; 
	
	private Integer type;
	
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public Integer getIndustry() {
		return industry;
	}
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
	
	public Integer getCompanyScale() {
		return companyScale;
	}
	public void setCompanyScale(Integer companyScale) {
		this.companyScale = companyScale;
	}
	
	public Integer getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Integer specialty) {
		this.specialty = specialty;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	
	public Long getLstart() {
		if(null != startDate){
			return startDate.getTime();
		}
		return lstart;
	}
	
	public void setLstart(Long lstart) {
		this.lstart = lstart;
	}
	
	public long getLend() {
		if(null != endDate){
			return endDate.getTime();
		}
		return lend;
	}
	
	public void setLend(long lend) {
		this.lend = lend;
	}

}
