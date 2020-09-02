package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.NotEmpty;


public class InfoDTO extends CourseDTO {


	
	
	
	@NotEmpty(message="{course.imageUrl.not.empty}")
	private String imageUrl;
	

	private Integer flag;

	
	@NotEmpty(message="{course.descr.not.empty}")
	private String descr;
	

	//@NotEmpty(message="{course.content.not.empty}")
	private String content;
	
	
	@NotEmpty(message="{info.content.not.empty}")
	private String twoma;


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getContent() {
		return content;
	}
	
	


	public String getTwoma() {
		return twoma;
	}


	public void setTwoma(String twoma) {
		this.twoma = twoma;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	
	
	
	
}