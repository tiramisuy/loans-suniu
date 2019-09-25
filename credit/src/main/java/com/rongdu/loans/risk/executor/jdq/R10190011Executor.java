package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 贷款类通话次数较多
 *
 * @version 2018-03-26
 */
public class R10190011Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190011);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBase(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null)
            return;
        // 命中的规则
        HitRule hitRule = check(vo.getMoxieTelecomReport().call_risk_analysis);
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


    private HitRule check(List<MoxieTelecomReport.CallRiskAnalysisBean> callRiskAnalysisBeans) {

        HitRule hitRule = createHitRule(getRiskRule());
        Integer num = null;
        for (int i = 0; i < callRiskAnalysisBeans.size(); i++) {
            if ("loan".equals(callRiskAnalysisBeans.get(i).analysis_item)) {
                num = callRiskAnalysisBeans.get(i).analysis_point.call_cnt_6m;
            }
        }

        if (num != null && num > 20) {
            setHitNum(1);
        }
        hitRule.setRemark(String.format("贷款类通话次数>20，当前：%d", num));
        return hitRule;
    }
}
