package com.rongdu.loans.risk.executor.xjbk;

import java.util.List;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 
 * @Description: 联系人通话次数不符合要求 数据来源于：API
 * @author: 饶文彪
 * @date 2018年6月27日 下午4:08:19
 */
public class R10030052Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030052);
	}

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

	/**
	 * 判断是否呼入呼出同时小于25
	 * 
	 * @param context
	 * @return
	 */
	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());

		XianJinBaiKaCommonOP xianJinBaiKaBase = getDataInvokeService().getXianJinBaiKaBase(context);
		List<ContactList> contactList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify().getContactList();
		List<ContactList> contactList2 = null;
		if (contactList != null) {
			contactList2 = BeanMapper.mapList(contactList, ContactList.class);
		}

		List<ContactList> calInCntList = AutoApproveUtils.getTopCalInCntList(contactList, 10);
		List<ContactList> callOutCntList = AutoApproveUtils.getTopCallOutCntList(contactList2, 10);

		int callInCount_max = 0;
		int callOutCount_max = 0;
		if (calInCntList.size() > 0) {
			callInCount_max = calInCntList.get(0).getCallInCnt();
		}
		if (callOutCntList.size() > 0) {
			callOutCount_max = callOutCntList.get(0).getCallOutCnt();
		}
		int maxCount = 24;
		if (callInCount_max < maxCount && callOutCount_max < maxCount) {// 呼入呼出同时小于25
			setHitNum(1);
		}

		hitRule.setRemark(String.format("呼入呼出同时小于%s,最大呼入%s;最大呼出%s;数据来源：现金白卡", maxCount, callInCount_max, callOutCount_max));

		return hitRule;
	}

}
