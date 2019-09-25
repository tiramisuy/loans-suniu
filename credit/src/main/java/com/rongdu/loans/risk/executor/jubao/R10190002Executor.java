package com.rongdu.loans.risk.executor.jubao;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10190002Executor.java  
* @Package com.rongdu.loans.risk.executor.rong360  
* @Description: 天机系统被查询次数<2
* @author: yuanxianchu  
* @date 2018年7月19日  
* @version V1.0  
*/
public class R10190002Executor extends Executor {

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
		int searchedCnt = tianjiReportDetail.getJson().getRiskAnalysis().getSearchedCnt();
		if (searchedCnt < 2 || searchedCnt > 300) {
			setHitNum(1);
		}
		hitRule.setRemark("天机系统被查询次数<2");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10190002);
	}

}
