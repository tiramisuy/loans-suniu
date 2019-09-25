package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 申请人近6个月主叫异地号码占比>=80%
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030121Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030121);
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
     * 申请人近6个月主叫异地号码占比>=80%
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (report.getContactList() != null) {
            // 获取申请人手机号码归属地
            String applyLoc = null;
            List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
            if (applicationCheck != null && applicationCheck.size() > 0) {
                for (ApplicationCheck check : applicationCheck) {
                    if ("cell_phone".equals(check.getAppPoint())) {
                        applyLoc = check.getCheckPoints().getWebsite();
                        break;
                    }
                }
            }
            // 被叫号码数量
            Integer count = 0;
            // 异地号码被叫数量
            Integer countOther = 0;
            List<ContactList> contactList = report.getContactList();
            for (ContactList contact : contactList) {
                if (contact.getCallInCnt() > 0) {
                    count++;
                    if (!applyLoc.contains(contact.getPhoneNumLoc())) {
                        countOther++;
                    }
                }

            }
            if ((Double.valueOf(countOther) / Double.valueOf(count)) >= Double.valueOf(0.8)) {
                setHitNum(1);
                remark = String.format("申请人近6个月主叫异地号码占比>=0.8，异地号码个数：%s，总数：%s", countOther, count);
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
