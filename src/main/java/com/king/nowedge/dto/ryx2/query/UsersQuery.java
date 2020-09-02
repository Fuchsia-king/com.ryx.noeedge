package com.king.nowedge.dto.ryx2.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class UsersQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String username;
	private String email;
	private String mobile;
	private Short status;
	private String password;
	private Long mid;
	private String validateCode;
	
	
	/**
	 * 来源用户Id
	 */
	private Long sid ;
	
	/**
	 * 邀请码
	 */
	private String icode; 
	
	private Integer rfrom;
	
	
	
	public String getProvince() {
		return province;
	}




	public void setProvince(String province) {
		this.province = province;
	}




	private Integer flag;
	private Long startTime;
	private Long endTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dstartTime;
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date dendTime;
	private String province; 
	private Short gender;
	public Short getGender() {
		return gender;
	}




	public void setGender(Short gender) {
		this.gender = gender;
	}




	public Integer getIndustry() {
		return industry;
	}




	public void setIndustry(Integer industry) {
		this.industry = industry;
	}




	private Integer industry;
	

	// Constructors

	public Long getStartTime() {
		return startTime;
	}




	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}




	public Long getEndTime() {
		return endTime;
	}




	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}




	public Date getDstartTime() {
		return dstartTime;
	}




	public void setDstartTime(Date dstartTime) {
		this.dstartTime = dstartTime;
	}



	



	public Date getDendTime() {
		return dendTime;
	}




	public void setDendTime(Date dendTime) {
		this.dendTime = dendTime;
	}




	/** default constructor */
	public UsersQuery() {
	}

	
	

	public String getValidateCode() {
		return validateCode;
	}




	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}




	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}




	public Short getStatus() {
		return status;
	}




	public void setStatus(Short status) {
		this.status = status;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public Long getMid() {
		return mid;
	}




	public void setMid(Long mid) {
		this.mid = mid;
	}




	public Integer getFlag() {
		return flag;
	}




	public void setFlag(Integer flag) {
		this.flag = flag;
	}




	public Long getSid() {
		return sid;
	}




	public void setSid(Long sid) {
		this.sid = sid;
	}




	public String getIcode() {
		return icode;
	}




	public void setIcode(String icode) {
		this.icode = icode;
	}




	public Integer getRfrom() {
		return rfrom;
	}




	public void setRfrom(Integer rfrom) {
		this.rfrom = rfrom;
	}




	
	
	

}