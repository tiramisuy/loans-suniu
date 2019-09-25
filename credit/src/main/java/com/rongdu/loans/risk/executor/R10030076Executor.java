package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * @version V1.0
 * @Description: 紧急联系人近一个月关联其他申请人较多
 * @author: yuanxianchu
 * @date 2018年10月11日
 */
public class R10030076Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 命中的规则
        HitRule hitRule = checkRule(context);
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
     * 申请人所填写的紧急联系人手机号近1个月出现在他人紧急联系人中的次数
     *
     * @param context
     * @return
     */
    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        // 加载风险分析数据

        int count = getDataInvokeService().countContract(context, "month", 1);
        if (count >= 4) {
            setHitNum(1);
        }
        String msg = String.format("紧急联系人手机号近1个月出现在他人紧急联系人中的次数：%s", count);
        hitRule.setRemark(msg);
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030076);
    }

}
