package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.Calendar;
import java.util.Date;

/**
 * 规则名称：借款人年龄小于等于40
 */
public class AT005Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        CustUserVO user = context.getUser();
        String birthday = user.getBirthday();
        int age = getAgeByBirthday(birthday);
        int hitNum = 0;
        String evidence = String.format("出生日期：%s，年龄：%s", birthday, age);
        RiskRule riskRule = getRiskRule();
        if (age < 22 || age > 40) {
            addHitNum(1);
            HitRule hitRule = createHitRule(riskRule);
            hitRule.setValue(String.valueOf(age));
            hitRule.setRemark(evidence);
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);
    }

    /**
     * 根据出生年月获取年龄
     */
    public static int getAgeByBirthday(String birthday) {
        int age = 0;
        Date birthdayDate = DateUtils.parseDate(birthday);
        if (birthdayDate != null) {
            Calendar birthdayCalendar = Calendar.getInstance();
            birthdayCalendar.setTime(birthdayDate);
            Calendar calendar = Calendar.getInstance();
            age = calendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
        }
        return age;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT005);
    }
}
