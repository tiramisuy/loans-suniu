package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 平时较少主动与外界通话
 * 数据来源于：jdq 魔蝎报告
 * @author hbx
 * @version 2017-08-14
 */
public class R10030014Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030014);
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
		HitRule hitRule = checkRule(vo.getMoxieTelecomReport().active_degree);
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

	private HitRule checkRule(List<MoxieTelecomReport.ActiveDegreeBean> basicCheckItemsBeans){
		HitRule hitRule = createHitRule(getRiskRule());
		Integer num = null;
		for (int i = 0; i < basicCheckItemsBeans.size(); i++) {
			if ("dial_peer_num_cnt".equals(basicCheckItemsBeans.get(i).app_point)) {
				num = Integer.valueOf(basicCheckItemsBeans.get(i).item.item_6m);
				break;
			}
		}

		if (num != null && num < 50) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(num));
		}
		hitRule.setRemark(String.format("拨出电话号码个数：%d", num));
		return hitRule;
	}
}
