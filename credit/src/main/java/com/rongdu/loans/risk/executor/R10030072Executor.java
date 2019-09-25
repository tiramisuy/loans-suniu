package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * @version V1.0
 * @Description: 申请人身份证签发机关城市不符
 * @author: yuanxianchu
 * @date 2018年10月11日
 */
public class R10030072Executor extends Executor {

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

    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        // 加载风险分析数据
        String idRegOrg = context.getUserInfo().getIdRegOrg();
        //福建宁德3522,福建龙岩3509,新疆,西藏,青海,甘肃
        String[] array = {"西藏","新疆","青海","甘肃","福建龙岩","福建宁德"};
        List<String> list1 = Arrays.asList(array);
        for (int i = 0; i < list1.size() ; i++) {
            if (idRegOrg.contains(list1.get(i))) {
                setHitNum(1);
                break;
            }
        }
        hitRule.setRemark(String.format("申请人身份证签发机关:%s",idRegOrg));
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030072);
    }

}
