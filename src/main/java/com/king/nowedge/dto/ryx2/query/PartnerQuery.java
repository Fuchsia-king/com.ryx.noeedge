package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * RyxTeacher entity. @author MyEclipse Persistence Tools
 */

public class PartnerQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private String username;
	private Integer email;
	private String mobile;
	private Integer type;

	// Constructors	
	
	
	@Override
	 public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getEmail() {
		return email;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}