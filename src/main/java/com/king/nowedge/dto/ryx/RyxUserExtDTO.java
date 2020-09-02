package com.king.nowedge.dto.ryx;

import com.king.nowedge.dto.base.BaseDTO;

public class RyxUserExtDTO  extends BaseDTO{
	
	private Long id ;
	private String corpCode ;
	private String username ;
	private String secret ;
	private String domain ;
	private String name ;
	private String mobile ;
	private String email ;
	
	
	/**
	 * 是否同意推广
	 */
	private Integer ispread ;
	
	
	/**
	 * 是否主账号
	 */
	private Integer imain ;
	
	
	/**
	 * 佣金比例,逗号隔开
	 */
	private String orate ;
	
	
	private String orgId ;
	
	private String orgName ;
	
	private String orgCode ;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 时代光华Id
	 */
	private String source ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	

	public Integer getImain() {
		return imain;
	}

	public void setImain(Integer imain) {
		this.imain = imain;
	}
	

    private String appKey ;
    
    private String appSecret ;




	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
	
	
	
}
