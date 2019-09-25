package com.rongdu.loans.anrong.op;

import lombok.Data;

import java.io.Serializable;

/**
 * 安融-获取征信报告-请求报文
 * @author fy
 * @version 2019-06-17
 */
@Data
public class MSPReportOP implements Serializable {

    private String member;
    private String sign;
    private String customerName;
    private String paperNumber;
    private String loanId;
    private String loanTypeDesc;
    private String applyAssureType;
    private String applyLoanMoney;
    private String applyLoanTimeLimit;
    private String applyDate;
    private String applyCreditCity;
    private String quickRisk;
    private String phone;
}
