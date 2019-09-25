package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.report.ApplicationCheck;
import com.rongdu.loans.loan.option.dwd.report.BehaviorCheck;
import com.rongdu.loans.loan.option.dwd.report.Report;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 申请人手机归属城市与申请人朋友圈主要活跃省份不一致
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030119Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030119);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        Report report = getDataInvokeService().getdwdChargeInfo(context).getReportInfo().getData().getReport();
        if (report == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(report);
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
     * 申请人手机归属城市与申请人朋友圈主要活跃省份不一致
     *
     * @param report
     * @return
     */
    private HitRule checkRule(Report report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        Boolean flag = true;

        // 获取申请人手机号码归属地
        String applyLoc = null;
        List<ApplicationCheck> applicationCheck = report.getApplicationCheck();
        if (applicationCheck != null && applicationCheck.size() > 0) {
            for (ApplicationCheck check : applicationCheck) {
                if ("cell_phone".equals(check.getAppPoint())) {
                    applyLoc = check.getCheckPoints().getWebsite();
                    break;
                }
            }
        }
        // 获取朋友圈主要活跃省份
        String friendLoc = null;
        List<BehaviorCheck> behaviorCheck = report.getBehaviorCheck();
        if (behaviorCheck != null && behaviorCheck.size() > 0) {
            for (BehaviorCheck check : behaviorCheck) {
                if ("regular_circle".equals(check.getCheckPoint())) {
                    friendLoc = StringUtils.substring(check.getResult(), check.getResult().indexOf("跃在") + 2, check.getResult().indexOf("地区"));
                    break;
                }
            }
        }
        if (StringUtils.isBlank(applyLoc) || (StringUtils.isNotBlank(applyLoc) && applyLoc.contains(friendLoc))) {
            setHitNum(1);
            remark = String.format("申请人手机归属城市与申请人朋友圈主要活跃省份不一致，手机号码归属地：%s，朋友圈主要活跃省份：%s", applyLoc, friendLoc);
            hitRule.setRemark(remark);
        }

        return hitRule;
    }

}
