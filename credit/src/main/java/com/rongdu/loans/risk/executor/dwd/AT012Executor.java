package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则名称：通讯录含贷款联系人较少
 */
public class AT012Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        List<PhoneList> addressBookList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);

        // 命中的规则
        HitRule hitRule = checkRule(dwdReport, addressBookList);
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
     * 通讯录名称包含“贷款”“代款”字眼的联系人小于等于1，且通话次数不在通讯录联系次数TOP20内
     *
     * @return
     */
    private HitRule checkRule(DWDReport report, List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        int count = 0;
        List<PhoneList> loanPhoneList = new ArrayList();
        // 获取运营商联系人通话详情
        if (report != null && report.getContactCheckList() != null && phoneList != null) {
            for(PhoneList item : phoneList) {
                if(item.getName() != null
                        && (item.getName().contains("贷款") || item.getName().contains("代款"))) {
                    count++;
                    loanPhoneList.add(item);
                }
            }
            if(count > 1) {
                setHitNum(1);
                remark = "通讯录名称包含“贷款”“代款”字眼的联系人大于1";
                hitRule.setRemark(remark);
                return  hitRule;
            }

            // 截取近一个月的通话记录top20
            List<ContactCheck> contactLists = null;
            if (report.getContactCheckList().size() > 20){
                contactLists = report.getContactCheckList().subList(0, 20);
            } else {
                contactLists = report.getContactCheckList();
            }

            count = 0;
            // 与通讯录号码比对匹配数
            for (PhoneList phone : loanPhoneList) {
                for (ContactCheck contact : contactLists) {
                    if (phone.getPhone().contains(contact.getMobile()) || contact.getMobile().contains(phone.getPhone())){
                        count++;
                        break;
                    }
                }
            }
            if (count > 0 ) {
                setHitNum(1);
                remark = "通讯录含贷款联系人的通话次数在通讯录联系次数TOP20内";
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
        super.setRuleId(RuleIds.AT012);
    }
}
