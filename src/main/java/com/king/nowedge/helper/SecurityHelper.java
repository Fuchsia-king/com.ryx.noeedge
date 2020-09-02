package com.king.nowedge.helper;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@Component
public class SecurityHelper {
	
	
	static public String GET_BAD_IP = "netstat -anlp|grep 80|grep tcp|awk '{print $5}'|awk -F: '{print $1}'|sort|uniq -c|sort -nr|head -n20 | netstat -ant |awk '/:80/{split($5,ip,\":\");++A[ip[1]]}END{for(i in A) print A[i],i}' |sort -rn|head -n200" ;
	static public String LIST_IPTALES_CONFIG = "iptables -nL --line-number" ;
	
	static public Boolean includedIptalesConfig(Connection conn,String ipAddr) throws IOException {
		System.out.println(DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + "     " +"正在执行 -->" + ipAddr);
		Session _ssh = null;
		StreamGobbler is =  null ;
		BufferedReader brs = null ;
		try {
			
			Boolean irestart = false;
			_ssh = conn.openSession();
			_ssh.execCommand(LIST_IPTALES_CONFIG);
			
			is = new StreamGobbler(_ssh.getStdout());
			brs = new BufferedReader(new InputStreamReader(is));
			
			String line;
			
			StringBuffer sb = new StringBuffer();
			
			while (null != (line = brs.readLine())) {				
				sb.append(line);
			}
			
			System.out.println(sb.toString());
			if(sb.toString().indexOf(ipAddr)>=0){
				System.out.println("inlcuded");
				return true;
			}
			System.out.println("not inlcuded");
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != _ssh) {
				_ssh.close();
			}
			if (null != is) {
				is.close();
			}
			if (null != brs) {
				brs.close();
			}
			
		}
	}
	
	static public void getBadIpAddr(Connection conn,String cmd) throws IOException {
		System.out.println(DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + "     " +"正在执行 -->" + cmd);
		Session _ssh = null;
		StreamGobbler is =  null ;
		BufferedReader brs = null ;
		try {
			
			Boolean irestart = false;
			_ssh = conn.openSession();
			_ssh.execCommand(cmd);
			
			is = new StreamGobbler(_ssh.getStdout());
			brs = new BufferedReader(new InputStreamReader(is));
			
			String line;
			
			StringBuffer sb = new StringBuffer();
			
			while (null != (line = brs.readLine())) {
				System.out.println(line);
				String[] strings = line.split(" ");
				System.out.println("strings size-->" + strings.length);
				if(strings.length == 2 ){
					System.out.println("nbr-->" + strings[0]);
					System.out.println("ip--->" + strings[1]);
					Integer nbr = Integer.parseInt(strings[0]);
					if(nbr >= 8){
						String ipAddr = strings[1].trim(); 
						if(!ipAddr.equals("127.0.0.1") && !includedIptalesConfig(conn, ipAddr)){
							irestart = true ;
							sb.append("iptables -I INPUT -s "+ ipAddr +" -j DROP;");
						}
					}
				}
			}
			
			if(irestart){
				executeCommand(conn,sb.toString());
				executeCommand(conn, "service iptables save");
				executeCommand(conn, "service iptables restart");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != _ssh) {
				_ssh.close();
			}
			if (null != is) {
				is.close();
			}
			if (null != brs) {
				brs.close();
			}
		}
	}
	
	
	static public void executeCommand(Connection conn,String cmd) {
		System.out.println(DateHelper.date2String("yyyy-MM-dd HH:mm:ss", new Date()) + "     " +"正在执行 -->" + cmd);
		Session _ssh = null;
		try {
			_ssh = conn.openSession();
			_ssh.execCommand(cmd);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != _ssh) {
				_ssh.close();
			}
		}
	}
	
	
	
}
