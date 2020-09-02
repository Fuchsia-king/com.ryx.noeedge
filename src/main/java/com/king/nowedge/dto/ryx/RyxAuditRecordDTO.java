package com.king.nowedge.dto.ryx;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.helper.StringHelper;

/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxAuditRecordDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * userId
	 */
	private Long userId;
	
	/**
	 * object id
	 */
	private Long objId;
	
	/***
	 * object type
	 */
	private Integer objType;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	
	
	
	/**
	 * 描述
	 */
	private String descr;
	
	private String tcreateString;
	
	private String statusString;
	
	private String createrString;
	
	
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
	}
	public Integer getObjType() {
		return objType;
	}
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getTcreateString() {
		if(null == status){
			return null;
		}
		else{
			EnumAuditStatus enumAuditStatus =   EnumAuditStatus.parse(status);
			if(null == enumAuditStatus){
				return  "";
			}
			else{
				return enumAuditStatus.getName();
			}
		}
	}
	
	public void setTcreateString(String tcreateString) {
		this.tcreateString = tcreateString;
	}
	
	public String getStatusString() {
		if(null == getTcreate()){
			return "";
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(getTcreate());
		}
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	
	public String getCreaterString() {
		return userId.toString().equals(getCreater()) ? "我" : "融易学";
	}
	
	public void setCreaterString(String createrString) {
		this.createrString = createrString;
	}
	
	
	
}