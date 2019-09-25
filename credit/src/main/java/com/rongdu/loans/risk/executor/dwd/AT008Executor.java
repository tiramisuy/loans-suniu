package com.rongdu.loans.risk.executor.dwd;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 规则名称：紧密联系人通话次数在通讯录联系次数TOP20内
 */
public class AT008Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {

        // 加载聚信立分析报告数据
        //Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        DWDReport dwdReport = getDataInvokeService().getdwdReport(context);
        if (dwdReport == null) {
            return;
        }
        //通讯录紧密联系人
        List<PhoneList> phoneList = (List<PhoneList>)context.get("addressBookCloseContacts");
        logger.info("AT009"+context.getApplyId()+":"+ JSONObject.toJSONString(phoneList));
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
     * 紧密联系人通话次数在通讯录联系次数TOP20内
     *
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report, List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report != null && report.getContactCheckList() != null && phoneList != null) {

            // 截取近一个月的通话记录top20
            List<ContactCheck> contactLists = null;
            if (report.getContactCheckList().size() > 20){
                contactLists = report.getContactCheckList().subList(0, 20);
            } else {
                contactLists = report.getContactCheckList();
            }
            // 与通讯录号码比对匹配数
            for (PhoneList phone : phoneList) {
                 for (ContactCheck contact : contactLists) {
                    if (phone.getPhone().contains(contact.getMobile()) || contact.getMobile().contains(phone.getPhone())){
                        count++;
                        break;
                    }
                }
            }
            if (count == 0 ) {
                setHitNum(1);
                remark = "紧密联系人通话次数有不在通讯录联系次数TOP20";
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
        super.setRuleId(RuleIds.AT008);
    }
}
