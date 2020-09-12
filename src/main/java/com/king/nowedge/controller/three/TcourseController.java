package com.king.nowedge.controller.three;

import com.google.gson.Gson;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.query.base.KeyrvQuery;
import com.king.nowedge.query.ryx.RyxCategoryQuery;
import com.king.nowedge.query.ryx.RyxCourseOutlineQuery;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.query.ryx.RyxEvaluQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class TcourseController  extends BaseController {

	private static final Log logger = LogFactory.getLog(TcourseController.class);

	@RequestMapping("/curriculumList")
	public ModelAndView doCreateOrder(
			HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (StringHelper.isMoblie(request)) {
			if (ConstHelper.isPreEnvironment()) {
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_online3.html");
			} else if (ConstHelper.isFormalEnvironment()) {
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_online3.html");
			} else {
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_online3.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("member/curriculumList");

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setDisplay(1);
		if (StringHelper.isNullOrEmpty(courseQuery.getOrderBy())) {
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());

		/**
		 * 二级类目
		 */
		if (null != courseQuery.getCategory()) {
			ResultDTO<RyxCategoryQuery> subcateResult = MetaHelper.getInstance().getCategoryByPid(courseQuery.getCategory());
			errList = addList(errList, subcateResult.getErrorMsg());
			mav.addObject("subcates", subcateResult.getModule().getList());
			String title = MetaHelper.getInstance().getCategoryById(courseQuery.getCategory()).getModule().getTitle();
			if (null != courseQuery.getSubcate()) {
				title += "_" + MetaHelper.getInstance().getCategoryById(courseQuery.getSubcate()).getModule().getTitle();
			}
			mav.addObject("title", title + "_教育培训课程");
		} else {
			mav.addObject("title", "融资租赁培训课程");
		}

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);
		Gson gs = new Gson();
		String gsStr = gs.toJson(courseQuery.getList());
		mav.addObject("courseList", gsStr);
		mav.addObject("tatalPage", courseQuery.getTotalPage());
		mav.addObject("keyword", courseQuery.getKeyword());
		return mav;

	}

	/**
	 * 响应全部课程展示页点击单个课程跳转到详情页
	 *
	 * @param query
	 * @param from
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/curriculumDetails")
	public ModelAndView currentCourseDetail(String from,
											RyxEvaluQuery query,
											HttpServletRequest request, HttpServletResponse response,
											RedirectAttributes rt) throws Exception {

		ModelAndView mav = new ModelAndView("member/curriculumDetails");
		String courseId = request.getParameter("courseId");
		Long cid = Long.parseLong(courseId);
		RyxUsersDTO users = getRyxUser();
		//暂时写死一个user用于测试
		users = new RyxUsersDTO();
		users.setId(123L);
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(cid);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		//判断课程是否为线上售卖课程
		if (null == course || null == course.getObjType()) {
			throw new Exception("invalid obj type");
		} else if (EnumObjectType.OFFLINE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/offline_" + courseId + ".html");
			return mav;
		} else if (EnumObjectType.ARTICLE_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/article_" + courseId + ".html");
			return mav;
		} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/video_" + courseId + ".html");
			return mav;
		} else if (EnumObjectType.ACTIVITY_MODULE.getCode() == course.getObjType()) {
			mav = new ModelAndView("redirect:/activity_" + courseId + ".html");
			return mav;
		} else {
			//判断请求是否由移动端发起
			if (StringHelper.isMoblie(request)) {
				if (ConstHelper.isPreEnvironment()) {
					return new ModelAndView("redirect:http://pm.ryx365.com/m/online_" + courseId + ".html");
				} else if (ConstHelper.isFormalEnvironment()) {
					return new ModelAndView("redirect:http://m.ryx365.com/m/online_" + courseId + ".html");
				} else {
					return new ModelAndView("redirect:http://am.ryx365.com/m/online_" + courseId + ".html");
				}
			}
			//如果课程不是体系主课程
			if (course.getFlag() != EnumCourseType.MAIN_COURSE.getCode()) {
				KeyrvQuery keyrvQuery = new KeyrvQuery();
				keyrvQuery.setRkey(courseId);
				keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
				ResultDTO<KeyrvQuery> keyrvResult = systemService.queryKeyrv(keyrvQuery);
				if (keyrvResult.isSuccess() && null != keyrvResult.getModule()) {
					List<KeyrvDTO> list = keyrvResult.getModule().getList();
					if (null != list && list.size() > 0) {
						mav = new ModelAndView("redirect:online_" + courseId + "_" + list.get(0).getKey1() + ".htm");

					} else {
						mav = new ModelAndView("redirect:online_" + courseId + "_0.htm");
					}
				} else {
					mav = new ModelAndView("redirect:online_" + courseId + "_0.htm");
				}
				return mav;
			}
			mav.addObject("course", course);
			//获取课程编号下的课程列表
			List<RyxCourseDTO> subcourseList = new ArrayList<>();
			List<Boolean> isBuySubOnlineList = new ArrayList<>();
			List<KeyrvDTO> keyrvDTOList = new CourseHelper().getCourseSeries(cid);
			mav.addObject("keyrvDTOList",keyrvDTOList);
			for (KeyrvDTO k : keyrvDTOList) {
				RyxCourseDTO subcourse = new CourseHelper().getCourseById(StringHelper.string2Long(k.getRkey()));
				subcourseList.add(subcourse);
				isBuySubOnlineList.add(new CourseHelper().isBuySubOnline(users.getId(), subcourse.getId()));
			}
			mav.addObject("subcourseList", subcourseList);
			mav.addObject("presubcourse",subcourseList.get(0));
			Boolean isBuyMainOnline = new CourseHelper().isBuyMainOnline(users.getId(), cid);
			mav.addObject("isBuyMainOnline", isBuyMainOnline);
			Boolean isMustSeriesBuy = new CourseHelper().isMustSeriesBuy(cid);
			mav.addObject("isMustSeriesBuy", isMustSeriesBuy);
			mav.addObject("cid", cid);
			//获取课程的flag,0表示线上课程,1表示线下课程,否则抛出异常
			if (null == course.getFlag()) {
				throw new Exception("INVALID FLAG");
			}

			//评价
			query.setObjId(cid);
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			query.setIdeleted(0);
			query.setStatus(EnumAuditStatus.APPROVED.getCode());
			ResultDTO<RyxEvaluQuery> queryResult = MetaHelper.getInstance().queryEvalu(query);
			mav.addObject("query", queryResult.getModule());
			List<RyxEvaluDTO> redList = queryResult.getModule().getList();

			List<RyxUsersDTO> userTopicList = new ArrayList<>();
			for (int i=0;i<redList.size();i++) {
				RyxUsersDTO userTopic = new MetaHelper().getUserById(redList.get(i).getUserId()).getModule();
				userTopic.setUname(StringHelper.getFuzzyUsername(userTopic));
				if(!StringHelper.isNullOrEmpty(userTopic.getPath())){
					userTopic.setPath(ConstHelper.getDefaultImage());
				}
				userTopic.setDcode(redList.get(i).getDescr());
				userTopic.setStatus(redList.get(i).getStatus());
				userTopic.setAddress(redList.get(i).getLcreate()*1000+"");
				userTopicList.add(userTopic);
			}
			mav.addObject("userTopicList",userTopicList);
			if (null != users) {
				Boolean buyFlag = isBuyOnline(course, users.getId(), null, null);
				mav.addObject("buyFlag", buyFlag);
			}

	//		Long ts = System.currentTimeMillis();
	//		mav.addObject("ts", ts);
	//		logger.debug("Kd8jQHITMj" + course.getVid() + ts);
	//		mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));

			/**
			 * 更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(Long.parseLong(courseId));
			errList = addList(errList, updateViewCountResult.getErrorMsg());

			/**
			 * 设置标题
			 */
			mav.addObject("title", (null == course.getCategory() || null == MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule() ? "" : MetaHelper.getInstance().getCategoryById(course.getCategory()).getModule().getTitle()) + "_" + course.getTitle());

			RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(course.getCuid());
			mav.addObject("teacher", teacherDTO);
			String msg = request.getParameter("msg");

			mav.addObject("msg", msg);
			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		mav.addObject("from",from);
		return mav;
	}


	/**
	 * 从课程明细跳转到课程的视频列表的明细
	 * @param id
	 * @param mid
	 * @param video
	 * @param index
	 * @param shidai
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/playvideoPage")
	public ModelAndView playvideoPage(String id,String mid,String video,String index,String shidai,HttpServletRequest request,
									  HttpServletResponse response,RedirectAttributes rt) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/playvideoPage");
		id = request.getParameter("id");
		mid = request.getParameter("mid");
		index = request.getParameter("index");
		RyxUsersDTO users = getRyxUser();
		users = new RyxUsersDTO();
		users.setMobile("17328702821");
		users.setId(3429l);
		if(users==null){
			return new ModelAndView("redirct:/");
		}

		KeyrvDTO keyrvDTO = new KeyrvDTO();
		keyrvDTO.setRkey1(mid); // 主课程
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

//		keyrvDTO.setRkey(mid);
//		keyrvDTO.setValue(url);
//		keyrvDTO.setType(EnumKeyRelatedValueType.KV_CURRENT_COURSE_ZHANGJIE.getCode());
//		systemService.createOrUpdateKeyrv(keyrvDTO); // 记录最后观看记录 ；

		if(StringHelper.isMoblie(request) && !StringHelper.isNullOrEmpty(shidai)){
			return new ModelAndView("redirect:/m/online1_"+ mid +"_"+ id +".html?from=shidai");
		}

		if(StringHelper.isMoblie(request) && StringHelper.isNullOrEmpty(shidai)){
			return new ModelAndView("redirect:/m/online1_"+ mid +"_"+ id +".html");
		}

		errList = new ArrayList<String>();

		//子课程
		RyxCourseDTO course= CourseHelper.getInstance().getCourseById(Long.parseLong(id));
		mav.addObject("course", course);
		//获取IP开始
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("0:0:0:0:0:0:0:1")){
				ip="127.0.0.1";
			}
		}
		//获取ip结束
		/////////H5播放视频开始
		//获取用户ip
		mav.addObject("ip",ip);
		//获取userId
		String userId = "bb7813dfb6";
		mav.addObject("userId", userId);
		//获取secretId
//		String secretId = "Kd8jQHITMj";
		//获取时间戳
		Long times = System.currentTimeMillis();
		mav.addObject("times", times);
		//获取会员id
		String memberId = "1";//users.getId().toString();
		mav.addObject("memberId", memberId);

		String concated = "ts"+times+"userId"+userId+"videoId"+course.getVid()+"viewerId"+memberId+"viewerIp"+ip;
		String plain = "Kd8jQHITMj"+concated+"Kd8jQHITMj";
		String sign = DigestUtils.md5Hex(plain).toUpperCase();
		mav.addObject("newSign", sign);
		/////////H5播放视频结束

		//主课程
		RyxCourseDTO mcourse= CourseHelper.getInstance().getCourseById(Long.parseLong(mid));
		mav.addObject("mcourse", mcourse);

		//获取课程编号下的课程列表
		List<RyxCourseDTO> subcourseList = new ArrayList<>();
		List<Boolean> isBuySubOnlineList = new ArrayList<>();
		List<KeyrvDTO> keyrvDTOList = new CourseHelper().getCourseSeries(Long.parseLong(mid));
		mav.addObject("keyrvDTOList",keyrvDTOList);
		for (KeyrvDTO k : keyrvDTOList) {
			RyxCourseDTO subcourse = new CourseHelper().getCourseById(StringHelper.string2Long(k.getRkey()));
			subcourseList.add(subcourse);
			isBuySubOnlineList.add(new CourseHelper().isBuySubOnline(users.getId(), subcourse.getId()));
		}
		mav.addObject("subcourseList", subcourseList);

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
//			if (null != users) {
//				Boolean buyFlag = isBuyOnline(mcourse,users.getId(),Long.parseLong(video),Long.parseLong(id));
//				mav.addObject("buyFlag", buyFlag);
//			}
//			Long ts = System.currentTimeMillis();
//			mav.addObject("ts", ts);
//			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
//			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
			/**
			 * 	更新课程查看次数
			 */
			ResultDTO<Integer> updateViewCountResult = ryxCourseService.updateCourseViewCount(Long.parseLong(mid));
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
			 * 	课程内容介绍刚要
			 */
			ResultDTO<RyxCourseOutlineQuery> outlineResult = MetaHelper.getInstance().getCourseOulineById(Long.parseLong(id));
			errList = addList(errList, outlineResult.getErrorMsg());
			mav.addObject("outlines", outlineResult.getModule().getList());
			mav.addObject("errList", errList);
			mav.addObject("na", "course");
			mav.addObject("loginUsers", users);
			addPasswordModel(mav, request, getCurrentUrl(request));
		}
		return mav;
	}

	@RequestMapping("/offlineList")
	public ModelAndView offlineList(){
		ModelAndView mav = new ModelAndView("member/offlineList");
		return mav;
	}
	@RequestMapping("/topteacherList")
	public ModelAndView topteacherList(){
		ModelAndView mav = new ModelAndView("member/topteacherList");
		return mav;
	}
	@RequestMapping("/customPage")
	public ModelAndView customPage(){
		ModelAndView mav = new ModelAndView("member/customPage");
		return mav;
	}
	@RequestMapping("/collegePage")
	public ModelAndView collegePage(){
		ModelAndView mav = new ModelAndView("member/collegePage");
		return mav;
	}
	@RequestMapping("/enterprisePage")
	public ModelAndView enterprisePage(){
		ModelAndView mav = new ModelAndView("member/enterprisePage");
		return mav;
	}

	@RequestMapping("/annualPage")
	public ModelAndView annualPage(){
		ModelAndView mav = new ModelAndView("member/annualPage");
		return mav;
	}
	@RequestMapping("/advisoryList")
	public ModelAndView advisoryList(){
		ModelAndView mav = new ModelAndView("member/advisoryList");
		return mav;
	}
	@RequestMapping("/booksList")
	public ModelAndView booksList(){
		ModelAndView mav = new ModelAndView("member/booksList");
		return mav;
	}
	@RequestMapping("/appdownloadPage")
	public ModelAndView appdownloadPage(){
		ModelAndView mav = new ModelAndView("member/appdownloadPage");
		return mav;
	}
	@RequestMapping("/seriesList")
	public ModelAndView seriesList(){
		ModelAndView mav = new ModelAndView("member/seriesList");
		return mav;
	}
	@RequestMapping("/onlineLive")
	public ModelAndView onlineLive(){
		ModelAndView mav = new ModelAndView("member/onlineLive");
		return mav;
	}
	@RequestMapping("/info_aboutus")
	public ModelAndView info_aboutus(){
		ModelAndView mav = new ModelAndView("member/info_aboutus");
		return mav;
	}
	@RequestMapping("/info_contactus")
	public ModelAndView info_contactus(){
		ModelAndView mav = new ModelAndView("member/info_contactus");
		return mav;
	}

	@RequestMapping("/info_statement")
	public ModelAndView info_statement(){
		ModelAndView mav = new ModelAndView("member/info_statement");
		return mav;
	}
	@RequestMapping("/info_register")
	public ModelAndView info_register(){
		ModelAndView mav = new ModelAndView("member/info_register");
		return mav;
	}
	@RequestMapping("/info_choosecourse")
	public ModelAndView info_choosecourse(){
		ModelAndView mav = new ModelAndView("member/info_choosecourse");
		return mav;
	}
	@RequestMapping("/info_opencourse")
	public ModelAndView info_opencourse(){
		ModelAndView mav = new ModelAndView("member/info_opencourse");
		return mav;
	}
	@RequestMapping("/info_avai")
	public ModelAndView info_avai(){
		ModelAndView mav = new ModelAndView("member/info_avai");
		return mav;
	}
	@RequestMapping("/info_cooperation")
	public ModelAndView info_cooperation(){
		ModelAndView mav = new ModelAndView("member/info_cooperation");
		return mav;
	}


	@RequestMapping("/list_gangwei.html")
	public ModelAndView listGangwei(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {
		

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		

		ModelAndView mav = new ModelAndView("/3/listGangwei");
		
		return mav;
		
	}
	
	
	
	@RequestMapping("/list_video3.html")
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
		

		ModelAndView mav = new ModelAndView("/3/listVideo3");
		
		
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
	
	
	@RequestMapping("/list_offline3.html")
	public ModelAndView listOffline(HttpServletRequest request, RyxCourseQuery courseQuery, 
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException, ParseException {
		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_offline3.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_offline3.html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_offline3.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/3/listOffline3");
		

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
		
		
		Integer unendCount = 0 ;

		if(null == courseQuery.getInterval()){
			
			/**
			 * 第一页
			 */
			if( null == courseQuery.getCurrentPage() || courseQuery.getCurrentPage() ==1){
				RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
				BeanUtils.copyProperties(courseQuery,ryxCourseQuery ,BeanHelper.getNullPropertyNames(courseQuery));
				ryxCourseQuery.setPageSize(Integer.MAX_VALUE);
				mav.addObject("unendList", MetaHelper.getInstance().getUnendOffline(ryxCourseQuery));
				unendCount  = ryxCourseQuery.getTotalItem();
			}

			courseQuery.setEtstart(System.currentTimeMillis()/1000);
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
			
		}
		else{
			
			
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("asc");
		}
		
		
		Integer ecount = 16 - unendCount ;
		
		courseQuery.setPageSize( ecount );
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
}
