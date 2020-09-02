package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.ryx.RyxUserCouponDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.dto.ryx.query.RyxUsersQuery;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeixinController extends BaseController {

	private static final Log logger = LogFactory.getLog(WeixinController.class);

//	@Autowired
//	@Qualifier("org.springframework.security.authenticationManager")
//	protected AuthenticationManager authenticationManager;
	
	@ResponseBody
	@RequestMapping("/weixinListOnline")
	public Map<String, Object> weixinListOnline(HttpServletRequest request)
			throws UnsupportedEncodingException {
		Map<String, Object> params = new HashMap<String, Object>(); 
		try {
			Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
			RyxCourseQuery query = new RyxCourseQuery();
			query.setOrderBy("id");
			query.setSooort("desc");
			query.setPageSize(pageSize);
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			query = result.getModule();
			System.out.println(query.getList());
			
			params.put("list", query.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			params.put("error", "查询异常");
		}
		return params;

	}
	
	
	

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> doWeixinRegisterMryx(HttpServletRequest req) throws UnsupportedEncodingException {
			String mobile = req.getParameter("mobile");
			String psw1 = req.getParameter("psw1");
			String psw2 = req.getParameter("psw2");
			Map<String, Object> params = new HashMap<String, Object>(); 
			System.out.println(mobile + psw1 + psw2);
		
			
			RyxUsersQuery query = new RyxUsersQuery();
			query.setMobile(mobile);
			query.setEmail("993373@163.com");

			ResultDTO<RyxUsersDTO> mobileResult = ryxUserService.getUserByMobile(mobile);
			errList = addList(errList, mobileResult.getErrorMsg());
			RyxUsersDTO user = mobileResult.getModule();
			if (null != user) {
				params.put("error","该手机号码已经注册，请直接登录");
			}
			
			if(!psw1.equals(psw2)){
				params.put("error","密码与确认密码不一致，请重新输入");
			}

			if (null == errList || errList.size() == 0) {

				user = new RyxUsersDTO();
				user.setEmail("993373@163.com");
				user.setPassword(Md5Util.GetMD5Code(psw1));
				user.setMobile(mobile);
				user.setUsername("ryx" + mobile);
				user.setFlag(1);

				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);

				if (createUserResult.isSuccess()) {

					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"代金券");
						userCouponDTO.setCreaterId(userId);
						ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
						addList(errList, result.getErrorMsg());
					} else {
						params.put("error","无效的用户Id");
					}
				} else {

				}
			}
			return params;
	}
	@RequestMapping("/toH5Home.html")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/mh5/mhome");
		return mav;

	}
	@RequestMapping("/h5_test.html")
	public ModelAndView hindex(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/mh5/mindex");
		return mav;

	}
	@RequestMapping("/toscroll.html")
	public ModelAndView toscroll(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/mh5/toscroll");
		return mav;

	}
	@RequestMapping("/toNewIndex.html")
	public ModelAndView Newindex(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/m/mlogin");
		return mav;

	}
	@RequestMapping("/mryx/list_online_course_h5")
	public ModelAndView listOnlineCourse(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/mh5/xinsanban");

		String price = courseQuery.getPrice();

		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}

		} catch (Throwable t) {

		}

		courseQuery.setPageSize(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setOrderBy("c.sort");
		courseQuery.setSooort("desc");
		courseQuery.setCategory(113);
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		

		courseQuery = courseResult.getModule();
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());
		mav.addObject("course", courseQuery.getList());
		mav.addObject("query", courseQuery);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));
		mav.addObject("exploreType", getExploreType(request));

		return mav;

	}
	@RequestMapping("/mryx/list_online_course_h5_2")
	public void listOnlineCourse2(HttpServletRequest request, RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		String price = courseQuery.getPrice();
		try {

			if(!StringHelper.isNullOrEmpty(price)){
				String[] p = price.split("_");
				courseQuery.setSprice(Double.valueOf(p[0]));
				courseQuery.setEprice(Double.valueOf(p[1]));
			}
		} catch (Throwable t) {
		}

		courseQuery.setPageSize(1);
		courseQuery.setCurrentPage(DEFAULT_PAGE_SIZE);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setOrderBy("c.sort");
		courseQuery.setSooort("desc");
		courseQuery.setCategory(113);
		
		ResultDTO<RyxCourseQuery> courseResult = MetaHelper.getInstance().queryCourseCache(courseQuery);
		System.out.println(courseResult.getModule().getList());
		errList = addList(errList, courseResult.getErrorMsg());
		if(courseResult.isSuccess()){
			writeAjax(response, true,"",courseResult.getModule().getList());
		}
		else{
			writeAjax(response, false,courseResult.getErrorMsg());
		}
	}
	
	@RequestMapping("/lunbo.html")
	public ModelAndView Lunbo(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/mh5/mlunbo");
		return mav;

	}
	
}
