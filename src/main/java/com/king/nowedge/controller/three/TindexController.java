package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumCourseType;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.enums.EnumTeacherType;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.query.ryx.RyxTeacherQuery;
import com.king.nowedge.service.three.SearchHistoryServeice;
import com.king.nowedge.utils.StringExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TindexController  extends BaseController {

	@RequestMapping("/")
	public ModelAndView toIndex(
			HttpServletRequest request,
			HttpServletResponse response,
			RyxCourseQuery courseQuery,
			Integer h,
			RyxTeacherQuery teacherQuery)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("index");
		CourseHelper.getInstance().getAllCategoryAttr(mav);
//		getAllCategorys(mav);

		MetaHelper metaHelper = new MetaHelper();
		CourseHelper courseHelper = CourseHelper.getInstance();
		//未结束直播
		int displayOnliveCourseCount = 4;//首页初始化展示直播课程数量
		List<RyxCourseDTO> unendList = metaHelper.getUnendVideo(displayOnliveCourseCount);
		Map<String,String> map1 = new HashMap<>();
		List<Map<String,String>> mapList = new ArrayList<>();
		for(RyxCourseDTO rcd:unendList){
			map1 = new HashMap<>();
			map1.put("courseId",rcd.getId().toString());
			map1.put("image",rcd.getImage());
			map1.put("status",courseHelper.getVideoStatus(rcd).getName());
			map1.put("title",rcd.getTitle());
			if(rcd.getPrice()==0){
				map1.put("price","");
			}else{
				map1.put("price",rcd.getPrice().toString());
			}
			map1.put("unendDate",DateHelper.long2String("MM月dd日",rcd.getTstart()*1000));
			map1.put("unendDate1",DateHelper.long2String("HH:mm",rcd.getTstart()*1000)+"-"+DateHelper.long2String("HH:mm",rcd.getTend()*1000));
			map1.put("unendDate2",DateHelper.long2String("HH:mm",rcd.getTstart()*1000));
			mapList.add(map1);
		}
		//已结束直播
		Integer count = metaHelper.getUnendVideoCount();
		List<RyxCourseDTO> endList = metaHelper.getEndVideo(displayOnliveCourseCount-count);
		if(endList!=null){
			for(RyxCourseDTO rcd:endList){
				map1 = new HashMap<>();
				map1.put("courseId",rcd.getId().toString());
				map1.put("image",rcd.getImage());
				map1.put("status",courseHelper.getVideoStatus(rcd).getName());
				map1.put("title",rcd.getTitle());
				if(rcd.getPrice()==0){
					map1.put("price","");
				}else{
					map1.put("price",rcd.getPrice().toString());
				}
				map1.put("unendDate",DateHelper.long2String("MM月dd日",rcd.getTstart()*1000));
				map1.put("unendDate1",DateHelper.long2String("HH:mm",rcd.getTstart()*1000)+"-"+DateHelper.long2String("HH:mm",rcd.getTend()*1000));
				map1.put("unendDate2",DateHelper.long2String("HH:mm",rcd.getTstart()*1000));
				mapList.add(map1);
			}
		}
		mav.addObject("mapList",mapList);
		mav.addObject("unendList",unendList);

		//按条件获取所有课程
		courseQuery.setCategory(h);
		if (StringHelper.isNullOrEmpty(courseQuery.getOrderBy())) {
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		courseQuery.setPageSize(8);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();
		List<RyxCourseDTO> courseDTOS =   courseQuery.getList();
		mav.addObject("courseDTOS",courseDTOS);

		teacherQuery.setPageSize(5);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		teacherQuery.setFlag(EnumTeacherType.PERSONAL.getCode());
		teacherQuery.setOrderBy("sort");

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		teacherQuery = result.getModule();
		mav.addObject("query",teacherQuery);
		List<Integer> pageList = new ArrayList<>();
		for(int i=1;i<=teacherQuery.getTotalPage();i++){
			pageList.add(i);
		}
		mav.addObject("pageList",pageList);
		List<RyxTeacherQuery> list =  teacherQuery.getList();

		mav.addObject("teachers",list);

		courseQuery.setPageSize(8);
		mav.addObject("courseQuery",courseQuery);
		courseQuery.setCurrentPage(1);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery = MetaHelper.getInstance().queryOfflineCourseCache(courseQuery);
		List<RyxCourseDTO> ryxCourseDTOS = courseQuery.getList();
		Map<String,String> map3;
		List<Map<String,String>> mapList1 = new ArrayList<>();
		for(RyxCourseDTO rcd:ryxCourseDTOS){
			map3 = new HashMap<>();
			Long time = System.currentTimeMillis();
			if(time-rcd.getTend()>0){
				map3.put("status","已结束");
			}
			if(time-rcd.getTstart()<0){
				map3.put("status","未开始");
			}
			if(time-rcd.getTstart()>0&&time-rcd.getTend()<0){
				map3.put("status","进行中");
			}
			map3.put("dateAround","时间: "+DateHelper.second2String("yyyy年MM月dd日",rcd.getTstart()) + "-" + DateHelper.second2String("MM月dd日",rcd.getTend()));
			map3.put("id",rcd.getId().toString());
			map3.put("image",rcd.getImage());
			map3.put("location",rcd.getLocation());
			map3.put("title",rcd.getTitle());
			mapList1.add(map3);
		}
		mav.addObject("mapList1",mapList1);
		return  mav ;
	}

	/**
	 * 获取课程公共方法
	 * @param
	 */
//	static void getAllCategorys(ModelAndView mav) {
//		List<RyxCategoryDTO> ryxCategoryDTOList = MetaHelper.getInstance().getOnlineCategory();
//		mav.addObject("ryxCategoryDTOList",ryxCategoryDTOList);
//		Map<RyxCategoryDTO, Map<RyxCategoryDTO,List<RyxCategoryDTO>>> map2 = CourseHelper.getInstance().getAllCategoryAttr(mav);
//		mav.addObject("map2",map2);
//	}

	@RequestMapping("/first")
	public ModelAndView doCreateOrder(
			HttpServletRequest request,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/member/index");
		return   mav ;
	}

	/**
	 * 跳转到搜索结果页之前的controller跳板
	 * @return
	 */
	@RequestMapping(value="/convert_index",method = RequestMethod.GET)
	public ModelAndView convertIndex(){

		return new ModelAndView("index");
	}

	/**
	 * 返回搜索结果页
	 * @param courseQuery
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("search")
	public ModelAndView toSerachPage(RyxCourseQuery courseQuery,HttpServletRequest request, HttpServletResponse response){
		request.getParameter("keyword");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("member/search");
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

	/**
	 * 保存首页搜索栏搜索历史记录
	 * @return
	 */
	@Autowired
	SearchHistoryServeice searchHistoryServeice;

	@RequestMapping(value = "save_history",method = RequestMethod.POST)
	public ModelAndView saveHistory(LoreInputDTO loreInputDTO){
		ModelAndView mav = new ModelAndView();
		searchHistoryServeice.saveHistory(loreInputDTO);
		mav.addObject("msg","保存搜索记录成功");
		return mav;
	}

	/**
	 * 删除搜索历史记录
	 * @return
	 */
	@RequestMapping(value = "delete_history",method = RequestMethod.POST)
	public ModelAndView deleteHistory(LoreInputDTO loreInputDTO){
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","保存搜索记录成功");
		return mav;
	}

	/**
	 * 获得图形验证码
	 * @param request
	 * @param response
	 * @param w
	 * @param h
	 * @param l
	 * @throws IOException
	 */
	@RequestMapping("/get_image_verify_code")
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

	/**
	 * 获取手机验证码
	 * @param mobile
	 * @param imgVerifyCode
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/send_register_verify_code", method = RequestMethod.POST)
	public void sendRegisterVerifyCode(
			@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "imgVerifyCode") String imgVerifyCode,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		response.setContentType("text/html;charset=utf-8");
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

	/**
	 * 资讯列表
	 * @param courseQuery
	 * @return
	 */
	@RequestMapping(value = "advisoryList",method = RequestMethod.GET)
	public ModelAndView advisoryList(RyxCourseQuery courseQuery){
		ModelAndView mav = MAVHelper.getMav("member/advisoryList");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
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
		List<RyxCourseDTO> ryxCourses =  courseQuery.getList();
		if(courseQuery.getCurrentPage()==2){
			for(RyxCourseDTO rcd:ryxCourses){
				rcd.setDescr("”"+rcd.getDescr().replaceAll("\r\n","").replace("一、","").replace("1、",":")+"”");
			}
		}
		mav.addObject("ryxCourses",ryxCourses);

		List<Integer> pageList = new ArrayList<>();
		for(int i=1;i<=ryxCourses.size();i++){
			pageList.add(i);
		}
		mav.addObject("pageList",pageList);
		//资讯分类
		List<RyxCategoryDTO> categorys =  MetaHelper.getInstance().getInfoIndustry();
		mav.addObject("categorys",categorys);
		//资讯行业
		List<RyxCategoryDTO> industrys =  MetaHelper.getInstance().getInfoCategory();
		mav.addObject("industrys",industrys);
//		for(RyxCategoryDTO rcad:industrys){
//			Integer count = CourseHelper.getInstance().countInfo(courseQuery.getFlag(),rcad.getId().intValue());
//			if(count>0){
//
//			}
//		}
		//加载全部课程列表
		CourseHelper.getInstance().getAllCategoryAttr(mav);
		return mav;
	}

	/**
	 * 资讯详情
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "advisoryDetails",method = RequestMethod.GET)
	public ModelAndView advisoryDetails(Long id,HttpServletRequest request){

		ModelAndView mav = new ModelAndView("member/advisoryDetails");
		errList = new ArrayList<String>();

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {
			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/online_" + id + ".html");
			} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}
			mav.addObject("course", course);
		}
		return mav;
	}

	/**
	 * 书籍列表
	 * @param courseQuery
	 * @return
	 */
	@RequestMapping("booksList")
	public ModelAndView booksList(RyxCourseQuery courseQuery){
		ModelAndView mav = MAVHelper.getMav("member/booksList");
		errList = new ArrayList<String>();

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		courseQuery.setObjType(EnumObjectType.BOOK_MODULE.getCode());
		courseQuery.setIdeleted(0);
		courseQuery.setDisplay(1);
		courseQuery = MetaHelper.getInstance().queryCourseCache(courseQuery).getModule();
		List<RyxCourseDTO> courseList = courseQuery.getList();

		mav.addObject("courseList", courseList);
		//获取所有课程列表
		CourseHelper.getInstance().getAllCategoryAttr(mav);
		return mav;
	}

	/**
	 * 书籍明细
	 * @param id
	 * @return
	 */
	@RequestMapping("booksDetails")
	public ModelAndView booksDetails(Long id){

		ModelAndView mav = MAVHelper.getMav("member/booksDetails");
		errList = new ArrayList<String>();

		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(id);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();

		if (null == course) {
			errList.add("invalid course id : " + id);
		} else {
			if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/online_" + id + ".html");
			} else if (EnumObjectType.VIDEO_MODULE.getCode() == course.getObjType()) {
				return new ModelAndView("redirect:/video_" + id + ".html");
			}
			mav.addObject("course", course);
			mav.addObject("nickname",UserHelper.getInstance().getTeacherByUserId(course.getCuid()).getNickname());
		}
		return mav;
	}

	/**
	 * 关于我们/融易学简介
	 * @return
	 */
	@RequestMapping("infoAboutus")
	public ModelAndView infoAboutus(){
		ModelAndView mav = MAVHelper.getMav("member/info_aboutus");
		return mav;
	}
	/**
	 * 如何注册
	 * @return
	 */
	@RequestMapping("infoRegister")
	public ModelAndView infoRegister(){
		ModelAndView mav = MAVHelper.getMav("member/info_register");
		return mav;
	}
	/**
	 * 联系我们
	 * @return
	 */
	@RequestMapping("infoContactus")
	public ModelAndView infoContactus(){
		ModelAndView mav = MAVHelper.getMav("member/info_contactus");
		return mav;
	}
	/**
	 * 免责声明
	 * @return
	 */
	@RequestMapping("infoStatement")
	public ModelAndView infoStatement(){
		ModelAndView mav = MAVHelper.getMav("member/info_statement");
		return mav;
	}
	/**
	 * 如何选课
	 * @return
	 */
	@RequestMapping("infoChoosecourse")
	public ModelAndView infoChoosecourse(){
		ModelAndView mav = MAVHelper.getMav("member/info_choosecourse");
		return mav;
	}
	/**
	 * 如何开课
	 * @return
	 */
	@RequestMapping("infoOpencourse")
	public ModelAndView infoOpencourse(){
		ModelAndView mav = MAVHelper.getMav("member/info_opencourse");
		return mav;
	}

	/**
	 * 课程有效期
	 * @return
	 */
	@RequestMapping("infoAvai")
	public ModelAndView infoAvai(){
		ModelAndView mav = MAVHelper.getMav("member/info_avai");
		return mav;
	}
	/**
	 * 合作方式
	 * @return
	 */
	@RequestMapping("infoCooperation")
	public ModelAndView infoCooperation(){
		ModelAndView mav = MAVHelper.getMav("member/info_cooperation");
		return mav;
	}

}
