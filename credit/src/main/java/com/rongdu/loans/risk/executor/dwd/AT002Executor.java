package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RiskRank;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.List;

/**
 * 规则名称：未触发拒绝规则
 */
public class AT002Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        List<HitRule> hitRuleList = context.getHitRules();

        int hitNum = 0;
        StringBuilder evidence = new StringBuilder();
        RiskRule riskRule = getRiskRule();
        HitRule hitRule = createHitRule(riskRule);
        for (HitRule r : hitRuleList) {
            if(RiskRank.A.equals(r.getRiskRank()) || RiskRank.B.equals(r.getRiskRank())) {
                addHitNum(1);
                evidence.append(r.getRuleId()).append(',');
            }
        }
        if(getHitNum() > 0){
            addHitRule(context, hitRule);
            evidence.deleteCharAt(evidence.length() - 1);
            hitRule.setRemark(evidence.toString());
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.AT002);
    }
}
