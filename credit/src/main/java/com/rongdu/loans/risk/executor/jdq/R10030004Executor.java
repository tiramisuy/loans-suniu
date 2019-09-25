package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.loans.loan.option.jdq.AddressBook;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备通讯录手机号码较少 数据来源于：jdq基础信息
 *
 * @author hbx
 * @version 2017-08-14
 */
public class R10030004Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030004);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
//		// 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBaseAdd(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null) {
            return;
        }
        List<AddressBook> addressBooks = vo.getAddressBook();
        // 命中的规则
        HitRule hitRule = checkRule(addressBooks);
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
     * 设备通讯录11位手机号码联系人且不重复的人数<10人，数据来源于：本地设备通讯录
     *
     * @param list
     * @return
     */
    private HitRule checkRule(List<AddressBook> list) {
        HitRule hitRule = createHitRule(getRiskRule());
        if (list == null) {
            setHitNum(1);
            hitRule.setRemark("设备通讯录为空");
            return hitRule;
        }
        // 手机号码数量
        int count = 0;
        int threshold = 10;
        String mobile = null;
        List<String> mobileList = new ArrayList<String>();
        for (AddressBook item : list) {
            mobile = AutoApproveUtils.parseContactMobile(item.getMobile());
            if (AutoApproveUtils.isMobile(mobile) && !mobileList.contains(mobile)) {
                mobileList.add(mobile);
                count++;
            }
        }
        if (count < threshold && count > 0) {
            setHitNum(1);
            hitRule.setValue(String.valueOf(count));
        }
        String msg = String.format("电话号码总数：%s，手机号码数量：%s", list.size(), count);
        hitRule.setRemark(msg);
        return hitRule;
    }

}
