package com.rongdu.loans.pay.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Cipher;

import com.rongdu.common.utils.EncodeUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class SecService {
    private static Logger log = Logger.getLogger(SecService.class);

    private static PrivateKey merPrivateKey;

    private static PublicKey acqPublicKey;

    private static String shareKey;


    /**
     * 报文编码
     *
     * @param req
     * @return
     */
    private String encode(Map<String, String> req) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : req.entrySet()) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
            }
        } catch (Exception e) {
            throw new Exception("通华报文编码失败");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 设置RSA签名参数，只需初始化一次。
     *
     * @param merPriCertPath 商户私钥证书绝对路径(商户签名使用)
     * @param acqPubCertPath 收单平台证书绝对路径(商户验证签名使用)
     */
    public static void setRSAParams(String merPriCertPath, String acqPubCertPath) {
        log.info("setRSAParams:merPriCertPath=" + merPriCertPath + ",acqPubCertPath" + acqPubCertPath);
        InputStream in = null;
        if (merPriCertPath != null && !"".equals(merPriCertPath)) {
            try {
                in = new FileInputStream(merPriCertPath);
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                        Base64.decodeBase64(new String(buffer)));
                KeyFactory keyf = KeyFactory.getInstance("RSA");
                merPrivateKey = keyf.generatePrivate(priPKCS8);
                log.info("Init privateKey successfully");
            } catch (Exception e) {
                log.error("Init privateKey failed", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }


        try {
            in = new FileInputStream(acqPubCertPath);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(new String(buffer));
            acqPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(
                    encodedKey));
            log.info("SecService init acq.cer successfully");
        } catch (Exception e) {
            log.error("Extract public key failed", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void setSHA256Params(String shareKey) {
        log.info("setSHA256Params:shareKey=" + shareKey);
        SecService.shareKey = shareKey;
    }

    /**
     * 验证签名，使用前确认SecService已经初始化成功。
     *
     * @param req 应答报文的全部要素
     */
    public static void checkSignature(Map<String, String> req) {
        log.info("checkSignature:" + req);
        if ("01".equals(req.get("sign_method"))) {
            checkRSASignature(acqPublicKey, req);
        } else if ("02".equals(req.get("sign_method"))) {
            checkSHASignature(req);
        } else {
            log.info("未知签名方式");
        }

    }

    private static void checkRSASignature(PublicKey publicKey,
                                          Map<String, String> req) {
        try {
            byte[] signature = hexDecode(req.remove("sign"));
            byte[] raw = sha1(sort(req).getBytes("utf-8")).getBytes();
            Signature st = Signature.getInstance("SHA1withRSA");
            st.initVerify(publicKey);
            st.update(raw);
            if (!st.verify(signature)) {
                throw new RuntimeException("验证签名失败");
            }
        } catch (Exception e) {
            log.error("check signature catch exception", e);
            throw new RuntimeException("验证签名异常");
        }

    }

    private static void checkSHASignature(Map<String, String> req) {

        if (StringUtils.isBlank(shareKey)) {
            throw new RuntimeException("未知找到商户密钥");
        }

        try {
            String signature = req.remove("sign");
            if (!sha256((sort(req) + shareKey).getBytes("utf-8")).equals(
                    signature)) {
                throw new RuntimeException("验证签名失败");
            }

        } catch (Exception e) {
            log.error("check signature catch exception", e);
            throw new RuntimeException("验证签名异常");
        }

    }

    /**
     * 附加签名，使用前确认SecService已经初始化成功。
     *
     * @param req 包含接口规范中除sign域外的所有请求要素
     */

    public static void addSignature(Map<String, String> req) {
        log.info("checkSignature:" + req);
        if ("01".equals(req.get("sign_method"))) {
            addRSASignature(req, merPrivateKey);
        } else if ("02".equals(req.get("sign_method"))) {
            addSHASignature(req);
        } else {
            throw new RuntimeException("未知的签名方式");
        }
    }

    private static void addSHASignature(Map<String, String> resp) {

        if (StringUtils.isBlank(shareKey)) {
            throw new RuntimeException("未知找到商户密钥");
        }
        try {
            resp.put("sign", sha256((sort(resp) + shareKey).getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("签名异常");
        }
    }

    private static void addRSASignature(Map<String, String> resp,
                                        PrivateKey privateKey) {
        try {
            byte[] raw = sha1(sort(resp).getBytes("utf-8")).getBytes();
            Signature st = Signature.getInstance("SHA1withRSA");
            st.initSign(privateKey);
            st.update(raw);
            resp.put("sign", EncodeUtils.hexEncode(st.sign()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("签名异常");
        }
    }

    private static String sort(Map<String, String> request) {
        String[] keys = request.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder raw = new StringBuilder();
        for (String key : keys) {
            raw.append(key).append("=").append(request.get(key)).append("&");
        }
        return raw.deleteCharAt(raw.length() - 1).toString();
    }

    private static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    private static String sha1(byte[] raw) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(raw);
            byte[] bytes = messageDigest.digest();
            return EncodeUtils.hexEncode(bytes);
        } catch (Exception e) {
            throw new RuntimeException("SHA-1 algorithm not supported", e);
        }
    }

    public static String sha256(byte[] raw) {

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(raw);
            byte[] bytes = messageDigest.digest();
            return EncodeUtils.hexEncode(bytes);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 algorithm not supported", e);
        }
    }

    public static String encryptPin(String pin) {
        return EncodeUtils.hexEncode(encryptData(pin.getBytes()));
    }

    public static byte[] encryptData(byte[] rawData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, acqPublicKey);

            return cipher.doFinal(rawData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA1RSA签名
     *
     * @param data
     * @param path
     */
    public static void signature(Map<String, String> data, String path) {
        PrivateKey privateKey = null;
        try (InputStream in = new FileInputStream(path)) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(new String(buffer)));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (FileNotFoundException e) {
            log.error("商户私钥不存在", e);
            throw new RuntimeException("商户私钥不存在", e);
        } catch (Exception e) {
            log.error("加载商户私钥失败", e);
            throw new RuntimeException("加载商户私钥失败", e);
        }

        addRSASignature(data, privateKey);
    }

    /**
     * SHA1RSA签名
     *
     * @param data
     * @param path
     */
    public static void signatureKey(Map<String, String> data, String strKey) {
        PrivateKey privateKey = null;
        try {
//            System.out.println(Base64.decodeBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu/7/xFeOwWcfYvDmJzynmE0MvlMxpSsbZ8VXWipzfdd0kcfyph4ocnOTBt2xtUaRBFGi5xdt1uichHXJEqv3l2s5aOHqd6jokX9aj3VxCKldcKtuoiRBQSJD0+9z+6jOUArTgcvgYtTA8GT9AGuhk0O+sfKLzp8lCnjDLaY6iwwIDAQAB"));
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (Exception e) {
            log.error("加载商户私钥失败", e);
            throw new RuntimeException("加载商户私钥失败", e);
        }
        addRSASignature(data, privateKey);
    }
}
