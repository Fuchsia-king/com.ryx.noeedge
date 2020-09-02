package com.king.nowedge.controller.ryx;

import ch.ethz.ssh2.Connection;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.DateHelper;
import com.king.nowedge.helper.SecurityHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Date;


@Controller
public class TimerController extends BaseController {

	private static final Log logger = LogFactory.getLog(TimerController.class);

	//@Scheduled(cron = "0/2 * *  * * ? ")
	public void autoGorderTask() {

		if(ConstHelper.isPreEnvironment()){
			
			String servers = "121.207.227.245:root:#jqdBUR&V6fB-121.207.227.159:root:87&3LRTS2l-121.207.227.227:root:YdQHBztMUt-121.207.227.117:root:pDLBL7vF#xub" ;
			
			String[] server = servers.split("-") ;
				
			for(String svr : server){
				
				String[] strings = svr.split(":");
				String hostname = strings[0];
				String username = strings[1];
				String password = strings[2];
				Connection conn = new Connection(hostname);
				try {
					// 连接到主机
					conn.connect();
					// 使用用户名和密码校验
					boolean isconn = conn.authenticateWithPassword(
							username, password);
					if (!isconn) {
						System.out.println(DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + "     " +DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + hostname + "---》用户名称或者是密码不正确");
						
					} else {
						
						System.out.println(DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + "     " +hostname + "---》已经连接OK");
						SecurityHelper.getBadIpAddr(conn,SecurityHelper.GET_BAD_IP);
						
					}
	
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					conn.close();
				}
			}
			
		}
	}
	


	
	
	

}
