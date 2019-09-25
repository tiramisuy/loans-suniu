package com.rongdu.loans.risk.executor;

import java.util.ArrayList;
import java.util.List;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.credit.moxie.vo.CreditcardReportVO;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 魔蝎-近6月延滞期数较多
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10180002Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10180002);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditcardReportVO vo = null;
		try {
			ReportService reportService = SpringContextHolder.getBean(ReportService.class);
			vo = reportService.getMoxieCreditcardReport(context.getUserId());
		} catch (Exception e) {
			logger.error("魔蝎查询信用卡异常", e);
		}
		if (vo == null)
			return;
		// 命中的规则
		HitRule hitRule = checkReport(vo);
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
	 * 近6月延滞期数>=2
	 * 
	 * @return
	 */
	private HitRule checkReport(CreditcardReportVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null) {
			int threshold = 2;
			List<Integer> delayedPeriodsNumList = new ArrayList<Integer>();
			if (vo.getType() == 1) {
				List<EmailReportVO> list = (List<EmailReportVO>) vo.getReportData();
				if (list != null && !list.isEmpty()) {
					for (EmailReportVO data : list) {
						Integer n = Integer.parseInt(data.getOverdue_analyze_information().getMonths_ovderdue_l6m());
						delayedPeriodsNumList.add(n);
					}
				}
			} else {
				BankReportVO data = (BankReportVO) vo.getReportData();
				if (data != null) {
					Integer n = Integer.parseInt(data.getCreditcard().getOverdue_creditcard()
							.getDelayed_periods_num_6());
					delayedPeriodsNumList.add(n);
				}
			}
			for (Integer n : delayedPeriodsNumList) {
				if (n.intValue() >= threshold) {
					setHitNum(1);
					String msg = String.format("延滞期数：%s", n);
					hitRule.setRemark(msg);
					break;
				}
			}
		}
		return hitRule;
	}
}
