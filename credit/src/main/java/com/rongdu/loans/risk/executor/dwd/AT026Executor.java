package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 规则名称：身份证组合过其它电话个数小于2(不包含申请人申请进件的身份证号码)
 */
public class AT026Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {

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
     * 身份证组合过其它电话个数小于2(不包含申请人申请进件的身份证号码)
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (report.getUserInfoCheck() != null && report.getUserInfoCheck().getCheckSearchInfo() != null && report.getUserInfoCheck().getCheckSearchInfo().getIdcardWithOtherPhones() != null) {
            List<String> lists = report.getUserInfoCheck().getCheckSearchInfo().getIdcardWithOtherPhones();
            if (lists != null && lists.size() >= 2) {
                setHitNum(1);
                remark = String.format("身份证组合过其它电话个数大于等于2，详情列表：%s", lists.toString());
                hitRule.setRemark(remark);
            }

        }
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT026);
    }
}
