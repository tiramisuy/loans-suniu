package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 联系人互通电话的号码数量不符合要求(近6个月)
 *
 * @version 2018-03-26
 */
public class R10190001Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190001);
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
        if (vo == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = check(vo.getMoxieTelecomReport().friend_circle.summary);
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


    private HitRule check(List<MoxieTelecomReport.FriendCircleBean.SummaryBean> summaryBeans) {

        HitRule hitRule = createHitRule(getRiskRule());
        Integer num = null;
        for (int i = 0; i < summaryBeans.size(); i++) {
            if ("inter_peer_num_6m".equals(summaryBeans.get(i).key)) {
                num = Integer.valueOf(summaryBeans.get(i).value);
                break;
            }
        }
        if (num != null && num > 120) {
            setHitNum(1);
        }
        String msg = String.format("互通电话的号码数量：%s", num);
        hitRule.setRemark(msg);
        return hitRule;
    }
}
