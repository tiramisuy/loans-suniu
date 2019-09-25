package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 近半年常用联系人累计通话时长较短
 * 数据来源于：借点钱报告
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030008Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030008);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
        // 魔蝎分析报告 通话详情
        List<MoxieTelecomReport.CallContactDetailBean> callList = creditDataInvokeService.getjdqBase(context).getMoxieTelecomReport().call_contact_detail;
        //命中的规则
        HitRule hitRule = checkCommonlyConnectMobiles(callList);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(), getRuleName(), context.getUserName(), context.getApplyId(), getHitNum(), evidence);

    }

    /**
     * 运营商前5名最常用联系人近半年累计通话时长有一人≤20分钟(1200秒)
     *
     * @return
     */
    private HitRule checkCommonlyConnectMobiles(List<MoxieTelecomReport.CallContactDetailBean> callList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String msg = "";
        if (null != callList && callList.size() > 5) {
            // 取通话记录前五的数据 从小到大排序
            Collections.sort(callList, new Comparator<MoxieTelecomReport.CallContactDetailBean>() {
                @Override
                public int compare(MoxieTelecomReport.CallContactDetailBean t1, MoxieTelecomReport.CallContactDetailBean t2) {
                    return t1.call_time_6m - t2.call_time_6m;
                }
            });
            List<MoxieTelecomReport.CallContactDetailBean> top5 = callList.subList(callList.size() - 5, callList.size());
            int count = 0;
            for (MoxieTelecomReport.CallContactDetailBean callContactDetailBean : top5) {
                if (callContactDetailBean.call_time_6m < 1200) {
                    count++;
                    break;
                }
            }
            if (count > 0) {
                setHitNum(1);
                msg = "运营商前5名最常用联系人近半年累计通话时长有一人≤20分钟";
                hitRule.setRemark(msg);
            }
        } else {
            setHitNum(1);
            msg = "运营商通话记录为空或常用联系人不足5名";
            hitRule.setRemark(msg);
        }
        return hitRule;
    }

}
