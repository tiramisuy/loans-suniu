package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 运营商手机号近3个月关机天数较多
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030106Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030106);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);
        if (dwdReport == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(dwdReport);
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
     * 运营商手机号近3个月关机天数大于等于20
     *
     * @param dwdReport
     * @return
     */
    private HitRule checkRule(DWDReport dwdReport) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (dwdReport.getMonth3Days() >= 20){
            setHitNum(1);
            remark = String.format("运营商手机号近3个月关机天数大于等于20，近3个月关机天数：%s", dwdReport.getMonth3Days());
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
