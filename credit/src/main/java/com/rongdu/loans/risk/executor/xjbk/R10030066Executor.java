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
* @Title: R10030066Executor.java  
* @Package com.rongdu.loans.risk.executor.xjbk  
* @Description: 110通话次数 
* @author: yuanxianchu  
* @date 2018年10月10日  
* @version V1.0  
*/
public class R10030066Executor extends Executor {

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
		// 加载风险分析数据
		XianJinBaiKaCommonOP op = getDataInvokeService().getXianJinBaiKaBase(context);
		int num = -1;
		for (BehaviorCheck behaviorCheck : op.getUser_verify().getOperatorReportVerify().getBehaviorCheck()) {
			if ("contact_110".equals(behaviorCheck.getCheckPoint())) {
				//正则表达式匹配：“与110号码通话*次，共*分钟”，提取出通话次数
				String result = behaviorCheck.getResult();
				String regex = "通话(\\d+)次";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(result);
				if (matcher.find()) {
					num = Integer.valueOf(matcher.group(1));
				}
				if (num > 0) {
					setHitNum(1);
				}
				break;
			}
		}
		hitRule.setRemark(String.format("110通话次数：%s次 ", num));
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030066);
	}

}
