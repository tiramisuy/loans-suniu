package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 呼入呼出第一名,联系次数都小于30拒绝
 * 
 * 
 * @version 2019-02-11
 */
public class R10030071Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030071);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		IntoOrder vo = null;
		try {
			vo = getDataInvokeService().getjdqBase(context);
		} catch (Exception e) {
			logger.error("JDQ基本信息查询异常", e);
		}
		if (vo == null) {
			return;
		}

		// 命中的规则
		HitRule hitRule = check(vo.getMoxieTelecomReport().call_contact_detail);
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
	 * 呼入呼出第一名,联系次数都小于30拒绝
	 * 
	 * @return
	 */
	private HitRule check(List<MoxieTelecomReport.CallContactDetailBean> callContactDetailBeans){
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 30;
		int count = 0;
		int terminatingCallCount = 0;// 呼入次数
		int originatingCallCount = 0;// 呼出次数
		for (MoxieTelecomReport.CallContactDetailBean contactDetailBean : callContactDetailBeans){
			if(contactDetailBean.dial_cnt_6m > terminatingCallCount){
				terminatingCallCount = contactDetailBean.dial_cnt_6m;
			}
			if(contactDetailBean.dialed_cnt_6m > originatingCallCount){
				originatingCallCount = contactDetailBean.dialed_cnt_6m;
			}
		}
		if(terminatingCallCount < threshold && originatingCallCount < threshold){
			setHitNum(1);
		}
//		for (ReportMnoCcm c : terminatingCallList) {
//			terminatingCallCount = c.getTerminatingCallCount();
//			if (terminatingCallCount < threshold) {
//				count++;
//			}
//		}
//		for (ReportMnoCcm c : originatingCallList) {
//			originatingCallCount = c.getOriginatingCallCount();
//			if (originatingCallCount < threshold) {
//				count++;
//			}
//		}
//		if (count >= 2) {
//
//		}
		String msg = String.format("呼入次数：%s，呼出次数：%s", terminatingCallCount, originatingCallCount);
		hitRule.setRemark(msg);
		return hitRule;
	}

}