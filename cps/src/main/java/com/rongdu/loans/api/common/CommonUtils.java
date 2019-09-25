package com.rongdu.loans.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SendMSGUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;

/**
 * cps公共服务类
 * @author likang
 */
public class CommonUtils {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
		
    /**
     * 发送短信
     * @param account 
     * @param msgType
     * @param ip
     * @param source
     * @param userId
     * @param shortMsgService
     * @return
     */
	public static String sendMessage(String account, int msgType,
			String ip, String source, String userId, 
			ShortMsgService shortMsgService) {
		
		// 拼接短信验证码缓存key
		String codeKey = SendMSGUtils.getMsgCodeKey(account, msgType);
		// 短信验证码类型异常情况
		if(StringUtils.equals(codeKey, SendMSGUtils.MSGCEDE_CACHE_EEOE)) {
			return Global.FALSE;
		}
		
		// 调短息验证码服务获取短信验证码
		SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
		sendShortMsgOP.setMobile(account);
		sendShortMsgOP.setMsgType(msgType);
		sendShortMsgOP.setIp(ip);
		sendShortMsgOP.setSource(source);
		sendShortMsgOP.setUserId(userId);
		String msgVerCode = shortMsgService.sendMsgCode(sendShortMsgOP);
		
		// 缓存验证码
		JedisUtils.set(codeKey, msgVerCode, SendMSGUtils.MSGCEDE_CACHESECONDS);
		logger.debug("cache [{}]:[{}]", codeKey, JedisUtils.get(codeKey));
		
		// 手机号限制统计
		SendMSGUtils.mobSendStatistics(account, msgType);
		
		// ip限制统计
		SendMSGUtils.ipSendStatistics(msgType, ip);
		
		return msgVerCode;
	}

	public static String getApplyNofromCache(String userId) {
		return JedisUtils.get(userId + Global.APPLY_NO_SUFFIX);
	}
}
