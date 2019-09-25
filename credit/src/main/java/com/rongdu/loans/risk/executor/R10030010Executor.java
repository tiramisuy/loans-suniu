package com.rongdu.loans.risk.executor;

import com.rongdu.loans.baiqishi.entity.ReportMnoCca;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.credit.common.IkanalyzerUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 申请人所在地通话呼出次数比例较低 数据来源于：白骑士资信云报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030010Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030010);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		// 手机运营商为电信则不跑此规则
		if (vo != null && vo.getData() != null
				&& vo.getData().getMnoBaseInfo() != null) {
			if ("电信".equals(vo.getData().getMnoBaseInfo().getMonType())) {
				return;
			}
		}
		String workArea = AutoApproveUtils.getWorkArea(context.getUserInfo());
		List<ReportMnoCca> dataList = vo.getData().getMnoCommonlyConnectAreas();

		// 命中的规则
		HitRule hitRule = checkCallOutTimePercentage(workArea, dataList);
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
	 * 申请人所在地对应地区运营商通话呼出次数百分比＜30%
	 * 
	 * @param list
	 * @return
	 */
	private HitRule checkCallOutTimePercentage(String workArea,
			List<ReportMnoCca> list) {
		HitRule hitRule = createHitRule(getRiskRule());
		double minPercentage = 0.30D;
		double similarity = 0.00D;
		String msg = "";
		if (list != null && workArea != null) {
			// workArea = AutoApproveUtils.filterArea(workArea);
			for (ReportMnoCca item : list) {
				String connectCity = item.getArea();
				// connectCity = AutoApproveUtils.filterArea(connectCity);
				// StringUtils.contains(workArea,connectCity)
				similarity = IkanalyzerUtils.getSimilarity(workArea,
						connectCity);
				double percentage = AutoApproveUtils.parsePercentage(item
						.getCallOutCountPercentage());
				msg = String.format("%s vs：%s，相似度：%s,呼出次数百分比：%s", workArea,
						connectCity, similarity,
						item.getCallOutCountPercentage());
				logger.info(msg);
				setHitNum(1);
				if (similarity >= 0.80 && percentage >= minPercentage) {
					setHitNum(0);
					break;
				}
			}
		} else {
			setHitNum(1);
			msg = "常用联系人为空";
		}
		hitRule.setRemark(msg);
		return hitRule;
	}

}
