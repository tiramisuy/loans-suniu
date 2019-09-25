package com.rongdu.loans.risk.executor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 近半年常用联系人累计通话时长较短
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030008Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030008);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<ReportMnoCcm> ccmList = vo.getData().getMnoCommonlyConnectMobiles();
		int minContacts = 5;
		List<ReportMnoCcm> topCcmList = AutoApproveUtils.getTopCcmList(ccmList,minContacts);
		//向白骑士上传的自定义规则字段
		Map<String,String> extParams = getDataInvokeService().getBaishiqiExtParams(context);

		//命中的规则
		HitRule hitRule = checkCommonlyConnectMobiles(topCcmList);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
			// 命中规则，需要设置白骑士规则引擎自定义字段的数值
			String fieldName = getRiskRule().getFieldName();
			if (StringUtils.isNotBlank(fieldName)){
				extParams.put(fieldName,String.valueOf(1));
			}
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);

	}

	/**
	 * 运营商前5名最常用联系人近半年累计通话时长有一人≤20分钟(1200秒)
	 * @param ccmList
	 * @return
     */
	private HitRule checkCommonlyConnectMobiles(List<ReportMnoCcm> ccmList) {
		HitRule hitRule = createHitRule(getRiskRule());
		int count = 0;
		Map<String,String> extInfo = new LinkedHashMap<>();
		if (ccmList!=null&&ccmList.size()<=5){
			//联系时间（秒）&&ccmList.size()>=minContacts
			int connectTime = 0;
			for (ReportMnoCcm ccm:ccmList){
				connectTime = ccm.getConnectTime();
				extInfo.put(ccm.getMobile(),String.valueOf(ccm.getConnectTime()));
				//排名前的5联系人中，有一人≤20分钟，则命中
				if(connectTime<1200){
					count++;
				}
			}
			if (count>0){
				setHitNum(1);
			}
			String msg = String.format("常用联系人TOP5数量：%s，不符合要求的人数：%s，详情如下：%s",ccmList.size(),count,JsonMapper.toJsonString(extInfo));
			hitRule.setRemark(msg);
		}else{
			setHitNum(1);
			String msg =ccmList==null?"常用联系人为空":"常用联系人数量不足5人："+ccmList.size();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}

}
