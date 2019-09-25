package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.zhicheng.vo.DecisionVO;

/**
 * 规则名称：宜信小额评分大于500分
 */
public class AT004Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        DecisionVO decisionVO = getDataInvokeService().getZhiChengDecision(context);

        int hitNum = 0;
        String evidence = null;
        RiskRule riskRule = getRiskRule();
        HitRule hitRule = createHitRule(riskRule);
        if (decisionVO != null && decisionVO.getData() != null && StringUtils.isNotBlank(decisionVO.getData().getCompositeScore())) {
            int compositeScore = Integer.valueOf(decisionVO.getData().getCompositeScore());
            if (compositeScore < 500) {
                addHitNum(1);
                evidence= String.format("该客户小额评分小于500分,分数为：%s", decisionVO.getData().getCompositeScore());
            }
        }
        if(getHitNum() > 0){
            addHitRule(context, hitRule);
            hitRule.setRemark(evidence);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.AT004);
    }
}
