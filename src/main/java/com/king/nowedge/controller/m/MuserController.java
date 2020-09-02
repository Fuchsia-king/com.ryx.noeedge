package com.king.nowedge.controller.m;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumCourseType;
import com.king.nowedge.dto.enums.EnumKeyRelatedValueType;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.enums.EnumTeacherType;
import com.king.nowedge.dto.ryx.RyxApplyDTO;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxMessageDTO;
import com.king.nowedge.dto.ryx.RyxObjectLimitDTO;
import com.king.nowedge.dto.ryx.RyxSmallCourseDTO;
import com.king.nowedge.dto.ryx.RyxSmallMessageDTO;
import com.king.nowedge.dto.ryx.RyxSmallTeacherDTO;
import com.king.nowedge.dto.ryx.RyxSmallVideDTO;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUserEcourseDTO;
import com.king.nowedge.dto.ryx.RyxUserMessageDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxApplyQuery;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.dto.ryx.query.RyxMessageQuery;
import com.king.nowedge.dto.ryx.query.RyxTeacherQuery;
import com.king.nowedge.dto.ryx.query.RyxUserEcourseQuery;
import com.king.nowedge.helper.BeanHelper;
import com.king.nowedge.helper.CourseHelper;
import com.king.nowedge.helper.DateHelper;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.helper.UserHelper;


@Controller
public class MuserController extends BaseController {

	
	@RequestMapping("/m/my/coupon.html")
	public ModelAndView myCoupon(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/mcoupon");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("na", "my_coupon");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/m/my/setting.html")
	public ModelAndView mySetting(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/msetting");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/m/my/recharge.html")
	public ModelAndView myRecharge(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/mrecharge");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/m/my/change_pay_password.html")
	public ModelAndView changePayPassword(String w, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mchangePayPassword");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

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
	@RequestMapping("/m/list_teacher.html")
	public ModelAndView listTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistTeacher");		
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		teacherQuery.setOrderBy("sort");
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		mav.addObject("query", result.getModule());
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("名家讲堂","名家讲堂【融易学金融学院】",mav,request);
		}
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		return mav ;
		
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param teacherQuery
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/m/list_org.html")
	public ModelAndView listOrg(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistOrg");		
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(EnumTeacherType.ORG.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		mav.addObject("query", result.getModule());
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("合作机构","合作机构【融易学金融学院】",mav,request);
		}
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		return mav ;
		
	}
	
	
	@RequestMapping("/m/ajax_list_teacher.html")
	public void ajaxListTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
			
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);
		teacherQuery.setIdeleted(0);
		teacherQuery.setOrderBy("sort");
		teacherQuery.setDisplay(1);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		
		if(result.isSuccess()){
			List<RyxSmallTeacherDTO> list = new ArrayList<RyxSmallTeacherDTO>();
			if(null != result.getModule().getList()){
				for(RyxTeacherDTO teacherDTO : (List<RyxTeacherDTO>)result.getModule().getList()){
					RyxSmallTeacherDTO smallTeacherDTO = new RyxSmallTeacherDTO();
					BeanUtils.copyProperties(teacherDTO, smallTeacherDTO, BeanHelper.getNullPropertyNames(teacherDTO));
					smallTeacherDTO.setCcnt(MetaHelper.getInstance().getCourseCountByTeacher(teacherDTO.getId()));
					smallTeacherDTO.setEval(StringHelper.double2Integer(MetaHelper.getInstance().getTeacherEvalueScorePersent(teacherDTO.getUid())));
					list.add(smallTeacherDTO);
				}
			}
			writeAjax(response, true,list);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}

		
	}
	
	
	
	@RequestMapping("/m/ajax_list_org.html")
	public void ajaxListOrg(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
			
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(EnumTeacherType.ORG.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		
		if(result.isSuccess()){
			List<RyxSmallTeacherDTO> list = new ArrayList<RyxSmallTeacherDTO>();
			if(null != result.getModule().getList()){
				for(RyxTeacherDTO teacherDTO : (List<RyxTeacherDTO>)result.getModule().getList()){
					RyxSmallTeacherDTO smallTeacherDTO = new RyxSmallTeacherDTO();
					BeanUtils.copyProperties(teacherDTO, smallTeacherDTO, BeanHelper.getNullPropertyNames(teacherDTO));
					smallTeacherDTO.setCcnt(MetaHelper.getInstance().getCourseCountByTeacher(teacherDTO.getId()));
					smallTeacherDTO.setEval(StringHelper.double2Integer(MetaHelper.getInstance().getTeacherEvalueScorePersent(teacherDTO.getUid())));
					list.add(smallTeacherDTO);
				}
			}
			writeAjax(response, true,list);
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
	@RequestMapping("/m/list_teacher_{category}.html")
	public ModelAndView listTeacher(
			@PathVariable Integer category,
			HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/m/mlistTeacher");		
		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setFlag(0);
		teacherQuery.setCategory(category);
		return listTeacher(request, teacherQuery, response, rt);
		
	}
	
	
	@RequestMapping("/m/teacher_{id}.html")
	public ModelAndView teacher(@PathVariable Long id, HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/m/mteacherDetail");
		RyxUsersDTO users = getRyxUser();

		RyxTeacherDTO teacherDTO = UserHelper.getInstance().getTeacherById(id);
		if(null == teacherDTO){
			return new ModelAndView("redirect:/default.html"); 
		}
		
		
		mav.addObject("teacher", teacherDTO);
		mav.addObject("title", teacherDTO.getNickname() + teacherDTO.getTags());
		RyxCourseQuery courseQuery = new RyxCourseQuery();
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
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		if(isWeixinExplorer){
			setCourseWeixinSpread("名家讲堂-" +  teacherDTO.getNickname(),teacherDTO.getTags(),mav,request);
		}
		mav.addObject("na", "index");
		mav.addObject("errList", errList);
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		mav.addObject("loginUsers", users);
		return mav;

	}
	
	
	@RequestMapping("/m/my/online.html")
	public ModelAndView myOnlineCourse(HttpServletRequest request,
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyOnline");

//		courseQuery.setUserId(users.getId());
//		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
//		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
//		courseQuery.setTnow(System.currentTimeMillis() / 1000);
//
//		ResultDTO<CourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse2(courseQuery);
//		errList = addList(errList, courseResult.getErrorMsg());
//
//		courseQuery = courseResult.getModule();
//		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("na", "my_online");

		return mav;

	}
	
	
	
	@RequestMapping("/m/my/ajax_online.html")
	public void myAjaxOnlineCourse(HttpServletRequest request,
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();


		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);

		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse2(courseQuery);
		
		if(courseResult.isSuccess()){
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			if(null != courseResult.getModule().getList()){
				for(RyxObjectLimitDTO limitDTO : (List<RyxObjectLimitDTO>)courseResult.getModule().getList()){
					RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(limitDTO.getMoid());
					RyxSmallCourseDTO smallCourseDTO = new RyxSmallCourseDTO();
					BeanUtils.copyProperties(courseDTO, smallCourseDTO, BeanHelper.getNullPropertyNames(courseDTO));
					smallCourseDTO.setScnt(CourseHelper.getInstance().getKeyrvCount(courseDTO.getId().toString()
							,EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode()));
					list.add(smallCourseDTO);
				}
			}
			writeAjax(response, true,list);
		}
		else{
			writeAjax(response, false,courseResult.getErrorMsg());
		}
		

	}
	//fjy     ajax获取收藏课程
	@RequestMapping("/m/my/ajax_followonline.html")
	public void myAjaxFollowCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		List<Integer> objTypes = new ArrayList<>();
		objTypes.add(EnumObjectType.ONLINE_MODULE.getCode());
		objTypes.add(EnumObjectType.OFFLINE_MODULE.getCode());
		objTypes.add(EnumObjectType.VIDEO_MODULE.getCode());
		courseQuery.setObjTypes(objTypes);
		writeAjax(response, true,MetaHelper.getInstance().getSmallCourse(courseQuery));
	}
	
	
	@RequestMapping("/m/my/message.html")
	public ModelAndView myMessage(HttpServletRequest request,
			RyxMessageQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyMessage");

		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setIdeleted(0);
		query.setOrderBy("lmodified");
		query.setSooort("desc");

		ResultDTO<RyxMessageQuery> result = ryxUserService.queryMyMessage(query);
		errList = addList(errList, result.getErrorMsg());

		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		
		mav.addObject("na", "my_message");

		return mav;

	}
//	fjy ajax 我的消息
	@RequestMapping("/m/my/ajax_message.html")
	public void myAjaxMessage(HttpServletRequest request,
			RyxMessageQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();


		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setIdeleted(0);
		query.setOrderBy("lmodified");
		query.setSooort("desc");

		writeAjax(response, true,MetaHelper.getInstance().getSmallMessag(query));

	}
//	fjy ajax 删除我的消息
	@RequestMapping("/m/my/ajax_delete_message.html")
	public void myAjaxDeleteMessage(HttpServletRequest request,
			RyxUserMessageDTO dto, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		dto.setUserId(users.getId());
		
		ResultDTO<Boolean> result = ryxUserService.deleteUserMessageByUserIdAndMsgId(dto);
		
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
		
	}
	
	@RequestMapping("/m/my/message_{id}.html")
	public ModelAndView myMessage(
			@PathVariable Long id, 
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmessageDetail");
		
		ResultDTO<RyxMessageDTO> result = ryxUserService.getMessageById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("course", result.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		

		return mav;

	}
	
	
	
	
	

	@RequestMapping("/m/my/spread.html")
	public ModelAndView mySpread(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		if(UserHelper.getInstance().isTeacher(users) || UserHelper.getInstance().isPartner(users)){
			new ModelAndView("redirect:/m/my/vspread.html");
		}
		ModelAndView mav = new ModelAndView("/ryx/m/my/mspread");

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	/**
	 * 普通用户推广
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/m/my/vspread.html")
	public ModelAndView myvSpread(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		if(UserHelper.getInstance().isUser(users)){
			new ModelAndView("redirect:/m/my/spread.html");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/m/my/mvspread");

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
	@RequestMapping("/m/my/follow_course.html")
	public ModelAndView myFollowCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mfollowCourse");

		mav.addObject("loginUsers", users);

		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
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
	
	
	
	
	private List<Integer> getMyunexpiredCategory(Long userId){
		List<Integer> list = new ArrayList<Integer>();
		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setUserId(userId);
		courseQuery.setPageSize(Integer.MAX_VALUE);
		courseQuery.setObjType(EnumObjectType.CARD_MODULE.getCode());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse1(courseQuery);
		for(RyxCourseDTO course : (List<RyxCourseDTO>)courseResult.getModule().getList()){
			list.add(course.getCategory());
		}
		return list ;
	}
	
	
	@RequestMapping("/m/my/vip_course.html")
	public ModelAndView myVipCourse(HttpServletRequest request,
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyVipOnline");

		/**
		 * 获取我没有过期的课程卡
		 */
		List<Integer> categorys = getMyunexpiredCategory(users.getId());
		mav.addObject("categorys", categorys);
		if(null != categorys && categorys.size()>0){
			courseQuery.setCategorys(categorys);
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
			errList = addList(errList, courseResult.getErrorMsg());
			courseQuery = courseResult.getModule();
		}
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_viponline");

		return mav;

	}
	
	
	@RequestMapping("/m/my/ajax_vip_course.html")
	public void myAjaxVipCourse(HttpServletRequest request,
			RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		/**
		 * 获取我没有过期的课程卡
		 */
		List<Integer> categorys = getMyunexpiredCategory(users.getId());
		ResultDTO<RyxCourseQuery> courseResult = null;
		if(null != categorys && categorys.size()>0){
			courseQuery.setCategorys(categorys);
			courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
			courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());	
			courseResult = ryxCourseService.queryCourse(courseQuery);
			courseQuery = courseResult.getModule();
		}
		
		if(courseResult.isSuccess() || null == courseResult){
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			if(null != courseResult.getModule().getList()){
				for(RyxCourseDTO courseDTO : (List<RyxCourseDTO>)courseResult.getModule().getList()){
					RyxSmallCourseDTO smallCourseDTO = new RyxSmallCourseDTO();
					BeanUtils.copyProperties(courseDTO, smallCourseDTO, BeanHelper.getNullPropertyNames(courseDTO));
					smallCourseDTO.setScnt(CourseHelper.getInstance().getKeyrvCount(courseDTO.getId().toString()
							,EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode()));
					list.add(smallCourseDTO);
				}
			}
			writeAjax(response, true,list);
		}
		else{
			writeAjax(response, false,courseResult.getErrorMsg());
		}
		
	}
	
	
	@RequestMapping("/m/my/offline.html")
	public ModelAndView myOffline(HttpServletRequest request,
			RyxApplyQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyOffline");

		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
		query = result.getModule();
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_offline");

		return mav;

	}
	
	
	@RequestMapping("/m/my/video.html")
	public ModelAndView myVideo(HttpServletRequest request,
			RyxCourseQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyVideo");

		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		query.setDisplay(1);
		query.setIdeleted(0);
		
		ResultDTO<RyxCourseQuery> result = ryxCourseService.queryMyVideo(query);
		
		List<RyxSmallVideDTO> list = new ArrayList<RyxSmallVideDTO>();
			
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
				list.add(smallVideDTO);
			}
		}	
		query = result.getModule();
		query.setList(list);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_video");

		return mav;

	}
	
	
	@RequestMapping("/m/my/ecourse.html")
	public ModelAndView myEcourse(HttpServletRequest request,
			RyxUserEcourseQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mmyEcourse");
		mav.addObject("list", UserHelper.getInstance().getUserEcourseByUserId(users.getId()));
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_ecourse");

		return mav;

	}
	
	
	@RequestMapping("/m/my/ajax_offline.html")
	public void myAjaxOffline(HttpServletRequest request,
			RyxApplyQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
		if(result.isSuccess()){
			List<RyxSmallCourseDTO> list = new ArrayList<RyxSmallCourseDTO>();
			if(null != result.getModule().getList()){
				for(RyxApplyDTO applyDTO : (List<RyxApplyDTO>)result.getModule().getList() ){
					RyxSmallCourseDTO smallCourseDTO = new RyxSmallCourseDTO();
					RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(applyDTO.getOid());
					BeanUtils.copyProperties(courseDTO, smallCourseDTO,BeanHelper.getNullPropertyNames(courseDTO));
					smallCourseDTO.setDistrict2(MetaHelper.getInstance()
							.getCityByCode(courseDTO.getDistrict2()).getValue());
					list.add(smallCourseDTO);
				}
			}

			writeAjax(response, true,list);
			
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/m/my/ajax_video.html")
	public void myAjaxVideo(HttpServletRequest request,
			RyxCourseQuery query, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();

		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
		query.setTtend(System.currentTimeMillis()/1000);
		query.setTtstart(System.currentTimeMillis()/1000);
		query.setEtstart(System.currentTimeMillis()/1000);
		query.setEtend(System.currentTimeMillis()/1000);
		query.setDisplay(1);
		query.setIdeleted(0);
		
		ResultDTO<RyxCourseQuery> result = ryxCourseService.queryMyVideo(query);
		if(result.isSuccess()){
			List<RyxSmallVideDTO> list = new ArrayList<RyxSmallVideDTO>();
			if(null != result.getModule().getList()){
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

			writeAjax(response, true,list);
			
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	
	
	
	@RequestMapping("/m/my/change_mobile.html")
	public ModelAndView changeMobile(String w, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mchangeMobile");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);

		return mav;

	}
	
	
	@RequestMapping("/m/my/change_password.html")
	public ModelAndView changePasswrodMryx(String w, HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO loginUsersDTO = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/m/my/mchangePassword");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", loginUsersDTO);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	
	
	
	@RequestMapping(value = "/m/search_teacher.html")
	public ModelAndView doSearchTeacher(HttpServletRequest request, RyxTeacherQuery teacherQuery,
			HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/msearchTeacher");
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		ResultDTO<RyxTeacherQuery> courseResult = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		mav.addObject("query", courseResult.getModule());

		
		mav.addObject("na", "teacher");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	
	
}
