package com.king.nowedge.ryx_kf.scheduled;

import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.ryx_kf.pojo.KfGetOrgServiceParam;
import com.king.nowedge.ryx_kf.pojo.KfUserInfoModel;
import com.king.nowedge.ryx_kf.service.interfaces.SyncKfUserService;
import com.king.nowedge.ryx_kf.utils.*;
import com.king.nowedge.service.SystemService;
import com.king.nowedge.service.ryx.RyxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fan
 * @program ScheduledTask
 * @description: TODO 定时任务 每晚二点执行
 * @date 2018/8/31 16:31
 */
@Component
public class ScheduledTask {
    @Value("${KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL_TEST}")
    private String KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL_TEST;
    @Value("${KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL}")
    private String KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL;
    @Value("${KF_PASSWORD_KEY}")
    private String KF_PASSWORD_KEY;

    @Autowired
    private SyncKfUserService syncKfUserService;
    @Resource(name = "ryxUserService")
    private RyxUserService ryxUserService;
    @Resource(name = "systemService")
    private SystemService systemService;

    /**
     * @Description: TODO 定时任务每晚两点执行代码块
     * @Param: []
     * @return: void
     * @Author: fan
     * @Date: 2018/09/07 12:48
     **/
//  @Scheduled(cron = "0 0 2 * * ?")
    public void syncKfInfo() {
    }

    /**
     * @Description: TODO 启动后同步数据
     * @Param: [servletContextEvent]
     * @return: void
     * @Author: fan
     * @Date: 2018/09/07 12:46
     **/
    @PostConstruct
    public void contextInitialized() {
        try {
        	
        	System.out.println("==========contextInitialized===========");
        	
            //将触发时间写入日志
            LogUtil.writeDateStart("SyncKf_Success.log");
            LogUtil.writeDateStart("SyncKf_Error.log");
            //获取组织架构入参对象
            KfGetOrgServiceParam kfGetOrgServiceParam = new KfGetOrgServiceParam();
            //调取康富获取组织架构接口
            String result;
            if (ConstHelper.isPreEnvironment() || ConstHelper.isFormalEnvironment()) {
                //正式环境地址
                result = HttpClientUtil.doPost(KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL, ListOrMapUtil.objectToMap(kfGetOrgServiceParam));
            } else {
                //测试环境地址
                result = HttpClientUtil.doPost(KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL_TEST, ListOrMapUtil.objectToMap(kfGetOrgServiceParam));
            }
            

            
            //获取加密数据 并且解密以及转换有用数据 kfUserInfo
            List<KfUserInfoModel> kfUserInfo = new ArrayList<>();
            List<KfUserInfoModel> kfUserInfoCopy = new ArrayList<>();
            Map<String, Object> resultMap = GsonUtil.GsonToMap(result);
            String str = ((Map) resultMap.get("data")).get("data").toString();
            String info = CipherUtil.decrypt(str, KF_PASSWORD_KEY);
            Map personMap = GsonUtil.GsonToMap(info);
            List<Map<String, Object>> list = (List) personMap.get("personList");
            for (Map<String, Object> map : list) {
                map.remove("positions");
                kfUserInfo.add((KfUserInfoModel) ListOrMapUtil.mapToObject(map, KfUserInfoModel.class));
                kfUserInfoCopy.add((KfUserInfoModel) ListOrMapUtil.mapToObject(map, KfUserInfoModel.class));
            }
            Boolean syncResult = syncKfUserService.syncKfUser(kfUserInfo, ryxUserService, systemService);
            if (syncResult) {
                LogUtil.writeDateStop("SyncKf_Success.log");
                LogUtil.writeDateStop("SyncKf_Error.log");
                System.out.println("Time:【" + new Date() + "】   同步数据成功！！！");
            } else {
                LogUtil.writeDateStop("SyncKf_Success.log");
                LogUtil.writeDateStop("SyncKf_Error.log");
                System.out.println("Time:【" + new Date() + "】   同步数据失败！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}