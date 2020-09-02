package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumCategoryType implements Serializable {
	
	
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

	public EnumCategoryType(){
		
	}
	public EnumCategoryType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	

	public static  EnumCategoryType TEACHER = new EnumCategoryType( 0, "讲师"  );
	
	public static  EnumCategoryType COOPER = new EnumCategoryType( 107, "合作伙伴"  );
	
	private static  ArrayList<EnumCategoryType> LIST = new ArrayList<EnumCategoryType>(Arrays.asList(
			TEACHER
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumCategoryType parse(Integer code) {
		return ( EnumCategoryType ) INSTANCES.get( code );
	}


	private static  Map<Integer,EnumCategoryType> INSTANCES = 
			new HashMap<Integer,EnumCategoryType> (){
		{
			put(TEACHER.getCode(), TEACHER);
		}
	};
	
	
	
	
	
}

