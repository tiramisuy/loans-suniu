package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 〈运营商报告手机号与客户身份证验证不一致〉
 *
 * @version 2018-03-26
 */
public class R10190018Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190018);
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
        HitRule hitRule = check(vo.getMoxieTelecomReport().basic_check_items);
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


    private HitRule check(List<MoxieTelecomReport.BasicCheckItemsBean> basicCheckItemsBeans) {

        HitRule hitRule = createHitRule(getRiskRule());
        for (int i = 0; i < basicCheckItemsBeans.size(); i++) {
            if ("idcard_match".equals(basicCheckItemsBeans.get(i).check_item)) {
                if ("匹配失败".equals(basicCheckItemsBeans.get(i).result)) {
                    setHitNum(1);
                    break;
                }
            }
        }
        hitRule.setRemark("运营商报告身份证验证不一致");
        return hitRule;
    }
}
