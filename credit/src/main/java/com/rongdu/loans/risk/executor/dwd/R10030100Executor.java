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
 * 运营商前3名最常用联系人近半年累计通话时长有一人≤20分钟
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030100Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030100);
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
     * 运营商前3名最常用联系人近半年累计通话时长有一人≤20分钟
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Boolean flag = true;
        // 获取运营商联系人通话详情
        if (report.getContactList() != null) {
            // 根据通话详情中近6个月通话时长排序
            List<ContactList> contactList = report.getContactList();
            Collections.sort(contactList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    // 倒序排序
                    return String.valueOf(o2.getCallLen()).compareTo(String.valueOf(o1.getCallLen()));
                }
            });
            // 截取近6个月的通话记录top3 为常用联系人
            List<ContactList> contactLists = null;
            if (contactList.size() >= 3){
                contactLists = contactList.subList(0, 3);
            } else {
                contactLists = contactList;
            }
            // 与通讯录号码比对匹配数
            for (ContactList contact : contactLists) {
                if (contact.getCallLen() <= Double.valueOf(20)){
                    flag = false;
                }
            }
            if (!flag) {
                setHitNum(1);
                remark = "运营商前3名最常用联系人近半年累计通话时长有一人≤20分钟";
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商联系人通话详情为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
