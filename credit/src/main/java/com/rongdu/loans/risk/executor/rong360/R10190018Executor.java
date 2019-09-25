package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 〈一句话功能简述〉<br>
 * 〈运营商报告身份证验证不一致〉
 *
 * @author yuanxianchu
 * @create 2019/2/11
 * @since 1.0.0
 */
public class R10190018Executor extends Executor {

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
        int idCardCheck = tianjiReportDetail.getJson().getBasicInfo().getIdCardCheck();
        if (idCardCheck == 3) {
            //身份证验证3：不一致
            setHitNum(1);
        }
        hitRule.setRemark("运营商报告身份证验证不一致");
        return hitRule;
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190018);
    }
}
