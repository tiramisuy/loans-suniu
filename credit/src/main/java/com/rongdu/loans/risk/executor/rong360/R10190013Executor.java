package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10190013Executor.java  
* @Package com.rongdu.loans.risk.executor.rong360  
* @Description: 紧急联系人在通话详单中的个数<1个  
* @author: yuanxianchu  
* @date 2018年9月27日  
* @version V1.0  
*/
public class R10190013Executor extends Executor {

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
		int ifCallEmergency1 = tianjiReportDetail.getJson().getBasicInfo().getIfCallEmergency1();
		int ifCallEmergency2 = tianjiReportDetail.getJson().getBasicInfo().getIfCallEmergency2();
		//1:有通话记录;2:没有通话记录;3:缺少联系人输入信息
		if ((ifCallEmergency1 == 2 || ifCallEmergency1 == 3) && (ifCallEmergency2 == 2 || ifCallEmergency2 == 3)) {
			setHitNum(1);
		}
		hitRule.setRemark("紧急联系人在通话详单中的个数<1个");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10190013);
	}

}
