package com.rongdu.loans.risk.executor.xjbk;

import com.rongdu.loans.loan.option.xjbk.BehaviorCheck;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10030068Executor.java  
* @Package com.rongdu.loans.risk.executor.xjbk  
* @Description: 夜间活动情况>20%  
* @author: yuanxianchu  
* @date 2018年10月11日  
* @version V1.0  
*/
public class R10030068Executor extends Executor {

	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkRule(context);
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

	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 加载风险分析数据
		XianJinBaiKaCommonOP op = getDataInvokeService().getXianJinBaiKaBase(context);
		String evidence = "";
		for (BehaviorCheck behaviorCheck : op.getUser_verify().getOperatorReportVerify().getBehaviorCheck()) {
			if ("contact_night".equals(behaviorCheck.getCheckPoint()) && behaviorCheck.getScore() == 2) {
				evidence = behaviorCheck.getEvidence();
				setHitNum(1);
				break;
			}
		}
		hitRule.setRemark(evidence);
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030068);
	}

}
