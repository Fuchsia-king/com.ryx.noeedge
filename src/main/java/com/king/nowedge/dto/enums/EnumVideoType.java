package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumVideoType implements Serializable {
	
	
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

	public EnumVideoType(){
		
	}
	public EnumVideoType(Integer code, String name) {
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
	

	public static  EnumVideoType SCREEN = new EnumVideoType( 1, "大屏模式"  );
	public static  EnumVideoType DOC = new EnumVideoType( 2, "文档模式"  );
	
	private static  ArrayList<EnumVideoType> LIST = new ArrayList<EnumVideoType>(Arrays.asList(
			SCREEN,
			DOC
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

	public static EnumVideoType parse(Integer code) {
		return ( EnumVideoType ) INSTANCES.get( code );
	}


	private static  Map<Integer,EnumVideoType> INSTANCES = 
			new HashMap<Integer,EnumVideoType> (){
		{
			put(SCREEN.getCode(), SCREEN);
			put(DOC.getCode(), DOC);
		}
	};

	
	
	
	
	
	
}

