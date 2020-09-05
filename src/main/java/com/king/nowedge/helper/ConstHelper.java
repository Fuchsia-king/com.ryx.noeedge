package com.king.nowedge.helper;

import com.king.nowedge.dto.ryx.RyxSubAccountPriceDTO;
import com.king.nowedge.utils.SysConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConstHelper {
	
	private static final Log logger = LogFactory.getLog(ConstHelper.class);
	
	public static final String DEFAULT_IMAGE_URL = SysConfig.getInstance().getProperty("DEFAULT_IMAGE_URL");
	public static final Double ONLINE_SCORE = Double.parseDouble(SysConfig.getInstance().getProperty("ONLINE_SCORE"));
	public static final Double OFFLINE_SCORE = Double.parseDouble(SysConfig.getInstance().getProperty("OFFLINE_SCORE"));
	public static final Double ARTICLE_SCORE = Double.parseDouble(SysConfig.getInstance().getProperty("ARTICLE_SCORE"));
	public static final String USER_ID_DEC_ENC_KEY = "/.*&(dR&*$EQW@";
	
	public static final String pc_weixin_erweima_notify_url = SysConfig.getInstance().getProperty("pc_weixin_erweima_notify_url");
	
	public static final String TRAIN_FLOW = "TRAIN_FLOW";
	public static final String TRAIN_COMMENT = "TRAIN_COMMENT";
	public static final String TRAIN_GOODAT = "TRAIN_GOODAT";
	
	public static final String  APP_ENCRYPT_KEY ="!@32!#!#$&r·y·x·" ;
	
	public static final Long DEFAULT_COUPON_LIMIT_DAYS = 30L ;
	
	public static final Long DEFAULT_EXPERIENCE_COUPON_LIMIT_DAYS = 7L ;
	
	public static final String IGNORE_CORP_CODE = "ckfil"; 
		
	
	public static final String FORMAL_IP_ADDR =  SysConfig.getInstance().getProperty("FORMAL_IP_ADDR");
	
	
	
	//public static final String  SHIDAI_APPKEY_TEST = "180F4BC38C1445E8A81FD5AA6381F88A";
	//public static final String  SHIDAI_APPSECRET_TEST = "45FD10F1658946E98CF4E763DCA11CCA";
	
	
	//public static final String  SHIDAI_APPKEY_FORMAL = "DA3B7935FF9642DA909E6AEEE4E9B93D";
	//public static final String  SHIDAI_APPSECRET_FORMAL = "57ABFD77CB7D437DBD456AB2DDA2DA3D";
	
	/**
	 * 每邀请注册 20 个人，送优惠券
	 */
	public static final Integer INVITE_REGIST_NBR_FOR_COUPON = 10 ;
	
	public static final Integer CAN_USED_COUPON_1 = 50 ;
	public static final Integer CAN_USED_COUPON_2 = 5 ;
	
	
	public static final Integer DEFAULT_AVAI_DAY = null == SysConfig.getInstance().getProperty("DEFAULT_AVAI_DAY") ? 
			90 : Integer.parseInt(SysConfig.getInstance().getProperty("DEFAULT_AVAI_DAY"));
	
	public static final Double CHONG = null == SysConfig.getInstance().getProperty("CHONG") ? 
			500 : Double.parseDouble(SysConfig.getInstance().getProperty("CHONG"));
	
	public static final Double CHONG_SONG_COUPON = null == SysConfig.getInstance().getProperty("CHONG_SONG_COUPON") ? 
			10 : Double.parseDouble(SysConfig.getInstance().getProperty("CHONG_SONG_COUPON"));
	
	public static final Double CHONG_SONG_SCORE = null == SysConfig.getInstance().getProperty("CHONG_SONG_SCORE") ? 
			10 : Double.parseDouble(SysConfig.getInstance().getProperty("CHONG_SONG_SCORE"));
	
	public static final String BAOLI_VIDEO_TYPE_LIMIT = null == SysConfig.getInstance().getProperty("BAOLI_VIDEO_TYPE_LIMIT") ? 
			"*.avi; *.mp4; *.mov; *.flv" : SysConfig.getInstance().getProperty("BAOLI_VIDEO_TYPE_LIMIT");
	
	public static final String BAOLI_VIDEO_SIZE_LIMIT = null == SysConfig.getInstance().getProperty("BAOLI_VIDEO_SIZE_LIMIT") ? 
			"5000M" : SysConfig.getInstance().getProperty("BAOLI_VIDEO_SIZE_LIMIT");
	
	
	public static Integer getInviteRegistNbrForCoupon() {
		return INVITE_REGIST_NBR_FOR_COUPON;
	}
	
	public static String getBaoliVideoSizeLimit(){
		return BAOLI_VIDEO_SIZE_LIMIT;
	}
	
	public static Integer getBaoliVideoSizeLimit1(){
		return Integer.parseInt(BAOLI_VIDEO_SIZE_LIMIT)/1024;
	}
	
	public static String getBaoliVideoTypeLimit(){
		return BAOLI_VIDEO_TYPE_LIMIT;
	}
	
	/**
	 * 一次性消费课程
	 */
	public static final Double COST = null == SysConfig.getInstance().getProperty("COST") ? 
			500 : Double.parseDouble(SysConfig.getInstance().getProperty("COST"));
	
	/**
	 * 一次性消费送购物券
	 */
	public static final Double COST_SONG_COUPON = null == SysConfig.getInstance().getProperty("COST_SONG_COUPON") ? 
			10 : Double.parseDouble(SysConfig.getInstance().getProperty("COST_SONG_COUPON"));
	
	/**
	 * 一次性消费送积分
	 */
	public static final Double COST_SONG_SCORE = null == SysConfig.getInstance().getProperty("COST_SONG_SCORE") ? 
			10 : Double.parseDouble(SysConfig.getInstance().getProperty("COST_SONG_SCORE"));
	
	
	/**
	 * 左侧广告栏关闭时间
	 */
	public static final Integer CLOSE_AD_LEFT_DAYS = null ==  SysConfig.getInstance().getProperty("CLOSE_AD_LEFT_DAYS") ? 
			2 : Integer.parseInt(SysConfig.getInstance().getProperty("CLOSE_AD_LEFT_DAYS"));;
			
	public static final String OFFICE_365_DES_KEY = SysConfig.getInstance().getProperty("OFFICE_365_DES_KEY");
	public static final String OFFICE_365_DES_IV = SysConfig.getInstance().getProperty("OFFICE_365_DES_IV");
	
	public static final String ARTICLE_TYPE_LIMIT = SysConfig.getInstance().getProperty("ARTICLE_TYPE_LIMIT");
	public static final Integer ARTICLE_SIZE_LIMIT = null == SysConfig.getInstance().getProperty("ARTICLE_SIZE_LIMIT") ? 
			10 : Integer.parseInt(SysConfig.getInstance().getProperty("ARTICLE_SIZE_LIMIT"));
	
	
	public static final String IMAGE_TYPE_LIMIT = SysConfig.getInstance().getProperty("IMAGE_TYPE_LIMIT");
	public static final Integer IMAGE_SIZE_LIMIT = null == SysConfig.getInstance().getProperty("IMAGE_SIZE_LIMIT") ? 
			3 : Integer.parseInt(SysConfig.getInstance().getProperty("IMAGE_SIZE_LIMIT"));
	
	
	/**
	 * 子账号定价
	 */
	public static final String SUB_ACCOUNT_PRICE = SysConfig.getInstance().getProperty("SUB_ACCOUNT_PRICE");
	
	
	/**
	 * 默认子账号个数
	 */
	public static final Integer DEFAULT_SUB_ACCOUNT_NBR = null ==  SysConfig.getInstance().getProperty("DEFAULT_SUB_ACCOUNT_NBR") ? 
			10 : Integer.parseInt(SysConfig.getInstance().getProperty("DEFAULT_SUB_ACCOUNT_NBR"));
	
	
	/**
	 * 注册赠送优惠券数量
	 */
	public static final Double REGISTER_COUPON = null == SysConfig.getInstance().getProperty("REGISTER_COUPON") ? 
			20 : Double.parseDouble(SysConfig.getInstance().getProperty("REGISTER_COUPON"));
	
	
	public static final String REGISTER_COUPON_DETAIL = SysConfig.getInstance().getProperty("REGISTER_COUPON_DETAIL");
	
	
	/**
	 * 注册赠送积分数量
	 */
	public static final Double REGISTER_SCORE = null == SysConfig.getInstance().getProperty("REGISTER_SCORE") ? 
			20 : Double.parseDouble(SysConfig.getInstance().getProperty("REGISTER_SCORE"));
	
	
	
	public static Double getArticleScore(){
		return ARTICLE_SCORE;
	}
	
	public static Double getOnlineScore(){
		return ONLINE_SCORE;
	}
	
	public static Double getOfflineScore(){
		return OFFLINE_SCORE;
	}
	
	public static Double getChong(){
		return CHONG;
	}
	
	public static Double getChongSongCoupon(){
		return CHONG_SONG_COUPON;
	}
	
	public static Double getChongSongScore(){
		return CHONG_SONG_SCORE;
	}
	
	public static Double getCost(){
		return COST;
	}
	
	public static Double getCostSongCoupon(){
		return COST_SONG_COUPON;
	}
	
	public static Double getCostSongScore(){
		return COST_SONG_SCORE;
	}
	
	public static Double getRegisterCoupon(){
		return  REGISTER_COUPON;
	}
	
	public static String getArticleTypeLimit(){
		return ARTICLE_TYPE_LIMIT;
	}
	
	public static Integer getArticleSizeLimit(){
		return ARTICLE_SIZE_LIMIT;
	}
	
	public static Integer getArticleSizeLimit1(){
		return ARTICLE_SIZE_LIMIT/1024;
	}
	
	public static Integer getImageSizeLimit(){
		return IMAGE_SIZE_LIMIT;
	}
	
	public static Integer getImageSizeLimit1(){
		return IMAGE_SIZE_LIMIT/1024;
	}
	
	public static String getImageTypeLimit(){
		return IMAGE_TYPE_LIMIT;
	}
	
	public static String getDefaultImage(){
		return DEFAULT_IMAGE_URL;
	}
	
	
	public static Double getRegisterScore(){
		return Double.parseDouble(SysConfig.getInstance().getProperty("REGISTER_SCORE"));
	}
	
	public static String getPhone (){
		return SysConfig.getInstance().getProperty("DEFAULT_PHONE");
	}

	
	/**
	 * 获取子账号定价权限
	 * @return
	 */
	public static List<RyxSubAccountPriceDTO> getSubAccountPrice(){
		try{
			List<RyxSubAccountPriceDTO> list = new ArrayList<RyxSubAccountPriceDTO>();
			String[] sss = SUB_ACCOUNT_PRICE.split("\\|");
			for(String ss : sss){
				String[] s = ss.split(",");
				RyxSubAccountPriceDTO subAccountPriceDTO = new RyxSubAccountPriceDTO();
				subAccountPriceDTO.setNbr(Integer.parseInt(s[0]));
				subAccountPriceDTO.setPrice(Double.parseDouble(s[1]));
				list.add(subAccountPriceDTO);
			}
			return list ;
		}
		catch(Throwable t){
			logger.error(t.getMessage(),t);
			return null;
		}
	}
	
	
	public static String MAIL_BODY_REGISTER_SUCCESS = "您已注册融易学用户，登陆账号{0}，密码{1}";
	
	
	/**
	 * 是否正式環境
	 * @return
	 */
	public static Boolean isFormalEnvironment(){
		
		File directory = new File("");
	       
       String path = directory.getAbsolutePath();
       
       logger.error("path--->" + path);
       
       if(path.indexOf("formal") >= 0){
    	   return true; 
       }
		
       return false;
	}
	
	
	
	
	/**
	 * 是否正式環境
	 * @return
	 */
	public static Boolean isPreEnvironment(){
		
		File directory = new File("");
	       
       String path = directory.getAbsolutePath();
       
       logger.error("path--->" + path);
       
       if(path.indexOf("pre") >= 0){
    	   return true; 
       }
		
       return false;
	}
	
	
	
	
}
