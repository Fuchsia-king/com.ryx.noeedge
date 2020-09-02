package com.king.nowedge.dto.query;import javax.validation.constraints.NotNull;

import com.king.nowedge.dto.query.base.LoreBaseQuery;




public class ResumeQuery extends LoreBaseQuery {
	
	
	private Long userId ;	 

	private Integer academic;  // 学历
	
	private Integer wyears ; // 工作年限
	
	private Long specialty0; // 职位
	
	
	private Long specialty1; // 职位
	private Long specialty2; // 职位
	
	private Integer salary; // 薪资要求
	
	private Integer wstatus; // work status ;
	
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
	private Long industry0; // 职位
	private Long industry1; // 职位
	private Long industry2; // 职位

	

	
	
	
	
	
	

}
