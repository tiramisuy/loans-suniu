package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 身份证关联过多手机号码 数据来源于：jdq魔蝎报告
 * 
 * 
 * @version 2017-11-13
 */
public class R10030046Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030046);
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
		HitRule hitRule = checkPhoneCount(vo.getMoxieTelecomReport().getUser_info_check().get(0).check_search_info);
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
	 * 身份证关联其它手机号个数≥2
	 * 
	 * @return
	 */
	private HitRule checkPhoneCount(MoxieTelecomReport.UserInfoCheckBean.CheckSearchInfoBean checkSearchInfoBean) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 2;
		if (checkSearchInfoBean != null && checkSearchInfoBean.idcard_with_other_phones != null) {
			int count = checkSearchInfoBean.idcard_with_other_phones.size();
			if (count >= threshold) {
				setHitNum(1);
			}
			hitRule.setRemark("身份证关联其它手机号个数 : " + count);
		}
		return hitRule;
	}
}