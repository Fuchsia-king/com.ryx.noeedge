package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumWorkExperType implements Serializable {
	
	
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

	public EnumWorkExperType(){
		
	}
	public EnumWorkExperType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	

	public static  EnumWorkExperType WORK_EXPER = new EnumWorkExperType( 1, "工作经历"  );
	public static  EnumWorkExperType STUDY_EXPER = new EnumWorkExperType( 2, "学习经历"  );
	
	private static  ArrayList<EnumWorkExperType> LIST 		
	= new ArrayList<EnumWorkExperType>(Arrays.asList(
			WORK_EXPER ,
			WORK_EXPER
			
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	

	public static EnumWorkExperType parse(Integer code) {
		for (EnumWorkExperType enumWorkExperType : LIST) {
			if(enumWorkExperType.getCode() == code){
				return enumWorkExperType;
			}			
		}
		return null;
	}
	
	
}

