package com.king.nowedge.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class ResumeDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	
	
	private Long userId ;	
	
	/**
	 * 姓名
	 */
	@NotEmpty(message="{common.name.not.empty}")
	private String name;
	
	@NotNull(message="{resume.address.not.empty}")
	private Long address;
	
	@NotNull(message="{resume.image.not.empty}")
	private String image ; // 头像
	
	@NotNull(message="{resume.contact.not.empty}")
	private Long contact;
	
	@NotNull(message="{common.gender.not.empty}")
	private Integer gender;
	
	@NotNull(message="{common.academic.not.empty}")
	private Integer academic;  // 学历
	
	@NotNull(message="{common.wyears.not.empty}")
	private Integer wyears ; // 工作年限
	
	@NotNull(message="{common.specialty0.not.empty}")
	private Long specialty0; // 职位
	
	
	private Long specialty1; // 职位
	private Long specialty2; // 职位
	
	@NotNull(message="{common.salary.not.empty}")
	private Integer salary; // 薪资要求
	
	@NotNull(message="{common.wstatus.not.empty}")
	private Integer wstatus; // work status ;
	
	@NotNull(message="{common.industry0.not.empty}")
	private Long industry0; // 职位
	private Long industry1; // 职位
	private Long industry2; // 职位
	
	private Long createTime;
	private Long updateTime;
	
	@NotEmpty(message="{resume.descr.not.empty}")
	private String descr ;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getAcademic() {
		return academic;
	}
	public void setAcademic(Integer academic) {
		this.academic = academic;
	}
	public Integer getWyears() {
		return wyears;
	}
	public void setWyears(Integer wyears) {
		this.wyears = wyears;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public Integer getWstatus() {
		return wstatus;
	}
	public void setWstatus(Integer wstatus) {
		this.wstatus = wstatus;
	}

	public Long getSpecialty0() {
		return specialty0;
	}
	public void setSpecialty0(Long specialty0) {
		this.specialty0 = specialty0;
	}
	public Long getSpecialty1() {
		return specialty1;
	}
	public void setSpecialty1(Long specialty1) {
		this.specialty1 = specialty1;
	}
	public Long getSpecialty2() {
		return specialty2;
	}
	public void setSpecialty2(Long specialty2) {
		this.specialty2 = specialty2;
	}
	public Long getIndustry0() {
		return industry0;
	}
	public void setIndustry0(Long industry0) {
		this.industry0 = industry0;
	}
	public Long getIndustry1() {
		return industry1;
	}
	public void setIndustry1(Long industry1) {
		this.industry1 = industry1;
	}
	public Long getIndustry2() {
		return industry2;
	}
	public void setIndustry2(Long industry2) {
		this.industry2 = industry2;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getContact() {
		return contact;
	}
	public void setContact(Long contact) {
		this.contact = contact;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Long getAddress() {
		return address;
	}
	public void setAddress(Long address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
	
	

}