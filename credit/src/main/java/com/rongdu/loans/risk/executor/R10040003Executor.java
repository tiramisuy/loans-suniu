package com.rongdu.loans.risk.executor;

import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 白骑士-反欺诈决策引擎服务 白骑士反欺诈分较高 数据来源于：白骑士
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10040003Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10040003);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		DecisionVO vo = getDataInvokeService().doBaishiqiDecision(context);

		HitRule hitRule = checkAntifraudScore(vo);
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
	 * 白骑士反欺诈决策引擎最终得分大于100分
	 * 
	 * @param vo
	 * @return
	 */
	private HitRule checkAntifraudScore(DecisionVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		int score = Integer.valueOf(vo.getFinalScore() == null ? "0" : vo
				.getFinalScore());
		int threshold = 100;
		if (score > threshold) {
			setHitNum(1);
		}
		hitRule.setValue(String.valueOf(score));
		String msg = String.format("风险决策：%s，最终得分：%s", vo.getFinalDecision(),
				score);
		hitRule.setRemark(msg);
		return hitRule;
	}

}
