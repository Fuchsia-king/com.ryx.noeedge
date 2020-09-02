package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumKeyValueType implements Serializable {
	
	
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

	public EnumKeyValueType(){
		
	}
	
	
	public EnumKeyValueType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	private static Map INSTANCES = new HashMap();
	
	
	public static Map toMap(){
		return INSTANCES;
	}
	
	

	private static List LIST = new ArrayList();
	
	public static final EnumKeyValueType KEY_VALUE_COMPANY_TYPE = new EnumKeyValueType( 1, "COMPANY_TYPE"  );
	public static final EnumKeyValueType KEY_VALUE_COMPANY_SCALE = new EnumKeyValueType( 2, "COMPANY_SCALE"  );
	public static final EnumKeyValueType KEY_VALUE_WORKING_YEARS = new EnumKeyValueType( 3, "WORKING_YEARS"  );
	public static final EnumKeyValueType KEY_VALUE_EDUCATION_LEVEL = new EnumKeyValueType( 4, "EDUCATION_LEVEL"  );
	public static final EnumKeyValueType KEY_VALUE_SALARY_REQUIREMENT = new EnumKeyValueType( 5, "SALARY_REQUIREMENT"  );
	public static final EnumKeyValueType KEY_VALUE_WELLFARE = new EnumKeyValueType( 6, "WELLFARE"  );
	//public static final EnumKeyValueType KEY_VALUE_SPECIALTY = new EnumKeyValueType( 7, "SPECIALTY"  );   // 专业职位分类
	//public static final EnumKeyValueType KEY_VALUE_INDUSTRY = new EnumKeyValueType( 8, "INDUSTRY"  );   // 行业分类
	public static final EnumKeyValueType KEY_VALUE_COUNTRY = new EnumKeyValueType( 9, "COUNTRY"  );   // 国家城市区域街道
	public static final EnumKeyValueType KEY_VALUE_PROVINCE = new EnumKeyValueType( 10, "PROVINCE"  );   // 省份，城市
	public static final EnumKeyValueType KEY_VALUE_AREA = new EnumKeyValueType( 11, "AREA"  );   // 城区、县级
	
	
	
	public static final EnumKeyValueType KEY_VALUE_INDUSTRY_0 = new EnumKeyValueType( 12, "INDUSTRY_0"  );   // 一级行业类目
	public static final EnumKeyValueType KEY_VALUE_INDUSTRY_1 = new EnumKeyValueType( 13, "INDUSTRY_1"  );   // 二级行业类目
	public static final EnumKeyValueType KEY_VALUE_INDUSTRY_2 = new EnumKeyValueType( 14, "INDUSTRY_2"  );   // 三级行业类目
	
	
	
	public static final EnumKeyValueType KEY_VALUE_SPECIALTY_0 = new EnumKeyValueType( 15, "SPECIALTY_0"  );   // 一级专业
	public static final EnumKeyValueType KEY_VALUE_SPECIALTY_1 = new EnumKeyValueType( 16, "SPECIALTY_1"  );   // 二级专业
	public static final EnumKeyValueType KEY_VALUE_SPECIALTY_2 = new EnumKeyValueType( 17, "SPECIALTY_2"  );   // 三级专业

	public static final EnumKeyValueType KEY_VALUE_CITY = new EnumKeyValueType( 18, "CITY"  );   // 城市
	/**
	 * 
	 */
	public static final EnumKeyValueType KEY_VALUE_SPREAD_LINK = new EnumKeyValueType( 19, "KEY_VALUE_SPREAD_LINK"  );   // 推广链接
	
	//public static final EnumKeyValueType KEY_VALUE_COURSE_CHATROOM = new EnumKeyValueType( 20, "KEY_VALUE_COURSE_CHATROOM"  );   // 推广链接
	
	public static final EnumKeyValueType KEY_VALUE_COURSE_CHAT_GROUP = new EnumKeyValueType( 21, "KEY_VALUE_COURSE_CHAT_GROUP"  );   // 推广链接
	
	public static final EnumKeyValueType KV_APPLY_COURSE = new EnumKeyValueType( 22, "KV_APPLY_COURSE"  );   // 课程报名
	
	public static final EnumKeyValueType KV_USER_TITLE = new EnumKeyValueType( 23, "KV_USER_TITLE"  );   // 用户头衔
	
	public static final EnumKeyValueType KV_EMPLOYEE_TITLE = new EnumKeyValueType( 24, "KV_EMPLOYEE_TITLE"  );   // 员工头衔
	
	public static final EnumKeyValueType KV_EMPLOYEE_LEVEL = new EnumKeyValueType( 25, "KV_EMPLOYEE_LEVEL"  );   // 员工级别
	
	public static final EnumKeyValueType KV_FAVROABLE_COURSE = new EnumKeyValueType( 26, "KV_FAVROABLE_COURSE"  );   // 特惠课程
	
	public static final EnumKeyValueType KV_INDEX_CATEGORY = new EnumKeyValueType( 27, "KV_INDEX_CATEGORY"  );   // 首页类别
	
	public static final EnumKeyValueType KV_RYX_OFFLLINE_CITY = new EnumKeyValueType( 28, "KV_RYX_OFFLLINE_CITY"  );   // 融易学线下课程城市
	
	public static final EnumKeyValueType KV_RYX_ACTIVITY_CITY = new EnumKeyValueType( 29, "KV_RYX_ACTIVITY_CITY"  );   // 融易学金融活动城市
	
	public static final EnumKeyValueType KV_SECURITY_QUESTION = new EnumKeyValueType( 30, "KV_SECURITY_QUESTION"  );   //安全问题设置
	
	public static final EnumKeyValueType KV_WORK_GROUP =  new EnumKeyValueType( 31, "KV_WORK_GROUP"  );   //工作组
	
	public static final EnumKeyValueType KV_RECOMMEND_COURSE = new EnumKeyValueType( 32, "KV_RECOMMEND_COURSE"  );   // 推荐课程

	
	//体系课程购买的标识（1，必须体系购买，0或者空允许单个购买）
	public static final EnumKeyValueType KV_COURSE_MUST_SERIES_BUY_FLAG = new EnumKeyValueType(33, "KV_COURSE_MUST_SERIES_BUY_FLAG");
	
	public static final EnumKeyValueType KV_TEACHER_INDEX = new EnumKeyValueType(34, "KV_TEACHER_INDEX");
	
	public static final EnumKeyValueType KV_MOBILE_TEACHER_INDEX = new EnumKeyValueType(35, "KV_MOBILE_TEACHER_INDEX");
	
	

	public static final EnumKeyValueType KV_CRM_BIZ_TYPE = new EnumKeyValueType(36, "CRM_BIZ_TYPE");
	
	public static final EnumKeyValueType KV_CRM_SOURCE = new EnumKeyValueType(37, "KV_CRM_SOURCE");
	
	public static final EnumKeyValueType KV_CRM_STATUS = new EnumKeyValueType(38, "KV_CRM_STATUS");

	public static final EnumKeyValueType KV_CRM_CONTRACT_TYPE = new EnumKeyValueType(39, "KV_CRM_CONTRACT_TYPE");
	
	public static final EnumKeyValueType KV_CRM_PAY_TYPE = new EnumKeyValueType(40, "KV_CRM_PAY_TYPE");

	public static final EnumKeyValueType KV_CRM_CONTRACT_STATUS = new EnumKeyValueType(41, "KV_CRM_CONTRACT_STATUS");
	
	
	/**
	 * 合同计划状态
	 */
	public static final EnumKeyValueType KV_CRM_MONEY_PLAN_STATUS = new EnumKeyValueType(42, "KV_CRM_MONEY_PLAN_STATUS");

	
	/**
	 * 
	 * @return
	 */
	public static EnumKeyValueType  getPayType(){
		return KV_CRM_PAY_TYPE ;
	}
	
	/**
	 * 
	 * @return
	 */
	public static EnumKeyValueType  getContractType(){
		return KV_CRM_CONTRACT_TYPE ;
	}
	
	/**
	 * 
	 * @return
	 */
	public static EnumKeyValueType  getContractStatus(){
		return KV_CRM_CONTRACT_STATUS ;
	}
	
	
	
	public static EnumKeyValueType getMoneyPlanStatus () {
		return KV_CRM_MONEY_PLAN_STATUS;
	}
	
	public static Map<Integer, EnumKeyValueType> getINSTANCES() {
		return INSTANCES;
	}
	public static void setINSTANCES(Map<Integer, EnumKeyValueType> iNSTANCES) {
		INSTANCES = iNSTANCES;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}



	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	


	public static EnumKeyValueType parse(Integer code) {
		return ( EnumKeyValueType ) INSTANCES.get( code );
	}
	


	
	
	
	
	
	
	
}

