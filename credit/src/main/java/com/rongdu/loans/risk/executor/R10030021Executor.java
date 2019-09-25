package com.rongdu.loans.risk.executor;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.common.IkanalyzerUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 工作城市与居住城市不一致
 * 数据来源于：魔蝎
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030021Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030021);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        context.initContext();
        //加载风险分析数据
        String workCity = context.getUserInfo().getWorkCity();
        String resideCity = context.getUserInfo().getResideCity();

        //命中的规则
        HitRule hitRule = checkWorkCityAndResideCity(workCity, resideCity);
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
     * 工作城市与居住城市不一致
     *
     * @param workCity
     * @param resideCity
     * @return
     */
    private HitRule checkWorkCityAndResideCity(String workCity, String resideCity) {
        HitRule hitRule = createHitRule(getRiskRule());
        String msg = "";
        if (StringUtils.isBlank(workCity) || StringUtils.isBlank(resideCity)) {
            msg = String.format("工作城市：%s，居住城市：%s，相似度：%s", workCity, resideCity, 0);
        } else {
            double similarity = IkanalyzerUtils.getSimilarity(workCity, resideCity);
            double minSimilarity = 0.80D;
            if (StringUtils.isAnyBlank(workCity, resideCity) || similarity < minSimilarity) {
                setHitNum(1);
            }
            hitRule.setValue(String.valueOf(similarity));
            msg = String.format("工作城市：%s，居住城市：%s，相似度：%s", workCity, resideCity, similarity);
        }

        hitRule.setRemark(msg);
        return hitRule;
    }


}
