package com.king.nowedge.controller.m;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.RyxCategoryDTO;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.dto.ryx.query.RyxEvaluQuery;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.Md5Helper;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class McourseController  extends BaseController{
	
	private static final Log logger = LogFactory.getLog(McourseController.class);
	
	@RequestMapping("/m/list_online.html")
	public ModelAndView listOnline(HttpServletRequest request,RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		
		if(!StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/list_online.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://www.ryx365.com/list_online.html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/list_online.html");
			}
		}
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mlistOnline");


		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		mav.addObject("course", MetaHelper.getInstance().queryMobileOnline(courseQuery));
		mav.addObject("errList", errList);
		mav.addObject("query", courseQuery);
		mav.addObject("loginUsers", users);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("在线培训课程","在线培训课程【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	@RequestMapping("/m/list_video.html")
	public ModelAndView listVideo(HttpServletRequest request,RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		
		if(!StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/list_video.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://www.ryx365.com/list_video.html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/list_video.html");
			}
		}
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mlistVideo");

		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setIdeleted(0);
		//courseQuery.setDisplay(1);
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("desc");
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		mav.addObject("course", MetaHelper.getInstance().queryMobileVideo(courseQuery));
		mav.addObject("errList", errList);
		mav.addObject("query", courseQuery);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("直播课程","直播课程【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	@RequestMapping("/m/my/play_offline_{id}.html")
	public ModelAndView playOffline(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {
		
		
		if(!StringHelper.isMoblie(request)){
			return new ModelAndView("redirect:/my/play_offline_"+ id +".html");
		}

		ModelAndView mav = new ModelAndView("/ryx/m/mplayOffline");
		RyxCourseDTO  ryxCourseDTO = ryxCourseService.getCourseById(id).getModule() ;
		mav.addObject("offline", ryxCourseDTO );
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUser", usersDTO);
		String username = usersDTO.getUsername() ;
		if(StringHelper.isNullOrEmpty(username) || username.length() == 14){
			username = StringHelper.getFuzzyMobile2(usersDTO.getMobile());
		}
		mav.addObject("username", username);
		mav.addObject("title", ryxCourseDTO.getTitle());
		return mav ;
		
	}
	
	
	@RequestMapping("/m/play_offline_{id}.html")
	public ModelAndView playOfflineFree(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		
		if(!StringHelper.isMoblie(request)){
			return new ModelAndView("redirect:/play_offline_"+ id +".html");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/m/mplayOffline");
		RyxCourseDTO  ryxCourseDTO = ryxCourseService.getCourseById(id).getModule() ;
		mav.addObject("offline", ryxCourseDTO );
		mav.addObject("title", ryxCourseDTO.getTitle());
		return mav ;
		
	}
	
	@RequestMapping("/m/my/play_video_{videoId}.html")
	public ModelAndView myPlayVideo(@PathVariable Long videoId,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
	
		
		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(videoId);
		if(null!=course && null != course.getPrice() && course.getPrice() > 0 && !CourseHelper.getInstance().isBuyVideo(users.getId(), videoId)){
			return new ModelAndView("redirect:/m/video_"+ videoId +".html");
		}
		

		ModelAndView mav = new ModelAndView("/ryx/m/mplayVideo2");
		if(EnumVideoType.DOC.getCode() == course.getFlag()){
			mav = new ModelAndView("/ryx/m/mplayVideo1");
		}
		
		
		mav.addObject("course", course);

		if (null == course) {
			errList.add("invalid course id : " + videoId);
		} else { // course is not null
			
			if(!StringHelper.isNullOrEmpty(course.getVid())){
				return new ModelAndView("redirect:/m/online_"+ course.getVid() +".html");
			}
			
			mav.addObject("buyFlag", CourseHelper.getInstance().isBuyVideo(null==users?null:users.getId(), videoId));

			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(videoId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ?
					"":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
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

	@RequestMapping("/m/play_video_{videoId}.html")
	public ModelAndView playVideo(@PathVariable Long videoId,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		errList = new ArrayList<String>();
		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(videoId);

		
		ModelAndView mav = new ModelAndView("/ryx/m/mplayVideo2");
		if(EnumVideoType.DOC.getCode() == course.getFlag()){
			mav = new ModelAndView("/ryx/m/mplayVideo1");
		}
		
		
		RyxUsersDTO users = getRyxUser();
		mav.addObject("course", course);
		
		

		if (null == course) {
			errList.add("invalid course id : " + videoId);
		} else { // course is not null
			
			if(!StringHelper.isNullOrEmpty(course.getVid())){
				return new ModelAndView("redirect:/m/online_"+ course.getVid() +".html");
			}
			
			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(videoId);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ?
					"":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);

		
			mav.addObject("errList", errList);
			mav.addObject("na", "course");

			mav.addObject("loginUsers", users);
			mav.addObject("ccUserId","B8AD6C2DA430F1C2");
			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		return mav;
	}
	
	@RequestMapping("/m/play_video2.html")
	public ModelAndView playVideo2(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {
		ModelAndView mav = new ModelAndView("/ryx/m/mplayVideo2");
		RyxUsersDTO usersDTO = new RyxUsersDTO();
		usersDTO.setId(1504L);
		RyxCourseDTO course = new RyxCourseDTO();
		course.setGetRtmp("2D1020C21F2AE4A39C33DC5901307461");
		mav.addObject("course" , course);
		mav.addObject("loginUsers" , usersDTO);
		mav.addObject("ccUserId","B8AD6C2DA430F1C2");
		return mav;
	}
	@RequestMapping("/m/ajax_get_user.html")
	public void ajaxGetUser(
			Long id ,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		writeAjax(response, true,UserHelper.getInstance().getUserById(id));
	}
	
	
	@RequestMapping("/m/ajax_get_teacher.html")
	public void ajaxGetTeacher(
			Long id ,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		writeAjax(response, true,UserHelper.getInstance().getTeacherByUserId(id));
	}
	
	
	@RequestMapping("/m/list_evalu.html")
	public ModelAndView listEvalu(HttpServletRequest request,RyxEvaluQuery query, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mlistEvalu");
		mav.addObject("errList", errList);
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		mav.addObject("query", query);
		mav.addObject("list", MetaHelper.getInstance().getSmallEvalu(query));
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	@RequestMapping(value = "/m/search_offline.html")
	public ModelAndView doSearchMobileOffline(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/msearchOffline");
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);

		mav.addObject("query",courseQuery);
		mav.addObject("course", MetaHelper.getInstance().queryMobileOffline(courseQuery));		
		mav.addObject("na", "offline");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	

	
	@RequestMapping(value = "/m/search_online.html")
	public ModelAndView doSearchMobileOnline(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/msearchOnline");
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "online");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		

		return mav;

	}
	
	
	@RequestMapping(value = "/m/search_video.html")
	public ModelAndView doSearchMobileVideo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/msearch");
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
	
	
	@RequestMapping("/m/list_online_{category}.html")
	public ModelAndView listOnline(
			
			HttpServletRequest request,RyxCourseQuery courseQuery, 
			@PathVariable Integer category,
			HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		courseQuery.setCategory(category);
		return listOnline( request, courseQuery,  response,  rt);

	}
	
	
	@RequestMapping("/m/list_offline_{category}.html")
	public ModelAndView listOffline(
			
			HttpServletRequest request,RyxCourseQuery courseQuery, 
			@PathVariable Integer category,
			HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		courseQuery.setCategory(category);
		return listOffline(request, courseQuery,  response,  rt);

	}
	
	
	@RequestMapping("/m/list_offline.html")
	public ModelAndView listOffline(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		if(!StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/list_offline.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://www.ryx365.com/list_offline.html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/list_offline.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistOffline");
		

		RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
		BeanUtils.copyProperties(courseQuery,ryxCourseQuery ,BeanHelper.getNullPropertyNames(courseQuery));
		ryxCourseQuery.setPageSize(Integer.MAX_VALUE);
		mav.addObject("unendList", MetaHelper.getInstance().getUnendOffline(ryxCourseQuery));



		courseQuery.setEtstart(System.currentTimeMillis()/1000);
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setOrderBy("tstart");
		courseQuery.setSooort("desc");
		
		List<RyxCategoryDTO> offlineCategoryResult = MetaHelper.getInstance().getOfflineCategory();
		
		mav.addObject("course", MetaHelper.getInstance().queryMobileOffline(courseQuery));
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("query", courseQuery);
		mav.addObject("categorys", offlineCategoryResult);
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("线下培训课程","线下培训课程【融易学金融学院】",mav,request);
		}

		return mav;

	}
	
	
	
	@RequestMapping("/m/list_info.html")
	public ModelAndView listInfo(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistInfo");


		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.INFO_MODULE.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		
		mav.addObject("course", MetaHelper.getInstance().queryMobileInfo(courseQuery));
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("query", courseQuery);
		mav.addObject("categorys", MetaHelper.getInstance().getInfoCategory());
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("行业资讯","行业资讯【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	
	@RequestMapping("/m/list_book.html")
	public ModelAndView listBook(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mlistBook");


		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.BOOK_MODULE.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		
		mav.addObject("course", MetaHelper.getInstance().queryMobileBook(courseQuery));
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("query", courseQuery);
		mav.addObject("categorys", MetaHelper.getInstance().getInfoCategory());
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("行业资讯","行业资讯【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	@RequestMapping("/m/train.html")
	public ModelAndView train(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mtrain");
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("企业内训","企业内训【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	
	@RequestMapping("/m/offline_{id}.html")
	public ModelAndView courseOfflineDetail(
			@PathVariable Long id,	
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		if(!StringHelper.isMoblie(request) || isWindowsWeixinExplorer(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/offline_"+ id +".html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:https://www.ryx365.com/offline_"+ id +".html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/offline_"+ id +".html");
			}
		}

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mofflineDetail");	
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/m/offline_"+ id +".html?u="+u);
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
			
			
			

			
			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
	
			/**
			 * get related course
			 */
	
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = course.getRelatedCourse();
			if (relatedCourse != null) {	
				ResultDTO<List<RyxCourseDTO>> rlistResult = MetaHelper.getInstance().getCourseByIds(relatedCourse);
				errList = addList(errList, rlistResult.getErrorMsg());
				List<RyxCourseDTO> courseList = rlistResult.getModule();
				mav.addObject("relatedCourse", courseList);
			}	
			
	
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());
	
			
			mav.addObject("course", course);
			
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
			Boolean isWeixinExplorer = isWeixinExplorer(request);		
			if(isWeixinExplorer){
				try{
					setCourseWeixinSpread(course,mav,request);
				}
				catch(Throwable t){
					logger.error(t.getMessage(), t);
				}
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
	
	
	
	@RequestMapping("/m/i_{id}.html")
	public ModelAndView infoDetail(
			@PathVariable Long id,	
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/minfoDetail");
		if(!StringHelper.isNullOrEmpty(request.getParameter("app"))){
			mav = new ModelAndView("/ryx/m/minfoDetailApp");	
		}
		mav.addObject("app", request.getParameter("app"));
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/m/i_"+ id +".html?u="+u);
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
			
			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
	
			/**
			 * get related course
			 */
	
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = course.getRelatedCourse();
			if (relatedCourse != null) {	
				ResultDTO<List<RyxCourseDTO>> rlistResult = MetaHelper.getInstance().getCourseByIds(relatedCourse);
				errList = addList(errList, rlistResult.getErrorMsg());
				List<RyxCourseDTO> courseList = rlistResult.getModule();
				mav.addObject("relatedCourse", courseList);
			}	
			
	
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());
	
			
			mav.addObject("course", course);
			
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
			Boolean isWeixinExplorer = isWeixinExplorer(request);		
			if(isWeixinExplorer){
				setCourseWeixinSpread(course,mav,request);
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
	
	
	
	
	@RequestMapping("/m/b_{id}.html")
	public ModelAndView bookDetail(
			@PathVariable Long id,	
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mbookDetail");
		if(!StringHelper.isNullOrEmpty(request.getParameter("app"))){
			mav = new ModelAndView("/ryx/m/mbookDetailApp");	
		}
		mav.addObject("app", request.getParameter("app"));
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/m/i_"+ id +".html?u="+u);
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
			
			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
	
			/**
			 * get related course
			 */
	
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = course.getRelatedCourse();
			if (relatedCourse != null) {	
				ResultDTO<List<RyxCourseDTO>> rlistResult = MetaHelper.getInstance().getCourseByIds(relatedCourse);
				errList = addList(errList, rlistResult.getErrorMsg());
				List<RyxCourseDTO> courseList = rlistResult.getModule();
				mav.addObject("relatedCourse", courseList);
			}	
			
	
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());
	
			
			mav.addObject("course", course);
			
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
			Boolean isWeixinExplorer = isWeixinExplorer(request);		
			if(isWeixinExplorer){
				setCourseWeixinSpread(course,mav,request);
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
	
	
	@RequestMapping("/m/video_{id}.html")
	public ModelAndView courseVideoDetail(
			@PathVariable Long id,	
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		if(!StringHelper.isMoblie(request) || isWindowsWeixinExplorer(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/video_"+ id +".html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:https://www.ryx365.com/video_"+ id +".html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/video_"+ id +".html");
			}
		}

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/mvideoDetail");	
		String uString = request.getParameter("u");
		if(StringHelper.isNullOrEmpty(uString)){
			String u = getPartnerUstring();
			if(!StringHelper.isNullOrEmpty(u)){
				mav = new ModelAndView("redirect:/m/video_"+ id +".html?u="+u);
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
			
			
			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());
	
			
			mav.addObject("course", course);
			
			mav.addObject("title", MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle() + "_" +  course.getTitle());
			
			Boolean isWeixinExplorer = isWeixinExplorer(request);		
			if(isWeixinExplorer){
				setCourseWeixinSpread(course,mav,request);
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
	
	@RequestMapping("/m/online.html")
	public ModelAndView online(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/monline");
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("在线培训课程","在线培训课程【融易学金融学院】",mav,request);
		}
		return mav;

	}
	
	
	
	@RequestMapping("/m/teacher.html")
	public ModelAndView teacher(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/mteacher");
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("名家讲堂","名家讲堂【融易学金融学院】",mav,request);
		}
		mav.addObject("isWeixinExplorer", isWeixinExplorer);

		return mav;

	}
	
	
	
	@RequestMapping("/m/my/vip_card.html")
	public ModelAndView myUnexpiredCard(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mvipCard");

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setObjType(EnumObjectType.CARD_MODULE.getCode());

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("na", "unexpired_card");

		return mav;

	}

	
	
	@RequestMapping("/m/online_{id}.html")
	public ModelAndView online(
			@PathVariable Long id,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {
		
		
		if(!StringHelper.isMoblie(request) || isWindowsWeixinExplorer(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pre.ryx365.com/online_"+ id +".html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:https://www.ryx365.com/online_"+ id +".html");
			}
			else{
				return new ModelAndView("redirect:http://a.ryx365.com/online_"+ id +".html");
			}
		}

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/monlineDetail");
		String from = request.getParameter("from");
		return online(mav,id,from);
	}
	
	
	private ModelAndView online( ModelAndView mav ,Long id,String from) throws Exception{
		
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		
		if(null == course || null == course.getObjType()){
			throw new Exception("invalid obj type");
		}
		else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/offline_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/article_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/video_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/activity_" + id + ".html");
			return mav;
		}
		else{
			
			Long subid = CourseHelper.getInstance().getSubCourseByMainCourse(id);
			mav = new ModelAndView("redirect:/m/online1_" + id + "_"+ subid +".html?from=" + from);
			
				
		}
		mav.addObject("from", from);
		
		return mav;
		
	}
	
	
	@RequestMapping("/m/online1_{id}_{subid}.html")
	public ModelAndView online1(
			@PathVariable Long id,
			@PathVariable Long subid,
			RyxEvaluQuery query,
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws Exception {

		CookieHelper.removeCookies(SessionHelper.LATEST_STUDY_COURSE_ZHANGJIE_COOKIE + "_" + id, "/" , request, response);

		String from = request.getParameter("from");
		
		Boolean buyFlag = false;
		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/monlineDetail");
		
		
		
		
		
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		
		if(null == course || null == course.getObjType()){
			throw new Exception("invalid obj type");
		}
		else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/offline_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/article_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/video_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/m/activity_" + id + ".html");
			return mav;
		}
		else{
			
			
			String url = "" ;
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setRkey1(id.toString()); // 主课程
			String shidaiUserId = CookieHelper.getCookies(SessionHelper.SHIDAI_CURRENT_USER_ID, request, "/") ;
			if(StringHelper.isNullOrEmpty(shidaiUserId)){
				if(null != users){
					keyrvDTO.setKey1(users.getId().toString()); //用户id
				}
			}
			else{
				keyrvDTO.setKey1(shidaiUserId);  //用户id
			}
			keyrvDTO.setRkey(id.toString());
			if(null != from && !from.equals("null") && !from.equals("singlemessage") ){
				mav = new ModelAndView("/ryx/m/monlineShidaiDetail");
				url = "/onlineshidai_"+ subid +"_"+ id +"__1.htm" ;
			}
			else{
				url =  "/online_"+ subid +"_"+ id +"__1.htm" ;
			}
			

			keyrvDTO.setValue(url);
			keyrvDTO.setType(EnumKeyRelatedValueType.KV_CURRENT_COURSE_ZHANGJIE.getCode());
			if(!StringHelper.isNullOrEmpty(keyrvDTO.getKey1())){
				systemService.createOrUpdateKeyrv(keyrvDTO); // 记录最后观看记录 ；
			}
			

			mav.addObject("course", course);
			
			if(null == course.getFlag()){
				throw new Exception("INVALID FLAG");
			}
			
			/**
			 * 评价
			 */
			query.setObjId(id);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setIdeleted(0);
			query.setPageSize(3);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxEvaluQuery> queryResult = MetaHelper.getInstance().queryEvalu(query);
			mav.addObject("query", queryResult.getModule());
			

			if (null != users) {
				buyFlag = isBuyOnline(course, users.getId(), null , subid); 
				mav.addObject("buyFlag", buyFlag);
			}
			if(null != subid){
				Long ts = System.currentTimeMillis();		
				mav.addObject("ts", ts);
				RyxCourseDTO subCourseDTO = CourseHelper.getInstance().getCourseById(subid);
				mav.addObject("subcourse", subCourseDTO);
				mav.addObject("sign", Md5Helper.getMD5String("Kd8jQHITMj" + subCourseDTO.getVid() + ts));
			}

			
			

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

			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);
			mav.addObject("subid",subid);

		

			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			mav.addObject("outline", request.getParameter("outline"));

			Boolean isWeixinExplorer = isWeixinExplorer(request);		
			if(isWeixinExplorer){
				setCourseWeixinSpread(course,mav,request);
			}
			mav.addObject("isWeixinExplorer", isWeixinExplorer);
			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		mav.addObject("from", from);
		return mav;
	}
	@RequestMapping("/m/ajax_online_evalue.html")
	public void onlineEvalue(
			RyxEvaluQuery query,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();

		/**
		 * 评价
		 */
		//Long id = (long) 1896;
		//query.setObjId(id);
		//query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		query.setIdeleted(0);
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		
		writeAjax(response, true,MetaHelper.getInstance().getSmallEvalu(query));
	}
	
	@RequestMapping("/m/offline.html")
	public ModelAndView offline(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/moffline");
		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);
		mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
}
