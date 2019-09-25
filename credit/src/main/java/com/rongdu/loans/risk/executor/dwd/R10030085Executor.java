package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.report.ContactList;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 近6个月与紧急联系人通话次数大于等于600次，且通话时长小于等于180分钟
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030085Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030085);
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
     * 近6个月与紧急联系人通话次数大于等于600次，且通话时长小于等于180分钟
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<CustContactVO> contactList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (report.getContactList() != null) {
            // 获取紧急联系人手机号码
            List<ContactList> contactListReport = report.getContactList();
            A:
            for (CustContactVO custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getMobile())) {
                    for (ContactList contact : contactListReport) {
                        // 执行判断
                        if (custContactVO.getMobile().equals(contact.getPhoneNum())) {
                            if (contact.getCallCnt() >= 600 && contact.getCallLen() <= Double.valueOf(180)) {
                                setHitNum(1);
                                remark = String.format("近6个月与紧急联系人通话次数大于等于600次，且通话时长小于等于180分钟，紧急联系人号码：%s，近6个月通话次数：%s，近6个月通话时长：%s", custContactVO.getMobile(), contact.getCallCnt(), contact.getCallLen());
                                hitRule.setRemark(remark);
                                break A;
                            }
                        }
                    }
                }
            }
        } else {
            setHitNum(1);
            remark = "聚信立报告号码通讯详情为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
