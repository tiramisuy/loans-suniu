package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.PhoneList;
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
 * 设备通讯录号码与运营商近6个月的常用通话记录TOP20中一致数＜7
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030114Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030114);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        // 获取申请人设备通讯录
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        if (report == null || phoneList == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(report, phoneList);
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
     * 设备通讯录号码与运营商近6个月的常用通话记录TOP20中一致数＜7
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report.getContactList() != null) {
            // 根据通话详情中近6个月通话次数排序
            List<ContactList> contactList = report.getContactList();
            Collections.sort(contactList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    // 倒序排序
                    return o2.getCallCnt() - o1.getCallCnt();
                }
            });
            // 截取近6个月的通话时长top20
            List<ContactList> contactLists = null;
            if (contactList.size() >= 20){
                contactLists = contactList.subList(0, 20);
            } else {
                contactLists = contactList;
            }
            // 与通讯录号码比对匹配数
            for (ContactList contact : contactLists) {
                if (null != contact){
                    for (PhoneList phone : phoneList) {
                        if (null!= phone && StringUtils.isNotBlank(phone.getPhone()) && phone.getPhone().equals(contact.getPhoneNum())){
                            count++;
                            break;
                        }
                    }
                }
            }
            if (count < 7) {
                setHitNum(1);
                remark = String.format("设备通讯录号码与运营商近6个月的常用通话记录TOP20中一致数＜7，近6个月通话次数匹配数：%s", count);
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
