package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.CollectionContact;
import com.rongdu.loans.loan.option.dwd.report.ContactDetails;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 申请人归属地与紧急联系人归属地不一致
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030077Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030077);
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
     * 申请人手机号码归属地与紧急联系人手机号码归属地不一致
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<CustContactVO> contactList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;

        // 获取申请人手机号码归属地
        String applyLoc = null;
        List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
        if (applicationCheck != null) {
            for (ApplicationCheck check : applicationCheck) {
                if ("cell_phone".equals(check.getAppPoint())) {
                    applyLoc = check.getCheckPoints().getWebsite();
                    break;
                }
            }
        }
        // 获取紧急联系人手机号码归属地
        List<CollectionContact> collectionContact = report.getCollectionContact();
        if (collectionContact != null) {
            A:
            for (CustContactVO custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getMobile())) {
                    for (CollectionContact contact : collectionContact) {
                        // 同一号码通话记录只取第一条
                        ContactDetails contactDetails = contact.getContactDetails().get(0);
                        if (custContactVO.getMobile().equals(contactDetails.getPhoneNum())) {
                            // 执行判断
                            if (!applyLoc.contains(contactDetails.getPhoneNumLoc())) {
                                setHitNum(1);
                                remark = String.format("申请人手机号码归属地与紧急联系人手机号码归属地不一致，申请人手机号码归属地：%s，紧急联系人手机号码归属地：%s", applyLoc, contactDetails.getPhoneNumLoc());
                                hitRule.setRemark(remark);
                                break A;
                            }
                        }
                    }
                }
            }
        }

        return hitRule;
    }

}
