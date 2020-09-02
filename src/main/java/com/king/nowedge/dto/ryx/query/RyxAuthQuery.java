package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxAuth entity. @author MyEclipse Persistence Tools
 */

public class RyxAuthQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private Short type;
	private Short mechanism;
	private Short authStatus;
	private String name;
	private Short gender;
	private Short authType;
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
	private Short status;


	
	

	
	

	public Long getId() {
		return id;
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

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getMechanism() {
		return this.mechanism;
	}

	public void setMechanism(Short mechanism) {
		this.mechanism = mechanism;
	}

	public Short getAuthStatus() {
		return this.authStatus;
	}

	public void setAuthStatus(Short authStatus) {
		this.authStatus = authStatus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public Short getAuthType() {
		return this.authType;
	}

	public void setAuthType(Short authType) {
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

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
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

}