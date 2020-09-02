package com.king.nowedge.dto.ryx2.validate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class SecureQuesDTO implements java.io.Serializable {

	
	@NotNull(message="{user.question1.not.empty}")
	private Integer question1;
	
	@NotNull(message="{user.question2.not.empty}")
	private Integer question2;	
	
	@NotNull(message="{user.question3.not.empty}")
	private Integer question3;
	
	@NotEmpty(message="{user.answer1.not.empty}")
	private String answer1;
	
	@NotEmpty(message="{user.answer2.not.empty}")
	private String answer2;	
	
	@NotEmpty(message="{user.answer3.not.empty}")
	private String answer3;
	
	@NotEmpty(message="{common.mobileVerifyCode.not.empty}")
	private String verifyCode;

	public Integer getQuestion1() {
		return question1;
	}

	public void setQuestion1(Integer question1) {
		this.question1 = question1;
	}

	public Integer getQuestion2() {
		return question2;
	}

	public void setQuestion2(Integer question2) {
		this.question2 = question2;
	}

	public Integer getQuestion3() {
		return question3;
	}

	public void setQuestion3(Integer question3) {
		this.question3 = question3;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	
	
	
	
	

}