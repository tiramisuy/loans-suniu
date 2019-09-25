package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.xinyan.vo.JbqbBlackResultDetailVO;
import com.rongdu.loans.xinyan.vo.JbqbBlackVO;

/**
 * 新颜-负面拉黑-聚宝钱包 逾期七天以上拒绝
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10170009Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10170009);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		JbqbBlackVO vo = null;
		try {
			vo = getDataInvokeService().getXinyanBlackJbqb(context);
		} catch (Exception e) {
			logger.error("新颜负面拉黑-聚宝钱包定制查询异常", e);
		}
		if (vo == null)
			return;
		// 命中的规则
		HitRule hitRule = checkBlack(vo);
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
	 * 负面拉黑 最大逾期金额：2000-3000， 最长逾期天数：31-60， 最近逾期时间：2018-05， 当前逾期机构数：6，
	 * 当前履约机构数：5， 异常还款机构数：0， 睡眠机构数：4,聚宝袋探查结论:建议拉黑/无法确认
	 * 
	 * @param JbqbBlackVO
	 * @return
	 */
	private HitRule checkBlack(JbqbBlackVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null && vo.isSuccess()) {
			if ("0".equals(vo.getData().getCode())) {
				JbqbBlackResultDetailVO d = vo.getData().getResult_detail();
				//1=建议拉黑,4=无法确认
				if ("1".equals(d.getTag_jubaodai())) {
					setHitNum(1);
				}
				String msg = String.format(
						"探查结论：%s，最大逾期金额：%s，最长逾期天数：%s，最近逾期时间：%s，当前逾期机构数：%s，当前履约机构数：%s，异常还款机构数：%s，睡眠机构数：%s",
						d.getTag_jubaodai(), d.getMax_overdue_amt(), d.getMax_overdue_days(),
						d.getLatest_overdue_time(), d.getCurrently_overdue(), d.getCurrently_performance(),
						d.getAcc_exc(), d.getAcc_sleep());
				hitRule.setRemark(msg);
			}
		}
		return hitRule;
	}
}
