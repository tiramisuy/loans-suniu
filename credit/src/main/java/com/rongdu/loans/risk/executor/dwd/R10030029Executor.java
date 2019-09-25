package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 上笔借款未结清，数据来源于：贷后数据
 *
 * @version 2018-03-26
 */
public class R10030029Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030029);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
        // 命中的规则
        HitRule hitRule = checkUserOrder(creditDataInvokeService,context);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), evidence);
    }

    /**
     * 有未完结订单
     *
     * @return
     */
    private HitRule checkUserOrder(CreditDataInvokeService creditDataInvokeService, AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        CustUserVO userVo = creditDataInvokeService.getUserById(context.getUserId());
        int count = creditDataInvokeService.countUnFinishApplyByMobile(userVo.getMobile());
        if (count > 1) {
            setHitNum(1);
            String msg = String.format("用户有未完结订单：%s,%s", userVo.getRealName(), userVo.getMobile());
            hitRule.setRemark(msg);
        }
        return hitRule;
    }

}
