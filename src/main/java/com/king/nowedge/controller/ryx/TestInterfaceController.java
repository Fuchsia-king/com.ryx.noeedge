package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.HttpHelper;
import com.king.nowedge.helper.StringHelper;

public class TestInterfaceController extends BaseController {

	
	static String url = "a.ryx365.com";
	
	//static String url = "localhost";

	
	public static void main(String[] args) throws Exception {
		
//		testDeleteSubAccount();
//		
//		testCreateSubAccount();
//		
//		testUpdateSubAccount();
		
//		testUndeleteSubAccount();
		
		testCheckUserOnlineAuth();
	}
	
	
	private static void testCreateSubAccount() throws Exception{
		String json = "{username:'wangdap222121112222221',corpCode:'ryx',uid:'ryx_ryx-3',source:'sd',password:'12345678'}";
		String string = HttpHelper.post1("http://"+ url +"/interface/create_sub_user.html",
				StringHelper.aesEncrypt(json, ConstHelper.APP_ENCRYPT_KEY)).getModule();
		System.out.println(string);
	}
	
	
	private static void testDeleteSubAccount() throws Exception{
		String json = "{username:'wangdap22212111222222',uid:'ryx_ryx-3',corpCode:'ryx',source:'sd',password:'12345678'}";
		String string = HttpHelper.post1("http://"+ url +"/interface/delete_sub_user.html",
				StringHelper.aesEncrypt(json, ConstHelper.APP_ENCRYPT_KEY)).getModule();
		System.out.println(string);
	}
	
	
	private static void testUndeleteSubAccount() throws Exception{
		String json = "{uid:'ryx_ryx-3',corpCode:'ryx',source:'sd'}";
		String string = HttpHelper.post1("http://"+ url +"/interface/undelete_sub_user.html",
				StringHelper.aesEncrypt(json, ConstHelper.APP_ENCRYPT_KEY)).getModule();
		System.out.println(string);
	}
	
	
	private static void testCheckUserOnlineAuth() throws Exception{
		String json = "{'courseCode':'1629','corpCode':'ckfil','source':'sd','uid':'admin-','isMobile':'Y'}";
		String string = HttpHelper.post1("http://"+ url +"/interface/check_user_online_auth.html",
				StringHelper.aesEncrypt(json, ConstHelper.APP_ENCRYPT_KEY)).getModule();
		System.out.println(string);
	}
	
	
	private static void testUpdateSubAccount() throws Exception{
		String json = "{username:'wangdap22212111222---',uid:'ryx_ryx-3',corpCode:'ryx',source:'sd',password:'12345678'}";
		String string = HttpHelper.post1("http://"+ url +"/interface/update_sub_user.html",
				StringHelper.aesEncrypt(json, ConstHelper.APP_ENCRYPT_KEY)).getModule();
		System.out.println(string);
	}
	
}
