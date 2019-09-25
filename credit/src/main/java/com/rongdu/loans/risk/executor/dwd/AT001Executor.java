package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.common.BaiqishiRiskDecision;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

/**
 * 规则名称：骑士返回结果为通过
 */
public class AT001Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        RiskRule riskRule = getRiskRule();
        HitRule hitRule = createHitRule(riskRule);
        String decisionResult = (String) context.get("decisionResult");
        if (StringUtils.equalsIgnoreCase(BaiqishiRiskDecision.REJECT,
                decisionResult)){
            addHitNum(1);
            addHitRule(context, hitRule);
            hitRule.setRemark("白骑士建议拒绝");
        }

        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), decisionResult);
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT001);
    }
}
