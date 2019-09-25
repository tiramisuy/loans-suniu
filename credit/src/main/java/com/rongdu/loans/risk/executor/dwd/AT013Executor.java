package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;
import java.util.List;

/**
 * 规则名称：入网时长
 */
public class AT013Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();

        //命中的规则
        HitRule hitRule = checkRule(report);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum>0){
            addHitRule(context,hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
    }

    /**
     * 手机号码入网时长大于等于24个月
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;

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
                    }else if(StringUtils.isEmpty(check.getCheckPoints().getReg_time())){
                        monthInterval = 25 ;
                    }
                }
            }

            if (monthInterval < 24) {
                setHitNum(1);
                remark = String.format("入网时长小于24个月，入网时长：%s", monthInterval);
                hitRule.setRemark(remark);
            }
        } else {
            setHitNum(1);
            remark = "聚信立报告号码通讯详情为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT013);
    }
}
