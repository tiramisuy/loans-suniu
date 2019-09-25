package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10190007Executor.java  
* @Package com.rongdu.loans.risk.executor.rong360  
* @Description: 连续3天非活跃次数>=2  
* @author: yuanxianchu  
* @date 2018年9月25日  
* @version V1.0  
*/
public class R10190007Executor extends Executor {

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
		int stop3Days = tianjiReportDetail.getJson().getUserPortrait().getActiveDays().getStop3Days();
		if (stop3Days >= 3) {
			setHitNum(1);
		}
		hitRule.setRemark(String.format("连续3天非活跃次数>=2，当前：%d",stop3Days));
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10190007);
	}

}
