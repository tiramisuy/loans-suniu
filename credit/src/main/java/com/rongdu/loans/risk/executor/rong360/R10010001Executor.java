package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 贷款申请信息不完整
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10010001Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10010001);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		HitRule hitRule = checkApplyInfo(context);
		String evidence = hitRule.getRemark();
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), hitNum, evidence);
	}

	private HitRule checkApplyInfo(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (context.getApplyInfo() == null || StringUtils.isBlank(context.getApplyInfo().getMobile())) {
			hitRule.setRemark("申请信息信息为空");
			setHitNum(1);
			return hitRule;
		}
		if (context.getUser() == null || StringUtils.isBlank(context.getUser().getIdNo())) {
			hitRule.setRemark("用户信息为空");
			setHitNum(1);
			return hitRule;
		}
		if (context.getUserInfo() == null || StringUtils.isBlank(context.getUserInfo().getIdNo())) {
			hitRule.setRemark("用户基本信息为空");
			setHitNum(1);
			return hitRule;
		}
		if (context.getUserInfo().getContactList() == null || context.getUserInfo().getContactList().isEmpty()) {
			hitRule.setRemark("紧急联系人信息为空");
			setHitNum(1);
			return hitRule;
		}
		TianjiReportDetailResp resp = getDataInvokeService().getRongTJReportDetail(context);
		if (resp == null || resp.getJson() == null) {
			hitRule.setRemark("融360运营商报告信息为空");
			setHitNum(1);
			return hitRule;
		}
		OrderAppendInfo orderAppendInfo = getDataInvokeService().getRongAdditional(context);
		if (orderAppendInfo == null || orderAppendInfo.getContacts() == null) {
			hitRule.setRemark("融360补充信息不完整");
			setHitNum(1);
			return hitRule;
		}
		if (orderAppendInfo.getContacts().getPhoneList() == null) {
			hitRule.setRemark("融360通讯录采集失败");
			setHitNum(1);
			return hitRule;
		}
		return hitRule;
	}
}
