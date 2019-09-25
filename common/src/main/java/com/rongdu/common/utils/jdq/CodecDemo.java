package com.rongdu.common.utils.jdq;

import java.nio.charset.Charset;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CodecDemo
 *
 * @author hejiangshan on 2018年12月19日
 * @version 1.0
 */
public class CodecDemo {

    private static String jdqPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzDNZwZMASr+44KhKwtLwGmLiN\n"
            + "xnU9EpCF4mc4k4Tb6txCNeMElbK1uxVMz5cU/IgEBSSjUx08nXZ1FGxktRoJPJBa\n"
            + "sNoTM+AqiDIHP4eWCq8Bts4vLWG+PsbIq9OzMjUMltJz/X0IL9bqwVbi76iGfrcX\n"
            + "6vBTvEcQajhc+VpjDwIDAQAB";

    /**
     * java使用pkcs8形式的key
     */
    private static String jdqPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM4eAF2gi1utvbmn\n"
            + "26C7DsLTgHKRKj4Cu3v5reNVncBe96xQIhu2QwOUpLm4S32Bi3okNP6CB6NoNO+u\n"
            + "l1wvDMEUleLIc0vhcEIfzxIv74AC8s/JdOrfRfd/IaR0Sz+QOjH2NZyYZJaXIMEq\n"
            + "7VvClhKzGLYYnmz7f8eqwmZWwjudAgMBAAECgYBLdyqVHSHI+Ezdu7qrF7Ho3T1L\n"
            + "NSEtQRzZ4GmtMXynoV23JkPGt63DktnY8cQZ+KNTngwVHxCi4JV5KAhdBRD6u4s6\n"
            + "R2QzHcgrMKmMSLzRzT43EBjOpXJrlG6zjBhTWUklOQHGoFSRTmp2aXfw7Ot8ykpL\n"
            + "3L3o/x95jyrp1XWkVQJBAP0CsboC9uKvodAlMVSbwSl+SY/OxDrsA1vZSQYGudnY\n"
            + "TG1CGqs+UA5InWT+vKdOoc8IQHCL/JA+Bly4KMfO84MCQQDQjXbd9EpDLf5I2VWK\n"
            + "El8YcbGcu33QlXmg5+OnPasMCaNdaVax5HaIt/8HvqZlF7uYyJ/Uitnb9/jyaRj4\n"
            + "hkpfAkA6gcv9esACraCalG8y5I2nfbefFK5V/cvNCpMTXKzPjnAl99pY7SDwHKSg\n"
            + "/TM88TcDvCHCpOxzopQwDroHcnjnAkAZ71224GhTzmZu/MgMcZht28WwrghJZi1H\n"
            + "+05uP9cDmjc8qJrlaHntKqSGfAkJTVhA+cIWlmXdWwsUakl2royVAkEAo4frBh7t\n"
            + "Ta+yv2jKreXJMyU7Ob9q75otbd4zaz6eDh7aufWSaoKtY0ZD+4A13AmziWvQc0p8\n"
            + "h7SglUTKKVaScA==";

    private static String partnerPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzDNZwZMASr+44KhKwtLwGmLiN\n"
            + "xnU9EpCF4mc4k4Tb6txCNeMElbK1uxVMz5cU/IgEBSSjUx08nXZ1FGxktRoJPJBa\n"
            + "sNoTM+AqiDIHP4eWCq8Bts4vLWG+PsbIq9OzMjUMltJz/X0IL9bqwVbi76iGfrcX\n"
            + "6vBTvEcQajhc+VpjDwIDAQAB";

    /**
     * java使用pkcs8形式的key
     */
    private static String partnerPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALMM1nBkwBKv7jgq\n"
            + "ErC0vAaYuI3GdT0SkIXiZziThNvq3EI14wSVsrW7FUzPlxT8iAQFJKNTHTyddnUU\n"
            + "bGS1Ggk8kFqw2hMz4CqIMgc/h5YKrwG2zi8tYb4+xsir07MyNQyW0nP9fQgv1urB\n"
            + "VuLvqIZ+txfq8FO8RxBqOFz5WmMPAgMBAAECgYBJdkXxXVT1FdvOjWyXtb/DkRt/\n"
            + "h+KISTqkw+yH6PbE6Eqwm71mHylwqK6nkjCxup7vz1Bl9m7zdMOqa9PBShDA83qJ\n"
            + "Z39FpNwoRuhbYX9jlSu8uPVh7FTfqghA7G9woOUYfOYibWg55RyziaLKlAFTYmps\n"
            + "dGgM7YqQPkpkhIh4qQJBAOHNqb2exq3R5fKdzQDoUir0NwoGYA7vZBHSCvOLtGt5\n"
            + "ZzN4gXoTR/VvDAJUUvqED/U+L5JaxzLWkc/TJWR4VRUCQQDK/pYopTlZDbvDSA9O\n"
            + "2PMnHLs50WgvR/T7WEeU145ahjQBzoFTt9OeUaltbLcZSwvhQ4bWYoZXxALw7ojt\n"
            + "LWiTAkEAnNRJBmhWubdFLIMpNW4mU6sW5yGSag0exbnK3Wi0wMirQvZ5hx4JJpAg\n"
            + "GSYUE+bRZpXwWIgwPkEFOQD/wY5KGQJALNAvTiHLm0FQ5jeOSZXTeBr7qjP0kBuO\n"
            + "lb5TbyR3JLzj1lJEcrruWNEmWiXZ9hd/M+e4aLOvfMik7ZN7XwDbSQJBAKJtL/UT\n"
            + "uEy+/wmrL+LRu7QrO6wXcnK/rEru3nmHoECpnFKtad4tWJF7ky2T23JO9CiDmrhl\n"
            + "3h70N8l7TeWWp9I=";

    public static class MetaInfo {
        private static String testChannelCode = "new_test";
        private static String originEncryptKey = "1234567890asdfgh";
        private static String compressType = "gzip";
        private static JSONObject originData = new JSONObject();

        static {
            originData.put("user_name", "小明");
            originData.put("user_age", 18);
            originData.put("extra_info", "hello ad）*&……%%￥#");
            originData.put("long_data",
                    "{\"face_result_active\":{\"id_exceptions\":{\"id_photo_monochrome\":0,\"id_attacked\":0},"
                            + "\"result_faceid\":{\"thresholds\":{\"1e-6\":78.038,\"1e-5\":74.399,\"1e-4\":69.315,"
                            + "\"1e-3\":62.169},\"confidence\":84.793},\"request_id\":\"1542854983,"
                            + "33ec3647-25ae-429d-a29e-df11277fcb1d\",\"time_used\":562},\"jdq_order_id\":\"XXXXX\","
                            + "\"loan_info\":{\"period\":\"6\",\"amount\":\"10000\"},"
                            + "\"device_info\":{\"device_model\":\"iPhone\",\"device_id\":\"\",\"ip\":\"127.0.0.1\","
                            + "\"jailbreak_flag\":true,\"device_type\":\"IOS\",\"device_os\":\"ios 11.4.1\","
                            + "\"openudid\":\"\"},\"user_login_upload_log\":{\"address\":\"中国上海市上海市杨浦区政通路189号-3楼\","
                            + "\"latitude\":\"31.301847\",\"longitude\":\"121.512752\"},"
                            + "\"user_info\":{\"id_negative_valid_state\":\"{\\\"Temporary ID Photo\\\":0.0,"
                            + "\\\"Screen\\\":0.0,\\\"Edited\\\":0.0,\\\"Photocopy\\\":0.0,\\\"ID Photo\\\":1.0}\","
                            + "\"nation\":\"汉\",\"industry_code\":\"6\",\"id_card\":\"620522198908140319\","
                            + "\"industry\":\"计算机/互联网\",\"company_address\":\"安徽省,合肥市,庐阳区|合生回家000006\","
                            + "\"id_negative\":\"http://bktest-10010.oss-cn-hangzhou.aliyuncs"
                            + ".com/53229177/d3130bf5b6d24e4d9368ec7503a11801.png\",\"telecom_auth\":\"1\","
                            + "\"id_positive_valid_state\":\"{\\\"Temporary ID Photo\\\":0.0,\\\"Screen\\\":0.0,"
                            + "\\\"Edited\\\":0.0,\\\"Photocopy\\\":0.0,\\\"ID Photo\\\":1.0}\","
                            + "\"work_profession\":\"批发/零售业\",\"company_city\":\"合肥市\","
                            + "\"id_card_address\":\"甘肃省秦安县兴国镇宋洼村55号\",\"educate\":\"大专以下\",\"loan_usage_code\":\"6\","
                            + "\"id_expiry_date\":\"2036-09-30\",\"id_positive\":\"http://bktest-10010.oss-cn-hangzhou"
                            + ".aliyuncs.com/53229177/2473500d2e7e4705b7e808d88395645b.png\","
                            + "\"company_tel\":\"0211234567\","
                            + "\"id_signing_authority\":\"秦安县公安局\",\"face\":\"http://bktest-10010.oss-cn-hangzhou"
                            + ".aliyuncs"
                            + ".com/53229177/eb421370882b487193ab3d1f904f4ef0.png\",\"phone\":\"13122053988\","
                            + "\"company_name\":\"和姐姐家\",\"loan_usage\":\"旅游贷款\",\"name\":\"宋文华\","
                            + "\"id_start_date\":\"2016-09-30\",\"educate_code\":\"1\"},"
                            + "\"user_contact\":{\"name_spare\":\"测试服\",\"relation_spare_code\":\"91\",\"name\":\"花花\","
                            + "\"mobile\":\"15853664852\",\"relation_spare\":\"兄弟姓名\",\"relation_code\":\"81\","
                            + "\"relation\":\"父母姓名\",\"mobile_spare\":\"18964573188\"},"
                            + "\"address_book\":[{\"name\":\"谭晶\","
                            + "\"mobile\":\".86 13647336742\"},{\"name\":\"徐超\",\"mobile\":\"15852753251\"},"
                            + "{\"name\":\"向文辉\",\"mobile\":\"18271631169\"},{\"name\":\"尹光蓉\","
                            + "\"mobile\":\"15001956228\"},"
                            + "{\"name\":\"山东饺子馆\",\"mobile\":\"176 2148 1118\"},{\"name\":\"王远亮，雷雷\","
                            + "\"mobile\":\"15212570891\"},{\"name\":\"齐_玉兰二期c_1700_南主卧\",\"mobile\":\"13262982872\"},"
                            + "{\"name\":\"李守洪\",\"mobile\":\"18724019948\"}],"
                            + "\"app_data\":{\"com_demo_syh\":{\"name\":\"闪赢花\",\"version\":\"1_0\"}},"
                            + "\"operator\":\"\"}");
        }
    }

    @Data
    public static class JdqInvokeChannelRequest {
        private String channel_code;
        private String sign;
        private String data;
        private int encrypt;
        private String encrypt_key;
        private int compress;
        private String compress_type;
    }

    @Data
    @Accessors(chain = true)
    public static class InvokeConfig {
        private boolean needEncrypt;
        private boolean needCompress;
    }

    /**
     * 借点钱方使用借点钱私钥加密加签
     */
    public static JdqInvokeChannelRequest jdqBuildInvokeChannelRequest(InvokeConfig config) throws Exception {
        JdqInvokeChannelRequest request = new JdqInvokeChannelRequest();
        request.setChannel_code(MetaInfo.testChannelCode);
        String originData = JSON.toJSONString(MetaInfo.originData);
        final RSAPrivateKey privateKey = RSATool.parseRSAPrivateKey(jdqPrivateKey);
        if (StringUtils.isNotEmpty(originData)) {
            byte[] dataBytes = originData.getBytes(Charset.forName("UTF-8"));
            if (config.isNeedCompress()) {
                request.setCompress(1);
                request.setCompress_type(MetaInfo.compressType);
                dataBytes = GzipTool.compress(dataBytes);
            }
            if (config.isNeedEncrypt()) {
                request.setEncrypt(1);
                byte[] rawEncryptKey = MetaInfo.originEncryptKey.getBytes(Charset.forName("UTF-8"));
                request.setEncrypt_key(Base64.encodeBase64String(
                        RSATool.rsaEncryptByPrivateKey(privateKey, rawEncryptKey)));
                dataBytes = AESTool.aesEncrypt(dataBytes, MetaInfo.originEncryptKey);
            }
            originData = Base64.encodeBase64String(dataBytes);
        }
        request.setData(originData);
        String originSign = PreSignTool.buildToSignString(JdqInvokeChannelRequest.class, request);
        request.setSign(Base64.encodeBase64String(RSATool
                .rsaSignByPrivateKey(privateKey, originSign.getBytes(Charset.forName("UTF-8")))));
        return request;
    }

    /**
     * 机构方使用借点钱公钥验签解密
     * <p>验签</p>
     * <p>需要解密则解密：先用RSA解密得到解密的密钥，然后用AES解密</p>
     * <p>需要解压则解压</p>
     */
    public static void channelParseJdqInvokeRequest(JdqInvokeChannelRequest request) throws Exception {
        // 先验签
        String originSign = PreSignTool.buildToSignString(JdqInvokeChannelRequest.class, request);
        final RSAPublicKey publicKey = RSATool.parseRSAPublicKey(jdqPublicKey);
        final boolean verify = RSATool.rsaVerifySignByPublicKey(publicKey,
                originSign.getBytes(Charset.forName("UTF-8")), Base64.decodeBase64(request.getSign()));
        if (!verify) {
            throw new RuntimeException("验签不通过");
        }
        if (request.getData() == null || request.getData().length() == 0) {
            System.out.println("无业务数据");
            return;
        }
        // 原始字符串进行base64解码
        byte[] toDecodeBytes = Base64.decodeBase64(request.getData());
        // 需要解密则解密
        if (request.getEncrypt() == 1) {
            // 需要解密
            final byte[] bytes =
                    RSATool.rsaDecryptByPublicKey(publicKey, Base64.decodeBase64(request.getEncrypt_key()));
            String originEncryptKey = new String(bytes, Charset.forName("UTF-8"));
            request.setEncrypt_key(originEncryptKey);
            toDecodeBytes = AESTool.aesDecrypt(toDecodeBytes, originEncryptKey);
        }
        // 需要解压则解压缩
        if (request.getCompress() == 1) {
            toDecodeBytes = GzipTool.unCompress(toDecodeBytes);
        }
        // 转换为原始数据
        request.setData(new String(toDecodeBytes, Charset.forName("UTF-8")));
    }

    /**
     * 测试借点钱请求机构方
     */
    private static void testJdqInvokeChannel(InvokeConfig config) throws Exception {
        final String postBody = JSON.toJSONString(jdqBuildInvokeChannelRequest(config));
        System.out.println("借点钱请求串: ");
        System.out.println(postBody);
        JdqInvokeChannelRequest request = JSON.parseObject(postBody, JdqInvokeChannelRequest.class);
        channelParseJdqInvokeRequest(request);
        System.out.println("机构验签后解析得到的串: ");
        System.out.println(JSON.toJSONString(request));
        final String data = request.getData();
        System.out.println("机构得到的业务数据: ");
        System.out.println(data);
    }

    @Data
    public static class InvokeJdqRequest {
        private String channel_code;
        private String call;
        private String version;
        private String sign;
        private String data;
        private long timestamp;
        // 可选：是否加密
        private Integer encrypt;
        // 可选：加密密钥
        private String encrypt_key;
    }

    /**
     * 机构方拼接请求参数，并签名(使用机构方私钥)
     */
    public static InvokeJdqRequest channelBuildInvokeJdqRequest() throws Exception {
        InvokeJdqRequest request = new InvokeJdqRequest();
        request.setChannel_code(MetaInfo.testChannelCode);
        request.setCall("testApi");
        request.setData(JSON.toJSONString(MetaInfo.originData));
        request.setVersion("1.0");
        request.setTimestamp(new Date().getTime() / 1000);
        // （不需要对参数加密可忽略）
        request.setEncrypt(1); // 需要加密
        byte[] rawEncryptKey = MetaInfo.originEncryptKey.getBytes(Charset.forName("UTF-8"));
        // （不需要对参数加密可忽略）加密密钥使用机构私钥进行RSA加密并base64编码，放入请求参数中
        request.setEncrypt_key(Base64.encodeBase64String(
                RSATool.rsaEncryptByPrivateKey(RSATool.parseRSAPrivateKey(partnerPrivateKey), rawEncryptKey)));
        // （不需要对参数加密可忽略）使用AES加密，密钥使用原始加密密钥，加密后使用base64编码
        request.setData(Base64.encodeBase64String(
                AESTool.aesEncrypt(request.getData().getBytes(Charset.forName("UTF-8")),
                        MetaInfo.originEncryptKey)));
        // 准备加签
        String originSign = PreSignTool.buildToSignString(InvokeJdqRequest.class, request);
        // 加签：使用机构私钥加签，签名使用base64编码
        request.setSign(Base64.encodeBase64String(
                RSATool.rsaSignByPrivateKey(RSATool.parseRSAPrivateKey(partnerPrivateKey),
                        originSign.getBytes(Charset.forName("UTF-8")))));
        return request;
    }

    /**
     * 借点钱验签(使用机构方公钥)
     */
    public static void jdqParseChannelInvokeRequest(InvokeJdqRequest request) throws Exception {
        String originSign = PreSignTool.buildToSignString(InvokeJdqRequest.class, request);
        final RSAPublicKey publicKey = RSATool.parseRSAPublicKey(partnerPublicKey);
        final boolean verify = RSATool.rsaVerifySignByPublicKey(publicKey,
                originSign.getBytes(Charset.forName("UTF-8")), Base64.decodeBase64(request.getSign()));
        if (!verify) {
            throw new RuntimeException("验签不通过");
        }
        // 查看是否需要解密
        if (request.getEncrypt() != null && request.getEncrypt().intValue() == 1) {
            // 需要解密
            final byte[] bytes =
                    RSATool.rsaDecryptByPublicKey(publicKey, Base64.decodeBase64(request.getEncrypt_key()));
            String originEncryptKey = new String(bytes, Charset.forName("UTF-8"));
            request.setEncrypt_key(originEncryptKey);
            request.setData(new String(
                    AESTool.aesDecrypt(Base64.decodeBase64(request.getData()), originEncryptKey), "UTF-8"));
        } else if (Base64.isBase64(request.getData())) {
            request.setData(new String(Base64.decodeBase64(request.getData()), "UTF-8"));
        }
    }

    /**
     * 测试借点钱请求机构方
     */
    private static void testChannelInvokeJdq() throws Exception {
        final String postBody = JSON.toJSONString(channelBuildInvokeJdqRequest());
        System.out.println("机构加签后请求串: ");
        System.out.println(postBody);
        InvokeJdqRequest request = JSON.parseObject(postBody, InvokeJdqRequest.class);
        jdqParseChannelInvokeRequest(request);
        System.out.println("借点钱验签&解密后请求串: ");
        System.out.println(JSON.toJSONString(request));
    }

    /**
     * 测试借点钱请求机构方
     */
    private static void testChannelInvokeJdq(String req) throws Exception {
        InvokeJdqRequest request = JSON.parseObject(req, InvokeJdqRequest.class);
        jdqParseChannelInvokeRequest(request);
        System.out.println("借点钱验签&解密后请求串: ");
        System.out.println(JSON.toJSONString(request));
    }

    public static void testFlow() throws Exception {
        /**
         * 测试借点钱请求机构接口
         */
        // 不需要压缩，不需要加密
        System.out.println("--------- 不需要压缩，不需要加密 ---------");
        testJdqInvokeChannel(new InvokeConfig());
        // 需要压缩，不需要加密
        System.out.println("--------- 需要压缩，不需要加密 ---------");
        testJdqInvokeChannel(new InvokeConfig().setNeedCompress(true));
        // 不需要压缩，需要加密
        System.out.println("--------- 不需要压缩，需要加密 ---------");
        testJdqInvokeChannel(new InvokeConfig().setNeedEncrypt(true));
        // 需要压缩，需要加密
        System.out.println("--------- 需要压缩，需要加密 ---------");
        testJdqInvokeChannel(new InvokeConfig().setNeedCompress(true).setNeedEncrypt(true));
        /**
         * 测试机构请求借点钱
         */
        System.out.println("--------- 机构请求借点钱 ---------");
        testChannelInvokeJdq();
        /**
         *对结构请求验签
         */
        String channelReq = "{\n"
                + "  \"channel_code\": \"new_test\",\n"
                + "  \"call\": \"call\",\n"
                + "  \"version\": \"1.0\",\n"
                + "  \"data\": \"{\\\"extra_info\\\":\\\"hello ad）*&……%%￥#\\\",\\\"user_name\\\":\\\"小明\\\","
                + "\\\"long_data\\\":\\\"{\\\\\\\"face_result_active\\\\\\\":{\\\\\\\"id_exceptions"
                + "\\\\\\\":{\\\\\\\"id_photo_monochrome\\\\\\\":0,\\\\\\\"id_attacked\\\\\\\":0},"
                + "\\\\\\\"result_faceid\\\\\\\":{\\\\\\\"thresholds\\\\\\\":{\\\\\\\"1e-6\\\\\\\":78.038,"
                + "\\\\\\\"1e-5\\\\\\\":74.399,\\\\\\\"1e-4\\\\\\\":69.315,\\\\\\\"1e-3\\\\\\\":62.169},"
                + "\\\\\\\"confidence\\\\\\\":84.793},\\\\\\\"request_id\\\\\\\":\\\\\\\"1542854983,"
                + "33ec3647-25ae-429d-a29e-df11277fcb1d\\\\\\\",\\\\\\\"time_used\\\\\\\":562},"
                + "\\\\\\\"jdq_order_id\\\\\\\":\\\\\\\"XXXXX\\\\\\\","
                + "\\\\\\\"loan_info\\\\\\\":{\\\\\\\"period\\\\\\\":\\\\\\\"6\\\\\\\","
                + "\\\\\\\"amount\\\\\\\":\\\\\\\"10000\\\\\\\"},"
                + "\\\\\\\"device_info\\\\\\\":{\\\\\\\"device_model\\\\\\\":\\\\\\\"iPhone\\\\\\\","
                + "\\\\\\\"device_id\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"ip\\\\\\\":\\\\\\\"127.0.0.1\\\\\\\","
                + "\\\\\\\"jailbreak_flag\\\\\\\":true,\\\\\\\"device_type\\\\\\\":\\\\\\\"IOS\\\\\\\","
                + "\\\\\\\"device_os\\\\\\\":\\\\\\\"ios 11.4.1\\\\\\\",\\\\\\\"openudid\\\\\\\":\\\\\\\"\\\\\\\"},"
                + "\\\\\\\"user_login_upload_log\\\\\\\":{\\\\\\\"address\\\\\\\":\\\\\\\"中国上海市上海市杨浦区政通路189号-3"
                + "楼\\\\\\\",\\\\\\\"latitude\\\\\\\":\\\\\\\"31.301847\\\\\\\","
                + "\\\\\\\"longitude\\\\\\\":\\\\\\\"121.512752\\\\\\\"},"
                + "\\\\\\\"user_info\\\\\\\":{\\\\\\\"id_negative_valid_state\\\\\\\":\\\\\\\"{\\\\\\\\\\\"Temporary "
                + "ID Photo\\\\\\\\\\\":0.0,\\\\\\\\\\\"Screen\\\\\\\\\\\":0.0,\\\\\\\\\\\"Edited\\\\\\\\\\\":0.0,"
                + "\\\\\\\\\\\"Photocopy\\\\\\\\\\\":0.0,\\\\\\\\\\\"ID Photo\\\\\\\\\\\":1.0}\\\\\\\","
                + "\\\\\\\"nation\\\\\\\":\\\\\\\"汉\\\\\\\",\\\\\\\"industry_code\\\\\\\":\\\\\\\"6\\\\\\\","
                + "\\\\\\\"id_card\\\\\\\":\\\\\\\"620522198908140319\\\\\\\","
                + "\\\\\\\"industry\\\\\\\":\\\\\\\"计算机/互联网\\\\\\\",\\\\\\\"company_address\\\\\\\":\\\\\\\"安徽省,合肥市,"
                + "庐阳区|合生回家000006\\\\\\\",\\\\\\\"id_negative\\\\\\\":\\\\\\\"http://bktest-10010.oss-cn-hangzhou"
                + ".aliyuncs.com/53229177/d3130bf5b6d24e4d9368ec7503a11801.png\\\\\\\","
                + "\\\\\\\"telecom_auth\\\\\\\":\\\\\\\"1\\\\\\\","
                + "\\\\\\\"id_positive_valid_state\\\\\\\":\\\\\\\"{\\\\\\\\\\\"Temporary ID Photo\\\\\\\\\\\":0.0,"
                + "\\\\\\\\\\\"Screen\\\\\\\\\\\":0.0,\\\\\\\\\\\"Edited\\\\\\\\\\\":0.0,"
                + "\\\\\\\\\\\"Photocopy\\\\\\\\\\\":0.0,\\\\\\\\\\\"ID Photo\\\\\\\\\\\":1.0}\\\\\\\","
                + "\\\\\\\"work_profession\\\\\\\":\\\\\\\"批发/零售业\\\\\\\","
                + "\\\\\\\"company_city\\\\\\\":\\\\\\\"合肥市\\\\\\\","
                + "\\\\\\\"id_card_address\\\\\\\":\\\\\\\"甘肃省秦安县兴国镇宋洼村55号\\\\\\\","
                + "\\\\\\\"educate\\\\\\\":\\\\\\\"大专以下\\\\\\\",\\\\\\\"loan_usage_code\\\\\\\":\\\\\\\"6\\\\\\\","
                + "\\\\\\\"id_expiry_date\\\\\\\":\\\\\\\"2036-09-30\\\\\\\","
                + "\\\\\\\"id_positive\\\\\\\":\\\\\\\"http://bktest-10010.oss-cn-hangzhou.aliyuncs"
                + ".com/53229177/2473500d2e7e4705b7e808d88395645b.png\\\\\\\","
                + "\\\\\\\"company_tel\\\\\\\":\\\\\\\"0211234567\\\\\\\","
                + "\\\\\\\"id_signing_authority\\\\\\\":\\\\\\\"秦安县公安局\\\\\\\","
                + "\\\\\\\"face\\\\\\\":\\\\\\\"http://bktest-10010.oss-cn-hangzhou.aliyuncs"
                + ".com/53229177/eb421370882b487193ab3d1f904f4ef0.png\\\\\\\","
                + "\\\\\\\"phone\\\\\\\":\\\\\\\"13122053988\\\\\\\","
                + "\\\\\\\"company_name\\\\\\\":\\\\\\\"和姐姐家\\\\\\\",\\\\\\\"loan_usage\\\\\\\":\\\\\\\"旅游贷款\\\\\\\","
                + "\\\\\\\"name\\\\\\\":\\\\\\\"宋文华\\\\\\\",\\\\\\\"id_start_date\\\\\\\":\\\\\\\"2016-09-30\\\\\\\","
                + "\\\\\\\"educate_code\\\\\\\":\\\\\\\"1\\\\\\\"},"
                + "\\\\\\\"user_contact\\\\\\\":{\\\\\\\"name_spare\\\\\\\":\\\\\\\"测试服\\\\\\\","
                + "\\\\\\\"relation_spare_code\\\\\\\":\\\\\\\"91\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"花花\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"15853664852\\\\\\\","
                + "\\\\\\\"relation_spare\\\\\\\":\\\\\\\"兄弟姓名\\\\\\\","
                + "\\\\\\\"relation_code\\\\\\\":\\\\\\\"81\\\\\\\",\\\\\\\"relation\\\\\\\":\\\\\\\"父母姓名\\\\\\\","
                + "\\\\\\\"mobile_spare\\\\\\\":\\\\\\\"18964573188\\\\\\\"},"
                + "\\\\\\\"address_book\\\\\\\":[{\\\\\\\"name\\\\\\\":\\\\\\\"谭晶\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\".86 13647336742\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"徐超\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"15852753251\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"向文辉\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"18271631169\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"尹光蓉\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"15001956228\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"山东饺子馆\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"176 2148 1118\\\\\\\"},"
                + "{\\\\\\\"name\\\\\\\":\\\\\\\"王远亮，雷雷\\\\\\\",\\\\\\\"mobile\\\\\\\":\\\\\\\"15212570891\\\\\\\"},"
                + "{\\\\\\\"name\\\\\\\":\\\\\\\"齐_玉兰二期c_1700_南主卧\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"13262982872\\\\\\\"},{\\\\\\\"name\\\\\\\":\\\\\\\"李守洪\\\\\\\","
                + "\\\\\\\"mobile\\\\\\\":\\\\\\\"18724019948\\\\\\\"}],"
                + "\\\\\\\"app_data\\\\\\\":{\\\\\\\"com_demo_syh\\\\\\\":{\\\\\\\"name\\\\\\\":\\\\\\\"闪赢花\\\\\\\","
                + "\\\\\\\"version\\\\\\\":\\\\\\\"1_0\\\\\\\"}},\\\\\\\"operator\\\\\\\":\\\\\\\"\\\\\\\"}\\\","
                + "\\\"user_age\\\":18}\",\n"
                + "  \"timestamp\": 1548132493,\n"
                + "  \"sign\": \"reZVJq7laJfGJeRQs-Pq5xLvX-bZEJquHhPSJNvXZX-7G6v0ZiwU6CEo9"
                +
                "-2PBgZgYGEEMZZhVX8xIJhSMNqY83XAP2nEWiOGnr2zJyjlClUUgU51SMNUeEkvTRkdztvPatdvcwDe84yosJ1kZBXYNpRPDlN0H00kXYj3Drmn0hI\"\n"
                + "}";
        System.out.println("机构原始请求：");
        System.out.println(channelReq);
        testChannelInvokeJdq(channelReq);
        /**
         *对结构加密请求验签解密
         */
        String channelEncryptReq = "{\"channel_code\":\"new_test\",\"call\":\"call\",\"version\":\"1.0\","
                + "\"data\":\"QJEiPsc2LHyDKRjZdcEnTp9qAcLYEkCnB9R4GA9Y8NsjzmZsQhjt+eBREutzFuGUemc"
                + "+xao2ORT8wuFJ5d9mlO1Jo1LOpAKlrQKqY0tLwJBx3bZOAJ1EWaWc9wg2igXZA7ERSCOCDMX2VDnNQl"
                + "\\/1JkC8uolmHljIXwuqm7NydLv97UfbmpyN8ytGWdShN+a7\\/8LklOaGDTEVsDJg9W"
                + "\\/4xly7O6PXVwujv9eYFfMNmPrzc9oklQCwTfdrgEbBGCzN143S4kmuAlLcSccEdLA\\/klpEGQunXdOBbWWc8gzxBISTO+1"
                +
                "+YLsOSzvW6im3lbOfz5DRUI5QiAv3nBckU3vRxS1anysJvKFf8NURSyOlGvssNAXX5K1GN3i2pZdK8fVp93Oj2q2NVSGBAzFjmcJkw"
                + "kipjap\\/rphwSThouTk4hGBfj8WIttyHTnlf4bOxXCCKgmd1OzT3CJmGbjOgXlqP8xUF4mrmAWKSsexPOatXl+S\\/DnzIako"
                + "i0ykEk0hm1e86mI3Y22Y1P1LAtC2EtkyHx6PQDliRS4AFPQ06vl4Fr3yt45sCO6y+TGwz9FPkwTg0CGTpeB"
                + "GQDiGwkKW3mzdk4+FxVugenIZJmpuKZELI+qrxbX+gyV1TuoU0S0Wdv0PXmr460Ta+5B8fy99Gcknjm3RAJ+l4Pg74eea"
                + "gTDRQmffaAT3CyQr9\\/Bz8mn4AGyUlgKPjrJCusd1aIUnOyZQoI4kwQZQN5S6zfkZGWzkGUPnfRCrg7X\\/rh857zWJMEz+1"
                + "NVsObNNeXjj2vFwr3FFyfmy5E+T33u+kochZFKpQuvss5gfCJlYIWfY5vSpoRokauAhno7nhvDpJf8W6IXXAl+bJN"
                + "7Q8KX3jWwl7rWsgyJ0w6lHL85v\\/zZ+WqMtV7BYnnp1o8HgMj69CkkxdiimHPNxJydVY\\/Hc7c2I7zkSJsB4y"
                + "gHy\\/5jtbfmnjambUmR6FEJl274KWFe+M\\/9iZbPGdWQt1ZKRyXJIlcCYJuvhCTkwhSUebWhqRLU1Bsz8qLf4Z"
                + "Z0v6lY4BI\\/dG\\/SdobkCLNywXsi2xWarYTT50UlFvDVxD2NlF7EDsnnCSG1Dr9WKH38\\/yg1YRIgIM1oxP38QUCYu1z7I"
                + "iKXmZuBYApEmjlKru6h1NftMU4gtCNij+QtDwCKVXa4NGbZV36apIp9XENIt0yXCB3QMMQr7ZY17Mmx8sdBDOh2FMdoZJLGAQFUd"
                + "PGlt8bKzqMed2tn5I42iQjo3luhJxL8yIVc+rP75ktYDlCNR9691MYNXg3IgHNtNx6bKPCUVzqrdiFEm6a5RSOCPNeHPXNNzOyo"
                + "TCbrsNZT0A8vXj\\/Q3FLqcr\\/NbtWQoHRScfY8hupXfRK3PgyCmKWStW870lsmKMJT6CoCleuOFVJI6vMHfbWOblSS8"
                + "8hgD\\/uvClIYvReN9mE2Ozd02F8WHhVD8xjPPMa4dhK56u3czziGnWy2ZewTM8lDiAuTezQbeRk0bu40v5Ch4shWD3P7VV6u"
                + "xdBy8vE\\/VcOoS3igrc561ClCiotu7K1UB0bSHbF9kvQ7ibdrCrxehGt7lhKXAb9\\/o\\/4I0zlZrwsKYbxF3ZjBcL7XTK"
                + "HMBRD6VMqeTUSN2uYN1MDZStsEU6ERl6oUzG5uoKGI+4Ty2skpY4TUEOmA1e8ulPgu6BROve0P18sPQDaG349nRoVB+BM4"
                + "2DCo+oQ49dpXE2KWLQw6sYA7ZQmN\\/pcaCna\\/F+lDbXnF+rbZloGQbi0mPu9AxQfJGqQL3VKT8cKkphUVrvj4jxLrQ8BF"
                + "YA+szugc3BlF1gPJNNas9szGsyPxVWvDAko4D7GycDSkzQ13y2z4upKB20lKy80Kuk0AwhMhkc2LBUL9o9QkplPLRe3dnScB"
                + "ECz3yNe6FejN7lde26v+\\/1pDyB8XSEIq5B1BhcbGxpbo8+c8sZYMaJaIOVZ1jK4HSbEXuKeAy9d7TDGqmUjCUAj"
                + "\\/KyeyH9fpf2t+zw3gVB3ILf02+xwneYWOaYXm+Vykq15Gl\\/vfjPJ5XTgS2iZF1UsyLrapcxrkU"
                + "+BltQqERFbCgNN6aP7lLPx42rurpgtQOUDgqujnwuotpd+aZcVJuZ8cKOn82Zez6U78f0yWXjUI0kXokWDNytKQY+JA"
                + "\\/NiqGcRoQtCDMTr3t+2zoyy9342lp0v7Ogpi6rwSZMKzVkPBLFkxE\\/dJgjdQzn"
                + "+qMsqLcgw6ltRLyNaJJTnVACXAJEUNePL6gPl4Fb49ovuQ+URUL3BObL5Ry\\/om"
                + "+72c12zFOFgMlb8LmAT0PoM6EylyfJHHGFuZyiHsk27e7mTNnBIvbzsz7BiGczSWIq7t\\/L"
                + "\\/rhnvkRVbMxdl6q4wHxt0p7I2qTraYG5vAqMtZzU"
                +
                "+FSg5DhsZNvnisMzI1lDyun6EdBQm9MjUeLgrVIEdIDkxm3TlTcLBJWyu7zE3UOPuXZxCOqFk2ZZCCGkUb6qf87iQlmuaM1xMhfZL0O4m3awq8XoRre5YSlwG\\/f6P+CNM5Wa8LCmG8Rd2YwXC+10yhzAUQ+lTKnk1Iac2zI+HKpcvxrUItHgplpOxjmRzzdiP65zCRMNXD1BYLFBKUPCqbN70lwsquEhi5QxjnNR9pSnrPGm8M2qN2gan7kGelj5v+VSlT2xOG2TXgXUJKPWFIIXIiMdI3nTYthAi9vSwUt43T\\/4i9caeJTFhCteFZBYSyJufDrturrnkZe5jv9sjVQ3v+zRGOXy1juBMfLWl2eYWfcrvaN+1\\/\\/wmyKNKcljEjjXarjCRF\\/CPWY4AfBAbhq16oAO+jpS6j4LoXq4FT34eAnMZ8n90agI4Lmk3TBl2yfsNFJHIYxJluGfw\\/v7\\/XTdaDMAQ4DXCx+aOyaWQfLuQmlGx7Rzzm7G6Nw9yvW5z6WAu9SexwmdEnq3yFsDhDylqu0C6gVhwlBSiwrzB0XfF1mMgdGf5cuGslTcKjRPswGiUQingQnzSsb85m+I8vKJ1K4cHH6W4sd2SrpwZE1TE4y\\/bkRRpuK15My8+6Hewed3RZxLo1E1Q4I9KNwqevRKxSkbWV3iniA2c0M27eRSIFOnigtfBF69hg+rPTh0TAk6zvW4cI3K+t4MCWcjeMLHUvfJOa93Z0+jBDLp8698QdtXZSdaZxq37PsOitfF2Ac9pZcJ3s2Pw11t9Xtd5IHD1j6Xd4Q3\\/oRtUUpinWWMCowuEfAEzszBxQ4kKq82lLb5H5b5PIk5oC3Jhw9IpGXU37jKXDS2LAeLYep7e79EErkMWTfTPgQkFVDx8XQ+uJZHrYylU30dqao8Hpv82trjeeejhvNlK5ndD5LDbBI7DE9eGCNMYC8D5yati6YUpAdyDrSN3xzFzZIjVvoeEDXYq1DZfys92UqhZ8dm69VJ8b6zXybRBbZIWt\\/lz4+ey6aWy8xWcg8qeFABsRlhLfMLMJ\\/5dzF4Nj9z\\/V6ykQcmqc0fkJMwnqqHyDgQWUjmFQ4EFtsQpl\\/5Cc\\/kaHJMGUScQJL1tgxdDs+WLpUZrMTn0538kWYDcO35qAps5OtI5PVwGGjBBSd9zu+cCRPOP2SjKUxldL78wTuxkiYe3Rd4\\/MLnNyaSRwFaF7NrKoJZbwshEIMk6JShEFWFBoG0s09y+5imOgR7ofjIDrjNudVKvFggiBm\\/9+sODlqEp0epQUsasGVjozXmmtvl9YkP6w6rp6YAnIqtgFN8iALr\\/+s77VI1Y9AwJNGazQe9OR4MEWr1Bld0wyRAQRXbV1QK9k2CVABHCjAFL5ogGZFD07p+tJwSDzOPZ5XAycAibKirv3XloKVPMBWDjVsK6Oakf3c2COqBzd7c3+b76HmbPDTb4ClatNeZCsJgngnIaCqLqRbF1JmKeDCQyVv6XxOeEXYlsIoP0\\/terhKEeqEFVjxWukALxWvSpzk5HL1parIXG7Ar\\/ox7PW8dirNM5n4\\/iRlew14HoTFQNA==\",\"timestamp\":1548244494,\"encrypt\":1,\"encrypt_key\":\"P6g8JrlubvN0VFmwYEGQiiz+BhisA+ow4V6xcNd0mMHFfZfufn97bIzYlvkUJO8eu6weR735vpR\\/dDlMLTTvHTLj9JB82H5WwXCCf\\/dDMjTMACQZsD4XeCvJ+AzqZR4WKOf4odEk9GJTKZZAWStkOiZOhAv\\/r\\/ImvgAm3t9jKAE=\",\"sign\":\"ZdoVlr23GgkJsOK5qzNPBrZL8tNAFNQucnlfex4plHM71af3yMlVf5T3THt7tk3UrtMiqfgLfaUJ1Vl-V2IpoaIluinJRmU3yM8nUEmngzm8EzVZXGeQYUEvjDkRn5lUWQSnzplnVVpZlr7CuA_7FiTuieKkF4YBJDp69llnol8\"}\n";
        System.out.println("机构原始加密后请求：");
        System.out.println(channelEncryptReq);
        testChannelInvokeJdq(channelEncryptReq);

        // 验证机构不加密
        String channelReq1 =
                "{\"channel_code\":\"new_test\",\"call\":\"call\",\"version\":\"1.0\","
                        + "\"data\":\"eyJleHRyYV9pbmZvIjoiaGVsbG8gYWTvvIkqJuKApuKApiUl77+lIyIsInVzZXJfbmFtZSI6IuWwj"
                        +
                        "+aYjiIsImxvbmdfZGF0YSI6IntcImZhY2VfcmVzdWx0X2FjdGl2ZVwiOntcImlkX2V4Y2VwdGlvbnNcIjp7XCJpZF9waG90b19tb25vY2hyb21lXCI6MCxcImlkX2F0dGFja2VkXCI6MH0sXCJyZXN1bHRfZmFjZWlkXCI6e1widGhyZXNob2xkc1wiOntcIjFlLTZcIjo3OC4wMzgsXCIxZS01XCI6NzQuMzk5LFwiMWUtNFwiOjY5LjMxNSxcIjFlLTNcIjo2Mi4xNjl9LFwiY29uZmlkZW5jZVwiOjg0Ljc5M30sXCJyZXF1ZXN0X2lkXCI6XCIxNTQyODU0OTgzLDMzZWMzNjQ3LTI1YWUtNDI5ZC1hMjllLWRmMTEyNzdmY2IxZFwiLFwidGltZV91c2VkXCI6NTYyfSxcImpkcV9vcmRlcl9pZFwiOlwiWFhYWFhcIixcImxvYW5faW5mb1wiOntcInBlcmlvZFwiOlwiNlwiLFwiYW1vdW50XCI6XCIxMDAwMFwifSxcImRldmljZV9pbmZvXCI6e1wiZGV2aWNlX21vZGVsXCI6XCJpUGhvbmVcIixcImRldmljZV9pZFwiOlwiXCIsXCJpcFwiOlwiMTI3LjAuMC4xXCIsXCJqYWlsYnJlYWtfZmxhZ1wiOnRydWUsXCJkZXZpY2VfdHlwZVwiOlwiSU9TXCIsXCJkZXZpY2Vfb3NcIjpcImlvcyAxMS40LjFcIixcIm9wZW51ZGlkXCI6XCJcIn0sXCJ1c2VyX2xvZ2luX3VwbG9hZF9sb2dcIjp7XCJhZGRyZXNzXCI6XCLkuK3lm73kuIrmtbfluILkuIrmtbfluILmnajmtabljLrmlL\\/pgJrot68xODnlj7ctM+alvFwiLFwibGF0aXR1ZGVcIjpcIjMxLjMwMTg0N1wiLFwibG9uZ2l0dWRlXCI6XCIxMjEuNTEyNzUyXCJ9LFwidXNlcl9pbmZvXCI6e1wiaWRfbmVnYXRpdmVfdmFsaWRfc3RhdGVcIjpcIntcXCJUZW1wb3JhcnkgSUQgUGhvdG9cXCI6MC4wLFxcIlNjcmVlblxcIjowLjAsXFwiRWRpdGVkXFwiOjAuMCxcXCJQaG90b2NvcHlcXCI6MC4wLFxcIklEIFBob3RvXFwiOjEuMH1cIixcIm5hdGlvblwiOlwi5rGJXCIsXCJpbmR1c3RyeV9jb2RlXCI6XCI2XCIsXCJpZF9jYXJkXCI6XCI2MjA1MjIxOTg5MDgxNDAzMTlcIixcImluZHVzdHJ5XCI6XCLorqHnrpfmnLov5LqS6IGU572RXCIsXCJjb21wYW55X2FkZHJlc3NcIjpcIuWuieW+veecgSzlkIjogqXluIIs5bqQ6Ziz5Yy6fOWQiOeUn+WbnuWutjAwMDAwNlwiLFwiaWRfbmVnYXRpdmVcIjpcImh0dHA6Ly9ia3Rlc3QtMTAwMTAub3NzLWNuLWhhbmd6aG91LmFsaXl1bmNzLmNvbS81MzIyOTE3Ny9kMzEzMGJmNWI2ZDI0ZTRkOTM2OGVjNzUwM2ExMTgwMS5wbmdcIixcInRlbGVjb21fYXV0aFwiOlwiMVwiLFwiaWRfcG9zaXRpdmVfdmFsaWRfc3RhdGVcIjpcIntcXCJUZW1wb3JhcnkgSUQgUGhvdG9cXCI6MC4wLFxcIlNjcmVlblxcIjowLjAsXFwiRWRpdGVkXFwiOjAuMCxcXCJQaG90b2NvcHlcXCI6MC4wLFxcIklEIFBob3RvXFwiOjEuMH1cIixcIndvcmtfcHJvZmVzc2lvblwiOlwi5om55Y+RL+mbtuWUruS4mlwiLFwiY29tcGFueV9jaXR5XCI6XCLlkIjogqXluIJcIixcImlkX2NhcmRfYWRkcmVzc1wiOlwi55SY6IKD55yB56em5a6J5Y6\\/5YW05Zu96ZWH5a6L5rS85p2RNTXlj7dcIixcImVkdWNhdGVcIjpcIuWkp+S4k+S7peS4i1wiLFwibG9hbl91c2FnZV9jb2RlXCI6XCI2XCIsXCJpZF9leHBpcnlfZGF0ZVwiOlwiMjAzNi0wOS0zMFwiLFwiaWRfcG9zaXRpdmVcIjpcImh0dHA6Ly9ia3Rlc3QtMTAwMTAub3NzLWNuLWhhbmd6aG91LmFsaXl1bmNzLmNvbS81MzIyOTE3Ny8yNDczNTAwZDJlN2U0NzA1YjdlODA4ZDg4Mzk1NjQ1Yi5wbmdcIixcImNvbXBhbnlfdGVsXCI6XCIwMjExMjM0NTY3XCIsXCJpZF9zaWduaW5nX2F1dGhvcml0eVwiOlwi56em5a6J5Y6\\/5YWs5a6J5bGAXCIsXCJmYWNlXCI6XCJodHRwOi8vYmt0ZXN0LTEwMDEwLm9zcy1jbi1oYW5nemhvdS5hbGl5dW5jcy5jb20vNTMyMjkxNzcvZWI0MjEzNzA4ODJiNDg3MTkzYWIzZDFmOTA0ZjRlZjAucG5nXCIsXCJwaG9uZVwiOlwiMTMxMjIwNTM5ODhcIixcImNvbXBhbnlfbmFtZVwiOlwi5ZKM5aeQ5aeQ5a62XCIsXCJsb2FuX3VzYWdlXCI6XCLml4XmuLjotLfmrL5cIixcIm5hbWVcIjpcIuWui+aWh+WNjlwiLFwiaWRfc3RhcnRfZGF0ZVwiOlwiMjAxNi0wOS0zMFwiLFwiZWR1Y2F0ZV9jb2RlXCI6XCIxXCJ9LFwidXNlcl9jb250YWN0XCI6e1wibmFtZV9zcGFyZVwiOlwi5rWL6K+V5pyNXCIsXCJyZWxhdGlvbl9zcGFyZV9jb2RlXCI6XCI5MVwiLFwibmFtZVwiOlwi6Iqx6IqxXCIsXCJtb2JpbGVcIjpcIjE1ODUzNjY0ODUyXCIsXCJyZWxhdGlvbl9zcGFyZVwiOlwi5YWE5byf5aeT5ZCNXCIsXCJyZWxhdGlvbl9jb2RlXCI6XCI4MVwiLFwicmVsYXRpb25cIjpcIueItuavjeWnk+WQjVwiLFwibW9iaWxlX3NwYXJlXCI6XCIxODk2NDU3MzE4OFwifSxcImFkZHJlc3NfYm9va1wiOlt7XCJuYW1lXCI6XCLosK3mmbZcIixcIm1vYmlsZVwiOlwiLjg2IDEzNjQ3MzM2NzQyXCJ9LHtcIm5hbWVcIjpcIuW+kOi2hVwiLFwibW9iaWxlXCI6XCIxNTg1Mjc1MzI1MVwifSx7XCJuYW1lXCI6XCLlkJHmlofovolcIixcIm1vYmlsZVwiOlwiMTgyNzE2MzExNjlcIn0se1wibmFtZVwiOlwi5bC55YWJ6JOJXCIsXCJtb2JpbGVcIjpcIjE1MDAxOTU2MjI4XCJ9LHtcIm5hbWVcIjpcIuWxseS4nOmluuWtkOmmhlwiLFwibW9iaWxlXCI6XCIxNzYgMjE0OCAxMTE4XCJ9LHtcIm5hbWVcIjpcIueOi+i\\/nOS6ru+8jOmbt+mbt1wiLFwibW9iaWxlXCI6XCIxNTIxMjU3MDg5MVwifSx7XCJuYW1lXCI6XCLpvZBf546J5YWw5LqM5pyfY18xNzAwX+WNl+S4u+WNp1wiLFwibW9iaWxlXCI6XCIxMzI2Mjk4Mjg3MlwifSx7XCJuYW1lXCI6XCLmnY7lrojmtKpcIixcIm1vYmlsZVwiOlwiMTg3MjQwMTk5NDhcIn1dLFwiYXBwX2RhdGFcIjp7XCJjb21fZGVtb19zeWhcIjp7XCJuYW1lXCI6XCLpl6rotaLoirFcIixcInZlcnNpb25cIjpcIjFfMFwifX0sXCJvcGVyYXRvclwiOlwiXCJ9IiwidXNlcl9hZ2UiOjE4fQ==\",\"timestamp\":1552283216,\"encrypt\":0,\"encrypt_key\":\"a\",\"sign\":\"GNuVl8hupy3sT7VAO3y55UvGXIky4RdL0v3dbI8GV3swRKlWWsqpsjLa-FwHEq2_1HC_OVCtOf_92j4eQYz5b3NCg99AyGKlsEenummzMZ6ppG5kiElbtLzpZxC2cscKca9w5cskihHmh8PJgaQX7tU1GW6ZKmRPsh5g-lhb3UE\"}\n";
        System.out.println("机构原始请求：");
        System.out.println(channelReq1);
        testChannelInvokeJdq(channelReq1);
    }

    public static void main(String[] args) throws Exception {
        testFlow();
    }

}
