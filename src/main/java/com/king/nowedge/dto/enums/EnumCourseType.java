package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public final class EnumCourseType implements Serializable {
	
	
	
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

	public EnumCourseType(){
		
	}
	public EnumCourseType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	
	

	public static final EnumCourseType OFFLINE_COURSE = new EnumCourseType( 1, "OFFLINE_COURSE"  );
	public static final EnumCourseType VIDEO_COURSE = new EnumCourseType( 2, "VIDEO_COURSE"  );	
	
	
	public static final EnumCourseType MAIN_COURSE = new EnumCourseType( 21, "体系主课程"  );  // 用于保存课程体系中的主课程
	public static final EnumCourseType SUB_COURSE = new EnumCourseType( 0, "体系子课程"  ); // 用于保存课程体系中的子课程
	
	
	public static final EnumCourseType ENTERPRISE_MAIN_COURSE = new EnumCourseType( 3, "企业课程主课程"  );  // 用于保存课程体系中的主课程
	public static final EnumCourseType ENTERPRISE_SUB_COURSE = new EnumCourseType( 4, "企业课程子课程"  ); // 用于保存课程体系中的子课程
	

	public static EnumCourseType getMainCourse(){
		return MAIN_COURSE;
	}
	
	public static EnumCourseType getSubCourse(){
		return SUB_COURSE;
	}
	
	
	public static EnumCourseType getEnterpriseMainCourse(){
		return ENTERPRISE_MAIN_COURSE;
	}
	
	public static EnumCourseType getEnterpriseSubCourse(){
		return ENTERPRISE_SUB_COURSE;
	}
	
	public static ArrayList<EnumCourseType> getMainSubList(){
		return new ArrayList<EnumCourseType>(Arrays.asList(
				MAIN_COURSE ,
				SUB_COURSE
			));
	}
	
	public static ArrayList<EnumCourseType> getEnterpriseMainSubList(){
		return new ArrayList<EnumCourseType>(Arrays.asList(
				ENTERPRISE_MAIN_COURSE ,
				ENTERPRISE_SUB_COURSE
			));
	}
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	
	private static  ArrayList<EnumCourseType> LIST = new ArrayList<EnumCourseType>(Arrays.asList(
			MAIN_COURSE,
			SUB_COURSE
		));

	

	public static EnumCourseType parse(Integer code) {
		if(null == code ) return null ;
		for(EnumCourseType enumCourseType : LIST){
			if(code == enumCourseType.getCode()){
				return enumCourseType;
			}
		}
		return null ;
		
	}


	
	
	
	
}

