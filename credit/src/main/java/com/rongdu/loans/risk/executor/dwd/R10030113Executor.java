package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录异号段占比情况较少
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030113Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030113);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        List<PhoneList> phoneList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();
        if (phoneList == null) {
            return;
        }
        //命中的规则
        HitRule hitRule = checkContactsInfo(phoneList);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(), getRuleName(), context.getUserName(), context.getApplyId(), getHitNum(), evidence);
    }

    /**
     * 申请客户通讯录异号段（手机号码前六位不相同）占比<=30%
     *
     * @param list
     * @return
     */
    private HitRule checkContactsInfo(List<PhoneList> list) {
        HitRule hitRule = createHitRule(getRiskRule());
        // 手机号码前六位集合
        Integer num = list.size();
        List<String> phoneList = new ArrayList<>();
        for (PhoneList phone : list) {
            if (phone != null && StringUtils.isNotBlank(phone.getPhone())){
                if (phone.getPhone().length() > 6) {
                    phoneList.add(phone.getPhone().substring(0, 6));
                } else {
                    phoneList.add(phone.getPhone());
                }
            }
        }
        // 去重后集合
        for (int i = 0; i < phoneList.size() - 1; i++) {
            for (int j = phoneList.size() - 1; j > i; j--) {
                if (phoneList.get(j).equals(phoneList.get(i))) {
                    phoneList.remove(j);
                }
            }
        }
        Integer numClear = phoneList.size();
        // 执行规则
        if ((Double.valueOf(numClear) / Double.valueOf(num)) < 0.3D) {
            setHitNum(1);
            String remark = String.format("申请客户通讯录异号段（手机号码前六位不相同）占比<=0.3 ,占比：%s", (Double.valueOf(numClear) / Double.valueOf(num)));
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

}
