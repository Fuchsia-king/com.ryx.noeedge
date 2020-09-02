package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class UserSecurityQuestionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String user ; 
	
	@NotEmpty(message="{userSecurityQuestion.question1.not.empty}") 
	private String question1;

	@NotEmpty(message="{userSecurityQuestion.answer1.not.empty}")
	private String answer1;	

	@NotEmpty(message="{userSecurityQuestion.question2.not.empty}")
	private String question2;

	@NotEmpty(message="{userSecurityQuestion.answer2.not.empty}")
	private String answer2;

	
	@NotEmpty(message="{userSecurityQuestion.question3.not.empty}")
	private String question3;

	@NotEmpty(message="{userSecurityQuestion.answer3.not.empty}")
	private String answer3;

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

}
