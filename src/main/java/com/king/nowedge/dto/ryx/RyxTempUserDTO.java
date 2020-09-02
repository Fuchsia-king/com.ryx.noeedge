package com.king.nowedge.dto.ryx;

/**
 * TempUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxTempUserDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String telephone;
	private String random;
	private Long time;

	// Constructors

	/** default constructor */
	public RyxTempUserDTO() {
	}

	/** full constructor */
	public RyxTempUserDTO(String telephone, String random, Long time) {
		this.telephone = telephone;
		this.random = random;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRandom() {
		return this.random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}