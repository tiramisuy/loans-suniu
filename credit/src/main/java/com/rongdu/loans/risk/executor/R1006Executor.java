package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;
import com.rongdu.loans.zhicheng.message.RequestRiskItem;
import com.rongdu.loans.zhicheng.vo.RiskListVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 宜信致诚-福网
 * 如果命中宜信致诚的福网风险名单，则根据风险等级进行处置
 * 数据来源于：福网
 * @author sunda
 * @version 2017-08-14
 */
public class R1006Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1006);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		RiskListVO vo = getDataInvokeService().getZhichengRiskList(context);
		//命中的第三方风险规则
		List<RequestRiskItem> thirdpartRiskList = vo.getData().getResultList();
		//当前规则集下所对应的规则
		Map<String, RiskRule> riskRuleMap = getDataInvokeService().getRiskRuleMap(getRuleId(),context.getModelId());
		//向白骑士上传的自定义规则字段
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
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, List<RequestRiskItem> riskList, Map<String,String> extParams) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		for (RequestRiskItem item:riskList){
			addHitNum(1);
			riskRule = riskRuleMap.get(item.getRiskDetailCode());
			if (riskRule!=null){
				hitRule = createHitRule(riskRule);
				hitRule.setRemark(item.getRiskDetail());
//				if (hitRule.getRemark() != null
//						&& hitRule.getRemark().indexOf("黑名单") != -1) {
//					hitRule.setRiskRank("A");
//				}else{
//					hitRule.setRiskRank("C");
//				}
				list.add(hitRule);
				// 命中规则，需要设置白骑士规则引擎自定义字段的数值
				String fieldName = getRiskRule().getFieldName();
				if (StringUtils.isNotBlank(fieldName)){
					extParams.put(fieldName,String.valueOf(1));
				}
			}else{
				hitRule = createHitRule(item);
				hitRule.setRemark(item.getRiskDetail());
				list.add(hitRule);
			}
			logger.info("执行规则-【{}-{}】,命中规则：{},{}",
					getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRemark());
		}
		return list;
	}

	private HitRule createHitRule(RequestRiskItem item) {
		HitRule hitRule = new HitRule();
		hitRule.setSource(getRiskRule().getSource());
		hitRule.setParentRuleId(getRuleId());
		hitRule.setRuleCode(item.getRiskDetailCode());
		hitRule.setRuleCode(item.getRiskDetail());
		return hitRule;
	}


}
