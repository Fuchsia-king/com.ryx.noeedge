package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EnumCourseStatus implements Serializable {
	
	
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
	
	private EnumCourseStatus() {
	}

	private EnumCourseStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List toList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	
	
	/**
	 * 
	 */
	public static  EnumCourseStatus INVALID = new EnumCourseStatus( 0, "INVALID"  ); 
	public static  EnumCourseStatus VALID = new EnumCourseStatus( 1, "VALID"  );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumCourseStatus parse(Integer code) {
		return ( EnumCourseStatus ) INSTANCES.get( code );
	}

	
	private static  Map<Integer,EnumCourseStatus> INSTANCES = new HashMap<Integer,EnumCourseStatus>(){
		{
			put(INVALID.getCode(), INVALID);
			put(VALID.getCode(), INVALID);			
		}
	};
	
	private static  List LIST = new ArrayList();
	
	
	
	
}

