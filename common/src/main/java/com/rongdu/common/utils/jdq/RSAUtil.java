package com.rongdu.common.utils.jdq;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加解密工具类
 */
public final class RSAUtil {
    public static final String CHARSET = "UTF-8";
    //密钥算法
    public static final String ALGORITHM_RSA = "RSA";
    public static final String ALGORITHM_RSA_SIGN = "SHA256WithRSA";
    public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 2048;
    public static final String ALGORITHM_AES = "AES";
    public static final String ALGORITHM_AES_PKCS7 = "AES";
    public static final String ALGORITHM_DES = "DES";
    public static final String ALGORITHM_DESede = "DESede";
    //加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    //工作模式有ECB--电子密码本模式,CBC--加密分组链接模式,CFB--加密反馈模式,OFB--输出反馈模式,CTR--计数器模式
    //其中ECB过于简单而不安全,已被弃用,相对的CBC模式是最安全的,http://www.moye.me/2015/06/14/cryptography_rsa/
    private static final String ALGORITHM_CIPHER_AES = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM_CIPHER_AES_PKCS7 = "AES/CBC/PKCS7Padding";
    private static final String ALGORITHM_CIPHER_DES = "DES/ECB/PKCS5Padding";
    private static final String ALGORITHM_CIPHER_DESede = "DESede/ECB/PKCS5Padding";

    private RSAUtil(){}

    /**
     * RSA算法分段加解密数据
     * @param cipher 初始化了加解密工作模式后的javax.crypto.Cipher对象
     * @param opmode 加解密模式,值为javax.crypto.Cipher.ENCRYPT_MODE/DECRYPT_MODE
     * @param datas   待分段加解密的数据的字节数组
     * @return 加密或解密后得到的数据的字节数组
     * @create Feb 21, 2016 1:37:21 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 16;
        }else{
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }


    /**
     * RSA算法私钥加密数据
     * @param data 待加密的明文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥加密后的经过Base64编码的密文字符串
     * @create Feb 20, 2016 8:03:25 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static String buildRSAEncryptByPrivateKey(String data, String key){
        try{
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            //encrypt
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //return Base64.encodeBase64URLSafeString(cipher.doFinal(data.getBytes(CHARSET)));
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * RSA算法公钥加密数据
     * @param data 待加密的明文字符串
     * @param key  RSA公钥字符串
     * @return RSA公钥加密后的经过Base64编码的密文字符串
     * @create Feb 20, 2016 8:25:21 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static String buildRSAEncryptByPublicKey(String data, String key){
        try{
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            //encrypt
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            //return Base64.encodeBase64URLSafeString(cipher.doFinal(data.getBytes(CHARSET)));
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * RSA算法公钥解密数据
     * @param data 待解密的经过Base64编码的密文字符串
     * @param key  RSA公钥字符串
     * @return RSA公钥解密后的明文字符串
     * @create Feb 20, 2016 8:33:22 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static String buildRSADecryptByPublicKey(String data, String key){
        try{
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA, new BouncyCastleProvider());
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            //decrypt
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            //return new String(cipher.doFinal(Base64.decodeBase64(data)), CHARSET);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data)), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * RSA算法私钥解密数据
     * @param data 待解密的经过Base64编码的密文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥解密后的明文字符串
     * @create Feb 20, 2016 8:33:22 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static String buildRSADecryptByPrivateKey(String data, String key){
        try{
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            //decrypt
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //return new String(cipher.doFinal(Base64.decodeBase64(data)), CHARSET);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data)), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * RSA算法使用私钥对数据生成数字签名
     * 注意签名算法SHA1WithRSA已被废弃,推荐使用SHA256WithRSA
     * @param data 待签名的明文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥签名后的经过Base64编码的字符串
     * @create Feb 20, 2016 8:43:49 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static String buildRSASignByPrivateKey(String data, String key){
        try{
			return buildRSASignByPrivateKey(data,key,ALGORITHM_RSA_SIGN);
        }catch(Exception e){
            throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
        }
    }

	/**
	 * RSA算法使用私钥对数据生成数字签名
	 * 注意签名算法SHA1WithRSA已被废弃,推荐使用SHA256WithRSA
	 * @param data 待签名的明文字符串
	 * @param key RSA私钥字符串
	 * @param rsaSign 算法
	 * @return
	 */
	public static String buildRSASignByPrivateKey(String data, String key, String rsaSign){
		try{
			//通过PKCS#8编码的Key指令获得私钥对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			//sign
			Signature signature = Signature.getInstance(rsaSign);
			signature.initSign(privateKey);
			signature.update(data.getBytes(CHARSET));
			return Base64.encodeBase64URLSafeString(signature.sign());
		}catch(Exception e){
			throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
		}
	}


    /**
     * RSA算法使用公钥校验数字签名
     * @param data 参与签名的明文字符串
     * @param key  RSA公钥字符串
     * @param sign RSA签名得到的经过Base64编码的字符串
     * @return true--验签通过,false--验签未通过
     * @create Feb 20, 2016 8:51:49 PM
     * @author 玄玉<http://blog.csdn.net/jadyer>
     */
    public static boolean buildRSAverifyByPublicKey(String data, String key, String sign){
        try{
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA, new BouncyCastleProvider());
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            //verify
            Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(CHARSET));
            return signature.verify(Base64.decodeBase64(sign));
        }catch(Exception e){
            throw new RuntimeException("验签字符串[" + data + "]时遇到异常", e);
        }
    }
}
