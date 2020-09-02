package com.king.nowedge.ryx_kf.controller;


import com.king.nowedge.controller.BaseController;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.ryx_kf.pojo.KfUserInfoModel;
import com.king.nowedge.ryx_kf.service.interfaces.SyncKfUserService;
import com.king.nowedge.ryx_kf.utils.CipherUtil;
import com.king.nowedge.ryx_kf.utils.CookieUtils;
import com.king.nowedge.ryx_kf.utils.GsonUtil;
import com.king.nowedge.ryx_kf.utils.ListOrMapUtil;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@PropertySource({"classpath:kangfuConfig.properties"})
public class KangfuController extends BaseController {
    @Value("${KF_USER_PWD}")
    private String KF_USER_PWD;
    @Value("${KF_USER_CCS}")
    private String KF_USER_CCS;
    @Value("${RYX_USER_CCS}")
    private String RYX_USER_CCS;
    @Value("${SD_KEY}")
    private String SD_KEY;
    @Value("${RYX_MAIN_USER_ID}")
    private String RYX_MAIN_USER_ID;
    @Value("${KF_COOKIE_KEY}")
    private String KF_COOKIE_KEY;
    @Value("${KF_COOKIE_VALUE}")
    private String KF_COOKIE_VALUE;
    @Value("${AUTO_LOGIN_IN}")
    private String AUTO_LOGIN_IN;
    @Value("${KF_PASSWORD_KEY}")
    private String KF_PASSWORD_KEY;
    @Autowired
    private SyncKfUserService syncKfUserService;

    /**
     * @Description: TODO 康富单点登录唯一入口
     * @Param: [request, response, rt]
     * @return: org.springframework.web.servlet.ModelAndView
     * @Author: fan
     * @Date: 2018/09/03 17:58
     **/
    @RequestMapping("/kfLogin.html")
    @ResponseBody
    public ModelAndView kfUserAutoLogin(HttpServletRequest request, HttpServletResponse response,
                                        RedirectAttributes rt) throws Exception {
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
        String kfUserName = principal.getName();
        Long id = syncKfUserService.getLoginId(kfUserName);
        String cipher = StringHelper.aesEncrypt(id.toString(), ConstHelper.APP_ENCRYPT_KEY);
        
        //String modelName = "id";
        
        //return new ModelAndView(AUTO_LOGIN_IN, modelName, cipher);
        
        return new ModelAndView("redirect:/interface/go2sd1.html?str=" + URLEncoder.encode(cipher, "UTF-8"));
    }

    /**
     * @Description: TODO 员工离职用户禁用（方法名有偏差，按照注释走）
     * @Param: [inParam]
     * @return: java.lang.String
     * @Author: fan
     * @Date: 2018/09/07 10:55
     **/
    @RequestMapping(value = "/getKfUserStatus.html", method = RequestMethod.POST)
    @ResponseBody()
    public String getKfUserStatus(@RequestBody Map<String, Object> inParam) {
        String kfOutUserInfo = inParam.get("data").toString();
        Map<String, Object> outUserMap = GsonUtil.GsonToMap(kfOutUserInfo);
        String personNumber = outUserMap.get("personNumber").toString();
        Boolean ok = syncKfUserService.delKfOutUser(personNumber);
        if (ok) {
            Map ret = new HashMap();
            ret.put("code", 200);
            ret.put("message", "Delete Success");
            ret.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return GsonUtil.GsonToString(ret);
        } else {
            Map ret = new HashMap();
            ret.put("code", 500);
            ret.put("message", "Delete Fail");
            ret.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return GsonUtil.GsonToString(ret);
        }
    }

    /**
     * @Description: TODO 获取康富最新组织架构信息  增加复职人员或新入职人员
     * @Param: [inParam]
     * @return: java.lang.String
     * @Author: fan
     * @Date: 2018/09/07 12:40
     **/
    @RequestMapping(value = "/kfPersonUpdate.html", method = RequestMethod.POST)
    @ResponseBody
    public String getUpdatePersonInfo(@RequestBody Map<String, Object> inParam) {
        List<KfUserInfoModel> kfUserInfo = new ArrayList<>();
        String str = inParam.get("data").toString();
        String info = CipherUtil.decrypt(str, KF_PASSWORD_KEY);
        Map personMap = GsonUtil.GsonToMap(info);
        List<Map<String, Object>> list = (List) personMap.get("personList");
        for (Map<String, Object> map : list) {
            map.remove("positions");
            kfUserInfo.add((KfUserInfoModel) ListOrMapUtil.mapToObject(map, KfUserInfoModel.class));
        }
        Boolean ok = syncKfUserService.addKfInUser(kfUserInfo, super.ryxUserService, super.systemService);
        if (ok) {
            Map ret = new HashMap();
            ret.put("code", 200);
            ret.put("message", "Update Success");
            ret.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return GsonUtil.GsonToString(ret);
        } else {
            Map ret = new HashMap();
            ret.put("code", 500);
            ret.put("message", "Update Fail");
            ret.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return GsonUtil.GsonToString(ret);
        }
    }

    @RequestMapping(value = "/saveCookie.html", method = RequestMethod.GET)
    @ResponseBody()
    /**
     * @Description: TODO 模拟cookie存入 显示康富入口
     * @Param: [request, response, rt]
     * @return: java.lang.String
     * @Author: fan
     * @Date: 2018/09/07 11:18
     **/
    public String saveCookie(HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes rt) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            CookieUtils.setCookie(request, response, KF_COOKIE_KEY, KF_COOKIE_VALUE);
            resultMap.put("code", 200);
            resultMap.put("msg", "Save Cookie Success!!!");
            String result = GsonUtil.GsonToString(resultMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", 400);
            resultMap.put("msg", "Save Cookie Error!!!");
            String result = GsonUtil.GsonToString(resultMap);
            return result;
        }
    }
}
