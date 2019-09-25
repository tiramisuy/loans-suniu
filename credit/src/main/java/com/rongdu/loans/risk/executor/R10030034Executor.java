package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.common.BaiqishiRiskDecision;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 白骑士建议拒绝
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030034Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030034);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		DecisionVO vo = getDataInvokeService().doBaishiqiDecision(context);

		HitRule hitRule = checkAntifraudDecision(vo);
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
	 * 白骑士建议拒绝
	 * 
	 * @param vo
	 * @return
	 */
	private HitRule checkAntifraudDecision(DecisionVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		int score = Integer.valueOf(vo.getFinalScore() == null ? "0" : vo
				.getFinalScore());
		if (StringUtils.equalsIgnoreCase(BaiqishiRiskDecision.REJECT,
				vo.getFinalDecision())) {
			setHitNum(1);
		}
		hitRule.setValue(vo.getFinalDecision());
		String msg = String.format("风险决策：%s，最终得分：%s", vo.getFinalDecision(),
				score);
		hitRule.setRemark(msg);
		return hitRule;
	}

}
