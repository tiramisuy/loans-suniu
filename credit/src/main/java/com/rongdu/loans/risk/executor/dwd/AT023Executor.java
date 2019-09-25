package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calloutcntlistv;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 规则名称：运营商报告呼出前20，与通讯录匹配大于50%
 */
public class AT023Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);
        if (dwdReport == null) {
            return;
        }
        // 获取申请人设备通讯录
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        if (dwdReport == null || phoneList == null) {
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
     * 运营商报告呼出前20，与通讯录匹配大于50%
     *
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report, List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report.getCalloutcntlistv() != null) {

            List<Calloutcntlistv> contactLists = null;
            if (report.getCalloutcntlistv().size() >= 20){
                contactLists = report.getCalloutcntlistv().subList(0, 20);
            } else {
                contactLists = report.getCalloutcntlistv();
            }
            // 与通讯录号码比对匹配数
            for (Calloutcntlistv contact : contactLists) {
                for (PhoneList phone : phoneList) {
                    if (phone.getPhone().contains(contact.getMobile())||contact.getMobile().contains(phone.getPhone())){
                        count++;
                        break;
                    }
                }
            }
            if (count <= 10) {
                setHitNum(1);
                remark = String.format("运营商报告呼出前20，与通讯录匹配小于等于0.5：%s", count);
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
        super.setRuleId(RuleIds.AT023);
    }
}
