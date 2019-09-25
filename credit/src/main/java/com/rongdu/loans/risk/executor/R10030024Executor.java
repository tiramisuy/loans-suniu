package com.rongdu.loans.risk.executor;

import java.util.Date;
import java.util.List;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportEmergencyContact;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.enums.MaritalEnum;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 存在伪造联系人的嫌疑 数据来源于：白骑士资信云报告数据
 * 
 * @author sunda
 * @version 2017-08-14
 */
public class R10030024Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030024);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		String marital = context.getUserInfo().getMarital();
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<ReportEmergencyContact> ecList = vo.getData().getEmergencyContacts();
		String applyTime = context.getApplyInfo().getApplyTime();
		List<ReportMnoCcm> ccmList = vo.getData().getMnoCommonlyConnectMobiles();

		// 命中的规则
		HitRule hitRule = checkFirstConnectTime(contactList, ccmList, ecList, marital, applyTime);
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
	 * 申请人婚姻状况为未婚，填写的父母近半年内最早联系时间距申请日≤150天，且＞120天
	 * （父母手机号码后4位与“常用联系电话（近6个月）”手机号码后4位进行匹配不成功）
	 * 
	 * @param contactList
	 * @param ccmList
	 * @return
	 */
	private HitRule checkFirstConnectTime(List<CustContactVO> contactList, List<ReportMnoCcm> ccmList,
			List<ReportEmergencyContact> ecList, String marital, String applyTimeSting) {
		HitRule hitRule = createHitRule(getRiskRule());
		// boolean unmarrired =
		// StringUtils.equals(MaritalEnum.UNMARRIED.getDesc(),marital);
		boolean unmarrired = AutoApproveUtils.getSpouseContact(contactList).size() == 0 ? true : false;
		Date applyTime = DateUtils.parseDate(applyTimeSting);
		String msg = "";
		if (unmarrired) {
			int minConnectDuration = 120;
			int maxConnectDuration = 150;
			// 通话间隔（天）
			int duration = 0;
			String contactMobile = AutoApproveUtils.getContactMobileByRelationship(contactList,
					RelationshipEnum.PARENTS);
			// 最早联系时间（匹配正常手机号码）
			String firstConnectTimeSting = AutoApproveUtils.getFirstConnectTime(ecList, contactMobile);
			Date firstConnectTime = null;
			boolean existShortNumber = AutoApproveUtils.existShortNumber(ccmList, contactMobile);
			if (!existShortNumber) {
				if (StringUtils.isNotBlank(firstConnectTimeSting)) {
					firstConnectTime = DateUtils.parseDate(firstConnectTimeSting);
					duration = DateUtils.daysBetween(firstConnectTime, applyTime);
					msg = String.format("父母半年内最早联系时间：%s，距申请日时长：%s天", firstConnectTimeSting, duration);
					if (minConnectDuration < duration && duration <= maxConnectDuration) {
						setHitNum(1);
					}

				} else {
					setHitNum(1);
					msg = "最早联系时间为空";
				}
			} else {
				msg = "联系人在通话详单中为短号";
			}

			hitRule.setValue(firstConnectTimeSting);
		} else {
			msg = String.format("婚姻状况：%s", marital);
		}

		hitRule.setRemark(msg);
		return hitRule;
	}

}
