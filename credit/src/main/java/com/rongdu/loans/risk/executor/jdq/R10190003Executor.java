package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @version V1.0
 * @Title: R10190003Executor.java
 * @Description: 申请人命中魔蝎黑名单
 * @author: yuanxianchu
 * @date 2018年7月19日
 */
public class R10190003Executor extends Executor {

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
        HitRule hitRule = checkRule(vo.getMoxieTelecomReport().user_info_check.get(0).check_black_info);
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
     * 用户号码联系关注名单综合分数（分数范围0-100，参考分为40，分数越低关系越紧密）
     *
     * @return
     */
    private HitRule checkRule(MoxieTelecomReport.UserInfoCheckBean.CheckBlackInfoBean checkSearchInfoBean) {
        HitRule hitRule = createHitRule(getRiskRule());
        int threshold = 40;
        if (checkSearchInfoBean != null) {
            int score = checkSearchInfoBean.phone_gray_score;
            if (score < threshold) {
                setHitNum(1);
                hitRule.setValue(String.valueOf(score));
            }
            hitRule.setRemark("用户号码联系关注名单综合分数 : " + score);
        }
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190003);
    }

}
