package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 申请人手机号段不合要求
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10030055Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030055);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkMobile(context);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), hitNum, evidence);
	}

	/**
	 * 手机号码为170号段 拒绝
	 * 
	 * @param context
	 * @return
	 */
	private HitRule checkMobile(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		String mobile = context.getUser().getMobile();
		if (AutoApproveUtils.isMobile(mobile) && mobile.startsWith("170")) {
			setHitNum(1);
			String remark = String.format("%s", mobile);
			hitRule.setRemark(remark);
		}
		return hitRule;
	}

}
