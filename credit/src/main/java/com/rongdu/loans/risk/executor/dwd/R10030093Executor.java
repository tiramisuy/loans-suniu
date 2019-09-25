package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.BlacklistService;

import java.util.List;

/**
 * 近6个月主叫号码命中命中本地黑名单的号码个数>=2
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030093Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030093);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        if (report == null) {
            return;
        }
        // 加载本地黑名单数据
        BlacklistService blacklistService = SpringContextHolder.getBean("blacklistService");
        List<Blacklist> list = blacklistService.getALLBlackCust();
        // 命中的规则
        HitRule hitRule = checkRule(report, list);
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
     * 近6个月主叫号码命中命中本地黑名单的号码个数>=2
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<Blacklist> list) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Integer count = 0;
        // 获取运营商联系人通话详情
        if (report.getContactList() != null) {
            List<ContactList> contactList = report.getContactList();
            for (ContactList contact : contactList) {
                for (Blacklist black : list) {
                    if (contact.getPhoneNum().equals(black.getMobile())){
                        count++;
                        break;
                    }
                }
            }
            if (count >= 2) {
                setHitNum(1);
                remark = String.format("申请人近6个月主叫号码命中命中本地黑名单的号码个数>=2，近6个月命中数量：%s", count);
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中运营商月度数据为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
