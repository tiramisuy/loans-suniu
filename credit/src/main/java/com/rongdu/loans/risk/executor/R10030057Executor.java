package com.rongdu.loans.risk.executor;

import java.util.List;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 日常联系人未保存在设备通讯录中2 数据来源于：白骑士资信云报告数据
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
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		DeviceContactVO deviceContactVo = creditDataInvokeService.getBaiqishiContactInfo(context);
		List<Contact> contactList = deviceContactVo.getContactsInfo();
		List<ReportMnoCcm> ccmList = vo.getData().getMnoCommonlyConnectMobiles();

		// 命中的规则
		HitRule hitRule = checkContactAuthenticity(contactList, ccmList);
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
	private HitRule checkContactAuthenticity(List<Contact> contactList, List<ReportMnoCcm> ccmList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		int minThreshold = 0;
		int maxThreshold = 10;
		if (contactList != null) {
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
		}
		if (count >= minThreshold && count < maxThreshold) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		int contactCount = contactList != null ? contactList.size() : 0;
		int ccmCount = ccmList != null ? ccmList.size() : 0;
		String msg = String.format("通讯录号码数量：%s，通话记录数量：%s，一致数量：%s", contactCount, ccmCount, count);
		hitRule.setRemark(msg);
		return hitRule;
	}

}
