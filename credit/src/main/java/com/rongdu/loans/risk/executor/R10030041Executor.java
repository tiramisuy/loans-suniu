package com.rongdu.loans.risk.executor;

import java.util.ArrayList;
import java.util.List;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.zhicheng.message.LoanRecord;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;

/**
 * 宜信致诚-阿福共享平台 规则：在阿福共享平台存在贷款历史逾期M3+，拒绝 数据来源于：宜信阿福共享平台
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030041Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030041);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder
				.getBean("creditDataInvokeService");
		CreditInfoVO vo = creditDataInvokeService
				.getZhichengCreditInfo(context);

		// 命中的规则
		HitRule hitRule = checkOverdueLoanNum(vo.getParams().getData()
				.getLoanRecords());
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				getHitNum(), evidence);
	}

	/**
	 * 借款记录里历史逾期M3+次数≥1；历史逾期M3+字段为overdueM3
	 * 
	 * @param loanRecords
	 * @return
	 */
	private HitRule checkOverdueLoanNum(List<LoanRecord> loanRecords) {
		HitRule hitRule = createHitRule(getRiskRule());
		List<String> hitLoanRecords = new ArrayList<>();
		int count = 0;
		if (loanRecords != null) {
			for (LoanRecord item : loanRecords) {
				int overdueM3 = StringUtils.isNotBlank(item.getOverdueM3()) ? Integer
						.parseInt(item.getOverdueM3()) : 0;
				if (overdueM3 >= 1) {
					count = count + 1;
					String msg = String
							.format("贷款时间：%s,贷款金额：%s,逾期M3+次数: %s",
									item.getLoanDate(), item.getLoanAmount(),
									overdueM3);
					hitLoanRecords.add(msg);
				}
			}
		}
		if (count > 0) {
			setHitNum(1);
		}
		String msg = String.format("历史逾期M3+笔数：%s,详情如下：%s", count,
				JsonMapper.toJsonString(hitLoanRecords));
		hitRule.setRemark(msg);
		return hitRule;
	}
}