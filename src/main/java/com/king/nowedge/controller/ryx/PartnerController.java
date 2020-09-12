package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.controller.comm.IndexsController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumPartnerType;
import com.king.nowedge.query.HistoryQuery;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxPartnerDTO;
import com.king.nowedge.dto.ryx.RyxSpreadLinkDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.query.ryx.RyxPartnerOrderQuery;
import com.king.nowedge.excp.BaseException;
import com.king.nowedge.helper.*;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@Controller
public class PartnerController extends BaseController {

	private static final Log logger = LogFactory.getLog(IndexsController.class);
	

	/**
	 * 
	 * @param historyQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/my/r.html")
	public ModelAndView r(HistoryQuery historyQuery, @RequestParam(value = "l") String l , 
			@RequestParam(value = "sign") String sign ,HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("redirect:/index.html"); // new
		try {
			
			String validateSign = StringHelper.MD5Encode(l, "utf-8");
			
			/**
			 * 验证
			 */
			if(!StringHelper.isNullOrEmpty(sign) && !StringHelper.isNullOrEmpty(l) && sign.equals(validateSign) ){
				
				String s = StringHelper.aesDecrypt(l, RequestHelper.PARNTER_URL_SEED); // 解密				
				JSONObject jsonObject = JSONObject.fromObject(s);
				RyxSpreadLinkDTO spreadLinkDTO =  (RyxSpreadLinkDTO)JSONObject.toBean(jsonObject, RyxSpreadLinkDTO.class);
				
				
				/**
				 * 读取 partner 时间设置
				 */
				RyxPartnerDTO partnerDTO = new RyxPartnerDTO();
				partnerDTO.setUserId(spreadLinkDTO.getPartnerId());
				partnerDTO.setType(EnumPartnerType.LINK_PARTNER.getCode());
				ResultDTO<RyxPartnerDTO> partnerResult = MetaHelper.getInstance().getPartnerByUserId(partnerDTO);
				if(partnerResult.isSuccess()){
				
					partnerDTO = partnerResult.getModule();
					if(null != partnerDTO){
						
						/**
						 * 设置 cookie 
						 */
						Cookie cookie = new Cookie(SessionHelper.LINK_PARTNER_COOKIE_SESSION, URLEncoder.encode(l, "utf-8") );
						cookie.setMaxAge(partnerDTO.getDays() * 24 * 60 * 60 );  // 30天  
						response.addCookie(cookie);						
					}				
				}
				
				if(!StringHelper.isNullOrEmpty(spreadLinkDTO.getMobile())){
					
					InputStream in = null;
					try {  
						URL url = new URL(spreadLinkDTO.getMobile());  
					    in = url.openStream(); 
					    mav = new ModelAndView("redirect:" + spreadLinkDTO.getMobile()); // new
					} catch (Exception e1) {
						mav = new ModelAndView("redirect:index.html"); // new  
					}  
					finally{
						if(null != in){
							in.close();
						}
					}
				}
				
			}
			else{
				throw new BaseException("invlid data : l ===> qqq;sign ===> " + sign);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
		
	}
	
	
	/**
	 * 
	 * @param historyQuery
	 * @param l
	 * @param sign
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/i.html")
	public ModelAndView i(@RequestParam(value = "i") String i ,Integer c, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {
		
		i = URLEncoder.encode(i,"UTF-8");
		
		ModelAndView mav ;
	
		String m = "am.ryx365.com";
		String p = "a.ryx365.com";
		
		if(ConstHelper.isPreEnvironment()){
			m = "pm.ryx365.com";
			p = "pre.ryx365.com";
		}
		else if (ConstHelper.isFormalEnvironment()){
			m = "m.ryx365.com";
			p = "www.ryx365.com";
		}
		
		if(StringHelper.isMoblie(request)){		
			if(null == c){
				mav = new ModelAndView("redirect:http://"+ m +"/set_spread_cookie.html?i=" + i); // new
			}else{
				mav = new ModelAndView("redirect:http://"+ m +"/set_spread_cookie.html?i=" + i + "&c=" + c); // new
			}
		}
		else{
			
			if(null == c){
				 mav = new ModelAndView("redirect:http://"+ p +"/set_spread_cookie.html?i=" + i ); // new
			}
			else{
				mav = new ModelAndView("redirect:http://"+ p +"/set_spread_cookie.html?i=" + i + "&c=" + c); // new
			}
			
		}
		
		return mav;
		
	}
	
	
	@RequestMapping("/set_spread_cookie.html")
	public ModelAndView setSpreadCookie(@RequestParam(value = "i") String i , Integer c, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav ;

		
		if(null == c){
			mav = new ModelAndView("redirect:/index.html");
		}
		else{
			if(StringHelper.isMoblie(request)){
				mav = new ModelAndView("redirect:/m/online_"+ c +".html");
			}
			else{
				mav = new ModelAndView("redirect:/online_"+ c +".html");
			}
		}
		
			
		try {
			
			CookieHelper.removeCookies(SessionHelper.LINK_PARTNER_COOKIE_SESSION, 
					SessionHelper.COOKIE_PATH_LINK_PARTNER, request, response);
			
			CookieHelper.addCookie(response, SessionHelper.LINK_PARTNER_COOKIE_SESSION, 
					i, 365, SessionHelper.COOKIE_PATH_LINK_PARTNER);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return mav;
		
	}
	
	
	@RequestMapping(value="/mryx/partner/ajax_generate_spread_link.html",method = RequestMethod.POST)
	public void ajaxGenerateSpreadLink(RyxSpreadLinkDTO spreadLinkDTO, 
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			spreadLinkDTO.setPartnerId(getRyxUser().getId());
			JSONObject jsonObject=JSONObject.fromObject(spreadLinkDTO);
			String s = StringHelper.aesEncrypt(jsonObject.toString(),RequestHelper.PARNTER_URL_SEED);
			String sign = StringHelper.MD5Encode(s, "utf-8");
			String url = "http://m.ryx365.com/r.html?l=" + URLEncoder.encode(s,"utf-8") + "&sign=" + URLEncoder.encode(sign,"utf-8") ;
			writeAjax(response, true,"", url);
		} catch (Exception e) {
			writeAjax(response, false,e.toString());
			e.printStackTrace();
		}
		
	}
	 
	
	@RequestMapping("/mryx/partner/generate_spread_link.html")
	public ModelAndView generateSpreadLink(
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/partner/generateSpreadLink");
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/partner/enter_broadcast_room.html")
	public ModelAndView enterBroadcastRoom(@RequestParam(value = "courseId") Long courseId, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/partner/enterBroadcastRoom");
		
		ResultDTO<RyxCourseDTO> courseResultDTO = MetaHelper.getInstance().getCourseById(courseId);
		errList = addList(errList, courseResultDTO.getErrorMsg());
		mav.addObject("course",courseResultDTO.getModule());
		
		return mav;		
	}

	
	@RequestMapping("/mryx/partner/list_order.html")
	public ModelAndView listOrder(RyxPartnerOrderQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/partner/listOrder"); //new RedirectView("index")		

		try {
			
			RyxUsersDTO users = getRyxUser();		
			query.setPartnerId(users.getId());
			errList = new ArrayList<String>();			
			ResultDTO<RyxPartnerOrderQuery> result = ryxOrderService.queryPartnerOrder(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
}
