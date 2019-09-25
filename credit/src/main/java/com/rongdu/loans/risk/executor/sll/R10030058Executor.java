package com.rongdu.loans.risk.executor.sll;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 日常联系人未保存在设备通讯录中3 数据来源于：白骑士资信云报告数据
 *
 * @author liuzhuang
 * @version 2018-04-16
 */
public class R10030058Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030058);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
        JDQReport jdqReport = creditDataInvokeService.getsllReport(context);
        List<ContactCheck> list = jdqReport.getContactCheckList();

        // 命中的规则
        HitRule hitRule = checkContactAuthenticity(list);
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
     * 设备通讯录号码与运营商近6个月的常用联系人通话次数均小于50次
     *
     * @param contactList
     * @param ccmList
     * @return
     */
    private HitRule checkContactAuthenticity(List<ContactCheck> contactChecks) {
        HitRule hitRule = createHitRule(getRiskRule());
        int count = 0;
        int threshold = 50;
        if (contactChecks != null) {
            for (ContactCheck c : contactChecks) {
                if (c.getCallCnt() >= threshold) {
                    count++;
                }
            }
        }
        if (count == 0) {
            setHitNum(1);
            hitRule.setValue(String.valueOf(count));
        }
        int contactCount = contactChecks != null ? contactChecks.size() : 0;
        String msg = String.format("通讯录号码数量：%s，通话次数>=50次数量：%s", contactCount, count);
        hitRule.setRemark(msg);
        return hitRule;
    }
}
