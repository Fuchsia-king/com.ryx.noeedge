package com.king.nowedge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class RecruitmentDTO extends BaseDTO {
	
	
	
	private String member;
	
	@NotNull(message="{recruitment.company.not.null}")
	private Long company;

	/**
	 * 职位名称
	 */
	@NotEmpty(message="{common.position.not.empty}")
	private String position ; 
	
	/**
	 * 招聘数量
	 */
	//@NotNull(message="{recruitment.nbr.not.null}")
	@Min(message="{recruitment.nbr.incorrect}",value=1)
	private Integer nbr;
	
	/**
	 * 专业
	 */
	//
	//@NotNull(message="{common.specialty.not.null}")
	private Long specialty ;
	
	
	//@NotEmpty(message="{common.specialtyName.not.null}")
	private String specialtyName ;
	
	
	/**
	 * 行业分类
	 */
	@NotNull(message="{recruitment.industry.not.null}")
	private Long industry ; 
	
	
	/**
	 * 学位(学力)
	 */
	//@Min(message="{common.educationLevel.not.empty}", value = 1)
	//@NotNull(message="{common.educationLevel.not.null}")
	private Long educationLevel ; 
	
	
	/**
	 * 工作年限
	 */
	@Min(message="{common.workingYears.not.empty}", value = 1)
	@NotNull(message="{common.educationLevel.not.null}")
	private Long workingYears ; 
	
	
	/**
	 * 
	 */
	@Min(message="{common.salary.not.empty}", value = 1)
	@NotNull(message="{common.salary.not.null}")
	private Long salary ;
	
	
	@NotNull(message="{common.province.not.null}")
	private String province ;
	
	
	@NotNull(message="{common.city.not.null}")
	private String city ;
	
	
	/**
	 * 任职描述
	 */
	@NotEmpty(message="{recruitment.descr.not.empty}")
	private String descr ;
	
	
	@Email(message="{common.email.invalid}")
	//@NotEmpty(message="{common.email.not.empty}")
	private String email ;
	
	
	//@NotEmpty(message="{common.contact.not.empty}")
	private String contact; 
	
	/**
	 * 
	 */
	//@NotEmpty(message="{common.phone.not.empty}")
	private String phone ;
	
	
	
	//@NotNull(message="{recruitment.address.not.empty}")
	private Long addr ; 
	
	
	private Integer status ; 
	
	
	//@NotEmpty(message="{common.wellfare.not.empty}")
	private String wellfare;



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public Integer getNbr() {
		return nbr;
	}



	public void setNbr(Integer nbr) {
		this.nbr = nbr;
	}



	public Long getSpecialty() {
		return specialty;
	}



	public void setSpecialty(Long specialty) {
		this.specialty = specialty;
	}



	public Long getEducationLevel() {
		return educationLevel;
	}



	public void setEducationLevel(Long educationLevel) {
		this.educationLevel = educationLevel;
	}



	public Long getWorkingYears() {
		return workingYears;
	}



	public void setWorkingYears(Long workingYears) {
		this.workingYears = workingYears;
	}



	public Long getSalary() {
		return salary;
	}



	public void setSalary(Long salary) {
		this.salary = salary;
	}



	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getContact() {
		return contact;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Long getAddr() {
		return addr;
	}


	public void setAddr(Long addr) {
		this.addr = addr;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	



	public Long getIndustry() {
		return industry;
	}



	public void setIndustry(Long industry) {
		this.industry = industry;
	}



	





	public String getWellfare() {
		return wellfare;
	}



	public void setWellfare(String wellfare) {
		this.wellfare = wellfare;
	}



	public String getMember() {
		return member;
	}



	public void setMember(String member) {
		this.member = member;
	}



	public Long getCompany() {
		return company;
	}



	public void setCompany(Long company) {
		this.company = company;
	}



	public String getSpecialtyName() {
		return specialtyName;
	}



	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}
	
	
	
	
}
