package com.king.nowedge.helper;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumPartnerType;
import com.king.nowedge.dto.ryx.RyxPartnerDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestHelper {
	
	private static final Log logger = LogFactory.getLog(RequestHelper.class);
	
	public static final String PARNTER_URL_SEED = "#$@!*&$@@r#y^x3#6&5+com";
	
	
	/**
	 * 获取 大 V
	 * @param request
	 * @param user
	 * @return
	 */
	public static Long getSpecailParterId(HttpServletRequest request,RyxUsersDTO user) {
		

		Long partnerLong = getCommonPartnerId(request, user);
		if(null != partnerLong){
			RyxPartnerDTO partnerDTO = new RyxPartnerDTO();
			partnerDTO.setUserId(partnerLong);
			partnerDTO.setIdeleted(0);
			partnerDTO.setType(EnumPartnerType.LINK_PARTNER.getCode());
			RyxPartnerDTO partnerDTO2 = MetaHelper.getInstance().getPartnerByUserId(partnerDTO).getModule();
			if(null == partnerDTO2 || null == partnerDTO2.getUserId()){
				return  null;
			}
			else{
				return partnerLong ;
			}
		}
		
		
		return partnerLong;
		
	}
	
	
	
	public static Long getCommonPartnerId(HttpServletRequest request,RyxUsersDTO user) {
		

		Long partnerLong = null;
		if(null != user){
			partnerLong = user.getSid();	
		}
		if(null == partnerLong || partnerLong.equals(0L)){
			String string = CookieHelper.getCookies(SessionHelper.LINK_PARTNER_COOKIE_SESSION, request, SessionHelper.COOKIE_PATH_LINK_PARTNER);
			if(!StringHelper.isNullOrEmpty(string)){
				try {
					partnerLong = Long.parseLong(StringHelper.aesDecrypt(string, PARNTER_URL_SEED));
				} catch (NumberFormatException e) {
					logger.error(e);
				} catch (UnsupportedEncodingException e) {
					logger.error(e);
				} catch (Exception e) {
					logger.error(e);
				}
			}	
		}
		
		
		
		
		return partnerLong;
		
	}
	
	
	public static RyxUsersDTO getRyxUser() {

		try {

			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = (UserDetails) obj;
			JSONObject jsonObject = JSONObject.fromObject(userDetails.getUsername());		
			return (RyxUsersDTO)JSONObject.toBean(jsonObject, RyxUsersDTO.class);

		} catch (Throwable t) {

			return null;
		}
	}
	
	
	public static  void setWideSpreadCookie(HttpServletRequest request, HttpServletResponse response) {
		
		String u = request.getParameter("u");
		if(!StringHelper.isNullOrEmpty(u)){
			
			Long userId;
			try {
				
				
				userId = Long.parseLong(StringHelper.aesDecrypt(u, RequestHelper.PARNTER_URL_SEED));
				RyxPartnerDTO partner = new RyxPartnerDTO();
				partner.setUserId(userId);
				partner.setType(EnumPartnerType.LINK_PARTNER.getCode());
				ResultDTO<RyxPartnerDTO> resultDTO = MetaHelper.getInstance().getPartnerByUserId(partner);
				partner = resultDTO.getModule();
				if(resultDTO.isSuccess() && null != partner){
					CookieHelper.removeCookies(SessionHelper.LINK_PARTNER_COOKIE_SESSION, SessionHelper.COOKIE_PATH_LINK_PARTNER, request, response);
					CookieHelper.addCookie(response, SessionHelper.LINK_PARTNER_COOKIE_SESSION, 
							URLEncoder.encode(u, "utf-8"), partner.getDays() * 24 * 60 * 60 , SessionHelper.COOKIE_PATH_LINK_PARTNER);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		
	}
	
	public static void responseForbidden(HttpServletResponse response) throws IOException{
		
		response.setStatus(HttpStatus.FORBIDDEN.value());  
        response.setContentType("text/html; charset=UTF-8");  
        PrintWriter out=response.getWriter();  
        out.write("{forbidden}");  
        out.flush();  
        out.close(); 
	}
	

}
