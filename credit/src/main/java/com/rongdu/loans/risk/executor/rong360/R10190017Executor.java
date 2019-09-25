package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 〈一句话功能简述〉<br>
 * 〈运营商报告手机号与客户身份信息手机号不一致〉
 *
 * @author yuanxianchu
 * @create 2019/2/11
 * @since 1.0.0
 */
public class R10190017Executor extends Executor {

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
        String userMobile = context.getUser().getMobile();
        String reportMobile = tianjiReportDetail.getJson().getBasicInfo().getPhone();
        if (!checkUserMobile(userMobile,reportMobile)) {
            setHitNum(1);
        }
        hitRule.setRemark(String.format("运营商报告手机号vs客户信息手机号：%s vs %s",reportMobile,userMobile));
        return hitRule;
    }

    private boolean checkUserMobile(String userMobile, String reportMobile) {
        if (StringUtils.isNotBlank(reportMobile)) {
            String[] reportMobileArr = reportMobile.split("\\*");

            for (String str : reportMobileArr) {
                if (StringUtils.isNotBlank(str) && !userMobile.contains(str)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190017);
    }
}
