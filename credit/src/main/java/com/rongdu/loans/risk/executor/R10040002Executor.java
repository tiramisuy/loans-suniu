package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 芝麻信用分不佳
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10040002Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10040002);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ZmScoreVO vo = getDataInvokeService().getZmScore(context);
		String zmScore = vo.getZmScore();

		HitRule hitRule =  checkZmScore(zmScore);
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
	 * 芝麻信用分小于550
	 * @param zmScoreString
	 * @return
     */
	private HitRule checkZmScore(String zmScoreString) {
		HitRule hitRule = createHitRule(getRiskRule());
		//通话次数
		int score = 0;
		int threshold = 550;
		if (StringUtils.isNumeric(zmScoreString)){
			score = Integer.parseInt(zmScoreString);
		}
		if (score<threshold){
			setHitNum(1);
		}
		hitRule.setValue(String.valueOf(score));
		String msg = String.format("芝麻信用分：%s",score);
		hitRule.setRemark(msg);
		return  hitRule;
	}


}
