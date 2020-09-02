package com.king.nowedge.dto.ryx;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.StringHelper;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxUsersDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String username;
	private String password;
	private String uname;
	private String name ;
	
	private String weixinUnionId ;
	
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
	private Integer status;
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
	private Double balance = 0.0 ;
	private Double balance1;
	private Double tbalance1;
	private Short authType;
	private String validateCode;
	private String qqOpenId;
	private Integer flag;
	private String code;
	
	private Double coupon = 0.0;
	
	private String province; 
	private Integer industry;
	
	private Long mid;  // main id ， 主账号Id
	
	private Boolean isSubacunt;
	private Double score = 0.0;
	private Long tid;
	
	private Integer usersCount;
	
	private Integer rfrom ;
	
	
	/**
	 * 邀请人Id
	 */
	private Long sid ;
	
	/**
	 * 邀请码
	 */
	private String icode; 
	
	
	/**
	 * 佣金比例
	 */
	private String orate ;
	
	private Integer ispread ;
	
	public Integer getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(Integer usersCount) {
		this.usersCount = usersCount;
	}

	/**
	 * 机构属性
	 * @return
	 */
	

	
	private String dcode;
	
	private String dpic1;
	
	private String dpic2;
	
	private String fname;
	
	private Integer scale;
	
	private Integer industry0 ;
	
	private Integer otype;
	
	private String isAgreeProtocal;
	

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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
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
	

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}
	
	
	public String getUname() {
		if(StringHelper.isNullOrEmpty(this.username)){
			Integer index = this.email.indexOf('@');
			if(index < 0 ){
				return this.email;
			}
			else{
				return this.email.substring(0, index);
			}
					
		}
		else{
			return username;
		}
	}

	public void setUname(String uname) {
		this.uname = uname;
	}



	public Boolean getIsSubacunt() {
		return (null != this.mid);
	}



	public void setIsSubacunt(Boolean isSubacunt) {
		this.isSubacunt = isSubacunt;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getDpic1() {
		return dpic1;
	}

	public void setDpic1(String dpic1) {
		this.dpic1 = dpic1;
	}

	public String getDpic2() {
		return dpic2;
	}

	public void setDpic2(String dpic2) {
		this.dpic2 = dpic2;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public Integer getIndustry0() {
		return industry0;
	}

	public void setIndustry0(Integer industry0) {
		this.industry0 = industry0;
	}

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
	}

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
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

	public String getWeixinUnionId() {
		return weixinUnionId;
	}

	public void setWeixinUnionId(String weixinUnionId) {
		this.weixinUnionId = weixinUnionId;
	}

	public Double getBalance1() {
		return balance1;
	}

	public void setBalance1(Double balance1) {
		this.balance1 = balance1;
	}

	public Double getTbalance1() {
		return tbalance1;
	}

	public void setTbalance1(Double tbalance1) {
		this.tbalance1 = tbalance1;
	}

	public String getOrate() {
		return orate;
	}

	public void setOrate(String orate) {
		this.orate = orate;
	}

	public Integer getIspread() {
		return ispread;
	}

	public void setIspread(Integer ispread) {
		this.ispread = ispread;
	}
	
	
	
}