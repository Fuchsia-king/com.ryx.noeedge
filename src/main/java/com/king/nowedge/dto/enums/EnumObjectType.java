package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumObjectType implements Serializable {
	
	
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
	

	public EnumObjectType(){
		
	}
	
	
	public EnumObjectType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	
	public static EnumObjectType getOnlineModule(){
		return ONLINE_MODULE;
	}
	
	
	public static EnumObjectType getSvideoModule(){
		return SVIDEO_MODULE;
	}
	
	public static EnumObjectType geEnterpriseCourseModule(){
		return ENTERPRISE_COURSE_MODULE;
	}
	
	public static EnumObjectType getInfoModule(){
		return INFO_MODULE;
	}
	
	public static EnumObjectType getBookModule(){
		return BOOK_MODULE;
	}
	
	public static EnumObjectType getCardModule(){
		return CARD_MODULE;
	}
	
	public static EnumObjectType getActivityModule(){
		return ACTIVITY_MODULE;
	}
	
	public static EnumObjectType getArticleModule(){
		return ARTICLE_MODULE;
	}
	
	public static EnumObjectType getKnowledgeModule(){
		return KNOWLEDGE_MODULE;
	}
	
	public static EnumObjectType getEvaluModule(){
		return EVALU_MODULE;
	}
	
	public static EnumObjectType getOfflineModule(){
		return OFFLINE_MODULE;
	}
	
	public static EnumObjectType getApplyPersonalModule(){
		return APPLY_PERSONAL_MODULE;
	}
	
	public static EnumObjectType getApplyOrgModule(){
		return APPLY_ORG_MODULE;
	}
	
	public static EnumObjectType getRecruitModule(){
		return RECRUIT_MODULE;
	}
	
	public static EnumObjectType getCommerceModule(){
		return COMMERCE_MODULE;
	}
	
	public static EnumObjectType getTeacherModule(){
		return TEACHER_MODULE;
	}
	public static EnumObjectType getVideoModule(){
		return VIDEO_MODULE;
	}
	public static EnumObjectType getScoreModule(){
		return SCORE_MODULE;
	}
	public static EnumObjectType getCouponModule(){
		return COUPON_MODULE;
	}
	public static EnumObjectType getWithdrawModule(){
		return WITHDRAW_MODULE;
	}
	public static EnumObjectType getSubAccountModule(){
		return SUB_ACCOUNT_MODULE;
	}
	public static EnumObjectType getCommpanyModule(){
		return COMPANY_MODULE;
	}

	public static  EnumObjectType ONLINE_MODULE = new EnumObjectType( 1, "线上课程"  );
	public static  EnumObjectType NEWS_MODULE = new EnumObjectType( 2, "新闻"  );
	public static  EnumObjectType CARD_MODULE = new EnumObjectType( 3, "VIP卡"  );
	public static  EnumObjectType TEACHER_MODULE = new EnumObjectType( 4, "讲师"  );
	public static  EnumObjectType ADS_MODULE = new EnumObjectType( 7, "广告模型 "  );
	public static  EnumObjectType ARTICLE_MODULE = new EnumObjectType( 8, "文库"  );
	public static  EnumObjectType ACTIVITY_MODULE = new EnumObjectType( 9, "线下活动"  );
	public static  EnumObjectType OFFLINE_MODULE = new EnumObjectType( 10, "线下课程"  );
	public static  EnumObjectType KNOWLEDGE_MODULE = new EnumObjectType( 11, "知识提问"  );
	public static  EnumObjectType ANSWER_MODULE = new EnumObjectType( 12, "知识回答"  );
	public static  EnumObjectType EVALU_MODULE = new EnumObjectType( 13, "评价体系"  );
	
	public static  EnumObjectType APPLY_PERSONAL_MODULE = new EnumObjectType( 14, "申请个人认证"  );
	public static  EnumObjectType APPLY_ORG_MODULE = new EnumObjectType( 15, "申请机构认证"  );
	
	public static  EnumObjectType RECRUIT_MODULE = new EnumObjectType( 16, "招聘"  );
	public static  EnumObjectType COMMERCE_MODULE = new EnumObjectType( 17, "商业服务"  );
	public static  EnumObjectType VIDEO_MODULE = new EnumObjectType( 18, "在线直播"  );
	
	public static  EnumObjectType COUPON_MODULE = new EnumObjectType( 19, "优惠券"  );
	public static  EnumObjectType SCORE_MODULE = new EnumObjectType( 20, "积分"  );
	public static  EnumObjectType WITHDRAW_MODULE = new EnumObjectType(21,"提现");
	
	public static  EnumObjectType SUB_ACCOUNT_MODULE = new EnumObjectType(22,"子账号");
	
	public static  EnumObjectType COMPANY_MODULE = new EnumObjectType(23,"企业用户");
	
	public static  EnumObjectType INFO_MODULE = new EnumObjectType(24,"金融资讯");
	
	public static  EnumObjectType ENTERPRISE_COURSE_MODULE = new EnumObjectType(25,"企业课程");
	

	public static  EnumObjectType NOTIFY_MODULE = new EnumObjectType(26,"系统通知");
	

	public static  EnumObjectType FORUM_MODULE = new EnumObjectType( 27, "论坛"  );
	
	public static  EnumObjectType BOOK_MODULE = new EnumObjectType(28,"行业书籍");
	
	public static  EnumObjectType SVIDEO_MODULE = new EnumObjectType(29,"短视频");
	

	
	public static  EnumObjectType XILIE_MOKUAI_MODULE = new EnumObjectType(30,"系列课模块");
	public static  EnumObjectType XILIE_ZHANGJIE_MODULE = new EnumObjectType(31,"系列课章节");
	public static  EnumObjectType XILIE_SHIPIN_MODULE = new EnumObjectType(32,"系列课视频");
	

	
	public static EnumObjectType getXilieMokuaiModule(){
		return XILIE_MOKUAI_MODULE;
	}
	
	
	public static EnumObjectType getXilieZhangjieModule(){
		return XILIE_ZHANGJIE_MODULE;
	}
	
	
	public static EnumObjectType getXilieShipinModule(){
		return XILIE_SHIPIN_MODULE;
	}
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		EnumObjectType.serialVersionUID = serialVersionUID;
	}
	
	
	
	
	
	
	public static ArrayList<EnumObjectType> getLIST() {
		return LIST;
	}
	public static void setLIST(ArrayList<EnumObjectType> lIST) {
		LIST = lIST;
	}
	public static Map<Integer, EnumObjectType> getINSTANCES() {
		return INSTANCES;
	}
	public static void setINSTANCES(Map<Integer, EnumObjectType> iNSTANCES) {
		INSTANCES = iNSTANCES;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}



	private static  ArrayList<EnumObjectType> LIST = new ArrayList<EnumObjectType>(Arrays.asList(
			ONLINE_MODULE,
			ARTICLE_MODULE,
			OFFLINE_MODULE,
			NEWS_MODULE,
			ADS_MODULE,
			VIDEO_MODULE,
			TEACHER_MODULE,
			INFO_MODULE,
			NOTIFY_MODULE
			
		));
	
	public List getApplyList(){
		 return new ArrayList<EnumObjectType>(Arrays.asList(
				ACTIVITY_MODULE,
				OFFLINE_MODULE					
			)); 
	}
	
	public static List<Integer> getCourseList(){
		 return new  ArrayList<Integer>(Arrays.asList(
					ONLINE_MODULE.code,
					ACTIVITY_MODULE.code,
					OFFLINE_MODULE.code,
					ARTICLE_MODULE.code,
					RECRUIT_MODULE.code
				)); 
	}
	public static List<EnumObjectType> getCourseListAll(){
		 return new  ArrayList<EnumObjectType>(Arrays.asList(
					ONLINE_MODULE,
					ACTIVITY_MODULE,
					OFFLINE_MODULE,
					ARTICLE_MODULE,
					RECRUIT_MODULE
				)); 
	}
	
	
	public static List<Integer> getApplyCourseList(){
		 return new  ArrayList<Integer>(Arrays.asList(
					ACTIVITY_MODULE.code,
					OFFLINE_MODULE.code,
					VIDEO_MODULE.code
				)); 
	}
	
	public static List<EnumObjectType> getPresentList(){
		 return new  ArrayList<EnumObjectType>(Arrays.asList(
				 COUPON_MODULE,
				 SCORE_MODULE,
				 ONLINE_MODULE,
				 ARTICLE_MODULE
				)); 
	}
	
	
	/**
	 * 讲师分成的佣金比例
	 * @return
	 */
	public static List<EnumObjectType> getTeacherRateList(){
		 return new  ArrayList<EnumObjectType>(Arrays.asList(
				 ONLINE_MODULE,
				 OFFLINE_MODULE,
				 VIDEO_MODULE
				)); 
	}
	
	
	public static List<EnumObjectType> getPartnerRateList(){
		 return new  ArrayList<EnumObjectType>(Arrays.asList(
				 ONLINE_MODULE,
				 OFFLINE_MODULE,
				 VIDEO_MODULE,
				 SUB_ACCOUNT_MODULE
				)); 
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	


	public static EnumObjectType parse(Integer code) {
		return ( EnumObjectType ) INSTANCES.get( code );
	}
	


	private static  Map<Integer,EnumObjectType> INSTANCES = 
			new HashMap<Integer,EnumObjectType> (){
		{
			put(ONLINE_MODULE.getCode(), ONLINE_MODULE);
			put(OFFLINE_MODULE.getCode(), OFFLINE_MODULE);
			put(NEWS_MODULE.getCode(), NEWS_MODULE);
			put(CARD_MODULE.getCode(),CARD_MODULE);
			put(TEACHER_MODULE.getCode(), TEACHER_MODULE);
			put(ADS_MODULE.getCode(), ADS_MODULE);
			put(ARTICLE_MODULE.getCode(), ARTICLE_MODULE);
			put(ACTIVITY_MODULE.getCode(), ACTIVITY_MODULE);
			put(VIDEO_MODULE.getCode(), VIDEO_MODULE);
			put(COUPON_MODULE.getCode(), COUPON_MODULE);
			put(SCORE_MODULE.getCode(), SCORE_MODULE);
			put(ENTERPRISE_COURSE_MODULE.getCode(), ENTERPRISE_COURSE_MODULE);
		}
	};
	
	
	
	
	
	
	
}

