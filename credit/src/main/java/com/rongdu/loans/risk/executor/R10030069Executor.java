package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10030069Executor.java  
* @Package com.rongdu.loans.risk.executor  
* @Description: 该身份证绑定其他手机号有未完成订单
* @author: yuanxianchu  
* @date 2018年10月30日  
* @version V1.0  
*/
public class R10030069Executor extends Executor {
	
	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkRule(context);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), hitNum, evidence);

	}
	
	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		int num = 0;
		CustUserVO custUserVO = context.getUser();
		if (custUserVO != null) {
			LoanApplyService loanApplyService = SpringContextHolder.getBean("loanApplyService");
			num = loanApplyService.countUnFinishByMobileAndIdNo(custUserVO.getIdNo(), custUserVO.getMobile());
		}
		if (num > 0) {
			setHitNum(1);
		}
		hitRule.setRemark("该身份证绑定其他手机号有未完成订单");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030069);
	}

}
