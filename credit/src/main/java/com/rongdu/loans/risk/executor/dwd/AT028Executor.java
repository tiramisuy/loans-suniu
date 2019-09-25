package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.zhicheng.message.LoanRecord;
import com.rongdu.loans.zhicheng.vo.CreditInfoVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * 规则名称：宜信阿福近1个月借款记录
 */
public class AT028Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {

        CreditInfoVO vo = getDataInvokeService().getZhichengCreditInfo(context);
        if (null == vo || null == vo.getParams()){
            return;
        }
        // 命中的规则
        HitRule hitRule = checkRule(vo);
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
     * 宜信阿福近1,3,12个月借款记录
     *
     * @param vo
     * @return
     */
    private HitRule checkRule(CreditInfoVO vo) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;
        // 执行规则逻辑
        if (vo.getParams() != null && vo.getParams().getData() != null && vo.getParams().getData().getLoanRecords() != null){
            Predicate<LoanRecord> p1 = s -> "201".equals(s.getApprovalStatusCode());
            Predicate<LoanRecord> p2 = s -> "203".equals(s.getApprovalStatusCode());
            Predicate<LoanRecord> p3 = s -> "204".equals(s.getApprovalStatusCode());
            Predicate<LoanRecord> p4 = s -> "302".equals(s.getApprovalStatusCode());
            // 近一个月
            String month = DateUtils.formatDate(new Date(), "yyyyMM");
            List<LoanRecord> loanRecords = vo.getParams().getData().getLoanRecords();
            long count = loanRecords.stream().filter(s -> month.equals(s.getLoanDate())).count();
            long confuseCount = loanRecords.stream().filter(s -> month.equals(s.getLoanDate())).filter(p1.or(p2).or(p3)).count();
            long accessCount = loanRecords.stream().filter(s -> month.equals(s.getLoanDate())).filter(p4).count();
            if (count <= 0) {
                String prevMonth = DateUtils.formatDate(DateUtils.addMonth(new Date(), -1), "yyyyMM");
                count = loanRecords.stream().filter(s -> prevMonth.equals(s.getLoanDate())).count();
                confuseCount = loanRecords.stream().filter(s -> prevMonth.equals(s.getLoanDate())).filter(p1.or(p2).or(p3)).count();
                accessCount = loanRecords.stream().filter(s -> prevMonth.equals(s.getLoanDate())).filter(p4).count();
            }

            if (count != 0 && confuseCount / count == 1){
                setHitNum(1);
                remark =  String.format("近1个月借贷记录审批结果码“拒贷,审核中,客户放弃”比例为百分之百");
                hitRule.setRemark(remark);
                return hitRule;
            }

            // 近3个月
            String prev3Month = DateUtils.formatDate(DateUtils.addMonth(new Date(), -2), "yyyyMM");
            long prev3Count = loanRecords.stream().filter(s -> Long.valueOf(prev3Month) <= Long.valueOf(s.getLoanDate())).count();
            // 近一个月还款状态码无正常或结清

            long access3Count = 0l;
            if (prev3Count != 0){
                long prev3ConfuseCount = loanRecords.stream().filter(s -> Long.valueOf(prev3Month) <= Long.valueOf(s.getLoanDate())).filter(p1.or(p2).or(p3)).count();
                access3Count = loanRecords.stream().filter(s -> Long.valueOf(prev3Month) <= Long.valueOf(s.getLoanDate())).filter(p4).count();
                BigDecimal prev3 = BigDecimal.valueOf(prev3ConfuseCount).divide(BigDecimal.valueOf(prev3Count),2, BigDecimal.ROUND_HALF_UP);
                if (prev3.compareTo(BigDecimal.valueOf(0.65)) >= 0 || accessCount != 0){
                    setHitNum(1);
                    remark = String.format("近3个月借贷记录审批结果码“拒贷,审核中,客户放弃”比例大于等于百分之65,比例为：%s或者近一个月还款状态码有逾期,数量为为：%s", prev3, accessCount);
                    hitRule.setRemark(remark);
                    return hitRule;
                }
            }

            // 近12个月
            String prev12Month = DateUtils.formatDate(DateUtils.addMonth(new Date(), -11), "yyyyMM");
            long prev12Count = loanRecords.stream().filter(s -> Long.valueOf(prev12Month) <= Long.valueOf(s.getLoanDate())).count();
            if (prev12Count != 0){
                long prev12ConfuseCount = loanRecords.stream().filter(s -> Long.valueOf(prev12Month) <= Long.valueOf(s.getLoanDate())).filter(p1.or(p2).or(p3)).count();
                BigDecimal prev12 = BigDecimal.valueOf(prev12ConfuseCount).divide(BigDecimal.valueOf(prev12Count),2, BigDecimal.ROUND_HALF_UP);
                if (prev12.compareTo(BigDecimal.valueOf(0.40)) >= 0 || access3Count != 0){
                    setHitNum(1);
                    remark = String.format("近12个月借贷记录审批结果码“拒贷,审核中,客户放弃”比例大于等于百分之40,比例为：%s或者近三个月还款状态码有逾期,数量为为：%s", prev12, access3Count);
                    hitRule.setRemark(remark);
                    return hitRule;
                }
            }
        }
        remark = "宜信阿福借款历史数据为空";
        hitRule.setRemark(remark);
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT028);
    }
}
