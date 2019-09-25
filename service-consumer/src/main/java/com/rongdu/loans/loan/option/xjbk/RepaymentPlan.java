package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-30 16:41:17
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class RepaymentPlan implements Serializable{

    private static final long serialVersionUID = 4263856224249788131L;
    @JsonProperty("period_no")
    private String periodNo;
    private Integer principle;
    private Integer interest;
    @JsonProperty("service_fee")
    private Integer serviceFee;
    @JsonProperty("bill_status")
    private Integer billStatus;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("already_paid")
    private Integer alreadyPaid;
    @JsonProperty("loan_time")
    private Integer loanTime;
    @JsonProperty("due_time")
    private Integer dueTime;
    @JsonProperty("can_pay_time")
    private Integer canPayTime;
    @JsonProperty("finish_pay_time")
    private Integer finishPayTime;
    @JsonProperty("overdue_day")
    private Integer overdueDay;
    @JsonProperty("overdue_fee")
    private Integer overdueFee;
    @JsonProperty("period_fee_desc")
    private String periodFeeDesc;
    @JsonProperty("pay_type")
    private Integer payType;


}