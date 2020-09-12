package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyRelatedValueType;
import com.king.nowedge.query.base.KeyrvQuery;
import com.king.nowedge.dto.ryx.RyxActivitySeatDTO;
import com.king.nowedge.query.ryx.RyxActivitySeatQuery;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.helper.UserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class QiandaoController extends BaseController {

	
	/**
	 * 签到首页
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping("/qiandao/{code}.html")
	public ModelAndView index(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		
		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoIndex");
		
		/**
		 * 获取图标
		 */
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setKey1(code);
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_ACTIVITY_ICONS.getCode());
		keyrvQuery.setOrderBy("sort");
		keyrvQuery.setSooort("asc");
		
		
		/**
		 * 图标列表
		 */
		mav.addObject("iconList", MetaHelper.getInstance().queryKeyrv(keyrvQuery).getModule().getList());		
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	/**
	 * 会议议程
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping("/qiandao/hyyc_{code}.html")
	public ModelAndView hyyc(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		
		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoHyyc");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	
	/**
	 * 座位信息(查询坐位)
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/zwxx_{code}.html")
	public ModelAndView zwxx(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoZwxx");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	@RequestMapping("/qiandao/zwxx1_{code}.html")
	public ModelAndView zwxx1(@PathVariable String code, String name,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		if(StringHelper.isNullOrEmpty(name)){
			throw new Throwable("missing name");
		}
		
		String seat = UserHelper.getInstance().getActivitySeat(code, name);
		
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoZwxx1");
		
		mav.addObject("code", code);
		mav.addObject("seat", seat);
		mav.addObject("name", name);
		
		return mav ;
	}
	
	
	/**
	 * 参会机构
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/chjg_{code}.html")
	public ModelAndView chjg(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoChjg");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	/**
	 * 用餐指引
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/yczy_{code}.html")
	public ModelAndView qiandaoYczy(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoYczy");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	
	/**
	 * 酒店信息
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/jdxx_{code}.html")
	public ModelAndView qiandaoJdxx(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoJdxx");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	/**
	 * 温馨提示
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/wxts_{code}.html")
	public ModelAndView qiandaoWxts(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoWxts");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	
	
	/**
	 * 联系我们
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/lxwm_{code}.html")
	public ModelAndView lxwm(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoLxwm");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	/**
	 * 会议签到
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/qiandao/hyqd_{code}.html")
	public ModelAndView hyqd(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		ModelAndView mav = new ModelAndView("/ryx/qiandao/qiandaoHyqd");
		
		mav.addObject("code", code);
		
		return mav ;
	}
	
	
	@RequestMapping("/qiandao/qiandao/{code}.html")
	public void doSignIn(@PathVariable String code,String name,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		if(StringHelper.isNullOrEmpty(code)){
			throw new Throwable("missing code");
		}
		
		if(StringHelper.isNullOrEmpty(name)){
			throw new Throwable("missing name");
		}
		
		RyxActivitySeatQuery ryxActivitySeatQuery = new RyxActivitySeatQuery();	
		ryxActivitySeatQuery.setCode(code);
		ryxActivitySeatQuery.setName(name);
		
		Integer iResultDTO = systemService.countQueryActivitySeat(ryxActivitySeatQuery).getModule();
		if(iResultDTO == 0){
			writeAjax(response, false,"无嘉宾信息，请联系主办方");
			return; 
		}
		else{
			RyxActivitySeatDTO ryxActivitySeatDTO = new RyxActivitySeatDTO();
			
			ryxActivitySeatDTO.setCode(code);
			ryxActivitySeatDTO.setName(name);
			ryxActivitySeatDTO.setIdao(1);
			ResultDTO<Boolean> result = systemService.updateActivitySeat1(ryxActivitySeatDTO);
			if(result.isSuccess()){
				writeAjax(response, true);
				return ;
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
				return;  
			}
		}
		
	}
}
