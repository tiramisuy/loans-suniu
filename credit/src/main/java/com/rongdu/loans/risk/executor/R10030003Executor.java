package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.baiqishi.entity.ReportWebDataSource;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 手机号实名与申请人身份证名字不一致
 * 数据来源于：白骑士资信云报告数据
 * @author sunda
 * @version 2017-08-14
 */
public class R10030003Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030003);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		//加载风险分析数据
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		List<ReportWebDataSource> webDataSources = vo.getData().getWebDataSources();
		String name = context.getUser().getRealName();
		String idNo = context.getUser().getIdNo();
		//命中的规则
		HitRule hitRule = checkPetitionerName(webDataSources,idNo,name);
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
	 * 判断手机号实名与申请人身份证名字是否一致
	 * A、如果 白骑士运营商实名信息 返回的结果有  身份证号码，就对比  我们这边的 身份证号码
	 * B、如果 白骑士运营商实名信息 返回的结果没有  身份证号码，就直接取  白骑士的 结果值 “是”和 “否”
	 * @param webDataSources
	 * @param idNo
	 * @param name
     * @return
     */
	private HitRule checkPetitionerName(List<ReportWebDataSource> webDataSources,String idNo,String name) {
		HitRule hitRule = createHitRule(getRiskRule());
		for (ReportWebDataSource item : webDataSources) {
			if ("运营商".equals(item.getSourceType())) {
				String remark = String.format("realNameInfo：%s,equalToPetitioner：%s", item.getRealNameInfo(), item.getEqualToPetitioner());
				hitRule.setRemark(remark);
//				String mnoName = item.getRealNameInfo().replaceAll("[^\\u4e00-\\u9fa5]", "");
//				if (!StringUtils.contains(item.getRealNameInfo(), idNo) && !StringUtils.contains(name, mnoName) && !item.getEqualToPetitioner()) {
//					setHitNum(1);
//					return hitRule;
//				}
				if (!item.getEqualToPetitioner()) {
					setHitNum(1);
					return hitRule;
				}
			}
		}
		return hitRule;
	}

}
