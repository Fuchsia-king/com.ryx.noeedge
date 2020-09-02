package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumInterval1Type implements Serializable {
	
	
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

	public EnumInterval1Type(){
		
	}
	public EnumInterval1Type(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	


	public static  EnumInterval1Type TODAY = new EnumInterval1Type( 1, "今天"  );
	public static  EnumInterval1Type LAST_WEEK = new EnumInterval1Type( 2, "最近一周"  );
	public static  EnumInterval1Type LAST_MONTH = new EnumInterval1Type( 3, "最近30天"  );
	public static  EnumInterval1Type LAST_MONTH_3 = new EnumInterval1Type( 4, "最近90天"  );
	public static  EnumInterval1Type LAST_MONTH_6 = new EnumInterval1Type( 5, "最近180天"  );
	public static  EnumInterval1Type LAST_YEAR = new EnumInterval1Type( 6, "最近一年"  );
	public static  EnumInterval1Type LAST_YEAR_BEFORE = new EnumInterval1Type( 7, "一年以前"  );
	
	private static  ArrayList<EnumInterval1Type> LIST 		
	= new ArrayList<EnumInterval1Type>(Arrays.asList(
			//TODAY ,
			LAST_WEEK,
			LAST_MONTH,
			//LAST_MONTH_3,
			LAST_MONTH_6,
			LAST_YEAR,
			LAST_YEAR_BEFORE
			
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static EnumInterval1Type parse(Integer code) {
		for (EnumInterval1Type enumInterval1Type : LIST) {
			if(enumInterval1Type.getCode() == code){
				return enumInterval1Type;
			}
		}
		return null;
	}


	
	
	
	
	
}

