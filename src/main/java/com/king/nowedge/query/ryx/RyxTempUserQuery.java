package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * TempUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxTempUserQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String telephone;
	private String random;
	private Long time;

	// Constructors

	/** default constructor */
	public RyxTempUserQuery() {
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