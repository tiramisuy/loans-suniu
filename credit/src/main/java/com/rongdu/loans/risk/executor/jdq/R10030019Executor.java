package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
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
		IntoOrder vo = null;
		try {
			vo = getDataInvokeService().getjdqBase(context);
		} catch (Exception e) {
			logger.error("JDQ基本信息查询异常", e);
		}
		if (vo == null) {
			return;
		}
		String mobileArea = null;
		for (int i = 0; i < vo.getMoxieTelecomReport().cell_phone.size(); i++) {
			if("phone_attribution".equals(vo.getMoxieTelecomReport().cell_phone.get(i).key)){
				mobileArea = vo.getMoxieTelecomReport().cell_phone.get(i).value;
			}
		}

		// 命中的规则
		HitRule hitRule = checkMobileArea(mobileArea);
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
