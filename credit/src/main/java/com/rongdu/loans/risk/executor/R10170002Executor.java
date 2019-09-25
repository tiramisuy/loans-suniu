package com.rongdu.loans.risk.executor;

import java.util.Date;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.xinyan.vo.TotaldebtResultDetail;
import com.rongdu.loans.xinyan.vo.TotaldebtVO;

/**
 * 
 * @Description:新颜-近5个月最大的共贷机构数过多
 * @author: 饶文彪
 * @date 2018年6月19日 上午11:58:06
 */
public class R10170002Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10170002);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		TotaldebtVO vo = null;
		try {
			vo = getDataInvokeService().getXinyanTotalDebt(context);

		} catch (Exception e) {
			logger.error("新颜共债档案查询异常", e);
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
	 * 申请客户近5个月统计周期内最大的共贷机构数>=3
	 * 
	 * @param TotaldebtVO
	 * @return
	 */
	private HitRule checkRule(TotaldebtVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo != null && vo.isSuccess()) {
			if ("0".equals(vo.getData().getCode())) {
				TotaldebtResultDetail d = vo.getData().getResult_detail();
				int org_count_max = 0;// 最大共债机构数

				// 当前月
				if (StringUtils.isNotBlank(d.getCurrent_org_count())) {
					org_count_max = Math.max(org_count_max, Integer.parseInt(d.getCurrent_org_count()));
				}

				if (d.getTotaldebt_detail() != null) {

					String fourMonth = DateUtils.formatDate(DateUtils.addMonth(new Date(), -4), "yyyy-MM");// 历史4个月

					for (int i = 0; i < d.getTotaldebt_detail().size(); i++) {
						if (fourMonth.compareTo(d.getTotaldebt_detail().get(i).getTotaldebt_date()) <= 0) {
							org_count_max = Math.max(org_count_max,
									Integer.parseInt(d.getTotaldebt_detail().get(i).getTotaldebt_org_count()));
						}

					}

					if (org_count_max >= 3) {// 申请客户近5个月统计周期内最大的共贷机构数>=3
						setHitNum(1);
					}

					String msg = String.format("近5个月最大共贷机构数:%s", org_count_max);
					hitRule.setRemark(msg);
				}

			}

		}
		return hitRule;
	}

}
