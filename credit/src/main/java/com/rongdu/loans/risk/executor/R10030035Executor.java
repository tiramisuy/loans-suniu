package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.common.BaiqishiRiskDecision;
import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.tongdun.common.TongdunRiskDecision;
import com.rongdu.loans.tongdun.vo.FraudApiVO;

/**
 * 同盾建议拒绝
 * @author sunda
 * @version 2017-08-14
 */
public class R10030035Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030035);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		FraudApiVO vo = getDataInvokeService().doTongdunAntifraud(context);

		HitRule hitRule =  checkAntifraudDecision(vo);
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
	 * 同盾反欺诈决策引擎最终决策为：决绝
	 * @param vo
	 * @return
	 */
	private HitRule checkAntifraudDecision(FraudApiVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		int score =	Integer.valueOf(vo.getFinal_score());
		if (StringUtils.equalsIgnoreCase(TongdunRiskDecision.REJECT,vo.getFinal_decision())){
			setHitNum(1);
		}
		hitRule.setValue(vo.getFinal_decision());
		String msg = String.format("风险决策：%s，最终得分：%s",vo.getFinal_decision(),score);
		hitRule.setRemark(msg);
		return  hitRule;
	}


}
