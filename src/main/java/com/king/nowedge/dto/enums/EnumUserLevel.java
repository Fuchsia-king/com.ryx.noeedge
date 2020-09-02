package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public  class EnumUserLevel implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	private  Integer code;
	
	
	/**
	 * 
	 */
	private  String name;
	

	public EnumUserLevel(){
		
	}
	public EnumUserLevel(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	

	public static  EnumUserLevel COMMON_USER = new EnumUserLevel( 1, "个人会员"  );
	public static  EnumUserLevel PERSONAL_TEACHER = new EnumUserLevel( 2, "认证讲师"  );
	public static  EnumUserLevel ORG_TEACHER = new EnumUserLevel( 3, "认证机构"  );
	public static  EnumUserLevel COMPANY_USER = new EnumUserLevel( 4, "企业会员"  );
	public static  EnumUserLevel PARTNER_USER = new EnumUserLevel( 5, "合作伙伴"  );
	public static  EnumUserLevel SUB_USER = new EnumUserLevel( 6, "子账号"  );
	
	
	
	/**
	 * 
	 */
	private static  ArrayList<EnumUserLevel> LIST = new ArrayList<EnumUserLevel>(Arrays.asList(
			COMMON_USER,
			PERSONAL_TEACHER,
			ORG_TEACHER,
			COMPANY_USER,
			SUB_USER
		));
	
	/**
	 * 
	 * @return
	 */
	public static ArrayList<EnumUserLevel> getUserList(){
		return new ArrayList<EnumUserLevel>(Arrays.asList(
				COMMON_USER,
				COMPANY_USER
			));
	}
	
	

	/**
	 * 
	 * @return
	 */
	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	

	
	public static EnumUserLevel parse(Integer code) {
		for(EnumUserLevel enumUserLevel : LIST){
			if(enumUserLevel.getCode() .equals(code)){
				return enumUserLevel;
			}
		}
		return null;
	}
	public static EnumUserLevel getCOMMON_USER() {
		return COMMON_USER;
	}
	public static void setCOMMON_USER(EnumUserLevel cOMMON_USER) {
		COMMON_USER = cOMMON_USER;
	}
	public static EnumUserLevel getPERSONAL_TEACHER() {
		return PERSONAL_TEACHER;
	}
	public static void setPERSONAL_TEACHER(EnumUserLevel pERSONAL_TEACHER) {
		PERSONAL_TEACHER = pERSONAL_TEACHER;
	}
	public static EnumUserLevel getORG_TEACHER() {
		return ORG_TEACHER;
	}
	public static void setORG_TEACHER(EnumUserLevel oRG_TEACHER) {
		ORG_TEACHER = oRG_TEACHER;
	}
	public static ArrayList<EnumUserLevel> getLIST() {
		return LIST;
	}
	public static void setLIST(ArrayList<EnumUserLevel> lIST) {
		LIST = lIST;
	}
	public static EnumUserLevel getCOMPANY_USER() {
		return COMPANY_USER;
	}
	public static void setCOMPANY_USER(EnumUserLevel cOMMPANY_USER) {
		COMPANY_USER = cOMMPANY_USER;
	}

	
	
	
	
	
}

