package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.math.BigDecimal;

/**
 * @version V1.0
 * @Title: R10030068Executor.java
 * @Package com.rongdu.loans.risk.executor.xjbk
 * @Description: 夜间活动情况>20%
 * @author: yuanxianchu
 * @date 2018年10月11日
 */
public class R10030068Executor extends Executor {

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
        DWDReport op = getDataInvokeService().getdwdReport(context);

        int num = op.getBl().compareTo(new BigDecimal(20));
        if (num > 0) {
            setHitNum(1);
        }


        hitRule.setRemark("夜间活动情况>20%");
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030068);
    }

}
