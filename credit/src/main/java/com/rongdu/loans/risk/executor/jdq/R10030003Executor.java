package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 手机号实名与申请人身份证名字不一致
 * @author hbx
 * @version 2017-08-14
 */
public class R10030003Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030003);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
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
		HitRule hitRule = checkRule(vo.getMoxieTelecomReport().basic_check_items);
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

	private HitRule checkRule(List<MoxieTelecomReport.BasicCheckItemsBean> basicCheckItemsBeans){
		HitRule hitRule = createHitRule(getRiskRule());
		for (int i = 0; i < basicCheckItemsBeans.size(); i++) {
			if ("name_match".equals(basicCheckItemsBeans.get(i).check_item)) {
				if ("匹配失败".equals(basicCheckItemsBeans.get(i).result)) {
					setHitNum(1);
					break;
				}
			}
		}
		hitRule.setRemark("运营商报告身份证验证不一致");
		return hitRule;
	}

}
