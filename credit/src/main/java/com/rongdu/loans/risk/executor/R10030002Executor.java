package com.rongdu.loans.risk.executor;

import com.rongdu.loans.baiqishi.entity.ReportWebDataSource;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 手机号未实名认证
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030002Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030002);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<ReportWebDataSource> webDataSources = vo.getData().getWebDataSources();

		//命中的规则
		HitRule hitRule = checkMobileRealName(webDataSources);
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
	 * 判断手机号码是否经过实名认证
	 * @param webDataSources
	 * @return
     */
	private HitRule checkMobileRealName(List<ReportWebDataSource> webDataSources) {
		HitRule hitRule =  createHitRule(getRiskRule());
		for (ReportWebDataSource item:webDataSources){
			if ("运营商".equals(item.getSourceType())){
				String remark = String.format("passRealName：%s，realNameInfo：%s",item.getPassRealName(),item.getRealNameInfo());
				hitRule.setRemark(remark);
				if (!item.getPassRealName()){
					addHitNum(1);
					return hitRule;
				}
			}
		}
		return  hitRule;
	}


}
