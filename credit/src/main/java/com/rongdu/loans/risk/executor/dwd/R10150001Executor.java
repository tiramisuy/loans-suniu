package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.kdcredit.vo.KDBlackDataDetailVO;
import com.rongdu.loans.kdcredit.vo.KDBlackListVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 口袋黑名单
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public class R10150001Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10150001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		KDBlackListVO vo = null;
		try {
			vo = getDataInvokeService().getKDBlacklist(context);
		} catch (Exception e) {
			logger.error("口袋查询黑名单异常", e);
		}
		if (vo == null)
			return;
		List<KDBlackDataDetailVO> blackList = vo.getData();
		// 命中的规则
		HitRule hitRule = checkBlackList(blackList);
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
	 * 口袋黑名单
	 * 
	 * @param blackList
	 * @return
	 */
	private HitRule checkBlackList(List<KDBlackDataDetailVO> blackList) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (blackList != null) {
			for (KDBlackDataDetailVO black : blackList) {
				if (black.getIs_in()) {
					setHitNum(1);
					hitRule.setRemark("命中口袋黑名单");
					break;
				}
			}
		}
		return hitRule;
	}

}
