package com.rongdu.loans.common.sll;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StandardDesUtil;
import com.rongdu.loans.common.dwd.RSA;
import com.rongdu.loans.loan.option.SLL.SLLReq;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/13
 * @since 1.0.0
 */
@Slf4j
public class SLLUtil {

    public static SLLConfig sllConfig = SpringContextHolder.getBean("sllConfig");

    //机构方公钥
    public static String partnerPublicKey = sllConfig.getPartnerPublicKey();
    //机构方私钥
    public static String partnerPrivateKey = sllConfig.getPartnerPrivateKey();

    //奇虎360公钥
    public static String sllPublicKey = sllConfig.getSLLPublicKey();

    public static String appId = sllConfig.getAppId();

    public static String jhhappId = sllConfig.getJHHAppId();


    //请求地址
    public static String gateWay = sllConfig.getGateWay();

    public static String getBizData(String sllReqStr, String methed) {
        SLLReq sllReq = JSONObject.parseObject(sllReqStr, SLLReq.class);
        String bizData = sllReq.getBizData();
        try {
            if ("1".equals(sllReq.getBizEnc())) {
                String desKey = sllReq.getDesKey();
                desKey = RSA.decrypt(desKey, partnerPrivateKey);
                bizData = StandardDesUtil.decrypt(sllReq.getBizData(), desKey);
                log.debug("【奇虎360-{}接口】请求参数biz_data={}", methed, bizData);
            } else {
                log.debug("【奇虎360-{}接口】请求参数biz_data={}", methed, bizData);
            }
            return bizData;
        } catch (Exception e) {
            throw new RuntimeException("【奇虎360】解密字符串[" + sllReqStr + "]时遇到异常", e);
        }
    }

    public static String Relation(String relationSpare) {
        String res = null;
        switch (relationSpare) {
            case "1":
                res = "父母";
                break;
            case "2":
                res = "配偶";
                break;
            case "3":
                res = "兄弟姐妹";
                break;
            case "4":
                res = "子女";
                break;
            case "5":
                res = "同事";
                break;
            default:
                res = "朋友";
        }
        return res;
    }
}
