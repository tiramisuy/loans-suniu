package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 每月平均消费不符合要求
 *
 * @version 2018-03-26
 */
public class R10190006Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190006);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBase(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null)
            return;
        // 命中的规则
        HitRule hitRule = check(vo.getMoxieTelecomReport().consumption_detail);
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


    private HitRule check(List<MoxieTelecomReport.ConsumptionDetailBean> consumptionDetailBeans) {

        HitRule hitRule = createHitRule(getRiskRule());
        Double avgFee = null;
        for (int i = 0; i < consumptionDetailBeans.size(); i++) {
            if ("total_fee".equals(consumptionDetailBeans.get(i).app_point)) {
                avgFee = Double.valueOf(consumptionDetailBeans.get(i).item.avg_item_6m);
                break;
            }
        }
        avgFee = avgFee / 100;
        double sup = 400;
        double inf = 30;
        if (avgFee > sup || avgFee < inf) {
            setHitNum(1);
        }
        hitRule.setRemark(String.format("每月平均消费>%s,或者每月平均消费<%s,当前：%s", sup, inf, avgFee));
        return hitRule;
    }
}
