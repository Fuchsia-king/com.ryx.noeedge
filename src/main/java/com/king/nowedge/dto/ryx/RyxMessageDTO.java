package com.king.nowedge.dto.ryx;

import java.util.List;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumYesorno;

/**
 * RyxMessage entity. @author MyEclipse Persistence Tools
 */

public class RyxMessageDTO extends BaseDTO implements java.io.Serializable {

	private Long oid ;
	private Integer otype ;
	private String title ;
	private String descr ;
	private Long lcreate;
	private Long userId ;
	private String url ;
	private String imageUrl ;
	private Integer iread = EnumYesorno.NO.getCode();
	
	private List<Long> ids;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getLcreate() {
		return lcreate;
	}
	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}
	public Integer getIread() {
		return iread;
	}
	public void setIread(Integer iread) {
		this.iread = iread;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	

}