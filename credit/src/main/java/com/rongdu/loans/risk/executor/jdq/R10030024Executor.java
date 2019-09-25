package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.Date;
import java.util.List;

/**
 * 存在伪造联系人的嫌疑 数据来源于：借点钱报告
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
		String marital = context.getUserInfo().getMarital();
		String applyTime = context.getApplyInfo().getApplyTime();
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<MoxieTelecomReport.CallContactDetailBean> callList = creditDataInvokeService.getjdqBase(context).getMoxieTelecomReport().getCall_contact_detail();

		// 命中的规则
		HitRule hitRule = checkFirstConnectTime(contactList, callList, marital, applyTime);
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
	 * @return
	 */
	private HitRule checkFirstConnectTime(List<CustContactVO> contactList, List<MoxieTelecomReport.CallContactDetailBean> callList, String marital, String applyTimeSting) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 判断是否未婚
		boolean unmarrired = AutoApproveUtils.getSpouseContact(contactList).size() == 0 ? true : false;
		// 申请时间
		Date applyTime = DateUtils.parseDate(applyTimeSting);
		String msg = "";
		if (unmarrired) {
			int minConnectDuration = 120;
			int maxConnectDuration = 150;
			// 通话间隔（天）
			int duration = 0;
			// 获取父母电话号码
			String contactMobile = AutoApproveUtils.getContactMobileByRelationship(contactList,
					RelationshipEnum.PARENTS);
			// 最早联系时间（匹配正常手机号码）
			String firstConnectTimeSting = null;
			Date firstConnectTime = null;
			for (MoxieTelecomReport.CallContactDetailBean callContactDetailBean : callList) {
				if (contactMobile.equals(callContactDetailBean.peer_num)){
					firstConnectTimeSting = callContactDetailBean.trans_start;
				}
			}
			if (StringUtils.isNotBlank(firstConnectTimeSting)){
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
			hitRule.setValue(firstConnectTimeSting);
		} else {
			msg = String.format("婚姻状况：%s", marital);
		}

		hitRule.setRemark(msg);
		return hitRule;
	}

}
