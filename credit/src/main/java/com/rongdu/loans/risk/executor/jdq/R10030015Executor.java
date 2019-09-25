package com.rongdu.loans.risk.executor.jdq;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.jdq.JDQUrgentContact;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 紧急联系人都不在申请人工作所在地
 * 数据来源于：借点钱报告
 *
 * @author sunda
 * @version 2017-08-14
 */
public class R10030015Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030015);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载风险分析数据
        CreditDataInvokeService creditDataInvokeService = getDataInvokeService();
        // 紧急联系人号码
        List<JDQUrgentContact> urgentcontact = creditDataInvokeService.getjdqReport(context).getUrgentcontact();
        // 工作所在地
        String workArea = context.getUserInfo().getWorkAddr();
        //命中的规则
        HitRule hitRule = checkEmergencyContacts(workArea, urgentcontact);
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
     * 申请人填写的常用联系人手机号码归属地没有一位在申请人手机号码所在地

     * @return
     */
    private HitRule checkEmergencyContacts(String workArea, List<JDQUrgentContact> urgentcontact) {
        HitRule hitRule = createHitRule(getRiskRule());
        int count = 0;
        int threshold = 1;
        String connectCity = null;
        Map<String, String> extInfo = new LinkedHashMap<>();
        String city = null;
        // 获取工作城市 字段模板: 上海,上海市,杨浦区|政府路14号  取上海市
        if (StringUtils.isNotBlank(workArea)){
            String substring = workArea.substring(0, workArea.indexOf("|"));
            if (StringUtils.isNotBlank(substring)){
                String s = substring.split(",")[1];
                if (StringUtils.isNotBlank(s)){
                    city = AutoApproveUtils.filterArea(s);
                }
            }
        }
        if (urgentcontact != null && StringUtils.isNotBlank(city)) {
           for (JDQUrgentContact item : urgentcontact) {
                connectCity = item.getPhoneNumLoc();
                connectCity = AutoApproveUtils.filterArea(connectCity);
                extInfo.put(item.getMobile(), item.getPhoneNumLoc());
                if (StringUtils.contains(workArea, connectCity)) {
                    count++;
                }
            }
            if (count < threshold) {
                setHitNum(1);
                hitRule.setValue(String.valueOf(count));
            }
            String msg = String.format("紧急联系人数量：%s，工作所在地：%s，匹配数量：%s，紧急联系人详情如下：%s", urgentcontact.size(), workArea, count, JsonMapper.toJsonString(extInfo));
            hitRule.setRemark(msg);

        } else {
            setHitNum(1);
            hitRule.setValue(String.valueOf(count));
            String msg = (urgentcontact == null) ? "紧急联系人为空" : "紧急联系人数量为：" + urgentcontact.size();
            hitRule.setRemark(msg);
        }
        return hitRule;
    }


}
