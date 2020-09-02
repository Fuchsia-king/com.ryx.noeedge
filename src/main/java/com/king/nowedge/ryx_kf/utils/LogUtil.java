package com.king.nowedge.ryx_kf.utils;

import com.king.nowedge.ryx_kf.pojo.KfUserInfoModel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName LogUtil
 * @Description: TODO 将同步日志输出至TXT文件
 * @Author fan
 * @Date 2018/9/1 17:20
 * @Version 1.0
 **/
public class LogUtil {
    /**
     * @Description: TODO 输出成功日志
     * @Param: [kfUserInfoModel]
     * @return: void
     * @Author: fan
     * @Date: 2018/09/01 20:46
     **/
    public static void outPutSuccessLog(KfUserInfoModel kfUserInfoModel, String logInfo) {
        try {
            FileOutputStream fileOutputStream = null;
            String pathname = getFilePath("SyncKf_Success.log");
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(("KangFuUser:" + kfUserInfoModel.getLoginName() + " " + logInfo + "\r\n").getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: TODO 输出失败日志
     * @Param: [kfUserInfoModel]
     * @return: void
     * @Author: fan
     * @Date: 2018/09/01 20:46
     **/
    public static void outPutErrorLog(KfUserInfoModel kfUserInfoModel, String logInfo) {
        try {
            FileOutputStream fileOutputStream = null;
            String pathname = getFilePath("SyncKf_Error.log");
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(("KangFuUser:" + kfUserInfoModel.getLoginName() + " " + logInfo + "\r\n").getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFilePath(String fileInfo) {
        String path = LogUtil.class.getClassLoader().getResource("").getPath();
        String rootPath = path.substring(0, path.indexOf("classes/")) + fileInfo;
        return rootPath;
    }

    public static void writeDateStart(String fileName) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(new Date());
        try {
            FileOutputStream fileOutputStream = null;
            String pathname = getFilePath(fileName);
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(("\r\n--Start--" + date + "--Start--\r\n").getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDateStop(String fileName) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(new Date());
        try {
            FileOutputStream fileOutputStream = null;
            String pathname = getFilePath(fileName);
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(("--Stop--" + date + "--Stop--\r\n").getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
