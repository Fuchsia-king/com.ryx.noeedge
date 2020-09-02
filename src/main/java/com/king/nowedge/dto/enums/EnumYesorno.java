package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumYesorno implements Serializable {
	
	
	
	
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
	

	public EnumYesorno(){
		
	}
	public EnumYesorno(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	

	public static  EnumYesorno YES = new EnumYesorno( 1, "是"  );
	public static  EnumYesorno NO = new EnumYesorno( 0, "否"  );
	
	private static  ArrayList<EnumYesorno> LIST = new ArrayList<EnumYesorno>(Arrays.asList(
			YES,
			NO
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	public static EnumYesorno parse(Integer code) {
		for(EnumYesorno type : LIST){
			if(type.getCode() == code){
				return type;
			}
		}
		return null;
	}


	
	
	public static EnumYesorno getYes() {
		return YES;
	}
	public static EnumYesorno getNo() {
		return NO;
	}
	
	
	
}

