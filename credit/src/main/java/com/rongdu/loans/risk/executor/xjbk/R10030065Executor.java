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
* @Title: R10030065Executor.java  
* @Package com.rongdu.loans.risk.executor.xjbk  
* @Description: 贷款类通话次数>=22  
* @author: yuanxianchu  
* @date 2018年10月10日  
* @version V1.0  
*/
public class R10030065Executor extends Executor {

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
			if ("contact_loan".equals(behaviorCheck.getCheckPoint())) {
				// 正则表达式匹配：“联系列表：[**]主叫*次共*分钟；被叫*次共*分钟”，提取出主叫次数和被叫次数
				String result = behaviorCheck.getResult();
				String regex = "主叫(\\d+)次.+?被叫(\\d+)次";
				int callInNum = 0;// 贷款类号码主叫
				int callOutNum = 0;// 贷款类号码被叫
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(result);
				while (matcher.find()) {
					callInNum += Integer.valueOf(matcher.group(1));
					callOutNum += Integer.valueOf(matcher.group(2));
				}
				num = callInNum + callOutNum;
				if (num >= 22) {
					setHitNum(1);
				}
				break;
			}
		}
		hitRule.setRemark(String.format("贷款类通话次数：%s次 ", num));
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030065);
	}

}
