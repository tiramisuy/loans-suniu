package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.Contact;
import com.rongdu.loans.baiqishi.vo.DeviceContactVO;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rong360Model.PhoneList;
import com.rongdu.loans.loan.option.rongTJreportv1.CallLog;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.List;

/**
 * 日常联系人未保存在设备通讯录中3 数据来源于：白骑士资信云报告数据
 * 
 * @author liuzhuang
 * @version 2018-04-16
 */
public class R10030058Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030058);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		// 加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
		OrderAppendInfo rongAdditional = creditDataInvokeService.getRongAdditional(context);
		TianjiReportDetailResp rongTJReportDetail = creditDataInvokeService.getRongTJReportDetail(context);
		List<PhoneList> phoneLists = rongAdditional.getContacts().getPhoneList();
		List<CallLog> callLogs = rongTJReportDetail.getJson().getCallLog();


		// 命中的规则
		HitRule hitRule = checkContactAuthenticity(phoneLists, callLogs);
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
	 * 设备通讯录号码与运营商近6个月的常用联系人通话次数均小于50次
	 */
	private HitRule checkContactAuthenticity(List<PhoneList> phoneLists, List<CallLog> callLogs) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		int threshold = 50;
		if (phoneLists != null) {
			String phone = null;
			String callLogPhone = null;
			for (CallLog callLog : callLogs) {
				callLogPhone = callLog.getPhone();
				int talkCnt = callLog.getTalkCnt();
				boolean isMatch = false;
				for (PhoneList phoneList : phoneLists) {
					phone = AutoApproveUtils.parseContactMobile(phoneList.getPhone());
					if (callLogPhone.length() < 8 || phone.length() < 8) {
						if (StringUtils.equals(callLogPhone, phone)) {
							isMatch = true;
							break;
						}
					} else {
						if (StringUtils.contains(callLogPhone, phone)
								|| StringUtils.contains(phone, callLogPhone)) {
							isMatch = true;
							break;
						}
					}
				}
				if (isMatch && talkCnt >= threshold) {
					count++;
				}
			}
		}
		if (count == 0) {
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		String msg = String.format("通讯录号码数量：%s，通话记录数量：%s，通话次数>=50次数量：%s", phoneLists.size(), callLogs.size(), count);
		hitRule.setRemark(msg);
		return hitRule;
	}
}
