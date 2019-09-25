package com.rongdu.loans.common;


import com.rongdu.common.config.Global;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.vo.FileResult;
import com.rongdu.loans.loan.vo.FileVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

public class XJ360FQUtil {

    private static String UA;

    private static String SignKey;

    private static String URL;


    public static String XianJinBaiKaRequest(Object obj, String call) throws Exception {
        XJ360FQUtil.init(XianJinBaiKaConfig.xianjinbaikafq_ua, XianJinBaiKaConfig.xianjinbaikafq_signkey, XianJinBaiKaConfig.xianjinbaika_url);
        String json = JsonMapper.toJsonString(obj);
        String sign = XJ360FQUtil.getRequestSign(call, json);
        String postfileds = "ua=" + UA + "&call=" + call + "&args=" + json + "&sign=" + sign;
        return XJ360FQUtil.sendRequest(postfileds);
    }
    public static long cleanCustUserInfoCache(String userId) {
        long result = 0;
        if (StringUtils.isNotBlank(userId)) {
            // 缓存用户信息
            result = JedisUtils.delObject(
                    Global.USER_CACHE_PREFIX + userId);
        } else {

        }
        return result;
    }

    public static void init(String ua, String signKey, String url) {
        if (ua == null || ua.trim().length() == 0) {
            throw new RuntimeException("参数 ua 非法，原因：不可以为空值");
        }
        if (signKey == null || signKey.trim().length() == 0) {
            throw new RuntimeException("参数 signKey 非法，原因：不可以为空值");
        }
        if (url == null || url.trim().length() == 0) {
            throw new RuntimeException("参数 url 非法，原因：不可以为空值");
        }

        XJ360FQUtil.UA = ua;
        XJ360FQUtil.SignKey = signKey;
        XJ360FQUtil.URL = url;
    }

    /**
     * 生成请求签名
     *
     * @param call 接口标识
     * @param args 接口请求参数; JSON格式字符串
     * @return 请求签名
     */
    public static String getRequestSign(String call, String args) {
        if (UA == null || UA.trim().length() == 0) {
            throw new RuntimeException("请先调用 init 方法完成请求参数初始化");
        }
        if (SignKey == null || SignKey.trim().length() == 0) {
            throw new RuntimeException("请先调用 init 方法完成请求参数初始化");
        }
        if (call == null || call.trim().length() == 0) {
            throw new RuntimeException("参数 call 非法，原因：不可以为空值");
        }
        if (args == null || args.trim().length() == 0) {
            throw new RuntimeException("参数 args 非法，原因：不可以为空值");
        }

        String signkey = UA + SignKey + UA;

        String sign = getMD5(signkey + call + signkey + args + signkey);

        return sign;
    }

    /**
     * 验证请求签名
     *
     * @param call 接口标识
     * @param args 接口请求参数; JSON格式字符串
     * @param sign 接口请求签名
     * @return 是否匹配
     */
    public static boolean authRequstSign(String call, String args, String sign) {
        if (UA == null || UA.trim().length() == 0) {
            throw new RuntimeException("请先调用 init 方法完成请求参数初始化");
        }
        if (SignKey == null || SignKey.trim().length() == 0) {
            throw new RuntimeException("请先调用 init 方法完成请求参数初始化");
        }
        if (call == null || call.trim().length() == 0) {
            throw new RuntimeException("参数 call 非法，原因：不可以为空值");
        }
        if (args == null || args.trim().length() == 0) {
            throw new RuntimeException("参数 args 非法，原因：不可以为空值");
        }
        if (sign == null || sign.trim().length() == 0) {
            throw new RuntimeException("参数 sign 非法，原因：不可以为空值");
        }

        String okSign = getRequestSign(call, args);
        return sign.equalsIgnoreCase(okSign);
    }


    /**
     * 发送请求
     *
     * @param postfileds 原始POST报文
     * @return 请求结果
     * @throws Exception
     */
    public static String sendRequest(String postfileds) throws Exception {
        URL u = new URL(URL);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
        conn.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
        conn.setDoOutput(true);// 是否打开输出流 true|false
        conn.setDoInput(true);// 是否打开输入流true|false
        conn.setRequestMethod("POST");// 提交方法POST|GET
        conn.setUseCaches(false);// 是否缓存true|false
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postfileds.length()));
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        conn.connect();// 打开连接端口

        OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        os.write(postfileds);
        os.flush();
        os.close();

        // 获得响应状态
        int resultCode = conn.getResponseCode();
        if (HttpURLConnection.HTTP_OK != resultCode) {
            throw new Exception("接口请求失败！错误码：" + resultCode);
        }

        StringBuffer sb = new StringBuffer();
        String readLine = new String();
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        while ((readLine = responseReader.readLine()) != null) {
            sb.append(readLine).append("\n");
        }
        responseReader.close();

        return sb.toString();
    }

    /**
     * 生成md5
     *
     * @param message
     * @return
     */
    public static String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes("UTF-8");

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str.toLowerCase();
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }

    public static String convertRelation(String Relation) {
        String res = null;
        switch (Relation) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "9":
            case "10":
            case "11":
            case "12":
                res = "1";
                break;
            case "5":
            case "6":
            case "7":
            case "8":
            case "100":
                res = "3";
                break;
            default:
                res = "1";
        }
        return res;
    }

    public static String convertMarriage(String marriage) {
        String res = null;
        switch (marriage) {
            case "1":
                res = "2";
                break;
            case "2":
                res = "1";
                break;
            default:
                res = marriage;
                break;
        }
        return res;
    }

    public static String getRqstUrl(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        boolean isFirst = true;
        for (String key : params.keySet()) {
            if (key != null && params.get(key) != null) {
                if (isFirst) {
                    isFirst = false;
                    builder.append("?");
                } else {
                    builder.append("&");
                }
                builder.append(key)
                        .append("=")
                        .append(params.get(key));
            }
        }
        return builder.toString();
    }




    public static FileVO uploadBase64Image(String base64Image, UploadParams params) {
        // 图片上传
        FileServerClient fileServerClient = new FileServerClient();
        String rz = fileServerClient.uploadBase64Image(base64Image, params);
        FileResult obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null == obj || !StringUtils.equals(obj.getCode(), "SUCCESS")) {
            rz = fileServerClient.uploadBase64Image(base64Image, params);
        }
        obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null != obj && StringUtils.equals(obj.getCode(), "SUCCESS") && null != obj.getData()) {
            return obj.getData();
        }
        return null;
    }
    
}
