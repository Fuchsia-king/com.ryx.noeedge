package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * 注册来源
 * @author Administrator
 *
 */

@Component
public  class EnumOrderSource implements Serializable {
	
	
	
	
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
	

	public EnumOrderSource(){
		
	}
	public EnumOrderSource(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}
	
	

	
	
	public static  EnumOrderSource IOS = new EnumOrderSource( 1, "IOS"  );
	public static  EnumOrderSource ANDROID = new EnumOrderSource( 2, "ANDROID"  );
	public static  EnumOrderSource PC = new EnumOrderSource( 3, "PC"  );
	public static  EnumOrderSource H5 = new EnumOrderSource( 4, "H5"  );
	
	
	private static  ArrayList<EnumOrderSource> LIST = new ArrayList<EnumOrderSource>(Arrays.asList(
			IOS,
			ANDROID,
			PC,
			H5
	));
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	public static EnumOrderSource parse(Integer code) {
		if(null == code){
			return null;
		}
		for(EnumOrderSource enumRegFrom : LIST){
			if(code == enumRegFrom.getCode()){
				return enumRegFrom;
			}
		}
		return null;
	}


	

	
	
	
	
	
	
}

