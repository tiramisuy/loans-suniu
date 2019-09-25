package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.xinyan.vo.OverdueDetail;
import com.rongdu.loans.xinyan.vo.OverdueResultDetail;
import com.rongdu.loans.xinyan.vo.OverdueVO;

/**
 * 
 * @Description:申请客户存在逾期天数为M1的订单（新颜）
 * @author: 饶文彪
 * @date 2018年6月19日 下午1:18:06
 */
public class R10170006Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10170006);
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
	 * 申请客户6个月内存在逾期天数为M1的订单
	 * 
	 * @param OverdueVO
	 * @return
	 */
	private HitRule checkRule(OverdueVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null && vo.isSuccess()) {
			if ("0".equals(vo.getData().getCode())) {
				OverdueResultDetail d = vo.getData().getResult_detail();

				int m1_count = 0;// 申请客户6个月内存在逾期天数为M1的订单数量

				if (d.getDetails() != null) {

					for (OverdueDetail detail : d.getDetails()) {
						if (detail.getCount().equals("M1")) {
							m1_count++;
						}

					}
				}

				// 申请客户6个月内存在逾期天数为M1的订单
				if (m1_count > 0) {
					setHitNum(1);
				}

				String msg = String.format("近6个月内逾期天数为M1的订单数：%s", m1_count);
				hitRule.setRemark(msg);
			}
		}
		return hitRule;
	}

}
