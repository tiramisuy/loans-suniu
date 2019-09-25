package com.rongdu.loans.risk.executor.xjbk;

import java.util.List;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.linkface.vo.IdnumberVerificationVO;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 
* @Description:  人脸识别失败
 * 数据来源于：linkface
* @author: 饶文彪
* @date 2018年6月27日 下午4:08:19
 */
public class R10030059Executor extends Executor {

	@Override
	public void init() {
		super.setRuleId(RuleIds.R10030059);
	}

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

	/**
	 * 判断置信度
	 * @param context
	 * @return
	 */
	private HitRule checkRule(AutoApproveContext context) {
		HitRule hitRule = createHitRule(getRiskRule());

		IdnumberVerificationVO idnumberVerificationVO = getDataInvokeService().getLinkfaceIdnumberVerification(context);
		Float confidence = null;
		String status = "";

		if (idnumberVerificationVO != null) {
			confidence = idnumberVerificationVO.getConfidence();
			status = idnumberVerificationVO.getStatus();

			if (idnumberVerificationVO.isSuccess() && confidence != null && confidence < 0.5) {// 置信度小于0.5
				setHitNum(1);
			}
		}

		hitRule.setRemark(String.format("返回状态:%s;置信度:%s", status, confidence));

		return hitRule;
	}

}
