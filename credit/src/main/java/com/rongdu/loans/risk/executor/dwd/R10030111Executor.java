package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.BehaviorCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 贷款类号码联系情况经常联系
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030111Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030111);
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
     * 贷款类号码联系情况经常联系
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (report.getBehaviorCheck() != null) {
            List<BehaviorCheck> behaviorCheck = report.getBehaviorCheck();
            for (BehaviorCheck check : behaviorCheck) {
                if ("contact_loan".equals(check.getCheckPoint())){
                    if (check.getScore() >= 2) {
                        setHitNum(1);
                        remark = "贷款类号码联系情况经常联系,多次通话(3次以上)";
                        hitRule.setRemark(remark);
                        break;
                    }
                }
            }
        }
        return hitRule;
    }

}
