package com.rongdu.loans.risk.executor;

import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 日常联系人未保存在设备通讯录中 数据来源于：魔蝎分析报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030016Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030016);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		JDQReport jdqReport = creditDataInvokeService.getjdqReport(context);
//        List<ContactCheck> list = jdqReport.getContactCheckList();
//
//        // 命中的规则
//        HitRule hitRule = checkContactAuthenticity(list);
		HitRule hitRule = checkRule(jdqReport);
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
	 * 近6个月通话时长top20的号码与通讯录名单匹配数小于5
	 * @param jdqReport
	 * @return
	 */
	private HitRule checkRule(JDQReport jdqReport) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = jdqReport.getCallCountTop20MatchAddressBookNum();
		if (count < 7) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		String msg = String.format("近6个月通话次数top20的号码与通讯录名单匹配数：%s",count);
		hitRule.setRemark(msg);
		return hitRule;
	}

	/**
	 * 设备通讯录号码与运营商近6个月的常用通话记录TOP20中一致数＜7
	 * 
	 * @param contactList
	 * @param ccmList
	 * @return
	 */
/*	private HitRule checkContactAuthenticity(List<Contact> contactList, List<ReportMnoCcm> ccmList) {
		HitRule hitRule = createHitRule(getRiskRule());
		if (contactList == null) {
			setHitNum(1);
			hitRule.setRemark("设备通讯录为空");
			return hitRule;
		}
		int count = 0;
		int threshold = 7;
		String contactMobile = null;
		String ccmMobile = null;
		for (ReportMnoCcm ccm : ccmList) {
			ccmMobile = ccm.getMobile();
			for (Contact contact : contactList) {
				contactMobile = AutoApproveUtils.parseContactMobile(contact.getMobile());
				if (ccmMobile.length() < 8 || contactMobile.length() < 8) {
					if (StringUtils.equals(ccmMobile, contactMobile)) {
						count++;
						break;
					}
				} else {
					if (StringUtils.contains(ccmMobile, contactMobile)
							|| StringUtils.contains(contactMobile, ccmMobile)) {
						count++;
						break;
					}
				}
			}
		}
		if (count < threshold) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		String msg = String.format("通讯录号码数量：%s，通话记录TOP20数量：%s，一致数量：%s", contactList.size(), ccmList.size(), count);
		hitRule.setRemark(msg);
		return hitRule;
	}*/

}
