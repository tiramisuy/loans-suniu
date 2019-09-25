package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.baiqishi.entity.ReportHighRiskList;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 白骑士资信报告黑名单
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R1014Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R1014);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		Map<String,List<ReportHighRiskList>> thirdpartRiskList = vo.getData().getBqsHighRiskList();
		String name = context.getUser().getRealName();
		String idNo = context.getUser().getIdNo();
		//命中的规则
		List<HitRule> hitRules = checkRiskList(thirdpartRiskList);
		//决策依据
		String evidence = JsonMapper.toJsonString(thirdpartRiskList);
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRules(context,hitRules);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),hitNum,evidence);
	}


	/**
	 * 白骑士资信报告黑名单
	 * @param thirdpartRiskList
	 * @return
     */
	private List<HitRule> checkRiskList(Map<String,List<ReportHighRiskList>> thirdpartRiskList) {
		List<HitRule> list = new ArrayList<>();
		HitRule hitRule = null;
		RiskRule riskRule = getRiskRule();
		List<ReportHighRiskList> highRiskList = null;
		if(thirdpartRiskList!=null){
			for (Map.Entry<String,List<ReportHighRiskList>> entry:thirdpartRiskList.entrySet()){
				highRiskList = entry.getValue();
				for (ReportHighRiskList highRisk:highRiskList){
					addHitNum(1);
					hitRule = createHitRule(riskRule);
					hitRule.setRuleName(highRisk.getSecondType());
					hitRule.setRuleCode(entry.getKey());
					String msg = String.format("%s，%s，%s",highRisk.getRiskGrade(),highRisk.getRiskIdType(),highRisk.getFirstType());
					hitRule.setRemark(msg);
					if("中风险".equals(highRisk.getRiskGrade())){
						hitRule.setRiskRank("C");
					}
					list.add(hitRule);
					logger.info("执行规则-【{}-{}】,命中规则：{},{}",
							getRuleId(),getRuleName(),hitRule.getRuleCode(),hitRule.getRuleName());
				}
			}
		}
		return list;
	}


}
