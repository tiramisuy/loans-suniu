package com.rongdu.loans.risk.executor;

import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * 单位名称含“金融”“贷款”“小贷”关键字，数据来源于：申请表
 * @author sunda
 * @version 2017-08-14
 */
public class R10030020Executor extends Executor {

	@Override
	public void init(){
		super.setRuleId(RuleIds.R10030020);
	}

	@Override
	public void doExecute(AutoApproveContext context) {
		UserInfoVO userInfo = context.getUserInfo();
		String companyName = userInfo.getComName();

		//命中的规则
		HitRule hitRule = checkCompanyName(companyName);
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
	 * 检查申请人工作单位是否符合要求
	 * @param str
	 * @return
	 */
	private HitRule checkCompanyName(String str) {
		HitRule hitRule = createHitRule(getRiskRule());
		//申请受限的地区
		String[] array = {"金融","贷款","小贷"};
		List<String> restrictedArea = Arrays.asList(array);
		if (AutoApproveUtils.containsAny(str,restrictedArea)){
			setHitNum(1);
		}
		hitRule.setValue(str);
		hitRule.setRemark(str);
		return  hitRule;
	}

}
