package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @version V1.0
 * @Title: R10030063Executor.java
 * @Package com.rongdu.loans.risk.executor.xjbk
 * @Description: 紧急联系人在通话详单中的个数小于等于5个
 */
public class R10030063Executor extends Executor {

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
     * 紧急联系人在通话详单中的个数小于等于5个
     *
     * @param context
     * @return
     */
    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        // 加载风险分析数据
        JDQReport xianJinBaiKaBase = getDataInvokeService().getjdqReport(context);
        int callcnt = xianJinBaiKaBase.getUrgentcontact().get(0).getCallInCnt() + xianJinBaiKaBase.getUrgentcontact().get(0).getCallOutCnt();
        int callcnt1 = xianJinBaiKaBase.getUrgentcontact().get(1).getCallInCnt() + xianJinBaiKaBase.getUrgentcontact().get(1).getCallOutCnt();
        if ((callcnt + callcnt1) <= 5) {
            setHitNum(1);
        }
        hitRule.setRemark("紧急联系人在通话详单中的个数不符合条件");
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030063);
    }

}
