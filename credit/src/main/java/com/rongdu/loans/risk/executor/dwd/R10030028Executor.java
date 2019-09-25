package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 在本平台存在15天以上逾期
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10030028Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030028);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		int maxOverdueDays = getDataInvokeService().getMaxOverdueDays(context);
		int num = 5;
		UserInfoVO userInfo = context.getUserInfo();
		// 复贷判断
		if (userInfo != null && userInfo.getLoanSuccCount() != null && userInfo.getLoanSuccCount() > 0){
			num = 4;
		}
		// 命中的规则
		HitRule hitRule = checkMaxOverdueDays(maxOverdueDays, num);
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
	 * 在本平台存在15天以上逾期
	 * 
	 * @param maxOverdueDays
	 * @return
	 */
	private HitRule checkMaxOverdueDays(int maxOverdueDays, int num) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (maxOverdueDays > num) {
			setHitNum(1);
			String remark = String.format("最高逾期天数：%s", maxOverdueDays);
			hitRule.setRemark(remark);
		}
		return hitRule;
	}

}
