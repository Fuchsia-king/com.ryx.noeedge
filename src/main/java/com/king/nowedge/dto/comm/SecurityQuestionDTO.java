package com.king.nowedge.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class SecurityQuestionDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{security.question.question.not.empty}") 
	private String question;

	public String getQuestion() {
		return question;
	}
	
	

	public void setQuestion(String question) {
		this.question = question;
	}


}
