package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumWorkStatus implements Serializable {
	
	
	
	
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
	

	public EnumWorkStatus(){
		
	}
	public EnumWorkStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	
	public static  EnumWorkStatus WORKING_CHANING = new EnumWorkStatus( 2, "工作中，打算寻找新机会"  );
	public static  EnumWorkStatus NOWORKING_CHANING = new EnumWorkStatus( 3, "已离职，正在寻找新机会"  );
	public static  EnumWorkStatus STUDING_WORKING = new EnumWorkStatus( 0, "正在学习，寻找实习机会"  );
	public static  EnumWorkStatus WORKING_NOCHANGING = new EnumWorkStatus( 1, "没有换工作计划"  );
	
	private static  ArrayList<EnumWorkStatus> LIST = new ArrayList<EnumWorkStatus>(Arrays.asList(
			STUDING_WORKING,
			WORKING_NOCHANGING,
			WORKING_CHANING,
			NOWORKING_CHANING
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	
	public static EnumWorkStatus parse(Integer code) {
		for(EnumWorkStatus wstatus : LIST){
			if(code == wstatus.getCode()){
				return wstatus;
			}
		}		
		return null;
	}


	

	
	public static EnumWorkStatus getStudyingWorking() {
		return STUDING_WORKING;
	}
	public static EnumWorkStatus getWorkingNochanging() {
		return WORKING_NOCHANGING;
	}
	public static EnumWorkStatus getWorkingChanging() {
		return WORKING_CHANING;
	}
	public static EnumWorkStatus getNoworkingChanging() {
		return NOWORKING_CHANING;
	}
	
	
}

