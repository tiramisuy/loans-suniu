package com.rongdu.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import com.alibaba.fastjson.JSONObject;

/**
 * @author : Kingdy
 * @Description : 标准化接口的签名，验签，加解密工具类
 * @Copyright : Sinaif Software Co.,Ltd.Rights Reserved
 * @Company : 海南新浪爱问普惠科技有限公司
 * @Date : 1.0 Create Date : 2018/10/11 20:20
 */
public class StandardDesUtil {

    private static final Integer DES_KEY_LEN = 16;
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public StandardDesUtil() {
    }

    /**
     * 生成DES秘钥，长度为16位
     * @return
     * @throws Exception
     */
    public static String generateDesKey() throws Exception {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < DES_KEY_LEN; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * DES 算法 ECB 模式 PKCS5 填充方式加密
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String source, String key) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secureKey = keyFactory.generateSecret(desKey);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(1, secureKey,random);

        byte[] buf = cipher.doFinal(source.getBytes());

        return new String(Base64.encodeBase64(buf));
    }

    /**
     * DES 算法 ECB 模式 PKCS5 填充方式解密
     * @param cryptograph
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph, String key) throws Exception {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(2, secureKey,random);
            byte[] buf = cipher.doFinal(Base64.decodeBase64(cryptograph.getBytes()));
            return new String(buf,"utf-8");
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 签名方法
     * @param content
     * @param privateKey
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(String content, String privateKey) throws Exception {
        String charset = "UTF-8";

        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(charset));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 验签方法
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean checkSign(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes());
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            boolean bverify = signature.verify(Base64.decodeBase64(sign.getBytes()));
            return bverify;
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }

    /**
     * 把json串中的所有的参数按照参数名做字典序排序
     * @param json
     * @return
     */
    public static String getSignParamStr(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String,Object> paramMap = jsonObject;

        Collection<String> keySet = new LinkedHashSet<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if( entry.getValue() != null && !"".equals(entry.getValue()) && !"sign".equals(entry.getKey())){
                keySet.add(entry.getKey());
            }
        }
        //按照参数名做字典序排序
        List list=new ArrayList<String>(keySet);
        Collections.sort(list);
        //拼接参数
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<list.size();i++){
            if(i < list.size()-1){
                sb.append(list.get(i) + "=" + paramMap.get(list.get(i)) + "&");
            }else{
                sb.append(list.get(i) + "=" + paramMap.get(list.get(i)));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String e = "{\"order_no\":\"15687924685234\",\"contract_return_url\":\"http://static.sinawallent.com/test.html\"}";
        String sign = StandardDesUtil.sign(e, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOxdmzIQlEZxW6mDstkZbDh07ryTBFyktc7MODQmiMj1yKaqnA/SFAUCopmkxHOteo05U7QT/Tq58MB8kASPkMpVo64b3Xy+bLflgf+Wh4BJA77etYTrO3Cgf0Cq3BWsB8gTcRmTjymsqCAx9hSPMkCPt4RUsSNqpnRpXA7Dz9JNAgMBAAECgYEAogbExTwCOIuqdvhVmGbJ/aWCpfftzIgILRtnB7DGoWOCyWU4l8u9d3XH+qStGlL4KGj8zPvK4f/mXjzFCKtVZ1yviwH05yM5Ko9JSXlyjnMLnB8BZ7YuVU5w6PcioijxlDZbI5/BXDLxH8wg9lwZTKLjBp0wtknhYueRVD1Gdw0CQQD2aXUR6xx7G7uvtYUgyUjVfXwhLd/ax3FRO/FvxYFT0ZkZ+P8SiXEzVusdG/HwJQuGvwSVb9TfD2n9lavd5v+XAkEA9ZATkbG6MD9w7ALe7gBo3rR/GBpLW2APwxwhemwfOOxdnzF9Pwj94lwDUlssIyLs27RjmIhf1iTSz4p8aXm5uwJAKOV1zaSE1JXUv6PT1Y4yrWQnPuZ7ObdSQlY9ivxvq3ak/1+JDg2LoSay3ODvdZjgYdvAKgEmhmLrh2/Y13poPwJAMFIJh8HRQW06azalZ2/tMuWRnMyPx4s/jsUaUVy1SB46zfiPDLhYPRKeFLacGM3mjdURRBKRyTG1yeEWWir5wQJAI0Guik1snweK2gzAEpbu6HvJz2RVzW+vp3NoqtsgZ2lxbTutj3et/PN7+xKlU4hnGLk+T05Lr7e2jesJgwkXlQ==");
        System.out.println("sign:" + sign);
        String key = generateDesKey();
        String e1 = StandardDesUtil.encrypt(e,key);
        System.out.println("encrypt:"+ e1);
        String s = StandardDesUtil.decrypt(e1, key);
        System.out.println("decrypt:"+s);
        boolean isTrueSign = StandardDesUtil.checkSign(e,sign,"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsXZsyEJRGcVupg7LZGWw4dO68kwRcpLXOzDg0JojI9cimqpwP0hQFAqKZpMRzrXqNOVO0E/06ufDAfJAEj5DKVaOuG918vmy35YH/loeASQO+3rWE6ztwoH9AqtwVrAfIE3EZk48prKggMfYUjzJAj7eEVLEjaqZ0aVwOw8/STQIDAQAB");
        System.out.println("checkSign:"+ isTrueSign);
        String s1 = generateDesKey();
        System.out.println("des_key: "+ s1);

    }
}
