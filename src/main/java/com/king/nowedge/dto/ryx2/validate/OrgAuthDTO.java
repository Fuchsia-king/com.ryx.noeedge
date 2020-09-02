package com.king.nowedge.dto.ryx2.validate;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;


public class OrgAuthDTO implements java.io.Serializable {
	
	private Long id ;
	
	private Long uid ;
	
	
	@NotEmpty(message="{orgAuth.nickname.not.empty}")
	private String nickname;
	
	
	@Pattern(regexp = "^[a-z]+$" ,message="{auth.nick.not.invalid}")
	private String nick; 
	
	private Integer flag;
	
	private Integer status ;
	
	@NotEmpty(message="{orgAuth.dcode.not.empty}")
	private String dcode;
	
	@NotEmpty(message="{orgAuth.dnPic1.not.empty}")
	private String dpic1;
	
	private String dpic2;
	
	//@NotEmpty(message="{orgAuth.mobile.not.empty}")
	private String mobile;
	
	//@NotEmpty(message="{orgAuth.phone.not.empty}")
	private String phone;
	
	//@Email(message="{common.email.invalid}")
	//@NotEmpty(message="{common.email.not.empty}")
	private String email;
	
	
	private String address;
	private String bank;
	private String cbank;
	
	@NotEmpty(message="{orgAuth.introduction.not.empty}")	
	private String introduction;
	private String accountHolder;
	private String bankAccount;
	
	//@NotEmpty(message="{orgAuth.contact.not.empty}")
	private String contact;
	
	@NotEmpty(message="{orgAuth.fname.not.empty}")
	private String fname;
	
	private Integer passTime;
	
	
	@NotEmpty(message="{orgAuth.imageUrl.not.empty}")
	private String imageUrl ;
	
	@NotEmpty(message="{orgAuth.tags.not.empty}")
	private String tags;
	
	@NotEmpty(message="{orgAuth.beGoodAt.not.empty}")
	private String beGoodAt ;
	

	
	
	@NotEmpty(message="{orgAuth.isAgreeProtocal.not.empty}")
	private String isAgreeProtocal;
	
	
	
	/**
	 * 规模
	 */
	//@NotNull(message="{orgAuth.scale.not.empty}") 
	private Integer scale;
	
	/**
	 * 行业
	 */
	//@NotNull(message="{orgAuth.industry0.not.empty}") 
	private Long industry0 ;
	
	private Long industry1 ;
	
	private Long industry2 ;
	
	/**
	 * org type ，机构类型
	 */
	//@NotNull(message="{orgAuth.otype.not.empty}")
	private Integer otype;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCbank() {
		return cbank;
	}

	public void setCbank(String cbank) {
		this.cbank = cbank;
	}



	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Integer getPassTime() {
		return passTime;
	}

	public void setPassTime(Integer passTime) {
		this.passTime = passTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getBeGoodAt() {
		return beGoodAt;
	}

	public void setBeGoodAt(String beGoodAt) {
		this.beGoodAt = beGoodAt;
	}

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}

	
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
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

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	
	
	
}