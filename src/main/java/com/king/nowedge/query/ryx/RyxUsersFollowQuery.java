package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * RyxUsersFollow entity. @author MyEclipse Persistence Tools
 */

public class RyxUsersFollowQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String remark;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public RyxUsersFollowQuery() {
	}

	/** full constructor */
	public RyxUsersFollowQuery( Integer fid, String remark,
			Integer createTime) {
		this.fid = fid;
		this.remark = remark;
		this.createTime = createTime;
	}

	// Property accessors



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