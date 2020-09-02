package com.king.nowedge.helper;

import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.utils.IPUtils;
import com.king.nowedge.utils.Md5Util;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringHelper {
	
	public static final String PASSWORD_CHARS = "23456789abcdefghijkmnpqrstuvwxyz";
	public static final String NUMBER_CHARS = "0123456789";
	public static final String INVALID_URL_CHAR = ";|";
	
	public static final String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	public static final String MOBILE_REGEX = "^1[0-9]{10}$";
	
	
	
	public static String createMd5Sign(SortedMap<String, String> packageParams,String suffix) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String s = sb.substring(0,sb.length() - 1);
		if(!isNullOrEmpty(suffix)){
			s = s + suffix;
		}
		String sign = Md5Util.GetMD5Code(s);
		return sign;
	}
	
    public static String generateRandomCode(int verifySize, String sources){  
        if(sources == null || sources.length() == 0){  
            sources = PASSWORD_CHARS;  
        }  
        int codesLen = sources.length();  
        Random rand = new Random(System.currentTimeMillis());  
        StringBuilder verifyCode = new StringBuilder(verifySize);  
        for(int i = 0; i < verifySize; i++){  
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));  
        }  
        return verifyCode.toString();  
    }  
    
    
    public static Boolean containInvalidUrlChar(String s ){
    	if (isNullOrEmpty(s)){
    		return true;
    	}
    	
    	return s.contains(";");
    }
    
    public static Boolean isEmail(String str){
    	if(isNullOrEmpty(str)){
    		return false;
    	}
    	
        //正则表达式的模式
        Pattern p = Pattern.compile(EMAIL_REGEX);
        //正则表达式的匹配器
        Matcher m = p.matcher(str);
        //进行正则匹配
        return m.matches();
    }
    
    
    public static Boolean isMobile(String str){
    	if(isNullOrEmpty(str)){
    		return false;
    	}
    	
        //正则表达式的模式
        Pattern p = Pattern.compile(MOBILE_REGEX);
        //正则表达式的匹配器
        Matcher m = p.matcher(str);
        //进行正则匹配
        return m.matches();
    }
    
    
    public static String generateRandomPassword(int verifySize){
        int codesLen = PASSWORD_CHARS.length();  
        Random rand = new Random(System.currentTimeMillis());  
        StringBuilder verifyCode = new StringBuilder(verifySize);  
        for(int i = 0; i < verifySize; i++){  
            verifyCode.append(PASSWORD_CHARS.charAt(rand.nextInt(codesLen-1)));  
        }  
        return verifyCode.toString();  
    }  

	public static String trim(String str) {
		if (null == str)
			return "";
		return str.trim();
	}

	public static Boolean isNullOrEmpty(String str) {

		if (null == str || str.equals("") || str.length() == 0)
			return true;

		return false;
	}
	
	public static Integer double2Integer(Double d){
		if (null == d){
			return 0;
		}
		return d.intValue();
	}

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 返回形式只为数字
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	
	
	public static String object2String(Object object) {
		if(null == object)return "";
		return object.toString();
	}

	
	public static Long daysLeft(Long second) {
		return ( second * 1000 - System.currentTimeMillis() ) / 1000 / 24 / 3600 ;
	}
	
	
	
	public static String hoursLeft(Long current){
		Long hoursLong = ( current * 1000 - System.currentTimeMillis() ) / 1000 / 3600 ;
		return hoursLong/24 + "天" + hoursLong%24 + "小时";
	}
	
	public static String list2HtmlString(List<String> list) {
		if (null == list || 0 == list.size()) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (String s : list) {
				sb.append(s).append("<BR>");
			}
			return sb.toString().substring(0, sb.length() - 4);
		}
	}
	
	public static String list2String(List<String> list) {
		if (null == list || 0 == list.size()) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (String s : list) {
				sb.append(s).append("\r\n");
			}
			return sb.toString().substring(0, sb.length() - 4);
		}
	}

	public static String encode(String s, String encoding) throws UnsupportedEncodingException {
		if (null == s || "".equals(s)) {
			return "";
		} else {
			return URLEncoder.encode(s, encoding);
		}
	}

	public static String decode(String s, String encoding) throws UnsupportedEncodingException {
		if (null == s || "".equals(s)) {
			return "";
		} else {
			return URLDecoder.decode(s, encoding);
		}
	}

	/***
	 * 
	 * @param s
	 * @return
	 */
	public static Long string2Long(String s) {
		if (null == s)
			return 0L;
		return Long.parseLong(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Integer string2Integer(String s) {
		if (null == s)
			return 0;
		return Integer.parseInt(s);
	}

	/**
	 * 将byte[]转为各种进制的字符串
	 * 
	 * @param bytes
	 *            byte[]
	 * @param radix
	 *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		return StringHelper.isNullOrEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

	/**
	 * 获取byte[]的md5值
	 * 
	 * @param bytes
	 *            byte[]
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);

		return md.digest();
	}

	
	
	public static boolean isMoblie(HttpServletRequest request) {  
        boolean isMoblie = false;  
        String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",  
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",  
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",  
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",  
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",  
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",  
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",  
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",  
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",  
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",  
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",  
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",  
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",  
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",  
                "Googlebot-Mobile" };  
        if (request.getHeader("User-Agent") != null) {  
            String agent=request.getHeader("User-Agent");  
            for (String mobileAgent : mobileAgents) {  
                if (agent.toLowerCase().indexOf(mobileAgent) >= 0&&agent.toLowerCase().indexOf("windows nt")<=0 &&agent.toLowerCase().indexOf("macintosh")<=0) {  
                    isMoblie = true;  
                    break;  
                }  
            }  
        }  
        return isMoblie;  
    }
	
	
	/**
	 * 获取字符串md5值
	 * 
	 * @param msg
	 * @return md5
	 * @throws Exception
	 */
	public static byte[] md5(String msg) throws Exception {
		return StringHelper.isNullOrEmpty(msg) ? null : md5(msg.getBytes());
	}

	/**
	 * 结合base64实现md5加密
	 * 
	 * @param msg
	 *            待加密字符串
	 * @return 获取md5后转为base64
	 * @throws Exception
	 */
	public static String getMD5(String str) throws Exception {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        throw new Exception("MD5加密出现错误");
	    }
	}
	
	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(Object content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(encryptKey.getBytes("utf-8"));
		kgen.init(128, secureRandom);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		return cipher.doFinal(content.toString().getBytes("utf-8"));
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(decryptKey.getBytes("utf-8"));
		kgen.init(128, secureRandom);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes,"utf-8");
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringHelper.isNullOrEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		if (null == origin)
			return null;
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 
	 * @param d
	 * @param pointLength
	 * @return
	 */
	public static String double2String(Double d, Integer pointLength) {
		if (null == d)
			return "";

		if (null == pointLength) {
			pointLength = 2;
		}
		String string = String.format("%." + pointLength + "f", d);
		if (string.indexOf(".") == 0)
			return "0" + string;
		return string;
	}

	
	public String formatDouble2(Double d) {
		if (null == d)
			return "";
		DecimalFormat df = new DecimalFormat("###");
		String s = df.format(d);
		if (s.indexOf(".") == 0)
			return "0" + s;
		return s;
	}
	
	public String formatDouble(Double d) {
		if (null == d)
			return "";
		DecimalFormat df = new DecimalFormat("###.00");
		String s = df.format(d);
		if (s.indexOf(".") == 0)
			return "0" + s;
		return s;
	}
	public String formatDouble1(Double d) {
		if (null == d)
			return "";
		DecimalFormat df = new DecimalFormat("###.000");
		String s = df.format(d);
		if (s.indexOf(".") == 0)
			return "0" + s;
		return s;
	}

	public static String sha1(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getObjectString(Object obj) {

		if (null == obj)
			return "";
		String s = obj.toString();

		Integer index = s.indexOf("[");
		if (index < 0)
			index = 0;
		try {
			return GetMD5Code(s.substring(index));

		} catch (Throwable t) {
			return s;
		}
	}
	
	
	public static String getObjectString1(Object obj) {

		if (null == obj)
			return "";
		String s = obj.toString();

		Integer index = s.indexOf("[");
		if (index < 0)
			index = 0;
		try {
			return s.substring(index);

		} catch (Throwable t) {
			return s;
		}
	}

	public static String getIconByExt(String filename) {

		filename = filename.toLowerCase();
		Integer index = filename.lastIndexOf(".");
		if (isNullOrEmpty(filename) || index < 0) {
			return "html_s.gif";
		}

		String ext = filename.substring(index + 1);
		switch (ext) {
		case "doc":
		case "docx":
			return "doc_s.gif";

		case "xls":
		case "xlsx":
			return "excel_s.gif";

		case "rar":
		case "zip":
			return "rar_s.gif";

		case "ppt":
		case "pptx":
			return "ppt_s.gif";
			
		case "png":
		case "jpg":
		case "jpeg":
		case "bmp":
			return "img_s.gif";
		case "pdf":
			return "pdf_s.gif";

		default:

			return "html_s.gif";
		}
	}

	


	public static String REGEX_IMG = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
	public static String REGEX_IFRAME = "<iframe.*src\\s*=\\s*(.*?)[^>]*?>";
	public static String getBaiduMapSrc(String s){
		if (isNullOrEmpty(s))
			return "";
		Pattern p_image;
		Matcher m_image;
		p_image = Pattern.compile(REGEX_IMG, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(s);
		if (m_image.find()) {
			return getImageSrc(s);
		}
		else{
			return getIframeSrc(s);
		}
	}
	
	public static String getImageSrc(String s) {
		if (isNullOrEmpty(s))
			return "";
		
		Pattern p_image;
		Matcher m_image;

		p_image = Pattern.compile(REGEX_IMG, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(s);
		if (m_image.find()) {
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher( m_image.group());
			if (m.find()) {
				return m.group(1);
			}
		}

		return "";

	}
	
	public static String getIframeSrc(String s) {
		if (isNullOrEmpty(s))
			return "";
		
		Pattern p_image;
		Matcher m_image;

		p_image = Pattern.compile(REGEX_IFRAME, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(s);
		if (m_image.find()) {
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher( m_image.group());
			if (m.find()) {
				return m.group(1);
			}
		}

		return "";

	}

	public static String getBigIconByExt(String filename) {

		Integer index = filename.lastIndexOf(".");
		if (isNullOrEmpty(filename) || index < 0) {
			return "html.gif";
		}

		String ext = filename.substring(index + 1);
		switch (ext) {
		case "doc":
		case "docx":
			return "doc.gif";

		case "xls":
		case "xlsx":
			return "excel.gif";

		case "rar":
		case "zip":
			return "rar.gif";

		case "ppt":
		case "pptx":
			return "ppt.gif";
			
		case "png":
		case "jpg":
		case "jpeg":
		case "bmp":
			return "img.gif";
		case "pdf":
			return "pdf.gif";

		default:

			return "html.gif";
		}
	}

	public static String getKeyvalueUid(String key, Integer type) {
		return key + String.format("%06d", type);
	}

	public static String getFuzzyUsername(RyxUsersDTO user) {
		if (null == user) {
			return "******";
		}
		if (!isNullOrEmpty(user.getUsername())) {
			if(user.getUsername().length() == 14){
				return user.getUsername().substring(0, 6) + "****"  + user.getUsername().substring(11, 14);
			}
			return user.getUsername().substring(0, 1) + "****";
		} else if (!isNullOrEmpty(user.getMobile())) {
			return user.getMobile().substring(7) +  "****"  ;
		} else if (!isNullOrEmpty(user.getEmail())) {
			return user.getEmail().substring(8, 12) + "****";
		} else {
			return "r******";
		}
	}
	

	private static Integer seq = 0;
	
	public static synchronized String longIdString(Integer suffixLength){
		Date date = new Date();
	    if (seq.toString().length()>suffixLength) 
	    	seq = 0;    
	    return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0"+ suffixLength +"d", date, seq++);   
	}
	
	public static String getFuzzyMobile(String mobile) {
		if (StringHelper.isNullOrEmpty(mobile)) {
			return "";
		}
		if(mobile.length() != 11){
			return mobile;
		}
		return mobile.replace(mobile.substring(0, 7), "*******");
	}
	
	public static String getFuzzyMobile(String mobile,Integer beginIndex,Integer endIndex) {
		if (StringHelper.isNullOrEmpty(mobile)) {
			return "";
		}
		if(mobile.length() != 11){
			return mobile;
		}
		return mobile.replace(mobile.substring(beginIndex, endIndex), "*******");
	}
	
	public static String getFuzzyMobile1(String mobile) {
		if (StringHelper.isNullOrEmpty(mobile)) {
			return "";
		}
		if (mobile.length() != 11) {
			return mobile;
		}
		return "ryx****" + mobile.substring(7);
	}
	
	
	public static String getFuzzyMobile2(String mobile) {
		if (StringHelper.isNullOrEmpty(mobile)) {
			return "用户" + NumberHelper.generateFixLengthRandom(6);
		}
		if (mobile.length() != 11) {
			return mobile;
		}
		return "ryx****" + mobile.substring(7);
	}
	
	public static String encodeOffice365Url(String url) throws Exception{
		return new CryptoHelper(ConstHelper.OFFICE_365_DES_KEY,ConstHelper.OFFICE_365_DES_IV).encode(url);
	}
	
	public static String getFileNameByUrl(String string){
		return string.substring(string.lastIndexOf('/')+1, string.lastIndexOf('.'));
	}
	
	private static int counter = 0;
	public static void resetCounter(){
		counter = 0 ;
	}
	public static int stringNumbers(String str,String substr)  
    {  
		if(isNullOrEmpty(str) || isNullOrEmpty(substr)){
			return 0 ;
		}
		
        if (str.indexOf(substr)==-1)  
        {  
            return 0;  
        }  
        else if(str.indexOf(substr) != -1)  
        {  
            counter++;  
            stringNumbers(str.substring(str.indexOf("java")+substr.length()),substr);  
            return counter;  
        }  
        return 0;  
    } 
	
	/**
	 * ip + second ;
	 */
	static Map<String, Long>SEND_MOBILE_CODE_CATCH_SECOND = new HashMap<String, Long>() ;
	
	public static Boolean checkSendMobileSecond(Integer min,HttpServletRequest request){
		
		String ipAddr = IPUtils.getIpAddr(request) ;
		
		if(SEND_MOBILE_CODE_CATCH_SECOND.containsKey(ipAddr)){
			Long second = SEND_MOBILE_CODE_CATCH_SECOND.get(ipAddr);
			Long timestamp = System.currentTimeMillis()/1000 - second ;
			if(timestamp <= min * 60){  // 
				return true;  
			}
			else{
				SEND_MOBILE_CODE_CATCH_SECOND.put(ipAddr, System.currentTimeMillis()/1000);
				return false ;
			}
		}
		else{
			
			SEND_MOBILE_CODE_CATCH_SECOND.put(ipAddr, System.currentTimeMillis()/1000);
			return false; 
		}
	}
	
	
	/**
	 * ip + 次数 ;
	 */
	static Map<String, Integer>SEND_MOBILE_CODE_CATCH_TIMES = new HashMap<String, Integer>() ;
	public static Boolean checkSendMobileTimes(HttpServletRequest request){
		
		
		String ipAddr = IPUtils.getIpAddr(request) ;
		Integer currentTimes = 0 ;
		if(SEND_MOBILE_CODE_CATCH_TIMES.containsKey(ipAddr)){
			currentTimes = SEND_MOBILE_CODE_CATCH_TIMES.get(ipAddr) ;
		}
		
		if(currentTimes < 2){
			currentTimes ++ ;
			SEND_MOBILE_CODE_CATCH_TIMES.put(ipAddr, currentTimes);
			return false  ;
		}
		else{
			return true ;
		}
		
	}
	
	
}
