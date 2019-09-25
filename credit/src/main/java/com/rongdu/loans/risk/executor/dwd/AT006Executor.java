package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.List;

/**
 * 规则名称：通讯录名单个数大于等于30
 */
public class AT006Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();

        int hitNum = 0;
        RiskRule riskRule = getRiskRule();
        int size = phoneList == null ? 0 : phoneList.size();
        String evidence = "通讯录名单个数:" + size;
        if (size < 30) {
            addHitNum(1);
            HitRule hitRule = createHitRule(riskRule);
            hitRule.setValue(String.valueOf(size));
            hitRule.setRemark(evidence);
            addHitRule(context, hitRule);
        }

        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT006);
    }
}
