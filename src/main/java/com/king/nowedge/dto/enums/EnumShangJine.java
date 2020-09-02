package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumShangJine implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static  long serialVersionUID = -8277601337461365457L;

	/**
	 * 
	 */
	private  Double code;
	
	
	/**
	 * 
	 */
	private  String name;
	

	public EnumShangJine(){
		
	}
	public EnumShangJine(Double code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}
	
	
	

	public static  EnumShangJine JINE_5 = new EnumShangJine( 0.01, "1元"  );
	public static  EnumShangJine JINE_10 = new EnumShangJine( 10.00, "10元"  );
	public static  EnumShangJine JINE_15 = new EnumShangJine( 15.00, "15元"  );
	public static  EnumShangJine JINE_20 = new EnumShangJine( 20.00, "20元"  );
	
	private static  ArrayList<EnumShangJine> LIST = new ArrayList<EnumShangJine>(Arrays.asList(
			JINE_5,
			JINE_10,
			JINE_15,
			JINE_20
	));
	


	public Double getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	public static EnumShangJine parse(Double code) {
		for(EnumShangJine jine : LIST){
			if(jine.getCode().equals(code)){
				return jine;
			}
		}
		return null;
	}




	
	
	
	
	
	
}

