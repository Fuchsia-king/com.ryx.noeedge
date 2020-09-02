package com.king.nowedge.dto.ryx;

/**
 * RyxUsersFollow entity. @author MyEclipse Persistence Tools
 */

public class RyxUsersFollowDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private Integer fid;
	private String remark;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public RyxUsersFollowDTO() {
	}

	/** full constructor */
	public RyxUsersFollowDTO(Integer uid, Integer fid, String remark,
			Integer createTime) {
		this.uid = uid;
		this.fid = fid;
		this.remark = remark;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

}