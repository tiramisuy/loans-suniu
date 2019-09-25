package com.rongdu.common.utils.jdq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/7.
 */
public class JdqUtil {

    static Logger log = LoggerFactory.getLogger(JdqUtil.class);

    //密钥均为1024位

    //机构方公钥
    public static String partnerPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzDNZwZMASr+44KhKwtLwGmLiN\n" +
            "xnU9EpCF4mc4k4Tb6txCNeMElbK1uxVMz5cU/IgEBSSjUx08nXZ1FGxktRoJPJBa\n" +
            "sNoTM+AqiDIHP4eWCq8Bts4vLWG+PsbIq9OzMjUMltJz/X0IL9bqwVbi76iGfrcX\n" +
            "6vBTvEcQajhc+VpjDwIDAQAB";
    //机构方私钥
    public static String partnerPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALMM1nBkwBKv7jgq\n" +
            "ErC0vAaYuI3GdT0SkIXiZziThNvq3EI14wSVsrW7FUzPlxT8iAQFJKNTHTyddnUU\n" +
            "bGS1Ggk8kFqw2hMz4CqIMgc/h5YKrwG2zi8tYb4+xsir07MyNQyW0nP9fQgv1urB\n" +
            "VuLvqIZ+txfq8FO8RxBqOFz5WmMPAgMBAAECgYBJdkXxXVT1FdvOjWyXtb/DkRt/\n" +
            "h+KISTqkw+yH6PbE6Eqwm71mHylwqK6nkjCxup7vz1Bl9m7zdMOqa9PBShDA83qJ\n" +
            "Z39FpNwoRuhbYX9jlSu8uPVh7FTfqghA7G9woOUYfOYibWg55RyziaLKlAFTYmps\n" +
            "dGgM7YqQPkpkhIh4qQJBAOHNqb2exq3R5fKdzQDoUir0NwoGYA7vZBHSCvOLtGt5\n" +
            "ZzN4gXoTR/VvDAJUUvqED/U+L5JaxzLWkc/TJWR4VRUCQQDK/pYopTlZDbvDSA9O\n" +
            "2PMnHLs50WgvR/T7WEeU145ahjQBzoFTt9OeUaltbLcZSwvhQ4bWYoZXxALw7ojt\n" +
            "LWiTAkEAnNRJBmhWubdFLIMpNW4mU6sW5yGSag0exbnK3Wi0wMirQvZ5hx4JJpAg\n" +
            "GSYUE+bRZpXwWIgwPkEFOQD/wY5KGQJALNAvTiHLm0FQ5jeOSZXTeBr7qjP0kBuO\n" +
            "lb5TbyR3JLzj1lJEcrruWNEmWiXZ9hd/M+e4aLOvfMik7ZN7XwDbSQJBAKJtL/UT\n" +
            "uEy+/wmrL+LRu7QrO6wXcnK/rEru3nmHoECpnFKtad4tWJF7ky2T23JO9CiDmrhl\n" +
            "3h70N8l7TeWWp9I=";

    //借点钱公钥
    public static String jdqPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOHgBdoItbrb25p9uguw7C04By\n" +
            "kSo+Art7+a3jVZ3AXvesUCIbtkMDlKS5uEt9gYt6JDT+ggejaDTvrpdcLwzBFJXi\n" +
            "yHNL4XBCH88SL++AAvLPyXTq30X3fyGkdEs/kDox9jWcmGSWlyDBKu1bwpYSsxi2\n" +
            "GJ5s+3/HqsJmVsI7nQIDAQAB";
    //借点钱私钥
    public static String jdqPrivateKey = "";

//    public static String gateWay = "http://openapi.jiedianqian.com/api/v1/channel/callback";
 //   public static String gateWay = "http://openapi-test.jiedianqian.com/api/v1/channel/callback";
    
    public static String channelCode = "new_jnfq";

    public static String encryptKey = "1234567890asdfgh";
    
    public static String FQ_DAY_28_DESC = "本次借款分两笔到账，第一笔%s元用于购买聚财APP商城购物券（请勿提现，扣款失败即产生本金%s违约金），扣款成功后第二笔款项%s元到账。";
    public static String DAY_15_DESC = "本次借款%1$s元（%2$s购物券+%3$s元），其中%2$s元会直接发送到您在聚财APP卡券中心，用于商城购物使用，%3$s元在确认借款后会到账。";

    @Getter
    public enum ChannelParse{

        JIEDIANQIAN("JDQAPI","new_jnfq"),
        JIEDIANQIAN2("JDQAPI2","");

        private String code;
        private String jdqCode;

        ChannelParse(String code, String jdqCode) {
            this.code = code;
            this.jdqCode = jdqCode;
        }

        public static String getCode(String jdqCode) {
            for (ChannelParse temp : ChannelParse.values()) {
                if (temp.getJdqCode().equals(jdqCode)) {
                    return temp.getCode();
                }
            }
            return null;
        }

        public static String getJdqCode(String code) {
            for (ChannelParse temp : ChannelParse.values()) {
                if (temp.getCode().equals(code)) {
                    return temp.getJdqCode();
                }
            }
            return null;
        }
    }


    /**
     * 借点钱调用机构方接口时，按此方式加密加签
     * @param param
     * @return
     */
    public static String jdqEncode(String param){
        String data = null;
        try {
            data = RSA.encryptByPublicKey(param, partnerPublicKey,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败");
        }

        String sign = RSAUtil.buildRSASignByPrivateKey(param, jdqPrivateKey);
        JSONObject paramJson=new JSONObject();
        paramJson.put("data", data);
        paramJson.put("sign", sign);

        return paramJson.toJSONString();
    }

    public static String partnerEncode(String param, String call, String channelId){

        CodecDemo.InvokeJdqRequest request = new CodecDemo.InvokeJdqRequest();
        request.setChannel_code(ChannelParse.getJdqCode(channelId));
        request.setCall(call);
        request.setData(param);
        request.setVersion("1.0");
        request.setTimestamp(new Date().getTime() / 1000);
        // （不需要对参数加密可忽略）
        request.setEncrypt(1); // 需要加密
        byte[] rawEncryptKey = encryptKey.getBytes(Charset.forName("UTF-8"));

        try {
            // （不需要对参数加密可忽略）加密密钥使用机构私钥进行RSA加密并base64编码，放入请求参数中
            request.setEncrypt_key(Base64.encodeBase64String(
                    RSATool.rsaEncryptByPrivateKey(RSATool.parseRSAPrivateKey(partnerPrivateKey), rawEncryptKey)));
            // （不需要对参数加密可忽略）使用AES加密，密钥使用原始加密密钥，加密后使用base64编码
            request.setData(Base64.encodeBase64String(
                    AESTool.aesEncrypt(request.getData().getBytes(Charset.forName("UTF-8")),
                            encryptKey)));
            // 准备加签
            String originSign = PreSignTool.buildToSignString(CodecDemo.InvokeJdqRequest.class, request);
            // 加签：使用机构私钥加签，签名使用base64编码
            request.setSign(Base64.encodeBase64String(
                    RSATool.rsaSignByPrivateKey(RSATool.parseRSAPrivateKey(partnerPrivateKey),
                            originSign.getBytes(Charset.forName("UTF-8")))));
        } catch (Exception e) {
            log.error("加密失败", e);
        }

        return JSONObject.toJSONString(request);
    }



    /**
     * 机构方获取借点钱的加密参数后，按此方式解密验签
     * @param paramJson
     * @return
     */
    public static String partnerDecode(JSONObject paramJson) throws Exception{
//        String data = RSAUtil.buildRSADecryptByPrivateKey(paramJson.getString("data"), partnerPrivateKey);//解密
//        boolean flag = RSAUtil.buildRSAverifyByPublicKey(data, jdqPublicKey, paramJson.getString("sign"));//验签
//        if(!flag){
//            System.out.println("验签失败");
//        }

        CodecDemo.JdqInvokeChannelRequest request = JSON.parseObject(paramJson.toJSONString(), CodecDemo.JdqInvokeChannelRequest.class);
        // 先验签
        RSAPublicKey publicKey = null;
         boolean verify = false;
        try {
            String originSign = PreSignTool.buildToSignString(CodecDemo.JdqInvokeChannelRequest.class, request);
             publicKey = RSATool.parseRSAPublicKey(jdqPublicKey);
            verify = RSATool.rsaVerifySignByPublicKey(publicKey,
                    originSign.getBytes(Charset.forName("UTF-8")), Base64.decodeBase64(request.getSign()));
        } catch (Exception e) {
            log.error("验签失败", e);
            throw new RuntimeException("验签失败");
        }


        if (!verify) {
            log.error("验签不通过");
            throw new RuntimeException("验签不通过");
        }
        if (request.getData() == null || request.getData().length() == 0) {
            log.error("无业务数据");
            return null;
        }
        // 原始字符串进行base64解码
        byte[] toDecodeBytes = Base64.decodeBase64(request.getData());
        // 需要解密则解密
        if (request.getEncrypt() == 1) {
            // 需要解密
            final byte[] bytes;
            try {
                bytes = RSATool.rsaDecryptByPublicKey(publicKey, Base64.decodeBase64(request.getEncrypt_key()));
                String originEncryptKey = new String(bytes, Charset.forName("UTF-8"));
                request.setEncrypt_key(originEncryptKey);
                toDecodeBytes = AESTool.aesDecrypt(toDecodeBytes, originEncryptKey);
            } catch (Exception e) {
                log.error("解密失败", e);
                throw new RuntimeException("解密失败");
            }
        }

        // 需要解压则解压缩
        if (request.getCompress() == 1) {
            try {
                toDecodeBytes = GzipTool.unCompress(toDecodeBytes);
            } catch (IOException e) {
                log.error("解压失败", e);
                throw new RuntimeException("解压失败");
            }
        }
        // 转换为原始数据
       // request.setData(new String(toDecodeBytes, Charset.forName("UTF-8")));
        return new String(toDecodeBytes, Charset.forName("UTF-8"));
    }


    /**
     * 机构方推送数据至借点钱时，按此方式加密加签
     * @param param
     * @return
     */
    public static String partnerEncode(String param){
        String data = null;
        try {
            data = RSA.encryptByPublicKey(param, jdqPublicKey,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败");
        }
        String sign = RSAUtil.buildRSASignByPrivateKey(param, partnerPrivateKey);
        JSONObject paramJson=new JSONObject();
        paramJson.put("data", data);
        paramJson.put("sign", sign);
        return paramJson.toJSONString();
    }


    /**
     * 借点钱压缩运营商数据
     * @param operator
     * @return
     */
    public static String zipOperator(String operator){
        return new String(Base64.encodeBase64(GzipUtil.compress(operator,"utf-8"))) ;
    }

    /**
     * 机构方解压运营商数据
     * @param zipOperator
     * @return
     */
    public static String unzipOperator(String zipOperator){
        return GzipUtil.uncompress(Base64.decodeBase64(zipOperator),"utf-8");
    }

    /**
     * MD5加密
     * @param idNumber
     * @return
     */
    public static String md5(String idNumber){
        return Md5Utils.getMD5(idNumber.toUpperCase());
    }
    
    public static String convertDegree(String degree) {
        if(degree == null) {
            return "1";
        }

        String res = null;
        switch (degree) {
            case "1":
                res = "1";//高中或以下
                break;
            case "2":
                res = "2";//大专
                break;
            case "3":
                res = "3";//本科
                break;
            case "4":
                res = "4";//研究生或以上
                break;
            default:
                res = "1";
        }
        return res;
    }
    
    public static String convertMarryCode(String degree) {
        if(degree == null) {
            return "1";
        }

        String res = null;
        switch (degree) {
            case "1":
                res = "1";//已婚
                break;
            case "2":
                res = "2";//未婚
                break;
            case "3":
                res = "3";//离异
                break;
            default:
                res = "3";
        }
        return res;
    }

  //  public static void main(String[] args) {
//        String postBody="{\"encrypt_key\":\"KeQbsV1DqdfpaRgbck1ddbhYU4ICmnDpU30hb65wMW/cYADimZEVZP4gQyuyR6qWMGPG4dRzdKp3AWi1fprSZZ5iiDSop7/WWNur3+hBHAhuc2Yb94IFSh165KEQeZH2/X0MD4WEr3K7k2ovYsiSCeJFXXCXAtAjV6+SdPxb0Qw=\",\"data\":\"5gxE03HqmURaZUXHVIre2NScQDzr+iYj4sI/3jFgL1px4s/ZBee8G2lnd4FsCaoQAVNS+46ENi6WlBMc4RF7NhYAARLBzI2TpR0wbMyd+mf2ISY/fCXr6ZX397y5Zz5FmNhC3FJJN8+4JdPoo2S1eisJwfW4SExB9BA5/0oK76fq/IScObQEPnNg64RoBtl+crUbQKKcyFvsQ27Oe29tdaw4rDSvyjvtcADJVX+99rFfXJHVFwp/OPGRIIA3mUap\",\"compress\":0,\"encrypt\":1,\"sign\":\"Y9OS1agwlx+b9nrpjeB3P1idtJBsOSWR+pOiJChdrt634GQATJevKVw11WeKufWPVowryH4DKXsCIWiq6+xKdsaeNU9mPBp1Y96Em1cfG+bxQylQOiIKJKGUSQbiU8mKkdH1wgVzjJ9IFcune+QyIydvRptpsAnR6x9VU2l1rMI=\",\"channel_code\":\"new_jcfq\"}";
//        String a = null;
//        try {
//            a = JdqUtil.partnerDecode(JSONObject.parseObject(postBody));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(a);

        //加密
//        String a = "{\"name\":\"张三\"}";
//
//        String str = partnerEncode(a, "feedbackOrderStatus");
//        System.out.println(str);
//
//        CodecDemo.InvokeJdqRequest request = JSON.parseObject(str, CodecDemo.InvokeJdqRequest.class);
//        try {
//            CodecDemo.jdqParseChannelInvokeRequest(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(request));
 //   }
    
}
