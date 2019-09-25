package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.dwd.charge.Transaction;
import com.rongdu.loans.loan.option.jdq.Transactions;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**  
* @Title: R10030061Executor.java  
* @Package com.rongdu.loans.risk.executor.xjbk  
* @Description: 运营商账单<4个月  
* @author: yuanxianchu  
* @date 2018年10月10日  
* @version V1.0  
*/
public class R10030061Executor extends Executor {

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
		DWDReport op = getDataInvokeService().getdwdReport(context);
		//账单信息
		List<Transaction> transactions = op.getTransactions();
		int num = -1;
		if (transactions == null || transactions.size() < 4) {
			num = transactions == null ? 0 : transactions.size();
			setHitNum(1);
		}
		hitRule.setRemark(String.format("运营商账单：%s个月 ", num));
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030061);
	}

}
