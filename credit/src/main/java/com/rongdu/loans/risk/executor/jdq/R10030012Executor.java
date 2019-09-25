package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 填写的常用联系人存疑
 * 数据来源于：借点钱分析报告
 * @author sunda
 * @version 2017-08-14
 */
public class R10030012Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030012);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		// 通话记录
		JDQReport jdqReport = creditDataInvokeService.getjdqReport(context);
		// 常用联系人
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		//命中的规则
		HitRule hitRule = checkRule(jdqReport,contactList);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
	}

	/**
	 * 申请人填写的常用联系人与运营商近6个月的常用通话记录TOP20中一致数＜1
	 * @return
	 */
	private HitRule checkRule(JDQReport jdqReport,List<CustContactVO> contactList) {
		HitRule hitRule = createHitRule(getRiskRule());
		List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
		List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
		int remaider = contactChecks.size() % 2; // 余数
		int number = contactChecks.size() / 2; // 商
		int offset = 0;// 偏移量
		for (int i = 0; i < 2; i++) {
			List<ContactCheck> value = null;
			if (remaider > 0) {
				value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
			}
			List<ContactCheck> value1 = new ArrayList<ContactCheck>();
			value1.addAll(value);
			result.add(value1);
		}
		int size = result.get(0).size();
		for (ContactCheck contactCheck : result.get(1)) {
			contactCheck.setIndex(size);
			size++;
		}

		int countIndex = 0;
		List<ContactCheck> contactChecks1 = result.get(0);
		int num = contactChecks1.size() <= 20 ? contactChecks1.size() : 20;
		for (int i = 0; i < num; i++) {
			ContactCheck check = contactChecks1.get(i);
			for (CustContactVO custContactVO : contactList) {
				if (check.getMobile().equals(custContactVO.getMobile())) {
					countIndex++;
				}
			}
		}
		if (countIndex < 1) {
			setHitNum(1);
		}
		String msg = String.format("申请人填写的常用联系人与运营商近6个月的常用通话记录TOP20中一致数：%s", countIndex);
		hitRule.setRemark(msg);
		return hitRule;
	}


}
