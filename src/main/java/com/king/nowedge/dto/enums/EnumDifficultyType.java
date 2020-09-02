package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EnumDifficultyType implements Serializable {
	
	
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
	
	public EnumDifficultyType(){
		
	}

	public EnumDifficultyType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static Map getMap(){
		return INSTANCES;
	}
	
	
	/**
	 * 
	 */
	 
	public static  EnumDifficultyType LOW = new EnumDifficultyType( 0, "初级课程"  );
	public static  EnumDifficultyType MIDDLE = new EnumDifficultyType( 1, "中级课程"  );
	public static  EnumDifficultyType HIGH = new EnumDifficultyType( 2, "高级课程"  );
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumDifficultyType parse(Integer code) {
		return ( EnumDifficultyType ) INSTANCES.get( code );
	}

	
	private static  Map<Integer,EnumDifficultyType> INSTANCES = new HashMap<Integer,EnumDifficultyType>(){
		{
			put(LOW.getCode(), LOW);
			put(MIDDLE.getCode(),MIDDLE);
			put(HIGH.getCode(),HIGH);
		}
	};
	

	private static  List<EnumDifficultyType> LIST = new ArrayList<EnumDifficultyType>()
	{
		{
			add(LOW);
			add(MIDDLE);
			add(HIGH);
		}
	};
	
}

