package com.rongdu.loans.api.common;


import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


public class RsaEncrypt {


//    public static final String RONG_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDwjs1tCSFINTS6KA4kIOjFyolF\n" +
//            "LYMUedpHaih8ALzFXbcKtYt/O1b2AcriptgVY6vlCWowK7xGDwwcnlPJ1O4CRyP8\n" +
//            "xMdhTqfdBHMgMM1yliRnNuggptoRF0bm1mtSD7ySHFaNq3X2mxzoFDZxOcPI6mBx\n" +
//            "5GcjLntUQSbsAw16JwIDAQAB";
   //生产
    public static final String RONG_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDk0QxbyRY49V62lvYy3htjy+uV\n" +
            "sT0eADLJPt3GbC9JeFtUjb1Q3yZi0be7YIgrrqJWtekw8SCr5l4ao47hZPA6X3HD\n" +
            "GJi8/FkPFOOzGK4KSTkshFefc2Udqy2ctRwJdji1TtsuPwyIsGAd3PV6NGQxtXGx\n" +
            "j3TeE/335+1RnA6QJwIDAQAB";

    /**
     * rsa验签
     *
     * @param content 被签名的内容
     * @param sign    签名后的结果
     *                字符集
     * @return 验签结果
     * @throws SignatureException 验签失败，则抛异常
     */
    public  static boolean doCheck(String content, byte[] sign, RSAPublicKey pubKey)
            throws SignatureException {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            return signature.verify((sign));
        } catch (Exception e) {
            throw new SignatureException("RSA验证签名[content = " + content
                    + "; charset = " + "; signature = " + sign + "]发生异常!", e);
        }
    }


    /**
     * 公钥
     */
    private static RSAPublicKey publicKey;

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public static RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

}