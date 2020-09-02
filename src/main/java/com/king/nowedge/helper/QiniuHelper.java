package com.king.nowedge.helper;

import com.google.gson.Gson;
import com.king.nowedge.dto.base.ResultDTO;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QiniuHelper {
	
    UploadManager uploadManager = new UploadManager(new Configuration());

    private static final Log logger = LogFactory.getLog(QiniuHelper.class);
    
    static String QINIU_DOCTOR_IMAGE_DOMAIN = "http://ryximages.ryx365.com/" ;
    

    static String accessKey = "UWHS2GqynEgs8aGWJTmJmdSbGTDlhc_WjbaKmLnR";      //AccessKey的值
    static String secretKey = "f7CmHAvq4OZYGc3vDUefFyb5kY-mtMhBlUpQy1pk";      //SecretKey的值
 
//    @Test
//    public  void aaa() {
//        Configuration cfg = new Configuration(Zone.zone0());                //zong1() 代表华北地区
//        UploadManager uploadManager = new UploadManager(cfg);
//
//        String bucket = "ryx-images";
//
//        //存储空间名
//        String localFilePath = "C:\\Users\\Administrator\\Pictures\\264_4a0887b436195378111ecd0560e888e2.jpg";     //上传图片路径
//
//        String key = "456.png";                                               //在七牛云中图片的命名
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println("111->" +putRet.key);
//            System.out.println("222->" +putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println("333->" +r.toString());
//            System.err.println("444->" +r.reqId);
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//        }
//    }
//
    
    
    
    public static ResultDTO<String> uploadBytes(byte[] bytes,String key) {
        Configuration cfg = new Configuration(Zone.zone0());                //zong1() 代表华北地区
        UploadManager uploadManager = new UploadManager(cfg);

        String bucket = "ryx-images";     
        //存储空间名

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            
            return new ResultDTO<String>(QINIU_DOCTOR_IMAGE_DOMAIN + key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(ex.getMessage(), ex);
            
            try {
				return new ResultDTO<String>("error",r.bodyString());
			} catch (QiniuException e) {
				logger.error(e.getMessage(), e );
				return new ResultDTO<String>("error",e.getMessage());
			}
        }
    }


    class MyRet {
        public String hash;
        public String key;
        public String fsize;
        public String fname;
        public String mimeType;
    }

}
