package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * RyxTeacher entity. @author MyEclipse Persistence Tools
 */

public class RyxTeacherQuery extends LoreBaseQuery implements java.io.Serializable {

	private String nickname;
	private Integer type;
	private Integer status;
	
	private Integer flag;
	private Integer category ; 

	private Long userId;
	
	private Integer display ;

	// Constructors

	/** default constructor */
	public RyxTeacherQuery() {
	}

	
	

	// Property accessors



	

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	

	

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Override
	 public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}




	public Integer getDisplay() {
		return display;
	}




	public void setDisplay(Integer display) {
		this.display = display;
	}
	
	
	
	
	
}