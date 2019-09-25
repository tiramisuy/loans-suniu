package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 资金饥渴急需贷款，或者贷款逾期
 * 数据来源于：借点钱分析报告
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030013Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030013);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        //加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
        List<MoxieTelecomReport.BehaviorCheckBean> behaviorList = creditDataInvokeService.getjdqBase(context).getMoxieTelecomReport().getBehavior_check();
        //命中的规则
        HitRule hitRule = checkLoanConnectInfo(behaviorList);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(), getRuleName(), context.getUserName(), context.getApplyId(), getHitNum(), evidence);
    }

    /**
     * 运营商贷款类号码通话使用情况＞20
     *
     * @param crossValidation
     * @return
     */
    private HitRule checkLoanConnectInfo(List<MoxieTelecomReport.BehaviorCheckBean> behaviorList) {
        HitRule hitRule = createHitRule(getRiskRule());
        Integer num = null;
        String evidence = null;
        if (null != behaviorList && behaviorList.size() > 0){
            for (MoxieTelecomReport.BehaviorCheckBean behaviorCheckBean : behaviorList) {
                if ("contact_loan".equals(behaviorCheckBean.check_point)){
                    evidence = behaviorCheckBean.evidence;
                    if (StringUtils.isNotBlank(evidence)){
                        // 贷款类主叫次数
                        String callNum = evidence.substring(evidence.indexOf("[总计]主叫") + 6, evidence.indexOf("[总计]主叫") + 7);
                        // 贷款类被叫次数
                        String beCallNum = evidence.substring(evidence.indexOf("被叫") + 2, evidence.indexOf("被叫") + 3);
                        // 总通话次数
                        num = Integer.valueOf(callNum) + Integer.valueOf(beCallNum);
                        break;
                    }
                }
            }
        }
        if (num != null && num > 20) {
            setHitNum(1);
        }
        hitRule.setRemark(evidence);
        return hitRule;
    }

}
