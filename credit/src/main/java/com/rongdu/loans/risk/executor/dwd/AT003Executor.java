package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.zhicheng.vo.FraudScreenVO;

/**
 * 规则名称：宜信欺诈评分为1-14分，欺诈等级为一级
 */
public class AT003Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        FraudScreenVO fraudScreenVO = getDataInvokeService().getZhiChengFraudScreen(context);

        int hitNum = 0;
        StringBuilder evidence = new StringBuilder();
        RiskRule riskRule = getRiskRule();
        HitRule hitRule = createHitRule(riskRule);
        // 执行规则 取反集
        if (null != fraudScreenVO && null != fraudScreenVO.getData()){
            if (!StringUtils.equals(fraudScreenVO.getData().getFraudLevel(), "1")){
                addHitNum(1);
                evidence.append("该客户宜信阿福欺诈等级结果为:").append(fraudScreenVO.getData().getFraudLevel());
            }
            int fraudScore = StringUtils.isNotBlank(fraudScreenVO.getData().getFraudScore()) ? Integer.valueOf(fraudScreenVO.getData().getFraudScore()) : 0;
            if (fraudScore > 14){
                addHitNum(1);
                evidence.append("该客户宜信阿福欺诈分数结果为:").append(fraudScore);
            }
        } else {
            addHitNum(1);
            evidence.append("该客户未返回宜信阿福欺诈等级结果");
        }
        if(getHitNum() > 0){
            addHitRule(context, hitRule);
            hitRule.setRemark(evidence.toString());
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);
    }


    @Override
    public void init() {
        super.setRuleId(RuleIds.AT003);
    }
}
