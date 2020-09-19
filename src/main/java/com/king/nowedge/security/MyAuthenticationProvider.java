package com.king.nowedge.security;

import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.service.three.impl.MyUserDetailsService;
import com.king.nowedge.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 描述：
 * 作者：
 * 日期：2020/9/8 9:24
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    /**
     * 注入自定义的用户信息获取服务
     */
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();//前端传入用户名
        String password = (String) authentication.getCredentials();// 前端传入密码
        String MD5Password = Md5Util.GetMD5Code(password);
        // 这里构建来判断用户是否存在和密码是否正确
        userName = SessionHelper.LOGIN_TYPE_RYX_MEMBER+SessionHelper.LOGIN_TYPE_SPLIT+userName;
        UserDetails userInfo=null;
        try{
            userInfo = userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("用户名不存在");
        }
         //这里判断密码正确与否
         if(!userInfo.getPassword().equals(MD5Password))
         {
            throw new BadCredentialsException("密码不正确");
         }
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}
