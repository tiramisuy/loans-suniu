package com.rongdu.loans.risk.executor.jubao;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10190001Executor.java  
* @Package com.rongdu.loans.risk.executor.rong360  
* @Description: 互通电话的号码数量>120, 或者互通电话的号码数量<12 
* @author: yuanxianchu  
* @date 2018年7月19日  
* @version V1.0  
*/
public class R10190001Executor extends Executor {

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
		TianjiReportDetailResp tianjiReportDetail = getDataInvokeService().getRongTJReportDetail(context);
		int callCnt = tianjiReportDetail.getJson().getUserPortrait().getBothCallCnt();
		if (callCnt > 120 || callCnt < 12) {
			setHitNum(1);
		}
		hitRule.setRemark("互通电话的号码数量>120, 或者互通电话的号码数量<12");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10190001);
	}

}
