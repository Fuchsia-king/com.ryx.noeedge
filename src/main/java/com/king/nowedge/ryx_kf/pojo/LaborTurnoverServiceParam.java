package com.king.nowedge.ryx_kf.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LaborTurnoverServiceParam
 * @Description: TODO 员工离职用户禁用入参pojo
 * @Author fan
 * @Date 2018/9/1 0001 13:15
 * @Version 1.0
 **/
public class LaborTurnoverServiceParam {
    private String time;
    private Map<String, Object> data;

    public LaborTurnoverServiceParam(String kfUserNum) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.time = sf.format(new Date());
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("personNumber", kfUserNum);
        this.data = inParam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
