package com.rongdu.loans.risk.executor;

import java.util.List;

import com.rongdu.loans.baiqishi.vo.MnoCuiShouInfo;
import com.rongdu.loans.baiqishi.vo.MnoCuiShouInfoDetail;
import com.rongdu.loans.baiqishi.vo.ReportDataVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 催收类号码通话过多 数据来源于：白骑士资信报告
 * 
 * @author liuzhuang
 * @version 2017-11-13
 */
public class R10030050Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030050);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		ReportDataVO vo = getDataInvokeService().getBaishiqiReportData(context);
		MnoCuiShouInfo mnoCuiShouInfo = vo.getData().getMnoCuiShouInfo();
		if (mnoCuiShouInfo == null)
			return;
		List<MnoCuiShouInfoDetail> dunnings= mnoCuiShouInfo.getDunnings();
		if (dunnings == null || dunnings.size() == 0)
			return;
		// 命中的规则
		HitRule hitRule = checkDunnings(dunnings);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
				getRuleName(), context.getUserName(), context.getApplyId(),
				getHitNum(), evidence);
	}

	/**
	 * 近1周               通话号码数>=3 或者 通话次数>=9
	 * 近2周               通话号码数>=6 或者 通话次数>=12
	 * 近3周               通话号码数>=6 或者 通话次数>=18
	 * 近1个月          通话号码数>=6 或者 通话次数>=20
	 * 30天-60天    通话号码数>=4 或者 通话次数>=12
	 * 60天-90天    通话号码数>=4 或者 通话次数>=12
	 * 近6个月          通话号码数>=25 或者 通话次数>=50
	 * @return
	 */
	private HitRule checkDunnings(List<MnoCuiShouInfoDetail> dunnings) {
		HitRule hitRule = createHitRule(getRiskRule());
		String remark=null;
		for (MnoCuiShouInfoDetail d : dunnings) {
			boolean isHit=false;
			if ("近1周".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 3 || d.getConnectCount() >= 9) {
					isHit=true;
				}
			}else if ("近2周".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 6 || d.getConnectCount() >= 12) {
					isHit=true;
				}
			}else if ("近3周".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 6 || d.getConnectCount() >= 18) {
					isHit=true;
				}
			}else if ("近1个月".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 6 || d.getConnectCount() >= 20) {
					isHit=true;
				}
			}else if ("30天-60天".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 4 || d.getConnectCount() >= 12) {
					isHit=true;
				}
			}else if ("60天-90天".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 4 || d.getConnectCount() >= 12) {
					isHit=true;
				}
			}else if ("近6个月".equals(d.getPeriodType())) {
				if (d.getConnectMobileSize() >= 25 || d.getConnectCount() >= 50) {
					isHit=true;
				}
			}
			if(isHit){
				remark = String.format("%s,通话号码数：%s,通话次数: %s",d.getPeriodType(),d.getConnectMobileSize(), d.getConnectCount());
				break;
			}
		}
		if (remark!=null) {
			setHitNum(1);
		}
		hitRule.setRemark(remark);
		return hitRule;
	}
}