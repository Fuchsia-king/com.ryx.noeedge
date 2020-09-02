package com.king.nowedge.dto.base;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



/***
 * 
 * @author wangdap
 *
 */
public abstract class RyxBaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
	
	@JsonIgnore
	private Long id; 
	
	
	@JsonIgnore
	private Long creater;	
	
	@JsonProperty("创建人")
	private String createrName ;
	
	
	@JsonProperty("创建时间")
	private String createTime ;
	
	
	
	@JsonProperty("更新时间")
	private String modifiedTime ;
	
	
	
	@JsonIgnore
    private Long lcreate;
	
	@JsonIgnore
    private Long lmodified;
	    
	    
    @JsonIgnore
	private Integer ideleted = 0 ;

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getCreater() {
		return creater;
	}



	public void setCreater(Long creater) {
		this.creater = creater;
	}



	public Long getLcreate() {
		return lcreate;
	}



	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}



	public Long getLmodified() {
		return lmodified;
	}



	public void setLmodified(Long lmodified) {
		this.lmodified = lmodified;
	}



	public Integer getIdeleted() {
		return ideleted;
	}



	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getModifiedTime() {
		return modifiedTime;
	}



	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getCreaterName() {
		return createrName;
	}



	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	
	

	


}

