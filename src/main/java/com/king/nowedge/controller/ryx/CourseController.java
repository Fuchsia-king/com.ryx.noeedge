package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.query.ryx.RyxUsersQuery;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.Tools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController extends BaseController {
	
	
	private static final Log log = LogFactory.getLog(CourseController.class);


	//找课程
	@RequestMapping("/listPageCourse.do")
	public String listPageCourse(
			RyxCourseQuery courseQuery,
			Model model,
			HttpServletRequest request){
		
		
		errList = new ArrayList<String>();
		
		String strPrice = null;
//		if (courseQuery.getPrice() == 1) {
//			strPrice = "0-0";
//		} else if (courseQuery.getPrice() == 2) {
//			strPrice = "10-50";
//		} else if (courseQuery.getPrice() == 3) {
//			strPrice = "50-100";
//		} else if (courseQuery.getPrice() == 4) {
//			strPrice = "100-100000";
//		}
		
		
		ResultDTO<RyxCourseQuery> queryResult = ryxCourseService.queryCourse(courseQuery);
//		String baseUrl = request.getContextPath() + "/video_image/";
//		log.info("baseUrl=" + baseUrl);
//		RyxCourse ryxCourse = null;
//		for (Object object : pageBean.getResultList()) {
//			ryxCourse = (RyxCourse) object;
//			ryxCourse.setImageUrl(baseUrl + ryxCourse.getImageUrl());
//			log.info("image_url=" + ryxCourse.getImageUrl());
//		}
		
		if(queryResult.isSuccess()){
			
		}
		else{
			errList.add(queryResult.getErrorMsg());
		}
		
		model.addAttribute("query", courseQuery);
		model.addAttribute("keyword", courseQuery.getKeyword());
		model.addAttribute("category", courseQuery.getCategory());
		model.addAttribute("difficulty", courseQuery.getDifficulty());
		model.addAttribute("price", courseQuery.getPrice());
		model.addAttribute("currentPage", courseQuery.getCurrentPage());
		model.addAttribute("lastCourse", ryxCourseService.getLastCourse(6));
		model.addAttribute("zdbf", ryxCourseService.getZdbfCourse(6));
//		model.addAttribute("cnxh", ryxCourseService.getCnxh(3, category));//猜你喜欢
		
		model.addAttribute("categorys", ryxCategoryService.getOnlineCategory());
		model.addAttribute("ads", ryxAdService.getAd(115));//精品推荐
		model.addAttribute("errList", errList);
		
		return "list_course";
	}
	
	//线下课程
	@RequestMapping("/listPageCourse2.do")
	public String listPageCourse2(
			RyxCourseQuery query,
			Model model,
			HttpServletRequest request){
	
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseQuery> queryResult = ryxCourseService.queryCourse(query);
		model.addAttribute("bean", queryResult.getModule());
		if(!queryResult.isSuccess()){
			errList.add(queryResult.getErrorMsg());
		}
		
		model.addAttribute("currentPage", query.getCurrentPage());
		model.addAttribute("city", query.getDistrict2());
		model.addAttribute("title", query.getTitle());
		
		
		
		ResultDTO<List<RyxCourseDTO>> lastCourseResult = ryxCourseService.getLastCourse(6);
		model.addAttribute("lastCourse", lastCourseResult.getModule());
		if(!lastCourseResult.isSuccess()){
			errList.add(lastCourseResult.getErrorMsg());
		}
		
		
		ResultDTO<List<RyxCourseDTO>> zdbfCourseResult = ryxCourseService.getZdbfCourse(6);
		model.addAttribute("zdbf", zdbfCourseResult.getModule());
		if(!zdbfCourseResult.isSuccess()){
			errList.add(zdbfCourseResult.getErrorMsg());
		}
		
		
		
		
		ResultDTO<List<RyxCourseDTO>> cnxhCourseResult = ryxCourseService.getCnxh(3, query.getTitle());
		model.addAttribute("cnxh", cnxhCourseResult.getModule());//猜你喜欢
		if(!cnxhCourseResult.isSuccess()){
			errList.add(cnxhCourseResult.getErrorMsg());
		}

		
		ResultDTO<List<RyxCategoryDTO>> categorysResult = ryxCategoryService.getOfflineCategory();
		model.addAttribute("categorys", categorysResult.getModule());
		if(!categorysResult.isSuccess()){
			errList.add(categorysResult.getErrorMsg());
		}
		
		
		/**
		 * 　　jin pin tui jian 
		 */
		ResultDTO<List<RyxAdDTO>> adsResult = ryxAdService.getAd(115);
		model.addAttribute("ads", adsResult.getModule());
		if(!adsResult.isSuccess()){
			errList.add(adsResult.getErrorMsg());
		}
		
		
		
		
		ResultDTO<RyxConfigDTO> configResult = ryxConfigService.getDistrict(74);
		if(configResult.isSuccess()){
			RyxConfigDTO config = configResult.getModule();
			String distrct = config.getValue();
			String[] districtList = distrct.split(",");
			log.info("length=" + districtList.length);
			model.addAttribute("districtList", districtList);
		}
		else{
			errList.add(configResult.getErrorMsg());
			model.addAttribute("districtList", null);
		}
		
		model.addAttribute("errList", errList);		
		return "list_off_course";
	}
	
	@RequestMapping("/getCourseById2.do")
	public String getCourseById2(Long id, Model model) throws UnsupportedEncodingException {
		//校验该用户是否有权限查看课堂
		
		
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> ryxCourse = ryxCourseService.getCourseById(id);
		model.addAttribute("course", ryxCourse.getModule());
		if(!ryxCourse.isSuccess()){
			errList.add(ryxCourse.getErrorMsg());
		}
		
		
		//更新课程查看次数
		ResultDTO<Integer> updateViewResult = ryxCourseService.updateCourseViewCount(id);
		if(!updateViewResult.isSuccess()){
			errList.add(updateViewResult.getErrorMsg());
		}
		
		
		
		ResultDTO<List<RyxCourseDTO>> lastCourseResult = ryxCourseService.getLastCourse(6);
		model.addAttribute("lastCourse", lastCourseResult.getModule());
		if(!lastCourseResult.isSuccess()){
			errList.add(lastCourseResult.getErrorMsg());
		}
		
		
		
		ResultDTO<List<RyxCourseDTO>> zdbfResult = ryxCourseService.getZdbfCourse(6);		
		model.addAttribute("zdbf", zdbfResult.getModule());
		if(!zdbfResult.isSuccess()){
			errList.add(zdbfResult.getErrorMsg());
		}
		
		
		
		ResultDTO<List<RyxCourseDTO>> cnxhCourseResult = ryxCourseService.getCnxh(3, ryxCourse.getModule().getTitle());
		model.addAttribute("cnxh", cnxhCourseResult.getModule());//猜你喜欢
		if(!cnxhCourseResult.isSuccess()){
			errList.add(cnxhCourseResult.getErrorMsg());
		}
		
		
		
		/**
		 * 
		 *  jin pin tui jian 
		 * 
		 */
		
		ResultDTO<List<RyxAdDTO>> adsResult = ryxAdService.getAd(115);
		model.addAttribute("ads", adsResult.getModule());
		if(!adsResult.isSuccess()){
			errList.add(adsResult.getErrorMsg());
		}
		
		model.addAttribute("errList", errList);
		
		return "course_detail2";
	}
	
	@RequestMapping("/getCourseById.do")
	public String getCourseById(Long id, String orderId, Model model, String feedback, 
			HttpSession session, HttpServletRequest request, RedirectAttributes attributes) 
					throws UnsupportedEncodingException {
		
		
		errList = new ArrayList<String>();
		
		
		//校验该用户是否有权限查看课堂
		Object object = session.getAttribute("user");
		log.info("obj=" + object == null);
		if (object != null) {
			RyxUsersQuery user = (RyxUsersQuery) object;
			log.debug("mobile=" + user.getMobile());
			if (Tools.isEmpty(user.getMobile())) {
				log.debug("请先完善手机号!");
				attributes.addFlashAttribute("msg", "请先完善手机号!");
				return "redirect:/my/my_info.html";
			}
			
			model.addAttribute("flag", 0);
			ResultDTO<Integer> orderCntResultDTO = ryxOrderService.getOrderCountByUserIdAndCourseId(user.getId(), id, System.currentTimeMillis()/1000) ;
			if(orderCntResultDTO.isSuccess()){
				if ( orderCntResultDTO.getModule() > 0) {//购买过
					model.addAttribute("flag", 0);
				}
			}else{
				errList.add(orderCntResultDTO.getErrorMsg());
			}
		} else {
			log.debug("-------url--------" + request.getRequestURI());
			session.setAttribute("url", "/course_" + id + ".html");
			return "redirect:/login.html";
		}
		
		
		RyxCourseDTO  ryxCourse = null;
		ResultDTO<RyxCourseDTO>  ryxCourseResult = ryxCourseService.getCourseById(id);
		if(ryxCourseResult.isSuccess()){
			
			ryxCourse = ryxCourseResult.getModule();
			
			List<RyxCourseDTO> list = new ArrayList<RyxCourseDTO>();
			String relatedCourse = ryxCourse.getRelatedCourse();
			if (relatedCourse != null) {
				log.debug("relatedCourse=" + relatedCourse);
				for (String vid : relatedCourse.split(",")) {
					log.debug("vid=" + vid);
					ResultDTO<List<RyxCourseDTO>> tempListResult = ryxCourseService.getCourseByVId(vid);
					if(tempListResult.isSuccess()){
						List<RyxCourseDTO> tempList = tempListResult.getModule();
						if (tempList != null && tempList.size() > 0) {
							list.add( tempList.get(0));;
						}
					}					
				}
				ryxCourse.setList(list);
			}
		}
		
		String msg = request.getParameter("msg");
		if (msg != null) {
			msg = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
		}
		model.addAttribute("msg", msg);
		model.addAttribute("course", ryxCourse);
		model.addAttribute("orderId", orderId);
		log.info("feedback=" + feedback);
		model.addAttribute("feedback", feedback);
		
		Long ts = System.currentTimeMillis() * 1000;
		model.addAttribute("ts", ts);
		log.debug("Kd8jQHITMj" + ryxCourse.getVid() + ts);
		model.addAttribute("sign", Md5Util.GetMD5Code("Kd8jQHITMj" + ryxCourse.getVid() + ts));
		
		//更新课程查看次数
		ResultDTO<Integer> updateCourseViewCountResult =  ryxCourseService.updateCourseViewCount(id);
		if(!updateCourseViewCountResult.isSuccess()){
			errList.add(updateCourseViewCountResult.getErrorMsg());
		}
		
		ResultDTO<List<RyxFeedbackDTO>> feedbackListResult = ryxUserService.getFeedback(id);
		model.addAttribute("feedbackList", feedbackListResult.getModule());
		if(!feedbackListResult.isSuccess()){
			errList.add(feedbackListResult.getErrorMsg());
		}
		
		model.addAttribute("errList", errList);
		
		return "course_detail";
	}
	
	@RequestMapping("/my/collectCourse")
	public String collectCourse(Long id, Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> ryxCourseResult = ryxCourseService.getCourseById(id);
		if(!ryxCourseResult.isSuccess()){
			errList.add(ryxCourseResult.getErrorMsg());
		}
		
		RyxUsersQuery user = (RyxUsersQuery) session.getAttribute("user");
		
		ResultDTO<List<RyxUserFollowCourseDTO>> listResult = ryxCourseService.getUserFollowCourseByCourseIdAndUserId(id, user.getId());
		if(listResult.isSuccess()){
			List<RyxUserFollowCourseDTO> list = listResult.getModule();
			if (null == list || list.size() < 1) {
				RyxUserFollowCourseDTO userFollowCourse = new RyxUserFollowCourseDTO();
				userFollowCourse.setCourseId(id);
				userFollowCourse.setUserId(user.getId());
				ryxCourseService.saveUserFollowCourse(userFollowCourse);
				model.addAttribute("msg", "收藏成功!");
			} else {
				model.addAttribute("msg", "您已经收藏该课程");
			}
		}
		else{
			errList.add(listResult.getErrorMsg());
		}
		
		model.addAttribute("course", ryxCourseResult.getModule());
		
		model.addAttribute("errList", errList);
		
		
		return "redirect:/getCourseById.do?id=" + id;
	}
	
	@RequestMapping("/my/listRyxUserFollowCourse")
	public String listRyxUserFollowCourse(Model model, HttpSession session) {
		RyxUsersQuery user = (RyxUsersQuery) session.getAttribute("user");
		
		ResultDTO<List<RyxUserFollowCourseDTO>> listResult = ryxCourseService.listUserFollowCourse(user.getId());
		if(!listResult.isSuccess()){
			errList.add(listResult.getErrorMsg());
		}
		model.addAttribute("list", listResult.getModule());
		model.addAttribute("errList", errList);
		return "my/my_user_follow_course";
	}
	
	@RequestMapping("/my/uncollectCourse")
	public String uncollectCourse(Long id, Model model, HttpSession session) {
		RyxUsersQuery user = (RyxUsersQuery) session.getAttribute("user");
		ResultDTO<Boolean> deleteResult = ryxCourseService.deleteUserFollowCourseByCourseIdAndUserId(id, user.getId());
		if(deleteResult.isSuccess()){
			model.addAttribute("msg", "取消收藏成功!");
		}
		else{
			model.addAttribute("msg", deleteResult.getErrorMsg());
		}
		
		return listRyxUserFollowCourse(model, session);
	}
}
