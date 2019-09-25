package com.rongdu.loans.credit.tencent.service;

import com.rongdu.loans.credit.tencent.vo.AccessTokenVo;

/**
 * 腾讯接口访问令牌-服务接口
 * @author sunda
 * @version 2017-07-20
 */
public interface AccessTokenService {
	
	/**
	 * 获取腾讯接口访问令牌
	 * @return
	 */
	public AccessTokenVo getAccessToken();
	
}
