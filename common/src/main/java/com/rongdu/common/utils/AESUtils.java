package com.rongdu.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lee on 2018/9/5.
 */
public class AESUtils {

    public static String aesEncrypt(String content, String encryptKey) throws
            Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }


    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }


    private static byte[] aesEncryptToBytes(String content, String encryptKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(base64Decode(encryptKey), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return new String(cipher.doFinal(encryptBytes));
    }

    private static byte[] base64Decode(String base64Code) throws Exception {
        return org.springframework.util.StringUtils.isEmpty(base64Code) ? null : new
                BASE64Decoder().decodeBuffer(base64Code);
    }


    public static String base64Encode(String msg) throws Exception {
        return org.springframework.util.StringUtils.isEmpty(msg) ? null : base64Encode(msg.getBytes("utf-8"));
    }


    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


}
