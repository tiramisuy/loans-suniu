package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 手机号码关联过多身份证   数据来源于：jdq魔蝎报告
 * 
 * 
 * @version 2017-11-13
 */
public class R10030045Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030045);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		IntoOrder vo = null;
		try {
			vo = getDataInvokeService().getjdqBase(context);
		} catch (Exception e) {
			logger.error("JDQ基本信息查询异常", e);
		}
		if (vo == null) {
			return;
		}

		// 命中的规则
		HitRule hitRule = checkRule(vo.getMoxieTelecomReport().getUser_info_check().get(0).check_search_info);
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
	 * 手机号关联其它身份证个数≥1
	 * 
	 * @return
	 */
	private HitRule checkRule(MoxieTelecomReport.UserInfoCheckBean.CheckSearchInfoBean checkSearchInfoBean) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 1;
		if (checkSearchInfoBean != null && checkSearchInfoBean.phone_with_other_idcards != null) {
			int count = checkSearchInfoBean.phone_with_other_idcards.size();
			if (count >= threshold) {
				setHitNum(1);
			}
			hitRule.setRemark("手机号关联其它身份证个数 : " + count);
		}
		return hitRule;
	}
}