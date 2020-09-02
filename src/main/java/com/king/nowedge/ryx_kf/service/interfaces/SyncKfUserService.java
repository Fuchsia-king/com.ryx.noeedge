package com.king.nowedge.ryx_kf.service.interfaces;

import com.king.nowedge.ryx_kf.pojo.KfUserInfoModel;
import com.king.nowedge.service.SystemService;
import com.king.nowedge.service.ryx2.RyxUserService;

import java.util.List;


public interface SyncKfUserService {

    /**
     * @Description: TODO 同步康富用户信息
     * @Param: [kfUserInfo]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/01 14:41
     **/
    Boolean syncKfUser(List<KfUserInfoModel> kfUserInfo, RyxUserService ryxUserService, SystemService systemService);

    /**
     * @Description: TODO 更新康富用户信息（去除离职，增加复职 定时任务用）
     * @Param: [kfUserInfo]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/01 14:47
     **/
    Boolean updateKfUser(List<KfUserInfoModel> kfUserInfo);

    /**
     * @Description: TODO 员工离职用户禁用
     * @Param: [userNum]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/07 12:39
     **/
    Boolean delKfOutUser(String userNum);

    /**
     * @Description: TODO 增加复职人员或新入职人员
     * @Param: [kfUserInfo]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/07 12:41
     **/
    Boolean addKfInUser(List<KfUserInfoModel> kfUserInfo, RyxUserService ryxUserService, SystemService systemService);

    /**
     * @Description: TODO 获取免登ID
     * @Param: [kfUserName]
     * @return: java.lang.Integer
     * @throws Exception 
     * @Author: fan
     * @Date: 2018/09/04 13:25
     **/
    Long getLoginId(String kfUserName) throws Exception;

}
