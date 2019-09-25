package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 〈一句话功能简述〉<br>
 * 〈贷款记录<2或>300〉
 *
 * @author yuanxianchu
 * @create 2019/3/12
 * @since 1.0.0
 */
public class R10190019Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 命中的规则
        HitRule hitRule = checkRule(context);
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

    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        TianjiReportDetailResp tianjiReportDetail = getDataInvokeService().getRongTJReportDetail(context);
        int loanRecordCnt = tianjiReportDetail.getJson().getRiskAnalysis().getLoanRecordCnt();
        if ((loanRecordCnt > 0 && loanRecordCnt < 2) || loanRecordCnt > 300) {
            //贷款记录<2或>300
            setHitNum(1);
        }
        hitRule.setRemark(String.format("贷款记录小于2 或大于300，当前：%s", loanRecordCnt));
        return hitRule;
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190019);
    }
}
