package com.king.nowedge.dto.ryx2;

/**
 * RyxUsersFavorite entity. @author MyEclipse Persistence Tools
 */

public class UsersFavoriteDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String keepType;
	private String objType;
	private Integer originId;
	private Integer objId;
	private String objName;
	private Integer uid;
	private String username;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public UsersFavoriteDTO() {
	}

	/** full constructor */
	public UsersFavoriteDTO(String keepType, String objType, Integer originId,
			Integer objId, String objName, Integer uid, String username,
			Integer createTime) {
		this.keepType = keepType;
		this.objType = objType;
		this.originId = originId;
		this.objId = objId;
		this.objName = objName;
		this.uid = uid;
		this.username = username;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeepType() {
		return this.keepType;
	}

	public void setKeepType(String keepType) {
		this.keepType = keepType;
	}

	public String getObjType() {
		return this.objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public Integer getOriginId() {
		return this.originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

}