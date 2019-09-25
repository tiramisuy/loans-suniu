package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.zhicheng.message.LoanRecord;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 宜信致诚-阿福共享平台 规则：在阿福共享平台存在3笔及以上贷款当前逾期，拒绝 数据来源于：宜信阿福共享平台
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030039Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030039);
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
	 * 统计申请人在阿福共享平台存在贷款逾期行为的数量 对逾期的定义如下：
	 * 审批结果为202且还款状态为302,审批结果字段为approvalStatusCode；还款状态字段为loanStatusCode，
	 * 逾期笔数为>=3，审批结果为202(批贷已放款)且还款状态为302（逾期）,
	 * 
	 * @param loanRecords
	 * @return
	 */
	private HitRule checkOverdueLoanNum(List<LoanRecord> loanRecords) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		String approveStatusCode = "202";
		String repayStatusCode = "302";
		List<String> hitLoanRecords = new ArrayList<>();
		if (loanRecords != null) {
			for (LoanRecord item : loanRecords) {
				if (approveStatusCode.equals(item.getApprovalStatusCode())
						&& repayStatusCode.equals(item.getLoanStatusCode())) {
					count = count + 1;
					String msg = String.format("贷款时间：%s,逾期金额：%s,逾期类型：%s",
							item.getLoanDate(), item.getOverdueAmount(),
							item.getOverdueStatus());
					hitLoanRecords.add(msg);
				}
			}
		}
		if (count >= 3) {
			setHitNum(1);
		}
		String msg = String.format("逾期笔数：%s,详情如下：%s", count,
				JsonMapper.toJsonString(hitLoanRecords));
		hitRule.setRemark(msg);
		return hitRule;
	}

}