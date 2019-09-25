package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 活跃总间隔<=149
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/28
 */
public class R10190004Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBase(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(context, vo.getMoxieTelecomReport().call_contact_detail);
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

    private HitRule checkRule(AutoApproveContext context, List<MoxieTelecomReport.CallContactDetailBean> detailBeanList) {
        HitRule hitRule = createHitRule(getRiskRule());
        //最小的第一次通话时间
        long minFirstCallTime = 0;
        //最大的最后一次通话时间
        long maxLastCallTime = 0;
        String minFirstCallTimeStr = "";
        String maxLastCallTimeStr = "";
        for (MoxieTelecomReport.CallContactDetailBean ccdBean : detailBeanList) {
            //第一次通话时间
            long minTime = DateUtils.parse(ccdBean.trans_start, "yyyy-MM-dd HH:mm:ss").getTime();
            //获取最小的第一次通话时间
            if (minFirstCallTime == 0 || minTime < minFirstCallTime) {
                minFirstCallTime = minTime;
                minFirstCallTimeStr = ccdBean.trans_start;
            }

            //最大的最后一次通话时间
            long maxTime = DateUtils.parse(ccdBean.trans_end, "yyyy-MM-dd HH:mm:ss").getTime();
            //获取最大的最后一次通话时间
            if (maxTime > maxLastCallTime) {
                maxLastCallTime = maxTime;
                maxLastCallTimeStr = ccdBean.trans_end;
            }
        }

        //计算活跃天数,天数计算
        long activeTotalDays = (maxLastCallTime - minFirstCallTime) / (24 * 3600 * 1000);

        if (activeTotalDays <= 149) {
            setHitNum(1);
        }

        hitRule.setRemark(String.format("近6月,第一次通话时间%s,最后一次通话时间%s,活跃总间隔<=149,活跃总间隔：%s", minFirstCallTimeStr, maxLastCallTimeStr, activeTotalDays));
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190004);
    }

}
