package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.tlblackrisk.vo.TongLianBlackVO;

/**
 * 通联查询网贷黑名单
 *
 * @author fy
 * @Package com.rongdu.loans.risk.executor.R10220001Executor
 * @date 2019/7/25 14:38
 */
public class R10220001Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10220001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		TongLianBlackVO vo = getDataInvokeService().getTongLianBlackDetail(context);
		if (null == vo){
			return;
		}
		// 命中的规则
		HitRule hitRule = checkRule(vo);
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
	 *  命中通联网贷黑名单高危风险
	 *  1：极黑账户，网贷平台有过
	 * 严重逾期行为(M3+)
	 * 2：高危账户，网贷平台出现
	 * 逾期但是未到 M3+
	 * 3：关注账户，最近 1 笔扣款
	 * 失败，疑似出现逾期
	 * @param vo
	 * @return
	 */
	private HitRule checkRule(TongLianBlackVO vo) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (vo.getData() != null && vo.getData().getFinancialMsg() != null && StringUtils.isNotBlank(vo.getData().getFinancialMsg().getLevel())){
			String level = vo.getData().getFinancialMsg().getLevel();
			if (StringUtils.equals("1",level) || StringUtils.equals("2",level)){
				setHitNum(1);
				String remark = String.format("命中通联网贷黑名单高危等级风险(1-极黑账户,2-高危账户,3-关注账户)，风险等级：%s", level);
				hitRule.setRemark(remark);
			}
		}
		return hitRule;
	}


}
