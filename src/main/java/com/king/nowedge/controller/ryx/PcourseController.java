package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.ResumeDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvQuery;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.dto.ryx2.validate.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PcourseController extends BaseController{

	private static final Log logger = LogFactory.getLog(PcourseController.class);




	@RequestMapping("/admin/object_limit.html")
	public void objectLimit(
			HttpServletRequest request,
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		List<KeyrvDTO> list = systemService.queryKeyrv(keyrvQuery).getModule().getList();
		for(KeyrvDTO keyrvDTO : list){
			RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
			objectLimitDTO.setUserId(Long.parseLong(keyrvDTO.getRkey()));
			objectLimitDTO.setMuserId(Long.parseLong(keyrvDTO.getKey1()));
			ryxCourseService.updateObjectLimitByUserId(objectLimitDTO);
		}
	}


	@RequestMapping("/my/ajax_update_course.html")
	public void ajaxUpdateCourse(
			HttpServletRequest request,
			RyxCourseDTO courseDTO,
			BindingResult bindingResult,
			HttpServletResponse response)
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();
			courseDTO.setCreater(user.getId());
			courseDTO.setCuid(user.getId());

			ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}



	@RequestMapping("/c/ajax_delete_auth_subaccount_course.html")
	public void ajaxDeleteAuthSubAccountCourse(
			HttpServletRequest request,
			RyxObjectLimitDTO objectLimitDTO,
			BindingResult bindingResult,
			HttpServletResponse response)
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();

			KeyrvQuery keyrvQuery = new KeyrvQuery();
			keyrvQuery.setKey1(user.getId().toString());
			keyrvQuery.setRkey(objectLimitDTO.getUserId().toString());
			keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());

			ResultDTO<KeyrvQuery> keyrvResultDTO = systemService.queryKeyrv(keyrvQuery);
			errList = addList(errList, keyrvResultDTO.getErrorMsg());
			if(keyrvResultDTO.isSuccess()
					&& null != keyrvResultDTO.getModule()
					&& null != keyrvResultDTO.getModule().getList()
					&& keyrvResultDTO.getModule().getList().size() ==1){

				objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode()); // 公司赠送			
				ResultDTO<Boolean> result = ryxCourseService.deleteOrgAuthObjectLimit(objectLimitDTO);
				if (!result.isSuccess()) {
					writeAjax(response, false,result.getErrorMsg());
				} else {
					writeAjax(response, true);
				}
			}
			else{
				writeAjax(response, false,"无效子账号--->" + objectLimitDTO.getUserId());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}



	@RequestMapping("/c/ajax_delete_auth_subaccount_course1.html")
	public void ajaxDeleteAuthSubAccountCourse1(
			HttpServletRequest request,
			RyxObjectLimitDTO objectLimitDTO,
			BindingResult bindingResult,
			HttpServletResponse response)
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();

			KeyrvQuery keyrvQuery = new KeyrvQuery();
			keyrvQuery.setKey1(user.getId().toString());
			keyrvQuery.setRkey(objectLimitDTO.getUserId().toString());
			keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());

			ResultDTO<KeyrvQuery> keyrvResultDTO = systemService.queryKeyrv(keyrvQuery);
			errList = addList(errList, keyrvResultDTO.getErrorMsg());
			if(keyrvResultDTO.isSuccess()
					&& null != keyrvResultDTO.getModule()
					&& null != keyrvResultDTO.getModule().getList()
					&& keyrvResultDTO.getModule().getList().size() ==1){

				objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode()); // 公司赠送			
				ResultDTO<Boolean> result = ryxCourseService.deleteOrgAuthObjectLimit(objectLimitDTO);
				if (!result.isSuccess()) {
					writeAjax(response, false,result.getErrorMsg());
				} else {
					writeAjax(response, true);
				}
			}
			else{
				writeAjax(response, false,"无效子账号--->" + objectLimitDTO.getUserId());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	/* --------------------------------------------------------------------
	 * 		活动
	 --------------------------------------------------------------------*/




	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_activity.html")
	public ModelAndView createActivity(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateActivity");
		errList = new ArrayList<String>();
		setCreateActivityObject(mav,errList);
		mav.addObject("createDTO",new ActivityDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;
	}


	@RequestMapping("/my/update_activity.html")
	public ModelAndView updateActivity(
			@RequestParam(value = "id") Long id,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateActivity");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();
		setCreateActivityObject(mav,errList);
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;
	}


	@RequestMapping("/my/activity.html")
	public ModelAndView myActivity(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyActivity");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
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
			courseQuery.setEtstart(DateHelper.getTodayLong());
		}


		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		courseQuery.setCuid(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListActivityObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_activity");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}


	@RequestMapping("/my/apply_recruit.html")
	public ModelAndView myApplyRecruit(
			KeyrvQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/capplyRecruit");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		query.setKey1(users.getId().toString());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
		ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", result.getModule());

		setListActivityObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_apply_recruit");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}

	@RequestMapping("/my/reserve_video.html")
	public ModelAndView myReserveVideo(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cReserveVideo");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		if(EnumVideoStatus.AFTER_LIVING.getCode() == query.getOinterval()){ // 已经结束
			query.setEtend(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.BEFORE_LIVING.getCode() == query.getOinterval()){  // 尚未开始
			query.setTtstart(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.LIVING.getCode() == query.getOinterval()){   // 直播中
			query.setEtstart(System.currentTimeMillis()/1000);
			query.setTtend(System.currentTimeMillis()/1000);
		}


		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOtype(EnumObjectType.VIDEO_MODULE.getCode());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);

		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", result.getModule());

		setListActivityObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_reserve_video");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}






	@RequestMapping("/my/do_create_activity.html")
	public ModelAndView doCreateActivity(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ActivityDTO activityDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateActivity");

		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		setCreateActivityObject(mav,errList);
		mav.addObject("na", "create_activity");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{

				activityDTO.setCreater(usersDTO.getId());
				activityDTO.setCreateTime(System.currentTimeMillis()/1000);
				activityDTO.setUpdateTime(System.currentTimeMillis()/1000);
				activityDTO.setCuid(usersDTO.getId());
				activityDTO.setTid(usersDTO.getTid());
				activityDTO.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
				activityDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(activityDTO,courseDTO,BeanHelper.getNullPropertyNames(activityDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功，请耐心等待审核");
				}
			}


		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/my/do_update_activity.html")
	public ModelAndView doUpdateMyActivity(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ActivityDTO activityDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateActivity");

		errList = new ArrayList<String>();
		setCreateActivityObject(mav,errList);
		mav.addObject("na", "create_activity");

		RyxUsersDTO usersDTO = getRyxUser();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{

				activityDTO.setCreater(usersDTO.getId());
				activityDTO.setUpdateTime(System.currentTimeMillis()/1000);
				activityDTO.setCuid(usersDTO.getId());
				activityDTO.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());


				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(activityDTO.getId());
				if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){
					activityDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
					auditRecordDTO.setObjId(activityDTO.getId());
					auditRecordDTO.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
					auditRecordDTO.setUserId(usersDTO.getId());
					auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					auditRecordDTO.setCreater(usersDTO.getId());
					auditRecordDTO.setDescr("我更新课程，重新打开审核");
					ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
					errList.add(auditResult.getErrorMsg());
				}

				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(activityDTO,courseDTO,BeanHelper.getNullPropertyNames(activityDTO));
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");
				}
			}


		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);

		return mav;

	}





	/**
	 *  文库
	 */



	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_article.html")
	public ModelAndView createArticle(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateArticle");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();
		setCreateArticleObject(mav,errList);
		mav.addObject("createDTO",new ArticleDTO());
		return mav;
	}


	@RequestMapping("/my/update_article.html")
	public ModelAndView updateArticle(
			@RequestParam(value = "id") Long id,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateArticle");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();
		setCreateArticleObject(mav,errList);
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;
	}


	@RequestMapping("/my/article.html")
	public ModelAndView myArticle(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyArticle");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		courseQuery.setCuid(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListArticleObject(mav, errList);

		mav.addObject("na", "my_article");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}




	@RequestMapping("/my/do_create_course_series.html")
	public ModelAndView doCreateCourseSeries(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("redirect:/my/list_course_series.html?courseId="+keyrvDTO.getKey1());
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvDTO.setIdeleted(0);
		ResultDTO<Boolean> resultDTO = systemService.createKeyrvBatch(keyrvDTO);
		addList(errList, resultDTO.getErrorMsg());
		RyxUsersDTO usersDTO = getRyxUser();
		//mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		redirectAttributes.addAttribute("errList", errList);
		return mav;
	}


	/**
	 * 线下课程
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/my/play_offline_{id}.html")
	public ModelAndView playOffline(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/cplayOffline");
		mav.addObject("offline", ryxCourseService.getCourseById(id).getModule());
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUser", usersDTO);
		String username = usersDTO.getUsername() ;
		if(StringHelper.isNullOrEmpty(username) || username.length() == 14){
			username = StringHelper.getFuzzyMobile2(usersDTO.getMobile());
		}
		mav.addObject("username", username);
		return mav ;

	}


	/**
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/play_offline_{id}.html")
	public ModelAndView playOfflineFree(@PathVariable Long id,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/cplayOffline");
		RyxCourseDTO ryxCourseDTO = ryxCourseService.getCourseById(id).getModule();
		mav.addObject("offline", ryxCourseDTO);
		mav.addObject("title", ryxCourseDTO.getTitle());
		return mav ;

	}



	@RequestMapping("/my/play_video_{videoId}.html")
	public ModelAndView playVideo(@PathVariable Long videoId,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cplayVideo");
		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(videoId);
		if(null!=course && null != course.getPrice() && course.getPrice() > 0 && !CourseHelper.getInstance().isBuyVideo(users.getId(), videoId)){
			return new ModelAndView("redirect:/video_"+ videoId +".html");
		}
		mav.addObject("course", course);

		if (null == course) {
			errList.add("invalid course id : " + videoId);
		} else { // course is not null

			mav.addObject("buyFlag", CourseHelper.getInstance().isBuyVideo(null==users?null:users.getId(), videoId));

			/**
			 * get chatroom id by course id
			 */
			String groupId="";
			KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setKey1(videoId.toString());
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COURSE_CHAT_GROUP.getCode());
			ResultDTO<List<KeyvalueDTO>> keyvalueResultDTO = systemService.queryKeyvalue(keyvalueQuery);
			errList = addList(errList, keyvalueResultDTO.getErrorMsg());
			List<KeyvalueDTO> keyvalueDTOs = keyvalueResultDTO.getModule();
			if(null!=keyvalueDTOs && keyvalueDTOs.size() == 1){
				groupId = keyvalueDTOs.get(0).getValue();
			}
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
			addPasswordModel(mav, request, getCurrentUrl(request));
		return mav;
	}
	
	
	
	@RequestMapping("/my/play_{videoId}.html")
	public ModelAndView play(@PathVariable Long videoId,
			RyxEvaluQuery query,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cplay");
		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(videoId);
		if(null!=course && null != course.getPrice() && course.getPrice() > 0 && !CourseHelper.getInstance().isBuyVideo(users.getId(), videoId)){
			return new ModelAndView("redirect:/video_"+ videoId +".html");
		}
		mav.addObject("course", course);

		if (null == course) {
			errList.add("invalid course id : " + videoId);
		} else { // course is not null
			
			mav.addObject("buyFlag", CourseHelper.getInstance().isBuyVideo(null==users?null:users.getId(), videoId));
			
			/**
			 * get chatroom id by course id
			 */
			String groupId="";
			KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setKey1(videoId.toString());
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COURSE_CHAT_GROUP.getCode());
			ResultDTO<List<KeyvalueDTO>> keyvalueResultDTO = systemService.queryKeyvalue(keyvalueQuery);
			errList = addList(errList, keyvalueResultDTO.getErrorMsg());
			List<KeyvalueDTO> keyvalueDTOs = keyvalueResultDTO.getModule();
			if(null!=keyvalueDTOs && keyvalueDTOs.size() == 1){
				groupId = keyvalueDTOs.get(0).getValue();
			}
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
			addPasswordModel(mav, request, getCurrentUrl(request));
		return mav;
	}
	
	
	@RequestMapping("/my/unexpired_card.html")
	public ModelAndView myUnexpiredCard(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cunexpiredCard");

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(32);
		courseQuery.setObjType(EnumObjectType.CARD_MODULE.getCode());

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse1(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("na", "unexpired_card");

		return mav;

	}

	
	
	
	@RequestMapping("/my/do_create_article.html")
	public ModelAndView doCreateArticle(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ArticleDTO articleDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateArticle");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		setCreateArticleObject(mav,errList);
		mav.addObject("na", "create_article");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				articleDTO.setCreater(usersDTO.getId());
				articleDTO.setCreateTime(System.currentTimeMillis()/1000);
				articleDTO.setUpdateTime(System.currentTimeMillis()/1000);
				articleDTO.setCuid(usersDTO.getId());
				articleDTO.setTid(usersDTO.getTid());
				articleDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
				articleDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
				articleDTO.setTcate(EnumArticleType.getByFilename(articleDTO.getUrl()).getCode());
				articleDTO.setDescr(getArticleDescr(articleDTO.getUrl()));
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(articleDTO,courseDTO,BeanHelper.getNullPropertyNames(articleDTO));				
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功，请耐心等待审核");
					return new ModelAndView("redirect:/my/article.html");
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_article.html")
	public ModelAndView doUpdateMyArticle(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ArticleDTO articleDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateArticle");
		
		errList = new ArrayList<String>();
		setCreateArticleObject(mav,errList);
		mav.addObject("na", "create_article");
		RyxUsersDTO usersDTO = getRyxUser();
		
		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				articleDTO.setCreater(usersDTO.getId());
				articleDTO.setUpdateTime(System.currentTimeMillis()/1000);
				articleDTO.setCuid(usersDTO.getId());
				articleDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
				articleDTO.setDescr(getArticleDescr(articleDTO.getUrl()));
				
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(articleDTO.getId());
				if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){
					articleDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
					auditRecordDTO.setObjId(articleDTO.getId());
					auditRecordDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
					auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					auditRecordDTO.setUserId(usersDTO.getId());
					auditRecordDTO.setCreater(usersDTO.getId());
					auditRecordDTO.setDescr("我更新课程，重新打开审核");
					ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
					errList.add(auditResult.getErrorMsg());
				}

				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(articleDTO,courseDTO,BeanHelper.getNullPropertyNames(articleDTO));				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");					
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	/***
	 * 线下课程
	 */

	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_offline.html")
	public ModelAndView createOffline(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateOffline");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateOfflineObject(mav,errList);		
		mav.addObject("createDTO",new OfflineDTO());
		return mav;		
	}
	
	
	@RequestMapping("/my/update_offline.html")
	public ModelAndView updateOffline(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateOffline");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateOfflineObject(mav,errList);		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	
	/**
	 * 我创建的
	 * @param courseQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/offline.html")
	public ModelAndView myOffline(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyOffline");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		
		/**
		 * 活动开始时间
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
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
			courseQuery.setEtstart(DateHelper.getTodayLong());
		}
		else if(EnumIntervalType.THREE_MONTH_INNER.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getMonthendLong(90));
		}
		
		/**
		 * 申请时间
		 */
		
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}		
		courseQuery.setCuid(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		//courseQuery.setFlag(EnumCourseType.OFFLINE_COURSE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListOfflineObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_offline");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/my/do_create_offline.html")
	public ModelAndView doCreateOffline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OfflineDTO offlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateOffline");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		setCreateOfflineObject(mav,errList);
		mav.addObject("na", "create_offline");
		try {
			
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				offlineDTO.setCreater(usersDTO.getId());
				offlineDTO.setCreateTime(System.currentTimeMillis()/1000);
				offlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
				offlineDTO.setCuid(usersDTO.getId());
				offlineDTO.setTid(usersDTO.getTid());
				offlineDTO.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
				
				offlineDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(offlineDTO,courseDTO,BeanHelper.getNullPropertyNames(offlineDTO));	
				//courseDTO.setFlag(EnumCourseType.OFFLINE_COURSE.getCode());
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功，请耐心等待审核");
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/my/do_ajax_follow_recruit.html")
	public void doAjaxAddCourseFavorite(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, String ids)
			throws UnsupportedEncodingException {
		
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		if(!StringHelper.isNullOrEmpty(ids)){
			String[] a =  ids.split(",");
			for(String string : a){
				RyxUserFollowCourseDTO userFollowCourseDTO = new RyxUserFollowCourseDTO();
				userFollowCourseDTO.setUserId(users.getId());
				userFollowCourseDTO.setCourseId(Long.parseLong(string));
				userFollowCourseDTO.setOtype(EnumObjectType.RECRUIT_MODULE.getCode());
				ResultDTO<Boolean> result = ryxCourseService.saveUserFollowCourse(userFollowCourseDTO);
				addList(errList, result.getErrorMsg());
			}
		}
		else{
			errList.add("请选择职位");
		}
		if(errList.size() == 0){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,StringHelper.list2HtmlString(errList));
		}

	}
	
	
	
	@RequestMapping("/my/do_ajax_apply_recruit.html")
	public void doAjaxApplyRecruit(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, String ids)
			throws UnsupportedEncodingException {
		
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		/*
		 * 判断简历是否存在
		 */
		ResultDTO<ResumeDTO> resultDTO = userService.queryResumeByUserId(users.getId());
		if(resultDTO.isSuccess() && null != resultDTO.getModule()){
			if(!StringHelper.isNullOrEmpty(ids)){
				String[] a =  ids.split(",");
				for(String string : a){
					KeyrvDTO dto = new KeyrvDTO();
					dto.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
					dto.setKey1(users.getId().toString());
					dto.setIdeleted(0);
					dto.setRkey(string);
					ResultDTO<Boolean> result = systemService.createOrUpdateKeyrv(dto);
					addList(errList, result.getErrorMsg());
				}
			}
			else{
				errList.add("请选择职位");
			}
		}
		else{
			errList.add("请在会员中心创建个人简历");
		}

		
		if(errList.size() == 0){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,StringHelper.list2HtmlString(errList));
		}

	}
	
	@RequestMapping("/my/do_update_offline.html")
	public ModelAndView doUpdateMyOffline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OfflineDTO offlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateOffline");
		
		errList = new ArrayList<String>();
		setCreateOfflineObject(mav,errList);
		mav.addObject("na", "create_offline");
		RyxUsersDTO usersDTO = getRyxUser();
		
		try {
			
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				offlineDTO.setCreater(usersDTO.getId());
				offlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
				offlineDTO.setCuid(usersDTO.getId());
				offlineDTO.setTid(usersDTO.getTid());
				offlineDTO.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
				
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(offlineDTO.getId());
				if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){	
					offlineDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					
					RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
					auditRecordDTO.setObjId(offlineDTO.getId());
					auditRecordDTO.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
					auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					auditRecordDTO.setUserId(usersDTO.getId());
					auditRecordDTO.setCreater(usersDTO.getId());
					auditRecordDTO.setDescr("我更新课程，重新打开审核");
					ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
					errList.add(auditResult.getErrorMsg());
				}
				
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(offlineDTO,courseDTO,BeanHelper.getNullPropertyNames(offlineDTO));				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");					
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	

	
	@RequestMapping("/my/create_online.html")
	public ModelAndView createOnline(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateOnline");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		OnlineDTO onlineDTO = new OnlineDTO();
		errList = new ArrayList<String>();		
		setCreateOnlineObject(mav,onlineDTO,errList);		
		mav.addObject("createDTO",new OnlineDTO());
		return mav;		
	}
	
	
	@RequestMapping("/my/update_online.html")
	public ModelAndView updateOnline(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateOnline");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		
		errList = new ArrayList<String>();		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		RyxCourseDTO courseDTO = result.getModule();
		OnlineDTO onlineDTO = new OnlineDTO();
		BeanUtils.copyProperties(courseDTO,onlineDTO,BeanHelper.getNullPropertyNames(courseDTO));
		mav.addObject("createDTO",onlineDTO);
		setCreateOnlineObject(mav,onlineDTO,errList);	
		errList = addList(errList, result.getErrorMsg());
		
		return mav;		
	}
	
	
	

	
	@RequestMapping("/my/follow_activity.html")
	public ModelAndView myFollowActivity(
			HttpServletRequest request, 
			RyxCourseQuery courseQuery, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowActivity");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());

		mav.addObject("follow", followCourseResult.getModule());

		mav.addObject("na", "my_follow_activity");


		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	@RequestMapping("/my/follow_offline.html")
	public ModelAndView myFollowOffline(
			HttpServletRequest request, 
			RyxCourseQuery courseQuery, 
			HttpServletResponse response,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowOffline");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
		errList = addList(errList, followCourseResult.getErrorMsg());

		mav.addObject("follow", followCourseResult.getModule());

		mav.addObject("na", "my_follow_offline");

		

		courseQuery = followCourseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	@RequestMapping("/my/ajax_my_follow_course.html")
	public void ajaxMyFollowCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		String price = courseQuery.getPrice();

		try {

			if (!StringHelper.isNullOrEmpty(price)) {
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

			courseQuery.setUserId(users.getId());
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			ResultDTO<RyxCourseQuery> followCourseResult = ryxCourseService.getMyFollowCourse(courseQuery);
			if (followCourseResult.isSuccess()) {
				writeAjax(response, true, "", followCourseResult.getModule().getList());
			} else {
				writeAjax(response, false, followCourseResult.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}
	@RequestMapping("/my/ajax_my_follow_teacher.html")
	public void ajaxMyFollowTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		try {

			teacherQuery.setUserId(users.getId());
			teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
			ResultDTO<RyxTeacherQuery> followTeacherResult = ryxTeacherService.getMyFollowTeacher(teacherQuery);
			if (followTeacherResult.isSuccess()) {
				writeAjax(response, true, "", followTeacherResult.getModule().getList());
			} else {
				writeAjax(response, false, followTeacherResult.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}
	}

	@RequestMapping("/list_online.html")
	public ModelAndView listOnline(HttpServletRequest request, 
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_online.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_online.html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_online.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistOnline");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setDisplay(1);
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());

		/**
		 * 二级类目
		 */
		if (null != courseQuery.getCategory()) {
			ResultDTO<RyxCategoryQuery> subcateResult = MetaHelper.getInstance().getCategoryByPid(courseQuery.getCategory());
			errList = addList(errList, subcateResult.getErrorMsg());
			mav.addObject("subcates", subcateResult.getModule().getList());
			String title = MetaHelper.getInstance().getCategoryById(courseQuery.getCategory()).getModule().getTitle();
			if(null != courseQuery.getSubcate()){
				title += "_" + MetaHelper.getInstance().getCategoryById(courseQuery.getSubcate()).getModule().getTitle();
			}
			mav.addObject("title", title + "_教育培训课程");
		}
		else{
			mav.addObject("title", "融资租赁培训课程");
		}


		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);

		return mav;

	}
	
	
	@RequestMapping("/list_online_favorable.html")
	public ModelAndView listOnlineFavorable(HttpServletRequest request, KeyvQuery keyvQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/clistOnlineFavorable");

		keyvQuery.setPageSize(DEFAULT_PAGE_SIZE);
		keyvQuery.setIdeleted(0);
		keyvQuery.setDisplay(1);
		keyvQuery.setType(EnumKeyValueType.KV_FAVROABLE_COURSE.getCode());
		
		ResultDTO<KeyvQuery> courseResult = MetaHelper.getInstance().queryKeyrv(keyvQuery);
		errList = addList(errList, courseResult.getErrorMsg());


		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("title", "金融培训教育在线课程");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseResult.getModule());

		return mav;

	}
	
	
	@RequestMapping("/my/online.html")
	public ModelAndView myOnline(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyOnline");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
				
		courseQuery.setCuid(users.getId());		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		List<Integer> flags = new ArrayList<>();
		//flags.add(EnumCourseType.MAIN_COURSE.getCode());
		//flags.add(EnumCourseType.SUB_COURSE.getCode());
		//courseQuery.setFlags(flags);
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListOnlineObject(mav, errList);
		
		/**
		 * 二级类目
		 */
		if (null != courseQuery.getCategory()) {
			ResultDTO<RyxCategoryQuery> subcateResult = MetaHelper.getInstance().getCategoryByPid(courseQuery.getCategory());
			errList = addList(errList, subcateResult.getErrorMsg());
			mav.addObject("subcates", subcateResult.getModule().getList());
		}

		mav.addObject("na", "my_online");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/my/set_course_series.html")
	public ModelAndView setCourseSeries(
			RyxCourseQuery courseQuery,
			Long courseId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/csetCourseSeries");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(courseId);
		errList = addList(errList, result.getErrorMsg());
		RyxCourseDTO courseDTO = result.getModule();
		if(!courseDTO.getCuid() .equals(users.getId())){
			throw new Exception("no permission");
		}
		
		
		/**
		 * 查询相关子课程
		 */
		courseQuery.setCategory(courseDTO.getCategory());
		courseQuery.setCuid(users.getId());		
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.SUB_COURSE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		
		/**
		 * 查询现有自课程
		 */
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setKey1(courseId.toString());
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		ResultDTO<List<String>> seriesResult = systemService.queryKeyrvRkey(keyrvQuery);
		errList = addList(errList, seriesResult.getErrorMsg());
		mav.addObject("seriesList", seriesResult.getModule());
		
		setListOnlineObject(mav, errList);
		
		mav.addObject("courseId", courseId);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/my/list_course_series.html")
	public ModelAndView listCourseSeries(
			RyxCourseQuery courseQuery,
			Long courseId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/clistCourseSeries");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(courseId);
		errList = addList(errList, result.getErrorMsg());
		RyxCourseDTO courseDTO = result.getModule();
		if(!courseDTO.getCuid() .equals(users.getId())){
			throw new Exception("no permission");
		}
		
		
		
		/**
		 * 查询现有自课程
		 */
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setKey1(courseId.toString());
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		keyrvQuery.setOrderBy("sort");
		ResultDTO<KeyrvQuery> seriesResult = systemService.queryKeyrv(keyrvQuery);
		errList = addList(errList, seriesResult.getErrorMsg());
		mav.addObject("query", seriesResult.getModule());
		
		setListOnlineObject(mav, errList);
		
		mav.addObject("courseId", courseId);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/my/do_create_online.html")
	public ModelAndView doCreateOnline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO onlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateOnline");
		RyxUsersDTO usersDTO = getRyxUser();
		errList = new ArrayList<String>();
		setCreateOnlineObject(mav,onlineDTO,errList);
		mav.addObject("na", "create_online");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				Double price = 0.0;
				Double oprice = 0.0;
				Long duration = 0L ;
				
				List<String> subErrorList = new ArrayList<String>();
				if(onlineDTO.getFlag() == EnumCourseType.MAIN_COURSE.getCode()){
					
					if(StringHelper.isNullOrEmpty(onlineDTO.getDescr())){
						errList.add("请输入课程简介以及预期达到的效果、收益");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getContent())){
						errList.add("请输入课程详细介绍");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getImageUrl())){
						errList.add("请上传海报、图片");
					}
					
					List<SubOnlineDTO> listSubOnline = onlineDTO.getListSubOnline();
					if(null != listSubOnline){
						for(SubOnlineDTO subOnline : listSubOnline){
							
							if(StringHelper.isNullOrEmpty(subOnline.getTitle())){
								subErrorList.add("请输入子课程标题");
							}
	//						if(StringHelper.isNullOrEmpty(subOnline.getDescr())){
	//							subErrorList.add("请输入子课程简介");
	//						}
							if(StringHelper.isNullOrEmpty(subOnline.getVid())){
								subErrorList.add("请上传视频文件");
							}
							if(StringHelper.isNullOrEmpty(subOnline.getDuration())){
								subErrorList.add("请输入课程时长(例如 22:06:59)");
							}
							if(null == subOnline.getPrice()){
								subErrorList.add("请输入子课程售卖价格");
							}
	//						if(null == subOnline.getOprice()){
	//							subErrorList.add("请输入子课程原价格");
	//						}
							
							price += null == subOnline.getPrice() ? 0.0 : subOnline.getPrice() ;
							oprice += null == subOnline.getOprice() ? 0.0 : subOnline.getOprice();
							duration += DateHelper.hhmmss2Second(subOnline.getDuration()); 
							
							  
							
						}
						mav.addObject("subErrorList", subErrorList);
					}
				}
				else{
					if(StringHelper.isNullOrEmpty(onlineDTO.getVid())){
						errList.add("请上传视频文件");
					}
					if(null ==onlineDTO.getPrice()){
						errList.add("请输入售卖价格");
					}
				}
				if(errList.size() == 0 && subErrorList.size() == 0){
					
					onlineDTO.setCreater(usersDTO.getId());
					onlineDTO.setCreateTime(System.currentTimeMillis()/1000);
					onlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
					onlineDTO.setCuid(usersDTO.getId());
					onlineDTO.setTid(usersDTO.getTid());
					onlineDTO.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
					onlineDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
					onlineDTO.setPrice(price);	 // 实际售卖价格
					onlineDTO.setDuration( DateHelper.secToTime(duration));
					onlineDTO.setOprice(price * 100/88); // 原价格
					RyxCourseDTO courseDTO = new RyxCourseDTO();
								
					BeanUtils.copyProperties(onlineDTO,courseDTO,BeanHelper.getNullPropertyNames(onlineDTO));				
					ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
					addList(errList, result.getErrorMsg());
					if (result.isSuccess()) {
						errList.add("创建线上课程成功，请耐心等待审核");
						onlineDTO.setId(result.getModule());
					}
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());			
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", usersDTO);
		return mav;

	}
	
	
	
	
	@RequestMapping("/c/view_auth_course.html")
	public ModelAndView viewAuthCourse(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/c/cviewAuthCourse");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setTstatus(EnumOrderStatus.ORG_PRESENT.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse2(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);


		return mav;

	}

	
	
	@RequestMapping("/my/do_update_online.html")
	public ModelAndView doUpdateMyOnline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO onlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateOnline");
		
		errList = new ArrayList<String>();
		setCreateOnlineObject(mav,onlineDTO,errList);
		mav.addObject("na", "create_online");
		RyxUsersDTO usersDTO = getRyxUser();
		
		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				/**
				 * $("#divSubVid").show();
				$("#divContent").hide();
				$("#divImageUrl").hide();
				$("#divRelatedCourse").hide();
				$("#divDescr").hide();
				 */
				if(onlineDTO.getFlag() == EnumCourseType.getMainCourse().getCode()){
					if(StringHelper.isNullOrEmpty(onlineDTO.getDescr())){
						errList.add("请输入课程简介以及预期达到的效果、收益");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getContent())){
						errList.add("请输入课程详细介绍");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getImageUrl())){
						errList.add("请上传海报、图片");
					}
				}
				else{
					if(StringHelper.isNullOrEmpty(onlineDTO.getVid())){
						errList.add("请上传视频");
					}
				}
			
				
				if(errList.size() == 0){
					onlineDTO.setCreater(usersDTO.getId());
					onlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
					onlineDTO.setCuid(usersDTO.getId());
					onlineDTO.setTid(usersDTO.getTid());
					onlineDTO.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
					
					ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(onlineDTO.getId());
					if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){
						onlineDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
						RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
						auditRecordDTO.setObjId(onlineDTO.getId());
						auditRecordDTO.setUserId(usersDTO.getId());
						auditRecordDTO.setCreater(usersDTO.getId());
						auditRecordDTO.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
						auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
						auditRecordDTO.setDescr("我更新课程，重新打开审核");
						ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
						errList.add(auditResult.getErrorMsg());
					}
	
					RyxCourseDTO courseDTO = new RyxCourseDTO();
					BeanUtils.copyProperties(onlineDTO,courseDTO,BeanHelper.getNullPropertyNames(onlineDTO));				
					ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
					errList.add(result.getErrorMsg());
					if (result.isSuccess()) {
						errList.add("更新线上课程成功，请耐心等待审核");
						if(onlineDTO.getFlag() == EnumCourseType.getSubCourse().getCode()){
							updateMainCourseBySubCourse(courseDTO);
						}
					}
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/my/ajax_get_audit_record.html")
	public void ajaxGetAuditRecord(Long objId,Integer objType, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		Long userId = getRyxUser().getId();
		ResultDTO<RyxAuditRecordQuery> result = MetaHelper.getInstance().getAuditRecord(userId,objId,objType);
		if (result.isSuccess()) {
			writeAjax(response, true, "", result.getModule().getList());
		} else {
			writeAjax(response, false, result.getErrorMsg());
		}
	}
	
	
	
	@RequestMapping("/offline_{id}.html")
	public ModelAndView offline(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/offline_"+ id +".html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/offline_"+ id +".html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/offline_"+ id +".html");
			}
		}
		

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/coffline");

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
	
	
	@RequestMapping("/forum_{id}.html")
	public ModelAndView forum_(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cforum");

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
	
	
	@RequestMapping("/i_{id}.html")
	public ModelAndView info(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cinfo1");

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
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

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
	
	
	
	
	@RequestMapping("/b_{id}.html")
	public ModelAndView book(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cbook");

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
			mav.addObject("title", (null == course.getCategory() || null ==MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

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
	 * 我购买的
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/offline_course.html")
	public ModelAndView myOfflineCourse(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cofflineCourse");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setUserId(users.getId());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyOfflineCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		
		courseQuery = courseResult.getModule();
		mav.addObject("course", courseQuery.getList());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);
		mav.addObject("na", "offline");

		return mav;

	}
	
	
	/**
	 * 招聘
	 */
	

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_recruit.html")
	public ModelAndView createRecruit(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateRecruit");
		errList = new ArrayList<String>();		
		setCreateRecruitObject(mav,errList);		
		mav.addObject("createDTO",new RecruitDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_recruit.html")
	public ModelAndView updateRecruit(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateRecruit");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateRecruitObject(mav,errList);		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	

	
	
	
	@RequestMapping("/my/recruit.html")
	public ModelAndView myRecruit(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyRecruit");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		
		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
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
			courseQuery.setEtstart(DateHelper.getTodayLong());
		}
				
		courseQuery.setCuid(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListRecruitObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_recruit");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/my/view_recruit_apply_{rkey}.html")
	public ModelAndView myViewRecruit(
			@PathVariable String rkey ,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cviewRecruitApply");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		KeyrvQuery query = new KeyrvQuery();
		query.setRkey(rkey);
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setType(EnumKeyRelatedValueType.KV_APPLY_RECRUIT.getCode());
		ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/my/do_create_recruit.html")
	public ModelAndView doCreateRecruit(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RecruitDTO recruitDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateRecruit");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		setCreateRecruitObject(mav,errList);
		mav.addObject("na", "create_recruit");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				recruitDTO.setCreater(usersDTO.getId());
				recruitDTO.setCreateTime(System.currentTimeMillis()/1000);
				recruitDTO.setUpdateTime(System.currentTimeMillis()/1000);
				recruitDTO.setCuid(usersDTO.getId());
				recruitDTO.setTid(usersDTO.getTid());
				recruitDTO.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
				recruitDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
				
				AddressDTO addressDTO = UserHelper.getInstance().getAddressById(Long.parseLong(recruitDTO.getAddress()));
				if(null!=addressDTO){
					recruitDTO.setDistrict1(addressDTO.getProvince());
					recruitDTO.setDistrict2(addressDTO.getCity());
					recruitDTO.setDistrict3(addressDTO.getArea());
				}
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(recruitDTO,courseDTO,BeanHelper.getNullPropertyNames(recruitDTO));				
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功，请耐心等待审核");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_recruit.html")
	public ModelAndView doUpdateMyRecruit(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RecruitDTO recruitDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateRecruit");
		
		errList = new ArrayList<String>();
		setCreateRecruitObject(mav,errList);
		mav.addObject("na", "create_recruit");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				recruitDTO.setCreater(usersDTO.getId());
				recruitDTO.setUpdateTime(System.currentTimeMillis()/1000);
				recruitDTO.setCuid(usersDTO.getId());
				AddressDTO addressDTO = UserHelper.getInstance().getAddressById(Long.parseLong(recruitDTO.getAddress()));
				if(null!=addressDTO){
					recruitDTO.setDistrict1(addressDTO.getProvince());
					recruitDTO.setDistrict2(addressDTO.getCity());
					recruitDTO.setDistrict3(addressDTO.getArea());
				}
				recruitDTO.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(recruitDTO.getId());
				if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){
					recruitDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
					auditRecordDTO.setObjId(recruitDTO.getId());
					auditRecordDTO.setUserId(usersDTO.getId());
					auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					auditRecordDTO.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
					auditRecordDTO.setCreater(usersDTO.getId());
					auditRecordDTO.setDescr("更新，重新打开审核");
					ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
					errList.add(auditResult.getErrorMsg());
				}

				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(recruitDTO,courseDTO,BeanHelper.getNullPropertyNames(recruitDTO));				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	/**
	 * 商业服务
	 */
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_commerce.html")
	public ModelAndView createCommerce(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateCommerce");
		errList = new ArrayList<String>();		
		setCreateCommerceObject(mav,errList);		
		mav.addObject("createDTO",new CommerceDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_commerce.html")
	public ModelAndView updateCommerce(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateCommerce");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateCommerceObject(mav,errList);		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	

	
	
	
	@RequestMapping("/my/commerce.html")
	public ModelAndView myCommerce(
			RyxCourseQuery courseQuery,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyCommerce");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		
		/**
		 * 时间 > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == courseQuery.getInterval()){
			courseQuery.setTtstart(DateHelper.getTodayLong());
			courseQuery.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == courseQuery.getInterval()){
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
			courseQuery.setEtstart(DateHelper.getTodayLong());
		}
				
		courseQuery.setCuid(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);

		setListCommerceObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_commerce");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}

	
	@RequestMapping("/my/do_create_commerce.html")
	public ModelAndView doCreateCommerce(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") CommerceDTO commerceDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateCommerce");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		setCreateCommerceObject(mav,errList);
		mav.addObject("na", "create_commerce");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				commerceDTO.setCreater(usersDTO.getId());
				commerceDTO.setCreateTime(System.currentTimeMillis()/1000);
				commerceDTO.setUpdateTime(System.currentTimeMillis()/1000);
				commerceDTO.setCuid(usersDTO.getId());
				commerceDTO.setTid(usersDTO.getTid());
				commerceDTO.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
				commerceDTO.setStatus(EnumAuditStatus.UNAUDITED.getCode());
				
				AddressDTO addressDTO = UserHelper.getInstance().getAddressById(Long.parseLong(commerceDTO.getAddress()));
				if(null!=addressDTO){
					commerceDTO.setDistrict1(addressDTO.getProvince());
					commerceDTO.setDistrict2(addressDTO.getCity());
					commerceDTO.setDistrict3(addressDTO.getArea());
				}
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(commerceDTO,courseDTO,BeanHelper.getNullPropertyNames(commerceDTO));				
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功，请耐心等待审核");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_commerce.html")
	public ModelAndView doUpdateMyCommerce(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") CommerceDTO commerceDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateCommerce");
		
		errList = new ArrayList<String>();
		setCreateCommerceObject(mav,errList);
		mav.addObject("na", "create_commerce");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				commerceDTO.setCreater(usersDTO.getId());
				commerceDTO.setUpdateTime(System.currentTimeMillis()/1000);
				commerceDTO.setCuid(usersDTO.getId());
				commerceDTO.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
				AddressDTO addressDTO = UserHelper.getInstance().getAddressById(Long.parseLong(commerceDTO.getAddress()));
				if(null!=addressDTO){
					commerceDTO.setDistrict1(addressDTO.getProvince());
					commerceDTO.setDistrict2(addressDTO.getCity());
					commerceDTO.setDistrict3(addressDTO.getArea());
				}
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(commerceDTO.getId());
				if(EnumAuditStatus.UNAUDITED.getCode() != courseResult.getModule().getStatus()){
					commerceDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					RyxAuditRecordDTO auditRecordDTO = new RyxAuditRecordDTO();
					auditRecordDTO.setObjId(commerceDTO.getId());
					auditRecordDTO.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
					auditRecordDTO.setUserId(usersDTO.getId());
					auditRecordDTO.setStatus(EnumAuditStatus.REUNAUDITED.getCode());
					auditRecordDTO.setCreater(usersDTO.getId());
					auditRecordDTO.setDescr("更新，重新打开审核");
					ResultDTO<Boolean> auditResult = ryxCourseService.createAuditRecord(auditRecordDTO);
					errList.add(auditResult.getErrorMsg());
				}

				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(commerceDTO,courseDTO,BeanHelper.getNullPropertyNames(commerceDTO));				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
//	@RequestMapping("/online_{id}.html")
//	public ModelAndView online_cookie(@PathVariable Long id,
//			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
//			throws Exception {
//		
//		ModelAndView mav = new ModelAndView("redirect:/offline_" + courseId + ".html");
//		return mav;
//	}
	
	@RequestMapping("/online_{courseId}.html")
	public ModelAndView online(@PathVariable Long courseId,
			RyxEvaluQuery query,
			String from,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {





		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/conline");

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		
		if(null == course || null == course.getObjType()){
			throw new Exception("invalid obj type");
		}
		else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/offline_" + courseId + ".html");
			return mav;
		}
		else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/article_" + courseId + ".html");
			return mav;
		}
		else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/video_" + courseId + ".html");
			return mav;
		}
		else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/activity_" + courseId + ".html");
			return mav;
		}
		else{
			
			if(StringHelper.isMoblie(request)){
				if(ConstHelper.isPreEnvironment()){
					return new ModelAndView("redirect:http://pm.ryx365.com/m/online_"+ courseId +".html");
				}
				else if(ConstHelper.isFormalEnvironment()){
					return new ModelAndView("redirect:http://m.ryx365.com/m/online_"+ courseId +".html");
				}
				else{
					return new ModelAndView("redirect:http://am.ryx365.com/m/online_"+ courseId +".html");
				}
			}
			
			 
			mav.addObject("course", course);
			
			if(null == course.getFlag()){
				throw new Exception("INVALID FLAG");
			}
			
			if(course.getFlag() != EnumCourseType.MAIN_COURSE.getCode()){
				KeyrvQuery keyrvQuery = new KeyrvQuery();
				keyrvQuery.setRkey(courseId.toString());
				keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
				ResultDTO<KeyrvQuery> keyrvResult =  systemService.queryKeyrv(keyrvQuery);
				if(keyrvResult.isSuccess() && null != keyrvResult.getModule() ){
					List<KeyrvDTO> list = keyrvResult.getModule().getList();
					if(null != list && list.size() >0 ){							
						mav = new ModelAndView("redirect:online_" + courseId + "_"+ list.get(0).getKey1() +".htm");
						
					}
					else{
						mav = new ModelAndView("redirect:online_" + courseId + "_0.htm");
					}					
				}
				else{
					mav = new ModelAndView("redirect:online_" + courseId + "_0.htm");
				}
				return mav;
			}

			
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
				Boolean buyFlag = isBuyOnline(course, users.getId(), null, null); 
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


			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			

			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		mav.addObject("from", from);
		return mav;
	}
	
	
	@RequestMapping("/e_{id}.html")
	public ModelAndView ecourse(@PathVariable Long id,
			RyxEvaluQuery query,
			String from,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws Exception {

		RyxUsersDTO users = getRyxUser();
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/cecourse");
		
		String server = request.getServerName();
		if (!StringHelper.isNullOrEmpty(server) && server.indexOf("m.ryx365.com") >= 0) {
			mav = new ModelAndView("/ryx/m/mecourse");
		}
		

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		mav.addObject("course", course);
		if(null == course || null == course.getObjType()){
			throw new Exception("invalid obj type");
		}
		else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/offline_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/article_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/video_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/activity_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/online_" + id + ".html");
			return mav;
		}
		else if (EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode() == course.getObjType()) {
			
			

			
			/**
			 * 评价
			 */
			query.setObjId(id);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setIdeleted(0);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxEvaluQuery> queryResult = MetaHelper.getInstance().queryEvalu(query);
			mav.addObject("query", queryResult.getModule());
			

			

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(id);
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null == MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() 
					? "":MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			
			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);


			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			

			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		
		Boolean isWeixinExplorer = isWeixinExplorer(request);		
		if(isWeixinExplorer){
			setCourseWeixinSpread(course,mav,request);
		}
		
		mav.addObject("from", from);
		return mav;
	}
	
	
	
	@RequestMapping("/robot.txt")
	public void robot(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {
	
		if(!ConstHelper.isFormalEnvironment()){
			response.getWriter().write("Disallow:360spider");
		}		
	}
	
	@RequestMapping("/baidu_sitemap.txt")
	public void baiduSiteMap(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		
		sBuffer.append("http://www.ryx365.com/list_online.html\r\n");
		sBuffer.append("http://www.ryx365.com/list_offline.html\r\n");
		sBuffer.append("http://www.ryx365.com/list_personal.html\r\n");
		sBuffer.append("http://www.ryx365.com/list_org.html\r\n");
		sBuffer.append("http://www.ryx365.com/list_info.html\r\n");		
		sBuffer.append("http://www.ryx365.com/default.html\r\n");
		
		response.getWriter().write(sBuffer.toString());
		
	}
	
	
	
	@RequestMapping("/online_{pageIndex}_{pageSize}.txt")
	public void baiduSiteMapOnline(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer pageIndex,
			@PathVariable Integer pageSize,
			RedirectAttributes rt) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		RyxCourseQuery query = new RyxCourseQuery();
		query.setIdeleted(0);
		query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		query.setDisplay(1);
		query.setPageSize(pageSize);
		query.setCurrentPage(pageIndex);
		
		query.setOrderBy("id");
		query.setSooort("desc");
		List<RyxCourseDTO> list1 = ryxCourseService.queryCourse(query).getModule().getList();
		for(RyxCourseDTO course : list1){
			sBuffer.append("http://www.ryx365.com/online_"+ course.getId() +".html\r\n");
		}
		
		response.getWriter().write(sBuffer.toString());		
	}
	
	
	@RequestMapping("/offline_{pageIndex}_{pageSize}.txt")
	public void baiduSiteMapOffline(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer pageIndex,
			@PathVariable Integer pageSize,
			RedirectAttributes rt) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		RyxCourseQuery query = new RyxCourseQuery();
		query.setIdeleted(0);
		query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		query.setDisplay(1);
		query.setPageSize(pageSize);
		query.setCurrentPage(pageIndex);
		
		query.setOrderBy("id");
		query.setSooort("desc");
		List<RyxCourseDTO> list1 = ryxCourseService.queryCourse(query).getModule().getList();
		for(RyxCourseDTO course : list1){
			sBuffer.append("http://www.ryx365.com/offline_"+ course.getId() +".html\r\n");
		}
		
		response.getWriter().write(sBuffer.toString());		
	}
	
	
	@RequestMapping("/info_{pageIndex}_{pageSize}.txt")
	public void baiduSiteMapInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer pageIndex,
			@PathVariable Integer pageSize,
			RedirectAttributes rt) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		RyxCourseQuery query = new RyxCourseQuery();
		query.setIdeleted(0);
		query.setObjType(EnumObjectType.INFO_MODULE.getCode());
		query.setDisplay(1);
		query.setPageSize(pageSize);
		query.setCurrentPage(pageIndex);
		
		query.setOrderBy("id");
		query.setSooort("desc");
		List<RyxCourseDTO> list1 = ryxCourseService.queryCourse(query).getModule().getList();
		for(RyxCourseDTO course : list1){
			sBuffer.append("http://www.ryx365.com/i_"+ course.getId() +".html\r\n");
		}
		
		response.getWriter().write(sBuffer.toString());		
	}
	
	
	
	@RequestMapping("/teacher_{pageIndex}_{pageSize}.txt")
	public void baiduSiteMapTeacher(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer pageIndex,
			@PathVariable Integer pageSize,
			RedirectAttributes rt) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		RyxTeacherQuery query = new RyxTeacherQuery();
		query.setIdeleted(0);
		query.setDisplay(1);
		query.setPageSize(pageSize);
		query.setCurrentPage(pageIndex);
		
		query.setOrderBy("id");
		query.setSooort("desc");
		List<RyxTeacherDTO> list1 = ryxTeacherService.queryTeacher(query).getModule().getList();
		for(RyxTeacherDTO course : list1){
			sBuffer.append("http://www.ryx365.com/"+ course.getNick()+".html\r\n");
		}
		
		response.getWriter().write(sBuffer.toString());		
	}
	
	
	@RequestMapping("/MP_verify_7KTIGlbYVqSlI5MH.txt")
	public void MP_verify_7KTIGlbYVqSlI5MH(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {
		response.getWriter().write("7KTIGlbYVqSlI5MH");
	}
	
	
	@RequestMapping("/MP_verify_aBcjEsu2zp0B9pKo.txt")
	public void MP_verify_aBcjEsu2zp0B9pKo(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {
		response.getWriter().write("aBcjEsu2zp0B9pKo");
	}
	
	
}
