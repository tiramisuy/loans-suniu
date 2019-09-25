package com.rongdu.loans.risk.executor.rong360;

import java.util.ArrayList;
import java.util.List;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.rongTJreportv1.CallLog;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 短号联系过多 数据来源于：融360运营商报告
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030049Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030049);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		TianjiReportDetailResp rongTJReportDetail = getDataInvokeService().getRongTJReportDetail(context);
		List<CallLog> callLogs = rongTJReportDetail.getJson().getCallLog();
		// 近6个月通话记录top10
		List<CallLog> calInCntList = AutoApproveUtils.getRongTopCallInCntList(callLogs, 10);// 呼入
		List<CallLog> calOutCntList = AutoApproveUtils.getRongTopCallOutCntList(callLogs, 10);// 呼出

		// 命中的规则
		HitRule hitRule = checkShortNumber(calInCntList, calOutCntList);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), getHitNum(), evidence);
	}

	/**
	 * 近6个月通话记录Top10里电话号码位数小于8的个数>=3
	 * 
	 * @return
	 */
	private HitRule checkShortNumber(List<CallLog> calInCntList, List<CallLog> calOutCntList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 3;
		int callInCount = 0;
		int callOutCount = 0;
		List<String> callInList = new ArrayList<String>();
		List<String> callOutList = new ArrayList<String>();
		for (CallLog callInLog : calInCntList) {
			String phone = AutoApproveUtils.parseContactMobile(callInLog.getPhone());
			if (StringUtils.isNotBlank(phone) && phone.length() < 8) {
				callInCount++;
				callInList.add(phone);
			}
		}
		for (CallLog callOutLog : calOutCntList) {
			String phone = AutoApproveUtils.parseContactMobile(callOutLog.getPhone());
			if (StringUtils.isNotBlank(phone) && phone.length() < 8) {
				callOutCount++;
				callOutList.add(phone);
			}
		}
		if (callInCount >= threshold || callOutCount >= threshold) {
			setHitNum(1);
		}
		String msg = String.format("呼入短号数量：%s，呼出短号数量：%s", callInCount, callOutCount);
		hitRule.setRemark(msg);
		return hitRule;
	}

}