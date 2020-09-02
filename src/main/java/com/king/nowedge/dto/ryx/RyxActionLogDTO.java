package com.king.nowedge.dto.ryx;

/**
 * RyxActionLog entity. @author MyEclipse Persistence Tools
 */

public class RyxActionLogDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private Short type;
	private Integer actionId;
	private Integer userId;
	private Long actionIp;
	private String model;
	private String recordId;
	private String remark;
	private Short status;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public RyxActionLogDTO() {
	}

	/** full constructor */
	public RyxActionLogDTO(Short type, Integer actionId, Integer userId,
			Long actionIp, String model, String recordId, String remark,
			Short status, Integer createTime) {
		this.type = type;
		this.actionId = actionId;
		this.userId = userId;
		this.actionIp = actionIp;
		this.model = model;
		this.recordId = recordId;
		this.remark = remark;
		this.status = status;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getActionIp() {
		return this.actionIp;
	}

	public void setActionIp(Long actionIp) {
		this.actionIp = actionIp;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

}