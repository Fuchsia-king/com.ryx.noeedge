package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumTaskStatus implements Serializable {
	
	
	
	
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
	

	public EnumTaskStatus(){
		
	}
	public EnumTaskStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	

	public static  EnumTaskStatus CREATED = new EnumTaskStatus( 0, "已创建"  );
	public static  EnumTaskStatus APPROVED = new EnumTaskStatus( 1, "审核通过"  );
	public static  EnumTaskStatus REGECTED = new EnumTaskStatus( 2, "审核拒绝"  );
	public static  EnumTaskStatus OUT_OF_DATE = new EnumTaskStatus( 3, "已过期"  );
	public static  EnumTaskStatus RECREATED = new EnumTaskStatus( 4, "重新打开审核"  );
	public static  EnumTaskStatus UNPROCESSED = new EnumTaskStatus( 5, "待处理"  );
	
	private static  ArrayList<EnumTaskStatus> LIST = new ArrayList<EnumTaskStatus>(Arrays.asList(
			CREATED,
			APPROVED,
			REGECTED,
			OUT_OF_DATE,
			RECREATED,
			UNPROCESSED
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	public static EnumTaskStatus parse(Integer code) {
		for(EnumTaskStatus type : LIST){
			if(type.getCode() == code){
				return type;
			}
		}
		return null;	
	}



	
	public static EnumTaskStatus getCreated() {
		return CREATED;
	}
	public static EnumTaskStatus getApproved() {
		return APPROVED;
	}
	public static EnumTaskStatus getRegected() {
		return REGECTED;
	}
	public static EnumTaskStatus getOutOfDate() {
		return OUT_OF_DATE;
	}
	public static EnumTaskStatus getReunaudited() {
		return RECREATED;
	}
	
	
	
	
}

