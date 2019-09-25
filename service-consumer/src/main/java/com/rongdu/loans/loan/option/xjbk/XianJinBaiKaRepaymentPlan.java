package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-30 16:41:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class XianJinBaiKaRepaymentPlan implements Serializable{

    private static final long serialVersionUID = -6619466977720063453L;
    @JsonProperty("order_sn")
    private String orderSn;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("total_svc_fee")
    private Integer totalSvcFee;
    @JsonProperty("received_amount")
    private Integer receivedAmount;
    @JsonProperty("already_paid")
    private Integer alreadyPaid;
    @JsonProperty("total_period")
    private Integer totalPeriod;
    @JsonProperty("finish_period")
    private Integer finishPeriod;
    @JsonProperty("repayment_plan")
    private List<RepaymentPlan> repaymentPlan;


}