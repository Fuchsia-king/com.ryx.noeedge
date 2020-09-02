package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumIntervalType implements Serializable {
	
	
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

	public EnumIntervalType(){
		
	}
	public EnumIntervalType(Integer code, String name) {
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
	

	public static  EnumIntervalType TOMORROW = new EnumIntervalType( 1, "明天"  );
	public static  EnumIntervalType THIS_WEEK = new EnumIntervalType( 2, "本周"  );
	public static  EnumIntervalType THIS_WEEKEND = new EnumIntervalType( 3, "本周末"  );
	public static  EnumIntervalType THIS_MONTH = new EnumIntervalType( 4, "1个月内"  );
	public static  EnumIntervalType THIS_MONTH_AFTER = new EnumIntervalType( 5, "本月之后"  );
	public static  EnumIntervalType TODAY_BEFORE = new EnumIntervalType( 6, "往期回顾"  );
	public static  EnumIntervalType THREE_MONTH_INNER = new EnumIntervalType( 7, "3个月内"  );
	
	
	private static  ArrayList<EnumIntervalType> LIST 		
	= new ArrayList<EnumIntervalType>(Arrays.asList(
			//TOMORROW ,
			//THIS_WEEK,
			//THIS_WEEKEND,
			THIS_MONTH,
			THREE_MONTH_INNER
			//THIS_MONTH_AFTER,
			//TODAY_BEFORE
			
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

	public static EnumIntervalType parse(Integer code) {
		return ( EnumIntervalType ) INSTANCES.get( code );
	}


	private static  Map<Integer,EnumIntervalType> INSTANCES = 
			new HashMap<Integer,EnumIntervalType> (){
		{
			put(TOMORROW.getCode(), TOMORROW);
			put(THIS_WEEK.getCode(), THIS_WEEK);
			put(THIS_WEEKEND.getCode(),THIS_WEEKEND);
			put(THIS_MONTH.getCode(), THIS_MONTH);
			put(THIS_MONTH_AFTER.getCode(), THIS_MONTH_AFTER);
			put(TODAY_BEFORE.getCode(), TODAY_BEFORE);
		}
	};
	
	
	
	
	
}

