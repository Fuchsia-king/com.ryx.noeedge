package com.king.nowedge.controller.ryx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyRelatedValueType;
import com.king.nowedge.dto.enums.EnumPartnerType;
import com.king.nowedge.dto.enums.EnumYesorno;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.crm.RyxContractDTO;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.dto.ryx.query.RyxUserExtQuery;
import com.king.nowedge.helper.*;
import com.king.nowedge.helper.sd.SSOHelper;
import com.king.nowedge.utils.Md5Helper;
import com.king.nowedge.utils.Md5Util;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Controller
public class InterfaceController  extends BaseController {
	
	private static final Log logger = LogFactory.getLog(InterfaceController.class);
	
	
	
	@RequestMapping("/crossdomain.xml")
	public void crossdomain(HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws IOException {

		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/xml; charset=utf-8"); 
		response.getWriter().write("<cross-domain-policy><allow-access-from domain=\"\"/></cross-domain-policy>");
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param ts
	 * @param vid
	 * @param response
	 * @param rt
	 * @throws IOException
	 */
	@RequestMapping("/get_polyv_sign.html")
	public void get_polyv_sign(HttpServletRequest request, 
			String ts,
			String vid,
			String from,
			HttpServletResponse response, RedirectAttributes rt)
			throws IOException {
		
		
		if(StringHelper.isNullOrEmpty(from)){
			writeAjax(response, false, "missing from");
			return ;
		}
		
		String froms  = ryxConfigService.getConfigByName("polyv_partner_froms").getModule().getValue() ;
		
		if(froms.indexOf(from)<0){
			writeAjax(response, false, "invalid from");
			return ;
		}
		
		if(StringHelper.isNullOrEmpty(ts)){
			writeAjax(response, false, "missing ts");
			return ;
		}
		
		if(StringHelper.isNullOrEmpty(vid)){
			writeAjax(response, false, "missing vid");
			return ;
		}
		
		writeAjax(response, true, "" ,Md5Helper.getMD5String("Kd8jQHITMj" + vid + ts));
	}
	
	
	
	/**
	 * 保利威授权接口
	 * @param request
	 * @param response
	 * @param rt
	 * @throws IOException
	 */
	@RequestMapping("/polyv_validate.html")
	public void polyv(HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws IOException {
		
		String secretkey = "Kd8jQHITMj";
		String vid = request.getParameter("vid");
		logger.error("vid---->" + vid);
		String t = request.getParameter("t");
		logger.error("t---->" + t);
		logger.error("code---->" + request.getParameter("code"));
		Long code = 282L;
		try{
			code = Long.parseLong(request.getParameter("code"));
		}
		catch(Throwable e){
			logger.error(e.getMessage(),e);
		}
		String fontSize="40";
		String fontColor="0xFFE900";
		String speed="200";
		String filter="on";
		String setting="3";
		String alpha="1";
		String filterAlpha="1";
		String filterColor="0x3914AF";
		String blurX="2";
		String blurY="2";
		String tweenTime="1";
		String interval="5";
		String lifeTime="3";
		String strength="4";
		String show="on";
		Integer status = 1 ;
		
		String username = code.toString() + "_is_watching";
		
		String msg = code + "_playing";
		
		String plain ="vid=" + vid + "&secretkey=" + secretkey + "&username=" + username + "&code=" + code + "&status=" + status + "&t=" + t + "&msg=" + msg + "&fontSize=" + fontSize + "&fontColor=" + fontColor + "&speed=" + speed + "&filter=" +filter + "&setting=" + setting + "&alpha=" + alpha + "&filterAlpha=" + filterAlpha  + "&filterColor=" + filterColor + "&blurX=" + blurX + "&blurY=" + blurY + "&interval=" + interval + "&lifeTime=" + lifeTime + "&tweenTime=" + tweenTime + "&strength=" + strength + "&show=" +show;
		logger.error("plain----->" + plain);
		String sign = Md5Util.GetMD5Code(plain);		
		logger.error("sign----->" + sign);
		response.getWriter().write("{"+
	    "\"status\":"+ status +","+
	    "\"username\":\""+ username +"\","+
	    "\"sign\":\""+ sign +"\","+
	    "\"msg\":\""+ msg +"\","+
	    "\"fontSize\":\""+ fontSize +"\","+
	    "\"fontColor\":\""+ fontColor +"\","+
	    "\"speed\":\""+ speed +"\","+
	    "\"filter\":\""+ filter +"\","+
	    "\"setting\":\""+ setting +"\","+
	    "\"alpha\":\""+ alpha +"\","+
	    "\"filterAlpha\":\""+ filterAlpha +"\","+
	    "\"filterColor\":\""+ filterColor +"\","+
	    "\"blurX\":\""+ blurX +"\","+
	    "\"blurY\":\""+ blurY +"\","+
	    "\"tweenTime\":\""+ tweenTime +"\","+
	    "\"interval\":\""+ interval +"\","+
	    "\"lifeTime\":\""+ lifeTime +"\","+
	    "\"strength\":\""+ strength +"\","+
	    "\"show\":\""+ show +"\"}");
		
	}
	
	

	
	
	
	@RequestMapping("/get_partner_product.html")
	public void ajaxMyFollowTeacher(HttpServletRequest request, 
			@RequestParam(value = "sign") String sign,
			@RequestParam(value = "page_size") Integer pageSize,
			@RequestParam(value = "page_index") Integer pageIndex,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		response.setHeader("content-type", "text/html;charset=UTF-8");
		Long partnerUserId = null;
		try {
			partnerUserId = Long.parseLong(StringHelper.aesDecrypt( sign, RequestHelper.PARNTER_URL_SEED));
			if(null == partnerUserId){
				writeAjax(response, false, "签名验证有误，请与我们联系");
				return;
			}
		}
		catch (Throwable t) {
			writeAjax(response, false, "签名验证异常，请与我们联系");
			logger.error(t.getMessage(),t);
			return ;
		}
		try{
			List<RyxPartnerCourseDTO> listParterCourse = new ArrayList<RyxPartnerCourseDTO>();
			pageIndex = (null == pageIndex || pageIndex == 0) ? 1 : pageIndex;
			KeyrvQuery query = new KeyrvQuery();	
			query.setKey1(partnerUserId.toString());
			query.setPageSize(DEFAULT_PAGE_SIZE);
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setType(EnumKeyRelatedValueType.KV_PARTNER_COURSE.getCode());
			query.setCurrentPage(pageIndex);
			query.setOrderBy("sort");
			ResultDTO<KeyrvQuery> result = MetaHelper.getInstance().queryKeyrv(query);
			query = result.getModule();
			if (result.isSuccess()) {
				RyxPartnerResultDTO partnerResult = new RyxPartnerResultDTO();
				List<KeyrvDTO> list = result.getModule().getList();
				if(null!=list){
					for(KeyrvDTO keyrvDTO : list){
						Long courseId =Long.parseLong(keyrvDTO.getRkey());
						RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(courseId);
						if(null != courseDTO){
							RyxPartnerCourseDTO partnetCourse = new RyxPartnerCourseDTO();
							partnetCourse.setUrl("http://www.ryx365.com/course_"+ courseId +".html?u=" + URLEncoder.encode(sign, "UTF-8"));
							partnetCourse.setImage(courseDTO.getImageUrl());
							partnetCourse.setPrice(courseDTO.getPrice());
							listParterCourse.add(partnetCourse);
						}
					}
				}
				partnerResult.setList(listParterCourse);
				partnerResult.setTotalNbr(query.getTotalItem());
				writeAjax(response, true,partnerResult);
				return;
			} else {
				writeAjax(response, false, result.getErrorMsg());
				return;
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}
	
	
	
	@RequestMapping("/get_partner_obj.html")
	public void getPartnerProduct(HttpServletRequest request, 
			@RequestParam(value = "id") Long id,
			@RequestParam(value = "page_size") Integer pageSize,
			@RequestParam(value = "page_index") Integer pageIndex,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		try{
			
			RyxPartnerDTO partnerDTO = new RyxPartnerDTO();
			partnerDTO.setUserId(id);
			partnerDTO.setType(EnumPartnerType.LINK_PARTNER.getCode());
			partnerDTO.setIdeleted(0);
			ResultDTO<RyxPartnerDTO> pResultDTO = MetaHelper.getInstance().getPartnerByUserId(partnerDTO);
			if(null == pResultDTO.getModule()){
				writeAjax(response, false, "无效Id==>" + id);
				return;
			}
			String sign = StringHelper.aesEncrypt(id.toString(), RequestHelper.PARNTER_URL_SEED);
			List<RyxPartnerCourseDTO> listParterCourse = new ArrayList<RyxPartnerCourseDTO>();
			pageIndex = (null == pageIndex || pageIndex == 0) ? 1 : pageIndex;
			RyxCourseQuery query = new RyxCourseQuery();
			query.setPageSize(pageSize);
			query.setCurrentPage(pageIndex);
			ResultDTO<RyxCourseQuery> result = MetaHelper.getInstance().queryCourseCache1(query);
			query = result.getModule();
			if (result.isSuccess()) {
				RyxPartnerResultDTO partnerResult = new RyxPartnerResultDTO();
				List<RyxCourseDTO> list = result.getModule().getList();
				if(null!=list){
					for(RyxCourseDTO courseDTO : list){
						RyxPartnerCourseDTO partnetCourse = new RyxPartnerCourseDTO();
						partnetCourse.setUrl("http://m.ryx365.com/m/course_"+ courseDTO.getId() + ".html?u=" + URLEncoder.encode(sign, "UTF-8"));
						partnetCourse.setImage(courseDTO.getImageUrl());
						partnetCourse.setPrice(courseDTO.getPrice());
						partnetCourse.setTitle(courseDTO.getTitle());
						partnetCourse.setDescr(courseDTO.getDescr());
						listParterCourse.add(partnetCourse);
					}
				}
				partnerResult.setList(listParterCourse);
				partnerResult.setTotalNbr(query.getTotalItem());
				writeAjax(response, true,partnerResult);
				return;
			} else {
				writeAjax(response, false, result.getErrorMsg());
				return;
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
			logger.error(t.getMessage(),t);
		}
	}
	
	
	/**
	 * 免登陆
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/interface/auto_login.html")
	public ModelAndView autoLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String encryptlogin = request.getParameter("login") ;
		
		ModelAndView mav = new ModelAndView("/ryx/pc/cautoLogin");
		String json = StringHelper.aesDecrypt(encryptlogin, ConstHelper.APP_ENCRYPT_KEY);
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		
		if(!jsonObject.containsKey("username")){
			throw new Exception("missing username");
		}
		
		if(!jsonObject.containsKey("source")){
			throw new Exception("missing source");
		}
		
		
		if(!jsonObject.containsKey("corpCode")){
			throw new Exception("missing corpCode");
		}
		
		if(!jsonObject.containsKey("timestamp")){
			throw new Exception("missing timestamp");
		}
		
		String corpCode  =  jsonObject.getString("corpCode") ;
		
		if(StringHelper.isNullOrEmpty(corpCode)){
			throw new Exception("invalid corpCode");
		}
		
		String username  =  jsonObject.getString("username") ;
		if(StringHelper.isNullOrEmpty(username)){
			throw new Exception("invalid username");
		}
		
		String source = jsonObject.getString("source") ;
		if(StringHelper.isNullOrEmpty(source)){
			throw new Exception("invalid source");
		}
		
		Long timestamp = jsonObject.getLong("timestamp");
		if(null == timestamp || timestamp <=0){
			throw new Exception("invalid timestamp");
		}
		
		
		Long userId = null;
		
		if(jsonObject.containsKey("userId")){
			userId = jsonObject.getLong("userId");
		}
		
		
		Long seconds =  Math.abs((timestamp - System.currentTimeMillis())/1000);
		if (seconds > 10 && ConstHelper.isFormalEnvironment()){
			//throw new Exception("time out!");
		}
		
		if(null == userId){
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setUsername(username);
			ResultDTO<RyxUserExtDTO> result = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
			if(result.isSuccess()){
				RyxUserExtDTO ryxUserExtDTO = result.getModule();
				
				if(null == ryxUserExtDTO){
					throw new Exception("invalid username&source:"+ username +"&" + source);
				}
				userId = ryxUserExtDTO.getId();
			}
			else{
				throw new Exception(result.getErrorMsg());
			}
		}
		
		CookieHelper.removeCookies(SessionHelper.SHIDAI_CURRENT_USER_ID , "/", request, response) ;
		CookieHelper.addCookie(response, SessionHelper.SHIDAI_CURRENT_USER_ID , userId.toString(), 24*60*60*30, "/");
		
		RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
		ryxUserExtQuery.setSource(source);
		ryxUserExtQuery.setCorpCode(corpCode);
		ryxUserExtQuery.setImain(EnumYesorno.YES.getCode());
		
		RyxUserExtDTO mainRyxUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
		if(null == mainRyxUserExtDTO){
			throw new Exception("invalid corpCode&source,no main account exist:"+ corpCode +"&" + source);
		}
			
		String targetUrl = ""; 
		
		if (jsonObject.containsKey("targetType")){
			String targetType = jsonObject.getString("targetType");
			if(StringHelper.isNullOrEmpty(targetType)){
				throw new Exception("missing targetType value");
			}
			if(!jsonObject.containsKey("targetId")){
				throw new Exception("missing targetId for {"+ targetType +"}");
			}
			Long targetId = jsonObject.getLong("targetId");
			if(null == targetId || targetId<=0){
				throw new Exception("missing targetId value");
			}
			
			if(targetType.equals("online")){
				KeyrvQuery keyrvQuery = new KeyrvQuery();
				keyrvQuery.setKey1(userId.toString());
				keyrvQuery.setRkey(targetId.toString());
				KeyrvDTO keyrvDTO = systemService.querySingleKeyrv(keyrvQuery).getModule();
				if(null != keyrvDTO && !StringHelper.isNullOrEmpty(keyrvDTO.getValue())){
					targetUrl = keyrvDTO.getValue();
					System.out.println("LATEST_STUDY_COURSE_ZHANGJIE_KEYRV--->" + targetUrl);
				}
				if(StringHelper.isNullOrEmpty(targetUrl)){
					Long subId = CourseHelper.getInstance().getSubCourseByMainCourse(targetId);
					targetUrl = "/onlineshidai_"+ subId + "_" + targetId +"__1.htm" ;
					System.out.println("LATEST_STUDY_COURSE_ZHANGJIE__DEFAULT--->" + targetUrl);
				}
				System.out.println("targetUrl--->" + targetUrl);
			}
			else{
				throw new Exception("invalid targetType value{"+ targetType +"}");
			}
			
		}
		
		if(StringHelper.isNullOrEmpty(targetUrl)){
			targetUrl = "/default.html" ;
		}
		
		mav.addObject("targetUrl", targetUrl);
		mav.addObject("id", StringHelper.aesEncrypt(userId.toString(), ConstHelper.APP_ENCRYPT_KEY) );
		
		logger.error("targetUrl--->"  + targetUrl);
		logger.error("id--->"  + userId);
		
		return mav ;
		
	}
	
	
	
	@RequestMapping("/my/interface/go2sd.html")
	public ModelAndView go2Shidai(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RyxUsersDTO ryxUsersDTO = getRyxUser();
		RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
		ryxUserExtQuery.setSource("sd");
		ryxUserExtQuery.setId(ryxUsersDTO.getId());
		RyxUserExtDTO ryxUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
		if(null == ryxUserExtDTO){
			throw new Exception("未知企业用户");
		}		
		String url = SSOHelper.toURLString("sso", ryxUserExtDTO.getUsername(), ryxUserExtDTO.getSecret(), ryxUserExtDTO.getCorpCode());
		return new ModelAndView("redirect:" + url);
		
	}
	
	
	@RequestMapping("/interface/go2sd1.html")
	public ModelAndView go2Shidai1(HttpServletRequest request, HttpServletResponse response,String str)
			throws Exception {
		
		Long id = Long.parseLong(StringHelper.aesDecrypt(str,ConstHelper.APP_ENCRYPT_KEY));

		RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
		ryxUserExtQuery.setSource("sd");
		ryxUserExtQuery.setId(id);
		RyxUserExtDTO ryxUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
		if(null == ryxUserExtDTO){
			throw new Exception("未知企业用户");
		}		
		String url = SSOHelper.toURLString("sso", ryxUserExtDTO.getUsername(), ryxUserExtDTO.getSecret(), ryxUserExtDTO.getCorpCode());		
		return new ModelAndView("redirect:" + url);
		
	}
	
	@RequestMapping("/interface/create_sub_user.html")
	public void createSubUser(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String json;
		try {
			json = StringHelper.aesDecrypt(IOUtils.toString(request.getInputStream(), "UTF-8"),
					ConstHelper.APP_ENCRYPT_KEY);
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("username")) {
				writeAjax(response, false, "missing username");
				return;
			}
			
			
			
			if (!jsonObject.containsKey("uid")) {
				writeAjax(response, false, "missing uid");
				return;
			}
			
			
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("password")) {
				writeAjax(response, false, "missing password");
				return;
			}
			
			
			/**
			 * 账号Id
			 */
			if (!jsonObject.containsKey("corpCode")) {
				writeAjax(response, false, "missing corpCode");
				return;
			}
			
			
			/**
			 * 账号来源
			 */
			if (!jsonObject.containsKey("source")) {
				writeAjax(response, false, "missing source");
				return;
			}
			
			
			String username = jsonObject.getString("username");
			
			if(StringHelper.isNullOrEmpty(username)){
				writeAjax(response, false, "invalid username");
				return;
			}
			
			
			String uid = jsonObject.getString("uid");
			
			if(StringHelper.isNullOrEmpty(uid)){
				writeAjax(response, false, "invalid uid");
				return;
			}
			
			String corpCode = jsonObject.getString("corpCode");
			if(StringHelper.isNullOrEmpty(corpCode)){
				writeAjax(response, false, "invalid corpCode");
				return;
			}
			
			if(ConstHelper.IGNORE_CORP_CODE.indexOf(corpCode) < 0 ){
				writeAjax(response, true);
				return; 
			}
			
			String password = jsonObject.getString("password");
			
			if(StringHelper.isNullOrEmpty(password)){
				writeAjax(response, false, "invalid password");
				return;
			}
			
			String source = jsonObject.getString("source");
			
			
			if(StringHelper.isNullOrEmpty(source)){
				writeAjax(response, false, "invalid source");
				return;
			}
			
			ResultDTO<RyxUsersDTO> resultDTO = ryxUserService.getUserByUsername(username);
			if(!resultDTO.isSuccess()){
				writeAjax(response, false, resultDTO.getErrorMsg());
				return ;
			}
			
			RyxUsersDTO user = resultDTO.getModule();
			
			if (null != user) {
				writeAjax(response, false, "该用户名已经存在，不能创建子账号");
				return ;
			}
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.YES.getCode());
		 	ResultDTO<RyxUserExtDTO> userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
		 	RyxUserExtDTO mainRyxUserExtDTO = userExtResultDTO.getModule();
		 	if(null == mainRyxUserExtDTO){
		 		writeAjax(response, false, "source-->" + source + ";corpCode--->" + corpCode + "  not exist");
				return ;
		 	}
			
		 	Long mainUserId = mainRyxUserExtDTO.getId();
			
			user = new RyxUsersDTO();
			user.setPassword(Md5Util.GetMD5Code(password));
			user.setUsername(username);
			user.setFlag(1);

			/**
			 * 创建子账号
			 */
			ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);

			if (createUserResult.isSuccess()) {
					
				Long userId = createUserResult.getModule();
				
				/**
				 * 创建关联关系
				 */
				KeyrvDTO keyrvDTO = new KeyrvDTO();
				keyrvDTO.setKey1(mainUserId.toString());
				keyrvDTO.setRkey(userId.toString());
				keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
				ResultDTO<Boolean> createKeyrvResult = systemService.createOrUpdateKeyrv(keyrvDTO);
				if(createKeyrvResult.isSuccess()){
					
					RyxUserExtDTO createRyxUserExtDTO = new RyxUserExtDTO();
					BeanHelper.copyBean(mainRyxUserExtDTO, createRyxUserExtDTO);
					createRyxUserExtDTO.setId(userId);
					createRyxUserExtDTO.setCorpCode(corpCode);
					createRyxUserExtDTO.setUsername(username);
					createRyxUserExtDTO.setImain(EnumYesorno.NO.getCode());
					createRyxUserExtDTO.setUid(uid);
					ResultDTO<Boolean> createResultDTO =  ryxUserService.createUserExt(createRyxUserExtDTO);
					if(createResultDTO.isSuccess()){
						writeAjax(response, true);
						return ;
					}
					else{
						writeAjax(response, false,createResultDTO.getErrorMsg());
						return ; 
					}
				}else {
					writeAjax(response, true,createKeyrvResult.getErrorMsg());
					return ;
				}
			} else {
				writeAjax(response, true,createUserResult.getErrorMsg());
				return ;
			}
		
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			writeAjax(response, false, e.getMessage());
			return;
		}
	}
	
	
	
	
	@RequestMapping("/interface/update_sub_user.html")
	public void updateSubUser(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String json;
		try {
			json = StringHelper.aesDecrypt(IOUtils.toString(request.getInputStream(), "UTF-8"),
					ConstHelper.APP_ENCRYPT_KEY);
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("username")) {
				writeAjax(response, false, "missing username");
				return;
			}
			
			
			
			if (!jsonObject.containsKey("uid")) {
				writeAjax(response, false, "missing uid");
				return;
			}
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("password")) {
				writeAjax(response, false, "missing password");
				return;
			}
			
			
			/**
			 * 账号Id
			 */
			if (!jsonObject.containsKey("corpCode")) {
				writeAjax(response, false, "missing corpCode");
				return;
			}
			
			
			/**
			 * 账号来源
			 */
			if (!jsonObject.containsKey("source")) {
				writeAjax(response, false, "missing source");
				return;
			}
			
			
			String username = jsonObject.getString("username");
		
			
			String corpCode = jsonObject.getString("corpCode");
			
			if(StringHelper.isNullOrEmpty(corpCode)){
				writeAjax(response, false, "invalid corpCode");
				return;
			}
			
			if(ConstHelper.IGNORE_CORP_CODE.indexOf(corpCode) < 0 ){
				writeAjax(response, true);
				return; 
			}
			
			
			String password = jsonObject.getString("password");
			
			
			String source = jsonObject.getString("source");
			
			
			if(StringHelper.isNullOrEmpty(source)){
				writeAjax(response, false, "invalid source");
				return;
			}
			
			String uid = jsonObject.getString("uid");
			
			
			if(StringHelper.isNullOrEmpty(uid)){
				writeAjax(response, false, "invalid uid");
				return;
			}
			
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.NO.getCode());
			ryxUserExtQuery.setUid(uid);
		 	ResultDTO<RyxUserExtDTO> userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
		 	
		 	RyxUserExtDTO ryxUserExtDTO = userExtResultDTO.getModule();
		 	
		 	if(null != ryxUserExtDTO){
			
			 	Long userId = ryxUserExtDTO.getId() ;
			 	
			 	RyxUsersDTO user = new RyxUsersDTO();
			 	
			 	if(!StringHelper.isNullOrEmpty(password)){
			 		user.setPassword(Md5Util.GetMD5Code(password));
			 	}
			 	
			 	if(!StringHelper.isNullOrEmpty(username)){
			 		user.setUsername(username);
			 	}
			 	
				user.setId(userId);
				
				/**
				 * 更新
				 */
				ResultDTO<Boolean> createUserResult = ryxUserService.updateUserById(user);
	
				if (createUserResult.isSuccess()) {
					writeAjax(response,true);
					return ;
				} else {
					writeAjax(response,false,createUserResult.getErrorMsg());
					return ;
				}
		 	}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			writeAjax(response, false, e.getMessage());
			return;
		}
	}
	
	
	@RequestMapping("/interface/delete_sub_user.html")
	public void deleteSubUser(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String json;
		try {
			json = StringHelper.aesDecrypt(IOUtils.toString(request.getInputStream(), "UTF-8"),
					ConstHelper.APP_ENCRYPT_KEY);
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("uid")) {
				writeAjax(response, false, "missing uid");
				return;
			}
			
			
		
			
			/**
			 * 账号Id
			 */
			if (!jsonObject.containsKey("corpCode")) {
				writeAjax(response, false, "missing corpCode");
				return;
			}
			
			
			/**
			 * 账号来源
			 */
			if (!jsonObject.containsKey("source")) {
				writeAjax(response, false, "missing source");
				return;
			}
			
			
			String uid = jsonObject.getString("uid");
			
			if(StringHelper.isNullOrEmpty(uid)){
				writeAjax(response, false, "invalid uid");
				return;
			}
			
			String corpCode = jsonObject.getString("corpCode");
			
			if(StringHelper.isNullOrEmpty(corpCode)){
				writeAjax(response, false, "invalid corpCode");
				return;
			}
			
			if(ConstHelper.IGNORE_CORP_CODE.indexOf(corpCode) < 0 ){
				writeAjax(response, true);
				return; 
			}
			
			String source = jsonObject.getString("source");
			
			
			if(StringHelper.isNullOrEmpty(source)){
				writeAjax(response, false, "invalid source");
				return;
			}
			
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.YES.getCode());
		 	ResultDTO<RyxUserExtDTO> userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
		 	
		 	RyxUserExtDTO mainRyxUserExtDTO = userExtResultDTO.getModule();
			if(null == mainRyxUserExtDTO){
				writeAjax(response, false,"source&corpCode: " + source + "&" + corpCode + " main account can not be found");
				return ;
			}
			
			
			
			ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.NO.getCode());
			ryxUserExtQuery.setUid(uid);
		 	userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
			
		 	
		 	
		 	RyxUserExtDTO subRyxUserExtDTO = userExtResultDTO.getModule();
		 	if(null == subRyxUserExtDTO){
				writeAjax(response, false,"source&corpCode&uid: " + source + "&" + corpCode + "&"+ uid +" sub account can not be found");
				return ;
			}
			
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
			keyrvDTO.setKey1(mainRyxUserExtDTO.getId().toString());  //主账号Id
			keyrvDTO.setRkey(subRyxUserExtDTO.getId().toString());
			ResultDTO<Boolean> result = systemService.deleteKeyrvByDTO(keyrvDTO);
			if(result.isSuccess()){
				writeAjax(response, true);
				return ;
			}
			else{
				writeAjax(response, false,"",result.getErrorMsg());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			writeAjax(response, false, e.getMessage());
			return;
		}
	}
	
	
	
	
	
	@RequestMapping("/interface/check_user_online_auth.html")
	public void checkUserOnlineAuth(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String json;
		try {
			json = StringHelper.aesDecrypt(IOUtils.toString(request.getInputStream(), "UTF-8"),
					ConstHelper.APP_ENCRYPT_KEY);
			
			logger.error("check_user_online_auth --->" + json);
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			/**
			 * 用户名（用户编号）
			 */
			if (!jsonObject.containsKey("uid")) {
				writeAjax(response, false, "missing uid");
				return;
			}

			/**
			 *（课程编号）
			 */
			if (!jsonObject.containsKey("courseCode")) {
				writeAjax(response, false, "missing courseCode");
				return;
			}
			
			/**
			 * 账号Id
			 */
			if (!jsonObject.containsKey("corpCode")) {
				writeAjax(response, false, "missing corpCode");
				return;
			}
			
			/**
			 * 账号来源
			 */
			if (!jsonObject.containsKey("source")) {
				writeAjax(response, false, "missing source");
				return;
			}
			
			
			String uid = jsonObject.getString("uid");
			
			if(StringHelper.isNullOrEmpty(uid)){
				writeAjax(response, false, "invalid uid");
				return;
			}
			
			String corpCode = jsonObject.getString("corpCode");
			
			if(StringHelper.isNullOrEmpty(corpCode)){
				writeAjax(response, false, "invalid corpCode");
				return;
			}
			
			String source = jsonObject.getString("source");
			if(StringHelper.isNullOrEmpty(source)){
				writeAjax(response, false, "invalid source");
				return;
			}
			
			String courseCode = jsonObject.getString("courseCode");
			if(StringHelper.isNullOrEmpty(courseCode)){
				writeAjax(response, false, "invalid courseCode");
				return;
			}
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.YES.getCode());
		 	ResultDTO<RyxUserExtDTO> mainResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!mainResultDTO.isSuccess()){
		 		writeAjax(response, false, mainResultDTO.getErrorMsg());
				return ;
		 	}
		 	
		 	RyxUserExtDTO mainRyxUserExtDTO = mainResultDTO.getModule();
			if(null == mainRyxUserExtDTO){
		 		logger.error("check_user_online_auth---> " + "source&corpCode: " + source + "&" + corpCode + " main account can not be found" );

				writeAjax(response, false,"source&corpCode: " + source + "&" + corpCode + " main account can not be found");
				return ;
			}
			
			RyxUserExtDTO subRyxUserExtDTO = mainRyxUserExtDTO ;
			
			
			/**
			 * 全部用主账号进行验证 
			 */
			
//			if(!mainRyxUserExtDTO.getUid().equals(uid)){
//			
//				ryxUserExtQuery = new RyxUserExtQuery();
//				ryxUserExtQuery.setSource(source);
//				ryxUserExtQuery.setCorpCode(corpCode);
//				ryxUserExtQuery.setImain(EnumYesorno.NO.getCode());
//				ryxUserExtQuery.setUid(uid);
//				ResultDTO<RyxUserExtDTO> subResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
//			 	if(!subResultDTO.isSuccess()){
//			 		writeAjax(response, false, subResultDTO.getErrorMsg());
//					return ;
//			 	}
//				
//			 	subRyxUserExtDTO = subResultDTO.getModule();
//			 	if(null == subRyxUserExtDTO){
//					writeAjax(response, false,"source&corpCode&uid: " + source + "&" + corpCode + "&"+ uid +" sub account can not be found");
//					return ;
//				}
//			}
			
			
		 	Boolean ibuy = CourseHelper.getInstance().isBuyFreeMainOnline(mainRyxUserExtDTO.getId(), Long.parseLong(courseCode));
		 	
		 	if(ibuy){
		 		
		 		String url = "" ;
		 		JSONObject jObject = new JSONObject();
		 		jObject.put("source", subRyxUserExtDTO.getSource());
		 		jObject.put("timestamp", System.currentTimeMillis());
		 		jObject.put("userId", subRyxUserExtDTO.getId());
		 		jObject.put("uid", subRyxUserExtDTO.getUid());
		 		jObject.put("username", subRyxUserExtDTO.getUsername());
		 		jObject.put("corpCode", subRyxUserExtDTO.getCorpCode());
		 		jObject.put("targetType", "online");
		 		jObject.put("targetId", courseCode);
				String s =URLEncoder.encode( StringHelper.aesEncrypt(jObject.toString(),ConstHelper.APP_ENCRYPT_KEY),"utf-8");
				
				Boolean isMobile = false;
				if(jsonObject.containsKey("isMobile")){
					String isMobileString = jsonObject.getString("isMobile").toUpperCase();
					if(isMobileString.equals("Y")){
						isMobile = true;
					}
					else {
						isMobile = false;
					}
				}
				else{
					isMobile = false ;
				}
				
				if(ConstHelper.isFormalEnvironment()){	
					if(isMobile){
						url = "https://m.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
					else{
						url = "https://www.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
				}
				else if(ConstHelper.isPreEnvironment()){
					if(isMobile){
						url = "https://pm.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
					else{
						url = "https://pre.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
				}
				else{
					if(isMobile){
						url = "https://am.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
					else{
						url = "https://a.ryx365.com/interface/auto_login.html?login=" + s + "&" + Math.random();
					}
				}
				
				
				logger.error("check_user_online_auth--->" + url);
				writeAjax(response, true, "" , url);
				return;
		 	}
		 	else{
		 		logger.error("check_user_online_auth---> " + "no sales for this course ,course id : {"+ courseCode +"}" );
		 		writeAjax(response, false, "no sales for this course ,course id : {"+ courseCode +"}" );
				return;
		 	}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			writeAjax(response, false, e.getMessage());
			return;
		}
	}
	
	
	
	@RequestMapping("/interface/undelete_sub_user.html")
	public void undeleteSubUser(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		String json;
		try {
			json = StringHelper.aesDecrypt(IOUtils.toString(request.getInputStream(), "UTF-8"),
					ConstHelper.APP_ENCRYPT_KEY);
			JSONObject jsonObject = JSONObject.fromObject(json);
			
			
			/**
			 * 用户名
			 */
			if (!jsonObject.containsKey("uid")) {
				writeAjax(response, false, "missing uid");
				return;
			}
			
			
		
			
			/**
			 * 账号Id
			 */
			if (!jsonObject.containsKey("corpCode")) {
				writeAjax(response, false, "missing corpCode");
				return;
			}
			
			
			/**
			 * 账号来源
			 */
			if (!jsonObject.containsKey("source")) {
				writeAjax(response, false, "missing source");
				return;
			}
			
			
			String uid = jsonObject.getString("uid");
			
			if(StringHelper.isNullOrEmpty(uid)){
				writeAjax(response, false, "invalid uid");
				return;
			}
			
			String corpCode = jsonObject.getString("corpCode");
			
			if(StringHelper.isNullOrEmpty(corpCode)){
				writeAjax(response, false, "invalid corpCode");
				return;
			}
			
			if(ConstHelper.IGNORE_CORP_CODE.indexOf(corpCode)  < 0 ){
				writeAjax(response, true);
				return; 
			}
			
			String source = jsonObject.getString("source");
			
			
			if(StringHelper.isNullOrEmpty(source)){
				writeAjax(response, false, "invalid source");
				return;
			}
			
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.YES.getCode());
		 	ResultDTO<RyxUserExtDTO> userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
		 	
		 	RyxUserExtDTO mainRyxUserExtDTO = userExtResultDTO.getModule();
			if(null == mainRyxUserExtDTO){
				writeAjax(response, false,"source&corpCode: " + source + "&" + corpCode + " main account can not be found");
				return ;
			}
			
			
			
			ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setSource(source);
			ryxUserExtQuery.setCorpCode(corpCode);
			ryxUserExtQuery.setImain(EnumYesorno.NO.getCode());
			ryxUserExtQuery.setUid(uid);
		 	userExtResultDTO = ryxUserService.getUserExtBySourceId(ryxUserExtQuery);
		 	if(!userExtResultDTO.isSuccess()){
		 		writeAjax(response, false, userExtResultDTO.getErrorMsg());
				return ;
		 	}
			
		 	
		 	
		 	RyxUserExtDTO subRyxUserExtDTO = userExtResultDTO.getModule();
		 	if(null == subRyxUserExtDTO){
				writeAjax(response, false,"source&corpCode&uid: " + source + "&" + corpCode + "&"+ uid +" sub account can not be found");
				return ;
			}
			
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
			keyrvDTO.setKey1(mainRyxUserExtDTO.getId().toString());  //主账号Id
			keyrvDTO.setRkey(subRyxUserExtDTO.getId().toString());
			ResultDTO<Boolean> result = systemService.createKeyrv(keyrvDTO);
			if(result.isSuccess()){
				writeAjax(response, true);
				return ;
			}
			else{
				writeAjax(response, false,"",result.getErrorMsg());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			writeAjax(response, false, e.getMessage());
			return;
		}
	}
	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		try {
			//String s =StringHelper.aesEncrypt("http://www.ryx365.com/teacher__1_.html","uot;MsoNormal&quot;&gt;");
			//System.out.println(new Date(1494725321L * 1000));
			//System.out.println(URLEncoder.encode(StringHelper.aesEncrypt("282", RequestHelper.PARNTER_URL_SEED),"UTF-8"));
			
			//String string="http://ryximages.ryx365.com/Uploads/Picture/course/ad/282_48ce460e5023168dc6bb7f8f437071cf_fileImageUrl.jpg";
			//System.out.println(string.substring(string.lastIndexOf('/')+1, string.lastIndexOf('.')));
			
			//Long aLong = DateHelper.string2Date("yyyy-MM-dd", "2017-10-31").getTime() /1000L  ;
//			//System.out.println(aLong);
//			UserCouponDTO userCouponDTO = new UserCouponDTO();
//			RyxUserService userService = new RyxUserServiceImpl();
//			ResultDTO<Long> result = userService.createUserCoupon(userCouponDTO);
			//System.out.println(Md5Util.GetMD5Code("message=你好中国&oid=3878&otype=1&title=你好rongyixue"));
			
			
			RyxContractDTO RyxContractDTO = new RyxContractDTO();
			RyxContractDTO.setTitle("你好");
			
			ObjectMapper mapper = new ObjectMapper();  
	          
	        //User类转JSON  
	        //输出结果：{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}  
	        String json = mapper.writeValueAsString(RyxContractDTO); 
			System.out.println(json);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("source", "sd");  //来源，sd
			jsonObject.put("timestamp", System.currentTimeMillis());
			jsonObject.put("username", "admin");
			jsonObject.put("corpCode", "ryx");
			String s = StringHelper.aesEncrypt(jsonObject.toString(),ConstHelper.APP_ENCRYPT_KEY);
			System.out.println(s);
			System.out.println(URLEncoder.encode(s,"utf-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
