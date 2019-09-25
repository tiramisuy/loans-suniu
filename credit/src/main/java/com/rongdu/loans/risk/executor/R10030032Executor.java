package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 芝麻账户不存在
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030032Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030032);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		AuthorizeResultVO vo = creditDataInvokeService.getZmAuthorizeResult(context);

		//命中的规则
		HitRule hitRule = checkZmAuthorizeResult(vo);
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
	 * 未开通芝麻账户
	 * @param vo
	 * @return
     */
	private HitRule checkZmAuthorizeResult(AuthorizeResultVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (StringUtils.isBlank(vo.getOpenId())){
			setHitNum(1);
		}
		hitRule.setRemark(vo.getMsg());
		return  hitRule;
	}


}
