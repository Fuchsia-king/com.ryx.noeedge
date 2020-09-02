package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumGenderType implements Serializable {
	
	
	/**
	 * 
	 */
	private static  long serialVersionUID = -8277601337461365457L;

	/**
	 * 
	 */
	private  Integer code;
	
	
	/**
	 * 
	 */
	private  String name;
	
	;

	public EnumGenderType(){
		
	}
	public EnumGenderType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	

	public static  EnumGenderType MAIL = new EnumGenderType( 1, "男"  );
	public static  EnumGenderType FEMAIL = new EnumGenderType( 2, "女"  );
	
	private static  ArrayList<EnumGenderType> LIST = new ArrayList<EnumGenderType>(Arrays.asList(
			MAIL,
			FEMAIL
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}


	public static EnumGenderType parse(Integer code) {
		for(EnumGenderType gender : LIST){
			if(gender.getCode() == code){
				return gender;
			}
		}
		return null;
	}


	
	
	
	
}

