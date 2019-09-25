package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * @Description: 近6个月互通电话号码较低
 * 数据来源于：API
 * @author: 饶文彪
 * @date 2018年6月27日 下午4:08:19
 */
public class R10030044Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030044);
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
        HitRule hitRule = checkRule(vo.getMoxieTelecomReport().friend_circle.summary);
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
     * 互通号码较少（近6个月）
     * @param summaryBeans
     * @return
     */
    private HitRule checkRule(List<MoxieTelecomReport.FriendCircleBean.SummaryBean> summaryBeans) {
        HitRule hitRule = createHitRule(getRiskRule());
        Integer num = null;
        for (int i = 0; i < summaryBeans.size(); i++) {
            if ("inter_peer_num_6m".equals(summaryBeans.get(i).key)) {
                num = Integer.valueOf(summaryBeans.get(i).value);
                break;
            }
        }
        if (num != null && num <= 20 && num > 12) {
            setHitNum(1);
        }
        String msg = String.format("近6个月互通电话的号码数量：%s", num);
        hitRule.setRemark(msg);
        return hitRule;
    }
    //    /**
//     * 判断是否互通电话号码较低
//     *
//     * @param context
//     * @return
//     */
//    private HitRule checkRule(AutoApproveContext context) {
//        HitRule hitRule = createHitRule(getRiskRule());
//        JDQReport xianJinBaiKaBase = getDataInvokeService().getjdqReport(context);
//
//        try {
//            int num = -1;
//            num = xianJinBaiKaBase.getCountcall();
//            if (num > -1 && num < 9) {
//                setHitNum(1);
//
//            }
//            hitRule.setRemark(String.format("互通过电话的号码数量：%s个", num));
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        return hitRule;
//    }



}
