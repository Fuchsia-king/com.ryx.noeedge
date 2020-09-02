package com.king.nowedge.dto.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.helper.StringHelper;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class Users_DTO extends BaseDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer iid;
	private String username;
	private String password;
	private String uname;
	
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email;
	
	
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	
	private Integer regTime;
	private Long regIp;
	private Integer lastLoginTime;
	private Long lastLoginIp;
	private Integer updateTime;
	private Short status;
	private String path;
	private Integer login;
	private Short gender;
	private String introduction;
	private Integer integral;
	private Date birthday;
	private Short sign;
	private Integer district1;
	private Integer district2;
	private Integer district3;
	private String address;
	private Double balance;
	private Short authType;
	private String validateCode;
	private String qqOpenId;
	private Short flag;
	private String code;
	private Set ryxUserFollowCourses = new HashSet(0);
	private Set ryxTeachers = new HashSet(0);
	private Set ryxOrders = new HashSet(0);
	private Set ryxUserFollowTeachers = new HashSet(0);
	private Set ryxUsersFavorites = new HashSet(0);
	private Integer coupon;
	
	private String province; 
	private Integer industry; 
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date tcreate;
	

	// Constructors

	/** default constructor */
	public Users_DTO() {
	}

	/** minimal constructor */
	public Users_DTO(String username, String password, String email,
			Integer regTime, Long regIp, Integer lastLoginTime,
			Long lastLoginIp, Integer updateTime, Integer login,
			Integer integral, Short sign) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.regTime = regTime;
		this.regIp = regIp;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.updateTime = updateTime;
		this.login = login;
		this.integral = integral;
		this.sign = sign;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Integer regTime) {
		this.regTime = regTime;
	}

	public Long getRegIp() {
		return this.regIp;
	}

	public void setRegIp(Long regIp) {
		this.regIp = regIp;
	}

	public Integer getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(Long lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getLogin() {
		return this.login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Short getSign() {
		return this.sign;
	}

	public void setSign(Short sign) {
		this.sign = sign;
	}

	public Integer getDistrict1() {
		return this.district1;
	}

	public void setDistrict1(Integer district1) {
		this.district1 = district1;
	}

	public Integer getDistrict2() {
		return this.district2;
	}

	public void setDistrict2(Integer district2) {
		this.district2 = district2;
	}

	public Integer getDistrict3() {
		return this.district3;
	}

	public void setDistrict3(Integer district3) {
		this.district3 = district3;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Short getAuthType() {
		return this.authType;
	}

	public void setAuthType(Short authType) {
		this.authType = authType;
	}

	public Set getRyxUserFollowCourses() {
		return this.ryxUserFollowCourses;
	}

	public void setRyxUserFollowCourses(Set ryxUserFollowCourses) {
		this.ryxUserFollowCourses = ryxUserFollowCourses;
	}

	public Set getRyxTeachers() {
		return this.ryxTeachers;
	}

	public void setRyxTeachers(Set ryxTeachers) {
		this.ryxTeachers = ryxTeachers;
	}

	public Set getRyxOrders() {
		return this.ryxOrders;
	}

	public void setRyxOrders(Set ryxOrders) {
		this.ryxOrders = ryxOrders;
	}

	public Set getRyxUserFollowTeachers() {
		return this.ryxUserFollowTeachers;
	}

	public void setRyxUserFollowTeachers(Set ryxUserFollowTeachers) {
		this.ryxUserFollowTeachers = ryxUserFollowTeachers;
	}

	public Set getRyxUsersFavorites() {
		return this.ryxUsersFavorites;
	}

	public void setRyxUsersFavorites(Set ryxUsersFavorites) {
		this.ryxUsersFavorites = ryxUsersFavorites;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public Date getTcreate() {
		return tcreate;
	}

	public void setTcreate(Date tcreate) {
		this.tcreate = tcreate;
	}

	public Integer getCoupon() {
		return coupon;
	}

	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}


	
	

	
}