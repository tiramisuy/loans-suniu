package com.rongdu.loans.risk.executor.jubao;

import java.util.Date;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.rong360Model.OrderBaseInfo;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

/**
 * 运营商手机号入网时长较短
 * 数据来源于：白骑士资信云报告数据
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030006Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030006);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        //加载风险分析数据
    	OrderBaseInfo orderBaseInfo = getDataInvokeService().getRongBase(context);
    	//入网时间
    	String regTime = orderBaseInfo.getAddinfo().getMobile().getUser().getRegTime();
        //命中的规则
        HitRule hitRule = checkMobileOpenTime(regTime);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(), getRuleName(), context.getUserName(), context.getApplyId(), hitNum, evidence);

    }

    /**
     * 运营商手机号入网时长是否少于N天
     * "openTime": {
     * "evidence": "本号码于2011-03-12 08:34:05开始使用，至今已使用5年302天",
     * inspectionItems": "入网时间",
     * "result": "2011-03-12 08:34:05"}
     */
    private HitRule checkMobileOpenTime(String regTime) {
        HitRule hitRule = createHitRule(getRiskRule());
        int threshold = 80;
        if (regTime != null && StringUtils.isNotBlank(regTime)) {
            Date date = DateUtils.parseDate(regTime);
            int pastDays = (int) DateUtils.pastDays(date);
            if (pastDays < threshold) {
                setHitNum(1);
            }
            hitRule.setRemark("入网时间少于80天");
        }
        return hitRule;
    }


}
