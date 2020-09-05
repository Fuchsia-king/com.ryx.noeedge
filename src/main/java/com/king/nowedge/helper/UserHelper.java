
package com.king.nowedge.helper;

import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.ContactDTO;
import com.king.nowedge.dto.ResumeDTO;
import com.king.nowedge.dto.WorkExperienceDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.AddressQuery;
import com.king.nowedge.dto.query.ContactQuery;
import com.king.nowedge.dto.query.WorkExperienceQuery;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;


@Component
public class UserHelper  extends BaseHelper {
	
	private static  UserHelper userHelper ;  
	
	
	

	@PostConstruct  
    public void init() {  		
		userHelper = this;
    }  

	public EnumUserLevel getUserLevel(Long userId){
		RyxUsersDTO usersDTO = getUserById(userId);
		if(isCompanyUser(usersDTO)){
			return EnumUserLevel.COMPANY_USER;
		}
		else if (isPersonalUser(usersDTO)){
			return EnumUserLevel.COMMON_USER;
		}
		else if (isOrgTeacher(usersDTO)){
			return EnumUserLevel.ORG_TEACHER;
		}
		else if (isPersonalTeacher(usersDTO)){
			return EnumUserLevel.PERSONAL_TEACHER;
		}
		else if (isPartner(usersDTO)){
			return EnumUserLevel.PARTNER_USER;
		}
		else {
			return EnumUserLevel.COMMON_USER;
		}
	}
	
	
	/**
	 * 我的粉丝数量
	 * @param userId
	 * @return
	 */
	public Integer getMyUsersCount(Long userId){

		RyxUsersQuery ryxUsersQuery = new RyxUsersQuery();
		ryxUsersQuery.setSid(userId);
		return userHelper.ryxUserService.countQueryUser(ryxUsersQuery).getModule();
		
	}
	
	/**
	 * 今日佣金收入
	 * @param userId
	 * @return
	 * @throws ParseException 
	 */
	public Double getTodayCommission(Long userId) throws ParseException{

		RyxUserCouponQuery query = new RyxUserCouponQuery();
		query.setUserId(userId);
		query.setInorout(1);
		query.setTstart(DateHelper.getTodayLongSecond());
		query.setTend(DateHelper.getTomorrowLongSecond());
		query.setType(EnumAccountType.COMMISSION.getCode());
		return userHelper.ryxUserService.sumUserCoupon(query).getModule();
		
	}
	
	public Integer getSpreadOrderCount(Long userId) throws ParseException{

		RyxPartnerOrderQuery query = new RyxPartnerOrderQuery();
		query.setUserId(userId);
		return userHelper.ryxOrderService.countQueryPartnerOrder(query).getModule();
		
	}
	
	public List<RyxAdminDTO> getAdmin() {

		String key = "_getadmin_" ;
		//logger.error("getCityByCode--->" + code);
		Ehcache ehcache = getCache("cacheMetadata");
		Element elem = ehcache.get(key);
		if (elem == null) {
			RyxAdminQuery query = new RyxAdminQuery();
			query.setIdeleted(0);
			ehcache.put(elem = new Element(key, userHelper.ryxUserService.queryAdmin(query).getModule().getList()));
		}
		//logger.error("getCityByCode--->" + elem.getObjectValue());
		return (List<RyxAdminDTO>) elem.getObjectValue();

	}
	
	
	
	public RyxAdminDTO getAdminByUserId(Long userId) {

		String key = "_getadminbuserid_" + userId ;
		//logger.error("getCityByCode--->" + code);
		Ehcache ehcache = getCache("cacheMetadata");
		Element elem = ehcache.get(key);
		if (elem == null) {
			ehcache.put(elem = new Element(key, userHelper.ryxUserService.getAdminByUserId(userId).getModule()));
		}
		//logger.error("getCityByCode--->" + elem.getObjectValue());
		return (RyxAdminDTO) elem.getObjectValue();

	}
	
	
	
	public List<RyxTeacherDTO> queryTeacher(Integer pageSize){		
		String key = "_qt_"+ pageSize +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxTeacherQuery query = new RyxTeacherQuery();

			query.setFlag(0);
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setOrderBy("sort");			
			query.setPageSize(pageSize);
			
			ResultDTO<RyxTeacherQuery> result = userHelper.ryxTeacherService.queryTeacher1(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}		
		return (List<RyxTeacherDTO>) elem.getObjectValue();
	}
	
	
	public Integer getMyInviteCount(Long sid){		
		String key = "_gmic_"+ sid +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxUsersQuery query = new RyxUsersQuery();
			query.setSid(sid);			
			ResultDTO<Integer> result = userHelper.ryxUserService.countQueryUser(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}		
		return (Integer) elem.getObjectValue();
	}
	
	
	public List<RyxUserEcourseDTO> getUserEcourseByUserId(Long userId){		
		String key = "_guebu_"+ userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxUserEcourseQuery query = new RyxUserEcourseQuery();
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxUserEcourseQuery> result = userHelper.ryxCourseService.queryEcourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}		
		return ( List<RyxUserEcourseDTO>) elem.getObjectValue();
	}
	
	public Boolean isFollowTeacher(Long userId,Long tid){
		ResultDTO<List<RyxUserFollowTeacherDTO>> resultDTO 
		= userHelper.ryxTeacherService.getUserFollowTeacherByTeacherIdAndUserId(tid, userId);
		if(resultDTO.isSuccess() && resultDTO.getModule().size() >0){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public ResultDTO<RyxUsersQuery> queryUserCache(RyxUsersQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			if(StringHelper.isNullOrEmpty(query.getOrderBy())){
				query.setOrderBy("update_time");
				query.setSooort("desc");
			}
			ResultDTO<RyxUsersQuery> result = userHelper.ryxUserService.queryUser1(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxUsersQuery>) elem.getObjectValue();
	}
	
	
	
	public Boolean isCompanyUser(RyxUsersDTO usersDTO){		
		
		if(null == usersDTO)
			return false;
		
		return (usersDTO.getFlag() == EnumUserLevel.COMPANY_USER.getCode() && usersDTO.getStatus() == EnumAuditStatus.APPROVED.getCode())
				&& !isTeacher(usersDTO ) && !isPartner(usersDTO);
	}
	
	public Boolean isPersonalUser(RyxUsersDTO usersDTO){	
		
		if(null == usersDTO)
			return false;
		
		return 
				(usersDTO.getFlag() != EnumUserLevel.COMPANY_USER.getCode() || 
				(usersDTO.getFlag() == EnumUserLevel.COMPANY_USER.getCode() && usersDTO.getStatus() != EnumAuditStatus.APPROVED.getCode()))
				&& !isTeacher(usersDTO) && !isPartner(usersDTO);
	}
	
	public Boolean isOrgTeacher(RyxUsersDTO usersDTO){
		
		if(null == usersDTO)
			return false;
		
		RyxTeacherDTO teacherDTO = getTeacherByUserId(usersDTO.getId());
		if(null == teacherDTO){
			return false;
		}
		return (teacherDTO.getFlag() == EnumTeacherType.ORG.getCode() 
				&& EnumAuditStatus.getApproved().getCode() == teacherDTO.getStatus());
	}
	
	public Boolean isPersonalTeacher(RyxUsersDTO usersDTO){	
		
		if(null == usersDTO)
			return false;
		
		RyxTeacherDTO teacherDTO = getTeacherByUserId(usersDTO.getId());
		if(null == teacherDTO){
			return false;
		}
		return (teacherDTO.getFlag() == EnumTeacherType.PERSONAL.getCode() 
				&& EnumAuditStatus.getApproved().getCode() == teacherDTO.getStatus());
	}
	
	
	public Boolean isPartner(RyxUsersDTO usersDTO){
		
		if(null == usersDTO)
			return false;
		
		if(null == usersDTO || null == usersDTO.getId()){
			return false;
		}
		RyxPartnerQuery query = new RyxPartnerQuery();
		query.setUserId(usersDTO.getId());
		query.setIdeleted(0);
		Integer cnt = userHelper.ryxUserService.countQueryPartner(query).getModule();
		return cnt > 0 ;
	}
	
	
	public Boolean isTeacher(RyxUsersDTO usersDTO){
		
		if(null == usersDTO)
			return false;
		
		return (isPersonalTeacher(usersDTO) || isOrgTeacher(usersDTO));
	}
	
	public Boolean isUser(RyxUsersDTO usersDTO){
		
		if(null == usersDTO)
			return false;
		
		return (isCompanyUser(usersDTO) || isPersonalUser(usersDTO)) && !isTeacher(usersDTO) && !isPartner(usersDTO);
	}
	
	
	
	public Boolean isSubAccount(RyxUsersDTO usersDTO){
		
		if(null == usersDTO)
			return false;
		
		
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		
		keyrvQuery.setRkey(usersDTO.getId().toString());
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		ResultDTO<Integer> resultDTO = userHelper.systemService.countQueryKeyrv(keyrvQuery);
		if(resultDTO.isSuccess() && resultDTO.getModule() >0 ){
			return true;
		}
		
		return false;
	
	}
	
	
	public static UserHelper getInstance() {
		return userHelper;
	}
	
	
	
	
	public Double getUserBalanceById(Long id){
		if(null == id || id.equals(0L)){
			return 0.0;
		}
		return userHelper.ryxUserService.getUserBalanceById(id).getModule();
	}
	
	
	public Double getUserScoreById(Long id){
		return userHelper.ryxUserService.getUserScoreById(id).getModule();
	}
	
	
	public Double getUserCouponById(Long id){
		return userHelper.ryxUserService.getUserCouponById(id).getModule();
	}


	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getCartCountByUserId(Long userId){
		return userHelper.ryxOrderService.countQueryCart(userId).getModule();
	}

	public Boolean getIsCloseLeftAd( HttpServletRequest request){
		if(null == request)
			return true;
		return "1".equals(CookieHelper.getCookies(SessionHelper.IS_CLOSE_LEFT_COOKIE, request, "/"));
	} 
	

	
	public List<WorkExperienceDTO> getWorkExperByUserId(Long userId){
		
		String key = "_ugweby_"+ userId +"_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			WorkExperienceQuery query = new WorkExperienceQuery();
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("lstart");
			query.setSooort("desc");
			ResultDTO<List<WorkExperienceDTO>> result = userHelper.userService.queryWorkExperience(query);
			ehcache.put(elem = new Element(key,(result.isSuccess()) ? result.getModule() : null));
		}		
		return (List<WorkExperienceDTO>) elem.getObjectValue();		
	}
	
	
	public RyxApplyDTO getLatestCourseApplyByUserId(Long userId) {
		String key = "_uglabutcaby_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxApplyQuery query = new RyxApplyQuery();
			query.setUserId(userId);
			query.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setPageSize(1);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxApplyQuery> result = userHelper.ryxUserService.queryApply(query);
			List list = query.getList();
			ehcache.put(elem = new Element(key,(result.isSuccess() && null!=list && list.size() == 1) ?list.get(0):null));
		}		
		return (RyxApplyDTO) elem.getObjectValue();					
	}
	
	
	public Integer getOrgCourseCount(Integer otype,Long userId) {
		String key = "_occ_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		RyxObjectLimitQuery query = new RyxObjectLimitQuery();
		query.setUserId(userId);
		query.setOtype(otype);
		query.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
		ResultDTO<Integer> result = userHelper.ryxCourseService.countQueryObjectLimit(query);		
		return result.getModule();
							
	}
	
	
	public List<KeyrvDTO> getSubAccountByUserId(Long userId) {
	
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<KeyrvQuery> result = userHelper.systemService.queryKeyrv(query);		
		return result.getModule().getList();					
	}
	
	
	public Double getTeacherIncome(Long userId) {
		
		RyxOrderDetailQuery query = new RyxOrderDetailQuery();
		query.setObjer(userId);		
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOamount(1);
		
		return userHelper.ryxOrderService.sumTeacherOamount(query).getModule();
	}
	
	
	public Integer getSubAccountCountByUserId(Long userId) {
		
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		ResultDTO<Integer> result = userHelper.systemService.countQueryKeyrv(query);		
		return result.getModule();					
	}
	
	
	public List<KeyrvDTO> getSubAccountByUserId(Long userId,Integer currentPage) {
		
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		query.setPageSize(10);
		query.setCurrentPage(currentPage);
		ResultDTO<KeyrvQuery> result = userHelper.systemService.queryKeyrv(query);		
		return result.getModule().getList();					
	}
	
    public List<KeyrvDTO> getSubAccountByUserId(Long userId,Integer currentPage,Integer pageSize) {
		
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		query.setPageSize(pageSize);
		query.setCurrentPage(currentPage);
		ResultDTO<KeyrvQuery> result = userHelper.systemService.queryKeyrv(query);		
		return result.getModule().getList();					
	}
	
	
	public Long getMainUserBySubUserId(Long userId) {
		
		KeyrvQuery query = new KeyrvQuery();
		query.setRkey(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		query.setPageSize(1);
		ResultDTO<KeyrvQuery> result = userHelper.systemService.queryKeyrv(query);		
		List<KeyrvDTO> list = result.getModule().getList();	
		if(null != list && list.size() > 0){
			return Long.parseLong(list.get(0).getKey1());
		}
		
		return null; 
	}
	
	public RyxApplyDTO getLatestTeacherApplyByUserId(Long userId) {
		String key = "_uglabutpbu_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxApplyQuery query = new RyxApplyQuery();
			query.setUserId(userId);
			query.setOtype(EnumObjectType.TEACHER_MODULE.getCode());
			query.setPageSize(1);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxApplyQuery> result = userHelper.ryxUserService.queryApply(query);
			List list = query.getList();
			ehcache.put(elem = new Element(key,(result.isSuccess() && null!=list && list.size() == 1) ?list.get(0):null));
		}		
		return (RyxApplyDTO) elem.getObjectValue();					
	}
	
	
	public List<RyxTeacherDTO> getHotOrg(Integer nbr) {
		String key = "_gho_"+ nbr + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setFlag(EnumTeacherType.ORG.getCode());
			query.setPageSize(nbr);
			ResultDTO<RyxTeacherQuery> result = userHelper.ryxTeacherService.queryTeacher(query);
			//query = result.getModule();
			List list = query.getList();
			ehcache.put(elem = new Element(key,(result.isSuccess()) ? list :null));
		}		
		return (List<RyxTeacherDTO>) elem.getObjectValue();					
	}
	
	public AddressDTO getAddressById(Long id) {
		String key = "_ugabi_"+ id + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<AddressDTO> result = userHelper.userService.queryAddressById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}		
		return (AddressDTO) elem.getObjectValue();					
	}
	
	
	public String getFullIndustry(RyxTeacherDTO dto){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		if(null == dto){
			return "";
		}
		if(null != dto.getIndustry0()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById( dto.getIndustry0());
			if(null != keyvalueDTO){
				findustry += keyvalueDTO.getKey1();
			}
		}
		
		if(null !=  dto.getIndustry1()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById( dto.getIndustry1());
			if(null != keyvalueDTO){
				findustry += " - " + keyvalueDTO.getKey1();
			}
		}
		
		if(null !=  dto.getIndustry2()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById( dto.getIndustry2());
			if(null != keyvalueDTO){
				findustry += " - " + keyvalueDTO.getKey1();
			}
		}
		
		
		return findustry;
		
	}
	
	public String getFullIndustry(Long industry0,Long industry1,Long industry2){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		
		if(null != industry0){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(industry0);
			if(null != keyvalueDTO){
				findustry += keyvalueDTO.getKey1();
			}
		}
		
		if(null != industry1){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(industry1);
			if(null != keyvalueDTO){
				findustry += " - " + keyvalueDTO.getKey1();
			}
		}
		
		if(null != industry2){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(industry2);
			if(null != keyvalueDTO){
				findustry += " - " + keyvalueDTO.getKey1();
			}
		}
		
		
		return findustry;
		
	}
	

	public String getFullIndustry1(ResumeDTO dto){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		if(null != dto){
			if(null != dto.getIndustry0()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getIndustry0());
				if(null != keyvalueDTO){
					findustry += keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getIndustry1()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getIndustry1());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getIndustry2()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getIndustry2());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
		}
		
		return findustry;
		
	}
	
	
	public String getFullPosition(Long p0,Long p1,Long p2){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		if(null != p0 && 0 != p0.intValue()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(p0);
			if(null != keyvalueDTO){
				findustry += keyvalueDTO.getKey1();
			}
		}
		
		if(null != p1 && 0 != p1.intValue()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(p1);
			if(null != keyvalueDTO){
				findustry += "-" + keyvalueDTO.getKey1() ;
			}
		}
		
		if(null != p2 && 0 != p2.intValue()){
			keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(p2);
			if(null != keyvalueDTO){
				findustry += "-" + keyvalueDTO.getKey1();
			}
		}
		
		return findustry;
		
	}
	
	public String getFullPosition(RyxCourseDTO dto){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		if(null != dto){
			if(null != dto.getCategory()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getCategory().longValue());
				if(null != keyvalueDTO){
					findustry += keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getSubcate()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getSubcate().longValue());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getTcate()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getTcate().longValue());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
		}
		
		return findustry;
		
	}
	
	public String getFullPosition1(ResumeDTO dto){
		String findustry = "";
		KeyvalueDTO keyvalueDTO = null;

		if(null != dto){
			if(null != dto.getSpecialty0()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getSpecialty0().longValue());
				if(null != keyvalueDTO){
					findustry += keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getSpecialty1()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getSpecialty1().longValue());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
			
			if(null != dto.getSpecialty2()){
				keyvalueDTO = MetaHelper.getInstance().getKeyvalueById(dto.getSpecialty2().longValue());
				if(null != keyvalueDTO){
					findustry += " - " + keyvalueDTO.getKey1();
				}
			}
		}
		
		return findustry;
		
	}
	
	public RyxTeacherDTO getTeacherById(Long id) {		
		
		String key = "_ugtbyid_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			ResultDTO<RyxTeacherDTO> result = userHelper.ryxTeacherService.getTeacherById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (RyxTeacherDTO) elem.getObjectValue();
					
	}
	
	public RyxTeacherDTO getTeacherByUserId(Long userId) {			
		
		String key = "_ugtbyuid_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			ResultDTO<RyxTeacherDTO> result = userHelper.ryxTeacherService.getTeacherByUserId(userId);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (RyxTeacherDTO) elem.getObjectValue();
					
	}

	public String getUserIdEncString(Long userId) throws Exception{
		return StringHelper.aesEncrypt(userId.toString(), ConstHelper.USER_ID_DEC_ENC_KEY);
	}
	
	public RyxTeacherDTO getTeacherByUserId1(Long userId) {				
		return userHelper.ryxTeacherService.getTeacherByUserId(userId).getModule();					
	}

	public String getFullAddrById(Long id){
		AddressDTO addr = getAddressById(id);
		String faddr= "";
		if(null != addr ){
			KeyvalueDTO keyvalueDTO = null;
			if(!StringHelper.isNullOrEmpty(addr.getProvince())){
				keyvalueDTO = MetaHelper.getInstance().getProvinceByCode(addr.getProvince());
				if(null != keyvalueDTO){
					faddr+= keyvalueDTO.getValue();
				}
			}
			
			if(!StringHelper.isNullOrEmpty(addr.getCity())){
				keyvalueDTO = MetaHelper.getInstance().getCityByCode(addr.getCity());
				if(null != keyvalueDTO){
					faddr+= " - " + keyvalueDTO.getValue();
				}
			}
			
			
			if(!StringHelper.isNullOrEmpty(addr.getArea())){
				keyvalueDTO = MetaHelper.getInstance().getAreaByCode(addr.getArea());
				if(null != keyvalueDTO){
					faddr+= " - " + keyvalueDTO.getValue();
				}
			}
			
			if(!StringHelper.isNullOrEmpty(addr.getAddress())){
				faddr+= " - " + addr.getAddress();			
			}
			
		}
		return faddr;
	}
	
	
	public String getProvinceAddrById(Long id){
		AddressDTO addr = getAddressById(id);
		String faddr= "";
		if(null != addr ){
			KeyvalueDTO keyvalueDTO = null;
			if(!StringHelper.isNullOrEmpty(addr.getProvince())){
				keyvalueDTO = MetaHelper.getInstance().getProvinceByCode(addr.getProvince());
				if(null != keyvalueDTO){
					faddr+= keyvalueDTO.getValue();
				}
			}
		}
		return faddr;
	}
	
	
	public String getCityAddrById(Long id){
		AddressDTO addr = getAddressById(id);
		String faddr= "";
		if(null != addr ){
			KeyvalueDTO keyvalueDTO = null;
			if(!StringHelper.isNullOrEmpty(addr.getProvince())){
				keyvalueDTO = MetaHelper.getInstance().getProvinceByCode(addr.getProvince());
				if(null != keyvalueDTO){
					faddr+= keyvalueDTO.getValue();
				}
			}
			
			if(!StringHelper.isNullOrEmpty(addr.getCity())){
				keyvalueDTO = MetaHelper.getInstance().getCityByCode(addr.getCity());
				if(null != keyvalueDTO){
					faddr+= " - " + keyvalueDTO.getValue();
				}
			}
			
		}
		return faddr;
	}
	
	public String getAreaAddrById(Long id){
		AddressDTO addr = getAddressById(id);
		String faddr= "";
		if(null != addr ){
			KeyvalueDTO keyvalueDTO = null;
			if(!StringHelper.isNullOrEmpty(addr.getProvince())){
				keyvalueDTO = MetaHelper.getInstance().getProvinceByCode(addr.getProvince());
				if(null != keyvalueDTO){
					faddr+= keyvalueDTO.getValue();
				}
			}
			
			if(!StringHelper.isNullOrEmpty(addr.getCity())){
				keyvalueDTO = MetaHelper.getInstance().getCityByCode(addr.getCity());
				if(null != keyvalueDTO){
					faddr+= " - " + keyvalueDTO.getValue();
				}
			}
			
			
			if(!StringHelper.isNullOrEmpty(addr.getArea())){
				keyvalueDTO = MetaHelper.getInstance().getProvinceByCode(addr.getArea());
				if(null != keyvalueDTO){
					faddr+= " - " + keyvalueDTO.getValue();
				}
			}
		}
		return faddr;
	}
	
	
	
	public String getFullContactById(Long id){
		ContactDTO contact = getContactById(id);
		String faddr= "";
		if(null != contact ){
			if(!StringHelper.isNullOrEmpty(contact.getName())){
				faddr+= contact.getName();
			}
			
			if(!StringHelper.isNullOrEmpty(contact.getMobile())){
				faddr+= " - " + contact.getMobile();
			}
			
			if(!StringHelper.isNullOrEmpty(contact.getPhone())){
				faddr+= " - " + contact.getPhone();
			}
			
			if(!StringHelper.isNullOrEmpty(contact.getEmail())){
				faddr+= " - " + contact.getEmail();
			}
			
			
		}
		return faddr;
	}
	
	
	public RyxAuthDTO getAuthByUserId(Long userId) {
		String key = "_gabuid_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<RyxAuthDTO> result = userHelper.ryxUserService.getAuthByUid(userId);
			ehcache.put(elem = new Element(key,result.getModule()));
		}		
		return (RyxAuthDTO) elem.getObjectValue();					
	}
	
	public List<AddressDTO> getAddressByUserId(Long userId) {
		String key = "_ugabui_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			AddressQuery query = new AddressQuery();
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<AddressQuery> result = userHelper.userService.queryAddress(query);
			List list = query.getList();
			ehcache.put(elem = new Element(key,result.isSuccess() ? list : null));
		}		
		return (List<AddressDTO>) elem.getObjectValue();					
	}
	
	public ResumeDTO getResumeByUserId(Long userId) {
		String key = "_ugrbuid_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			AddressQuery query = new AddressQuery();
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<ResumeDTO> result = userHelper.userService.queryResumeByUserId(userId);
			List list = query.getList();
			ehcache.put(elem = new Element(key,result.getModule()));
		}		
		return (ResumeDTO) elem.getObjectValue();					
	}
	
	public List<ContactDTO> getContactByUserId(Long userId) {
		String key = "_ugcbui_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ContactQuery query = new ContactQuery();
			query.setUserId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<ContactQuery> result = userHelper.userService.queryContact(query);
			List list = query.getList();
			ehcache.put(elem = new Element(key,result.isSuccess() ? list : null));
		}		
		return (List<ContactDTO>) elem.getObjectValue();					
	}
	
	
	
	public ContactDTO getContactById(Long id) {
		String key = "_ucbi_"+ id + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<ContactDTO> result = userHelper.userService.queryContactById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}		
		return (ContactDTO) elem.getObjectValue();					
	}
	
	
	
	public RyxUsersDTO getUserById(Long userId) {
		
		if(null == userId){
			return null ;
		}
		
		String key = "_ugubi_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<RyxUsersDTO> result = userHelper.ryxUserService.getUserById(userId);
			ehcache.put(elem = new Element(key,(result.isSuccess() ) ? result.getModule() : null));
		}		
		return (RyxUsersDTO) elem.getObjectValue();					
	}
	
	
	public RyxUsersDTO getUserById1(Long userId) {
		
		if(null == userId){
			return null ;
		}
			
		return userHelper.ryxUserService.getUserById(userId).getModule();		

	}
	
	
	public RyxUserExtDTO getUserExtByUserId(Long userId) {
		return userHelper.ryxUserService.getUserExtById(userId).getModule();
	}
	
	
	public String getSpreadUserString(Long userId) throws UnsupportedEncodingException, Exception {
		if(null == userId){
			return  "" ;
		}
		return URLEncoder.encode(StringHelper.aesEncrypt(userId.toString(),RequestHelper.PARNTER_URL_SEED ), "UTF-8");
	}
	
	
	
	
	
	/**
	 * 
	 * @param oid
	 * @param otype
	 * @return
	 */
	public Integer getApplyCourseCount(Long oid,Integer otype) {
		String key = "_ugac_"+ oid + "_"+ otype +"_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxApplyQuery query = new RyxApplyQuery();
			query.setOid(oid);;
			query.setOtype(otype);
			ResultDTO<Integer> result = userHelper.ryxUserService.countApplyNbr(query);
			ehcache.put(elem = new Element(key,(result.isSuccess() && null != result.getModule()  ) ? result.getModule() : 0));
		}		
		return (Integer) elem.getObjectValue();					
	}
	
	
	/**
	 * 
	 * @param otype
	 * @return
	 */
	public Integer getApplyCourseCount(Integer otype) {
		String key = "_ugac_"+ otype +"_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxApplyQuery query = new RyxApplyQuery();
			query.setOtype(otype);
			ResultDTO<Integer> result = userHelper.ryxUserService.countApplyNbr(query);
			ehcache.put(elem = new Element(key,(result.isSuccess() && null != result.getModule()  ) ? result.getModule() : 0));
		}		
		return (Integer) elem.getObjectValue();					
	}
	
	
	
	/**
	 * 获取嘉宾坐位信息
	 * @param otype
	 * @return
	 */
	public String getActivitySeat(String code,String name) {
		String key = "_getActivitySeat_"+ code +"_" + name;
		Ehcache ehcache = getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxActivitySeatQuery ryxActivitySeatQuery = new RyxActivitySeatQuery();
			ryxActivitySeatQuery.setCode(code);
			ryxActivitySeatQuery.setName(name);
			List<RyxActivitySeatDTO>  list = systemService.queryActivitySeat(ryxActivitySeatQuery).getModule() ;
			String seat = "";
			if(null != list && list.size() >0){
				seat=list.get(0).getSeat();
			}
			ehcache.put(elem = new Element(key,seat));
		}		
		return (String) elem.getObjectValue();					
	}
	
	
	
	
	
	/**
	 * 
	 * @param rkey
	 * @param type
	 * @return
	 */
	public Integer getApplyRecruitCount(Object rkey) {
		String key = "_ugacrcnt_"+ rkey + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvQuery query = new KeyrvQuery();
			query.setRkey(rkey.toString());
			query.setIdeleted(0);
			query.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
			ResultDTO<Integer> result = userHelper.systemService.countQueryKeyrv(query);
			ehcache.put(elem = new Element(key,(result.isSuccess() && null != result.getModule()) ? result.getModule() : 0));
		}		
		return (Integer) elem.getObjectValue();					
	}
	
	
	/**
	 * 获取子账号数量
	 * @param userId
	 * @return
	 */
	public Integer getTotalSubAccountCount(Long userId) {
	
		RyxObjectLimitQuery query = new RyxObjectLimitQuery();
		query.setUserId(userId);
		query.setOid(userId);
		query.setOtype(EnumObjectType.SUB_ACCOUNT_MODULE.getCode());
		ResultDTO<RyxObjectLimitDTO> result = userHelper.ryxCourseService.queryObjectLimitByOou(query);
		if(null != result.getModule()){
			return result.getModule().getLimi().intValue();
		}
		else{
			return ConstHelper.DEFAULT_SUB_ACCOUNT_NBR;
		}
	}
	
	public Integer getUsedSubAccountCount(Long userId) {
		
		KeyrvQuery query = new KeyrvQuery();
		query.setKey1(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		ResultDTO<Integer> result = userHelper.systemService.countQueryKeyrv(query);
		return result.getModule();
						
	}
	
	
	public Integer getKeyrvCount(Object rkey,Integer type) {
		String key = "_ugkeyrvcnt_"+ rkey + "_"+ type +"_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvQuery query = new KeyrvQuery();
			query.setRkey(rkey.toString());
			query.setIdeleted(0);
			query.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
			ResultDTO<Integer> result = userHelper.systemService.countQueryKeyrv(query);
			ehcache.put(elem = new Element(key,(result.isSuccess() && null != result.getModule()) ? result.getModule() : 0));
		}		
		return (Integer) elem.getObjectValue();					
	}
	
	
	/**
	 * 
	 * @param rkey
	 * @param type
	 * @return
	 */
	public Integer getApplyQuestionCount(Object rkey) {
		String key = "_ugaqccnt_"+ rkey + "_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyrvQuery query = new KeyrvQuery();
			query.setRkey(rkey.toString());
			query.setIdeleted(0);
			query.setType(EnumKeyRelatedValueType.KV_FOLLOW_QUESTION.getCode());
			ResultDTO<Integer> result = userHelper.systemService.countQueryKeyrv(query);
			ehcache.put(elem = new Element(key,(result.isSuccess() && null != result.getModule()) ? result.getModule() : 0));
		}		
		return (Integer) elem.getObjectValue();					
	}
	
	
	public Boolean isShidaiUser(RyxUsersDTO  user){
		
		if(null == user){
			return false; 
		}
		
		if( (isCompanyUser(user) || isSubAccount(user) ) && isThirdUser(user) ){
			return true; 
		}
		
		return false; 
		
	}
	
	public Boolean isThirdUser(RyxUsersDTO users){
		if(null == users){
			return false; 
		}
		
		RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
		ryxUserExtQuery.setId(users.getId());
		ResultDTO<Integer> result = userHelper.ryxUserService.countQueryUserExt(ryxUserExtQuery);
		
		if(null != result.getModule() && result.getModule()>0){
			return true ;
		}
		
		return false ;
		
		
	}
	
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	public static String getUsername(RyxUsersDTO user) {
		if (null == user) {
			return "******";
		}
		if (!StringHelper.isNullOrEmpty(user.getUsername())) {
			return user.getUsername().substring(0, 1) + "*****";
		} else if (!StringHelper.isNullOrEmpty(user.getMobile())) {
			return "*****" + user.getMobile().substring(7);
		} else if (!StringHelper.isNullOrEmpty(user.getEmail())) {
			return user.getEmail().substring(8, 4) + "*****";
		} else {
			return "r******";
		}
	}
	
	
	public List<RyxSearchStatisticsDTO> getHotSearchStatistics(Integer pageSize){
		
		String key = "_ghss_" + pageSize;
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxSearchStatisticsQuery query = new RyxSearchStatisticsQuery();
			query.setStartRow(1);
			query.setPageSize(pageSize);
			query.setSooort("desc");
			query.setOrderBy("cnt");
			ResultDTO<List<RyxSearchStatisticsDTO>> result = userHelper.userService.query(query);
			ehcache.put(elem = new Element(key,(result.isSuccess()) ? result.getModule() : null));
		}		
		return (List<RyxSearchStatisticsDTO>) elem.getObjectValue();
		
	}
	public List<RyxSearchStatisticsDTO> getRecentSearchStatistics(Integer pageSize){
		
		String key = "_grss_" + pageSize;
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxSearchStatisticsQuery query = new RyxSearchStatisticsQuery();
			query.setStartRow(1);
			query.setPageSize(pageSize);
			query.setSooort("desc");
			query.setOrderBy("lmodified");
			ResultDTO<List<RyxSearchStatisticsDTO>> result = userHelper.userService.query(query);
			ehcache.put(elem = new Element(key,(result.isSuccess()) ? result.getModule() : null));
		}		
		return (List<RyxSearchStatisticsDTO>) elem.getObjectValue();
		
	}


	public List<RyxPresentDTO> getPresent() {
		String key = "_grss_";
		Ehcache ehcache =  getCache("cacheRyxUser");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxPresentQuery query = new RyxPresentQuery();
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxPresentQuery> result = userHelper.ryxUserService.queryPresent(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}		
		return (List<RyxPresentDTO>) elem.getObjectValue();
	}
	public Double getProvinceById(Long id){
		return userHelper.ryxUserService.getUserScoreById(id).getModule();
	}



}
