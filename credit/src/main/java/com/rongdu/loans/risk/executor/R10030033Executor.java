package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请人最近未与紧急联系人沟通 数据来源于：白骑士资信云报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030033Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030033);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		String applyTime = context.getApplyInfo().getApplyTime();
		List<ReportEmergencyContact> ecList = vo.getData().getEmergencyContacts();
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<String> familyContacts = AutoApproveUtils.getFamilyContact(contactList);

		// 命中的规则
		HitRule hitRule = checkLatestConnectTime(ecList, familyContacts, applyTime);
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
	 * 申请人填写的配偶和父母联系人近半年内最近一次通话联系时间距申请日≥60天
	 * 
	 * @param ecList
	 * @return
	 */
	private HitRule checkLatestConnectTime(List<ReportEmergencyContact> ecList, List<String> familyContacts,
			String applyTimeSting) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 通话次数
		int duration = 0;
		int minDuration = 60;
		Map<String, String> extInfo = new LinkedHashMap<>();
		Date applyTime = DateUtils.parseDate(applyTimeSting);
		String latestConnectTimeSting = "";
		Date latestConnectTime = null;
		int count = 0;
		if (ecList != null) {
			for (ReportEmergencyContact contact : ecList) {
				latestConnectTimeSting = contact.getLatestConnectTime();
				// 父母或者配偶
				if (familyContacts.contains(contact.getMobile())) {
					extInfo.put(contact.getMobile(), latestConnectTimeSting);
					if (StringUtils.isNotBlank(latestConnectTimeSting)) {
						latestConnectTime = DateUtils.parseDate(latestConnectTimeSting);
						duration = DateUtils.daysBetween(latestConnectTime, applyTime);
						if (duration >= minDuration) {
							setHitNum(1);
							count++;
						}
					} else {
						setHitNum(1);
						hitRule.setValue(String.valueOf(count));
						hitRule.setRemark("常用联系人最近通话联系时间为空");
						return hitRule;
					}
				}
			}
		} else {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
			hitRule.setRemark("常用联系人为空");
			return hitRule;
		}
		hitRule.setValue(String.valueOf(count));
		String msg = String.format("匹配数量：%s，详情如下：%s", count, JsonMapper.toJsonString(extInfo));
		hitRule.setRemark(msg);
		return hitRule;
	}

}
