package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calincntlistv;
import com.rongdu.loans.loan.option.jdq.report.Calloutcntlistv;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;


/**
 * 规则名称：紧急联系人
 */
public class AT015Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        DWDReport report = getDataInvokeService().getdwdReport(context);
        // 加载用户紧急联系人数据
        List<CustContactVO> contactList = context.getUserInfo().getContactList();
        //命中的规则
        HitRule hitRule = checkRule(report,contactList);
        //决策依据
        String evidence = hitRule.getRemark();
        //命中规则的数量
        int hitNum = getHitNum();
        if (hitNum>0){
            addHitRule(context,hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}",
                getRuleId(),getRuleName(),context.getUserName(),context.getApplyId(),getHitNum(),evidence);
    }

    /**
     * 借款人填写的紧急联系人有一个满足通话次数大于0
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report ,List<CustContactVO> contactList) {
        HitRule hitRule = createHitRule(getRiskRule());
        int countIndex = 0;
        // 执行规则逻辑
        if (report.getCalloutcntlistv() != null) {
            // 获取紧急联系人手机号码
            List<Calloutcntlistv> contactListReports = report.getCalloutcntlistv();

            for (CustContactVO custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getMobile())) {
                    for (Calloutcntlistv contact : contactListReports) {
                        // 执行判断
                        if (custContactVO.getMobile().contains(contact.getMobile())||contact.getMobile().contains(custContactVO.getMobile())) {
                            countIndex++;
                            return hitRule;
                        }
                    }
                }
            }
        }
        if (report.getCalincntlistv() != null) {
            // 获取紧急联系人手机号码
            List<Calincntlistv> contactListReports = report.getCalincntlistv();

            for (CustContactVO custContactVO : contactList) {
                if (StringUtils.isNotBlank(custContactVO.getMobile())) {
                    for (Calincntlistv contact : contactListReports) {
                        // 执行判断
                        if (custContactVO.getMobile().contains(contact.getMobile())||contact.getMobile().contains(custContactVO.getMobile())) {
                            countIndex++;
                            return hitRule;
                        }
                    }
                }
            }
        }

        if (countIndex == 0) {
            setHitNum(1);
        }

        String msg = String.format("紧密联系人不在呼入或者呼出TOP20！");
        hitRule.setRemark(msg);
        return hitRule;
    }

//    /**
//     * 借款人填写的紧急联系人有一个满足首次呼入至末次呼入间隔大于等于120天，且首次呼出至末次呼出间隔大于等于120天
//     * @param report
//     * @return
//     */
//    private HitRule checkRule(DWDReport report) {
//        HitRule hitRule = createHitRule(getRiskRule());
//        String remark = null;
//
//        if (report != null && CollectionUtils.isNotEmpty(report.getUrgentcontact()) ) {
//            int count = 0;
//            for (JDQUrgentContact urgentContact : report.getUrgentcontact()) {
//                if(StringUtils.isNotBlank(urgentContact.getFirstCallIn())
//                        && StringUtils.isNotBlank(urgentContact.getFirstCallOut())
//                        && StringUtils.isNotBlank(urgentContact.getLastCallIn())
//                        && StringUtils.isNotBlank(urgentContact.getLastCallOut())){
//                    Date firstCallIn =  DateUtils.parse(urgentContact.getFirstCallIn(), DateUtils.FORMAT_LONG);
//                    Date firstCallOut = DateUtils.parse(urgentContact.getFirstCallOut(), DateUtils.FORMAT_LONG);
//                    Date lastCallIn =  DateUtils.parse(urgentContact.getLastCallIn(), DateUtils.FORMAT_LONG);
//                    Date lastCallOut = DateUtils.parse(urgentContact.getLastCallOut(), DateUtils.FORMAT_LONG);
//                    if(DateUtils.daysBetween(firstCallIn, lastCallIn) >= 120 && DateUtils.daysBetween(firstCallOut, lastCallOut) >= 120){
//                        count++;
//                    }
//                }
//            }
//            if (count == 0) {
//                setHitNum(1);
//                remark = "借款人填写的紧急联系人没有一个满足首次呼入至末次呼入间隔大于等于120天，且首次呼出至末次呼出间隔大于等于120天";
//                hitRule.setRemark(remark);
//            }
//        } else {
//            setHitNum(1);
//            remark = "紧急联系人为空";
//            hitRule.setRemark(remark);
//        }
//        return hitRule;
//    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT015);
    }
}
