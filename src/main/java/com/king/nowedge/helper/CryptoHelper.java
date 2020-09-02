package com.king.nowedge.helper;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;


public class CryptoHelper {
	
	//加密算法的参数接口，IvParameterSpec是它的一个实现
	static AlgorithmParameterSpec iv = null;
	private static Key key = null;
	
	public CryptoHelper(String desKey,String desIv) throws Exception {
		// 设置密钥参数
		DESKeySpec keySpec = new DESKeySpec(desKey.getBytes());
		// 设置向量
		iv = new IvParameterSpec(desIv.getBytes());
		// 获得密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		key = keyFactory.generateSecret(keySpec);// 得到密钥对象
	}
	
  /**
    * @param data
    * @return
    * @throws 加密
    */
	public  String encode(String data) throws Exception {
		// 得到加密对象Cipher
		Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 设置工作模式为加密模式，给出密钥和向量
		enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(pasByte).replace("+", "_").replace("/", "@");
	}
	/**
    * @param data
    * @return
    * @throws 解密
    */
	public  String decode(String data) throws Exception {
		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data.replace("_","+").replace("@","/")));
		return new String(pasByte, "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		CryptoHelper tools = new CryptoHelper("1","2");
		System.out.println("加密:" + tools.encode("http://cn-beijing.aliyuncs.com/160125173708003409.pdf"));
		System.out.println("解密:" + tools.decode(tools.encode("http://cn-beijing.aliyuncs.com/160125173708003409.pdf")));
	}

}
