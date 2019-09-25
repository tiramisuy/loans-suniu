package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calincntlistv;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 规则名称：运营商报告呼入TOP50短号较少
 */
public class AT021Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);
        if (dwdReport == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkShortNumber(dwdReport);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), evidence);
    }

    /**
     * 运营商报告呼出前50，短号小于5个
     *
     * @return
     */
    private HitRule checkShortNumber(DWDReport report) {
        HitRule hitRule = createHitRule(getRiskRule());
        int countIndex = 0;
        if (report != null && report.getCalincntlistv() != null) {
            List<Calincntlistv> contactList = report.getCalincntlistv();
            // 判断号码位数
            for (Calincntlistv contact : contactList) {
                if (contact.getMobile().length() < 8 &&contact.getMobile().length()>3){
                    if(!contact.getMobile().substring(0,3).equals("100")&&!contact.getMobile().substring(0,3).equals("955")){
                        countIndex++;
                    }

                }
            }
        }
        if (countIndex >= 5) {
            setHitNum(1);
        }

        String msg = String.format("运营商报告呼入前50，短号数为：", countIndex);
        hitRule.setRemark(msg);
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT021);
    }
}
