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
 * 近半年较少使用此手机号码2 数据来源于：白骑士资信云报告数据
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030036Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030036);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		// ReportCrossValidationDetail crossValidation =
		// vo.getData().getCrossValidation().get("notCallAndSmsDayCount");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getNotCallAndSmsDayCount();

		// 命中的规则
		HitRule hitRule = checkNotCallAndSmsDayCount(crossValidation);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), getHitNum(), evidence);
	}

	/**
	 * 运营商近半年全天未使用通话和短信功能的累计天数＞50，数据来源于：白骑士
	 * 
	 * @param crossValidation
	 * @return
	 */
	private HitRule checkNotCallAndSmsDayCount(ReportCrossValidationDetail crossValidation) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 50;
		if (crossValidation != null && StringUtils.isNotBlank(crossValidation.getResult())) {
			String resultString = crossValidation.getResult();
			int notCallAndSmsDayCount = AutoApproveUtils.extractNumFromString(resultString);
			if (notCallAndSmsDayCount > threshold) {
				setHitNum(1);
				hitRule.setValue(String.valueOf(notCallAndSmsDayCount));
			}
			String msg = crossValidation.getEvidence();
			hitRule.setRemark(msg);
		}
		return hitRule;
	}

}
