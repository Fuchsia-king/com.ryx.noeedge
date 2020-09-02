package com.king.nowedge.m.controller;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.StringExUtils;
import com.king.nowedge.wxpay.WeixinShareDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Controller
public class MindexController extends BaseController {

	private static final Log logger = LogFactory.getLog(MindexController.class);

//	@Autowired
//	@Qualifier("org.springframework.security.authenticationManager")
//	protected AuthenticationManager authenticationManager;

	
	@RequestMapping("/mryx/my/test1.html")
	public ModelAndView test1(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		Long userId = 282L;
		RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
		userCouponDTO.setCoupon(20.00);//
		userCouponDTO.setUserId(userId);
		userCouponDTO.setRemark("新用户注册");
		userCouponDTO.setCreaterId(userId);
		ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
		addList(errList, result.getErrorMsg());
		
		ModelAndView mav = new ModelAndView("/ryx/m/test");
		return mav;
		
	}
	
	@RequestMapping("/mryx/test2.html")
	public void test2(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws IOException {

		ResultDTO<String> string = HttpHelper.get("http://m.ryx365.com/mryx/course_519.htm"); 
		response.getWriter().write(string.getModule());
		
	}
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/m/mindex");		
		mav.addObject("loginUsers", users);		
		String serverName = request.getServerName().toLowerCase();
		Boolean isWeixinExplorer = isWeixinExplorer(request);		
		if(isWeixinExplorer){
			setCourseWeixinSpread1("融易学金融学院首页","融易学金融学院首页",mav,request);
		}
		if(StringHelper.isNullOrEmpty(serverName) || serverName.indexOf("m.")<0){
			return new ModelAndView("redirect:/default.html");
		}
		else{
			return mav;
		}

	}

	
	
	
	@RequestMapping("/mryx/more.html")
	public ModelAndView more(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mmore");
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		
		ResultDTO<List<RyxAdDTO>> moreAdResult = MetaHelper.getInstance().queryAdCache(146);
		errList = addList(errList, moreAdResult.getErrorMsg());
		mav.addObject("moreAd", moreAdResult.getModule().subList(0, 2));
		
		return mav;

	}
	
	
	@RequestMapping("/mryx/teacher_detail.html")
	public ModelAndView teacherDetail(@RequestParam(value = "id") Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mteacherDetail");
		RyxUsersDTO users = getRyxUser();
		
		ResultDTO<RyxTeacherDTO> result =MetaHelper.getInstance().getTeacherById(id);
		errList = addList(errList, result.getErrorMsg());
		
		
	
		RyxTeacherDTO teacherDTO = result.getModule();
		mav.addObject("teacher", teacherDTO);		
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/teacher_detail1.html")
	public ModelAndView teacherDetail1(@RequestParam(value = "id") Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		RyxUsersDTO users = getRyxUser();

		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mteacherDetail1");		
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/mryx/teacher_detail1.html?id="+id+"&u="+u);
				return mav;
			}
		}
			
		ResultDTO<RyxTeacherDTO> result =MetaHelper.getInstance().getTeacherById(id);
		errList = addList(errList, result.getErrorMsg());
		
		RyxCourseQuery courseQuery=new RyxCourseQuery();
		courseQuery.setTid(id);
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setFlag(0);
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		
		
		RyxTeacherDTO teacher = result.getModule();
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		
		if(isWeixinExplorer){
		
			WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
			weixinShareDTO.setShareTitle("融易学_" + teacher.getNickname() +"_" + teacher.getBeGoodAt());
			weixinShareDTO.setShareImageUrl(teacher.getImageUrl());
			String contentString = HtmlHelper.unescapeHtmlAndDeleteHtmlTag(teacher.getIntroduction());
			contentString = contentString.length()>22 ? contentString.substring(22) : contentString;
			weixinShareDTO.setShareDesc( contentString );
			if(StringHelper.isNullOrEmpty(uString)){
				weixinShareDTO.setShareLink("https://m.ryx365.com/mryx/teacher_"+id+".htm");
			}
			mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
			
		}
		

		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("title", MetaHelper.getInstance().getCategoryByTeacherId(id).getModule().getTitle() + "_"
				+ teacher.getNickname() + "_" 
				+ teacher.getBeGoodAt());
		mav.addObject("query", courseQuery);
		mav.addObject("teacher", teacher);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		mav.addObject("isUcExplorer", isUcExplorer(request));
		mav.addObject("discountIntervalArray", JSONArray.fromObject(discountIntervalArray).toString()) ;
		
		addPasswordModel(mav,request,getCurrentUrl(request));
	
		
		return mav;
		
	}
	
	

	
	@RequestMapping("/mryx/news_detail.html")
	public ModelAndView newsDetail(@RequestParam(value = "id") Long id, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mnewsDetail");
		RyxUsersDTO users = getRyxUser();
		
		ResultDTO<RyxNewsDTO> result =MetaHelper.getInstance().getNewsById(id);
		errList = addList(errList, result.getErrorMsg());
		
		mav.addObject("news", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/news_detail_by_title.html")
	public ModelAndView newsDetailByTitle(@RequestParam(value = "title") String title, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mnewsDetail");
		RyxUsersDTO users = getRyxUser();
		
		ResultDTO<RyxNewsDTO> result = ryxNewsService.getNewsByTitle(title);
		errList = addList(errList, result.getErrorMsg());
		
		mav.addObject("news", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/course_detail.html")
	public ModelAndView courseDetail(@RequestParam(value = "id") Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mcourseDetail");
		
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/mryx/course_detail.html?id="+id+"&u="+u);
				return mav;
			}
		}
		
		
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		
		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {  // course is not null
			
			if(EnumCourseType.OFFLINE_COURSE.getCode() == course.getFlag()){
				return new ModelAndView("redirect:/mryx/offline_course_"+ id +".htm");
			}
			else if(EnumCourseType.VIDEO_COURSE.getCode() == course.getFlag()){
				return new ModelAndView("redirect:/mryx/video_"+ id +".htm");
			}
			
			if(null != users){
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
				if(rlistResult.isSuccess() && null!=courseList && courseList.size()>0){
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
			RyxFeedbackQuery feedbackQuery =new RyxFeedbackQuery();
			feedbackQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			feedbackQuery.setCourseId(id);
			ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(feedbackQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("comment", result.getModule().getList());
			mav.addObject("course", course);
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
	
			Boolean isWeixinExplorer = isWeixinExplorer(request);
			if(isWeixinExplorer){
				WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
				weixinShareDTO.setShareTitle("融易学_" + course.getTitle());
				weixinShareDTO.setShareImageUrl(course.getImageUrl());
				String contentString = HtmlHelper.unescapeHtmlAndDeleteHtmlTag(course.getContent());
				contentString = contentString.length()>22 ? contentString.substring(22) : contentString;
				weixinShareDTO.setShareDesc( contentString );
				if(StringHelper.isNullOrEmpty(uString)){
					weixinShareDTO.setShareLink("https://m.ryx365.com/mryx/course_"+id+".htm");
				}
			
				mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
			}
			
	
			mav.addObject("isWeixinExplorer", isWeixinExplorer);mav.addObject("isUcExplorer", isUcExplorer(request));
			ResultDTO<RyxTeacherDTO> teacherResult =MetaHelper.getInstance().getTeacherById(course.getTid());
			errList = addList(errList, teacherResult.getErrorMsg());
			mav.addObject("teacher", teacherResult.getModule());
			String msg = request.getParameter("msg");
			
			mav.addObject("msg", msg);
	
			mav.addObject("errList", errList);
	
			mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
			addPasswordModel(mav,request,getCurrentUrl(request));
			
			
			/**
			 * 获取课程相关的问题 。
			 */
			RyxQuestionQuery questionQuery = new RyxQuestionQuery();
			questionQuery.setPageSize(Integer.MAX_VALUE);
			questionQuery.setCid(id);
			ResultDTO<RyxQuestionQuery> questionResult = ryxUserService.queryQuestion(questionQuery);
			errList = addList(errList, questionResult.getErrorMsg());
			mav.addObject("questionQuery", questionResult.getModule());
		}			
			
			
			
		
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/video_detail.html")
	public ModelAndView videoDetail(@RequestParam(value = "id") Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();		

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mvideoDetail");
		
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/mryx/video_detail.html?id="+id+"&u="+u);
				return mav;
			}
		}		

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		
		

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {  // course is not null 
			if(users!=null){
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), id, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);



				/**
				 * 创建环信用户
				 */
				String husername = "ryx_" + users.getId();
				String nickname = users.getUsername();

				mav.addObject("husername",husername);
				mav.addObject("hpassword",getHuanxinPassword(users.getId()));
				
				/**
				 * get chatroom id by course id
				 */
				String groupId="";
				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setKey1(id.toString());
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COURSE_CHAT_GROUP.getCode());
				ResultDTO<List<KeyvalueDTO>> keyvalueResultDTO = systemService.queryKeyvalue(keyvalueQuery);
				errList = addList(errList, keyvalueResultDTO.getErrorMsg());
				List<KeyvalueDTO> keyvalueDTOs = keyvalueResultDTO.getModule();
				if(null!=keyvalueDTOs && keyvalueDTOs.size() == 1){
					groupId = keyvalueDTOs.get(0).getValue();
				}
				
				if(!StringHelper.isNullOrEmpty(groupId)){
					mav.addObject("hgroupId", groupId);
				}
				else{

					RyxTeacherDTO teacher =  MetaHelper.getInstance().getTeacherById(course.getTid()).getModule();
					String hteacherUserId = "ryx_" + teacher.getUid();
					String hteacherUsername = MetaHelper.getInstance().getUserById(teacher.getUid()).getModule().getUsername();
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
				ResultDTO<List<RyxCourseDTO>> rlistResult ;
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
				if(rlistResult.isSuccess() && null!=courseList && courseList.size()>0){
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
			RyxFeedbackQuery feedbackQuery =new RyxFeedbackQuery();
			feedbackQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			feedbackQuery.setCourseId(id);
			ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(feedbackQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("comment", result.getModule().getList());
			mav.addObject("course", course);
			
	
			Boolean isWeixinExplorer = isWeixinExplorer(request);
			if(isWeixinExplorer){
				WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
				weixinShareDTO.setShareTitle("融易学_ " + course.getTitle());
				weixinShareDTO.setShareImageUrl(course.getImageUrl());
				String contentString = HtmlHelper.unescapeHtmlAndDeleteHtmlTag(course.getContent());
				contentString = contentString.length()>22 ? contentString.substring(22) : contentString;
				weixinShareDTO.setShareDesc( contentString );
				if(StringHelper.isNullOrEmpty(uString)){
					weixinShareDTO.setShareLink("https://m.ryx365.com/mryx/video_"+id+".htm");
				}
			
				mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
			}
			
	
			mav.addObject("isWeixinExplorer", isWeixinExplorer);mav.addObject("isUcExplorer", isUcExplorer(request));
			
			
			String msg = request.getParameter("msg");
			
			mav.addObject("msg", msg);
	
			mav.addObject("errList", errList);
	
			mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
			
			
			
			
			/**
			 * 获取课程相关的问题 。
			 */
			RyxQuestionQuery questionQuery = new RyxQuestionQuery();
			questionQuery.setPageSize(Integer.MAX_VALUE);
			questionQuery.setCid(id);
			ResultDTO<RyxQuestionQuery> questionResult = ryxUserService.queryQuestion(questionQuery);
			errList = addList(errList, questionResult.getErrorMsg());
			mav.addObject("questionQuery", questionResult.getModule());
			
			ResultDTO<RyxTeacherDTO> teacherResult =MetaHelper.getInstance().getTeacherById(course.getTid());
			errList = addList(errList, teacherResult.getErrorMsg());
			mav.addObject("teacher", teacherResult.getModule());
			
			addPasswordModel(mav,request,getCurrentUrl(request));
			
			
			
			
			/**
			 * 往期回顾
			 */
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setPageSize(Integer.MAX_VALUE);
			courseQuery.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
			courseQuery.setTtstart(null);
			courseQuery.setEtstart(DateHelper.getTodayLong());
			ResultDTO<RyxCourseQuery>  previousLivingResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
			errList = addList(errList, courseResult.getErrorMsg());
			mav.addObject("previousLiving", previousLivingResult.getModule().getList());			
		}
		
		mav.addObject("errList", errList);
		
		return mav;

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

	
	
	
	
	@RequestMapping("/mryx/shopping_card_detail.html")
	public ModelAndView shoppingCardDetail(@RequestParam(value = "id") Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mshoppingCardDetail");	
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/mryx/shopping_card_detail.html?id="+id+"&u="+u);
				return mav;
			}
		}		
		
		errList = new ArrayList<String>();		

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {  // course is not null 
			
			if(null != users){
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
				if(rlistResult.isSuccess() && null!=courseList && courseList.size()>0){
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
			RyxFeedbackQuery feedbackQuery =new RyxFeedbackQuery();
			feedbackQuery.setStatus(EnumCommentStatus.APPROVED.getCode());
			feedbackQuery.setCourseId(id);
			ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(feedbackQuery);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("comment", result.getModule().getList());
			mav.addObject("course", course);
			
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
			Boolean isWeixinExplorer = isWeixinExplorer(request);
			
			if(isWeixinExplorer){				
				
				WeixinShareDTO weixinShareDTO = new WeixinShareDTO();
				weixinShareDTO.setShareTitle("融易学_" + course.getTitle());
				weixinShareDTO.setShareImageUrl(course.getImageUrl());
				
				String contentString = HtmlHelper.unescapeHtmlAndDeleteHtmlTag(course.getContent());
				contentString = contentString.length()>22 ? contentString.substring(22) : contentString;
				weixinShareDTO.setShareDesc( contentString);
				if(StringHelper.isNullOrEmpty(uString)){
					weixinShareDTO.setShareLink("https://m.ryx365.com/mryx/shopping_card_"+id+".htm");
				}
				
				mav  =  getWeixinShareParams(mav, request,weixinShareDTO);
				
			}
			
			mav.addObject("isWeixinExplorer", isWeixinExplorer);
			mav.addObject("isUcExplorer", isUcExplorer(request));
			
			String msg = request.getParameter("msg");
			
			mav.addObject("msg", msg);
	
			mav.addObject("errList", errList);
	
			mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
			addPasswordModel(mav,request,getCurrentUrl(request));
			
		}		

		return mav;
	}


	@RequestMapping("/mryx/offline_course.html")
	public ModelAndView offlineCourse(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	@RequestMapping("/mryx/teacher.html")
	public ModelAndView teacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	@RequestMapping("/mryx/register_teacher.html")
	public ModelAndView registerTeacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	@RequestMapping("/mryx/do_register_teacher.html")
	public ModelAndView doRegisterTeacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/offlineCourse");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		

		mav.addObject("course", courseResult.getModule().getList());
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	@RequestMapping("/m/login.html")
	public ModelAndView mlogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		String serverName = request.getServerName().toLowerCase();
		if(StringHelper.isNullOrEmpty(serverName) || serverName.indexOf("m.ryx365.com")<0){
			return new ModelAndView("redirect:/login.html");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlogin");
		mav.addObject("targetUrl", request.getParameter("targetUrl")) ;
		return mav;
	}
	

	@RequestMapping("/login.html")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		
		HttpSession session = request.getSession();

		errList = (ArrayList<String>) session.getAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
		session.removeAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
		if(null==errList){
			errList = new ArrayList<String>();
		}
		
		ModelAndView mav = new ModelAndView("/ryx/pc/clogin");
		String serverName = request.getServerName().toLowerCase();
	
		/**
		 * 手机端
		 */
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/mlogin");
		}
		
		if(errList.size()==0 && !StringHelper.isNullOrEmpty(request.getParameter("error"))){
			errList.add("登录超时");
		}
		mav.addObject("errList", errList);
		addPasswordModel(mav,request,null);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		
		String targetUrl = request.getParameter("targetUrl") ;
		
		mav.addObject("targetUrl",targetUrl);
		return mav;

	}
	

	
	
	@RequestMapping("/crm/login.html")
	public ModelAndView loginCrm(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		
		HttpSession session = request.getSession();

		errList = (ArrayList<String>) session.getAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
		session.removeAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
		if(null==errList){
			errList = new ArrayList<String>();
		}
		
		ModelAndView mav = new ModelAndView("/ryx/pc/cloginCrm");
	
	
		if(errList.size()==0 && !StringHelper.isNullOrEmpty(request.getParameter("error"))){
			errList.add("登录超时");
		}
		mav.addObject("errList", errList);
		addPasswordModel(mav,request,null);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		
		
		return mav;

	}


	
	
	@RequestMapping("/m/register.html")
	public ModelAndView register(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		ModelAndView mav = new ModelAndView("/ryx/m/mregister");	
		mav.addObject("isGet", true);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("targetUrl", request.getParameter("targetUrl"));
		return mav;

	}
	
	
	@RequestMapping("/mryx/register_subuser.html")
	public ModelAndView registerSubMryx(String w, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		
		ModelAndView mav = new ModelAndView("/ryx/m/mregisterSubuser");
		mav.addObject("isGet", true);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		return mav;

	}

	@RequestMapping("/m/reset_password.html")
	public ModelAndView resetPasswrodMryx(String w, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/m/mresetPassword");
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		return mav;

	}
	

	
	@RequestMapping("/mryx/my/create_subacunt.html")
	public ModelAndView createSubacunt(String w, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mcreateSubacunt");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/do_create_subacunt.html")
	public ModelAndView doCreateSubacunt(@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mcreateSubacunt");
		
		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
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
				errList.add("该用户名已经注册，请直接登录");
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
				user.setMid(loginUsersDTO.getId());

				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {

					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {

						
						errList.add("注册成功，请<a href=\"/mryx/login.html\">登录</a>");

					} else {
						errList.add("无效的用户Id");
					}
				} else {

				}
			}
		} else {

		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	
	@RequestMapping("/m/do_register.html")
	public ModelAndView doRegisterMryx(
			@Valid @ModelAttribute("registerDTO") RyxRegister2DTO registerDTO, 
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mregister");
		String serverName = request.getServerName().toLowerCase();
		if(StringHelper.isNullOrEmpty(serverName) || serverName.indexOf("m.ryx365.com")<0){
			mav = new ModelAndView("/ryx/pc/cregister");
		}

		mav.addObject("targetUrl", request.getParameter("targetUrl")) ;

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), 
					registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
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
				errList.add("该用户名已经注册，请直接登录");
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
				user.setUsername("ryx"+registerDTO.getMobile());
				user.setFlag(1);
				user.setRfrom(EnumRegFrom.H5.getCode());
				user.setSid(RequestHelper.getCommonPartnerId(request,null));
				
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
						userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"元代金券");
						userCouponDTO.setCreaterId(userId);
						userCouponDTO.setType(EnumAccountType.COUPON.getCode());
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
						addList(errList, result.getErrorMsg());
						
						
						
						
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
								result = ryxUserService.addUserCoupon(userCouponDTO);
								addList(errList, result.getErrorMsg());
							}
						}
						
						
						mav.addObject("registerResult", "1");
						
						

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
	
	

	@RequestMapping("/mryx/do_register_sub_user.html")
	public ModelAndView doRegisterSubuserMryx(@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO, 
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mregisterSubuser");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
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
						//mav = new ModelAndView("redirect:/index.html");

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

	@RequestMapping("/m/do_reset_password.html")
	public ModelAndView doResetPasswordMryx(@Valid @ModelAttribute("forgetPasswordDTO") RyxForgetPasswordDTO1 forgetPasswordDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mresetPassword");

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(forgetPasswordDTO.getMobile(), forgetPasswordDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
			}

			if (null == errList || errList.size() == 0) {

				ResultDTO<Boolean> updatepasswordResult = ryxUserService.updatePasswordByMobile(Md5Util.GetMD5Code(forgetPasswordDTO.getPassword()),
						forgetPasswordDTO.getMobile());
				errList = addList(errList, updatepasswordResult.getErrorMsg());
				if(updatepasswordResult.isSuccess()){
					mav.addObject("registerResult", "1");
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
	
	
	
	
	

	@RequestMapping(value = "/m/send_register_verify_code.html", method = RequestMethod.POST)
	public void sendRegisterVerifyCode(@RequestParam(value = "mobile") String mobile, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			
			String referer = request.getHeader("Referer");
			if(StringHelper.isNullOrEmpty(referer)){
				writeAjax(response, false, "操作过于频繁，请60分钟之后再试");
				return ;
			}
			
			
			if(StringHelper.checkSendMobileSecond(30,request)){
				writeAjax(response, false, "操作过于频繁，请30分钟之后再试");
				return ;
			}
			
			if(StringHelper.checkSendMobileTimes(request)){
				writeAjax(response, false, "已超出短信获取限制，请联系客服");
				return ;
			}
			
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else {				
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);				
				if(userResult.isSuccess()){
					
					RyxUsersDTO users = userResult.getModule();
				
					if(null != users){
						writeAjax(response, false, "该手机号已经存在，请<a href=\"/login.html\">直接登录</a>");
					}
					else{
						String text = "【融易学】欢迎您注册融易学；您的验证码是：{random}，更多惊喜请登录融易学官网，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);				
						if (result.isSuccess()) {
							writeAjax(response, true,"",result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(),result.getModule());
						}
					}
				}
				else{
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	
	
	@RequestMapping(value = "/mryx/send_common_verify_code.html", method = RequestMethod.POST)
	public void sendCommonVerifyCode(@RequestParam(value = "mobile") String mobile, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else {				
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);				
				if(userResult.isSuccess()){
					
					RyxUsersDTO users = userResult.getModule();
				
					if(null != users){
						writeAjax(response, false, "该手机号已经存在，请<a href=\"/mryx/login.html\">直接登录</a>");
					}
					else{
						String text = "【融易学】您的验证码是：{random}，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, text);				
						if (result.isSuccess()) {
							writeAjax(response, true,"",result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(),result.getModule());
						}
					}
				}
				else{
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	
	
	
	@RequestMapping(value = "/mryx/my/send_change_password_code.html", method = RequestMethod.POST)
	public void sendChangePasswordCode(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO user = getRyxUser();
			String mobile = user.getMobile();
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "您的手机号码为空，请完善您的信息");			
			} else {
				String text = "【融易学】您修改密码的验证码是：{random}，如非本人发起请忽略。";
				ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile,text);
				if (result.isSuccess()) {
					writeAjax(response, true,"",result.getModule());
				} else {
					writeAjax(response, false, result.getErrorMsg(),result.getModule());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}
	
	

	@RequestMapping(value = "/m/send_reset_password_verify_code.html", method = RequestMethod.POST)
	public void sendResetPasswordVerifyCode(@RequestParam(value = "mobile") String mobile,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			if (StringExUtils.isNullOrEmpty(mobile)) {
				writeAjax(response, false, "请输入手机号码");
			} else if (!StringExUtils.isMobile(mobile)) {
				writeAjax(response, false, "请输入正确的手机号码");
			} else {

				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserByMobile(mobile);				
				if(userResult.isSuccess()){
					
					RyxUsersDTO users = userResult.getModule();
				
					if(null == users){
						writeAjax(response, false, "该手机号不存在，请<a href=\"/mryx/register.html\">免费注册</a>");
					}
					else{
						String text = "【融易学】您重置密码的验证码是：{random}，如非本人发起请忽略。";
						ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile,text);
		
						if (result.isSuccess()) {
							writeAjax(response, true,"",result.getModule());
						} else {
							writeAjax(response, false, result.getErrorMsg(),null);
						}
					}
				}
				else{
					writeAjax(response, false, userResult.getErrorMsg());
				}
			}
		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping("/m/my/profile.html")
	public ModelAndView myProfile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mprofile");
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("users", userResult.getModule());
		mav.addObject("errList", errList);

		return mav;

	}
	
	

	
	
	
	
	

	@RequestMapping("/m/my/do_update_profile.html")
	public ModelAndView doUpdateProfile(HttpServletRequest request, @Valid @ModelAttribute("users") 
		RyxUpdateUserProfileDTO updateUserProfileDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/mprofile");

		errList = new ArrayList<String>();

		RyxUsersDTO loginUsersDTO = getRyxUser();
		mav.addObject("loginUsers", loginUsersDTO);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		updateUserProfileDTO.setId(loginUsersDTO.getId());
		mav.addObject("createBindingResult", bindingResult);

		if (!bindingResult.hasErrors()) {
			
			RyxUsersDTO users = new RyxUsersDTO();
			BeanUtils.copyProperties( updateUserProfileDTO,users);
			ResultDTO<Boolean> result = ryxUserService.updateUserById(users);
			errList = addList(errList, result.getErrorMsg());
		}

		mav.addObject("errList", errList);
		mav.addObject("isPost", true);

		return mav;

	}

	@RequestMapping("/mryx/my/course.html")
	public ModelAndView myCourse(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mcourse");

		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

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

	@RequestMapping("/m/my/follow_teacher.html")
	public ModelAndView myFollowTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mfollowTeacher");

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		teacherQuery.setUserId(users.getId());
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxTeacherQuery> followTeacherResult = ryxTeacherService.getMyFollowTeacher(teacherQuery);
		errList = addList(errList, followTeacherResult.getErrorMsg());

		mav.addObject("query", followTeacherResult.getModule());
		mav.addObject("errList", errList);

		return mav;

	}
	//fjy ajax 收藏教师
	@RequestMapping("/m/my/ajax_follow_teacher.html")
	public void myAjaxFollowTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();


		teacherQuery.setUserId(users.getId());
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		
		writeAjax(response, true,MetaHelper.getInstance().getSmallTeacher(teacherQuery));
	}

	
	
	
	
	
	
	
	@RequestMapping("/mryx/my/ajax_my_follow_course.html")
	public void ajaxMyFollowCourse(HttpServletRequest request, 
			RyxCourseQuery courseQuery ,HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();


		String price = courseQuery.getPrice();

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

			courseQuery.setUserId(users.getId());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
			if(followCourseResult.isSuccess()){
				writeAjax(response, true,"",followCourseResult.getModule().getList());
			}
			else{
				writeAjax(response, false,followCourseResult.getErrorMsg());
			}

	
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}


	}
	
	
	@RequestMapping("/mryx/my/ajax_add_answer_agree.html")
	public void ajaxAddAnswerAgree(HttpServletRequest request, 
			Long id ,HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		try {

			ResultDTO<Boolean> followCourseResult = ryxUserService.addAnswerAgree(id);
			if(followCourseResult.isSuccess()){
				writeAjax(response, true,"");
			}
			else{
				writeAjax(response, false,followCourseResult.getErrorMsg());
			}
	
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/my/ajax_add_answer_disagree.html")
	public void ajaxAddAnswerDisagree(HttpServletRequest request, 
			Long id ,HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {
		
		try {

			ResultDTO<Boolean> followCourseResult = ryxUserService.addAnswerDisagree(id);
			if(followCourseResult.isSuccess()){
				writeAjax(response, true,"");
			}
			else{
				writeAjax(response, false,followCourseResult.getErrorMsg());
			}
	
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	
	@RequestMapping("/mryx/my/ajax_my_follow_teacher.html")
	public void ajaxMyFollowTeacher(HttpServletRequest request, 
			RyxTeacherQuery teacherQuery ,HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();


		try {


			teacherQuery.setUserId(users.getId());
			teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
			ResultDTO<RyxTeacherQuery> followTeacherResult = ryxTeacherService.getMyFollowTeacher(teacherQuery);
			if(followTeacherResult.isSuccess()){
				writeAjax(response, true,"",followTeacherResult.getModule().getList());
			}
			else{
				writeAjax(response, false,followTeacherResult.getErrorMsg());
			}

	
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}


	}

	@RequestMapping("/m/myindex.html")
	public ModelAndView myIndex(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/mindex");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		addPasswordModel(mav, request, getCurrentUrl(request));
		return mav;

	}

	@RequestMapping("/mryx/list_online_course")
	public ModelAndView listOnlineCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistOnlineCourse");

		String price = courseQuery.getPrice();

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

		} catch (Throwable t) {

		}

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setOrderBy("c.sort");
		courseQuery.setSooort("desc");
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		

		courseQuery = courseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("course", courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("exploreType", getExploreType(request));

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/list_video")
	public ModelAndView listVideoCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistVideo");

		

		
		/**
		 * 今日直播
		 */
		
		
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().getTodayLiving();
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("todayLiving", courseResult.getModule().getList());
		
		
		/**
		 * 近期直播
		 */
		
		courseResult = MetaHelper.getInstance().getRecentLiving();
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("recentLiving", courseResult.getModule().getList());
		
		/**
		 * 往期回顾
		 */
		
		
		courseResult = MetaHelper.getInstance().getPreviousLiving();
		errList = addList(errList, courseResult.getErrorMsg());
		mav.addObject("previousLiving", courseResult.getModule().getList());

		

		courseQuery = courseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("exploreType", getExploreType(request));
		ResultDTO<List<RyxAdDTO>> moreAdResult = MetaHelper.getInstance().queryAdCache(146);
		mav.addObject("moreAd", moreAdResult.getModule().subList(0, 2));

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/my/unexpired_course")
	public ModelAndView myUnexpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/munexpiredCourse");

		String price = courseQuery.getPrice();
		

		try {
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

		} catch (Throwable t) {

		}

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(6);
		courseQuery.setFlag(0);
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		courseQuery.setOrderBy("sort");;
		courseQuery.setSooort("asc");
		
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		
		

		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/my/ajax_unexpired_course")
	public void ajaxMyUnexpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		String price = courseQuery.getPrice();
		

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}		

			courseQuery.setUserId(users.getId());
			courseQuery.setPageSize(6);
			courseQuery.setFlag(0);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			
			ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse(courseQuery);
			
			
			if(courseResult.isSuccess()){
				writeAjax(response, true,"", courseResult.getModule().getList());
			}
			else{
				writeAjax(response, false,courseResult.getErrorMsg());
			}

		
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	
	@RequestMapping("/mryx/my/expired_course")
	public ModelAndView myExpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mexpiredCourse");

		String price = courseQuery.getPrice();

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

		} catch (Throwable t) {

		}

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setFlag(0);
		courseQuery.setUserId(users.getId());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		
		ResultDTO<RyxCourseQuery> courseResult =  ryxCourseService.getMyExpiredCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		

		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/my/ajax_expired_course.html")
	public void ajaxMyExpiredCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		String price = courseQuery.getPrice();

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}			


			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setFlag(0);
			courseQuery.setUserId(users.getId());
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			
			ResultDTO<RyxCourseQuery> courseResult =  ryxCourseService.getMyExpiredCourse(courseQuery);
			if(courseResult.isSuccess()){
				writeAjax(response, true,"",courseResult.getModule().getList());
			}
			else{
				writeAjax(response, false,courseResult.getErrorMsg());
			}
			
		}
		catch(Throwable t){
			writeAjax(response, false,t.getMessage());
		}


	}
	
	
	
	
	
	@RequestMapping("/mryx/ajax_list_course_first.html")
	public void ajaxListCourseFirst(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {


		try {
			

			String price = courseQuery.getPrice();
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
			
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			
			ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
			if(courseResult.isSuccess()){
				writeAjax(response, true,"",courseResult.getModule());
			}
			else{
				writeAjax(response, false,courseResult.getErrorMsg());
			}
			

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	
	
	@RequestMapping("/mryx/my/ajax_my_follow_course_first.html")
	public void ajaxMyFollowCourseFirst(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {


		try {
			

			String price = courseQuery.getPrice();
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
			
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			
			ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().getMyFellowCourse(courseQuery);
			if(courseResult.isSuccess()){
				writeAjax(response, true,"",courseResult.getModule());
			}
			else{
				writeAjax(response, false,courseResult.getErrorMsg());
			}
			

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	@RequestMapping("/m/ajax_list_online.html")
	public void ajaxListOnlineCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		try {
			
			String price = courseQuery.getPrice();
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			writeAjax(response, true,"",MetaHelper.getInstance().queryMobileOnline(courseQuery));
					

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/m/ajax_list_offline.html")
	public void ajaxListOfflineCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		try {
			
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			if(null == courseQuery.getCurrentPage() || 1 == courseQuery.getCurrentPage()){
				RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
				BeanUtils.copyProperties(courseQuery,ryxCourseQuery ,BeanHelper.getNullPropertyNames(courseQuery));

				ryxCourseQuery.setPageSize(Integer.MAX_VALUE);
				list = CourseHelper.getInstance().queryUnendOffline(ryxCourseQuery);
			}
			
			String price = courseQuery.getPrice();
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			courseQuery.setEtstart(System.currentTimeMillis()/1000);
			
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");			

			list.addAll(MetaHelper.getInstance().queryMobileOffline(courseQuery));
			
			writeAjax(response, true,"",list);
					

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/m/ajax_list_info.html")
	public void ajaxListInfoCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		try {
			
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setObjType(EnumObjectType.INFO_MODULE.getCode());
			writeAjax(response, true,"",MetaHelper.getInstance().queryMobileInfo(courseQuery));
					

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/m/ajax_list_book.html")
	public void ajaxListBook(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		try {
			
			courseQuery.setIdeleted(0);
			courseQuery.setDisplay(1);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setObjType(EnumObjectType.BOOK_MODULE.getCode());
			writeAjax(response, true,"",MetaHelper.getInstance().queryMobileBook(courseQuery));
					

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/m/ajax_list_video.html")
	public void ajaxListVideoCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		try {
			
			List<RyxSmallVideDTO> list = new ArrayList<RyxSmallVideDTO>();
			if(null == courseQuery.getCurrentPage() || 1 == courseQuery.getCurrentPage()){
				list = CourseHelper.getInstance().queryUnendVideo(Integer.MAX_VALUE);
			}
			
			String price = courseQuery.getPrice();
			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
			
			courseQuery.setIdeleted(0);
			//courseQuery.setDisplay(1);
			courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
			courseQuery.setEtend(System.currentTimeMillis()/1000);
			courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			list.addAll(MetaHelper.getInstance().queryMobileVideo(courseQuery));
			writeAjax(response, true,"",list);
					

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	
	@RequestMapping("/mryx/my/ajax_do_create_question.html")
	public void ajaxDoCreateQuestion(HttpServletRequest request, RyxQuestionDTO questionDTO,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {

			RyxUsersDTO users = getRyxUser();
			questionDTO.setUserId(users.getId());
			questionDTO.setOtype(MetaHelper.getInstance().getCategoryById(questionDTO.getCategory()).getModule().getType());
			ResultDTO<Long> resultDTO = ryxUserService.createQuestion(questionDTO) ;
			if(resultDTO.isSuccess()){
				writeAjax(response, true,"");
			}
			else{
				writeAjax(response, false,resultDTO.getErrorMsg());
			}			

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
		
	}
	
	
	
	
	@RequestMapping("/mryx/my/ajax_get_question.html")
	public void ajaxGetQuestion(HttpServletRequest request, RyxQuestionQuery query,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			
			ResultDTO<RyxQuestionQuery> resultDTO = ryxUserService.queryQuestion(query) ;
			if(resultDTO.isSuccess()){
				writeAjax(response, true,"",resultDTO.getModule());
			}
			else{
				writeAjax(response, false,resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/my/ajax_do_create_answer.html")
	public void ajaxDoCreateAnswer(HttpServletRequest request, RyxAnswerDTO answerDTO,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {			

			RyxUsersDTO users = getRyxUser();
			answerDTO.setUserId(users.getId());
			ResultDTO<Long> resultDTO = ryxUserService.createAnswer(answerDTO);
			if(resultDTO.isSuccess()){
				writeAjax(response, true,"");
			}
			else{
				writeAjax(response, false,resultDTO.getErrorMsg());
			}			

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
		
	}
	
	@RequestMapping("/mryx/my/ajax_get_answer.html")
	public void ajaxGetQuestion(HttpServletRequest request, RyxAnswerQuery query,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {			
			ResultDTO<RyxAnswerQuery> resultDTO = ryxUserService.queryAnswer(query) ;
			if(resultDTO.isSuccess()){
				writeAjax(response, true,"",resultDTO.getModule());
			}
			else{
				writeAjax(response, false,resultDTO.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/list_online_course1.html")
	public ModelAndView listOnlineCourse1(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistOnlineCourse1");

		String price = courseQuery.getPrice();
	

		courseQuery.setPageSize(6);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);

		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		

		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		return mav;

	}

	
	
	
	
	
	@RequestMapping("/mryx/my/offline_course")
	public ModelAndView myOfflineCourse(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mofflineCourse");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setUserId(users.getId());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyOfflineCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		
		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("query", courseQuery);

		return mav;

	}
	
	
	

	
	
	private static String PATH_FOLDER = "/";
	// 存放临时文件的目录
	private static String TEMP_FOLDER = "/";
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/mryx/my/do_upload_file", method = RequestMethod.POST )
	public void doUploadFile(

			@RequestParam(value = "path") String path,
			@RequestParam(value = "url") String url,
			HttpServletRequest request,  HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		//doUploadImage(path,url,request,response,rt);

	}
	
	
	@RequestMapping(value="/mryx/my/do_upload_image", method = RequestMethod.POST )
	public void doUploadImage(

			@RequestParam(value = "path") String path,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(TEMP_FOLDER));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * ConstHelper.IMAGE_SIZE_LIMIT);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			
		
			if(null == item){
				writeAjax(response,false, "请选择上传的图片");
			}
			else{
				/**
				 * 获取文件大小
				 */
				Long size = item.getSize();
				if(size<=0){
					writeAjax(response,false, "请选择上传的图片");
				}
				else if(size > 1024 * ConstHelper.IMAGE_SIZE_LIMIT){
					writeAjax(response, false, "图片大小不能超过"+ ConstHelper.IMAGE_SIZE_LIMIT/1024 +"M");
				}
				else{
					
					/**
					 * 获取二进制流
					 */
				
					byte[] bytes = item.get();				
	
					// 获取文件名
					String filename = getUploadFileName(item);
					
					String ext = filename.substring(filename.lastIndexOf("."));
					
					if( !ConstHelper.IMAGE_TYPE_LIMIT.equals("*") && ConstHelper.IMAGE_TYPE_LIMIT.indexOf(ext) < 0 ){
						writeAjax(response, false,"文件格式只能为"+ ConstHelper.IMAGE_TYPE_LIMIT +"其中的一种");
					}
					else{		
						
						String saveName =  UUID.randomUUID().toString() +  ext;
						
						ResultDTO<String> uploadResult = QiniuHelper.uploadBytes(bytes, saveName);
						if(uploadResult.isSuccess()){	
							writeAjax(response, true,"",uploadResult.getModule());
						}
						else{
							writeAjax(response, false, uploadResult.getErrorMsg());
						}
					}
				}
			}
		
		} catch (FileUploadException e) {
			writeAjax(response, false,e.getMessage());
		} catch (Exception e) {
			writeAjax(response, false,e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value="/mryx/my/do_upload_article", method = RequestMethod.POST )
	public void doUploadArticle(HttpServletRequest request,
			@RequestParam(value = "path") final String path,
			@RequestParam(value = "url") String url,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO users = getRyxUser();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(TEMP_FOLDER));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024 * ConstHelper.ARTICLE_SIZE_LIMIT);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			
		
			if(null == item){
				writeAjax(response,false, "请选择上传文件");
			}
			else{
				/**
				 * 获取文件大小
				 */
				Long size = item.getSize();
				if(size<=0){
					writeAjax(response,false, "请选择上传文件");
				}
				else if(size > 1024 * ConstHelper.ARTICLE_SIZE_LIMIT){
					writeAjax(response, false, "图片大小不能超过"+ ConstHelper.ARTICLE_SIZE_LIMIT +"M");
				}
				else{
					
					/**
					 * 获取二进制流
					 */
				
					final byte[] bytes = item.get();				
	
					// 获取文件名
					String filename = getUploadFileName(item);
					
					String ext = filename.substring(filename.lastIndexOf("."));
					
					if( !ConstHelper.ARTICLE_TYPE_LIMIT.equals("*") && ConstHelper.ARTICLE_TYPE_LIMIT.indexOf(ext) < 0 ){
						writeAjax(response, false,"文件格式只能为"+ ConstHelper.ARTICLE_TYPE_LIMIT +"其中的一种");
					}
					else{		
						
						// 保存后的文件名
						
						final String saveName =  UUID.randomUUID().toString() +  ext;
				
						ResultDTO<String> result = QiniuHelper.uploadBytes(bytes, saveName)	;							
												
						 writeAjax(response, true,"",result.getModule());						 
					}
				}
			}
		
		} catch (FileUploadException e) {
			writeAjax(response, false,e.getMessage());
		} catch (Exception e) {
			writeAjax(response, false,e.getMessage());
		}

	}
	
	private String getFileNameByUrl(String url){
		return "";
	}
	
	private FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if(!fileItem.isFormField()) {
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


	@RequestMapping("/mryx/list_teacher")
	public ModelAndView listTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistTeacher");
		
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);

		//ResultDTO<TeacherQuery> result = MetadataHelper.getInstance().queryTeacherCache1(teacherQuery);
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		
		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);
		mav.addObject("teacher", teacherQuery.getList());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());

		return mav;

	}
	
	
	
	

	
	@RequestMapping("/mryx/ajax_get_category_by_teacher_id.html")
	public void ajaxGetCategoryByTeacherId(@RequestParam(value = "tid") Long tid,HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);
		
		ResultDTO<RyxCategoryDTO> result = MetaHelper.getInstance().getCategoryByTeacherId(tid);
		if(result.isSuccess()){
			writeAjax(response, true,"",result.getModule());
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
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
	@RequestMapping("/mryx/ajax_get_teacher_by_id.html")
	public void ajaxGetTeacherById(HttpServletRequest request, Long id,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		//ResultDTO<TeacherQuery> result = MetadataHelper.getInstance().queryTeacherCache1(teacherQuery);
		ResultDTO<RyxTeacherDTO> result = MetaHelper.getInstance().getTeacherById(id);
		if(result.isSuccess()){
			writeAjax(response, true,"",result.getModule());
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/mryx/my/ajax_get_user_by_huanxin_username.html")
	public void ajaxGetUserByHuanxinUsername(HttpServletRequest request,
			@RequestParam(value = "username") String husername,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try{
		
			Long userId = Long.parseLong(husername.split("_")[1]);
			ResultDTO<RyxUsersDTO> result = MetaHelper.getInstance().getUserById(userId);
			if(result.isSuccess()){
				RyxUsersDTO usersDTO = result.getModule();
				String username = StringHelper.isNullOrEmpty(usersDTO.getUsername()) ? usersDTO.getEmail() : usersDTO.getUsername();
				String imageUrl = StringHelper.isNullOrEmpty(usersDTO.getPath()) ? "/resources/images/ryx/default_pic.png" : usersDTO.getPath(); 
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("username", username);
				jsonObject.put("imageUrl", imageUrl);
				writeAjax(response, true,"",jsonObject.toString());
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}
		}
		catch(Throwable t){
			writeAjax(response, false,t.toString());
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
	@RequestMapping("/mryx/list_teacher1")
	public ModelAndView listTeacher1(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistTeacher1");
		
		teacherQuery.setFlag(0);

		//ResultDTO<TeacherQuery> result = MetadataHelper.getInstance().queryTeacherCache1(teacherQuery);
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache(teacherQuery);
		errList = addList(errList, result.getErrorMsg());

		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);
		mav.addObject("teacher", teacherQuery.getList());
		mav.addObject("errList", errList);

		return mav;

	}

	
	@RequestMapping("/mryx/list_comment")
	public ModelAndView listComment(HttpServletRequest request, RyxFeedbackQuery query, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistComment");
		
		query.setStatus(EnumCommentStatus.APPROVED.getCode());
		ResultDTO<RyxFeedbackQuery> result = MetaHelper.getInstance().queryFeedbackCache(query);
		errList = addList(errList, result.getErrorMsg());
		
		if(null!=users){		
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
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/search.html")
	public ModelAndView search(HttpServletRequest request, String keyword , HttpServletResponse response)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/m/msearch");
		
		
		
		
		
		
		
		mav.addObject("keyword",keyword);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	
	
	@RequestMapping("/mryx/my/do_ajax_create_comment.html")
	public void doAjaxCreateComment(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt,RyxFeedbackDTO feedback)
			throws UnsupportedEncodingException {
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		
		
		if(null == feedback.getCourseId() || 0 == feedback.getCourseId() ){
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
		
		if(feedbackQueryResult.isSuccess()){
			
			if(feedbackQueryResult.getModule().getList().size() == 0){
				
				if(StringExUtils.isNullOrEmpty(feedback.getContent())){
					errList.add("请输入评论内容");
				}				
				else{
					Long orderId = feedback.getOrderId();
					
					feedback.setUserId(users.getId());
					feedback.setStatus(EnumCommentStatus.UNAUDITED.getCode());
					feedback.setFeedbackTime(new Date());
					feedback.setOrderId(feedback.getOrderDetailId());
					if(null==errList || 0 == errList.size()){
						ResultDTO<Boolean> result = ryxUserService.saveFeedback(feedback);
						errList = addList(errList, result.getErrorMsg());
						if(result.isSuccess()){
							
							ResultDTO<Boolean> updateOrderDetailResult =  ryxOrderService.updateOrderDetailIfFeedback(feedback.getOrderDetailId());
							errList = addList(errList, updateOrderDetailResult.getErrorMsg());
							
							RyxOrderDetailQuery orderDetailQuery = new RyxOrderDetailQuery();
							orderDetailQuery.setOrderId(orderId);
							orderDetailQuery.setIsFeedback(0);
							
							ResultDTO<Integer> countResult= ryxOrderService.countQueryOrderDetail(orderDetailQuery);
							errList = addList(errList, countResult.getErrorMsg());
							if(countResult.isSuccess() && countResult.getModule()==0){
								ResultDTO<Boolean> updateOrderResult =  ryxOrderService.updateOrderIfFeedback(orderId);
								errList = addList(errList, updateOrderResult.getErrorMsg());
							}							
						}
					}
				}
			}
			else{
				errList.add("您已评论，请勿重复评论");
			}
		}
		
		if(null==errList || 0 == errList.size()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,StringHelper.list2HtmlString(errList));
		}

	}
	
	
	
	@RequestMapping("/mryx/my/do_ajax_add_course_favorite.html")
	public void doAjaxAddCourseFavorite(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxUserFollowCourseDTO userFollowCourseDTO)
			throws UnsupportedEncodingException {
		
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		userFollowCourseDTO.setUserId(users.getId());
		
		RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(userFollowCourseDTO.getCourseId());
		userFollowCourseDTO.setOtype(courseDTO.getObjType());
		ResultDTO<Boolean> result = ryxCourseService.saveUserFollowCourse(userFollowCourseDTO);
				
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}

	}
	
	@RequestMapping("/mryx/my/ajax_cancel_follow_course.html")
	public void doAjaxCancelCourseFavorite(HttpServletRequest request, HttpServletResponse response,Long courseId,
			RedirectAttributes rt, RyxUserFollowCourseDTO userFollowCourseDTO)
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		ResultDTO<Boolean> result = ryxCourseService.deleteUserFollowCourseByCourseIdAndUserId(courseId, users.getId());
		
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/mryx/my/ajax_cancel_follow_teacher.html")
	public void doAjaxCancelTeacherFavorite(HttpServletRequest request, HttpServletResponse response,Long teacherId,
			RedirectAttributes rt, RyxUserFollowTeacherDTO userFollowTeacherDTO)
			throws UnsupportedEncodingException {
		
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		
		ResultDTO<Boolean> result = ryxTeacherService.deleteUserFollowTeacherByTeacherIdAndUserId(teacherId, users.getId());
		
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}

	}
	
	
	
	
	@RequestMapping("/mryx/my/do_ajax_add_teacher_favorite.html")
	public void doAjaxAddTeacherFavorite(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxUserFollowTeacherDTO userFollowTeacherDTO)
			throws UnsupportedEncodingException {
		
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		userFollowTeacherDTO.setUserId(users.getId());
		
		ResultDTO<Boolean> result = ryxTeacherService.saveUserFollowTeacher(userFollowTeacherDTO);
		
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}

	}
	
	
	@RequestMapping("/baidu_verify_8LoIOVHiL5.html")
	public ModelAndView baiduVerify_8LoIOVHiL5(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxUserFollowTeacherDTO userFollowTeacherDTO)
			throws IOException {
		ModelAndView mav = new ModelAndView("/ryx/m/baidu_verify_8LoIOVHiL5");
		return mav;
	}
	
	
	
	@RequestMapping("/baidu_verify_4SfE3fDU4I.html")
	public ModelAndView baiduVerify_4SfE3fDU4I(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, RyxUserFollowTeacherDTO userFollowTeacherDTO)
			throws IOException {
		ModelAndView mav = new ModelAndView("/ryx/m/baidu_verify_4SfE3fDU4I");
		return mav;
	}
	
	
	@ModelAttribute
	private void getSearchKeyword(){
		
	}
	
	private JSONObject getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}
	
	@RequestMapping("/mryx/my/king_upload.html")  
	
	public void doKingUpload(

			@RequestParam(value = "path") String path,
			HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws IOException {
		
		RyxUsersDTO users = getRyxUser();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(TEMP_FOLDER));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * ConstHelper.IMAGE_SIZE_LIMIT);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			// 提交上来的信息都在这个list里面
			// 这意味着可以上传多个文件
			// 请自行组织代码
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			
		
			if(null == item){
				writeAjax(response,false, "请选择上传的图片");
			}
			else{
				/**
				 * 获取文件大小
				 */
				Long size = item.getSize();
				if(size<=0){
					writeAjax(response,false, "请选择上传的图片");
				}
				else if(size > 1024 * ConstHelper.IMAGE_SIZE_LIMIT){
					writeAjax(response, false, "图片大小不能超过"+ ConstHelper.IMAGE_SIZE_LIMIT/1024 +"M");
				}
				else{
					
					/**
					 * 获取二进制流
					 */
				
					byte[] bytes = item.get();				
	
					// 获取文件名
					String filename = getUploadFileName(item);
					
					String ext = filename.substring(filename.lastIndexOf("."));
					
					if( !ConstHelper.IMAGE_TYPE_LIMIT.equals("*") && ConstHelper.IMAGE_TYPE_LIMIT.indexOf(ext) < 0 ){
						writeAjax(response, false,"文件格式只能为"+ ConstHelper.IMAGE_TYPE_LIMIT +"其中的一种");
					}
					else{		
						
						String saveName =  UUID.randomUUID().toString() +  ext;
						
						ResultDTO<String> uploadResult = QiniuHelper.uploadBytes(bytes, saveName);
						if(uploadResult.isSuccess()){
							JSONObject obj = new JSONObject();
							obj.put("error", 0);
							obj.put("url", uploadResult.getModule());
							writeAjax(response, obj);
						}
						else{
							writeAjax(response,getError(uploadResult.getErrorMsg()));
						}
					}
				}
			}
		
		} catch (FileUploadException e) {
			writeAjax(response, getError(e.getMessage()));
		} catch (Exception e) {
			writeAjax(response, getError(e.getMessage()));
		}
	}
	
//	fjy手机端登录
	@RequestMapping("/m/ajax_do_login.html")
	public void ajaxmDoMlogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		String targetUrl = request.getParameter("targetUrl");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String isRememberPassword = request.getParameter("isRememberPassword");
		errList = new ArrayList<String>();
		
		
		if (!StringUtils.isNotEmpty(username)) {
			errList.add("用户名不能为空");
		}

		if (!StringUtils.isNotEmpty(password)) {
			errList.add("密码不能为空");
		}

		if (null == errList || errList.size() == 0) {	
			try {
				
				username = username.trim();			
				RyxUsersDTO  user = ryxUserService.getUserByMobileOrEmail(username).getModule();
				if (null == user) {
					errList.add("用户名不存在");
				} else {
					user = ryxUserService.checkUserLogin(username,Md5Util.GetMD5Code(password.trim())).getModule();
					
					if (null == user) {
						errList.add("错误的用户名、密码");
					}else {
						CookieHelper.addCookie(response, SessionHelper.LOGIN_USERNAME_COOKIE, 
								StringHelper.aesEncrypt(username, SessionHelper.ENCRIPT_DECRIPT_KEY), 24*60*60*30, "/");
						if(StringHelper.isNullOrEmpty(isRememberPassword)){
							CookieHelper.removeCookies(SessionHelper.LOGIN_USER_PASSWORD_COOKIED, "/", request, response);
						}		
						else{
							CookieHelper.addCookie(response, SessionHelper.LOGIN_USER_PASSWORD_COOKIED, 
									StringHelper.aesEncrypt(password, SessionHelper.ENCRIPT_DECRIPT_KEY), 24*60*60*30, "/");
						}
					}
					
				}
				
			}
			
			catch (Exception ex) {
				errList.add(ex.getMessage());
			}
		}
		
		if (null == errList || errList.size() == 0) {			
			writeAjax(response, true);			
		} else {
			String errors = "";
			for (String s : errList) {
				errors += s + "<BR>";
			}
			writeAjax(response, false, errors);
		}
	}
	@RequestMapping("/m/ajax_do_register.html")
	public void ajaxmDoRegisterMryx(
			HttpServletRequest request,
			@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO, 
			BindingResult bindingResult,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		
		String serverName = request.getServerName().toLowerCase();
		if (/*!bindingResult.hasErrors()*/ 1 == 1) {
			/*ResultDTO<TempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
			TempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
			}*/

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
				errList.add("该用户名已经注册，请直接登录");
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

				user.setRfrom(EnumRegFrom.H5.getCode());
				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {

					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {

						/**//**
						 * 注册成功送代金券
						 *//*
	*/						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"元代金券");
						userCouponDTO.setCreaterId(userId);
						userCouponDTO.setType(EnumAccountType.COUPON.getCode());
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
						addList(errList, result.getErrorMsg());
//							errList.add("注册成功，请<a href=\"/mryx/login.html\">登录</a>");

					} else {
						errList.add("无效的用户Id");
					}
				} else {
					errList.add("未知错误");
				}
			}
		}

		
		
		if (null == errList || errList.size() == 0) {			
			writeAjax(response, true);			
		} else {
			String errors = "";
			for (String s : errList) {
				errors += s + "<BR>";
			}
			writeAjax(response, false, errors);
		}

	}
	
	
	@RequestMapping("/m/ajax_do_reset_password.html")
	public void ajaxmDoResetPasswordMryx(@Valid @ModelAttribute("forgetPasswordDTO") RyxForgetPasswordDTO forgetPasswordDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		if (!bindingResult.hasErrors()) {

			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(forgetPasswordDTO.getMobile(), forgetPasswordDTO.getVerifyCode());
			RyxTempUserDTO list = listr.getModule();
			errList = addList(errList, listr.getErrorMsg());

			if (null == list) {
				errList.add("验证码无效");
			}

			if (null == errList || errList.size() == 0) {

				ResultDTO<Boolean> updatepasswordResult = ryxUserService.updatePasswordByMobile(Md5Util.GetMD5Code(forgetPasswordDTO.getPassword()),
						forgetPasswordDTO.getMobile());
				errList = addList(errList, updatepasswordResult.getErrorMsg());
				if(updatepasswordResult.isSuccess()){
					errList.add("重置密码成功，请<a href=\"/mryx/login.html\">登录</a>");
				}
			}

		} else {
			errList.add("未知错误");
		}
		if (null == errList || errList.size() == 0) {			
			writeAjax(response, true);			
		} else {
			String errors = "";
			for (String s : errList) {
				errors += s + "<BR>";
			}
			writeAjax(response, false, errors);
		}

	}

	
}
