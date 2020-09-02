package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EnumTeacherType implements Serializable {
	
	
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
	
	public EnumTeacherType(){
		
	}

	public EnumTeacherType(Integer code, String name) {
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
	
	
	/**
	 * 
	 */	 
	public static  EnumTeacherType PERSONAL = new EnumTeacherType( 0, "个人"  );
	public static  EnumTeacherType ORG = new EnumTeacherType( 2, "机构"  );
	
	public static  EnumTeacherType getPersonal() {
		return PERSONAL;
	}
	
	public static  EnumTeacherType getOrg() {
		return ORG;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumTeacherType parse(Integer code) {
		return ( EnumTeacherType ) INSTANCES.get( code );
	}
	
	private static  Map<Integer,EnumTeacherType> INSTANCES = new HashMap<Integer,EnumTeacherType>(){
		{
			put(PERSONAL.getCode(), PERSONAL);
			put(ORG.getCode(),ORG);
		}
	};	

	private static  List<EnumTeacherType> LIST = new ArrayList<EnumTeacherType>()
	{
		{
			add(PERSONAL);
			add(ORG);
		}
	};
	
}

