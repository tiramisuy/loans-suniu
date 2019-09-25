package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 近6个月通话号码”数量众多“，大于100
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030092Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030092);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        if (report == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(report);
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
     * 近6个月通话号码”数量众多“，大于100
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report.getContactList() != null) {
            List<ContactList> contactList = report.getContactList();
            for (ContactList contact : contactList) {
                if (contact.getCallCnt() > 0) {
                    count++;
                }
            }
            if (count > 100) {
                setHitNum(1);
                remark = String.format("近6个月通话号码”数量众多“，大于100，近6个月通话号码数量：%s", count);
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商月度数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
