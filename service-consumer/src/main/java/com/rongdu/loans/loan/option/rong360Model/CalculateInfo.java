package com.rongdu.loans.loan.option.rong360Model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lee on 2018/7/5.
 */
@Data
public class CalculateInfo implements Serializable {
    private static final long serialVersionUID = 2904374164882397592L;
    private int delayDays;
    private BigDecimal overdueFee;
    private BigDecimal deduction;
    private BigDecimal delayInterest;
    private BigDecimal delayAmt;
    private Date deferRepayDay;
    private BigDecimal totalAmount;
    private BigDecimal interest;
    private BigDecimal delayBaseAmt;
    private BigDecimal principal;
    private int overdueDays;
    private BigDecimal loanApplyInterest;
}
