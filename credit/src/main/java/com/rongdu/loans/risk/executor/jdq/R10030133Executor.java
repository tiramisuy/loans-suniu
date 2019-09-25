package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * 进件单位不符合含有敏感词
 *
 * @author fy
 * @version 2019-07-04
 */
public class R10030133Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030133);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        UserInfoVO userInfo = context.getUserInfo();
        if (userInfo == null) {
            return;
        }
        //命中的规则
        HitRule hitRule = checkRule(userInfo);
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
     * 设备通讯录联系含有敏感词
     *
     * @param userInfo
     * @return
     */
    private HitRule checkRule(UserInfoVO userInfo) {
        HitRule hitRule = createHitRule(getRiskRule());
        String[] array = {"公安局", "派出所", "检察院", "法院", "律师", "部队"};
        List workList = Arrays.asList(array);
        if (containsAny(userInfo.getComName(), workList)) {
            setHitNum(1);
        }
        hitRule.setValue(String.valueOf(userInfo.getComName()));
        String msg = String.format("匹配详情如下：%s", JsonMapper.toJsonString(userInfo.getComName()));
        hitRule.setRemark(msg);
        return hitRule;
    }

    private boolean containsAny(String name, List<String> list) {
        boolean match = false;
        for (String item : list) {
            if (StringUtils.contains(name, item)) {
                return true;
            }
        }
        return match;
    }


}
