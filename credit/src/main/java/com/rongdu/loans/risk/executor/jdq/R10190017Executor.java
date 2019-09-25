package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 〈运营商报告手机号与客户身份信息手机号不一致〉
 *
 * @version 2018-03-26
 */
public class R10190017Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190017);
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
        HitRule hitRule = check(vo.getMoxieTelecomReport().cell_phone, context.getUser().getMobile());
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


    private HitRule check(List<MoxieTelecomReport.CellPhoneBean> cellPhoneBeans, String userMobile) {

        HitRule hitRule = createHitRule(getRiskRule());
        String reportMobile = null;
        for (int i = 0; i < cellPhoneBeans.size(); i++) {
            if ("mobile".equals(cellPhoneBeans.get(i).key)) {
                reportMobile = cellPhoneBeans.get(i).value;
                break;
            }
        }
        if (!checkUserMobile(userMobile, reportMobile)) {
            setHitNum(1);
        }
        hitRule.setRemark(String.format("运营商报告手机号vs客户信息手机号：%s vs %s", reportMobile, userMobile));
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
}
