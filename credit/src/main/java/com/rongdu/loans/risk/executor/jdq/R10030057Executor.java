package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 设备通讯录号码与运营商近6个月的常用通话记录一致数>=0并且<10
 *
 * @version 2018-04-16
 */
public class R10030057Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030057);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
        JDQReport jdqReport = creditDataInvokeService.getjdqReport(context);
        List<ContactCheck> list = jdqReport.getContactCheckList();
//
//        // 命中的规则
        HitRule hitRule = checkContactAuthenticity(list);
//        HitRule hitRule = checkRule(jdqReport);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), evidence);
    }

    /**
     * 近6个月通话时长top20的号码与通讯录名单匹配数小于5
     *
     * @param jdqReport
     * @return
     */
    private HitRule checkRule(JDQReport jdqReport) {
        HitRule hitRule = createHitRule(getRiskRule());
        int count = jdqReport.getCallLongTop20MatchAddressBookNum();
        if (count < 5) {
            setHitNum(1);
            hitRule.setValue(String.valueOf(count));
        }
        String msg = String.format("近6个月通话时长top20的号码与通讯录名单匹配数：%s", count);
        hitRule.setRemark(msg);
        return hitRule;
    }

    /**
     * 设备通讯录号码与运营商近6个月的常用通话记录一致数>=0并且<10
     *
     * @param contactList
     * @param ccmList
     * @return
     */
    private HitRule checkContactAuthenticity(List<ContactCheck> contactChecks) {
        HitRule hitRule = createHitRule(getRiskRule());
        int count = 0;
        int minThreshold = 0;
        int maxThreshold = 10;
        if (contactChecks != null) {
            for (ContactCheck c : contactChecks) {
                if (c.getCallCnt() > 0) {
                    count++;
                }
            }
        }
        if (count >= minThreshold && count < maxThreshold) {
            setHitNum(1);
            hitRule.setValue(String.valueOf(count));
        }
        int contactCount = contactChecks != null ? contactChecks.size() : 0;
        String msg = String.format("通讯录号码数量：%s，一致数量：%s", contactCount, count);
        hitRule.setRemark(msg);
        return hitRule;
    }

}
