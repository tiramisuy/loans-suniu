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
 * 资金饥渴急需贷款，或者贷款逾期
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030013Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030013);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		CreditDataInvokeService creditDataInvokeService = SpringContextHolder.getBean("creditDataInvokeService");
		ReportDataVO vo = creditDataInvokeService.getBaishiqiReportData(context);
//		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().get("p2pConnectInfo");
		ReportCrossValidationDetail crossValidation = vo.getData().getCrossValidation().getP2pConnectInfo();

		//命中的规则
		HitRule hitRule = checkP2pConnectInfo(crossValidation);
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
	 * 运营商贷款类号码通话使用情况＞20
	 * @param crossValidation
	 * @return
	 */
	private HitRule checkP2pConnectInfo(ReportCrossValidationDetail crossValidation) {

		HitRule hitRule = createHitRule(getRiskRule());
		int threshold = 20;
		if (crossValidation!=null&& StringUtils.isNotBlank(crossValidation.getResult())){
			String resultString = crossValidation.getResult();
			int p2pConnectCount = AutoApproveUtils.extractNumFromString(resultString);
			if (p2pConnectCount>threshold){
				setHitNum(1);
				hitRule.setValue(String.valueOf(p2pConnectCount));
			}
			String msg = crossValidation.getEvidence();
			hitRule.setRemark(msg);
		}
		return  hitRule;
	}

}
