package com.rongdu.loans.risk.executor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoMui;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 申请人手机号月平均消费不合要求 数据来源于：白骑士资信云报告数据
 * 
 * @author liuzhuang
 * @version 2018-03-27
 */
public class R10030056Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030056);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<ReportMnoMui> muiList = vo.getData().getMnoMonthUsedInfos();
		// 白骑士可能反馈超过6个月的数据，我们只取近6个月的数据
		List<ReportMnoMui> topList = AutoApproveUtils.getTopList(muiList, 6);
		// 命中的规则
		HitRule hitRule = checkMoney(topList);
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
	 * 每月平均消费>350，或者<26
	 * 
	 * @param muiList
	 * @return
	 */
	private HitRule checkMoney(List<ReportMnoMui> muiList) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (muiList != null && muiList.size() > 0) {
			Map<String, String> extInfo = new LinkedHashMap<>();
			int n = 0;
			BigDecimal totalAmt = new BigDecimal(0);
			BigDecimal averageAmt = new BigDecimal(0);
			int totalOriginatingCallCount = 0;// 主叫次数
			for (ReportMnoMui item : muiList) {
				String month = item.getMonth();
				int originatingCallCount = item.getOriginatingCallCount() == null ? 0 : item.getOriginatingCallCount();
				// 当月账单爬取不到，忽略
				if (month != null && !month.equals(DateUtils.getDate("yyyy-MM"))) {
					n++;
					totalAmt = totalAmt.add(item.getCostMoney());
					totalOriginatingCallCount = totalOriginatingCallCount + originatingCallCount;
					extInfo.put(month, String.valueOf(item.getCostMoney()));
				}
			}
			// 主叫次数>0，消费金额<=0为月账单爬取失败情况，不做风控决策
			if (totalOriginatingCallCount > 0 && totalAmt.compareTo(new BigDecimal(0)) <= 0) {
				return hitRule;
			}
			if (n > 0) {
				averageAmt = totalAmt.divide(new BigDecimal(n), 2, BigDecimal.ROUND_HALF_UP);
			}
			if (averageAmt.compareTo(new BigDecimal(350)) > 0 || averageAmt.compareTo(new BigDecimal(26)) < 0) {
				setHitNum(1);
				String msg = String.format("月平均消费：%s，详情如下：%s", averageAmt, JsonMapper.toJsonString(extInfo));
				hitRule.setRemark(msg);
			}
		}
		return hitRule;
	}
}
