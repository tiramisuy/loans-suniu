package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人通话时间不符合要求（拒绝）
 * 数据来源于：白骑士资信云报告数据
 * @author MARK
 * @version 2017-08-14
 */
public class R10030053Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030053);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<ReportMnoCcm> ccmList = vo.getData().getMnoCommonlyConnectMobiles();

		//命中的规则
		HitRule hitRule = checkContactConnectCount(contactList,ccmList);
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
	 * 申请人填写的所有联系人，近6个月通话时间都小于20分钟
	 * @param contactList
	 * @param ccmList
	 * @return
	 */
	private HitRule checkContactConnectCount(List<CustContactVO> contactList,List<ReportMnoCcm> ccmList) {
		HitRule hitRule = createHitRule(getRiskRule());
		//20分钟=1200秒
		int minConnectTime = 1200;
		String contactMobile = "";

		Map<String,Integer> extInfo = new HashMap<>();
		int connectTime = 0;
		int contactNum = contactList.size();
		int matchNum = 0;
		int unmatchNum = 0;
		for(CustContactVO contact:contactList){
			contactMobile = contact.getMobile();
			//通话时长（匹配正常手机号码）
			connectTime = AutoApproveUtils.getConnectTime(ccmList,contactMobile);
			if (0<connectTime&&connectTime<minConnectTime){
				unmatchNum++;
			}else if (connectTime==0) {
				//通话时长（匹配短号）
				connectTime = AutoApproveUtils.getConnectTimeByShortNumber(ccmList, contactMobile);
				if (connectTime<minConnectTime) {
					unmatchNum++;
					boolean existShortNumber = AutoApproveUtils.existShortNumber(ccmList, contactMobile);
					if (existShortNumber) {
						logger.info("与{}通话时间为：{}秒，通话详单中为短号码", contactMobile, connectTime);
					} else {
						logger.info("与{}通话时间为：{}秒", contactMobile, connectTime);
					}
				}
			}
			extInfo.put(contactMobile,connectTime);
		}
		matchNum = contactNum-unmatchNum;
		if (matchNum<1){
			setHitNum(1);
		}
		String msg = String.format("联系人数量：%s，不符合要求人数：%s，明细如下：%s",contactNum, unmatchNum, JsonMapper.toJsonString(extInfo));
		hitRule.setValue(String.valueOf(matchNum));
		hitRule.setRemark(msg);
		return  hitRule;
	}

}
