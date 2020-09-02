package com.king.nowedge.helper.deploy;

public class BaseAutoDeploy {
	
	// static String BASE_PATH = "E:\\java\\trunk\\linglingyi\\";
		static String BASE_PATH = "D:\\work\\wangdap\\source\\ryx365_branches\\trunk";
		static String LOCAL_WAR_FILE = BASE_PATH + "ROOT.war";
		static String ANT_BAT = LOCAL_WAR_FILE + "build.bat";

		static String REMOTE_DICTIONARY = "/root/apache-tomcat/webapps/";
		static String SHUNDOWN = "sh /root/apache-tomcat/bin/shutdown.sh";
		static String STARTUP = "sh /root/apache-tomcat/bin/startup.sh";
		static Integer BUILD_WAIT_SECOND = 20;

}
