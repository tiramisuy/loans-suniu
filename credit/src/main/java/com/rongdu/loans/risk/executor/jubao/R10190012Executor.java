package com.rongdu.loans.risk.executor.jubao;

import java.util.List;

import com.rongdu.loans.loan.option.rongTJreportv1.SpecialCate;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10190012Executor.java  
* @Package com.rongdu.loans.risk.executor.rong360  
* @Description: 赌博类通话次数>0  
* @author: yuanxianchu  
* @date 2018年9月27日  
* @version V1.0  
*/
public class R10190012Executor extends Executor {

	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkRule(context);
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
	
	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		TianjiReportDetailResp tianjiReportDetail = getDataInvokeService().getRongTJReportDetail(context);
		List<SpecialCate> specialCate = tianjiReportDetail.getJson().getSpecialCate();
		for (SpecialCate specialCate2 : specialCate) {
			//赌博类通话次数>0
			if ("5".equals(specialCate2.getCate()) && specialCate2.getTalkCnt() > 0) {
				setHitNum(1);
				break;
			}
		}
		hitRule.setRemark("赌博类通话次数>0");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10190012);
	}

}
