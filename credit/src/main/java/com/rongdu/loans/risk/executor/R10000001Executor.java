package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.common.BaiqishiRiskDecision;
import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.tongdun.common.TongdunRiskDecision;
import com.rongdu.loans.tongdun.vo.FraudApiVO;

/**
 *如果白骑士和同盾风险决策皆为通过，那么该笔贷款申请自动审批通过
 * @author sunda
 * @version 2017-08-14
 */
public class R10000001Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10000001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		DecisionVO baishiqiDecisionVo = getDataInvokeService().doBaishiqiDecision(context);

		FraudApiVO tongdunDecisionVo = getDataInvokeService().doTongdunAntifraud(context);

		HitRule hitRule =  checkAntifraudDecision(baishiqiDecisionVo,tongdunDecisionVo);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
	}

	/**
	 * 如果白骑士和同盾风险决策皆为通过，那么该笔贷款申请自动审批通过
	 */
	private HitRule checkAntifraudDecision(DecisionVO baishiqiDecisionVo,FraudApiVO tongdunDecisionVo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (StringUtils.equalsIgnoreCase(BaiqishiRiskDecision.ACCEPT,baishiqiDecisionVo.getFinalDecision())&&
				StringUtils.equalsIgnoreCase(TongdunRiskDecision.ACCEPT,tongdunDecisionVo.getFinal_decision())){
			setHitNum(1);
		}
		hitRule.setValue(baishiqiDecisionVo.getFinalDecision());
		String msg = String.format("白骑士风险决策：%s，同盾风险决策：%s",baishiqiDecisionVo.getFinalDecision(),tongdunDecisionVo.getFinal_decision());
		hitRule.setRemark(msg);
		return  hitRule;
	}

}
