package com.rongdu.loans.risk.executor;

import java.util.List;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 呼入呼出第一名,联系次数都小于30拒绝
 * 
 * @author liuzhuang
 * @version 2019-02-11
 */
public class R10030071Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030071);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<ReportMnoCcm> ccmListSource1 = vo.getData().getMnoCommonlyConnectMobiles();
		List<ReportMnoCcm> ccmListSource2 = null;
		if (ccmListSource1 != null) {
			ccmListSource2 = BeanMapper.mapList(ccmListSource1, ReportMnoCcm.class);
		}
		// 呼入（被叫）
		List<ReportMnoCcm> terminatingCallList = AutoApproveUtils.getTopCcmTerminatingCallList(ccmListSource1, 1);
		// 呼出（主叫）
		List<ReportMnoCcm> originatingCallList = AutoApproveUtils.getTopCcmOriginatingCallList(ccmListSource2, 1);

		// 命中的规则
		HitRule hitRule = check(terminatingCallList, originatingCallList);
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
	private HitRule check(List<ReportMnoCcm> terminatingCallList, List<ReportMnoCcm> originatingCallList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 30;
		int count = 0;
		int terminatingCallCount = 0;// 呼入次数
		int originatingCallCount = 0;// 呼出次数
		for (ReportMnoCcm c : terminatingCallList) {
			terminatingCallCount = c.getTerminatingCallCount();
			if (terminatingCallCount < threshold) {
				count++;
			}
		}
		for (ReportMnoCcm c : originatingCallList) {
			originatingCallCount = c.getOriginatingCallCount();
			if (originatingCallCount < threshold) {
				count++;
			}
		}
		if (count >= 2) {
			setHitNum(1);
		}
		String msg = String.format("呼入次数：%s，呼出次数：%s", terminatingCallCount, originatingCallCount);
		hitRule.setRemark(msg);
		return hitRule;
	}

}