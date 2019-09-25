package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListDetail;
import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 芝麻信用行业关注名单
 * 如果命中芝麻信用行业关注名单，则根据风险等级进行处置
 * 数据来源于：白骑士
 * @author sunda
 * @version 2017-08-14
 */
public class R1007Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1007);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ZmWatchListVO vo = getDataInvokeService().getZmWatchList(context);
		//命中的第三方风险规则
		List<ZmWatchListDetail>  thirdpartRiskList = vo.getDetails();
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
	private List<HitRule> checkRiskList(Map<String, RiskRule> riskRuleMap, List<ZmWatchListDetail> riskList, Map<String, String> extParams) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = null;
		if (riskList!=null){
			for (ZmWatchListDetail item:riskList){
				addHitNum(1);
				riskRule = riskRuleMap.get(item.getCode());
				if (riskRule!=null){
					hitRule = createHitRule(riskRule);
					hitRule.setRuleName(item.getCodeName());
					if(riskRule.getRuleCode().startsWith("AA") && !item.isSettlement()) {
						hitRule.setRiskRank("B");
					}
					list.add(hitRule);
					// 如果命中规则，就设置白骑士规则引擎自定义字段的数值
					String fieldName = riskRule.getFieldName();
					if (StringUtils.isNotBlank(fieldName)){
						extParams.put(fieldName,String.valueOf(1));
					}
					logger.info("执行规则-【{}-{}】,命中规则：{},{};结清状态：{}",
							getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRuleName(),item.isSettlement());
				}else{
					hitRule = createHitRule(item);
					list.add(hitRule);
				}
			}
		}
		return list;
	}

	private HitRule createHitRule(ZmWatchListDetail item) {
		HitRule hitRule = new HitRule();
		hitRule.setSource(getRiskRule().getSource());
		hitRule.setParentRuleId(getRuleId());
		hitRule.setRuleName(item.getCodeName());
		hitRule.setRuleCode(item.getCode());
		return hitRule;
	}

//	/**
//	 * 获取命中的风控规则列表
//	 * @param vo
//	 * @return
//     */
//	private List<Rule> getRuleList(DecisionVO vo) {
//		List<Rule> list = new ArrayList<>();
//		List<Strategy> strategySet = vo.getStrategySet();
//		for (Strategy item:strategySet){
//			if (item.getHitRules()!=null){
//				list.addAll(item.getHitRules());
//			}
//		}
//		return  list;
//	}
//
//	/**
//	 * 按照风险规则代码，来行获取命中的规则
//	 * @param riskList
//	 * @param ruleCode
//	 * @return
//     */
//	private List<Rule> getHitRiskItems(List<Rule> riskList, String ruleCode) {
//		List<Rule> list = new ArrayList<>();
//		for (Rule item :riskList){
//			if (ruleCode.equals(item.getRuleId())){
//				list.add(item);
//			}
//		}
//		return  list;
//	}


}
