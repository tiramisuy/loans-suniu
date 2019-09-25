/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit100.service.impl;

import com.bfd.facade.MerchantServer;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.common.RedisPrefix;
import com.rongdu.loans.credit100.common.Credit100Config;
import com.rongdu.loans.credit100.common.LoginRespCode;
import com.rongdu.loans.credit100.message.Credit100LoginResponse;

/**
 * 百融-业务逻辑基础类
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class Credit100BaseService extends PartnerApiService {

	// 商户客户端
	protected static MerchantServer ms = new MerchantServer();

	/**
	 * 登录百融服务器
	 */
	private String login() {
		logger.debug("百融金服-登录认证：进行中...");
		String result = null;
		try {
			result = ms.login(Credit100Config.api_username, Credit100Config.api_password, "LoginApi",
					Credit100Config.api_code);
		} catch (Exception e) {
			logger.debug("百融金服-登录认证：认证失败，{}", e.getMessage());
			e.printStackTrace();
		}
		String tokenid = "";
		Credit100LoginResponse loginResponse = (Credit100LoginResponse) JsonMapper.fromJsonString(result,
				Credit100LoginResponse.class);
		String succCode = LoginRespCode.SUCCESS.getCode();
		if (loginResponse != null && succCode.equals(loginResponse.getCode())) {
			tokenid = loginResponse.getTokenid();
			logger.debug("百融金服-登录认证：认证成功，tokenid为：{}", tokenid);
		} else {
			logger.debug("百融金服-登录认证：认证失败，{}", result);
		}
		return tokenid;
	}

	/**
	 * 获取令牌；如果令牌为空，就进行登录
	 * 
	 * @return
	 */
	public String getTokenid() {
		String cacheId = RedisPrefix.CREDIT100_TOKEN_ID;
		String tokenid = JedisUtils.get(cacheId);
		if (StringUtils.isBlank(tokenid)) {
			tokenid = login();
			if (StringUtils.isNotBlank(tokenid)) {
				// 缓存半个小时
				JedisUtils.set(cacheId, tokenid, 30 * 60);
			}
		}
		return tokenid;
	}

	/**
	 * 从缓存中清除令牌
	 */
	public void clearTokenidCache() {
		String cacheId = RedisPrefix.CREDIT100_TOKEN_ID;
		JedisUtils.del(cacheId);
	}

	/**
	 * 从缓存获取重试登录的次数
	 * 
	 * @param applyId
	 * @return
	 */
	public int getRetryTimes(String applyId) {
		String cacheId = RedisPrefix.CREDIT100_RETRY_TIMES + applyId;
		String str = (String) JedisUtils.get(cacheId);
		int retryTimes = 0;
		if (StringUtils.isNotBlank(str)) {
			retryTimes = retryTimes + Integer.parseInt(str);
		}
		return retryTimes;
	}

	/**
	 * 通过缓存来记录重试登录的次数
	 * 
	 * @param applyId
	 * @return
	 */
	public int addRetryTimes(String applyId) {
		String cacheId = RedisPrefix.CREDIT100_RETRY_TIMES + applyId;
		int retryTimes = getRetryTimes(applyId);
		retryTimes = retryTimes + 1;
		JedisUtils.set(cacheId, String.valueOf(retryTimes), 300);
		return retryTimes;
	}

}