package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;
import java.util.List;

/**
 * 申请人最近未与紧急联系人沟通 数据来源于：借点钱报告数据
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
		//亲属列表
		List<CustContactVO> contactList = context.getUserInfo()
				.getContactList();
		String applyTime = context.getApplyInfo().getApplyTime();
		// 加载风险分析数据
		IntoOrder vo = null;
		try {
			vo = getDataInvokeService().getjdqBase(context);
		} catch (Exception e) {
			logger.error("JDQ基本信息查询异常", e);
		}
		if (vo == null) {
			return;
		}
		//通话详单
		List<MoxieTelecomReport.CallContactDetailBean> detailBeans = vo.getMoxieTelecomReport().call_contact_detail;
		// Assert.notNull(ccmList,"常用联系电话(近6个月)为空");

		// 命中的规则
		HitRule hitRule = checkLatestConnectTime(contactList, detailBeans,applyTime);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				getHitNum(), evidence);
	}

	/**
	 * 申请人填写的配偶和父母联系人近半年内最近一次通话联系时间距申请日≥60天
	 *
	 * @return
	 */
	private HitRule checkLatestConnectTime(List<CustContactVO> contactList, List<MoxieTelecomReport.CallContactDetailBean> callList,
			String applyTimeSting) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 申请时间
		Date applyTime = DateUtils.parseDate(applyTimeSting);
		String msg = "";
		int connectDuration = 60;
		// 通话间隔（天）
		int parentsDuration = 0;
		// 获取父母电话号码
		String parents = AutoApproveUtils.getContactMobileByRelationship(contactList,
				RelationshipEnum.PARENTS);
		// 通话间隔（天）
		int spouseDuration = 0;
		// 获取配偶电话号码
		String spouse = AutoApproveUtils.getContactMobileByRelationship(contactList,
				RelationshipEnum.SPOUSE);
		// 没有配偶就获取朋友的电话
		spouse = StringUtils.isNotBlank(spouse) ? spouse :AutoApproveUtils.getContactMobileByRelationship(contactList,
				RelationshipEnum.FRIEND);
		// 最晚联系时间（匹配正常手机号码）
		String parentsConnectTimeSting = null;
		Date parentsConnectTime = null;
		String spouseConnectTimeSting = null;
		Date spouseConnectTime = null;
		for (MoxieTelecomReport.CallContactDetailBean callContactDetailBean : callList) {
			if (parents.equals(callContactDetailBean.peer_num)){
				parentsConnectTimeSting = callContactDetailBean.trans_end;
			}
			if (spouse.equals(callContactDetailBean.peer_num)){
				spouseConnectTimeSting = callContactDetailBean.trans_end;
			}
		}
		if (StringUtils.isNotBlank(parentsConnectTimeSting) && StringUtils.isNotBlank(spouseConnectTimeSting)){
			parentsConnectTime = DateUtils.parseDate(parentsConnectTimeSting);
			parentsDuration = DateUtils.daysBetween(parentsConnectTime, applyTime);
			spouseConnectTime = DateUtils.parseDate(spouseConnectTimeSting);
			spouseDuration = DateUtils.daysBetween(spouseConnectTime, applyTime);
			msg = String.format("申请人过去未与常用联系人沟通 申请人填写的配偶和父母联系人近半年内最近一次通话联系时间分别为配偶：%s，距申请日时长：%s天,父母：%s，距申请日时长：%s天", spouseConnectTimeSting, spouseDuration,parentsConnectTimeSting,parentsDuration);
			if (connectDuration <= parentsDuration && connectDuration <= spouseDuration) {
				setHitNum(1);
			}
		} else {
			setHitNum(1);
			msg = "申请人常用联系人最少有一人最近一次通话联系时间距申请日≥60天";
		}
		hitRule.setRemark(msg);
		return hitRule;
	}

}
