package com.rongdu.loans.risk.executor.sll;

import com.rongdu.loans.loan.option.jdq.Transactions;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * @version V1.0
 * @Title: R10030067Executor.java
 * @Package com.rongdu.loans.risk.executor.xjbk
 * @Description: 平均月消费>400
 * @author: yuanxianchu
 * @date 2018年10月10日
 */
public class R10030067Executor extends Executor {

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
        JDQReport op = getDataInvokeService().getsllReport(context);
        List<Transactions> transactionsList = op.getTransactions();
        if (transactionsList != null) {
            for (Transactions transaction : transactionsList) {
                if ("平均".equals(transaction.getBillCycle())) {
                    if (transaction.getPayAmt() > 400 || transaction.getPayAmt() < 30) {
                        setHitNum(1);
                        hitRule.setRemark(String.format("平均月消费：%s", transaction.getPayAmt()));
                    }
                }
            }
        }
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030067);
    }

}
