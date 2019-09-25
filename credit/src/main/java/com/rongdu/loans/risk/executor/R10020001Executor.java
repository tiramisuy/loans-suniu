package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.BlacklistService;

/**
 * 聚宝钱包自有黑名单，数据来源于：自有黑名单
 * @author sunda
 * @version 2017-08-14
 */
public class R10020001Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10020001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		HitRule hitRule = checkBlacklist(context);
		String evidence = hitRule.getRemark();
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				hitNum, evidence);
	}
	private HitRule checkBlacklist(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		BlacklistService blacklistService = SpringContextHolder.getBean("blacklistService");
		long count=blacklistService.findBlacklistCount(context.getApplyInfo().getUserId());
		if(count>0){
			hitRule.setRemark("命中聚钱包自有黑名单");
			setHitNum(1);
			return hitRule;
		}
		return hitRule;
	}
}
