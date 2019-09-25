package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;

/**
 * 规则名称：关机时长
 */
public class AT016Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        DWDReport report = getDataInvokeService().getdwdReport(context);

        //命中的规则
        HitRule hitRule = checkRule(report, context.getApplyInfo().getApplyTime());
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum>0){
            addHitRule(context,hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
    }

    /**
     * 借款人连续关机天数，最近一次时间拒申请时间大于30天
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report, String applyTime) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;

        if (report != null) {
            if(StringUtils.isNotBlank(report.getRecentDays3Time())) {
                Date recentDays3Date = DateUtils.parse(report.getRecentDays3Time());
                Date applyDate = DateUtils.parse(applyTime);
                if(DateUtils.daysBetween(recentDays3Date, applyDate) <= 30) {
                    setHitNum(1);
                    remark = "借款人连续关机天数，最近一次时间:" + DateUtils.formatDateTime(recentDays3Date);
                    hitRule.setRemark(remark);
                }
            }
        } else {
            setHitNum(1);
            remark = "运营商报告为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT016);
    }
}
