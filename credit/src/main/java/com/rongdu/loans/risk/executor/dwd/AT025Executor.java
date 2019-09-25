package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calincntlistv;
import com.rongdu.loans.loan.option.jdq.report.Calloutcntlistv;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 规则名称：紧密联系人在呼出TOP20
 */
public class AT025Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        //通讯录紧密联系人
        List<PhoneList> phoneList = (List<PhoneList>)context.get("addressBookCloseContacts");

        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);
        if (dwdReport == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(dwdReport, phoneList);
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
     * 紧密联系人在呼出TOP20
     *
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report, List<PhoneList> contactList) {
        HitRule hitRule = createHitRule(getRiskRule());
        int countIndex = 0;
        // 执行规则逻辑
        if (report.getCalloutcntlistv() != null) {
            // 获取紧急联系人手机号码
            List<Calloutcntlistv> contactListReports = null;
            if (report.getCalloutcntlistv().size() >= 20){
                contactListReports = report.getCalloutcntlistv().subList(0, 20);
            } else {
                contactListReports = report.getCalloutcntlistv();
            }
            for (PhoneList custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getPhone())) {
                    for (Calloutcntlistv contact : contactListReports) {
                        // 执行判断
                        if (custContactVO.getPhone().contains(contact.getMobile())||contact.getMobile().contains(custContactVO.getPhone())) {
                            countIndex++;
                            break ;
                        }
                    }
                }
            }
        }
        if (report.getCalincntlistv() != null) {
            // 获取紧急联系人手机号码
            List<Calincntlistv> contactListReports = null;
            if (report.getCalincntlistv().size() >= 20){
                contactListReports = report.getCalincntlistv().subList(0, 20);
            } else {
                contactListReports = report.getCalincntlistv();
            }
            for (PhoneList custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getPhone())) {
                    for (Calincntlistv contact : contactListReports) {
                        // 执行判断
                        if (custContactVO.getPhone().contains(contact.getMobile())||contact.getMobile().contains(custContactVO.getPhone())) {
                            countIndex++;
                            break ;
                        }
                    }
                }
            }
        }

        if (countIndex == 0) {
            setHitNum(1);
        }

        String msg = String.format("紧密联系人不在呼入或者呼出TOP20！");
        hitRule.setRemark(msg);
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT025);
    }
}
