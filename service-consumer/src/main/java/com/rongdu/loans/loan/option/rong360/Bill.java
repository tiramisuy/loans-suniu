package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@lombok.Data
public class Bill implements Serializable{

	private static final long serialVersionUID = -8612816573977495509L;
	
	@JsonProperty("new_balance")
    private int newBalance;
    @JsonProperty("min_payment")
    private int minPayment;
    @JsonProperty("statement_start_date")
    private Date statementStartDate;
    private String month;
    @JsonProperty("statement_end_date")
    private Date statementEndDate;
    @JsonProperty("payment_cur_date")
    private Date paymentCurDate;
    @JsonProperty("payment_due_date")
    private Date paymentDueDate;
    @JsonProperty("credit_limit")
    private int creditLimit;
    @JsonProperty("cash_advance_limit")
    private int cashAdvanceLimit;
    @JsonProperty("last_balance")
    private int lastBalance;
    @JsonProperty("last_payment")
    private int lastPayment;
    @JsonProperty("new_charges")
    private int newCharges;
    private int interest;
    @JsonProperty("cur_adjust_amount")
    private int curAdjustAmount;
    @JsonProperty("last_points")
    private int lastPoints;
    private int repayment;
    private String currency;

}