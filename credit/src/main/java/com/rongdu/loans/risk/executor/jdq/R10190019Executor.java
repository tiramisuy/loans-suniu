package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * @Author: hbx
 * @Date: 2019/4/10 14:43
 * @desc 近6个月连续3天以上关机次数
 */
public class R10190019Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190019);
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
        HitRule hitRule = check(vo.getMoxieTelecomReport().active_degree);
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


    private HitRule check(List<MoxieTelecomReport.ActiveDegreeBean> activeDegreeBeans) {

        HitRule hitRule = createHitRule(getRiskRule());
        Integer num = null;
        for (int i = 0; i < activeDegreeBeans.size(); i++) {
            if ("continue_power_off_cnt".equals(activeDegreeBeans.get(i).app_point)) {
                num = Integer.valueOf(activeDegreeBeans.get(i).item.item_6m);
                break;
            }
        }
        if (num != null && num >= 3) {
            setHitNum(1);
        }
        hitRule.setRemark(String.format("近6个月连续3天以上关机次数>=2，当前：%d", num));
        return hitRule;
    }
}