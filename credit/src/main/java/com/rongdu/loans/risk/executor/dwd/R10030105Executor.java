package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.report.CollectionContact;
import com.rongdu.loans.loan.option.dwd.report.ContactDetails;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 直亲的最近一次通话时间（月）距进件日期（月）大于1个月
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030105Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030105);
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
        HitRule hitRule = checkRule(report, contactList, context.getApplyInfo().getApplyTime());
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
     * 直亲的最近一次通话时间（月）距进件日期（月）大于1个月
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<CustContactVO> contactList, String applyTime) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        boolean flag = true;
        // 执行规则逻辑
        if (report.getCollectionContact() != null) {
            // 获取紧急联系人手机号码
            A:
            for (CustContactVO custContactVO : contactList) {
                // 与本人关系: 1父母，2配偶
                if (custContactVO.getRelationship() == 1 || custContactVO.getRelationship() == 2) {
                    List<CollectionContact> collectionContact = report.getCollectionContact();
                    for (CollectionContact contact : collectionContact) {
                        List<ContactDetails> contactDetails = contact.getContactDetails();
                        for (ContactDetails contactDetail : contactDetails) {
                            if (custContactVO.getMobile().equals(contactDetail.getPhoneNum()) && StringUtils.isNotBlank(contact.getBeginDate())) {
                                int num = DateUtils.daysBetween(DateUtils.parse(contact.getEndDate(), "yyyy-MM-dd"), DateUtils.parse(applyTime, "yyyy-MM-dd"));
                                if (num > 30) {
                                    setHitNum(1);
                                    remark = String.format("直亲的最近一次通话时间（月）距进件日期（月）大于1个月，最近一次通话时间：%s，进件时间：%s", contact.getEndDate(), applyTime);
                                    hitRule.setRemark(remark);
                                    break A;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            setHitNum(1);
            remark = "聚信立分析报告数据中";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
