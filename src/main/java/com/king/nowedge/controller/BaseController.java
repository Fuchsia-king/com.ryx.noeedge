package com.king.nowedge.controller;

import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.NoticeDTO;
import com.king.nowedge.dto.RecruitmentDTO;
import com.king.nowedge.dto.UserDTO;
import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.LoreQuery;
import com.king.nowedge.dto.query.RecruitmentQuery;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.query.base.LoreBaseQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.RyxApplyQuery;
import com.king.nowedge.dto.ryx.query.RyxCategoryQuery;
import com.king.nowedge.dto.ryx.query.RyxObjectLimitQuery;
import com.king.nowedge.dto.ryx.query.RyxTeacherQuery;
import com.king.nowedge.dto.ryx2.validate.OnlineDTO;
import com.king.nowedge.helper.*;
import com.king.nowedge.service.*;
import com.king.nowedge.service.ryx.*;
import com.king.nowedge.utils.StringExUtils;
import com.king.nowedge.wxpay.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class BaseController {
	
	
	protected static Integer DEFAULT_PAGE_SIZE = 12 ;
	
	protected static Integer DEFAULT_PAGE_SIZE1 = 22 ;
	
	/**
	 * 最大可用购物券
	 */
	protected static Double MAX_USED_COUPON = 100.00 ; 

	@Resource(name = "loginService")
	protected LoginService loginService;

	@Resource(name = "userService")
	protected UserService userService;

	@Resource(name = "noticeService")
	protected NoticeService noticeService;

	@Resource(name = "systemService")
	protected SystemService systemService;

	@Resource(name = "loreService")
	protected LoreService loreService;

	@Resource(name = "messageService")
	protected MessageService messageService;

	@Resource(name = "taskService")
	protected TaskService taskService;


	@Resource(name = "consumerService")
	protected ConsumerService consumerService;


	@Resource(name = "productService")
	protected ProductService productService;

	@Resource(name = "industryService")
	protected IndustryService industryService;
	
	protected static Long MAX_CHAT_ROOM_USER_NBR = 200L;
	
	
	/**--------------------------------------------
	 * 
	 *  ryx service 
	 * 
	 --------------------------------------------*/
	@Resource(name = "ryxUserService")
	protected RyxUserService ryxUserService;
	
	@Resource(name = "ryxAdService")
	protected RyxAdService ryxAdService;
	
	
	@Resource(name = "ryxCategoryService")
	protected RyxCategoryService ryxCategoryService;
	
	@Resource(name = "ryxConfigService")
	protected RyxConfigService ryxConfigService;
	
	@Resource(name = "ryxCourseService")
	protected RyxCourseService ryxCourseService;
	
	
	@Resource(name = "ryxCrmService")
	protected RyxCrmService ryxCrmService;
	
	
	@Resource(name = "ryxFinanceService")
	protected RyxFinanceService ryxFinanceService;
	
	@Resource(name = "ryxMailService")
	protected RyxMailService ryxMailService;
	
	
	@Resource(name = "ryxNewsService")
	protected RyxNewsService ryxNewsService;
	
	@Resource(name = "ryxOrderService")
	protected RyxOrderService ryxOrderService;
	
	
	@Resource(name = "ryxTeacherService")
	protected RyxTeacherService ryxTeacherService;
	
	
	
	

	private static final Log logger = LogFactory.getLog(BaseController.class);

	public static String CHARACTER_ENCODING = "UTF-8";

	protected Integer pageSize = 20;

	protected ArrayList<String> errList = new ArrayList<String>();
	protected ArrayList<String> searchKeyword = new ArrayList<String>();

	protected static String __FORGET_PASSWORD_USER__ = "__FORGET_PASSWORD_USER__";
	protected static String __FORGET_PASSWORD_USER_QUESTION__ = "__FORGET_PASSWORD_USER_QUESTION__";
	

	

	protected String splitSign = ";";
	
	protected String getHuanxinPassword(Long id){
		return id + "$#!@*(K%$";
	}
	
			
	protected ArrayList<RyxDiscountIntervalDTO> discountIntervalArray = new ArrayList<RyxDiscountIntervalDTO>(){{
				 add(new RyxDiscountIntervalDTO(0.0  , 100.0 , 0.99));
				 add(new RyxDiscountIntervalDTO(100.0 , 200.0 , 0.98));
				 add(new RyxDiscountIntervalDTO(200.0 , 500.0 , 0.96));
				 add(new RyxDiscountIntervalDTO(500.0 , 1000.0 , 0.90));
				 add(new RyxDiscountIntervalDTO(1000.0 , 2000.0 , 0.85));
				 add(new RyxDiscountIntervalDTO(2000.0 , Double.MAX_VALUE , 0.80));
	}};
	
	

	@Autowired
	Validator validator;

	protected RyxUsersDTO getRyxUser() {

		try {

			Object obj = SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			UserDetails userDetails = (UserDetails) obj;

			JSONObject jsonObject = JSONObject.fromObject( userDetails.getUsername());			
			
			return (RyxUsersDTO)JSONObject.toBean(jsonObject, RyxUsersDTO.class);

		} catch (Throwable t) {

			return null;
		}
	}
	
	protected RyxUsersDTO getUser() {
		return getRyxUser();
	}
	

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected List<UserDTO> getAllUsers() {

		// UserCache cache = new UserCache(userService);
		//
		// List<UserDTO> list = new ArrayList<UserDTO>();
		// NoticeQuery noticeQuery = new NoticeQuery();
		// noticeQuery.setOrderBy("tcreate");
		// noticeQuery.setSooort("desc");
		// noticeQuery.setPageSize(5);
		// noticeQuery.setStartRow(0);
		//
		// ResultDTO<List<UserDTO>> result = cache.getCachedUserAll();
		//
		// if(result.isSuccess()){
		// list = result.getModule();
		// }
		// else{
		// errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		// }
		// return list;

		return null;

	}

	/**
	 * 
	 * @return
	 */
	protected List<LoreDTO> getFaq() {
		List<LoreDTO> list = new ArrayList<LoreDTO>();
		LoreQuery loreQuery = new LoreQuery();
		loreQuery.setOrderBy("visit");
		loreQuery.setSooort("desc");
		loreQuery.setPageSize(20);
		loreQuery.setStartRow(0);

		ResultDTO<List<LoreDTO>> result = loreService.queryLore(loreQuery);

		if (result.isSuccess()) {
			list = result.getModule();
		} else {
			errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		}
		return list;
	}

	/**
	 * 
	 * @return
	 */
	protected List<NoticeDTO> getNotice() {

		// NoticeCache noticeCache = new NoticeCache(noticeService);
		//
		// List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		// NoticeQuery noticeQuery = new NoticeQuery();
		// noticeQuery.setOrderBy("tcreate");
		// noticeQuery.setSooort("desc");
		// noticeQuery.setPageSize(5);
		// noticeQuery.setStartRow(0);
		//
		// ResultDTO<List<NoticeDTO>> result =
		// noticeCache.getCachedNotice(noticeQuery) ;
		//
		// if(result.isSuccess()){
		// list = result.getModule();
		// }
		// else{
		// errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		// }
		// return list;

		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected List<String> getTags() {

		// LoreCache loreCache = new LoreCache(loreService);
		//
		// List<String> list = new ArrayList<String>();
		// LoreTagQuery loreTagQuery = new LoreTagQuery();
		// loreTagQuery.setOrderBy("visit");
		// loreTagQuery.setSooort("desc");
		// loreTagQuery.setPageSize(20);
		// loreTagQuery.setStartRow(0);
		//
		// ResultDTO<List<String>> result =
		// loreCache.getCachedTagStringList(loreTagQuery) ;
		//
		// if(result.isSuccess()){
		// list = result.getModule();
		// }
		// else{
		// errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		// }
		// return list;

		return null;

	}

	protected void writeAjax(HttpServletResponse response, boolean isSuccess) {
		writeAjax(response, isSuccess, null, null);
	}

	protected void writeAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg) {
		writeAjax(response, isSuccess, errorMsg, null);
	}

	/**
	 * 
	 * @param response
	 * @param isSuccess
	 * @param errorMsg
	 * @param obj
	 */
	protected void writeAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg, Object obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("isSuccess", isSuccess);
			json.put("obj", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("errorMsg", errorMsg);
			}
			writeAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	
	
	protected void writeAjax(HttpServletResponse response, boolean isSuccess,Object obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("isSuccess", isSuccess);
			json.put("obj", obj);
			writeAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	protected void writeAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg , List obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("isSuccess", isSuccess);
			json.put("obj", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("errorMsg", errorMsg);
			}
			writeAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	protected void writeAjax(HttpServletResponse response, boolean isSuccess, List obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("isSuccess", isSuccess);
			json.put("obj", obj);
			writeAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}

	protected void writeAjax(HttpServletResponse response, boolean isSuccess,
			String errorCode, String errorMsg, Object obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("isSuccess", isSuccess);
			json.put("obj", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("errorCode", errorCode);
				json.put("errorMsg", errorMsg);
			}
			writeAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param isSuccess
	 * @param errorMsg
	 * @param obj
	 */
	protected void writeAjax(HttpServletRequest request,
			HttpServletResponse response, boolean isSuccess, String errorMsg,
			Object obj) {
		writeAjax(response, isSuccess, errorMsg, obj);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	protected void writeAjax(HttpServletRequest request,
			HttpServletResponse response, JSONObject json) throws IOException {
		writeAjax(response, json);
	}

	/**
	 * 
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	protected void writeAjax(HttpServletResponse response, JSONObject json)
			throws IOException {
		if (null == json) {
			response.getWriter().write("");
			;
		} else {
			response.getWriter().write(json.toString());
			;
		}
	}

	
	/**
	 * 
	 * @return
	 */
	protected List<KeyvalueDTO> getCountryRealtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
		keyvalueQuery.setPageSize(Integer.MAX_VALUE);
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	
	/**
	 * 
	 * @return
	 */
	protected List<KeyvalueDTO> getProvinceRealtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
		keyvalueQuery.setPageSize(Integer.MAX_VALUE);
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getCountryCached() {

		ResultDTO<List<KeyvalueDTO>> resultDTO = MetaHelper
				.getInstance().getCountry();
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getCityCached() {

		return MetaHelper
				.getInstance().getCity();
	}

	protected List<KeyvalueDTO> getAreaCached() {

		return MetaHelper
				.getInstance().getArea();
	}

	protected List<KeyvalueDTO> getProvinceCached() {

		return MetaHelper
				.getInstance().getProvince();
	}

	protected List<KeyvalueDTO> getCityRealtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getAreaRealtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getIndustry0Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getIndustry1Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getIndustry2Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getSpecialty0Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getSpecialty1Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected List<KeyvalueDTO> getSpecialty2Realtime() {

		KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService
				.queryKeyvalue(keyvalueQuery);
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}

	}

	public String getCompanyNameByIdRealtime(Long id) {

		CompanyDTO companyDTO = getCompanyByIdRealtime(id);
		if (null != companyDTO) {

			return companyDTO.getName();
		}

		return null;

	}

	public CompanyDTO getCompanyByIdRealtime(Long id) {

		ResultDTO<CompanyDTO> resultDTO = userService.queryCompanyById(id);
		if (resultDTO.isSuccess()) {

			return resultDTO.getModule();
		} else {
			errList.add(resultDTO.getErrorMsg());
		}

		return null;

	}

	// protected List<KeyvalueDTO> getCachedAllSpecialty2(){
	//
	// String key = "__Cached_getAllSpecialty2__";
	// Ehcache ehcache = getCache("cacheMetadata");
	// Element elem =ehcache.get(key);
	// if (elem == null) {
	// KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
	// keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
	// ResultDTO<List<KeyvalueDTO>> resultDTO =
	// systemService.queryKeyvalue(keyvalueQuery);
	// if(resultDTO.isSuccess()){
	// ehcache.put(elem = new Element(key,resultDTO.getModule()));
	//
	// }else{
	// errList.add(resultDTO.getErrorMsg());
	// return null;
	// }
	// }
	// return (List<KeyvalueDTO>) elem.getObjectValue();
	// }

	// protected List<KeyvalueDTO> getCachedAllSpecialty0(){
	//
	// String key = "__Cached_getAllSpecialty0__";
	// Ehcache ehcache = getCache("cacheMetadata");
	// Element elem =ehcache.get(key);
	// if (elem == null) {
	// KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
	// keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
	// ResultDTO<List<KeyvalueDTO>> resultDTO =
	// systemService.queryKeyvalue(keyvalueQuery);
	// if(resultDTO.isSuccess()){
	// ehcache.put(elem = new Element(key,resultDTO.getModule()));
	//
	// }else{
	// errList.add(resultDTO.getErrorMsg());
	// return null;
	// }
	// }
	// return (List<KeyvalueDTO>) elem.getObjectValue();
	// }

	public List<KeyvalueDTO> getIndustry0Cached() {
		return MetaHelper
				.getInstance().getIndustry0();
	}

	public List<KeyvalueDTO> getSpecialty0Cached() {
		ResultDTO<List<KeyvalueDTO>> resultDTO = MetaHelper
				.getInstance().getSpecialty0();
		if (resultDTO.isSuccess()) {
			return resultDTO.getModule();

		} else {
			errList.add(resultDTO.getErrorMsg());
			return null;
		}
	}

	protected RecruitmentQuery queryRecruitment(
			RecruitmentQuery recruitmentQuery) {

		if (null == recruitmentQuery.getPageSize()
				|| recruitmentQuery.getPageSize() == 0) {
			recruitmentQuery.setPageSize(20);
		}

		if (null == recruitmentQuery.getCurrentPage()
				|| recruitmentQuery.getCurrentPage() == 0) {
			recruitmentQuery.setCurrentPage(1);
		}

		ResultDTO<List<RecruitmentDTO>> result = userService
				.queryRecruitment(recruitmentQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			recruitmentQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = userService
				.countQueryRecruitment(recruitmentQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		recruitmentQuery.setTotalItem(totalItem);

		return recruitmentQuery;

	}
	
	protected ArrayList<String> addList(ArrayList<String> list,String str){
		
		if(!StringExUtils.isNullOrEmpty(str)){
			list.add(str);
			logger.error(str);
		}
		return list;
	}
	
	public boolean isMobile (String userAgent){
		return true;
	}
	
	protected String getRyxOrderId(RyxOrderDTO order){
		return "R"+  DateHelper.long2String( "yyyyMMddHHmmsssss",(long)order.getOrderTime() * 1000);
	}
	
	protected String getRyxOrderId(Integer orderTime){
		return "R"+  DateHelper.long2String( "yyyyMMddHHmmsssss",(long)orderTime * 1000);
	}
	


	
	
	

	protected Boolean isWeixinExplorer(HttpServletRequest request) {
		Boolean b = false;
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			b = true;
		}
		return b;
	}
	
	
	protected Boolean isMobileWeixinExplorer(HttpServletRequest request) {
		Boolean b = false;
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0 && ua.indexOf("windowswechat") < 0) {// 是微信浏览器
			b = true;
		}
		return b;
	}
	
	
	protected Boolean isWindowsWeixinExplorer(HttpServletRequest request) {
		Boolean b = false;
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("windowswechat") > 0) {// 是微信浏览器
			b = true;
		}
		return b;
	}
	
	
	protected Boolean isUcExplorer(HttpServletRequest request) {
		Boolean b = false;
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("ucweb") > 0 || ua.indexOf("ucbrowser") > 0) {// 是微信浏览器
			b = true;
		}
		return b;
	}

	protected void addCookie(HttpServletResponse response,String key ,String value ,Integer expired){
		Cookie cookie = new Cookie(key, value );
		cookie.setMaxAge(expired);  // 30天  	
		response.addCookie(cookie);			
	}
	
	
	/**
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	protected String getCookies(String key ,HttpServletRequest request){	
		
		if(StringHelper.isNullOrEmpty(key)) return null;
		
		Cookie[] cookieList = request.getCookies();
		
		if(null!=cookieList){
			
			for(Cookie cookie : cookieList){
				
				try{
					if(key.equals(cookie.getName())){
						return cookie.getValue();
					}
				}
				catch(Exception e){
					//errList.add(e.getMessage());
				}
			}
		}		
		
		return null;		
		
	}
	
	
	protected String removeCookies(String key ,String path,
			HttpServletRequest request,
			HttpServletResponse response){	
		
		if(StringHelper.isNullOrEmpty(key)) return null;
		
		Cookie[] cookieList = request.getCookies();
		
		if(null!=cookieList){
			
			for(Cookie cookie : cookieList){
				
				try{
					if(key.equals(cookie.getName())){
						cookie = new Cookie(key, null);
						cookie.setMaxAge(0);
						cookie.setPath(path);
						response.addCookie(cookie); 
					}
				}
				catch(Exception e){
					logger.error(e);
				}
			}
		}		
		
		return null;		
		
	}
	
	
	


	protected Object getExploreType(HttpServletRequest request) {
		
		String ua = request.getHeader("User-Agent");
		return ua;
	}

	protected String getCurrentUrl(HttpServletRequest request){
		return "http://" + request.getServerName() //服务器地址 
		+ request.getContextPath() //项目名称 
		+ request.getServletPath() //请求页面或其他地址
		+  (StringHelper.isNullOrEmpty(request.getQueryString())?"": ("?"+request.getQueryString())) ;
	}
	
	
	protected void addPasswordModel(ModelAndView mav,HttpServletRequest request,String targetUrl){
		
		try {
			mav.addObject("username", StringHelper.aesDecrypt(CookieHelper.getCookies(SessionHelper.LOGIN_USERNAME_COOKIE, request, "/"),
					SessionHelper.ENCRIPT_DECRIPT_KEY));
			mav.addObject("password", StringHelper.aesDecrypt(CookieHelper.getCookies(SessionHelper.LOGIN_USER_PASSWORD_COOKIED, request, "/"), 
					SessionHelper.ENCRIPT_DECRIPT_KEY));
			
		} catch (Exception e) {
		}			
		
		if(StringHelper.isNullOrEmpty(targetUrl)){
			targetUrl = CookieHelper.getCookies( SessionHelper.LATEST_URL_SESSION, request, "/");
			
			if(StringExUtils.isNullOrEmpty(targetUrl)
					|| targetUrl.indexOf("register") >0
					||  targetUrl.indexOf("reset_password") >0
					||  targetUrl.indexOf("login") >0
					||  targetUrl.indexOf("ajax") >0
					||  targetUrl.indexOf("default.html") >0
					){
				
				targetUrl = "/index.html" ;
				
			}
		}
		else{
			if(StringExUtils.isNullOrEmpty(targetUrl)
					|| targetUrl.indexOf("register") >0
					||  targetUrl.indexOf("reset_password") >0
					||  targetUrl.indexOf("login") >0
					||  targetUrl.indexOf("ajax") >0
					||  targetUrl.indexOf("default.html") >0
					){
				
				targetUrl = "/index.html" ;
				
			}
		}
		
		mav.addObject("request", request);
	
		
		mav.addObject("targetUrl", targetUrl);
		
	}
	
	protected LoreBaseQuery getObject(ResultDTO<LoreBaseQuery> r){
		if(r.isSuccess()){
			return r.getModule();
		}
		else{
			return new LoreBaseQuery();
		}
	}
	
	
	protected ModelAndView setCreateAdminActivityObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	

	protected ModelAndView setCreateActivityObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);
		
		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	
	
	
	protected ModelAndView setListRecruitObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);		
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	
	protected ModelAndView setListCommerceObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);		
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}

	
	protected ModelAndView setListActivityObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);		
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	protected ModelAndView setArticleObject(ModelAndView mav,ArrayList<String> errList){
		
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		
		mav.addObject("categorys",categoryResult.getModule().getList());
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	
	protected ModelAndView setCreateArticleObject(ModelAndView mav,ArrayList<String> errList){
		
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		
		mav.addObject("categorys",categoryResult.getModule().getList());	
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	


	
	protected ModelAndView setCreateAdminArticleObject(ModelAndView mav,ArrayList<String> errList){
		
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		
		mav.addObject("categorys",categoryResult.getModule().getList());
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	protected ModelAndView setListArticleObject(ModelAndView mav,ArrayList<String> errList){
		
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		
		mav.addObject("categorys",categoryResult.getModule().getList());	
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	
	protected ModelAndView setCreateOfflineObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);		
		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	protected ModelAndView setCreateRecruitObject(ModelAndView mav,ArrayList<String> errList){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());		
		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}
	
	
	protected ModelAndView setCreateCommerceObject(ModelAndView mav,ArrayList<String> errList){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());		
		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		
		mav.addObject("citys", cityListResult);
		
		return mav;
	}

	
	protected ModelAndView setCreateAdminOfflineObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		return mav;
	}
	
	protected ModelAndView setListOfflineObject(ModelAndView mav,ArrayList<String> errList){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys",categoryResult);		

		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		mav.addObject("citys", cityListResult);
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	
	protected ModelAndView setCreateOnlineObject(ModelAndView mav, OnlineDTO onlineDTO, ArrayList<String> errList){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
		
		if(null != onlineDTO.getCategory()){
			mav.addObject("subcates", MetaHelper.getInstance().getCategoryByPid(onlineDTO.getCategory()).getModule().getList());
		}
		
		if(null != onlineDTO.getSubcate()){
			mav.addObject("tcates", MetaHelper.getInstance().getCategoryByPid(onlineDTO.getSubcate()).getModule().getList());
		}
		
		mav.addObject("difficultys", EnumDifficultyType.getList());
		
		return mav;
	}

	
	protected ModelAndView setCreateAdminOnlineObject(ModelAndView mav,ArrayList<String> errList){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		return mav;
	}
	
	protected ModelAndView setListOnlineObject(ModelAndView mav,ArrayList<String> errList){

		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());			
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("difficultys", EnumDifficultyType.getList());
		
		return mav;
	}
	
	
	/**
	 * 
	 * @param
	 */
	protected void updateMainCourse(RyxCourseDTO updateCourseDTO){
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setKey1(updateCourseDTO.getId().toString());
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvQuery.setIdeleted(0);
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		List<KeyrvDTO> list = systemService.queryKeyrv(keyrvQuery).getModule().getList();
		Double price = 0.0 ;
		Long duration = 0L;
		for(KeyrvDTO keyrvDTO : list){
			RyxCourseDTO courseDTO = ryxCourseService.getCourseById(Long.parseLong(keyrvDTO.getRkey())).getModule();
			price += null == courseDTO.getPrice() ? 0.0 : courseDTO.getPrice();
			duration += DateHelper.hhmmss2Second(courseDTO.getDuration());
		}
		
		updateCourseDTO.setDuration(DateHelper.secToTime(duration));
		
		/**
		 * 必须体系购买的课程价格不更新
		 */
		if(!MetaHelper.getInstance().isKeyv(updateCourseDTO.getId(),33)){
			updateCourseDTO.setOprice(price);
			updateCourseDTO.setPrice(price *88 / 100);
		}
		ryxCourseService.updateCourse(updateCourseDTO);
	}
	
	
	
	/**
	 * 
	 * @param subCourse
	 */
	protected void updateMainCourseBySubCourse(RyxCourseDTO subCourse){
		KeyrvQuery query = new KeyrvQuery();
		query.setRkey(subCourse.getId().toString());
		query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		query.setPageSize(1);
		ResultDTO<KeyrvQuery> resultDTO = systemService.queryKeyrv(query);
		if(resultDTO.isSuccess() && resultDTO.getModule().getList().size() ==1){
			KeyrvDTO keyrvDTO = (KeyrvDTO)resultDTO.getModule().getList().get(0);
			RyxCourseDTO mainCourseDTO = new RyxCourseDTO();
			mainCourseDTO.setId(Long.parseLong(keyrvDTO.getKey1()));
			mainCourseDTO.setCreater(subCourse.getCreater());
			updateMainCourse(mainCourseDTO);
		}
	}
	
	
	protected String getArticleDescr(String url) throws Exception{
		try{
			String string = HttpHelper.get("http://ow365.cn/?i=11610&info=0&words=5000&furl="+URLEncoder.encode(StringHelper.encodeOffice365Url(url),"utf-8")).getModule();
			JSONObject json = JSONObject.fromObject(string);
			if(url.indexOf(".ppt")>0 || url.indexOf(".pptx") >0 ){
				string = HtmlHelper.delHtmlTag(json.getString("SlideNames")).replaceAll("\r", "").replaceAll("\r\n", "").replaceAll(" ", "").replaceAll("	", "").replaceAll("　", "");
			}
			else{
				string = HtmlHelper.delHtmlTag(json.getString("Text")).replaceAll("\r", "").replaceAll("\r\n", "").replaceAll(" ", "").replaceAll("	", "").replaceAll("　", "");
			}
			return string.length() > 168 ? string.substring(0, 166) : string;
		}
		catch(Throwable t){
			logger.error(t.getMessage(),t);
			return "";
		}
	}
	
	
	
	protected Boolean isBuyOnline(RyxCourseDTO courseDTO,Long userId,Long videoId,Long subId){
		
		if(null == userId){
			return false;
		}
		
		if(null != courseDTO && userId.equals(courseDTO.getCuid())){
			return true;
		}		
			

		Long tnow = System.currentTimeMillis() / 1000;
		
		/**
		 * 购买课程判断
		 */
		if(null != subId){
			ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(userId, subId, tnow);
			errList = addList(errList, buyResult.getErrorMsg());
			if(buyResult.getModule()>0){
				return true;
			}
		}
			
		/**
		 * 年卡判断
		 */
		RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
		objectLimitQuery.setUserId(userId);
		objectLimitQuery.setSlimi(tnow);
		objectLimitQuery.setCategory(courseDTO.getCategory());
		objectLimitQuery.setOtype(EnumObjectType.CARD_MODULE.getCode());
		ResultDTO<Integer> cardNbrResultDTO = ryxCourseService.countQueryObjectLimit(objectLimitQuery);
		if(cardNbrResultDTO.getModule()>0){
			return true;
		}
		
		
	
		
				
		/**
		 * 购买视频判断
		 */
		if(null != videoId && videoId > 0){
			RyxApplyQuery applyQuery = new RyxApplyQuery();
			applyQuery.setUserId(userId);
			applyQuery.setPageSize(1);
			applyQuery.setOtypes(new ArrayList<Integer>(Arrays.asList(
					EnumObjectType.VIDEO_MODULE.getCode(),
					EnumObjectType.OFFLINE_MODULE.getCode()
				)));	
			applyQuery.setStatuss(new ArrayList<Integer>(Arrays.asList(
					EnumOrderStatus.FREE.getCode(),
					EnumOrderStatus.ORG_PRESENT.getCode(),
					EnumOrderStatus.PAY_SUCCESS.getCode()
				)));	
			
			applyQuery.setOid(videoId);
			ResultDTO<Integer> applyNbr = ryxUserService.countApplyNbr(applyQuery);
			if(null != applyNbr.getModule() && applyNbr.getModule()>0){
				return true;
			}
		}
		
		
		/**
		 * 根据课程id找到对应的视频id
		 */
		RyxCourseDTO videoCourseDTO = CourseHelper.getInstance().getCourseByVid(courseDTO.getId());
		
		logger.error("videoCourseDTO --->" + videoCourseDTO);
		logger.error("videoCourseDTO userId --->" + userId);
		
		if(null!= videoCourseDTO){
			RyxApplyQuery applyQuery = new RyxApplyQuery();
			applyQuery.setUserId(userId);
			applyQuery.setPageSize(1);
			applyQuery.setOtypes(new ArrayList<Integer>(Arrays.asList(
					EnumObjectType.VIDEO_MODULE.getCode(),
					EnumObjectType.OFFLINE_MODULE.getCode()
				)));	
			applyQuery.setStatuss(new ArrayList<Integer>(Arrays.asList(
					EnumOrderStatus.FREE.getCode(),
					EnumOrderStatus.ORG_PRESENT.getCode(),
					EnumOrderStatus.PAY_SUCCESS.getCode()
				)));	
			
			applyQuery.setOid(videoCourseDTO.getId());
			logger.error("videoCourseDTO setOid --->" + videoCourseDTO.getId());
			
			
			
			ResultDTO<Integer> applyNbr = ryxUserService.countApplyNbr(applyQuery);
			
			logger.error("videoCourseDTO applyNbr --->" + applyNbr);
			logger.error("videoCourseDTO applyNbr --->" + applyNbr.getModule());
			if(null != applyNbr.getModule() && applyNbr.getModule()>0){
				return true;
			}
		}
				
		return false;
	}
	
	
	protected String getPartnerUstring(){
		try{
			RyxUsersDTO usersDTO = getRyxUser();
			if(null!=usersDTO){
				RyxPartnerDTO partnerDTO = new RyxPartnerDTO();
				partnerDTO.setType(EnumPartnerType.LINK_PARTNER.getCode());
				partnerDTO.setUserId(usersDTO.getId());
				ResultDTO<RyxPartnerDTO> result =  MetaHelper.getInstance().getPartnerByUserId(partnerDTO);
				if(result.isSuccess()){
					partnerDTO = result.getModule();
					if(null != partnerDTO && null != partnerDTO.getId()){					
						return URLEncoder.encode(StringHelper.aesEncrypt(partnerDTO.getUserId().toString(), RequestHelper.PARNTER_URL_SEED),"utf-8");
					}
				}
			}
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return "";
	}
	
	
	protected ModelAndView getWeixinShareParams(
			ModelAndView mav ,
			HttpServletRequest request,
			WeixinShareDTO weixinShareDTO){
		
		Long timestamp = System.currentTimeMillis()/1000;
		String nonceStr = WeixinPay.getNonceStr();
		mav.addObject("appId", PayConstant.appid);
		mav.addObject("timestamp", timestamp.toString());
		mav.addObject("nonceStr",nonceStr);
		
		WeixinJsapiTicket weixinJsapiTicket = MetaHelper.getInstance().getWeixinJsapiTicket();
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		
		/**
		 * 判断是否是 link partner
		 */
		
		String urlString = StringHelper.isNullOrEmpty(weixinShareDTO.getShareLink()) ? "https://" + request.getServerName() //服务器地址 
				+ request.getContextPath() //项目名称 
				+ request.getServletPath() //请求页面或其他地址
				+  (StringHelper.isNullOrEmpty(request.getQueryString())?"": ("?"+request.getQueryString())) : weixinShareDTO.getShareLink();
		
//		UsersDTO usersDTO = getRyxUser();
//		if(null != usersDTO){
//			Long userId = usersDTO.getId();
//			ResultDTO<PartnerDTO> resultDTO = ryxUserService.getPartnerByUserId(userId);
//			if(resultDTO.isSuccess()){
//				PartnerDTO partnerDTO = resultDTO.getModule();
//				if(null != partnerDTO){
//					SpreadLinkDTO spreadLinkDTO = new SpreadLinkDTO();
//					spreadLinkDTO.setPartnerId(partnerDTO.getUserId());
//					spreadLinkDTO.setMobile(urlString);
//					JSONObject jsonObject=JSONObject.fromObject(spreadLinkDTO);
//					String s;
//					try {
//						s = StringHelper.aesEncrypt(jsonObject.toString(),PARNTER_URL_SEED);
//						String sign = StringHelper.MD5Encode(s, "utf-8");
//						urlString = "http://" + request.getServerName() //服务器地址 
//								+ request.getContextPath() + "r.html?l=" + URLEncoder.encode(s,"utf-8") + "&sign=" + URLEncoder.encode(sign,"utf-8") ;
//					} catch (Exception e) {
//					}
//				}
//			}
//		}
		
		packageParams.put("timestamp", timestamp.toString());
		packageParams.put("noncestr",nonceStr);
		packageParams.put("jsapi_ticket",  weixinJsapiTicket.getTicket());
		packageParams.put("url", urlString);
		weixinShareDTO.setShareLink(urlString);
		mav.addObject("signature", WeixinPay.createSignJSSDK(packageParams) );
		mav.addObject("weixinShareDTO", weixinShareDTO);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
			
		return mav;
		
	}
	
	protected Integer getMaxUsedCoupon(Double requestAmount){
		return requestAmount.intValue()/ConstHelper.CAN_USED_COUPON_1 * ConstHelper.CAN_USED_COUPON_2;
	}
	
	protected Integer getMaxUsedCoupon(Double requestAmount,Boolean isUsingCoupon){
		if(null != isUsingCoupon && isUsingCoupon){
			return requestAmount.intValue()/ConstHelper.CAN_USED_COUPON_1 * ConstHelper.CAN_USED_COUPON_2;
		}
		else{
			return 0;
		}
	}
	
	

	protected void callbackByWeixin(HttpServletRequest request,HttpServletResponse response,Integer payType){
		
		try{
			logger.error("------callback_by_weixin_erweima_begin------");
			// 示例报文
			// String xml =
			// "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
			String inputLine;
			String notityXml = "";
			String resXml = "";
	
			try {
				while ((inputLine = request.getReader().readLine()) != null) {
					notityXml += inputLine;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				request.getReader().close();
			}
	
			logger.error("callbackByWeixinJSAPI--recv notify xml --->：" + notityXml);
	
			Map m = parseXmlToList2(notityXml);
			WxPayResult wpr = new WxPayResult();
			wpr.setAppid(m.get("appid").toString());
			wpr.setBankType(m.get("bank_type").toString());
			wpr.setCashFee(m.get("cash_fee").toString());
			if(m.containsKey("fee_type")){
				wpr.setFeeType(m.get("fee_type").toString());
			}
			wpr.setIsSubscribe(m.get("is_subscribe").toString());
			wpr.setMchId(m.get("mch_id").toString());
			wpr.setNonceStr(m.get("nonce_str").toString());
			wpr.setOpenid(m.get("openid").toString());
			wpr.setOutTradeNo(m.get("out_trade_no").toString());
			wpr.setResultCode(m.get("result_code").toString());
			wpr.setReturnCode(m.get("return_code").toString());
			wpr.setSign(m.get("sign").toString());
			wpr.setTimeEnd(m.get("time_end").toString());
			wpr.setTotalFee(m.get("total_fee").toString());
			wpr.setTradeType(m.get("trade_type").toString());
			wpr.setTransactionId(m.get("transaction_id").toString());
			
			Double usedCoupon = 0.0; // 使用的代金券;
			Double usedBalance = 0.0; // 使用的余额
			Long couponId = 0L;
			logger.error("m.containsKey11111--------------->");
			if(m.containsKey("attach") && null != m.get("attach")){
				logger.error("m.containsKey--------------->");
				String attach = m.get("attach").toString();
				logger.error("attach------>" + attach);
				JSONObject object =  JSONObject.fromObject(URLDecoder.decode(attach,"UTF-8"));
				logger.error("JSONObject---->" + object.toString());
				RyxExtraParamDTO extraParamDTO =  (RyxExtraParamDTO)JSONObject.toBean(object,RyxExtraParamDTO.class);
		
				usedCoupon = extraParamDTO.getC(); // 使用的代金券;
				usedBalance = extraParamDTO.getB(); // 使用的余额
				couponId = extraParamDTO.getCid();
				logger.error("extraParamDTO.getB()--->" + extraParamDTO.getB());
			}
			
	
			Double totalFee = Double.valueOf(wpr.getTotalFee())/100; // 支付的实际金额
	
			logger.error("wpr.getResultCode()--->" + wpr.getResultCode());
			
			if ("SUCCESS".equals(wpr.getResultCode())) {
				
				
				logger.error("out_trade_no=" + wpr.getOutTradeNo());
	
				// logger.error("trade_no" + trade_no);
				// logger.error("trade_status" + trade_status);
	
				String orderUniqueId = wpr.getOutTradeNo();
	
				logger.error("orderUniqueId=" + orderUniqueId);
				ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderByUid(orderUniqueId);
				RyxOrderDTO order = orderResult.getModule();
				if (null != order) {
					if (EnumOrderStatus.PAY_SUCCESS.getCode() != order.getStatus()) {
						Long userId = order.getOrderUid();
						if(totalFee + usedBalance + usedCoupon == order.getOrderAmount()){
	
							processOrderAfterPaySuccess(errList,order,orderUniqueId,usedBalance,usedCoupon,totalFee,null,payType,couponId);
							// 支付成功
							resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
						}					
						else{
							logger.error("支付失败[金额有误:实际支付["+ totalFee +"]，余额支付["+ usedBalance +"]，代金券["+ usedCoupon +"],订单总额["+ order.getOrderAmount() +"]");
						}
					} else {
						resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
					}
	
				} else {
					logger.error("weixin_js_api_invalid_order_unique_id ----->" + orderUniqueId);
					resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[无效out_trade_no:" + wpr.getOutTradeNo()
							+ "]]></return_msg>" + "</xml> ";
				}
	
			} else {
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
	
			logger.error("resXml=" + resXml);
	
			logger.error("callback_by_weixin_erweima_end");
			//
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		}
		catch(Throwable t){
			logger.fatal(t.getMessage(),t);
		}
	}

	
	public Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	
	protected KeyvalueQuery queryKeyvalue(KeyvalueQuery keyvalueQuery) {

		if (null == keyvalueQuery.getPageSize()
				|| keyvalueQuery.getPageSize() == 0) {
			keyvalueQuery.setPageSize(20);
		}

		if (null == keyvalueQuery.getCurrentPage()
				|| keyvalueQuery.getCurrentPage() == 0) {
			keyvalueQuery.setCurrentPage(1);
		}

		if (keyvalueQuery.getStartRow() > 0) {
			keyvalueQuery.setStartRow(keyvalueQuery.getStartRow() - 1);
		}

		ResultDTO<List<KeyvalueDTO>> result = systemService
				.queryKeyvalue(keyvalueQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			keyvalueQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQueryKeyvalue(keyvalueQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		keyvalueQuery.setTotalItem(totalItem);

		return keyvalueQuery;

	}
	
	
	protected void processOrderAfterPaySuccess(ArrayList<String> errList,
			RyxOrderDTO order,String returnOuterId,
			Double usedBalance,Double usedCoupon,Double totalFee,
			ModelAndView mav,
			Integer payType,
			Long couponId
			
			){
		/**
		 * 
		 * 支付成功 （事务处理） 1 更新订单状态、外部订单号 2、 更新订单明细
		 * 
		 */	
		RyxOrderDTO updateOrder = new RyxOrderDTO();
		updateOrder.setOrderType(order.getOrderType());
		updateOrder.setUid(order.getUid());
		updateOrder.setTnow(System.currentTimeMillis() / 1000);
		updateOrder.setId(order.getId());
		updateOrder.setReturnOrderId(returnOuterId);
		updateOrder.setPayType(payType);
		updateOrder.setTpay(new Date());
		updateOrder.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); // 支付成功
		updateOrder.setOrderType(order.getOrderType());

		updateOrder.setRealPrice(order.getOrderAmount() - usedCoupon); // 实际支付金额  + 余额支付的那部分
		updateOrder.setUseBalance(usedBalance);
		updateOrder.setCoupon(usedCoupon); //
		updateOrder.setDiscount1(order.getDiscount1());
		updateOrder.setDiscount2(updateOrder.getRealPrice() / order.getOrderAmount());// 第2个折扣,用于分摊每个课程的购物券
		
		updateOrder.setOrderUid(order.getOrderUid());
		updateOrder.setPartnerId(order.getPartnerId());
		updateOrder.setBalance(usedBalance);
		updateOrder.setCouponId(couponId);
		ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(updateOrder);

		errList = addList(errList, updateOrderResult.getErrorMsg());

		
		if (updateOrderResult.isSuccess()) {
			if(null != mav){
				mav.addObject("order", updateOrder);
				mav.addObject("totalFee", totalFee);
			}
			
			if(EnumOrderType.COURSE_ORDER.getCode() == order.getOrderType()){ // 消费课程订单

				// 更新学习人数
				ResultDTO<List<RyxOrderDetailDTO>> orderdetailResult = ryxOrderService.getOrderDetailByOrderId(order.getId());
				errList = addList(errList, orderdetailResult.getErrorMsg());
				if (orderdetailResult.isSuccess()) {
					List<RyxOrderDetailDTO> orderDetailList = orderdetailResult.getModule();
					if (null != orderDetailList) {
						for (RyxOrderDetailDTO detail : orderDetailList) {
							ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(detail.getObjId());
							errList = addList(errList, updateStudyCountResult.getErrorMsg());
						}
					}
				}		
				
				
				/**
				 * 消费课程赠送积分优惠券
				 */
				
				if(updateOrder.getRealPrice() > 0 ){
					
					Double coupon = (updateOrder.getRealPrice().intValue()/ ConstHelper.COST.intValue()) * ConstHelper.COST_SONG_COUPON;
					Double score = (updateOrder.getRealPrice().intValue()/ ConstHelper.COST.intValue()) * ConstHelper.COST_SONG_SCORE;
					
					/**
					 * 消费课程送代金券
					 */
//					if(coupon > 0.1 && ConstHelper.COST_SONG_COUPON > 0.1){	
//						UserCouponDTO dto = new UserCouponDTO();
//						dto.setUserId(order.getOrderUid());
//						dto.setType(EnumAccountType.COUPON.getCode());
//						dto.setCoupon(coupon);
//						dto.setRemark("购买课程满"+ totalFee +"送"+ coupon +"优惠券");
//						dto.setCreaterId(order.getOrderUid());
//						dto.setLimi(System.currentTimeMillis()/1000 + 
//								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
//						ResultDTO<Boolean>resultDTO = ryxUserService.addUserCoupon(dto);
//						addList(errList, resultDTO.getErrorMsg());
//					}

//					/**
//					 * 消费课程增加积分
//					 */
//					if(score > 0.1  && ConstHelper.COST_SONG_SCORE > 0.1 ){
//						UserCouponDTO dto = new UserCouponDTO();
//						dto.setUserId(order.getOrderUid());
//						dto.setType(EnumAccountType.SCORE.getCode());
//						dto.setCoupon(score);
//						dto.setRemark("购买课程满"+ totalFee +"送"+ score +"积分");
//						dto.setCreaterId(order.getOrderUid());
//						ResultDTO<Boolean>resultDTO = ryxUserService.addUserScore(dto);
//						addList(errList, resultDTO.getErrorMsg());
//					}
				}
			}
			else if(EnumOrderType.BALANCE_ORDER.getCode() == order.getOrderType()){
				/**
				 * 增加余额
				 */
				RyxUserCouponDTO dto = new RyxUserCouponDTO();
				dto.setUserId(order.getOrderUid());
				dto.setType(EnumAccountType.MONEY.getCode());
				dto.setCoupon(totalFee);
				dto.setRemark("充值");
				dto.setCreaterId(order.getOrderUid());
				ResultDTO<Boolean> resultDTO = ryxUserService.addUserBalance(dto);
				addList(errList, resultDTO.getErrorMsg());
				
			
				if(totalFee > 0 ){
					
					Double coupon = (totalFee.intValue()/ ConstHelper.CHONG.intValue()) * ConstHelper.CHONG_SONG_COUPON;
					Double score = (totalFee.intValue()/ ConstHelper.CHONG.intValue()) * ConstHelper.CHONG_SONG_SCORE;
					
					/**
					 * 充值送代金券
					 */
					if(coupon > 0.1 ){						
						dto.setUserId(order.getOrderUid());
						dto.setType(EnumAccountType.COUPON.getCode());
						dto.setCoupon(coupon);
						dto.setRemark("充值"+ totalFee );
						dto.setCreaterId(order.getOrderUid());
						dto.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						resultDTO = ryxUserService.addUserCoupon(dto);
						addList(errList, resultDTO.getErrorMsg());
					}
					


					/**
					 * 增加积分
					 */
					if(score > 0.1 ){
						dto.setUserId(order.getOrderUid());
						dto.setType(EnumAccountType.SCORE.getCode());
						dto.setCoupon(score);
						dto.setRemark("充值"+ totalFee +"送"+ score +"积分");
						dto.setCreaterId(order.getOrderUid());
						resultDTO = ryxUserService.addUserScore(dto);
						addList(errList, resultDTO.getErrorMsg());
					}
				}
				
			}
			else if(EnumOrderType.DASHANG_ORDER.getCode() == order.getOrderType()){
				RyxUserCouponDTO dto = new RyxUserCouponDTO();
				dto.setUserId(order.getOrderUid());
				dto.setType(EnumAccountType.MONEY.getCode());
				dto.setCoupon(totalFee);
				dto.setRemark("打赏对冲-充值");
				dto.setCreaterId(order.getOrderUid());
				ResultDTO<Boolean> resultDTO = ryxUserService.addUserBalance(dto);
				addList(errList, resultDTO.getErrorMsg());
				
				dto = new RyxUserCouponDTO();
				dto.setUserId(order.getOrderUid());
				dto.setType(EnumAccountType.MONEY.getCode());
				dto.setCoupon(- totalFee);
				dto.setRemark("打赏对冲-扣款");
				dto.setCreaterId(order.getOrderUid());
				resultDTO = ryxUserService.addUserBalance(dto);
				addList(errList, resultDTO.getErrorMsg());
				
			}
			else if(EnumOrderType.SUB_ACCOUNT_ORDER.getCode() == order.getOrderType()){	
				
				// 子账号订单
				RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
				objectLimitDTO.setUserId(order.getOrderUid());
				objectLimitDTO.setOtype(EnumObjectType.SUB_ACCOUNT_MODULE.getCode());
				objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
				objectLimitDTO.setLimi(Long.parseLong(order.getOrderBody()));
				objectLimitDTO.setOid(order.getOrderUid());
				ResultDTO<Long> resultDTO = ryxCourseService.createOrUpdateObjectLimit(objectLimitDTO);
				addList(errList, resultDTO.getErrorMsg());
				
			}
			else if(EnumOrderType.CARD_ORDER.getCode() == order.getOrderType()){	
				
				/**
				 * 课程卡订单
				 */
				
				String[] orderBody = order.getOrderBody().split(":");
				RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
				objectLimitDTO.setUserId(order.getOrderUid());
				objectLimitDTO.setOtype(EnumObjectType.CARD_MODULE.getCode());
				objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
				objectLimitDTO.setOid(Long.parseLong(orderBody[0])); // course id
				objectLimitDTO.setCategory(Integer.parseInt(orderBody[1])); // course category
				objectLimitDTO.setAvaiDay(Integer.parseInt(orderBody[2]));
				ResultDTO<Long> resultDTO = ryxCourseService.createOrUpdateObjectLimit(objectLimitDTO);
				addList(errList, resultDTO.getErrorMsg());
				
			}
		}
	}
	
	protected void setCourseWeixinSpread(RyxCourseDTO course,ModelAndView mav,HttpServletRequest request) {		
		String contentString = HtmlHelper.unescapeHtmlAndDeleteHtmlTag(course.getDescr());
		contentString = contentString.length()>52 ? contentString.substring(0,52) : contentString;
		WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
		weixinShareDTO.setShareTitle( course.getTitle() +"【融易学金融学院】");
		weixinShareDTO.setShareImageUrl("http://ryximages.ryx365.com/ryx/default.png");
		weixinShareDTO.setShareDesc( contentString);
		//weixinShareDTO.setShareLink("http://m.ryx365.com/e_"+id+".html");
		mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
	}
	
	
	protected void setCourseWeixinSpread(String title,String descr,ModelAndView mav,HttpServletRequest request) {		
		WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
		weixinShareDTO.setShareTitle( title + "【融易学金融学院】");
		weixinShareDTO.setShareImageUrl("http://ryximages.ryx365.com/ryx/default.png");
		weixinShareDTO.setShareDesc( descr);
		//weixinShareDTO.setShareLink("http://m.ryx365.com/e_"+id+".html");
		mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
	}
	
	protected void setCourseWeixinSpread1(String title,String descr,ModelAndView mav,HttpServletRequest request) {		
		WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
		weixinShareDTO.setShareTitle( title );
		weixinShareDTO.setShareImageUrl("http://ryximages.ryx365.com/ryx/default.png");
		weixinShareDTO.setShareDesc( descr );
		//weixinShareDTO.setShareLink("http://m.ryx365.com/e_"+id+".html");
		mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
	}
	
	
	
	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess) {
		writeAppAjax(response, isSuccess, "", null);
	}

	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg) {
		writeAppAjax(response, isSuccess, errorMsg, null);
	}
	
	protected void writeAppAjax1(HttpServletResponse response, boolean isSuccess,
			 Object obj) {
		writeAppAjax(response, isSuccess, null, obj);
	}
	
	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg, Object obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("i", isSuccess);
			json.put("o", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("e", errorMsg);
			}
			writeAppAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	
	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,
			Integer code,String errorMsg) {

		JSONObject json = new JSONObject();

		try {

			json.put("i", isSuccess);
			json.put("c", code);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("e", errorMsg);
			}
			writeAppAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	

	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,Object obj) {

		JSONObject json = new JSONObject();

		try {
			if(null == obj){
				json.put("i", false);
				json.put("e", "返回结果值为空");
			}
			else{
				json.put("i", isSuccess);
				json.put("o", obj);
			}
			writeAppAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,
			String errorMsg , List obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("i", isSuccess);
			json.put("o", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("e", errorMsg);
			}
			writeAppAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	
	protected void writeAppAjax(HttpServletResponse response, boolean isSuccess,
			String errorCode, String errorMsg, Object obj) {

		JSONObject json = new JSONObject();

		try {

			json.put("i", isSuccess);
			json.put("o", obj);
			if (StringUtils.isNotEmpty(errorMsg)) {
				json.put("c", errorCode);
				json.put("e", errorMsg);
			}
			writeAppAjax(response, json);
		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @throws Exception 
	 */
	protected void writeAppAjax(HttpServletRequest request,
			HttpServletResponse response, JSONObject json) throws Exception {
		writeAppAjax(response, json);
	}
	
	
	/**
	 * 
	 * @param response
	 * @param json
	 * @throws Exception 
	 */
	protected void writeAppAjax(HttpServletResponse response,JSONObject json)
			throws Exception {
		String data = null;
		if (null == json) {
			response.getWriter().write("");
			;
		} else {
			data = StringHelper.aesEncrypt(json.toString(), ConstHelper.APP_ENCRYPT_KEY);
			response.getWriter().write(data);
			;
		}
	}
	

	 protected static String testAppUrl = "localhost";
	
}
