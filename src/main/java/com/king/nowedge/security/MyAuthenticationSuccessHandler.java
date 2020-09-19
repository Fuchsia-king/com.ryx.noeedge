package com.king.nowedge.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：处理登录成功的
 * 作者：
 * 日期：2020/9/8 9:41
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        RyxUsersDTO usersDTO = new RyxUsersDTO();
        usersDTO.setUsername(request.getParameter("username"));
        session.setAttribute("user",usersDTO);
        Cookie cookie = new Cookie("sessionId",session.getId());
        cookie.setMaxAge(30*24*60*60);
        response.addCookie(cookie);

        String targetUrl = request.getRequestURI();
        Map<String,String> map=new HashMap<>();
        map.put("code", "200");
        map.put("msg", "登录成功");
        if(targetUrl.indexOf("register") >0
                ||  targetUrl.indexOf("reset_password") >0
                ||  targetUrl.indexOf("login") >0
                ||  targetUrl.indexOf("ajax") >0
                ||  targetUrl.indexOf("index") >0)
        {
            map.put("url","/first");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }else{
            map.put("url",targetUrl);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }

    }
}
