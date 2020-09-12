package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 描述：
 * 作者：
 * 日期：2020/9/7 17:43
 */
@Controller
public class TUserController extends BaseController {

    @RequestMapping("/getUser")
    @ResponseBody
    public RyxUsersDTO getCuurUser(){
        return getUser();
    }

    @RequestMapping("/tologin")
    public void login(String url,String userName,String password, HttpServletRequest request, HttpServletResponse response,
                              RedirectAttributes rt) throws UnsupportedEncodingException {
    }
}
