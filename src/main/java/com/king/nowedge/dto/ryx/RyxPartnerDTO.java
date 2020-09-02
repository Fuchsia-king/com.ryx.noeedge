package com.king.nowedge.dto.ryx;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxTeacher entity. @author MyEclipse Persistence Tools
 */

public class RyxPartnerDTO  extends BaseDTO implements java.io.Serializable {

	
	// Fields
	
	private Long id;
	private Long userId;
	private String username;
	private String email;
	private String mobile;
	private Integer type;
	private Map<Integer, Double> rateMap ;
	
	/**
	 * session 保留时间
	 */
	private Integer days;
	
	
	
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Map<Integer, Double> getRateMap() {
		return rateMap;
	}
	public void setRateMap(Map<Integer, Double> rateMap) {
		this.rateMap = rateMap;
	}
	
	
	
	

	// Constructors

	
	
	
	
}