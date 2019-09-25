package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Date;

/**
 * 运营商手机号入网时长较短
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030006Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030006);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
//		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().get("openTime");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getOpenTime();
		
		//命中的规则
		HitRule hitRule = checkMobileOpenTime(crossValidation);
		//决策依据
		String evidence = hitRule.getRemark();
		//命中规则的数量
		int hitNum = getHitNum();
		if (hitNum>0){
			addHitRule(context,hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
				getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),hitNum,evidence);

	}

	/**
	 * 运营商手机号入网时长是否少于N天
	 * "openTime": {
	 * "evidence": "本号码于2011-03-12 08:34:05开始使用，至今已使用5年302天",
	 * inspectionItems": "入网时间",
	 * "result": "2011-03-12 08:34:05"}
     */
	private HitRule checkMobileOpenTime(ReportCrossValidationDetail crossValidation) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 180;
		if (crossValidation!=null&&StringUtils.isNotBlank(crossValidation.getResult())){
			Date date = DateUtils.parseDate(crossValidation.getResult());
			int pastDays = (int)DateUtils.pastDays(date);
			if (pastDays<threshold){
				setHitNum(1);
			}
			hitRule.setRemark(crossValidation.getEvidence());
		}
		return hitRule;
	}


}
