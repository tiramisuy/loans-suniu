package com.rongdu.loans.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SendMSGUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.vo.FileVO;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.sys.web.FileResult;

/**
 * API公共服务类
 *
 * @author likang
 */
public class CommonUtils {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    // 外部接口调用次数缓存时间（1天）
    private static final int EXTERNAL_INTERFACE_CACHESECONDS = 24 * 60 * 60;

    // 短信验证码缓存key异常返回值
    private static final String MSGCEDE_CACHE_EEOE = "9999";

    // 缓存时间15分钟
    private static final int CACHESECONDS_FIFTEEN_MINUTES = 15 * 60;

    // 默认产品类型 现金贷【0】
    public static final String DEF_PRODUCT_TYPE = "0";

    // 默认受理机构
    public static final String DEF_ORG_ID = "JQB";

    // 默认受理机构名称
    public static final String DEF_ORG_NANE = "聚宝钱包";

    /**
     * 发送短信
     *
     * @param account
     * @param msgType
     * @param ip
     * @param source
     * @param userId
     * @param shortMsgService
     * @return
     */
    public static String sendMessage(String account, int msgType, String ip, String source, String userId,
                                     String channelId, ShortMsgService shortMsgService) {

        // 拼接短信验证码缓存key
        String codeKey = getMsgCodeKey(account, msgType);
        // 短信验证码类型异常情况
        if (StringUtils.equals(codeKey, SendMSGUtils.MSGCEDE_CACHE_EEOE)) {
            return Global.FALSE;
        }

        // 调短息验证码服务获取短信验证码
        SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
        sendShortMsgOP.setMobile(account);
        sendShortMsgOP.setMsgType(msgType);
        sendShortMsgOP.setIp(ip);
        sendShortMsgOP.setSource(source);
        sendShortMsgOP.setUserId(userId);
        sendShortMsgOP.setChannelId(channelId);
        String msgVerCode = shortMsgService.sendMsgCode(sendShortMsgOP);
        // String msgVerCode = "665862";

        // 缓存验证码
        JedisUtils.set(codeKey, msgVerCode, SendMSGUtils.MSGCEDE_CACHESECONDS);
        logger.debug("cache [{}]:[{}]", codeKey, JedisUtils.get(codeKey));

        // 手机号限制统计
        SendMSGUtils.mobSendStatistics(account, msgType);

        // ip限制统计
        // SendMSGUtils.ipSendStatistics(msgType, ip);

        return msgVerCode;
    }

    /**
     * 用户代理过滤
     *
     * @param userAgent
     * @return
     */
    public static boolean filterUserAgent(String userAgent) {
        if (StringUtils.contains(userAgent, "Windows")) {
            return true;
        } else if (StringUtils.contains(userAgent, "Apache-HttpClient/UNAVAILABLE")) {
            return true;
        } else if (StringUtils.contains(userAgent, "MSIE")) {
            return true;
        }
        // else if(StringUtils.contains(userAgent, "WOW")) {
        // return true;
        // }
        return false;
    }

    /**
     * 获取不同类型验证码缓存key
     *
     * @param account
     * @param msgType
     * @return
     */
    private static String getMsgCodeKey(String account, int msgType) {
        // 拼接短信验证码缓存key
        StringBuilder codeKey = new StringBuilder();
        codeKey.append(account);
        switch (msgType) {
            case 1: // 注册短信验证码
                codeKey.append(Global.REG_MCODE_SUFFIX);
                break;
            case 2: // 忘记密码短信验证码
                codeKey.append(Global.FORGET_MCODE_SUFFIX);
                break;
            case 3: // 存管开户短信验证码
                codeKey.append(Global.BANKDEPOSIT_SRVAUTHCODE_SUFFIX);
                break;
            case 4: // 存管四合一授权短信验证码
                codeKey.append(Global.BANKDEPOSIT_SMSSEQ_SUFFIX);
                break;
            case 5: // 短信登录验证码
                codeKey.append(Global.LOGIN_MCODE_SUFFIX);
                break;
            default:
                logger.warn("请输入有效的短信验证类型");
                return MSGCEDE_CACHE_EEOE;
        }
        return codeKey.toString();
    }

    /**
     * 缓存恒丰银行发送短信后的业务授权码
     *
     * @param account
     * @param SrvAuthCode
     */
    public static void cacheBDSrvAuthCode(String account, String SrvAuthCode) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(SrvAuthCode)) {
            // 恒丰银行发送短信类型
            int msgType = Integer.parseInt(ShortMsgTemplate.MSG_TYPE_JXBANK);
            // 拼接短信验证码缓存key
            String codeKey = getMsgCodeKey(account, msgType);
            JedisUtils.set(codeKey, SrvAuthCode, Global.TWO_MINUTES_CACHESECONDS);
        }
    }

    /**
     * 缓存存管开户发送短信后的短信序列号
     *
     * @param account
     * @param SrvAuthCode
     */
    public static void cacheBDSmsSeq(String account, String smsSeq) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(smsSeq)) {
            logger.debug("account:[{}]; SmsSeq:[{}]", account, smsSeq);
            // 拼接短信验证码缓存key
            String codeKey = account + Global.BANKDEPOSIT_SMSSEQ_SUFFIX;
            JedisUtils.set(codeKey, smsSeq, Global.TWO_MINUTES_CACHESECONDS);
        }
    }

    /**
     * 缓存中获取存管四合一授权发送短信后的短信序列号
     *
     * @param account
     * @param SrvAuthCode
     */
    public static String getBDSmsSeqFormCache(String account) {
        logger.debug("getBDSmsSeqFormCache account:[{}]", account);
        if (StringUtils.isNotBlank(account)) {
            return JedisUtils.get(account + Global.BANKDEPOSIT_SMSSEQ_SUFFIX);
        }
        return null;
    }

    /**
     * 获取缓存的恒丰银行业务授权码
     *
     * @param account
     * @return
     */
    public static String getJXBSrvAuthCodeCache(String account) {
        if (StringUtils.isNotBlank(account)) {
            // 恒丰银行发送短信类型
            int msgType = Integer.parseInt(ShortMsgTemplate.MSG_TYPE_JXBANK);
            // 拼接短信验证码缓存key
            String codeKey = getMsgCodeKey(account, msgType);
            return JedisUtils.get(codeKey);
        }
        return null;
    }

    /**
     * 清除缓存的恒丰银行业务授权码
     *
     * @param account
     */
    public static void cleanJXBSrvAuthCodeCache(String account) {
        if (StringUtils.isNotBlank(account)) {
            // 恒丰银行发送短信类型
            int msgType = Integer.parseInt(ShortMsgTemplate.MSG_TYPE_JXBANK);
            // 拼接短信验证码缓存key
            String codeKey = getMsgCodeKey(account, msgType);
            JedisUtils.del(codeKey);
        }
    }

    /**
     * 获取缓存中的申请编号
     *
     * @param userId
     * @return
     */
    public static String getApplyNofromCache(String userId) {
        return JedisUtils.get(userId + Global.APPLY_NO_SUFFIX);
    }

    /**
     * 清理缓存中的申请编号
     *
     * @param userId
     * @return
     */
    public static void cleanApplyNofromCache(String userId) {
        JedisUtils.del(userId + Global.APPLY_NO_SUFFIX);
    }

    /**
     * 获取ocr或者人类识别调用次数
     *
     * @param userId
     * @param type   外部接口类型
     * @return
     */
    public static Integer getCountCache(String userId, Integer type) {
        String key = getExtInterfaceTypeKey(userId, type);
        return (Integer) JedisUtils.getObject(key);
    }

    /**
     * 第三方接口调用次数
     *
     * @param userId
     * @param type   外部接口类型
     * @param count  值
     * @return
     */
    public static String setCountCache(String userId, Integer type, Integer count) {
        String key = getExtInterfaceTypeKey(userId, type);
        return JedisUtils.setObject(key, count, EXTERNAL_INTERFACE_CACHESECONDS);
    }

    /**
     * 获取不同类型验外部接口调用次数缓存key
     *
     * @param account
     * @param msgType
     * @return
     */
    private static String getExtInterfaceTypeKey(String userId, int type) {
        // 拼接短信验证码缓存key
        String countKey = userId;
        switch (type) {
            case 1: // ocr
                countKey = countKey + Global.COUNT_OCR_SUFFIX;
                break;
            case 2: // 人类识别
                countKey = countKey + Global.COUNT_FACE_SUFFIX;
                break;
            case 3: // 运营商
                countKey = countKey + Global.COUNT_OPERATOR_SUFFIX;
                break;
            case 4: // 芝麻信用
                countKey = countKey + Global.COUNT_SESAME_SUFFIX;
                break;
            case 5: // 信用卡
                countKey = countKey + Global.COUNT_CREDIT_CARD_SUFFIX;
                break;
            default:
                logger.warn("请输入有效的内部接口证类型");
                return MSGCEDE_CACHE_EEOE;
        }
        return countKey;
    }

    /**
     * 调用文件服务上载图片
     *
     * @param base64Image
     * @param params
     * @return
     */
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

    /**
     * 调用文件服务上载视频
     *
     * @param base64Video
     * @param params
     * @return
     */
    public static FileVO uploadBase64Video(String base64Video, UploadParams params) {
        // 图片上传
        FileServerClient fileServerClient = new FileServerClient();
        String rz = fileServerClient.uploadBase64Video(base64Video, params);
        FileResult obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null == obj || !StringUtils.equals(obj.getCode(), "SUCCESS")) {
            rz = fileServerClient.uploadBase64Video(base64Video, params);
        }
        obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null != obj && StringUtils.equals(obj.getCode(), "SUCCESS") && null != obj.getData()) {
            return obj.getData();
        }
        return null;
    }

    /**
     * 资产端来源码 转换成 资金端渠道码
     *
     * @param loanSource 资产端来源码 [1]–ios; [2]–android; [3]–h5; [4]–api;
     * @return channel 资金端渠道码 [1]–PC; [2]–ios; [3]–android; [4]–H5
     */
    public static String FinancialChannelAdapter(String loanSource) {
        String channel = loanSource;
        if (StringUtils.isNotBlank(loanSource)) {
            int source = Integer.parseInt(loanSource);
            switch (source) {
                case 1: // ios
                    channel = "2";
                    break;
                case 2: // android
                    channel = "3";
                    break;
                case 3: // H5
                    channel = "4";
                    break;
                default:
                    channel = "1";
                    ;
            }
        }
        return channel;
    }

    /**
     * 缓存预支付信息
     *
     * @param preAuthPayVO
     */
    public static void cachePreAuthPayResult(RePayOP rePayOP) {
        if (null != rePayOP) {
            String key = rePayOP.getPayComOrderNo() + Global.PREPAY_RESULT_SUFFIX;
            JedisUtils.setObject(key, rePayOP, CACHESECONDS_FIFTEEN_MINUTES);
        }
    }

    /**
     * 获取预支付信息
     *
     * @param transId
     */
    public static RePayOP getPreAuthPayResult(String payComOrderNo) {
        if (StringUtils.isNotBlank(payComOrderNo)) {
            String key = payComOrderNo + Global.PREPAY_RESULT_SUFFIX;
            return (RePayOP) JedisUtils.getObject(key);
        }
        return null;
    }

    /**
     * 清除预支付信息缓存
     *
     * @param transId
     */
    public static void cleanPreAuthPayResultCache(String payComOrderNo) {
        if (StringUtils.isNotBlank(payComOrderNo)) {
            String key = payComOrderNo + Global.PREPAY_RESULT_SUFFIX;
            JedisUtils.del(key);
        }
    }

    /**
     * 缓存白骑士设备唯一标识
     *
     * @param userId
     * @param val
     */
    public static void cacheBqs(String userId, String val) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(val)) {
            JedisUtils.setObject(userId + Global.BQS_KEY_SUFFIX, val, CACHESECONDS_FIFTEEN_MINUTES);
        }
    }

    /**
     * 缓存同盾设备唯一标识
     *
     * @param userId
     * @param val
     */
    public static void cacheTd(String userId, String val) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(val)) {
            JedisUtils.setObject(userId + Global.TD_KEY_SUFFIX, val, CACHESECONDS_FIFTEEN_MINUTES);
        }
    }
}
