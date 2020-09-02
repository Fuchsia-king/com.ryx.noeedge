package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.AddressDTO;
import com.king.nowedge.dto.AttrValueDTO;
import com.king.nowedge.dto.ResumeDTO;
import com.king.nowedge.dto.WorkExperienceDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.query.AddressQuery;
import com.king.nowedge.dto.query.ContactQuery;
import com.king.nowedge.dto.query.WorkExperienceQuery;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.dto.ryx2.validate.ProfileDTO;
import com.king.nowedge.dto.ryx2.validate.SubAccountDTO;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.Md5Util;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;
//import com.tbc.paas.open.domain.uc.OpenUser;
//import com.tbc.paas.open.service.uc.OpenUserService;
//import com.tbc.paas.sdk.core.ServiceManager;
//import com.tbc.paas.sdk.util.SdkContext;

@Controller
public class PuserController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(PuserController.class);
	

	
	@RequestMapping("/list_org.html")
	public ModelAndView listOrg(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/clistOrg");

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		teacherQuery.setFlag(EnumTeacherType.ORG.getCode());

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);

		/**
		 * 讲师类别
		 */
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());


		mav.addObject("title", "融资租赁培训课程_合作机构");
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/list_personal.html")
	public ModelAndView listPersonal(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/clistPersonal");

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setDisplay(1);
		teacherQuery.setFlag(EnumTeacherType.PERSONAL.getCode());
		teacherQuery.setOrderBy("sort");

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);

		/**
		 * 讲师类别
		 */
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());


		mav.addObject("title", "融资租赁培训课程_名师讲堂");
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/list_commerce_org.html")
	public ModelAndView listCommerceOrg(HttpServletRequest request, RyxTeacherQuery teacherQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/clistCommerceOrg");

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);

		teacherQuery.setPageSize(DEFAULT_PAGE_SIZE);
		teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
		teacherQuery.setIdeleted(0);
		teacherQuery.setFlag(EnumTeacherType.ORG.getCode());

		ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
		errList = addList(errList, result.getErrorMsg());
		teacherQuery = result.getModule();
		mav.addObject("query", teacherQuery);

		/**
		 * 讲师类别
		 */
		mav.addObject("categorys", MetaHelper.getInstance().getOnlineCategory());


		mav.addObject("title", "金融培训教育讲师机构");
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/question_{id}.html")
	public ModelAndView question(
			HttpServletRequest request,
			@PathVariable Long id,
			RyxAnswerQuery query,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/cquestion");
		errList = new ArrayList<String>();
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setQid(id);
		query.setId(null);
		query.setOrderBy("agree");
		query.setSooort("desc");
		query.setStatus(EnumAuditStatus.APPROVED.getCode());
		query.setIdeleted(0);
		ResultDTO<RyxAnswerQuery> resultAnswer = ryxUserService.queryAnswer(query);
		mav.addObject("query", resultAnswer.getModule());
		RyxUsersDTO users = getRyxUser();
		ResultDTO<RyxQuestionDTO> result = ryxUserService.getQuestionById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("question", result.getModule());
		setListArticleObject(mav, errList);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	@RequestMapping("/my/question.html")
	public ModelAndView myQuestion(
			RyxQuestionQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cquestion");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxQuestionQuery> result = ryxUserService.queryQuestion(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		setListArticleObject(mav, errList);
		mav.addObject("na", "my_question");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/apply.html")
	public ModelAndView myApply(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		
		
		/**
		 * 活动开放时间  open time 
		 * 活动时间  > 全部 明天 本周 本周末 本月
		 */
		if(EnumIntervalType.THIS_MONTH.getCode() == query.getOinterval()){
			query.setTtstart(DateHelper.getTodayLong());
			query.setEtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.THIS_WEEK.getCode() == query.getOinterval()){
			query.setTtstart(DateHelper.getTodayLong());
			query.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.TOMORROW.getCode() == query.getOinterval()){
			System.out.println(DateHelper.getTomorrow());
			query.setTtstart(DateHelper.getTomorrowLong());
			query.setEtstart(DateHelper.getTomorrowLong(1));
		}
		else if(EnumIntervalType.THIS_WEEKEND.getCode() == query.getOinterval()){
			query.setTtstart(DateHelper.getWeekendLong());
			query.setEtstart(DateHelper.getWeekendLong(2));
		}
		else if(EnumIntervalType.THIS_MONTH_AFTER.getCode() == query.getOinterval()){
			query.setTtstart(DateHelper.getMonthendLong());
		}
		else if(EnumIntervalType.TODAY_BEFORE.getCode() == query.getOinterval()){
			query.setEtstart(DateHelper.getTodayLong());
		}
		
		/**
		 * 申请时间
		 */
		if(EnumInterval1Type.TODAY.getCode() == query.getAinterval()){
			query.setMstart(DateHelper.getTodayLongSecond());
		}
		else if(EnumInterval1Type.LAST_WEEK.getCode() == query.getAinterval()){
			query.setMstart(DateHelper.getTodayLongSecond(-7));
		}
		else if(EnumInterval1Type.LAST_MONTH.getCode() == query.getAinterval()){		
			query.setMstart(DateHelper.getTodayLongSecond(-30));
		}
		else if(EnumInterval1Type.LAST_MONTH_3.getCode() == query.getAinterval()){		
			query.setMstart(DateHelper.getTodayLongSecond(-90));
		}
		else if(EnumInterval1Type.LAST_MONTH_6.getCode() == query.getAinterval()){
			query.setMstart(DateHelper.getTodayLongSecond(-180));
		}
		else if(EnumInterval1Type.LAST_YEAR.getCode() == query.getAinterval()){
			query.setMstart(DateHelper.getTodayLongSecond(-365));
		}
		else if(EnumInterval1Type.LAST_YEAR_BEFORE.getCode() == query.getAinterval()){
			query.setMend(DateHelper.getTodayLongSecond(-365));
		}
		

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyApply");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_apply");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
		
	}
	
	
	@RequestMapping("/my/activity_apply.html")
	public ModelAndView myObjectApply(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyActivityApply");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();	
		mav.addObject("query", getApplyQuery(query));
		mav.addObject("na", "my_apply");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
		
	}
	
	
	@RequestMapping("/my/offline_apply.html")
	public ModelAndView myOfflineApply(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmyOfflineApply");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();	
		
		mav.addObject("query", getApplyQuery(query));
		mav.addObject("na", "my_apply");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
		
	}
	
	private RyxApplyQuery getApplyQuery(RyxApplyQuery query){
		
		query.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		
		return query;
		
	}
	
	
	@RequestMapping("/my/view_apply.html")
	public ModelAndView viewApply(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cviewApply");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();	
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setObjer(users.getId());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "view_apply");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/evalu.html")
	public ModelAndView myEvalu(
			RyxEvaluQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cevalu");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxEvaluQuery> result = ryxUserService.queryEvalu(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_evalu");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	@RequestMapping("/my/answer.html")
	public ModelAndView myAnswer(
			RyxAnswerQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/canswer");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		ResultDTO<RyxAnswerQuery> result = ryxUserService.queryAnswer(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		setListArticleObject(mav, errList);
		mav.addObject("na", "my_answer");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}	
	
	@RequestMapping("/my/balance.html")
	public ModelAndView myBalance(
			RyxUserCouponQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cbalance");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setType(EnumAccountType.MONEY.getCode());
		
		
		if(EnumInterval1Type.TODAY.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond());
		}
		else if(EnumInterval1Type.LAST_WEEK.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-7));
		}
		else if(EnumInterval1Type.LAST_MONTH.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-30));
		}
		else if(EnumInterval1Type.LAST_MONTH_3.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-90));
		}
		else if(EnumInterval1Type.LAST_MONTH_6.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-180));
		}
		else if(EnumInterval1Type.LAST_YEAR.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-365));
		}
		else if(EnumInterval1Type.LAST_YEAR_BEFORE.getCode() == query.getInterval()){
			query.setTend(DateHelper.getTodayLongSecond(-365));
		}
		
		
		
		query.setOrderBy("id");
		query.setSooort("desc");
		
		ResultDTO<RyxUserCouponQuery> result = ryxUserService.queryCoupon(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_balance");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/my/balance1.html")
	public ModelAndView myBalance1(
			RyxOrderDetailQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cbalance1");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
//		if(StringHelper.isNullOrEmpty(query.getYear())){
//			query.setYear("2020");
//		}
		
		query.setObjer(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		
		query.setOrderBy("detail_id");
		query.setSooort("desc");
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOamount(1);
		
		if(!StringHelper.isNullOrEmpty(query.getYear())){
			query.setStartTime(DateHelper.string2Date("yyyy-MM-dd", query.getYear()+"-01-01").getTime()/1000);
			query.setEndTime(DateHelper.string2Date("yyyy-MM-dd", (Integer.valueOf(query.getYear())+1)+"-01-01").getTime()/1000);
			
		}
		
		ResultDTO<RyxOrderDetailQuery> result = ryxOrderService.queryOrderDetail(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("total", ryxOrderService.sumTeacherOamount(query).getModule());
		mav.addObject("na", "my_balance");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
//	fjy 申请提现
	@RequestMapping("/t/withdraw.html")
	public ModelAndView tWithdraw(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ctWithdraw");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setOtype(EnumObjectType.getWithdrawModule().getCode());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setOrderBy("lcreate");
		query.setSooort("desc");
		ResultDTO<RyxApplyQuery> resultDTO = ryxUserService.queryWithdraw(query);
		
		mav.addObject("query",resultDTO.getModule());
		mav.addObject("na", "t_withdraw");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	

	/*fjy*/
	@RequestMapping("/t/account.html")
	public ModelAndView teacherAccount(HttpServletRequest request, RyxCourseQuery courseQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ctaccount");

		courseQuery.setPageSize(24);
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());

		courseQuery.setDisplay(1);
		courseQuery.setIdeleted(0);
		
		//courseQuery.setIdeleted(0);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		if(StringHelper.isNullOrEmpty(courseQuery.getOrderBy())){
			courseQuery.setOrderBy("update_time");
			courseQuery.setSooort("desc");
		}
		//courseQuery.setDisplay(1);
		courseQuery.setCuid(users.getId());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.queryCourse(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());
		courseQuery = courseResult.getModule();

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("query", courseQuery);
		mav.addObject("na", "t_account");

		return mav;

	}
	
	
	/*fjy*/
	@RequestMapping("/p/account.html")
	public ModelAndView partnerAccount(HttpServletRequest request, 
			RyxOrderDetailQuery query,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpaccount");

		query.setPartnerId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOrderBy("pay_time");
		query.setSooort("desc");
		
		ResultDTO<RyxOrderDetailQuery> orderDetailResultDTO = ryxOrderService.queryOrderDetail(query);
		errList = addList(errList, orderDetailResultDTO.getErrorMsg());
		query = orderDetailResultDTO.getModule();
		mav.addObject("query", query);

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		mav.addObject("na", "p_account");

		return mav;

	}
	
	
	@RequestMapping("/p/withdraw.html")
	public ModelAndView pwithdraw(
			RyxApplyQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpwithdraw");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setOtype(EnumObjectType.getWithdrawModule().getCode());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setOrderBy("lcreate");
		query.setSooort("desc");
		ResultDTO<RyxApplyQuery> resultDTO = ryxUserService.queryApply(query);
		
		mav.addObject("query",resultDTO.getModule());
		mav.addObject("na", "p_withdraw");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/c/sub_account.html")
	public ModelAndView subAccount(
			KeyrvQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/c/csubAccount");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setKey1(users.getId().toString());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		ResultDTO<KeyrvQuery> resultDTO = systemService.queryKeyrvSubAccount(query);
		
		mav.addObject("query",resultDTO.getModule());
		mav.addObject("na", "c_subaccount");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	@RequestMapping("/c/create_sub_account.html")
	public ModelAndView createSubAccount(
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/pc/c/ccreateSubAccount");
		errList = new ArrayList<String>();			
		mav.addObject("createDTO",new SubAccountDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		Integer avai = UserHelper.getInstance().getTotalSubAccountCount(users.getId()) -  UserHelper.getInstance().getUsedSubAccountCount(users.getId()) ;
		mav.addObject("avai", avai);
		
		return mav;	
		
	}
	
	
	@RequestMapping("/c/ajax_do_delete_sub_account.html")
	public void ajaxDoDeleteSubAccount(
			String rkey,
			HttpServletRequest request, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		RyxUsersDTO user = getRyxUser();	
		errList = new ArrayList<String>();
		KeyrvDTO keyrvDTO = new KeyrvDTO();
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
		keyrvDTO.setKey1(user.getId().toString());
		keyrvDTO.setRkey(rkey);
		ResultDTO<Boolean> result = systemService.deleteKeyrvByDTO(keyrvDTO);
		
		
		
		/**
		 * 删除课程
		 */
		RyxObjectLimitDTO  ryxObjectLimitDTO = new RyxObjectLimitDTO();
		ryxObjectLimitDTO.setUserId(Long.valueOf(rkey));
		ryxObjectLimitDTO.setMuserId(user.getId());
		ryxCourseService.deleteObjectLimitByMuserIdUserId(ryxObjectLimitDTO);
		
		
		if(result.isSuccess()){
			
			try{
			
			RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
			ryxUserExtQuery.setId(user.getId());
			ryxUserExtQuery.setSource("sd");
			
			
			RyxUserExtDTO mainRyxUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
			
//			 SdkContext.appKey = mainRyxUserExtDTO.getAppKey();
//		     SdkContext.appSecret = mainRyxUserExtDTO.getAppSecret();
//
//
//			// 测试环境调用地址：yufa.21tb.com
//	        // 正式环境调用地址：v4.21tb.com
//
//			if(ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()){
//				SdkContext.serverName = "v4.21tb.com";
//			}
//			else{
//				SdkContext.serverName = "yufa.21tb.com";
//			}
			
	        
			/**
			 * 不负流年:
					180F4BC38C1445E8A81FD5AA6381F88A
					45FD10F1658946E98CF4E763DCA11CCA
					ryx   admin      admin000 
					不负流年:
					测试环境的appkey 和appSecret
					不负流年:
					yufa.21tb.com测试地址
			 */
			
	        
	        RyxUserExtDTO ryxUserExtDTO = ryxUserService.getUserExtById(Long.parseLong(rkey)).getModule();
	        RyxUsersDTO ryxUsersDTO = ryxUserService.getUserById(Long.parseLong(rkey)).getModule();
	        
	        ryxUserService.deleteUserExtById(Long.parseLong(rkey));
	        
			 //人员同步DEMO 将同步一名学员
//	        List<OpenUser> shidaiUsers = new ArrayList<OpenUser>();
//	        OpenUser shidaiUser = new OpenUser();
//	        //*****必须字段*****
//	        shidaiUser.setAccountStatus("FORBIDDEN"); //人员状态： 激活=ENABLE, 冻结=FORBIDDEN
//	        shidaiUser.setCorpCode(ryxUserExtDTO.getCorpCode());
//	        shidaiUser.setEmployeeCode(ryxUserExtDTO.getUsername()); //员工编号，唯一识别
//	        shidaiUser.setLoginName(ryxUsersDTO.getUsername());    //登录名，唯一
//	        shidaiUser.setOrganizeCode("*"); //所在组织的组织编号，必须为现有组织
//	        shidaiUser.setUserName(ryxUsersDTO.getUsername());  //姓名
//	        //shidaiUser.setRank("Manager");
//	        //shidaiUser.setInWorkTime(new Date());
//	        //*****必需字段完毕*****
//	        //设置密码 注意：若此用户已经存在于系统，那么后续更新操作若设置的password字段，则将更新此人密码，
//	        //注意做MD5转码后，转成小写
//	        //user.setPassword(Md5Util.GetMD5Code(password).toLowerCase());
//	        OpenUserService userService = ServiceManager.getService(OpenUserService.class);
//	        shidaiUsers.add(shidaiUser);
//	        //调用人员数据同步方法 第二个字段的意义为： 是否为第一次登陆的新员工弹出修改密码功能，若已经做了
//	        //单点登录集成则可以设为false
//	        userService.syncUsers(shidaiUsers, true);
	        
			}
			catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	@RequestMapping("/c/ajax_do_update_password.html")
	public void ajaxDoUpdatePassword(
			@RequestParam("password") String password,
			@RequestParam("id") Long id,
			HttpServletRequest request, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		password = Md5Util.GetMD5Code(password); 
		ResultDTO<Boolean> result = ryxUserService.updatePasswordById(password, id);
		if(result.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}
	}
	
	@RequestMapping("/c/ajax_do_auth_online_2_sub_account.html")
	public void ajaxDoAuthOnline2SubAccount(
			Long courseId,
			String accounts,
			HttpServletRequest request, 
			WorkExperienceDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		
		String corpCode = "" ;
		List<String> authUserList=new ArrayList<String>();

		try {
			
			
			
			if(courseId == 0L || null == courseId){
				writeAjax(response, false,"请选择授权的课程/VIP卡");
				return ;
			}
			if(StringHelper.isNullOrEmpty(accounts)){
				writeAjax(response, false,"请选择授权的子账号");
				return ;
			}
			RyxUsersDTO user = getRyxUser();
			
				
			String[] accountArray = accounts.split(",");
			
			RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
			objectLimitQuery.setUserId(user.getId());
			objectLimitQuery.setOid(courseId);
			ResultDTO<RyxObjectLimitDTO> objectLimitResult = ryxCourseService.queryObjectLimitByOou(objectLimitQuery);
			RyxObjectLimitDTO objectLimitDTO1 = objectLimitResult.getModule();
			
			addList(errList, objectLimitResult.getErrorMsg());

			RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(courseId);
			
			for(String account : accountArray){
				
				if(!StringHelper.isNullOrEmpty(account)){
					
					/**
					 * 对每一个账号进行判断，是否是有效账户，防止客户端进行修改
					 */
					KeyrvQuery keyrvQuery = new KeyrvQuery();
					keyrvQuery.setKey1(user.getId().toString());
					keyrvQuery.setRkey(account);
					keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
					ResultDTO<KeyrvDTO> result = systemService.querySingleKeyrv(keyrvQuery);
					
					if(result.isSuccess() && null !=result.getModule()){
						
						/**
						 * 在 objectLimit 为子账号创建记录
						 */
						
						
						/**
						 * 对于课程而言 ，只能针对主课程进行授权 
						 * 根据主课程获取未过期的子课程
						 */
						RyxObjectLimitQuery objLimitQuery = new RyxObjectLimitQuery();
						objLimitQuery.setMoid(courseId);
						//objLimitQuery.setOtype(EnumObjectType.ONLINE_MODULE.getCode());//这个地方注释掉，因为有可能是VIP卡的授权
						objLimitQuery.setUserId(user.getId());
						objLimitQuery.setSlimi(System.currentTimeMillis()/1000);
						objLimitQuery.setPageSize(Integer.MAX_VALUE);
						
						
						List<RyxObjectLimitDTO> list = ryxCourseService.queryObjectLimit(objLimitQuery).getModule().getList();
						
						//根据主课程（课程卡），找不到对应的子课程，则拷贝元数据（课程卡）
						if(null == list || list.size() == 0){							
							RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
							objectLimitDTO.setOid(courseId);
							objectLimitDTO.setUserId(Long.parseLong(account));
							objectLimitDTO.setOtype(courseDTO.getObjType());
							objectLimitDTO.setCategory(courseDTO.getCategory());
							objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
							objectLimitDTO.setLimi(objectLimitDTO1.getLimi());
							objectLimitDTO.setSort(objectLimitDTO1.getSort());
							objectLimitDTO.setMuserId(user.getId());
							ResultDTO<Long> resultDTO = ryxCourseService.createOrUpdateUndueObjectLimit(objectLimitDTO);
							addList(errList, resultDTO.getErrorMsg());
						}
						else{
							//是主课程 ，拷贝对应的子课程到子账号下
							for(RyxObjectLimitDTO objectLimitDTO : list){
								objectLimitDTO.setUserId(Long.parseLong(account));
								objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
								objectLimitDTO.setMuserId(user.getId());
								ResultDTO<Long> resultDTO = ryxCourseService.createOrUpdateUndueObjectLimit(objectLimitDTO);
								addList(errList, resultDTO.getErrorMsg());
							}
						}
					}
				}
			}
			
			if(errList.size() == 0){
				writeAjax(response, true,"课程授权成功");
			}
			else{
				writeAjax(response, false,StringHelper.list2HtmlString(errList));
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/c/ajax_cancel_auth_online_2_sub_account.html")
	public void ajaxDoAuthOnline2SubAccount(
			Long moid,			 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			
			RyxUsersDTO userDTO = getRyxUser();
			
			RyxCourseDTO ryxCourseDTO = CourseHelper.getInstance().getCourseById(moid);
			
			RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
			objectLimitDTO.setMoid(moid);
			objectLimitDTO.setMuserId(userDTO.getId());
			ResultDTO<Boolean> resultDTO = ryxCourseService.deleteObjectLimitByMuserIdMoid(objectLimitDTO);
			addList(errList, resultDTO.getErrorMsg());
			
			if(errList.size() == 0){
				writeAjax(response, true,"取消赠送成功");
			}
			else{
				writeAjax(response, false,StringHelper.list2HtmlString(errList));
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	
	@RequestMapping("/c/ajax_do_auth_ecourse_2_sub_account.html")
	public void ajaxDoAuthEcourse2SubAccount(
			Long ecid,
			Long ecid1,
			Integer category,
			String accounts,
			HttpServletRequest request, 
			WorkExperienceDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			if(ecid1 == 0L || null == ecid1){
				writeAjax(response, false,"请选择授权的课程/VIP卡");
				return ;
			}
			if(StringHelper.isNullOrEmpty(accounts)){
				writeAjax(response, false,"请选择授权的子账号");
				return ;
			}
			RyxUsersDTO user = getRyxUser();	
			String[] accountArray = accounts.split(",");
			
			
			List<KeyrvDTO> listOnlineMain = CourseHelper.getInstance().getSubCoursesByMainCourse(ecid1);
			
			
			for(String account : accountArray){				
				
				
				
				if(!StringHelper.isNullOrEmpty(account)){
					
					
					
					/**
					 * 对每一个账号进行判断，是否是有效账户，防止客户端进行修改
					 */
					KeyrvQuery keyrvQuery = new KeyrvQuery();
					keyrvQuery.setKey1(user.getId().toString());
					keyrvQuery.setRkey(account);
					keyrvQuery.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
					ResultDTO<KeyrvDTO> result = systemService.querySingleKeyrv(keyrvQuery);
					
					if(result.isSuccess() && null !=result.getModule()){
						
						
						/**
						 * user ecouse 创建记录
						 */
						RyxUserEcourseDTO userEcourseDTO = new RyxUserEcourseDTO();
						userEcourseDTO.setUserId(Long.parseLong(account));
						userEcourseDTO.setEcid(ecid);
						userEcourseDTO.setEcid1(ecid1);
						userEcourseDTO.setCategory(category);
						userEcourseDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
					
						ryxCourseService.createUserEcourse(userEcourseDTO);
						
						
						/**
						 * 在 objectLimit 为子账号创建记录
						 */						
						
						for(KeyrvDTO keyrv:listOnlineMain){
							
							Long courseId = Long.parseLong(keyrv.getRkey());
						
							/**
							 * 对于课程而言 ，只能针对主课程进行授权 
							 * 根据主课程获取未过期的子课程
							 */
							RyxObjectLimitQuery objLimitQuery = new RyxObjectLimitQuery();
							objLimitQuery.setMoid(courseId);
							//objLimitQuery.setOtype(EnumObjectType.ONLINE_MODULE.getCode());//这个地方注释掉，因为有可能是VIP卡的授权
							objLimitQuery.setUserId(user.getId());
							objLimitQuery.setSlimi(System.currentTimeMillis()/1000);
							objLimitQuery.setPageSize(Integer.MAX_VALUE);
							
							List<RyxObjectLimitDTO> list = ryxCourseService.queryObjectLimit(objLimitQuery).getModule().getList();
							
							
							//是主课程 ，拷贝对应的子课程到子账号下
							for(RyxObjectLimitDTO objectLimitDTO : list){
								objectLimitDTO.setUserId(Long.parseLong(account));
								objectLimitDTO.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());
								ResultDTO<Long> resultDTO = ryxCourseService.createOrUpdateUndueObjectLimit(objectLimitDTO);
								addList(errList, resultDTO.getErrorMsg());
							}
							
						}
						
					}
				}
			}
			if(errList.size() == 0){
				writeAjax(response, true,"课程授权成功");
			}
			else{
				writeAjax(response, false,StringHelper.list2HtmlString(errList));
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/c/do_create_sub_account.html")
	public ModelAndView doCreateSubAccount(
			@Valid @ModelAttribute("createDTO") SubAccountDTO subAccountDTO, 
			BindingResult bindingResult, 
			HttpServletRequest request,
			HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {

		RyxUsersDTO users = getRyxUser();

		
		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/c/ccreateSubAccount");

		Integer avai = UserHelper.getInstance().getTotalSubAccountCount(users.getId()) - UserHelper.getInstance().getUsedSubAccountCount(users.getId()) ;
		mav.addObject("avai", avai);
		
		if(users.getMobile().equals(subAccountDTO.getMobile())){
			errList.add("错误：不能将自己加为子账号");
		}
		else{
			if(avai > 0) {			
				if (!bindingResult.hasErrors()) {
		
					RyxUsersQuery query = new RyxUsersQuery();
					query.setMobile(subAccountDTO.getMobile());
					ResultDTO<RyxUsersDTO> emailResult = ryxUserService.getUserByUsername(subAccountDTO.getUsername());
					RyxUsersDTO user = emailResult.getModule();
					
					if (null != user) {
						errList.add("该用户名已经存在，不能创建子账号");
					}
					
					if(!StringHelper.isNullOrEmpty(subAccountDTO.getMobile())){
						emailResult = ryxUserService.getUserByMobile(subAccountDTO.getMobile());
						user = emailResult.getModule();
						if (null != user) {
							errList.add("该手机号码已经存在，不能创建子账号");
						}
					}
					
					if(null == errList || errList.size() == 0){
		
						user = new RyxUsersDTO();
						String password = "12345678";
						user.setPassword(Md5Util.GetMD5Code(password));
						user.setMobile(StringHelper.isNullOrEmpty(subAccountDTO.getMobile()) ? null : subAccountDTO.getMobile());
						user.setUsername(subAccountDTO.getUsername());
						user.setFlag(EnumUserLevel.SUB_USER.getCode());
		
						ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
						errList = addList(errList, createUserResult.getErrorMsg());
		
						if (createUserResult.isSuccess()) {
							
								
							Long userId = createUserResult.getModule();
							
							KeyrvDTO keyrvDTO = new KeyrvDTO();
							keyrvDTO.setKey1(users.getId().toString());
							keyrvDTO.setRkey(userId.toString());
							keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
							ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(keyrvDTO);
							addList(errList, resultDTO.getErrorMsg());
							if(resultDTO.isSuccess()){
								errList.add("创建子账号成功");
							}							
							
							
							/**
							 * 同步数据到时代光华学院 
							 */
							
							RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
							ryxUserExtQuery.setId(users.getId());
							ryxUserExtQuery.setSource("sd");
							RyxUserExtDTO ryxUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
							
							if(null == ryxUserExtDTO){
								//errList.add("no userExt exist in " + users.getId());
							}
							else{
								
								String uid = ryxUserExtDTO.getCorpCode() + "_" + subAccountDTO.getUsername();
								RyxUserExtDTO subRyxUserExtDTO = new RyxUserExtDTO();
								BeanHelper.copyBean(ryxUserExtDTO, subRyxUserExtDTO);
								subRyxUserExtDTO.setId(createUserResult.getModule());
								subRyxUserExtDTO.setUsername(subAccountDTO.getUsername());
								subRyxUserExtDTO.setImain(0);
								subRyxUserExtDTO.setUid(uid);
								ResultDTO<Boolean> createExtResultDTO = ryxUserService.createUserExt(subRyxUserExtDTO);
								

//						        SdkContext.appKey = ryxUserExtDTO.getAppKey();
//						        SdkContext.appSecret = ryxUserExtDTO.getAppSecret();
//
						        
								if(createExtResultDTO.isSuccess()){
								
								
									// 测试环境调用地址：yufa.21tb.com
							        // 正式环境调用地址：v4.21tb.com
//									if(ConstHelper.isFormalEnvironment()){
//										SdkContext.serverName = "v4.21tb.com";
//									}
//									else{
//										SdkContext.serverName = "yufa.21tb.com";
//									}
//
							        
									/**
									 * 不负流年:
											180F4BC38C1445E8A81FD5AA6381F88A
											45FD10F1658946E98CF4E763DCA11CCA
											ryx   admin      admin000 
											不负流年:
											测试环境的appkey 和appSecret
											不负流年:
											yufa.21tb.com测试地址
									 */
									
									 //人员同步DEMO 将同步一名学员
//							        List<OpenUser> shidaiUsers = new ArrayList<OpenUser>();
//							        OpenUser shidaiUser = new OpenUser();
//							        //*****必须字段*****
//							        shidaiUser.setAccountStatus("ENABLE"); //人员状态： 激活=ENABLE, 冻结=FORBIDDEN
//							        shidaiUser.setCorpCode(ryxUserExtDTO.getCorpCode());
//							        shidaiUser.setEmployeeCode(uid); //员工编号，唯一识别
//							        shidaiUser.setLoginName(subAccountDTO.getUsername());    //登录名，唯一
//							        shidaiUser.setOrganizeCode("*"); //所在组织的组织编号，必须为现有组织
//							        shidaiUser.setUserName(subAccountDTO.getUsername());  //姓名
//							        //shidaiUser.setRank("Manager");
//							        shidaiUser.setInWorkTime(new Date());
//							        //*****必需字段完毕*****
//							        //设置密码 注意：若此用户已经存在于系统，那么后续更新操作若设置的password字段，则将更新此人密码，
//							        //注意做MD5转码后，转成小写
//							        user.setPassword(Md5Util.GetMD5Code(password).toLowerCase());
//							        OpenUserService userService = ServiceManager.getService(OpenUserService.class);
//							        shidaiUsers.add(shidaiUser);
//							        //调用人员数据同步方法 第二个字段的意义为： 是否为第一次登陆的新员工弹出修改密码功能，若已经做了
//							        //单点登录集成则可以设为false
//							        userService.syncUsers(shidaiUsers, true);
								}
								else{
									errList.add(createExtResultDTO.getErrorMsg());
								}
					        
							}
							
							ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
							errList = addList(errList, createUserResult.getErrorMsg());
							if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
							
		
								/**
								 * 注册成功送代金券
								 */
								if(null != ConstHelper.REGISTER_COUPON && ConstHelper.REGISTER_COUPON > 0){
									RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
									userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
									userCouponDTO.setUserId(userId);
									userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"代金券");
									userCouponDTO.setCreaterId(userId);
									ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
									addList(errList, result.getErrorMsg());
								}
								
								if(null != ConstHelper.REGISTER_SCORE && ConstHelper.REGISTER_SCORE > 0){
									RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
									userCouponDTO.setCoupon(ConstHelper.REGISTER_SCORE);//
									userCouponDTO.setUserId(userId);
									userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"积分");
									userCouponDTO.setCreaterId(userId);
									ResultDTO<Boolean> result = ryxUserService.addUserScore(userCouponDTO);
									addList(errList, result.getErrorMsg());
								}
		
							} else {
								errList.add("无效的用户Id");
							}
						} else {
							errList.add(createUserResult.getErrorMsg());
						}
					}
				} 
			}
			else{
				errList.add("贵机构已经没有可用的子账号名额，请<a href=\"/c/sub_account.html\">立即购买></a>");
			}
		}
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		// mav.addObject("createBindingResult", bindingResult);

		return mav;


	}
	
	
	
//	fjy 提交申请提现请求
	@RequestMapping("/my/ajax_do_withdraw_apply.html")
	public void ajaxDoWithdrawApply(
			RyxApplyDTO dto ,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		
		Double amount = ryxUserService.getUserBalance1ById(users.getId()).getModule();
		
		if(amount < 100){
			writeAjax(response, false , "必须满100才可以提现");
			return ;
		}
		
		dto.setAmount(amount);
		
		RyxApplyQuery applyQuery = new RyxApplyQuery();
		applyQuery.setUserId(users.getId());
		applyQuery.setOtype(EnumObjectType.WITHDRAW_MODULE.getCode());
		applyQuery.setStatus(EnumAuditStatus.UNAUDITED.getCode());
		ResultDTO<RyxApplyQuery> list = ryxUserService.queryWithdraw(applyQuery);
		if (list.isSuccess() && list.getModule().getList().size() > 0) {
			writeAjax(response, false , "您还有未完成的提现申请,不能重复提交!");
			return ;
		}else {
			dto.setOtype(EnumObjectType.getWithdrawModule().getCode());
			dto.setUserId(users.getId());
			dto.setStatus(EnumAuditStatus.getUnaudited().getCode());
			dto.setOtype(EnumObjectType.getWithdrawModule().getCode());
			dto.setOid(users.getId());
			ResultDTO<Long> resultDTO = ryxUserService.createWithdraw(dto);
			if (resultDTO.isSuccess()) {
				writeAjax(response, true);
			}else {
				writeAjax(response, false , "申请提交失败!");
			}
		}
		
	}
	@RequestMapping("/t/myorder.html")
	public ModelAndView tmyOrder(
			RyxOrderDetailQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ctOrder");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setObjer(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOrderBy("pay_time");
		query.setSooort("desc");
		
		ResultDTO<RyxOrderDetailQuery> orderDetailResultDTO = ryxOrderService.queryOrderDetail(query);
		errList = addList(errList, orderDetailResultDTO.getErrorMsg());
		query = orderDetailResultDTO.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_torder");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;
		
	}
	
	
	@RequestMapping("/p/myorder.html")
	public ModelAndView pmyOrder(
			RyxOrderDetailQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cporder");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setPartnerId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOrderBy("pay_time");
		query.setSooort("desc");
		
		ResultDTO<RyxOrderDetailQuery> orderDetailResultDTO = ryxOrderService.queryOrderDetail(query);
		errList = addList(errList, orderDetailResultDTO.getErrorMsg());
		query = orderDetailResultDTO.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_porder");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;
		
	}
	
	
	
	@RequestMapping("/my/iusers.html")
	public ModelAndView myInviteUsers(
			RyxUsersQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ciusers");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setOrderBy("id");
		query.setSooort("desc");
		query.setSid(users.getId());
		
		ResultDTO<RyxUsersQuery> resultDTO = UserHelper.getInstance().queryUserCache(query);
		errList = addList(errList, resultDTO.getErrorMsg());
		query = resultDTO.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_iusers");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;
		
	}
	
	
	
	@RequestMapping("/my/score.html")
	public ModelAndView myScore(
			RyxUserCouponQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cscore");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setType(EnumAccountType.SCORE.getCode());
		
		
		if(EnumInterval1Type.TODAY.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond());
		}
		else if(EnumInterval1Type.LAST_WEEK.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-7));
		}
		else if(EnumInterval1Type.LAST_MONTH.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-30));
		}
		else if(EnumInterval1Type.LAST_MONTH_3.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-90));
		}
		else if(EnumInterval1Type.LAST_MONTH_6.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-180));
		}
		else if(EnumInterval1Type.LAST_YEAR.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-365));
		}
		else if(EnumInterval1Type.LAST_YEAR_BEFORE.getCode() == query.getInterval()){
			query.setTend(DateHelper.getTodayLongSecond(-365));
		}
		
		query.setOrderBy("id");
		query.setSooort("desc");
		
		ResultDTO<RyxUserCouponQuery> result = ryxUserService.queryCoupon(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		mav.addObject("na", "my_score");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/my/coupon.html")
	public ModelAndView myCoupon(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccoupon");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("na", "my_coupon");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/my/expired_coupon.html")
	public ModelAndView myExpiredCoupon(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cexpiredCoupon");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("na", "my_expired_coupon");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/my/used_coupon.html")
	public ModelAndView myUsedCoupon(
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cusedCoupon");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		mav.addObject("na", "my_used_coupon");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/my/follow_question.html")
	public ModelAndView myFollowQuestion(
			KeyrvQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cfollowQuestion");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		query.setKey1(users.getId().toString());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setType(EnumKeyRelatedValueType.KV_FOLLOW_QUESTION.getCode());
		ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
		errList = addList(errList, result.getErrorMsg());
		query = result.getModule();
		mav.addObject("query", query);
		setListArticleObject(mav, errList); 
		mav.addObject("na", "my_follow_question");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/my/exper.html")
	public ModelAndView myExper(
			WorkExperienceQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cexper");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();		
		query.setUserId(users.getId());
		query.setPageSize(Integer.MAX_VALUE);
		query.setType(EnumWorkExperType.WORK_EXPER.getCode());
		query.setOrderBy("lstart");
		query.setSooort("desc");
		ResultDTO<List<WorkExperienceDTO>> result = userService.queryWorkExperience(query);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("list", result.getModule());
		setListArticleObject(mav, errList);
		mav.addObject("na", "my_exper");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_exper.html")
	public ModelAndView createExper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateExper");
		errList = new ArrayList<String>();		
		setCreateExperObject(mav,errList);		
		mav.addObject("createDTO",new WorkExperienceDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	private void setCreateExperObject(ModelAndView mav,ArrayList<String> errList){
		
	}
	
	private void setResumeObject(ModelAndView mav,ArrayList<String> errList,Long userId){
		
		

		ResultDTO<ResumeDTO> resumeResult = userService.queryResumeByUserId(userId);
		errList = addList(errList, resumeResult.getErrorMsg());
		mav.addObject("createDTO", resumeResult.getModule());
		
		
		/**
		 * 工作经历
		 */
		WorkExperienceQuery query = new WorkExperienceQuery();
		query.setUserId(userId);
		query.setType(EnumWorkExperType.WORK_EXPER.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		query.setOrderBy("lstart");
		query.setSooort("desc");
		ResultDTO<List<WorkExperienceDTO>> result = userService.queryWorkExperience(query);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("workList", result.getModule());
		
		
		/**
		 * 学习经历
		 */
		query = new WorkExperienceQuery();
		query.setUserId(userId);
		query.setType(EnumWorkExperType.STUDY_EXPER.getCode());
		query.setPageSize(Integer.MAX_VALUE);
		query.setOrderBy("lstart");
		query.setSooort("desc");
		result = userService.queryWorkExperience(query);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("studyList", result.getModule());
		
		mav.addObject("errList", errList);
		mav.addObject("na", "my_resume");
	}
	
	@RequestMapping("/my/update_exper.html")
	public ModelAndView updateExper(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateExper");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateExperObject(mav,errList);		
		ResultDTO<WorkExperienceDTO> result = userService.queryWorkExperienceById(id);
		errList = addList(errList, result.getErrorMsg());
		if(!result.getModule().getUserId().equals(users.getId())){
			throw new Exception("invalid input");
		}
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	@RequestMapping("/my/do_create_exper.html")
	public ModelAndView doCreateExper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") WorkExperienceDTO experDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateExper");		

		RyxUsersDTO usersDTO = getRyxUser();
		errList = new ArrayList<String>();
		setCreateExperObject(mav,errList);
		mav.addObject("na", "create_exper");
		try {
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				experDTO.setCreater(usersDTO.getId());
				experDTO.setUserId(usersDTO.getId());
				experDTO.setType(EnumWorkExperType.WORK_EXPER.getCode());
				ResultDTO<Boolean> result = userService.createWorkExperience(experDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
					/**
					 * 更新 pdf 
					 */
					updateResumePdf(UserHelper.getInstance().getUserIdEncString(usersDTO.getId()));
				}
			}


		} catch (Throwable t) {
			errList.add(t.toString());
		}
		mav.addObject("na", "create_exper");
		mav.addObject("errList", errList);
		
		mav.addObject("loginUsers", usersDTO);
		return mav;

	}
	
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_study_exper.html")
	public ModelAndView createStudyExper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateStudyExper");
		errList = new ArrayList<String>();		
		setCreateExperObject(mav,errList);		
		mav.addObject("createDTO",new WorkExperienceDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_study_exper.html")
	public ModelAndView updateStudyExper(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateStudyExper");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateExperObject(mav,errList);		
		ResultDTO<WorkExperienceDTO> result = userService.queryWorkExperienceById(id);
		errList = addList(errList, result.getErrorMsg());
		if(!result.getModule().getUserId().equals(users.getId())){
			throw new Exception("invalid input");
		}
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	@RequestMapping("/my/do_create_study_exper.html")
	public ModelAndView doCreateStudyExper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.StudyExperienceDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateStudyExper");		

		RyxUsersDTO usersDTO = getRyxUser();
		errList = new ArrayList<String>();
		setCreateExperObject(mav,errList);
		mav.addObject("na", "create_study_exper");
		try {
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				dto.setCreater(usersDTO.getId());
				dto.setUserId(usersDTO.getId());
				dto.setType(EnumWorkExperType.STUDY_EXPER.getCode());
				WorkExperienceDTO  workExperienceDTO = new WorkExperienceDTO();
				org.springframework.beans.BeanUtils.copyProperties(dto, workExperienceDTO, BeanHelper.getNullPropertyNames(dto));
				ResultDTO<Boolean> result = userService.createWorkExperience(workExperienceDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
				updateResumePdf(UserHelper.getInstance().getUserIdEncString(usersDTO.getId()));
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}
		mav.addObject("na", "create_study_exper");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", usersDTO);

		return mav;

	}
	
	@RequestMapping("/my/update_evalu.html")
	public ModelAndView updateEvalu(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateEvalu");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();		
		setCreateExperObject(mav,errList);		
		ResultDTO<RyxEvaluDTO> result = ryxUserService.getEvaluById(id);
		errList = addList(errList, result.getErrorMsg());
		if(!result.getModule().getUserId().equals(users.getId())){
			throw new Exception("invalid input");
		}
		mav.addObject("na", "my_evalu");
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	@RequestMapping("/my/do_update_profile.html")
	public ModelAndView doUpdateProfile(HttpServletRequest request, @Valid @ModelAttribute("createDTO") ProfileDTO profileDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cprofile");

		errList = new ArrayList<String>();

		RyxUsersDTO loginUsersDTO = getRyxUser();
		mav.addObject("loginUsers", loginUsersDTO);
		profileDTO.setId(loginUsersDTO.getId());		
		mav.addObject("createBindingResult", bindingResult);

		if (!bindingResult.hasErrors()) {
			RyxUsersDTO users = new RyxUsersDTO();
			try {
				BeanUtils.copyProperties(users, profileDTO);
				ResultDTO<Boolean> result = ryxUserService.updateUserById(users);
				errList = addList(errList, result.getErrorMsg());
			} catch (IllegalAccessException e) {
				errList = addList(errList, e.getMessage());
			} catch (InvocationTargetException e) {
				errList = addList(errList, e.getMessage());
			}
			if(errList.size() ==0 ){
				errList.add("修改成功");
			}
		}
		
		

		mav.addObject("errList", errList);
		mav.addObject("isPost", true);
		mav.addObject("na", "my_profile");

		return mav;

	}
	@RequestMapping("/t/do_update_profile.html")
	public ModelAndView doUpdateProfile(HttpServletRequest request, @Valid @ModelAttribute("createDTO") RyxTeacherDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ctProfile");

		errList = new ArrayList<String>();

		RyxUsersDTO loginUsersDTO = getRyxUser();
		mav.addObject("loginUsers", loginUsersDTO);
		dto.setUid(loginUsersDTO.getId());		
		mav.addObject("createBindingResult", bindingResult);

		if (!bindingResult.hasErrors()) {
			RyxTeacherDTO dto2 = new RyxTeacherDTO();
			try {
				BeanUtils.copyProperties(dto2, dto);
				ResultDTO<Boolean> result = ryxTeacherService.updateTeacher(dto);
				errList = addList(errList, result.getErrorMsg());
				result = ryxUserService.updateUserPic(loginUsersDTO.getId(),dto.getImageUrl());
			} catch (IllegalAccessException e) {
				errList = addList(errList, e.getMessage());
			} catch (InvocationTargetException e) {
				errList = addList(errList, e.getMessage());
			}
			if(errList.size() ==0 ){
				errList.add("修改成功");
			}
		}
		
		

		mav.addObject("errList", errList);
		mav.addObject("isPost", true);
		mav.addObject("na", "my_tprofile");

		return mav;

	}
	
	@RequestMapping("/my/do_update_study_exper.html")
	public ModelAndView doUpdateStudyExper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.StudyExperienceDTO experDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateExper");
		
		errList = new ArrayList<String>();
		setCreateExperObject(mav,errList);
		mav.addObject("na", "create_exper");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				experDTO.setUserId(usersDTO.getId());	
				WorkExperienceDTO  workExperienceDTO = new WorkExperienceDTO();
				org.springframework.beans.BeanUtils.copyProperties(experDTO, workExperienceDTO, BeanHelper.getNullPropertyNames(experDTO));
				ResultDTO<Boolean> result = userService.updateWorkExperience(workExperienceDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功");
				}

				updateResumePdf(UserHelper.getInstance().getUserIdEncString(usersDTO.getId()));
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_exper.html")
	public ModelAndView doUpdateMyExper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") WorkExperienceDTO experDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateExper");
		
		errList = new ArrayList<String>();
		setCreateExperObject(mav,errList);
		mav.addObject("na", "create_exper");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				experDTO.setUserId(usersDTO.getId());			
				ResultDTO<Boolean> result = userService.updateWorkExperience(experDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功");
					updateResumePdf(UserHelper.getInstance().getUserIdEncString(usersDTO.getId()));
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/my/do_update_evalu.html")
	public ModelAndView doUpdateEvalu(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxEvaluDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateEvalu");
		
		errList = new ArrayList<String>();
		setCreateExperObject(mav,errList);
		mav.addObject("na", "my_evalu");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{				
				dto.setUserId(usersDTO.getId());	
				dto.setStatus(EnumAuditStatus.getUnaudited().getCode() == dto.getStatus() ? 
						EnumAuditStatus.getUnaudited().getCode():EnumAuditStatus.getReunaudited().getCode() );
				ResultDTO<Boolean> result = ryxUserService.updateEvalu(dto);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功，请耐心等待审核");
				}
			}
		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/ajax_update_exper.html")
	public void ajaxUpdateExper(
			HttpServletRequest request, 
			WorkExperienceDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = userService.updateWorkExperience(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/ajax_close_left_ad.html")
	public void ajaxCloseLeftAd(
			HttpServletRequest request, 
			WorkExperienceDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			CookieHelper.removeCookies(SessionHelper.IS_CLOSE_LEFT_COOKIE, "/", request, response);
			CookieHelper.addCookie(response, SessionHelper.IS_CLOSE_LEFT_COOKIE, "1", ConstHelper.CLOSE_AD_LEFT_DAYS * 24 * 60 * 60, "/");		
			writeAjax(response, true);
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/my/ajax_delete_exper.html")
	public void ajaxDeleteExper(
			HttpServletRequest request, 
			WorkExperienceDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = userService.deleteWorkExperience(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/my/do_ajax_update_message.html")
	public void ajaxUpdateMessage(
			HttpServletRequest request, 
			RyxMessageDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateMessage(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/my/do_ajax_delete_message.html")
	public void ajaxDeleteMessage(
			HttpServletRequest request, 
			RyxMessageDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.deleteMessage(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	
	@RequestMapping("/my/ajax_check_pay_password.html")
	public void ajaxCheckPayPassword(
			HttpServletRequest request, 
			String payPassword,
			String imgVerifyCode,
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			if(StringHelper.isNullOrEmpty(payPassword)){
				writeAjax(response, false,"请输入支付密码");
				return ;
			}
			
			if(StringHelper.isNullOrEmpty(imgVerifyCode)){
				writeAjax(response, false,"请输入图像验证码");
				return ;
			}
			
			if (!imgVerifyCode.toLowerCase()
					.equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
				writeAjax(response, false,"图像验证码无效");
				return ;
			}
			
			RyxUsersDTO user = getRyxUser();			
			String uid = StringHelper.getKeyvalueUid(user.getId().toString(), EnumAttrType.USER_PAY_PASSWORD.getId());
			ResultDTO<AttrValueDTO> result = systemService.queryAttrValueByUid(uid);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				AttrValueDTO attrValueDTO = result.getModule();
				if(null == attrValueDTO || StringHelper.isNullOrEmpty(attrValueDTO.getValue())){
					writeAjax(response, false,"请在会员中心设置支付密码");
					return ;
				}
				else{
					if(Md5Util.GetMD5Code(payPassword).equals(attrValueDTO.getValue())){
						writeAjax(response, true);
					}
					else{
						writeAjax(response, false,"支付密码不正确");
						return ;
					}
				}
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	
	@RequestMapping("/c/ajax_search_sub_account.html")
	public void ajaxSearchSubAccount(
			HttpServletRequest request, 
			@RequestParam(value = "username") String username, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
	

		List<RyxUsersDTO> returnList = new ArrayList<RyxUsersDTO>();
		
		RyxUsersDTO usersDTO = getRyxUser();		
		Map<String, String> map = new HashMap<String,String>();
		List<KeyrvDTO>  subList = UserHelper.getInstance().getSubAccountByUserId(usersDTO.getId());
		if(null != subList){
			for(KeyrvDTO keyrvDTO : subList){
				map.put(keyrvDTO.getRkey(), null);
			}
		}
		
		
		if(StringHelper.isNullOrEmpty(username)){
			if(null != subList){
				for(KeyrvDTO keyrvDTO : subList){
					returnList.add(UserHelper.getInstance().getUserById(Long.parseLong(keyrvDTO.getRkey())));
				}
			}
			writeAjax(response, true,returnList);
			return ;
		}
		
		RyxUsersQuery usersQuery = new RyxUsersQuery();
		usersQuery.setKeyword(username);
		usersQuery.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxUsersQuery> result = UserHelper.getInstance().queryUserCache(usersQuery);
		if(result.isSuccess()){
			List<RyxUsersDTO> list = result.getModule().getList();
			if(null != list){
				for(RyxUsersDTO user : list){
					if(map.containsKey(user.getId())){
						returnList.add(user);
					}
				}
			}
			writeAjax(response, true,returnList);
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
			return ;
		}
		
	}
	
	
	
	@RequestMapping("/m/my/ajax_check_pay_password.html")
	public void ajaxmCheckPayPassword(
			HttpServletRequest request, 
			String payPassword,
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			if(StringHelper.isNullOrEmpty(payPassword)){
				writeAjax(response, false,"请输入支付密码");
				return ;
			}
		
			
			RyxUsersDTO user = getRyxUser();			
			String uid = StringHelper.getKeyvalueUid(user.getId().toString(), EnumAttrType.USER_PAY_PASSWORD.getId());
			ResultDTO<AttrValueDTO> result = systemService.queryAttrValueByUid(uid);			
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				AttrValueDTO attrValueDTO = result.getModule();
				if(null == attrValueDTO || StringHelper.isNullOrEmpty(attrValueDTO.getValue())){
					writeAjax(response, false,"请在会员中心设置支付密码");
					return ;
				}
				else{
					if(Md5Util.GetMD5Code(payPassword).equals(attrValueDTO.getValue())){
						writeAjax(response, true);
					}
					else{
						writeAjax(response, false,"支付密码不正确");
						return ;
					}
				}
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/my/ajax_update_apply.html")
	public void ajaxUpdateApply(
			HttpServletRequest request, 
			RyxApplyDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateApply(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/my/ajax_delete_apply.html")
	public void ajaxDeleteApply(
			HttpServletRequest request, 
			RyxApplyDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.deleteApply(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/ajax_create_apply.html")
	public void ajaxCreateApply(
			HttpServletRequest request, 
			RyxApplyDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
				
			if(!StringHelper.isEmail(dto.getEmail())){
				writeAjax(response, false,"请输入正确的电子邮箱");
				return;
			}
			
			if(!StringHelper.isMobile(dto.getMobile())){
				writeAjax(response, false,"请输入正确的手机号码");
				return ;
			}
			
			dto.setCreater(0L);
			dto.setUserId(0L);
			ResultDTO<Long> result = ryxUserService.createApply(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}
		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/my/ajax_create_apply.html")
	public void ajaxMyCreateApply(
			HttpServletRequest request, 
			RyxApplyDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			
			RyxUsersDTO users = getRyxUser();
			
			dto.setCreater(users.getId());
			dto.setUserId(users.getId());
			ResultDTO<Long> result = ryxUserService.createApply(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}
		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	
	

	
	@RequestMapping("/my/ajax_update_evalu.html")
	public void ajaxUpdateEvalu(
			HttpServletRequest request, 
			RyxEvaluDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateEvalu(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/my/ajax_delete_evalu.html")
	public void ajaxDeleteEvalu(
			HttpServletRequest request, 
			RyxEvaluDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.deleteEvalu(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/my/message.html")
	public ModelAndView myMessage(
			RyxMessageQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cmessage");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		
		
		if(EnumInterval1Type.TODAY.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond());
		}
		else if(EnumInterval1Type.LAST_WEEK.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-7));
		}
		else if(EnumInterval1Type.LAST_MONTH.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-30));
		}
		else if(EnumInterval1Type.LAST_MONTH_3.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-90));
		}
		else if(EnumInterval1Type.LAST_MONTH_6.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-180));
		}
		else if(EnumInterval1Type.LAST_YEAR.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-365));
		}
		else if(EnumInterval1Type.LAST_YEAR_BEFORE.getCode() == query.getInterval()){
			query.setTend(DateHelper.getTodayLongSecond(-365));
		}
		
				
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);		
		ResultDTO<RyxMessageQuery> courseResult = ryxUserService.queryMessage(query);
		errList = addList(errList, courseResult.getErrorMsg());
		query = courseResult.getModule();
		mav.addObject("query", query);

		setListActivityObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_message");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	
	@RequestMapping("/my/unread_message.html")
	public ModelAndView myUnreadMessage(
			RyxMessageQuery query,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, ParseException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cunreadMessage");
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		
		if(EnumInterval1Type.TODAY.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond());
		}
		else if(EnumInterval1Type.LAST_WEEK.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-7));
		}
		else if(EnumInterval1Type.LAST_MONTH.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-30));
		}
		else if(EnumInterval1Type.LAST_MONTH_3.getCode() == query.getInterval()){		
			query.setTstart(DateHelper.getTodayLongSecond(-90));
		}
		else if(EnumInterval1Type.LAST_MONTH_6.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-180));
		}
		else if(EnumInterval1Type.LAST_YEAR.getCode() == query.getInterval()){
			query.setTstart(DateHelper.getTodayLongSecond(-365));
		}
		else if(EnumInterval1Type.LAST_YEAR_BEFORE.getCode() == query.getInterval()){
			query.setTend(DateHelper.getTodayLongSecond(-365));
		}
		
				
		query.setIread(EnumYesorno.NO.getCode());
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);		
		ResultDTO<RyxMessageQuery> courseResult = ryxUserService.queryMessage(query);
		errList = addList(errList, courseResult.getErrorMsg());
		query = courseResult.getModule();
		mav.addObject("query", query);

		setListActivityObject(mav, errList);

		mav.addObject("intervals", EnumIntervalType.getList());
		mav.addObject("na", "my_unread_message");
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;
		
	}
	
	
	@RequestMapping("/my/do_create_apply.html")
	public ModelAndView doCreateApply(
			@Valid @ModelAttribute("createDTO") RyxApplyDTO applyDTO, 
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					
					throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/capply");

		RyxUsersDTO user = getRyxUser();	
		applyDTO.setUserId(user.getId());
		
		if(!bindingResult.hasErrors()){
			RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(applyDTO.getOid());
			applyDTO.setObjer(courseDTO.getCuid());
			ResultDTO<Long> result = ryxUserService.createApply(applyDTO);
			errList = addList(errList, result.getErrorMsg());
			if(result.isSuccess()){
				return new ModelAndView("redirect:/my/save_apply_order.html?objId="+ applyDTO.getOid() +"&nbr=" + applyDTO.getNbr() +"&applyId=" + result.getModule());
			}
		}

		mav.addObject("createBindingResult",bindingResult);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	@RequestMapping("/my/ajax_is_reserve_offline.html")
	public void ajaxReserveOffline(
			RyxApplyQuery applyQuery, 
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					throws Exception {

		RyxUsersDTO user = getRyxUser();	
		applyQuery.setUserId(user.getId());
		
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(applyQuery);

		if (result.isSuccess() && result.getModule()==null) {
		} else {
			int yes = 0;
			int no = 0;
			List<RyxApplyDTO> list = result.getModule().getList();
			for (int i=0 ; i<list.size();i++) {
				RyxApplyDTO aDto = list.get(0);
				if (aDto.getStatus() == EnumOrderStatus.getPaid().getCode()) {
					yes += 1;
				}else {
					no += 1;
				}
			}
			int  sum = yes + no ;
			String msg = "";
			if(sum>0){
				msg = "您已为"+sum+"人报名该课程,"+yes +"人已支付,"+no+"人未支付<BR>确认再次报名？";
			}
			writeAjax(response, true,msg);
		}
		
	}
	
	
	@RequestMapping("/my/do_create_video_apply.html")
	public ModelAndView doCreateVideoApply(
			@ModelAttribute("createDTO") RyxApplyDTO applyDTO, 
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					
					throws Exception {

		errList = new ArrayList<String>();


		RyxUsersDTO user = getRyxUser();	
		applyDTO.setUserId(user.getId());
		
		RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(applyDTO.getOid());
		applyDTO.setObjer(courseDTO.getCuid());
		applyDTO.setNbr(1);
		ResultDTO<Long> result = ryxUserService.createApply(applyDTO);
		errList = addList(errList, result.getErrorMsg());
		if(result.isSuccess()){
			return new ModelAndView("redirect:/my/save_apply_order.html?objId="+ applyDTO.getOid() +"&nbr=" + applyDTO.getNbr() +"&applyId=" + result.getModule());
		}
		else{
			throw new Exception(result.getErrorMsg());
		}
	}
	@RequestMapping("/my/ajax_is_reserve.html")
	public void ajaxIsReserve(
			RyxApplyQuery applyQuery, 
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) 
					throws Exception {

		RyxUsersDTO user = getRyxUser();	
		applyQuery.setUserId(user.getId());
		ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(applyQuery);

		if (result.isSuccess() ) {
			applyQuery  = result.getModule();
			if(null == applyQuery.getList() || applyQuery.getList().size() == 0){
				writeAjax(response, true);
			}
			else{
				RyxApplyDTO applyDTO = (RyxApplyDTO) result.getModule().getList().get(0);
				writeAjax(response, false,"您已预约该直播"+ "("+EnumOrderStatus.parse(applyDTO.getStatus())+")" +",不能重复预约直播");
			}
		} else {
			writeAjax(response, false,result.getErrorMsg());
		}
		
	}
	
	@RequestMapping("/my/profile.html")
	public ModelAndView myProfile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cprofile");
		mav.addObject("loginUsers", users);

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("na", "my_profile");
		return mav;

	}
//	更改教师资料
	@RequestMapping("/t/profile.html")
	public ModelAndView tProfile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/ctProfile");
		mav.addObject("loginUsers", users);
		
		ResultDTO<RyxTeacherDTO> teacherResult = ryxTeacherService.getTeacherByUserId(users.getId());
		

		errList = addList(errList, teacherResult.getErrorMsg());
		mav.addObject("createDTO", teacherResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("na", "t_profile");
		
		return mav;

	}
	
	
	@RequestMapping("/my/account.html")
	public ModelAndView myAccount(HttpServletRequest request,RyxCourseQuery courseQuery,
			HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		if(UserHelper.getInstance().isTeacher(users)){
			return new ModelAndView("redirect:/t/account.html");
		}
		else if(UserHelper.getInstance().isPartner(users)){
			return new ModelAndView("redirect:/p/account.html");
		}
		ModelAndView mav = new ModelAndView("/ryx/pc/my/caccount");
		mav.addObject("loginUsers", users);

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());
		
		courseQuery.setUserId(users.getId());
		courseQuery.setPageSize(12);
		courseQuery.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		courseQuery.setFlag(EnumCourseType.MAIN_COURSE.getCode());
		courseQuery.setTnow(System.currentTimeMillis() / 1000);
		//courseQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse2(courseQuery);
		errList = addList(errList, courseResult.getErrorMsg());

		courseQuery = courseResult.getModule();
		mav.addObject("query", courseQuery);
		
		
		mav.addObject("errList", errList);
		mav.addObject("na", "my_account");
		return mav;

	}
	
	
	/**
	 * 我的推广佣金
	 * @param request
	 * @param partnerOrderQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/my/spread_order.html")
	public ModelAndView mySpreadOrder(HttpServletRequest request,RyxPartnerOrderQuery partnerOrderQuery,
			HttpServletResponse response, RedirectAttributes rt) 
			throws Exception {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cspreadOrder");
		mav.addObject("loginUsers", users);
		
		mav.addObject("encryptUserId", URLEncoder.encode(StringHelper.aesEncrypt(users.getId().toString(),RequestHelper.PARNTER_URL_SEED ), "UTF-8"));

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());	
		
		
		partnerOrderQuery.setPartnerId(users.getId());
		partnerOrderQuery = ryxOrderService.queryPartnerOrder(partnerOrderQuery).getModule();
		
		mav.addObject("query", partnerOrderQuery);
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "spread_order");
		return mav;

	}
	
	
	
	
	
	
	@RequestMapping("/my/spread_withdraw.html")
	public ModelAndView mySpreadWithdraw(HttpServletRequest request,RyxApplyQuery query ,
			HttpServletResponse response, RedirectAttributes rt) 
			throws Exception {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cspreadWithdraw");
		mav.addObject("loginUsers", users);
		
		mav.addObject("encryptUserId", URLEncoder.encode(StringHelper.aesEncrypt(users.getId().toString(),RequestHelper.PARNTER_URL_SEED ), "UTF-8"));

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());		
		
		query.setUserId(users.getId());
		
		query.setOtype(EnumObjectType.getWithdrawModule().getCode());
		query.setPageSize(DEFAULT_PAGE_SIZE1);
		query.setOrderBy("lcreate");
		query.setSooort("desc");

		query = ryxUserService.queryWithdraw(query).getModule();
		
		mav.addObject("query", query);
		
		mav.addObject("errList", errList);
		
		return mav;

	}
	
	
	/**
	 * 我的推广会员
	 * @param request
	 * @param partnerOrderQuery
	 * @param response
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/my/spread_user.html")
	public ModelAndView mySpreadUser(HttpServletRequest request,RyxUsersQuery ryxUsersQuery,
			HttpServletResponse response, RedirectAttributes rt) 
			throws Exception {
		
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cspreadUser");
		mav.addObject("loginUsers", users);
		
		mav.addObject("encryptUserId", URLEncoder.encode(StringHelper.aesEncrypt(users.getId().toString(),RequestHelper.PARNTER_URL_SEED ), "UTF-8"));

		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		mav.addObject("createDTO", userResult.getModule());		
		
		ryxUsersQuery.setSid(users.getId());

		ryxUsersQuery = ryxUserService.queryUser1(ryxUsersQuery).getModule();
		
		mav.addObject("query", ryxUsersQuery);
		
		mav.addObject("errList", errList);
		
		mav.addObject("na", "spread_order");
		return mav;

	}
	
	@RequestMapping("/my/resume.html")
	public ModelAndView myResume(HttpServletRequest request, 
			HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cresume");
		mav.addObject("loginUsers", users);		
		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());
		
		setResumeObject(mav, errList,users.getId());
		return mav;

	}
	
	@RequestMapping("/view_resume_{strId}.html")
	public ModelAndView viewResume(HttpServletRequest request, @PathVariable String strId,
			HttpServletResponse response, RedirectAttributes rt) 
			throws NumberFormatException, Exception {
		
		Long userId = Long.parseLong(StringHelper.aesDecrypt(strId, ConstHelper.USER_ID_DEC_ENC_KEY));
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cviewResume");
		setResumeObject(mav, errList,userId);		
		mav.addObject("errList", errList);
		mav.addObject("isdownload","true");
		return mav;

	}

	@RequestMapping("/my/view_resume_{strId}.html")
	public ModelAndView myViewResume(HttpServletRequest request, @PathVariable String strId,
			HttpServletResponse response, RedirectAttributes rt) 
			throws NumberFormatException, Exception {
		
		Long userId = Long.parseLong(StringHelper.aesDecrypt(strId, ConstHelper.USER_ID_DEC_ENC_KEY));
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cviewResume");
		mav.addObject("loginUsers", users);		
		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
		errList = addList(errList, userResult.getErrorMsg());	
		setResumeObject(mav, errList,userId);		
		mav.addObject("errList", errList);
		return mav;

	}
	

	@RequestMapping("/my/download_resume_{strId}.html")
	public void myDownloadResume(HttpServletRequest request, @PathVariable String strId,
			HttpServletResponse response, RedirectAttributes rt) 
			throws NumberFormatException, Exception {
		
		errList = new ArrayList<String>();
		
		Long userId = Long.parseLong(StringHelper.aesDecrypt(strId, ConstHelper.USER_ID_DEC_ENC_KEY));
		RyxUsersDTO users = getRyxUser();
		try{
			
			URL urlfile = null;
	        HttpURLConnection httpUrl = null;
	        BufferedInputStream bis = null;
			String filePath = "http://ryximages.ryx365.com/uploads/picture/user/resume/"+ strId +".pdf";
			ResumeDTO resumeDTO = (ResumeDTO)userService.queryResumeByUserId(userId).getModule();
			if(!StringHelper.isNullOrEmpty(filePath)){
				String fileName = URLEncoder.encode("融易学HRCOACH-"+ resumeDTO.getName() +"的个人简历","utf-8") + filePath.substring(filePath.lastIndexOf('.') );
				urlfile = new URL(filePath);
				response.setContentType("application/octet-stream");
		        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
	            httpUrl = (HttpURLConnection)urlfile.openConnection();
	            httpUrl.connect();
	            bis = new BufferedInputStream(httpUrl.getInputStream());		            
	            int len = 2048;
	            byte[] b = new byte[len];
	            OutputStream out = response.getOutputStream();
	            while ((len = bis.read(b)) != -1)
	            {
	            	out.write(b, 0, len);
	            }
	            out.flush();
	            bis.close();
	            httpUrl.disconnect();
			}
			else{
				errList = addList(errList, "文件不存在");
			}
		}
		catch(IOException e){
			errList = addList(errList, e.toString());
		}
	}
	
	
	@RequestMapping("/my/address.html")
	public ModelAndView myAddress(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/caddress");
		mav.addObject("loginUsers", users);
		AddressQuery addressQuery = new AddressQuery();
		addressQuery.setUserId(users.getId());
		ResultDTO<AddressQuery> resultDTO = userService.queryAddress(addressQuery);
		errList = addList(errList, resultDTO.getErrorMsg());
		mav.addObject("query", resultDTO.getModule());
		mav.addObject("errList", errList);		
		mav.addObject("na", "my_address");

		return mav;

	}
	
	@RequestMapping("/my/contact.html")
	public ModelAndView myContact(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) 
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccontact");
		mav.addObject("loginUsers", users);
		ContactQuery query = new ContactQuery();
		query.setUserId(users.getId());
		ResultDTO<ContactQuery> resultDTO = userService.queryContact(query);
		errList = addList(errList, resultDTO.getErrorMsg());
		mav.addObject("query", resultDTO.getModule());
		mav.addObject("errList", errList);		
		mav.addObject("na", "my_contact");

		return mav;

	}
	
	
	
	/**
	 * 地址
	 */
	

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_address.html")
	public ModelAndView createAddress(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateAddress");
		errList = new ArrayList<String>();			
		mav.addObject("createDTO",new AddressDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_address.html")
	public ModelAndView updateAddress(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateAddress");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();			
		ResultDTO<AddressDTO> result = userService.queryAddressById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	

	
	

	
	@RequestMapping("/my/do_create_address.html")
	public ModelAndView doCreateAddress(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") AddressDTO addressDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateAddress");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		mav.addObject("na", "create_address");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				addressDTO.setCreater(usersDTO.getId());	
				addressDTO.setUserId(usersDTO.getId());				
				ResultDTO<Boolean> result = userService.createAddress(addressDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_address.html")
	public ModelAndView doUpdateMyAddress(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") AddressDTO addressDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateAddress");
		
		errList = new ArrayList<String>();
		mav.addObject("na", "create_address");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				addressDTO.setUserId(usersDTO.getId());
				addressDTO.setCreater(usersDTO.getId());						
				ResultDTO<Boolean> result = userService.updateAddress(addressDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功");
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	/**
	 * resume
	 */
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_resume.html")
	public ModelAndView createResume(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateResume");
		errList = new ArrayList<String>();			
		mav.addObject("createDTO",new ResumeDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_resume.html")
	public ModelAndView updateResume(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateResume");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();			
		ResultDTO<ResumeDTO> result = userService.queryResumeById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	

	
	

	
	@RequestMapping("/my/do_create_resume.html")
	public ModelAndView doCreateResume(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ResumeDTO resumeDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cresume");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		mav.addObject("na", "create_resume");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				resumeDTO.setUserId(usersDTO.getId());	
				resumeDTO.setUid(java.util.UUID.randomUUID().toString());
				ResultDTO<Boolean> result = userService.createOrUpdateResume(resumeDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
			}

			/**
			 * 工作经历
			 */
			WorkExperienceQuery query = new WorkExperienceQuery();
			query.setUserId(usersDTO.getId());
			query.setType(EnumWorkExperType.WORK_EXPER.getCode());
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("lstart");
			query.setSooort("desc");
			ResultDTO<List<WorkExperienceDTO>> result = userService.queryWorkExperience(query);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("workList", result.getModule());
			
			
			/**
			 * 学习经历
			 */
			query = new WorkExperienceQuery();
			query.setUserId(usersDTO.getId());
			query.setType(EnumWorkExperType.STUDY_EXPER.getCode());
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("lstart");
			query.setSooort("desc");
			result = userService.queryWorkExperience(query);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("studyList", result.getModule());
			
			mav.addObject("errList", errList);
			mav.addObject("na", "my_resume");
			
			updateResumePdf(UserHelper.getInstance().getUserIdEncString(usersDTO.getId()));

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	private void updateResumePdf(String strId) throws UnknownHostException{
		 InputStream in = null;  
		 String localFile = "/root/resume/" + strId +".pdf";
		 try{
			 Process pro = Runtime.getRuntime().exec(new String[]{"sh",  
                "/bin/wkhtml2pdf.sh","http://a.ryx365.com/view_resume_"+ strId +".html  "+ localFile});  
			pro.waitFor();  
			in = pro.getInputStream();  
			BufferedReader read = new BufferedReader(new InputStreamReader(in));  
			String result = read.readLine();  
			logger.error("INFO:"+result);
		 } catch (Exception e) {  
           logger.error(e.getMessage(),e);
           errList.add(e.getMessage());
		 }  
		 
		File file = new File(localFile);
		SmbHelper smbHelper = SmbHelper.getInstance(SmbHelper.BASE_URL,"/user/resume");
		ResultDTO<Boolean> updateFile = smbHelper.uploadFile(file);
		addList(errList, updateFile.getErrorMsg());
	}
	
	@RequestMapping("/my/do_update_resume.html")
	public ModelAndView doUpdateMyResume(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ResumeDTO resumeDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateResume");
		
		errList = new ArrayList<String>();
		mav.addObject("na", "create_resume");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				resumeDTO.setUserId(usersDTO.getId());					
				ResultDTO<Boolean> result = userService.updateResume(resumeDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功");
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/create_contact.html")
	public ModelAndView createContact(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateContact");
		errList = new ArrayList<String>();			
		mav.addObject("createDTO",new com.king.nowedge.dto.ContactDTO());
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		return mav;		
	}
	
	
	@RequestMapping("/my/update_contact.html")
	public ModelAndView updateContact(
			@RequestParam(value = "id") Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateContact");
		RyxUsersDTO users = getRyxUser();
		mav.addObject("loginUsers", users);
		errList = new ArrayList<String>();			
		ResultDTO<com.king.nowedge.dto.ContactDTO> result = userService.queryContactById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	

	
	

	
	@RequestMapping("/my/do_create_contact.html")
	public ModelAndView doCreateContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.ContactDTO contactDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccreateContact");
		
		RyxUsersDTO usersDTO = getRyxUser();
		mav.addObject("loginUsers", usersDTO);
		errList = new ArrayList<String>();
		//mav.addObject("na", "my_contact");
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				contactDTO.setCreater(usersDTO.getId());	
				contactDTO.setUserId(usersDTO.getId());				
				ResultDTO<Boolean> result = userService.createContact(contactDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/my/do_update_contact.html")
	public ModelAndView doUpdateMyContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.ContactDTO contactDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cupdateContact");
		
		errList = new ArrayList<String>();
		mav.addObject("na", "create_contact");

		RyxUsersDTO usersDTO = getRyxUser();
		
		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				contactDTO.setUserId(usersDTO.getId());
				contactDTO.setCreater(usersDTO.getId());						
				ResultDTO<Boolean> result = userService.updateContact(contactDTO);
				errList.add(result.getErrorMsg());
				if (result.isSuccess()) {
					errList.add("更新成功");
				}
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		return mav;
	}
	@RequestMapping("/my/ajax_delete_address.html")
	public void ajaxDeleteAddress(
			HttpServletRequest request, 
			AddressDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = userService.deleteAddress(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/my/ajax_delete_contact.html")
	public void ajaxDeleteContact(
			HttpServletRequest request, 
			com.king.nowedge.dto.ContactDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUserId(user.getId());
			ResultDTO<Boolean> result = userService.deleteContact(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	@RequestMapping("/ajax_get_present.html")
	public void ajaxGetPresent(
			HttpServletRequest request, 
			RyxPresentDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		String present = "";
		try {
			
			List<RyxPresentDTO> presentList = UserHelper.getInstance().getPresent();
			if (null!= presentList && presentList.size()>0) {
				Random ra =new Random();
				RyxPresentDTO presentDTO = presentList.get(ra.nextInt(presentList.size()));
				
				CookieHelper.removeCookies(SessionHelper.REGISTER_PRESENT, "/", request, response);
				CookieHelper.addCookie(response, SessionHelper.REGISTER_PRESENT, JSONObject.fromObject(presentDTO).toString(), 60*60*24*30, "/");
						
				RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(presentDTO.getValue());
				if (presentDTO.getType() == EnumObjectType.OFFLINE_MODULE.getCode()) {
					present = "<BR>线下课程:"+ courseDTO.getTitle()+"<BR>价值:"+courseDTO.getPrice();
				}else if (presentDTO.getType() == EnumObjectType.ONLINE_MODULE.getCode()) {
					present = "<BR>线上课程:"+ courseDTO.getTitle()+"<BR>价值:"+courseDTO.getPrice();
				}else if (presentDTO.getType() == EnumObjectType.SCORE_MODULE.getCode()) {
					present = "<BR>积分:"+presentDTO.getValue()+"分" ;
				}else if (presentDTO.getType() == EnumObjectType.COUPON_MODULE.getCode()) {
					present = "<BR>代金券:￥"+presentDTO.getValue();
				}else if (presentDTO.getType() == EnumObjectType.ARTICLE_MODULE.getCode()) {//文章
					present = "<BR>文章:"+ courseDTO.getTitle()+"<BR>价值:"+courseDTO.getPrice();
				}
				writeAjax(response, true,present);
			}
			else{
				writeAjax(response, false,"无效礼品列表");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	@RequestMapping("/ajax_create_feedback.html")
	public void ajaxCreateFeedback(
			HttpServletRequest request, 
			RyxFeedbackDTO dto,
			BindingResult bindingResult, 
			HttpServletResponse response)
					throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		

		try {
			
			if(StringHelper.isNullOrEmpty(dto.getContent())){
				writeAjax(response, false,"请输入反馈意见内容");
			}
			else{
			
				RyxUsersDTO user = getRyxUser();
				if(null != user){
					dto.setUserId(user.getId());
				}
				ResultDTO<Boolean> result = ryxUserService.saveFeedback(dto);
				if (!result.isSuccess()) {
					writeAjax(response, false,result.getErrorMsg());
				} else {
					writeAjax(response, true);
				}		
			}
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());logger.error(t.getMessage(),t);
		}
	}
	
	
	
	/**
	 * 获取是否同意推广协议
	 * @param request
	 * @param courseQuery
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/my/ajax_ispread.html")
	public void ajaxIspread(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			Long userId = getRyxUser().getId();
			ResultDTO<RyxUsersDTO> resultDTO = ryxUserService.getUserById(userId) ;
			if(resultDTO.isSuccess()){
				writeAjax(response, true, resultDTO.getModule().getIspread());
			}
			else{
				writeAjax(response, false, resultDTO.getErrorMsg());
			}
			

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}
	
	
	
	@RequestMapping("/my/ajax_do_agree_ispread.html")
	public void ajaxDoAgreeIspread(HttpServletRequest request,  HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			Long userId = getRyxUser().getId();
			RyxUsersDTO ryxUsersDTO = new RyxUsersDTO();
			ryxUsersDTO.setId(userId);
			ryxUsersDTO.setIspread(1);
			ResultDTO<Boolean> resultDTO = ryxUserService.updateUserById(ryxUsersDTO) ;
			if(resultDTO.isSuccess()){
				writeAjax(response, true);
			}
			else{
				writeAjax(response, false, resultDTO.getErrorMsg());
			}
			

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage());
		}

	}
	
	
}
