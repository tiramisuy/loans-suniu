package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * 申请人工作单位所在地不合要求
 * 数据来源于：用户基本数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030018Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030018);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		String workArea = AutoApproveUtils.getWorkArea(context.getUserInfo());

		//命中的规则
		HitRule hitRule = checkWorkArea(workArea);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
	}

	/**
	 * 申请人工作单位所在地不合要求
	 * @param area
	 * @return
     */
	private HitRule checkWorkArea(String area) {
		HitRule hitRule = createHitRule(getRiskRule());
		//申请受限的地区
		String[] array = {"西藏","新疆","宁德"};
		List<String> restrictedArea = Arrays.asList(array);
		if (AutoApproveUtils.containsAny(area,restrictedArea)){
			setHitNum(1);
		}
		hitRule.setValue(area);
		hitRule.setRemark(area);
		return  hitRule;
	}


}
