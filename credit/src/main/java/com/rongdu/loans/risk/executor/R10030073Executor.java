package com.rongdu.loans.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.Arrays;
import java.util.List;

/**
 * @version V1.0
 * @Description: 申请人身份证归属省份及城市不符
 * @author: yuanxianchu
 * @date 2018年10月11日
 */
public class R10030073Executor extends Executor {

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
        //西藏,新疆,青海,港澳台,海外,河北省丰宁县,江西省余干县,湖南省双峰县,广东茂名电白区,广西省宾阳县,海南儋州市,福建龙岩市新罗区,福建省安溪县,福建省永春县
        String[] array = {"西藏", "新疆", "青海", "港澳台", "海外", "河北省丰宁县", "江西省余干县", "湖南省双峰县", "广东茂名电白区", "广西省宾阳县", "海南儋州市", "福建龙岩市新罗区", "福建省安溪县", "福建省永春县"};
        HitRule hitRule = createHitRule(getRiskRule());
        // 加载风险分析数据
        String idAddress = context.getUserInfo().getRegAddr();
        List<String> list1 = Arrays.asList(array);
        for (int i = 0; i < list1.size(); i++) {
            if (idAddress.contains(list1.get(i))) {
                setHitNum(1);
                break;
            }
        }
        hitRule.setRemark("申请人身份证地址：" + idAddress);
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030073);
    }

}
