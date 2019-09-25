package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.Behavior;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 申请人最近一月消费合计<20元
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030127Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030127);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        if (report == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(report);
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
     * 申请人最近一月消费合计<20元
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 获取运营商月度报告
        if (report.getCellBehavior() != null && report.getCellBehavior().get(0).getBehavior() != null) {
            // 根据月度报告月份排序
            List<Behavior> behavior = report.getCellBehavior().get(0).getBehavior();
            Collections.sort(behavior, new Comparator<Behavior>() {
                @Override
                public int compare(Behavior o1, Behavior o2) {
                    // 倒序排序
                    return o2.getCellMth().compareTo(o1.getCellMth());
                }
            });
            // 截取最近一个月的报告 向前推一月
            Behavior behaviorOne = null;
            if(behavior.size() > 1){
                behaviorOne= behavior.get(1);
            } else {
                behaviorOne= behavior.get(0);
            }
            if (behaviorOne.getTotalAmount() < 20) {
                setHitNum(1);
                remark = String.format("申请人最近一月消费合计<20元，最近一月消费：%s", behaviorOne.getTotalAmount());
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商月度数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
