package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.dwd.charge.Transaction;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 规则名称：运营商账单
 */
public class AT014Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 加载聚信立分析报告数据
        DWDReport report = getDataInvokeService().getdwdReport(context);

        //命中的规则
        HitRule hitRule = checkRule(report);
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
     * 运营商报告的套餐及固定费大于等于50，小于200 ，取前5个月
     * @param report
     * @return
     */
    private HitRule checkRule(DWDReport report) {
        HitRule hitRule = createHitRule(getRiskRule());
        String remark = null;

        if (report != null && CollectionUtils.isNotEmpty(report.getTransactions()) ) {
            flag:
            for(int i=1;i<6;i++){
                String month = getLastMonth(i) ;
                int num =0 ;
                for (Transaction transaction : report.getTransactions()) {
                    if(transaction.getBillCycle()!=null&&transaction.getBillCycle().length() > 7){
                        String transMonth = transaction.getBillCycle().substring(0,7);
                        if(month.equals(transMonth)){
                            num++;
                            //如前一个月为0，则取前4个月最低值
                            if(i==1&&transaction.getPlanAmt()==0){
                                break flag;
                            }
                            if (transaction.getPlanAmt() < 50 || transaction.getPlanAmt() >= 200) {
                                setHitNum(1);
                                remark = String.format("运营商报告的套餐及固定费：%s", transaction.getPlanAmt());
                                hitRule.setRemark(remark);
                                return hitRule;
                            }

                        }
                    }

                }
                if(num==0 && i>1){
                    setHitNum(1);
                    remark = String.format("运营商报告的套餐及固定费无账单：%s",month);
                    hitRule.setRemark(remark);
                    break ;
                }
            }

        } else {
            setHitNum(1);
            remark = "运营商账单为空";
            hitRule.setRemark(remark);
        }
        return hitRule;
    }

    public String getLastMonth(int i) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - i); // 设置为上一个月
        date = calendar.getTime();
        String accDate = format.format(date);
        return accDate;
   }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT014);
    }
}
