package com.rongdu.loans.risk.executor.xjbk;

import java.util.List;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.xjbk.Calls;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**  
* @Title: R10030063Executor.java  
* @Package com.rongdu.loans.risk.executor.xjbk  
* @Description: 紧急联系人在通话详单中的个数<1个 
* @author: yuanxianchu  
* @date 2018年10月10日  
* @version V1.0  
*/
public class R10030063Executor extends Executor {

	@Override
	public void doExecute(AutoApproveContext context) {
		// 命中的规则
		HitRule hitRule = checkRule(context);
		// 决策依据
		String evidence = hitRule.getRemark();
		// 命中规则的数量
		int hitNum = getHitNum();
		if (hitNum > 0) {
			addHitRule(context, hitRule);
		}
		logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
				context.getApplyId(), hitNum, evidence);
	}

	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());
		// 加载风险分析数据
		XianJinBaiKaCommonOP xianJinBaiKaBase = getDataInvokeService().getXianJinBaiKaBase(context);
		XianJinBaiKaCommonOP xianJinBaiKaAdditional = getDataInvokeService().getXianJinBaiKaAdditional(context);
		String mobile = xianJinBaiKaAdditional.getUser_additional().getContactInfo().getMobile();//第一联系人手机号
		String mobileSpare = xianJinBaiKaAdditional.getUser_additional().getContactInfo().getMobileSpare();//第二联系人手机号
		List<Calls> calls = xianJinBaiKaBase.getUser_verify().getOperatorVerify().getCalls();
		boolean result = false;
		if (!StringUtils.isBlank(mobile) && !StringUtils.isBlank(mobileSpare)) {
			for (Calls calls2 : calls) {
				if (mobile.equals(calls2.getOtherCellPhone()) || mobileSpare.equals(calls2.getOtherCellPhone())) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			setHitNum(1);
		}
		hitRule.setRemark("紧急联系人在通话详单中的个数不符合条件");
		return hitRule;
	}

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030063);
	}

}
