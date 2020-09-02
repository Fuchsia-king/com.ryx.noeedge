package com.king.nowedge.dto.ryx2.query;

/**
 * TbInfo entity. @author MyEclipse Persistence Tools
 */

public class TbInfoQuery implements java.io.Serializable {

	// Fields

	private Integer infoId;
	private String info1;
	private String info2;
	private String info3;

	// Constructors

	/** default constructor */
	public TbInfoQuery() {
	}

	/** full constructor */
	public TbInfoQuery(String info1, String info2, String info3) {
		this.info1 = info1;
		this.info2 = info2;
		this.info3 = info3;
	}

	// Property accessors

	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getInfo1() {
		return this.info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return this.info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return this.info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}

}