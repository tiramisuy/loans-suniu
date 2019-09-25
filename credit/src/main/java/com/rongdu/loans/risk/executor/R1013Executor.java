package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.tongdun.vo.FraudApiVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 同盾-反欺诈决策引擎服务
 * 如果命中同盾欺诈决策引擎服务风险名单，则根据风险等级进行处置
 * 数据来源于：同盾
 * @author sunda
 * @version 2017-08-14
 */
public class R1013Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1013);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		FraudApiVO vo = getDataInvokeService().doTongdunAntifraud(context);
		//直接命中同盾的风险清单
		List<com.rongdu.loans.tongdun.vo.HitRule> thirdpartRiskList = vo.getHit_rules();
		//自定义的规则集合
		Map<String, RiskRule> riskRuleMap = getDataInvokeService().getRiskRuleMap(getRuleId(),context.getModelId());
		//命中的风险规则
		List<HitRule> hitRuleList = checkRiskList(riskRuleMap,thirdpartRiskList);

		//决策依据
		String evidence = JsonMapper.toJsonString(thirdpartRiskList);
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRules(context,hitRuleList);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
	}

	/**
	 *  按照风险规则代码，来获取命中的规则
	 * @param riskRuleMap
	 * @param riskList
	 * @return
	 */
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, List<com.rongdu.loans.tongdun.vo.HitRule> riskList) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		for (com.rongdu.loans.tongdun.vo.HitRule item:riskList){
			addHitNum(1);
			riskRule = riskRuleMap.get(item.getId());
			if (riskRule!=null){
				hitRule = createHitRule(riskRule);
				hitRule.setRuleCode(item.getId());
				hitRule.setRuleName(item.getName());
				hitRule.setScore(item.getScore());
				hitRule.setDecision(item.getDecision());
				list.add(hitRule);
			}else{
				hitRule = createHitRule(item);
				list.add(hitRule);
			}
			logger.info("执行规则-【{}-{}】,命中规则：{},{}",
					getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRuleName());
		}
		return list;
	}

	private HitRule createHitRule(com.rongdu.loans.tongdun.vo.HitRule item) {
		HitRule hitRule = new HitRule();
		hitRule.setSource(getRiskRule().getSource());
		hitRule.setParentRuleId(getRuleId());
		hitRule.setRuleCode(item.getId());
		hitRule.setRuleName(item.getName());
		hitRule.setScore(item.getScore());
		hitRule.setDecision(item.getDecision());
		return hitRule;
	}

	/**
	 * 按照风险规则代码，来行获取命中的规则
	 * @param riskList
	 * @param ruleCode
	 * @return
     */
	private List<com.rongdu.loans.tongdun.vo.HitRule> getHitRiskItems(List<com.rongdu.loans.tongdun.vo.HitRule> riskList, String ruleCode) {
		List<com.rongdu.loans.tongdun.vo.HitRule> list = new ArrayList<>();
		for (com.rongdu.loans.tongdun.vo.HitRule item :riskList){
			if (ruleCode.equals(item.getId())){
				list.add(item);
			}
		}
		return  list;
	}


}
