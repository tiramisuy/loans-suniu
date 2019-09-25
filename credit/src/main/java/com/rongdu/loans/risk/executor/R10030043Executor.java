package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 互通电话号码较低 数据来源于：白骑士资信报告
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030043Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030043);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
//		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().get("exchangeCallMobileCount");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getExchangeCallMobileCount();

		// 命中的规则
		HitRule hitRule = checkCallMobileCount(crossValidation);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				getHitNum(), evidence);
	}

	/**
	 * 互通电话号码较低 互通电话号码个数≤20且>10
	 * 
	 * @return
	 */
	private HitRule checkCallMobileCount(ReportCrossValidationDetail crossValidation) {
		HitRule hitRule = createHitRule(getRiskRule());
		int minThreshold = 10;
		int maxThreshold = 20;
		if (crossValidation != null
				&& StringUtils.isNotBlank(crossValidation.getResult())) {
			int count = AutoApproveUtils.extractNumFromString(crossValidation.getResult());
			if (count>minThreshold && count <= maxThreshold) {
				setHitNum(1);
			}
			hitRule.setRemark(crossValidation.getEvidence());
		}
		return hitRule;
	}
}