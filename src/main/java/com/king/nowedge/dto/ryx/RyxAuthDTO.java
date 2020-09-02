package com.king.nowedge.dto.ryx;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxAuth entity. @author MyEclipse Persistence Tools
 */

public class RyxAuthDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private Long uid;
	
	private String username;
	
	private Integer type;
	private Integer mechanism;
	private Integer authStatus;
	private String name;
	private Integer gender;
	private Integer authType;
	private String dnCode;
	private String dnPic1;
	private String dnPic2;
	private String telphone;
	private String email;
	private String address;
	private String bank;
	private String cbank;
	private String description;
	private String accountHolder;
	private String bankAccount;
	private String orgIname;
	private String orgFname;
	private Integer createTime;
	private Integer passTime;
	private Integer status;
	private String reason;
	
	private Integer certStatus;
	private String beGoodAt;
	private String tags ; 
	
	private String imageUrl; 


	private String isAgreeProtocal;
	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMechanism() {
		return this.mechanism;
	}

	public void setMechanism(Integer mechanism) {
		this.mechanism = mechanism;
	}

	public Integer getAuthStatus() {
		return this.authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAuthType() {
		return this.authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getDnCode() {
		return this.dnCode;
	}

	public void setDnCode(String dnCode) {
		this.dnCode = dnCode;
	}

	public void setDnPic1(String dnPic1) {
		this.dnPic1 = dnPic1;
	}

	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCbank() {
		return this.cbank;
	}

	public void setCbank(String cbank) {
		this.cbank = cbank;
	}

	public String getAccountHolder() {
		return this.accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getOrgIname() {
		return this.orgIname;
	}

	public void setOrgIname(String orgIname) {
		this.orgIname = orgIname;
	}

	public String getOrgFname() {
		return this.orgFname;
	}

	public void setOrgFname(String orgFname) {
		this.orgFname = orgFname;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getPassTime() {
		return this.passTime;
	}

	public void setPassTime(Integer passTime) {
		this.passTime = passTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getDnPic2() {
		return dnPic2;
	}



	public void setDnPic2(String dnPic2) {
		this.dnPic2 = dnPic2;
	}



	public String getDnPic1() {
		return dnPic1;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getCertStatus() {
		return certStatus;
	}

	public void setCertStatus(Integer certStatus) {
		this.certStatus = certStatus;
	}

	public String getBeGoodAt() {
		return beGoodAt;
	}

	public void setBeGoodAt(String beGoodAt) {
		this.beGoodAt = beGoodAt;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	
	

}