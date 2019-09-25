package com.rongdu.common.utils;

import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;

/**
 *
 */
public class XianJinCardUtils {

    // 日志对象
    protected static Logger logger = LoggerFactory.getLogger(XianJinCardUtils.class);

    /**
     * 密码SHA1消息摘要散列处理
     *
     * @param pwd
     * @return
     */
    public static String pwdToSHA1(String pwd) {
        if (StringUtils.isNotBlank(pwd)) {
            return Digests.sha1(pwd);
        } else {
            return pwd;
        }
    }

    public static String getRelation(String relation) {
        String relationName = "";
        switch (relation) {
            case "1":
                relationName = "父亲";
                break;
            case "2":
                relationName = "配偶";
                break;
            case "3":
                relationName = "母亲";
                break;
            case "4":
                relationName = "子女";
                break;
            case "5":
                relationName = "同学";
                break;
            case "6":
                relationName = "朋友";
                break;
            case "7":
                relationName = "同事";
                break;
            case "8":
                relationName = "亲戚";
                break;
            case "9":
                relationName = "儿子";
                break;
            case "10":
                relationName = "女儿";
                break;
            case "11":
                relationName = "兄弟";
                break;
            case "12":
                relationName = "父亲";
                break;
            case "100":
                relationName = "姐妹";
                break;
            default:
                relationName = "未知";
                break;
        }
        return relationName;
    }

    public static void setRepayPlanFeedbackToRedis(String applyId) {
        Map<String, String> map = Maps.newHashMap();
        map.put(applyId, String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut(Global.REPAY_PLAN_FEEDBACK, map);
    }

    public static void rongPayFeedback(String applyId) {
        Map<String, String> map = Maps.newHashMap();
        map.put(applyId, String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut(Global.RONG_PAY_FEEDBACK, map);
    }

    public static void rongApproveFeedback(String applyId) {
        Map<String, String> map = Maps.newHashMap();
        map.put(applyId, String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut(Global.RONG_APPROVE_FEEDBACK, map);
    }

    public static void setRepayStatusFeedbackToRedis(String applyId, String status) {
        CallbackOP callbackOP = new CallbackOP();
        callbackOP.setTime(String.valueOf(System.currentTimeMillis()));
        callbackOP.setStatus(status);
        String json = JsonMapper.toJsonString(callbackOP);
        Map<String, String> map = Maps.newHashMap();
        map.put(applyId, json);
        JedisUtils.mapPut(Global.REPAY_STATUS_FEEDBACK, map);
    }

    public static void setJDQRepayPlanFeedbackToRedis(String applyId) {
        Map<String, String> map = Maps.newHashMap();
        map.put(applyId, String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut(Global.JDQ_ORDERSTATUS_FEEDBACK, map);
    }

    public static String getH5Sign(Map<String, String> treeMap) {
        String sign = "";
        try {
            String str = "";
            if (null != treeMap) {
                for (Map.Entry<String, String> string : treeMap.entrySet()) {
                    if (StringUtils.isBlank(str)) {
                        str = string.getKey() + string.getValue();
                    } else {
                        str += string.getKey() + string.getValue();
                    }
                }
            }
            String ua = "SH-XJ360";
            String signkey = "5ee2fbaa8e5265e138708d7940f814c1";
            String key = ua + signkey + ua;
            sign = MD5Util.md5(key + str + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static String setData(int length) {
        String data = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String cOrN = random.nextInt(2) % 2 == 0 ? "c" : "n";
            if ("c".equalsIgnoreCase(cOrN)) {
                data += (char) (random.nextInt(26) + 97);
            } else if ("n".equalsIgnoreCase(cOrN)) {
                data += String.valueOf(random.nextInt(9));
            }
        }
        data += (char) (random.nextInt(26) + 97);
        data += String.valueOf(random.nextInt(9));
        return data;
    }

    public static void main(String[] args) {
    	Random random = new Random();
    	System.out.println(random.nextInt(26) + 97);
    	System.out.println((char)117);
	}

}
