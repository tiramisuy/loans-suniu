package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 短号联系过多
 *
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030049Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030049);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        // 命中的规则
        HitRule hitRule = checkShortNumber(report);
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
     * 近6个月通话记录Top10里电话号码位数小于8的个数>=3
     *
     * @return
     */
    private HitRule checkShortNumber(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        int countIndex = 0;
        if (report != null && report.getContactList() != null) {
            // 根据通话详情中近6个月通话次数排序
            List<ContactList> contactList = report.getContactList();
            Collections.sort(contactList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    // 倒序排序
                    return o2.getCallCnt()- o1.getCallCnt();
                }
            });
            // 截取近6个月的通话记录top10
            List<ContactList> contactLists = null;
            if (contactList.size() >= 10){
                contactLists = contactList.subList(0, 10);
            } else {
                contactLists = contactList;
            }
            // 判断号码位数
            for (ContactList contact : contactLists) {
                if (contact.getPhoneNum().length() < 8){
                    countIndex++;
                }
            }
        }
        if (countIndex >= 3) {
            setHitNum(1);
        }

        String msg = String.format("近6个月通话记录Top10里电话号码位数小于8的个数>=3的数量有：%s", countIndex);
        hitRule.setRemark(msg);
        return hitRule;
    }
}