package com.rongdu.loans.risk.executor.xjbk;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 百融-多次申请核查月度版
 * @author sunda
 * @version 2017-08-14
 */
public class R1011Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1011);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		Map<String,String> vo = getDataInvokeService().getCredit100ApplyLoanMon(context);
		//直接命中百融的风险清单
		//Map<String,String> thirdpartRiskList = (Map<String,String>)vo;
		//自定义的规则集合
		Map<String,RiskRule> riskConfigMap = getDataInvokeService().getRiskRuleMap(getRuleId(),context.getModelId());
		Map<String,String> extParams = getDataInvokeService().getBaishiqiExtParams(context);
		//自定义的规则命中百融的风险清单
		List<HitRule> hitRuleList = checkRiskList(vo,riskConfigMap,extParams);

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
	 * 对命中的百融规则进行汇总
	 * @param riskMap
	 * @param riskConfigMap
	 * @return
	 */
	private List<HitRule> checkRiskList(Map<String, String> riskMap, Map<String,RiskRule> riskConfigMap,Map<String,String> extParams) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		boolean hit = false;
		for(Map.Entry<String,RiskRule> config:riskConfigMap.entrySet()){
			int num = 0;
			riskRule = config.getValue();
			for (Map.Entry<String,String> entry:riskMap.entrySet()){
				if (StringUtils.startsWith(entry.getKey(),config.getKey())){
					if (StringUtils.isNumeric(entry.getValue())){
						num = num+Integer.parseInt(entry.getValue());
					}
				}
			}
//			if (num>Integer.valueOf(config.getValue().getThreshold())){
			if (num>0){
				hit = true;
				addHitNum(1);
				hitRule = createHitRule(riskRule);
				hitRule.setValue(String.valueOf(num));
				hitRule.setRemark(String.valueOf(num));
				list.add(hitRule);
			}else{
				hit = false;
			}
			// 无论是否命中规则，都需要设置白骑士规则引擎自定义字段的数值
			String fieldName = riskRule.getFieldName();
			if (StringUtils.isNotBlank(fieldName)){
				extParams.put(fieldName,String.valueOf(num));
			}
			logger.info("执行规则-【{}-{}】：{},是否命中：{},决策依据：{}",getRuleId(),getRuleName(),riskRule.getRuleName(),hit,num);
		}
		return list;
	}

}
