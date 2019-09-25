package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 百融-特殊名单核查-检查是否命中百融规则
 * @author sunda
 * @version 2017-08-14
 */
public class R1009Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1009);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		Map<String, String> vo = getDataInvokeService().getCredit100SpecialList(context);
		//命中的第三方风险规则
		//Map<String,String> thirdpartRiskList = (Map<String,String>)vo;
		//当前规则集下所对应的规则
		Map<String, RiskRule> riskRuleMap = getDataInvokeService().getRiskRuleMap(getRuleId(),context.getModelId());
		//命中的风险规则
		List<HitRule> hitRuleList = checkRiskList(riskRuleMap,vo);

		//决策依据
		String evidence = JsonMapper.toJsonString(vo);
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
	 * @param riskMap
	 * @return
	 */
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, Map<String,String> riskMap) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		for (String key:riskMap.keySet()){
			riskRule = riskRuleMap.get(key);
			if (riskRule!=null){
				addHitNum(1);
				hitRule = createHitRule(riskRule);
				hitRule.setValue(riskMap.get(key));
				list.add(hitRule);
				logger.info("执行规则-【{}-{}】,命中规则：{},{}",
						getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRuleName());
			}
		}
		return list;
	}

}
