package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.BlacklistService;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录命中本地黑名单人数大于等于2
 *
 * @author fy
 * @version 2019-06-17
 */
public class R10030132Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030132);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        if (phoneList == null) {
            return;
        }
        // 命中的规则
        HitRule hitRule = hitBlackList(phoneList);
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
     * 通讯录命中本地黑名单人数大于等于2
     *
     * @return
     */
    private HitRule hitBlackList(List<PhoneList> phoneList) {
        HitRule hitRule = createHitRule(getRiskRule());
        BlacklistService blacklistService = SpringContextHolder.getBean("blacklistService");
        List<String> mobiles = new ArrayList<>();
        for (PhoneList phone : phoneList) {
            mobiles.add(phone.getPhone());
        }
        int num = blacklistService.getBlacklistByPhoneList(mobiles);
        if (num >= 2) {
            hitRule.setRemark("通讯录命中本地黑名单人数大于等于2");
            setHitNum(1);
            return hitRule;
        }
        return hitRule;
    }

}
