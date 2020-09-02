package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.RyxAdDTO;
import com.king.nowedge.dto.ryx.RyxCategoryDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxCategoryQuery;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.helper.*;
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

	
	@RequestMapping("/list_online3.html")
	public ModelAndView doCreateOrder(
			HttpServletRequest request,
			RyxCourseQuery courseQuery,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		
		
		if(StringHelper.isMoblie(request)){
			if(ConstHelper.isPreEnvironment()){
				return new ModelAndView("redirect:http://pm.ryx365.com/m/list_online3.html");
			}
			else if(ConstHelper.isFormalEnvironment()){
				return new ModelAndView("redirect:http://m.ryx365.com/m/list_online3.html");
			}
			else{
				return new ModelAndView("redirect:http://am.ryx365.com/m/list_online3.html");
			}
		}

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/3/listOnline3");

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