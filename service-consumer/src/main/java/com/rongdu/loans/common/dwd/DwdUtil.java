package com.rongdu.loans.common.dwd;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StandardDesUtil;
import com.rongdu.loans.loan.option.dwd.DWDInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Map;

/**
 * @version V1.0
 * @Title: DwdUtil.java
 * @Package com.rongdu.loans.common.dwd
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yuanxianchu
 * @date 2018年10月29日
 */
@Slf4j
public class DwdUtil {

    public static DWDConfig dwdConfig = SpringContextHolder.getBean("dwdConfig");

    //机构方公钥
    public static String partnerPublicKey = dwdConfig.getPartnerPublicKey();
    //机构方私钥
    public static String partnerPrivateKey = dwdConfig.getPartnerPrivateKey();

    //大王贷公钥
    public static String dwdPublicKey = dwdConfig.getDWDPublicKey();

    public static String gateWay = dwdConfig.getGateWay();

    public static String queryWay = dwdConfig.getQueryWay();

    public static String getBizData(DWDInfo dwdInfo) {
        String bizData = dwdInfo.getBizData();
        try {
            if ("1".equals(dwdInfo.getBizEnc())) {
                String desKey = dwdInfo.getDesKey();
                desKey = RSA.decrypt(desKey, partnerPrivateKey);
                bizData = StandardDesUtil.decrypt(dwdInfo.getBizData(), desKey);
                if (!"fund.userinfo.base".equals(dwdInfo.getMethod()) && !"fund.userinfo.addit".equals(dwdInfo.getMethod())){
                    log.debug("【大王贷-{}接口】请求参数biz_data={}", dwdInfo.getMethod(), bizData);
                }
            }
            return bizData;
        } catch (Exception e) {
            throw new RuntimeException("【大王贷】解密字符串[" + dwdInfo + "]时遇到异常", e);
        }
    }

    public static String getAppId(ChannelParse channelParse){
        return dwdConfig.getAppId(channelParse);
    }

    @Getter
    public enum ChannelParse{

        DAWANGDAI("DWDAPI","RZ0001","融泽财富","dwd_product_flag"),
        CYQB("CYQBAPI","RZ0022","Android橙意钱包","cyqb_product_flag"),
        CYQBIOS("CYQBIOSAPI","RZ0021","IOS橙意钱包","cyqbios_product_flag"),
        _51JDQ("51JDQAPI","RZ0027","51借点钱","51jdq_product_flag"),
        YBQB("YBQBAPI","RZ0023","亿宝钱包","ybqb_product_flag");

        private String channelCode;
        private String sourceId;
        private String app;
        private String productFlag;

        private static final Map<String, ChannelParse> channelCodeIndex = Maps.uniqueIndex(
                EnumSet.allOf(ChannelParse.class), new Function<ChannelParse, String>() {
                    @Override
                    public String apply(ChannelParse input) {
                        return input.getChannelCode();
                    }
                }
        );

        private static final Map<String, ChannelParse> sourceIdIndex = Maps.uniqueIndex(
                EnumSet.allOf(ChannelParse.class), new Function<ChannelParse, String>() {
                    @Override
                    public String apply(ChannelParse input) {
                        return input.getSourceId();
                    }
                }
        );

        ChannelParse(String channelCode, String sourceId, String app, String productFlag) {
            this.channelCode = channelCode;
            this.sourceId = sourceId;
            this.app = app;
            this.productFlag = productFlag;
        }

        public static ChannelParse lookupByChannelCode(String channelCode) {
            return channelCodeIndex.get(channelCode);
        }

        public static ChannelParse lookupBySourceId(String sourceId) {
            return sourceIdIndex.get(sourceId);
        }
    }

    public static String convertDegree(String userEducation) {
        String res = null;
        switch (userEducation) {
            case "E01":
                res = "1";
                break;
            case "E02":
                res = "1";
                break;
            case "E03":
                res = "2";
                break;
            case "E04":
                res = "3";
                break;
            case "E05":
                res = "4";
                break;
            default:
                res = "";
        }
        return res;
    }

    public static String convertRelationship(String contact1aRelationship) {
        String res = null;
        switch (contact1aRelationship) {
            case "9":
                //父母
            case "10":
                //子女
            case "11":
                //兄弟
            case "12":
                //姐妹
                res = "1";
                break;
            case "13":
                //配偶
                res = "2";
                break;
            default:
                res = "3";
        }
        return res;
    }

    public static String convertContact(String emergencyContactPersonaRelationship){
        String res = null;
        switch (emergencyContactPersonaRelationship){
            case "4":
                //亲人
                res = "1";
                break;
            case "6":
                //朋友
                res = "3";
                break;
            case "7":
                //同事
                res = "4";
                break;
            default:
                res = "3";
                break;
        }
        return res;
    }

    public static String convertShip(String contact1aRelationship) {
        String res = null;
        switch (contact1aRelationship) {
            case "4":
                res = "亲人";
                break;
            case "6":
                res = "朋友";
                break;
            default:
                res = "朋友";
        }
        return res;
    }
}
