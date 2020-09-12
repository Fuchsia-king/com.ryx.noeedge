package com.king.nowedge.dto.base;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;



/***
 * 
 * @author wangdap
 *
 */
public abstract class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	private Long id;
	private String uid ;
	private Long creater;
	private Long createrId;
    private Long lcreate;
    private Long lmodified;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date tcreate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date tmodified;
	private Integer ideleted = 0 ;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
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
	public Date getTcreate() {
		return tcreate;
	}
	public void setTcreate(Date tcreate) {
		this.tcreate = tcreate;
	}
	public Date getTmodified() {
		return tmodified;
	}
	public void setTmodified(Date tmodified) {
		this.tmodified = tmodified;
	}
	public Integer getIdeleted() {
		return ideleted;
	}
	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
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
}

