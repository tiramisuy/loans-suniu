package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 平时较少主动与外界通话
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030014Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030014);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
//		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().get("originatingMobileCount");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getOriginatingMobileCount();

		//命中的规则
		HitRule hitRule = checkOriginatingMobileCount(crossValidation);
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
	 * 运营商 拨出电话号码个数＜20
	 * @param crossValidation
	 * @return
	 */
	private HitRule checkOriginatingMobileCount(ReportCrossValidationDetail crossValidation) {
		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 20;
		if (crossValidation!=null&& StringUtils.isNotBlank(crossValidation.getResult())){
			String resultString = crossValidation.getResult();
			int originatingMobileCount = AutoApproveUtils.extractNumFromString(resultString);
			if (originatingMobileCount<threshold){
				setHitNum(1);
				hitRule.setValue(String.valueOf(originatingMobileCount));
			}
			String msg = crossValidation.getEvidence();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}
}
