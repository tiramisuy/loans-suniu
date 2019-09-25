package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;


/**
 * 规则名称：通讯录有通话次数的联系人个数大于等于20
 */
public class AT009Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        // 获取申请人设备通讯录
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();

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
     * 通讯录有通话次数的联系人个数大于等于20
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<PhoneList> phoneList) {

        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report != null && report.getContactList() != null && phoneList != null) {
            List<ContactList> contactList = report.getContactList();
            for (PhoneList phone : phoneList) {
                // 与通讯录号码比对匹配数
                for (ContactList contact : contactList) {
                    if (contact.getPhoneNum().contains(phone.getPhone()) || phone.getPhone().contains(contact.getPhoneNum())){
                        count++;
                        break;
                    }
                }
             }

            if (count < 20) {
                setHitNum(1);
                remark = String.format("通讯录有通话次数的联系人个数：%s", count);
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商联系人通话详情为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.AT009);
    }
}
