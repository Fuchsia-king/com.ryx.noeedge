package com.king.nowedge.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述：
 * 作者：
 * 日期：2020/9/25 15:28
 */
@Component
public class MAVHelper {
    private static ModelAndView mav=null;

    public static ModelAndView getMav(String s){
        if(mav==null){
            return new ModelAndView(s);
        }else {
            return mav;
        }
    }
}
