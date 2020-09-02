package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.StringHelper;


/***
 * 
 * @author wangdap
 *
 */

public class RyxSmallTeacherDTO implements java.io.Serializable {

	private Long id ;
	private String imageUrl ;
	private Integer ccnt;
	private Integer eval;
	private String nickname ;
	private String tags ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getCcnt() {
		return ccnt;
	}
	public void setCcnt(Integer ccnt) {
		this.ccnt = ccnt;
	}
	public Integer getEval() {
		return eval;
	}
	public void setEval(Integer eval) {
		this.eval = eval;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	

}