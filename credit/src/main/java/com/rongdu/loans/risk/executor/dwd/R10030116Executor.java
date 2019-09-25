package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;
import java.util.List;

/**
 * 手机号入网时长小于6个月
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030116Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030116);
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
     * 手机号入网时长小于6个月
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (report.getApplicationCheck() != null) {
            List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
            for (ApplicationCheck check : applicationCheck) {
                if ("cell_phone".equals(check.getAppPoint()) && StringUtils.isNotBlank(check.getCheckPoints().getReg_time())) {
                    String reg_time = check.getCheckPoints().getReg_time();
                    Integer monthInterval = DateUtils.getMonth(DateUtils.parse(reg_time), new Date());
                    if (monthInterval < 6){
                        setHitNum(1);
                        remark = String.format("手机号入网时长小于6个月，入网时间：%s", reg_time);
                        hitRule.setRemark(remark);
                    }
                    break;
                }
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
