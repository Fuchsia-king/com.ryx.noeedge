package com.king.nowedge.dto.ryx2;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;


/***
 * 
 * @author wangdap
 *
 */

public class TeacherDTO implements java.io.Serializable {

	// Fields

	private Long id;
	
	@NotEmpty(message="{teacher.nickname.not.empty}")
	private String nickname;
	
	private String title ;
	
	
	private Integer type;
	
	@NotEmpty(message="{teacher.nick.not.empty}")
	private String nick; 
	
	//@NotEmpty(message="{teacher.beGoodAt.not.empty}")
	private String beGoodAt;
	
	@NotEmpty(message="{teacher.introduction.not.empty}")
	private String introduction;
	
	@NotEmpty(message="{teacher.tags.not.empty}")
	private String tags;
	private Long rating;
	private Integer ratingCount;
	
	@NotNull(message="{teacher.status.not.empty}")
	private Integer status;
	private String imageUrl;
	private Double price;
	
	@NotNull(message="{teacher.flag.not.empty}")
	private Integer flag;
	
	private String category;
	
	@NotNull(message="{teacher.uid.not.empty}")
	private Long uid;
	
	private Long lcreate;
	private Long lmodified;
	private Long creater;
	private Integer ideleted;
	
	@NotNull(message="{common.display.not.empty}")
	private Integer display;
	private Integer sort;
	private Map<Integer, Double> orate;
	
	public Map<Integer, Double> getOrate() {
		return orate;
	}

	public void setOrate(Map<Integer, Double> orate) {
		this.orate = orate;
	}

	/**
	 * 规模
	 */
	private Integer scale;
	
	/**
	 * 行业
	 */
	private Long industry0 ;
	private Long industry1 ;
	private Long industry2 ;
	
	/**
	 * org type ，机构类型
	 */
	private Integer otype;
	
	
	
	private String dcode;  	//证件号码
	
	private String dpic1;		// 证件照片-正面
	
	private String dpic2;  	// 证件照片-反面
	
	private String mobile;	// 联系电话(手机号码)
	
	private String phone;	// 联系电话(手机号码)
	
	private String email; 	 	//电子邮件
	
	private String address;
	private String bank;
	private String cbank;
	
	
	private String accountHolder;
	private String bankAccount;
	private String contact;
	private String fname;
	private Integer passTime;
	
	private String isAgreeProtocal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getBeGoodAt() {
		return beGoodAt;
	}

	public void setBeGoodAt(String beGoodAt) {
		this.beGoodAt = beGoodAt;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getLcreate() {
		return lcreate;
	}

	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}

	public Long getLmodified() {
		return lmodified;
	}

	public void setLmodified(Long lmodified) {
		this.lmodified = lmodified;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Integer getIdeleted() {
		return ideleted;
	}

	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	

	public Integer getOtype() {
		return otype;
	}

	public void setOtype(Integer otype) {
		this.otype = otype;
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

	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}

	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}