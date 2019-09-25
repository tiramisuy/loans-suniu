package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @version V1.0
 * @Title: R10030064Executor.java
 * @Package com.rongdu.loans.risk.executor.xjbk
 * @Description: 非活跃总天数>90
 * @author: yuanxianchu
 * @date 2018年10月10日
 */
public class R10030064Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 命中的规则
        HitRule hitRule = checkRule(context);
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

    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        // 加载风险分析数据
        JDQReport op = getDataInvokeService().getjdqReport(context);
        int num = -1;
        num = op.getDays1();
        if (num > 90) {
            setHitNum(1);
        }


        hitRule.setRemark(String.format("非活跃总天数：%s天 ", num));
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030064);
    }

}
