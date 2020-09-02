package com.king.nowedge.ryx_kf.scheduled;

import com.king.nowedge.ryx_kf.pojo.KfGetOrgServiceParam;
import com.king.nowedge.ryx_kf.pojo.KfUserInfoModel;
import com.king.nowedge.ryx_kf.pojo.LaborTurnoverServiceParam;
import com.king.nowedge.ryx_kf.utils.CipherUtil;
import com.king.nowedge.ryx_kf.utils.GsonUtil;
import com.king.nowedge.ryx_kf.utils.HttpClientUtil;
import com.king.nowedge.ryx_kf.utils.ListOrMapUtil;
//import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class test {
    @Value("${KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL}")
    private String KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL = "http://testportalapi.i-leasing.cn/rest/organizationReceiving";
    @Value("${KF_PASSWORD_KEY}")
    private String KF_PASSWORD_KEY = "c9c55fae-182a-4472-9084-54b6939e8706";

//    @Test
    public void getPersonInfo() {
        KfGetOrgServiceParam kfGetOrgServiceParam = new KfGetOrgServiceParam();
        String result = HttpClientUtil.doPost(KF_GET_ORGANIZATIONAL_STRUCTURE_INFO_URL, ListOrMapUtil.objectToMap(kfGetOrgServiceParam));
        Map<String, Object> resultMap = GsonUtil.GsonToMap(result);
        String str = ((Map) resultMap.get("data")).get("data").toString();
        //加密数据
        String info = CipherUtil.decrypt(str, KF_PASSWORD_KEY);
        Map personMap = GsonUtil.GsonToMap(info);
        List<Map<String, Object>> list = (List) personMap.get("personList");
        List<KfUserInfoModel> kfUserInfo = new ArrayList<>();
        for (Map<String, Object> map : list) {
            map.remove("positions");
            kfUserInfo.add((KfUserInfoModel) ListOrMapUtil.mapToObject(map, KfUserInfoModel.class));
        }
        for (KfUserInfoModel kfUserInfoModel : kfUserInfo) {
            String uid = kfUserInfoModel.getFid(); // 员工编码
            String username = kfUserInfoModel.getLoginName();
            String name = kfUserInfoModel.getName();

        }
        System.out.println(kfUserInfo.get(0).getBirthday());
        System.out.println("调用完毕！！！");
        System.out.println("调用完毕！！！");
        System.out.println("调用完毕！！！");
    }

//    @Test
    public void ssss() {
        //员工离职用户禁用对象
        LaborTurnoverServiceParam laborTurnoverServiceParam = new LaborTurnoverServiceParam(
                "Test-20180817-003");
        //调取员工离职用户禁用接口 获取此员工状态
//        String kfUserStatusResult = HttpClientUtil.doPost("http://testportalapi.i-leasing.cn",
//                ListOrMapUtil.objectToMap(laborTurnoverServiceParam));

        String kfUserStatusResult = HttpClientUtil.doGet("http://kapok.imwork.net:38800/saveCookie.html",
                ListOrMapUtil.objectToMap(laborTurnoverServiceParam));
        System.out.println("入参：" + GsonUtil.GsonToString(laborTurnoverServiceParam));
        System.out.println();
        System.out.println("返回结果：" + kfUserStatusResult);

    }
}
