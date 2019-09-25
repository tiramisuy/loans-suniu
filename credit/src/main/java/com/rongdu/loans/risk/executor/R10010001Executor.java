package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
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

		// 资信云报告
		ReportDataVO reportDataVO = getDataInvokeService().getBaishiqiReportData(context);
		if (reportDataVO == null || reportDataVO.getData() == null) {
			String msg = "未能采集到白骑士资信云报告";
			hitRule.setRemark(msg);
			setHitNum(1);
			return hitRule;
		}
		if (!reportDataVO.isSuccess()) {
			String msg = String.format("未能采集到白骑士资信云报告：%s，%s", reportDataVO.getCode(), reportDataVO.getMsg());
			// CCOM3010=请求过于频繁，请稍后再试
			// CCOM9999=资信云系统异常，请联系白骑士工作人员
			if ("CCOM3010".equals(reportDataVO.getCode()) || "CCOM9999".equals(reportDataVO.getCode())) {
				throw new IllegalStateException(msg);
			} else {
				hitRule.setRemark(msg);
				setHitNum(1);
				return hitRule;
			}
		}
		if (reportDataVO.getData().getMnoCommonlyConnectMobiles() == null) {
			String msg = "资信云报告常用联系电话(近6个月)为空";
			hitRule.setRemark(msg);
			setHitNum(1);
			return hitRule;
		}
		// 首贷客户需要验证[设备通讯录]完整性
		UserInfoVO userInfo = context.getUserInfo();
		int loanSuccCount = (userInfo != null && userInfo.getLoanSuccCount() != null) ? userInfo.getLoanSuccCount()
				.intValue() : 0;
		if (loanSuccCount == 0) {
			// 手机设备通讯录
			DeviceContactVO deviceContactVO = getDataInvokeService().getBaiqishiContactInfo(context);
			if (deviceContactVO == null) {
				String msg = "未能获取手机设备通讯录";
				hitRule.setRemark(msg);
				setHitNum(1);
				return hitRule;
			}
			if (!deviceContactVO.isSuccess()) {
				String msg = String.format("未能获取手机设备通讯录：%s，%s", deviceContactVO.getCode(), deviceContactVO.getMsg());
				hitRule.setRemark(msg);
				setHitNum(1);
				return hitRule;
			}
		}
		return hitRule;
	}
}
