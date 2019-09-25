package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.zhicheng.message.EchoQueryApiResponseData;
import com.rongdu.loans.zhicheng.message.LoanRecord;
import com.rongdu.loans.zhicheng.message.ResponseRiskItem;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 宜信致诚-阿福共享平台 如果命中阿福共享平台的风险名单，则根据风险等级进行处置 数据来源于：宜信阿福共享平台
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R1005Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R1005);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditInfoVO vo = getDataInvokeService().getZhichengCreditInfo(context);
        if (null == vo || null == vo.getParams()){
            return;
        }
        // 命中的第三方风险规则
        EchoQueryApiResponseData echoQueryApiResponseData = vo.getParams().getData();
        // 当前规则集下所对应的规则
        Map<String, RiskRule> riskRuleMap = getDataInvokeService()
                .getRiskRuleMap(getRuleId(), context.getModelId());


        // 命中的风险规则
        List<HitRule> hitRuleList = checkRiskList(riskRuleMap, echoQueryApiResponseData);
		// 决策依据
        String evidence = hitRuleList.stream().map(HitRule::getRuleCode).collect(Collectors.joining(","));
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRules(context, hitRuleList);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
                getRuleName(), context.getUserName(), context.getApplyId(),
                getHitNum(), evidence);
    }

    /**
     * 按照风险规则代码，来获取命中的规则
     * riskTypeCode[10:丧失还款能力类 11：伪冒类，12：资料虚假类，13：用途虚假类，19：其它]
     *
     * @param riskRuleMap
     * @param echoQueryApiResponseData
     * @return
     */
    private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap,
                                        EchoQueryApiResponseData echoQueryApiResponseData) {
        List<HitRule> list = new ArrayList<>();
        HitRule hitRule = null;
        RiskRule riskRuleType = null;
        RiskRule riskRuleDetail = null;
        RiskRule riskRuleLoan = null;
        if (echoQueryApiResponseData != null) {
            List<ResponseRiskItem> riskList = echoQueryApiResponseData.getRiskResults();
            if (null!=riskList && riskList.size() >0 ) {
                for (ResponseRiskItem item : riskList) {
                    //
                    logger.info("命中风险项：{}",item.toString());
                    if ("10".equals(item.getRiskTypeCode())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050006)) {
                            riskRuleType = riskRuleMap.get(RuleIds.R10050006);
                        }
                    } else if ("11".equals(item.getRiskTypeCode())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050007)) {
                            riskRuleType = riskRuleMap.get(RuleIds.R10050007);
                        }
                    } else if ("12".equals(item.getRiskTypeCode())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050008)) {
                            riskRuleType = riskRuleMap.get(RuleIds.R10050008);
                        }
                    } else if ("13".equals(item.getRiskTypeCode())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050009)) {
                            riskRuleType = riskRuleMap.get(RuleIds.R10050009);
                        }
                    } else if ("19".equals(item.getRiskTypeCode())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050010)) {
                            riskRuleType = riskRuleMap.get(RuleIds.R10050010);
                        }
                    }


                    if ("长期拖欠".equals(item.getRiskDetail())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050002)) {
                            riskRuleDetail = riskRuleMap.get(RuleIds.R10050002);
                        }
                    } else if ("丧失还款能力".equals(item.getRiskDetail())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050003)) {
                            riskRuleDetail = riskRuleMap.get(RuleIds.R10050003);
                        }
                    } else if ("法院-失信".equals(item.getRiskDetail())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050004)) {
                            riskRuleDetail = riskRuleMap.get(RuleIds.R10050004);
                        }
                    } else if ("法院-被执行".equals(item.getRiskDetail())) {
                        if (riskRuleMap.containsKey(RuleIds.R10050005)) {
                            riskRuleDetail = riskRuleMap.get(RuleIds.R10050005);
                        }
                    }

                    if (riskRuleType != null) {
                        hitRule = createHitRule(riskRuleType);
                        hitRule.setRuleCode(riskRuleType.getRuleCode());
                        hitRule.setRemark(item.getRiskDetail());
                        list.add(hitRule);
                        addHitNum(1);
                    }
                    if (riskRuleDetail != null) {
                        hitRule = createHitRule(riskRuleDetail);
                        hitRule.setRuleCode(riskRuleDetail.getRuleCode());
                        hitRule.setRemark(item.getRiskDetail());
                        list.add(hitRule);
                        addHitNum(1);
                    }
                }
            }
            List<LoanRecord> loanRecords = echoQueryApiResponseData.getLoanRecords();

            if (null!=loanRecords && loanRecords.size() >0 ) {
                if (riskRuleMap.containsKey(RuleIds.R10050001)) {
                    for (LoanRecord item : loanRecords) {
                        if ("202".equals(item.getApprovalStatusCode()) && "302".equals(item.getLoanStatusCode())) {
                            riskRuleLoan = riskRuleMap.get(RuleIds.R10050001);
                            break;
                        }
                    }
                }
                if (riskRuleLoan != null) {
                    hitRule = createHitRule(riskRuleLoan);
                    hitRule.setRuleCode(riskRuleLoan.getRuleCode());
                    hitRule.setRemark("借款记录审批结果为批贷已放款且还款状态为逾期");
                    list.add(hitRule);
                    addHitNum(1);
                }
            }
//
        }
        return list;
    }

//    private HitRule createHitRule(ResponseRiskItem item) {
//        HitRule hitRule = new HitRule();
//        hitRule.setSource(getRiskRule().getSource());
//        hitRule.setParentRuleId(getRuleId());
//        hitRule.setRuleCode(item.getRiskTypeCode());
//        hitRule.setRuleName(item.getRiskDetail());
//        hitRule.setRemark(item.getRiskDetail());
//        return hitRule;
//    }

}
