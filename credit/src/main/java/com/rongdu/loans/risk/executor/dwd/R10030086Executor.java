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
import java.util.List;

/**
 * 近1个月通话次数top10的号码与通讯录名单匹配小于等于2
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030086Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030086);
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
     * 近1个月通话次数top10的号码与通讯录名单匹配小于等于2
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
            // 根据通话详情中近一个月通话次数排序
            List<ContactList> contactList = report.getContactList();
            Collections.sort(contactList,(o1, o2) -> o2.getContact1m() - o1.getContact1m());
            // 截取近一个月的通话记录top10
            List<ContactList> contactLists = null;
            if (contactList.size() >= 10){
                contactLists = contactList.subList(0, 10);
            } else {
                contactLists = contactList;
            }
            // 与通讯录号码比对匹配数
            for (ContactList contact : contactLists) {
                if (null != contact){
                    for (PhoneList phone : phoneList) {
                        if (null != phone && StringUtils.isNotBlank(phone.getPhone()) && phone.getPhone().equals(contact.getPhoneNum())){
                            count++;
                            break;
                        }
                    }
                }
            }
            if (count <= 2) {
                setHitNum(1);
                remark = String.format("申请人近1个月通话次数top10的号码与通讯录名单匹配小于等于2，近一个月通话次数匹配数：%s", count);
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
