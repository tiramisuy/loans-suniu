package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.report.*;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;
import java.util.List;

/**
 * 入网时长小于60个月且与直亲近6个月通话次数小于180次，与直亲的平均通话时长小于60秒
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030104Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030104);
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
     * 入网时长小于60个月且与直亲近6个月通话次数小于180次，与直亲的平均通话时长小于60秒
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report, List<CustContactVO> contactList, String applyTime) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        boolean flag = true;
        if (report.getContactList() != null) {
            // 入网时长
            Integer monthInterval = 0;
            List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
            if (null != applicationCheck) {
                for (ApplicationCheck check : applicationCheck) {
                    if ("cell_phone".equals(check.getAppPoint()) && StringUtils.isNotBlank(check.getCheckPoints().getReg_time())) {
                        String reg_time = check.getCheckPoints().getReg_time();
                        monthInterval = DateUtils.getMonth(DateUtils.parse(reg_time), new Date());
                        break;
                    }
                }
            }
            A:
            for (CustContactVO custContactVO : contactList) {
                // 与本人关系: 1父母，2配偶
                if (custContactVO.getRelationship() == 1 || custContactVO.getRelationship() == 2) {
                    List<ContactList> contactAllList = report.getContactList();
                    for (ContactList contact : contactAllList) {
                        if (custContactVO.getMobile().equals(contact.getPhoneNum())) {
                            double v = contact.getCallLen() * 60 / contact.getCallCnt();
                            if (monthInterval < 60 && contact.getCallCnt() < 180 && v < Double.valueOf(60)) {
                                setHitNum(1);
                                remark = String.format("入网时长小于60个月且与直亲近6个月通话次数小于180次，与直亲的平均通话时长小于60秒，入网时长：%s，通话次数：%s，平均通话时长：%s", monthInterval, contact.getCallCnt(), v);
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
