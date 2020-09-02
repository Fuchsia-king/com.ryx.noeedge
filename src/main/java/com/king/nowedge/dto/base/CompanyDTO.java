package com.king.nowedge.dto.base;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CompanyDTO extends BaseDTO{
	
	
	
	private String member;
	
	/**
	 * 名称
	 */
	@NotEmpty(message="{company.name.not.empty}")
	private String name ; 
	
	
	/**
	 * 别名
	 */
	private String name1 ; 
	
	
	/**
	 * 公司所在行业
	 */
	@NotNull(message="{common.industry.not.empty}")
	private Long industry ; 
	
	
	@NotEmpty(message="{common.industry.not.empty}")
	private String industryName ; 
	
	
	
	
		
	/**
	 * 公司性质： 国有，民营、私营之类 
	 */
	@NotNull(message="{common.companyType.not.empty}")
	private Long companyType ;
	
	
	/**
	 *  公司规模
	 */
	@NotNull(message="{common.companyScale.not.empty}")
	private Long companyScale; 
	
	
	
	/**
	 * 公司描述
	 */
	@NotEmpty(message="{common.descr.not.empty}")
	private String descr ; 
	
	
	/**
	 *  网址
	 */
	@NotEmpty(message="{common.website.not.empty}")
	private String website ;
	
	
	private String qq; 
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email ; 
	
	
	
	private String addr ; 
	
	@NotEmpty(message="{common.contact.not.empty}")
	private String contact ; 
	
	@NotEmpty(message="{common.phone.not.empty}")
	private String phone ; 
	
	
	@Pattern(regexp="^[1][3-8]+\\d{9}",message="{common.mobile.invalid}")  
	@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile ; 
	
	private String weixin ;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName1() {
		return name1;
	}


	public void setName1(String name1) {
		this.name1 = name1;
	}


	public Long getCompanyType() {
		return companyType;
	}


	public void setCompanyType(Long companyType) {
		this.companyType = companyType;
	}


	public Long getCompanyScale() {
		return companyScale;
	}


	public void setCompanyScale(Long companyScale) {
		this.companyScale = companyScale;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
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


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getWeixin() {
		return weixin;
	}


	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}


	public Long getIndustry() {
		return industry;
	}
	

	public void setIndustry(Long industry) {
		this.industry = industry;
	}


	public String getMember() {
		return member;
	}


	public void setMember(String member) {
		this.member = member;
	}


	public String getIndustryName() {
		return industryName;
	}


	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	
	
	

}
