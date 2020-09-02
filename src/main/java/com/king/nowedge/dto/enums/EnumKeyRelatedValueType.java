package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public  class EnumKeyRelatedValueType implements Serializable {
	
	
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

	public EnumKeyRelatedValueType(){
		
	}
	public EnumKeyRelatedValueType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static EnumKeyRelatedValueType getFollowQuestion(){
		return KV_FOLLOW_QUESTION;
	}
	
	public static EnumKeyRelatedValueType getCourseSeries(){
		return KV_COURSE_SERIES;
	}
	
	public static EnumKeyRelatedValueType getUserDownload(){
		return KV_U_DOWNLOAD;
	}
	
	public static EnumKeyRelatedValueType getApplyRecruit(){
		return KV_APPLY_RECRUIT;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static EnumKeyRelatedValueType getTeacherRate(){
		return KV_TEACHER_RATE;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static EnumKeyRelatedValueType getPartnerRate(){
		return KV_PARTNER_RATE;
	}
	public static EnumKeyRelatedValueType getPartnerCourse(){
		return KV_PARTNER_COURSE;
	}
	
	public static final EnumKeyRelatedValueType KV_COURSE_SERIES = new EnumKeyRelatedValueType( 1, "KV_COURSE_SERIES"  );   //课程体系
	public static final EnumKeyRelatedValueType KV_FOLLOW_QUESTION = new EnumKeyRelatedValueType( 2, "KV_FOLLOW_QUESTION"  );   //收藏问题
	public static final EnumKeyRelatedValueType KV_U_DOWNLOAD = new EnumKeyRelatedValueType( 3, "KV_U_DOWNLOAD"  );   // 下载关联
	public static final EnumKeyRelatedValueType KV_APPLY_RECRUIT = new EnumKeyRelatedValueType( 4, "KV_APPLY_RECRUIT"  );   // 应聘关联
	public static final EnumKeyRelatedValueType KV_GROUP_MEMBER = new EnumKeyRelatedValueType( 5, "KV_GROUP_MEMBER"  );   // 
	public static final EnumKeyRelatedValueType KV_SUB_ACCOUNT = new EnumKeyRelatedValueType( 7, "KV_SUB_ACCOUNT"  );   // 子账号
	public static final EnumKeyRelatedValueType KV_PARTNER_COURSE = new EnumKeyRelatedValueType( 8, "KV_PARTNER_COURSE"  );  //合作方课程体系
	
	public static final EnumKeyRelatedValueType KV_TEACHER_RATE = new EnumKeyRelatedValueType( 9, "KV_TEACHER_RATE"  );  //讲师合作佣金
	public static final EnumKeyRelatedValueType KV_PARTNER_RATE = new EnumKeyRelatedValueType( 10, "KV_PARTNER_RATE"  );  //推广合作伙伴佣金
	

	public static final EnumKeyRelatedValueType KV_ACTIVITY_MAIN = new EnumKeyRelatedValueType( 11, "KV_ACTIVITY_MAIN"  );  //活动主体信息
	public static final EnumKeyRelatedValueType KV_ACTIVITY_IMAGES = new EnumKeyRelatedValueType( 12, "KV_ACTIVITY_IMAGES"  );  //活动图片信息
	
	public static final EnumKeyRelatedValueType KV_ACTIVITY_ICONS = new EnumKeyRelatedValueType( 13, "KV_ACTIVITY_ICONS"  );  //首页图标信息
	
	public static final EnumKeyRelatedValueType KV_CURRENT_COURSE_ZHANGJIE = new EnumKeyRelatedValueType( 15, "CURRENT_COURSE_ZHANGJIE"  );  //当前学习章节
	
	
	
	
	
	private static  ArrayList<EnumKeyRelatedValueType> LIST = new ArrayList<EnumKeyRelatedValueType>(Arrays.asList(
			KV_APPLY_RECRUIT	
		));
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	
	
	
	
}



