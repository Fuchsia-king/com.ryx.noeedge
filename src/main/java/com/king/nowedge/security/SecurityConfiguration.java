package com.king.nowedge.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 描述：
 * 作者：
 * 日期：2020/8/31 15:11
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
//                .antMatchers("/index").permitAll()// 设置所有人都可以访问登录页面
//                .anyRequest().authenticated();  // 任何请求,登录后可以访问
//    }

    // 放行所有请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/index.html");
//    }


}
