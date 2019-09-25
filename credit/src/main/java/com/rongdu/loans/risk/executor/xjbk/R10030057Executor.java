package com.rongdu.loans.risk.executor.xjbk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 日常联系人未保存在设备通讯录中2
 * 
 * @author liuzhuang
 * @version 2018-04-16
 */
public class R10030057Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030057);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		XianJinBaiKaCommonOP xianJinBaiKaAdditional = creditDataInvokeService.getXianJinBaiKaAdditional(context);
		XianJinBaiKaCommonOP xianJinBaiKaBase = creditDataInvokeService.getXianJinBaiKaBase(context);
		List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook().getPhoneList();
		List<ContactList> contactList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify().getContactList();
		List<ContactCheck> contactChecks = new ArrayList<ContactCheck>();
		for (String phone : phoneList) {
			ContactCheck contactCheck = new ContactCheck();
			String contactMobile = phone.substring(phone.lastIndexOf("_") + 1);
			String contactName = phone.substring(0, (phone.lastIndexOf("_")));
			contactCheck.setMobile(contactMobile);
			contactCheck.setName(contactName);
			contactCheck.setCallCnt(0);
			contactCheck.setCallLen(0);
			for (ContactList contactList1 : contactList) {
				if (contactMobile.equals(contactList1.getPhoneNum())) {
					contactCheck.setCallCnt(contactList1.getCallCnt());
					contactCheck.setCallLen(new Double(contactList1.getCallLen() * 60).intValue());
				}
			}
			contactChecks.add(contactCheck);
		}
		Collections.sort(contactChecks);

		// 命中的规则
		HitRule hitRule = checkContactAuthenticity(contactChecks, contactList);
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
	 * 设备通讯录号码与运营商近6个月的常用通话记录一致数>=0并且<10
	 * 
	 * @param contactList
	 * @param ccmList
	 * @return
	 */
	private HitRule checkContactAuthenticity(List<ContactCheck> contactChecks, List<ContactList> contactList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		int minThreshold = 0;
		int maxThreshold = 10;
		if (contactChecks != null) {
			for (ContactCheck c : contactChecks) {
				if (c.getCallCnt() > 0) {
					count++;
				}
			}
		}
		if (count >= minThreshold && count < maxThreshold) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		int contactCount = contactChecks != null ? contactChecks.size() : 0;
		int ccmCount = contactList != null ? contactList.size() : 0;
		String msg = String.format("通讯录号码数量：%s，通话记录数量：%s，一致数量：%s", contactCount, ccmCount, count);
		hitRule.setRemark(msg);
		return hitRule;
	}

}
