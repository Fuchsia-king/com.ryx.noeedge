package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.query.ryx.RyxTeacherQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController extends BaseController {
	private static final Log log = LogFactory.getLog(TeacherController.class);
	
	
	@RequestMapping("/listPageTeacher")
	public String listPageTeacher(
			RyxTeacherQuery teacherQuery,
			Model model){
	
		errList = new ArrayList<String>();
	
		ResultDTO<RyxTeacherQuery> queryResult = ryxTeacherService.queryTeacher(teacherQuery);
		errList = addList(errList, queryResult.getErrorMsg());
		teacherQuery = queryResult.getModule();
		
		model.addAttribute("teacherQuery", teacherQuery);
		model.addAttribute("category", teacherQuery.getCategory());
		model.addAttribute("keyword", teacherQuery.getKeyword());
		model.addAttribute("currentPage", teacherQuery.getCurrentPage());
		
		
		ResultDTO<List<RyxCategoryDTO>> cateResult = ryxCategoryService.getOnlineCategory();
		errList = addList(errList, cateResult.getErrorMsg());
		model.addAttribute("categorys", cateResult.getModule() );
		
		ResultDTO<List<RyxAdDTO>> adResult = ryxAdService.getAd(115);
		errList = addList(errList, adResult.getErrorMsg());
		model.addAttribute("ads", adResult.getModule());//精品推荐
		
		
		ResultDTO<List<RyxCourseDTO>> courseResult = ryxCourseService.getLastCourse(6); 
		errList = addList(errList, courseResult.getErrorMsg());
		model.addAttribute("lastCourse", courseResult.getModule());
		
		
		courseResult = ryxCourseService.getZdbfCourse(6);
		errList = addList(errList, courseResult.getErrorMsg());
		model.addAttribute("zdbf", courseResult.getModule());
		
		
		courseResult = ryxCourseService.getCnxh(3, teacherQuery.getKeyword());
		errList = addList(errList, courseResult.getErrorMsg());
		model.addAttribute("cnxh", courseResult.getModule());//猜你喜欢
		
		model.addAttribute("errList", errList);
		
		return "list_teacher";
	}
	
	@RequestMapping("/getTeacherById")
	public String getTeacherById(Long id, Model model) {
		
		errList = new ArrayList<String>();
		ResultDTO<RyxTeacherDTO> teacherResult = ryxTeacherService.getTeacherById(id);
		errList = addList(errList, teacherResult.getErrorMsg());
		model.addAttribute("teacher", teacherResult.getModule());
		
		RyxCourseQuery courseQuery = new RyxCourseQuery();  
		courseQuery.setTid(id);
		ResultDTO<Integer> countResult =  ryxCourseService.countQueryCourse(courseQuery);
		errList = addList(errList, countResult.getErrorMsg());
		model.addAttribute("courseCount",countResult.getModule());
		
		return "teacher_detail";
	}
	
	@RequestMapping("/my/followTeacherByTeacherId")
	public String followTeacherByTeacherId(Long id, Model model, HttpSession session) {
		
		errList = new ArrayList<String>();	
		
		ResultDTO<RyxTeacherDTO> tresult = ryxTeacherService.getTeacherById(id);
		errList = addList(errList, tresult.getErrorMsg());
		RyxTeacherDTO teacher = tresult.getModule();
		
		
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<List<RyxUserFollowTeacherDTO>> followResult = ryxTeacherService.getUserFollowTeacherByTeacherIdAndUserId(id, Long.valueOf(user.getId()));
		errList = addList(errList, followResult.getErrorMsg());
		List<RyxUserFollowTeacherDTO> list = followResult.getModule();
		
		if (null == list || list.size() < 1) {
			RyxUserFollowTeacherDTO ryxUserFollowTeacher = new RyxUserFollowTeacherDTO();
			ryxUserFollowTeacher.setTeacherId(id);
			ryxUserFollowTeacher.setUserId(Long.valueOf(user.getId()));
			ResultDTO<Boolean> r = ryxTeacherService.saveUserFollowTeacher(ryxUserFollowTeacher);
			errList = addList(errList, r.getErrorMsg());
		}
		
		model.addAttribute("teacher", teacher);
		model.addAttribute("msg", "关注成功!");
		
		model.addAttribute("errList", errList);
		
		return "teacher_detail";
	}
	
	@RequestMapping("/my/listRyxUserFollowTeacher")
	public String listRyxUserFollowTeacher(Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		
		ResultDTO<List<RyxUserFollowTeacherDTO>> result = ryxTeacherService.listUserFollowTeacher(Long.valueOf(user.getId()));
		
		errList = addList(errList, result.getErrorMsg());		
		
		model.addAttribute("errList", errList);
		
		model.addAttribute("list", result.getModule());
		return "my/my_user_follow_teacher";
	}
}
