package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.baiqishi.entity.ReportMnoMui;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 近半年内某一个月主叫较少
 * 数据来源于：魔蝎
 * @author sunda
 * @version 2017-08-14
 */
public class R10030025Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030025);
	}


	@Override
	public void doExecute(AutoApproveContext context) {
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

		//命中的规则
		HitRule hitRule = checkTeleCallCount(vo.getMoxieTelecomReport().cell_behavior.get(0).behavior);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(), getRuleName(), context.getUserName(), context.getApplyId(), getHitNum(), evidence);
	}


	/**
	 * 运营商近半年内某一个月主叫次数为0
	 *
	 * @param muiList
	 * @return
	 */
	private HitRule checkTeleCallCount(List<MoxieTelecomReport.CellBehaviorBean.BehaviorBean> muiList) {
		HitRule hitRule = createHitRule(getRiskRule());
		String noteTime = "";
		for (MoxieTelecomReport.CellBehaviorBean.BehaviorBean behaviorBean : muiList) {
			//若某个月为0
			if (behaviorBean.dial_cnt <= 0) {
				noteTime = behaviorBean.cell_mth;
				setHitNum(1);
				break;
			}
		}
		String msg = String.format("运营商近半年内某一个月主叫次数为0:%s", noteTime);
		hitRule.setRemark(msg);
		return hitRule;
	}

//	@Override
//	public void doExecute(AutoApproveContext context) {
//		//加载风险分析数据
//		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
//		List<ReportMnoMui> muiList = vo.getData().getMnoMonthUsedInfos();
//		//白骑士可能反馈超过6个月的数据，我们只取近6个月的数据
//		List<ReportMnoMui> topList = AutoApproveUtils.getTopList(muiList,6);
//		String applyTime = context.getApplyInfo().getApplyTime();
//
//		//命中的规则
//		HitRule hitRule = checkOriginatingCallCount(topList,applyTime);
//		//决策依据
//		String evidence = hitRule.getRemark();
//		//命中规则的数量
//		int hitNum = getHitNum();
//		if (hitNum>0){
//			addHitRule(context,hitRule);
//		}
//		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
//				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
//	}
//
//	/**
//	 * 运营商近半年内某一个月主叫次数为0
//	 * @param muiList
//	 * @return
//     */
//	private HitRule checkOriginatingCallCount(List<ReportMnoMui> muiList,String applyTimeSting) {
//		HitRule hitRule = createHitRule(getRiskRule());
//		//通话次数
//		int originatingCallCount = 0;
//		int minOriginatingCallCount = 1;
//		int count = 0;
//		Map<String,Integer> extInfo = new LinkedHashMap<>();
//		Date month = null;
//		long millis = AutoApproveUtils.getMonthMillis(applyTimeSting);
//		for (ReportMnoMui item:muiList){
//			month = DateUtils.parseDate(item.getMonth());
//			if (month!=null&&month.getTime()<millis){
//				originatingCallCount = item.getOriginatingCallCount();
//				extInfo.put(item.getMonth(),originatingCallCount);
//				if (originatingCallCount<minOriginatingCallCount){
//					count++;
//				}
//			}
//		}
//		if (count>0){
//			setHitNum(1);
//		}
//		hitRule.setValue(String.valueOf(count));
//		String msg = String.format("统计的月份：%s，匹配数量：%s，详情如下：%s",muiList.size(),count,JsonMapper.toJsonString(extInfo));
//		hitRule.setRemark(msg);
//		return  hitRule;
//	}


}
