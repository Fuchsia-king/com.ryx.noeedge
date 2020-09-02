package com.king.nowedge.timer;

import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.dto.enums.EnumOrderStatus;
import com.king.nowedge.dto.ryx.RyxApplyDTO;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxApplyQuery;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.helper.UserHelper;
import com.king.nowedge.mapper.ryx.RyxApplyMapper;
import com.king.nowedge.mapper.ryx.RyxCourseMapper;
import com.king.nowedge.utils.JavaSmsApi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @author Administrator
 *
 */

public class VideoNotifyTask extends TimerTask {
	
	public VideoNotifyTask(){
		
	}
	
	private static final Log logger = LogFactory.getLog(VideoNotifyTask.class);

	private static final int C_SCHEDULE_MINUTE = 0;
	private static boolean isRunning = false;
	private ServletContext context = null;
	
	
	private RyxCourseMapper courseMapper ;
	private RyxApplyMapper ryxApplyMapper ;
	

	

//	public VideoNotifyTask(ServletContext context) {
//		this.context = context;
//		ryxApplyMapper = new com.king.nowedge.mapper.ryx.impl.RyxApplyDAOImpl();
//		courseMapper = new com.king.nowedge.mapper.ryx.impl.RyxCourseDAOImpl();
//	}

	public void run() {
		Calendar c = Calendar.getInstance();
		if (!isRunning) {
			if (C_SCHEDULE_MINUTE == c.get(Calendar.MINUTE)) {
				isRunning = true;
				context.log("开始执行指定任务");
				
				/**
				 * 直播在2个小时之前提醒客户,要提前准备
				 */
				RyxCourseQuery query = new RyxCourseQuery();
				query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
				query.setTtstart(System.currentTimeMillis()/1000);
				query.setEtstart(System.currentTimeMillis()/1000 + 2 * 60 * 60); // 提前2 小时通知
				try {
					List<RyxCourseDTO> list = courseMapper.query(query);
					for(RyxCourseDTO courseDTO : list){
						try {
							RyxApplyQuery applyQuery = new RyxApplyQuery();
							applyQuery.setOtype(EnumObjectType.VIDEO_MODULE.getCode());
							applyQuery.setOid(courseDTO.getId());
							List<Integer> statuss = new ArrayList<>(Arrays.asList(
									EnumOrderStatus.FREE.getCode() ,
									EnumOrderStatus.PAY_SUCCESS.getCode() ,
									EnumOrderStatus.PRESENT.getCode()									
								));
							applyQuery.setStatuss(statuss);
							List<RyxApplyDTO> applyList = ryxApplyMapper.query(applyQuery);
							for(RyxApplyDTO applyDTO : applyList){
								RyxUsersDTO usersDTO = UserHelper.getInstance().getUserById(applyDTO.getUserId());
								if(null != usersDTO && !StringHelper.isNullOrEmpty(usersDTO.getMobile())){
									String text = "";
									try {
										JavaSmsApi.sendSms(JavaSmsApi.API_KEY, text, usersDTO.getMobile());
									} catch (IOException e) {
										logger.error(e.getMessage(),e);
									}
								}
							}
						}catch (BaseDaoException e) {
							logger.error(e.getMessage(),e);
						}
					}
				} catch (BaseDaoException e) {
					logger.error(e.getMessage(),e);
				}
				
				isRunning = false;
				context.log("指定任务执行结束");
			} else {
				context.log("上一次任务执行还未结束");
			}
		}
	}

}
