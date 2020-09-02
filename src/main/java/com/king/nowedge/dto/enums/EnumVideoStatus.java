package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumVideoStatus implements Serializable {
	
	
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

	public EnumVideoStatus(){
		
	}
	public EnumVideoStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	
	

	public static  EnumVideoStatus BEFORE_LIVING = new EnumVideoStatus( 3, "未开始"  );
	public static  EnumVideoStatus LIVING = new EnumVideoStatus( 1, "直播中"  );
	public static  EnumVideoStatus AFTER_LIVING = new EnumVideoStatus( 2, "已结束"  );
	public static  EnumVideoStatus PLAY_BACK = new EnumVideoStatus( 4, "回放中"  );
	
	
	private static  ArrayList<EnumVideoStatus> LIST 		
	= new ArrayList<EnumVideoStatus>(Arrays.asList(
			LIVING ,
			BEFORE_LIVING,
			AFTER_LIVING,
			PLAY_BACK
			
		));
	
	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	public static EnumVideoStatus parse(Integer code) {
		if (null == code){
			return null;
		}
		for(EnumVideoStatus obj : LIST){
			if(obj.code .equals(code)){
				return obj;
			}
		}
		return null;
	}
	public static EnumVideoStatus getBEFORE_LIVING() {
		return BEFORE_LIVING;
	}
	public static void setBEFORE_LIVING(EnumVideoStatus bEFORE_LIVING) {
		BEFORE_LIVING = bEFORE_LIVING;
	}
	public static EnumVideoStatus getLIVING() {
		return LIVING;
	}
	public static void setLIVING(EnumVideoStatus lIVING) {
		LIVING = lIVING;
	}
	
	public static EnumVideoStatus getAFTER_LIVING() {
		return AFTER_LIVING;
	}
	public static void setAFTER_LIVING(EnumVideoStatus aFTER_LIVING) {
		AFTER_LIVING = aFTER_LIVING;
	}
	public static ArrayList<EnumVideoStatus> getLIST() {
		return LIST;
	}
	public static void setLIST(ArrayList<EnumVideoStatus> lIST) {
		LIST = lIST;
	}
	public static EnumVideoStatus getPLAY_BACK() {
		return PLAY_BACK;
	}
	public static void setPLAY_BACK(EnumVideoStatus pLAY_BACK) {
		PLAY_BACK = pLAY_BACK;
	}


	
	
	
	
	
}

