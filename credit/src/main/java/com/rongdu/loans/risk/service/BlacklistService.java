/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 风控黑名单-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface BlacklistService {
	public int saveBlacklist(HitRule hitRule,AutoApproveContext context);
	public long findBlacklistCount(String userId);
	public List<Blacklist> getALLBlackCust();
	/**
	 * 根据手机号获取黑名单
	 */
	int getBlacklistByPhoneList(List<String> phoneList);
}