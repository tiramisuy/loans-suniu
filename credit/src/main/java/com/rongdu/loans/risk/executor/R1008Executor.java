package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.tencent.vo.AntiFraudVO;
import com.rongdu.loans.tencent.vo.RiskDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 腾讯-反欺诈服务-风险名单
 * @author sunda
 * @version 2017-08-14
 */
public class R1008Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1008);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		AntiFraudVO vo = getDataInvokeService().doTencentAntifraud(context);
		//命中的第三方风险规则
		List<RiskDetail> thirdpartRiskList = vo.getRiskInfo();
		//当前规则集下所对应的规则
		Map<String, RiskRule> riskRuleMap = getDataInvokeService().getRiskRuleMap(getRuleId(),context.getModelId());
		//需要向白骑士上送的自定义规则字段
		Map<String,String> extParams = getDataInvokeService().getBaishiqiExtParams(context);
		//命中的风险规则
		List<HitRule> hitRuleList = checkRiskList(riskRuleMap,thirdpartRiskList,extParams);

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
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, List<RiskDetail> riskList, Map<String,String> extParams) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		if (riskList!=null){
			for (RiskDetail item:riskList){
				addHitNum(1);
				riskRule = riskRuleMap.get(item.getRiskCode());
				if (riskRule!=null){
					hitRule = createHitRule(riskRule);
					list.add(hitRule);
					// 如果命中规则，就设置白骑士规则引擎自定义字段的数值
					String fieldName = riskRule.getFieldName();
					if (StringUtils.isNotBlank(fieldName)){
						extParams.put(fieldName,String.valueOf(1));
					}
				}else{
					hitRule = createHitRule(item);
					list.add(hitRule);
				}
				logger.info("执行规则-【{}-{}】,命中规则：{},{}",
						getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRuleName());
			}
		}
		return list;
	}

	private HitRule createHitRule(RiskDetail item) {
		HitRule hitRule = new HitRule();
		hitRule.setSource(getRiskRule().getSource());
		hitRule.setParentRuleId(getRuleId());
		hitRule.setRuleCode(item.getRiskCode());
		return hitRule;
	}

}
