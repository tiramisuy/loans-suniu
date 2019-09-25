package com.rongdu.loans.risk.executor;

import java.util.Date;
import java.util.List;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 近半年常用联系人Top10平均通话时长较短（拒绝）
 * 数据来源于：白骑士资信云报告数据
 * @author liuzhuang
 * @version 2018-01-03
 */
public class R10030054Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030054);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
		int topNum=20;
		List<ReportMnoCcm> ccmList = AutoApproveUtils.getTopCcmList(vo.getData().getMnoCommonlyConnectMobiles(), topNum);
		
		//命中的规则
		HitRule hitRule = checkAverageConnectTime(ccmList,topNum);
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
	 * 近半年常用联系人Top20中，至少有一人平均通话时长>=90秒，并且最后和最早联系时间间隔>=90天
	 * @param contactList
	 * @param ccmList
	 * @return
	 */
	private HitRule checkAverageConnectTime(List<ReportMnoCcm> ccmList,int topNum) {
		HitRule hitRule = createHitRule(getRiskRule());
		int minAverageConnectTime=90;
		int minDuration=90;
		int count = 0;
		if (ccmList!=null&&ccmList.size()<=topNum){
			int averageConnectTime = 0;//平均通话时长
			int duration = 0;	//通话间隔（天）
			for (ReportMnoCcm ccm:ccmList){
				averageConnectTime = ccm.getConnectCount()>0?ccm.getConnectTime()/ccm.getConnectCount():0;
				Date beginTime=new Date(new Long(ccm.getBeginTime()));
				Date endTime=new Date(new Long(ccm.getEndTime()));
				duration = DateUtils.daysBetween(beginTime,endTime);
				if(averageConnectTime>=minAverageConnectTime && duration>=minDuration){
					count++;
					break;
				}
			}
			if (count==0){
				setHitNum(1);
			}
			String msg = String.format("近半年常用联系人平均通话时长全部不符合要求");
			hitRule.setRemark(msg);
		}else{
			setHitNum(1);
			String msg =ccmList==null?"常用联系人为空":"常用联系人数量不足："+ccmList.size();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}
	

}
