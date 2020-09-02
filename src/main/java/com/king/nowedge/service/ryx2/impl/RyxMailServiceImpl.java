package com.king.nowedge.service.ryx2.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxConfigDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxConfigMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx2.RyxMailService;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service("ryxMailService")
public class RyxMailServiceImpl  extends BaseService implements RyxMailService {

	private static final Log log = LogFactory.getLog(RyxMailServiceImpl.class);
	public static final String HOST = "smtp.ym.163.com";
    public static final String PROTOCOL = "smtp";   
    public static final int PORT = 25;
    public static final String FROM = "rongyixue@ryx365.com";//发件人的email
    public static final String PWD = "rhdt2014";//发件人密码
    
    @Autowired
	RyxConfigMapper ryxConfigMapper ;

    /**
     * 获取Session
     * @return
     */
    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol" , PROTOCOL);//设置协议
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth" , true);

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }
        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }

    private void send(String toEmail , String content) {
        Session session = getSession();
        try {
            System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("账号激活邮件");
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");

            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    private RyxConfigDTO getEmailTemplate(int id) throws BaseDaoException  {
    	
    	return ryxConfigMapper.getById(id) ;
    }
	
    public ResultDTO<Boolean> processRegister(RyxUsersDTO user, String serverName){
    	

		ResultDTO<Boolean> result = null;
		
    	try{
		
	    	///邮件的内容
	        StringBuffer sb = new StringBuffer("");
	        sb.append("<a href=\"http://");
	        sb.append(serverName);
	        sb.append("/check_validate_code.html?email=");
	        sb.append(user.getEmail()); 
	        sb.append("&validate_code="); 
	        sb.append(user.getValidateCode());
	        sb.append("\">http://");
	        sb.append(serverName);
	        sb.append("/check_validate_code.html?email=");
	        sb.append(user.getEmail());
	        sb.append("&validate_code=");
	        sb.append(user.getValidateCode());
	        sb.append("</a>");
	        log.info(sb.toString());
	    	
	    	RyxConfigDTO emailTemplate = this.getEmailTemplate(73);
	    	String email = null;
	    	if (emailTemplate != null) {
	    		email = emailTemplate.getValue();
	    		email = email.replace("【链接】", sb.toString());
	    	}
	    	
	//        ///邮件的内容
	//        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
	//        sb.append("<a href=\"http://");
	//        sb.append(serverName);
	//        sb.append("/checkValidateCode.html?email=");
	//        sb.append(user.getEmail()); 
	//        sb.append("&validateCode="); 
	//        sb.append(user.getValidateCode());
	//        sb.append("\">http://");
	//        sb.append(serverName);
	//        sb.append("/checkValidateCode.html?email=");
	//        sb.append(user.getEmail());
	//        sb.append("&validateCode=");
	//        sb.append(user.getValidateCode());
	//        sb.append("</a>");
	//        log.info(sb.toString());
	
	        //发送邮件
	        this.send(user.getEmail(), email);
	        
	        result = new ResultDTO<Boolean>(true);
        
    	}
    	catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
        
        return result;
    }
    
    public static void main(String[] args) {
    	RyxUsersDTO user = new RyxUsersDTO();
    	user.setEmail("294156882@qq.com");
    	user.setValidateCode(Md5Util.GetMD5Code(user.getEmail()));
//		new MailService().processregister(user);
	}
	
}
