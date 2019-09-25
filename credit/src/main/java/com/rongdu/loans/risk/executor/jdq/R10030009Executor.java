package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 申请人所在地通话呼入次数比例较低 数据来源于：借点钱魔蝎报告
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030009Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030009);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 工作所在地
		String workArea = context.getUserInfo().getWorkAddr();
		if (StringUtils.isBlank(workArea)) {
			return;
		}
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		// 魔蝎分析报告
		IntoOrder intoOrder = creditDataInvokeService.getjdqBase(context);
		if (null != intoOrder && null != intoOrder.getMoxieTelecomReport()) {
			for (MoxieTelecomReport.ReportBean reportBean : intoOrder.getMoxieTelecomReport().report) {
				if ("source_name_zh".equals(reportBean.key) && "电信".equals(reportBean.value)) {
					return;
				}
			}
		}
		List<MoxieTelecomReport.ContactRegionBean> list = intoOrder.getMoxieTelecomReport().contact_region;
		// 命中的规则
		HitRule hitRule = checkCallInTimePercentage(workArea, list);
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
	 * 申请人所在地对应地区运营商通话呼入次数百分比＜30%
	 * 
	 * @param list
	 * @return
	 */
	private HitRule checkCallInTimePercentage(String workArea, List<MoxieTelecomReport.ContactRegionBean> list) {
		HitRule hitRule = createHitRule(getRiskRule());
		double minPercentage = 0.30D;
		double callOut = 0.00D;
		String msg = "";
		String city = null;
		// 获取工作城市 字段模板: 上海,上海市,杨浦区|政府路14号  取上海市
		if (StringUtils.isNotBlank(workArea)) {
			String substring = workArea.substring(0, workArea.indexOf("|"));
			if (StringUtils.isNotBlank(substring)) {
				String s = substring.split(",")[1];
				if (StringUtils.isNotBlank(s)) {
					city = AutoApproveUtils.filterArea(s);
				}
			}
		}
		if (list != null && StringUtils.isNotBlank(city)) {
			A:
			for (MoxieTelecomReport.ContactRegionBean item : list) {
				if ("contact_region_6m".equals(item)) {
					for (MoxieTelecomReport.ContactRegionBean.RegionListBean regionListBean : item.region_list) {
						if (StringUtils.contains(regionListBean.region_loc, workArea)) {
							callOut = regionListBean.region_dialed_cnt_pct;
						}
						if (callOut < minPercentage) {
							msg = String.format("工作地区%s vs：通话地区%s,呼入次数百分比：%s", workArea,
									regionListBean.region_loc, callOut);
							setHitNum(1);
							break A;
						}
					}
				}
			}

		} else {
			setHitNum(1);
			msg = "工作地区为空";
		}
		hitRule.setRemark(msg);
		return hitRule;
	}

}
