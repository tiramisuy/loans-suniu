package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.message.Rule;
import com.rongdu.loans.baiqishi.message.Strategy;
import com.rongdu.loans.baiqishi.vo.DecisionVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 白骑士-反欺诈决策引擎服务
 * 如果命中白骑士欺诈决策引擎服务风险名单，则根据风险等级进行处置
 * 数据来源于：白骑士
 * @author sunda
 * @version 2017-08-14
 */
public class R1012Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1012);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		DecisionVO vo = getDataInvokeService().doBaishiqiDecision(context);
		context.put("decisionResult", vo.getFinalDecision());
		//直接命中白骑士的风险清单
		List<Rule> thirdpartRiskList = getRuleList(vo);
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
	 * @param thirdpartRiskList
	 * @return
	 */
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, List<Rule> thirdpartRiskList) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		for (Rule item:thirdpartRiskList){
			addHitNum(1);
			riskRule = riskRuleMap.get(item.getRuleId());
			if (riskRule!=null){
				hitRule = createHitRule(riskRule);
				hitRule.setRuleCode(item.getRuleId());
				hitRule.setRuleName(item.getRuleName());
				hitRule.setRemark(item.getMemo());
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

	private HitRule createHitRule(Rule item) {
		HitRule hitRule = new HitRule();
		hitRule.setSource(getRiskRule().getSource());
		hitRule.setParentRuleId(getRuleId());
		hitRule.setRuleCode(item.getRuleId());
		hitRule.setScore(item.getScore());
		hitRule.setRuleName(item.getRuleName());
		hitRule.setRemark(item.getMemo());
		hitRule.setDecision(item.getDecision());
		return hitRule;
	}

	/**
	 * 获取命中的风控规则列表
	 * @param vo
	 * @return
     */
	private List<Rule> getRuleList(DecisionVO vo) {
		List<Rule> list = new ArrayList<>();
		List<Strategy> strategySet = vo.getStrategySet();
		if (strategySet!=null){
			for (Strategy item:strategySet){
				if (item.getHitRules()!=null){
					list.addAll(item.getHitRules());
				}
			}
		}
		return  list;
	}

}
