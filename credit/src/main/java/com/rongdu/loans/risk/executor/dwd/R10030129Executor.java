package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 历史存在逾期，且历史订单逾期累计天数大于等于10天
 * 
 * @author fy
 * @version 2019-06-17
 */
public class R10030129Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030129);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		int count = getDataInvokeService().getCountOverdueDays(context);
		// 命中的规则
		HitRule hitRule = checkMaxOverdueDays(count);
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
	 * 历史存在逾期，且历史订单逾期累计天数大于等于10天
	 * 
	 * @param maxOverdueDays
	 * @return
	 */
	private HitRule checkMaxOverdueDays(int maxOverdueDays) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (maxOverdueDays >= 10) {
			setHitNum(1);
			String remark = String.format("历史存在逾期，且历史订单逾期累计天数大于等于10天,累计逾期天数：%s", maxOverdueDays);
			hitRule.setRemark(remark);
		}
		return hitRule;
	}

}
