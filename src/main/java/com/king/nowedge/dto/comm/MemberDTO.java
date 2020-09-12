package com.king.nowedge.dto.comm;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class MemberDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	@NotEmpty(message="{common.code.not.empty}")
	private String code;
	@NotEmpty(message="{common.name.not.empty}")
	private String name; 
	private String passd;
	private String qq;
	private String wangwang ;
	private String weixin ;
	private Integer sellerLevel ;
	private Integer buyerLevel ;
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email;
	@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassd() { return passd;}
	public void setPassd(String passd) { this.passd = passd; }
	public String getQq() { return qq; }
	public void setQq(String qq) { this.qq = qq; }
	public String getWangwang() { return wangwang; }
	public void setWangwang(String wangwang) { this.wangwang = wangwang; }
	public String getWeixin() { return weixin; }
	public void setWeixin(String weixin) { this.weixin = weixin; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }
}
