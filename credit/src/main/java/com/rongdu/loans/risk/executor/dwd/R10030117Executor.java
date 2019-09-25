package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 临时小号检查
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030117Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030117);
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
     * 联系人中有临时小号
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Boolean flag = true;
        // 执行规则逻辑
        if (report.getApplicationCheck() != null) {
            List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
            if (null != applicationCheck) {
                for (ApplicationCheck check : applicationCheck) {
                    if ("contact".equals(check.getAppPoint())){
                        if ("该联系人号码为临时小号".equals(check.getCheckPoints().getCheck_xiaohao())){
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (!flag){
                setHitNum(1);
                remark = "联系人中有临时小号";
                hitRule.setRemark(remark);
            }
        }
        return hitRule;
    }

}
