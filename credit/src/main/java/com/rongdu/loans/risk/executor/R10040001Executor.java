package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;

/**
 * 宜信致诚-阿福共享平台
 * 规则：致诚信用分字段为zcCreditScore＜400，拒绝
 * @author sunda
 * @version 2017-08-14
 */
public class R10040001Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10040001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		CreditInfoVO vo = creditDataInvokeService.getZhichengCreditInfo(context);

		//命中的规则
		HitRule hitRule =  checkCreditScore(vo);
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
	 * 致诚信用分小于等于400
	 * @param vo
	 * @return
	 */
	private HitRule checkCreditScore(CreditInfoVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		int score = 0;
		String scoreString = vo.getParams().getData().getZcCreditScore();
		if (StringUtils.isNotBlank(scoreString)){
			score =	Integer.valueOf(scoreString);
			if (score<=400){
				setHitNum(1);
			}
		}
		hitRule.setValue(String.valueOf(score));
		String msg = String.format("致诚信用分：%s",scoreString);
		hitRule.setRemark(msg);
		return  hitRule;
	}



}