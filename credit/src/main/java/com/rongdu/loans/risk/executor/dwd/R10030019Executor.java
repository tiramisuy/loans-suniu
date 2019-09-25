package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * 申请人手机号归属所在地不合要求
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030019Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030019);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
		if (report == null) {
			return;
		}
		// 获取申请人手机号码归属地
		String applyLoc = null;
		List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
		if (applicationCheck != null && applicationCheck.size() > 0) {
			for (ApplicationCheck check : applicationCheck) {
				if ("cell_phone".equals(check.getAppPoint())) {
					applyLoc = check.getCheckPoints().getWebsite();
					break;
				}
			}
		}
		// 命中的规则
		HitRule hitRule = checkMobileArea(applyLoc);
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
	 * 申请人手机号归属所在地不合要求
	 * 
	 * @param area
	 * @return
	 */
	private HitRule checkMobileArea(String area) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 申请受限的地区
		String[] array = { "西藏", "新疆", "宁德" };
		List<String> restrictedArea = Arrays.asList(array);
		if (AutoApproveUtils.containsAny(area, restrictedArea)) {
			setHitNum(1);
		}
		hitRule.setValue(area);
		hitRule.setRemark(area);
		return hitRule;
	}

}
