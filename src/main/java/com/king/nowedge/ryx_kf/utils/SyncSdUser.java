package com.king.nowedge.ryx_kf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author fan
 * @program SyncSdUser
 * @description: TODO 同步康富用户到时代（企业大学）
 * @date 2018/8/31 10:15
 */
public class SyncSdUser {

    private static final Logger Logger = LoggerFactory.getLogger(SyncSdUser.class);

    @Value("${SD_ACCOUNT_ENABLE}")
    private static String SD_ACCOUNT_ENABLE;
    @Value("${SD_ACCOUNT_FORBIDDEN}")
    private static String SD_ACCOUNT_FORBIDDEN;

    /**
     * @Description: TODO 同步数据
     * @Param: [ryxUserExt, password]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/8/31
     */
//    public static Boolean syncData(RyxUserExtDTO ryxShiDaiUserExt, String password, String accountStatus) {
//        Map map = ListOrMapUtil.objectToMap(ryxShiDaiUserExt);
//        Logger.info("ryxShiDaiUserExt：" + map.toString());
//        // 测试环境调用地址：yufa.21tb.com
//        // 正式环境调用地址：v4.21tb.com
//        if (ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()) {
//            SdkContext.serverName = "v4.21tb.com";
//
//        } else {
//            SdkContext.serverName = "yufa.21tb.com";
//        }
//
//        SdkContext.appKey = ryxShiDaiUserExt.getAppKey();
//        SdkContext.appSecret = ryxShiDaiUserExt.getAppSecret();
//
//        /**
//         * 不负流年:
//         180F4BC38C1445E8A81FD5AA6381F88A
//         45FD10F1658946E98CF4E763DCA11CCA
//         ryx   admin      admin000
//         不负流年:
//         测试环境的appkey 和appSecret
//         不负流年:
//         yufa.21tb.com测试地址
//         */
//
//        //OpenOrganizeService organizeService = ServiceManager.getService(OpenOrganizeService.class);
//        //organizeService.sy
//
//        //人员同步DEMO 将同步一名学员
//        List<OpenUser> shidaiUsers = new ArrayList<OpenUser>();
//        OpenUser shidaiUser = new OpenUser();
//        //*****必须字段*****
//        shidaiUser.setAccountStatus(accountStatus); //人员状态： 激活=ENABLE, 冻结=FORBIDDEN
//        shidaiUser.setCorpCode(ryxShiDaiUserExt.getCorpCode());
//        shidaiUser.setEmployeeCode(ryxShiDaiUserExt.getUid()); //员工编号，唯一识别
//        shidaiUser.setLoginName(ryxShiDaiUserExt.getUsername());    //登录名，唯一
//        shidaiUser.setOrganizeCode("*"); //所在组织的组织编号，必须为现有组织
//        shidaiUser.setUserName(ryxShiDaiUserExt.getName());  //姓名
//        shidaiUser.setMobile(ryxShiDaiUserExt.getMobile());
//        shidaiUser.setEmail(ryxShiDaiUserExt.getEmail());
//        //shidaiUser.setRank("Manager");
//        shidaiUser.setInWorkTime(new Date());
////        shidaiUser.setOrganizeId(ryxShiDaiUserExt.getOrgId());
////        shidaiUser.setOrganizeCode(ryxShiDaiUserExt.getOrgCode());
////        shidaiUser.setOrganizeName(ryxShiDaiUserExt.getOrgName());
//        //*****必需字段完毕*****
//        //设置密码 注意：若此用户已经存在于系统，那么后续更新操作若设置的password字段，则将更新此人密码，
//        //注意做MD5转码后，转成小写
//        shidaiUser.setPassword(Md5Util.GetMD5Code(password).toLowerCase());
//        OpenUserService userService = ServiceManager.getService(OpenUserService.class);
//        map = ListOrMapUtil.objectToMap(shidaiUser);
//        Logger.info("shidaiUsers：" + map.toString());
//        shidaiUsers.add(shidaiUser);
//        //调用人员数据同步方法 第二个字段的意义为： 是否为第一次登陆的新员工弹出修改密码功能，若已经做了
//        //单点登录集成则可以设为false
//
//
//
//        Map<String, String> resultMap = userService.syncUsers(shidaiUsers, false);
//
//
//        if (resultMap != null || resultMap.size() != 0)
//            return true;
//        return false;
//    }

    /**
     * @Description: TODO 修改离职人员对应企业大学账号状态为冻结
     * @Param: [ryxShiDaiUserExt]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/20 9:03
     **/
//    public static Boolean disableShidaiUser(RyxUserExtDTO ryxShiDaiUserExt) {
//        // 测试环境调用地址：yufa.21tb.com
//        // 正式环境调用地址：v4.21tb.com
//        if (ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()) {
//            SdkContext.serverName = "v4.21tb.com";
//
//        } else {
//            SdkContext.serverName = "yufa.21tb.com";
//        }
//
//        SdkContext.appKey = ryxShiDaiUserExt.getAppKey();
//        SdkContext.appSecret = ryxShiDaiUserExt.getAppSecret();
//
//        /**
//         * 不负流年:
//         180F4BC38C1445E8A81FD5AA6381F88A
//         45FD10F1658946E98CF4E763DCA11CCA
//         ryx   admin      admin000
//         不负流年:
//         测试环境的appkey 和appSecret
//         不负流年:
//         yufa.21tb.com测试地址
//         */
//
//        //人员同步DEMO 将同步一名学员
//        //*****必须字段*****
//
//        OpenUserService userService = ServiceManager.getService(OpenUserService.class);
//        //调用人员数据同步方法 第二个字段的意义为： 是否为第一次登陆的新员工弹出修改密码功能，若已经做了
//        //单点登录集成则可以设为false
//        userService.disableUser(ryxShiDaiUserExt.getUid());
//        return true;
//    }

    /**
     * @Description: TODO 批量或单个修改企业大学账号的人员状态 激活=ENABLE, 冻结=FORBIDDEN
     * @Param: [ryxShiDaiUserExt, accountStatus]
     * @return: java.lang.Boolean
     * @Author: fan
     * @Date: 2018/09/20 9:02
     **/
//    public static Boolean updateShidaiUserStatus(RyxUserExtDTO ryxShiDaiUserExt, String accountStatus) {
//        // 测试环境调用地址：yufa.21tb.com
//        // 正式环境调用地址：v4.21tb.com
//        if (ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()) {
//            SdkContext.serverName = "v4.21tb.com";
//
//        } else {
//            SdkContext.serverName = "yufa.21tb.com";
//        }
//
//        SdkContext.appKey = ryxShiDaiUserExt.getAppKey();
//        SdkContext.appSecret = ryxShiDaiUserExt.getAppSecret();
//
//        /**
//         * 不负流年:
//         180F4BC38C1445E8A81FD5AA6381F88A
//         45FD10F1658946E98CF4E763DCA11CCA
//         ryx   admin      admin000
//         不负流年:
//         测试环境的appkey 和appSecret
//         不负流年:
//         yufa.21tb.com测试地址
//         */
//
//        //人员同步DEMO 将同步一名学员
//        //*****必须字段*****
//        OpenUserService userService = ServiceManager.getService(OpenUserService.class);
//        //调用人员数据同步方法 第二个字段的意义为： 是否为第一次登陆的新员工弹出修改密码功能，若已经做了
//        //单点登录集成则可以设为false
//        List<String> employeeCode = new ArrayList<>();
//        employeeCode.add(ryxShiDaiUserExt.getUid());
//        userService.updateUserStatus(employeeCode, accountStatus, ryxShiDaiUserExt.getCorpCode());
//        return true;
//    }
}