package com.rongdu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;

/**
 * 发送短信帮助类
 * @author likang
 */
public class SendMSGUtils {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(SendMSGUtils.class);
			
	// 短信验证码有效时间
	public static final int MSGCEDE_CACHESECONDS = 5*60;
		
	// 短信验证码缓存key异常返回值
	public static final String MSGCEDE_CACHE_EEOE = "9999";
	
	// 限制类型-某个时间段限制
	public static final int LIMIT_TYPE_SOMETIEM = 1;
	
	// 限制类型-全部时间段限制
	public static final int LIMIT_TYPE_ALL = 2;
	
	// 黑名单类型-手机号
	public static final int BLACKLIST_TYPE_MOB = 1;
	
	// 黑名单类型-ip
	public static final int BLACKLIST_TYPE_IP = 2;
	
	// 超限判断返回码-[YES] 超限
	public static final String LR_YES = "YES";
	
	// 超限判断返回码-[NO] 未超限
    public static final String LR_NO = "NO";
    
    // 超限判断返回码-[BL] 手机号超限，且添加黑名单
    public static final String LR_BL_MOB = "BL_MOB";
    
    // 超限判断返回码-[BL] 手机号超限，且添加黑名单
    public static final String LR_BL_IP = "BL_IP";
	
	/**
	 * 用户代理过滤
	 * @param userAgent
	 * @return
	 */
	public static boolean filterUserAgent(String userAgent) {
		if(StringUtils.contains(userAgent, "Apache-HttpClient/UNAVAILABLE")) {
			return true;
		}
//		else if(StringUtils.contains(
//				userAgent, "Windows")) {
//			return true;
//		}
		else if(StringUtils.contains(userAgent, "MSIE")) {
			return true;
		}
//		else if(StringUtils.contains(userAgent, "WOW")) {
//			return true;
//		}
		return false;
	}
	
	/**
	 * 发送短信验证码 手机号与ip 次数限制过滤
	 * @param account
	 * @param msgType
	 * @param ip
	 * @return
	 */
	public static String filterMsgSend(String account, int msgType, String ip) {
		// 初始化返回值
		String rz = LR_NO;
		// 拼接短信验证码缓存key
		String codeKey = getMsgCodeKey(account, msgType);
		// 短信验证码类型异常情况
		if(!StringUtils.equals(codeKey, MSGCEDE_CACHE_EEOE)) {
			String code = JedisUtils.get(codeKey);
			// 验证码缓存code未失效的情况
			if(null != code) {
				return LR_YES;
			}
			// ip超限判断
			rz =isIpCountPassLimit(msgType, ip);
			if(StringUtils.equals(rz, LR_NO)) {
				// 手机号超限判断
				rz = isMobCountPassLimit(account, msgType);	
			}
		}
		return rz;
	}
	
	/**
	 * 短信发送统计公共算法
	 * @param cacheKey
	 * @param cacheSeconds
	 */
	public static void commonSendStatistics(String cacheKey, int cacheSeconds) {
		// 获取缓存中统计数
		Integer count = (Integer) JedisUtils.getObject(cacheKey);
		// 更新缓存中统计数
		if(null == count) {
			count = 1;
		} else {
			count++;
		}
		JedisUtils.setObject(cacheKey, count, cacheSeconds);
	}
	
	/**
	 * 发送短信 手机号限制缓存key
	 * @param mob
	 * @param msgType
	 * @param limtType
	 * @return
	 */
	private static String getMobLimitCacheKey(String mob, int msgType, int limtType) {
		// 拼接缓存key
		StringBuilder countKey = new StringBuilder(); 
		countKey.append(mob).append("_").append(msgType);
		if(limtType == 1) {
			countKey.append(Global.MCODE_MOB_LIMT_S_SUFFIX);
			return  countKey.toString();
		} else{
			countKey.append(Global.MCODE_MOB_LIMT_A_SUFFIX);
			return  countKey.toString();
		}	
	}
	
    /**
     * 手机号发送记录统计缓存
     * @param mob
     * @param msgType
     */
	public static void mobSendStatistics(String mob, int msgType) {
		// 某时间段次数统计
		String countKey = getMobLimitCacheKey(mob, msgType, LIMIT_TYPE_SOMETIEM);
		commonSendStatistics(countKey, Global.TWO_HOURS_CACHESECONDS);
		// 总共次数统计
		String allKey = getMobLimitCacheKey(mob, msgType, LIMIT_TYPE_ALL);
		commonSendStatistics(allKey, Global.THREE_DAY_CACHESECONDS);
	}
	
	/**
	 * 手机号次数超限限制判断
	 * @param mob
	 * @param msgType
	 * @return
	 */
	public static String isMobCountPassLimit(String mob, int msgType) {
		// 总数统计
		String allKey = getMobLimitCacheKey(mob, msgType, LIMIT_TYPE_ALL);
		Integer allCount = (Integer) JedisUtils.getObject(allKey);
		if(null != allCount && allCount >= Global.MCODE_MOB_LIMT_A) {
			return LR_BL_MOB;
		}
		// 拼接缓存key
		String countKey = getMobLimitCacheKey(mob, msgType, LIMIT_TYPE_SOMETIEM);
		// 获取缓存中统计数
		Integer count = (Integer) JedisUtils.getObject(countKey);
		if(null != count && count >= Global.MCODE_MOB_LIMT_S) {
			return LR_YES;
		}
		return LR_NO;
	}
	
    /**
     * 发送短信 ip限制缓存key
     * @param msgType
     * @param ip
     * @return
     */
	private static String getIpLimitCacheKey(int msgType, String ip, int limtType){
		// 拼接缓存key
		StringBuilder countKey = new StringBuilder(); 
		countKey.append(msgType).append("_").append(ip);
		if(limtType == 1) {
			countKey.append(Global.MCODE_IP_LIMT_S_SUFFIX);
			return  countKey.toString();
		} else{
			countKey.append(Global.MCODE_IP_LIMT_A_SUFFIX);
			return  countKey.toString();
		}
	}
	
	/**
	 * ip发送记录统计缓存
	 * @param msgType
	 * @param ip
	 */
	public static void ipSendStatistics(int msgType, String ip) {
		// 某时间段次数统计
		String countKey = getIpLimitCacheKey(msgType, ip, LIMIT_TYPE_SOMETIEM);
		commonSendStatistics(countKey, Global.TWO_HOURS_CACHESECONDS);
		// 总共次数统计
		String allKey = getIpLimitCacheKey(msgType, ip, LIMIT_TYPE_ALL);
		commonSendStatistics(allKey, Global.THREE_DAY_CACHESECONDS);
	}
	/**
	 * ip次数超限限制判断
	 * @param msgType
	 * @param ip
	 * @return
	 */
	public static String isIpCountPassLimit(int msgType, String ip) {
		// 总数统计
		String allKey = getIpLimitCacheKey(msgType, ip, LIMIT_TYPE_ALL);
		Integer allCount = (Integer) JedisUtils.getObject(allKey);
		if(null != allCount && allCount >= Global.MCODE_IP_LIMT_A) {
			return LR_BL_IP;
		}
		// 某时间段次数统计
		String countKey = getIpLimitCacheKey(msgType, ip, LIMIT_TYPE_SOMETIEM);
		Integer typeCount = (Integer) JedisUtils.getObject(countKey);
		if(null != typeCount && typeCount >= Global.MCODE_IP_LIMT_S) {
			return LR_YES;
		}
		return LR_NO;
	}
		
	/**
	 * 获取不同类型验证码缓存key
	 * @param account
	 * @param msgType
	 * @return
	 */
	public static String getMsgCodeKey(String account, int msgType) {
		// 拼接短信验证码缓存key
		StringBuilder codeKey = new StringBuilder();
		codeKey.append(account);
		switch(msgType) {
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
			case 5: // 短信密码登录验证码
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
	 * @param account
	 * @param SrvAuthCode
	 */
	public static void cacheBDSrvAuthCode(String account, String SrvAuthCode) {	
		if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(SrvAuthCode)) {
			// 恒丰银行发送短信类型
			int msgType = Integer.parseInt(
					ShortMsgTemplate.MSG_TYPE_JXBANK);
			// 拼接短信验证码缓存key
			String codeKey = getMsgCodeKey(account, msgType);
			JedisUtils.set(codeKey, SrvAuthCode, Global.TWO_MINUTES_CACHESECONDS);
		}
	}
	
	/**
	 * 缓存存管开户发送短信后的短信序列号
	 * @param account
	 * @param SrvAuthCode
	 */
	public static void cacheBDSmsSeq(String account, String smsSeq) {	
		if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(smsSeq)) {
			logger.debug("account:[{}]; SmsSeq:[{}]", account, smsSeq);
			// 拼接短信验证码缓存key
			String codeKey = account+Global.BANKDEPOSIT_SMSSEQ_SUFFIX;
			JedisUtils.set(codeKey, smsSeq, Global.TWO_MINUTES_CACHESECONDS);
		}
	}
	
	/**
	 * 缓存中获取存管四合一授权发送短信后的短信序列号
	 * @param account
	 * @param SrvAuthCode
	 */
	public static String getBDSmsSeqFormCache(String account) {
		logger.debug("getBDSmsSeqFormCache account:[{}]", account);
		if(StringUtils.isNotBlank(account)) {
			return JedisUtils.get(
					account+Global.BANKDEPOSIT_SMSSEQ_SUFFIX);
		}
		return null;
	}
	
	/**
	 * 获取缓存的恒丰银行业务授权码
	 * @param account
	 * @return
	 */
	public static String getJXBSrvAuthCodeCache(String account) {	
		if(StringUtils.isNotBlank(account)) {
			// 恒丰银行发送短信类型
			int msgType = Integer.parseInt(
					ShortMsgTemplate.MSG_TYPE_JXBANK);
			// 拼接短信验证码缓存key
			String codeKey = getMsgCodeKey(account, msgType);
			return JedisUtils.get(codeKey);
		}
		return null;
	}
	
	/**
	 * 清除缓存的恒丰银行业务授权码
	 * @param account
	 */
	public static void cleanJXBSrvAuthCodeCache(String account) {	
		if(StringUtils.isNotBlank(account)) {
			// 恒丰银行发送短信类型
			int msgType = Integer.parseInt(
					ShortMsgTemplate.MSG_TYPE_JXBANK);
			// 拼接短信验证码缓存key
			String codeKey = getMsgCodeKey(account, msgType);
			JedisUtils.del(codeKey);
		}
	}
}
