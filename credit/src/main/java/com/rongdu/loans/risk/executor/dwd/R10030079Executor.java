package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.report.*;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 申请人未呼叫过紧急联系人
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030079Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030079);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载用户紧急联系人数据
        List<CustContactVO> contactList = context.getUserInfo().getContactList();
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        if (report == null || contactList == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(report, contactList);
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
     * 申请人未呼叫过紧急联系人
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<CustContactVO> contactList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Boolean flag = true;
        // 执行规则逻辑
        // 获取紧急联系人手机号码
        if (report.getContactList() != null) {
            List<ContactList> contactListReport = report.getContactList();
            A:
            for (CustContactVO custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getMobile())) {
                    for (ContactList contact : contactListReport) {
                        // 执行判断
                        if (custContactVO.getMobile().equals(contact.getPhoneNum())) {
                            flag = false;
                        }
                    }
                }
            }
            if (flag) {
                setHitNum(1);
                remark = "聚信立分析报告数据中紧急联系人的通话记录为空";
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立报告号码通讯详情为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
