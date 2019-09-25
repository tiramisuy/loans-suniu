package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 运营商手机号使用时长较短
 * 数据来源于：白骑士资信云报告数据
 * @author liuzhuang
 * @version 2018-03-27
 */
public class R10030007Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030007);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
//		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().get("numberUsedLong");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getNumberUsedLong();
		//命中的规则
		HitRule hitRule = checkMobileUsedLong(crossValidation);
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
	 * 运营商手机号使用时长是否少于N天
	 * "numberUsedLong": {
	 * "evidence": "使用记录至今至少172天",
	 * "inspectionItems": "号码使用时长",
	 * "result": "至少172天"
     */
	private HitRule checkMobileUsedLong(ReportCrossValidationDetail crossValidation) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 120;
		if (crossValidation!=null&&StringUtils.isNotBlank(crossValidation.getResult())){
			String result = crossValidation.getResult();
			int pastDays = AutoApproveUtils.extractNumFromString(result);
			if (pastDays<threshold){
				setHitNum(1);
			}
			String msg = crossValidation.getEvidence();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}

}
