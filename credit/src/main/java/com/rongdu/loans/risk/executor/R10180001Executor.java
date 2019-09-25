package com.rongdu.loans.risk.executor;

import java.math.BigDecimal;
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
 * 魔蝎-逾期未还金额较多
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10180001Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10180001);
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
	 * 逾期未还金额大于500元
	 * 
	 * @return
	 */
	private HitRule checkReport(CreditcardReportVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null) {
			BigDecimal threshold = new BigDecimal(500);
			List<BigDecimal> overdueAmtList = new ArrayList<BigDecimal>();
			if (vo.getType() == 1) {
				List<EmailReportVO> list = (List<EmailReportVO>) vo.getReportData();
				if (list != null && !list.isEmpty()) {
					for (EmailReportVO data : list) {
						BigDecimal overdueAmt = new BigDecimal(data.getOverdue_information().getTotal_overdue_amount());
						overdueAmtList.add(overdueAmt);
					}
				}
			} else {
				BankReportVO data = (BankReportVO) vo.getReportData();
				if (data != null) {
					BigDecimal overdueAmt = new BigDecimal(data.getCreditcard().getOverdue_information()
							.getDelay_amount_1());
					overdueAmtList.add(overdueAmt);
				}
			}
			for (BigDecimal amt : overdueAmtList) {
				if (amt.compareTo(threshold) > 0) {
					setHitNum(1);
					String msg = String.format("未还金额：%s", amt);
					hitRule.setRemark(msg);
					break;
				}
			}
		}
		return hitRule;
	}
}
