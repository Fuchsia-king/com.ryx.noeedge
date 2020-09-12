package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * RyxAd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxAdQuery extends LoreBaseQuery  implements java.io.Serializable {


	private Integer category;
	private String title;
	private String url;
	private String imageUrl;
	private Integer status;
	private String content;
	private Integer display;
	private String code ;

	


	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}