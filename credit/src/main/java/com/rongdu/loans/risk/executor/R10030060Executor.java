package com.rongdu.loans.risk.executor;

import java.util.List;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 配偶有未完结订单
 * 
 * @author liuzhuang
 * @version 2018-09-10
 */
public class R10030060Executor extends Executor {
	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030060);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		// 命中的规则
		HitRule hitRule = checkContactList(creditDataInvokeService, contactList);
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
	 * 配偶有未完结订单
	 * 
	 * @param contactList
	 * @return
	 */
	private HitRule checkContactList(CreditDataInvokeService creditDataInvokeService, List<CustContactVO> contactList) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (contactList != null) {
			for (CustContactVO vo : contactList) {
				if (StringUtils.isNotBlank(vo.getMobile()) && vo.getRelationship().intValue() == 2) {
					int count = creditDataInvokeService.countUnFinishApplyByMobile(vo.getMobile());
					if (count > 0) {
						setHitNum(1);
						String msg = String.format("配偶：%s,%s", vo.getName(), vo.getMobile());
						hitRule.setRemark(msg);
						break;
					}
				}
			}
		}
		return hitRule;
	}
}
