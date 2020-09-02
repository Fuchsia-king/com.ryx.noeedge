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
public  class EnumRegFrom implements Serializable {
	
	
	
	
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
	

	public EnumRegFrom(){
		
	}
	public EnumRegFrom(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}
	
	

	
	
	public static  EnumRegFrom IOS = new EnumRegFrom( 1, "IOS"  );
	public static  EnumRegFrom ANDROID = new EnumRegFrom( 2, "ANDROID"  );
	public static  EnumRegFrom PC = new EnumRegFrom( 3, "PC"  );
	public static  EnumRegFrom H5 = new EnumRegFrom( 4, "H5"  );
	
	
	private static  ArrayList<EnumRegFrom> LIST = new ArrayList<EnumRegFrom>(Arrays.asList(
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

	
	

	
	public static EnumRegFrom parse(Integer code) {
		if(null == code){
			return null;
		}
		for(EnumRegFrom enumRegFrom : LIST){
			if(code == enumRegFrom.getCode()){
				return enumRegFrom;
			}
		}
		return null;
	}


	

	
	
	
	
	
	
}

