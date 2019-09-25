package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 历史累计已放款次数大于等于8次
 * 
 * @author fy
 * @version 2019-06-17
 */
public class R10030130Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030130);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkMaxOverdueDays(context);
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
	 * 历史累计已放款次数大于等于8次
	 *
	 * @return
	 */
	private HitRule checkMaxOverdueDays(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		UserInfoVO userInfo = context.getUserInfo();
		if (userInfo != null && userInfo.getLoanSuccCount() != null && userInfo.getLoanSuccCount() > 0) {
			if (userInfo.getLoanSuccCount() >= 8) {
				setHitNum(1);
				String remark = String.format("历史累计已放款次数大于等于8次,累计放款次数：%s", userInfo.getLoanSuccCount());
				hitRule.setRemark(remark);
			}
		}
		return hitRule;
	}

}
