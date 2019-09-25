/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.manager.BlacklistManager;
import com.rongdu.loans.risk.service.BlacklistService;

import java.util.List;

/**
 * 风控黑名单-业务逻辑实现类
 * 
 * @author sunda
 * @version 2017-08-14
 */
@Service("blacklistService")
public class BlacklistServiceImpl extends BaseService implements
		BlacklistService {

	/**
	 * 风控黑名单-实体管理接口
	 */
	@Autowired
	private BlacklistManager blacklistManager;

	public int saveBlacklist(HitRule hitRule, AutoApproveContext context) {
		Blacklist black = new Blacklist();
		black.setId(IdGen.uuid());
		black.setUserId(context.getApplyInfo().getUserId());
		black.setIdNo(context.getUser().getIdNo());
		black.setMobile(context.getUser().getMobile());
		black.setTelphone(context.getUserInfo().getComTelNo());
		black.setQq(context.getUserInfo().getQq());
		black.setName(context.getUser().getRealName());
		black.setRiskCode(hitRule.getRuleCode());
		black.setRiskName(hitRule.getRuleName());
		black.setSourceType("3");//来源类型：0-网贷行业公布的黑名单;1-在平台有欠息或有不良贷款; 2-提供虚假材料或拒绝接受检查;3-命中合作机构的黑名单；4-网络黑名单
		black.setSourceOrg(hitRule.getSource());
		black.setTime(new java.sql.Date(new java.util.Date().getTime()));
		black.setReason(hitRule.getRemark());
		black.setStatus(1);//黑名单状态：0-预登记;1-生效;2-否决;3-注销
		black.setDel(0);//0-正常，1-已经删除
		return blacklistManager.insert(black);
	}

	@Override
	public long findBlacklistCount(String userId) {
		return blacklistManager.findBlacklistCount(userId);
	}

	@Override
	public List<Blacklist> getALLBlackCust() {
		String cache = "ALL_BLACK_CUST_LIST";
		List<Blacklist> list = (List<Blacklist>) JedisUtils.getObjectList(cache);
		if (list == null) {
			list = blacklistManager.getALLBlackCust();
			JedisUtils.setObjectList(cache, list, 30 * 60);
		}
		return list;
	}

	@Override
	public int getBlacklistByPhoneList(List<String> phoneList) {
		List<Blacklist> list = blacklistManager.getBlacklistByPhoneList(phoneList);
		return 0;
	}
}