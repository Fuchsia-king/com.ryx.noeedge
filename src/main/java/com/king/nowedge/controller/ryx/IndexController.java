package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.RyxCategoryQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController extends BaseController {
	private static final Log log = LogFactory.getLog(IndexController.class);

	
	@RequestMapping("/index")
	public String index(Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		
		ResultDTO<List<RyxAdDTO>> adResult = ryxAdService.getAd(106);
		model.addAttribute("banners", adResult.getModule());//banner
		if(!adResult.isSuccess()){
			errList.add(adResult.getErrorMsg());
		}
		
			
		adResult = ryxAdService.getAd(107);
		model.addAttribute("indexFootAd", adResult.getModule());//首页底部广告
		if(!adResult.isSuccess()){
			errList.add(adResult.getErrorMsg());
		}
		
		
//		log.info("114=" + ryxAdService.getAd(114));
		adResult = ryxAdService.getAd(114);
		model.addAttribute("ads", adResult.getModule());//首页中间广告
		if(!adResult.isSuccess()){
			errList.add(adResult.getErrorMsg());
		}
		
		
		
		ResultDTO<List<RyxCategoryDTO>> categoryResult = ryxCategoryService.getFirstCategory();
		model.addAttribute("firstCategory", categoryResult.getModule());
		if(!categoryResult.isSuccess()){
			errList.add(categoryResult.getErrorMsg());
		}
		
		categoryResult = ryxCategoryService.getSecondCategory();
		model.addAttribute("secondCategory", categoryResult.getModule());
		if(!categoryResult.isSuccess()){
			errList.add(categoryResult.getErrorMsg());
		}
		
		
		
//		model.addAttribute("hotestCategory", ryxCategoryService.getHotestCategory());
		ResultDTO<List<RyxTeacherDTO>> teacherResult = ryxTeacherService.getMostHotTeacher();
		model.addAttribute("hotTeachers", teacherResult.getModule());
		if(!teacherResult.isSuccess()){
			errList.add(teacherResult.getErrorMsg());
		}
		
		
		ResultDTO<List<RyxCourseDTO>> courseResult = ryxCourseService.getLastCourse(8);
		model.addAttribute("lastCourse", courseResult.getModule());
		if(!courseResult.isSuccess()){
			errList.add(courseResult.getErrorMsg());
		}
		
		
		courseResult = ryxCourseService.getZdbfCourse(8);
		model.addAttribute("zdbfCourse", courseResult.getModule());
		if(!courseResult.isSuccess()){
			errList.add(courseResult.getErrorMsg());
		}
		
		courseResult = ryxCourseService.getZjrqCourse(8);
		model.addAttribute("zjrqCourse", courseResult.getModule());//最具人气
		if(!courseResult.isSuccess()){
			errList.add(courseResult.getErrorMsg());
		}
		
		
		
		courseResult = ryxCourseService.getOfflineCourse();
		model.addAttribute("offlineCourse", courseResult.getModule());//线下课程
		if(!courseResult.isSuccess()){
			errList.add(courseResult.getErrorMsg());
		}
		
		
		ResultDTO<List<RyxNewsDTO>> newsResult = ryxNewsService.getHotNews();
		model.addAttribute("hotNews", newsResult.getModule());//热门排行
		if(!newsResult.isSuccess()){
			errList.add(newsResult.getErrorMsg());
		}
		
		
		newsResult = ryxNewsService.getXYXS();
		model.addAttribute("xyxs",newsResult.getModule());//学员心声
		if(!newsResult.isSuccess()){
			errList.add(newsResult.getErrorMsg());
		}
		
		model.addAttribute("errList",errList);
		
		return "index";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/changeCategory", produces = "application/json")
	public Map<String, Object> changeCategory(RyxCategoryQuery query, Model model, HttpServletResponse response) throws IOException {
//		log.info("......................changeCategory.................");
//		
//		if (page < 1) {
//			page = 1;
//		}
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		PageBean bean = ryxCategoryService.getCategoryPage(page);
//		map.put("msg", bean.getStr());
//		map.put("page", bean.getCurrentPage());
//		return map;
		
		return null;
	}
	
	
	
	
}
