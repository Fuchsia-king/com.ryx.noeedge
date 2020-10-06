package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.enums.EnumVideoStatus;
import com.king.nowedge.dto.ryx.RyxAdDTO;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TonliveController extends BaseController {

	private static final Log logger = LogFactory.getLog(TonliveController.class);

	@RequestMapping("/living")
	public ModelAndView living(Long id,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("member/living");
		RyxCourseDTO rcd = ryxCourseService.getCourseById(id).getModule();
		mav.addObject("offline", rcd);
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUser", usersDTO);
		String username ;
		if(usersDTO==null||StringUtils.isEmpty(usersDTO.getUsername())){
			username = "";
		}else{
			username = usersDTO.getUsername();
		}
		if(StringHelper.isNullOrEmpty(username) || username.length() == 14){
			username = StringHelper.getFuzzyMobile2(usersDTO.getMobile());
		}
		Boolean isBuyOffline = CourseHelper.getInstance().isBuyOffline(usersDTO.getId(),rcd.getId());
		mav.addObject("isBuyOffline",isBuyOffline);
		username = StringHelper.encode(username,"utf-8");
		mav.addObject("username", username);
		return mav;
	}

	@RequestMapping("onlineLiveDetails")
	public ModelAndView onlineLiveDetails(Long courseId, HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("member/onlineLiveDetails");
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
		mav.addObject("users",users);
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResult.getErrorMsg());
		RyxCourseDTO course = courseResult.getModule();
		if (null == course) {
			errList.add("invalid course id : " + courseId);
		} else {
			CourseHelper courseHelper = CourseHelper.getInstance();
			String status = courseHelper.getVideoStatus(course).getName();
			String dateTime = DateHelper.long2String("MM月dd日",course.getTstart()*1000)+" "+DateHelper.long2String("HH:mm",course.getTstart()*1000);
			mav.addObject("dateTime",dateTime);
			mav.addObject("status",status);
			mav.addObject("course",course);
			if (null != users) {
				Long tnow = System.currentTimeMillis() / 1000;
				ResultDTO<Integer> buyResult = ryxOrderService.getOrderCountByUserIdAndCourseId(users.getId(), courseId, tnow);
				errList = addList(errList, buyResult.getErrorMsg());
				Integer buyFlag = buyResult.getModule();
				mav.addObject("buyFlag", buyFlag);
			}else{
				mav.addObject("buyFlag", -1);
			}
			Long ts = System.currentTimeMillis();
			mav.addObject("ts", ts);
			logger.debug("Kd8jQHITMj" + course.getVid() + ts);
			mav.addObject("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + course.getVid() + ts));
			//直播倒计时
			Long t1 = course.getTstart()-ts;
			if(t1>0){
				Long day = t1/(1000*24*60*60);
				Long hour = (t1%(1000*24*60*60))/(1000*60*60);
				Long minute = ((t1%(1000*24*60*60))%(1000*60*60))/(1000*60);
				Long second = (((t1%(1000*24*60*60))%(1000*60*60))%(1000*60))/1000;
				mav.addObject("dateLeave",day+"天"+hour+"时"+minute+"分"+second+"秒");
			}else{
				mav.addObject("dateLeave","直播已结束");
			}
		}
		CourseHelper.getInstance().getAllCategoryAttr(mav);
		return mav;
	}

	/**
	 *
	 * @param c category 分类
	 * @param s status 状态
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/onlineLive")
	public ModelAndView onlineLive(Integer c,Integer s,HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt){
		ModelAndView mav = new ModelAndView("member/onlineLive");
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
		if(c!=null){
			courseQuery.setCategory(c);
		}
		if(s!=null){
			courseQuery.setStatus(s);
		}
		mav.addObject("c",c);
		mav.addObject("s",s);
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		if(EnumVideoStatus.AFTER_LIVING.getCode() == s){ // 已经结束
			courseQuery.setEtend(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.BEFORE_LIVING.getCode() == s){  // 未开始
			courseQuery.setTtstart(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.LIVING.getCode() == s){   // 直播中
			courseQuery.setEtstart(System.currentTimeMillis()/1000);
			courseQuery.setTtend(System.currentTimeMillis()/1000);
		}
		else if(EnumVideoStatus.PLAY_BACK.getCode() == s){   // 回放中
			courseQuery.setEtend(System.currentTimeMillis()/1000);
			courseQuery.setVid("1");
		}
		else{
//			mav.addObject("unendVideo",MetaHelper.getInstance().getUnendVideo(Integer.MAX_VALUE));
			courseQuery.setEtend(System.currentTimeMillis()/1000);
		}

		courseQuery.setPageSize(10);
		courseQuery.setStatus(EnumAuditStatus.APPROVED.getCode());//审核通过
		courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.VIDEO_MODULE.getCode());//在线直播

		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("tstart");
			courseQuery.setSooort("desc");
		}
		//courseQuery.setDisplay(1);
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		//总页数
		Integer pages = courseQuery.getTotalPage();
		List<Integer> pageList = new ArrayList<>();
		for(int i=1;i<=pages;i++){
			pageList.add(i);
		}
		mav.addObject("pages",pages);
		mav.addObject("pageList",pageList);

		//直播分类
		mav.addObject("categorys",MetaHelper.getInstance().getVideoCategory());
		//直播状态
		List<EnumVideoStatus> statusList = EnumVideoStatus.getList();
		Map<Integer,String> map3 = new HashMap<>();
		for(EnumVideoStatus e:statusList){
			map3.put(e.getCode(),e.getName());
		}
		mav.addObject("map3",map3);

		ResultDTO<List<RyxAdDTO>> courseHigtResult = MetaHelper.getInstance().queryAdCache(137);
		errList = addList(errList, courseHigtResult.getErrorMsg());
		mav.addObject("high", courseHigtResult.getModule());
		mav.addObject("title", "金融培训教育_直播课程");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);
		mav.addObject("queryList",courseQuery.getList());
		Map<String,String> map = new HashMap<>();
		List<Map<String,String>> list = new ArrayList<>();
		for(Object rcq:courseQuery.getList()){
			map = new HashMap<>();
			RyxCourseDTO r = (RyxCourseDTO)rcq;
			map.put("id",r.getId().toString());
			map.put("title",r.getTitle());
			map.put("vedioStatus",CourseHelper.getInstance().getVideoStatus(r).getName());
			map.put("imgUrl",r.getImageUrl());
			map.put("desc",r.getDescr());
			map.put("start",DateHelper.second2String("MM月dd日",r.getTstart()));
			map.put("start1",DateHelper.long2String("HH:mm",r.getTstart()*1000));
			if(r.getPrice()==0){
				map.put("price","0");
			}else{
				map.put("price",r.getPrice().toString());
			}
			if(r.getOprice()==0){
				map.put("oprice","0");
			}else{
				map.put("oprice",r.getOprice().toString());
			}
			list.add(map);
		}
		mav.addObject("list1",list);
		if(courseQuery.getCategory()==null){
			mav.addObject("queryCategory",null);
		}
		//响应导航栏全部课程(线上课程)
		CourseHelper.getInstance().getAllCategoryAttr(mav);
		return mav;
	}
}
