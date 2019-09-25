package com.rongdu.loans.risk.executor.sll;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @Description: 联系人通话次数不符合要求 数据来源于：API
 * @author: 饶文彪
 * @date 2018年6月27日 下午4:08:19
 */
public class R10030052Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030052);
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
     * 判断是否呼入呼出同时小于25
     *
     * @param context
     * @return
     */
    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());

        JDQReport xianJinBaiKaBase = getDataInvokeService().getsllReport(context);
        int callInCount_max = xianJinBaiKaBase.getBjsize();
        int callOutCount_max = xianJinBaiKaBase.getZjsize();

        int maxCount = 24;
        if (callInCount_max < maxCount && callOutCount_max < maxCount) {// 呼入呼出同时小于25
            setHitNum(1);
        }

        hitRule.setRemark(String.format("呼入呼出同时小于%s,最大呼入%s;最大呼出%s;数据来源：现金白卡", maxCount, callInCount_max, callOutCount_max));

        return hitRule;
    }

}
