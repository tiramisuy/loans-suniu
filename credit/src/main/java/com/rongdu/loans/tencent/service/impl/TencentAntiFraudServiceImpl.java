/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.utils.CharsetUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.tencent.common.TencentConfig;
import com.rongdu.loans.tencent.service.TencentAntiFraudService;
import com.rongdu.loans.tencent.vo.AntiFraudOP;
import com.rongdu.loans.tencent.vo.AntiFraudVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 腾讯-反欺诈服务-风险信息-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("tencentAntiFraudService")
public class TencentAntiFraudServiceImpl extends PartnerApiService implements TencentAntiFraudService {

    /**
     * 腾讯-反欺诈服务
     * @param op
     * @return
     */
    public AntiFraudVO antiFraud(String userId,String applyId,AntiFraudOP op){

        //配置参数
        String partnerId = TencentConfig.partner_id;
        String partnerName = TencentConfig.partner_name;
        String bizCode = TencentConfig.antifraud_biz_code;
        String bizName = TencentConfig.antifraud_biz_name;
        String url = TencentConfig.antifraud_url;
        String fee = TencentConfig.antifraud_fee;

        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);
        log.setFee(fee);

        SortedMap<String, String> params = new TreeMap<String, String>();
        String secretId = TencentConfig.secret_id;
        String secretKey = TencentConfig.secret_key;
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = String.valueOf(System.nanoTime());
        String action = "AntiFraud";


        Map<String, String> args = beanToMap(op);

        params.putAll(args);
        params.put("Nonce", String.valueOf((int)(Math.random() * 0x7fffffff)));
        params.put("Action", action);
        params.put("Region", "gz");
        params.put("SecretId", secretId);
        params.put("Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        params.put("Signature", hmacSHA1(secretKey, String.format("%s%s?%s", "GET", url, makeQueryString(params, null)), CharsetUtils.DEFAULT_CHARSET));

        //拼接请求字符串
        url =   String.format("https://%s?%s", url, makeQueryString(params, CharsetUtils.DEFAULT_CHARSET));
        //发送请求
        AntiFraudVO vo = (AntiFraudVO) getForJson(url, null,AntiFraudVO.class,log);
        return vo;

    }


    private static String makeQueryString(Map<String, String> args, String charset) {
        String url = "";
        for (Map.Entry<String, String> entry : args.entrySet())
            try {
                url += entry.getKey() + "=" + (charset == null ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        return url.substring(0, url.length()-1);
    }

    /**
     * 将JavaBean转化为HashMap
     * @param op
     * @return
     */
    private Map<String, String> beanToMap(AntiFraudOP op) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = BeanUtils.describe(op);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        for(Iterator i = map.entrySet().iterator();i.hasNext();) {
            Map.Entry<String,String> enrty = (Map.Entry<String,String>)i.next();
            if (StringUtils.isBlank(enrty.getValue())||"class".equals(enrty.getKey())){
                i.remove();
            }
        }
        return map;
    }

    /* Signature algorithm using HMAC-SHA1 */
    public static String hmacSHA1(String key, String text, String charset) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key.getBytes(charset), "HmacSHA1"));
            return encode(mac.doFinal(text.getBytes(charset)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 编码
     * @param bstr
     * @return String
     */
    private static String encode(byte[] bstr){
        String sp = System.getProperty("line.separator");
        return (new BASE64Encoder().encode(bstr)).replaceAll(sp, "");
    }


}