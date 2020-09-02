package com.king.nowedge.helper;

import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailHelper {

	private static String host;
	private static String username;
	private static String password;
	private static String from;
	private static String nick;
	private static String port;

	static {
		try {
			// Test Data
			host = "smtp.ym.163.com";
			username = "service@ryx365.com";
			password = "";
			from = "service@ryx365.com";
			nick = "融易学-中国金融在线教育";
			port = "25";
			// nick + from 组成邮箱的发件人信息
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            收件人列表，以","分割
	 * @param subject
	 *            标题
	 * @param body
	 *            内容
	 * @param filepath
	 *            附件列表,无附件传递null
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean send(String to, String subject, String body, List<String> filepath) 
			throws AddressException, MessagingException,
			UnsupportedEncodingException {
		// 参数修饰
		if (body == null) {
			body = "";
		}
		if (subject == null) {
			subject = "无主题";
		}
		// 创建Properties对象
		Properties props = System.getProperties();
		// 创建信件服务器
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true"); // 通过验证
		props.put("mail.smtp.port", port);
		props.put("mail.transport.protocol", "smtp");
		//props.put("mail.smtp.ssl.enable", "true");
		// props.put("mail.debug", "true");
		// 得到默认的对话对象
		Session session = Session.getDefaultInstance(props, null);
		// 创建一个消息，并初始化该消息的各项元素
		MimeMessage msg = new MimeMessage(session);
		nick = MimeUtility.encodeText(nick);
		msg.setFrom(new InternetAddress(nick + "<" + from + ">"));
		// 创建收件人列表
		if (to != null && to.trim().length() > 0) {
			String[] arr = to.split(",");
			int receiverCount = arr.length;
			if (receiverCount > 0) {
				InternetAddress[] address = new InternetAddress[receiverCount];
				for (int i = 0; i < receiverCount; i++) {
					address[i] = new InternetAddress(arr[i]);
				}
				msg.addRecipients(Message.RecipientType.TO, address);
				msg.setSubject(subject);
				// 后面的BodyPart将加入到此处创建的Multipart中
				Multipart mp = new MimeMultipart();
				// 附件操作
				if (filepath != null && filepath.size() > 0) {
					for (String filename : filepath) {
						MimeBodyPart mbp = new MimeBodyPart();
						// 得到数据源
						FileDataSource fds = new FileDataSource(filename);
						// 得到附件本身并至入BodyPart
						mbp.setDataHandler(new DataHandler(fds));
						// 得到文件名同样至入BodyPart
						mbp.setFileName(fds.getName());
						mp.addBodyPart(mbp);
					}
					MimeBodyPart mbp = new MimeBodyPart();
					mbp.setText(body);
					mp.addBodyPart(mbp);
					// 移走集合中的所有元素
					filepath.clear();
					// Multipart加入到信件
					msg.setContent(mp);
				} else {
					// 设置邮件正文
					msg.setText(body);
				}
				// 设置信件头的发送日期
				msg.setSentDate(new Date());
				msg.saveChanges();
				// 发送信件
				Transport transport = session.getTransport("smtp");
				transport.connect(username, password);
				transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
				transport.close();
				return true;
			} else {
				System.out.println("None receiver!");
				return false;
			}
		} else {
			System.out.println("None receiver!");
			return false;
		}
	}

	public static void main(String[] args) throws AddressException, UnsupportedEncodingException, MessagingException {
		send("87302870@qq.com", "【融易学-注册成功】", "您已注册融易学用户，登录账号：13146707660；登录密码 123456", null);
		System.out.println("sendMail success!");
	}
}
