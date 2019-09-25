package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.Behavior;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 申请人历史6个账单月的消费合计波动系数>15%
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030126Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030126);
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
     * 申请人历史6个账单月的消费合计波动系数>15%
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
            // 截取近6个月的通话记录 排除当月
            List<Behavior> behaviors = null;
            if (behavior.size() >= 6){
                behaviors = behavior.subList(1, 6);
                // 获取近6个月花费集合
                BigDecimal[] money = new BigDecimal[5];
                for (int i = 0; i < 5; i++) {
                    money[i] = BigDecimal.valueOf(behaviors.get(i).getTotalAmount());
                }
                BigDecimal max = money[0];
                BigDecimal min = money[0];
                BigDecimal sum = BigDecimal.ZERO;
                BigDecimal ave = BigDecimal.ZERO;
                BigDecimal StanDev = BigDecimal.ZERO;
                for (int i = 0; i < money.length; i++) {
                    if (money[i].compareTo(max) > 0) {
                        max = money[i];
                    }
                    if (money[i].compareTo(min)  < 0 ) {
                        min = money[i];
                    }
                    sum.add(money[i]);
                }
                sum = getDouble(sum);
                ave = sum.divide(BigDecimal.valueOf(money.length));
                ave = getDouble(ave);
                BigDecimal squareSum = BigDecimal.ZERO;
                for (int i = 0; i < money.length; i++) {
                    //squareSum += ((money[i]-ave) * (money[i]-ave));
                    squareSum.add((money[i].subtract(ave)).multiply((money[i].subtract(ave))));
                }
               // StanDev = Math.sqrt(squareSum/(money.length-1));
                StanDev = BigDecimal.valueOf(Math.sqrt(squareSum.divide((BigDecimal.valueOf(money.length).subtract(BigDecimal.ONE))).doubleValue()));
                StanDev = getDouble(StanDev);

                // 波动系数（历史6个账单月消费金额的标准差/历史6个账单月消费金额的均值*100%)
                if (ave.compareTo(BigDecimal.ZERO) == 0){
                    ave = BigDecimal.ONE;
                }
                BigDecimal aDouble = getDouble(StanDev.divide(ave));
                if (aDouble.compareTo(BigDecimal.valueOf(0.15)) > 0) {
                    setHitNum(1);
                    remark = String.format("申请人历史5个账单月的消费合计波动系数>0.15,波动系数：%s", aDouble);
                    hitRule.setRemark(remark);
                }
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商月度数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

    //四舍五入保留2为小数
    private static BigDecimal getDouble(BigDecimal d) {
        return d.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
