package com.king.nowedge.helper;

import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class CourseHelper  extends BaseHelper {
	
	private static final Log logger = LogFactory.getLog(CourseHelper.class);
	
	private static  CourseHelper courseHelper ;  

	public static CourseHelper getInstance() {
		return courseHelper;
	}


	@PostConstruct  
    public void init() {  
		
		courseHelper = this;  
    	
    }  
	
	/**
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Boolean isBuyVideo(Long userId,Long courseId){
		
		if(null == userId || userId.equals(0L)){
			return false;
		}
		
		RyxApplyQuery query = new RyxApplyQuery();
		query.setUserId(userId);
		query.setPageSize(1);
		query.setOid(courseId);
		
		List<Integer> statuss =  new ArrayList<Integer>(Arrays.asList(
				EnumOrderStatus.FREE.getCode() ,
				//EnumOrderStatus.PRESENT.getCode(),
				EnumOrderStatus.PAY_SUCCESS.getCode()
				
			));
		query.setStatuss(statuss);
		ResultDTO<RyxApplyQuery> result = courseHelper.ryxUserService.queryApply(query);
		query = result.getModule();
		if(result.isSuccess() && null != query && query.getList().size()>0){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 是否必须按体系购买
	 * @param courseId
	 * @return
	 */
	public Boolean isMustSeriesBuy(Long courseId){
		String uid = StringHelper.getKeyvalueUid(courseId.toString(), 
				EnumKeyValueType.KV_COURSE_MUST_SERIES_BUY_FLAG.getCode());
		KeyvDTO keyvDTO = MetaHelper.getInstance().getKeyvByUid(uid);
		if(null == keyvDTO){
			return false;
		}
		else{
			return true;
		}
	}	
	
	
	/**
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Boolean isBuyOffline(Long userId,Long courseId){
		
		RyxApplyQuery query = new RyxApplyQuery();
		query.setUserId(userId);
		query.setPageSize(1);
		query.setOid(courseId);
		
		List<Integer> statuss =  new ArrayList<Integer>(Arrays.asList(
				EnumOrderStatus.FREE.getCode() ,
				//EnumOrderStatus.PRESENT.getCode(),
				EnumOrderStatus.PAY_SUCCESS.getCode()
				
			));
		query.setStatuss(statuss);
		ResultDTO<RyxApplyQuery> result = courseHelper.ryxUserService.queryApply(query);
		query = result.getModule();
		if(result.isSuccess() && null != query && query.getList().size()>0){
			return true;
		}
		
		return false;
	}
	
	
	
	public Integer countInfo(Integer flag,Integer category){
		
		RyxCourseQuery query = new RyxCourseQuery();
		query.setObjType(EnumObjectType.INFO_MODULE.getCode());
		query.setIdeleted(0);
		query.setDisplay(1);
		if(flag > 0 ){
			query.setFlag(flag);
		}
		query.setCategory(category);
		return courseHelper.ryxCourseService.countQueryCourse(query).getModule();
		
	}


	
	/**
	 * 
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Boolean isBuySubOnline(Long userId,Long courseId){
		
		if(null == userId || null == courseId ){
			return false;
		}
		
		RyxCourseDTO courseDTO = getCourseById(courseId);
		if(null == courseDTO){
			return false;
		}
		
		if(!(null != courseDTO.getPrice() && courseDTO.getPrice()>0)){
			return true;
		}
		
		Long tnow = System.currentTimeMillis() / 1000;
		ResultDTO<Integer> buyResult = courseHelper.ryxOrderService.getOrderCountByUserIdAndCourseId(userId, courseId, tnow);
		if(buyResult.isSuccess() && buyResult.getModule()>0){
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	public List<RyxObjectLimitDTO> getExpiredObjectByMoid(Long moid,Long userId){
		
		
		String key = "_geobm_"+ moid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setMoid(moid);
			query.setUserId(userId);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			query.setElimi(System.currentTimeMillis()/1000);
			ResultDTO<RyxObjectLimitQuery> result = courseHelper.ryxCourseService.queryObjectLimit(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));			
		}
		
		return (List<RyxObjectLimitDTO>) elem.getObjectValue();
	}
	
	
	
	public List<RyxObjectLimitDTO> getUnexpiredObjectByMoid(Long moid,Long userId){		
		
		String key = "_gueobm_"+ moid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setMoid(moid);
			query.setUserId(userId);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			query.setSlimi(System.currentTimeMillis()/1000);
			//query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			ResultDTO<RyxObjectLimitQuery> result = courseHelper.ryxCourseService.queryObjectLimit(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));			
		}
		
		return (List<RyxObjectLimitDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxObjectLimitDTO> getObjectByMoid(Long moid,Long userId){		
		
		String key = "_gobm_"+ moid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setMoid(moid);
			query.setUserId(userId);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxObjectLimitQuery> result = courseHelper.ryxCourseService.queryObjectLimit(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));			
		}
		
		return (List<RyxObjectLimitDTO>) elem.getObjectValue();
	}
	
	
	public Integer getObjectCountByMoid(Long moid,Long userId){		
		
		String key = "_gobcm_"+ moid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setMoid(moid);
			query.setUserId(userId);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryObjectLimit(query);
			ehcache.put(elem = new Element(key,result.getModule()));			
		}
		
		return (Integer) elem.getObjectValue();
	}

	
	public List<RyxUserCouponDTO> getUnexpiredCoupon(Long userId){		
		
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setSlimi(System.currentTimeMillis()/1000);
		query.setIuse(0);			
		query.setScoupon(1);
		ResultDTO<RyxUserCouponQuery> result = courseHelper.ryxUserService.queryCoupon(query);

	
		return result.getModule().getList();
	}
	
	
	public List<RyxUserCouponDTO> getMyCoupon(Long userId){		
		
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");	
		query.setPageSize(Integer.MAX_VALUE);
		query.setScoupon(1);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));			
		query.setScoupon(1);
		ResultDTO<RyxUserCouponQuery> result = courseHelper.ryxUserService.queryCoupon(query);

	
		return result.getModule().getList();
	}
	
	
	public Integer getUnexpiredCouponCount(Long userId){		
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setSlimi(System.currentTimeMillis()/1000);
		query.setIuse(0);
		query.setScoupon(1);
		ResultDTO<Integer> result = courseHelper.ryxUserService.countQueryCoupon(query);
		return result.getModule();
	}
	
	
	
	public List<RyxUserCouponDTO> getExpiredCoupon(Long userId){		
		
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setElimi(System.currentTimeMillis()/1000);
		query.setIuse(0);
		query.setScoupon(1);
		ResultDTO<RyxUserCouponQuery> result = courseHelper.ryxUserService.queryCoupon(query);
	
		return result.getModule().getList();
	}
	
	
	public Integer getExpiredCouponCount(Long userId){	
					
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setElimi(System.currentTimeMillis()/1000);
		query.setIuse(0);
		query.setScoupon(1);
		ResultDTO<Integer> result = courseHelper.ryxUserService.countQueryCoupon(query);
			
		return result.getModule();
			
	}
	

	
	public List<RyxUserCouponDTO> getUsedCoupon(Long userId){				
					
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setSlimi(System.currentTimeMillis()/1000);
		query.setIuse(1);
		query.setScoupon(1);
		ResultDTO<RyxUserCouponQuery> result = courseHelper.ryxUserService.queryCoupon(query);
		return result.getModule().getList();
	}
	
	
	public Integer getUsedCouponCount(Long userId){		
					
		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setOrderBy("limi");		
		query.setPageSize(Integer.MAX_VALUE);
		query.setTypes(new ArrayList<Integer>(Arrays.asList(
				EnumAccountType.COUPON.getCode(),
				EnumAccountType.EXPERIENCE_COUPON.getCode()
			)));
		query.setSlimi(System.currentTimeMillis()/1000);
		query.setIuse(1);
		query.setScoupon(1);
		ResultDTO<Integer> result = courseHelper.ryxUserService.countQueryCoupon(query);
		return result.getModule();		
		
	}

	
	public Boolean isBuyMainOnline(Long userId,Long courseId){
		
		if(null == userId || null == courseId ){
			return false;
		}
		
		
		
		RyxCourseDTO courseDTO = getCourseById(courseId);
		if(null == courseDTO){
			return false;
		}
		
		if(!(null != courseDTO.getPrice() && courseDTO.getPrice()>0)){
			return true;
		}
		
		/**
		 * 年卡判断
		 */
		RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
		objectLimitQuery.setUserId(userId);
		objectLimitQuery.setSlimi(System.currentTimeMillis()/1000);
		objectLimitQuery.setCategory(courseDTO.getCategory());
		objectLimitQuery.setOtype(EnumObjectType.CARD_MODULE.getCode());
		ResultDTO<Integer> cardNbrResultDTO = courseHelper.ryxCourseService.countQueryObjectLimit(objectLimitQuery);
		if(cardNbrResultDTO.getModule()>0){
			return true;
		}
		
		return false;
		
	}
	
	
	
	public Boolean isBuyFreeMainOnline(Long userId,Long courseId){
		
		if(null == userId || null == courseId ){
			return false;
		}
		
		
		
		RyxCourseDTO courseDTO = getCourseById(courseId);
		if(null == courseDTO){
			return false;
		}
		
		if(null == courseDTO.getPrice() || 0 >= courseDTO.getPrice()){
			return true ;
		}
		
		/**
		 * 年卡判断
		 */
		RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
		objectLimitQuery.setUserId(userId);
		objectLimitQuery.setSlimi(System.currentTimeMillis()/1000);
		//objectLimitQuery.setCategory(courseDTO.getCategory());
		objectLimitQuery.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
		objectLimitQuery.setMoid(courseId);
		ResultDTO<Integer> cardNbrResultDTO = courseHelper.ryxCourseService.countQueryObjectLimit(objectLimitQuery);
		if(cardNbrResultDTO.getModule()>0){
			return true;
		}
		
		return false;
		
	}
	
	
	
	public List<KeyvDTO> getRecommendCourse(Integer cnt){
		String key = "_grcmmc_"+ cnt +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			KeyvQuery query = new KeyvQuery();			
			query.setType(EnumKeyValueType.KV_RECOMMEND_COURSE.getCode());
			query.setPageSize(cnt);
			ResultDTO<KeyvQuery> result = courseHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<KeyvDTO>) elem.getObjectValue();
	}
	
	
	/**
	 * 获取直播状态
	 * @param video
	 * @return
	 */
	public EnumVideoStatus getVideoStatus(RyxCourseDTO video){
		
		if(video.getTend() < System.currentTimeMillis()/1000){
			if(StringHelper.isNullOrEmpty(video.getVid())){
				return EnumVideoStatus.AFTER_LIVING;
			}
			else{
				return EnumVideoStatus.PLAY_BACK;
			}
		}
		else if (video.getTstart()> System.currentTimeMillis()/1000){
			return EnumVideoStatus.BEFORE_LIVING;
		}
		else{
			return EnumVideoStatus.LIVING;
		}
	}
	
	public List<KeyvDTO> getFavourableCourse(Integer cnt){
		String key = "_gfavc_"+ cnt +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			KeyvQuery query = new KeyvQuery();			
			query.setType(EnumKeyValueType.KV_FAVROABLE_COURSE.getCode());
			query.setPageSize(cnt);
			ResultDTO<KeyvQuery> result = courseHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}		
		return (List<KeyvDTO>) elem.getObjectValue();
	}
	
	public Integer getKeyrvCount(String key1,Integer type){
	
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(key1);
		query.setType(type);
		return courseHelper.systemService.countQueryKeyrv(query).getModule();

	}
	
	public Integer getMyUnexpiredCourseCount(Long userId){
		
		String key = "gmucc_" + userId;
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			//courseQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			ehcache.put(elem = new Element(key, courseHelper.ryxCourseService.getMyUnexpiredCourseCount2(courseQuery).getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}
	
	public List<RyxObjectLimitDTO> getMyUnexpiredCourse(Long userId){
		RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
		ryxCourseQuery.setUserId(userId);
		ryxCourseQuery.setPageSize(Integer.MAX_VALUE);
		ryxCourseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		ryxCourseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		ryxCourseQuery.setTnow(System.currentTimeMillis() / 1000);
		return courseHelper.ryxCourseService.getMyUnexpiredCourse2(ryxCourseQuery).getModule().getList();
	}
	
	
	public List<RyxUserEcourseDTO> getUserEcourseByEcid(Long userId,Long ecid){
		
		String key = "guebe_" + userId + "_" + ecid;
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxUserEcourseQuery userEcourseQuery = new RyxUserEcourseQuery();
			userEcourseQuery.setUserId(userId);
			userEcourseQuery.setEcid(ecid);
			elem = new Element(key, courseHelper.ryxCourseService.queryEcourse(userEcourseQuery).getModule().getList());
			elem.setTimeToLive(120);
			ehcache.put(elem);
		}
		return (List<RyxUserEcourseDTO>) elem.getObjectValue();
		
	}
	

	public Integer getMyOfflineCourseCount(Long userId){
		RyxApplyQuery query = new RyxApplyQuery();
		query.setUserId(userId);
		query.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
		ResultDTO<Integer> result = courseHelper.ryxUserService.countQueryApply(query);
		return result.getModule();
	}
	
	public Integer getRecruitCountByTeacher(Long tid){
		String key = "_gccby_"+ tid +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery query = new RyxCourseQuery();			
			query.setTid(tid);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Integer getCourseCount(String keyword,Integer objType){
		String key = "_getcc_"+ keyword +"_" + objType +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery query = new RyxCourseQuery();	
			query.setKeyword(keyword);
			query.setObjType(objType);
			if(EnumObjectType.ONLINE_MODULE.getCode().equals(objType)){
				query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			}
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer getTeacherCount(String keyword){
		String key = "_getctc_"+ keyword  +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxTeacherQuery query = new RyxTeacherQuery();	
			query.setKeyword(keyword);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxTeacherService.countQueryTeacher1(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer getUnexpiredCourseCount(Integer category,Long userId){
		
		if(0 == category){
			category = null;
		}
				
		RyxCourseQuery courseQuery = new RyxCourseQuery();			
		courseQuery.setUserId(userId);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		courseQuery.setCategory(category);
		courseQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyUnexpiredCourseCount2(courseQuery);
		return result.getModule();
		
	}
	
	
	
	public Integer getPresentCourseCount(Integer category,Long userId){
		String key = "_pgucc_"+ category +"_" + userId;		
		Ehcache ehcache =  getCache("cacheMetadata");
		if(0==category){
			category = null;
		}
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();			
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setCategory(category);
			courseQuery.setTstatus(EnumOrderStatus.ORG_PRESENT.getCode());
			ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyUnexpiredCount2(courseQuery);
			elem = new Element(key,result.getModule());
			elem.setTimeToLive(120); // 120 秒钟
			ehcache.put(elem);
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Integer getPresentCourseCount(Long userId){
		String key = "_pgucc_" + userId;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();			
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setTstatus(EnumOrderStatus.ORG_PRESENT.getCode());
			ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyUnexpiredCount2(courseQuery);
			elem = new Element(key,result.getModule());
			elem.setTimeToLive(120); // 120 秒钟
			ehcache.put(elem);
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	
	public Integer getUnexpiredCourseCount(Long userId){
		String key = "_gucc_" + userId;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();			
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			courseQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyUnexpiredCourseCount2(courseQuery);
			elem = new Element(key,result.getModule());
			elem.setTimeToLive(120); // 120 秒钟
			ehcache.put(elem);
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Integer getExpiredCourseCount(Integer category,Long userId){
		String key = "_gecc_"+ category +"_" + userId;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();			
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			courseQuery.setCategory(category);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyExpiredCount2(courseQuery);
			elem = new Element(key,result.getModule());
			elem.setTimeToLive(120); // 120 秒钟
			ehcache.put(elem);
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer getExpiredCourseCount(Long userId){
		String key = "_gecc_" + userId;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();			
			courseQuery.setUserId(userId);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.getMyExpiredCount2(courseQuery);
			elem = new Element(key,result.getModule());
			elem.setTimeToLive(120); // 120 秒钟
			ehcache.put(elem);
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public RyxObjectLimitDTO getObjectLimitByUserMoid(Long userId,Long moid){			
			
		String key = "_geobum_"+ moid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setMoid(moid);
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			query.setSlimi(System.currentTimeMillis()/1000);
			ResultDTO<RyxObjectLimitQuery> result = courseHelper.ryxCourseService.queryObjectLimit(query);
			List<RyxObjectLimitDTO> list = result.getModule().getList();
			RyxObjectLimitDTO objectLimitDTO = null != list && list.size()>0 ? list.get(0) : null;
			ehcache.put(elem = new Element(key,objectLimitDTO));			
		}
		
		return (RyxObjectLimitDTO) elem.getObjectValue();
		
	}
	
	public Integer getDaysLeftByUserOid(Long userId,Long oid){			
		
		String key = "_gdlbuo_"+ oid +"_" + userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxObjectLimitQuery query = new RyxObjectLimitQuery();
			query.setOid(oid);
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			query.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			query.setSlimi(System.currentTimeMillis()/1000);
			ResultDTO<RyxObjectLimitQuery> result = courseHelper.ryxCourseService.queryObjectLimit(query);
			List<RyxObjectLimitDTO> list = result.getModule().getList();
			RyxObjectLimitDTO objectLimitDTO = null != list && list.size()>0 ? list.get(0) : null;
			
			Integer daysLeft = 0;
			if(null != objectLimitDTO && null != objectLimitDTO.getLimi()){
				daysLeft = StringHelper.daysLeft(objectLimitDTO.getLimi()).intValue();
			}
			elem = new Element(key,daysLeft);
			elem.setTimeToLive(120); // 缓存  5 分钟 。
			ehcache.put(elem);			
		}
		
		return (Integer) elem.getObjectValue();
		
	}
	
	
	
	
	public RyxCourseDTO getCourseById(Long id) {				
		
		String key = "_cgcbi_"+ id + "_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<RyxCourseDTO> result = courseHelper.ryxCourseService.getCourseById(id);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule():null));
		}
		
		return (RyxCourseDTO) elem.getObjectValue();
					
	}
	
	public RyxCourseDTO getCourseByVid(Object vid) {				
		
		String key = "_cgcbvi_"+ vid + "_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<List<RyxCourseDTO>> result = courseHelper.ryxCourseService.getCourseByVId(vid.toString());
			List<RyxCourseDTO> list = result.getModule();
			ehcache.put(elem = new Element(key,null == list || 0== list.size() ?null:list.get(0)));
		}
		
		return (RyxCourseDTO) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getSubCourseByUserCategory(Long userId,Integer category) {		
		
		if(userId.equals(0L)){
			userId = null;
		}
		
		RyxCourseQuery query = new RyxCourseQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setCuid(userId);
		//query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setIdeleted(0);
		query.setDisplay(1);
		//query.setCategory(category);
		query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		query.setFlag(EnumCourseType.SUB_COURSE.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
		List<RyxCourseDTO> subList = result.getModule().getList();
		
		/**
		 * 获取当前用户的主课程
		 */
		List<String> keys = new ArrayList<>();
		keys.add("0");
		query = new RyxCourseQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setCuid(userId);
		//query.setStatus(EnumAuditStatus.APPROVED.getCode());
		//query.setIdeleted(0);
		//query.setDisplay(1);
		//query.setCategory(category);
		query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		result = courseHelper.ryxCourseService.queryCourse(query);
		if(result.isSuccess()){
			List<RyxCourseDTO> list = result.getModule().getList();
			if(null != list){
				for(RyxCourseDTO courseDTO : list){
					keys.add(courseDTO.getId().toString());
				}
			}
		}
		
		
		/**
		 * 
		 */
		List<String> subIdList = new ArrayList<String>();
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvQuery.setKeys(keys);
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		List<KeyrvDTO> keyrvList = courseHelper.systemService.queryKeyrv(keyrvQuery).getModule().getList();
		if(null != keyrvList){
			for(KeyrvDTO keyrvDTO : keyrvList){
				subIdList.add(keyrvDTO.getRkey());
			}
		}
		
		
		
		List<RyxCourseDTO> returnList = new ArrayList<>();
		
		for(RyxCourseDTO courseDTO : subList){
			if(!subIdList.contains(courseDTO.getId().toString())){
				returnList.add(courseDTO);
			}
		}
		
		return returnList;
		
	}
	
	
	public List<RyxCourseDTO> getEnterpriseSubCourseByUserCategory(Long userId,Integer category) {		
		
		if(userId.equals(0L)){
			userId = null;
		}
		
		RyxCourseQuery query = new RyxCourseQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setCuid(userId);
		//query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setIdeleted(0);
		query.setDisplay(1);
		//query.setCategory(category);
		query.setObjType(EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode());
		query.setFlag(EnumCourseType.ENTERPRISE_SUB_COURSE.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
		List<RyxCourseDTO> subList = result.getModule().getList();
		
		/**
		 * 获取当前用户的主课程
		 */
		List<String> keys = new ArrayList<>();
		keys.add("0");
		query = new RyxCourseQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setCuid(userId);
		//query.setStatus(EnumAuditStatus.APPROVED.getCode());
		//query.setIdeleted(0);
		//query.setDisplay(1);
		//query.setCategory(category);
		query.setObjType(EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode());
		query.setFlag(EnumCourseType.ENTERPRISE_MAIN_COURSE.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		result = courseHelper.ryxCourseService.queryCourse(query);
		if(result.isSuccess()){
			List<RyxCourseDTO> list = result.getModule().getList();
			if(null != list){
				for(RyxCourseDTO courseDTO : list){
					keys.add(courseDTO.getId().toString());
				}
			}
		}
		
		
		/**
		 * 
		 */
		List<String> subIdList = new ArrayList<String>();
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvQuery.setKeys(keys);
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		List<KeyrvDTO> keyrvList = courseHelper.systemService.queryKeyrv(keyrvQuery).getModule().getList();
		if(null != keyrvList){
			for(KeyrvDTO keyrvDTO : keyrvList){
				subIdList.add(keyrvDTO.getRkey());
			}
		}
		
		
		
		List<RyxCourseDTO> returnList = new ArrayList<>();
		
		for(RyxCourseDTO courseDTO : subList){
			if(!subIdList.contains(courseDTO.getId().toString())){
				returnList.add(courseDTO);
			}
		}
		
		return returnList;
		
	}

	
	public List<RyxCourseDTO> getActivityByUserId(Long userId,Integer pageSize) {		
		
		String key = "_gabu_"+ userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(pageSize);
			query.setCuid(userId);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
		
	}
	
	
	public List<RyxCourseDTO> getOfflineByUserId(Long userId,Integer pageSize) {		
		
		String key = "_gabu_"+ userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(pageSize);
			query.setCuid(userId);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
		
	}
	
	
	

	/**
	 * 
	 * @param pageSize
	 * @return
	 */
	public List<RyxCourseDTO> queryOffline(Integer pageSize){
		
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setPageSize(pageSize);
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("desc");
		String key = "_qo_"+ pageSize +"_" ;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	/**
	 * 
	 * @param pageSize
	 * @return
	 */
	public List<RyxSmallVideDTO> queryVideo(Integer pageSize){
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		//courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setPageSize(pageSize);
		
		return MetaHelper.getInstance().queryMobileVideo(courseQuery);
		
	}
	
	public List<RyxSmallVideDTO> queryUnendVideo(Integer pageSize){
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtend(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		
		return MetaHelper.getInstance().queryMobileVideo(courseQuery);
		
	}
	
	
	
	public List<RyxSmallCourseDTO> queryUnendOffline(Integer pageSize){
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtstart(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		
		return MetaHelper.getInstance().queryMobileOffline(courseQuery);
		
	}
	
	public List<RyxSmallCourseDTO> queryUnendOffline(RyxCourseQuery courseQuery){
		
		courseQuery.setTtstart(System.currentTimeMillis()/1000);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		
		return MetaHelper.getInstance().queryMobileOffline(courseQuery);
		
	}
	
	public List<RyxSmallVideDTO> getEndVideo(Integer pageSize) {				
		
		if(null == pageSize || pageSize <=0 ){
			return null;
		}
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setEtend(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
		}
		
		return  MetaHelper.getInstance().queryMobileVideo(courseQuery);
					
	}

	/**
	 * 
	 * @param objType
	 * @param userId
	 * @return
	 */
	public Integer getCourseCountByTypeUser(Integer objType,Long userId){
		String key = "_gccbyu_"+ objType +"_"+ userId +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			if(EnumObjectType.ONLINE_MODULE.getCode() == objType){
				query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			}
			query.setCuid(userId);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule():null));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Long getMainCourseBySubCourse(Long cid){
		String key = "_gmcbsc_"+ cid ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			Long val = null ;
			
			RyxCourseDTO courseDTO = getCourseById(cid);
			if(null == courseDTO ){
				return null ;				
			}
			
			if(EnumCourseType.MAIN_COURSE.getCode() == courseDTO.getFlag()){
				return cid;
			}
			
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setRkey(cid.toString());
			query.setPageSize(1);
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			query = result.getModule();
			if(null != query){
				List<KeyrvDTO> list = query.getList();
				if(null != list && list.size()>0){
					val = Long.parseLong(list.get(0).getKey1());
				}
			}
			ehcache.put(elem = new Element(key,val));
		}
		
		return (Long) elem.getObjectValue();
	}
	
	
	public RyxCourseDTO getMainCourseBySubCourse2(Long cid){
		String key = "_gmcbsc2_"+ cid ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			
			RyxCourseDTO val = getCourseById(cid);
			if(null == val ){
				return null ;				
			}
			
			if(EnumCourseType.MAIN_COURSE.getCode() == val.getFlag()){
				return val;
			}
			
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setRkey(cid.toString());
			query.setPageSize(1);
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			query = result.getModule();
			if(null != query){
				List<KeyrvDTO> list = query.getList();
				if(null != list && list.size()>0){
					val = getCourseById(Long.parseLong(list.get(0).getKey1()));
				}
			}
			ehcache.put(elem = new Element(key,val));
		}
		
		return (RyxCourseDTO) elem.getObjectValue();
	}
	
	
	public KeyrvDTO getMainCourseBySubCourse1(Long cid){
		String key = "_gmcbsc1_"+ cid ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvDTO val = new KeyrvDTO();
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setRkey(cid.toString());
			query.setPageSize(1);
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			query = result.getModule();
			if(null != query){
				List<KeyrvDTO> list = query.getList();
				if(null != list && list.size()>0){
					val = list.get(0);
				}
			}
			ehcache.put(elem = new Element(key,val));
		}
		
		return (KeyrvDTO) elem.getObjectValue();
	}
	
	
	public Long getSubCourseByMainCourse(Long cid){
		String key = "_gscbmc_"+ cid ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			Long val = 0L;
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setKey1(cid.toString());
			query.setOrderBy("sort");
			query.setPageSize(1);
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			query = result.getModule();
			if(null != query){
				List<KeyrvDTO> list = query.getList();
				if(null != list && list.size()>0){
					val = Long.parseLong(list.get(0).getRkey());
				}
			}
			ehcache.put(elem = new Element(key,val));
		}
		
		return (Long) elem.getObjectValue();
	}
	
	
	public List<KeyrvDTO> getSubCoursesByMainCourse(Long cid){
		
		logger.error("getSubCoursesByMainCourse");
		
		String key = "_gscsbmc_"+ cid ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setKey1(cid.toString());
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);			
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<KeyrvDTO>) elem.getObjectValue();
	}
	
	
	public Integer getEcourseCount(Long userId){
		String key = "_gecct_"+ userId ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxUserEcourseQuery userEcourseQuery = new RyxUserEcourseQuery();
			ResultDTO<Integer> result = courseHelper.ryxCourseService.queryEcourseCount(userEcourseQuery);			
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getCourseByTypeCategory(Integer objType,Integer category,Integer pageSize){
		String key = "_ggcbtc_"+ objType +"_"+ category +"_"+ pageSize +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setCategory(category);
			query.setPageSize(pageSize);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	public List<RyxCourseDTO> getCourseByTypeCategoryNotfree(Integer objType,Integer category,Integer cnt){
		String key = "_ggcbtc_"+ objType +"_"+ category +"_"+ cnt +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setCategory(category);
			query.setPageSize(cnt);
			query.setSprice(0.001);
			query.setOrderBy("study_count");
			query.setSooort("desc");
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	public Integer getCourseCountByTypeCategory(Integer objType,Integer category){
		String key = "_ggccbtc_"+ objType +"_"+ category +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setCategory(category);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Integer getHitsCountByTypeCategory(Integer objType,Integer category){
		String key = "_ghcbtc_"+ objType +"_"+ category +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setCategory(category);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countCourseHits(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public Integer getHitsCountByCuid(Long cuid){
		String key = "_ghcbtu_"+ cuid +"_" ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(cuid);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countCourseHits(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getCourseByType(Integer objType,Integer cnt){
		String key = "_gcbt_"+ objType +"_"+ cnt +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setPageSize(cnt);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getBeforeLivingVideo(Integer cnt){
		String key = "_gblv_" + cnt +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			//query.setTtstart(System.currentTimeMillis()/1000);
			query.setPageSize(cnt);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setIdeleted(0);
			//query.setDisplay(1); 隐藏的也出现
			query.setOrderBy("tstart");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getOfflineVideo(Integer cnt){
		String key = "_goffllv_" + cnt +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			//query.setTtstart(System.currentTimeMillis()/1000);
			query.setPageSize(cnt);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setOrderBy("tstart");
			query.setUrl(1);
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	public List<RyxCourseDTO> getBeforeLivingOffline(Integer cnt){
		String key = "_gblo_" + cnt +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setTtstart(System.currentTimeMillis()/1000);
			query.setPageSize(cnt);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setOrderBy("tstart");
			query.setSooort("asc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	public Integer getCourseCountByTypeTid(Integer objType,Long tid){
		String key = "_gccbytid_"+ objType +"_"+ tid +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setObjType(objType);
			query.setTid(tid);			
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule():null));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getLatestOnlineByTuid(Long id,Integer nbr) {				
		
		String key = "_cglocbrt_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setCuid(id);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getLatestOnline(Integer nbr) {				
		
		String key = "_cglocbt_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	

	public List<RyxCourseDTO> getLatestInfo(Integer nbr) {				
		
		String key = "_cgleti_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.INFO_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestObject(Integer objType,Integer nbr) {				
		
		String key = "_cghstobj_"+ objType +"_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getNewestObject(Integer objType,Integer nbr) {				
		
		String key = "_gno_"+ objType +"_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getNewestObject(Integer objType,Integer nbr,Integer currentPage) {				
		
		String key = "_gno_"+ objType +"_"+ nbr +"_" + currentPage +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			query.setCurrentPage(currentPage);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getHotestObject(Integer objType,Long userId,Integer nbr) {				
		
		String key = "_cghstobj_"+ objType +"_"+ userId +"_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setCuid(userId);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestRecruit(Integer nbr) {				
		
		String key = "_cghstrcit_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestCommerce(Integer nbr) {				
		
		String key = "_cghstcmmer_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestOnlineByTuid(Long id,Integer nbr) {				
		
		String key = "_cghocbt_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getHotestOnline(Integer nbr) {				
		
		String key = "_cghocbt_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getHitestOnline(Integer nbr) {				
		
		String key = "_ghtol_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getLatestArticleByTuid(Long id,Integer nbr) {				
		
		String key = "_cglabt_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getLatestArticle(Integer nbr) {				
		
		String key = "_cglabt_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getDownestArticle(Integer nbr) {				
		
		String key = "_gda_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getLatestRecruit(Integer nbr) {				
		
		String key = "_cglr_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getLatestObject(Integer objType, Long userId, Integer nbr) {				
		
		String key = "_cglobj_"+ userId +"_"+ objType +"_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setCuid(userId);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getLatestObject(Integer objType, Integer nbr) {				
		
		String key = "_cglobj_"+ objType +"_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(objType);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public Integer getCourseSeriesCount(Long courseId){
		
		String key = "_gcsc_"+ courseId +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setKey1(courseId.toString());
			query.setIdeleted(0);
			query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.systemService.countQueryKeyrv(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
		
	}
	

	public List<KeyrvDTO> getCourseSeries(Long id){
		String key = "_gcs_"+ id ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			Long val = 0L;
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setKey1(id.toString());
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<KeyrvDTO>) elem.getObjectValue();
	}
	
	
	public KeyrvDTO getCourseSeriesFirst(Long id){
		String key = "_gcsf_"+ id ;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			Long val = 0L;
			KeyrvQuery query = new KeyrvQuery();
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setKey1(id.toString());
			query.setPageSize(1);
			query.setOrderBy("sort");
			ResultDTO<KeyrvQuery> result = courseHelper.systemService.queryKeyrv(query);
			query = result.getModule();
			ehcache.put(elem = new Element(key,result.isSuccess() && null != query.getList() && query.getList().size()>0 ?result.getModule().getList().get(0):null));
		}
		
		return (KeyrvDTO) elem.getObjectValue();
	}
	
	public List<RyxCourseDTO> getLatestCommerce(Integer nbr) {				
		
		String key = "_cglc_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getHotestArticleByTuid(Long id,Integer nbr) {				
		
		String key = "_cgharbt_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestArticleByTuid(Integer nbr) {				
		
		String key = "_cghartt_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getHotestActivityByTuid(Long id,Integer nbr) {				
		
		String key = "_cghabcat_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}

	
	public List<RyxCourseDTO> getHotestActivity(Integer nbr) {				
		
		String key = "_cghabt_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public Integer getTotalObjectCount(Integer objType){
		
		String key = "_gtocboby_"+ objType +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setObjType(objType);
			query.setDisplay(1);
			ResultDTO<Integer> result = courseHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}


	public List<RyxCourseDTO> getThisMonthOffline(Integer days) {				
		
		String key = "_gtmo_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			
			query.setTtstart(DateHelper.getTomorrowLongSecond());
			query.setEtstart(DateHelper.getTodayLongSecond(days));
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setOrderBy("tstart");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess() ? result.getModule().getList() : null));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getThisMonthActivity(Integer days) {				
		
		String key = "_gtma_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			
			query.setTtstart(DateHelper.getTomorrowLongSecond());
			query.setEtstart(DateHelper.getTodayLongSecond(days));
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setOrderBy("tstart");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess() ? result.getModule().getList() : null));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getLatestOffline(Integer nbr) {				
		
		String key = "_cglo_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	

	
	
	public List<RyxCourseDTO> getLatestActivity(Integer nbr) {				
		
		String key = "_cgla_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();					
	}

	
	public List<RyxCourseDTO> getLatestActivityByUserId(Long userId,Integer nbr) {				
		
		String key = "_cglabuid_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setCuid(userId);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();					
	}
	
	

	public List<RyxCourseDTO> getHotestOfflineByTuid(Long id,Integer nbr) {				
		
		String key = "_cghofbt_"+ id + "_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setPageSize(nbr);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.OFFLINE_COURSE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	public List<RyxCourseDTO> getHotestOffline(Integer nbr) {				
		
		String key = "_cghofb_"+ nbr +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.OFFLINE_COURSE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	public List<RyxCourseDTO> queryMostVisitVideo(Integer pageSize){
		
		String key = "_qvideoc_";	
		
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setPageSize(pageSize);		
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			courseQuery.setDisplay(1);
			courseQuery.setOrderBy("hits");
			courseQuery.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> queryRecentVideo(Integer pageSize){
		
		String key = "_qrvideoc_";	
		
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setPageSize(pageSize);		
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			courseQuery.setDisplay(1);
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
			courseQuery.setTtstart(System.currentTimeMillis()/1000);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	public List<RyxCourseDTO> getOnlinesByTuid(Long id) {				
		
		String key = "_cgobtuid_"+ id + "_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCuid(id);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
				
	public List<RyxCourseDTO> getCourseByTeacherCategory(Integer category,Integer nbr) {				
		
		String key = "_cgcbtcategory_"+ category + "_" + nbr;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setCategory(category);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getCourseByTeacherCategoryNotfree(Integer category ,Integer nbr) {				
		
		String key = "_cgcbtcategory_"+ category + "_" + nbr;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(nbr);
			query.setCategory(category);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			query.setSprice(0.0);
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getVideoByTeacher(Long cuid) {				
		
		String key = "_gvbt_"+ cuid +"_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setCuid(cuid);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			query.setOrderBy("study_count");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
	public List<RyxCourseDTO> getRelatedCourse(Long id) {				
		
		String key = "_grc_"+ id;
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = getCourseById(id).getRelatedCourse();
			List<RyxCourseDTO> tempList = null;
			if (!StringHelper.isNullOrEmpty(relatedCourse) ) {
				RyxCourseDTO rcourse = null;
				ResultDTO<List<RyxCourseDTO>> rlistResult;
				for (String vid : relatedCourse.split(",")) {
					rlistResult = MetaHelper.getInstance().getCourseByVId(vid);
					tempList = rlistResult.getModule();
					if (tempList != null && tempList.size() > 0) {
						rcourse = tempList.get(0);
						if (!list.contains(rcourse)) {
							list.add(rcourse);
						}
					}
				}
				
				
				rlistResult = MetaHelper.getInstance().getCourseByIds(relatedCourse);
				List<RyxCourseDTO> courseList = rlistResult.getModule();
				if(rlistResult.isSuccess() && null!=courseList && courseList.size()>0){
					list.addAll(courseList);
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		return (List<RyxCourseDTO>) elem.getObjectValue();
					
	}
	
	
public RyxCourseDTO getPreObject(Integer objType,Long id) {				
		
		String key = "_gno1_"+ objType + "_" + id + "_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(1);
			query.setNextId(id);
			query.setObjType(objType);
			query.setOrderBy("id");
			query.setSooort("asc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,!result.isSuccess() || result.getModule().getList().size()==0 ? null:result.getModule().getList().get(0)));
		}
		
		return (RyxCourseDTO) elem.getObjectValue();
					
	}
	
	
	public RyxCourseDTO getNextObject(Integer objType,Long id) {				
		
		String key = "_gpo1_"+ objType + "_" + id + "_";
		Ehcache ehcache =  getCache("cacheCourse");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());query.setIdeleted(0);query.setDisplay(1);
			query.setPageSize(1);
			query.setPreId(id);
			query.setObjType(objType);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = courseHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,!result.isSuccess() || result.getModule().getList().size()==0 ? null:result.getModule().getList().get(0)));
		}
		
		return (RyxCourseDTO) elem.getObjectValue();
					
	}


}
