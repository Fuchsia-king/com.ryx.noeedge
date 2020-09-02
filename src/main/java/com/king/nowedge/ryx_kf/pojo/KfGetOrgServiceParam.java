package com.king.nowedge.ryx_kf.pojo;

import com.king.nowedge.helper.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author fan
 * @description: TODO 获取组织架构信息入参pojo
 * @program KfGetOrgServiceParam
 * @date 2018/8/31 14:35
 */
public class KfGetOrgServiceParam {
    //组织架构时间	String	需要获取某时间点的组织架构信息,不填则为当前时间点。格式为"yyyy-MM-dd"
    private String datetime;
    //必传，请求时间戳	String	格式"yyyy-MM-dd HH:mm:ss"
    private String time;
    //必传，数据获取方	String	EAS：财务系统 IL:智慧租 RYX:融易学系统
    private String from;
    //必传，32位uuid
    private String uuid;

    public KfGetOrgServiceParam() {

        String uuid = DateHelper.date2String("yyyyMMddHHmmss", new Date()) + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 18);
        SimpleDateFormat sf = new SimpleDateFormat();
        sf.applyPattern("yyyy-MM-dd");
        this.datetime = sf.format(new Date());
        sf.applyPattern("yyyy-MM-dd HH:mm:ss");
        this.time = sf.format(new Date());
        this.from = "RYX";
        this.uuid = uuid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}