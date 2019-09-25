package com.rongdu.loans.risk.executor.sll;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @Description: 互通电话号码较低
 * 数据来源于：API
 * @author: 饶文彪
 * @date 2018年6月27日 下午4:08:19
 */
public class R10030044Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030044);
    }

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

    /**
     * 判断是否互通电话号码较低
     *
     * @param context
     * @return
     */
    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        JDQReport xianJinBaiKaBase = getDataInvokeService().getsllReport(context);

        try {
            int num = -1;
            num = xianJinBaiKaBase.getCountcall();
            if (num > -1 && num < 12) {
                setHitNum(1);

            }
            hitRule.setRemark(String.format("互通过电话的号码数量：%s个", num));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hitRule;
    }

}
