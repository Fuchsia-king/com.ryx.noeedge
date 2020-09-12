package com.king.nowedge.helper;

import com.king.nowedge.dto.DeptDTO;
import com.king.nowedge.dto.RecruitmentDTO;
import com.king.nowedge.dto.base.*;
import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.comm.RoleDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.query.DeptQuery;
import com.king.nowedge.query.RecruitmentQuery;
import com.king.nowedge.query.RoleQuery;
import com.king.nowedge.query.base.CompanyQuery;
import com.king.nowedge.query.base.KeyrvQuery;
import com.king.nowedge.query.base.KeyvQuery;
import com.king.nowedge.query.base.KeyvalueQuery;
import com.king.nowedge.query.ryx.*;
import com.king.nowedge.wxpay.PayConstant;
import com.king.nowedge.wxpay.WeixinJsapiTicket;
import com.king.nowedge.wxpay.WeixinPay;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class MetaHelper extends BaseHelper {
	
    private static  MetaHelper metaHelper ;  

	public static MetaHelper getInstance() {
		return metaHelper;
	}


	@PostConstruct  
    public void init() {  
		
    	metaHelper = this;  
    	
    }  

	
	public List<RyxOrderDetailDTO> getOrderDetailByOrderId(Long orderId){
		
		String key = "_getOrderDetailByOrderId_"+ orderId +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			List<RyxOrderDetailDTO> list = metaHelper.ryxOrderService.getOrderDetailByOrderId(orderId).getModule();
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxOrderDetailDTO>) elem.getObjectValue();
		
	}
	
	
	public KeyrvDTO getActivityImage(String code, String rkey){
		
		String key = "_getActivityImage_"+ code +"_" + rkey;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyrvQuery keyrvQuery = new KeyrvQuery();
			keyrvQuery.setKey1(code);
			keyrvQuery.setRkey(rkey);
			KeyrvDTO keyrvDTO = (KeyrvDTO)metaHelper.systemService.queryKeyrv(keyrvQuery).getModule().getList().get(0);
			ehcache.put(elem = new Element(key,keyrvDTO));
		}
		
		return (KeyrvDTO) elem.getObjectValue();
		
	}
	
	public List<KeyvalueDTO> getSalary(){
		
		String key = "_gslary_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
		
	}
	
	
	
	
	public List<KeyvalueDTO> getEducationLevel(){
		
		
		String key = "_gedulev_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
		
	}
	
	
	public Map<Integer, Double> getTeacherRate(Long userId){
		if(null == userId){
			return new HashMap<Integer, Double>();
		}
		String key = "_gtr_"+ userId +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			Map<Integer, Double> partnerRateMap = new HashMap<Integer, Double>();
			if( null != userId && userId != 0 ){
				KeyrvQuery keyrvQuery = new KeyrvQuery();
				keyrvQuery.setKey1(userId.toString());	
				keyrvQuery.setPageSize(Integer.MAX_VALUE);
				keyrvQuery.setType(EnumKeyRelatedValueType.KV_TEACHER_RATE.getCode());
				List<KeyrvDTO> list = metaHelper.systemService.queryKeyrv(keyrvQuery).getModule().getList();
				if(null != list ){
					for(KeyrvDTO keyrvDTO : list){
						partnerRateMap.put(Integer.parseInt(keyrvDTO.getRkey()), keyrvDTO.getSort().doubleValue()/100);
					}
				}
			}
			ehcache.put(elem = new Element(key,partnerRateMap));
		}
		return (Map<Integer, Double>) elem.getObjectValue();
	}
	
	
	
	public Map<Integer, Double> getPartnerRate(Long userId){
		if(null == userId){
			return new HashMap<Integer, Double>();
		}
		String key = "_gpr_"+ userId +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			Map<Integer, Double> partnerRateMap = new HashMap<Integer, Double>();
			if( null != userId && userId != 0 ){
				KeyrvQuery keyrvQuery = new KeyrvQuery();
				keyrvQuery.setKey1(userId.toString());	
				keyrvQuery.setPageSize(Integer.MAX_VALUE);
				keyrvQuery.setType(EnumKeyRelatedValueType.KV_PARTNER_RATE.getCode());
				List<KeyrvDTO> list = metaHelper.systemService.queryKeyrv(keyrvQuery).getModule().getList();
				if(null != list ){
					for(KeyrvDTO keyrvDTO : list){
						partnerRateMap.put(Integer.parseInt(keyrvDTO.getRkey()), keyrvDTO.getSort().doubleValue()/100);
					}
				}
			}
			ehcache.put(elem = new Element(key,partnerRateMap));
		}
		return (Map<Integer, Double>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getWorkingYears(){

		String key = "_gwky_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	public List<KeyvalueDTO> getCrmBizSource(){

		String key = "_crmbs_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_SOURCE.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			keyvalueQuery.setIdeleted(0);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	
	
	
	public List<KeyvalueDTO> getKeyvalueByType(Integer type){

		String key = "_crmbs_" + type;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(type);
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			keyvalueQuery.setIdeleted(0);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getCrmBizType(){

		String key = "_crmbiztype_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			keyvalueQuery.setIdeleted(0);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	

	public List<KeyvalueDTO> getCrmBizTypeByDept(Long dept){

		String key = "_crmbiztype_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			keyvalueQuery.setIdeleted(0);
			keyvalueQuery.setValue(dept.toString());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	public List<KeyvalueDTO> getCrmBizStatus(){

		String key = "_crmbizstatus_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_STATUS.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			keyvalueQuery.setIdeleted(0);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	public List<KeyvalueDTO> getWellfare(){
		
		String key = "_gwlfr_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
			keyvalueQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(keyvalueQuery);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();		
	}
	
	

	public String getCompanyNameById(Long id) throws BaseDaoException{
		
		CompanyDTO companyDTO = getCompanyById(id);
		if(null != companyDTO){
			
			return companyDTO.getName();
		}
		
		return null;
		
	}
	

	public CompanyDTO getCompanyById(Long id) {				
		
		String key = "_c_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<CompanyDTO> result = metaHelper.userService.queryCompanyById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (CompanyDTO) elem.getObjectValue();
					
	}
	
	
	public List<CompanyDTO> getCompany() {				
		
		String key = "_g_cmmpan_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			CompanyQuery companyQuery = new CompanyQuery();
			ResultDTO<List<CompanyDTO>> result = metaHelper.userService.queryCompany(companyQuery);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (List<CompanyDTO>) elem.getObjectValue();
					
	}
	
	
	public DeptDTO getDeptById(Integer id) {
		
		String key = "_gdtbi_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<DeptDTO> result = metaHelper.systemService.queryDeptById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (DeptDTO) elem.getObjectValue();
					
	}
	
	
	
	public ResultDTO<RyxAuditRecordQuery> getAuditRecord(Long userId,Long objId, Integer objType){			
		
		String key = "_gar_"+ userId +"_"+ objId +"_"+ objType +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxAuditRecordQuery query = new RyxAuditRecordQuery();
			query.setUserId(userId);
			query.setObjId(objId);
			query.setObjType(objType);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxAuditRecordQuery> result = metaHelper.ryxCourseService.queryAuditRecord(query);
			ehcache.put(elem = new Element(key,result));
		}
		return (ResultDTO<RyxAuditRecordQuery>) elem.getObjectValue();
					
	}
	
	public Integer getTotalArticleCount(){
		
		String key = "_gtacar_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setDisplay(1);
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}
	
	
	public Integer getTotalOfflineCount(){
		
		String key = "_gtacof_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			query.setDisplay(1);
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}
	
	
	public Integer getTotalActivityCount(){
		
		String key = "_gtactiv_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			query.setDisplay(1);
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}
	
	public Integer getTotalCommerceCount(){
		
		String key = "_gtacmcnt_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			query.setDisplay(1);
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (Integer) elem.getObjectValue();
		
	}
	
	
	public RecruitmentDTO getRecruitmentById(Long id) {
		
		String key = "_Recruitment_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<RecruitmentDTO> result = metaHelper.userService.queryRecruitmentById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (RecruitmentDTO) elem.getObjectValue();
					
	}
	
	

	
	public String getSalaryNameById(Long id) throws BaseDaoException{
		
		KeyvalueDTO dto = getSalaryById(id);
		if(null != dto){
			
			return dto.getKey1();
		}
		
		return null;
		
	}
	

	public KeyvalueDTO getSalaryById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
		String key = "_get_salary_by_id_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
public String getWorkingYearsNameById(Long id) throws BaseDaoException{
		
		KeyvalueDTO dto = getWorkingYearsById(id);
		if(null != dto){
			
			return dto.getKey1();
		}
		
		return null;
		
	}
	

	public KeyvalueDTO getWorkingYearsById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
		String key = "_get_working_years_by_id_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	public String getWellfareNameById(Long id) throws BaseDaoException{
		
		KeyvalueDTO dto = getWellfareById(id);
		if(null != dto){
			
			return dto.getKey1();
		}
		
		return null;
		
	}
	


	public KeyvalueDTO getWellfareById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
		String key = "_salary_getWellfareById_"+ id +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	public String getCompanyTypeNameById(Long id) throws BaseDaoException{
		
		KeyvalueDTO dto = getCompanyTypeById(id);
		if(null != dto){
			
			return dto.getKey1();
		}
		
		return null;
		
	}
	
	public List<KeyvalueDTO> getCompanyType() {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		String key = "_salary_getCompanyType_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);			
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
					
	}

	
	


	public KeyvalueDTO getCompanyTypeById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
		String key = "_salary_getCompanyTypeById_"+ id +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	public String getCompanyScaleNameById(Long id) throws BaseDaoException{
		
		KeyvalueDTO dto = getCompanyScaleById(id);
		if(null != dto){
			
			return dto.getKey1();
		}
		
		return null;
		
	}
	
	
	public List<KeyvalueDTO> getCompanyScale() {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
		String key = "_gcpyscle_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);			
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
					
	}



	public KeyvalueDTO getCompanyScaleById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
		String key = "_salary_getCompanyScaleById_" + id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	public String getFullAddressById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry()) + splitSign +
					 getProvinceNameByCode(addressDTO.getProvince()) + splitSign +
					 getCityNameByCode(addressDTO.getCity()) + splitSign +
					 getAreaNameByCode(addressDTO.getArea()) + splitSign +
					 addressDTO.getDescr();
					
			return addr;
		}
		
		return "";		
	}
	
	public String getAddressNameById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry()) + splitSign + 
					 getProvinceNameByCode(addressDTO.getProvince()) + splitSign +
					 getCityNameByCode(addressDTO.getCity()) + splitSign +
					 getAreaNameByCode(addressDTO.getArea()) + splitSign +
					 addressDTO.getDescr();
					
			return addr;
		}
		
		return "";		
	}
	
	
	public String getAddressCountryById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry());
					
			return addr;
		}
		
		return "";		
	}
	
	public String getAddressCountryProvinceById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry()) + splitSign +
					 getProvinceNameByCode(addressDTO.getProvince());
					
			return addr;
		}
		
		return "";		
	}
	

	public String getAddressCountryProvinceCityById(Long id) throws BaseDaoException{
	
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry())  + splitSign +
					 getProvinceNameByCode(addressDTO.getProvince())  + splitSign +
					 getCityNameByCode(addressDTO.getCity());
			return addr;
		}
		
		return "";		
	}


	public String getAddressCountryProvinceCityAreaById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  getCountryNameByCode(addressDTO.getCountry())  + splitSign + 
					 getProvinceNameByCode(addressDTO.getProvince())  + splitSign +
					 getCityNameByCode(addressDTO.getCity())  + splitSign +
					 getAreaNameByCode(addressDTO.getArea()) ;
					
			return addr;
		}
		
		return "";		
	}
	
	public String getAddressProvinceCityAreaById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  
					 getProvinceNameByCode(addressDTO.getProvince())  + splitSign +
					 getCityNameByCode(addressDTO.getCity())  + splitSign +
					 getAreaNameByCode(addressDTO.getArea()) ;
					
			return addr;
		}
		
		return "";		
	}

	public String getAddressProvinceCityById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  
					 getProvinceNameByCode(addressDTO.getProvince()) + splitSign +
					 getCityNameByCode(addressDTO.getCity()) ;
			return addr;
		}
		
		return "";		
	}

	public String getAddressProvinceById(Long id) throws BaseDaoException{
		
		AddressDTO addressDTO = getAddressById(id);
		if(null != addressDTO){
			
			String addr =  
					 getProvinceNameByCode(addressDTO.getProvince()) ;
					
			return addr;
		}
		
		return "";		
	}


	
	public AddressDTO getAddressById(Long id) {				
		
		String key = "_address_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<AddressDTO> result = metaHelper.userService.queryAddressById(id);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		return (AddressDTO) elem.getObjectValue();
					
	}
	
	
	
	
	public ResultDTO<List<KeyvalueDTO>> getCountry(){
		
		
		String key = "_all_country_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();		
	
	}
	
	
	public String getCountryNameByCode(String code) throws BaseDaoException{
		
		KeyvalueDTO dto = getCountryByCode(code);
		if(null != dto){
			
			return dto.getValue();
		}
		
		return null;
		
	}
	

	public KeyvalueDTO getCountryByCode(String code) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setKey1(code);
		query.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
		String key = "_get_country_by_code_"+ code + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){
					
					return list.get(0);
					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	
	public String getProvinceNameByCode(String code) throws BaseDaoException{
		
		KeyvalueDTO dto = getProvinceByCode(code);
		if(null != dto){
			
			return dto.getValue();
		}
		
		return null;
		
	}
	

	public KeyvalueDTO getProvinceByCode(String code) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setKey1(code);
		query.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
		String key = "_get_province_by_code_"+ code + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){					
					keyvalueDTO = list.get(0);					
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	public String getCityNameByCode(String code) throws BaseDaoException{
		
		KeyvalueDTO dto = getCityByCode(code);
		if(null != dto){
			
			return dto.getValue();
		}
		
		return null;
		
	}
	
	
	

	public List<KeyvalueDTO> getCityByProvince(String province) {	
		
		if(StringHelper.isNullOrEmpty(province)){
			return new ArrayList<KeyvalueDTO>();
		}
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid1(province);
		query.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
		String key = "_keyvalue_getCityByProvince_"+ province +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key, result.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
					
	}
	
	public List<KeyvalueDTO> getAreaByCity(String city) {
		
		
		if(StringHelper.isNullOrEmpty(city)){
			return new ArrayList<KeyvalueDTO>();
		}
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid2(city);
		query.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
		String key = "_gabyc_"+ city +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key, result.getModule()));
		}		
		return (List<KeyvalueDTO>) elem.getObjectValue();
					
	}

	public KeyvalueDTO getCityByCode(String code) {				
		

		String key = "_get_city_by_code_"+ code + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyvalueQuery query = new KeyvalueQuery();
			query.setKey1(code);
			query.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){					
					keyvalueDTO = list.get(0);
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	
	public String getAreaNameByCode(String code) throws BaseDaoException{
		
		KeyvalueDTO dto = getAreaByCode(code);
		if(null != dto){
			
			return dto.getValue();
		}
		
		return null;
		
	}
	
	
	public KeyvalueDTO getAreaByCode(String code) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setKey1(code);
		query.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
		String key = "_get_area_by_code_"+ code + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			if(result.isSuccess()){
				
				List<KeyvalueDTO> list = result.getModule();
				if(null != list && list.size() == 1){					
					keyvalueDTO = list.get(0);
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	

	public List<KeyvalueDTO> getProvince(){		

		String key = "_all_province_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyvalueQuery  query = new KeyvalueQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getCity(){
		
		String key = "_all_city_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);			
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getArea(){
		
		String key = "_all_area_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();			
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	
	public String getIndustry0NameById(Long id) {
		KeyvalueDTO dto = getIndustry0ById(id);
		if(null != dto){
			return dto.getKey1();
		}
		return "";
	}
	
	public String getIndustry1NameById(Long id) {
		KeyvalueDTO dto = getIndustry1ById(id);
		if(null != dto){
			return dto.getKey1();
		}
		return "";
	}
	
	
	public String getIndustryFullNameById(Long id) {
		
		KeyvalueDTO dto = getIndustry0ById(id);
		if(null != dto){
			
			String name = "";
			if(StringUtils.isNotEmpty(dto.getPid())) {
				
				KeyvalueDTO dto1 = getIndustry0ById(Long.parseLong(dto.getPid()));
				if(null != dto1){
					name = dto1.getKey1() + splitSign;
				}				
			}
			
			if(StringUtils.isNotEmpty(dto.getPid1())) {
				
				KeyvalueDTO dto1 = getIndustry0ById(Long.parseLong(dto.getPid1()));
				if(null != dto1){
					name = name + dto1.getKey1() + splitSign ;
				}				
			}
			
			
			if(StringUtils.isNotEmpty(dto.getPid2())) {
				
				KeyvalueDTO dto1 = getIndustry0ById(Long.parseLong(dto.getPid2()));
				if(null != dto1){
					name = name + dto1.getKey1() + splitSign;
				}				
			}
			
			name = name + dto.getKey1();
			
			return name ; 
		}
		
		return null;
		
	}
	

	public KeyvalueDTO getIndustry0ById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());		
		String key = "_keyvalue_getIndustry0ById_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueById(id);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	
	public KeyvalueDTO getIndustry1ById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());		
		String key = "_keyvalue_getIndustry1ById_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueById(id);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	public List<KeyvalueDTO> getIndustry0(){
	
		String key = "_gidtyo_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getIndustry1ByIndustry0(String industry0){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(industry0);
		query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
		String key = "_gidy1bind0_"+ industry0 +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<List<KeyvalueDTO>> getIndustry1ByIndustry0Realtime(String industry0){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(industry0);
		query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
		
		ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
	
		return resultDTO;
	}
	

	
	public List<KeyvalueDTO> getIndustry2ByIndustry1(String industry0,String industry1){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(industry0);
		query.setPid1(industry1);
		query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
		String key = "_gind2bind1_"+ industry0 +"_"+ industry1 +"__" ;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}

	public List<KeyvalueDTO> getIndustry1(){
		
		String key = "_all_industry1_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public ResultDTO<List<KeyvalueDTO>> getIndustry2(){
		
		String key = "_all_industry2_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}
	
	
	
	
	public KeyvalueDTO getSpecialty0ById(Long id) {				
	
		String key = "_keyvalue_getSpecialty0ById_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueById(id);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	
	public KeyvalueDTO getSpecialty1ById(Long id) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setId(id);
		query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());		
		String key = "_keyvalue_getSpecialty1ById_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueById(id);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	
	
	public String getSpecialty0NameById(Long id) {
		KeyvalueDTO dto = getSpecialty0ById(id);
		if(null != dto){
			return dto.getKey1();
		}
		return "";
	}
	
	public String getSpecialty1NameById(Long id) {
		KeyvalueDTO dto = getSpecialty1ById(id);
		if(null != dto){
			return dto.getKey1();
		}
		return "";
	}
	
	
	public String getSpecialtyFullNameById(Long id) {
		
		KeyvalueDTO dto = getSpecialty0ById(id);
		if(null != dto){
			
			String name = "";
			if(StringUtils.isNotEmpty(dto.getPid())) {
				
				KeyvalueDTO dto1 = getSpecialty0ById(Long.parseLong(dto.getPid()));
				if(null != dto1){
					name = dto1.getKey1() + splitSign;
				}				
			}
			
			if(StringUtils.isNotEmpty(dto.getPid1())) {
				
				KeyvalueDTO dto1 = getSpecialty0ById(Long.parseLong(dto.getPid1()));
				if(null != dto1){
					name = name + dto1.getKey1() + splitSign ;
				}				
			}
			
			
			if(StringUtils.isNotEmpty(dto.getPid2())) {
				
				KeyvalueDTO dto1 = getSpecialty0ById(Long.parseLong(dto.getPid2()));
				if(null != dto1){
					name = name + dto1.getKey1() + splitSign;
				}				
			}
			
			name = name + dto.getKey1();
			
			return name ; 
		}
		
		return null;
		
	}
	
	
	
	
	public List<KeyvalueDTO> getSpecialty1BySpecialty0(String specialty0){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(specialty0);
		query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
		String key = "_getSpecialty1BySpecialty0_"+ specialty0 +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	

	
	public List<KeyvalueDTO> getSpecialty2BySpecialty1(String specialty0,String specialty1){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(specialty0);
		query.setPid1(specialty1);
		query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
		String key = "_getSpecialty2BySpecialty1_"+ specialty0 +"_"+ specialty1 +"__" ;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	public List<KeyvalueDTO> getPosition1ByPosition0(String o0){		

		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(o0);
		query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
		String key = "_gp1bp0_"+ o0 +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<List<KeyvalueDTO>> resultDTO = 
					metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	

	
	public List<KeyvalueDTO> getPosition2ByPosition1(String o0,String o1){		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setPid(o0);
		query.setPid1(o1);
		query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
		String key = "_gp2bp1_"+ o0 +"_"+ o1 +"__" ;
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<List<KeyvalueDTO>> resultDTO = 
					metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	
	
public List<KeyvalueDTO> getPosition0(){
		
		String key = "_all_specialty0_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO.getModule()));
		}
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}


	public ResultDTO<List<KeyvalueDTO>> getPosition1(){
		
		String key = "_all_specialty1_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<List<KeyvalueDTO>> getSpecialty0(){
		
		String key = "_all_specialty0_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}


	public ResultDTO<List<KeyvalueDTO>> getSpecialty1(){
		
		String key = "_all_specialty1_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}

	
	public ResultDTO<List<KeyvalueDTO>> getSpecialty2(){
		
		String key = "_all_specialty2_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {

			KeyvalueQuery  query = new KeyvalueQuery();
			query.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
			ResultDTO<List<KeyvalueDTO>> resultDTO = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,resultDTO));
		}
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}

	
	
	public ResultDTO<List<RecruitmentDTO>> getRecentRecruitmentByCompany(Long company){
		
		RecruitmentQuery recruitmentQuery = new RecruitmentQuery();
		recruitmentQuery.setOrderBy("tmodified");
		recruitmentQuery.setSooort("desc");
		recruitmentQuery.setCompany(company.toString());;
		recruitmentQuery.setPageSize(10);;
		
		String key = "_getRecentRecruitmentByCompany_"+ company +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<List<RecruitmentDTO>> result = metaHelper.userService.queryRecruitment(recruitmentQuery);
			ehcache.put(elem = new Element(key,result));	
		}
		
		return (ResultDTO<List<RecruitmentDTO>>) elem.getObjectValue();
		
	}
	
	
	
	public KeyvalueDTO getKeyvalueById(Long id) {				
				
		String key = "_kv_gkvbid_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueById(id);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
				if(null == keyvalueDTO){
					keyvalueDTO = new KeyvalueDTO();
				}
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
					
	}
	
	public KeyvalueDTO getKeyvalueByUid(String uid) {				
		
		String key = "_gkvabu_" + uid + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueByUid(uid);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
		
	}
	
	
	public KeyvalueDTO getKeyvalueByKey1(String key1) {				
		
		String key = "_getKeyvalueByKey1_" + key1 + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvalueDTO keyvalueDTO = null;
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueByKey1(key1);
			if(result.isSuccess()){
				
				keyvalueDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,keyvalueDTO));
		}
		
		return (KeyvalueDTO) elem.getObjectValue();
		
	}
	
	
	public KeyvDTO getKeyvByUid(String uid) {				
		
		String key = "_gkvbu_" + uid + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			KeyvDTO veyvDTO = null;
			ResultDTO<KeyvDTO> result = metaHelper.systemService.queryKeyvByUid(uid);
			if(result.isSuccess()){				
				veyvDTO = result.getModule();
			}
			ehcache.put(elem = new Element(key,veyvDTO));
		}
		
		return (KeyvDTO) elem.getObjectValue();
		
	}
	
	public KeyrvDTO getKeyrvByKrt(String key1,String rkey,Integer type) {				
		
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setKey1(key1);
		keyrvQuery.setRkey(rkey);
		keyrvQuery.setType(type);
		keyrvQuery = metaHelper.systemService.queryKeyrv(keyrvQuery).getModule();
		return null!=keyrvQuery && null!=keyrvQuery.getList()&&keyrvQuery.getList().size()>0 ? (KeyrvDTO)keyrvQuery.getList().get(0) : null;
		
	}
	
	
	public Boolean isKeyvalue(Long id,Integer type) {				
		
		KeyvalueQuery query = new KeyvalueQuery();
		query.setType(type);
		query.setIdeleted(0);
		query.setKey1(id.toString());
		ResultDTO<Integer> result = metaHelper.systemService.countQueryKeyvalue(query);
		if(result.isSuccess()){
			Integer cnt = result.getModule();
			if(cnt == 0){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}					
	}
	
	
	public Boolean isKeyv(Long id,Integer type) {				
		
		KeyvQuery query = new KeyvQuery();
		query.setType(type);
		query.setIdeleted(0);
		query.setKey1(id.toString());
		ResultDTO<Integer> result = metaHelper.systemService.countQueryKeyv(query);
		if(result.isSuccess()){
			Integer cnt = result.getModule();
			if(cnt == 0){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}					
	}
	
	
	public ResultDTO<RyxCourseDTO> getCourseById(Long id) {				
		
		String key = "_gcbi_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<RyxCourseDTO> result = metaHelper.ryxCourseService.getCourseById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxCourseDTO> getCourseById1(Long id) {				
		return metaHelper.ryxCourseService.getCourseById(id);
	}
	
	
	
	/**
	 * 获取未结束的（直播中、未开始）
	 * @param id
	 * @return
	 */
	public List<RyxCourseDTO> getUnendVideo(Integer pageSize) {				
	
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtend(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		return  metaHelper.ryxCourseService.queryCourse(courseQuery).getModule().getList();
					
	}
	
	
	public Integer getUnendVideoCount() {
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtend(System.currentTimeMillis()/1000);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		return  metaHelper.ryxCourseService.countQueryCourse(courseQuery).getModule();
	}
	
	
	
	/**
	 * 获取未结束的（直播中、未开始）
	 * @param id
	 * @return
	 */
	public List<RyxCourseDTO> getUnendOffline1(Integer pageSize) {				
	
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtstart(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		return  metaHelper.ryxCourseService.queryCourse(courseQuery).getModule().getList();
					
	}
	
	
	/**
	 * 获取未结束的（直播中、未开始）
	 * @param id
	 * @return
	 */
	public List<RyxCourseDTO> getUnendOffline(RyxCourseQuery courseQuery) {				
	
		courseQuery.setTtstart(System.currentTimeMillis()/1000);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		return  metaHelper.ryxCourseService.queryCourse(courseQuery).getModule().getList();
					
	}
	
	
	public Integer getUnendOfflineCount() {
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setTtstart(System.currentTimeMillis()/1000);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		return  metaHelper.ryxCourseService.countQueryCourse(courseQuery).getModule();
	}
	
	
	/**
	 * 获取已结束的
	 * @param pageSize
	 * @return
	 */
	public List<RyxCourseDTO> getEndVideo(Integer pageSize) {				
		
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
		
		return  metaHelper.ryxCourseService.queryCourse(courseQuery).getModule().getList();
					
	}
	
	
	public List<RyxCourseDTO> getEndOffline(Integer pageSize) {				
		
		if(null == pageSize || pageSize <=0 ){
			return null;
		}
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setEtstart(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(pageSize);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
		}
		
		return  metaHelper.ryxCourseService.queryCourse(courseQuery).getModule().getList();
					
	}
	
	
	public ResultDTO<RyxCourseQuery> getCourseByCategory(Integer category) {				
		
		String key = "_gcbc_"+ category + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setPageSize(Integer.MAX_VALUE);
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setCategory(category);
			courseQuery.setDisplay(1);
			courseQuery.setOrderBy("c.sort");
			courseQuery.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
			
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
					
	}
	
	
	public ResultDTO<RyxTeacherDTO> getTeacherById(Long id) {				
		
		String key = "_gtby_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			ResultDTO<RyxTeacherDTO> result = metaHelper.ryxTeacherService.getTeacherById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherDTO>) elem.getObjectValue();
					
	}
	
	public Boolean isDownload(Long objId,Long userId){
		KeyrvQuery query  = new KeyrvQuery();
		query.setKey1(objId.toString());
		query.setRkey(userId.toString());
		query.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
		ResultDTO<Integer> resultDTO =  metaHelper.systemService.countQueryKeyrv(query);
		if(resultDTO.isSuccess()){
			Integer cnt = resultDTO.getModule();
			return cnt > 0;
		}
		return false ;
	}
	
	public List<KeyrvDTO> getCourseSeriesById(Long id) {				
		
		String key = "_get_c_s_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			KeyrvQuery query = new KeyrvQuery();
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setKey1(id.toString());
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			query.setOrderBy("sort");	
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyrvQuery> result = metaHelper.systemService.queryKeyrv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}		
		return (List<KeyrvDTO>) elem.getObjectValue();					
	}
	
	
	public List<KeyvDTO> getIndexTeacher(){
		String key = "_git_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			KeyvQuery query = new KeyvQuery();
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KV_TEACHER_INDEX.getCode());
			query.setOrderBy("sort");	
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}		
		return (List<KeyvDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxCourseDTO> getIndexForum(Integer pageSize){
		String key = "_giform_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setPageSize(pageSize);
			query.setObjType(EnumObjectType.FORUM_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}		
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvDTO> getIndexTeacher(Integer pageSize){
		String key = "_git_"+ pageSize +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			KeyvQuery query = new KeyvQuery();
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setPageSize(pageSize);
			query.setType(EnumKeyValueType.KV_TEACHER_INDEX.getCode());
			query.setOrderBy("sort");	
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}		
		return (List<KeyvDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvDTO> getMobileIndexTeacher(){
		String key = "_gmit_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			KeyvQuery query = new KeyvQuery();
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KV_MOBILE_TEACHER_INDEX.getCode());
			query.setOrderBy("sort");	
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}		
		return (List<KeyvDTO>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<RyxCourseOutlineQuery> getCourseOulineById(Long id) {				
		
		String key = "_get_co_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			RyxCourseOutlineQuery query = new RyxCourseOutlineQuery();
			query.setIdeleted(0);
			query.setCid(id);
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");			
			ResultDTO<RyxCourseOutlineQuery> result = ryxCourseService.queryCourseOutline(query);
			ehcache.put(elem = new Element(key,result));
			
		}
		
		return (ResultDTO<RyxCourseOutlineQuery>) elem.getObjectValue();
					
	}

	
	public RyxTeacherDTO getTeacherByNick(String nick) {				
		
		String key = "_getTeacherById_"+ nick + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			ResultDTO<RyxTeacherDTO> result = metaHelper.ryxTeacherService.getTeacherByNick(nick);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (RyxTeacherDTO) elem.getObjectValue();
					
	}
	
	
	public RyxTeacherDTO getTeacherByUserId(Long userId) {				
		
		String key = "_getTeacherById_"+ userId + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {			
			ResultDTO<RyxTeacherDTO> result = metaHelper.ryxTeacherService.getTeacherByUserId(userId);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (RyxTeacherDTO) elem.getObjectValue();
					
	}
	
	
	
	public ResultDTO<RyxNewsDTO> getNewsById(Long id) {				
		
		String key = "_getNewsById_"+ id + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			
			ResultDTO<RyxNewsDTO> result = metaHelper.ryxNewsService.getNewsById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxNewsDTO>) elem.getObjectValue();
					
	}
	
	
	public ResultDTO<RyxNewsQuery> getNewsByCategory(Integer category) {				
		
		String key = "_getNewsByCategory_"+ category + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			RyxNewsQuery query = new RyxNewsQuery ();
			query.setCategory(category);
			query.setDisplay(1);;
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxNewsQuery> result = metaHelper.ryxNewsService.queryNews(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxNewsQuery>) elem.getObjectValue();
					
	}
	
	
	
	public ResultDTO<RyxNewsQuery> getNewsByCategory(Integer category,Integer cnt) {				
		
		String key = "_getNewsByCategory_"+ category + "_"+ cnt +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		
		if (elem == null) {
			RyxNewsQuery query = new RyxNewsQuery ();
			query.setCategory(category);
			query.setDisplay(1);;
			query.setPageSize(cnt);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxNewsQuery> result = metaHelper.ryxNewsService.queryNews(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxNewsQuery>) elem.getObjectValue();
					
	}

	
	
	public ResultDTO<List<RyxCourseDTO>> getCourseByVId(String vid) {				
		
		String key = "_getCourseByVId_"+ vid + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<List<RyxCourseDTO>> result = metaHelper.ryxCourseService.getCourseByVId(vid);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<RyxCourseDTO>>) elem.getObjectValue();
					
	}
	
	public ResultDTO<List<RyxCourseDTO>> getCourseByIds(String ids) {				
		
		String key = "_getCourseByIds_"+ ids + "_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			
			ResultDTO<List<RyxCourseDTO>> result = metaHelper.ryxCourseService.getCourseByIds(ids);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<RyxCourseDTO>>) elem.getObjectValue();
					
	}
	
	
	
	
	public ResultDTO<RyxCourseQuery> getLivingRecentLiving(){
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setTtstart(DateHelper.getTodayLong());
		courseQuery.setEtstart(DateHelper.getTomorrowLong());
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		String key =StringHelper.getObjectString(courseQuery);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
		
		
	}
	
	
	
	public ResultDTO<RyxCourseQuery> getTodayLiving(){
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setTtstart(DateHelper.getTodayLong());
		courseQuery.setEtstart(DateHelper.getTomorrowLong());
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		String key =StringHelper.getObjectString(courseQuery);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
		
		
	}
	
	public void clearTodayLiving(){
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setTtstart(DateHelper.getTodayLong());
		courseQuery.setEtstart(DateHelper.getTomorrowLong());
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		String key =StringHelper.getObjectString(courseQuery);

		Ehcache ehcache =  getCache("cacheMetadata");
		ehcache.remove(key);
	}
	
	public ResultDTO<RyxCourseQuery> getRecentLiving(){
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		courseQuery.setTtstart(DateHelper.getTomorrowLong());
		courseQuery.setEtstart(null);
		String key =StringHelper.getObjectString(courseQuery);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	public void clearRecentLiving(){
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		courseQuery.setTtstart(DateHelper.getTomorrowLong());
		courseQuery.setEtstart(null);
		String key =StringHelper.getObjectString(courseQuery);
		Ehcache ehcache =  getCache("cacheMetadata");
		ehcache.remove(key);
	}
	
	

	public ResultDTO<RyxCourseQuery> getPreviousLiving(){
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setTtstart(null);
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		courseQuery.setEtstart(DateHelper.getTodayLong());
		
		String key =StringHelper.getObjectString(courseQuery);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
		
	}


	public void clearPreviousLiving(){
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("asc");
		courseQuery.setTtstart(null);
		courseQuery.setDisplay(EnumDisplayType.DISPLAY.getCode());
		courseQuery.setEtstart(DateHelper.getTodayLong());
		
		String key =StringHelper.getObjectString(courseQuery);
		Ehcache ehcache =  getCache("cacheMetadata");
		ehcache.remove(key);
	}
	
	
	public ResultDTO<RyxCourseQuery> queryCourseCache(RyxCourseQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			if(StringHelper.isNullOrEmpty(query.getOrderBy())){
				query.setOrderBy("update_time");
				query.setSooort("desc");
			}
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	public List<RyxSmallCourseDTO> queryMobileOnline(RyxCourseQuery query){
		

		query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		String key = "_o_"+StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			if(StringHelper.isNullOrEmpty(query.getOrderBy())){
				query.setOrderBy("update_time");
				query.setSooort("desc");
			}
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryOnlineCourse(query);
			if(EnumObjectType.ONLINE_MODULE.getCode() == query.getObjType() &&
					EnumCourseType.MAIN_COURSE.getCode() == query.getFlag() ){
				
				if(null !=  result.getModule().getList()){
					for(RyxSmallCourseDTO courseDTO : (List<RyxSmallCourseDTO>)result.getModule().getList() ){
						courseDTO.setScnt(CourseHelper.getInstance().getKeyrvCount(courseDTO.getId().toString()
								,EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode()));
						list.add(courseDTO);
					}
				}
				
			}
			else{
				list = result.getModule().getList(); 
			}
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxSmallCourseDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxSmallInfoDTO> queryMobileInfo(RyxCourseQuery query){
		

		query.setObjType(EnumObjectType.INFO_MODULE.getCode());
		String key = "_o_"+StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			if(StringHelper.isNullOrEmpty(query.getOrderBy())){
				query.setOrderBy("update_time");
				query.setSooort("desc");
			}
			List<RyxSmallInfoDTO> list = new ArrayList<RyxSmallInfoDTO>();
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryOnlineCourse(query);
			
			if(null !=  result.getModule().getList()){
				for(RyxSmallCourseDTO courseDTO : (List<RyxSmallCourseDTO>)result.getModule().getList() ){
					RyxSmallInfoDTO smallVideDTO = new RyxSmallInfoDTO();
					smallVideDTO.setHits(courseDTO.getHits());
					smallVideDTO.setId(courseDTO.getId());
					smallVideDTO.setImageUrl(courseDTO.getImageUrl());
					smallVideDTO.setPrice(courseDTO.getPrice());
					smallVideDTO.setOprice(courseDTO.getOprice());
					smallVideDTO.setTcreate(DateHelper.second2String("yyyy.MM.dd", courseDTO.getUpdateTime()));
					smallVideDTO.setTitle(courseDTO.getTitle());
					smallVideDTO.setCategory(courseDTO.getCategory());
					smallVideDTO.setIframeUrl(courseDTO.getIframeUrl());
					smallVideDTO.setGetHls(courseDTO.getGetHls());
					smallVideDTO.setDescr(courseDTO.getDescr());
					list.add(smallVideDTO);
				}
			}	
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxSmallInfoDTO>) elem.getObjectValue();
	}
	
	
	
	public List<RyxSmallInfoDTO> queryMobileBook(RyxCourseQuery query){
		

		query.setObjType(EnumObjectType.BOOK_MODULE.getCode());
		String key = "_o_"+StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			if(StringHelper.isNullOrEmpty(query.getOrderBy())){
				query.setOrderBy("update_time");
				query.setSooort("desc");
			}
			List<RyxSmallInfoDTO> list = new ArrayList<RyxSmallInfoDTO>();
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryOnlineCourse(query);
			
			if(null !=  result.getModule().getList()){
				for(RyxSmallCourseDTO courseDTO : (List<RyxSmallCourseDTO>)result.getModule().getList() ){
					RyxSmallInfoDTO smallVideDTO = new RyxSmallInfoDTO();
					smallVideDTO.setHits(courseDTO.getHits());
					smallVideDTO.setId(courseDTO.getId());
					smallVideDTO.setImageUrl(courseDTO.getImageUrl());
					smallVideDTO.setPrice(courseDTO.getPrice());
					smallVideDTO.setOprice(courseDTO.getOprice());
					smallVideDTO.setTcreate(DateHelper.second2String("yyyy.MM.dd", courseDTO.getUpdateTime()));
					smallVideDTO.setTitle(courseDTO.getTitle());
					smallVideDTO.setCategory(courseDTO.getCategory());
					smallVideDTO.setIframeUrl(courseDTO.getIframeUrl());
					smallVideDTO.setGetHls(courseDTO.getGetHls());
					smallVideDTO.setDescr(courseDTO.getDescr());
					list.add(smallVideDTO);
				}
			}	
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxSmallInfoDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxSmallVideDTO> queryMobileVideo(RyxCourseQuery query){
		query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		//query.setOrderBy("id");
		//query.setSooort("desc");
		String key = "_o_"+StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			
			List<RyxSmallVideDTO> list = new ArrayList<RyxSmallVideDTO>();
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryVideo(query);
				
			if(null !=  result.getModule().getList()){
				for(RyxCourseDTO courseDTO : (List<RyxCourseDTO>)result.getModule().getList() ){
					RyxSmallVideDTO smallVideDTO = new RyxSmallVideDTO();
					smallVideDTO.setHits(courseDTO.getHits());
					smallVideDTO.setId(courseDTO.getId());
					smallVideDTO.setImageUrl(courseDTO.getImageUrl());
					smallVideDTO.setPrice(courseDTO.getPrice());
					smallVideDTO.setOprice(courseDTO.getOprice());
					smallVideDTO.setTitle(courseDTO.getTitle());
					smallVideDTO.setTstart1(DateHelper.second2String("MM月dd日  HH:mm", courseDTO.getTstart()));
					smallVideDTO.setTend1(DateHelper.second2String("HH:mm", courseDTO.getTend()));
					smallVideDTO.setVstatus(CourseHelper.getInstance().getVideoStatus(courseDTO).getCode());
					smallVideDTO.setVid(courseDTO.getVid());
					list.add(smallVideDTO);
				}
			}			
				
			
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxSmallVideDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxSmallCourseDTO> queryMobileOffline(RyxCourseQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	

			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			if(null !=  result.getModule().getList()){
				for(RyxCourseDTO courseDTO : (List<RyxCourseDTO>)result.getModule().getList() ){
					RyxSmallCourseDTO smallCourseDTO = new RyxSmallCourseDTO();
					BeanUtils.copyProperties(courseDTO, smallCourseDTO,BeanHelper.getNullPropertyNames(courseDTO));
					smallCourseDTO.setDistrict2(getCityByCode(courseDTO.getDistrict2()).getValue());
					list.add(smallCourseDTO);
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxSmallCourseDTO>) elem.getObjectValue();
	}
	
	
	
	
	public RyxCourseQuery queryOfflineCourseCache(RyxCourseQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,query));
		}
		
		return (RyxCourseQuery) elem.getObjectValue();
	}
	
	public ResultDTO<RyxCourseQuery> getHotestOnline(Integer pageSize){
		
		String key = "_gho_"+ pageSize +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			//query.setFlag(0);
			query.setPageSize(pageSize);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxCourseQuery> getHotestOnline(Long cuid,Integer pageSize){
		
		String key = "_gho_"+cuid+"_"+ pageSize +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			//query.setFlag(0);
			query.setCuid(cuid);
			query.setPageSize(pageSize);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxCourseQuery> getRecentOnline(Integer pageSize){
		
		
		String key = "_gro_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(0);
			query.setPageSize(pageSize);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxCourseQuery> getRecentOnline(Long cuid,Integer pageSize){
		
		
		String key = "_gro_"+ pageSize +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setPageSize(pageSize);
			query.setCuid(cuid);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setFlag(0);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}

	
	public ResultDTO<RyxCourseQuery> getHotestArticle(Integer pageSize){
		
		String key = "_gha_"+ pageSize +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			
			query.setPageSize(pageSize);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxCourseQuery> getHotestArticle(Long cuid,Integer pageSize){
		
		String key = "_gha_"+cuid+"_"+pageSize+"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			
			query.setCuid(cuid);
			query.setPageSize(pageSize);
			query.setOrderBy("hits");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	


	
	public ResultDTO<RyxCourseQuery> getDownestArticle(Long cuid,Integer pageSize){
		
		String key = "_gda_"+cuid+"_"+pageSize+"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());			
			query.setCuid(cuid);
			query.setPageSize(pageSize);
			query.setOrderBy("renqi");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<RyxCourseQuery> getRecentArticle(Integer pageSize){
		
		
		String key = "_gra_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			
			query.setPageSize(pageSize);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxCourseQuery> getRecentArticle(Long cuid,Integer pageSize){
		
		
		String key = "_gra_"+ pageSize +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setPageSize(pageSize);
			query.setCuid(cuid);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	

	public ResultDTO<List<KeyvalueDTO>> queryKeyvalueCache(KeyvalueQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}
	
	
	public ResultDTO<KeyrvQuery> queryKeyrv(KeyrvQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<KeyrvQuery> result = metaHelper.systemService.queryKeyrv(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<KeyrvQuery>) elem.getObjectValue();
		
	}
	
	public ResultDTO<KeyvQuery> queryKeyrv(KeyvQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<KeyvQuery>) elem.getObjectValue();
		
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO<List<RyxAdDTO>> queryAdCache(Integer id){
	
		String key ="_queryAdCache_"+ id +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<List<RyxAdDTO>> result = metaHelper.ryxAdService.getAd(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<RyxAdDTO>>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxTeacherQuery> queryTeacherCache(RyxTeacherQuery query){	
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxTeacherQuery> queryTeacherCache1(RyxTeacherQuery query){	
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxTeacherQuery> result = null;
			if(null == query.getCategory()){
				result = metaHelper.ryxTeacherService.queryTeacher(query);
			}
			else{
				result = metaHelper.ryxTeacherService.queryTeacher1(query);
				RyxTeacherQuery teacherQuery = result.getModule();
				List<RyxTeacherDTO> list=new ArrayList<RyxTeacherDTO>();
				List<Long> idList=teacherQuery.getList();
				for(Long id:idList){
					list.add(getTeacherById(id).getModule());
				}
				teacherQuery.setList(list);
				result.setModule(teacherQuery);
			}
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxUsersDTO> getRyxUserById(Long userId){	
		
		String key="_getruById_"+ userId +"_";
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxUsersDTO> result = metaHelper.ryxUserService.getUserById(userId);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxUsersDTO>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<RyxFeedbackQuery> queryFeedbackCache(RyxFeedbackQuery query){	
		
		String key = StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxFeedbackQuery> result = metaHelper.ryxUserService.queryFeedkack(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxFeedbackQuery>) elem.getObjectValue();
	}

	
	public ResultDTO<RyxEvaluQuery> queryEvalu(RyxEvaluQuery query){	
		
		String key = StringHelper.getObjectString(query) + "_qeva_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxEvaluQuery> result = metaHelper.ryxUserService.queryEvalu(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxEvaluQuery>) elem.getObjectValue();
	}
	
	
	public Integer countQueryEvalu(Long objer,Integer score){	
		
		RyxEvaluQuery query = new RyxEvaluQuery();
		query.setObjer(objer);
		query.setScore(score);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		String key = StringHelper.getObjectString(query);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			ResultDTO<Integer> result = metaHelper.ryxUserService.countQueryEvalu(query);
			ehcache.put(elem = new Element(key,result.isSuccess()? result.getModule():0));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer countQueryEvalu(Long objer,Integer objType,Long objId){	
		
		RyxEvaluQuery query = new RyxEvaluQuery();
		query.setObjer(objer);
		query.setObjType(objType);
		query.setObjId(objId);
		query.setIdeleted(0);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		String key = StringHelper.getObjectString(query);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			ResultDTO<Integer> result = metaHelper.ryxUserService.countQueryEvalu(query);
			ehcache.put(elem = new Element(key,result.isSuccess()? result.getModule():0));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer countQueryEvalu(Long objer,Integer objType,Long objId,Integer score){	
		
		RyxEvaluQuery query = new RyxEvaluQuery();
		query.setObjer(objer);
		query.setObjType(objType);
		query.setObjId(objId);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setScore(score);
		query.setIdeleted(0);
		String key = StringHelper.getObjectString(query);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			ResultDTO<Integer> result = metaHelper.ryxUserService.countQueryEvalu(query);
			ehcache.put(elem = new Element(key,result.isSuccess()? result.getModule():0));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer countQueryEvalu(Long objer){	
		
		RyxEvaluQuery query = new RyxEvaluQuery();
		query.setObjer(objer);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setIdeleted(0);
		String key = StringHelper.getObjectString(query);
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			ResultDTO<Integer> result = metaHelper.ryxUserService.countQueryEvalu(query);
			ehcache.put(elem = new Element(key,result.isSuccess()? result.getModule():0));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxQuestionQuery> queryQuestion(RyxQuestionQuery query){	
		
		String key = StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxQuestionQuery> result = metaHelper.ryxUserService.queryQuestion(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxQuestionQuery>) elem.getObjectValue();
	}
	
	

	
	public ResultDTO<RyxCategoryDTO> getCategoryById(Integer id) {

		String key="_getCById_"+ id +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCategoryDTO> result = metaHelper.ryxCategoryService.getCategoryById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCategoryDTO>) elem.getObjectValue();
	}
	

	
	
	public ResultDTO<RyxCategoryQuery> getCategoryByType(Integer type,Integer pageSize) {

		String key="_getCByType_"+ type +"_" + pageSize + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setType(type);
			query.setPageSize(pageSize);
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategory(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCategoryQuery>) elem.getObjectValue();
	}
	
	
	public RyxCategoryDTO getCategoryByCode(String code) {

		String key="_gcbc_"+ code + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			ResultDTO<RyxCategoryDTO> result = metaHelper.ryxCategoryService.queryCategoryByCode(code);
			ehcache.put(elem = new Element(key,result.isSuccess()  ? result.getModule() : null));
		}
		
		return (RyxCategoryDTO) elem.getObjectValue();
	}
	
	public List<RyxCategoryDTO> getSubCategoryByCode(String code) {

		String key="_gscbc_"+ code + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			
			RyxCategoryDTO categoryDTO = getCategoryByCode(code);
			
			if(null != categoryDTO){
				RyxCategoryQuery query = new RyxCategoryQuery();
				query.setPid(categoryDTO.getId().intValue());
				query.setPageSize(Integer.MAX_VALUE);
				query.setIdeleted(0);
				query.setDisplay(1);
				query.setOrderBy("sort");
				ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategory(query);
				ehcache.put(elem = new Element(key,result.getModule().getList()));
			}
			else{
				ehcache.put(elem = new Element(key,null));
			}
		}
		
		return (List<RyxCategoryDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxCategoryDTO> getSubCategoryByCode(String code,Integer pageSize,Integer currentPage) {

		String key="_gscbc_"+ code + "_" + pageSize + "_" + currentPage ;
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			
			RyxCategoryDTO categoryDTO = getCategoryByCode(code);
			
			if(null != categoryDTO){
				RyxCategoryQuery query = new RyxCategoryQuery();
				query.setPid(categoryDTO.getId().intValue());
				query.setPageSize(pageSize);
				query.setCurrentPage(currentPage);
				query.setIdeleted(0);
				query.setDisplay(1);
				query.setOrderBy("sort");
				ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategory(query);
				ehcache.put(elem = new Element(key,result.getModule().getList()));
			}
			else{
				ehcache.put(elem = new Element(key,null));
			}
		}
		
		return (List<RyxCategoryDTO>) elem.getObjectValue();
	}
	
	
	
	public Integer getSubCategoryPageCount(String code,Integer pageSize) {

			
		RyxCategoryDTO categoryDTO = getCategoryByCode(code);
		
		if(null != categoryDTO){
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(categoryDTO.getId().intValue());
			query.setPageSize(Integer.MAX_VALUE);
			query.setIdeleted(0);
			query.setDisplay(1);
			ResultDTO<Integer> result = metaHelper.ryxCategoryService.countQueryCategory(query);
			
			
			query.setPageSize(pageSize);
			query.setTotalItem(result.getModule());
			return  query.getTotalPage();
		}
		else{
			return 0 ;
		}
		
	}
	
	
	public List<RyxCategoryDTO> getSubCategoryByPid(Integer pid) {

		String key="_gscbcpid_"+ pid + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(pid);
			query.setPageSize(Integer.MAX_VALUE);
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setOrderBy("sort");
			ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategory(query);
			ehcache.put(elem = new Element(key,result.getModule().getList()));
			
		}
		
		return (List<RyxCategoryDTO>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<RyxCourseQuery> getArticleByCategory(Integer category,Integer pageSize) {

		String key="_getabc_"+ category +"_" + pageSize + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setCategory(category);
			query.setPageSize(pageSize);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<RyxCategoryQuery> getCategoryByPid(Integer pid) {

		String key="_getcbp_"+ pid +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategoryByPid(pid);
			elem = new Element(key,result);
			elem.setTimeToLive(30*60);
			ehcache.put(elem);
		}
		
		return (ResultDTO<RyxCategoryQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<String> getCategoryJsonByPid(Integer pid) {

		String key="_getcjbp_"+ pid +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		ResultDTO<String> r;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			List<KvDTO> kvList = new ArrayList<KvDTO>();
			ResultDTO<RyxCategoryQuery> result = getCategoryByPid(pid);
			
			if(result.isSuccess()){
				List<RyxCategoryDTO> list = result.getModule().getList();
				if(null != list){
					for(RyxCategoryDTO categoryDTO : list){
						KvDTO kv = new KvDTO();
						kv.setK(categoryDTO.getId().toString());
						kv.setV(categoryDTO.getTitle());
						kvList.add(kv);
					}
				}
				String s = JSONArray.fromObject(kvList).toString();
				r =  new ResultDTO<String>(s);
			}
			else{
				r =  new ResultDTO<String>(result.getErrorCode(),result.getErrorMsg());
			}
			
			elem = new Element(key,r);
			ehcache.put(elem);
		}
		
		return (ResultDTO<String>) elem.getObjectValue();
	}
	
	
	public ResultDTO<String> getPosition1JsonByPosition0(String pid) {

		String key="_gp1jbp0_"+ pid +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		ResultDTO<String> r;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			List<KvDTO> kvList = new ArrayList<KvDTO>();
			List<KeyvalueDTO> list = getPosition1ByPosition0(pid);
		
			if(null != list){
				for(KeyvalueDTO kvDTO : list){
					KvDTO kv = new KvDTO();
					kv.setK(kvDTO.getId().toString());
					kv.setV(kvDTO.getKey1());
					kvList.add(kv);
				}
			}
			String s = JSONArray.fromObject(kvList).toString();
			r =  new ResultDTO<String>(s);
		
			elem = new Element(key,r);
			ehcache.put(elem);
		}
		
		return (ResultDTO<String>) elem.getObjectValue();
	}
	
	public ResultDTO<String> getPosition2JsonByPosition1(String p0,String p1) {

		String key="_gp2jbp1_"+ p0 +"_"+ p1 +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		ResultDTO<String> r;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			List<KvDTO> kvList = new ArrayList<KvDTO>();
			List<KeyvalueDTO> list = getPosition2ByPosition1(p0,p1);
		
			if(null != list){
				for(KeyvalueDTO kvDTO : list){
					KvDTO kv = new KvDTO();
					kv.setK(kvDTO.getId().toString());
					kv.setV(kvDTO.getKey1());
					kvList.add(kv);
				}
			}
			String s = JSONArray.fromObject(kvList).toString();
			r =  new ResultDTO<String>(s);
		
			elem = new Element(key,r);
			ehcache.put(elem);
		}
		
		return (ResultDTO<String>) elem.getObjectValue();
	}
	
	
	public EnumObjectType getCategoryTypeById(Integer id) {

		String key="_gctbi_"+ id +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			EnumObjectType result = EnumObjectType.parse(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (EnumObjectType) elem.getObjectValue();
	}
	

	public List<RyxCategoryDTO> getOnlineCategory() {
		return getSubCategoryByCode("new_online_category");
	}
	
	public List<RyxCategoryDTO> getEcourseCategory() {
		return getSubCategoryByCode("new_ecourse_category");
	}
	
	
	/**
	 * 资讯行业
	 * @return
	 */
	public List<RyxCategoryDTO> getInfoCategory() {
		return getSubCategoryByCode("new_info_category");
	}
	
	
	/**
	 * 资讯分类
	 * @return
	 */
	public List<RyxCategoryDTO> getInfoIndustry() {
		return getSubCategoryByCode("ryx_info_industry");
	}
	
	
	public List<RyxCategoryDTO> getArticleCategory() {
		return getSubCategoryByCode("new_article_category");
	}
	
	/**
	 * get article category
	 * @param pageSize
	 * @return
	 */
	public ResultDTO<RyxCategoryQuery> getArticleCategory(Integer pageSize) {
		return getCategoryByType(EnumObjectType.ARTICLE_MODULE.getCode(), pageSize);
	}
	
	
	
	public ResultDTO<RyxCategoryQuery> getShoppingCategory() {

		String key="_gsc_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCategoryQuery query =  new RyxCategoryQuery();
			query.setType(EnumObjectType.CARD_MODULE.getCode());
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxCategoryQuery> result = metaHelper.ryxCategoryService.queryCategory(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCategoryQuery>) elem.getObjectValue();
	}
	
	
	public List<RyxCategoryDTO> getVideoCategory() {
		return getSubCategoryByCode("new_video_category");
	}
	
	
	public ResultDTO<RyxTeacherQuery> getTeacher() {
		
		RyxTeacherQuery query = new RyxTeacherQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setOrderBy("CONVERT(nickname USING gbk)");
		query.setSooort("asc");
		String key = StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	
	public void clearTeacher() {		
		RyxTeacherQuery query = new RyxTeacherQuery();
		query.setPageSize(Integer.MAX_VALUE);
		String key = StringHelper.getObjectString(query);		
		Ehcache ehcache =  getCache("cacheMetadata");		
		Boolean elem =ehcache.remove(key);
	}
	
	
	public ResultDTO<RyxPartnerQuery> getPartners(Integer type) {
		
		RyxPartnerQuery query = new RyxPartnerQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setType(type);
		String key = "_g_pt_"+ type +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxPartnerQuery> result = metaHelper.ryxUserService.queryPartner(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxPartnerQuery>) elem.getObjectValue();
	}
	
	
	
	public List<RyxCategoryDTO> getOfflineCategory() {

		String key="_gfc_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			List<RyxCategoryDTO> result = metaHelper.ryxCategoryService.getOfflineCategory().getModule();
			ehcache.put(elem = new Element(key,result));
		}
		
		return (List<RyxCategoryDTO>) elem.getObjectValue();
	}
	
	
	public List<String> getOfflineCourseCityListOld() {

		String key="_gfct_old_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		ResultDTO<List<KeyvalueDTO>> cityResult = null;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			KeyvQuery query = new KeyvQuery();
			query.setIdeleted(0);
			query.setType(EnumKeyValueType.KV_RYX_OFFLLINE_CITY.getCode());
			ResultDTO<RyxConfigDTO> result = metaHelper.ryxConfigService.getDistrict(74);
			String distrct = result.getModule().getValue();
			String[] districtList = distrct.split(",");
			ehcache.put(elem = new Element(key,Arrays.asList(districtList)));
		}
		
		return (List<String>) elem.getObjectValue();
	}
	
	public List<KeyrvDTO> getOfflineCourseCityList() {

		String key="_gfct_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		ResultDTO<List<KeyvalueDTO>> cityResult = null;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			KeyvQuery query = new KeyvQuery();
			query.setIdeleted(0);
			query.setType(EnumKeyValueType.KV_RYX_OFFLLINE_CITY.getCode());
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);			
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<KeyrvDTO>) elem.getObjectValue();
	}
	
	
	public List<KeyvalueDTO> getActivityCourseCityList() {

		String key="_gac_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		ResultDTO<List<KeyvalueDTO>> cityResult = null;
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			KeyvQuery query = new KeyvQuery();
			query.setIdeleted(0);
			query.setType(EnumKeyValueType.KV_RYX_ACTIVITY_CITY.getCode());
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<KeyvQuery> result = metaHelper.systemService.queryKeyv(query);			
			ehcache.put(elem = new Element(key,result.getModule().getList()));
		}
		
		return (List<KeyvalueDTO>) elem.getObjectValue();
	}
	
	
	public List<String> getSearchKeywordList() {
		return PayConstant.searchKeywordList;
	}
	
	public ResultDTO<RyxCategoryDTO> getCategoryByTeacherId(Long id){
		
		String key="_gcbti_"+ id +"_";

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setTid(id);
			courseQuery.setPageSize(1);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result =  ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key, new ResultDTO<RyxCategoryDTO>(new RyxCategoryDTO())));;
			
			if(result.isSuccess()){
				
				List<RyxCourseDTO> list = result.getModule().getList();
				if(null != list && list.size()>0){
					ehcache.put(elem = new Element(key, getCategoryById(list.get(0).getCategory())));;
				}
			}
		}
		return (ResultDTO<RyxCategoryDTO>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxCategoryDTO> getCategoryByCourseId(Long id, Integer cid){
		String key="_gcbtcourseid_"+ id +"_";

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setId(id);
			courseQuery.setPageSize(1);
			courseQuery.setObjType(cid);
			ResultDTO<RyxCourseQuery> result =  ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key, new ResultDTO<RyxCategoryDTO>(new RyxCategoryDTO())));
			
			
			if(result.isSuccess()){
				
				List<RyxCourseDTO> list = result.getModule().getList();
				if(null != list && list.size()>0){
					ehcache.put(elem = new Element(key, getCategoryById(list.get(0).getCategory())));
				}
			}
		}
		return (ResultDTO<RyxCategoryDTO>) elem.getObjectValue();
	}

	public ResultDTO<RyxCourseQuery> getMyFellowCourse(RyxCourseQuery courseQuery) {
		
		String key =StringHelper.getObjectString(courseQuery);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.getMyFollowCourse(courseQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}


	public ResultDTO<RyxTeacherQuery> getMyFollowTeacher(RyxTeacherQuery teacherQuery) {
		
		String key =StringHelper.getObjectString(teacherQuery);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {			
			
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.getMyFollowTeacher(teacherQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDTO<RyxUsersDTO> getUserById(Long userId) {

		String key = "_gubi_"+ userId +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxUsersDTO> result = metaHelper.ryxUserService.getUserById(userId);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxUsersDTO>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxUsersDTO> getUserByMobileOrEmail(String username) {

		String key = "_gubu_"+ username +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxUsersDTO> result = metaHelper.ryxUserService.getUserByMobileOrEmail(username);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxUsersDTO>) elem.getObjectValue();
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDTO<RyxPartnerDTO> getPartnerById(Long id) {

		String key = "_gpbi_"+ id +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxPartnerDTO> result = metaHelper.ryxUserService.getPartnerById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxPartnerDTO>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxPartnerDTO> getPartnerByUserId(RyxPartnerDTO partnerDTO) {

		String key = "_gpbui_"+ StringHelper.getObjectString(partnerDTO) +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxPartnerDTO> result = metaHelper.ryxUserService.getPartnerByUserId(partnerDTO);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxPartnerDTO>) elem.getObjectValue();
	} 
	
	
	
	public ResultDTO<RyxPartnerOrderDTO> getPartnerOrderByOrderId(Long orderId) {

		String key = "_gpoboi_"+ orderId +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxPartnerOrderDTO> result = metaHelper.ryxOrderService.getPartnerOrderByOrderId(orderId);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxPartnerOrderDTO>) elem.getObjectValue();
	} 
	
	
	public ResultDTO<RyxAnswerQuery> getAnswerByQuestionId(Long qid,Integer pageSize){
		
		String key = "_gabqi_"+ qid +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAnswerQuery answerQuery = new RyxAnswerQuery();			
			answerQuery.setQid(qid);
			answerQuery.setOrderBy("agree");;
			answerQuery.setSooort("desc");
			if(null == pageSize || pageSize == 0){
				answerQuery.setPageSize(Integer.MAX_VALUE);
			}
			else{
				answerQuery.setPageSize(pageSize);
			}
			ResultDTO<RyxAnswerQuery> result = metaHelper.ryxUserService.queryAnswer(answerQuery);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxAnswerQuery>) elem.getObjectValue();
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO<RyxQuestionDTO> getQuestionById(Long id){
		String key = "_gqbi_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxQuestionDTO> result = metaHelper.ryxUserService.getQuestionById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxQuestionDTO>) elem.getObjectValue();
	}
	
	public ResultDTO<RyxAnswerDTO> getAnswerById(Long id){
		String key = "_gabi_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			ResultDTO<RyxAnswerDTO> result = metaHelper.ryxUserService.getAnswerById(id);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxAnswerDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxAdDTO> getAdByCateogryId(Integer category){
		String key = "_gabci"+ category +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAdQuery query = new RyxAdQuery();
			query.setCategory(category);
			query.setPageSize(Integer.MAX_VALUE);
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxAdQuery> result = metaHelper.ryxAdService.queryAd(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxAdDTO>) elem.getObjectValue();
	}
	
	
	public List<RyxAdDTO> getAdByCode(String code){
		String key = "_gabc_"+ code +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAdQuery query = new RyxAdQuery();
			query.setCode(code);
			query.setPageSize(Integer.MAX_VALUE);
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setOrderBy("sort");
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxAdQuery> result = metaHelper.ryxAdService.queryAd(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxAdDTO>) elem.getObjectValue();
	}


	public List<RyxAdDTO> getAdByCode(String code,Integer nbr){
		String key = "_gabc_"+ code +"_" + nbr + "_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAdQuery query = new RyxAdQuery();
			query.setCode(code);
			query.setPageSize(nbr);
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxAdQuery> result = metaHelper.ryxAdService.queryAd(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule().getList():null));
		}
		
		return (List<RyxAdDTO>) elem.getObjectValue();
	}
	
	
	public RyxAdDTO getSingleAdByCode(String code){
		String key = "_gsabc_"+ code +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAdQuery query = new RyxAdQuery();
			query.setCode(code);
			query.setPageSize(1);
			query.setDisplay(1);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setIdeleted(0);
			ResultDTO<RyxAdQuery> result = metaHelper.ryxAdService.queryAd(query);
			ehcache.put(elem = new Element(
					key,result.isSuccess() && 
					null != result.getModule() &&
					null != result.getModule().getList() &&
					result.getModule().getList().size() >0 					
					?result.getModule().getList().get(0):null));
		}
		
		return (RyxAdDTO) elem.getObjectValue();
	}
	
	public String getAppDownloadUrl(){
		
		
		String key = "_get_app_download_url_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {					
			RyxAdDTO adDTO = MetaHelper.getInstance().getSingleAdByCode("app_download_url");
			if(null == adDTO){
				return null;
			}
			ehcache.put(elem = new Element(key,adDTO.getUrl()));	
		}
		
		return (String) elem.getObjectValue();
	}
	
	
	public RyxNewsDTO getSingleNewsByCode(String code){
		String key = "_gsnbc_"+ code +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxNewsQuery query = new RyxNewsQuery();
			query.setCode(code);
			query.setPageSize(1);
			ResultDTO<RyxNewsQuery> result = metaHelper.ryxNewsService.queryNews(query);
			ehcache.put(elem = new Element(
					key,result.isSuccess() && 
					null != result.getModule() &&
					null != result.getModule().getList() &&
					result.getModule().getList().size() >0 					
					?result.getModule().getList().get(0):null));
		}
		
		return (RyxNewsDTO) elem.getObjectValue();
	}
	
	
	public List<RyxAdDTO> getAdCode(String code){
		String key = "_gabc"+ code +"_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxAdQuery query = new RyxAdQuery();
			query.setCode(code);
			query.setPageSize(Integer.MAX_VALUE);
			query.setDisplay(1);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setIdeleted(0);
			ResultDTO<RyxAdQuery> result = metaHelper.ryxAdService.queryAd(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?  result.getModule().getList() : null));
		}
		
		return (List<RyxAdDTO>) elem.getObjectValue();
	}
	
	public WeixinJsapiTicket getWeixinJsapiTicket(){
		
		String key = "_gwjt_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			WeixinJsapiTicket weixinJsapiTicket = WeixinPay.getJsapiTicket();
			elem = new Element(key,weixinJsapiTicket);
			elem.setTimeToLive(weixinJsapiTicket.getExpires() - 10);
			ehcache.put(elem);
		}
		
		return (WeixinJsapiTicket) elem.getObjectValue();
	}


	public ResultDTO<RyxTeacherQuery> getOrgTeacher() {
		String key = "_got_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setFlag(EnumTeacherType.ORG.getCode());
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxTeacherQuery> getOrgTeacher(Integer pageSize) {
		String key = "_got_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(pageSize);
			query.setFlag(EnumTeacherType.ORG.getCode());
			query.setIdeleted(0);
			query.setDisplay(1);
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}
	
	
	
	public ResultDTO<List<RoleDTO>> getRoles() {
				
		RoleQuery query = new RoleQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setIdeleted(0);
		return metaHelper.userService.queryRole(query);
			
	}
	
	
	

	public ResultDTO<List<KeyvalueDTO>> getSecurityQuestion() {
		String key = "_get_s_q_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			KeyvalueQuery query = new KeyvalueQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setIdeleted(0);
			query.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
	}
	
	
	
	
	public ResultDTO<RyxTeacherQuery> getPersonalTeacher() {
		String key = "_gpt_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(Integer.MAX_VALUE);
			query.setFlag(EnumTeacherType.PERSONAL.getCode());
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}


	

	public ResultDTO<RyxTeacherQuery> getPersonalTeacher(Integer pageSize) {
		String key = "_gpt_";
		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxTeacherQuery query = new RyxTeacherQuery();
			query.setPageSize(pageSize);
			query.setFlag(EnumTeacherType.PERSONAL.getCode());
			query.setIdeleted(0);
			query.setDisplay(1);
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.queryTeacher(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxTeacherQuery>) elem.getObjectValue();
	}

	
	
	public ResultDTO<RyxCourseQuery> getMostvisitOnlineCourse(Integer cnt) {
		
		String key = "_gmoc_"+ cnt +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(cnt);
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setDisplay(1);
			query.setIdeleted(0);
			query.setOrderBy("hits");
			query.setSooort("desc");
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxCourseQuery> result = 
					metaHelper.ryxCourseService.queryCourse(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}


	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> getMyMenu(Long userId) {

		String key = "_gmm_"+ userId +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = metaHelper.userService.querySysmenuByUserId(userId.toString());
			elem = new Element(key,result);
			elem.setTimeToLive(5*60);  // 五分钟有效时间
			ehcache.put(elem);
		}
		
		return (ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>) elem.getObjectValue();
	}

	public List<DeptDTO> getDept() {

		String key = "_get_dept_" ;
		//logger.error("getCityByCode--->" + code);
		Ehcache ehcache = getCache("cacheMetadata");
		Element elem = ehcache.get(key);
		if (elem == null) {
			DeptQuery query = new DeptQuery();
			query.setIdeleted(0);
			query.setOrderBy("sort");
			query.setSooort("desc");
			query.setPageSize(Integer.MAX_VALUE);
			ehcache.put(elem = new Element(key, metaHelper.systemService.queryDept(query).getModule().getList()));
		}
		//logger.error("getCityByCode--->" + elem.getObjectValue());
		return (List<DeptDTO>) elem.getObjectValue();

	}
	
	
	public ResultDTO<List<KeyvalueDTO>> getEtitle() {		
		
		String key = "_g_et_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			KeyvalueQuery query = new KeyvalueQuery();
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			query.setType(EnumKeyValueType.KV_EMPLOYEE_TITLE.getCode());
			ResultDTO<List<KeyvalueDTO>> result = metaHelper.systemService.queryKeyvalue(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<List<KeyvalueDTO>>) elem.getObjectValue();
		
	}
	
	public List<RyxCourseDTO> getRelatedCourse(String ids){
		if(StringHelper.isNullOrEmpty(ids)){
			return new ArrayList<RyxCourseDTO>();
		}
		
		
		String key = "_get_r_c_" +ids;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			List<RyxCourseDTO> tempList = null;
			
			RyxCourseDTO rcourse = null;
			ResultDTO<List<RyxCourseDTO>> rlistResult;
			for (String vid : ids.split(",")) {
				rlistResult = MetaHelper.getInstance().getCourseByVId(vid);
				tempList = rlistResult.getModule();
				if (tempList != null && tempList.size() > 0) {
					rcourse = tempList.get(0);
					if (!list.contains(rcourse)) {
						list.add(rcourse);
					}
				}
			}				
			
			rlistResult = MetaHelper.getInstance().getCourseByIds(ids);
			List<RyxCourseDTO> courseList = rlistResult.getModule();
			if(rlistResult.isSuccess() && null!=courseList && courseList.size()>0){
				list.addAll(courseList);
			}
			
			ehcache.put(elem = new Element(key,list));
		}
		
		return (List<RyxCourseDTO>) elem.getObjectValue();
		
	}
	
	
	public Integer getFollowCourseCount(Long id){
		String key = "_gfcc_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxUserFollowCourseQuery query = new RyxUserFollowCourseQuery();
			query.setCourseId(id);
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countUserFollowCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Integer getFollowTeacherCount(Long id){
		String key = "_gftc_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxUserFollowTeacherQuery query = new RyxUserFollowTeacherQuery();
			query.setTeacherId(id);
			ResultDTO<Integer> result = metaHelper.ryxTeacherService.countQueryUserFollowTeacher(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	
	
	public Integer getCourseCountByTeacher(Long tid){
		String key = "_gccby_"+ tid +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setDisplay(1);
			query.setTid(tid);
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<Integer> result = metaHelper.ryxCourseService.countQueryCourse(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Integer) elem.getObjectValue();
	}
	
	public Double getCourseEvalueScore(Long id){
		String key = "_gces_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	public Double getCourseEvalueScore(Long id,Integer type){
		String key = "_gces_"+ id +"_" + type;		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(type);
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule() * 2));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	public Double getCourseEvalueScorePercent(Long id){
		String key = "_gcesp_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()/5*100));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	
	public Double getObjectEvalueScorePercent(Long id,Integer objType){
		String key = "_gcesp_"+ id +"_" + objType + "_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(objType);
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()/5*100));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	
	public Double getArticleEvalueScore(Long id){
		String key = "_gaes_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	
	public Double getArticleEvalueScorePersent(Long id){
		String key = "_gaesp_"+ id +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");
		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjId(id);
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.isSuccess()?result.getModule()/5*100 : 100));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	/**
	 * 讲师对应的user id
	 * @param tuid
	 * @return
	 */
	public Double getTeacherEvalueScore(Long tuid){
		String key = "_gtes_"+ tuid +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjer(tuid);
			query.setIdeleted(0);
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	
	/**
	 * 讲师对应的user id
	 * @param tuid
	 * @return
	 */
	public Double getTeacherEvalueScorePersent(Long tuid){
		String key = "_gtes_"+ tuid +"_";		
		Ehcache ehcache =  getCache("cacheMetadata");		
		Element elem =ehcache.get(key);
		if (elem == null) {		
			
			RyxEvaluQuery query = new RyxEvaluQuery();
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setObjer(tuid);
			query.setIdeleted(0);
			ResultDTO<Double> result = metaHelper.ryxUserService.getEvaluScore(query);
			ehcache.put(elem = new Element(key,result.getModule()/5*100));
		}
		
		return (Double) elem.getObjectValue();
	}
	
	public ResultDTO<RyxCategoryDTO> getCategoryByTeacherIdPC(Long id){
		
		String key="_gcbtipc_"+ id +"_";

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setTid(id);
			courseQuery.setPageSize(1);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result =  metaHelper.ryxCourseService.queryCourse(courseQuery);
			ehcache.put(elem = new Element(key, new ResultDTO<RyxCategoryDTO>(new RyxCategoryDTO())));;
			
			if(result.isSuccess()){
				
				List<RyxCourseDTO> list = result.getModule().getList();
				if(null != list && list.size()>0){
					ehcache.put(elem = new Element(key, getCategoryById(list.get(0).getCategory())));;
				}
			}
		}
		return (ResultDTO<RyxCategoryDTO>) elem.getObjectValue();
	}
	public String getProvinceNameByKey1(String id){
		if (id == null || id.equals("")) {
			return null;
		}else {
			ResultDTO<KeyvalueDTO> result = metaHelper.systemService.queryKeyvalueByKey1(id);
			return result.getModule().getValue();
		}

	}
	
	public List<RyxSmallTeacherDTO> getSmallTeacher(RyxTeacherQuery query){
		
		String key = "_gst_"+StringHelper.getObjectString(query);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxTeacherQuery> result = metaHelper.ryxTeacherService.getMyFollowTeacher(query);
			List<RyxSmallTeacherDTO> list = new ArrayList<RyxSmallTeacherDTO>();
			if(null != result.getModule().getList()){
				for(RyxTeacherDTO teacherDTO : (List<RyxTeacherDTO>)result.getModule().getList()){
					RyxSmallTeacherDTO smallTeacherDTO = new RyxSmallTeacherDTO();
					BeanUtils.copyProperties(teacherDTO, smallTeacherDTO,BeanHelper.getNullPropertyNames(teacherDTO));
					smallTeacherDTO.setCcnt(MetaHelper.getInstance().getCourseCountByTeacher(teacherDTO.getId()));
					smallTeacherDTO.setEval(StringHelper.double2Integer(MetaHelper.getInstance().getTeacherEvalueScorePersent(teacherDTO.getUid())));
					list.add(smallTeacherDTO);
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		return (List<RyxSmallTeacherDTO>) elem.getObjectValue();
	}
	
	public List<RyxSmallCourseDTO> getSmallCourse(RyxCourseQuery query){
		
		String key = "_gsc_"+StringHelper.getObjectString(query);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxCourseQuery> followCourseResult = metaHelper.ryxCourseService.getMyFollowCourse(query);
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			if(null != followCourseResult.getModule().getList()){
				for(RyxCourseDTO courseDTO : (List<RyxCourseDTO>)followCourseResult.getModule().getList()){
					RyxSmallCourseDTO smallCourseDTO = new RyxSmallCourseDTO();
					BeanUtils.copyProperties(courseDTO, smallCourseDTO, BeanHelper.getNullPropertyNames(courseDTO));
					int scnt = CourseHelper.getInstance().getKeyrvCount(courseDTO.getId().toString()
							,EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
					if (scnt>0) {
						smallCourseDTO.setTitle(smallCourseDTO.getTitle()+"(共"+scnt+"集)");
					}
					list.add(smallCourseDTO);
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		return (List<RyxSmallCourseDTO>) elem.getObjectValue();
	}
	public List<RyxSmallMessageDTO> getSmallMessag(RyxMessageQuery query){
		
		String key = "_gsm_"+StringHelper.getObjectString(query);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxMessageQuery> result = metaHelper.ryxUserService.queryMyMessage(query);
			List<RyxSmallMessageDTO> list = new ArrayList<RyxSmallMessageDTO>();
			if(null != result.getModule().getList()){
				for(RyxMessageDTO messageDTO : (List<RyxMessageDTO>)result.getModule().getList()){
					RyxSmallMessageDTO smallMessageDTO = new RyxSmallMessageDTO();
					BeanUtils.copyProperties(messageDTO, smallMessageDTO, BeanHelper.getNullPropertyNames(messageDTO));
					smallMessageDTO.setLcreateTime(DateHelper.second2String("yyyy-MM-dd" ,messageDTO.getLcreate()));
					list.add(smallMessageDTO);
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		return (List<RyxSmallMessageDTO>) elem.getObjectValue();
	}
	public List<RyxSmallEvaluDTO> getSmallEvalu(RyxEvaluQuery query){
		
		String key = "_gse_"+StringHelper.getObjectString(query);

		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {	
			ResultDTO<RyxEvaluQuery> queryResult = metaHelper.queryEvalu(query);
			List<RyxSmallEvaluDTO> list = new ArrayList<RyxSmallEvaluDTO>();
			if(null != queryResult.getModule().getList()){
				for(RyxEvaluDTO evaluDTO : (List<RyxEvaluDTO>)queryResult.getModule().getList() ){
					RyxUsersDTO user = metaHelper.getUserById(evaluDTO.getUserId()).getModule();
					if(null != user){
						RyxSmallEvaluDTO smallEvaluDTO = new RyxSmallEvaluDTO();
						BeanUtils.copyProperties(evaluDTO, smallEvaluDTO,BeanHelper.getNullPropertyNames(evaluDTO));
						if(StringHelper.isNullOrEmpty(user.getPath())){
							smallEvaluDTO.setPath(ConstHelper.getDefaultImage());
						}
						else{
							smallEvaluDTO.setPath(user.getPath());
						}
						smallEvaluDTO.setUserString(StringHelper.getFuzzyUsername(user));
						smallEvaluDTO.setLcreateTime(DateHelper.long2String("yyyy-MM-dd",evaluDTO.getLcreate()*1000));
						list.add(smallEvaluDTO);
					}
				}
			}
			ehcache.put(elem = new Element(key,list));
		}
		return (List<RyxSmallEvaluDTO>) elem.getObjectValue();
	}
	
	
	public ResultDTO<RyxCourseQuery> queryCourseCache1(RyxCourseQuery query){
		
		String key =StringHelper.getObjectString(query);
		
		Ehcache ehcache =  getCache("cacheMetadata");
		Element elem =ehcache.get(key);
		if (elem == null) {
			ResultDTO<RyxCourseQuery> result = metaHelper.ryxCourseService.queryDefaultCourse1(query);
			ehcache.put(elem = new Element(key,result));
		}
		
		return (ResultDTO<RyxCourseQuery>) elem.getObjectValue();
	}


}
