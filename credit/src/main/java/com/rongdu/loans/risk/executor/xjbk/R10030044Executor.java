package com.rongdu.loans.risk.executor.xjbk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rongdu.loans.loan.option.xjbk.BehaviorCheck;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 
* @Description:  互通电话号码较低
 * 数据来源于：API
* @author: 饶文彪
* @date 2018年6月27日 下午4:08:19
 */
public class R10030044Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030044);
	}

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

	/**
	 * 判断是否互通电话号码较低
	 * @param context
	 * @return
	 */
	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		XianJinBaiKaCommonOP xianJinBaiKaBase = getDataInvokeService().getXianJinBaiKaBase(context);

		try {
			int num = -1;
			for (BehaviorCheck behaviorCheck : xianJinBaiKaBase.getUser_verify().getOperatorReportVerify()
					.getBehaviorCheck()) {
				if ("contact_each_other".equals(behaviorCheck.getCheckPoint())) {// 互通过电话的号码数量
					//正则表达式匹配：“互通过电话的号码有*个，比例为*%”
					String evidence = behaviorCheck.getEvidence();
					String regex = "\\d+";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(evidence);
					if (matcher.find()) {
						num = Integer.valueOf(matcher.group(1));
					}
					if(num > -1 && num < 12) {
						setHitNum(1);
					}
					break;
				}
			}
			hitRule.setRemark(String.format("互通过电话的号码数量：%s个", num));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return hitRule;
	}

}
