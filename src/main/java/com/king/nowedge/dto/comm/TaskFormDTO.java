package com.king.nowedge.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class TaskFormDTO extends BaseDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{taskform.title.not.empty}") 	
	private String title; 
	
	@NotEmpty(message="{taskform.input.not.empty}") 	
	private String input; 
	
	private String descr; 
	
	@javax.validation.constraints.NotNull(message="{taskform.type.not.empty}")
	private Long type;
	
	
	@javax.validation.constraints.NotNull(message="{taskform.imust.not.empty}")
	private Integer imust ;
	
	
	private String regex ;
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Integer getImust() {
		return imust;
	}

	public void setImust(Integer imust) {
		this.imust = imust;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	 
	
	


}
