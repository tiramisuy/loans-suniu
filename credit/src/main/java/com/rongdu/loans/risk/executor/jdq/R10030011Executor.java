package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 近半年较少使用此手机号码 数据来源于：魔蝎通话报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030011Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030011);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		List<MoxieTelecomReport.ActiveDegreeBean> list = creditDataInvokeService.getjdqBase(context).getMoxieTelecomReport().getActive_degree();
		//命中的规则
		HitRule hitRule = checkRule(list);
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
	 * 运营商近半年全天未使用通话和短信功能的累计天数＞30且≤50，数据来源于：借点钱魔蝎分析报告数据
	 * @return
	 */
	private HitRule checkRule(List<MoxieTelecomReport.ActiveDegreeBean> list) {
		HitRule hitRule = createHitRule(getRiskRule());
		Integer num = null;
		if (null != list && list.size() > 0){
			for (MoxieTelecomReport.ActiveDegreeBean activeDegreeBean : list) {
				if ("power_off_day".equals(activeDegreeBean.app_point)){
						// 关机天数（无通话，短信记录的天数）
						num = Integer.valueOf(activeDegreeBean.item.item_6m);
						break;
				}
			}
		}
		if (num != null && num > 30 && num <= 50) {
			setHitNum(1);
		}
		hitRule.setRemark(String.format("运营商近半年全天未使用通话和短信功能的累计天数，当前：%d", num));
		return hitRule;
	}

}
