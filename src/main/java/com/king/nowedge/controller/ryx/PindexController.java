
package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.AttrValueDTO;
import com.king.nowedge.dto.ResumeDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.dto.ryx2.validate.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.NumberExUtils;
import com.king.nowedge.utils.StringExUtils;
import com.king.nowedge.wxpay.HttpClientConnectionManager;
import com.king.nowedge.wxpay.PayConstant;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

@Controller
public class PindexController extends BaseController {

	private static final Log logger = LogFactory.getLog(PindexController.class);

//	@Autowired
//	@Qualifier("org.springframework.security.authenticationManager")
//	protected AuthenticationManager authenticationManager;
//
//
//    @Resource(name = "sessionRegistry")
//	private SessionRegistry sessionRegistry;
//    public void setSessionRegistry(SessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//    }

	@GetMapping("/")
	public String toIndex(){
		return "index";
	}

	@RequestMapping("/my/mtest.html")
	public ModelAndView test1(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws IOException {
		ModelAndView mav = new ModelAndView("/ryx/m/test");
		
		RyxUsersDTO usersDTO = getRyxUser();
		
		RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
		userCouponDTO.setCoupon(0.00);//
		userCouponDTO.setUserId(usersDTO.getId());
		userCouponDTO.setType(EnumAccountType.EXPERIENCE_COUPON.getCode());
		userCouponDTO.setRemark("邀请满人送免费体验券");
		userCouponDTO.setCreaterId(usersDTO.getId());
		ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
		addList(errList, result.getErrorMsg());
		
		return mav;

	}
	
	@RequestMapping("/weixin_scan_login.html")
	public ModelAndView weixinScanLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws IOException {
	
		
		String oauthUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		String callback = "http://www.ryx365.com/weixin_scan_login_redirect.html" ;
		String redirect_uri = URLEncoder.encode(callback, "utf-8"); ;
		oauthUrl = oauthUrl.replace("APPID",PayConstant.WEB_SCAN_APPID ).replace("REDIRECT_URI",callback).replace("SCOPE","snsapi_login");
		
		logger.error("oauthUrl--->" + oauthUrl);
		
		//model.addAttribute("name","liuzp");
		//model.addAttribute("oauthUrl",oauthUrl);
		//return "index";
		
		return new ModelAndView("redirect:" + oauthUrl);
	
	}
	
	
	@RequestMapping("/weixin_scan_login_redirect.html")
	public ModelAndView weixinScanLoginRedirect(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws Exception {
	
//		Map<String, String[]> requestParameterMap = request.getParameterMap();
//		
//		for (Map.Entry<String, String[]> entry : requestParameterMap.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//		}
		
		ModelAndView mav = new ModelAndView();
		
		String errcode = "";
		String errmsg = "";
		String openid = "" ;
		String unionid = "" ;
		String code = request.getParameter("code") ;
		
		DefaultHttpClient client = new DefaultHttpClient();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + PayConstant.WEB_SCAN_APPID + "&secret="
				+ PayConstant.WEB_SCAN_APPID_SECRET + "&code=" + code + "&grant_type=authorization_code";
		HttpGet httpget = HttpClientConnectionManager.getGetMethod(url);

		HttpResponse httpResponse = client.execute(httpget);
		String jsonStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		
		logger.error("jsonStr--->" + jsonStr);
		
		org.json.JSONObject json = new org.json.JSONObject(jsonStr);
		
		if(json.has("errcode")){
			errcode = (String)json.get("errcode");
		}
		
		
//		if(json.has("refresh_token")){
//			refreshToken = (String)json.get("refresh_token");
//		}
		
		
		if(json.has("openid")){
			openid = (String)json.get("openid");
		}
		
		if(json.has("unionid")){
			unionid = (String)json.get("unionid");
			ResultDTO<RyxUsersDTO> resultDTO = ryxUserService.getUserByWeixinUnionId(unionid) ;
			if(resultDTO.isSuccess()){
				RyxUsersDTO ryxUsersDTO = resultDTO.getModule();
				if(null != ryxUsersDTO){
					mav = new ModelAndView("/ryx/pc/cautoLogin");
					mav.addObject("id", StringHelper.aesEncrypt(ryxUsersDTO.getId().toString(), ConstHelper.APP_ENCRYPT_KEY));
					mav.addObject("targetUrl", request.getParameter("targetUrl")) ;	
					
				}
				else{
					mav = new ModelAndView("redirect:/bind_mobile.html?weixinUnionId=" + unionid);
				}
			}
		}
		
		if(json.has("errmsg")){
			errmsg = (String)json.get("errmsg");
		}
		
		System.out.println("errcode = " + errcode);
		System.out.println("openid = " + openid);
		System.out.println("errmsg = " + errmsg);
		
		return mav; 
	
	}
	
	@RequestMapping("/my/data_correct_course_teacher_userid.html")
	public void data_correct_course_teacher_userid(
			@RequestParam(value = "page_size") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		RyxCourseQuery query = new RyxCourseQuery();
		query.setPageSize(pageSize);
		ResultDTO<RyxCourseQuery> r = ryxCourseService.queryCourse(query);
		List<RyxCourseDTO> list = r.getModule().getList();
		for(RyxCourseDTO course : list){
			Long tid = course.getTid();
			if(null != tid){
				RyxTeacherDTO teacher = MetaHelper.getInstance().getTeacherById(tid).getModule();
				if(teacher != null){
					RyxCourseDTO newdto = new RyxCourseDTO();
					newdto.setId(course.getId());
					newdto.setCuid(teacher.getUid());
					newdto.setCreater(282L);
					ryxCourseService.updateCourse(newdto);
				}
			}
		}
	}
	
	
	@RequestMapping("/my/data_correct_order_detail.html")
	public void data_correct_order_detail(
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
					throws IOException {

		RyxOrderDetailQuery query = new RyxOrderDetailQuery();
		query.setPageSize(pageSize);
		ResultDTO<RyxOrderDetailQuery> r = ryxOrderService.queryOldOrderDetail(query);
		List<RyxOrderDetailDTO> list = r.getModule().getList();
		for(RyxOrderDetailDTO orderDetailDTO : list){
			RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(orderDetailDTO.getObjId());
			if(null != courseDTO){
				RyxObjectLimitDTO dto = new RyxObjectLimitDTO();
				dto.setUserId(orderDetailDTO.getUserId());
				dto.setOid(orderDetailDTO.getObjId());
				dto.setOtype(courseDTO.getObjType());
				dto.setLimi(orderDetailDTO.getLimitTime());
				dto.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
				KeyrvDTO keyrvDTO = CourseHelper.getInstance().getMainCourseBySubCourse1(orderDetailDTO.getObjId());
				if(null != keyrvDTO && null != keyrvDTO.getKey1()){
					dto.setMoid(Long.parseLong(keyrvDTO.getKey1()));
					dto.setSort(keyrvDTO.getSort());
					RyxCourseDTO mainCourseDTO = CourseHelper.getInstance().getCourseById(dto.getMoid());
					if(null!=mainCourseDTO){
						dto.setCategory(mainCourseDTO.getCategory());
					}
				}
				ResultDTO<Long> resultDTO = ryxCourseService.createObjectLimit(dto);
				if(!resultDTO.isSuccess()){
					response.getWriter().write(resultDTO.getErrorMsg()+"<BR> " + dto.toString() + "<BR>");
				}
			}
		}
	}
	

	@RequestMapping("/test2.html")
	public void test2(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws IOException {

		String ua = request.getHeader("user-agent").toLowerCase();
		//ResultDTO<String> string = HttpHelper.get("http://m.ryx365.copc/cryx/course_519.htm");
		response.getWriter().write(ua);

	}

	@RequestMapping("/default.html")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/pc/cindex");
		/**
		 * 如果是移动端，跳转
		 */
		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.") >= 0) {
			return new ModelAndView("redirect:/index.html");
		}

		errList = new ArrayList<String>();

		try {

			RyxUsersDTO users = getRyxUser();			

			/**
			 * 机构讲师
			 */
			ResultDTO<RyxTeacherQuery> orgTeacherResult = MetaHelper.getInstance().getOrgTeacher(12);
			errList = addList(errList, orgTeacherResult.getErrorMsg());
			mav.addObject("orgTeacher", orgTeacherResult.getModule().getList());

			/**
			 * 个人讲师
			 */
			ResultDTO<RyxTeacherQuery> personalTeacherResult = MetaHelper.getInstance().getPersonalTeacher(12);
			errList = addList(errList, personalTeacherResult.getErrorMsg());
			mav.addObject("personalTeacher", personalTeacherResult.getModule().getList());
			
		

			mav.addObject("errList", errList);
			mav.addObject("loginUsers", users);


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errList.add(e.toString());
		}
		
		mav.addObject("request",request);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/hindex.html")
	public ModelAndView hindex(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/chindex");

		

		errList = new ArrayList<String>();

		try {

			RyxUsersDTO users = getRyxUser();

			/**
			 * 广告 banner
			 */
			ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
			errList = addList(errList, adResult.getErrorMsg());
			mav.addObject("banners", adResult.getModule());

			mav.addObject("loginUsers", users);
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errList.add(e.toString());
		}
		

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/cindex.html")
	public ModelAndView cindex(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/ccindex");

		

		errList = new ArrayList<String>();

		try {

			RyxUsersDTO users = getRyxUser();

			/**
			 * 广告 banner
			 */
			ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
			errList = addList(errList, adResult.getErrorMsg());
			mav.addObject("banners", adResult.getModule());

			mav.addObject("loginUsers", users);
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errList.add(e.toString());
		}
		

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/online_course.html")
	public ModelAndView onlineCourse(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/onlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);
		mav.addObject("title", "在线课程");
		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/more.html")
	public ModelAndView more(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/cmore");
		mav.addObject("loginUsers", users);

		ResultDTO<List<RyxAdDTO>> moreAdResult = MetaHelper.getInstance().queryAdCache(146);
		errList = addList(errList, moreAdResult.getErrorMsg());
		mav.addObject("moreAd", moreAdResult.getModule().subList(0, 2));

		return mav;

	}


	@RequestMapping("/{nick}.html")
	public ModelAndView teacher(@PathVariable String nick, HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cteacher");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByNick(nick);
		if(null == teacherDTO){
			return new ModelAndView("redirect:/default.html"); 
		}
		
		if( EnumTeacherType.ORG.getCode() == teacherDTO.getFlag()){
			mav = new ModelAndView("/ryx/pc/corg");
		}
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setCuid(teacherDTO.getUid());
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("query",courseResult.getModule());
		
		addPasswordModel(mav, request, getCurrentUrl(request));
		mav.addObject("na", "index");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/t.html")
	public ModelAndView corp(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccorp");
		RyxUsersDTO users = getRyxUser();

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping("/course.html")
	public ModelAndView course1(Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("redirect:/course_" + id + ".html");
		return mav;
	}
	
	@RequestMapping("/course_{id}.html")
	public ModelAndView course(@PathVariable Long id, 
			HttpServletRequest request, 
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, 
			RedirectAttributes rt)
			throws Exception {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("redirect:/online_" + id + ".html");
		
		ResultDTO<RyxCourseDTO> result = MetaHelper.getInstance().getCourseById(id);
		if (result.isSuccess()) {
			RyxCourseDTO course = result.getModule();
			if(null == course.getObjType()){
				throw new Exception("invalid obj type");
			}
			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				if(null == course.getFlag()){
					throw new Exception("invalid flag");
				}
				if(course.getFlag().equals(EnumCourseType.MAIN_COURSE.getCode())){
					mav = new ModelAndView("redirect:online_" + id + ".html");
				}
				
				else if(course.getFlag().equals(EnumCourseType.SUB_COURSE.getCode())){
					KeyrvQuery keyrvQuery = new KeyrvQuery();
					keyrvQuery.setRkey(id.toString());
					keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
					ResultDTO<KeyrvQuery> keyrvResult =  systemService.queryKeyrv(keyrvQuery);
					if(keyrvResult.isSuccess() && null != keyrvResult.getModule() ){
						List<KeyrvDTO> list = keyrvResult.getModule().getList();
						if(null != list && list.size() >0 ){							
							mav = new ModelAndView("redirect:online_" + id + "_"+ list.get(0).getKey1() +".htm");							
						}
						else{
							mav = new ModelAndView("redirect:online_" + id + "_0.htm");
						}
					}
					else{
						mav = new ModelAndView("redirect:online_" + id + "_0.htm");
					}
				}
				else{
					throw new Exception("invalid flag -->" + course.getFlag());
				}
			} else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
				mav = new ModelAndView("redirect:/offline_" + id + ".html");
			}
			else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
				mav = new ModelAndView("redirect:/article_" + id + ".html");
			}
			else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				mav = new ModelAndView("redirect:/video_" + id + ".html");
			}
			else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
				mav = new ModelAndView("redirect:/activity_" + id + ".html");
			}
			else if (EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode() == course.getObjType()) {
				mav = new ModelAndView("redirect:/e_" + id + ".html");
			}
			else if (EnumObjectType.INFO_MODULE.getCode() == course.getObjType()) {
				String url = "/i_" + id + ".html";
				if(!StringHelper.isNullOrEmpty(course.getIframeUrl())){
					url = course.getIframeUrl() ;
				}
				mav = new ModelAndView("redirect:" + url);
			}
		}
		return mav;
	}

	@RequestMapping("/tcourse_{userId}.html")
	public ModelAndView tcourse(@PathVariable Long userId, HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctcourse");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(userId);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(userId);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();;

	

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "course");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	
	@RequestMapping("/toffline_{cuid}.html")
	public ModelAndView toffline(@PathVariable Long cuid, HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctoffline");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

	

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "offline");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/roffline_{cuid}.html")
	public ModelAndView roffline(@PathVariable Long cuid, HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/croffline");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

	

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "offline");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	@RequestMapping("/tactivity_{cuid}.html")
	public ModelAndView tactivity_(@PathVariable Long cuid, HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctactivity");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "activity");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	
	@RequestMapping("/ractivity_{cuid}.html")
	public ModelAndView ractivity_(@PathVariable Long cuid, HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		
		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cractivity");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "activity");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	
	@RequestMapping("/rcommerce_{cuid}.html")
	public ModelAndView rcommerce_(@PathVariable Long cuid, HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/crcommerce");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "commerce");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/rrecruit_{cuid}.html")
	public ModelAndView rrecruit_(@PathVariable Long cuid, HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/crrecruit");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "recruit");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/trecruit_{cuid}.html")
	public ModelAndView trecruit_(@PathVariable Long cuid, HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		
		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctrecruit");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(cuid);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(cuid);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "recruit");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	/**
	 * 机构课程
	 * @param id
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/rcourse_{id}.html")
	public ModelAndView rcourse(@PathVariable Long id, HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/crcourse");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(id);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setCuid(id);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();



		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "course");

		mav.addObject("query", courseQuery);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	

	@RequestMapping("/tintro_{userId}.html")
	public ModelAndView tintro(@PathVariable Long userId, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctintro");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(userId);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "intro");

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}

	@RequestMapping("/tevalu_{objer}.html")
	public ModelAndView tevalu(@PathVariable Long objer, RyxEvaluQuery evaluQuery, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctevalu");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(objer);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
		evaluQuery.setIdeleted(0);
		ResultDTO<RyxEvaluQuery> result1 = MetaHelper.getInstance().queryEvalu(evaluQuery);
		errList = addList(errList, result1.getErrorMsg());
		mav.addObject("query", result1.getModule());

		

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "evalu");

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}

	
	@RequestMapping("/news.html")
	public ModelAndView newsDetail(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cnewsDetail");
		RyxUsersDTO users = getRyxUser();

		ResultDTO<RyxNewsDTO> result = MetaHelper.getInstance().getNewsById(id);
		errList = addList(errList, result.getErrorMsg());

		mav.addObject("news", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/news_detail_by_title.html")
	public ModelAndView newsDetailByTitle(@RequestParam(value = "title") String title, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cnewsDetail");
		RyxUsersDTO users = getRyxUser();

		ResultDTO<RyxNewsDTO> result = ryxNewsService.getNewsByTitle(title);
		errList = addList(errList, result.getErrorMsg());

		mav.addObject("news", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	
	
	
	@RequestMapping("/video_{courseId}.html")
	public ModelAndView video(@PathVariable Long courseId,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {
		
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/video_"+ courseId +".html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:https://m.ryx365.com/m/video_"+ courseId +".html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/video_"+ courseId +".html");
			}
		}
		

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cvideo");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		mav.addObject("course", course);
		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else { // course is not null
			
			
			
			/**
			 * 评价
			 */
			query.setObjId(courseId);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setIdeleted(0);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxEvaluQuery> queryResult = MetaHelper.getInstance().queryEvalu(query);
			mav.addObject("query", queryResult.getModule());
			

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), courseId, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(courseId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			/***
			 * 课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(courseId);
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());

			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);

			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		return mav;
	}
	
	
	@RequestMapping("/video.html")
	public ModelAndView video1( Long id, Long tid, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		
		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cvideo");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			mav.addObject("course", course);

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || 
					null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setTid(tid);
			mav.addObject("lastcourse", ryxCourseService.getCourseByTeacherId(query).getModule());
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);


			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	@RequestMapping("/news_{id}.html")
	public ModelAndView news(@PathVariable Long id, 
			HttpServletRequest request,
			HttpServletResponse response, 
			RedirectAttributes rt)
			throws UnsupportedEncodingException {		
		return new ModelAndView("redirect:/default.html");
	}
	
	
	@RequestMapping("/online1_{id}.html")
	public ModelAndView course(@PathVariable Long id,
		HttpServletRequest request,
		HttpServletResponse response)
				throws UnsupportedEncodingException {
		String  targetUrl = CookieHelper.getCookies(SessionHelper.LATEST_STUDY_COURSE_ZHANGJIE_COOKIE + "_" + id , request, "/") ;
		if(StringHelper.isNullOrEmpty(targetUrl)){
			targetUrl = "/online_"+ CourseHelper.getInstance().getSubCourseByMainCourse(id) + "_" + id +"__1.htm" ;
		}
		return new ModelAndView("redirect:" + targetUrl);
	}


	
	@RequestMapping("/online1.html")
	public ModelAndView online(
			Long mid,
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {
		String  targetUrl = CookieHelper.getCookies(SessionHelper.LATEST_STUDY_COURSE_ZHANGJIE_COOKIE + "_" + mid , request, "/") ;
		if(StringHelper.isNullOrEmpty(targetUrl)){
			targetUrl = "/online_"+ CourseHelper.getInstance().getSubCourseByMainCourse(mid) + "_" + mid +"__1.htm" ;
		}
		return new ModelAndView("redirect:" + targetUrl);
	
	}
	
	
	/***
	 * 课程体系中的自课程详情
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/online.html")
	public ModelAndView online(
			Long id, 	//子课程Id
			Long mid, 	//系列课程Id
			Long video, //视频id,从视频那边过来的 ，要返回视频
			Integer index , // 第几节
			String shidai, 
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		

		RyxUsersDTO users = getRyxUser();
		
		if(null == users){
			return new ModelAndView("redirect:/default.html");
		}
		

		ModelAndView mav = new ModelAndView("/ryx/pc/conline1");
		
		KeyrvDTO keyrvDTO = new KeyrvDTO();
		keyrvDTO.setRkey1(mid.toString()); // 主课程
		String shidaiUserId = CookieHelper.getCookies(SessionHelper.SHIDAI_CURRENT_USER_ID, request, "/") ;
		if(StringHelper.isNullOrEmpty(shidaiUserId)){
			keyrvDTO.setKey1(users.getId().toString()); //用户id
		}
		else{
			keyrvDTO.setKey1(shidaiUserId);  //用户id
		}
		
		String url  = ""; 
		if(!StringHelper.isNullOrEmpty(shidai)){
			url = "/onlineshidai_"+ id +"_"+ mid +"__"+ index +".htm";
			mav = new ModelAndView("/ryx/pc/conlineShidai");
		}
		else{
			url = "/online_"+ id +"_"+ mid +"__"+ index +".htm" ;
		}
		
		keyrvDTO.setRkey(mid.toString());
		keyrvDTO.setValue(url);
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_CURRENT_COURSE_ZHANGJIE.getCode());
		systemService.createOrUpdateKeyrv(keyrvDTO); // 记录最后观看记录 ；
		
		if(StringHelper.isMoblie(request) && !StringHelper.isNullOrEmpty(shidai)){
			return new ModelAndView("redirect:/m/online1_"+ mid +"_"+ id +".html?from=shidai");
		}
		
		if(StringHelper.isMoblie(request) && StringHelper.isNullOrEmpty(shidai)){
			return new ModelAndView("redirect:/m/online1_"+ mid +"_"+ id +".html");
		}

		errList = new ArrayList<String>();
		
		
		

		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		RyxCourseDTO mcourse= CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", mcourse);
		
		mav.addObject("video", video);
		mav.addObject("index1", index);

		if (null == course || null == mcourse ) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == mcourse.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == mcourse.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}


			if (null != users) {
				Boolean buyFlag = isBuyOnline(mcourse,users.getId(),video,id);
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(mid);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || 
					null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? 
							"":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			/***
			 * 课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(id);
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());

			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	/**
	 * 在线课程课程体系
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/university.html")
	public ModelAndView university( HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {


		ModelAndView mav = new ModelAndView("/ryx/pc/cuniversity");
		RyxUsersDTO ryxUsersDTO = getRyxUser();
		
		if(null != ryxUsersDTO){
			if(UserHelper.getInstance().isShidaiUser(ryxUsersDTO)){
				return new ModelAndView("redirect:/my/interface/go2sd.html");
			}
			else{
				mav.addObject("tip", "tip");
			}
		}

		mav.addObject("title", "融易学金融企业大学");
		mav.addObject("loginUsers", ryxUsersDTO);
		return mav;
	}
	
	
	/**
	 * 在线课程课程体系
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/cseries_{id}.html")
	public ModelAndView cseries(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccseries");
		mav.addObject("id", id);
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			mav.addObject("course", course);

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule()
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);


			/**
			 * 课程体系介绍
			 */
			mav.addObject("series", MetaHelper.getInstance().getCourseSeriesById(id));

			mav.addObject("errList", errList);
			mav.addObject("na", "series");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}

	
	
	@RequestMapping("/cseries.html")
	public ModelAndView cseries(
			Long id,
			Long mid,
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccseries1");
		mav.addObject("id", id);
		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule()
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);


			/**
			 * 课程体系介绍
			 */
			mav.addObject("series", MetaHelper.getInstance().getCourseSeriesById(mid));

			mav.addObject("errList", errList);
			mav.addObject("na", "series");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/my/ajax/ajax_check_score.html")
	public void myCheckScore(
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request,
			HttpServletResponse response){	
		
		if (StringHelper.isNullOrEmpty(imgVerifyCode)) {
			writeAjax(response, false, "请输入图形验证码");
		} else if (!imgVerifyCode.toLowerCase().equals(
				SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, 
				request, response).toString().toLowerCase())) {
			writeAjax(response, false, "图形验证码有误，请重新输入");
		} else{
		
			RyxUsersDTO users = getRyxUser();
			errList = new ArrayList<String>();
			ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
			errList = addList(errList, courseResult.getErrorMsg());
			RyxCourseDTO course = courseResult.getModule();
	
			Boolean isEnough = false;
			
			if (null != course) {
				
				Boolean isDownload = MetaHelper.getInstance().isDownload(id, users.getId());
				
				if(isDownload){
					writeAjax(response, true);
				}
				else{
				
					/**
					 * 判断积分是否够用
					 */
					Double courseScore = course.getScore(); 
					courseScore = null == courseScore ? 0.0 : courseScore;
					
					ResultDTO<Double> scoreResult = ryxUserService.getUserScoreById(users.getId());
					errList = addList(errList, scoreResult.getErrorMsg());
					Double userScore = scoreResult.getModule();
					userScore = null == userScore ? 0.0 : userScore;
					
					if(userScore < courseScore){
						isEnough = false;
					}
					else{
						isEnough = true;
					}
					writeAjax(response, isEnough);		
				}
			}
			else{
				writeAjax(response, false);
			}
		}		
	}
	
	
	@RequestMapping("/my/download_{id}.html")
	public void myDownload(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) 
			throws IOException{

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {
			
			
			
			/**
			 * 判断是否下载过
			 */
			if(MetaHelper.getInstance().isDownload(id, users.getId())){ 
				

				/**
				 *  已经下载，直接下载
				 */
				
				download(request,response,course,errList);
				
			}
			else{
				/**
				 *  尚未下载
				 */
				
				
				/**
				 * 判断积分是否够用
				 */
				Boolean isEnough = true;
				Double courseScore = course.getScore(); 
				if(null != courseScore && courseScore > 0){
					ResultDTO<Double> scoreResult = ryxUserService.getUserScoreById(users.getId());
					errList = addList(errList, scoreResult.getErrorMsg());
					Double userScore = scoreResult.getModule();
					
					if(userScore < courseScore){
						isEnough = false;
					}
				}
				
				if(isEnough){
					
					if(courseScore>0){
						
						/**
						 * 扣减积分
						 */
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(-courseScore);
						
						userCouponDTO.setUserId(users.getId());
						userCouponDTO.setRemark("下载文库：" + course.getTitle() );;
						userCouponDTO.setCreaterId(users.getId());
						userCouponDTO.setOrderId(id);
						userCouponDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
						ryxUserService.addUserScore(userCouponDTO);
						
						/**
						 * 记录已经下载（查看）
						 */
						KeyrvDTO dto  = new KeyrvDTO();
						dto.setKey1(id.toString());
						dto.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
						dto.setRkey(users.getId().toString());
						dto.setIdeleted(0);
						ResultDTO<Boolean> result = systemService.createOrUpdateKeyrv(dto);
						errList = addList(errList, result.getErrorMsg());
						
					}
		
					/**
					 * 增加人气
					 */
					ResultDTO<Boolean> addDownResult = ryxCourseService.addDownloadCount(id);
					errList = addList(errList, addDownResult.getErrorMsg());
					
					download(request,response,course,errList);
					
				}
				else{
					errList.add("您的积分不够本次下载");
				}
			}
			
		}
		if(errList.size()>0){
			for(String err : errList){
				response.getWriter().write(err);
			}
		}
	}
	
	
	@RequestMapping("/my/viewdetail_{id}.html")
	public void viewdetail(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) 
			throws IOException{

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {
			
			KeyrvQuery query = new KeyrvQuery();
			query.setKey1(id.toString());
			query.setRkey(users.getId().toString());
			ResultDTO<Integer> cntResult = systemService.countQueryKeyrv(query);
			Integer cnt = cntResult.getModule();
			errList = addList(errList, cntResult.getErrorMsg());
			
			/**
			 * 判断是否下载过
			 */
			if(cntResult.isSuccess() &&  null != cnt  && cnt == 0){ 
				
				/**
				 *  尚未下载
				 */
				
				
				/**
				 * 判断积分是否够用
				 */
				Boolean isEnough = true;
				Double courseScore = course.getScore(); 
				if(null != courseScore && courseScore > 0){
					ResultDTO<Double> scoreResult = ryxUserService.getUserScoreById(users.getId());
					errList = addList(errList, scoreResult.getErrorMsg());
					Double userScore = scoreResult.getModule();
					
					if(userScore < courseScore){
						isEnough = false;
					}
				}
				
				if(isEnough){
					
					if(courseScore>0){
						
						/**
						 * 扣减积分
						 */
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(-courseScore);						
						userCouponDTO.setUserId(users.getId());
						userCouponDTO.setRemark("查看文库：" + course.getTitle() );;
						userCouponDTO.setCreaterId(users.getId());
						userCouponDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
						userCouponDTO.setOrderId(id);
						ryxUserService.addUserScore(userCouponDTO);
						
						
						/**
						 * 记录已经下载（查看）
						 */
						KeyrvDTO dto  = new KeyrvDTO();
						dto.setKey1(id.toString());
						dto.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
						dto.setRkey(users.getId().toString());
						dto.setIdeleted(0);
						ResultDTO<Boolean> result = systemService.createOrUpdateKeyrv(dto);
						errList = addList(errList, result.getErrorMsg());
						
					}
		
					/**
					 * 增加人气
					 */
					ResultDTO<Boolean> addDownResult = ryxCourseService.addDownloadCount(id);
					errList = addList(errList, addDownResult.getErrorMsg());
					
					response.sendRedirect("/article_" + id + ".html");
					
				}
				else{
					errList.add("您的积分不够本次下载");
				}
				
			}
			else{
				
				/**
				 *  已经下载，直接下载
				 */
				
				response.sendRedirect("/article_" + id + ".html");
			}
			
		}
		if(errList.size()>0){
			for(String err : errList){
				response.getWriter().write(err);
			}
		}
	}

	private void download(HttpServletRequest request,
			HttpServletResponse response,RyxCourseDTO course,ArrayList<String> errList){
		try{
			
			URL urlfile = null;
	        HttpURLConnection httpUrl = null;
	        BufferedInputStream bis = null;
			String filePath =  course.getUrl();
			if(!StringHelper.isNullOrEmpty(filePath)){
				String fileName = URLEncoder.encode(course.getTitle(),"utf-8") + filePath.substring(filePath.lastIndexOf('.') );
				urlfile = new URL(filePath);
				response.setContentType("application/octet-stream");
		        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
	            httpUrl = (HttpURLConnection)urlfile.openConnection();
	            httpUrl.connect();
	            bis = new BufferedInputStream(httpUrl.getInputStream());		            
	            int len = 2048;
	            byte[] b = new byte[len];
	            OutputStream out = response.getOutputStream();
	            while ((len = bis.read(b)) != -1)
	            {
	            	out.write(b, 0, len);
	            }
	            out.flush();
	            bis.close();
	            httpUrl.disconnect();
			}
			else{
				errList = addList(errList, "文件不存在");
			}
		}
		catch(IOException e){
			errList = addList(errList, e.toString());
		}
	}
	
	@RequestMapping("/article_{articleId}.html")
	public ModelAndView article(@PathVariable Long articleId,RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/carticle");
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(articleId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		try{
			if (null == course) {
				errList.add("invalid course id : " + articleId);
			} else { // course is not null
	
				if(StringHelper.isNullOrEmpty(course.getDescr())){
					RyxCourseDTO updateCourseDTO = new RyxCourseDTO();
					updateCourseDTO.setDescr(getArticleDescr(course.getUrl()));
					updateCourseDTO.setId(course.getId());
					ResultDTO<Boolean> updateResultDTO = ryxCourseService.updateCourse(updateCourseDTO);
					addList(errList, updateResultDTO.getErrorMsg());
				}
	
				mav.addObject("course", course);
	
				if (null != users) {
					Long tnow = System.currentTimeMillis() / 1000;
					ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), articleId, tnow);
					errList = addList(errList, buyResult.getErrorMsg());
					Integer buyFlag = buyResult.getModule();
					mav.addObject("buyFlag", buyFlag);
				}
	
				Long ts = System.currentTimeMillis();
				mav.addObject("ts", ts);
				logger.debug("Kd8jQHITMj" + course.getVid() + ts);
				mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
	
				/**
				 * 更新课程查看次数
				 */
				ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(articleId);
				errList = addList(errList, updateViewCountResult.getErrorMsg());
	
				/**
				 * 设置标题
				 */
				mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
	
				
	
				/**
				 * 评价
				 */
				query.setObjId(articleId);
				query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
				query.setIdeleted(0);
				query.setStatus(EnumAuditStatus.APPROVED.getCode());
				ResultDTO<RyxEvaluQuery> queryResult = MetaHelper.getInstance().queryEvalu(query);
				mav.addObject("query", queryResult.getModule());
				
				
				RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
				
				mav.addObject("teacher", teacherDTO);
				String msg = request.getParameter("msg");
	
				mav.addObject("msg", msg);
	
				/***
				 * 课程内容介绍刚要
				 */
				ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(articleId);
				errList = addList(errList, outlineResult.getErrorMsg());
				mav.addObject("outlines", outlineResult.getModule().getList());
	
				
				mav.addObject("errList", errList);
				mav.addObject("na", "course");
				mav.addObject("loginUsers", users);
				addPasswordModel(mav, request, getCurrentUrl(request));
				
			}
		}
		catch(Throwable t){
			logger.error(t.getMessage(),t);
			errList.add(t.getMessage());
		}
		return mav;
	}

	

	
	
	@RequestMapping("/recruit_{id}.html")
	public ModelAndView recruit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/crecruit");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/online_" + id + ".html");
			} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}


			mav.addObject("course", course);

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule()
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			mav.addObject("teacher", MetaHelper.getInstance().getTeacherByUserId(course.getCuid()));
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			/***
			 * 课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(id);
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());
			
			
			mav.addObject("errList", errList);
			mav.addObject("na", "index");
			mav.addObject("loginUsers", users);
			
		}
		return mav;
	}

	
	
	@RequestMapping("/commerce_{id}.html")
	public ModelAndView commerce(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccommerce");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/online_" + id + ".html");
			} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}


			mav.addObject("course", course);

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule()
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			mav.addObject("teacher", MetaHelper.getInstance().getTeacherByUserId(course.getCuid()));
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			/***
			 * 课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(id);
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());
			
		

			mav.addObject("errList", errList);
			mav.addObject("na", "index");
			mav.addObject("loginUsers", users);
			
		}
		return mav;
	}
	
	
	@RequestMapping("/activity_{id}.html")
	public ModelAndView activity(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cactivity");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/online_" + id + ".html");
			} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			mav.addObject("course", course);

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacher = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacher);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			/***
			 * 课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(id);
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());

		

			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}

	@RequestMapping("/cteacher_{id}.html")
	public ModelAndView cteacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccteacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	@RequestMapping("/train.html")
	public ModelAndView train(
			HttpServletRequest request,
			HttpServletResponse response,
			RyxTeacherQuery query,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctrain");
		
		mav.addObject("title", "融资租赁培训课程");

		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setIdeleted(0);				
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);

		String msg = request.getParameter("msg");

		mav.addObject("msg", msg);	
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "train");
		
		mav.addObject("loginUsers", users);
		addPasswordModel(mav, request, getCurrentUrl(request));
		

		return mav;
	}
	
	
	@RequestMapping("/train_goodat.html")
	public ModelAndView trainGoodat(
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctrgoodat");
		
		mav.addObject("title", "企业内训");


		String msg = request.getParameter("msg");

		mav.addObject("msg", msg);	
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "goodat");
		
		mav.addObject("loginUsers", users);
		
		

		return mav;
	}
	
	@RequestMapping("/train_flow.html")
	public ModelAndView trainFlow(
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctrflow");
		
		mav.addObject("title", "企业内训");


		String msg = request.getParameter("msg");

		mav.addObject("msg", msg);	
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "flow");
		
		mav.addObject("loginUsers", users);
		
		

		return mav;
	}
	
	@RequestMapping("/train_comment.html")
	public ModelAndView trainComment(
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ctrcomment");
		
		mav.addObject("title", "企业内训");


		String msg = request.getParameter("msg");

		mav.addObject("msg", msg);	
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "comment");
		
		mav.addObject("loginUsers", users);
		
		

		return mav;
	}
	
	@RequestMapping("/cteacher.html")
	public ModelAndView cteacher(
			Long id,
			Long mid,
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccteacher1");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/steacher_{id}.html")
	public ModelAndView steacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/csteacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	
	/**
	 * 
	 * @param courseId
	 * @param courseQuery
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/zrecruit_{courseId}.html")
	public ModelAndView zrecruit(
			@PathVariable Long courseId,
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/czrecruit");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(courseId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherResult);

			courseQuery.setCuid(course.getCuid());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			ResultDTO<RyxCourseQuery> r = MetaHelper.getInstance().queryCourseCache(courseQuery);
			errList = addList(errList, r.getErrorMsg());			
			
			String msg = request.getParameter("msg");
			mav.addObject("msg", msg);
			mav.addObject("query",  r.getModule());
			mav.addObject("errList", errList);
			mav.addObject("na", "zrecruit");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	
	
	@RequestMapping("/mcommerce_{courseId}.html")
	public ModelAndView mcommerce(
			@PathVariable Long courseId,
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cmcommerce");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(courseId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() 
					? "" : MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherResult);

			courseQuery.setCuid(course.getCuid());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> r = MetaHelper.getInstance().queryCourseCache(courseQuery);
			errList = addList(errList, r.getErrorMsg());			
			
			String msg = request.getParameter("msg");
			mav.addObject("msg", msg);
			mav.addObject("query",  r.getModule());
			mav.addObject("errList", errList);
			mav.addObject("na", "commerce");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	@RequestMapping("/zteacher_{id}.html")
	public ModelAndView zteacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/czteacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());			
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher",teacherResult);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/mteacher_{id}.html")
	public ModelAndView mteacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cmteacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());			
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher",teacherResult);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/oteacher_{id}.html")
	public ModelAndView oteacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/coteacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() 
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherResult);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/ateacher_{id}.html")
	public ModelAndView ateacher(
			@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cateacher");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());			
			mav.addObject("teacher", teacherResult);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "teacher");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	

	@RequestMapping("/cevalu_{id}.html")
	public ModelAndView cevalu(
			@PathVariable Long id,
			RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccevalu");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(id);
			evaluQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			evaluQuery.setIdeleted(0);
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
//fjy	
	@RequestMapping("/cvideoEvalu.html")
	public ModelAndView cvideoEvalu(
			Long id,
			Long tid,
			RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cvideoEvalu");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null
			
			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(id);
			evaluQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			evaluQuery.setIdeleted(0);
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getTid());
			ResultDTO<RyxTeacherDTO> resultTeacher = MetaHelper.getInstance().getTeacherById(course.getTid());
			RyxTeacherDTO teacherDTO2 = resultTeacher.getModule();
			
			mav.addObject("teacher", teacherDTO2);
			String msg = request.getParameter("msg");
			
			RyxCourseQuery query = new RyxCourseQuery();
			query.setTid(tid);
			mav.addObject("lastcourse",ryxCourseService.getCourseByTeacherId(query).getModule());

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	
	@RequestMapping("/cevalu.html")
	public ModelAndView cevalu(
			Long id,
			Long mid,
			RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccevalu1");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(id);
			evaluQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			evaluQuery.setIdeleted(0);
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/oevalu_{id}.html")
	public ModelAndView oevalu(
			@PathVariable Long id,
			RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/coevalu");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(id);
			evaluQuery.setIdeleted(0);
			evaluQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	/**
	 * 机构的评价， org ---> r
	 * @param id
	 * @param evaluQuery
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/revalu_{userId}.html")
	public ModelAndView revalu(
			@PathVariable Long userId,
			RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/crevalu");

		/**
		 * get feedback ，评价
		 */
		evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
		evaluQuery.setObjer(userId);
		evaluQuery.setIdeleted(0);
		ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", result.getModule());
			
		
		RyxTeacherDTO teacher = MetaHelper.getInstance().getTeacherByUserId(userId);
		mav.addObject("teacher", teacher);
		String msg = request.getParameter("msg");
		mav.addObject("title", teacher.getNickname() + "_" + teacher.getTags());	
		mav.addObject("msg", msg);

		mav.addObject("errList", errList);
		mav.addObject("na", "evalu");
		mav.addObject("loginUsers", users);
		addPasswordModel(mav, request, getCurrentUrl(request));
		
	
		return mav;
	}
	
	
	
	/**
	 * 课程评估（评价）
	 * @param
	 * @param evaluQuery
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/aevalu_{objId}.html")
	public ModelAndView aevalu(@PathVariable Long objId, RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/caevalu");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(objId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + objId);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + objId + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + objId + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), objId, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(objId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(objId);
			evaluQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			evaluQuery.setIdeleted(0);
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}

	
	
	/**
	 * 课程评估（评价）
	 * @param id
	 * @param evaluQuery
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sevalu_{id}.html")
	public ModelAndView sevalu(@PathVariable Long id, RyxEvaluQuery evaluQuery, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/csevalu");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback ，评价
			 */
			evaluQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			evaluQuery.setObjId(id);
			evaluQuery.setIdeleted(0);
			evaluQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			ResultDTO<RyxEvaluQuery> result = MetaHelper.getInstance().queryEvalu(evaluQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "evalu");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}

	
	@RequestMapping("/srelate_{id}.html")
	public ModelAndView srelate(@PathVariable Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/csrelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			mav.addObject("relatedCourse", MetaHelper.getInstance().getRelatedCourse(course.getRelatedCourse()));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/zrelate_{courseId}.html")
	public ModelAndView zrelate(@PathVariable Long courseId, 
			RyxCourseQuery courseQuery,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/czrelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(courseId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() 
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherResult);

			courseQuery.setCategory(course.getCategory());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			ResultDTO<RyxCourseQuery> r = MetaHelper.getInstance().queryCourseCache(courseQuery);
			errList = addList(errList, r.getErrorMsg());			
			
			String msg = request.getParameter("msg");
			mav.addObject("msg", msg);
			mav.addObject("query",  r.getModule());
			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	
	@RequestMapping("/mrelate_{courseId}.html")
	public ModelAndView mrelate(@PathVariable Long courseId, 
			RyxCourseQuery courseQuery,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cmrelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else { // course is not null
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(courseId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() 
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());
			
			RyxTeacherDTO teacherResult = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherResult);

			courseQuery.setCategory(course.getCategory());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> r = MetaHelper.getInstance().queryCourseCache(courseQuery);
			errList = addList(errList, r.getErrorMsg());			
			
			String msg = request.getParameter("msg");
			mav.addObject("msg", msg);
			mav.addObject("query",  r.getModule());
			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/crelate_{id}.html")
	public ModelAndView crelate(@PathVariable Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccrelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			mav.addObject("relatedCourse", MetaHelper.getInstance().getRelatedCourse(course.getRelatedCourse()));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	

	@RequestMapping("/crelate.html")
	public ModelAndView crelate(
			Long id, 
			Long mid,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccrelate1");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		
		course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			mav.addObject("relatedCourse", MetaHelper.getInstance().getRelatedCourse(course.getRelatedCourse()));

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	@RequestMapping("/cvideoRelate.html")
	public ModelAndView cvideoRelate(
			Long id, 
			Long tid,
			RyxCourseQuery query,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cvideoRelate");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		
		/*course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);*/

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			/*query.setPageSize(DEFAULT_PAGE_SIZE);
			query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setIdeleted(0);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setDisplay(1);*/
			
			
			mav.addObject("relatedCourse", ryxCourseService.getCourseByTeacherId(query).getModule());
			mav.addObject("lastcourse", ryxCourseService.getCourseByTeacherId(query).getModule());

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	

	/**
	 * 线下课程相关
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/orelate_{id}.html")
	public ModelAndView orelate(@PathVariable Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/corelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			mav.addObject("relatedCourse", MetaHelper.getInstance().getRelatedCourse(course.getRelatedCourse()));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	/**
	 * 相关文库
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/arelate_{id}.html")
	public ModelAndView arelate(@PathVariable Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/carelate");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */
			mav.addObject("relatedCourse", MetaHelper.getInstance().getRelatedCourse(course.getRelatedCourse()));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "relate");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}

	@RequestMapping("/cqa_{id}.html")
	public ModelAndView cqa(@PathVariable Long id, RyxQuestionQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccqa");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			/**
			 * 问答
			 */

			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setCid(id);
			query.setIdeleted(0);
			ResultDTO<RyxQuestionQuery> result = MetaHelper.getInstance().queryQuestion(query);
			mav.addObject("query", result.getModule());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "qa");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/cqa.html")
	public ModelAndView cqa(
			Long id,
			Long mid,
			RyxQuestionQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/ccqa1");

		RyxCourseDTO course = CourseHelper.getInstance().getCourseById(id);
		mav.addObject("course", course);
		course = CourseHelper.getInstance().getCourseById(mid);
		mav.addObject("mcourse", course);

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			/**
			 * 问答
			 */

			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setCid(id);
			query.setIdeleted(0);
			ResultDTO<RyxQuestionQuery> result = MetaHelper.getInstance().queryQuestion(query);
			mav.addObject("query", result.getModule());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "qa");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	
	
	@RequestMapping("/sqa_{id}.html")
	public ModelAndView sqa(@PathVariable Long id, RyxQuestionQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/csqa");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null

			if (EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/offline_" + id + ".html");
			} else if (EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}

			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			mav.addObject("course", course);
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ?
					"":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			/**
			 * 问答
			 */

			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			query.setCid(id);
			query.setIdeleted(0);
			ResultDTO<RyxQuestionQuery> result = MetaHelper.getInstance().queryQuestion(query);
			mav.addObject("query", result.getModule());

			

			
			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);
			mav.addObject("na", "qa");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
			
		}
		return mav;
	}
	

	@RequestMapping("/video_detail.html")
	public ModelAndView videoDetail(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/cvideoDetail");

		String uString = request.getParameter("u");
		if (StringHelper.isNullOrEmpty(uString)) {
			String u = getPartnerUstring();
			if (!StringHelper.isNullOrEmpty(u)) {
				mav = new ModelAndView("redirect:/mryx/video_detail.html?id=" + id + "&u=" + u);
				return mav;
			}
		}

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else { // course is not null
			if (users != null) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);

				/**
				 * get chatroom id by course id
				 */
				String roomId = "";
				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setKey1(id.toString());
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COURSE_CHAT_GROUP.getCode());
				ResultDTO<List<KeyvalueDTO>> keyvalueResultDTO = MetaHelper.getInstance().queryKeyvalueCache(keyvalueQuery);
				errList = addList(errList, keyvalueResultDTO.getErrorMsg());
				List<KeyvalueDTO> keyvalueDTOs = keyvalueResultDTO.getModule();
				if (null != keyvalueDTOs && keyvalueDTOs.size() == 1) {
					roomId = keyvalueDTOs.get(0).getValue();
				}
			}

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * get related course
			 */

			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = course.getRelatedCourse();
			List<RyxCourseDTO> tempList = null;
			if (relatedCourse != null) {
				RyxCourseDTO rcourse = null;
				ResultDTO<List<RyxCourseDTO>> rlistResult;
				for (String vid : relatedCourse.split(",")) {
					rlistResult = MetaHelper.getInstance().getCourseByVId(vid);
					errList = addList(errList, rlistResult.getErrorMsg());
					tempList = rlistResult.getModule();
					if (tempList != null && tempList.size() > 0) {
						rcourse = tempList.get(0);
						if (!list.contains(rcourse)) {
							list.add(rcourse);
						}
					}
				}

				rlistResult = MetaHelper.getInstance().getCourseByIds(relatedCourse);
				errList = addList(errList, rlistResult.getErrorMsg());
				List<RyxCourseDTO> courseList = rlistResult.getModule();
				if (rlistResult.isSuccess() && null != courseList && courseList.size() > 0) {
					list.addAll(courseList);
				}
			}

			mav.addObject("relatedCourse", list);

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * get feedback
			 */
			RyxFeedbackQuery feedbackQuery = new RyxFeedbackQuery();
			feedbackQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			feedbackQuery.setCourseId(id);
			ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(feedbackQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("comment", result.getModule().getList());
			mav.addObject("course", course);

			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

			mav.addObject("errList", errList);

			mav.addObject("loginUsers", users);

			/**
			 * 获取课程相关的问题 。
			 */
			RyxQuestionQuery questionQuery = new RyxQuestionQuery();
			questionQuery.setCid(id);
			ResultDTO<RyxQuestionQuery> questionResult = ryxUserService.queryQuestion(questionQuery);
			errList = addList(errList, questionResult.getErrorMsg());
			mav.addObject("questionQuery", questionResult.getModule());

			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			
			mav.addObject("teacher", teacherDTO);

			addPasswordModel(mav, request, getCurrentUrl(request));
			

		}

		mav.addObject("errList", errList);

		return mav;

	}

	

	@RequestMapping("/offline.html")
	public ModelAndView courseOfflineDetail(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("redirect:/offline_"+ id +".html");		
		return mav;
	}

	@RequestMapping("/register_teacher.html")
	public ModelAndView registerTeacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("title", "申请注册讲师");
		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/do_register_teacher.html")
	public ModelAndView doRegisterTeacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/my/register_subuser.html")
	public ModelAndView registerSubMryx(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/cregisterSubuser");
		mav.addObject("isGet", true);

		return mav;

	}

	@RequestMapping("/reset_password.html")
	public ModelAndView resetPasswordMryx(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/cresetPassd");
		return mav;

	}

	@RequestMapping("/my/change_password.html")
	public ModelAndView changePassword(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangePassword");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		return mav;

	}

	
	@RequestMapping("/my/change_mobile.html")
	public ModelAndView changeMobile(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangeMobile");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		return mav;

	}

	
	@RequestMapping("/my/change_email.html")
	public ModelAndView changeEmail(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangeEmail");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		return mav;

	}
	
	@RequestMapping("/register.html")
	public ModelAndView registerMryx(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		ModelAndView mav = new ModelAndView("/ryx/pc/cregister");		
		mav.addObject("isGet", true);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("targetUrl",request.getParameter("targetUrl"));
		return mav;

	}
	
	
	
	/**
	 * 微信注册绑定手机号
	 * @param w
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/bind_mobile.html")
	public ModelAndView bindMobile(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		ModelAndView mav = new ModelAndView("/ryx/pc/cbindMobile");		
		mav.addObject("isGet", true);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("targetUrl",request.getParameter("targetUrl"));
		mav.addObject("weixinUnionId", request.getParameter("weixinUnionId")) ;
		
		RyxRegisterDTO registerDTO = new RyxRegisterDTO();
		
		registerDTO.setWeixinUnionId(request.getParameter("weixinUnionId"));
		registerDTO.setTargetUrl(request.getParameter("targetUrl"));
		
		mav.addObject("registerDTO",registerDTO);
		
		return mav;

	}
	
	@RequestMapping("/my/security_question.html")
	public ModelAndView securityQuestion(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/csecurityQuestion");

		AttrValueDTO attrValueDTO = new AttrValueDTO();
		SecureQuesDTO sq = new SecureQuesDTO();

		ResultDTO<AttrValueDTO> result1 = systemService.queryAttrValueByUid(StringHelper.getKeyvalueUid(loginUsersDTO.getId().toString(),
				EnumAttrType.USER_SECURITY_QUESTION_1.getId()));
		errList = addList(errList, result1.getErrorMsg());
		if (result1.isSuccess() && null != result1.getModule()) {
			attrValueDTO = result1.getModule();
			sq.setQuestion1(Integer.parseInt(attrValueDTO.getValue()));
			sq.setAnswer1(attrValueDTO.getValue1());
		}

		result1 = systemService.queryAttrValueByUid(StringHelper.getKeyvalueUid(loginUsersDTO.getId().toString(), EnumAttrType.USER_SECURITY_QUESTION_2.getId()));
		errList = addList(errList, result1.getErrorMsg());
		if (result1.isSuccess() && null != result1.getModule()) {
			attrValueDTO = result1.getModule();
			sq.setQuestion2(Integer.parseInt(attrValueDTO.getValue()));
			sq.setAnswer2(attrValueDTO.getValue1());
		}

		result1 = systemService.queryAttrValueByUid(StringHelper.getKeyvalueUid(loginUsersDTO.getId().toString(), EnumAttrType.USER_SECURITY_QUESTION_3.getId()));
		errList = addList(errList, result1.getErrorMsg());
		if (result1.isSuccess() && null != result1.getModule()) {
			attrValueDTO = result1.getModule();
			sq.setQuestion3(Integer.parseInt(attrValueDTO.getValue()));
			sq.setAnswer3(attrValueDTO.getValue1());
		}

		mav.addObject("createDTO", sq);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		setSecurityQuestion(mav);

		return mav;

	}

	private void setSecurityQuestion(ModelAndView mav) {
		ResultDTO<List<KeyvalueDTO>> result = MetaHelper.getInstance().getSecurityQuestion();
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("questions", result.getModule());
	}

	@RequestMapping("/my/change_pay_password.html")
	public ModelAndView changePayPassword(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangePayPassword");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		return mav;

	}

	@RequestMapping("/do_register_sub_user.html")
	public ModelAndView doRegisterSubuserMryx(@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/cregisterSubuser");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("短信验证码无效");
			}

			RyxUsersQuery query = new RyxUsersQuery();
			query.setMobile(registerDTO.getMobile());
			query.setEmail(registerDTO.getEmail());

			ResultDTO<RyxUsersDTO> emailResult = ryxUserService.getUserByEmail(registerDTO.getEmail());
			errList = addList(errList, emailResult.getErrorMsg());
			RyxUsersDTO user = emailResult.getModule();
			if (null != user) {
				errList.add("该电子邮箱已经注册，请直接登录");
			}

			ResultDTO<RyxUsersDTO> usernameResult = ryxUserService.getUserByUsername(registerDTO.getUsername());
			errList = addList(errList, usernameResult.getErrorMsg());
			user = usernameResult.getModule();
			if (null != user) {
				errList.add("该电子邮箱已经注册，请直接登录");
			}

			ResultDTO<RyxUsersDTO> mobileResult = ryxUserService.getUserByMobile(registerDTO.getMobile());
			errList = addList(errList, mobileResult.getErrorMsg());
			user = mobileResult.getModule();
			if (null != user) {
				errList.add("该手机号码已经注册，请直接登录");
			}

			if (null == errList || errList.size() == 0) {

				user = new RyxUsersDTO();
				user.setEmail(registerDTO.getEmail());
				user.setPassword(Md5Util.GetMD5Code(registerDTO.getPassword()));
				user.setMobile(registerDTO.getMobile());
				user.setUsername(registerDTO.getUsername());
				user.setFlag(1);

				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {

					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {

						/**
						 * 注册成功送 20 代金券
						 */
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"元代金券");
						userCouponDTO.setCreaterId(userId);
						ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
						addList(errList, result.getErrorMsg());
						errList.add("注册成功，请<a href=\"/mryx/login.html\">登录</a>");
						// mav = new ModelAndView("redirect:/index.html");

					} else {
						errList.add("无效的用户Id");
					}
				} else {

				}
			}
		} else {

		}

		mav.addObject("errList", errList);
		// mav.addObject("createBindingResult", bindingResult);

		return mav;

	}

	@RequestMapping("/do_reset_password.html")
	public ModelAndView doResetPasswordMryx(@Valid @ModelAttribute("createDTO") RyxForgetPasswordDTO forgetPasswordDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/cresetPassd");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(forgetPasswordDTO.getMobile(), forgetPasswordDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("短信验证码无效");
			}

			if (!forgetPasswordDTO.getImgVerifyCode().toLowerCase()
					.equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				errList.add("图形验证码无效");
			}

			if (null == errList || errList.size() == 0) {

				ResultDTO<Boolean> updatepasswordResult = ryxUserService.updatePasswordByMobile(Md5Util.GetMD5Code(forgetPasswordDTO.getPassword()),
						forgetPasswordDTO.getMobile());
				errList = addList(errList, updatepasswordResult.getErrorMsg());
				if (updatepasswordResult.isSuccess()) {
					errList.add("密码重置成功，请记住新密码");
					mav.addObject("resetResult", 1);
				}
			}

		} else {

		}

		mav.addObject("errList", errList);
		mav.addObject("bindingResult", bindingResult);
		mav.addObject("isPost", true);
		// mav.addObject("createBindingResult", bindingResult);

		return mav;

	}

	@RequestMapping("/my/do_change_password.html")
	public ModelAndView doChangePassword(@Valid @ModelAttribute("createDTO") ChangePasswordDTO changePasswordDTO,
			BindingResult bindingResult,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt) 
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangePassword");
		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/my/mchangePassword");
		}

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(users.getMobile(), changePasswordDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码不正确，请重新输入");
			} 
			if (!users.getPassword().equals(Md5Util.GetMD5Code(changePasswordDTO.getPassword()))) {
				errList.add("原密码不正确");
			}
			if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
				errList.add("新密码与确认新密码不一致，请重新输入");
			}
			
			if(errList.size() == 0){
				ResultDTO<Boolean> updatepasswordResult = ryxUserService.updatePasswordById(Md5Util.GetMD5Code(changePasswordDTO.getNewPassword()),
						users.getId());
				errList = addList(errList, updatepasswordResult.getErrorMsg());
	
				if(errList.size() ==0 ){
					errList.add("登录密码修改成功，请记住新密码");
				}
			}

		} else {
			mav.addObject("bindingResult", bindingResult);
		}
		

		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}

	
	@RequestMapping("/my/do_change_mobile.html")
	public ModelAndView doChangeMobile(@Valid @ModelAttribute("createDTO") ChangeMobileDTO
			changeMobileDTO, BindingResult bindingResult,
			HttpServletRequest request, 
			HttpServletResponse response, 
			RedirectAttributes rt) 
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangeMobile");		
		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/my/mchangeMobile");
		}

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(changeMobileDTO.getMobile(), changeMobileDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码不正确，请重新输入");
			} else {
				
				if (users.getPassword().equals(Md5Util.GetMD5Code(changeMobileDTO.getPassword()))) {
					RyxUsersDTO newUser = new RyxUsersDTO();
					newUser.setMobile(changeMobileDTO.getMobile());
					newUser.setId(users.getId());
					ResultDTO<Boolean> updatepasswordResult = ryxUserService.updateUserById(newUser);
					errList = addList(errList, updatepasswordResult.getErrorMsg());

					if(errList.size() ==0 ){
						errList.add("手机号码设定成功");
					}
				} else {
					errList.add("密码不正确");
				}
			}

		} else {
			mav.addObject("bindingResult", bindingResult);
		}
		

		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}
	
	
	
	@RequestMapping("/my/do_change_email.html")
	public ModelAndView doChangeEmail(@Valid @ModelAttribute("createDTO") ChangeEmailDTO changeEmailDTO, BindingResult bindingResult,
									  HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangeEmail");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(users.getMobile(), changeEmailDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码不正确，请重新输入");
			} else {
				
				RyxUsersDTO newUser = new RyxUsersDTO();
				newUser.setEmail(changeEmailDTO.getEmail());
				newUser.setId(users.getId());
				ResultDTO<Boolean> updatepasswordResult = ryxUserService.updateUserById(newUser);
				errList = addList(errList, updatepasswordResult.getErrorMsg());

				if(errList.size() ==0 ){
					errList.add("电子邮箱设定成功");
				}
			
			}

		} else {
			mav.addObject("bindingResult", bindingResult);
		}
		

		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}
	
	@RequestMapping("/my/do_set_security_question.html")
	public ModelAndView doSetSecurityQuestion(@Valid @ModelAttribute("createDTO") SecureQuesDTO secureQuesDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/csecurityQuestion");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(users.getMobile(), secureQuesDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("短信验证码无效");
			}

			if (errList.size() == 0) {

				AttrValueDTO attrValueDTO = new AttrValueDTO();
				attrValueDTO.setOid(users.getId());
				attrValueDTO.setAttr(EnumAttrType.USER_SECURITY_QUESTION_1.getId());
				attrValueDTO.setValue(secureQuesDTO.getQuestion1().toString());
				attrValueDTO.setValue1(secureQuesDTO.getAnswer1());
				attrValueDTO.setUid(StringHelper.getKeyvalueUid(users.getId().toString(), EnumAttrType.USER_SECURITY_QUESTION_1.getId()));
				ResultDTO<Boolean> result = systemService.createOrUpdateAttrValue(attrValueDTO);
				errList = addList(errList, result.getErrorMsg());

				attrValueDTO.setAttr(EnumAttrType.USER_SECURITY_QUESTION_2.getId());
				attrValueDTO.setValue(secureQuesDTO.getQuestion2().toString());
				attrValueDTO.setValue1(secureQuesDTO.getAnswer2());
				attrValueDTO.setUid(StringHelper.getKeyvalueUid(users.getId().toString(), EnumAttrType.USER_SECURITY_QUESTION_2.getId()));
				result = systemService.createOrUpdateAttrValue(attrValueDTO);
				errList = addList(errList, result.getErrorMsg());

				attrValueDTO.setAttr(EnumAttrType.USER_SECURITY_QUESTION_3.getId());
				attrValueDTO.setValue(secureQuesDTO.getQuestion3().toString());
				attrValueDTO.setValue1(secureQuesDTO.getAnswer3());
				attrValueDTO.setUid(StringHelper.getKeyvalueUid(users.getId().toString(), EnumAttrType.USER_SECURITY_QUESTION_3.getId()));
				result = systemService.createOrUpdateAttrValue(attrValueDTO);
				errList = addList(errList, result.getErrorMsg());

				if (errList.size() == 0) {
					errList.add("设置成功，请记住您的新密码保护问题");
				}

			}

		} else {
			mav.addObject("bindingResult", bindingResult);
		}

		setSecurityQuestion(mav);

		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}

	@RequestMapping("/my/do_change_pay_password.html")
	public ModelAndView doChangePayPassword(@Valid @ModelAttribute("createDTO") ChangePayPasswordDTO changePayPasswordDTO, BindingResult bindingResult,
											HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cchangePayPassword");
		String serverName = request.getServerName().toLowerCase();
		if (!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/my/mchangePayPassword");
		}


		if (!bindingResult.hasErrors()) {

			/**
			 * 判断手机验证码
			 */
			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(users.getMobile(), changePayPasswordDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码不正确，请重新输入");
			}

			if (!users.getPassword().equals(Md5Util.GetMD5Code(changePayPasswordDTO.getPassword()))) {
				errList.add("登录密码输入有误，请重新输入");
			}

			/**
			 * 与登录密码进行比较
			 */
			if (users.getPassword().equals(Md5Util.GetMD5Code(changePayPasswordDTO.getNewPassword()))) {
				errList.add("支付密码不能与登录密码一致，请重新输入新支付密码");
			}
			
			if(!changePayPasswordDTO.getNewPassword().equals(changePayPasswordDTO.getConfirmNewPassword())){
				errList.add("支付密码与确认支付密码不一致，请重新输入");
			}

			if (errList.size() == 0) {
				AttrValueDTO attrValueDTO = new AttrValueDTO();
				attrValueDTO.setOid(users.getId());
				attrValueDTO.setAttr(EnumAttrType.USER_PAY_PASSWORD.getId());
				attrValueDTO.setValue(Md5Util.GetMD5Code(changePayPasswordDTO.getNewPassword()));
				attrValueDTO.setUid(StringHelper.getKeyvalueUid(users.getId().toString(), EnumAttrType.USER_PAY_PASSWORD.getId()));
				ResultDTO<Boolean> result = systemService.createOrUpdateAttrValue(attrValueDTO);
				errList = addList(errList, result.getErrorMsg());

				if(errList.size() ==0 ){
					errList.add("支付密码修改成功，请记住新密码");
				}
			}

		} else {
			mav.addObject("bindingResult", bindingResult);
		}

		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}

	@RequestMapping("/my/do_apply_course.html")
	public void doAjaxApplyCourse(@ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		keyvalueDTO.setValue(users.getId().toString());
		keyvalueDTO.setType(EnumKeyValueType.KV_APPLY_COURSE.getCode());
		ResultDTO<Boolean> resultDTO = systemService.createKeyvalue(keyvalueDTO);
		if (resultDTO.isSuccess()) {
			writeAjax(response, true);
		} else {
			if (resultDTO.getErrorCode().equals(EnumSqlState.INTEGRITY_CONSTRAINT_VIOLATION.getCode())) {
				writeAjax(response, true);
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}
		}
	}

	@RequestMapping("/do_register.html")
	public ModelAndView doRegisterMryx(@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mregister");
		String serverName = request.getServerName().toLowerCase();
		if (StringHelper.isNullOrEmpty(serverName) || serverName.indexOf("m.ryx365.com") < 0) {
			mav = new ModelAndView("/ryx/pc/cregister");
			if(StringHelper.isNullOrEmpty(registerDTO.getTargetUrl())){
				registerDTO.setTargetUrl("/default.html") ;
			}
		}
		
		
		

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("短信验证码无效");
			}

			if (!registerDTO.getImgVerifyCode().toLowerCase()
					.equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				errList.add("图形验证码无效");
			}

			RyxUsersQuery query = new RyxUsersQuery();
			query.setMobile(registerDTO.getMobile());
			query.setEmail(registerDTO.getEmail());

			ResultDTO<RyxUsersDTO> emailResult = ryxUserService.getUserByEmail(registerDTO.getEmail());
			errList = addList(errList, emailResult.getErrorMsg());
			RyxUsersDTO user = emailResult.getModule();
			if (null != user) {
				errList.add("该电子邮箱已经注册，请直接登录");
			}

		

			ResultDTO<RyxUsersDTO> mobileResult = ryxUserService.getUserByMobile(registerDTO.getMobile());
			errList = addList(errList, mobileResult.getErrorMsg());
			user = mobileResult.getModule();
			if (null != user) {
				errList.add("该手机号码已经注册，请直接登录");
			}
			
			if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
				errList.add("密码与确认密码不一致，请重新输入");
			}

			if (null == errList || errList.size() == 0) {

				
				/**
				 * 注册用户
				 */
				user = new RyxUsersDTO();
				user.setEmail(registerDTO.getEmail());
				user.setPassword(Md5Util.GetMD5Code(registerDTO.getPassword()));
				user.setMobile(registerDTO.getMobile());
				user.setUsername("ryx" + registerDTO.getMobile());
				user.setFlag(EnumUserLevel.COMMON_USER.getCode());
				user.setSid(RequestHelper.getCommonPartnerId(request,null));
				user.setRfrom(EnumRegFrom.PC.getCode());
				String icode = null;
				do{
					icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
				}while(null != ryxUserService.getUserIdByIcode(icode).getModule());
				user.setIcode(icode);
				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {

				
					
					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
						
						/**
						 * 注册成功送代金券
						 */
						
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("新用户注册");
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						userCouponDTO.setCreaterId(userId);
						ryxUserService.addUserCoupon(userCouponDTO);
							
						
//						if(null != ConstHelper.REGISTER_SCORE && ConstHelper.REGISTER_SCORE > 0){
//							UserCouponDTO userCouponDTO = new UserCouponDTO();
//							userCouponDTO.setCoupon(ConstHelper.REGISTER_SCORE);//
//							userCouponDTO.setUserId(userId);
//							userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"积分");
//							userCouponDTO.setCreaterId(userId);
//							ResultDTO<Boolean> result = ryxUserService.addUserScore(userCouponDTO);
//							addList(errList, result.getErrorMsg());
//						}
						
						
						
						/**
						 * 注册成功，给邀请人送体验券
						 */
						if(null != user.getSid() && user.getSid()>0){
							Integer inviteNbr = ryxUserService.getMyInviteNbr(user.getSid()).getModule();
							if(inviteNbr % ConstHelper.INVITE_REGIST_NBR_FOR_COUPON == 0 && inviteNbr > 0 ){
								userCouponDTO = new RyxUserCouponDTO();
								userCouponDTO.setCoupon(0.00);//
								userCouponDTO.setUserId(user.getSid());
								userCouponDTO.setType(EnumAccountType.EXPERIENCE_COUPON.getCode());
								userCouponDTO.setRemark("邀请满"+ inviteNbr +"人送免费体验券");
								userCouponDTO.setCreaterId(userId);
								ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
								addList(errList, result.getErrorMsg());
							}
						}
						
						
						mav.addObject("registerResult", "1");
						
						/*fjy*/
						
						String pre = CookieHelper.getCookies(SessionHelper.REGISTER_PRESENT, request, "/");
						if(!StringHelper.isNullOrEmpty(pre)){
						
							JSONObject jsonObject = JSONObject.fromObject(pre);	
							RyxPresentDTO presentDTO = (RyxPresentDTO)JSONObject.toBean(jsonObject, RyxPresentDTO.class);
							CookieHelper.removeCookies(SessionHelper.REGISTER_PRESENT,  "/", request, response);
							if (presentDTO.getType() == EnumObjectType.OFFLINE_MODULE.getCode()) {//线下课程
								/*CourseDTO dto = ryxCourseService.getCourseById(Long.parseLong(value)).getModule();
								ApplyDTO applyDTO = new ApplyDTO();
								applyDTO.setNbr(1);
								applyDTO.setContact("");
								applyDTO.setStatus(EnumOrderStatus.PRESENT.getCode());
								applyDTO.setAddr("");
								applyDTO.setEmail(user.getEmail());
								applyDTO.setMobile(user.getMobile());
								applyDTO.setOid(dto.getId());
								applyDTO.setOtype(dto.getObjType());
								applyDTO.setObjer(dto.getCuid());
								applyDTO.setUserId(userId);
								ResultDTO<Long> resultDTO = ryxUserService.createApply(applyDTO);*/
							}else if (presentDTO.getType()== EnumObjectType.ONLINE_MODULE.getCode()) {//在线课程
								RyxCourseDTO dto = CourseHelper.getInstance().getCourseById(presentDTO.getValue());
								RyxOrderDTO order = new RyxOrderDTO();
								order.setOrderUid(userId);
								order.setRealPrice(0.0);
								order.setDiscount1(0.0);
								order.setStatus(EnumOrderStatus.PRESENT.getCode()); 
								order.setOrderAmount(0.0);
								order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());				
								order.setUid(NumberExUtils.longIdString(8));
								order.setOriginalPrice(dto.getOprice());
								order.setIfFeedback(0);
								order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
								order.setDiscount2(1.0);
								order.setCreater(userId);
								
								Long[] cids = { (long)dto.getId()};
								order.setCourseIds(cids);
								ResultDTO<Long> createOrderResult1 = ryxOrderService.saveOrder(order);
								errList = addList(errList, createOrderResult1.getErrorMsg());
								
								if(createOrderResult1.isSuccess()){
									order.setTnow(System.currentTimeMillis() / 1000);
									order.setId(order.getId());
									
									order.setPayType(EnumPayType.OUTER_ADMIN_PAY.getCode());
									order.setTpay(new Date());
									order.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); 
	
	
									order.setCoupon(0.0);
									order.setDiscount1(order.getDiscount1());
									
									ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(order);
									errList = addList(errList, updateOrderResult.getErrorMsg());
									
									if(updateOrderResult.isSuccess()){
										
										ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(dto.getId());
										errList = addList(errList, updateStudyCountResult.getErrorMsg());
										
									}
								}
										
							}else if (presentDTO.getType() == EnumObjectType.SCORE_MODULE.getCode()) {//积分
								RyxUserCouponDTO userCouponDTO2 = new RyxUserCouponDTO();
								userCouponDTO2.setCoupon(presentDTO.getValue().doubleValue());//
								userCouponDTO2.setUserId(userId);
								userCouponDTO2.setType(EnumAccountType.SCORE.getCode());
								userCouponDTO2.setRemark("抽奖获得");
								userCouponDTO2.setCreaterId(userId);
								userCouponDTO2.setUid(userId.toString());
								ResultDTO<Boolean> resultDTO = ryxUserService.addUserScore(userCouponDTO2);
								errList = addList(errList, resultDTO.getErrorMsg());
								
								
							}else if (presentDTO.getType() == EnumObjectType.COUPON_MODULE.getCode()) {//代金券
								RyxUserCouponDTO userCouponDTO1 = new RyxUserCouponDTO();
								userCouponDTO1.setCoupon(presentDTO.getValue().doubleValue());//
								userCouponDTO1.setUserId(userId);
								userCouponDTO1.setType(EnumAccountType.COUPON.getCode());
								userCouponDTO1.setRemark("抽奖获得");
								userCouponDTO1.setCreaterId(userId);
								userCouponDTO1.setLimi(System.currentTimeMillis()/1000 + 
										ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
								ResultDTO<Boolean> result1 = ryxUserService.addUserCoupon(userCouponDTO1);
								errList = addList(errList, result1.getErrorMsg());
							}else if (presentDTO.getType() == EnumObjectType.ARTICLE_MODULE.getCode()) {//文章
								ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(presentDTO.getValue());
								errList = addList(errList, courseResult.getErrorMsg());
								RyxCourseDTO course = courseResult.getModule();
								KeyrvDTO dto  = new KeyrvDTO();
								dto.setKey1(userId.toString());
								dto.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
								dto.setRkey(presentDTO.getValue().toString());
								dto.setIdeleted(0);
								ResultDTO<Boolean> result2 = systemService.createOrUpdateKeyrv(dto);
								errList = addList(errList, result2.getErrorMsg());
								ResultDTO<Boolean> addDownResult = ryxCourseService.addDownloadCount(presentDTO.getValue());
								errList = addList(errList, addDownResult.getErrorMsg());
							}
						}

					} else {
						errList.add("无效的用户Id");
					}
				} else {

				}
			}
		} else {

		}

		mav.addObject("errList", errList);
		
		mav.addObject("targetUrl",registerDTO.getTargetUrl()) ;
		
		// mav.addObject("createBindingResult", bindingResult);

		return mav;


	}
	
	
	/***
	 * 
	 * @param registerDTO
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/do_weixin_register.html")
	public ModelAndView doWeixinRegisterMryx( RyxRegisterDTO registerDTO,
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws Exception {
		errList = new ArrayList<String>();
		
		
		if(StringHelper.isNullOrEmpty(registerDTO.getIsAgreeProtocal())){
			errList.add("请同意【用户服务协议及隐私政策】");
		}
		
		if(StringHelper.isNullOrEmpty(registerDTO.getMobile())){
			errList.add("请输入手机号码");
		}
		
		if(StringHelper.isNullOrEmpty(registerDTO.getVerifyCode())){
			errList.add("请输入手机验证码");
		}

		ModelAndView mav = new ModelAndView("/ryx/pc/cbindMobile");
		if(StringHelper.isNullOrEmpty(registerDTO.getTargetUrl())){
			registerDTO.setTargetUrl("/default.html") ;
		}


		if(0 == errList.size()){
			
			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());
	
			if (null == list) {
				errList.add("短信验证码无效");
			}
	
			if (!registerDTO.getImgVerifyCode().toLowerCase()
					.equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				errList.add("图形验证码无效");
			}

		}
	
		
		

		if (null == errList || errList.size() == 0) {

			

			ResultDTO<RyxUsersDTO> mobileResult = ryxUserService.getUserByMobile(registerDTO.getMobile());
			errList = addList(errList, mobileResult.getErrorMsg());
			RyxUsersDTO user = mobileResult.getModule();
			if (null != user) {
				user.setWeixinUnionId(registerDTO.getWeixinUnionId());
				ryxUserService.updateUserById(user); 
				
				mav = new ModelAndView("/ryx/pc/cautoLogin");
				mav.addObject("id", StringHelper.aesEncrypt(user.getId().toString(), ConstHelper.APP_ENCRYPT_KEY));
				mav.addObject("targetUrl", registerDTO.getTargetUrl()) ;	
			}
			
			else{
				/**
			
				 * 注册用户
				 */
				user = new RyxUsersDTO();
				user.setEmail(registerDTO.getEmail());
				user.setPassword(Md5Util.GetMD5Code(StringHelper.generateRandomPassword(6)));
				user.setMobile(registerDTO.getMobile());
				user.setUsername("ryx" + registerDTO.getMobile());
				user.setFlag(EnumUserLevel.COMMON_USER.getCode());
				user.setSid(RequestHelper.getCommonPartnerId(request,null));
				user.setRfrom(EnumRegFrom.PC.getCode());
				String icode = null;
				do{
					icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
				}while(null != ryxUserService.getUserIdByIcode(icode).getModule());
				user.setIcode(icode);
				user.setWeixinUnionId(registerDTO.getWeixinUnionId());
				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());
	
				if (createUserResult.isSuccess()) {

					mav.addObject("registerResult","1");
					
					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
						
						/**
						 * 注册成功送代金券
						 */
						
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("新用户注册");
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						userCouponDTO.setCreaterId(userId);
						ryxUserService.addUserCoupon(userCouponDTO);
							
						
	//						if(null != ConstHelper.REGISTER_SCORE && ConstHelper.REGISTER_SCORE > 0){
	//							UserCouponDTO userCouponDTO = new UserCouponDTO();
	//							userCouponDTO.setCoupon(ConstHelper.REGISTER_SCORE);//
	//							userCouponDTO.setUserId(userId);
	//							userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"积分");
	//							userCouponDTO.setCreaterId(userId);
	//							ResultDTO<Boolean> result = ryxUserService.addUserScore(userCouponDTO);
	//							addList(errList, result.getErrorMsg());
	//						}
						
						
						
						/**
						 * 注册成功，给邀请人送体验券
						 */
						if(null != user.getSid() && user.getSid()>0){
							Integer inviteNbr = ryxUserService.getMyInviteNbr(user.getSid()).getModule();
							if(inviteNbr % ConstHelper.INVITE_REGIST_NBR_FOR_COUPON == 0 && inviteNbr > 0 ){
								userCouponDTO = new RyxUserCouponDTO();
								userCouponDTO.setCoupon(0.00);//
								userCouponDTO.setUserId(user.getSid());
								userCouponDTO.setType(EnumAccountType.EXPERIENCE_COUPON.getCode());
								userCouponDTO.setRemark("邀请满"+ inviteNbr +"人送免费体验券");
								userCouponDTO.setCreaterId(userId);
								ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
								addList(errList, result.getErrorMsg());
							}
						}
						
						
						mav.addObject("registerResult", "1");
						
						/*fjy*/
						
						String pre = CookieHelper.getCookies(SessionHelper.REGISTER_PRESENT, request, "/");
						if(!StringHelper.isNullOrEmpty(pre)){
						
							JSONObject jsonObject = JSONObject.fromObject(pre);	
							RyxPresentDTO presentDTO = (RyxPresentDTO)JSONObject.toBean(jsonObject, RyxPresentDTO.class);
							CookieHelper.removeCookies(SessionHelper.REGISTER_PRESENT,  "/", request, response);
							if (presentDTO.getType() == EnumObjectType.OFFLINE_MODULE.getCode()) {//线下课程
								/*CourseDTO dto = ryxCourseService.getCourseById(Long.parseLong(value)).getModule();
								ApplyDTO applyDTO = new ApplyDTO();
								applyDTO.setNbr(1);
								applyDTO.setContact("");
								applyDTO.setStatus(EnumOrderStatus.PRESENT.getCode());
								applyDTO.setAddr("");
								applyDTO.setEmail(user.getEmail());
								applyDTO.setMobile(user.getMobile());
								applyDTO.setOid(dto.getId());
								applyDTO.setOtype(dto.getObjType());
								applyDTO.setObjer(dto.getCuid());
								applyDTO.setUserId(userId);
								ResultDTO<Long> resultDTO = ryxUserService.createApply(applyDTO);*/
							}else if (presentDTO.getType()== EnumObjectType.ONLINE_MODULE.getCode()) {//在线课程
								RyxCourseDTO dto = CourseHelper.getInstance().getCourseById(presentDTO.getValue());
								RyxOrderDTO order = new RyxOrderDTO();
								order.setOrderUid(userId);
								order.setRealPrice(0.0);
								order.setDiscount1(0.0);
								order.setStatus(EnumOrderStatus.PRESENT.getCode()); 
								order.setOrderAmount(0.0);
								order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());				
								order.setUid(NumberExUtils.longIdString(8));
								order.setOriginalPrice(dto.getOprice());
								order.setIfFeedback(0);
								order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
								order.setDiscount2(1.0);
								order.setCreater(userId);
								
								Long[] cids = { (long)dto.getId()};
								order.setCourseIds(cids);
								ResultDTO<Long> createOrderResult1 = ryxOrderService.saveOrder(order);
								errList = addList(errList, createOrderResult1.getErrorMsg());
								
								if(createOrderResult1.isSuccess()){
									order.setTnow(System.currentTimeMillis() / 1000);
									order.setId(order.getId());
									
									order.setPayType(EnumPayType.OUTER_ADMIN_PAY.getCode());
									order.setTpay(new Date());
									order.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); 
	
	
									order.setCoupon(0.0);
									order.setDiscount1(order.getDiscount1());
									
									ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(order);
									errList = addList(errList, updateOrderResult.getErrorMsg());
									
									if(updateOrderResult.isSuccess()){
										
										ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(dto.getId());
										errList = addList(errList, updateStudyCountResult.getErrorMsg());
										
									}
								}
										
							}else if (presentDTO.getType() == EnumObjectType.SCORE_MODULE.getCode()) {//积分
								RyxUserCouponDTO userCouponDTO2 = new RyxUserCouponDTO();
								userCouponDTO2.setCoupon(presentDTO.getValue().doubleValue());//
								userCouponDTO2.setUserId(userId);
								userCouponDTO2.setType(EnumAccountType.SCORE.getCode());
								userCouponDTO2.setRemark("抽奖获得");
								userCouponDTO2.setCreaterId(userId);
								userCouponDTO2.setUid(userId.toString());
								ResultDTO<Boolean> resultDTO = ryxUserService.addUserScore(userCouponDTO2);
								errList = addList(errList, resultDTO.getErrorMsg());
								
								
							}else if (presentDTO.getType() == EnumObjectType.COUPON_MODULE.getCode()) {//代金券
								RyxUserCouponDTO userCouponDTO1 = new RyxUserCouponDTO();
								userCouponDTO1.setCoupon(presentDTO.getValue().doubleValue());//
								userCouponDTO1.setUserId(userId);
								userCouponDTO1.setType(EnumAccountType.COUPON.getCode());
								userCouponDTO1.setRemark("抽奖获得");
								userCouponDTO1.setCreaterId(userId);
								userCouponDTO1.setLimi(System.currentTimeMillis()/1000 + 
										ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
								ResultDTO<Boolean> result1 = ryxUserService.addUserCoupon(userCouponDTO1);
								errList = addList(errList, result1.getErrorMsg());
							}else if (presentDTO.getType() == EnumObjectType.ARTICLE_MODULE.getCode()) {//文章
								ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(presentDTO.getValue());
								errList = addList(errList, courseResult.getErrorMsg());
								RyxCourseDTO course = courseResult.getModule();
								KeyrvDTO dto  = new KeyrvDTO();
								dto.setKey1(userId.toString());
								dto.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
								dto.setRkey(presentDTO.getValue().toString());
								dto.setIdeleted(0);
								ResultDTO<Boolean> result2 = systemService.createOrUpdateKeyrv(dto);
								errList = addList(errList, result2.getErrorMsg());
								ResultDTO<Boolean> addDownResult = ryxCourseService.addDownloadCount(presentDTO.getValue());
								errList = addList(errList, addDownResult.getErrorMsg());
							}
						}
	
					} else {
						errList.add("无效的用户Id");
					}
				} else {
	
				}
			}
		}
		

		mav.addObject("errList", errList);
		
		mav.addObject("targetUrl",registerDTO.getTargetUrl()) ;
		
		mav.addObject("registerDTO",registerDTO);
		
		// mav.addObject("createBindingResult", bindingResult);

		return mav;


	}

	@RequestMapping(value = "/send_register_verify_code.html", method = RequestMethod.POST)
	public void sendRegisterVerifyCode(
			@RequestParam(value = "mobile") String mobile, 
			@RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			
			
			String referer = request.getHeader("Referer");
			if(StringHelper.isNullOrEmpty(referer)){
				writeAjax(response, false, "操作过于频繁，请60分钟之后再试");
				return ;
			}
			
//			if(StringHelper.checkSendMobileSecond(30,request)){
//				writeAjax(response, false, "操作过于频繁，请30分钟之后再试");
//				return ;
//			}
//			
//			if(StringHelper.checkSendMobileTimes(request)){
//				writeAjax(response, false, "已超出短信获取限制，请联系客服");
//				return ;
//			}
			
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else if (StringHelper.isNullOrEmpty(imgVerifyCode)) {
				writeAjax(response, false, "请输入图形验证码");
			} else if (!imgVerifyCode.toLowerCase().equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				writeAjax(response, false, "图形验证码有误，请重新输入");
			} else {
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);
				if (userResult.isSuccess()) {

					RyxUsersDTO users = userResult.getModule();

					if (null != users) {
						writeAjax(response, false, "该手机号已经存在，请<a href=\"/mryx/login.html\">直接登录</a>");
					} else {
						String text = "【融易学】欢迎您注册融易学；您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
						if (result.isSuccess()) {
							writeAjax(response, true, "", result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(), result.getModule());
						}
					}
				} else {
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/send_bind_verify_code.html", method = RequestMethod.POST)
	public void sendBindVerifyCode(
			@RequestParam(value = "mobile") String mobile, 
			@RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			
			
			String referer = request.getHeader("Referer");
			if(StringHelper.isNullOrEmpty(referer)){
				writeAjax(response, false, "操作过于频繁，请60分钟之后再试");
				return ;
			}
						
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else if (StringHelper.isNullOrEmpty(imgVerifyCode)) {
				writeAjax(response, false, "请输入图形验证码");
			} else if (!imgVerifyCode.toLowerCase().equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request,response).toString().toLowerCase())) {
				writeAjax(response, false, "图形验证码有误，请重新输入");
			} else {
				
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);
				if (userResult.isSuccess()) {

					RyxUsersDTO users = userResult.getModule();
					
					String text = "【融易学】欢迎您注册融易学；您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
					ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
					if (result.isSuccess()) {
						writeAjax(response, true, "", result.getModule());
					} else {
						writeAjax(response, false, result.getErrorMsg(), result.getModule());
					}
					
				} else {
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	

	@RequestMapping(value = "/send_common_verify_code.html", method = RequestMethod.POST)
	public void sendCommonVerifyCode(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else if (!StringHelper.isNullOrEmpty(imgVerifyCode)) {
				writeAjax(response, false, "请输入图形验证码");
			} else if (!imgVerifyCode.equals(CookieHelper.getCookies(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, "/"))) {
				writeAjax(response, false, "图形验证码有误，请重新输入");
			} else {
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);
				if (userResult.isSuccess()) {

					RyxUsersDTO users = userResult.getModule();

					if (null != users) {
						writeAjax(response, false, "该手机号已经存在，请<a href=\"/mryx/login.html\">直接登录</a>");
					} else {
						String text = "【融易学】欢迎您注册融易学；您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
						if (result.isSuccess()) {
							writeAjax(response, true, "", result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(), result.getModule());
						}
					}
				} else {
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping(value = "/my/send_common_verify_code.html", method = RequestMethod.POST)
	public void sendMyCommonVerifyCode(@RequestParam(value = "imgVerifyCode") String imgVerifyCode, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO user = getRyxUser();
			String mobile = user.getMobile();

			if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else if (!StringHelper.isNullOrEmpty(imgVerifyCode)) {
				writeAjax(response, false, "请输入图形验证码");
			} else if (!imgVerifyCode.equals(CookieHelper.getCookies(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, "/"))) {
				writeAjax(response, false, "图形验证码有误，请重新输入");
			} else {
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);
				if (userResult.isSuccess()) {

					RyxUsersDTO users = userResult.getModule();

					if (null != users) {
						writeAjax(response, false, "该手机号已经存在，请<a href=\"/mryx/login.html\">直接登录</a>");
					} else {
						String text = "【融易学】欢迎您注册融易学；您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
						if (result.isSuccess()) {
							writeAjax(response, true, "", result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(), result.getModule());
						}
					}
				} else {
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	@RequestMapping(value = "/my/send_common_verify_code1.html", method = RequestMethod.POST)
	public void sendMyCommonVerifyCode1(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO user = getRyxUser();
			String mobile = user.getMobile();

			if (StringHelper.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请在“安全设置”中设置您的手机号码");
			} else {
				
				String text = "【融易学】您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
				ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
				if (result.isSuccess()) {
					writeAjax(response, true, "", result.getModule());
				} else {
					writeAjax(response, false, result.getErrorMsg(), result.getModule());
				}				
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping(value = "/my/send_common_verify_code2.html", method = RequestMethod.POST)
	public void sendMyCommonVerifyCode2(HttpServletRequest request, String mobile,HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO user = getRyxUser();
			if (StringHelper.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else {				
				String text = "【融易学】您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
				ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
				if (result.isSuccess()) {
					writeAjax(response, true, "", result.getModule());
				} else {
					writeAjax(response, false, result.getErrorMsg(), result.getModule());
				}				
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	@RequestMapping(value = "/my/send_change_password_code.html", method = RequestMethod.POST)
	public void sendChangePasswordCode(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			RyxUsersDTO user = getRyxUser();
			String mobile = user.getMobile();
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "您的手机号码为空，请完善您的信息");
			} else {
				String text = "【融易学】您修改密码的验证码是：{random}，如非本人发起请忽略。";
				ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
				if (result.isSuccess()) {
					writeAjax(response, true, "", result.getModule());
				} else {
					writeAjax(response, false, result.getErrorMsg(), result.getModule());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping(value = "/my/send_security_question_code.html", method = RequestMethod.POST)
	public void sendSecurityQuestionCode(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			RyxUsersDTO user = getRyxUser();
			String mobile = user.getMobile();
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "您的手机号码为空，请完善您的信息");
			} else {
				String text = "【融易学】您设置密保问题的验证码是：{random}，如非本人发起请忽略。";
				ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);
				if (result.isSuccess()) {
					writeAjax(response, true, "", result.getModule());
				} else {
					writeAjax(response, false, result.getErrorMsg(), result.getModule());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping(value = "/send_reset_password_verify_code.html", method = RequestMethod.POST)
	public void sendResetPasswordVerifyCode(@RequestParam(value = "mobile") String mobile, @RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else if (StringHelper.isNullOrEmpty(imgVerifyCode)) {
				writeAjax(response, false, "请输入图形验证码");
			} else if (!imgVerifyCode.toLowerCase().equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				writeAjax(response, false, "图形验证码有误，请重新输入");
			} else {

				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);
				if (userResult.isSuccess()) {

					RyxUsersDTO users = userResult.getModule();

					if (null == users) {
						writeAjax(response, false, "该手机号不存在，请<a href=\"/mryx/register.html\">免费注册</a>");
					} else {
						String text = "【融易学】您重置密码的验证码是：{random}，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);

						if (result.isSuccess()) {
							writeAjax(response, true, "", result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(), null);
						}
					}
				} else {
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	

	@RequestMapping("/my/apply_personal.html")
	public ModelAndView applyPersonal(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ResultDTO<RyxTeacherDTO> result = ryxTeacherService.getTeacherByUserId(users.getId());
		errList = addList(errList, result.getErrorMsg());
		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyPersonal");
		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("na", "apply_personal");
		mav.addObject("createDTO", result.getModule());
		return mav;

	}
	
	
	@RequestMapping("/my/apply_company.html")
	public ModelAndView applyCompany(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ResultDTO<RyxUsersDTO> result = ryxUserService.getUserById(users.getId());
		errList = addList(errList, result.getErrorMsg());
		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyCompany");
		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("createDTO", result.getModule());
		mav.addObject("na", "apply_company");
		return mav;

	}

	
	@RequestMapping("/my/apply_org.html")
	public ModelAndView applyOrg(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ResultDTO<RyxTeacherDTO> result = ryxTeacherService.getTeacherByUserId(users.getId());
		errList = addList(errList, result.getErrorMsg());
		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyOrg");
		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("createDTO", result.getModule());
		mav.addObject("na", "apply_org");
		return mav;

	}
	
	
	@RequestMapping("/my/publish_course.html")
	public ModelAndView publishCourse(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ResultDTO<RyxAuthDTO> result = ryxUserService.getAuthByUid(users.getId());
		errList = addList(errList, result.getErrorMsg());
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpublishCourse");
		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		mav.addObject("applyTeacherResult", result.getModule());

		return mav;

	}

	@RequestMapping("/my/do_apply_personal.html")
	public ModelAndView doApplyPersonal(HttpServletRequest request, 
			@Valid @ModelAttribute("createDTO") PersonalAuthDTO createDTO,
			BindingResult bindingResult, HttpServletResponse response) 
					throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyPersonal");

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		
		createDTO.setUid(users.getId());
		mav.addObject("createBindingResult", bindingResult);


		if (!bindingResult.hasErrors()) {
			
			RyxTeacherDTO teacherDTO = new RyxTeacherDTO();
			
			BeanUtils.copyProperties(createDTO,teacherDTO,BeanHelper.getNullPropertyNames(createDTO));
			

			if (EnumAuditStatus.UNAUDITED.getCode() != teacherDTO.getStatus()) {
				teacherDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
			}

			ResultDTO<Long> result = ryxTeacherService.createOrUpdateTeacher(teacherDTO);
			createDTO.setId(teacherDTO.getId());
			errList = addList(errList, result.getErrorMsg());
			

			if(errList.size() == 0){
				errList.add("资料已经提交，我们会在3个工作日之内给您回复，请耐心等待审核并等待我们的通知");
			}
		}

		mav.addObject("errList", errList);
		mav.addObject("na", "apply_personal");
		return mav;

	}

	
	
	@RequestMapping("/my/do_apply_company.html")
	public ModelAndView doApplyCompany(HttpServletRequest request, @Valid @ModelAttribute("createDTO") CompanyAuthDTO createDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyCompany");

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		
		mav.addObject("createBindingResult", bindingResult);


		if (!bindingResult.hasErrors()) {
			
			RyxUsersDTO usersDTO = new RyxUsersDTO();
			
			
			BeanUtils.copyProperties(createDTO,usersDTO,BeanHelper.getNullPropertyNames(createDTO));
			

			if (EnumAuditStatus.UNAUDITED.getCode() != usersDTO.getStatus()) {
				usersDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
			}
			usersDTO.setFlag(EnumUserLevel.COMPANY_USER.getCode());
			usersDTO.setId(users.getId());
			ResultDTO<Boolean> result = ryxUserService.updateUserById(usersDTO);
			errList = addList(errList, result.getErrorMsg());
			

			if(errList.size() == 0){
				errList.add("资料已经提交，我们会在3个工作日之内给您回复，请耐心等待审核并等待我们的通知");
			}
		}

		mav.addObject("errList", errList);
		mav.addObject("na", "apply_company");
		return mav;

	}
	
	
	@RequestMapping("/my/do_apply_org.html")
	public ModelAndView doApplyOrg(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OrgAuthDTO createDTO,
			BindingResult bindingResult, HttpServletResponse response)
					throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyOrg");

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		
		createDTO.setFlag(EnumTeacherType.ORG.getCode());
		createDTO.setUid(users.getId());
		mav.addObject("createBindingResult", bindingResult);


		if (!bindingResult.hasErrors()) {
			
			RyxTeacherDTO teacherDTO = new RyxTeacherDTO();
			
			BeanUtils.copyProperties(createDTO,teacherDTO,BeanHelper.getNullPropertyNames(createDTO));

			if (EnumAuditStatus.UNAUDITED.getCode() != teacherDTO.getStatus()) {
				teacherDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
			}
			ResultDTO<Long> result = ryxTeacherService.createOrUpdateTeacher(teacherDTO);
			errList = addList(errList, result.getErrorMsg());
			createDTO.setId(teacherDTO.getId());

			if(errList.size() == 0){
				errList.add("资料已经提交，我们会在3个工作日之内给您回复，请耐心等待审核并等待我们的通知");
			}
		}
		mav.addObject("na", "apply_org");
		mav.addObject("errList", errList);
		return mav;

	}

	
	
	
	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param updateUserProfileDTO
	 * @param bindingResult
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/do_update_user_image.html")
	public ModelAndView doUpdateUserImage(HttpServletRequest request, @Valid @ModelAttribute("createDTO") UserImageDTO userImageDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cimage");

		errList = new ArrayList<String>();

		RyxUsersDTO loginUsersDTO = getRyxUser();
		mav.addObject("loginUsers", loginUsersDTO);
		mav.addObject("createBindingResult", bindingResult);

		if (!bindingResult.hasErrors()) {

			RyxUsersDTO users = new RyxUsersDTO();
			
			BeanUtils.copyProperties(userImageDTO,users,BeanHelper.getNullPropertyNames(userImageDTO));
			users.setId(loginUsersDTO.getId());
			ResultDTO<Boolean> result = ryxUserService.updateUserById(users);
			errList = addList(errList, result.getErrorMsg());
		
		}
		else{
			ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(loginUsersDTO.getId());
			errList = addList(errList, userResult.getErrorMsg());
			mav.addObject("createDTO", userResult.getModule());
		}

		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param updateUserProfileDTO
	 * @param bindingResult
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/do_update_user_cert.html")
	public ModelAndView doUpdateUserCert(HttpServletRequest request, @Valid @ModelAttribute("createDTO") UserCertDTO certDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccert");

		errList = new ArrayList<String>();

		RyxUsersDTO loginUsersDTO = getRyxUser();
		mav.addObject("loginUsers", loginUsersDTO);
		mav.addObject("createBindingResult", bindingResult);

		if (!bindingResult.hasErrors()) {

			RyxAuthDTO auth = new RyxAuthDTO();
			
			BeanUtils.copyProperties(certDTO,auth,BeanHelper.getNullPropertyNames(certDTO));
			auth.setUid(loginUsersDTO.getId());
			auth.setAuthStatus(EnumAuditStatus.UNAUDITED.getCode());
			ResultDTO<Boolean> result = ryxUserService.updateAuth(auth);
			errList = addList(errList, result.getErrorMsg());
			
		}

		mav.addObject("errList", errList);
		mav.addObject("isPost", true);
		mav.addObject("na", "ert");

		return mav;

	}

	

	@RequestMapping("/my/course.html")
	public ModelAndView myCourse(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccourse");

		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);

		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setUserId(users.getId());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);

		/**
		 * 过期课程
		 */
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyExpiredCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("expiredCourse", courseResult.getModule().getList());

		/**
		 * 线下课程
		 */
		courseResult = ryxCourseService.getMyOfflineCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("offlineCourse", courseResult.getModule().getList());

		/**
		 * 未过期课程
		 */
		courseResult = ryxCourseService.getMyUnexpiredCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("unexpiredCourse", courseResult.getModule().getList());

		mav.addObject("errList", errList);

		return mav;

	}
	@RequestMapping("/my/follow_course.html")
	public ModelAndView myFollowCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowCourse");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
//		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		List<Integer> objTypes = new ArrayList<>();
		objTypes.add(EnumObjectType.ONLINE_MODULE.getCode());
		objTypes.add(EnumObjectType.OFFLINE_MODULE.getCode());
		objTypes.add(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setObjTypes(objTypes);
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());

		mav.addObject("followCourse", followCourseResult.getModule());

		mav.addObject("na", "my_follow_course");

		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}


	@RequestMapping("/my/follow_teacher.html")
	public ModelAndView myFollowTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowTeacher");

		mav.addObject("loginUsers", users);
		teacherQuery.setUserId(users.getId());
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxTeacherQuery> followTeacherResult = ryxTeacherService.getMyFollowTeacher(teacherQuery);
		errList = addList(errList, followTeacherResult.getErrorMsg());

		mav.addObject("query", followTeacherResult.getModule());
		mav.addObject("errList", errList);

		mav.addObject("na", "my_follow_teacher");

		return mav;

	}

	@RequestMapping("/my/follow_article.html")
	public ModelAndView myFollowArticle(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowArticle");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());

		mav.addObject("follow", followCourseResult.getModule());

		mav.addObject("na", "my_follow_article");


		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	
	@RequestMapping("/my/download_article.html")
	public ModelAndView myDownloadArticle(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cdownloadArticle");

		mav.addObject("loginUsers", users);
		
		courseQuery.setUserId(users.getId());
		
		KeyrvQuery query = new KeyrvQuery();
		query.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
		query.setRkey(users.getId().toString());
		ResultDTO<List<KeyrvDTO>> resultDTO =  systemService.queryKeyrvByRkey(query);
		List<KeyrvDTO> keyrvDTOList = resultDTO.getModule();
		errList = addList(errList, resultDTO.getErrorMsg());
		List<RyxCourseDTO> articleDTOList = new ArrayList<>();
		for (int i = 0; i < keyrvDTOList.size(); i++) {
			String key = keyrvDTOList.get(i).getKey1();
			RyxCourseDTO arg0 = ryxCourseService.getCourseById(Long.parseLong(key)).getModule();
			articleDTOList.add(arg0);
		}
		

		mav.addObject("download",articleDTOList);

		mav.addObject("na", "my_download_article");


		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	@RequestMapping("/my/follow_recruit.html")
	public ModelAndView myFollowRecruit(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowRecruit");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());
		mav.addObject("follow", followCourseResult.getModule());
		mav.addObject("na", "my_follow_recruit");


		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	@RequestMapping("/my/follow_commerce.html")
	public ModelAndView myFollowCommerce(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowCommerce");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());
		mav.addObject("follow", followCourseResult.getModule());
		mav.addObject("na", "my_follow_commerce");


		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	

	@RequestMapping("/all_online.html")
	public ModelAndView allOnline(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/callOnline");
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());

		mav.addObject("title", "金融培训教育在线课程");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}



	

	@RequestMapping("/my/index.html")
	public ModelAndView myIndex(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("redirect:/my/account.html");
		return mav;

	}

	@RequestMapping("/my/image.html")
	public ModelAndView myImage(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cimage");

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());

		

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "image");

		return mav;

	}

	@RequestMapping("/my/cert.html")
	public ModelAndView myCert(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccert");

		ResultDTO<RyxAuthDTO> userResult = ryxUserService.getAuthByUid(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());


		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "ert");

		return mav;

	}

	@RequestMapping("/my/security.html")
	public ModelAndView mySecurity(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/csecurity");

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("users", userResult.getModule());


		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "security");

		return mav;

	}

	

	@RequestMapping("/my/exam.html")
	public ModelAndView myExam(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cexam");

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("users", userResult.getModule());


		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "exam");

		return mav;

	}

	@RequestMapping("/list_video.html")
	public ModelAndView listVideoCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_video.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_video.html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_video.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistVideoCourse");
		
		
		if(EnumVideoStatus.AFTER_LIVING.getCode() == courseQuery.getInterval()){ // 已经结束
			courseQuery.setEtend(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.BEFORE_LIVING.getCode() == courseQuery.getInterval()){  // 尚未开始
			courseQuery.setTtstart(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.LIVING.getCode() == courseQuery.getInterval()){   // 直播中 
			courseQuery.setEtstart(System.currentTimeMillis()/1000);
			courseQuery.setTtend(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.PLAY_BACK.getCode() == courseQuery.getInterval()){   // 直播中 
			courseQuery.setEtend(System.currentTimeMillis()/1000);
			courseQuery.setVid("1");
		}
		else{
			mav.addObject("unendVideo",MetaHelper.getInstance().getUnendVideo(Integer.MAX_VALUE));
			courseQuery.setEtend(System.currentTimeMillis()/1000);
		}
		
		
		courseQuery.setPageSize(18);		
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
		}
		//courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());

	

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("title", "金融培训教育_直播课程");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}

	@RequestMapping("/my/unexpired_course.html")
	public ModelAndView myUnexpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cunexpiredCourse");

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		//courseQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse2(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("na", "unexpired");

		return mav;

	}
	
	
	@RequestMapping("/my/ecourse.html")
	public ModelAndView myEcourse(
			HttpServletRequest request, RyxUserEcourseQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		if(null == query.getCategory()){
			query.setCategory(MetaHelper.getInstance().getCategoryByCode("ecourse_rzzl").getId().intValue());
		}
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cecourse");
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxUserEcourseQuery> courseResult = ryxCourseService.queryEcourse(query);
		errList = addList(errList, courseResult.getErrorMsg());

		query = courseResult.getModule();
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "ecourse");

		return mav;

	}
	
	
	

	@RequestMapping("/my/expired_course")
	public ModelAndView myExpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cexpiredCourse");

		String price = courseQuery.getPrice();

		try {

			if (!StringHelper.isNullOrEmpty(price)) {
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setUserId(users.getId());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyExpiredCourse2(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("course", null==courseQuery ? null : courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "expired");

		return mav;

	}

	
	
	@RequestMapping("/my/expired_card")
	public ModelAndView myExpiredCard(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cexpiredCard");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.CARD_MODULE.getCode());
		courseQuery.setUserId(users.getId());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyExpiredCourse1(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("course", null==courseQuery ? null : courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "expired_card");

		return mav;

	}
	
	

	@RequestMapping("/ajax_list_course_first.html")
	public void ajaxListCourseFirst(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			String price = courseQuery.getPrice();
			if (!StringHelper.isNullOrEmpty(price)) {
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);

			ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
			if (courseResult.isSuccess()) {
				writeAjax(response, true, "", courseResult.getModule());
			} else {
				writeAjax(response, false, courseResult.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	@RequestMapping("/my/ajax_my_follow_course_first.html")
	public void ajaxMyFollowCourseFirst(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			String price = courseQuery.getPrice();
			if (!StringHelper.isNullOrEmpty(price)) {
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);

			ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().getMyFellowCourse(courseQuery);
			if (courseResult.isSuccess()) {
				writeAjax(response, true, "", courseResult.getModule());
			} else {
				writeAjax(response, false, courseResult.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	@RequestMapping("/ajax_list_course.html")
	public void ajaxListOnlineCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			String price = courseQuery.getPrice();
			if (!StringHelper.isNullOrEmpty(price)) {
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);

			ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
			if (courseResult.isSuccess()) {
				writeAjax(response, true, "", courseResult.getModule().getList());
			} else {
				writeAjax(response, false, courseResult.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	@RequestMapping("/my/ajax_do_create_question.html")
	public void ajaxDoCreateQuestion(HttpServletRequest request, RyxQuestionDTO questionDTO, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO users = getRyxUser();
			questionDTO.setUserId(users.getId());
			ResultDTO<Long> resultDTO = ryxUserService.createQuestion(questionDTO);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "");
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	@RequestMapping("/my/ajax_get_question.html")
	public void ajaxGetQuestion(HttpServletRequest request, RyxQuestionQuery query, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			ResultDTO<RyxQuestionQuery> resultDTO = ryxUserService.queryQuestion(query);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "", resultDTO.getModule());
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	@RequestMapping("/my/ajax_do_create_answer.html")
	public void ajaxDoCreateAnswer(HttpServletRequest request, RyxAnswerDTO answerDTO, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO users = getRyxUser();
			answerDTO.setUserId(users.getId());
			ResultDTO<Long> resultDTO = ryxUserService.createAnswer(answerDTO);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "");
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	
	@RequestMapping("/my/ajax_do_create_follow_question.html")
	public void ajaxDoCreateAnswer(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO users = getRyxUser();
			dto.setKey1(users.getId().toString());
			dto.setType(EnumKeyRelatedValueType.KV_FOLLOW_QUESTION.getCode());
			dto.setIdeleted(0);
			ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(dto);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "");
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/my/ajax_do_create_evalu.html")
	public void ajaxDoCreateEvalu(HttpServletRequest request, RyxEvaluDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			dto.setUserId(users.getId());
			dto.setStatus(EnumAuditStatus.UNAUDITED.getCode());
			ResultDTO<Boolean> resultDTO = ryxUserService.createOrUpdateEvalu(dto);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "");
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}
	
	

	@RequestMapping(value = "/my/ajax_do_create_keyrv.html")
	public void ajaxDoCreateKeyrv(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			ResultDTO<Boolean> result = systemService.createOrUpdateKeyrv(dto);
			if (result.isSuccess()) {
				writeAjax(response, true, "" );
			} else {
				writeAjax(response, false, result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}

	
	@RequestMapping(value = "/my/ajax_do_delete_keyrv.html")
	public void ajaxDoDeleteKeyrv(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			ResultDTO<Boolean> result = systemService.deleteKeyrvByDTO(dto);
			if(dto.getType() == EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode()){
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				courseDTO.setId(Long.parseLong(dto.getKey1()));
				courseDTO.setCreater(users.getId());
				updateMainCourse(courseDTO);
			}
			if (result.isSuccess()) {
				writeAjax(response, true, "" );
			} else {
				writeAjax(response, false, result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/my/ajax_do_update_keyrv.html")
	public void ajaxDoUpdateKeyrv(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			ResultDTO<Boolean> result = systemService.updateKeyrv(dto);
			if (result.isSuccess()) {
				writeAjax(response, true, "" );
			} else {
				writeAjax(response, false, result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}

	
	@RequestMapping(value = "/my/ajax_do_apply_recruit.html")
	public void ajaxDoApplyRecruit(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO users = getRyxUser();
			dto.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
			ResultDTO<ResumeDTO> resultDTO = userService.queryResumeByUserId(users.getId());
			if(resultDTO.isSuccess() && null != resultDTO.getModule()){
				dto.setKey1(users.getId().toString());
				ajaxDoCreateKeyrv(request,dto,response,rt);
			}
			else{
				writeAjax(response, false, "请在会员中心创建个人简历", null);
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}
	
	

	
	@RequestMapping("/my/ajax_get_answer.html")
	public void ajaxGetQuestion(HttpServletRequest request, RyxAnswerQuery query, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			ResultDTO<RyxAnswerQuery> resultDTO = ryxUserService.queryAnswer(query);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "", resultDTO.getModule());
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}

	
	
	@RequestMapping("/list_offline_video.html")
	public ModelAndView listOfflineVideo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistOfflineVideo");
		

		//courseQuery.setTtstart(System.currentTimeMillis()/1000);
		
		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getMonthendLongSecond(30L));
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
			System.out.println(DateHelper.getTomorrow());
			courseQuery.setTtstart(DateHelper.getTomorrowLong());
			courseQuery.setEtstart(DateHelper.getTomorrowLong(1));
		}
		else if(EnumIntervalType.THIS_WEEKEND.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getWeekendLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.THIS_MONTH_AFTER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.TODAY_BEFORE.getCode() == courseQuery.getInterval()){
			courseQuery.setEtstart(DateHelper.getTodayLongSecond());
		}	
		else if(EnumIntervalType.THREE_MONTH_INNER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getMonthendLongSecond(90L));
		}
		

		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("desc");
		courseQuery.setUrl(1);
		
		courseQuery = MetaHelper.getInstance().queryOfflineCourseCache(courseQuery);

		List<RyxCategoryDTO> offlineCategoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys", offlineCategoryResult);

		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		
		mav.addObject("citys", cityListResult);


		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("intervals", EnumIntervalType.getList());
		if (null != courseQuery.getCategory()) {
			ResultDTO<RyxCategoryQuery> subcateResult = MetaHelper.getInstance().getCategoryByPid(courseQuery.getCategory());
			errList = addList(errList, subcateResult.getErrorMsg());
			mav.addObject("subcates", subcateResult.getModule().getList());
			String title = MetaHelper.getInstance().getCategoryById(courseQuery.getCategory()).getModule().getTitle();
			if(null != courseQuery.getSubcate()){
				title += "_" + MetaHelper.getInstance().getCategoryById(courseQuery.getSubcate()).getModule().getTitle();
			}
			mav.addObject("title", title + "_培训课程");
		}
		else{
			mav.addObject("title", "融资租赁培训课程_线下直播培训");
		}
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}


	@RequestMapping("/list_offline.html")
	public ModelAndView listOffline(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_offline.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_offline.html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_offline.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistOffline");
		

		//courseQuery.setTtstart(System.currentTimeMillis()/1000);
		
		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getMonthendLongSecond(30L));
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
			System.out.println(DateHelper.getTomorrow());
			courseQuery.setTtstart(DateHelper.getTomorrowLong());
			courseQuery.setEtstart(DateHelper.getTomorrowLong(1));
		}
		else if(EnumIntervalType.THIS_WEEKEND.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getWeekendLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.THIS_MONTH_AFTER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.TODAY_BEFORE.getCode() == courseQuery.getInterval()){
			courseQuery.setEtstart(DateHelper.getTodayLongSecond());
		}	
		else if(EnumIntervalType.THREE_MONTH_INNER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getMonthendLongSecond(90L));
		}
		

		if(null == courseQuery.getInterval()){
			
			/**
			 * 第一页
			 */
			if( null == courseQuery.getCurrentPage() || courseQuery.getCurrentPage() ==1){
				RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
				BeanUtils.copyProperties(courseQuery,ryxCourseQuery ,BeanHelper.getNullPropertyNames(courseQuery));
				ryxCourseQuery.setPageSize(Integer.MAX_VALUE);
				mav.addObject("unendList", MetaHelper.getInstance().getUnendOffline(ryxCourseQuery));
			}

			courseQuery.setEtstart(System.currentTimeMillis()/1000);
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
			
		}
		else{
			
			
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery = MetaHelper.getInstance().queryOfflineCourseCache(courseQuery);

		List<RyxCategoryDTO> offlineCategoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys", offlineCategoryResult);

		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();
		
		mav.addObject("citys", cityListResult);


		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("intervals", EnumIntervalType.getList());
		if (null != courseQuery.getCategory()) {
			ResultDTO<RyxCategoryQuery> subcateResult = MetaHelper.getInstance().getCategoryByPid(courseQuery.getCategory());
			errList = addList(errList, subcateResult.getErrorMsg());
			mav.addObject("subcates", subcateResult.getModule().getList());
			String title = MetaHelper.getInstance().getCategoryById(courseQuery.getCategory()).getModule().getTitle();
			if(null != courseQuery.getSubcate()){
				title += "_" + MetaHelper.getInstance().getCategoryById(courseQuery.getSubcate()).getModule().getTitle();
			}
			mav.addObject("title", title + "_培训课程");
		}
		else{
			mav.addObject("title", "融资租赁培训课程_线下培训");
		}
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}
	
	
	
	@RequestMapping("/list_info.html")
	public ModelAndView listInfo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistInfo");
		

		
		

		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.INFO_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery = MetaHelper.getInstance().queryCourseCache(courseQuery).getModule();

		
		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("title", "融资租赁培训课程_行业资讯");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}
	
	
	
	@RequestMapping("/list_book.html")
	public ModelAndView listBook(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistBook");
		

		
		

		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.BOOK_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery = MetaHelper.getInstance().queryCourseCache(courseQuery).getModule();

		
		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("title", "融资租赁培训课程_行业书籍");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}
	
	
	@RequestMapping("/list_recruit.html")
	public ModelAndView listRecruit(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistRecruit");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		List<RyxCategoryDTO> offlineCategoryResult = MetaHelper.getInstance().getOfflineCategory();		
		mav.addObject("categorys", offlineCategoryResult);

		List<KeyrvDTO> cityListResult = MetaHelper.getInstance().getOfflineCourseCityList();		
		mav.addObject("citys", cityListResult);

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("title", "金融培训教育线下课程");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}

	@RequestMapping("/list_activity.html")
	public ModelAndView listActivity(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistActivity");
		
		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLongSecond());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
			System.out.println(DateHelper.getTomorrow());
			courseQuery.setTtstart(DateHelper.getTomorrowLong());
			courseQuery.setEtstart(DateHelper.getTomorrowLong(1));
		}
		else if(EnumIntervalType.THIS_WEEKEND.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getWeekendLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.THIS_MONTH_AFTER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.TODAY_BEFORE.getCode() == courseQuery.getInterval()){
			courseQuery.setEtstart(DateHelper.getTodayLongSecond());
		}
		
		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		List<RyxCategoryDTO> offlineCategoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("categorys", offlineCategoryResult);

		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);

		mav.addObject("intervals", EnumIntervalType.getList());


		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		mav.addObject("title", "金融培训教育活动");

		return mav;

	}

	
	@RequestMapping("/list_commerce.html")
	public ModelAndView listCommerce(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistCommerce");
		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		List<KeyvalueDTO> cityListResult = MetaHelper.getInstance().getActivityCourseCityList();
		
		mav.addObject("citys", cityListResult);

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		mav.addObject("title", "金融商业服务");

		return mav;

	}
	
	@RequestMapping("/list_article.html")
	public ModelAndView listArticle(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistArticle");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		

		ResultDTO<RyxCategoryQuery> category = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		errList = addList(errList, category.getErrorMsg());
		mav.addObject("categorys", category.getModule().getList());


		/**
		 * 文档类型
		 */
		mav.addObject("articleTypes", EnumArticleType.getList());

		

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("title", "金融培训教育材料");

		return mav;

	}

	@RequestMapping("/tarticle_{userId}.html")
	public ModelAndView tarticle(HttpServletRequest request, RyxCourseQuery courseQuery, @PathVariable Long userId, HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/ctarticle");

		courseQuery.setCuid(userId);
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		ResultDTO<RyxCategoryQuery> category = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		errList = addList(errList, category.getErrorMsg());
		mav.addObject("categorys", category.getModule().getList());

		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(userId);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		/**
		 * 文档类型
		 */
		mav.addObject("articleTypes", EnumArticleType.getList());

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner
		mav.addObject("na", "article");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("title", "金融培训教育材料");
		return mav;
	}
	
	@RequestMapping("/rarticle_{userId}.html")
	public ModelAndView rarticle(HttpServletRequest request, RyxCourseQuery courseQuery, 
			@PathVariable Long userId, HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/crarticle");

		courseQuery.setCuid(userId);
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		ResultDTO<RyxCategoryQuery> category = MetaHelper.getInstance().getArticleCategory(Integer.MAX_VALUE);
		errList = addList(errList, category.getErrorMsg());
		mav.addObject("categorys", category.getModule().getList());


		RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(userId);
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + "_" + teacherDTO.getTags());

		/**
		 * 文档类型
		 */
		mav.addObject("articleTypes", EnumArticleType.getList());

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		/**
		 * 广告 banner
		 */
		ResultDTO<List<RyxAdDTO>> adResult = MetaHelper.getInstance().queryAdCache(136);
		errList = addList(errList, adResult.getErrorMsg());
		mav.addObject("banners", adResult.getModule());// banner

		mav.addObject("na", "article");

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("title", "金融培训教育材料");

		return mav;

	}

	@RequestMapping("/my/ajax_offline_course.html")
	public void ajaxMyOfflineCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setFlag(1);
			courseQuery.setUserId(users.getId());
			ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyOfflineCourse(courseQuery);
			if (courseResult.isSuccess()) {
				writeAjax(response, true, "", courseResult.getModule().getList());
			} else {
				writeAjax(response, false, courseResult.getErrorMsg());
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	

	private static String PATH_FOLDER = "/";
	// 存放临时文件的目录
	private static String TEMP_FOLDER = "/";

	

	private FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}

	private String getUploadFileName(FileItem item) {
		// 获取路径名
		String value = item.getName().toLowerCase();
		// 索引到最后一个反斜杠
		int start = value.lastIndexOf("/");
		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		String filename = value.substring(start + 1);

		return filename;
	}

	@RequestMapping("/list_teacher.html")
	public ModelAndView listTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/clistTeacher");

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);

		/**
		 * 讲师类别
		 */
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());


		mav.addObject("title", "金融培训教育讲师机构");
		mav.addObject("errList", errList);
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param teacherQuery
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/ajax_list_teacher.html")
	public void ajaxListTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);

		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule().getList());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}

	@RequestMapping("/ajax_get_category_by_teacher_id.html")
	public void ajaxGetCategoryByTeacherId(@RequestParam(value = "tid") Long tid, HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);

		ResultDTO<RyxCategoryDTO> result = MetaHelper.getInstance().getCategoryByTeacherId(tid);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}

	@RequestMapping("/ajax_get_category_by_pid.html")
	public void ajaxGetCategoryByPid(@RequestParam(value = "pid") Integer pid, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ResultDTO<String> result = MetaHelper.getInstance().getCategoryJsonByPid(pid);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}
	
	@RequestMapping("/ajax_get_p1_by_p0.html")
	public void ajaxGetPosition1ByPosition0(@RequestParam(value = "p0") String p0, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ResultDTO<String> result = MetaHelper.getInstance().getPosition1JsonByPosition0(p0);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}
	
	@RequestMapping("/ajax_get_p2_by_p1.html")
	public void ajaxGetPosition1ByPosition0(
			@RequestParam(value = "p0") String p0,
			@RequestParam(value = "p1") String p1,  
			HttpServletRequest request, 
			HttpServletResponse response, 
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ResultDTO<String> result = MetaHelper.getInstance().getPosition2JsonByPosition1(p0,p1);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}

	@RequestMapping("/ajax_list_teacher_first.html")
	public void ajaxListTeacherFirst(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}

	/**
	 * 
	 * @param request
	 * @param teacherQuery
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/ajax_get_teacher_by_id.html")
	public void ajaxGetTeacherById(HttpServletRequest request, Long id, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		// ResultDTO<TeacherQuery> result =
		// MetadataHelper.getInstance().queryTeacherCache1(teacherQuery);
		ResultDTO<RyxTeacherDTO> result = MetaHelper.getInstance().getTeacherById(id);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/ajax_get_current_user.html")
	public void ajaxGetCurrentUser(HttpServletRequest request, Long id, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {
		RyxUsersDTO usersDTO = getRyxUser();
		if(null == usersDTO){
			writeAjax(response, false);
		}
		else{
			writeAjax(response, true);
		}
	}

	/**
	 * 
	 * @param request
	 * @param teacherQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/list_teacher1")
	public ModelAndView listTeacher1(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistTeacher1");

		teacherQuery.setFlag(0);

		// ResultDTO<TeacherQuery> result =
		// MetadataHelper.getInstance().queryTeacherCache1(teacherQuery);
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache(teacherQuery);
		errList = addList(errList, result.getErrorMsg());

		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);
		mav.addObject("teacher", teacherQuery.getList());
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/list_comment")
	public ModelAndView listComment(HttpServletRequest request, RyxFeedbackQuery query, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistComment");

		query.setStatus(EnumCommentStatus.APPROVED.getCode());
		ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(query);
		errList = addList(errList, result.getErrorMsg());

		if (null != users) {
			Long tnow = System.currentTimeMillis() / 1000;
			ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), query.getCourseId(), tnow);
			errList = addList(errList, buyResult.getErrorMsg());
			Integer buyFlag = buyResult.getModule();
			mav.addObject("buyFlag", buyFlag);
		}

		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("comment", query.getList());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/search.html")
	public ModelAndView search(HttpServletRequest request, String keyword, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		
	
		mav.addObject("path", request.getServletPath());
		mav.addObject("keyword", keyword);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping(value = "/search_online.html")
	public ModelAndView doSearchOnline(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "online");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		

		return mav;

	}
	
	
	@RequestMapping(value = "/search_info.html")
	public ModelAndView doSearchInfo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.INFO_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "info");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		

		return mav;

	}
	
	
	@RequestMapping(value = "/search_video.html")
	public ModelAndView doSearchVideo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule()); 

		
		mav.addObject("na", "video");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		
		

		return mav;

	}
	
	
	

	
	
	
	
	@RequestMapping(value = "/search_offline.html")
	public ModelAndView doSearchOffline(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "offline");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	
	
	
	@RequestMapping(value = "/search_activity.html")
	public ModelAndView doSearchActivity(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "activity");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	@RequestMapping(value = "/search_recruit.html")
	public ModelAndView doSearchRecruit(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "recruit");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	@RequestMapping(value = "/search_teacher.html")
	public ModelAndView doSearchTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearchTeacher");
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		ResultDTO<RyxTeacherQuery> courseResult = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "teacher");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	@RequestMapping(value = "/search_article.html")
	public ModelAndView doSearchArticle(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/csearch");
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "article");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	@RequestMapping("/info_{code}.html")
	public ModelAndView info(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cinfo");
		RyxAdDTO ad = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",ad);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/intro_{code}.html")
	public ModelAndView intro_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro");
		}
		
		if(code.equals("register_protocol_app")){
			return new ModelAndView("redirect:/intro1_register_protocol.html");
		}
		
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		
		Boolean isWeixinExplorer = isWeixinExplorer(request);		
		if(isWeixinExplorer){
			setCourseWeixinSpread(dto.getTitle(),HtmlHelper.unescapeHtmlAndDeleteHtmlTag(dto.getContent(),52),mav,request);
		}
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		
		return mav;

	}
	
	
	
	@RequestMapping("/wxintro_{code}.html")
	public ModelAndView weixinIntro_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro");
		}
		if(isWeixinExplorer(request)){
			code = "wx_" + code ;
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/wxintro1_{code}.html")
	public ModelAndView weixinIntro1_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro1");
		}
		if(isWeixinExplorer(request)){
			if(code.length()<=2 || !code.substring(0, 3).equals("wx_")){
				code = "wx_" + code ;
			}
		}
		else {
			if(code.length()>2 && code.substring(0, 3).equals("wx_")){
				code = code.substring(3);
			}
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/wxintro2_{code}.html")
	public ModelAndView weixinIntro2_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro2");
		}
		if(isWeixinExplorer(request)){
			if(code.length()<=2 || !code.substring(0, 3).equals("wx_")){
				code = "wx_" + code ;
			}
		}
		else {
			if(code.length()>2 && code.substring(0, 3).equals("wx_")){
				code = code.substring(3);
			}
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/intro1_{code}.html")
	public ModelAndView intro1_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro1");
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/intro2_{code}.html")
	public ModelAndView intro2_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro2");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro2");
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/intro3_{code}.html")
	public ModelAndView intro3_(@PathVariable String code,HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/cintro3");

		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mintro3");
		}
		RyxAdDTO dto = MetaHelper.getInstance().getSingleAdByCode(code);
		mav.addObject("obj",dto);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread(dto.getTitle(),HtmlHelper.unescapeHtmlAndDeleteHtmlTag(dto.getDescr(), 52),mav,request);
		}
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		
		return mav;

	}
	

	@RequestMapping("/my/do_ajax_create_comment.html")
	public void doAjaxCreateComment(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxFeedbackDTO feedback)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		if (null == feedback.getCourseId() || 0 == feedback.getCourseId()) {
			errList.add("课程参数有误");
		}

		/**
		 * 判断是否已经评价
		 */

		RyxFeedbackQuery feedbackQuery = new RyxFeedbackQuery();
		feedbackQuery.setOrderId(feedback.getOrderDetailId());
		feedbackQuery.setCourseId(feedback.getCourseId());
		feedbackQuery.setUserId(users.getId());
		ResultDTO<RyxFeedbackQuery> feedbackQueryResult = ryxUserService.queryFeedkack(feedbackQuery);
		errList = addList(errList, feedbackQueryResult.getErrorMsg());

		if (feedbackQueryResult.isSuccess()) {

			if (feedbackQueryResult.getModule().getList().size() == 0) {

				if (StringExUtils.isNullOrEmpty(feedback.getContent())) {
					errList.add("请输入评论内容");
				} else {
					Long orderId = feedback.getOrderId();

					feedback.setUserId(users.getId());
					feedback.setStatus(EnumCommentStatus.UNAUDITED.getCode());
					feedback.setFeedbackTime(new Date());
					feedback.setOrderId(feedback.getOrderDetailId());
					if (null == errList || 0 == errList.size()) {
						ResultDTO<Boolean> result = ryxUserService.saveFeedback(feedback);
						errList = addList(errList, result.getErrorMsg());
						if (result.isSuccess()) {

							ResultDTO<Boolean> updateOrderDetailResult = ryxOrderService.updateOrderDetailIfFeedback(feedback.getOrderDetailId());
							errList = addList(errList, updateOrderDetailResult.getErrorMsg());

							RyxOrderDetailQuery orderDetailQuery = new RyxOrderDetailQuery();
							orderDetailQuery.setOrderId(orderId);
							orderDetailQuery.setIsFeedback(0);

							ResultDTO<Integer> countResult = ryxOrderService.countQueryOrderDetail(orderDetailQuery);
							errList = addList(errList, countResult.getErrorMsg());
							if (countResult.isSuccess() && countResult.getModule() == 0) {
								ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderIfFeedback(orderId);
								errList = addList(errList, updateOrderResult.getErrorMsg());
							}
						}
					}
				}
			} else {
				errList.add("您已评论，请勿重复评论");
			}
		}

		if (null == errList || 0 == errList.size()) {
			writeAjax(response, true);
		} else {
			writeAjax(response, false, StringHelper.list2HtmlString(errList));
		}

	}

	@RequestMapping("/my/do_ajax_add_course_favorite.html")
	public void doAjaxAddCourseFavorite(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxUserFollowCourseDTO userFollowCourseDTO)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		userFollowCourseDTO.setUserId(users.getId());

		ResultDTO<Boolean> result = ryxCourseService.saveUserFollowCourse(userFollowCourseDTO);

		if (result.isSuccess()) {
			writeAjax(response, true);
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}

	}

	@RequestMapping("/my/ajax_cancel_follow_course.html")
	public void doAjaxCancelCourseFavorite(HttpServletRequest request, HttpServletResponse response, Long courseId, RedirectAttributes rt,
			RyxUserFollowCourseDTO userFollowCourseDTO) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ResultDTO<Boolean> result = ryxCourseService.deleteUserFollowCourseByCourseIdAndUserId(courseId, users.getId());

		if (result.isSuccess()) {
			writeAjax(response, true);
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}

	@RequestMapping("/my/ajax_cancel_follow_teacher.html")
	public void doAjaxCancelTeacherFavorite(HttpServletRequest request, HttpServletResponse response, Long teacherId, RedirectAttributes rt,
			RyxUserFollowTeacherDTO userFollowTeacherDTO) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ResultDTO<Boolean> result = ryxTeacherService.deleteUserFollowTeacherByTeacherIdAndUserId(teacherId, users.getId());

		if (result.isSuccess()) {
			writeAjax(response, true);
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}

	}

	@RequestMapping("/my/do_ajax_add_teacher_favorite.html")
	public void doAjaxAddTeacherFavorite(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt,
			RyxUserFollowTeacherDTO userFollowTeacherDTO) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		userFollowTeacherDTO.setUserId(users.getId());

		ResultDTO<Boolean> result = ryxTeacherService.saveUserFollowTeacher(userFollowTeacherDTO);

		if (result.isSuccess()) {
			writeAjax(response, true);
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}

	}

	@ModelAttribute
	private void getSearchKeyword() {

	}

	private JSONObject getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}

	@RequestMapping("/my/king_upload.html")
	public void kingEditorUploadFile(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			writeAjax(response, getError("请选择文件。"));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			writeAjax(response, getError("目录名不正确。"));
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName().toLowerCase();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				// 检查文件大小
				if (item.getSize() > maxSize) {
					writeAjax(response, getError("上传文件大小超过限制。"));
					return;
				}
				// 检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
					writeAjax(response, getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
					return;
				}

				byte[] bytes = item.get();

				// 保存后的文件名
				String saveName = UUID.randomUUID().toString() + "."+fileExt;

				ResultDTO<String> uploadResult = QiniuHelper.uploadBytes(bytes, saveName);
				if (uploadResult.isSuccess()) {
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("url", uploadResult.getModule());
					writeAjax(response, obj);
				} else {
					writeAjax(response, getError(uploadResult.getErrorMsg()));
				}
			}
		}
	}

	@RequestMapping("/get_image_verify_code.html")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response, Integer w, Integer h,Integer l) throws IOException {
		
		String referer = request.getHeader("Referer");
		
		if(StringHelper.isNullOrEmpty(referer)){
			writeAjax(response, false, "操作过于频繁，请60分钟之后再试");
			return ;
		}
		
		l = (null == l || 0 == l) ? 4:l;
		String code = VerfiyCodeHelpler.generateVerifyCode(l);
		SessionHelper.set(SessionHelper.IMG_VERIFY_CODE_COOKIE, code, request, response);
		System.out.println("test --- >"  + SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase());
		CookieHelper.addCookie(response, SessionHelper.IMG_VERIFY_CODE_COOKIE, code, 100000, "/");
		VerfiyCodeHelpler.outputImage(w, h, response.getOutputStream(), code);
	}
	
	
	@RequestMapping("/ajax_createorupdate_search.html")
	public void ajaxCreateOrUpdateSearch(HttpServletRequest request, RyxSearchDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			
			dto.setTcreate(DateHelper.long2Date(System.currentTimeMillis()/1000));
			RyxUsersDTO users = getRyxUser();
			if (users != null) {
				dto.setUid(users.getUid());
			}
			
			/*SearchDTO searchDTO = new SearchDTO();
			searchDTO.setContent(dto.getContent());
			
					
			ResultDTO<Boolean> b = userService.createSearch(searchDTO);
			
			ResultDTO<Boolean> resultDTO = userService.createOrUpdateSearchStatistics(dto);*/
			
			ResultDTO<Boolean> resultDTO = userService.createSearchAndSearchstatistics(dto);
			
			if (resultDTO.isSuccess()) {
				writeAjax(response, true, "");
			} else {
				writeAjax(response, false, resultDTO.getErrorMsg());
			}

			

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}
	
	@RequestMapping("/my/ajax_do_create_follow_teacher.html")
	public void ajaxDoCreateMyFollowTeacher(HttpServletRequest request, RyxUserFollowTeacherDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		try {
			dto.setUserId(users.getId());
			ResultDTO<Boolean> result = ryxTeacherService.saveUserFollowTeacher(dto);
			if (result.isSuccess()) {
				writeAjax(response, true, "关注成功", result);
			} else {
				writeAjax(response, false, result.getErrorMsg());
			}
			
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}
	
	
	
	
}
