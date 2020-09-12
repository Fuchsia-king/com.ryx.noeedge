package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumCourseType;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.service.three.SearchHistoryServeice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


@Controller
public class TindexController  extends BaseController {

	@RequestMapping("/")
	public ModelAndView toIndex(
			HttpServletRequest request,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/index");
		return   mav ;
	}
	
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
		ModelAndView mav = new ModelAndView("search");
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
}
