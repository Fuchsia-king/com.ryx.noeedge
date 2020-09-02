package com.king.nowedge.ryx_kf.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.king.nowedge.ryx_kf.utils.exception.DecryptException;
import com.king.nowedge.ryx_kf.utils.exception.EncryptException;
import org.apache.commons.codec.binary.Base64;


/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class CipherUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * 加密操作
     *
     * @param content 待加密内容
     * @param key 加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64.encodeBase64String(result);//通过Base64转码返回
            
        } catch (Exception ex) {
            throw new EncryptException("Failed to encrypt data.", ex);
        }
    }

    /**
     * 解密操作
     *
     * @param content 待解密内容
     * @param key 解密密钥
     * @return 返回解密后的字符串
     */
    public static String decrypt(String content, String key) {
        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
        	throw new DecryptException("Failed to decrypt data.", ex);
        }
    }

    /**
     * 生成加密秘钥
     */
    private static SecretKeySpec getSecretKey(final String key) throws NoSuchAlgorithmException {
    	MessageDigest md5 = MessageDigest.getInstance("MD5");
    	byte[] rawKey = md5.digest(key.getBytes());
        return new SecretKeySpec(rawKey, KEY_ALGORITHM);// 转换为AES专用密钥
    }

}