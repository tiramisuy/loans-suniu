package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportAntiFraudCloud;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 手机号码关联过多身份证   数据来源于：白骑士资信报告
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030045Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030045);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		ReportAntiFraudCloud reportAntiFraudCloud = vo.getData()
				.getBqsAntiFraudCloud();

		// 命中的规则
		HitRule hitRule = checkIdcCount(reportAntiFraudCloud);
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
	 * 手机号关联身份证个数≥2
	 * 
	 * @return
	 */
	private HitRule checkIdcCount(ReportAntiFraudCloud reportAntiFraudCloud) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 2;
		if (reportAntiFraudCloud != null
				&& StringUtils.isNotBlank(reportAntiFraudCloud.getIdcCount())) {
			int count = AutoApproveUtils
					.extractNumFromString(reportAntiFraudCloud.getIdcCount());
			if (count >= threshold) {
				setHitNum(1);
			}
			hitRule.setRemark("手机号关联身份证个数 : " + count);
		}
		return hitRule;
	}
}