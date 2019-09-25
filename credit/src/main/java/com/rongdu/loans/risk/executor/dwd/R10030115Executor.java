package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 通讯录电话号码小于等于10个
 *
 * @author fy
 * @version 2019-05-27
 */
public class R10030115Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030115);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 获取申请人设备通讯录
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        if (phoneList == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(phoneList);
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
     * 通讯录电话号码小于等于10个
     *
     * @param phoneList
     * @return
     */
    private HitRule checkRule(List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        if (phoneList.size() <= 10) {
            setHitNum(1);
            remark = String.format("通讯录电话号码小于等于10个，通讯录电话号码数：%s", phoneList.size());
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
