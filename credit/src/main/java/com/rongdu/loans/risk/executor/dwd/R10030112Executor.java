package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.CheckPoints;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 运营商金融服务类机构黑名单电话号检查
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030112Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030112);
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
     * 运营商金融服务类机构黑名单电话号检查
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
                if ("cell_phone".equals(check.getAppPoint())) {
                    CheckPoints.FinancialBlacklist financialBlacklist = check.getCheckPoints().getFinancial_blacklist();
                    if (financialBlacklist.arised){
                        setHitNum(1);
                        remark = String.format("运营商金融服务类机构黑名单电话号检查,用户命中黑名单，列表：%s",financialBlacklist.black_type != null ? financialBlacklist.black_type.toString() : "");
                        hitRule.setRemark(remark);
                    }
                    break;
                }
            }
        }
        return hitRule;
    }

}
