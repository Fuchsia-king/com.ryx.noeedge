package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxAuthDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class AuthController extends BaseController {
	
	private static final Log log = LogFactory.getLog(AuthController.class);
	 
	
	
	@RequestMapping("/my/applyTeacher") 
	public String applyTeacher(Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<RyxAuthDTO> result =  ryxUserService.getAuthByUid(Long.valueOf(user.getId()));
		
		if(result.isSuccess()){
		
			RyxAuthDTO list  =result.getModule();
			if (null!=list) {
				Integer status = list.getAuthStatus();
				if (status == 1) {
					String code = Md5Util.GetMD5Code(Long.valueOf(user.getId()) + "szbelle");
					return "redirect:http://ryximages.ryx365.com/teacher.do?userid=" + Long.valueOf(user.getId()) + "&code=" + code;
	//				model.addAttribute("msg", "您的资料已审核通过！恭喜您！赶快上传课程吧！");
				} else if (status == 0) {
					model.addAttribute("msg", "您的资料已经在审核，请耐心等待，可随时咨询客服热线了解审核进度。");
				} else if (status == 2) {
					model.addAttribute("msg", "你的资料未审核通过！请完善资料或咨询客户热线。");
				}
			}
			return "apply_teacher";
		}
		else{
			
			model.addAttribute("msg", result.getErrorMsg());
			return "apply_teacher";
		}
	}
	
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	public void uploadFile(HttpServletResponse response, HttpServletRequest request,@RequestParam(value="file", required=false) MultipartFile file) throws IOException{
		log.debug("uploadFile");
		if (file != null) {
			byte[] bytes = file.getBytes();
			System.out.println(file.getOriginalFilename());
			String uploadDir = "D:\\wamp\\www\\Uploads\\Picture\\teacher";
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			String sep = System.getProperty("file.separator");
			String fileName = System.currentTimeMillis() + file.getOriginalFilename();
			File uploadedFile = new File(uploadDir + sep + fileName);
			FileCopyUtils.copy(bytes, uploadedFile);
			response.getWriter().write("http://ryximages.ryx365.com/Uploads/Picture/teacher/" + fileName);
		}
	}
	
	
}
