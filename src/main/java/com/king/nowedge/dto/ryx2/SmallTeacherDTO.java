package com.king.nowedge.dto.ryx2;


/***
 * 
 * @author wangdap
 *
 */

public class SmallTeacherDTO implements java.io.Serializable {

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