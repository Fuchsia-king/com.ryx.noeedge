package com.king.nowedge.dto.ryx;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxSmallEvaluDTO extends BaseDTO implements java.io.Serializable {
	
	
	private String path ;
	private String userString;
	private Integer score;
	private String descr;
	private String lcreateTime;
	private Long userId; 
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUserString() {
		return userString;
	}
	public void setUserString(String userString) {
		this.userString = userString;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getLcreateTime() {
		return lcreateTime;
	}
	public void setLcreateTime(String lcreateTime) {
		this.lcreateTime = lcreateTime;
	}
	

	
	

}