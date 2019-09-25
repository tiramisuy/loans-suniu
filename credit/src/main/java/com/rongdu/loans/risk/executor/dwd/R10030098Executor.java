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
 * 近6个月呼出总时长的月均值<=0.2小时或>=75小时
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030098Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030098);
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
     * 近6个月呼出总时长的月均值<=0.2小时或>=75小时
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Double count = 0.00;
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
            // 截取近6个月的通话记录
            List<Behavior> behaviors = null;
            if (behavior.size() >= 6){
                behaviors = behavior.subList(0, 6);
            } else {
                behaviors = behavior;
            }
            // 统计近6个月呼入总时长(单位:分)
            for (Behavior behaviorss : behaviors) {
                count += behaviorss.getCallOutTime();
            }
            Double count6= Double.valueOf(count)/Double.valueOf(6)/Double.valueOf(60);
            if (count6 <= Double.valueOf(0.2) || count6 >= Double.valueOf(75) ) {
                setHitNum(1);
                remark = String.format("近6个月呼出总时长的月均值<=0.2小时或>=75小时，近6个月通话次数均值：%s", count6);
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
