package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class RepaymentPlanDetail implements Serializable {
    private static final long serialVersionUID = 7463730948437625842L;

    @JsonProperty("period_no")
    private String periodNo;

    @JsonProperty("period_no_desc")
    private String periodNoDesc;

    @JsonProperty("bill_status")
    private Integer billStatus;

    @JsonProperty("due_time")
    private String dueTime;

    @JsonProperty("can_repay_time")
    private String canRepayTime;

    @JsonProperty("pay_type")
    private Integer payType;

    private BigDecimal amount;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("overdue_fee")
    private BigDecimal overdueFee;

    @JsonProperty("overdue_day")
    private Integer overdueDay;

    @JsonProperty("success_time")
    private String successTime;

    private String remark;

    public void setBillStatus(Integer status, Date repayDate) {
        if (status.intValue() == 0) {
            this.billStatus = 1;
        }
        if (DateUtils.daysBetween(repayDate, new Date()) > 0) {
            this.billStatus = 3;
        }
        if (status.intValue() == 1) {
            this.billStatus = 2;
        }

    }
}
