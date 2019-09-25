package com.rongdu.loans.risk.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 填写的常用联系人存疑
 * 数据来源于：白骑士资信云报告数据
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
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<CustContactVO> contactList = context.getUserInfo().getContactList();
		List<ReportMnoCcm> ccmList = AutoApproveUtils.getTopCcmList(vo.getData().getMnoCommonlyConnectMobiles(),20);

		//命中的规则
		HitRule hitRule = checkContactAuthenticity(contactList,ccmList);
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
	 * @param contactList
	 * @param ccmList
     * @return
     */
	private HitRule checkContactAuthenticity(List<CustContactVO> contactList,List<ReportMnoCcm> ccmList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int total = contactList.size();
		int count = 0;
		int threshold=1;
		String contactMobile = null;
		String ccmMobile = null;
		Map<String,Boolean> extInfo = new HashMap<>();
		for (CustContactVO contact:contactList){
			contactMobile = contact.getMobile();
			extInfo.put(contactMobile,false);
			for (ReportMnoCcm ccm:ccmList){
				ccmMobile = ccm.getMobile();
				//该紧急联系人在常用联系人Top20中
				if (StringUtils.equals(ccmMobile,contactMobile)){
					count++;
					extInfo.put(contactMobile,true);
					break;
				}
			}
		}
		if (count<threshold){
			setHitNum(1);
			hitRule.setValue(String.valueOf(count));
		}
		String msg = String.format("紧急联系人数量：%s，未在通话记录TOP20的数量：%s，详情如下：%s",total,(total-count),JsonMapper.toJsonString(extInfo));
		hitRule.setRemark(msg);
		return  hitRule;
	}


}
