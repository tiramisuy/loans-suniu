package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.xinyan.vo.OverdueResultDetail;
import com.rongdu.loans.xinyan.vo.OverdueVO;

/**
 * 
 * @Description: 逾期机构过多（新颜）
 * @author: 饶文彪
 * @date 2018年6月19日 下午1:17:29
 */
public class R10170004Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10170004);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		OverdueVO vo = null;
		try {
			vo = getDataInvokeService().getXinyanOverdueVO(context);
		} catch (Exception e) {
			logger.error("新颜逾期档案查询异常", e);
		}
		if (vo == null)
			return;
		// 命中的规则
		HitRule hitRule = checkRule(vo);
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
	 * 近6个月内逾期机构数>=4
	 * 
	 * @param OverdueVO
	 * @return
	 */
	private HitRule checkRule(OverdueVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null && vo.isSuccess()) {
			if ("0".equals(vo.getData().getCode())) {
				OverdueResultDetail d = vo.getData().getResult_detail();
				// 近6个月内逾期机构数>=4
				if (Integer.parseInt(d.getMember_count()) >= 4) {
					setHitNum(1);
				}

				String msg = String.format("近6个月内逾期机构数:%s", d.getMember_count());
				hitRule.setRemark(msg);
			}
		}
		return hitRule;
	}

}
